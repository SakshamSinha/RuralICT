package app.business.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.business.services.message.MessageService;
import app.business.services.message.TextMessageService;
import app.business.services.message.VoiceMessageService;
import app.entities.Group;
import app.entities.InboundCall;
import app.entities.Order;
import app.entities.User;
import app.entities.Voice;
import app.entities.broadcast.Broadcast;
import app.entities.message.BinaryMessage;
import app.entities.message.TextMessage;
import app.entities.message.VoiceMessage;

@Service
public class TelephonyService {

	@Autowired
	MessageService messageService;

	@Autowired
	VoiceMessageService voiceMessageService;
	
	@Autowired
	TextMessageService textMessageService;
	
	@Autowired
	VoiceService voiceService;

	@Autowired
	OrderService orderService;
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;


	public void addVoiceMessage(User user, Broadcast broadcast, Group group, String mode, String type, boolean response, String url, InboundCall inboundCall){
		Voice voice=new Voice(url,false);
		voice = voiceService.addVoice(voice);
		
		VoiceMessage voiceMessage=new VoiceMessage(user, broadcast, group, mode, type, response, null, voice, inboundCall);
		
		if(type.equals("order")) {
			Order order = new Order();
			order.setStatus("new");
			order.setOrganization(group.getOrganization());
			order = orderService.addOrder(order);
			voiceMessage.setOrder(order);
		}
		
		voiceMessageService.addMessage(voiceMessage);
	}
	
	public void addVoiceMessage(String userPhoneNumber, Broadcast broadcast, Group group, String mode, String type, boolean response, String url, InboundCall inboundCall){
		Voice voice=new Voice(url,false);
		voice = voiceService.addVoice(voice);
		
		VoiceMessage voiceMessage=new VoiceMessage(userPhoneNumberService.getUserPhoneNumber(userPhoneNumber).getUser(), broadcast, group, mode, type, response, null, voice, inboundCall);
		
		if(type.equals("order")) {
			Order order = new Order();
			order.setStatus("new");
			order.setOrganization(group.getOrganization());
			order = orderService.addOrder(order);
			voiceMessage.setOrder(order);
		}
		
		voiceMessageService.addMessage(voiceMessage);

	}


	public void addTextMessage(User user, Group group,String mode, String type, boolean response,String textContent, Timestamp textTime){
		TextMessage textMessage=new TextMessage(user, null, group,mode, type, response, null, textContent, textTime);
		messageService.addMessage(textMessage);
	}

	public void addBinaryMessage(User user, Group group,String mode, String type, boolean response, Timestamp time){
		BinaryMessage binaryMessage=new BinaryMessage(user, null,time, group, mode, type, response, null);
		messageService.addMessage(binaryMessage);
	}

	public void addBinaryMessage(String userPhoneNumber,Broadcast broadcast , Group group,String mode, String type, boolean response, Timestamp time){
		BinaryMessage binaryMessage=new BinaryMessage(userPhoneNumberService.getUserPhoneNumber(userPhoneNumber).getUser(), null,time, group, mode, type, response, null);
		messageService.addMessage(binaryMessage);
	}

	
	public void addTextMessage(User user, Broadcast broadcast, Group group, String mode, String type, boolean response,String textContent, Timestamp textTime){
		TextMessage textMessage=new TextMessage(user, broadcast, group, mode, type, response, null, textContent, textTime);
		
		if(type.equals("order")) {
			Order order = new Order();
			order.setStatus("new");
			order.setOrganization(group.getOrganization());
			order = orderService.addOrder(order);
			textMessage.setOrder(order);
		}
		
		textMessageService.addMessage(textMessage);
	}

	public void addBinaryMessage(User user, Broadcast broadcast, Group group, String mode, String type, boolean response, Timestamp time){
		BinaryMessage binaryMessage=new BinaryMessage(user, broadcast, time, group, mode, type, response, null);
		
		if(type.equals("order")) {
			Order order = new Order();
			order.setStatus("new");
			order.setOrganization(group.getOrganization());
			order = orderService.addOrder(order);
			binaryMessage.setOrder(order);
		}
		
		messageService.addMessage(binaryMessage);
	}

}
