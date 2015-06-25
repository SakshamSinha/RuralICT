package app.business.services.springcontext;

import app.business.services.BroadcastRecipientService;
import app.business.services.BroadcastScheduleService;
import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.InboundCallService;
import app.business.services.LatestRecordedVoiceService;
import app.business.services.OrderService;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.OutboundCallService;
import app.business.services.ProductService;
import app.business.services.ProductTypeService;
import app.business.services.TelephonyService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.UserViewService;
import app.business.services.VoiceService;
import app.business.services.broadcast.TextBroadcastService;
import app.business.services.broadcast.VoiceBroadcastService;
import app.business.services.message.TextMessageService;
import app.business.services.message.VoiceMessageService;

/*
 * This interface represents a list of Spring Beans (services) which need to be referenced from a non Spring class.
 */
public interface SpringContextBridgedServices {
		
	public VoiceService getVoiceService();
	public BroadcastRecipientService getBroadcastRecipientService();
	public BroadcastScheduleService getBroadcastScheduleService();
	public GroupMembershipService getGroupMembershipService();
	public GroupService getGroupService();
	public InboundCallService getInboundCallService();
	public OrderService getOrderService();
	public OrganizationMembershipService getOrganizationMembershipService();
	public OutboundCallService getOutboundCallService();
	public OrganizationService getOrganizationService();
	public ProductService getProductService();
	public ProductTypeService getProductTypeService();
	public UserPhoneNumberService getUserPhoneNumberService();
	public UserService getUserService();
	public UserViewService getUserViewService();
	public TextBroadcastService getTextBroadcastService();
	public VoiceBroadcastService getVoiceBroadcastService();
	public TextMessageService getTextMessageService();
	public VoiceMessageService getVoiceMessageService();
	public TelephonyService getTelephonyService();
	public LatestRecordedVoiceService getLatestBroadcastableVoiceService();
}