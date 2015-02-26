package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.InboundCall;

public interface InboundCallRepository extends JpaRepository<InboundCall, Integer> {

}
