website.controller('OrderSummaryGroupsController', function($scope, $route, ShowOrderSummaryGroups) {
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
    var from= $.trim($('#fromDate').val());
    var to= $.trim($('#toDate').val());
    if(from=="") alert("Please select(type) a valid From date in yyyy-mm-dd format");
    else if(to=="") alert("Please select(type) a valid To date in yyyy-mm-dd format");
    else if(validatedate(from)==false)	alert("Please select(type) a valid From date in yyyy-mm-dd format");
    else if(validatedate(to)==false)	alert("Please select(type) a valid To date in yyyy-mm-dd format");
    else if(to<from)	alert("To date should be ahead of From date!");
    else{
    	var data={};
	    data.group=group;
	    data.fromDate=from;
	    data.toDate=to;
	    angular.element($('#submitGroups')).scope().orderSummaries(data);
    }
   });