package app.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.OrganizationRepository;
import app.entities.Group;
import app.entities.Organization;
import app.entities.OrganizationMembership;

@Service
public class OrganizationService {
	@Autowired
	OrganizationRepository organizationRepository;
	public Organization getOrganizationByAbbreviation(String org)
	{
		Organization organization = organizationRepository.findByAbbreviation(org);
		return organization;	
	}
	public List<OrganizationMembership> getOrganizationMembershipList(Organization organization) {
		return organization.getOrganizationMemberships();
	}
	public int getOrganizationId(Organization organization) {
		return organization.getOrganizationId();
	}
	public List<Group> getOrganizationGroupList(Organization organization){
		return (new ArrayList<Group>(organization.getGroups()));
		
	}

	

}
