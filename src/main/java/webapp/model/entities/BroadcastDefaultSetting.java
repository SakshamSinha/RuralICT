package webapp.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the broadcast_default_settings database table.
 * 
 */
@Entity
@Table(name="broadcast_default_settings")
public class BroadcastDefaultSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="broadcast_default_settings_id")
	private int broadcastDefaultSettingsId;

	@Column(name="ask_feedback")
	private int askFeedback;

	@Column(name="ask_order")
	private int askOrder;

	@Column(name="ask_response")
	private int askResponse;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	public BroadcastDefaultSetting() {
	}

	public BroadcastDefaultSetting(Organization organization, Group group, boolean askFeedback, boolean askOrder, boolean askResponse) {
		this.organization = organization;
		this.group = group;
		this.askFeedback = askFeedback ? 1 : 0;
		this.askOrder = askOrder ? 1 : 0;
		this.askResponse = askResponse ? 1 : 0;
	}

	public int getBroadcastDefaultSettingsId() {
		return this.broadcastDefaultSettingsId;
	}

	public void setBroadcastDefaultSettingsId(int broadcastDefaultSettingsId) {
		this.broadcastDefaultSettingsId = broadcastDefaultSettingsId;
	}

	public boolean getAskFeedback() {
		return this.askFeedback != 0;
	}

	public void setAskFeedback(boolean askFeedback) {
		this.askFeedback = askFeedback ? 1 : 0;
	}

	public boolean getAskOrder() {
		return this.askOrder != 0;
	}

	public void setAskOrder(boolean askOrder) {
		this.askOrder = askOrder ? 1 : 0;
	}

	public boolean getAskResponse() {
		return this.askResponse != 0;
	}

	public void setAskResponse(boolean askResponse) {
		this.askResponse = askResponse ? 1 : 0;
	}

	@JsonProperty(value="organizationId")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="organizationId")
	@JsonIdentityReference(alwaysAsId=true)
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@JsonProperty(value="groupId")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="groupId")
	@JsonIdentityReference(alwaysAsId=true)
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}