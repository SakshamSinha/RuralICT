package app.telephony.fsm.action;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.broadcast.BroadcastService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Organization;
import app.entities.broadcast.VoiceBroadcast;
import app.telephony.RuralictSession;
import app.telephony.config.Configs;
import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class PlayGroupSelectedAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		RuralictSession ruralictSession = (RuralictSession) session;
		Organization organization = SpringContextBridge.services().getOrganizationService().getOrganizationByIVRS(session.getIvrNumber());
		VoiceBroadcast voicebroadcast = new VoiceBroadcast(
				organization,
				SpringContextBridge.services().getGroupService().getGroup(Integer.parseInt(session.getGroupID())),
				SpringContextBridge.services().getUserPhoneNumberService().getUserPhoneNumber(session.getUserNumber()).getUser(),
				"phone",
				organization.getBroadcastDefaultSettings().get(0).getAskFeedback(),
				organization.getBroadcastDefaultSettings().get(0).getAskOrder(),
				organization.getBroadcastDefaultSettings().get(0).getAskResponse(),
				false,
				ruralictSession.getVoiceMessage(),
				true);
		voicebroadcast.setOrganization(organization);
		BroadcastService broadcastService = SpringContextBridge.services().getVoiceBroadcastService();
		broadcastService.addBroadcast(voicebroadcast);
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/groupHasBeenSelected.wav");

	}

}
