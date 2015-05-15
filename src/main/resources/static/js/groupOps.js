/**
 * This contains the group operations such as, add new, sort, etc.
 */
website.factory("Group", function($resource) {
	return $resource("/app/api/groups/:id");
});


website.controller("GroupsCtrl", function($scope, $route, Group) {

	$scope.saveGroup = function(data) {
		var group = new Group(data);
		group.organization = "organizations/" + $("#organization").attr("data-orgId");
		//group.parentGroup = "groups/1";
		group.$save(function(group) {
		 	
			window.location.reload(true);
		    $scope.groupName = group.name;
			$("#group-add-success-modal").modal('toggle');
			
		}, function(error) {
			$scope.failure = error.data;
			$("#group-add-failed-modal").modal('toggle');
		});
	}

});	   
	

//add new group from index.html
$("#add-new-group").click(function() {  
    var name = $.trim($('#new-group-name-input').val());
    console.log(name);
    var data ={};
    data.name=name;
    
    angular.element($('#add-new-group')).scope().saveGroup(data);
    
    $('#add-new-group-modal').modal('toggle');

    $('#new-group-name-input').val("");
	
});