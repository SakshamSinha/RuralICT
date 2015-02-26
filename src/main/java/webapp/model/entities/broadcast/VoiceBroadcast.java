package webapp.model.entities.broadcast;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	public int getVoiceBroadcastDraft() {
		return this.voiceBroadcastDraft;
	}

	public void setVoiceBroadcastDraft(int voiceBroadcastDraft) {
		this.voiceBroadcastDraft = voiceBroadcastDraft;
	}

	public Voice getVoice() {
		return this.voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

}
