website.controller('GenerateLogController', function($scope, $route, GenerateLogs) {
	$('#fromLogsDate').datepicker({
		dateFormat: 'yy-mm-dd',
		changeMonth: true,
		changeYear: true
	});
	$('#toLogsDate').datepicker({
		dateFormat: 'yy-mm-dd',
		changeMonth: true,
		changeYear: true
	});
	
	$scope.generateLogsByOrg = function(data){
		$scope.generateLog=  GenerateLogs.update(data, function(orderSummariesByOrg){
			console.log("successfully written");
			document.getElementById("text").innerHTML="Successfully generated log.";
			document.getElementById("logButton").style.visibility = 'visible';
	  	});
	}
	
});
$("#page-content").on("click", "#submitLogs", function(e){
	e.preventDefault();
    var from= $.trim($('#fromLogsDate').val());
    var to= $.trim($('#toLogsDate').val());
    if(from=="") createAlert("Invalid Input","Please select(type) a valid From date in yyyy-mm-dd format");
    else if(to=="") createAlert("Invalid Input","Please select(type) a valid To date in yyyy-mm-dd format");
    else if(validatedate(from)==false)	createAlert("Invalid Input","Please select(type) a valid From date in yyyy-mm-dd format");
    else if(validatedate(to)==false)	createAlert("Invalid Input","Please select(type) a valid To date in yyyy-mm-dd format");
    else if(to<from)	createAlert("Invalid Input","To date should be ahead of From date!");
    else{
    	var data={};
	    data.fromTime=from;
	    data.toTime=to;
	    data.org=$('#GenerateLogsDiv').attr('organizationId');
	    angular.element($('#submitLogs')).scope().generateLogsByOrg(data);
    }
});