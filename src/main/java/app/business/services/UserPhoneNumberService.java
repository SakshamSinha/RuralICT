package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import app.data.repositories.UserPhoneNumberRepository;
import app.entities.User;
import app.entities.UserPhoneNumber;

public class UserPhoneNumberService {
	
	@Autowired
	UserPhoneNumberRepository userPhoneNumberRepository;

	/*
	 * Get user phone number only if primary is set as true.
	 */
	public UserPhoneNumber getUserPhoneNumberByPrimaryTrue(User user){
		
		return userPhoneNumberRepository.findByUserAndPrimaryTrue(user);
	}
	
	/*
	 * Get all phone Numbers of a  user.		
	 */
	public List<UserPhoneNumber> getAllUserPhoneNumberList(User user){
		
		return userPhoneNumberRepository.findByUser(user);
	}
	
	/*
	 * add a phone number to database
	 */
	public Boolean addUserPhoneNumber(UserPhoneNumber userPhoneNumber) {
		
		try {
			userPhoneNumberRepository.save(userPhoneNumber);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * delete a userPhoneNumber from database
	 */
	public Boolean removeUserPhoneNumber(UserPhoneNumber userPhoneNumber) {
		
		try {
			
			userPhoneNumberRepository.delete(userPhoneNumber);
		}
		catch(Exception e) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/*
	 * Delete all numbers corresponding to a user
	 */
	public Boolean removeUserPhoneNumber(User user) {
		
		try {
			List<UserPhoneNumber> userPhoneNumberList = this.getAllUserPhoneNumberList(user);
			
			for(UserPhoneNumber userPhoneNumber: userPhoneNumberList) {	
				this.removeUserPhoneNumber(userPhoneNumber);
			}
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Get UserPhoneNumber object by phoneNumber
	 */
	public UserPhoneNumber getUserPhoneNumber(String phoneNumber) {
		
		return userPhoneNumberRepository.findOne(phoneNumber);
	}
}

