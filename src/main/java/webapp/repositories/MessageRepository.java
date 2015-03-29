package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.message.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	/*
	 * Default functions
	 */


	/*
	 * Search functions
	 */

}
