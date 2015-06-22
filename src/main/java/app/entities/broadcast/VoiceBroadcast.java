package app.entities.broadcast;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import app.entities.Group;
import app.entities.Organization;
import app.entities.User;
import app.entities.Voice;


@Entity
@DiscriminatorValue("voice")
public class VoiceBroadcast extends Broadcast {
	private static final long serialVersionUID = 1L;

	@Column(name="voice_broadcast_draft")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean voiceBroadcastDraft;

	//bi-directional many-to-one association to Voice
	@ManyToOne
	@JoinColumn(name="voice_id")
	private Voice voice;

	public VoiceBroadcast() {
	}

	public VoiceBroadcast(Organization organization, Group group, User user, String mode, boolean askFeedback,
			boolean askOrder, boolean askResponse, boolean appOnly, Voice voice, boolean voiceBroadcastDraft) {

		super(organization, group, user, "voice", mode, askFeedback, askOrder, askResponse, appOnly);
		this.voice = voice;
		this.voiceBroadcastDraft = voiceBroadcastDraft;
	}

	public boolean getVoiceBroadcastDraft() {
		return this.voiceBroadcastDraft;
	}

	public void setVoiceBroadcastDraft(boolean voiceBroadcastDraft) {
		this.voiceBroadcastDraft = voiceBroadcastDraft;
	}

	public Voice getVoice() {
		return this.voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

}
