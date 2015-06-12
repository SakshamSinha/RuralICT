package app.telephony.fsm.action.member;

import java.util.Arrays;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.springcontext.SpringContextBridge;
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
		
		OrganizationService organisationService = SpringContextBridge.services().getOrganizationService();
		String langs = "123";
		//organisationService.getOrganizationByIVRS(session.getIvrNumber()).getDefaultCallLocale();
		int i=1;
		String[] responses = new String[RuralictStateMachine.tempLanguageMap.size()];
		Object[] keys = RuralictStateMachine.tempLanguageMap.keySet().toArray();
		Arrays.sort(keys);
		for(Object k:keys){
			if(langs.contains((String)k)){
				String l = RuralictStateMachine.tempLanguageMap.get(k);
				
				if(l.equalsIgnoreCase("en"))
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for_"+l+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+l+".wav"); //language
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press_"+l+".wav"); //Press
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+(i)+"_"+l+".wav"); // 'i'
				}
				else
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+l+".wav"); //language
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for_"+l+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+(i)+"_"+l+".wav"); // 'i'
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press_"+l+".wav"); //Press
				}
				responses[i-1]=l;
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
