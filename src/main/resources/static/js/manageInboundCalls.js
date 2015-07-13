website.factory("InboundCallReports", function($resource) {
	return $resource(API_ADDR+"/api/inboundCalls/search/getInboundCalls", {org:"@org", fromDate:"@fromDate", toDate:"@toDate"}, {
		update: {
			method: 'GET'
		}
	});
});

website.controller('ManageInboundCallsController', function($scope, $route, InboundCallReports) {
	
      $scope.callReports = function(data){
      	$scope.incomingCallReports =  InboundCallReports.get(data);
	};
	$scope.mySplit = function(string, nb) {
	    $scope.array = string.split('T');
	    return $scope.result = $scope.array[nb];
	}
});
      
$("#page-content").on("click", "#submitBut", function(e) {
    e.preventDefault();
    var from= $('#fromDate').val();
    var to= $('#toDate').val();
    if(from=="") alert("Please select(type) a valid From date in yyyy-mm-dd format");
    else if(to=="") alert("Please select(type) a valid To date in yyyy-mm-dd format");
    else if(validatedate(from)==false)	alert("Please select(type) a valid From date in yyyy-mm-dd format");
    else if(validatedate(to)==false)	alert("Please select(type) a valid To date in yyyy-mm-dd format");
    else if(to<from)  alert("To date should be ahead of From date!");
    else{
    	var data={};
    	data.org = $("#organizationId").val();
	    data.fromDate=from;
	    data.toDate=to;
	    angular.element($('#submitBut')).scope().callReports(data);
    }
});