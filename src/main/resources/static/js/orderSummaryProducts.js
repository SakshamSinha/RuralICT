website.controller('OrderSummaryProductsController', function($scope, $route, ShowOrderSummaryProducts) {
      $scope.orderSummaries = function(data){
        document.getElementById("totalProducts").innerHTML=0;
      	$scope.orderSummariesProduct =  ShowOrderSummaryProducts.update(data, function(orderSummaries){
      		var locTotal=0;
      		for(var i=0; i<orderSummaries.length; i++){
      			locTotal+=(parseFloat(orderSummaries[i].collection));
      		}
      		document.getElementById("totalProducts").innerHTML="₹ " + locTotal;
      	});
	};
});
      
$("#page-content").on("click", "#submitProducts", function(e) {
    e.preventDefault();
    var product= $.trim($('#product').val());
    var from= $.trim($('#fromDate').val());
    var to= $.trim($('#toDate').val());
    if(from=="") alert("Please select(type) a valid From date in yyyy-mm-dd format");
    else if(to=="") alert("Please select(type) a valid To date in yyyy-mm-dd format");
    else if(validatedate(from)==false)	alert("Please select(type) a valid From date in yyyy-mm-dd format");
    else if(validatedate(to)==false)	alert("Please select(type) a valid To date in yyyy-mm-dd format");
    else if(to<from)	alert("To date should be ahead of From date!");
    else{
    	var data={};
	    data.product=product;
	    data.fromDate=from;
	    data.toDate=to;
	    angular.element($('#submitProducts')).scope().orderSummaries(data);
    }
});