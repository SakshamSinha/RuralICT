package app.entities.message;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import app.entities.Group;
import app.entities.Order;
import app.entities.User;
import app.entities.broadcast.Broadcast;


/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@Table(name="message")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="format", discriminatorType=DiscriminatorType.STRING)
public abstract class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="message_id")
	private int messageId;

	private Timestamp time;

	private String comments;

	@Column(insertable=false, updatable=false)
	private String format;

	private String mode;

	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean response;

	private String type;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="from_user_id")
	private User user;

	//bi-directional many-to-one association to Broadcast
	@ManyToOne
	@JoinColumn(name="source_broadcast_id")
	private Broadcast broadcast;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	//uni-directional many-to-one association to Order
	@OneToOne
	@JoinColumn(name="order_id")
	private Order order;

	public Message() {
	}

	public Message(User user, Broadcast broadcast, Timestamp time, Group group, String mode, String format, String type,
			boolean response, Order order) {

		this.user = user;
		this.broadcast = broadcast;
		this.time = time;
		this.mode = mode;
		this.format = format;
		this.type = type;
		this.response = response;
		this.order = order;
		this.group = group;
	}

	public int getMessageId() {
		return this.messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
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

	public boolean getResponse() {
		return this.response;
	}

	public void setResponse(boolean response) {
		this.response = response;
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

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
