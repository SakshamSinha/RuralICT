/* Controller for handling voice Messages */
website.controller("VoiceMessageCtrl", function($window, $resource, $scope, $route, GetOrderItemsByOrder, RemoveOrderItem, AddOrderItem, UpdateOrder, UpdateMessage, UpdateTextMessageComment) {
	
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
		setTimeout($window.location.reload,2000);
	}
});

/************************************************************************/

/* View inbox voice message modal */
$("#page-content").on("click", ".view-inbox-voice-message", function () {
	var id = $(this).val();
	
	/* Take values from inboxVoiceMessageTable */
	var voiceMessageTime = $("#inboxVoiceMessageTime"+id).text();
	var voiceMessageOrderId = $("#inboxVoiceMessageOrderId"+id).val();
	var voiceMessageName = $("#inboxVoiceMessageName"+id).text();
	var voiceMessageComment = $("#inboxVoiceMessageComment"+id).val();
	var voiceMessageURL = $("#inboxVoiceMessageURL"+id).val();
	
	/* Dump them into modal */
	$("#inboxVoiceTime").html(voiceMessageTime);
	$("#inboxVoiceOrderId").val(voiceMessageOrderId);
	$("#inboxVoiceName").html(voiceMessageName);
	$("#inboxVoiceComment").val(voiceMessageComment);
    
	/* Special Processing for audio */
	loadAudio("inboxVoiceURL", voiceMessageURL);
    
	$("#save-inbox-voice-order").val(id);
	$("#confirm-reject-inbox-voice-order").val(id);
});

/* Function to add new orderItem to queue */
$("#page-content").on("click", "#add-inbox-voice-order-items", function () {
	
	/* Manipulate button value to store count */
	var count = parseInt($("#add-inbox-voice-order-items").val());
	count++;
	$("#add-inbox-voice-order-items").val(count);
	
	/* Get values to generate orderItem objects from modal */
	var productId = $.trim($("#inboxVoiceProductName").val()).split(" ")[0];
	var productUnitRate = $.trim($("#inboxVoiceProductName").val()).split(" ")[1];
	var productName = $("#inboxVoiceProductName")[0].options[$("#inboxVoiceProductName")[0].selectedIndex].innerHTML
	var productQuantity = $.trim($("#inboxVoiceProductQuantity").val());
	var orderId = $.trim($("#inboxVoiceOrderId").val());
	if(productQuantity == "other"){
		productQuantity = $.trim($("#inboxVoiceCustomQuantity").val());
	}
	
	/* Create and add new row element for user */
	var new_row = $('\
		<div id="row'+ count +'" class="fluid-row">\
			<div class="span3"></div>\
			<div class="span3">'+ productName +'</div>\
			<div class="span3">'+ productQuantity +'</div>\
			<div class="span2">'+ productUnitRate +'</div>\
			<div class="span1">\
				<button class="close remove-inbox-voice-order-item" value="'+ count +'"><i class="icon-remove" aria-hidden="true"></i></button>\
			</div>\
		</div>\
	');
	new_row.appendTo($('#inboxVoiceOrderItems'));
	
	/* Create order item element and push it in the queue */
	var data={};
	data.id = count;
	data.product = 'products/'+ productId;
	data.quantity = parseInt(productQuantity);
	data.order = 'orders/' + orderId;
	data.unitRate = productUnitRate;
	
	angular.element($("#add-inbox-voice-order-items")).scope().addOrderItemToQueue(data);
});

$("#page-content").on('change','#inboxVoiceProductQuantity',function(e){
	var quantity =$("#inboxVoiceProductQuantity")[0].options[$("#inboxVoiceProductQuantity")[0].selectedIndex].innerHTML;
	if(quantity == "other"){
		$("#inboxVoiceCustomQuantity").removeAttr("disabled");
		
	}
	else{
		$("#inboxVoiceCustomQuantity").attr("disabled", "true");
	}
});

/* Remove orderItem from queue */
$("#page-content").on("click", ".remove-inbox-voice-order-item", function () {
	/* Get required values from modal */
	var id = $.trim($(this).val());
	
	/* Manipulating HTML to respond that order Item is removed */
	$("#row"+id).remove();
	
	/* Remove order item from the queue */
	angular.element($("#add-inbox-voice-order-items")).scope().removeOrderItemFromQueue(id);	
});

