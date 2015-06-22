/* Javascript file for the Text Broadcast View Controller */
 
website.factory("TextBroadcast", function($resource) {
    return $resource("/api/textBroadcasts/:id");
});

website.controller("textBroadcastCtrl", function($scope, $http, $routeParams, TextBroadcast, OutboundCall) {

    // Get the required variables from thymeleaf attributes
    var orgid = document.getElementById("broadcast-text-ids").getAttribute("organizationid");
    var abbr = document.getElementById("broadcast-text-ids").getAttribute("organizationabbr");
    var groupid = document.getElementById("broadcast-text-ids").getAttribute("groupid");
    var publisherid = document.getElementById("broadcast-text-ids").getAttribute("publisherid");
    
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
		$http.post('/web/'+ abbr +'/textBroadcast/create/' + groupid, $scope.broadcast)
		.success(function(data,status,header,config){
			console.log("controller called successfully from backend");
			console.log(data);
			console.log(status);
			console.log(header);
			console.log(config);
		})
		.error(function(data,status,header,config){
			
		})
		
		$scope.radioOptions[currentValue]=false;
        
    };
});