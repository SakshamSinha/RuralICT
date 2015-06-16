package app.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the latest_broadcastable_voice database table.
 * 
 */
@Entity
@Table(name="latest_broadcastable_voice")
public class LatestBroadcastableVoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="latest_broadcastable_voice_id")
	private int latestBroadcastableVoiceId;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	private Timestamp time;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="voice_id")
	private Voice voice;

	public LatestBroadcastableVoice() {
	}

	public LatestBroadcastableVoice(Organization organization, Group group, Timestamp time, Voice voice) {
		this.organization = organization;
		this.group = group;
		this.time = time;
		this.voice = voice;
	}

	public int getLatestBroadcastableVoiceId() {
		return latestBroadcastableVoiceId;
	}

	public void setLatestBroadcastableVoiceId(int latestBroadcastableVoiceId) {
		this.latestBroadcastableVoiceId = latestBroadcastableVoiceId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

}
