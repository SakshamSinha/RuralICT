package app.business.controllers;

public class UserManage{
	
	 private int manageUserID;
	 private String name;
	 private String email;
	 private String phone;
	 private String role;
	 private String address;
	 
	 public UserManage(int manageUserID, String name, String email, String phone, String role, String address)
	 {
		 this.manageUserID = manageUserID;
		 this.name = name;
		 this.email = email;
		 this.phone = phone;
		 this.role = role;
		 this.address = address;
	 }
	 
	public int getmanageUserID()
	{
		return manageUserID;
	}
	 
	public String getName()
	{
		return name;
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