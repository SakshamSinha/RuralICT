package app.telephony.fsm.action;

import java.util.Calendar;
import java.util.Random;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.telephony.fsm.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Record;
import com.ozonetel.kookoo.Response;

public class DoRecordMessageAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();

		//response.addPlayText("Please record your message after the beep, and press # to finish.", Configs.Telephony.TTS_SPEED);
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/broadcastMessageRecordAfterBeep.wav");

		Record record = new Record();
		record.setFileName("message" + Calendar.getInstance().getTimeInMillis() + ((new Random()).nextInt(90000) + 10000));
		record.setMaxDuration(Configs.Telephony.MAX_RECORDING_DURATION);
		record.setSilence(Configs.Telephony.RECORDING_SILENCE);
		response.addRecord(record);
	}

}
