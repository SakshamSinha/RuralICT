package app.business.services.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import app.data.repositories.MessageRepository;
import app.entities.Group;
import app.entities.Order;
import app.entities.broadcast.VoiceBroadcast;
import app.entities.message.Message;

@Service
public class MessageService {

	@Autowired
	MessageRepository messageRepository;

	/*
	 * Returns messages with 'Yes' Response for a group  
	 */
	public HashMap<String,ArrayList<Message>> getPositiveResponseList(Group group, String format) {
		ArrayList<Message> message= (ArrayList<Message>) messageRepository.findByGroupAndResponseAndTypeAndFormat(group, true, "response", format, new Sort(Direction.DESC, "time"));
		HashMap<String, ArrayList<Message>> map1 = new HashMap<String, ArrayList<Message>>();
		ArrayList<Message> temp = new ArrayList<Message>();
		for(Message m1 : message){
			String url=((VoiceBroadcast)m1.getBroadcast()).getVoice().getUrl();
			if(map1.containsKey(url))
			{
				temp=map1.get(url);
				temp.add(m1);
				map1.put(url, temp);
			}
			else
			{
				temp.add(m1);
				map1.put(url, temp);
			}
			temp=new ArrayList<Message>();
		}
		return map1;
	}
	public List<Message> getPositiveResponseListType(Group group, String format) {
		List<Message> message= (ArrayList<Message>) messageRepository.findByGroupAndResponseAndTypeAndFormat(group, true, "response", format, new Sort(Direction.DESC, "time"));
		return message;
	}
	
	public List<Message> getNegativeResponseListType(Group group, String format) {
		List<Message> message= (ArrayList<Message>) messageRepository.findByGroupAndResponseAndTypeAndFormat(group, false, "response", format, new Sort(Direction.DESC, "time"));
		return message;
	}
	
	public List<Message> getResponseListType(Group group, String format) {
		List<Message> message= (ArrayList<Message>) messageRepository.findByGroupAndTypeAndFormat(group, "response", format, new Sort(Direction.DESC, "time"));
		return message;
	}
	
	public HashMap<String, Integer> getPositiveResponseListCount(Group group, String format)
	{
		List<Object[]> response= messageRepository.countByGroupAndResponseAndTypeAndFormat(group.getGroupId(), true, "response", format);
		HashMap<String,Integer> map1 = new HashMap<String,Integer>();
		for(int i=0;i<response.size();i++)
			map1.put(response.get(i)[1].toString(),Integer.parseInt(response.get(i)[0].toString()));
		return map1;
	}
	public HashMap<String, Integer> getNegativeResponseListCount(Group group, String format)
	{
		List<Object[]> response= messageRepository.countByGroupAndResponseAndTypeAndFormat(group.getGroupId(), false, "response", format);
		HashMap<String,Integer> map1 = new HashMap<String,Integer>();
		for(int i=0;i<response.size();i++)
			map1.put(response.get(i)[1].toString(),Integer.parseInt(response.get(i)[0].toString()));
		return map1;
	}
	/*
	 * Returns messages with 'No' Response for a group  
	 */
	public HashMap<String,ArrayList<Message>> getNegativeResponseList(Group group, String format) {
		ArrayList<Message> message= (ArrayList<Message>) messageRepository.findByGroupAndResponseAndTypeAndFormat(group, false, "response", format, new Sort(Direction.DESC, "time"));
		HashMap<String, ArrayList<Message>> map1 = new HashMap<String, ArrayList<Message>>();
		ArrayList<Message> temp = new ArrayList<Message>();
		for(Message m1 : message){
			String url=((VoiceBroadcast)m1.getBroadcast()).getVoice().getUrl();
			if(map1.containsKey(url))
			{
				temp=map1.get(url);
				temp.add(m1);
				map1.put(url, temp);
			}
			else
			{
				temp.add(m1);
				map1.put(url, temp);
			}
			temp=new ArrayList<Message>();
		}
		return map1;
	}

	/*
	 * Returns messages which are of type 'response' for a group
	 */
	public HashMap<String,ArrayList<Message>> getResponseList(Group group, String format) {
		ArrayList<Message> message= (ArrayList<Message>) messageRepository.findByGroupAndTypeAndFormat(group, "response", format, new Sort(Direction.DESC, "time"));
		HashMap<String, ArrayList<Message>> map1 = new HashMap<String, ArrayList<Message>>();
		ArrayList<Message> temp = new ArrayList<Message>();
		for(Message m1 : message){
			String url=((VoiceBroadcast)m1.getBroadcast()).getVoice().getUrl();
			if(map1.containsKey(url))
			{
				temp=map1.get(url);
				temp.add(m1);
				map1.put(url, temp);
			}
			else
			{
				temp.add(m1);
				map1.put(url, temp);
			}
			temp=new ArrayList<Message>();
		}
		return map1;
	}

	/*
	 * Return messages which are of type 'feedback' for a group
	 */
	public List<Message> getFeedbackList(Group group, String format) {
		return messageRepository.findByGroupAndTypeAndFormat(group, "feedback", format, new Sort(Direction.DESC, "time"));
	}

	/*
	 * Returns messages which are of type 'order' for a group
	 */
	public List<Message> getOrderList(Group group, String format) {
		return messageRepository.findByGroupAndTypeAndFormat(group, "order", format, new Sort(Direction.DESC, "time"));
	}

	/*
	 * Adds a new message to database
	 */
	public void addMessage(Message message){
		messageRepository.save(message);
	}

	/*
	 * Removes a message from the database
	 */
	public void removeMessage(Message message){
		messageRepository.delete(message);
	}				

	/*
	 * Returns message according to the messageID
	 */
	public Message getMessage(int messageId){
		return messageRepository.findOne(messageId);
	}

	public Message updateMessageComment(String comment, Message message){
		message.setComments(comment);
		return messageRepository.save(message);
	}

	/*
	 * Returns all messages for a group 
	 */
	public List<Message> getAllMessageList(Group group){
		return messageRepository.findByGroup(group);
	}

	/*
	 * Returns messages of a given format for a group 
	 */
	public List<Message> getMessageListByFormat(Group group, String format){
		return messageRepository.findByGroupAndFormat(group, format);
	}

	public List<Message> getMessageListByModeAndFormat(Group group, String mode,String format){
		return messageRepository.findByGroupAndModeAndFormat(group, mode, format);
	}
	
	/*
	 * Returns messages of a given order status for a group 
	 */
	public List<Message> getMessageListByOrderStatus(Group group, String format, String status){
		return messageRepository.findByGroupAndFormatAndOrder_Status(group, format, status, new Sort(Direction.DESC, "time"));
	}

	public List<Message> getRejectedTextMessageList(Group group) {
		return getMessageListByOrderStatus(group, "text", "rejected");
	}
	
	public Message getMessageFromOrder(Order order) {
		return messageRepository.findByOrder(order);
	
	}
}