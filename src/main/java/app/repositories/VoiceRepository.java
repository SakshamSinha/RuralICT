package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Voice;

public interface VoiceRepository extends JpaRepository<Voice, Integer> {
	/*
	 * Search functions
	 */

}
