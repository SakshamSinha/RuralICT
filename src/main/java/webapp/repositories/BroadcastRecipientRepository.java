package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.BroadcastRecipient;

public interface BroadcastRecipientRepository extends JpaRepository<BroadcastRecipient, Integer> {

}
