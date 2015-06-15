package app.telephony;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.RecordEvent;

import com.continuent.tungsten.commons.patterns.fsm.*;

import app.business.services.GroupService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.VoiceService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.InboundCall;
import app.entities.Organization;
import app.entities.Voice;
import app.telephony.fsm.*;

public class RuralictSession extends IVRSession {

	/**
	 * The persistence object associated with this session.
	 */
	InboundCall call;
	/**
	 * The last recorded message in this session, if any.
	 */
	
	Voice voiceMessage ;
	boolean isOutbound=false;
	boolean orderAllowed=false;
	boolean feedbackAllowed=false;
	boolean responseAllowed=false;
	int broadcastID;
	RecordEvent recordEvent;


	OrganizationService organizationService = SpringContextBridge.services().getOrganizationService();


	/**
	 * @param groupService 
	 * @throws InstantiationException 
	 * @see {@link IVRSession#IVRSession(String, String, String, String, String, Class)}
	 */
	
	public RuralictSession(String sessionId, String userNumber, String ivrNumber, String circle, String operator)
			throws FiniteStateException, InstantiationException {

		super(sessionId, userNumber, ivrNumber, circle, operator, RuralictStateMachine.class);

		call = new InboundCall();
		call.setTime(new Timestamp(getStartTime().getTime()));
		call.setFromNumber(userNumber);
		call.setOrganization(organizationService.getOrganizationByIVRS(ivrNumber));

	}

	/**
	 * @see IVRSession#finish(long)
	 */
	@Override
	public void finish(long totalCallDuration) {
		super.finish(totalCallDuration);
		call.setDuration((int) totalCallDuration);
	}

	/**
	 * Returns the persistence object associated with this session.
	 * @return The Call persistence object.
	 */

	public InboundCall getCall() {
		return call;
	}

	public void setCall(InboundCall call) {
		this.call = call;
	}

	public Voice getVoiceMessage() {
		return voiceMessage;
	}

	public void setVoiceMessage(Voice voiceMessage) {
		this.voiceMessage = voiceMessage;
	}


	UserService userService;


	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}


	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public boolean isOutbound() {
		return isOutbound;
	}

	public void setOutbound(boolean isOutbound) {
		this.isOutbound = isOutbound;
	}

	public boolean isOrderAllowed() {
		return orderAllowed;
	}

	public void setOrderAllowed(boolean orderAllowed) {
		this.orderAllowed = orderAllowed;
	}

	public boolean isFeedbackAllowed() {
		return feedbackAllowed;
	}

	public void setFeedbackAllowed(boolean feedbackAllowed) {
		this.feedbackAllowed = feedbackAllowed;
	}

	public boolean isResponseAllowed() {
		return responseAllowed;
	}

	public void setResponseAllowed(boolean responseAllowed) {
		this.responseAllowed = responseAllowed;
	}
	public int getBroadcastID() {
		return broadcastID;
	}

	public void setBroadcastID(int broadcastID) {
		this.broadcastID = broadcastID;
	}

	public RecordEvent getRecordEvent() {
		return recordEvent;
	}

	public void setRecordEvent(RecordEvent recordEvent) {
		this.recordEvent = recordEvent;
	}	
}

