package app.config;

import in.ac.iitb.ivrs.telephony.base.config.IVRConfigs;

import org.springframework.stereotype.Component;

@Component
public class IvrsConfig {

	public IvrsConfig() {
		IVRConfigs.KooKoo.API_KEY = "KK4063496a1784f9f003768bd6e34c185b"; // TODO: this should also not be in this file.
		IVRConfigs.Network.USE_PROXY = true;
		IVRConfigs.Network.PROXY_HOST = "netmon.iitb.ac.in";
		IVRConfigs.Network.PROXY_PORT = 80;

		// XXX: IMPORTANT: Make sure you fill these up before deploying on IIT servers!
		IVRConfigs.Network.PROXY_USER = "p11057";
		IVRConfigs.Network.PROXY_PASS = "rajani@";

		// TODO: Move all the configs in this file to an external file that is not committed to the repository, and is
		//       shared by the team locally. A sample empty version of that file can be provided in the repository.
	}

}
