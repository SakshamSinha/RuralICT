package webapp.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import webapp.model.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	List<User> findByName(String name);
}
