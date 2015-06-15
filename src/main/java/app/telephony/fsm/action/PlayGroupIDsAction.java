package app.telephony.fsm.action;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.*;
import app.business.services.springcontext.SpringContextBridge;
import app.telephony.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

import app.entities.*;

public class PlayGroupIDsAction implements Action<IVRSession> {


	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/theGroupIDsAre.wav");
		GroupMembershipService groupMembershipService = SpringContextBridge.services().getGroupMembershipService();
		OrganizationService organizationService = SpringContextBridge.services().getOrganizationService();
		UserPhoneNumberService userPhoneNumberService=SpringContextBridge.services().getUserPhoneNumberService();
		HashMap<String,String> groups = new HashMap<String, String>();
		List<GroupMembership>  groupMemberships = groupMembershipService.getGroupsByUserAndOrganizationSorted(
				userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser(), 
				organizationService.getOrganizationByIVRS(session.getIvrNumber()));

		if(groupMemberships.size()==0){
			response.addPlayText("You are currently not added to any groups", Configs.Telephony.TTS_SPEED);
		}

		for(GroupMembership groupMemberShip : groupMemberships){
			String groupId = groupMemberShip.getGroup().getGroupId()+"";
			String groupName = groupMemberShip.getGroup().getName();
			groups.put(groupId, groupName);

		}

		for(String k:groups.keySet()){
			response.addPlayText(k, Configs.Telephony.TTS_SPEED);				// Group ID
			response.addPlayText(groups.get(k), Configs.Telephony.TTS_SPEED);   // Group Name

		}

	}

}
