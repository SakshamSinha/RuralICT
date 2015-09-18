/* Custom Utility Object Created for getting data to be displayed on 'Manage Users' page table */

package app.util;

import java.sql.Timestamp;

public class UserManage{

	private int manageUserID;
	private String name;
	private String email;
	private String phone;
	private String role;
	private String address;
	private Timestamp time;

	public UserManage(int manageUserID, String name, String email, String phone, String role, String address, Timestamp time)
	{
		this.manageUserID = manageUserID;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
		this.address = address;
		this.time = time;
	}

	public int getmanageUserID()
	{
		return manageUserID;
	}

	public String getName()
	{
		return name;
	}
	
	public Timestamp getTime() {
		return this.time;
	}
	
	public void setTime(Timestamp Time) {
		this.time = Time;
	}
	
	public String getEmail()
	{
		return email;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getRole()
	{
        return role;
	}

	public String getAddress()
	{
		return address;
	}
}