package webapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the group_membership database table.
 * 
 */
@Entity
@Table(name="group_membership")
public class GroupMembership implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="group_membership_id")
	private int groupMembershipId;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public GroupMembership() {
	}

	public int getGroupMembershipId() {
		return this.groupMembershipId;
	}

	public void setGroupMembershipId(int groupMembershipId) {
		this.groupMembershipId = groupMembershipId;
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

}