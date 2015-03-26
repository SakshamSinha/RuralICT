package webapp.model.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import webapp.model.entities.broadcast.Broadcast;


/**
 * The persistent class for the organization database table.
 * 
 */
@Entity
@Table(name="organization")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="organization_id")
	private int organizationId;

	private String abbreviation;

	private String address;

	private String contact;

	@Column(name="default_call_locale")
	private String defaultCallLocale;

	@Column(name="default_web_locale")
	private String defaultWebLocale;

	@Column(name="enable_billing")
	private int enableBilling;

	@Column(name="enable_broadcasts")
	private int enableBroadcasts;

	@Column(name="enable_feedbacks")
	private int enableFeedbacks;

	@Column(name="enable_order_cancellation")
	private int enableOrderCancellation;

	@Column(name="enable_responses")
	private int enableResponses;

	@Column(name="enable_sms")
	private int enableSms;

	@Column(name="inbound_call_ask_feedback")
	private int inboundCallAskFeedback;

	@Column(name="inbound_call_ask_order")
	private int inboundCallAskOrder;

	@Column(name="inbound_call_ask_response")
	private int inboundCallAskResponse;

	@Column(name="inbound_call_groupwise_latest_broadcast")
	private int inboundCallGroupwiseLatestBroadcast;

	@Column(name="inbound_call_play_latest_broadcast")
	private int inboundCallPlayLatestBroadcast;

	@Column(name="incoming_sms_code")
	private String incomingSmsCode;

	@Column(name="ivr_number")
	private String ivrNumber;

	private String name;

	//bi-directional many-to-one association to Broadcast
	@OneToMany(mappedBy="organization")
	private List<Broadcast> broadcasts;

	//bi-directional many-to-one association to BroadcastDefaultSetting
	@OneToMany(mappedBy="organization")
	private List<BroadcastDefaultSetting> broadcastDefaultSettings;

	//bi-directional many-to-one association to Group
	@OneToMany(mappedBy="organization")
	private List<Group> groups;

	//bi-directional many-to-one association to InboundCall
	@OneToMany(mappedBy="organization")
	private List<InboundCall> inboundCalls;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="parent_organization_id")
	private Organization parentOrganization;

	//bi-directional many-to-one association to Organization
	@OneToMany(mappedBy="parentOrganization")
	private List<Organization> subOrganizations;

	//bi-directional many-to-one association to OrganizationMembership
	@OneToMany(mappedBy="organization")
	private List<OrganizationMembership> organizationMemberships;

	//bi-directional many-to-one association to PresetQuantity
	@OneToMany(mappedBy="organization")
	private List<PresetQuantity> presetQuantities;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="organization")
	private List<Product> products;

	//bi-directional many-to-one association to ProductType
	@OneToMany(mappedBy="organization")
	private List<ProductType> productTypes;

	//bi-directional many-to-one association to WelcomeMessage
	@OneToMany(mappedBy="organization")
	private List<WelcomeMessage> welcomeMessages;

	public Organization() {
	}

	public Organization(String name, String abbreviation, String ivrNumber, String incomingSmsCode, String address,
			String contact, Organization parentOrganization, String defaultWebLocale, String defaultCallLocale) {

		this.name = name;
		this.abbreviation = abbreviation;
		this.ivrNumber = ivrNumber;
		this.incomingSmsCode = incomingSmsCode;
		this.address = address;
		this.contact = contact;
		this.parentOrganization = parentOrganization;
		this.defaultWebLocale = defaultWebLocale;
		this.defaultCallLocale = defaultCallLocale;
	}

	public int getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDefaultCallLocale() {
		return this.defaultCallLocale;
	}

	public void setDefaultCallLocale(String defaultCallLocale) {
		this.defaultCallLocale = defaultCallLocale;
	}

	public String getDefaultWebLocale() {
		return this.defaultWebLocale;
	}

	public void setDefaultWebLocale(String defaultWebLocale) {
		this.defaultWebLocale = defaultWebLocale;
	}

	public boolean getEnableBilling() {
		return this.enableBilling != 0;
	}

	public void setEnableBilling(boolean enableBilling) {
		this.enableBilling = enableBilling ? 1 : 0;
	}

	public boolean getEnableBroadcasts() {
		return this.enableBroadcasts != 0;
	}

	public void setEnableBroadcasts(boolean enableBroadcasts) {
		this.enableBroadcasts = enableBroadcasts ? 1 : 0;
	}

	public boolean getEnableFeedbacks() {
		return this.enableFeedbacks != 0;
	}

	public void setEnableFeedbacks(boolean enableFeedbacks) {
		this.enableFeedbacks = enableFeedbacks ? 1 : 0;
	}

	public boolean getEnableOrderCancellation() {
		return this.enableOrderCancellation != 0;
	}

	public void setEnableOrderCancellation(boolean enableOrderCancellation) {
		this.enableOrderCancellation = enableOrderCancellation ? 1 : 0;
	}

	public boolean getEnableResponses() {
		return this.enableResponses != 0;
	}

	public void setEnableResponses(boolean enableResponses) {
		this.enableResponses = enableResponses ? 1 : 0;
	}

	public boolean getEnableSms() {
		return this.enableSms != 0;
	}

	public void setEnableSms(boolean enableSms) {
		this.enableSms = enableSms ? 1 : 0;
	}

	public boolean getInboundCallAskFeedback() {
		return this.inboundCallAskFeedback != 0;
	}

	public void setInboundCallAskFeedback(boolean inboundCallAskFeedback) {
		this.inboundCallAskFeedback = inboundCallAskFeedback ? 1 : 0;
	}

	public boolean getInboundCallAskOrder() {
		return this.inboundCallAskOrder != 0;
	}

	public void setInboundCallAskOrder(boolean inboundCallAskOrder) {
		this.inboundCallAskOrder = inboundCallAskOrder ? 1 : 0;
	}

	public boolean getInboundCallAskResponse() {
		return this.inboundCallAskResponse != 0;
	}

	public void setInboundCallAskResponse(boolean inboundCallAskResponse) {
		this.inboundCallAskResponse = inboundCallAskResponse ? 1 : 0;
	}

	public boolean getInboundCallGroupwiseLatestBroadcast() {
		return this.inboundCallGroupwiseLatestBroadcast != 0;
	}

	public void setInboundCallGroupwiseLatestBroadcast(boolean inboundCallGroupwiseLatestBroadcast) {
		this.inboundCallGroupwiseLatestBroadcast = inboundCallGroupwiseLatestBroadcast ? 1 : 0;
	}

	public boolean getInboundCallPlayLatestBroadcast() {
		return this.inboundCallPlayLatestBroadcast != 0;
	}

	public void setInboundCallPlayLatestBroadcast(boolean inboundCallPlayLatestBroadcast) {
		this.inboundCallPlayLatestBroadcast = inboundCallPlayLatestBroadcast ? 1 : 0;
	}

	public String getIncomingSmsCode() {
		return this.incomingSmsCode;
	}

	public void setIncomingSmsCode(String incomingSmsCode) {
		this.incomingSmsCode = incomingSmsCode;
	}

	public String getIvrNumber() {
		return this.ivrNumber;
	}

	public void setIvrNumber(String ivrNumber) {
		this.ivrNumber = ivrNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Broadcast> getBroadcasts() {
		return this.broadcasts;
	}

	public void setBroadcasts(List<Broadcast> broadcasts) {
		this.broadcasts = broadcasts;
	}

	public Broadcast addBroadcast(Broadcast broadcast) {
		getBroadcasts().add(broadcast);
		broadcast.setOrganization(this);

		return broadcast;
	}

	public Broadcast removeBroadcast(Broadcast broadcast) {
		getBroadcasts().remove(broadcast);
		broadcast.setOrganization(null);

		return broadcast;
	}

	public List<BroadcastDefaultSetting> getBroadcastDefaultSettings() {
		return this.broadcastDefaultSettings;
	}

	public void setBroadcastDefaultSettings(List<BroadcastDefaultSetting> broadcastDefaultSettings) {
		this.broadcastDefaultSettings = broadcastDefaultSettings;
	}

	public BroadcastDefaultSetting addBroadcastDefaultSetting(BroadcastDefaultSetting broadcastDefaultSetting) {
		getBroadcastDefaultSettings().add(broadcastDefaultSetting);
		broadcastDefaultSetting.setOrganization(this);

		return broadcastDefaultSetting;
	}

	public BroadcastDefaultSetting removeBroadcastDefaultSetting(BroadcastDefaultSetting broadcastDefaultSetting) {
		getBroadcastDefaultSettings().remove(broadcastDefaultSetting);
		broadcastDefaultSetting.setOrganization(null);

		return broadcastDefaultSetting;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Group addGroup(Group group) {
		getGroups().add(group);
		group.setOrganization(this);

		return group;
	}

	public Group removeGroup(Group group) {
		getGroups().remove(group);
		group.setOrganization(null);

		return group;
	}

	public List<InboundCall> getInboundCalls() {
		return this.inboundCalls;
	}

	public void setInboundCalls(List<InboundCall> inboundCalls) {
		this.inboundCalls = inboundCalls;
	}

	public InboundCall addInboundCall(InboundCall inboundCall) {
		getInboundCalls().add(inboundCall);
		inboundCall.setOrganization(this);

		return inboundCall;
	}

	public InboundCall removeInboundCall(InboundCall inboundCall) {
		getInboundCalls().remove(inboundCall);
		inboundCall.setOrganization(null);

		return inboundCall;
	}

	public Organization getParentOrganization() {
		return this.parentOrganization;
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}

	public List<Organization> getSubOrganizations() {
		return this.subOrganizations;
	}

	public void setSubOrganizations(List<Organization> subOrganizations) {
		this.subOrganizations = subOrganizations;
	}

	public Organization addSubOrganization(Organization subOrganization) {
		getSubOrganizations().add(subOrganization);
		subOrganization.setParentOrganization(this);

		return subOrganization;
	}

	public Organization removeSubOrganization(Organization subOrganization) {
		getSubOrganizations().remove(subOrganization);
		subOrganization.setParentOrganization(null);

		return subOrganization;
	}

	public List<OrganizationMembership> getOrganizationMemberships() {
		return this.organizationMemberships;
	}

	public void setOrganizationMemberships(List<OrganizationMembership> organizationMemberships) {
		this.organizationMemberships = organizationMemberships;
	}

	public OrganizationMembership addOrganizationMembership(OrganizationMembership organizationMembership) {
		getOrganizationMemberships().add(organizationMembership);
		organizationMembership.setOrganization(this);

		return organizationMembership;
	}

	public OrganizationMembership removeOrganizationMembership(OrganizationMembership organizationMembership) {
		getOrganizationMemberships().remove(organizationMembership);
		organizationMembership.setOrganization(null);

		return organizationMembership;
	}

	public List<PresetQuantity> getPresetQuantities() {
		return this.presetQuantities;
	}

	public void setPresetQuantities(List<PresetQuantity> presetQuantities) {
		this.presetQuantities = presetQuantities;
	}

	public PresetQuantity addPresetQuantity(PresetQuantity presetQuantity) {
		getPresetQuantities().add(presetQuantity);
		presetQuantity.setOrganization(this);

		return presetQuantity;
	}

	public PresetQuantity removePresetQuantity(PresetQuantity presetQuantity) {
		getPresetQuantities().remove(presetQuantity);
		presetQuantity.setOrganization(null);

		return presetQuantity;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setOrganization(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setOrganization(null);

		return product;
	}

	public List<ProductType> getProductTypes() {
		return this.productTypes;
	}

	public void setProductTypes(List<ProductType> productTypes) {
		this.productTypes = productTypes;
	}

	public ProductType addProductType(ProductType productType) {
		getProductTypes().add(productType);
		productType.setOrganization(this);

		return productType;
	}

	public ProductType removeProductType(ProductType productType) {
		getProductTypes().remove(productType);
		productType.setOrganization(null);

		return productType;
	}

	public List<WelcomeMessage> getWelcomeMessages() {
		return this.welcomeMessages;
	}

	public void setWelcomeMessages(List<WelcomeMessage> welcomeMessages) {
		this.welcomeMessages = welcomeMessages;
	}

	public WelcomeMessage addWelcomeMessage(WelcomeMessage welcomeMessage) {
		getWelcomeMessages().add(welcomeMessage);
		welcomeMessage.setOrganization(this);

		return welcomeMessage;
	}

	public WelcomeMessage removeWelcomeMessage(WelcomeMessage welcomeMessage) {
		getWelcomeMessages().remove(welcomeMessage);
		welcomeMessage.setOrganization(null);

		return welcomeMessage;
	}

}
