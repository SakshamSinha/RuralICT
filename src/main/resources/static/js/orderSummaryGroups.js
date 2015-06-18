website.factory("ShowOrderSummaryGroups", function($resource) {
	return $resource("/api/orderItems/search/orderSummaryGroups", {org:"@org", groupName:"@groupName", fromTime:"@fromTime", toTime:"@toTime"}, {
		update: {
			method: 'GET'
		}
	});
});

website.controller('OrderSummaryGroupsController', function($scope, $route, ShowOrderSummaryGroups) {
      $scope.orderItems = function(data){
        document.getElementById("totalGroups").innerHTML=0;
      	$scope.orderSumGroups =  ShowOrderSummaryGroups.get(data, function(orderItem){
      		var locTotal=0;
      		var orderItemArray=orderItem._embedded.orderItems;
      		for(var i=0; i<orderItemArray.length; i++){
      			locTotal+=((parseFloat(orderItemArray[i].quantity))*(parseFloat(orderItemArray[i].unitRate)));
      		}
      		document.getElementById("totalGroups").innerHTML=locTotal;
      	});
	};
});
      
$("#page-content").on("click", "#submitGroups", function(e) {
    e.preventDefault();
    var org= $.trim($('#orgAbbrevation').text());
    var group_data= $.trim($('#group_name').val());
    var from= $.trim($('#fromDate').val());
    var to= $.trim($('#toDate').val());
    if(from=="") alert("Please select(type) a valid From date in yyyy-mm-dd format");
    else if(to=="") alert("Please select(type) a valid To date in yyyy-mm-dd format");
    else if(validatedate(from)==false)	alert("Please select(type) a valid From date in yyyy-mm-dd format");
    else if(validatedate(to)==false)	alert("Please select(type) a valid To date in yyyy-mm-dd format");
    else if(to<from)	alert("To date should be ahead of From date!");
    else{
    	var data={};
	    data.org=org;
	    data.groupName=group_data;
	    data.fromTime=from;
	    data.toTime=to;
	    angular.element($('#submitGroups')).scope().orderItems(data);
    }
   });