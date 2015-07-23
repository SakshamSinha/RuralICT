
website.controller("BroadcastDefaultSettingsController",function($window, $scope, $resource, $location, UpdateBroadcastDefaultSettings, BroadcastDefaultSettingsCreate){
	
	var orgid = $('#organizationId').val();
	$scope.outgoingCheckBoxOptions = {
			"order" : false,
			"feedback" : false,
			"response" : false
	};    
	
	var outboundcall = UpdateBroadcastDefaultSettings.get(
		{id: orgid},
		function() {
		
		//intialize 'checkbox' elements from outgoing call settings
		$scope.outgoingCheckBoxOptions.order = outboundcall.askOrder;
		$scope.outgoingCheckBoxOptions.feedback = outboundcall.askFeedback;
		$scope.outgoingCheckBoxOptions.response = outboundcall.askResponse;

		},
		function(error){
			if(error.status == "404")
				{
					$scope.broadcastDefaultSettings = new BroadcastDefaultSettingsCreate();
					$scope.broadcastDefaultSettings.askOrder = 0;
					$scope.broadcastDefaultSettings.askFeedback = 0;
					$scope.broadcastDefaultSettings.askResponse = 0;
					$scope.broadcastDefaultSettings.organizationId = orgid;
					//broadcast default settings is not with respect to group. It is for whole organization.
					$scope.broadcastDefaultSettings.groupId = null;
					console.log($scope.broadcastDefaultSettings);
					BroadcastDefaultSettingsCreate.save($scope.broadcastDefaultSettings,function(){
						console.log("saved i dont know");
					},
					function(error){
						console.log("Error in saving.")
						console.log(error);
					});
					
				}
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


