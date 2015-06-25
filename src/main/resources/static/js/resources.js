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

/* Resources for Groups */
website.factory("AddGroup",['$resource',function($resource){
	return $resource("/api/groups/:id", null, {
		save: {
			method: "POST"
		}
	});
}]);

website.factory("UpdateGroup", function($resource) {
	return $resource("/api/groups/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
		update: {method: "PATCH",params: {id: '@id'}}
	});
});

/* Resources for Users */
website.factory("AddUser",['$resource',function($resource){
	return $resource("/api/users/:id", null, {
		save: {
			method: "POST"
		}
	});
}]);

website.factory("GetUser",function($resource){
	return $resource("/api/users/:id",{id:'@id'},{
		query: { method: "GET", isArray: false },
	});
});


/* Resources for UserPhoneNumbers */
website.factory("AddUserPhoneNumber",['$resource',function($resource){
	return $resource("/api/userPhoneNumbers/:id", null, {
		save: {
			method: "POST"
		}
	});
}]);

/* Resources for UserView */
website.factory("AddUserView",['$resource',function($resource){
	return $resource("/api/userViews/add/:groupId", {groupId: "@groupId", userView: "@userView"}, {
		save: {
			method: "POST"
		}
	});
}]);

website.factory("RemoveUserView",function($resource){
	return $resource("/api/userViews/delete/:id",{id:'@id'},{
		update: {
			method: "DELETE",params: {id: '@id'}
		}
	});
});

/* Resources for GroupMembership */
website.factory("AddGroupMembership",['$resource',function($resource){
	return $resource("/api/groupMemberships/:id", null, {
		save: {
			method: "POST"
		}
	});
}]);
