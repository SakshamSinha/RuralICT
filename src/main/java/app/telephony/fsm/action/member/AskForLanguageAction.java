package app.telephony.fsm.action.member;

import java.util.Arrays;


import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.telephony.config.Configs;
import app.telephony.fsm.RuralictStateMachine;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.CollectDtmf;
import com.ozonetel.kookoo.Response;

public class AskForLanguageAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		CollectDtmf cd = new CollectDtmf();
		String langs = "123"; //organisationService.getOrganizationByIVRS(session.getIvrNumber()).getDefaultCallLocale();
		int i=1;
		String[] responses = new String[RuralictStateMachine.tempLanguageMap.size()];
		Object[] responseKeys = RuralictStateMachine.tempLanguageMap.keySet().toArray();
		Arrays.sort(responseKeys);
		for(Object k:responseKeys){
			if(langs.contains((String)k)){
				String language = RuralictStateMachine.tempLanguageMap.get(k);

				if(language.equalsIgnoreCase("en"))
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for_"+language+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+language+".wav"); //language
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press_"+language+".wav"); //Press
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+(i)+"_"+language+".wav"); // 'i'
				}
				else
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+language+".wav"); //language
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for_"+language+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+(i)+"_"+language+".wav"); // 'i'
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press_"+language+".wav"); //Press
				}
				responses[i-1]=language;
				i++;
			}
		}
		RuralictStateMachine.tempLanguageMap.clear();
		for(i=0;i<responses.length;i++){
			RuralictStateMachine.tempLanguageMap.put((i+1)+"", responses[i]);
		}

		cd.setMaxDigits(1);
		cd.setTimeOut(Configs.Telephony.DTMF_TIMEOUT);
		response.addCollectDtmf(cd);
	}


}
