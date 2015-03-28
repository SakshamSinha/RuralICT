package webapp.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the outbound_call database table.
 * 
 */
@Entity
@Table(name="outbound_call")
public class OutboundCall implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="outbound_call_id")
	private int outboundCallId;

	private int duration;

	private String status;

	@Column(name="status_detail")
	private String statusDetail;

	//bi-directional many-to-one association to BroadcastSchedule
	@ManyToOne
	@JoinColumn(name="broadcast_schedule_id")
	private BroadcastSchedule broadcastSchedule;

	//bi-directional many-to-one association to BroadcastRecipient
	@ManyToOne
	@JoinColumn(name="broadcast_recipient_id")
	private BroadcastRecipient broadcastRecipient;

	public OutboundCall() {
	}

	public OutboundCall(BroadcastRecipient broadcastRecipient, BroadcastSchedule broadcastSchedule, String status,
			String statusDetail, int duration) {

		this.broadcastRecipient = broadcastRecipient;
		this.broadcastSchedule = broadcastSchedule;
		this.status = status;
		this.statusDetail = statusDetail;
		this.duration = duration;
	}

	public int getOutboundCallId() {
		return this.outboundCallId;
	}

	public void setOutboundCallId(int outboundCallId) {
		this.outboundCallId = outboundCallId;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDetail() {
		return this.statusDetail;
	}

	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}

	public BroadcastSchedule getBroadcastSchedule() {
		return this.broadcastSchedule;
	}

	public void setBroadcastSchedule(BroadcastSchedule broadcastSchedule) {
		this.broadcastSchedule = broadcastSchedule;
	}

	public BroadcastRecipient getBroadcastRecipient() {
		return this.broadcastRecipient;
	}

	public void setBroadcastRecipient(BroadcastRecipient broadcastRecipient) {
		this.broadcastRecipient = broadcastRecipient;
	}

}
