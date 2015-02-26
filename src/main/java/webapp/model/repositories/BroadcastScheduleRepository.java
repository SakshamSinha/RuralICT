package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.BroadcastSchedule;

public interface BroadcastScheduleRepository extends JpaRepository<BroadcastSchedule, Integer> {

}
