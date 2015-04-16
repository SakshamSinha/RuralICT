package app.entities;

import java.io.Serializable;
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

import app.entities.broadcast.Broadcast;
import app.entities.message.Message;


/**
 * The persistent class for the group database table.
 * 
 */
@Entity
@Table(name="group")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="group_id")
	private int groupId;

	private String name;

	//bi-directional many-to-one association to Broadcast
	@OneToMany(mappedBy="group")
	private List<Broadcast> broadcasts;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="group")
	private List<Message> messages;

	//bi-directional many-to-one association to BroadcastDefaultSetting
	@OneToMany(mappedBy="group")
	private List<BroadcastDefaultSetting> broadcastDefaultSettings;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="parent_group_id")
	private Group parentGroup;

	//bi-directional many-to-one association to Group
	@OneToMany(mappedBy="parentGroup")
	private List<Group> subGroups;

	//bi-directional many-to-one association to GroupMembership
	@OneToMany(mappedBy="group")
	private List<GroupMembership> groupMemberships;

	public Group() {
	}

	public Group(Organization organization, String name, Group parentGroup) {
		this.organization = organization;
		this.name = name;
		this.parentGroup = parentGroup;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Broadcast> getBroadcasts() {
		return this.broadcasts;
	}

	public void setBroadcasts(List<Broadcast> broadcasts) {
		this.broadcasts = broadcasts;
	}

	public Broadcast addBroadcast(Broadcast broadcast) {
		getBroadcasts().add(broadcast);
		broadcast.setGroup(this);

		return broadcast;
	}

	public Broadcast removeBroadcast(Broadcast broadcast) {
		getBroadcasts().remove(broadcast);
		broadcast.setGroup(null);

		return broadcast;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setGroup(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setGroup(null);

		return message;
	}

	public List<BroadcastDefaultSetting> getBroadcastDefaultSettings() {
		return this.broadcastDefaultSettings;
	}

	public void setBroadcastDefaultSettings(List<BroadcastDefaultSetting> broadcastDefaultSettings) {
		this.broadcastDefaultSettings = broadcastDefaultSettings;
	}

	public BroadcastDefaultSetting addBroadcastDefaultSetting(BroadcastDefaultSetting broadcastDefaultSetting) {
		getBroadcastDefaultSettings().add(broadcastDefaultSetting);
		broadcastDefaultSetting.setGroup(this);

		return broadcastDefaultSetting;
	}

	public BroadcastDefaultSetting removeBroadcastDefaultSetting(BroadcastDefaultSetting broadcastDefaultSetting) {
		getBroadcastDefaultSettings().remove(broadcastDefaultSetting);
		broadcastDefaultSetting.setGroup(null);

		return broadcastDefaultSetting;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Group getParentGroup() {
		return this.parentGroup;
	}

	public void setParentGroup(Group parentGroup) {
		this.parentGroup = parentGroup;
	}

	public List<Group> getSubGroups() {
		return this.subGroups;
	}

	public void setSubGroups(List<Group> subGroups) {
		this.subGroups = subGroups;
	}

	public Group addSubGroup(Group subGroup) {
		getSubGroups().add(subGroup);
		subGroup.setParentGroup(this);

		return subGroup;
	}

	public Group removeSubGroup(Group subGroup) {
		getSubGroups().remove(subGroup);
		subGroup.setParentGroup(null);

		return subGroup;
	}

	public List<GroupMembership> getGroupMemberships() {
		return this.groupMemberships;
	}

	public void setGroupMemberships(List<GroupMembership> groupMemberships) {
		this.groupMemberships = groupMemberships;
	}

	public GroupMembership addGroupMembership(GroupMembership groupMembership) {
		getGroupMemberships().add(groupMembership);
		groupMembership.setGroup(this);

		return groupMembership;
	}

	public GroupMembership removeGroupMembership(GroupMembership groupMembership) {
		getGroupMemberships().remove(groupMembership);
		groupMembership.setGroup(null);

		return groupMembership;
	}

}
