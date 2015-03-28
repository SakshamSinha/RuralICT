package webapp.entities.broadcast;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import webapp.entities.BroadcastRecipient;
import webapp.entities.BroadcastSchedule;
import webapp.entities.Group;
import webapp.entities.Organization;
import webapp.entities.User;
import webapp.entities.message.Message;


/**
 * The persistent class for the broadcast database table.
 * 
 */
@Entity
@Table(name="broadcast")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="format", discriminatorType=DiscriminatorType.STRING)
public abstract class Broadcast implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="broadcast_id")
	private int broadcastId;

	@Column(name="app_only")
	private int appOnly;

	@Column(name="ask_feedback")
	private int askFeedback;

	@Column(name="ask_order")
	private int askOrder;

	@Column(name="ask_response")
	private int askResponse;

	@Column(name="broadcasted_time")
	private Timestamp broadcastedTime;

	@Column(insertable=false, updatable=false)
	private String format;

	private String mode;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="publisher_id")
	private User publisher;

	//bi-directional many-to-one association to BroadcastRecipient
	@OneToMany(mappedBy="broadcast")
	private List<BroadcastRecipient> broadcastRecipients;

	//bi-directional many-to-one association to BroadcastSchedule
	@OneToMany(mappedBy="broadcast")
	private List<BroadcastSchedule> broadcastSchedules;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="broadcast")
	private List<Message> messages;

	public Broadcast() {
	}

	public Broadcast(Organization organization, Group group, User publisher, String format, String mode,
			boolean askFeedback, boolean askOrder, boolean askResponse, boolean appOnly) {
		this.organization = organization;
		this.group = group;
		this.publisher = publisher;
		this.format = format;
		this.mode = mode;
		this.askFeedback = askFeedback ? 1 : 0;
		this.askOrder = askOrder ? 1 : 0;
		this.askResponse = askResponse ? 1 : 0;
		this.appOnly = appOnly ? 1 : 0;
	}

	public int getBroadcastId() {
		return this.broadcastId;
	}

	public void setBroadcastId(int broadcastId) {
		this.broadcastId = broadcastId;
	}

	public boolean getAppOnly() {
		return this.appOnly != 0;
	}

	public void setAppOnly(boolean appOnly) {
		this.appOnly = appOnly ? 1 : 0;
	}

	public boolean getAskFeedback() {
		return this.askFeedback != 0;
	}

	public void setAskFeedback(boolean askFeedback) {
		this.askFeedback = askFeedback ? 1 : 0;
	}

	public boolean getAskOrder() {
		return this.askOrder != 0;
	}

	public void setAskOrder(boolean askOrder) {
		this.askOrder = askOrder ? 1 : 0;
	}

	public boolean getAskResponse() {
		return this.askResponse != 0;
	}

	public void setAskResponse(boolean askResponse) {
		this.askResponse = askResponse ? 1 : 0;
	}

	public Timestamp getBroadcastedTime() {
		return this.broadcastedTime;
	}

	public void setBroadcastedTime(Timestamp broadcastedTime) {
		this.broadcastedTime = broadcastedTime;
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

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getPublisher() {
		return this.publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

	public List<BroadcastRecipient> getBroadcastRecipients() {
		return this.broadcastRecipients;
	}

	public void setBroadcastRecipients(List<BroadcastRecipient> broadcastRecipients) {
		this.broadcastRecipients = broadcastRecipients;
	}

	public BroadcastRecipient addBroadcastRecipient(BroadcastRecipient broadcastRecipient) {
		getBroadcastRecipients().add(broadcastRecipient);
		broadcastRecipient.setBroadcast(this);

		return broadcastRecipient;
	}

	public BroadcastRecipient removeBroadcastRecipient(BroadcastRecipient broadcastRecipient) {
		getBroadcastRecipients().remove(broadcastRecipient);
		broadcastRecipient.setBroadcast(null);

		return broadcastRecipient;
	}

	public List<BroadcastSchedule> getBroadcastSchedules() {
		return this.broadcastSchedules;
	}

	public void setBroadcastSchedules(List<BroadcastSchedule> broadcastSchedules) {
		this.broadcastSchedules = broadcastSchedules;
	}

	public BroadcastSchedule addBroadcastSchedule(BroadcastSchedule broadcastSchedule) {
		getBroadcastSchedules().add(broadcastSchedule);
		broadcastSchedule.setBroadcast(this);

		return broadcastSchedule;
	}

	public BroadcastSchedule removeBroadcastSchedule(BroadcastSchedule broadcastSchedule) {
		getBroadcastSchedules().remove(broadcastSchedule);
		broadcastSchedule.setBroadcast(null);

		return broadcastSchedule;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setBroadcast(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setBroadcast(null);

		return message;
	}

}
