/* Resources for OrderItems */
website.factory("AddOrderItem",['$resource',function($resource){
	 return $resource("/api/orderItems/:id", null, {
		 save: {
			 method: "POST"
		}
	 });
}]);

website.factory("RemoveOrderItem",function($resource){
	return $resource("/api/orderItems/:id",{id:'@id'},{
		query: { method: "GET", isArray: false },
	    update: {method: "DELETE",params: {id: '@id'}}
	});
});

website.factory("GetOrderItemsByOrder", function($resource) {
	return $resource("/api/orderItems/search/findByOrder_OrderId", {orderId:"@orderId"}, {
		update: {
			method: 'GET'
		}
	});
});

/* Resources for Orders */
website.factory("UpdateOrder", function($resource) {
	return $resource("/api/orders/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
	    update: {method: "PATCH",params: {id: '@id'}}
	});
});

/* Resources for Messages */
website.factory("UpdateMessage", function($resource) {
	return $resource("/api/messages/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
	    update: {method: "PATCH",params: {id: '@id'}}
	});
});

website.factory("UpdateTextMessageComment", function($resource) {
	return $resource("/rest/textMessage/updateComment/:id/:comment", {id: "@id", comment:"@comment"}, {
		update: {
			method: 'GET'
		}
	});
});

website.factory("UpdateVoiceMessageComment", function($resource) {
	return $resource("/rest/voiceMessage/updateComment/:id/:comment", {id: "@id", comment:"@comment"}, {
		update: {
			method: 'GET'
		}
	});
});