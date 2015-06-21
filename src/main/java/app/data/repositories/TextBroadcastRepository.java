package app.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.broadcast.TextBroadcast;

public interface TextBroadcastRepository extends JpaRepository<TextBroadcast, Integer> 
{

}