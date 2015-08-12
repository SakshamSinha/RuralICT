/* Javascript file for the Text Broadcast View Controller */
website.controller("textBroadcastCtrl", function($scope, $http, $routeParams, TextBroadcast) {

	// Get the required variables from thymeleaf attributes
	var orgid = $('#organizationId').val();
	var abbr = $('#organizationAbbr').val();
	var groupid = document.getElementById("broadcast-text-ids").getAttribute("data-groupid");
	var publisherid = document.getElementById("broadcast-text-ids").getAttribute("data-publisherid");

	$scope.broadcastsleft=0;
	
	
	// Initialize the values for radio-buttons
	$scope.radioOptions = [false,false,false,false];
	
	// Function called when 'Send SMS' button is clicked
	$scope.saveTextBroadcast = function() {
		
		// Create TextBroadcast object
		$scope.broadcast = new TextBroadcast();
	   
		// Set the required attributes
		$scope.broadcast.organization = orgid;
		$scope.broadcast.group = groupid;
		$scope.broadcast.publisher = publisherid;
		$scope.broadcast.mode = "web";
		$scope.broadcast.askOrder = $scope.radioOptions[0];
		$scope.broadcast.askFeedback = $scope.radioOptions[1];
		$scope.broadcast.askResponse = $scope.radioOptions[2];
		$scope.broadcast.appOnly = false;
		$scope.broadcast.format = "text";
		$scope.broadcast.voice = null;
		$scope.broadcast.voiceBroadcastDraft = 0;
		$scope.broadcast.textContent = $scope.textContent;
		
		// Get the checked users in the checkboxes
		var userIds = '';
		$("#text-broadcast-user-list input:checked").each(function(){
			userIds = userIds + this.value + ',';
		});
		
		$scope.broadcast.userIds = userIds;
		if($scope.textContent==null||$scope.textContent=='')
		{
			createAlert("Warning!!","There is no text to broadcast");
		}
		else if(userIds=='')
		{
			createAlert("Warning!!","No users selected");
		}
		else
		{
			// Create a new textbroadcast object by sending 'POST' request to controller
			$http.post(API_ADDR + 'web/' + abbr + '/textBroadcast/create/' + groupid, $scope.broadcast)
			.success(function(data,status,header,config){
				console.log("TextBroadcast controller called successfully from backend");
				console.log(data);
//				if(data === 0)
//				{
//					createAlert("SMS Sent","The SMS has been sent successfully");
//				}
//				else
//				{
//					createAlert("Error Sending Message","There was some problem in the server !");
//				}
		
				if(data.status="success")
					createAlert("SMS Sent","The SMS has been sent successfully");
				else if(data.status=="error")
				{
					if(data.cause=="BroadcastExhausted")
						{
						createAlert("Limit exhausted.","Limit exhausted..Broadcast Failed. Contact Provider.");
						}
					else if(data.cause="LimitExceeded")
						{
						createAlert("Broadcast Recipients exceeded","Broadcast Recipients exceeded.Contact Provider.");
						}
				}
					
				$scope.getMetadata();
			})
			.error(function(data,status,header,config){
				
			})
		}
	};
	
	$scope.getMetadata=function(){
		console.log("Getting metadata");
		var abbr = $('#organizationAbbr').val();
		$http.get('/web/'+abbr+'/textbroadcastsleft').
		  then(function(response) {
			  console.log(response);
			$scope.broadcastsleft=response.data;
			console.log($scope.broadcastsleft)
		    // this callback will be called asynchronously
		    // when the response is available
		  }, function(response) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		  });
	}
	
	$scope.getMetadata();
});

$("#page-content").on("click","#text-broadcast-select-all",function(e){
	$("#text-broadcast-user-list input[type=checkbox]").each(function(){
		$(this).prop("checked",true);
	});
});

$("#page-content").on("click","#text-broadcast-unselect-all",function(e){
	$("#text-broadcast-user-list input[type=checkbox]").each(function(){
		$(this).prop("checked",false);
	});
});