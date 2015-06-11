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

website.controller("SettingsCtrl", function($scope, Organization) {
	
	$scope.selectOptions = [
                            { name: 'Disable(बंद करना)', value: '0' }, 
                            { name: 'Enable(चालू करना)', value: '1' }
                        ]; 
	
	  var organization = Organization.get({id:1}, function() {
	        // dont know why but this statement works inside this function only.    
		   
		  /*console.log(organization.enableFeedbacks);
	    	var num = Number(organization.enableFeedbacks);
	        console.log(num);*/
		   
		  // console.log(organization.enableFeedbacks);
		  
		    /*
		     *  set initial values for 'select' element model from database
		     */
	        $scope.feedbackSelect = $scope.selectOptions[Number(organization.enableFeedbacks)].value;
	        $scope.responseSelect = $scope.selectOptions[Number(organization.enableResponses)].value;
	        $scope.billSelect = $scope.selectOptions[Number(organization.enableBilling)].value;
	        
	        // whether to replace saveSelect with broadcastSelect as 'enableBroadcasts' is present in organization database ?
	        //$scope.saveSelect = $scope.selectOptions[Number(organization.enableFeedbacks)].value;
	        //$scope.rejectSelect = $scope.selectOptions[Number(organization.enableOrderCancellation)].value;
	        $scope.textSelect = $scope.selectOptions[Number(organization.enableSms)].value;
	        
	    });
	  
        console.log(organization);	  
	  
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
	    	
	    	$scope.organization = Organization.get({ id: 1 }, function() {
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
		    	  $scope.organization.$update({id:1},function() {
		    	     console.log("Updated Successfully.");
		    	     //console.log("enableFeedback property" + $scope.organization.enableFeedbacks);
		    	     //console.log("After update feedbackSelect Prop:" + Number($scope.feedbackSelect));
		    	  });
		    	});
	    	
	    	
	  };
	});

/*
 *  Saves the currently selected options in the select dropdown menu (Javascript way)
 *  will use it if pure angularjs method fails 
 */ 

/*$("#page-content").on("click", "#save-btn-voice-dashboard", function(e) {  
    e.preventDefault();
    
    console.log();
    console.log($scope.organization.enableFeedbacks);
    
    var feedback_val = $('#feedback').val();
    console.log("feedback enable = " + feedback_val);
    var response_val = $('#response').val();
    console.log("response enable = " + response_val);
    var bill_val = $('#bill').val();
    console.log("bill enable = " + bill_val);
    var save_val = $('#save-menu').val();
    console.log("save enable = " + save_val);
    var reject_val = $('#reject-menu').val();
    console.log("reject enable = " + reject_val);
    var text_val = $('#text-menu').val();
    console.log("text message respose enable = " + text_val);
    
    /*var new_row = $('<tr>\
    <td><input type="checkbox" class="checkthis" /></td>\
    <td>'+name+'</td>\
    <td>'+email+'</td>\
    <td>'+phone+'</td>\
 <td>'+role+'</td>\
 <td>'+address+'</td>\
 <td><p data-placement="top" data-toggle="tooltip" title="Edit">\
    <button class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" name = "'+name+'" email = "'+email+'" phone="'+phone+'" role="'+role+'" city="'+address+'"data-target="#edit" >\
    <i class="icon-white icon-pencil"></i>Edit</button></p></td>\
    <td><p data-placement="top" data-toggle="tooltip" title="Delete">\
    <button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" >\
    <i class="icon-white icon-trash"></i>Delete</button></p></td>\
</tr>');
    
    new_row.appendTo($('#usertable'));
    
    $('#new-user-name-input').val("");
    $('#new-user-address-input').val("");
    $('#new-user-email-input').val("");
    $('#new-user-phone-input').val("");
    $('#new-user-role-input').val(""); 
   
});*/

$('#feedback').live('change', function(e) {
	  console.log($(this).val());
	});