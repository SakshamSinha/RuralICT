package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.Voice;

public interface VoiceRepository extends JpaRepository<Voice, Integer> {

}
