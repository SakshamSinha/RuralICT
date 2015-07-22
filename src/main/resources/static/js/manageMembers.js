/**
 * This file contains all the functionalities related to users
 */


website.controller("UsersCtrl", function($scope, $http, $routeParams) {
	
	// Get Organization Abbreviation from Thymeleaf
	var abbr = $('#organizationAbbr').val();
	
	// By Default display all members
	$scope.selectedRole = "";
	
	// Initialize the table
	$http.get( API_ADDR + 'api/' + abbr + '/manageUsers/getUserList').
		success(function(data, status, headers, config) {

			// Store the data into Angular scope model variable
			$scope.manageUserItems = data;
		}).
		error(function(data, status, headers, config) {
			createAlert("Error Fetching Data","There was some error in response from the remote server.");
		});

	// Click event handler for the 'Add' Modal Button
	$scope.addNewUserModalButton = function() {

		// Check if required text fields are blank or not
		if(!$scope.inputUserName)
		{
			createAlert("Invalid Input","Please Enter a Name for the User !");
		}
		else if(!$scope.inputUserPhone)
		{
			createAlert("Invalid Input","Please Enter a Phone Number !");
		}
		else
		{
			// Get the attributes of the new user
			var newUserDetails = {};
			newUserDetails.name = $scope.inputUserName;
			newUserDetails.email = $scope.inputUserEmail;
			newUserDetails.phone = $scope.inputUserPhone;
			newUserDetails.role = "Member";                   // New User is by default a Member
			newUserDetails.address = $scope.inputUserAddress;
			
			if(normalizePhoneNumber(newUserDetails.phone) == false)
			{
				createAlert("Invalid Input","Please enter a valid phone number !");
			}
			else
			{
				// Normalize the phone number to database format
				newUserDetails.phone = normalizePhoneNumber(newUserDetails.phone);
				
				// Add the User as we have validated the number
				$http.post( API_ADDR + 'api/' + abbr + '/manageUsers/addNewUser', newUserDetails).
					success(function(data, status, headers, config) {
						
						if(!data)
						{
							createAlert("Invalid Input","The Entered Phone Number already exists in the database");
						}
						else
						{
							// Push the new object in the ng-repeat variable for for table
							// This Automatically updates the table
							$scope.manageUserItems.push(data);
		
							// Hide the modal dialog box after successful operation
							$('#add-new-user-modal').modal('hide');
							
							// Display modal conveying that the new user has been successfully added
							createAlert("Success","The new user was sucessfully added.");
							
							// Clear the contents of scope variables
							$scope.inputUserName = '';
							$scope.inputUserEmail = '';
							$scope.inputUserPhone = '';
							$scope.inputUserAddress = '';
						}
					}).
					error(function(data, status, headers, config) {
						createAlert("Error Adding Member","There was some error in response from the remote server.");
					});
			}
			
			
		}
	};

	// Utility functions used in ng-if to check the roles
	$scope.detectIfAdmin = function(manageUserItem) {
		if(manageUserItem)
		{
			if(this.manageUserItem.role.search("Admin") != -1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	};

	$scope.detectIfPublisher = function(manageUserItem) {
		if(manageUserItem)
		{
			if(this.manageUserItem.role.search("Publisher") != -1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	};

	// makeRole functions are called when user does not have the specified roles
	$scope.makeRoleAdmin = function($event, manageUserItem) {
		$event.preventDefault();

		var userDetails = {};
		userDetails.userid = this.manageUserItem.manageUserID;
		userDetails.addRole = "Admin";

		$http.post( API_ADDR + 'api/' + abbr + '/manageUsers/addUserRole', userDetails).
			success(function(data, status, headers, config) {

				var previousRole = manageUserItem.role;
				if(previousRole === "Member")
				{
					manageUserItem.role = "Admin";
				}
				else
				{
					manageUserItem.role = "Admin Publisher";
				}
			}).
			error(function(data, status, headers, config) {
				createAlert("Error Updating Member","There was some error in response from the remote server.");
			});
	};

	$scope.makeRolePublisher = function($event, manageUserItem) {
		$event.preventDefault();

		var userDetails = {};
		userDetails.userid = this.manageUserItem.manageUserID;
		userDetails.addRole = "Publisher";

		$http.post( API_ADDR + 'api/' + abbr + '/manageUsers/addUserRole', userDetails).
			success(function(data, status, headers, config) {

				var previousRole = manageUserItem.role;
				if(previousRole === "Member")
				{
					manageUserItem.role = "Publisher";
				}
				else
				{
					manageUserItem.role = "Admin Publisher";
				}
			}).
			error(function(data, status, headers, config) {
				createAlert("Error Updating Member","There was some error in response from the remote server.");
			});
	};

	// removeRole functions are called when we want to remove a specific role
	// they assume that the user is necessarily having roles admin or publisher or both
	$scope.removeRoleAdmin = function($event, manageUserItem) {
		$event.preventDefault();

		var userDetails = {};
		userDetails.userid = this.manageUserItem.manageUserID;
		userDetails.removeRole = "Admin";

		$http.post( API_ADDR  + 'api/' + abbr + '/manageUsers/removeUserRole', userDetails).
			success(function(data, status, headers, config) {

				var previousRole = manageUserItem.role;
				if(previousRole === "Admin")
				{
					manageUserItem.role = "Member";
				}
				else
				{
					manageUserItem.role = "Publisher";
				}
			}).
			error(function(data, status, headers, config) {
				createAlert("Error Updating Member","There was some error in response from the remote server.");
			});
	};

	$scope.removeRolePublisher = function($event, manageUserItem) {
		$event.preventDefault();

		var userDetails = {};
		userDetails.userid = this.manageUserItem.manageUserID;
		userDetails.removeRole = "Publisher";

		$http.post( API_ADDR  + 'api/' + abbr + '/manageUsers/removeUserRole', userDetails).
			success(function(data, status, headers, config) {

				var previousRole = manageUserItem.role;
				if(previousRole === "Publisher")
				{
					manageUserItem.role = "Member";
				}
				else
				{
					manageUserItem.role = "Admin";
				}
			}).
			error(function(data, status, headers, config) {
				createAlert("Error Updating Member","There was some error in response from the remote server.");
			});
	};

	// Function to handle events for edit user anchor button
	$scope.editRowUser = function($event, manageUserItem) {
		$event.preventDefault();

		$scope.editUserName = this.manageUserItem.name;
		$scope.editUserPhone = this.manageUserItem.phone;
		$scope.editUserAddress = this.manageUserItem.address;
		$scope.editUserEmail = this.manageUserItem.email;

		$('#edit-user-modal').modal('show');

		// click event handler for edit user modal save button
		$scope.editUserModalAnchorButton = function() {
			
			// Check if required text fields are blank or not
			if(!$scope.editUserName)
			{
				createAlert("Invalid Input","Please Enter a Name for the User !");
			}
			else if(!$scope.editUserPhone)
			{
				createAlert("Invalid Input","Please Enter a Phone Number !");
			}
			else
			{
				
				// Get the attributes of the new user
				var newUserDetails = {};
				newUserDetails.userid = manageUserItem.manageUserID;
				newUserDetails.name = $scope.editUserName;
				newUserDetails.email = $scope.editUserEmail;
				newUserDetails.phone = $scope.editUserPhone;
				newUserDetails.address = $scope.editUserAddress;
				
				if(normalizePhoneNumber(newUserDetails.phone) == false)
				{
					createAlert("Invalid Input","Please enter a valid phone number !");
				}
				else
				{
					// Normalize the phone number to database format
					newUserDetails.phone = normalizePhoneNumber(newUserDetails.phone);
					
					// If phone Number has been changed, update phone number as well as user details
					if(newUserDetails.phone != manageUserItem.phone)
					{
						$http.post( API_ADDR + 'api/' + abbr + '/manageUsers/editUserWithPhoneNumber', newUserDetails).
							success(function(data, status, headers, config) {
								
								if(data == "-1")
								{
									createAlert("Invalid Input","The Entered Phone Number already exists in the database");
								}
								else
								{
									manageUserItem.name = $scope.editUserName;
									manageUserItem.phone = newUserDetails.phone;  // Normalized phone number
									manageUserItem.address = $scope.editUserAddress;
									manageUserItem.email = $scope.editUserEmail;
			
									// Hide the edit user modal dialog box after successful operation
									$('#edit-user-modal').modal('hide');
									
									// Display modal conveying that the user details have been changed successfully
									createAlert("Success","The user details were successfully changed.");
									
									// clear the contents of scope variables
									$scope.editUserName = '';
									$scope.editUserEmail = '';
									$scope.editUserPhone = '';
									$scope.editUserAddress = '';
								}
		
							}).
							error(function(data, status, headers, config) {
								createAlert("Error Updating Member","There was some error in response from the remote server.");
							});
					}
					// Or else just update the user details
					else
					{
						$http.post( API_ADDR + 'api/' + abbr + '/manageUsers/editUserOnly', newUserDetails).
						success(function(data, status, headers, config) {
							
							manageUserItem.name = $scope.editUserName;
							manageUserItem.address = $scope.editUserAddress;
							manageUserItem.email = $scope.editUserEmail;
	
							// Hide the edit user modal dialog box after successful operation
							$('#edit-user-modal').modal('hide');
							
							// Display modal conveying that the user details have been changed successfully
							createAlert("Success","The user details were successfully changed.");
							
							// clear the contents of scope variables
							$scope.editUserName = '';
							$scope.editUserEmail = '';
							$scope.editUserPhone = '';
							$scope.editUserAddress = '';
	
						}).
						error(function(data, status, headers, config) {
							createAlert("Error Updating Member","There was some error in response from the remote server.");
						});
					}
					
				}
			}

		};

	};

	// Event handler for get user details anchor link
	$scope.getRowUserDetails = function($event, manageUserItem) {
		$event.preventDefault();

		var userid = manageUserItem.manageUserID;

		$http.post( API_ADDR  + 'api/' + abbr + '/manageUsers/getUserDetails', userid).
			success(function(data, status, headers, config) {

				$scope.userDetails = data;

				$('#get-user-details-modal').modal('show');

			}).
			error(function(data, status, headers, config) {
				createAlert("Error Fetching Data","There was some error in response from the remote server.");
			});
	};

});
