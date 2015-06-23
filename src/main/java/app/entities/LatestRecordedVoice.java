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
@Table(name="latest_recorded_voice")
public class LatestRecordedVoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="latest_recorded_voice_id")
	private int latestRecordedVoiceId;

	//bi-directional many-to-one association to Organization
	//NOTE: Although here for one organization we have one to many mapping to latest recorded voice.
	//But actually we update our table to have only the latest recorded voice.
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	private Timestamp recordedTime;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="voice_id")
	private Voice voice;

	public LatestRecordedVoice() {
	}

	public LatestRecordedVoice(Organization organization, Timestamp recordedTime, Voice voice) {
		this.organization = organization;
		this.recordedTime = recordedTime;
		this.voice = voice;
	}

	public int getLatestRecordedVoiceId() {
		return latestRecordedVoiceId;
	}

	public void setLatestRecordedVoiceId(int latestRecordedVoiceId) {
		this.latestRecordedVoiceId = latestRecordedVoiceId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Timestamp getTime() {
		return recordedTime;
	}

	public void setTime(Timestamp recordedTime) {
		this.recordedTime = recordedTime;
	}

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

}
