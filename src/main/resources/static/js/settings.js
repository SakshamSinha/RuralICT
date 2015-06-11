/**
 * This contains the organization setting operations to change various boolean variables etc.
 * All CRUD operations are provided by default by spring data rest
 * I am using here PATCH http verb to update an entity
 * Also, to set the values from database thymeleaf comes into picture
 */
website.factory("Organization", function($resource) {
	return $resource("/api/organizations/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
	    update: {method: "PATCH",params: {id: '@id'}}
	});
});

website.controller("SettingsCtrl", function($scope, $routeParams, Organization) {
	
	// get the current organization id
	var orgid = document.getElementById("settings-page").getAttribute("orgid");
    console.log("orgid: " + orgid);	  
	
	$scope.selectOptions = [
                            { name: 'Disable(बंद करना)', value: '0' }, 
                            { name: 'Enable(चालू करना)', value: '1' }
                        ]; 
	  
	  var organization = Organization.get({id : orgid}, function() {
	        // dont know why but this statement works inside this function only.    
		   
		  /*console.log(organization.enableFeedbacks);
	    	var num = Number(organization.enableFeedbacks);
	        console.log(num);*/
		   
		  // console.log(organization.enableFeedbacks);
		  
		    /*
		     *  set initial values for 'select' element model from database
		     */
		  
		    // 'select' elements from dashboard options
	        $scope.feedbackSelect = $scope.selectOptions[Number(organization.enableFeedbacks)].value;
	        $scope.responseSelect = $scope.selectOptions[Number(organization.enableResponses)].value;
	        $scope.billSelect = $scope.selectOptions[Number(organization.enableBilling)].value;
	        // whether to replace saveSelect with broadcastSelect as 'enableBroadcasts' is present in organization database ?
	        //$scope.saveSelect = $scope.selectOptions[Number(organization.enableFeedbacks)].value;
	        //$scope.rejectSelect = $scope.selectOptions[Number(organization.enableOrderCancellation)].value;
	        $scope.textSelect = $scope.selectOptions[Number(organization.enableSms)].value;
	        
	        // 'select' elements from voice call options
	        $scope.orderCancelSelect = $scope.selectOptions[Number(organization.enableOrderCancellation)].value;
	        $scope.broadcastEnableSelect = $scope.selectOptions[Number(organization.enableBroadcasts)].value;
	        
	        
	        
	    });
	  
	  /* $scope.organization = Organization.get({ id: $scope.id }, function() {
	    	  // $scope.entry is fetched from server and is an instance of Entry
	    	  $scope.organization.enableFeedbacks = $scope.feedbackSelect;
	    	  $scope.entry.$update(function() {
	    	     console.log("Updated Successfully");
	    	  });
	    	});*/
	    
        
        /*var num = Number(organization.enableFeedbacks);
        console.log(num);
        console.log(organization.enableFeedbacks);
        $scope.feedbackSelect = $scope.selectOptions[0].value;*/
        
        // click function for 'save details' button in voice dashboard settings
	    $scope.updateDashboardOpt = function() {
		 /* Organization.enableFeedbacks = $scope.feedbackSelect;
		  Organization.update= ({id:1},organization,function() {
			  console.log("updated successfully");
			  console.log("current value of checkbox is:" + Organization.enableFeedbacks);
		  });*/
	    	
	    	$scope.organization = Organization.get({ id: orgid }, function() {
		    	  // $scope.organization is fetched from server and is an instance of Entry
		    	  //$scope.organization.enableFeedbacks = $scope.feedbackSelect;
	    		  // Also it necessary to convert the select value from first to String, then to Number,
	    		  // then to Boolean
	    	//	console.log("Before update feedbackSelect Prop:" + Boolean(Number($scope.feedbackSelect)));
	    		
	    		/*
	    		 * change the required attributes
	    		 */ 
	    		$scope.organization.enableFeedbacks = Boolean(Number($scope.feedbackSelect));
	    		$scope.organization.enableResponses = Boolean(Number($scope.responseSelect));
	    		$scope.organization.enableBilling = Boolean(Number($scope.billSelect));
	    		$scope.organization.enableOrderCancellation = Boolean(Number($scope.rejectSelect));
	    		$scope.organization.enableSms = Boolean(Number($scope.textSelect));
	    		
	    		//	console.log("Before update enablefeedbacks: " + $scope.organization.enableFeedbacks)
	    		
	    		 /* Finally , update the entity with required values */
		    	  $scope.organization.$update({id: orgid},function() {
		    	     console.log("Updated Successfully.");
		    	     console.log("organization " + orgid + " updated successfully.");
		    	     //console.log("enableFeedback property" + $scope.organization.enableFeedbacks);
		    	     //console.log("After update feedbackSelect Prop:" + Number($scope.feedbackSelect));
		    	  });
		    	});
	    	
	    	
	  };
	  
	  $scope.updateVoiceCallOpt = function() {
		  
		  $scope.organization = Organization.get({ id: orgid }, function() {
	    	
    		$scope.organization.enableFeedbacks = Boolean(Number($scope.feedbackSelect));
    		$scope.organization.enableResponses = Boolean(Number($scope.responseSelect));
    		$scope.organization.enableBilling = Boolean(Number($scope.billSelect));
    		$scope.organization.enableOrderCancellation = Boolean(Number($scope.rejectSelect));
    		$scope.organization.enableSms = Boolean(Number($scope.textSelect));
    		
    		$scope.organization.$update({id: orgid},function() {
	    	      console.log("Updated Successfully.");
	    	  });
	    	});	  
	 
	  };
	  
	});

/*
 *  Saves the currently selected options in the select dropdown menu (Javascript way)
 *  will use it if pure angularjs method fails 
 */ 

/*$('#feedback').live('change', function(e) {
	  console.log($(this).val());
	});*/