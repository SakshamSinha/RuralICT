package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.BroadcastRecipient;

public interface BroadcastRecipientRepository extends JpaRepository<BroadcastRecipient, Integer> {

}
