package app.telephony;

import java.sql.Timestamp;
import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.RecordEvent;
import com.continuent.tungsten.commons.patterns.fsm.*;
import app.business.services.OrganizationService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.InboundCall;
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
	boolean isPublisher;
	int broadcastID;
	RecordEvent recordEvent;


	/**
	 * @param groupService 
	 * @throws InstantiationException 
	 * @see {@link IVRSession#IVRSession(String, String, String, String, String, Class)}
	 */
	
	public RuralictSession(String sessionId, String userNumber, String ivrNumber, String circle, String operator)
			throws FiniteStateException, InstantiationException {

		super(sessionId, userNumber, ivrNumber, circle, operator, RuralictStateMachine.class);
		OrganizationService organizationService = SpringContextBridge.services().getOrganizationService();

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
	
	public boolean isPublisher() {
		return isPublisher;
	}

	public void setPublisher(boolean isPublisher) {
		this.isPublisher = isPublisher;
	}
}

