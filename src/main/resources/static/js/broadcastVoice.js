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
	
	$scope.broadcastsleft=0;
	//function to save product
	$scope.saveBroadcast = function(data){
		$scope.broadcast = data;
		//TODO remove this
		console.log('save broadcast has been called');
		if($scope.broadcast.userIds=='')
		{
			createAlert("No user selected", "Select atleast one user.")
		}
		else
		{
			$http.post(API_ADDR + 'web/'+data.abbr+'/broadcastVoiceMessages/'+data.groupId,$scope.broadcast)
			.success(function(data,status,header,config){
				if(data.status=="error")
				{
					if(data.cause=="BroadcastExhausted")
						{
						createAlert("Limit exhausted.","Limit exhausted..Broadcast Failed. Contact Provider.");
						}
					else if(data.cause="LimitExceeded")
						{
						createAlert("Broadcast Recipients exceeded","Broadcast Recipients exceeded.Contact Provider.");
						}
				}
				else if(data.status=="success")
				{
					createAlert("Broadcast Successful.",'Call has been placed. Reach of call is dependent upon many factors like Network Coverage and DND Exclusion.')
				}
				$scope.getMetadata();
				console.log('broadcast data posted. Users called.');
			})
			.error(function(data,status,header,config){
				
			})	
		}	
	}
		
	$scope.uploadFile = function(ids){
		if ($scope.myFile == undefined)
		{
			createAlert("Error Uploading File","No file added. Please choose a file of '.wav' audio format");
		}
		else if ($scope.myFile.type != "audio/wav")
		{
			createAlert("Error Uploading File","Invalid File!! Please choose again. You can only choose a file of '.wav' audio format");
		}
		else
		{
			$scope.latestBroadcastableVoiceIds =ids;
			var formData=new FormData();
			
			formData.append("file",$scope.myFile); //myFile.files[0] will take the file and append in formData since the name is myFile.
			$http({
				method: 'POST',
				url: API_ADDR + 'web/'+$scope.latestBroadcastableVoiceIds.abbr+'/upload', // The URL to Post.
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
				$http.post(API_ADDR + 'web/'+$scope.latestBroadcastableVoiceIds.abbr+'/latestBroadcastVoiceMessages/'+$scope.latestBroadcastableVoiceIds.groupId,$scope.latestBroadcastableVoiceIds)
					.success(function(data,status,header,config){
						createAlert("Audio Uploaded","Audio successfully uploaded");
						//TODO Eliminating this function doing hard refresh
						setTimeout($window.location.reload.bind(window.location),2000);
						})
					})
			.error(function(data, status) {
				if (status == "500")
					createAlert("Error Uploading File","File already present. Choose a different file before uploading.");
			});
		}
	}
	
	//TODO Eliminating this function doing hard refresh
	$scope.reload = function(){
		setTimeout($window.location.reload.bind(window.location),2000);
	}
	
	$scope.getMetadata=function(){
		console.log("Getting metadata");
		var abbr = $('#organizationAbbr').val();
		$http.get('/web/'+abbr+'/voicebroadcastsleft').
		  then(function(response) {
			  console.log(response);
			$scope.broadcastsleft=response.data;
			console.log($scope.broadcastsleft)
		  }, function(response) {
		  });
	}
	$scope.getMetadata();
    
});

$("#page-content").on("click","#unselect-all",function(e){
	$("#user-list input[type=checkbox]").each(function(){
		$(this).prop("checked",false);
	});
});

$("#page-content").on("click","#unselect-all",function(e){
	$("#user-list input[type=checkbox]").each(function(){
		$(this).prop("checked",false);
	});
});

$("#page-content").on("click","#broadcast-message-file",function(e){
	//In order to set the value of file as null
	this.value = null;
});

$("#page-content").on("change","#broadcast-message-file",function(e){
	//get the filename set by the fileModel angular directive
	var filename = angular.element($('#broadcast-voice-ids')).scope().myFile.name;
	//set the filename to be seen in UI
	$("#broadcast-file-url").text(filename);
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

$("#page-content").on("click","#place-voice-broadcast-call",function(e){
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
	console.log("Place broadcast is being called.");
	//look for the time it which is broadcasted.
	//data.broadcastedTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
	//console.log(data.broadcastedTime);
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
