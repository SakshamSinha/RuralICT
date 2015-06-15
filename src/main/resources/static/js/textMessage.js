/* Controller for handling text Messages */
website.controller("TextMessageCtrl", function($window, $scope, $route, RemoveOrderItem, AddOrderItem, UpdateOrder, UpdateMessage, UpdateTextMessageComment) {
	
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
	
	/* accept an order */
	$scope.acceptOrder = function(orderId) {
		$scope.order = UpdateOrder.get({id:orderId},function(){
			$scope.order.status = "accepted";
			
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
	
	/* update the comment. */
	$scope.updateTextComment = function(messageId, comment) {
		
		UpdateTextMessageComment.get({"id":messageId, "comment":comment}, function(e){});
	};
	
	//TODO Eliminating this function doing hard refresh
	$scope.reload = function(){
		setTimeout($window.location.reload,2000);
	}

});

$("#page-content").on("click", "#accept-inbox-text-order", function(e) {
    
	e.preventDefault();
    
	var comment = $.trim($("#inboxTextComment").val());
	var orderId = $.trim($("#inboxTextOrderId").val());
    var id = $(this).val();

    angular.element($('#accept-inbox-text-order')).scope().addOrderItems();
    angular.element($('#accept-inbox-text-order')).scope().acceptOrder(orderId);
    angular.element($('#accept-inbox-text-order')).scope().updateTextComment(id,comment);
    angular.element($('#accept-inbox-text-order')).scope().reload();

    $('#view-inbox-text-message-modal').modal('toggle');
	
});

$("#page-content").on("click", "#reject-inbox-text-order", function(e) {
    
	e.preventDefault();
	
	/* Get required values from modal */
    var id = $.trim($(this).val());
    
    /* Send request to reject message */
    angular.element($('#reject-inbox-text-order')).scope().rejectOrder(id);

    $('#view-inbox-text-message-modal').modal('toggle');
    $('#reject-inbox-text-order-modal').modal('toggle');
    
    angular.element($('#reject-inbox-text-order')).scope().reload();

	
});


/* Open the modal to reject the order */
$("#page-content").on("click", "#confirm-reject-inbox-text-order", function () {
	var id = $(this).val();
    $("#reject-inbox-text-order").val(id);
});

$("#page-content").on("click", ".remove-inbox-text-order-item", function () {
	/* Get required values from modal */
	var id = $.trim($(this).val());
	
	/* Manipulating HTML to respond that order Item is removed */
	$("#row"+id).remove();
	
	/* Remove order item from the queue */
	angular.element($("#add-inbox-text-order-items")).scope().removeOrderItemFromQueue(id);
	
});

/* Remove orderItem from queue */
$("#page-content").on("click", ".inboxTextMessageSaveButton", function (e) {
	e.preventDefault();
	
	/* Get required values from modal */
	var id = $(this).attr("id").split(" ")[1];
	var comment = $.trim($("#inboxTextMessageComment" + id).val());
	
	/* Remove order item from the queue */
	angular.element($("#inboxTextMessageComment" + id)).scope().updateTextComment(id,comment);
	
	alert("Comment has been updated");
	
});

/* Function to add new orderItem to queue */
$("#page-content").on("click", "#add-inbox-text-order-items", function () {
	
	/* Manipulate button value to store count */
	var count = parseInt($("#add-inbox-text-order-items").val());
	count++;
	$("#add-inbox-text-order-items").val(count);
	
	/* Get values to generate orderItem objects from modal */
	var productId = $.trim($("#inboxTextProductName").val()).split(" ")[0];
	var productUnitRate = $.trim($("#inboxTextProductName").val()).split(" ")[1];
	var productName = $("#inboxTextProductName")[0].options[$("#inboxTextProductName")[0].selectedIndex].innerHTML
	var productQuantity = $.trim($("#inboxTextProductQuantity").val());
	var orderId = $.trim($("#inboxTextOrderId").val());
	if(productQuantity == "other"){
		productQuantity = $.trim($("#inboxTextCustomQuantity").val());
	}
	
	/* Create and add new row element for user */
	var new_row = $('\
			<div id="row'+ count +'" class="fluid-row">\
				<div class="span3"></div>\
				<div class="span3">'+ productName +'</div>\
				<div class="span3">'+ productQuantity +'</div>\
				<div class="span2">'+ productUnitRate +'</div>\
				<div class="span1">\
					<button class="close remove-inbox-text-order-item" value="'+ count +'"><i class="icon-remove" aria-hidden="true"></i></button>\
				</div>\
			</div>\
	');
	new_row.appendTo($('#inboxTextOrderItems'));
	
	/* Create order item element and push it in the queue */
	var data={};
	data.id = count;
	data.product = 'products/'+ productId;
	data.quantity = parseInt(productQuantity);
	data.order = 'orders/' + orderId;
	data.unitRate = productUnitRate;
	
	angular.element($("#add-inbox-text-order-items")).scope().addOrderItemToQueue(data);
});

/* View inbox text message modal */
$("#page-content").on("click", ".view-inbox-text-message", function () {
	var id = $(this).val();
	
	/* Take values from inboxVoiceMessageTable */
	var textMessageTime = $("#inboxTextMessageTime"+id).text();
	var textMessageOrderId = $("#inboxTextMessageOrderId"+id).val();
	var textMessageName = $("#inboxTextMessageName"+id).text();
	var textMessageComment = $("#inboxTextMessageComment"+id).val();
	var textMessageContent = $("#inboxTextMessageContent"+id).val();
	
	/* Dump them into modal */
    $("#inboxTextTime").html(textMessageTime);
    $("#inboxTextOrderId").val(textMessageOrderId);
    $("#inboxTextName").html(textMessageName);
    $("#inboxTextComment").val(textMessageComment);
    $("#inboxTextContent").val(textMessageContent);
    
    $("#accept-inbox-text-order").val(id);
    $("#confirm-reject-inbox-text-order").val(textMessageOrderId);
});

$("#page-content").on('change','#inboxTextProductQuantity',function(e){
	var quantity =$("#inboxTextProductQuantity")[0].options[$("#inboxTextProductQuantity")[0].selectedIndex].innerHTML;
	if(quantity == "other"){
		$("#inboxTextCustomQuantity").removeAttr("disabled");
		
	}
	else{
		$("#inboxTextCustomQuantity").attr("disabled", "true");
	}
});

$("#page-content").on("click", ".feedbackTextMessageSaveButton", function (e) {
	e.preventDefault();
	
	/* Get required values from modal */
	var id = $(this).attr("id").split(" ")[1];
	var comment = $.trim($("#feedbackTextMessageComment" + id).val());
	
	/* Remove order item from the queue */
	angular.element($("#feedbackTextMessageComment" + id)).scope().updateTextComment(id,comment);
	
	alert("Comment has been updated");
	
});

$("#page-content").on("click", ".inbox-text-modal-close", function (e) {
	e.preventDefault();
	document.getElementById("inboxTextOrderItems").innerHTML = "";
	angular.element($("#add-inbox-text-order-items")).scope().clearQueue();
});
