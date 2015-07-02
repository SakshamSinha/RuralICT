website.controller("BroadcastVoiceUrlCtrl",function($window, $scope, $resource, $rootScope, $location, $window){
	$scope.changeUrl = function(){
		
		$scope.url=null;
		$scope.url='broadcastVoiceMessages/'+$location.path().slice(7);
		console.log($scope.url);
		$scope.$apply();
	};
});

