website.controller("GroupSettingsCtrl", function($scope, $route, UpdateGroup) {
	
	$scope.updateGroupName = function(groupId, name) {
		$scope.group = UpdateGroup.get({id:groupId},function(){
			$scope.group.name = name;
			
			$scope.group.$update({id:groupId},function(){
				
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
    angular.element($('#submitGroupSettings')).scope().reload();
});



