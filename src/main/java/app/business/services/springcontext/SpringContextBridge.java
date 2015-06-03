package app.business.services.springcontext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import app.business.services.BroadcastRecipientService;
import app.business.services.BroadcastScheduleService;
import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.InboundCallService;
import app.business.services.OrderService;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.OutboundCallService;
import app.business.services.ProductService;
import app.business.services.ProductTypeService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.UserViewService;
import app.business.services.VoiceService;
import app.business.services.broadcast.TextBroadcastService;
import app.business.services.broadcast.VoiceBroadcastService;
import app.business.services.message.TextMessageService;
import app.business.services.message.VoiceMessageService;

/**
* Register this SpringContextBridge as a Spring Component.
*/

@Component
public class SpringContextBridge implements SpringContextBridgedServices, ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Autowired
	private VoiceService voiceService;
	
	@Autowired
	private UserViewService userViewService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserPhoneNumberService userPhoneNumberService;

	@Autowired
	private ProductTypeService productTypeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OutboundCallService outboundCallService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private OrganizationMembershipService organizationMembershipService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private InboundCallService inboundCallService;

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupMembershipService groupMembershipService;

	@Autowired
	private BroadcastScheduleService broadcastScheduleService;

	@Autowired
	private BroadcastRecipientService broadcastRecipientService;

	@Autowired
	private TextBroadcastService textBroadcastService;

	@Autowired
	private VoiceBroadcastService voiceBroadcastService;

	@Autowired
	private TextMessageService textMessageService;

	@Autowired
	private VoiceMessageService voiceMessageService;

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		System.out.println("abc");
		applicationContext = appContext;
	}

	/*
	 * A static method to lookup the SpringContextBridgedServices Bean in
	 * the applicationContext. It is basically an instance of itself, which
	 * was registered by the @Component annotation.
	 *
	 * @return the SpringContextBridgedServices, which exposes all the
	 * Spring services that are bridged from the Spring context.
	 */
	public static SpringContextBridgedServices services() {
		System.out.println(applicationContext);
		return applicationContext.getBean(SpringContextBridgedServices.class);
	}

	@Override
	public VoiceService getVoiceService() {
		return voiceService;
	}
	
	@Override
	public BroadcastRecipientService getBroadcastRecipientService() {
		return broadcastRecipientService;
	}
	
	@Override
	public BroadcastScheduleService getBroadcastScheduleService() {
		return broadcastScheduleService;
	}
	
	@Override
	public GroupMembershipService getGroupMembershipService() {
		return groupMembershipService;
	}
	
	@Override
	public GroupService getGroupService() {
		return groupService;
	}
	
	@Override
	public InboundCallService getInboundCallService() {
		return inboundCallService;
	}
	
	@Override
	public OrderService getOrderService() {
		return orderService;
	}
	
	@Override
	public OrganizationMembershipService getOrganizationMembershipService() {
		return organizationMembershipService;
	}
	
	@Override
	public OutboundCallService getOutboundCallService() {
		return outboundCallService;
	}
	
	@Override
	public OrganizationService getOrganizationService() {
		return organizationService;
	}
	
	@Override
	public ProductService getProductService() {
		return productService;
	}
	
	@Override
	public ProductTypeService getProductTypeService() {
		return productTypeService;
	}
	
	@Override
	public UserPhoneNumberService getUserPhoneNumberService() {
		return userPhoneNumberService;
	}
	
	@Override
	public UserService getUserService() {
		return userService;
	}
	
	@Override
	public UserViewService getUserViewService() {
		return userViewService;
	}
	
	@Override
	public TextBroadcastService getTextBroadcastService() {
		return textBroadcastService;
	}
	
	@Override
	public VoiceBroadcastService getVoiceBroadcastService() {
		return voiceBroadcastService;
	}
	
	@Override
	public TextMessageService getTextMessageService() {
		return textMessageService;
	}
	
	@Override
	public VoiceMessageService getVoiceMessageService() {
		return voiceMessageService;
	}
}