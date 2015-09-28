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
import app.entities.Voice;
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

public class DoStoreFeedbackMessageAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		RuralictSession ruralictSession = (RuralictSession) session;
		String messageURL=ruralictSession.getMessageURL();
		InboundCall inboundCall=ruralictSession.getCall();
		OutboundCall outboundCall=ruralictSession.getOutboundCall();
		Broadcast broadcast  = new VoiceBroadcast();
		GroupService groupService = SpringContextBridge.services().getGroupService();
		BroadcastRecipientService broadcastRecipientService = SpringContextBridge.services().getBroadcastRecipientService();
		BroadcastScheduleService broadcastScheduleService = SpringContextBridge.services().getBroadcastScheduleService();
		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
		String groupID = ruralictSession.getGroupID();
		int groupId = Integer.parseInt(groupID);
		Group group = groupService.getGroup(groupId);
		broadcast.setBroadcastId(ruralictSession.getBroadcastID());
		Voice voice = new Voice();
		boolean isOutboundCall = ruralictSession.isOutbound();
		inboundCall.setDuration(ruralictSession.getRecordEvent().getDuration());
		String mode = "web";
		String type ="feedback";
		String feedbackUrl = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		voice.setUrl(messageURL);
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		if(isOutboundCall){

			outboundCall.setBroadcastRecipient(broadcastRecipientService.getBroadcastRecipientByUserAndBroadcast(userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser() , broadcast));
			outboundCall.setBroadcastSchedule(broadcastScheduleService.getAllBroadcastScheduleListByBroadcast(broadcast).get(0));
			telephonyService.addVoiceMessage(session.getUserNumber(),broadcast,group, mode , type , false ,feedbackUrl,null,outboundCall);
		}
		else{

			telephonyService.addVoiceMessage(session.getUserNumber(), null, group, mode , type , false ,feedbackUrl, inboundCall,null);
		}

		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/recordingDone_"+ruralictSession.getLanguage()+".mp3");
	}


}
