package app.telephony.fsm.guards;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;
import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.entities.Group;
import app.entities.GroupMembership;
import app.telephony.RuralictSession;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.EventTypeGuard;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnGroupIDExist extends EventTypeGuard<IVRSession> {

	boolean allow;

	ArrayList<String> groupsID;

	public OnGroupIDExist(boolean allow) {
		super(GotDTMFEvent.class);
		this.allow=allow;
	}


	// returns true if:
	// 1. groupId exists and allow=true
	// 2. groupId doesn't exist and allow=false
	// returns false in all other cases	
	@Override
	public boolean accept(Event<Object>event, IVRSession session, State<?> state) {
		// TODO Auto-generated method stub
		
		RuralictSession ictSession = (RuralictSession) session;
		OrganizationService orgService = ictSession.getOrganizationService();
		GroupService groupService = ictSession.getGroupService();
				
		if (super.accept(event, session, state)) {
			GotDTMFEvent ev = (GotDTMFEvent) event;
			int groupID = Integer.parseInt(ev.getInput());
			Group g = groupService.getGroup(groupID);
			if(g!=null){
				if(g.getOrganization()==orgService.getOrganizationByIVRS(ictSession.getIvrNumber())){
					return (true==allow);
				}
			}
			return (false==allow);
		}

		return (false==allow);
	}

}
