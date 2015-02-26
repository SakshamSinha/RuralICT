package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.broadcast.Broadcast;

public interface BroadcastRepository extends JpaRepository<Broadcast, Integer> {

}
