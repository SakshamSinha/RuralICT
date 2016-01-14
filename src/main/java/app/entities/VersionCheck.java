package app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="version_check")
public class VersionCheck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "version")
	private float version;
	
	@Column(name = "mandatory")
	private int mandatory;
	
	public VersionCheck() {
		
	}
	public VersionCheck(float version, int mandatory) {
		this.version = version;
		this.mandatory = mandatory;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public float getVersion() {
		return this.version;
	}
	
	public void setVersion(float version) {
		this.version = version;
	}
	
	public int getMandatory() {
		return this.mandatory;
	}
	
	public void setMandatory(int mandatory){
		this.mandatory = mandatory;
	}
}
