website.controller('OrderSummaryGroupsController', function($scope, $route, ShowOrderSummaryGroups) {
	$('#fromGroupsDate').datepicker({
		dateFormat: 'yy-mm-dd',
		changeMonth: true,
		changeYear: true
	});
	$('#toGroupsDate').datepicker({
		dateFormat: 'yy-mm-dd',
		changeMonth: true,
		changeYear: true
	});
	$scope.orderSummaries = function(data){
	document.getElementById("totalGroups").innerHTML=0;
	$scope.orderSummariesGroup =  ShowOrderSummaryGroups.update(data, function(orderSummaries){
			var locTotal=0;
			for(var i=0; i<orderSummaries.length; i++){
				locTotal+=((parseFloat(orderSummaries[i].collection)));
			}
			document.getElementById("totalGroups").innerHTML="₹ " + locTotal;
	  	});
	};
});
      
$("#page-content").on("click", "#submitGroups", function(e) {
    e.preventDefault();
    var group= $.trim($('#selectedGroup').val());
    var from= $.trim($('#fromGroupsDate').val());
    var to= $.trim($('#toGroupsDate').val());
    if(from=="") createAlert("Invalid Input","Please select(type) a valid From date in yyyy-mm-dd format");
    else if(to=="") createAlert("Invalid Input","Please select(type) a valid To date in yyyy-mm-dd format");
    else if(validatedate(from)==false)	createAlert("Invalid Input","Please select(type) a valid From date in yyyy-mm-dd format");
    else if(validatedate(to)==false)	createAlert("Invalid Input","Please select(type) a valid To date in yyyy-mm-dd format");
    else if(to<from)	createAlert("Invalid Input","To date should be ahead of From date!");
    else if(group == "? undefined:undefined ?") createAlert("Invalid Input","Please select a group from Group Name!");
    else{
    	var data={};
	    data.group=group;
	    data.fromDate=from;
	    data.toDate=to;
	    angular.element($('#submitGroups')).scope().orderSummaries(data);
    }
   });