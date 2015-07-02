/**
 *  Javascript file for the Settings Controller
 */

/* Directive for making uploading files easier */
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

/* Function to dynamically change audio of Audio control and Audio Download link */
function changeAudioSource(url){
	audioControl = $('#welcome-message-audio');
	audioDownload = $('#download-message-audio');
	audioControl.attr("src", url);
	audioControl.load();
	audioDownload.attr("href", url);
}

/* Actual Settings Controller */
website.controller("SettingsCtrl", function($scope, $http, $routeParams, $window, UpdateOrganization, UpdateBroadcastDefaultSettings) {

	// get the current organization Attributes
	var orgid = $('#organizationId').val();
	var abbr = $('#organizationAbbr').val();

	$scope.languageUrl  = [];
	
	$scope.selectOptions = [{
		name: 'Disable(बंद करना)',
		value: '0'
	}, {
		name: 'Enable(चालू करना)',
		value: '1'
	}];
	
	$scope.languageOptions = [{
		language: 'English',
		locale: 'en',
		value: '0'
	}, {
		language: 'Marathi',
		locale: 'mr',
		value: '1'
	}, {
		language: 'Hindi',
		locale: 'hi',
		value: '2'
	}];
		
	
	$scope.incomingCheckBoxOptions = {
			"order" : false,
			"feedback" : false,
			"response" : false
	};
	
	var organization = UpdateOrganization.get({
		id: orgid
	}, function() {

		/*
		 *  set initial values for 'select' element model from database
		 */

		// 'select' elements from dashboard options
		$scope.feedbackSelect = $scope.selectOptions[Number(organization.enableFeedbacks)].value;
		$scope.responseSelect = $scope.selectOptions[Number(organization.enableResponses)].value;
		$scope.billSelect = $scope.selectOptions[Number(organization.enableBilling)].value;
		$scope.textSelect = $scope.selectOptions[Number(organization.enableSms)].value;

		// 'select' elements from voice call options
		$scope.orderCancelSelect = $scope.selectOptions[Number(organization.enableOrderCancellation)].value;
		$scope.broadcastEnableSelect = $scope.selectOptions[Number(organization.enableBroadcasts)].value;
		
		// 'checkbox' elements from incoming call settings
		$scope.incomingCheckBoxOptions.order = organization.inboundCallAskOrder;
		$scope.incomingCheckBoxOptions.feedback = organization.inboundCallAskFeedback;
		$scope.incomingCheckBoxOptions.response = organization.inboundCallAskResponse;
		
		// 'select' element from welcome message settings
		$scope.WelcomeMessageLanguageSelect = '0';  // set default option in select as "English"

	});
	
	
	
	// Get the initial url for the audio files from the Backend
	var postData = new FormData();
	postData.append("orgid", orgid);

	$http({
		method: 'POST',
		url: API_ADDR + 'web/' + abbr + '/getwelcomeMessageUrl', // The URL to Post.
		headers: {'Content-Type': undefined}, // Set the Content-Type to undefined always.
		data: postData,
		transformRequest: function(data, headersGetterFunction) {
			return data;
		}
	}).success(function(data, status) {
		
		// set the url as recieved from the backend
		$scope.englishMessageurl = data[0];
		$scope.marathiMessageurl = data[1];
		$scope.hindiMessageurl = data[2];
		
		$scope.languageUrl.push($scope.englishMessageurl);
		$scope.languageUrl.push($scope.marathiMessageurl);
		$scope.languageUrl.push($scope.hindiMessageurl);
		 
		// set initial values for the audio control and dowload link 
		changeAudioSource($scope.languageUrl[0]);
		}).error(function(data, status) {
			alert("There was some error in response from the server.");
	});
	

	// click function for 'save details' button in voice dashboard settings
	$scope.updateDashboardOpt = function() {

		$scope.organization = UpdateOrganization.get({
			id: orgid
		}, function() {

			//change the required attributes
			$scope.organization.enableFeedbacks = Boolean(Number($scope.feedbackSelect));
			$scope.organization.enableResponses = Boolean(Number($scope.responseSelect));
			$scope.organization.enableBilling = Boolean(Number($scope.billSelect));
			$scope.organization.enableOrderCancellation = Boolean(Number($scope.rejectSelect));
			$scope.organization.enableSms = Boolean(Number($scope.textSelect));

			//Finally , update the entity with required values
			$scope.organization.$update({
				id: orgid
			}, function() 
			{alert("Your Settings have been saved.") });
		});
	};

	$scope.updateVoiceCallOpt = function() {

		$scope.organization = UpdateOrganization.get({
			id: orgid
		}, function() {

			//make changes in the $resource object
			$scope.organization.enableFeedbacks = Boolean(Number($scope.feedbackSelect));
			$scope.organization.enableResponses = Boolean(Number($scope.responseSelect));
			$scope.organization.enableBilling = Boolean(Number($scope.billSelect));
			$scope.organization.enableOrderCancellation = Boolean(Number($scope.rejectSelect));
			$scope.organization.enableSms = Boolean(Number($scope.textSelect));

			//finally update the database
			$scope.organization.$update({
				id: orgid
			}, function() {alert("Your Settings have been saved.")});
		});
	};
	
	// click function for 'save details' button in incoming call settings
	$scope.updateIncomingCallOpt = function() {

		$scope.organization = UpdateOrganization.get({
			id: orgid
		}, function() {

			//make changes in the $resource object
			$scope.organization.inboundCallAskOrder = $scope.incomingCheckBoxOptions.order;
			$scope.organization.inboundCallAskFeedback = $scope.incomingCheckBoxOptions.feedback;
			$scope.organization.inboundCallAskResponse = $scope.incomingCheckBoxOptions.response;
			
			// check if at least one option is selected
			if(!$scope.organization.inboundCallAskOrder && !$scope.organization.inboundCallAskFeedback && !$scope.organization.inboundCallAskResponse)
			{
				alert("You must select at least one option !");
				return;
			}
			
			//finally update the database
			$scope.organization.$update({
				id: orgid
			}, function() {alert("Your Settings have been saved.")});
		});
	};
	
	$scope.uploadFile = function(){
		
		
		var localeIndex = $scope.WelcomeMessageLanguageSelect;
		
		// We use formData to pass various attributes to the spring controller method
		var formData=new FormData();

		formData.append("file", $scope.myFile); // can be accessed in Spring Controller using request.getPart()
		formData.append("locale", $scope.languageOptions[localeIndex].locale);
		formData.append("orgid", orgid);
		
		$http({
			method: 'POST',
			url: API_ADDR + 'web/' + abbr + '/upload/welcomeMessage', // The URL to Post.
			headers: {'Content-Type': undefined}, // Set the Content-Type to undefined always.
			data: formData,
			transformRequest: function(data, headersGetterFunction) {
				return data;
			}
		}).success(function(data, status) {
			
			if (data === "-1")
			{
				alert("Please select a file to upload !");
			}
			else if(data == "-2")
			{
				alert("Please Upload a File less than 10MB");
			}
			else if(data == "-3")
			{
				alert("The File you have uploaded is not a audio file");
			}
			else
			{
			$scope.languageUrl[localeIndex] = data;
			changeAudioSource($scope.languageUrl[localeIndex]);
			alert("The Audio File was Successfully Uploaded.");
			}
		})
		.error(function(data, status) {
			alert("There was some error in response from the server.");
		});
	}
});

// Jquery Specific Code
$("#page-content").on("change","#select-welcome-message-language",function(e){
	
	// Get the scope of the angular controller so that we can access required variables from it
	myScope = angular.element('#settings-page').scope();
	  
	// Depending on value of select element, update the audio player and download link
	if(this.value === '1')
 	{
	  	changeAudioSource(myScope.languageUrl[1]);
  	}
  	else if (this.value === '2')
  	{
	  	changeAudioSource(myScope.languageUrl[2]);
  	}
  	else if(this.value === '0')
  	{
	  	changeAudioSource(myScope.languageUrl[0]);
 	}
});