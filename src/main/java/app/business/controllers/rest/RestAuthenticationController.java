package app.business.controllers.rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.GroupMembershipService;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.data.repositories.GroupMembershipRepository;
import app.data.repositories.GroupRepository;
import app.data.repositories.OrganizationMembershipRepository;
import app.data.repositories.OrganizationRepository;
import app.data.repositories.UserPhoneNumberRepository;
import app.data.repositories.UserRepository;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.util.SendMail;


@RestController
@RequestMapping("/app")
public class RestAuthenticationController {

	@Autowired
	UserPhoneNumberRepository userPhoneNumberRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	OrganizationMembershipRepository organizationMemberRepository;
	
	@Autowired
	GroupMembershipRepository groupMembershipRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	GroupMembershipService groupMembershipService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	OrganizationMembershipService organizationMembershipService;
	
	@RequestMapping(value = "/otp",method = RequestMethod.POST )
	public String otp(@RequestBody String requestBody) throws Exception
	{
		JSONObject responseJsonObject = new JSONObject();
		HashMap<String,String> response= new HashMap<String, String>();
		JSONObject jsonObject=null;
		String phonenumber = null;
		String email = null;
		try {
			jsonObject = new JSONObject(requestBody);
			phonenumber=jsonObject.getString("phonenumber");
			email=jsonObject.getString("email");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		UserPhoneNumber userPhoneNumber=userPhoneNumberRepository.findByPhoneNumber(phonenumber);
		List<Organization> orglist = organizationRepository.findAll();
		JSONArray orgArray=new JSONArray();
		for(Organization organization: orglist)
		{
			try {
				if(!organization.getName().equals("Testing") && !organization.getName().equals("TestOrg1") && !organization.getName().equals("Testorg3"))
				{
					JSONObject org= new JSONObject();
					org.put("name", organization.getName());
					org.put("org_id", organization.getOrganizationId());
					org.put("abbr", organization.getAbbreviation());	
					org.put("ph_no", organization.getIvrNumber());
					orgArray.put(org);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}
				
		if(userPhoneNumber==null)
		{
			String otp=randomString(4);
			int status=0;
			if((status=SendMail.sendMail(email, "Cottage Industry App OTP" , "Your OTP is: " + otp ))==1)
				response.put("text", "Otp has been sent to your email");
			//IVRUtils.sendSMS(phonenumber, otp, null , null);
			response.put("otp",otp);
			try {
				responseJsonObject.put("otp", otp);
				responseJsonObject.put("organizations",orgArray);
				if(status==1)
					responseJsonObject.put("text", "Otp has been sent to your email");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return responseJsonObject.toString();
		}
		else
		{
			response.put("otp",null);
			try {
				responseJsonObject.put("otp", "null");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return responseJsonObject.toString();
		}
		
		
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.POST ,produces="application/json")
	@Transactional
	public HashMap<String,String> registration(@RequestBody String requestBody)
	{
		/*
		 * Add organization membership, user, organization
		 */
		HashMap<String,String> response= new HashMap<String, String>();
		JSONObject jsonObject=null;
		String phonenumber = null;
		String address =null;
		String password=null;
		String name=null;
		String email=null;
		JSONArray orgListJsonArray = null;
		User user = new User();
		UserPhoneNumber userPhoneNumber= new UserPhoneNumber();
		List<UserPhoneNumber> userPhoneNumbers= new ArrayList<UserPhoneNumber>();
		//List<Organization> orgList= new ArrayList<Organization>();
		try {
			jsonObject = new JSONObject(requestBody);
			phonenumber=jsonObject.getString("phonenumber");
			address=jsonObject.getString("address");
			password=jsonObject.getString("password");
			name=jsonObject.getString("name");
			email=jsonObject.getString("email");
			//orgListJsonArray=jsonObject.getJSONArray("orglist");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		/*
		 * Check if exists 
		*/
		User usercheck = userRepository.findByuserPhoneNumbers_phoneNumber(phonenumber);
		if(usercheck!=null)
		{
			response.put("Status", "Failure");
			response.put("Error", "Number Exists");
			return response;
		}
		List<User> userCheckList = userRepository.findByEmail(email);
		if(userCheckList.size()>0)
		{
			response.put("Status", "Failure");
			response.put("Error", "Email Exists");
			return response;
		}
		/*for (int i=0;i<orgListJsonArray.length();i++)
		{
			 try {
				JSONObject org = orgListJsonArray.getJSONObject(i);
				int org_id=org.getInt("org_id");
				//Adding organization
				Organization organization= organizationRepository.findOne(org_id);
				if(organization==null)
				{
					response.put("Status", "Failure");
					response.put("Error", "Organization with Id "+org_id+" does not exists");
					return response;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}*/
		
		//Organization organization=new Organization();
		user.setAddress(address);
		user.setCallLocale("en");
		user.setEmail(email);
		user.setSha256Password(passwordEncoder.encode(password));
		user.setName(name);
//		java.util.Date date= new java.util.Date();
//		Timestamp currentTimestamp= new Timestamp(date.getTime());
//		user.setTime(currentTimestamp);
		user=userRepository.save(user);
		/*List<OrganizationMembership>  organizationMemberships= new ArrayList<OrganizationMembership>();
		List<GroupMembership>  groupMemberships= new ArrayList<GroupMembership>();
		for (int i=0;i<orgListJsonArray.length();i++)
		{
			 try {
				JSONObject org = orgListJsonArray.getJSONObject(i);
				int org_id=org.getInt("org_id");
				//Adding organization
				organization= organizationRepository.findOne(org_id);
				if(organization==null)
				{
					response.put("Status", "Failure");
					response.put("Error", "Organization with Id "+org_id+" does not exists");
					return response;
				}
				orgList.add(organization);
				OrganizationMembership organizationMembership = new OrganizationMembership();
				organizationMembership.setOrganization(organization);
				organizationMembership.setUser(user);
				organizationMembership.setIsAdmin(false);
				organizationMembership.setIsPublisher(false);
				organizationMembership.setStatus(0);
				organizationMembership=organizationMemberRepository.save(organizationMembership);
				organizationMemberships.add(organizationMembership);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}*/
		//user.setGroupMemberships(groupMemberships);
		//user.setOrganizationMemberships(organizationMemberships);
		user.setTextbroadcastlimit(0);
		user.setVoicebroadcastlimit(0);
		user=userRepository.save(user);
		System.out.println("User phone number is being saved");
		phonenumber="91"+phonenumber;
		userPhoneNumber.setPhoneNumber(phonenumber);
		userPhoneNumber.setPrimary(true);
		userPhoneNumber.setUser(user);
		userPhoneNumber=userPhoneNumberRepository.save(userPhoneNumber);
		userPhoneNumbers.add(userPhoneNumber);
		user.setUserPhoneNumbers(userPhoneNumbers);
		System.out.println("User phone number is  saved");
		userRepository.save(user);
//		for(Organization org: orgList)
//		{
//			groupMembershipService.addParentGroupMembership(org, user);
//		}
		response.put("Status","Success");
		return response;
	}
	
	@RequestMapping(value = "/orgsave",method = RequestMethod.POST ,produces="application/json")
	@Transactional
	public HashMap<String,String> orgselection(@RequestBody String requestBody)
	{
		/*
		 * Add organization membership, user, organization
		 */
		HashMap<String,String> response= new HashMap<String, String>();
		JSONObject jsonObject=null;
		String phonenumber = null;
		String email=null;
		JSONArray orgListJsonArray = null;
		List<Organization> orgList= new ArrayList<Organization>();
		try {
			jsonObject = new JSONObject(requestBody);
			phonenumber="91"+jsonObject.getString("phonenumber");
//			address=jsonObject.getString("address");
//			password=jsonObject.getString("password");
//			name=jsonObject.getString("name");
//			email=jsonObject.getString("email");
			orgListJsonArray=jsonObject.getJSONArray("orglist");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		/*
		 * Check if exists 
		*/
		System.out.println("User phone no is: "+phonenumber);
		User user = userRepository.findByuserPhoneNumbers_phoneNumber(phonenumber);
		if(user==null)
		{
			response.put("Status", "Failure");
			response.put("Error", "Number doesn't Exists");
			return response;
		}
		List<User> userCheckList = userRepository.findByEmail(email);
		if(userCheckList.size()==0)
		{
			response.put("Status", "Failure");
			response.put("Error", "Email doesn't exist Exists");
			return response;
		}
		for (int i=0;i<orgListJsonArray.length();i++)
		{
			 try {
				JSONObject org = orgListJsonArray.getJSONObject(i);
				int org_id=org.getInt("org_id");
				//Adding organization
				Organization organization= organizationRepository.findOne(org_id);
				if(organization==null)
				{
					response.put("Status", "Failure");
					response.put("Error", "Organization with Id "+org_id+" does not exists");
					return response;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		Organization organization=new Organization();
//		user.setAddress(address);
//		user.setCallLocale("en");
//		user.setEmail(email);
//		user.setSha256Password(passwordEncoder.encode(password));
//		user.setName(name);
		java.util.Date date= new java.util.Date();
		Timestamp currentTimestamp= new Timestamp(date.getTime());
		user.setTime(currentTimestamp);
		user=userRepository.save(user);
		List<OrganizationMembership>  organizationMemberships= new ArrayList<OrganizationMembership>();
		List<GroupMembership>  groupMemberships= new ArrayList<GroupMembership>();
		for (int i=0;i<orgListJsonArray.length();i++)
		{
			 try {
				JSONObject org = orgListJsonArray.getJSONObject(i);
				int org_id=org.getInt("org_id");
				//Adding organization
				organization= organizationRepository.findOne(org_id);
				if(organization==null)
				{
					response.put("Status", "Failure");
					response.put("Error", "Organization with Id "+org_id+" does not exists");
					return response;
				}
				orgList.add(organization);
				OrganizationMembership organizationMembership = new OrganizationMembership();
				organizationMembership.setOrganization(organization);
				organizationMembership.setUser(user);
				organizationMembership.setIsAdmin(false);
				organizationMembership.setIsPublisher(false);
				organizationMembership.setStatus(0);
				organizationMembership=organizationMemberRepository.save(organizationMembership);
				organizationMemberships.add(organizationMembership);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		//user.setGroupMemberships(groupMemberships);
		user.setOrganizationMemberships(organizationMemberships);
//		user.setTextbroadcastlimit(0);
//		user.setVoicebroadcastlimit(0);
//		user=userRepository.save(user);
//		phonenumber="91"+phonenumber;
//		userPhoneNumber.setPhoneNumber(phonenumber);
//		userPhoneNumber.setPrimary(true);
//		userPhoneNumber.setUser(user);
//		userPhoneNumber=userPhoneNumberRepository.save(userPhoneNumber);
//		userPhoneNumbers.add(userPhoneNumber);
//		user.setUserPhoneNumbers(userPhoneNumbers);
		userRepository.save(user);
		for(Organization org: orgList)
		{
			groupMembershipService.addParentGroupMembership(org, user);
		}
		response.put("Status","Success");
		return response;
	}
	
	@RequestMapping(value = "/forgotpassword",method = RequestMethod.POST )
	public String forgotpassword(@RequestBody String requestBody)
	{	
		JSONObject responseJsonObject = new JSONObject();
		HashMap<String,String> response= new HashMap<String, String>();
		JSONObject jsonObject=null;
		String phonenumber = null;
		try {
			jsonObject = new JSONObject(requestBody);
			phonenumber=jsonObject.getString("phonenumber");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		UserPhoneNumber userPhoneNumber = userPhoneNumberRepository.findByPhoneNumber(phonenumber);
		if(userPhoneNumber!=null)
		{
			String otp=randomString(4);
			int status=0;
			String email=userPhoneNumber.getUser().getEmail();
			if((status=SendMail.sendMail(email, "Cottage Industry App OTP" , "Your OTP is: " + otp ))==1)
				response.put("text", "Otp has been sent to your email");
			//IVRUtils.sendSMS(phonenumber, otp, null , null);
			response.put("otp",otp);
			try {
				responseJsonObject.put("otp", otp);
				if(status==1)
				responseJsonObject.put("text", "Otp has been sent to your email");	
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return responseJsonObject.toString();
		}
		else
		{
			response.put("otp",null);
			try {
				responseJsonObject.put("otp", "null");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return responseJsonObject.toString();
		}
		
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.POST )
	public String login(@RequestBody String requestBody)
	{
		//Check if exists
		JSONObject responseJsonObject = new JSONObject();
		JSONObject jsonObject=null;
		String phonenumber = null;
		String password=null;
		try {
			jsonObject = new JSONObject(requestBody);
			phonenumber=jsonObject.getString("phonenumber");
			password=jsonObject.getString("password");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		User user = userRepository.findByuserPhoneNumbers_phoneNumber(phonenumber);
		
		
		if(user!=null)
		{
			/*
			 * Check if the user is approved
			 * 
			 */
			if(password==null)
			{
				try {
					responseJsonObject.put("Status", "Error");
					responseJsonObject.put("Error", "Password is null.");
					responseJsonObject.put("Authentication", "failure");
					responseJsonObject.put("registered", "true");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return responseJsonObject.toString();
			}
			if(user.getSha256Password()==null)
			{
				try {
					responseJsonObject.put("Status", "Error");
					responseJsonObject.put("Error", "User Password is null. Set the password");
					responseJsonObject.put("Authentication", "failure");
					responseJsonObject.put("registered", "true");
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				return responseJsonObject.toString();
			}
			if(passwordEncoder.matches(password, user.getSha256Password()))
			{
				List<OrganizationMembership> organizationMemberships = user.getOrganizationMemberships();
				List<Organization> organizationList=organizationService.getAllOrganizationList();
				if(organizationList.size()==0)
				{
					try{
						responseJsonObject.put("Authentication", "success");	
						responseJsonObject.put("email", user.getEmail());
						responseJsonObject.put("Error", "No organization has accepted the user");
						responseJsonObject.put("organizations", "null");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					return responseJsonObject.toString();
				}
				
				
				JSONArray orgArray= new JSONArray();
				for(Organization organization: organizationList)
				{
					try {
						if(!organization.getName().equals("Testing") && !organization.getName().equals("TestOrg1") && !organization.getName().equals("Testorg3"))
						{
							
							JSONObject org= new JSONObject();
							org.put("name", organization.getName());
							org.put("org_id", organization.getOrganizationId());
							org.put("abbr", organization.getAbbreviation());	
							org.put("ph_no", organization.getIvrNumber());
							if(organizationMembershipService.getUserOrganizationMembership(user, organization)==null)
							{
								org.put("status", "Rejected");
								orgArray.put(org);
								continue;
							}
							if(organizationMembershipService.getOrganizationMembershipStatus(user, organization)==1)
								org.put("status", "Accepted");
							//organizationList.add(organizationMembership.getOrganization());
							else if(organizationMembershipService.getOrganizationMembershipStatus(user, organization)==0)
								org.put("status", "Pending");
							else
								org.put("status", "Rejected");
							orgArray.put(org);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}	
				}
				try 
				{
					responseJsonObject.put("Authentication", "success");	
					responseJsonObject.put("email", user.getEmail());
					responseJsonObject.put("organizations", orgArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return responseJsonObject.toString();
			}
			else
			{
				try {
					responseJsonObject.put("Authentication", "failure");
					responseJsonObject.put("registered", "true");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return responseJsonObject.toString();
			}
		}
		else
		{
			try {
				responseJsonObject.put("Authentication", "failure");
				responseJsonObject.put("registered", "false");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return responseJsonObject.toString();
		}
	
	}
	
	
	@RequestMapping(value = "/changepassword",method = RequestMethod.POST )
	public HashMap<String,String> changePassword(@RequestBody String requestBody)
	{
		HashMap<String,String> response = new HashMap<String, String>();
		JSONObject jsonObject=null;
		String password = null;
		String phonenumber=null;
		try {
			jsonObject = new JSONObject(requestBody);
			phonenumber=jsonObject.getString("phonenumber");
			password=jsonObject.getString("password");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		User user = userRepository.findByuserPhoneNumbers_phoneNumber(phonenumber);
		if(user==null)
		{
			response.put("Status","Error");
			response.put("Error","No user with the phone number:"+phonenumber+" exists.");
			return response;
		}
		//AuthenticatedUser authuser=Utils.getSecurityPrincipal();
		
		password=passwordEncoder.encode(password);
		user.setSha256Password(password);
		userRepository.save(user);
		response.put("Status","Success");
		return response;
	}
	
	
	//Util functions
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();

	String randomString( int len ) 
	{
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
}
