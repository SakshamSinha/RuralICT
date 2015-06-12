package app.telephony.fsm.action.member;

import java.sql.Timestamp;

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
import app.telephony.RuralictSession;
import app.telephony.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class DoStoreOrderMessageAction implements Action<IVRSession> {

	@Autowired
	Voice voice;
	
	//@Autowired
	//VoiceService voiceService;
	
	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		String messageURL=session.getMessageURL();
		RecordEvent recordEvent = (RecordEvent) event;
		RuralictSession ruralictSession = (RuralictSession) session;
	
	 	Voice voiceMessage = new Voice();
		InboundCall inboundCall= new InboundCall();
        String mode = "web";
        String type ="order";
        String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		
		voiceMessage.setUrl(messageURL);
		ruralictSession.setVoiceMessage(voiceMessage);
		inboundCall.setDuration(recordEvent.getDuration());
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		String groupID = session.getGroupID();
		int groupId = Integer.parseInt(groupID);
		GroupService groupService = SpringContextBridge.services().getGroupService();
		Group group = groupService.getGroup(groupId);
	    telephonyService.addVoiceMessage(session.getUserNumber(),null,group, mode , type , false ,url, inboundCall);
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/orderMessageConfirmed_"+session.getLanguage()+".wav");
		
 	}

}