/* Save the order */
$("#page-content").on("click", "#save-inbox-voice-order", function(e) {
    
	e.preventDefault();
    
	/* Get required values from modal */
	var comment = $.trim($("#inboxVoiceComment").val());
	var id = $(this).val();
	var orderId = $.trim($("#inboxVoiceOrderId").val());
    
	angular.element($('#save-inbox-voice-order')).scope().addOrderItems();
	angular.element($('#save-inbox-voice-order')).scope().saveOrder(orderId);
	angular.element($('#save-inbox-voice-order')).scope().updateVoiceComment(id,comment);
	$('#view-inbox-voice-message-modal').modal('toggle');
    
	// Workaround for time being
	angular.element($('#save-inbox-voice-order')).scope().reload();  
});

/* Open the modal to reject the order */
$("#page-content").on("click", "#confirm-reject-inbox-voice-order", function () {
	var id = $(this).val();
	$("#reject-inbox-voice-order").val(id);
});

/* Reject the order */
$("#page-content").on("click", "#reject-inbox-voice-order", function(e) {
    
	e.preventDefault();
	
	/* Get required values from modal */
	var id = $.trim($(this).val());
	var orderId = $.trim($("#inboxVoiceOrderId").val());
    
	/* Send request to reject message */
	angular.element($('#reject-inbox-voice-order')).scope().rejectOrder(orderId);

	$('#view-inbox-voice-message-modal').modal('toggle');
	$('#reject-inbox-voice-order-modal').modal('toggle');
    
	angular.element($('#reject-inbox-voice-order')).scope().reload();
});

/* Remove orderItem from queue */
$("#page-content").on("click", ".inboxVoiceMessageSaveButton", function (e) {
	e.preventDefault();
	
	/* Get required values from modal */
	var id = $(this).attr("id").split(" ")[1];
	var comment = $.trim($("#inboxVoiceMessageComment" + id).val());
	
	/* Remove order item from the queue */
	angular.element($("#inboxVoiceMessageComment" + id)).scope().updateVoiceComment(id,comment);
	
	alert("Comment has been updated");
	
});

$("#page-content").on("click", ".inbox-voice-modal-close", function (e) {
	e.preventDefault();
	document.getElementById("inboxVoiceOrderItems").innerHTML = "";
	angular.element($("#add-inbox-voice-order-items")).scope().clearQueue();
	//$('#view-inbox-voice-message-modal').modal('toggle');
});

/************************************************************************/

/* View saved voice message modal */
$("#page-content").on("click", ".view-saved-voice-message-modal", function () {
	var id = $(this).val();
	
	/* Take values from inboxVoiceMessageTable */
	var voiceMessageTime = $("#savedVoiceMessageTime"+id).text();
	var voiceMessageName = $("#savedVoiceMessageName"+id).text();
	var voiceMessageComment = $("#savedVoiceMessageComment"+id).text();
	var voiceMessageURL = $("#savedVoiceMessageURL"+id).val();
	var voiceMessageOrderId = $("#savedVoiceMessageOrderId"+id).val();
	
	/* Dump them into modal */
	$("#savedVoiceTime").html(voiceMessageTime);
	$("#savedVoiceName").html(voiceMessageName);
	$("#savedVoiceComment").html(voiceMessageComment);
	$("#savedVoiceOrderId").val(voiceMessageOrderId);
    
	/* Special Processing for audio */
	loadAudio("savedVoiceURL", voiceMessageURL);
    
	angular.element($('#process-saved-voice-order')).scope().getOrderItemsByOrder(voiceMessageOrderId);
    
	$("#process-saved-voice-order").val(id);
});

$("#page-content").on('change','#savedVoiceProductQuantity',function(e){
	var quantity =$("#savedVoiceProductQuantity")[0].options[$("#savedVoiceProductQuantity")[0].selectedIndex].innerHTML;
	if(quantity == "other"){
		$("#savedVoiceCustomQuantity").removeAttr("disabled");
		
	}
	else{
		$("#savedVoiceCustomQuantity").attr("disabled", "true");
	}
});

