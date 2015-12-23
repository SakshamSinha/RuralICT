package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;

import java.util.ArrayList;

import app.business.services.GroupService;
import app.business.services.OrganizationService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Group;
import app.telephony.RuralictSession;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.EventTypeGuard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnGroupIDExist extends EventTypeGuard<IVRSession> {

	boolean allow;

	ArrayList<String> groupsID;

	public OnGroupIDExist(boolean allow) {
		super(GotDTMFEvent.class);
		this.allow=allow;
	}

	/**	 returns true if:
	 1. groupId exists and allow=true
	 2. groupId doesn't exist and allow=false
	 returns false in all other cases */	
	@Override
	public boolean accept(Event<Object>event, IVRSession session, State<?> state) {

		if (super.accept(event, session, state)) {
			RuralictSession ruralictSession = (RuralictSession) session;

			OrganizationService orgService = SpringContextBridge.services().getOrganizationService();
			GroupService groupService = SpringContextBridge.services().getGroupService();

			GotDTMFEvent ev = (GotDTMFEvent) event;
			int groupID = Integer.parseInt(ev.getInput());
			Group group = groupService.getGroup(groupID);
			if(group!=null){
				if(group.getOrganization()==orgService.getOrganizationByIVRS(session.getIvrNumber())){
					ruralictSession.setGroupID(((Integer)groupID).toString());
					return (allow);
				}
			}
			return (!allow);
		}

		return (false);
	}

}
