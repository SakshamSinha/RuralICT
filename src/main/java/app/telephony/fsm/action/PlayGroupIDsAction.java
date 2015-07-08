package app.telephony.fsm.action;

import java.util.HashMap;
import java.util.List;

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
		GroupService groupService = SpringContextBridge.services().getGroupService();
		OrganizationService organizationService = SpringContextBridge.services().getOrganizationService();
		HashMap<Integer,String> groups = new HashMap<Integer, String>();
		List<Group> group = groupService.getGroupListByOrganization(organizationService.getOrganizationByIVRS(session.getIvrNumber()));
		if(group.isEmpty()){
			response.addPlayText("You are currently not added to any groups", Configs.Telephony.TTS_SPEED);
		}

		for(Group groupList : group){

			Integer groupId =groupList.getGroupId();
			String groupName = groupList.getName();
			groups.put(groupId, groupName);

		}

		for(Integer k:groups.keySet()){

			response.addPlayText(k.toString(), Configs.Telephony.TTS_SPEED);	// Group ID
			response.addPlayText(groups.get(k), Configs.Telephony.TTS_SPEED);   // Group Name

		}

	}

}
