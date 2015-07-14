website.controller("GroupSettingsCtrl", function($scope, $route, UpdateGroup, RemoveGroup) {
	
	$scope.updateGroupName = function(groupId, name) {
		$scope.group = UpdateGroup.get({id:groupId},function(){
			$scope.group.name = name;
			
			$scope.group.$update({id:groupId},function(success){
				
				createAlert("Group Updated","Group Renamed Successfully");
				$scope.reload();
			}, function(error){
				
				if(error.status == "409"){
					createAlert("Invalid Input","Group Name should be distinct.")
				}
			});
		});
	};
	
	$scope.removeGroup = function(groupId){
		
		$scope.group = RemoveGroup.get({id: groupId},function(){
			
			$scope.group.$update({id:groupId},function(group){
				window.location.href = "/";
				
			}, function(error){
					if(error.status == "409")
						createAlert("Error Deleting Group","To delete this group, remove all its members.");
			});
		});		
	};
	
	/* Need to find out way to reload page without refresh. Work halted since message repository gives errors */
	//TODO Eliminating this function doing hard refresh
	$scope.reload = function(){
		
		setTimeout(window.location.reload.bind(window.location),2000);
	};
	
});	   
	

//add new group from index.html
$("#page-content").on("click", "#submitGroupSettings", function (e) {
	e.preventDefault();
    var name = $.trim($('#changeGroupName').val());
    var groupId = $.trim($('#groupId').val());

    angular.element($('#submitGroupSettings')).scope().updateGroupName(groupId, name);
});

$("#page-content").on("click", "#delete-group", function(e) {
	e.preventDefault();
    
	/* Get required values from modal */
	var id = $("#groupId").val();
	angular.element($('#delete-group')).scope().removeGroup(id);
	
	$('#delete-group-confirmation-modal').modal('toggle');

	
});