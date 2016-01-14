package app.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.VersionCheck;


public interface VersionCheckRepository extends JpaRepository<VersionCheck, Integer> {


	@Override
	public VersionCheck findOne(Integer id);

}