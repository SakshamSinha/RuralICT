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

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getPrimary() {
		return this.primary;
	}

	public void setPrimary(int primary) {
		this.primary = primary;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}