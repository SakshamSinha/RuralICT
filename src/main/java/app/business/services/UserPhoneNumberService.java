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
	public void addUserPhoneNumber(UserPhoneNumber userPhoneNumber) {
			userPhoneNumberRepository.save(userPhoneNumber);
	}
	
	/*
	 * delete a userPhoneNumber from database
	 */
	public void removeUserPhoneNumber(UserPhoneNumber userPhoneNumber) {
			
		userPhoneNumberRepository.delete(userPhoneNumber);
	}
	
	/*
	 * Delete all numbers corresponding to a user
	 */
	public void removeUserPhoneNumber(User user) {
			List<UserPhoneNumber> userPhoneNumberList = this.getAllUserPhoneNumberList(user);
			
			for(UserPhoneNumber userPhoneNumber: userPhoneNumberList) {	
				this.removeUserPhoneNumber(userPhoneNumber);
		}
	}
	
	/*
	 * Get UserPhoneNumber object by phoneNumber
	 */
	public UserPhoneNumber getUserPhoneNumber(String phoneNumber) {
		
		return userPhoneNumberRepository.findOne(phoneNumber);
	}
}

