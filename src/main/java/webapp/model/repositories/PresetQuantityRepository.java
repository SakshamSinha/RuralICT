package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.PresetQuantity;

public interface PresetQuantityRepository extends JpaRepository<PresetQuantity, Integer> {

}
