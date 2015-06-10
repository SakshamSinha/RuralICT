package app.telephony.fsm.action.member;


import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.TelephonyService;
import app.business.services.VoiceService;
import app.business.services.message.MessageService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.InboundCall;
import app.telephony.fsm.config.Configs;

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
		String mode = "web";
        String type ="response";
		InboundCall inboundCall = new InboundCall();
 
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		
		telephonyService.addBinaryMessage(session.getUserNumber(), mode, type, true,inboundCall.getTime());
		

		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/yourResponseIsYes_"+session.getLanguage()+".wav");
				
	}



}
