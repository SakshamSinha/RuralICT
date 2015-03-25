package webapp.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import webapp.model.entities.Organization;
import webapp.model.repositories.OrganizationRepository;

// TODO: Move all the business logic and interaction with data layer to the service layer!
@RestController
@RequestMapping("/api")
public class OrganizationController {

	@Autowired
	OrganizationRepository organizationRepo;

	@RequestMapping(method=RequestMethod.GET)
	public List<Organization> getAllOrganizations() {
		return organizationRepo.findAll();
	}

	@RequestMapping(value="/{orgId}", method=RequestMethod.GET)
	public ResponseEntity<Organization> getOrganization(@PathVariable int orgId) {
		Organization currentOrg = organizationRepo.findOne(orgId);
		if (currentOrg == null)
			return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Organization>(currentOrg, HttpStatus.OK);
	}

	@RequestMapping(value="/{orgId}", method=RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN'+#orgId)")
	public ResponseEntity<Organization> updateOrganization(@PathVariable int orgId, @RequestBody Organization updatedOrg) {
		Organization currentOrg = organizationRepo.findOne(orgId);
		if (currentOrg == null)
			return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND);
		if (currentOrg.getOrganizationId() != updatedOrg.getOrganizationId())
			return new ResponseEntity<Organization>(HttpStatus.CONFLICT);

		return new ResponseEntity<Organization>(organizationRepo.save(updatedOrg), HttpStatus.OK);
	}

	@RequestMapping(value="/{orgId}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#orgId)")
	public ResponseEntity<Organization> addChildOrganization(@PathVariable int orgId, @RequestBody Organization childOrg) {
		Organization currentOrg = organizationRepo.findOne(orgId);
		if (currentOrg == null)
			return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND);
		if (organizationRepo.findOne(childOrg.getOrganizationId()) != null)
			return new ResponseEntity<Organization>(HttpStatus.CONFLICT);

		childOrg.setParentOrganization(currentOrg);
		return new ResponseEntity<Organization>(organizationRepo.save(childOrg), HttpStatus.OK);
	}

}
