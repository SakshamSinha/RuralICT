website.controller('OrderSummaryProductsController', function($scope, $route, ShowOrderSummaryProducts) {
		$('#fromProductsDate').datepicker({
			dateFormat: 'yy-mm-dd',
			changeMonth: true,
			changeYear: true
		});
		$('#toProductsDate').datepicker({
			dateFormat: 'yy-mm-dd',
			changeMonth: true,
			changeYear: true
		});
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
    var from= $.trim($('#fromProductsDate').val());
    var to= $.trim($('#toProductsDate').val());
    if(from=="") createAlert("Invalid Input","Please select(type) a valid From date in yyyy-mm-dd format");
    else if(to=="") createAlert("Invalid Input","Please select(type) a valid To date in yyyy-mm-dd format");
    else if(validatedate(from)==false)	createAlert("Invalid Input","Please select(type) a valid From date in yyyy-mm-dd format");
    else if(validatedate(to)==false)	createAlert("Invalid Input","Please select(type) a valid To date in yyyy-mm-dd format");
    else if(to<from)	createAlert("Invalid Input","To date should be ahead of From date!");
    else{
    	var data={};
	    data.product=product;
	    data.fromDate=from;
	    data.toDate=to;
	    angular.element($('#submitProducts')).scope().orderSummaries(data);
    }
});