package webapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the organization_membership database table.
 * 
 */
@Entity
@Table(name="organization_membership")
public class OrganizationMembership implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="organization_membership_id")
	private int organizationMembershipId;

	private String role;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	public OrganizationMembership() {
	}

	public int getOrganizationMembershipId() {
		return this.organizationMembershipId;
	}

	public void setOrganizationMembershipId(int organizationMembershipId) {
		this.organizationMembershipId = organizationMembershipId;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}