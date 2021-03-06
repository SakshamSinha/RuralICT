package app.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the user_phone_number database table.
 * 
 */
@Entity
@Table(name="user_phone_number")
public class UserPhoneNumber implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_phone_number_id")
	private int userPhoneNumberId;
	
	@Column(name="phone_number")
	private String phoneNumber;

	@Type(type="org.hibernate.type.NumericBooleanType")
	@Column(name="is_primary")
	private boolean primary;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public UserPhoneNumber() {
	}

	public UserPhoneNumber(User user, String phoneNumber, boolean primary) {
		this.user = user;
		this.phoneNumber = phoneNumber;
		this.primary = primary;
	}

	public int getUserPhoneNumberId() {
		return this.userPhoneNumberId;
	}

	public void setUserPhoneNumberId(int userPhoneNumberId) {
		this.userPhoneNumberId = userPhoneNumberId;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean getPrimary() {
		return this.primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
