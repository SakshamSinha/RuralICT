/* Controller for handling voice Messages */
website.controller("AppMessageCtrl", function($window, $resource, $scope, $route, GetOrderItemsByOrder, RemoveOrderItem, AddOrderItem, UpdateOrder, UpdateMessage, UpdateTextMessageComment) {
	
	/* This array is a temporary queue to store new orderItems */
	$scope.orderItemList = [];
	
	/* Clears orderItem queue */
	$scope.clearQueue = function(){
		
		$scope.orderItemList = [];
	}
	
	/* Adds orderItems from queue to database */
	$scope.addOrderItems = function() {
		
		for(i=0;i<$scope.orderItemList.length;i++){
			var orderItem = $scope.orderItemList[i];
			
			$scope.orderItem = new AddOrderItem();
			$scope.orderItem.product = orderItem.product;
			$scope.orderItem.quantity = orderItem.quantity;
			$scope.orderItem.order = orderItem.order;
			$scope.orderItem.unitRate = orderItem.unitRate;
			
			AddOrderItem.save($scope.orderItem, function() {});
		}
		$scope.clearQueue();
	};
	
	/* Required in saved message tab to display previously added order items */
	$scope.getOrderItemsByOrder = function(orderId){
		GetOrderItemsByOrder.get({orderId: orderId}, function(orderItems){
			 
			/* Used directly in view to fill the product table */
			$scope.orderItems = [];
			 
			/* Since product name is in different relation, separate request has to be sent to collect product names */
			var productList = [];
			 
			var items = orderItems["_embedded"]["orderItems"];
			 
			for(var i=0;i<items.length;i++){
				 
				/* Build new resource locally */
				var Product = $resource(items[i]["_links"]["product"]["href"], {}, {
						update: {
							method: 'GET'
						}
				});
				 
				/* This scope variable is kind of a flag to determine if all the requests are made */
				$scope.countOfCalls = 0;
				 
				/* Send request for each product name */ 
				Product.get({}, function(product){
					$scope.countOfCalls++;
					productList.push(product.name);
					 
					/* Only if all requests are responded */
					/* This approach makes the process little slow but could not find better way */
					// TODO Better Approach
					if($scope.countOfCalls==items.length){
						for(var j=0;j<items.length;j++){
							 
							/*
							 * Again very weird but seemingly only possible way 
							 * to get object key faster is to extract key from the url 
							 */
							// TODO Better Approach
							var urlChunks = items[j]["_links"]["self"]["href"].split("/");
							items[j].id = urlChunks[urlChunks.length-1];
							
							items[j].quantity = parseFloat(items[j].quantity);
							items[j].product = productList[j];
							 
							$scope.orderItems.push(items[j]);
						}	 
					 }
				});
		 	}
			 
		 });
	};

	/* Remove already stored order Item from the database */ 
	$scope.removeOrderItem = function(orderItemId){
		
		$scope.product = RemoveOrderItem.get({id: orderItemId},function(){
			
			$scope.product.$update({id:orderItemId},function(){
				
			});
		});		
	};
	
	/* Adding order item to queue */
	$scope.addOrderItemToQueue = function(data) {
		$scope.orderItemList.push(data);
	};
	
	/* Removing order item from queue */
	$scope.removeOrderItemFromQueue = function(id) {
		for(i=0;i<($scope.orderItemList.length);i++){
			if($scope.orderItemList[i].id == id){
				$scope.orderItemList.splice(i,1);
			}
		}
	};
	
	// FIXME Code commented for future utilization
	/*$scope.saveOrderItem = function(data) {
		var orderItem = new OrderItem(data);
		console.log(orderItem);
		orderItem.$save();
		orderItem.$save(function(orderItem) {

			/*var new_row = $('<tr>\
			      <td><input type="checkbox" class="checkthis"/></td>\
			      <td>'+product.name+'</td>\
			      <td>'+product.unitRate+'</td>\
			      <td>\
			        <p data-placement="top" data-toggle="tooltip" title="Edit">\
			          <button class="open-edit-modal btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" data-target="#edit">\
			          <i class="icon-white icon-pencil"></i> Edit</button>\
			        </p>\
			      </td>\
			      <td>\
			        <p data-placement="top" data-toggle="tooltip" title="Delete">\
			          <button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete">\
			          <i class="icon-white icon-trash"></i> Delete</button>\
			        </p>\
			      </td>\
			    </tr>');

		    new_row.appendTo($('#producttable > tbody'));

		    $scope.productName = product.name;
			$('#new-product-input').val("");
		    $('#new-product-type-input').val("");
		    $('#new-price-input').val("");
		}, function(error) {
			console.log(error);
			$scope.failure = error.data;
			$("#product-add-failed-modal").modal('toggle');
		});
	};*/
	
	/* save an order */
	$scope.saveOrder = function(orderId) {
		$scope.order = UpdateOrder.get({id:orderId},function(){
			$scope.order.status = "saved";
			
			$scope.order.$update({id:orderId},function(){
				
			});
		});
	};
	
	/* process an order */
	$scope.processOrder = function(orderId) {
		$scope.order = UpdateOrder.get({id:orderId},function(){
			$scope.order.status = "processed";
			
			$scope.order.$update({id:orderId},function(){		
			});
		});
	};
	
	/* reject an order */
	$scope.rejectOrder = function(orderId) {
		$scope.order = UpdateOrder.get({id:orderId},function(){
			$scope.order.status = "rejected";
			
			$scope.order.$update({id:orderId},function(){
			});
		});
	};
	
	/* It would have been great if this function worked. */
	$scope.updateVoiceMessageComment = function(messageId, comment) {
		
		$scope.message = UpdateMessage.query({id:messageId},function(message){
			$scope.message.comments = comment;
			
			$scope.message.$update({id:messageId},function(){			
			});
		});		
	};
	
	/* update the comment. This is very lame way to do this thing. But since above method doesnt work, no other alternative */
	$scope.updateVoiceComment = function(messageId, comment) {
		UpdateTextMessageComment.get({"id":messageId, "comment":comment});
	};
	
	/* Need to find out way to reload page without refresh. Work halted since message repository gives errors */
	//TODO Eliminating this function doing hard refresh
	$scope.reload = function(){
		
		setTimeout(window.location.reload.bind(window.location),2000);
	}
});

