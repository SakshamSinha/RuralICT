package webapp.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import webapp.entities.message.VoiceMessage;


/**
 * The persistent class for the inbound_call database table.
 * 
 */
@Entity
@Table(name="inbound_call")
public class InboundCall implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="inbound_call_id")
	private int inboundCallId;

	private int duration;

	@Column(name="from_number")
	private String fromNumber;

	private Timestamp time;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="inboundCall")
	private List<VoiceMessage> messages;

	public InboundCall() {
	}

	public InboundCall(Organization organization, String fromNumber, Timestamp time, int duration) {
		this.organization = organization;
		this.fromNumber = fromNumber;
		this.time = time;
		this.duration = duration;
	}

	public int getInboundCallId() {
		return this.inboundCallId;
	}

	public void setInboundCallId(int inboundCallId) {
		this.inboundCallId = inboundCallId;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getFromNumber() {
		return this.fromNumber;
	}

	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<VoiceMessage> getMessages() {
		return this.messages;
	}

	public void setMessages(List<VoiceMessage> messages) {
		this.messages = messages;
	}

	public VoiceMessage addMessage(VoiceMessage message) {
		getMessages().add(message);
		message.setInboundCall(this);

		return message;
	}

	public VoiceMessage removeMessage(VoiceMessage message) {
		getMessages().remove(message);
		message.setInboundCall(null);

		return message;
	}

}
