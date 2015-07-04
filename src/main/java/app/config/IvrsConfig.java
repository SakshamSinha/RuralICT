package app.config;

import in.ac.iitb.ivrs.telephony.base.config.IVRConfigs;

import org.springframework.stereotype.Component;

@Component
public class IvrsConfig {

	public IvrsConfig() {
		IVRConfigs.KooKoo.API_KEY = "KK4063496a1784f9f003768bd6e34c185b";
		IVRConfigs.Network.USE_PROXY = true;
		IVRConfigs.Network.PROXY_HOST = "netmon.iitb.ac.in";
		IVRConfigs.Network.PROXY_PORT = 80;
		IVRConfigs.Network.PROXY_USER = "";
		IVRConfigs.Network.PROXY_PASS = "";
	}

}
