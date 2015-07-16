package app.business.controllers;
import javax.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/web/{org}")
public class ManageController {

	
	@RequestMapping(value="/manage/incomingCalls/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String manageIncomingCallsReports(@PathVariable String org, @PathVariable int groupId, Model model) {
		return "manageInboundCallReports";
	}
	@RequestMapping(value="/manage/outgoingCalls/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String manageOutgoingCallsReports(@PathVariable String org, @PathVariable int groupId, Model model) {
		return "manageOutboundCallReports";
	}

}
