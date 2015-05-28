package app.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.InboundCall;
import app.entities.Organization;
import app.entities.Voice;

public interface VoiceRepository extends JpaRepository<Voice, Integer> {
	/*
	 * Search functions
	 */
	public List<Voice> findByIsDownloaded(boolean isdownloaded);

}
