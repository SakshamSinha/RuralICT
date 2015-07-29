website.controller("UserCtrl", function($window, $resource, $scope, $http, $route, AddUserView, GetUser, RemoveUserView, AddGroupMembership, RemoveGroupMembership, GetGroupMembershipByUserAndGroup, GetGroupMembershipsByUser, AddUserPhoneNumber) {
	$scope.addUserView = function(user, userPhoneNumber) {

		$scope.userView = new AddUserView();
		$scope.userView.user = user;
		$scope.userView.phone = userPhoneNumber;
		$scope.userView.role = "";

		$scope.groupId = $("#groupId").val();
		var abbr = $('#organizationAbbr').val();
		
		$http.post( API_ADDR + 'api/userViews/add/' + abbr + '/' + $scope.groupId, $scope.userView).
			success(function(data, status, headers, config) {
				
				if(data === false)
				{
					createAlert("Invalid Input","The Entered Phone Number already exists for another user. Please Enter a different phone number.");
				}
				else
				{
					createAlert("Success","Member was successfully added to the group.");
					$scope.reload();
				}
			}).
			error(function(data, status, headers, config) {
				createAlert("Error Adding Member","There was some error in response from the remote server.");
			});
		
	};

	$scope.removeMemberFromGroup = function(data){
		console.log(data);
		$scope.groupMembership = GetGroupMembershipByUserAndGroup.get(data, function(groupMembership){
			$scope.groupMembershipId = getId(groupMembership["_embedded"]["groupMemberships"][0]);
			RemoveGroupMembership.update({id: $scope.groupMembershipId});	
		}, function(error){console.log(error);});
		console.log($scope.groupMembership);
	};

	$scope.addGroupMembership = function(data){
		$scope.groupMembership = new AddGroupMembership();
		$scope.groupMembership.group = data.group;
		$scope.groupMembership.user = data.user;
		AddGroupMembership.save($scope.groupMembership,function(success){

			createAlert("User Group Updated","Group for User has been updated.")
		}, function(error){
			if(error.status == "409")
				createAlert("Error Updating User Group","User already exists in this group.");

		});
	};

	$scope.getUserGroups = function(data){
		$scope.groupList = [];
		GetGroupMembershipsByUser.get(data, function(groupMemberships){
			groupMemberships = groupMemberships["_embedded"]["groupMemberships"] 

			for(var count=0; count<groupMemberships.length;count++){
				var Group = $resource(groupMemberships[count]["_links"]["group"]["href"], {}, {
					update: {
						method: 'GET'
					}
				});

				/* This scope variable is kind of a flag to determine if all the requests are made */
				$scope.countOfCalls = 0;

				/* Send request for each product name */ 
				Group.get({}, function(group){
					$scope.groupList.push({id: getId(group), name: group.name});
				});
			}
		}, function(error){console.log(error);});
	};

	$scope.changeUserGroup = function(data){
		$scope.groupMembership = new AddGroupMembership();
		$scope.groupMembership.group = data.newGroup;
		$scope.groupMembership.user = "user/" + data.user;
		AddGroupMembership.save($scope.groupMembership,function(success){
			$scope.groupMembership = GetGroupMembershipByUserAndGroup.get({user:data.user, group:data.group}, function(groupMembership){
				console.log(groupMembership);
				console.log($scope.groupMembership);
				$scope.groupMembershipId = getId(groupMembership["_embedded"]["groupMemberships"][0]);
				RemoveGroupMembership.update({id: $scope.groupMembershipId}, function(success){
					createAlert("User Group Updated","Group for User has been updated.");
				});	
			});
			console.log($scope.groupMembership);
		}, function(error){
			if(error.status == "409")
				createAlert("Error Updating User Group","User already exists in this group.");

			return error;
		});
	};

	$scope.addUserPhoneNumber = function(data){
		$scope.userPhoneNumber = new AddUserPhoneNumber();
		$scope.userPhoneNumber.user = data.user;
		$scope.userPhoneNumber.phoneNumber = data.phoneNumber;
		$scope.userPhoneNumber.primary = data.primary;
		AddUserPhoneNumber.save($scope.userPhoneNumber,function(success){
			createAlert("Phone Number Added","Phone Number has been added.");
		}, function(error){
			if(error.status == "409")

				createAlert("Error Adding Phone Number","Duplicate Phone Number");

		});
	};

	$scope.reload = function(){
		setTimeout(window.location.reload.bind(window.location),2000);
	};
});

