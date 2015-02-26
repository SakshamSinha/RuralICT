package webapp.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import webapp.model.entities.broadcast.Broadcast;

import java.util.List;


/**
 * The persistent class for the broadcast_recipient database table.
 * 
 */
@Entity
@Table(name="broadcast_recipient")
public class BroadcastRecipient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="broadcast_recipient_id")
	private int broadcastRecipientId;

	//bi-directional many-to-one association to Broadcast
	@ManyToOne
	@JoinColumn(name="broadcast_id")
	private Broadcast broadcast;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-one association to OutboundCall
	@OneToMany(mappedBy="broadcastRecipient")
	private List<OutboundCall> outboundCalls;

	public BroadcastRecipient() {
	}

	public BroadcastRecipient(Broadcast broadcast, User user) {
		this.broadcast = broadcast;
		this.user = user;
	}

	public int getBroadcastRecipientId() {
		return this.broadcastRecipientId;
	}

	public void setBroadcastRecipientId(int broadcastRecipientId) {
		this.broadcastRecipientId = broadcastRecipientId;
	}

	public Broadcast getBroadcast() {
		return this.broadcast;
	}

	public void setBroadcast(Broadcast broadcast) {
		this.broadcast = broadcast;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OutboundCall> getOutboundCalls() {
		return this.outboundCalls;
	}

	public void setOutboundCalls(List<OutboundCall> outboundCalls) {
		this.outboundCalls = outboundCalls;
	}

	public OutboundCall addOutboundCall(OutboundCall outboundCall) {
		getOutboundCalls().add(outboundCall);
		outboundCall.setBroadcastRecipient(this);

		return outboundCall;
	}

	public OutboundCall removeOutboundCall(OutboundCall outboundCall) {
		getOutboundCalls().remove(outboundCall);
		outboundCall.setBroadcastRecipient(null);

		return outboundCall;
	}

}