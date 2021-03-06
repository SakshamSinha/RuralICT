package app.telephony.config;

import in.ac.iitb.ivrs.telephony.base.config.IVRConfigs;

/**
 * Holds all the configuration for this application. 
 */
public class Configs extends IVRConfigs {

	/**
	 * Telephony-specific configuration.
	 */
	public static class Telephony {
		/**
		 * KooKoo application URL for Outbound Calls.
		 */
		public static final String OUTBOUND_APP_URL = "http://ruralict.cse.iitb.ac.in/ruralict/BroadcastCallHandler";
		/**
		 * KooKoo application URL.
		 */
		public static final String APP_URL = "http://ruralict.cse.iitb.ac.in/ruralict/CallHandler";
		/**
		 * The IVR number being used for this application. Outbound calls will be made from this number.
		 */
		public static final String IVR_NUMBER = "912233578383";
		/**
		 * The Text-to-speech speed for KooKoo.
		 */
		public static final int TTS_SPEED = 4;
		/**
		 * The number of milliseconds to wait for DTMF input.
		 */
		public static final int DTMF_TIMEOUT = 7 * 1000;
		/**
		 * The seconds of silence before which an ongoing recording is accepted.
		 */
		public static final int RECORDING_SILENCE = 5;
		/**
		 * The maximum duration of a recording in seconds.
		 */
		public static final int MAX_RECORDING_DURATION = 10 * 60;
		/**
		 * Maximum number of invalid tries before disconnection.
		 */
		public static final int MAX_INVALID_ATTEMPTS = 5;
	}

	/**
	 * Voice-specific configuration.
	 */
	public static class Voice {
		/**
		 * The root URL where all the voice files are.
		 */
		public static final String VOICE_DIR = "http://ruralict.cse.iitb.ac.in/Downloads/voices";
	}

}
