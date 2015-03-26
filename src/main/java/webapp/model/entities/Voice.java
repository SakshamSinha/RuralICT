package webapp.model.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import webapp.model.entities.broadcast.VoiceBroadcast;


/**
 * The persistent class for the voice database table.
 * 
 */
@Entity
@Table(name="voice")
public class Voice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="voice_id")
	private int voiceId;

	@Column(name="is_downloaded")
	private int isDownloaded;

	private String url;

	//bi-directional many-to-one association to Broadcast
	@OneToMany(mappedBy="voice")
	private List<VoiceBroadcast> broadcasts;

	//bi-directional many-to-one association to WelcomeMessage
	@OneToMany(mappedBy="voice")
	private List<WelcomeMessage> welcomeMessages;

	public Voice() {
	}

	public Voice(String url, int isDownloaded) {
		this.url = url;
		this.isDownloaded = isDownloaded;
	}

	public int getVoiceId() {
		return this.voiceId;
	}

	public void setVoiceId(int voiceId) {
		this.voiceId = voiceId;
	}

	public boolean getIsDownloaded() {
		return this.isDownloaded != 0;
	}

	public void setIsDownloaded(boolean isDownloaded) {
		this.isDownloaded = isDownloaded ? 1 : 0;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<VoiceBroadcast> getBroadcasts() {
		return this.broadcasts;
	}

	public void setBroadcasts(List<VoiceBroadcast> broadcasts) {
		this.broadcasts = broadcasts;
	}

	public VoiceBroadcast addBroadcast(VoiceBroadcast broadcast) {
		getBroadcasts().add(broadcast);
		broadcast.setVoice(this);

		return broadcast;
	}

	public VoiceBroadcast removeBroadcast(VoiceBroadcast broadcast) {
		getBroadcasts().remove(broadcast);
		broadcast.setVoice(null);

		return broadcast;
	}

	public List<WelcomeMessage> getWelcomeMessages() {
		return this.welcomeMessages;
	}

	public void setWelcomeMessages(List<WelcomeMessage> welcomeMessages) {
		this.welcomeMessages = welcomeMessages;
	}

	public WelcomeMessage addWelcomeMessage(WelcomeMessage welcomeMessage) {
		getWelcomeMessages().add(welcomeMessage);
		welcomeMessage.setVoice(this);

		return welcomeMessage;
	}

	public WelcomeMessage removeWelcomeMessage(WelcomeMessage welcomeMessage) {
		getWelcomeMessages().remove(welcomeMessage);
		welcomeMessage.setVoice(null);

		return welcomeMessage;
	}

}
