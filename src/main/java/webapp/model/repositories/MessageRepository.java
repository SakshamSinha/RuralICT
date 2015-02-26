package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.message.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
