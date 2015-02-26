package webapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;


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

	public int getBroadcastDefaultSettingsId() {
		return this.broadcastDefaultSettingsId;
	}

	public void setBroadcastDefaultSettingsId(int broadcastDefaultSettingsId) {
		this.broadcastDefaultSettingsId = broadcastDefaultSettingsId;
	}

	public int getAskFeedback() {
		return this.askFeedback;
	}

	public void setAskFeedback(int askFeedback) {
		this.askFeedback = askFeedback;
	}

	public int getAskOrder() {
		return this.askOrder;
	}

	public void setAskOrder(int askOrder) {
		this.askOrder = askOrder;
	}

	public int getAskResponse() {
		return this.askResponse;
	}

	public void setAskResponse(int askResponse) {
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