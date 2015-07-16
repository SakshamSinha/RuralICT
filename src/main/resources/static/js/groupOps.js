website.controller("GroupsCtrl", function($scope, $route, AddGroup, RemoveGroup) {
	
	$scope.addGroup = function(data) {
			$scope.group = new AddGroup();
			$scope.group.organization = data.organization;
			$scope.group.name = data.name;
			$scope.group.parentGroup = data.parentGroup;
			AddGroup.save($scope.group, function(group) {
				
				$scope.groupName = group.name;
				createAlert("Group Added", "New group has been added successfully.")
				angular.element($('#add-new-group')).scope().reload();
			}, function(error) {
				$scope.failure = error.data;
				if(error.status == "409"){
					createAlert("Group Addition Failed", "Group already exists.")
				}
				else{
					createAlert("Group Addition Failed", "Group could not be created.")
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

    // for getting parent group ID of current organization 
    var parentGroupId = $("#parentGroupId").val();
    var parentGroup = "parentGroup/" + parentGroupId;

    // for child hirarchy
   	// var parentGroup = "parentGroup/" + $.trim($('#new-group-parent-group-input').val());

    console.log(name);
    var data ={};
    data.name=name;
    data.organization = "organization/" + $("#organizationId").val();
    // for child hirarchy
  //  data.parentGroup = parentGroup;
    angular.element($('#add-new-group')).scope().addGroup(data);
    
    $('#add-new-group-modal').modal('toggle');

    
	
});