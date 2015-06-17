package app;

import static app.matcher.BeanMatcher.has;
import static app.matcher.BeanPropertyMatcher.property;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import app.data.repositories.GroupRepository;
import app.data.repositories.MessageRepository;
import app.entities.Group;
import app.entities.message.Message;


@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RuralIvrsApplication.class)
@DirtiesContext
@WebAppConfiguration
@Transactional
public class MessageRepositoryTest {
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	GroupRepository groupRepository;

    @SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getMessageListByGroupAndFormatTest(){
		Group group = groupRepository.findOne(2);
		List<Message> messages = messageRepository.findByGroupAndFormat(group,"voice");
		assertThat(messages.size(),is(3));
		assertThat(messages,contains(
				has(
                        property("messageId", is(3)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(1)),
                        property("format", is("voice"))
                ),
                has(
                        property("messageId", is(4)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(2)),
                        property("format", is("voice"))
                ),
                has(
                        property("messageId", is(7)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(3)),
                        property("format", is("voice"))
                
                )));
	 }
    
    @SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getMessageListByGroupAndTypeAndFormatTest(){
		Group group = groupRepository.findOne(2);
		List<Message> messages = messageRepository.findByGroupAndTypeAndFormat(group,"order","voice");
		assertThat(messages.size(),is(2));
		assertThat(messages,contains(
				has(
                        property("messageId", is(3)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(1)),
                        property("format", is("voice")),
                        property("type", is("order"))
                ),
                has(
                        property("messageId", is(7)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(3)),
                        property("format", is("voice")),
                        property("type", is("order"))
                )));
	 }
    
    @SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getMessageListByGroupAndFormatAndOrder_StatusTest(){
		Group group = groupRepository.findOne(2);
		List<Message> messages = messageRepository.findByGroupAndFormatAndOrder_Status(group,"text","Accept", null);
		assertThat(messages.size(),is(1));
		assertThat(messages,contains(
				has(
                        property("messageId", is(9)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(3)),
                        property("format", is("text")),
                        property("order.status", is("Accept"))
                
                )));
	 }
    
    @SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getMessageListByByGroupAndResponseAndTypeTest(){
		Group group = groupRepository.findOne(1);
		List<Message> messages = messageRepository.findByGroupAndResponseAndType(group,true,"feedback");
		assertThat(messages.size(),is(1));
		assertThat(messages,contains(
				has(
                        property("messageId", is(10)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(1)),
                        property("type", is("feedback")),
                        property("response", is(true))
                
                )));
	 }
    
    @SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getMessageListByByGroupTest(){
		Group group = groupRepository.findOne(1);
		List<Message> messages = messageRepository.findByGroup(group);
		assertThat(messages.size(),is(1));
		assertThat(messages,contains(
				has(
                        property("messageId", is(10)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(1))
                )));
	 }
}
