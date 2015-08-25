package app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


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

	@Column(name="is_admin")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean isAdmin;

	@Column(name="is_publisher")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean isPublisher;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	@Column(name="status")
	private int status;
	
	public OrganizationMembership() {
	}

	public OrganizationMembership(Organization organization, User user, boolean isAdmin, boolean isPublisher, int status) {
		this.organization = organization;
		this.user = user;
		this.isAdmin = isAdmin;
		this.isPublisher = isPublisher;
		this.status = status;
	}

	public int getOrganizationMembershipId() {
		return this.organizationMembershipId;
	}

	public void setOrganizationMembershipId(int organizationMembershipId) {
		this.organizationMembershipId = organizationMembershipId;
	}

	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean getIsPublisher() {
		return this.isPublisher;
	}

	public void setIsPublisher(boolean isPublisher) {
		this.isPublisher = isPublisher;
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
