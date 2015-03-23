package webapp.model.entities.broadcast;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import webapp.model.entities.Group;
import webapp.model.entities.Organization;
import webapp.model.entities.User;
import webapp.model.entities.Voice;

@Entity
@DiscriminatorValue("voice")
public class VoiceBroadcast extends Broadcast {
	private static final long serialVersionUID = 1L;

	@Column(name="voice_broadcast_draft")
	private int voiceBroadcastDraft;

	//bi-directional many-to-one association to Voice
	@ManyToOne
	@JoinColumn(name="voice_id")
	private Voice voice;

	public VoiceBroadcast() {
	}

	public VoiceBroadcast(Organization organization, Group group, User user, String mode, boolean askFeedback,
			boolean askOrder, boolean askResponse, boolean appOnly, Voice voice, boolean voiceBroadcastDraft) {

		super(organization, group, user, "text", mode, askFeedback, askOrder, askResponse, appOnly);
		this.voice = voice;
		this.voiceBroadcastDraft = voiceBroadcastDraft ? 1 : 0;
	}

	public boolean getVoiceBroadcastDraft() {
		return this.voiceBroadcastDraft != 0;
	}

	public void setVoiceBroadcastDraft(boolean voiceBroadcastDraft) {
		this.voiceBroadcastDraft = voiceBroadcastDraft ? 1 : 0;
	}

	@JsonProperty(value="voiceId")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="voiceId")
	@JsonIdentityReference(alwaysAsId=true)
	public Voice getVoice() {
		return this.voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

}
