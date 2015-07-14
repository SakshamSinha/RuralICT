/* Javascript file for the Text Broadcast View Controller */
website.controller("textBroadcastCtrl", function($scope, $http, $routeParams, TextBroadcast) {

	// Get the required variables from thymeleaf attributes
	var orgid = $('#organizationId').val();
	var abbr = $('#organizationAbbr').val();
	var groupid = document.getElementById("broadcast-text-ids").getAttribute("data-groupid");
	var publisherid = document.getElementById("broadcast-text-ids").getAttribute("data-publisherid");
	
	// Initialize the values for radio-buttons
	$scope.radioOptions = [false,false,false,false];
	
	// Function called when 'Send SMS' button is clicked
	$scope.saveTextBroadcastBtn = function() {
		
		// Set the currently selected value in radio button as true
		var currentValue =  $scope.radioValues;
		$scope.radioOptions[currentValue]=true;
		
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
		$("#user-text-list input:checked").each(function(){
			userIds = userIds + this.value + ',';
		});
		
		$scope.broadcast.userIds = userIds;
		
		// Create a new textbroadcast object by sending 'POST' request to controller
		$http.post(API_ADDR + 'web/' + abbr + '/textBroadcast/create/' + groupid, $scope.broadcast)
		.success(function(data,status,header,config){
			console.log("TextBroadcast controller called successfully from backend");
			
			if(data === 0)
			{
				createAlert("SMS Sent","The SMS has been sent successfully");
			}
			else
			{
				createAlert("Error Sending Message","There was some problem in the server !");
			}
			
		})
		.error(function(data,status,header,config){
			
		})
		
		$scope.radioOptions[currentValue]=false;
		
	};
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