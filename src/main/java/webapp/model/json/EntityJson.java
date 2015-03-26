package webapp.model.json;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Represents a JSON object corresponding to a database entity.
 *
 * @param <T> The entity type.
 * @param <R> The repository type for the entity.
 */
public interface EntityJson<T extends Serializable, R extends JpaRepository<T, ? extends Serializable>> extends Serializable {

	public void updateEntity(T object, R repository);

}
