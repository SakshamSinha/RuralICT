package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.BroadcastDefaultSetting;

public interface BroadcastDefaultSettingRepository extends JpaRepository<BroadcastDefaultSetting, Integer> {

}
