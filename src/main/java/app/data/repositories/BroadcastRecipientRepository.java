package app.data.repositories;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.BroadcastRecipient;
import app.entities.Organization;
import app.entities.User;
import app.entities.broadcast.Broadcast;

public interface BroadcastRecipientRepository extends JpaRepository<BroadcastRecipient, Integer> {
	/*
	 * Default functions
	 */

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

	@Override
	public <S extends BroadcastRecipient> S save(@Param("recipient") S recipient);

	@PreAuthorize("hasRole('ADMIN_OR_PUBLISHER'+#recipient.broadcast.organization.abbreviation)")
	@Override
	public void delete(@Param("recipient") BroadcastRecipient recipient);

	/*
	 * Search functions
	 */
	
	public BroadcastRecipient findTopByUserAndBroadcast_OrganizationOrderByBroadcast_BroadcastedTimeDesc(User user,Organization organization);
	public BroadcastRecipient findByBroadcast(Broadcast broadcast);
	public BroadcastRecipient findByUserAndBroadcast(User user, Broadcast broadcast);
}
