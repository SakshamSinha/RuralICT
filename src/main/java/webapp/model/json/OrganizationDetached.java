package webapp.model.json;

import java.io.Serializable;

import webapp.model.entities.Organization;

public class OrganizationDetached implements Serializable {
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

	public OrganizationDetached() {
	}

	public OrganizationDetached(Organization org) {
		setOrganizationId(org.getOrganizationId());
		setAbbreviation(org.getAbbreviation());
		// TODO etc. write a script to generate these..
	}

	public void updateOrganization(Organization org) {
		if (organizationId != null) org.setOrganizationId(organizationId);
		if (abbreviation != null) org.setAbbreviation(abbreviation);
		// TODO etc.
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
