package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.broadcast.Broadcast;

public interface BroadcastRepository extends JpaRepository<Broadcast, Integer> {

}
