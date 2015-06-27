website.factory("BroadcastCreate",function($resource){
	return $resource("/api/voiceBroadcasts",{
		query: {method: "GET", isArray: false}
	});
});

website.directive('fileModel', ['$parse',function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

website.controller("BroadcastVoiceCtrl",function($window, $scope, $resource, $http, $route, $location, BroadcastCreate){
	var latestBroadcastableVoiceIds;
	//function to save product
	$scope.saveBroadcast = function(data){
		$scope.broadcast = data;
		$http.post('/web/'+data.abbr+'/broadcastVoiceMessages/'+data.groupId,$scope.broadcast)
		.success(function(data,status,header,config){
		})
		.error(function(data,status,header,config){
			
		})
			
	}
		
	$scope.uploadFile = function(ids){
		$scope.latestBroadcastableVoiceIds =ids;
		var formData=new FormData();
		formData.append("file",$scope.myFile); //myFile.files[0] will take the file and append in formData since the name is myFile.
		$http({
			method: 'POST',
			url: '/web/'+$scope.latestBroadcastableVoiceIds.abbr+'/upload', // The URL to Post.
			headers: {'Content-Type': undefined}, // Set the Content-Type to undefined always.
			data: formData,
			transformRequest: function(data, headersGetterFunction) {
			return data;
			}
		})
		.success(function(data, status) {
			$scope.latestBroadcastableVoiceIds.voiceId = data;
			$scope.latestBroadcastableVoiceIds.broadcastedTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
			console.log("Audio successfully uploaded and added in voice table. Posting over to Latest Broadcast Voice controller");
			$http.post('/web/'+$scope.latestBroadcastableVoiceIds.abbr+'/latestBroadcastVoiceMessages/'+$scope.latestBroadcastableVoiceIds.groupId,$scope.latestBroadcastableVoiceIds)
				.success(function(data,status,header,config){
					//TODO Eliminating this function doing hard refresh
					setTimeout($window.location.reload.bind(window.location),2000);
					})
				})
		.error(function(data, status) {
		});
	}
	
	//TODO Eliminating this function doing hard refresh
	$scope.reload = function(){
		setTimeout($window.location.reload,2000);
	}
    
});


$("#page-content").on("click","#voice-upload",function(e){
	console.log("voice upload called");
	broadcastVoiceIds = $("#broadcast-voice-ids");
	data={};
	data.abbr=broadcastVoiceIds.attr("org");
	data.organizationId = broadcastVoiceIds.attr("organizationid");
	data.groupId = broadcastVoiceIds.attr("groupid");
	angular.element($('#broadcast-voice-ids')).scope().uploadFile(data);
});



$("#page-content").on("click","#place-broadcast-call",function(e){
	var data={};
	data.askOrder=0;
	data.askFeedback=0;
	data.askResponse=0;
	broadcastVoiceIds = $("#broadcast-voice-ids");
	data.abbr=broadcastVoiceIds.attr("org");
	data.organizationId = broadcastVoiceIds.attr("organizationid");
	data.groupId = broadcastVoiceIds.attr("groupid");
	data.broadcastedTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
	data.publisherId = broadcastVoiceIds.attr("publisherid");
	data.mode = "web";
	if($("#order-check").is(":checked"))
	{
			data.askOrder=1;
	}
	if($("#feedback-check").is(":checked"))
	{
		data.askFeedback=1;
	}
	if($("#response-check").is(":checked"))
	{
		data.askResponse=1;
	}
	//look for the time it which is broadcasted.
	data.broadcastedTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
	data.appOnly = 0;
	data.format = "voice";
	//setting the voice id if the voice is present in group
	data.voiceId = $('#latest-voice').attr("voiceid");
	//ask about these fields
	data.voiceBroadcastDraft = 0;
	data.textContent = null;
	var userIds = '';
	$("#user-list input:checked").each(function(){
		userIds = userIds + this.value + ',';
	});
	data.userIds = userIds;
	angular.element($('#broadcast-voice-ids')).scope().saveBroadcast(data);
	
});