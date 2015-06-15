package app.telephony.fsm.action.member;

import org.springframework.beans.factory.annotation.Autowired;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.RecordEvent;
import app.business.services.GroupService;
import app.business.services.TelephonyService;
import app.business.services.VoiceService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Group;
import app.entities.InboundCall;
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


	@Autowired
	Voice voice;


	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		RuralictSession ruralictSession = (RuralictSession) session;
		String messageURL=session.getMessageURL();
		InboundCall inboundCall=ruralictSession.getCall();
		Broadcast broadcast  = new VoiceBroadcast();
		GroupService groupService = SpringContextBridge.services().getGroupService();
		String groupID = session.getGroupID();
		int groupId = Integer.parseInt(groupID);
		Group group = groupService.getGroup(groupId);
		broadcast.setBroadcastId(ruralictSession.getBroadcastID());
		Voice voiceMessage = new Voice();
		boolean isOutboundCall = ruralictSession.isOutbound();
		inboundCall.setDuration(ruralictSession.getRecordEvent().getDuration());
		String mode = "web";
		String type ="feedback";
		String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		voiceMessage.setUrl(messageURL);
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		if(isOutboundCall){
			
			telephonyService.addVoiceMessage(session.getUserNumber(),broadcast,group, mode , type , false ,url,null);
		}
		else{

			telephonyService.addVoiceMessage(session.getUserNumber(),null , group, mode , type , false ,url, inboundCall);
		}



	}


}
