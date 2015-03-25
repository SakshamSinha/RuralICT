package webapp.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the organization_membership database table.
 * 
 */
@Entity
@Table(name="organization_membership")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="organizationMembershipId")
public class OrganizationMembership implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="organization_membership_id")
	private int organizationMembershipId;

	@Column(name="is_admin")
	private int isAdmin;

	@Column(name="is_publisher")
	private int isPublisher;

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

	public OrganizationMembership(Organization organization, User user, boolean isAdmin, boolean isPublisher) {
		this.organization = organization;
		this.user = user;
		this.isAdmin = isAdmin ? 1 : 0;
		this.isPublisher = isPublisher ? 1 : 0;
	}

	public int getOrganizationMembershipId() {
		return this.organizationMembershipId;
	}

	public void setOrganizationMembershipId(int organizationMembershipId) {
		this.organizationMembershipId = organizationMembershipId;
	}

	public boolean getIsAdmin() {
		return this.isAdmin != 0;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin ? 1 : 0;
	}

	public boolean getIsPublisher() {
		return this.isPublisher != 0;
	}

	public void setIsPublisher(boolean isPublisher) {
		this.isPublisher = isPublisher ? 1 : 0;
	}

	@JsonProperty(value="userId")
	@JsonIdentityReference(alwaysAsId=true)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonProperty(value="organizationId")
	@JsonIdentityReference(alwaysAsId=true)
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}
