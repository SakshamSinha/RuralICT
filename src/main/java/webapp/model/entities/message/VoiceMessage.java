package webapp.model.entities.message;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import webapp.model.entities.InboundCall;
import webapp.model.entities.Order;
import webapp.model.entities.User;
import webapp.model.entities.Voice;
import webapp.model.entities.broadcast.Broadcast;

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

	public VoiceMessage(User user, Broadcast broadcast, String mode, String type, boolean response, Order order,
			Voice voice, InboundCall inboundCall) {

		super(user, broadcast, mode, "voice", type, response, order);
		this.voice = voice;
		this.inboundCall = inboundCall;
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

	@JsonProperty(value="inboundCallId")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="inboundCallId")
	@JsonIdentityReference(alwaysAsId=true)
	public InboundCall getInboundCall() {
		return this.inboundCall;
	}

	public void setInboundCall(InboundCall inboundCall) {
		this.inboundCall = inboundCall;
	}

}
