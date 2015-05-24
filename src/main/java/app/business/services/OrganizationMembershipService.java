package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.OrganizationMembershipRepository;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;

@Service
public class OrganizationMembershipService {
	
	@Autowired
	OrganizationMembershipRepository origanizationMembershipRepository;
	
	
	/*
	 * Method to get one to one membership between user and organization
	 * Note here is that we can technically create many to many relation between these two objects.
	 * Do we have any mechanism for that? 
	 */
	public OrganizationMembership getUserOrganizationMembership(User user, Organization organization){
		
		return origanizationMembershipRepository.findByUserAndOrganization(user, organization).iterator().next();
	}
	
	public List<OrganizationMembership> getOrganizationMembershipList(User user){
		
		return user.getOrganizationMemberships();
	}
	
	public List<OrganizationMembership> getAllOrganizationMembershipList(){
		
		return origanizationMembershipRepository.findAll();
	}
	
	public List<OrganizationMembership> getOrganizationMembershipList(Organization organization){
		
		return organization.getOrganizationMemberships();
	}
	
	public void addOrganizationMembership(OrganizationMembership organizationMembership) {
		
		origanizationMembershipRepository.save(organizationMembership);
	}
	
	public void removeOrganizationMembership(OrganizationMembership organizationMembership) {
		
		origanizationMembershipRepository.delete(organizationMembership);
	}
	
	public OrganizationMembership getOrganizationMembership(int organizationMembershipId) {
		
		return origanizationMembershipRepository.findOne(organizationMembershipId);
	}
}
