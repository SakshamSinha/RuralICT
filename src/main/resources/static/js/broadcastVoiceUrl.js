website.controller("BroadcastVoiceUrlCtrl",function($scope, $resource, $location, $route, $templateCache){
	//$scope.url='broadcastVoiceMessages/'+$location.path().slice(7)+'?random='+random;
	$scope.tab=1;
	$scope.changeUrl = function(){
		var random = Math.random();
		$scope.url='broadcastVoiceMessages/'+$location.path().slice(7)+'?random='+random;
		console.log($scope.url);
	};
//	$scope.isSet(value) = function(){
//		console.log("IsSet function called");
//		return $scope.tab == value;
//	};
//	$scope.setTab(value) = function(){
//		consolelog("setTab funciton called");
//		$scope.tab = value;
//	};
});

