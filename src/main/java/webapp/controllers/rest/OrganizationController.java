package webapp.controllers.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import webapp.model.entities.Organization;
import webapp.model.json.OrganizationJson;
import webapp.model.repositories.OrganizationRepository;
import webapp.service.OrganizationService;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

	@Autowired
	OrganizationService service;

	@Autowired
	OrganizationRepository repository;

	@RequestMapping(method=RequestMethod.GET)
	public List<OrganizationJson> allOrganizations() {
		List<OrganizationJson> orgs = new ArrayList<OrganizationJson>();
		for (Organization org : service.getAllOrganizations()) {
			orgs.add(new OrganizationJson(org));
		}
		return orgs;
	}

	@RequestMapping(value="/{orgId}", method=RequestMethod.GET)
	public OrganizationJson showOrganization(@PathVariable int orgId) {
		return new OrganizationJson(service.getOrganizationById(orgId));
	}

	@RequestMapping(value="/{orgId}", method=RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN'+#orgId)")
	public OrganizationJson updateOrganization(@PathVariable int orgId, @RequestBody OrganizationJson updated) {
		Organization currentOrg = service.getOrganizationById(orgId);
		updated.updateEntity(currentOrg, repository);
		service.saveOrganization(currentOrg);
		return new OrganizationJson(currentOrg);
	}

	@RequestMapping(value="/{orgId}", method=RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#orgId)")
	public OrganizationJson addOrganization(@PathVariable int orgId, @RequestBody OrganizationJson childOrg) {
		Organization newOrg = new Organization();
		childOrg.updateEntity(newOrg, repository);
		service.addOrganization(newOrg, orgId);
		return new OrganizationJson(newOrg);
	}

}
