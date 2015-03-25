package webapp.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import webapp.model.entities.broadcast.Broadcast;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the broadcast_schedule database table.
 * 
 */
@Entity
@Table(name="broadcast_schedule")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="broadcastScheduleId")
public class BroadcastSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="broadcast_schedule_id")
	private int broadcastScheduleId;

	private int cancelled;

	@Column(name="send_to_all")
	private int sendToAll;

	private Timestamp time;

	//bi-directional many-to-one association to Broadcast
	@ManyToOne
	@JoinColumn(name="broadcast_id")
	private Broadcast broadcast;

	//bi-directional many-to-one association to OutboundCall
	@OneToMany(mappedBy="broadcastSchedule")
	@JsonIgnore
	private List<OutboundCall> outboundCalls;

	public BroadcastSchedule() {
	}

	public BroadcastSchedule(Broadcast broadcast, Timestamp time, boolean sendToAll) {
		this.broadcast = broadcast;
		this.time = time;
		this.sendToAll = sendToAll ? 1 : 0;
	}

	public int getBroadcastScheduleId() {
		return this.broadcastScheduleId;
	}

	public void setBroadcastScheduleId(int broadcastScheduleId) {
		this.broadcastScheduleId = broadcastScheduleId;
	}

	public boolean getCancelled() {
		return this.cancelled != 0;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled ? 1 : 0;
	}

	public boolean getSendToAll() {
		return this.sendToAll != 0;
	}

	public void setSendToAll(boolean sendToAll) {
		this.sendToAll = sendToAll ? 1 : 0;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@JsonProperty(value="broadcastId")
	@JsonIdentityReference(alwaysAsId=true)
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
