website.controller("GroupsCtrl", function($scope, $route, AddGroup, RemoveGroup) {
	
	$scope.addGroup = function(data) {
			$scope.group = new AddGroup();
			$scope.group.organization = data.organization;
			$scope.group.name = data.name;
			$scope.group.parentGroup = data.parentGroup;
			AddGroup.save($scope.group, function(group) {
				
				window.location.reload(true);
				$scope.groupName = group.name;
				alert("New Group Added");
			}, function(error) {
				$scope.failure = error.data;
				alert("Adding of Group Failed");
			});
		
	};
	
	$scope.reload = function(){
		setTimeout(window.location.reload.bind(window.location),2000);
	};
	
});	   
	

//add new group from index.html
$("#add-new-group").click(function() {  
    var name = $.trim($('#new-group-name-input').val());
    var parentGroup = "parentGroup/1";
    console.log(name);
    var data ={};
    data.name=name;
    data.organization = "organization/" + $("#organizationId").val();
    data.parentGroup = parentGroup;
    angular.element($('#add-new-group')).scope().addGroup(data);
    
    $('#add-new-group-modal').modal('toggle');

    angular.element($('#add-new-group')).scope().reload();
	
});