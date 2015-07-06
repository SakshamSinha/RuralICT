package app.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the broadcast_default_settings database table.
 * 
 */
@Entity
@Table(name="broadcast_default_settings")
public class BroadcastDefaultSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="broadcast_default_settings_id")
	private int broadcastDefaultSettingsId;

	@Column(name="ask_feedback")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean askFeedback;

	@Column(name="ask_order")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean askOrder;

	@Column(name="ask_response")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean askResponse;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	public BroadcastDefaultSettings() {
	}

	public BroadcastDefaultSettings(Organization organization, Group group, boolean askFeedback, boolean askOrder, boolean askResponse) {
		this.organization = organization;
		this.group = group;
		this.askFeedback = askFeedback;
		this.askOrder = askOrder;
		this.askResponse = askResponse;
	}

	public int getBroadcastDefaultSettingsId() {
		return this.broadcastDefaultSettingsId;
	}

	public void setBroadcastDefaultSettingsId(int broadcastDefaultSettingsId) {
		this.broadcastDefaultSettingsId = broadcastDefaultSettingsId;
	}

	public boolean getAskFeedback() {
		return this.askFeedback;
	}

	public void setAskFeedback(boolean askFeedback) {
		this.askFeedback = askFeedback;
	}

	public boolean getAskOrder() {
		return this.askOrder;
	}

	public void setAskOrder(boolean askOrder) {
		this.askOrder = askOrder;
	}

	public boolean getAskResponse() {
		return this.askResponse;
	}

	public void setAskResponse(boolean askResponse) {
		this.askResponse = askResponse;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
