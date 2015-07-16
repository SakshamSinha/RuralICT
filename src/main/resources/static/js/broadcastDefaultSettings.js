
website.controller("BroadcastDefaultSettingsController",function($window, $scope, $resource, $location, UpdateBroadcastDefaultSettings){
	
	var orgid = $('#organizationId').val();
	console.log(orgid);
	$scope.outgoingCheckBoxOptions = {
			"order" : false,
			"feedback" : false,
			"response" : false
	};    
	
	var outboundcall = UpdateBroadcastDefaultSettings.get({
		id: orgid
	}, function() {
		
		//intialize 'checkbox' elements from outgoing call settings
		$scope.outgoingCheckBoxOptions.order = outboundcall.askOrder;
		$scope.outgoingCheckBoxOptions.feedback = outboundcall.askFeedback;
		$scope.outgoingCheckBoxOptions.response = outboundcall.askResponse;

	});

	//click function for 'save details' button in outgoing call settings
	$scope.updateOutgoingCallOpt = function() {
	
		$scope.outboundcall = UpdateBroadcastDefaultSettings.get({
			id: orgid
		}, function() {
	
			//make changes in the $resource object
			$scope.outboundcall.askOrder = $scope.outgoingCheckBoxOptions.order;
			$scope.outboundcall.askFeedback = $scope.outgoingCheckBoxOptions.feedback;
			$scope.outboundcall.askResponse = $scope.outgoingCheckBoxOptions.response;
			//group can actually be removed as it is not required. By default it is being set to NULL.
			$scope.outboundcall.groupId = null;
			
			//finally update the database
			$scope.outboundcall.$update({
				id: orgid
			}, function() {
				createAlert("Settings Saved","Your Settings have been saved.");
			});
		});
	};
});


