package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.RecordEvent;
import app.business.services.GroupService;
import app.business.services.TelephonyService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Group;
import app.entities.InboundCall;
import app.entities.Voice;
import app.entities.broadcast.Broadcast;
import app.entities.broadcast.VoiceBroadcast;
import app.telephony.RuralictSession;
import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;

public class DoStoreOrderMessageAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		String messageURL=session.getMessageURL();
		RecordEvent recordEvent = (RecordEvent) event;
		RuralictSession ruralictSession = (RuralictSession) session;
		Voice voiceMessage = new Voice();
		InboundCall inboundCall= ruralictSession.getCall();
		String mode = "web";
		String type ="order";
		String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		boolean isOutboundCall = ruralictSession.isOutbound();
		voiceMessage.setUrl(messageURL);
		ruralictSession.setVoiceMessage(voiceMessage);
		inboundCall.setDuration(recordEvent.getDuration());
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		String groupID = session.getGroupID();
		int groupId = Integer.parseInt(groupID);
		GroupService groupService = SpringContextBridge.services().getGroupService();
		Group group = groupService.getGroup(groupId);
		Broadcast broadcast = new VoiceBroadcast();
		broadcast.setBroadcastId(ruralictSession.getBroadcastID());

		if(isOutboundCall){

			telephonyService.addVoiceMessage(session.getUserNumber(),broadcast, group, mode , type , false ,url, inboundCall);
		}
		else{

			telephonyService.addVoiceMessage(session.getUserNumber(), null , group, mode , type , false ,url, inboundCall);
		}

	}

}
