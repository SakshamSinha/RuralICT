website.controller("BroadcastVoiceUrlCtrl",function($scope, $resource, $location, $route, $templateCache){
//	$scope.voiceBroadcastUrl='broadcastVoiceMessages/'+$location.path().slice(7)+'?random='+random;
//	$scope.tab=1;
	$scope.changeUrl = function(){
		$scope.tab=1;
		var random = Math.random();
		$scope.voiceBroadcastUrl='broadcastVoiceMessages/'+$location.path().slice(7)+'?random='+random;
		console.log($scope.voiceBroadcastUrl);
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

