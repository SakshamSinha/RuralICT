package app.telephony.fsm.action.member;

import java.util.Arrays;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.springcontext.SpringContextBridge;
import app.telephony.fsm.RuralictStateMachine;
import app.telephony.fsm.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.CollectDtmf;
import com.ozonetel.kookoo.Response;

public class AskForResponseTypeAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		CollectDtmf cd = new CollectDtmf();

		OrganizationService organisationService = SpringContextBridge.services().getOrganizationService();
		boolean[] responses = new boolean[] {false,false,false};
		responses[0] = organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskResponse();
		responses[1] = organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskOrder();
		responses[2] = organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskFeedback();
		
		int i=1;
		String[] newResponses = new String[RuralictStateMachine.tempResponseMap.size()];
		Object[] keys = RuralictStateMachine.tempResponseMap.keySet().toArray();
		Arrays.sort(keys);
		for(Object k:keys){
			if(responses[Integer.parseInt((String)k)-1]){
				String responseType = RuralictStateMachine.tempResponseMap.get(k);
				String l = session.getLanguage();
				
				if(l.equalsIgnoreCase("English"))
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for"+l+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+responseType+l+".wav"); //language
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press"+l+".wav"); //Press
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+(i)+""+l+".wav"); // 'i'
				}
				else
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+responseType+l+".wav"); //language
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for"+l+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+(i)+""+l+".wav"); // 'i'
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press"+l+".wav"); //Press
				}
				
				newResponses[i-1]=responseType;
				i++;
			}
		}
		RuralictStateMachine.tempResponseMap.clear();
		for(i=0;i<responses.length;i++){
			RuralictStateMachine.tempResponseMap.put((i+1)+"", newResponses[i]);
		}
		
		//cd.addPlayText("If you are satisfied with your message, press 1. To cancel your message, press 2.", Configs.Telephony.TTS_SPEED);
		/*
		cd.addPlayAudio(Configs.Voice.VOICE_DIR + "/press1ForOrder"+session.getLanguage()+".wav");
		cd.addPlayAudio(Configs.Voice.VOICE_DIR + "/press2ForFeedback"+session.getLanguage()+".wav");
		cd.addPlayAudio(Configs.Voice.VOICE_DIR + "/press3ForResponse"+session.getLanguage()+".wav");
		*/
		cd.setMaxDigits(1);
		cd.setTimeOut(Configs.Telephony.DTMF_TIMEOUT);
		response.addCollectDtmf(cd);
	}
}
