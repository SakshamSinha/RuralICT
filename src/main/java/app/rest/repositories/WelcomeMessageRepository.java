package app.rest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.WelcomeMessage;

public interface WelcomeMessageRepository extends JpaRepository<WelcomeMessage, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('MEMBER'+returnObject.organization.abbreviation)")
	@Override
	public WelcomeMessage findOne(Integer id);

	@PostFilter("hasRole('MEMBER'+filterObject.organization.abbreviation)")
	@Override
	public List<WelcomeMessage> findAll();

	@PostFilter("hasRole('MEMBER'+filterObject.organization.abbreviation)")
	@Override
	public Page<WelcomeMessage> findAll(Pageable pageable);

	@PostFilter("hasRole('MEMBER'+filterObject.organization.abbreviation)")
	@Override
	public List<WelcomeMessage> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#message.organization.abbreviation)")
	@Override
	public <S extends WelcomeMessage> S save(@Param("message") S message);

	@PreAuthorize("hasRole('ADMIN'+#message.organization.abbreviation)")
	@Override
	public void delete(@Param("message") WelcomeMessage message);

	/*
	 * Search functions
	 */

}
