package app.rest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.BroadcastRecipient;

public interface BroadcastRecipientRepository extends JpaRepository<BroadcastRecipient, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN_OR_PUBLISHER'+returnObject.broadcast.organization.abbreviation)")
	@Override
	public BroadcastRecipient findOne(Integer id);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.broadcast.organization.abbreviation)")
	@Override
	public List<BroadcastRecipient> findAll();

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.broadcast.organization.abbreviation)")
	@Override
	public Page<BroadcastRecipient> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.broadcast.organization.abbreviation)")
	@Override
	public List<BroadcastRecipient> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN_OR_PUBLISHER'+#recipient.broadcast.organization.abbreviation)")
	@Override
	public <S extends BroadcastRecipient> S save(S recipient);

	@PreAuthorize("hasRole('ADMIN_OR_PUBLISHER'+#recipient.broadcast.organization.abbreviation)")
	@Override
	public void delete(BroadcastRecipient recipient);

	/*
	 * Search functions
	 */

}
