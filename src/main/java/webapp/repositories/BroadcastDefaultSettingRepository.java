package webapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import webapp.entities.BroadcastDefaultSetting;

public interface BroadcastDefaultSettingRepository extends JpaRepository<BroadcastDefaultSetting, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN_OR_PUBLISHER'+returnObject.organization.organizationId)")
	@Override
	public BroadcastDefaultSetting findOne(Integer id);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.organizationId)")
	@Override
	public List<BroadcastDefaultSetting> findAll();

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.organizationId)")
	@Override
	public Page<BroadcastDefaultSetting> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.organizationId)")
	@Override
	public List<BroadcastDefaultSetting> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#setting.organization.organizationId)")
	@Override
	public <S extends BroadcastDefaultSetting> S save(S setting);

	@PreAuthorize("hasRole('ADMIN'+#setting.organization.organizationId)")
	@Override
	public void delete(BroadcastDefaultSetting setting);

	/*
	 * Search functions
	 */

}
