website.controller("BroadcastVoiceUrlCtrl",function($scope, $resource, $location, $route, $templateCache){

	$scope.changeUrl = function(){
		$scope.tab=1;
		var random = Math.random();
		$scope.voiceBroadcastUrl='broadcastVoiceMessages/'+$location.path().slice(7)+'?random='+random;
	};
});

