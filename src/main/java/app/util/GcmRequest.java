package app.util;

import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;


public class GcmRequest {
	
	public void broadcast(String userMessage, String title, List<String> androidTargets, int ... params) {
		final long serialVersionUID = 1L;
		//List<String> androidTargets = new ArrayList<String>();
		//final String SENDER_ID = "AIzaSyBsUr9b5VT5_oH_0FSygF8us4AXKA1yvGw";
		final String SENDER_ID = "AIzaSyArxqWuwifUbkvXAgfAAzxuls3TBrMeMBg";
		Sender sender = new Sender(SENDER_ID);
		Message message = null;
		if (params.length == 1) {
	     message = new Message.Builder()
	        //.timeToLive(30)
	        .delayWhileIdle(false)
	        .addData("message", userMessage)
	        .addData("title", title)
	        .addData("id", Integer.toString(params[0]))
	        .build();
		}
		else if (params.length == 2){
			 message = new Message.Builder()
				        //.timeToLive(30)
				        .delayWhileIdle(false)
				        .addData("message", userMessage)
				        .addData("title", title)
				        .addData("id", Integer.toString(params[0]))
				        .addData("userId", Integer.toString(params[1]))
				        .build();
			
		}
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
