package app.telephony;

import java.sql.Timestamp;










import org.springframework.beans.factory.annotation.Autowired;

import in.ac.iitb.ivrs.telephony.base.IVRSession;

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
	//Message recordedMessage;
	Voice voiceMessage ;
	static boolean isOutbound=false;
	
	public boolean isOutbound() {
		return isOutbound;
	}

	public void setOutbound(boolean isOutbound) {
		this.isOutbound = isOutbound;
	}

	
	OrganizationService organizationService = SpringContextBridge.services().getOrganizationService();
	

	/**
	 * @param groupService 
	 * @throws InstantiationException 
	 * @see {@link IVRSession#IVRSession(String, String, String, String, String, Class)}
	 */
	public RuralictSession(String sessionId, String userNumber, String ivrNumber, String circle, String operator)
			throws FiniteStateException, InstantiationException {

		super(sessionId, userNumber, ivrNumber, circle, operator, RuralictStateMachine.class);

		/*EntityManager em = EntityManagerService.getEntityManager();
		User user = em.find(User.class, userNumber);
		if (user == null) {
			user = new User();
			user.setCircle(circle);
			user.setOperator(operator);
			user.setPhoneNo(userNumber);
			em.persist(user);
		}*/
		
	

		
		call = new InboundCall();
		call.setTime(new Timestamp(getStartTime().getTime()));
		call.setFromNumber(userNumber);
		call.setOrganization(organizationService.getOrganizationByIVRS(ivrNumber));

		//call.setUser(user);
		//em.persist(call);
	}

	/**
	 * @see IVRSession#finish(long)
	 */
	@Override
	public void finish(long totalCallDuration) {
		super.finish(totalCallDuration);
		//EntityManager em = EntityManagerService.getEntityManager();

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
}

