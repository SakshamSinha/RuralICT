package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * Get user phone number only if primary is set as false.
	 */
	public List<UserPhoneNumber> getUserSecondaryPhoneNumbers(User user){
		
		return userPhoneNumberRepository.findByUserAndPrimaryFalse(user);
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
	public UserPhoneNumber addUserPhoneNumber(UserPhoneNumber userPhoneNumber) {
			return userPhoneNumberRepository.save(userPhoneNumber);
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
	
	@Transactional
	public void setPrimaryPhoneNumberByUser(User user, UserPhoneNumber userPhoneNumber) {
		
		UserPhoneNumber currentPrimary = this.getUserPrimaryPhoneNumber(user);
		currentPrimary.setPrimary(false);
		userPhoneNumberRepository.save(currentPrimary);
		userPhoneNumber.setPrimary(true);
		userPhoneNumberRepository.save(userPhoneNumber);
	
	}
}


