package app.util;

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

import app.data.repositories.UserRepository;
import app.entities.User;
import app.security.AuthenticatedUser;

/**
 * Utilities for this application.
 */
public class Utils {

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
	
	public static String downloadFile(String fileURL, String saveDir) throws IOException {
		
		/*
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
