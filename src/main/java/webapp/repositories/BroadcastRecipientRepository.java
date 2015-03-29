package webapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import webapp.entities.BroadcastRecipient;

public interface BroadcastRecipientRepository extends JpaRepository<BroadcastRecipient, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+returnObject.broadcast.organization.organizationId)")
	@Override
	public BroadcastRecipient findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+filterObject.broadcast.organization.organizationId)")
	@Override
	public List<BroadcastRecipient> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+filterObject.broadcast.organization.organizationId)")
	@Override
	public Page<BroadcastRecipient> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+filterObject.broadcast.organization.organizationId)")
	@Override
	public List<BroadcastRecipient> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#recipient.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+#recipient.broadcast.organization.organizationId)")
	@Override
	public <S extends BroadcastRecipient> S save(S recipient);

	@PreAuthorize("hasRole('ADMIN'+#recipient.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+#recipient.broadcast.organization.organizationId)")
	@Override
	public void delete(BroadcastRecipient recipient);

	/*
	 * Search functions
	 */

}
