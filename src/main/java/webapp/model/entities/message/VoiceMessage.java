package webapp.model.entities.message;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import webapp.model.entities.InboundCall;
import webapp.model.entities.Voice;

@Entity
@DiscriminatorValue("voice")
public class VoiceMessage extends Message {
	private static final long serialVersionUID = 1L;

	//uni-directional many-to-one association to Voice
	@ManyToOne
	@JoinColumn(name="voice_id")
	private Voice voice;

	//bi-directional many-to-one association to InboundCall
	@ManyToOne
	@JoinColumn(name="voice_inbound_call_id")
	private InboundCall inboundCall;

	public VoiceMessage() {
	}

	public Voice getVoice() {
		return this.voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public InboundCall getInboundCall() {
		return this.inboundCall;
	}

	public void setInboundCall(InboundCall inboundCall) {
		this.inboundCall = inboundCall;
	}

}