/************************************************************************/

/* View saved app message modal */
$("#page-content").on("click", ".view-saved-app-message-modal", function () {
	var id = $(this).val();
	
	/* Take values from inboxVoiceMessageTable */
	var appMessageTime = $("#savedAppMessageTime"+id).text();
	var appMessageName = $("#savedAppMessageName"+id).text();
	var appMessageComment = $("#savedAppMessageComment"+id).text();
	var appMessageOrderId = $("#savedAppMessageOrderId"+id).val();
	
	/* Dump them into modal */
	$("#savedAppTime").html(appMessageTime);
	$("#savedAppName").html(appMessageName);
	$("#savedAppComment").html(appMessageComment);
	$("#savedAppOrderId").val(appMessageOrderId);
    
	angular.element($('#process-saved-app-order')).scope().getOrderItemsByOrder(appMessageOrderId);
    
	$("#process-saved-app-order").val(id);
});

$("#page-content").on('change','#savedAppProductQuantity',function(e){
	var quantity =$("#savedAppProductQuantity")[0].options[$("#savedAppProductQuantity")[0].selectedIndex].innerHTML;
	if(quantity == "other"){
		$("#savedAppCustomQuantity").removeAttr("disabled");
		
	}
	else{
		$("#savedAppCustomQuantity").attr("disabled", "true");
	}
});

/* Function to add new orderItem to queue */
$("#page-content").on("click", "#add-saved-app-order-items", function () {
	
	/* Manipulate button value to store count */
	var count = parseInt($("#add-saved-app-order-items").val());
	count++;
	$("#add-saved-app-order-items").val(count);
	
	/* Get values to generate orderItem objects from modal */
	var productId = $.trim($("#savedAppProductName").val()).split(" ")[0];
	var productUnitRate = $.trim($("#savedAppProductName").val()).split(" ")[1];
	var productName = $("#savedAppProductName")[0].options[$("#savedAppProductName")[0].selectedIndex].innerHTML
	var productQuantity = $.trim($("#savedAppProductQuantity").val());
	if(productQuantity == "other"){
		productQuantity = $.trim($("#savedAppCustomQuantity").val());
	}
	if(! $.isNumeric(productQuantity)){
		createAlert("Quantity","Enter valid quantity.")
		return;
	}
	var orderId = $.trim($("#savedAppOrderId").val());
	
	/* Create and add new row element for user */
	var new_row = $('\
		<div id="row'+ count +'" class="fluid-row">\
			<div class="span3"></div>\
			<div class="span3">'+ productName +'</div>\
			<div class="span3">'+ productQuantity +'</div>\
			<div class="span2">'+ productUnitRate +'</div>\
			<div class="span1">\
				<button class="close remove-saved-app-order-item" value="'+ count +'"><i class="icon-remove" aria-hidden="true"></i></button>\
			</div>\
			<input id="savedVoiceHidden'+count+'" type="hidden" value="unsaved">\
		</div>');
	new_row.appendTo($('#savedAppOrderItems'));
	
	/* Create order item element and push it in the queue */
	var data={};
	data.id = count;
	data.product = 'products/'+ productId;
	data.quantity = parseInt(productQuantity);
	data.order = 'orders/' + orderId;
	data.unitRate = productUnitRate;
	
	angular.element($("#add-saved-app-order-items")).scope().addOrderItemToQueue(data);
});

$("#page-content").on("click", ".remove-saved-app-order-item", function () {
	/* Get required values from modal */
	var hashKey = $.trim($(this).val());
	var status = document.getElementById("savedAppHidden"+hashKey).getAttribute("value");
	
	/* This means that order item is already stored in database. So remove it from db. */
	if(status == "saved"){
		var id = document.getElementById("savedAppHiddenOrderItemId"+hashKey).getAttribute("value");
		$("#savedAppAddedRow"+id).remove();
		angular.element($("#add-saved-app-order-items")).scope().removeOrderItem(id);
	}
	else{
		/* Manipulating HTML to respond that order Item is removed */
		$("#row"+hashKey).remove();
		
		/* Remove order item from the queue */
		angular.element($("#add-saved-app-order-items")).scope().removeOrderItemFromQueue(hashKey);
	}
});

$("#page-content").on("click", "#process-saved-app-order", function(e) {
    
	e.preventDefault();
	var id = $(this).val();
	var orderId = $.trim($("#savedAppOrderId").val());
	angular.element($('#process-saved-app-order')).scope().addOrderItems();
	angular.element($('#process-saved-app-order')).scope().processOrder(orderId);
	$('#view-saved-app-message-modal').modal('toggle');
	angular.element($('#process-saved-app-order')).scope().reload();
});

$("#page-content").on("hide", ".view-saved-app-message-modal", function (e) {
	e.preventDefault();
	
	angular.element($("#add-saved-app-order-items")).scope().clearQueue();
	$('#view-saved-app-message-modal').modal('toggle');
});

$("#page-content").on("click", ".saved-app-modal-close", function (e) {
	e.preventDefault();
	document.getElementById("savedAppOrderItems").innerHTML = "";
	angular.element($("#add-saved-app-order-items")).scope().clearQueue();
});
