package app.business.services;

import java.util.ArrayList;
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
	public OrganizationMembership getOrganizationMembership(User user, Organization organization){
	
		List<OrganizationMembership> organizationMembershipList =  origanizationMembershipRepository.findAll();
		
		for(OrganizationMembership organizationMembership : organizationMembershipList){
			
			if(organizationMembership.getUser().equals(user) &&  organizationMembership.getOrganization().equals(organization)){
				
				return organizationMembership;
			}
		}
		return null;
	}
	
	public List<OrganizationMembership> getOrganizationMembershipList(User user){
		
		List<OrganizationMembership> userMembershipList = new ArrayList<OrganizationMembership>();
		
		List<OrganizationMembership> organizationMembershipList =  origanizationMembershipRepository.findAll();
		
		for(OrganizationMembership organizationMembership : organizationMembershipList){
			
			if(organizationMembership.getUser().equals(user)){
				
				userMembershipList.add(organizationMembership);
			}
		}
		return userMembershipList;
	}
	
	public List<OrganizationMembership> getOrganizationMembershipList(){
		
		return origanizationMembershipRepository.findAll();
	}
	
	public List<OrganizationMembership> getOrganizationMembershipList(Organization organization){
		
		List<OrganizationMembership> userMembershipList = new ArrayList<OrganizationMembership>();
		
		List<OrganizationMembership> organizationMembershipList =  origanizationMembershipRepository.findAll();
		
		for(OrganizationMembership organizationMembership : organizationMembershipList){
			
			if(organizationMembership.getOrganization().equals(organization)){
				
				userMembershipList.add(organizationMembership);
			}
		}
		return userMembershipList;
	}
	
	public List<OrganizationMembership> getAdminMembershipList(User user){
		
		List<OrganizationMembership> userMembershipList = new ArrayList<OrganizationMembership>();
		
		List<OrganizationMembership> organizationMembershipList =  origanizationMembershipRepository.findAll();
		
		for(OrganizationMembership organizationMembership : organizationMembershipList){
			
			if(organizationMembership.getUser().equals(user) && organizationMembership.getIsAdmin()){
				
				userMembershipList.add(organizationMembership);
			}
		}
		return userMembershipList;
	}
	
	public List<OrganizationMembership> getAdminMembershipList(Organization organization){
		
		List<OrganizationMembership> userMembershipList = new ArrayList<OrganizationMembership>();
		
		List<OrganizationMembership> organizationMembershipList =  origanizationMembershipRepository.findAll();
		
		for(OrganizationMembership organizationMembership : organizationMembershipList){
			
			if(organizationMembership.getOrganization().equals(organization) && organizationMembership.getIsAdmin()){
				
				userMembershipList.add(organizationMembership);
			}
		}
		return userMembershipList;
	}
}
