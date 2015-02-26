package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.UserPhoneNumber;

public interface UserPhoneNumberRepository extends JpaRepository<UserPhoneNumber, String> {

}
