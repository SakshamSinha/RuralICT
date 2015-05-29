package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.UserPhoneNumberRepository;
import app.entities.User;
import app.entities.UserPhoneNumber;

@Service
public class UserPhoneNumberService {
	
	@Autowired
	UserPhoneNumberRepository userPhoneNumberRepository;

	/*
	 * Get user phone number only if primary is set as true.
	 */
	public UserPhoneNumber getUserPrimaryPhoneNumber(User user){
		
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
	
	/*
	 * Set phone number as primary
	 * Kept private so that no controller or service can use this method from outside.
	 */
	private void setPrimary(UserPhoneNumber userPhoneNumber) {
		
		userPhoneNumber.setPrimary(true);
		userPhoneNumberRepository.save(userPhoneNumber);
	}
	
	/*
	 * unset phone number as primary
	 */
	public void unsetPrimary(UserPhoneNumber userPhoneNumber) {
		
		userPhoneNumber.setPrimary(false);
		userPhoneNumberRepository.save(userPhoneNumber);
	}
	
	/*
	 * unset all phone numbers as primary for user
	 */
	public void unsetPrimaryPhoneNumberByUser(User user) {
		
		UserPhoneNumber userPhoneNumber = this.getUserPrimaryPhoneNumber(user);
		userPhoneNumber.setPrimary(false);
		userPhoneNumberRepository.save(userPhoneNumber);
	
	}
	
	public void setPrimaryPhoneNumberByUser(User user, UserPhoneNumber userPhoneNumber) {
		
		UserPhoneNumber currentPrimary = this.getUserPrimaryPhoneNumber(user);
		userPhoneNumber.setPrimary(false);
		userPhoneNumberRepository.save(currentPrimary);
		userPhoneNumber.setPrimary(true);
		userPhoneNumberRepository.save(userPhoneNumber);
	
	}
}


