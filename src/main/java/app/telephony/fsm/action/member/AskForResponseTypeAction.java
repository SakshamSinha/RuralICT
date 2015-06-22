package app.telephony.fsm.action.member;

import java.util.Arrays;
import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.telephony.RuralictSession;
import app.telephony.config.Configs;
import app.telephony.fsm.RuralictStateMachine;

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

		RuralictSession ruralictSession = (RuralictSession) session;
		Response response = session.getResponse();
		CollectDtmf cd = new CollectDtmf();

		boolean[] responses = new boolean[] {false,false,false};
		responses[0] = ruralictSession.isOrderAllowed();
		responses[1] = ruralictSession.isFeedbackAllowed();
		responses[2] = ruralictSession.isResponseAllowed();
		int i=1,j=0;
		String[] newResponses = new String[RuralictStateMachine.tempResponseMap.size()];
		String[] responseTypeKeys=new String[RuralictStateMachine.tempResponseMap.size()];
				
		for(String key:RuralictStateMachine.tempResponseMap.keySet()){
			responseTypeKeys[j]=key;
			j++;
		}
		Arrays.sort(responseTypeKeys);
		for(String k:responseTypeKeys){
			if(responses[Integer.parseInt((String)k)-1]){
				String responseType = RuralictStateMachine.tempResponseMap.get(k);
				String language = session.getLanguage();

				if(language.equalsIgnoreCase("en"))
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press_"+language+".wav"); //Press
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+(i)+"_"+language+".wav"); // 'i'
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for_"+language+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+responseType+"_"+language+".wav"); //language
					
				}
				else
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+responseType+"_"+language+".wav"); //language
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for_"+language+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/"+(i)+"_"+language+".wav"); // 'i'
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press_"+language+".wav"); //Press
				}

				newResponses[i-1]=responseType;
				i++;
			}
		}
		RuralictStateMachine.tempResponseMap.clear();
		for(i=0;i<newResponses.length;i++){
			RuralictStateMachine.tempResponseMap.put((i+1)+"", newResponses[i]);
		}

		cd.setMaxDigits(1);
		cd.setTimeOut(Configs.Telephony.DTMF_TIMEOUT);
		response.addCollectDtmf(cd);
	}
}