$("#page-content").on("click", "#add-new-group-user", function (e) {

	//e.preventDefault();
	/* Get values to generate orderItem objects from modal */
	var userName = $.trim($("#newGroupUserName").val());
	var userEmail = $.trim($("#newGroupUserEmail").val());
	
	console.log(userEmail);
	
	// validate email id
	if(!validateEmail(userEmail))
	{
		createAlert("Invalid Input","Invalid Email ID !");
		return;
	}
	
	if(userEmail == "")
	{
		userEmail = null;
	}
	
	var userAddress = $("#newGroupUserAddress").val();
	var userPrimaryPhoneNumber = $("#newGroupUserPrimaryPhoneNumber").val();


	if(userName == ""){
		createAlert("Invalid Input","Enter User Name");
		return;
	}

	userPrimaryPhoneNumber = normalizePhoneNumber(userPrimaryPhoneNumber);
	if(userPrimaryPhoneNumber == false){
		createAlert("Invalid Input","Invalid phone number.");
		return;
	}

	/* Create and add new row element for user */

	/* Create order item element and push it in the queue */
	var user={};
	user.name = userName;
	user.email = userEmail;
	user.address = userAddress;
	user.webLocale = "";
	user.callLocale = "";

	userPhoneNumber = {};
	userPhoneNumber.phoneNumber = userPrimaryPhoneNumber;
	userPhoneNumber.primary = true;
	userPhoneNumber.user = user;



	angular.element($("#add-new-group-user")).scope().addUserView(user, userPhoneNumber);
	$('#add-new-group-user-modal').modal('toggle');

});

$("#page-content").on("click", ".remove-group-user", function (e) {
	var id = $(this).attr("data-value");
	console.log(id);

	$("#remove-group-user").val(id);
});

$("#page-content").on("click", ".add-group-user-to-new-group", function (e) {
	var id = $(this).attr("data-value");
	console.log(id);

	$("#add-group-user-to-new-group").val(id);
});

$("#page-content").on("click", "#remove-group-user", function (e) {
	var id = $(this).val();
	var groupId = $("#groupId").val();

	var data = {};
	data.user = id;
	data.group = groupId;

	angular.element($("#remove-group-user")).scope().removeMemberFromGroup(data);
	$('#remove-group-user-modal').modal('toggle');
	angular.element($("#remove-group-user")).scope().reload();
	

});

$("#page-content").on("click", "#add-group-user-to-new-group", function (e) {
	var id = $(this).val();
	var groupId = $("#groupForGroupMember").val();
	console.log(id);

	data = {};
	data.group = "group/" + groupId;
	data.user = "user/" + id;

	console.log(data);

	angular.element($("#add-group-user-to-new-group")).scope().addGroupMembership(data);
	$('#add-group-user-to-new-group-modal').modal('toggle');
	angular.element($("#add-group-user-to-new-group")).scope().reload();
});

$("#page-content").on("click", ".add-group-user-phone-number", function (e) {
	var id = $(this).attr("data-value");
	console.log(id);

	$("#add-group-user-phone-number").val(id);
});

$("#page-content").on("click", "#add-group-user-phone-number", function (e) {
	var id = $(this).val();
	var phoneNumber = $("#groupUserNewPhoneNumber").val();
	console.log(id);

	phoneNumber = normalizePhoneNumber(phoneNumber);
	if(phoneNumber == false){
		createAlert("Invalid Input","Invalid phone number.");
		return;
	}

	data = {};
	data.phoneNumber = phoneNumber;
	data.user = "user/" + id;
	data.primary = false;

	console.log(data);

	angular.element($("#add-group-user-phone-number")).scope().addUserPhoneNumber(data);
	$('#add-group-user-phone-number-modal').modal('toggle');
	angular.element($("#add-group-user-phone-number")).scope().reload();
});

$("#page-content").on("click", ".show-user-groups", function (e) {
	var id = $(this).attr("data-value");
	var orgId = $("#organizationId").val();
	angular.element($("#add-group-user-phone-number")).scope().getUserGroups({user:id, organization:orgId});
});

$("#page-content").on("click", "#change-user-group", function (e) {
	var user = $(this).val();
	var group = $("#groupId").val();
	var newGroup = "group/" + $("#changeUserGroup").val();
	angular.element($("#add-group-user-phone-number")).scope().changeUserGroup({user:user, group:group, newGroup: newGroup});
	//angular.element($("#add-group-user-phone-number")).scope().reload();
});

$("#page-content").on("click", ".change-user-group", function (e) {
	var id = $(this).attr("data-value");
	console.log(id);

	$("#change-user-group").val(id);
});