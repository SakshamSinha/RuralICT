package webapp.model.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the user_phone_number database table.
 * 
 */
@Entity
@Table(name="user_phone_number")
public class UserPhoneNumber implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="phone_number")
	private String phoneNumber;

	private int primary;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public UserPhoneNumber() {
	}

	public UserPhoneNumber(User user, String phoneNumber, boolean primary) {
		this.user = user;
		this.phoneNumber = phoneNumber;
		this.primary = primary ? 1 : 0;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean getPrimary() {
		return this.primary != 0;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary ? 1 : 0;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
