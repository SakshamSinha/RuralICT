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
				angular.element($('#add-new-group')).scope().reload();
			}, function(error) {
				$scope.failure = error.data;
				if(error.status == "409"){
					alert("Group Already Exists");
				}
				else{
					alert("Adding of Group Failed");
				}
			});
		
	};
	
	$scope.reload = function(){
		setTimeout(window.location.reload.bind(window.location),2000);
	};
	
});	   
	

//add new group from index.html
$("#add-new-group").click(function() {  
    var name = $.trim($('#new-group-name-input').val());
    var parentGroup = "parentGroup/" + $("#parentgroupId").val();
    console.log("Parentgroup url: " + parentGroup);
    console.log(name);
    var data ={};
    data.name=name;
    data.organization = "organization/" + $("#organizationId").val();
    data.parentGroup = parentGroup;
    angular.element($('#add-new-group')).scope().addGroup(data);
    
    $('#add-new-group-modal').modal('toggle');

    
	
});