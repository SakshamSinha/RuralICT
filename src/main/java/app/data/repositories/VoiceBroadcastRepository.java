package app.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.broadcast.VoiceBroadcast;

public interface VoiceBroadcastRepository extends JpaRepository<VoiceBroadcast, Integer> 
{

}