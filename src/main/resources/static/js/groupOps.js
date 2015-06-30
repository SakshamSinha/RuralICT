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
	
	$scope.removeGroup = function(groupId){
		
		$scope.group = RemoveGroup.get({id: groupId},function(){
			
			$scope.group.$update({id:groupId},function(group){}, function(error){
					if(error.status == "409")
						alert("To delete this group, remove all its members.");
			});
		});		
	};
	
	$scope.reload = function(){
		setTimeout(window.location.reload.bind(window.location),2000);
	};
	
});	   
	

//add new group from index.html
$("#add-new-group").click(function() {  
    var name = $.trim($('#new-group-name-input').val());
    var parentGroup = "parentGroup/" + $.trim($('#new-group-parent-group-input').val());
    console.log(name);
    var data ={};
    data.name=name;
    data.organization = "organization/" + $("#organizationId").val();
    data.parentGroup = parentGroup;
    angular.element($('#add-new-group')).scope().addGroup(data);
    
    $('#add-new-group-modal').modal('toggle');

    angular.element($('#add-new-group')).scope().reload();
	
});

$("#groupList").on("click", ".delete-group", function(e) {
	e.preventDefault();
    
	/* Get required values from modal */
	var id = $(this).val();
	angular.element($('#groupList')).scope().removeGroup(id);
	
	$('#view-inbox-voice-message-modal').modal('toggle');

	// Workaround for time being
	angular.element($('#groupList')).scope().reload();  
});