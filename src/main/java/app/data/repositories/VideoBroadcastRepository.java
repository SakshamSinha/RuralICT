package app.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.broadcast.VideoBroadcast;

public interface VideoBroadcastRepository extends JpaRepository<VideoBroadcast, Integer> 
{

}