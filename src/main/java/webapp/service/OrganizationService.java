package webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.model.entities.Organization;
import webapp.model.repositories.OrganizationRepository;

@Service
public class OrganizationService {

	@Autowired
	OrganizationRepository repository;

	public List<Organization> getAllOrganizations() {
		return repository.findAll();
	}

	public Organization getOrganizationById(Integer id) {
		return repository.findOne(id);
	}

	public Organization getOrganizationByAbbreviation(String abbreviation) {
		return repository.findByAbbreviation(abbreviation);
	}

	public void saveOrganization(Organization org) {
		repository.save(org);
	}

	public void addOrganization(Organization org, Integer parentId) {
		Organization parentOrg = getOrganizationById(parentId);
		if (repository.exists(org.getOrganizationId()))
			throw new IllegalArgumentException("Duplicate org ID");
		if (parentId != null && parentOrg == null)
			throw new IllegalArgumentException("Invalid parent ID");
		org.setParentOrganization(parentOrg);
		saveOrganization(org);
	}

}
