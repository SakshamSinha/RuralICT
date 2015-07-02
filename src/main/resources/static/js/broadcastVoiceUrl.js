website.controller("BroadcastVoiceUrlCtrl",function($window, $scope, $resource, $location, $route, $templateCache){
	$scope.changeUrl = function(){
		var random = Math.random();
		$scope.url=null;
		$scope.url='broadcastVoiceMessages/'+$location.path().slice(7)+'?random='+random;
		console.log($scope.url);
	};
});

