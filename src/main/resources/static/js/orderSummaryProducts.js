website.factory("ShowOrderSummaryProducts", function($resource) {
	return $resource("/api/orderItems/search/orderSummaryProducts", {org:"@org", prod:"@prod", fromTime:"@fromTime", toTime:"@toTime"}, {
		update: {
			method: 'GET'
		}
	});
});

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

function validatedate(inputText)
	  {
	  var dateformat = /^(19|20)\d\d([- /.])(0[1-9]|1[012])\2(0[1-9]|[12][0-9]|3[01])$/;
	  // Match the date format through regular expression
	  if(inputText.match(dateformat)){
	  //Test which seperator is used '/' or '-'
		  var opera1 = inputText.split('/');
		  var opera2 = inputText.split('-');
		  lopera1 = opera1.length;
		  lopera2 = opera2.length;
		  // Extract the string into month, date and year
		  if (lopera1>1){
		  var pdate = inputText.split('/');
		  }
		  else if (lopera2>1) {
		  var pdate = inputText.split('-');
		  }
		  var mm  = parseInt(pdate[0]);
		  var dd = parseInt(pdate[1]);
		  var yy = parseInt(pdate[2]);
		  // Create list of days of a month [assume there is no leap year by default]
		  var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
		  if (mm==1 || mm>2){
		  	if (dd>ListofDays[mm-1]){
		  	return false;
		  	console.log("1");
		  	}
		  }
		  if (mm==2){
		  	var lyear = false;
		  	if ( (!(yy % 4) && yy % 100) || !(yy % 400)){
		  	lyear = true;
		  	}
		  	if ((lyear==false) && (dd>=29)){
		  	return false;
		  	console.log("2");
		  	}
		  if ((lyear==true) && (dd>29)){
		  	return false;
		  	console.log("3");
		  	}
		  }
	  }
	  else
	  {
	  console.log("4");
	  return false;
	  }
	  console.log("5");
	  return true;
}
      
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