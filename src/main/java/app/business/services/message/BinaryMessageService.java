package app.business.services.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.MessageRepository;

@Service
public class BinaryMessageService extends MessageService {
	
	@Autowired
	MessageRepository messageRepository;
	
}