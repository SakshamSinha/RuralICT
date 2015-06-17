package app.entities.message;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonTypeName;

=======
import app.entities.Group;
>>>>>>> f5475c82924bfb9e2e4cb69e5c519ca13a0b054a
import app.entities.InboundCall;
import app.entities.Order;
import app.entities.User;
import app.entities.Voice;
import app.entities.broadcast.Broadcast;


@Entity
@DiscriminatorValue("voice")
@JsonTypeName("voice")
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

	public VoiceMessage(User user, Broadcast broadcast, Group group, String mode, String type, boolean response, Order order,
			Voice voice, InboundCall inboundCall) {

		super(user, broadcast, inboundCall.getTime(), group, mode, "voice", type, response, order);
		this.voice = voice;
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
