website.controller("UserCtrl", function($window, $resource, $scope, $route, AddUserView, GetUser, RemoveUserView, AddGroupMembership, AddUserPhoneNumber) {
	$scope.addUserView = function(user, userPhoneNumber) {
		
		$scope.userView = new AddUserView();
		$scope.userView.user = user;
		$scope.userView.phone = userPhoneNumber;
		$scope.userView.role = "";
		
		$scope.groupId = $("#groupId").val();
		AddUserView.save({groupId: $scope.groupId}, $scope.userView, function(userObject) {
				console.log(userObject);
		}); 
	};
	
	$scope.removeUserView = function(userId){
		$scope.userView = new RemoveUserView();
		$scope.userView.$update({id:userId},function(){});
	};
	
	$scope.addGroupMembership = function(data){
		$scope.groupMembership = new AddGroupMembership();
		$scope.groupMembership.group = data.group;
		$scope.groupMembership.user = data.user;
		AddGroupMembership.save($scope.groupMembership,function(success){
			
			alert("Group for User has been updated.");
		}, function(error){
			if(error.status == "409")
			alert("User already exists in this group.");
		});
	};
	
	$scope.addUserPhoneNumber = function(data){
		$scope.userPhoneNumber = new AddUserPhoneNumber();
		$scope.userPhoneNumber.user = data.user;
		$scope.userPhoneNumber.phoneNumber = data.phoneNumber;
		$scope.userPhoneNumber.primary = data.primary;
		AddUserPhoneNumber.save($scope.userPhoneNumber,function(success){
			alert("Phone Number has been added.");
		}, function(error){
			if(error.status == "409")
			alert("Duplicate Phone Number");
		});
	};
	
	$scope.reload = function(){
		setTimeout(window.location.reload.bind(window.location),2000);
	};
});

$("#page-content").on("click", "#add-new-group-user", function (e) {
	
	e.preventDefault();
	/* Get values to generate orderItem objects from modal */
	var userName = $.trim($("#newGroupUserName").val());
	var userEmail = $.trim($("#newGroupUserEmail").val());
	if(userEmail == ""){
		userEmail = null;
	}
	var userAddress = $("#newGroupUserAddress").val();
	var userPrimaryPhoneNumber = $("#newGroupUserPrimaryPhoneNumber").val();
	
	
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
	angular.element($("#add-new-group-user")).scope().reload();
});

$("#page-content").on("click", ".remove-group-user", function (e) {
	var id = $(this).attr("data-value");
	console.log(id);
	
	$("#delete-group-user").val(id);
});

$("#page-content").on("click", ".add-group-user-to-new-group", function (e) {
	var id = $(this).attr("data-value");
	console.log(id);
	
	$("#add-group-user-to-new-group").val(id);
});

$("#page-content").on("click", "#delete-group-user", function (e) {
	var id = $(this).val();
	console.log(id);
	
	angular.element($("#delete-group-user")).scope().removeUserView(id);
	$('#delete-group-user-modal').modal('toggle');
	angular.element($("#delete-group-user")).scope().reload();
	
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
	
	data = {};
	data.phoneNumber = phoneNumber;
	data.user = "user/" + id;
	data.primary = false;
	
	console.log(data);
	
	angular.element($("#add-group-user-phone-number")).scope().addUserPhoneNumber(data);
	$('#add-group-user-phone-number-modal').modal('toggle');
	angular.element($("#add-group-user-phone-number")).scope().reload();
});

