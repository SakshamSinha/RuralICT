package webapp.model.json;

import webapp.model.entities.Organization;
import webapp.model.repositories.OrganizationRepository;

public class OrganizationJson implements EntityJson<Organization, OrganizationRepository> {
	private static final long serialVersionUID = 1L;

	private Integer organizationId;
	private String abbreviation;
	private String address;
	private String contact;
	private String defaultCallLocale;
	private String defaultWebLocale;
	private Boolean enableBilling;
	private Boolean enableBroadcasts;
	private Boolean enableFeedbacks;
	private Boolean enableOrderCancellation;
	private Boolean enableResponses;
	private Boolean enableSms;
	private Boolean inboundCallAskFeedback;
	private Boolean inboundCallAskOrder;
	private Boolean inboundCallAskResponse;
	private Boolean inboundCallGroupwiseLatestBroadcast;
	private Boolean inboundCallPlayLatestBroadcast;
	private String incomingSmsCode;
	private String ivrNumber;
	private String name;
	private Integer parentOrganizationId;

	public OrganizationJson() {
	}

	/**
	 * Construct a JSON object from an entity
	 */
	public OrganizationJson(Organization org) {
		setOrganizationId(org.getOrganizationId());
		setAbbreviation(org.getAbbreviation());
		setAddress(org.getAddress());
		setContact(org.getContact());
		setDefaultCallLocale(org.getDefaultCallLocale());
		setDefaultWebLocale(org.getDefaultWebLocale());
		setEnableBilling(org.getEnableBilling());
		setEnableBroadcasts(org.getEnableBroadcasts());
		setEnableFeedbacks(org.getEnableFeedbacks());
		setEnableOrderCancellation(org.getEnableOrderCancellation());
		setEnableResponses(org.getEnableResponses());
		setEnableSms(org.getEnableSms());
		setInboundCallAskFeedback(org.getInboundCallAskFeedback());
		setInboundCallAskOrder(org.getInboundCallAskOrder());
		setInboundCallAskResponse(org.getInboundCallAskResponse());
		setInboundCallGroupwiseLatestBroadcast(org.getInboundCallGroupwiseLatestBroadcast());
		setInboundCallPlayLatestBroadcast(org.getInboundCallPlayLatestBroadcast());
		setIncomingSmsCode(org.getIncomingSmsCode());
		setIvrNumber(org.getIvrNumber());
		setName(org.getName());
		setParentOrganizationId(org.getParentOrganization().getOrganizationId());
	}

