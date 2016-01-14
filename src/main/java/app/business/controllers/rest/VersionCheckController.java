package app.business.controllers.rest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.data.repositories.VersionCheckRepository;
import app.entities.VersionCheck;

@RestController
@RequestMapping("/api")
public class VersionCheckController {
	@Autowired
	VersionCheckRepository versionCheckRepository;
	
	@RequestMapping(value = "/versioncheck",method = RequestMethod.GET)
	public String checkVersion (@RequestParam(value="version")String version) {
		int id=1;
		float appVersion = Float.parseFloat(version);
		JSONObject responseJsonObject = new JSONObject();
		VersionCheck currentVersion = versionCheckRepository.findOne(id);
		float curVersion = currentVersion.getVersion();
		int curMandatory = currentVersion.getMandatory();
		if (curVersion == appVersion) {
			try {
				responseJsonObject.put("response", "0");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if ((curVersion > appVersion) && (curMandatory == 0) ) {
			try {
				responseJsonObject.put("response", "1");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else if ((curVersion > appVersion) && (curMandatory == 1) ) {
			try {
				responseJsonObject.put("response", "2");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return responseJsonObject.toString();
	}
	
	
}
