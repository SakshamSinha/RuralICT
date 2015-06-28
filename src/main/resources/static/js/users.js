/**
 * This file contains all the functionalities related to users
 */


website.controller("UsersCtrl", function($scope, $http, $routeParams) {

	// get the current organization id
	//var orgid = document.getElementById("settings-page").getAttribute("orgid");
	//var outboundcallid = document.getElementById("settings-page").getAttribute("orgid");
	
	$scope.sortByKey = 'role';
	$scope.sortReverse = false;
	$scope.selectedRole = "";
	var currentUserID = "1";
	
	// Initialize the table
	$http.get('/api/iitb/manageUsers/getUserList').
	  success(function(data, status, headers, config) {
	    console.log("The data received is: " + data[0].name);
	    $scope.manageUserItems = data;
	  }).
	  error(function(data, status, headers, config) {
	  });
	
	// Click event handler for the 'Add' Modal Button
	$scope.addNewUserModalButton = function() {
		
		// Check if required text fields are blank or not
		if(!$scope.inputUserName)
		{
			alert("Please Enter a Name for the User !");
		}
		else if(!$scope.inputUserPhone)
		{
			alert("Please Enter a Phone Number !");
		}
		else
	    {
		// Get the attributes of the new user
		var newUserDetails = {};
		newUserDetails.name = $scope.inputUserName;
		newUserDetails.email = $scope.inputUserEmail;
		newUserDetails.phone = $scope.inputUserPhone;
		newUserDetails.role = "User"   // Modify the UI for multiple checkbox instead of select Element
		newUserDetails.address = $scope.inputUserAddress;
		
		
		
		console.log("The Userdetails object is as follows: " + newUserDetails);
		
		$http.post('/api/iitb/manageUsers/addNewUser', newUserDetails).
		  success(function(data, status, headers, config) {
			  console.log("Successfully received data from backend");
			  console.log("Received data is " + data);
			  
			  // Push the new object in the ng-repeat variable for for table
			  // This Automatically updates the table
			  $scope.manageUserItems.push(data);
			  
			  // Hide the modal dialog box after successful operation
			  $('#add-new-user-modal').modal('hide');
			  
		  }).
		  error(function(data, status, headers, config) {
		  });
	    }
	};
	
	// Utility functions used in ng-if to check the roles
	$scope.detectIfAdmin = function(manageUserItem) {    
	     if(this.manageUserItem.role.search("Admin") != -1)
    	 {
    	          return true;
    	 }
	     else 
	     {
	    	 	  return false;
	     }
	};
	
	$scope.detectIfPublisher = function(manageUserItem) {    
	     if(this.manageUserItem.role.search("Publisher") != -1)
   	 {
   	          return true;
   	 }
	     else 
	     {
	    	 	return false;
	     }
	};
	
	$scope.detectIfUser = function(manageUserItem) {    
	     if(this.manageUserItem.role.search("User") != -1)
  	 {
  	          return true;
  	 }
     else 
     {
    	 	return false;
     }
	};
	
	
	// make functions are called when user does not have the specified roles
	$scope.makeRoleAdmin = function($event, manageUserItem) {
		$event.preventDefault();
		console.log("You clicked Make Admin");
		console.log("User ID of the currently selected row is : " + this.manageUserItem.manageUserID);
		
		var userDetails = {};
		userDetails.userid = this.manageUserItem.manageUserID;
		userDetails.addRole = "Admin";
		
		$http.post('/api/iitb/manageUsers/addUserRole', userDetails).
		  success(function(data, status, headers, config) {
			  console.log("Successfully changed the role of the user to admin");
			  console.log("Received data is " + data);
			
			  var previousRole = manageUserItem.role;
				if(previousRole === "User")
				{
					manageUserItem.role = "Admin";
					console.log("User was a member and was changed to admin");
				}
				else
				{
					manageUserItem.role = "Admin Publisher";
					console.log("User was a Publisher and was changed to admin as well as Publisher");
				}
		  }).
		  error(function(data, status, headers, config) {
		  });			
	};
	
	$scope.makeRolePublisher = function($event, manageUserItem) {
		$event.preventDefault();
		console.log("You clicked Make Publisher");
		console.log("User ID of the currently selected row is : " + this.manageUserItem.manageUserID);
		
		var userDetails = {};
		userDetails.userid = this.manageUserItem.manageUserID;
		userDetails.addRole = "Publisher";
		
		$http.post('/api/iitb/manageUsers/addUserRole', userDetails).
		  success(function(data, status, headers, config) {
			  console.log("Successfully changed the role of the user to admin");
			  console.log("Received data is " + data);
			
			  var previousRole = manageUserItem.role;
				if(previousRole === "User")
				{
					manageUserItem.role = "Publiser";
					console.log("User was a member and was changed to admin");
				}
				else
				{
					manageUserItem.role = "Admin Publisher";
					console.log("User was a Admin and was changed to admin as well as Publisher");
				}
		  }).
		  error(function(data, status, headers, config) {
		  });			
	};
	
	$scope.makeRoleUser = function($event, manageUserItem) {
		$event.preventDefault();
		console.log("You clicked Make User");
console.log("User ID of the currently selected row is : " + this.manageUserItem.manageUserID);
		
		var userDetails = {};
		userDetails.userid = this.manageUserItem.manageUserID;
		userDetails.addRole = "User";
		
		$http.post('/api/iitb/manageUsers/addUserRole', userDetails).
		  success(function(data, status, headers, config) {
			  console.log("Successfully changed the role of the user to admin");
			  console.log("Received data is " + data);
			  
			  manageUserItem.role = "User";
			  
			  console.log("Current user was changed to normal user");
			
		  }).
		  error(function(data, status, headers, config) {
		  });			
	};
	
	// remove functions are called when we want to remove a specific role
	// they assume that the user is necessarily having roles admin or publisher or both
	$scope.removeRoleAdmin = function($event, manageUserItem) {
		$event.preventDefault();
		console.log("You clicked Make Admin");
		console.log("User ID of the currently selected row is : " + this.manageUserItem.manageUserID);
		
		var userDetails = {};
		userDetails.userid = this.manageUserItem.manageUserID;
		userDetails.removeRole = "Admin";
		
		$http.post('/api/iitb/manageUsers/removeUserRole', userDetails).
		  success(function(data, status, headers, config) {
			  console.log("Successfully changed the role of the user to admin");
			  console.log("Received data is " + data);
			
			  var previousRole = manageUserItem.role;
				if(previousRole === "Admin")
				{
					manageUserItem.role = "User";
					console.log("User was a admin and was changed to normal user");
				}
				else
				{
					manageUserItem.role = "Publisher";
					console.log("User was a admin and publisher and was changed to just a Publisher");
				}
		  }).
		  error(function(data, status, headers, config) {
		  });			
	};
	
	$scope.removeRolePublisher = function($event, manageUserItem) {
		$event.preventDefault();
		console.log("You clicked Make Admin");
		console.log("User ID of the currently selected row is : " + this.manageUserItem.manageUserID);
		
		var userDetails = {};
		userDetails.userid = this.manageUserItem.manageUserID;
		userDetails.removeRole = "Publisher";
		
		$http.post('/api/iitb/manageUsers/removeUserRole', userDetails).
		  success(function(data, status, headers, config) {
			  console.log("Successfully changed the role of the user to admin");
			  console.log("Received data is " + data);
			
			  var previousRole = manageUserItem.role;
				if(previousRole === "Publisher")
				{
					manageUserItem.role = "User";
					console.log("User was just a Publihser and was changed to normal user");
				}
				else
				{
					manageUserItem.role = "Admin";
					console.log("User was a Admin and publisher and was changed to just a Admin");
				}
		  }).
		  error(function(data, status, headers, config) {
		  });			
	};
	
	// Function to handle events for edit user anchor button
	$scope.editRowUser = function($event, manageUserItem) {
		$event.preventDefault();
		console.log("You clicked Edit User Anchor Link");
		
		
		$scope.editUserName = this.manageUserItem.name;
		$scope.editUserPhone = this.manageUserItem.phone;
		$scope.editUserAddress = this.manageUserItem.address;
		$scope.editUserEmail = this.manageUserItem.email;
		
		$('#edit-user-modal').modal('show');
		
		// click event handler for edit user modal save button
		$scope.editUserModalAnchorButton = function() {
			
			console.log("You clicked Edit user Model Dialog save button");
			
			// Check if required text fields are blank or not
			if(!$scope.editUserName)
			{
				alert("Please Enter a Name for the User !");
			}
			else if(!$scope.editUserPhone)
			{
				alert("Please Enter a Phone Number !");
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
			
			
			
			console.log("The Userdetails object is as follows: " + newUserDetails);
			
			$http.post('/api/iitb/manageUsers/editUser', newUserDetails).
			  success(function(data, status, headers, config) {
				  console.log("Successfully received data from backend");
				  console.log("Received data is " + data);
				  
				  manageUserItem.name = $scope.editUserName;
				  manageUserItem.phone = $scope.editUserPhone;
				  manageUserItem.address = $scope.editUserAddress;
				  manageUserItem.email = $scope.editUserEmail;
				  
				  // Hide the edit user modal dialog box after successful operation
				  $('#edit-user-modal').modal('hide');
				  
			  }).
			  error(function(data, status, headers, config) {
			  });
		    }
			
		};
		
	};
	
	// Event handler for get user details anchor link
	$scope.getRowUserDetails = function($event, manageUserItem) {
		$event.preventDefault();
		console.log("You clicked Get User Details Anchor Link");
		
		
		var userid = manageUserItem.manageUserID;
		
		$http.post('/api/iitb/manageUsers/getUserDetails', userid).
		  success(function(data, status, headers, config) {
			  console.log("Successfully received data from backend");
			  console.log("HashMap Received data.groups is " + data.groups);
			  
			  $scope.userDetails = data;
			  
			  $('#get-user-details-modal').modal('show');
			  
		  }).
		  error(function(data, status, headers, config) {
			  alert("There was some error in connecting to the remote server.");
		  });
	};

});
