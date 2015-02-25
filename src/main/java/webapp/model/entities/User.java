package webapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private int userId;

	private String address;

	@Column(name="call_locale")
	private String callLocale;

	private String email;

	private String name;

	@Column(name="sha256_password")
	private String sha256Password;

	@Column(name="web_locale")
	private String webLocale;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCallLocale() {
		return this.callLocale;
	}

	public void setCallLocale(String callLocale) {
		this.callLocale = callLocale;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSha256Password() {
		return this.sha256Password;
	}

	public void setSha256Password(String sha256Password) {
		this.sha256Password = sha256Password;
	}

	public String getWebLocale() {
		return this.webLocale;
	}

	public void setWebLocale(String webLocale) {
		this.webLocale = webLocale;
	}

}