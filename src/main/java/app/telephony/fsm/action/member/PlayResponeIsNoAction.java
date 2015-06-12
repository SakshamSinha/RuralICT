package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.GroupService;
import app.business.services.TelephonyService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Group;
import app.entities.InboundCall;
import app.telephony.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class PlayResponeIsNoAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();

		String mode = "web";
        String type ="response";
		InboundCall inboundCall = new InboundCall();
		String groupID = session.getGroupID();
		int groupId = Integer.parseInt(groupID);
		GroupService groupService = SpringContextBridge.services().getGroupService();
		Group group = groupService.getGroup(groupId);
 		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
 		 telephonyService.addBinaryMessage(session.getUserNumber(),null,group, mode, type, true,inboundCall.getTime());
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/yourResponseIsNo_"+session.getLanguage()+".wav");
				
	}


}