	/**
	 * Update an entity from this JSON object
	 */
	@Override
	public void updateEntity(Organization org, OrganizationRepository repo) {
		if (organizationId != null) org.setOrganizationId(organizationId);
		if (abbreviation != null) org.setAbbreviation(abbreviation);
		if (address != null) org.setAddress(address);
		if (contact != null) org.setContact(contact);
		if (defaultCallLocale != null) org.setDefaultCallLocale(defaultCallLocale);
		if (defaultWebLocale != null) org.setDefaultWebLocale(defaultWebLocale);
		if (enableBilling != null) org.setEnableBilling(enableBilling);
		if (enableBroadcasts != null) org.setEnableBroadcasts(enableBroadcasts);
		if (enableFeedbacks != null) org.setEnableFeedbacks(enableFeedbacks);
		if (enableOrderCancellation != null) org.setEnableOrderCancellation(enableOrderCancellation);
		if (enableResponses != null) org.setEnableResponses(enableResponses);
		if (enableSms != null) org.setEnableSms(enableSms);
		if (inboundCallAskFeedback != null) org.setInboundCallAskFeedback(inboundCallAskFeedback);
		if (inboundCallAskOrder != null) org.setInboundCallAskOrder(inboundCallAskOrder);
		if (inboundCallAskResponse != null) org.setInboundCallAskResponse(inboundCallAskResponse);
		if (inboundCallGroupwiseLatestBroadcast != null) org.setInboundCallGroupwiseLatestBroadcast(inboundCallGroupwiseLatestBroadcast);
		if (inboundCallPlayLatestBroadcast != null) org.setInboundCallPlayLatestBroadcast(inboundCallPlayLatestBroadcast);
		if (incomingSmsCode != null) org.setIncomingSmsCode(incomingSmsCode);
		if (ivrNumber != null) org.setIvrNumber(ivrNumber);
		if (name != null) org.setName(name);
		if (parentOrganizationId != null) org.setParentOrganization(repo.findOne(parentOrganizationId));
	}

	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getDefaultCallLocale() {
		return defaultCallLocale;
	}
	public void setDefaultCallLocale(String defaultCallLocale) {
		this.defaultCallLocale = defaultCallLocale;
	}
	public String getDefaultWebLocale() {
		return defaultWebLocale;
	}
	public void setDefaultWebLocale(String defaultWebLocale) {
		this.defaultWebLocale = defaultWebLocale;
	}
	public Boolean getEnableBilling() {
		return enableBilling;
	}
	public void setEnableBilling(Boolean enableBilling) {
		this.enableBilling = enableBilling;
	}
	public Boolean getEnableBroadcasts() {
		return enableBroadcasts;
	}
	public void setEnableBroadcasts(Boolean enableBroadcasts) {
		this.enableBroadcasts = enableBroadcasts;
	}
	public Boolean getEnableFeedbacks() {
		return enableFeedbacks;
	}
	public void setEnableFeedbacks(Boolean enableFeedbacks) {
		this.enableFeedbacks = enableFeedbacks;
	}
	public Boolean getEnableOrderCancellation() {
		return enableOrderCancellation;
	}
	public void setEnableOrderCancellation(Boolean enableOrderCancellation) {
		this.enableOrderCancellation = enableOrderCancellation;
	}
	public Boolean getEnableResponses() {
		return enableResponses;
	}
	public void setEnableResponses(Boolean enableResponses) {
		this.enableResponses = enableResponses;
	}
	public Boolean getEnableSms() {
		return enableSms;
	}
	public void setEnableSms(Boolean enableSms) {
		this.enableSms = enableSms;
	}
	public Boolean getInboundCallAskFeedback() {
		return inboundCallAskFeedback;
	}
	public void setInboundCallAskFeedback(Boolean inboundCallAskFeedback) {
		this.inboundCallAskFeedback = inboundCallAskFeedback;
	}
	public Boolean getInboundCallAskOrder() {
		return inboundCallAskOrder;
	}
	public void setInboundCallAskOrder(Boolean inboundCallAskOrder) {
		this.inboundCallAskOrder = inboundCallAskOrder;
	}
	public Boolean getInboundCallAskResponse() {
		return inboundCallAskResponse;
	}
	public void setInboundCallAskResponse(Boolean inboundCallAskResponse) {
		this.inboundCallAskResponse = inboundCallAskResponse;
	}
	public Boolean getInboundCallGroupwiseLatestBroadcast() {
		return inboundCallGroupwiseLatestBroadcast;
	}
	public void setInboundCallGroupwiseLatestBroadcast(
			Boolean inboundCallGroupwiseLatestBroadcast) {
		this.inboundCallGroupwiseLatestBroadcast = inboundCallGroupwiseLatestBroadcast;
	}
	public Boolean getInboundCallPlayLatestBroadcast() {
		return inboundCallPlayLatestBroadcast;
	}
	public void setInboundCallPlayLatestBroadcast(
			Boolean inboundCallPlayLatestBroadcast) {
		this.inboundCallPlayLatestBroadcast = inboundCallPlayLatestBroadcast;
	}
	public String getIncomingSmsCode() {
		return incomingSmsCode;
	}
	public void setIncomingSmsCode(String incomingSmsCode) {
		this.incomingSmsCode = incomingSmsCode;
	}
	public String getIvrNumber() {
		return ivrNumber;
	}
	public void setIvrNumber(String ivrNumber) {
		this.ivrNumber = ivrNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentOrganizationId() {
		return parentOrganizationId;
	}
	public void setParentOrganizationId(Integer parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

}
