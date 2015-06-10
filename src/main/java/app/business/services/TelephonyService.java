package app.business.services;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.business.services.message.MessageService;
import app.entities.InboundCall;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.entities.Voice;
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
	
	public void addVoiceMessage(User user, String mode, String type, boolean response, String url, InboundCall inboundCall){
		Voice voice=new Voice(url,false);
		voiceService.addVoice(voice);
		VoiceMessage voiceMessage=new VoiceMessage(user, null, mode, type, response, null, voice, inboundCall);
		messageService.addMessage(voiceMessage);
	}
	
	public void addVoiceMessage(String userPhoneNumber, String mode, String type, boolean response, String url, InboundCall inboundCall){
		Voice voice=new Voice(url,false);
		voiceService.addVoice(voice);
		VoiceMessage voiceMessage=new VoiceMessage(userPhoneNumberService.getUserPhoneNumber(userPhoneNumber).getUser(), null, mode, type, response, null, voice, inboundCall);
		messageService.addMessage(voiceMessage);
	}
	
	public void addTextMessage(User user, String mode, String type, boolean response,String textContent, Timestamp textTime){
		TextMessage textMessage=new TextMessage(user, null, mode, type, response, null, textContent, textTime);
		messageService.addMessage(textMessage);
	}
	
	public void addBinaryMessage(User user, String mode, String type, boolean response, Timestamp time){
		BinaryMessage binaryMessage=new BinaryMessage(user, null, time, mode, type, response, null);
		messageService.addMessage(binaryMessage);
	}
	
	public void addBinaryMessage(String userPhoneNumber, String mode, String type, boolean response, Timestamp time){
		BinaryMessage binaryMessage=new BinaryMessage(userPhoneNumberService.getUserPhoneNumber(userPhoneNumber).getUser(), null, time, mode, type, response, null);
		messageService.addMessage(binaryMessage);
	}
}
