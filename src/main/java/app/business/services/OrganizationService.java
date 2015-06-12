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
		return organizationRepository.findByAbbreviation(org);
	}
	
	public List<OrganizationMembership> getOrganizationMembershipList(Organization organization) {
		return organization.getOrganizationMemberships();
	}
	
	public List<Group> getOrganizationGroupList(Organization organization){
		return (new ArrayList<Group>(organization.getGroups()));
	}
	
	public void addOrganization(Organization organization){
		organizationRepository.save(organization);
	}
	
	public void removeOrganization(Organization organization){
		organizationRepository.delete(organization);
	}
	
	public void updateParentOrganization(Organization organization,Organization newParentOrganization){
		organization.getParentOrganization().removeSubOrganization(organization);
		newParentOrganization.addSubOrganization(organization);
	}
	
	public Organization getOrganizationById(int organizationId){
		return organizationRepository.findOne(organizationId);
	}
	
	public List<Organization> getAllOrganizationList(){
		return organizationRepository.findAll();
	}
	
	public List<Organization> getAllOrganizationListSortedByName(){
		/*
		 * The query can also be done by the statement new Sort(Sort.Direction.ASC, "name" in the repository as well. 
		 */
		return organizationRepository.findAllByOrderByNameAsc();
	}
	
	public Organization getOrganizationByIVRS(String ivrNumber){
		return organizationRepository.findByIvrNumber(ivrNumber);
	}
	
}

