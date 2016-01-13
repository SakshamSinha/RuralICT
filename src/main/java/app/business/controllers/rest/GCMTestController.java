package app.business.controllers.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")


public class GCMTestController {

	@RequestMapping(value ="/broadcast", method = RequestMethod.GET )
	public void broadcast(){
		 final long serialVersionUID = 1L;
		 final String SENDER_ID = "AIzaSyBsUr9b5VT5_oH_0FSygF8us4AXKA1yvGw";
		 final String CHEAT = "APA91bEju-eB74DWRChlVt5gh7YfIVzNOr8gRYPisFbmcwBPlMJeGTYmdF7cYR3oL-F9KqmTey016drxmWAkYa4WQv9pQ_KvRzI1VUkql6ObbYGPkV7UBsm6pYoBw0dEk3veh60v3lVhDtLztWIbDc3XqtjU_fE_0g";
	}
	
	
}
