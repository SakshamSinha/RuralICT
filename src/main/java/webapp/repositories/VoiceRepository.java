package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.Voice;

public interface VoiceRepository extends JpaRepository<Voice, Integer> {
	/*
	 * Search functions
	 */

}