/* Function to add new orderItem to queue */
$("#page-content").on("click", "#add-saved-voice-order-items", function () {
	
	/* Manipulate button value to store count */
	var count = parseInt($("#add-saved-voice-order-items").val());
	count++;
	$("#add-saved-voice-order-items").val(count);
	
	/* Get values to generate orderItem objects from modal */
	var productId = $.trim($("#savedVoiceProductName").val()).split(" ")[0];
	var productUnitRate = $.trim($("#savedVoiceProductName").val()).split(" ")[1];
	var productName = $("#savedVoiceProductName")[0].options[$("#savedVoiceProductName")[0].selectedIndex].innerHTML
	var productQuantity = $.trim($("#savedVoiceProductQuantity").val());
	if(productQuantity == "other"){
		productQuantity = $.trim($("#savedVoiceCustomQuantity").val());
	}
	var orderId = $.trim($("#savedVoiceOrderId").val());
	
	/* Create and add new row element for user */
	var new_row = $('\
		<div id="row'+ count +'" class="fluid-row">\
			<div class="span3"></div>\
			<div class="span3">'+ productName +'</div>\
			<div class="span3">'+ productQuantity +'</div>\
			<div class="span2">'+ productUnitRate +'</div>\
			<div class="span1">\
				<button class="close remove-saved-voice-order-item" value="'+ count +'"><i class="icon-remove" aria-hidden="true"></i></button>\
			</div>\
			<input id="savedVoiceHidden'+count+'" type="hidden" value="unsaved">\
		</div>');
	new_row.appendTo($('#savedVoiceOrderItems'));
	
	/* Create order item element and push it in the queue */
	var data={};
	data.id = count;
	data.product = 'products/'+ productId;
	data.quantity = parseInt(productQuantity);
	data.order = 'orders/' + orderId;
	data.unitRate = productUnitRate;
	
	angular.element($("#add-saved-voice-order-items")).scope().addOrderItemToQueue(data);
});

$("#page-content").on("click", ".remove-saved-voice-order-item", function () {
	/* Get required values from modal */
	var hashKey = $.trim($(this).val());
	var status = document.getElementById("savedVoiceHidden"+hashKey).getAttribute("value");
	
	/* This means that order item is already stored in database. So remove it from db. */
	if(status == "saved"){
		var id = document.getElementById("savedVoiceHiddenOrderItemId"+hashKey).getAttribute("value");
		$("#savedVoiceAddedRow"+id).remove();
		angular.element($("#add-saved-voice-order-items")).scope().removeOrderItem(id);
	}
	else{
		/* Manipulating HTML to respond that order Item is removed */
		$("#row"+hashKey).remove();
		
		/* Remove order item from the queue */
		angular.element($("#add-saved-voice-order-items")).scope().removeOrderItemFromQueue(hashKey);
	}
});

$("#page-content").on("click", "#process-saved-voice-order", function(e) {
    
	e.preventDefault();
	var id = $(this).val();
	var orderId = $.trim($("#savedVoiceOrderId").val());
    
	angular.element($('#process-saved-voice-order')).scope().addOrderItems();
	angular.element($('#process-saved-voice-order')).scope().processOrder(orderId);
    
	$('#view-saved-voice-message-modal').modal('toggle');
    
	angular.element($('#process-saved-voice-order')).scope().reload();
});

$("#page-content").on("hide", ".view-saved-voice-message-modal", function (e) {
	e.preventDefault();
	
	angular.element($("#add-saved-voice-order-items")).scope().clearQueue();
	$('#view-saved-voice-message-modal').modal('toggle');
});

$("#page-content").on("click", ".saved-voice-modal-close", function (e) {
	e.preventDefault();
	document.getElementById("savedVoiceOrderItems").innerHTML = "";
	angular.element($("#add-saved-voice-order-items")).scope().clearQueue();
	$('#view-inbox-voice-message-modal').modal('toggle');
});


/**************************************************************************/

$("#page-content").on("click", ".feedbackVoiceMessageSaveButton", function (e) {
	e.preventDefault();
	
	/* Get required values from modal */
	var id = $(this).attr("id").split(" ")[1];
	var comment = $.trim($("#feedbackVoiceMessageComment" + id).val());
	
	/* Remove order item from the queue */
	angular.element($("#feedbackVoiceMessageComment" + id)).scope().updateVoiceComment(id,comment);
	
	alert("Comment has been updated");
	
});

$("#page-content").on("click", ".responseVoiceMessageSaveButton", function (e) {
	e.preventDefault();
	
	/* Get required values from modal */
	var id = $(this).attr("id").split(" ")[1];
	var comment = $.trim($("#responseVoiceMessageComment" + id).val());
	
	/* Remove order item from the queue */
	angular.element($("#responseVoiceMessageComment" + id)).scope().updateVoiceComment(id,comment);
	
	alert("Comment has been updated");
});