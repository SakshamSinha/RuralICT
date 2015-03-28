package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.WelcomeMessage;

public interface WelcomeMessageRepository extends JpaRepository<WelcomeMessage, Integer> {

}
