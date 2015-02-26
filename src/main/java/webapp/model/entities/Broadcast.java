package webapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the broadcast database table.
 * 
 */
@Entity
@Table(name="broadcast")
public class Broadcast implements Serializable {
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

	private String format;

	private String mode;

	@Column(name="text_content")
	private String textContent;

	@Column(name="voice_broadcast_draft")
	private int voiceBroadcastDraft;

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
	private User user;

	//bi-directional many-to-one association to Voice
	@ManyToOne
	@JoinColumn(name="voice_id")
	private Voice voice;

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

	public int getBroadcastId() {
		return this.broadcastId;
	}

	public void setBroadcastId(int broadcastId) {
		this.broadcastId = broadcastId;
	}

	public int getAppOnly() {
		return this.appOnly;
	}

	public void setAppOnly(int appOnly) {
		this.appOnly = appOnly;
	}

	public int getAskFeedback() {
		return this.askFeedback;
	}

	public void setAskFeedback(int askFeedback) {
		this.askFeedback = askFeedback;
	}

	public int getAskOrder() {
		return this.askOrder;
	}

	public void setAskOrder(int askOrder) {
		this.askOrder = askOrder;
	}

	public int getAskResponse() {
		return this.askResponse;
	}

	public void setAskResponse(int askResponse) {
		this.askResponse = askResponse;
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

	public String getTextContent() {
		return this.textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public int getVoiceBroadcastDraft() {
		return this.voiceBroadcastDraft;
	}

	public void setVoiceBroadcastDraft(int voiceBroadcastDraft) {
		this.voiceBroadcastDraft = voiceBroadcastDraft;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Voice getVoice() {
		return this.voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
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