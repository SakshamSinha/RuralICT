package app.business.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.business.services.message.MessageService;
import app.entities.Group;
import app.entities.InboundCall;
import app.entities.User;
import app.entities.UserPhoneNumber;
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
	VoiceService voiceService;
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	
	public void addVoiceMessage(User user,Broadcast broadcast ,Group group, String mode, String type, boolean response, String url, InboundCall inboundCall){
		Voice voice=new Voice(url,false);
		voiceService.addVoice(voice);
		VoiceMessage voiceMessage=new VoiceMessage(user, broadcast,group, mode, type, response, null, voice, inboundCall);
		messageService.addMessage(voiceMessage);
	}
	
	/*public void addVoiceMessage(String userPhoneNumber, Broadcast broadcast ,Group group, String mode, String type, boolean response, String url, InboundCall inboundCall){
		Voice voice=new Voice(url,false);
		voiceService.addVoice(voice);
		VoiceMessage voiceMessage=new VoiceMessage(userPhoneNumberService.getUserPhoneNumber(userPhoneNumber).getUser(), broadcast,group, mode, type, response, null, voice, inboundCall);
		messageService.addMessage(voiceMessage);
	}*/
	public void addVoiceMessage(String userPhoneNumber, Broadcast broadcast ,Group group,String mode, String type, boolean response, String url,InboundCall inboundCall){
		Voice voice=new Voice(url,false);
		voiceService.addVoice(voice);
		VoiceMessage voiceMessage=new VoiceMessage(userPhoneNumberService.getUserPhoneNumber(userPhoneNumber).getUser(), broadcast, group,mode, type, response, null, voice, inboundCall);
		messageService.addMessage(voiceMessage);
	}
	
	
	public void addTextMessage(User user, Group group,String mode, String type, boolean response,String textContent, Timestamp textTime){
		TextMessage textMessage=new TextMessage(user, null, group,mode, type, response, null, textContent, textTime);
		messageService.addMessage(textMessage);
	}
	
	public void addBinaryMessage(User user, Group group,String mode, String type, boolean response, Timestamp time){
		BinaryMessage binaryMessage=new BinaryMessage(user, null,group, time, mode, type, response, null);
		messageService.addMessage(binaryMessage);
	}
	
	public void addBinaryMessage(String userPhoneNumber,Broadcast broadcast , Group group,String mode, String type, boolean response, Timestamp time){
		BinaryMessage binaryMessage=new BinaryMessage(userPhoneNumberService.getUserPhoneNumber(userPhoneNumber).getUser(), null,group, time, mode, type, response, null);
		messageService.addMessage(binaryMessage);
	}
}
