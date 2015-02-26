package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.WelcomeMessage;

public interface WelcomeMessageRepository extends JpaRepository<WelcomeMessage, Integer> {

}
