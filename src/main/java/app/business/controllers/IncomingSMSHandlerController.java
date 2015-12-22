package app.business.controllers;

import in.ac.iitb.ivrs.telephony.base.util.IVRUtils;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.business.services.TelephonyService;
import app.business.services.UserPhoneNumberService;
import app.entities.Group;
import app.entities.User;

@Controller
public class IncomingSMSHandlerController {

	@Autowired
	UserPhoneNumberService userPhoneNumberService;

	@Autowired
	TelephonyService telephonyService;
	@RequestMapping(value="/IncomingSMSHandler", method=RequestMethod.GET)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {


		User user = null;
		Group group =new Group();
		group.setGroupId(1);
		String caller_id = request.getParameter("cid");
		String message = request.getParameter("message");

		java.util.Date date= new java.util.Date();
		Timestamp currentTimestamp= new Timestamp(date.getTime());

		try{
			user = userPhoneNumberService.getUserPhoneNumber(caller_id).getUser();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//Needs to be changed if implemented in future. Change is checking on organization membership.
		if(user!=null){

			telephonyService.addTextMessage(user, null, group, "web","text" ,false, message,currentTimestamp);
			IVRUtils.sendSMS(caller_id, "Message is received",null,null);
		}
		else{

			IVRUtils.sendSMS(caller_id, "You are not registered",null, null);
			//IVRUtils.sendSMS(caller_id, "You are not registered");
		}

	}

}