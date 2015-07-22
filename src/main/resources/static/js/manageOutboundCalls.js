website.controller("ManageOutboundCallsController", function($scope, $http, $routeParams,OutboundCallReports) {
	$('#fromOutboundReportsDate').datepicker({
		dateFormat: 'yy-mm-dd',
		changeMonth: true,
		changeYear: true
	});
	$('#toOutboundReportsDate').datepicker({
		dateFormat: 'yy-mm-dd',
		changeMonth: true,
		changeYear: true
	});
	$scope.outgoingCallRows = [];
	
	$scope.mySplit = function(string, nb) {
	    $scope.array = string.split(' ');
	    return $scope.result = $scope.array[nb];
	}
	
	$scope.submit = function(){
		var dat={};
		dat.grp = $('#groupId').val();
		var from= $('#fromOutboundReportsDate').val();
		var to= $('#toOutboundReportsDate').val();
		if(from=="") createAlert("Invalid Input","Please select(type) a valid From date in yyyy-mm-dd format");
		else if(to=="") createAlert("Invalid Input","Please select(type) a valid To date in yyyy-mm-dd format");
		else if(validatedate(from)==false)	createAlert("Invalid Input","Please select(type) a valid From date in yyyy-mm-dd format");
		else if(validatedate(to)==false)	createAlert("Invalid Input","Please select(type) a valid To date in yyyy-mm-dd format");
		else if(to<from) createAlert("Invalid Input","To date should be ahead of From date!");
        
		var data= OutboundCallReports.update(dat, function(success){
			for (i = 0; i < data.length; i++) {
				var dateStr = data[i].dateTime;
				if(dateStr>=from && dateStr<to){
					$scope.outgoingCallRows.push(data[i]);		    
		    	}
		    }	    
		}, function(error){
			createAlert("Error Fetching Data","Error: " + error,status);			
		});		
	}	
});
