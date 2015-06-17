website.controller("GroupsCtrl", function($scope, $route, AddGroup) {
	
	$scope.addGroup = function(data) {
			$scope.group = new AddGroup();
			$scope.group.organization = data.organization;
			$scope.group.name = data.name;
			$scope.group.parentGroup = data.parentGroup;
			AddGroup.save($scope.group, function(group) {
				
				window.location.reload(true);
				$scope.groupName = group.name;
				$("#group-add-success-modal").modal('toggle');
			}, function(error) {
				$scope.failure = error.data;
				$("#group-add-failed-modal").modal('toggle');
			});
		
	};
	
});	   
	

//add new group from index.html
$("#add-new-group").click(function() {  
    var name = $.trim($('#new-group-name-input').val());
    var parentGroup = "parentGroup/" + $.trim($('#new-group-parent-group-input').val());
    console.log(name);
    var data ={};
    data.name=name;
    data.organization = "organization/" + $("#organization").attr("data-orgId");
    data.parentGroup = parentGroup;
    angular.element($('#add-new-group')).scope().addGroup(data);
    
    $('#add-new-group-modal').modal('toggle');

    $('#new-group-name-input').val("");
	
});