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
import app.entities.LatestRecordedVoice;
import app.entities.Organization;
import app.entities.Product;

public interface LatestRecordedVoiceRepository extends JpaRepository<LatestRecordedVoice, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.organization.abbreviation)")
	@Override
	public LatestRecordedVoice findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public List<LatestRecordedVoice> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public Page<LatestRecordedVoice> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public List<LatestRecordedVoice> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#latestVoice.organization.abbreviation)")
	@Override
	public void delete(@Param("latestVoice") LatestRecordedVoice latestVoice);
	
	@PreAuthorize("hasRole('ADMIN'+#latestVoice.organization.abbreviation)")
	@Override
	public <S extends LatestRecordedVoice> S save(@Param("latestVoice") S latestVoice);

	/*
	 * Search functions
	 */

	/**
	 * Use this when looking up the latest recorded voice for an organization.
	 */
	public LatestRecordedVoice findByOrganization(Organization organization);

}
