package app.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.Group;
import app.entities.LatestBroadcastableVoice;
import app.entities.Organization;

public interface LatestBroadcastableVoiceRepository extends JpaRepository<LatestBroadcastableVoice, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.organization.abbreviation)")
	@Override
	public LatestBroadcastableVoice findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public List<LatestBroadcastableVoice> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public Page<LatestBroadcastableVoice> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public List<LatestBroadcastableVoice> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#latestVoice.organization.abbreviation)")
	@Override
	public void delete(@Param("latestVoice") LatestBroadcastableVoice latestVoice);

	/*
	 * Search functions
	 */

	/**
	 * Use this when looking up the latest broadcastable voice for a group.
	 */
	public LatestBroadcastableVoice findByOrganizationAndGroup(Organization organization,Group group);

	/**
	 * Use this when looking up the latest broadcastable voice that may not belong to a group.
	 */
	public LatestBroadcastableVoice findTopByOrganizationAndGroupOrderByTimeDesc(Organization organization, Group group);

}
