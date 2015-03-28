package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.BroadcastSchedule;

public interface BroadcastScheduleRepository extends JpaRepository<BroadcastSchedule, Integer> {

}
