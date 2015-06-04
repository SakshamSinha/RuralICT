package app.telephony.fsm.action.member;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.RecordEvent;
import app.business.services.TelephonyService;
import app.business.services.VoiceService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.InboundCall;
import app.entities.Voice;
import app.telephony.RuralictSession;
import app.telephony.fsm.config.Configs;

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
		
		/*
		RecordEvent recordEvent = (RecordEvent) event;
*/		RuralictSession ruralictSession = (RuralictSession) session;
		Voice message = new Voice();
		System.out.println(message.toString());
		InboundCall messagea = new InboundCall();

		/*messagea.setDuration(recordEvent.getDuration());*/
		message.setUrl(messageURL);
		
		ruralictSession.setVoiceMessage(message);
		
		voice = new Voice("http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav" , false);
		RuralictSession ictSession = (RuralictSession) session;
		VoiceService voiceService = ictSession.getVoiceService();
		
		/*VoiceService voiceService = SpringContextBridge.services().getVoiceService();*/
		System.out.println((voiceService==null)+" ---- "+(voice==null));
	/*	TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		telephonyService.addVoiceMessage(session.getUserNumber(),)*/
		
		voiceService.addVoice(voice);
				   
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/orderMessageConfirmed"+session.getLanguage()+".wav");
		
 	}

}
