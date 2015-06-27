website.controller('OrderSummaryProductsController', function($scope, $route, ShowOrderSummaryProducts) {
      $scope.orderItems = function(data){
        document.getElementById("totalProducts").innerHTML=0;
      	$scope.orderSumProducts =  ShowOrderSummaryProducts.get(data, function(orderItem){
      		var locTotal=0;
      		var orderItemArray=orderItem._embedded.orderItems;
      		for(var i=0; i<orderItemArray.length; i++){
      			locTotal+=((parseFloat(orderItemArray[i].quantity))*(parseFloat(orderItemArray[i].unitRate)));
      		}
      		document.getElementById("totalProducts").innerHTML=locTotal;
      	});
	};
});
      
$("#page-content").on("click", "#submitProducts", function(e) {
    e.preventDefault();
    var org= $.trim($('#orgAbbrevation').text());
    var prod_data= $.trim($('#prod_name').val());
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
	    data.prod=prod_data;
	    data.fromTime=from;
	    data.toTime=to;
	    angular.element($('#submitProducts')).scope().orderItems(data);
    }
});