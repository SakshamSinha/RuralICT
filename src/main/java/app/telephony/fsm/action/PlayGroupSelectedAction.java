package app.telephony.fsm.action;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.util.IVRUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import app.business.services.BroadcastRecipientService;
import app.business.services.BroadcastScheduleService;
import app.business.services.VoiceService;
import app.business.services.broadcast.BroadcastService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.BroadcastRecipient;
import app.entities.BroadcastSchedule;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.entities.Voice;
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
		VoiceService voiceService = SpringContextBridge.services().getVoiceService();
		String messageURL = ruralictSession.getMessageURL();
		String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		Voice voice=new Voice(url,false);
		voice = voiceService.addVoice(voice);
		VoiceBroadcast voicebroadcast = new VoiceBroadcast(
				organization,
				SpringContextBridge.services().getGroupService().getGroup(Integer.parseInt(ruralictSession.getGroupID())),
				SpringContextBridge.services().getUserPhoneNumberService().getUserPhoneNumber(session.getUserNumber()).getUser(),
				"phone",
				organization.getBroadcastDefaultSettings().get(0).getAskFeedback(),
				organization.getBroadcastDefaultSettings().get(0).getAskOrder(),
				organization.getBroadcastDefaultSettings().get(0).getAskResponse(),
				false,
				voice,
				true);

		java.util.Date date= new java.util.Date();
		Timestamp currentTimestamp= new Timestamp(date.getTime());
		voicebroadcast.setBroadcastedTime(currentTimestamp);
		BroadcastService broadcastService = SpringContextBridge.services().getVoiceBroadcastService();
		// Add row for broadcast
		voicebroadcast = (VoiceBroadcast) broadcastService.addBroadcast(voicebroadcast);
		BroadcastRecipientService broadcastRecipientService = SpringContextBridge.services().getBroadcastRecipientService();
		Group group = SpringContextBridge.services().getGroupService().getGroup(Integer.parseInt(ruralictSession.getGroupID()));
		List<GroupMembership> memberships = SpringContextBridge.services().getGroupMembershipService().getGroupMembershipListByGroup(group);


		//Adding Broadcast schedule 
		//set the time at which you have actually send the schedule and set the send to all field as well.
		BroadcastScheduleService broadcastScheduleService = SpringContextBridge.services().getBroadcastScheduleService();
		BroadcastSchedule broadcastSchedule = new BroadcastSchedule(voicebroadcast, currentTimestamp, false);
		broadcastScheduleService.addBroadcastSchedule(broadcastSchedule);

		// Add rows for each broadcast-recipient
		List<BroadcastRecipient> broadcastRecipients = new ArrayList<BroadcastRecipient>();
		// Add rows for each broadcast-recipient
		for(GroupMembership gm:memberships){
			User user = gm.getUser();
			BroadcastRecipient broadcastRecipient = new BroadcastRecipient(voicebroadcast,user);

			// Add row for broadcast-recipient
			broadcastRecipientService.addBroadcastRecipient(broadcastRecipient);
			broadcastRecipients.add(broadcastRecipient);
		}

		//Different for loop to avoid problem in IVRS
		for(BroadcastRecipient recipient: broadcastRecipients)
		{
			User user=recipient.getUser();
			List<UserPhoneNumber> phoneNumbers=user.getUserPhoneNumbers();
			for(UserPhoneNumber no:phoneNumbers)
			{        
				//Outbound call has to be appended with a zero after removing 91 
				String phoneNumber = "0" + no.getPhoneNumber().substring(2);
				if(IVRUtils.makeOutboundCall(phoneNumber, Configs.Telephony.IVR_NUMBER, Configs.Telephony.OUTBOUND_APP_URL))
				{
					break;
				}
			}
		}

		response.addHangup();
	}

}
