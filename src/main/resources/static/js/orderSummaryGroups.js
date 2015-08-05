website.controller('OrderSummaryGroupsController', function($scope, $route, ShowOrderSummaryGroups, ShowOrderSummaryOrg) {
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
	document.getElementById("totalAmount").innerHTML=0;
	$scope.orderSummariesGroup =  ShowOrderSummaryGroups.update(data, function(orderSummaries){
			var locTotal=0;
			for(var i=0; i<orderSummaries.length; i++){
				locTotal+=((parseFloat(orderSummaries[i].collection)));
			}
			document.getElementById("totalAmount").innerHTML="₹ " + locTotal;
	  	});
	};
	$scope.orderSummariesByOrg = function(data){
		document.getElementById("totalAmount").innerHTML=0;
		$scope.orderSummariesGroup =  ShowOrderSummaryOrg.update(data, function(orderSummariesByOrg){
				var locTotal=0;
				for(var i=0; i<orderSummariesByOrg.length; i++){
					locTotal+=((parseFloat(orderSummariesByOrg[i].collection)));
				}
				document.getElementById("totalAmount").innerHTML="₹ " + locTotal;
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
    else if(group == "") createAlert("Invalid Input","Please select a group from Group Name!");
    else{
    	var data={};
	    data.group=group;
	    data.fromDate=from;
	    data.toDate=to;
	    if(group=="All Groups")
	    {
	    	data.organization=$('#OrderSummaryGroup').attr('organizationId');
	    	//console.log(data.group);
	    	angular.element($('#submitGroups')).scope().orderSummariesByOrg(data);
	    }
	    else
	    	angular.element($('#submitGroups')).scope().orderSummaries(data);
    }
   });