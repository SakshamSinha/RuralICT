package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.OutboundCall;

public interface OutboundCallRepository extends JpaRepository<OutboundCall, Integer> {

}
