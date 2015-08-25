website.controller("HomeCtrl", function($scope, $http, $routeParams) {
	// Initialize the table
	var abbr = $('#organizationAbbr').val();
	
	$http.get( API_ADDR + 'api/' + abbr + '/manageUsers/getUserApprovalList').
		success(function(data, status, headers, config) {

			// Store the data into Angular scope model variable
			$scope.userApprovalItems = data;
		}).
		error(function(data, status, headers, config) {
			createAlert("Error Fetching Data","There was some error in response from the remote server.");
		});
	$scope.reload = function(){
		setTimeout(function(){$http.get( API_ADDR + 'api/' + abbr + '/manageUsers/getUserApprovalList').
			success(function(data, status, headers, config) {

				// Store the data into Angular scope model variable
				$scope.userApprovalItems = data;
			}).
			error(function(data, status, headers, config) {
				createAlert("Error Fetching Data","There was some error in response from the remote server.");
			})},2000);
	}
	$scope.approveButton = function() {
		//$event.preventDefault();

		var userDetails = {};
		userDetails.userid = this.userApprovalItem.manageUserID;
		userDetails.phno = this.userApprovalItem.phone;
		$http.post( API_ADDR + 'web/' + abbr + '/homePage/approve', userDetails).
		success(function(data, status, headers, config) {
			createAlert("Approved","User approved, sms sent");
		}).
		error(function(data, status, headers, config) {
			createAlert("Error Updating Member","There was some error in response from the remote server.");
		});
		$scope.reload();
	}
	$scope.rejectButton = function() {
		var userDetails = {};
		userDetails.userid = this.userApprovalItem.manageUserID;
		userDetails.phno = this.userApprovalItem.phone;
		$http.post( API_ADDR + 'web/' + abbr + '/homePage/reject', userDetails).
		success(function(data, status, headers, config) {
			createAlert("Rejected","User rejected.");
		}).
		error(function(data, status, headers, config) {
			createAlert("Error Updating Member","There was some error in response from the remote server.");
		});
		$scope.reload();
	}
});