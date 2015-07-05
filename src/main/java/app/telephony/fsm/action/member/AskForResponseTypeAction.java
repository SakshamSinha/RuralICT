package app.telephony.fsm.action.member;


import java.util.Set;
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

		Integer i=1,keys=RuralictStateMachine.tempResponseMap.size();
		for(Integer j=1;j<=keys;j++){
			String key=j.toString();
			String responseType = RuralictStateMachine.tempResponseMap.get(key);
			RuralictStateMachine.tempResponseMap.remove(key);
			if( (responseType != null) &&
					((responseType.equalsIgnoreCase("Order") && ruralictSession.isOrderAllowed()) ||
							(responseType.equalsIgnoreCase("Feedback") && ruralictSession.isFeedbackAllowed()) ||
							(responseType.equalsIgnoreCase("Response") && ruralictSession.isResponseAllowed())) ){	
				String language = ruralictSession.getLanguage();

				if(language.equalsIgnoreCase("en"))
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for_"+responseType+"_"+language+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press_"+(i)+"_"+language+".wav"); //Press
				}
				else
				{
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/for_"+responseType+"_"+language+".wav"); //For
					response.addPlayAudio(Configs.Voice.VOICE_DIR+"/press_"+(i)+"_"+language+".wav"); //Press
				}
				RuralictStateMachine.tempResponseMap.put(i.toString(),responseType);
				i++;
			}

		}

		cd.setMaxDigits(1);
		cd.setTimeOut(Configs.Telephony.DTMF_TIMEOUT);
		response.addCollectDtmf(cd);
	}

	private static String[] toStringArray(Set<String> keys){
		String[] res = new String[keys.size()];
		int i=0;
		for(String s:keys){
			res[i]=s;
			i++;
		}
		return res;
	}
}
