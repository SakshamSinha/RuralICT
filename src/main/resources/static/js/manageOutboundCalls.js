website.factory("OutboundCallReports", function($resource) {
	return $resource(API_ADDR+'api/manageReportsOutboundCalls/getOutboundCallsList',{grp:"@grp"}, {
		update: {
			method: 'GET',
			isArray: true
		}
	});
});

website.controller("ManageOutboundCallsController", function($scope, $http, $routeParams,OutboundCallReports) {
	$scope.outgoingCallRows = [];
	
	$scope.mySplit = function(string, nb) {
	    $scope.array = string.split(' ');
	    return $scope.result = $scope.array[nb];
	}
	
	$scope.submit = function(){
		var dat={};
		dat.grp = $('#groupId').val();
		var from= $('#fromDate2').val();
		var to= $('#toDate2').val();
		if(from=="") alert("Please select(type) a valid From date in yyyy-mm-dd format");
		else if(to=="") alert("Please select(type) a valid To date in yyyy-mm-dd format");
		else if(validatedate(from)==false)	alert("Please select(type) a valid From date in yyyy-mm-dd format");
		else if(validatedate(to)==false)	alert("Please select(type) a valid To date in yyyy-mm-dd format");
		else if(to<from) alert("To date should be ahead of From date!");
        
		var data= OutboundCallReports.update(dat, function(success){
			for (i = 0; i < data.length; i++) {
				var dateStr = data[i].dateTime;
				if(dateStr>=from && dateStr<to){
					$scope.outgoingCallRows.push(data[i]);		    
		    	}
		    }	    
		}, function(error){
			alert("Error: " + error,status);			
		});		
	}	
});
