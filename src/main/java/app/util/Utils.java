package app.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.DefaultFFMPEGLocator;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import app.data.repositories.UserRepository;
import app.entities.User;
import app.security.AuthenticatedUser;


/**
 * Utilities for this application.
 */
public class Utils {
	
	/**
	 * Variable to store working path of voice files
	 */
	private final static String VOICE_DIR = "/home/ruralivrs/Ruralict/apache-tomcat-7.0.42/webapps/Downloads/voices";	
	private final static String DOWNLOAD_VOICE_DIR = "Downloads/voices/";
	private final static String IMAGE_DIR = "/home/ruralivrs/Ruralict/apache-tomcat-7.0.42/webapps/Downloads/images";	
	private final static String DOWNLOAD_IMAGE_DIR = "Downloads/images/";
	private final static String LOG_DIR = "/home/ruralivrs/Ruralict/apache-tomcat-7.0.42/webapps/Downloads/ruralictLogs/";
	private final static String DOWNLOAD_LOG_DIR = "Downloads/ruralictLogs/";
	private final static String WEBSITE_ADDRESS = "http://ruralict.cse.iitb.ac.in/";
	

	
	public static String getVoiceDir() {
		return VOICE_DIR;
	}
	
	public static String getVoiceDirURL() {
		return WEBSITE_ADDRESS + DOWNLOAD_VOICE_DIR;
	}

	public static String getImageDir() {
		return IMAGE_DIR;
	}
	
	public static String getImageDirURL() {
		return WEBSITE_ADDRESS + DOWNLOAD_IMAGE_DIR;
	}
	
	public static String getLogDir() {
		return LOG_DIR;
	}
	
	public static String getLogDirURL() {
		return WEBSITE_ADDRESS + DOWNLOAD_LOG_DIR;
	}
	/**
	 * Returns the UserDetails object set during authentication for the currently logged in user. No database lookup is
	 * done.
	 * @return The currently logged in user's AuthenticatedUser object.
	 */
	public static AuthenticatedUser getSecurityPrincipal() {
		return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	/**
	 * Returns the persistent user object from the database for the currently logged in user.
	 * @param userRepository The user repository for the user lookup.
	 * @return The currently logged in user's persistent User object.
	 */
	public static User getCurrentUser(UserRepository userRepository) {
		return userRepository.findOne(getSecurityPrincipal().getUserId());
	}
	
	public static File saveFile(String fileName, String directory, MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
 
				// Creating the directory to store file
				File dir = new File(directory);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File temp = new File(dir.getAbsolutePath() + File.separator + fileName);
				System.out.println(temp);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(temp));
				stream.write(bytes);
				stream.close();
				
				return temp;
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		else {
			System.out.println("File is Empty!");
			return null;
		}
	}
	
	public static File convertToKookooFormat(File source, File destination) {
		
		int bitRate = 128;
		int samplingRate = 8000;
		int channels = 1;
		
		try {
			AudioAttributes audio = new AudioAttributes();
			audio.setBitRate(new Integer(bitRate));
			audio.setChannels(new Integer(channels));
			audio.setSamplingRate(new Integer(samplingRate));
			
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setAudioAttributes(audio);
			attrs.setFormat("wav");
			
			Encoder encoder = new Encoder(new DefaultFFMPEGLocator());
			encoder.encode(source, destination, attrs);
			return destination;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	public static String downloadFile(String fileURL, String saveDir) throws IOException {
		
		/**
		 * Proxy settings for network 
		 */
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.cse.iitb.ac.in", 80));
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("amey15", "amey15*".toCharArray());
			}
		});
			
		URL url = new URL(fileURL);
		
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection(proxy);
		
		int responseCode = httpConn.getResponseCode();
		String location = httpConn.getHeaderField("Location");
		
		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			
			String contentType = httpConn.getContentType();
			String disposition = httpConn.getHeaderField("Content-Disposition");;
			int contentLength = httpConn.getContentLength();
	 
			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} 
			else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
			}
	 
			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			
			System.out.println("fileName = " + fileName);
	 
			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;
			 
			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	 
			int bytesRead = -1;
			byte[] buffer = new byte[1<<56];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
	 
			outputStream.close();
			inputStream.close();
	 
			System.out.println("File downloaded");
			return fileName;
		} 
		else if(location != null) {
			
			String fileName = Utils.downloadFile(location, saveDir);
			return fileName;
		}
		else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
			
		}
		httpConn.disconnect();
		return null;
	}

}
