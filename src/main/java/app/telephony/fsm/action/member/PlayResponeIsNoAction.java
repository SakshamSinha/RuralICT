package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.BroadcastRecipientService;
import app.business.services.BroadcastScheduleService;
import app.business.services.GroupService;
import app.business.services.TelephonyService;
import app.business.services.UserPhoneNumberService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Group;
import app.entities.InboundCall;
import app.entities.OutboundCall;
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

public class PlayResponeIsNoAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		RuralictSession ruralictSession = (RuralictSession) session;
		String mode = "web";
		String type ="response";
		InboundCall inboundCall = ruralictSession.getCall();
		OutboundCall outboundCall = ruralictSession.getOutboundCall();
		String groupID = ruralictSession.getGroupID();
		int groupId = Integer.parseInt(groupID);
		GroupService groupService = SpringContextBridge.services().getGroupService();
		BroadcastRecipientService broadcastRecipientService = SpringContextBridge.services().getBroadcastRecipientService();
		BroadcastScheduleService broadcastScheduleService = SpringContextBridge.services().getBroadcastScheduleService();
		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
		Group group = groupService.getGroup(groupId);
		Broadcast broadcast = new VoiceBroadcast();
		broadcast.setBroadcastId(ruralictSession.getBroadcastID());
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		
		if(ruralictSession.isOutbound()){
		
			outboundCall.setBroadcastRecipient(broadcastRecipientService.getBroadcastRecipientByUserAndBroadcast(userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser() , broadcast));
			outboundCall.setBroadcastSchedule(broadcastScheduleService.getAllBroadcastScheduleListByBroadcast(broadcast).get(0));
			telephonyService.addVoiceMessage(session.getUserNumber(), broadcast, group, mode, type, false, null, null,outboundCall);
			
		}
		else{
			
			telephonyService.addVoiceMessage(session.getUserNumber(),null,group, mode, type, false,null,inboundCall,null);
		}


		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/yourResponseIsNo_"+ruralictSession.getLanguage()+".wav");

	}


}
