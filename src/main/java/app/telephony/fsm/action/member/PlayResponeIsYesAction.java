package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.GroupService;
import app.business.services.TelephonyService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Group;
import app.entities.InboundCall;
import app.entities.broadcast.Broadcast;
import app.entities.broadcast.VoiceBroadcast;
import app.telephony.RuralictSession;
import app.telephony.config.Configs;
import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class PlayResponeIsYesAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		RuralictSession ruralictSession = (RuralictSession) session;
		String mode = "web";
		String type ="response";
		InboundCall inboundCall = ruralictSession.getCall();
		String groupID = session.getGroupID();
		int groupId = Integer.parseInt(groupID);
		GroupService groupService = SpringContextBridge.services().getGroupService();
		Group group = groupService.getGroup(groupId);
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		Broadcast broadcast = new VoiceBroadcast();
		broadcast.setBroadcastId(ruralictSession.getBroadcastID());
		if(ruralictSession.isOutbound()){

			telephonyService.addBinaryMessage(session.getUserNumber(),broadcast, group, mode , type , true , inboundCall.getTime());
		}
		else{

			telephonyService.addBinaryMessage(session.getUserNumber(),null,group, mode, type, true,inboundCall.getTime());
		}

		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/yourResponseIsYes_"+session.getLanguage()+".wav");

	}



}
