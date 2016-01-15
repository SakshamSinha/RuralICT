package app.util;

import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;


public class GcmRequest {
	
	public void broadcast(String userMessage, String title, List<String> androidTargets) {
		final long serialVersionUID = 1L;
		//List<String> androidTargets = new ArrayList<String>();
		final String SENDER_ID = "AIzaSyBsUr9b5VT5_oH_0FSygF8us4AXKA1yvGw";
		Sender sender = new Sender(SENDER_ID);
	     Message message = new Message.Builder()
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
