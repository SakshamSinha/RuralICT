package app.business.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.message.VoiceMessageService;
import app.entities.message.Message;

@RestController
@RequestMapping("/rest/voiceMessage")
public class VoiceMessageRestController {
	
	@Autowired
	VoiceMessageService voiceMessageService;
	
	@RequestMapping(value="/updateComment/{messageId}/{comment}", method=RequestMethod.GET)
	@Transactional
	public boolean updateComment(@PathVariable int messageId, @PathVariable String comment) {
		try {
			Message currentTextMessage = voiceMessageService.getMessage((Integer)messageId);
			voiceMessageService.updateMessageComment(comment, currentTextMessage);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
