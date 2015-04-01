package app.rest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.message.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.broadcast.organization.abbreviation)")
	@Override
	public Message findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcast.organization.abbreviation)")
	@Override
	public List<Message> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.broadcast.organization.abbreviation)")
	@Override
	public Page<Message> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcast.organization.abbreviation)")
	@Override
	public List<Message> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#message.broadcast.organization.abbreviation)")
	@Override
	public <S extends Message> S save(S message);

	@PreAuthorize("hasRole('ADMIN'+#message.broadcast.organization.abbreviation)")
	@Override
	public void delete(Message message);

	/*
	 * Search functions
	 */

}
