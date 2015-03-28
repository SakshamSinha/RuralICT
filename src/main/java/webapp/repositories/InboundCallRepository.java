package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.InboundCall;

public interface InboundCallRepository extends JpaRepository<InboundCall, Integer> {

}
