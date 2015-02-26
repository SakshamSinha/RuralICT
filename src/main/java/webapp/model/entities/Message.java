package webapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@Table(name="message")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="message_id")
	private int messageId;

	private String comments;

	private String format;

	private String mode;

	private int response;

	@Column(name="text_content")
	private String textContent;

	@Column(name="text_time")
	private Timestamp textTime;

	private String type;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="from_user_id")
	private User user;

	//bi-directional many-to-one association to Broadcast
	@ManyToOne
	@JoinColumn(name="source_broadcast_id")
	private Broadcast broadcast;

	//uni-directional many-to-one association to Voice
	@ManyToOne
	@JoinColumn(name="voice_id")
	private Voice voice;

	//bi-directional many-to-one association to InboundCall
	@ManyToOne
	@JoinColumn(name="voice_inbound_call_id")
	private InboundCall inboundCall;

	//uni-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

	public Message() {
	}

	public int getMessageId() {
		return this.messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMode() {
		return this.mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getResponse() {
		return this.response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public String getTextContent() {
		return this.textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public Timestamp getTextTime() {
		return this.textTime;
	}

	public void setTextTime(Timestamp textTime) {
		this.textTime = textTime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Broadcast getBroadcast() {
		return this.broadcast;
	}

	public void setBroadcast(Broadcast broadcast) {
		this.broadcast = broadcast;
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

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}