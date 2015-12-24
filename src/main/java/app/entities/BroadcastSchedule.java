package app.entities;

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

import org.hibernate.annotations.Type;

import app.entities.broadcast.Broadcast;


/**
 * The persistent class for the broadcast_schedule database table.
 * 
 */
@Entity
@Table(name="broadcast_schedule")
public class BroadcastSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="broadcast_schedule_id")
	private int broadcastScheduleId;

	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean cancelled;

	@Column(name="send_to_all")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean sendToAll;

	private Timestamp time;

	//bi-directional many-to-one association to Broadcast
	@ManyToOne
	@JoinColumn(name="broadcast_id")
	private Broadcast broadcast;

	//bi-directional many-to-one association to OutboundCall
	@OneToMany(mappedBy="broadcastSchedule")
	private List<OutboundCall> outboundCalls;

	public BroadcastSchedule() {
	}

	public BroadcastSchedule(Broadcast broadcast, Timestamp time, boolean sendToAll) {
		this.broadcast = broadcast;
		this.time = time;
		this.sendToAll = sendToAll;
	}

	public int getBroadcastScheduleId() {
		return this.broadcastScheduleId;
	}

	public void setBroadcastScheduleId(int broadcastScheduleId) {
		this.broadcastScheduleId = broadcastScheduleId;
	}

	public boolean getCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean getSendToAll() {
		return this.sendToAll;
	}

	public void setSendToAll(boolean sendToAll) {
		this.sendToAll = sendToAll;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Broadcast getBroadcast() {
		return this.broadcast;
	}

	public void setBroadcast(Broadcast broadcast) {
		this.broadcast = broadcast;
	}

	public List<OutboundCall> getOutboundCalls() {
		return this.outboundCalls;
	}

	public void setOutboundCalls(List<OutboundCall> outboundCalls) {
		this.outboundCalls = outboundCalls;
	}

	public OutboundCall addOutboundCall(OutboundCall outboundCall) {
		getOutboundCalls().add(outboundCall);
		outboundCall.setBroadcastSchedule(this);

		return outboundCall;
	}

	public OutboundCall removeOutboundCall(OutboundCall outboundCall) {
		getOutboundCalls().remove(outboundCall);
		outboundCall.setBroadcastSchedule(null);

		return outboundCall;
	}

}
