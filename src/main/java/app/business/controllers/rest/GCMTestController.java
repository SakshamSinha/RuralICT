package app.business.controllers.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

@RestController
@RequestMapping("/api")


public class GCMTestController {

	@RequestMapping(value ="/broadcast", method = RequestMethod.GET )
	public void broadcast(@RequestParam (value="Message") String userMessage, @RequestParam(value="CollapseKey") String collapseKey, @RequestParam(value="Title") String title){
		 final long serialVersionUID = 1L;
		 List<String> androidTargets = new ArrayList<String>();
		 final String SENDER_ID = "AIzaSyBsUr9b5VT5_oH_0FSygF8us4AXKA1yvGw";
		 final String CHEAT = "cfTzvcuWBxY:APA91bEj82KHgFMyKz-oT7SQoK_awFCP87hUza6G2tb4QTMAC2AC3CmKy9cGoGaGwCGH5NG0g_V3FK1bD6v-rBEzVS7gvLfvD7AB0BHFR-bWqdhg8HWj9giANBkuYRA6k0_tUHKPvLfQ";
		 androidTargets.add(CHEAT);
		 Sender sender = new Sender(SENDER_ID);
	     Message message = new Message.Builder()
	        .collapseKey(collapseKey)
	        .timeToLive(30)
	        .delayWhileIdle(true)
	        .addData("message", userMessage)
	        .addData("title", title)
	        .build();
	     
	        try {

	            MulticastResult result = sender.send(message, androidTargets, 1);
	             
	            if (result.getResults() != null) {
	                int canonicalRegId = result.getCanonicalIds();
	                if (canonicalRegId != 0) {
	                     
	                }
	            } else {
	                int error = result.getFailure();
	                System.out.println("Broadcast failure: " + error);
	            }
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println("Done");
	}
}
