/* Resources for OrderItems */
website.factory("AddOrderItem",['$resource',function($resource){
	return $resource(API_ADDR + "api/orderItems/:id", null, {
		save: {
			method: "POST"
		}
	});
}]);

website.factory("RemoveOrderItem",function($resource){
	return $resource(API_ADDR + "api/orderItems/:id",{id:'@id'},{
		query: { method: "GET", isArray: false },
		update: {method: "DELETE",params: {id: '@id'}}
	});
});

website.factory("GetOrderItemsByOrder", function($resource) {
	return $resource(API_ADDR + "api/orderItems/search/findByOrder_OrderId", {orderId:"@orderId"}, {
		update: {
			method: 'GET'
		}
	});
});

/* Resources for Orders */
website.factory("UpdateOrder", function($resource) {
	return $resource(API_ADDR + "api/orders/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
		update: {method: "PATCH",params: {id: '@id'}}
	});
});

/* Resources for Messages */
website.factory("UpdateMessage", function($resource) {
	return $resource(API_ADDR + "api/messages/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
		update: {method: "PATCH",params: {id: '@id'}}
	});
});

website.factory("UpdateTextMessageComment", function($resource) {
	return $resource(API_ADDR + "rest/textMessage/updateComment/:id/:comment", {id: "@id", comment:"@comment"}, {
		update: {
			method: 'GET'
		}
	});
});

website.factory("UpdateVoiceMessageComment", function($resource) {
	return $resource(API_ADDR + "rest/voiceMessage/updateComment/:id/:comment", {id: "@id", comment:"@comment"}, {
		update: {
			method: 'GET'
		}
	});
});

/* Resources for Groups */
website.factory("AddGroup",['$resource',function($resource){
	return $resource(API_ADDR + "api/groups/:id", null, {
		save: {
			method: "POST"
		}
	});
}]);

website.factory("RemoveGroup",function($resource){
	return $resource(API_ADDR + "api/groups/:id",{id:'@id'},{
		query: { method: "GET", isArray: false },
		update: {method: "DELETE",params: {id: '@id'}}
	});
});

website.factory("UpdateGroup", function($resource) {
	return $resource(API_ADDR + "api/groups/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
		update: {method: "PATCH",params: {id: '@id'}}
	});
});

/* Resources for Users */
website.factory("AddUser",['$resource',function($resource){
	return $resource(API_ADDR + "api/users/:id", null, {
		save: {
			method: "POST"
		}
	});
}]);

website.factory("GetUser",function($resource){
	return $resource(API_ADDR + "api/users/:id",{id:'@id'},{
		query: { method: "GET", isArray: false },
	});
});


/* Resources for UserPhoneNumbers */
website.factory("AddUserPhoneNumber",['$resource',function($resource){
	return $resource(API_ADDR + "api/userPhoneNumbers/:id", null, {
		save: {
			method: "POST"
		}
	});
}]);

/* Resources for UserView */
website.factory("AddUserView",['$resource',function($resource){
	console.log(API_ADDR + "api/userViews/add/:groupId");
	return $resource(API_ADDR + "api/userViews/add/:groupId", {groupId: "@groupId", userView: "@userView"}, {
		save: {
			method: "POST"
		}
	});
}]);

website.factory("RemoveUserView",function($resource){
	return $resource(API_ADDR + "api/userViews/delete/:id",{id:'@id'},{
		update: {
			method: "DELETE",params: {id: '@id'}
		}
	});
});

/* Resources for GroupMembership */
website.factory("AddGroupMembership",['$resource',function($resource){
	return $resource(API_ADDR + "api/groupMemberships/:id", null, {
		save: {
			method: "POST"
		}
	});
}]);

website.factory("RemoveGroupMembership",function($resource){
	return $resource(API_ADDR + "api/groupMemberships/:id",{id:'@id'},{
		update: {
			method: "DELETE",params: {id: '@id'}
		}
	});
});

website.factory("GetGroupMembershipByUserAndGroup", function($resource) {
	return $resource(API_ADDR + "api/groupMemberships/search/findByUserAndGroup", {user:"@user", group:"@group"}, {
		update: {
			method: 'GET'
		}
	});
});

/* Resources for Products */
website.factory("ProductCreate",function($resource){
	return $resource(API_ADDR + "api/products",{
		query: {method: "GET", isArray: false}
	});
});

website.factory("ProductEdit",function($resource){	
	return $resource(API_ADDR + "api/products/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
	    update: {method: "PATCH",params: {id: '@id'}}
	});
});

website.factory("ProductDelete",function($resource){
	return $resource(API_ADDR + "api/products/:id",{id:'@id'},{
		query: { method: "GET", isArray: false },
	    update: {method: "DELETE",params: {id: '@id'}}
	});
});

website.factory("ProductListGet",function($resource){
	return $resorce(API_ADDR + "api/products",{
		query: {method: "GET", isArray: true}
	});
});

/* Resources for Preset Quantities */
website.factory("PresetQuantityCreate",function($resource){
	return $resource(API_ADDR + "/api/presetQuantities");
});

website.factory("PresetQuantityEdit",function($resource){
	
	return $resource(API_ADDR + "api/presetQuantities/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
	    update: {method: "PATCH",params: {id: '@id'}}
	});
});

website.factory("PresetQuantityDelete",function($resource){
	return $resource(API_ADDR + "api/presetQuantities/:id",{id:'@id'},{
		query: { method: "GET", isArray: false },
	    update: {method: "DELETE",params: {id: '@id'}}
	});
});

/* Resources for Order Summary */
website.factory("ShowOrderSummaryGroups", function($resource) {
	return $resource(API_ADDR + "api/orderItems/search/orderSummaryGroups", {org:"@org", groupName:"@groupName", fromTime:"@fromTime", toTime:"@toTime"}, {
		update: {
			method: 'GET'
		}
	});
});
	
website.factory("ShowOrderSummaryProducts", function($resource) {
	return $resource(API_ADDR + "/api/orderItems/search/orderSummaryProducts", {org:"@org", prod:"@prod", fromTime:"@fromTime", toTime:"@toTime"}, {
		update: {
			method: 'GET'
		}
	});
});

/* Resources for Organization */
website.factory("UpdateOrganization", function($resource) {
	return $resource(API_ADDR + "/api/organizations/:id", {id: '@id'}, {
		query: {
			method: "GET",
			isArray: false
		},
		update: {
			method: "PATCH",
			params: {
				id: '@id'
			}
        }
    });
});

/* Resources for Outbound Call */
website.factory("UpdateBroadcastDefaultSettings", function($resource) {
    return $resource(API_ADDR + "api/broadcastDefaultSettings/:id", {id: '@id'}, {
    	query: {
    		method: "GET",
    		isArray: false
    	},
    	update: {
    		method: "PATCH",
    		params: {
    			id: '@id'
    		}
    	}
	});
});

/* Resources for Outbound Call */
website.factory("UpadateBroadcastDefaultSettings", function($resource) {
    return $resource(API_ADDR + "api/broadcastDefaultSettings/:id", {id: '@id'}, {
    	query: {
    		method: "GET",
    		isArray: false
    	},
    	update: {
    		method: "PATCH",
    		params: {
    			id: '@id'
    		}
    	}
	});
});

/* Resources for text broadcast */
website.factory("TextBroadcast", function($resource) {
	return $resource(API_ADDR + "api/textBroadcasts/:id");
});