/**
 *  Javascript file for the Settings Controller
 */
website.controller("SettingsCtrl", function($scope, $routeParams, UpdateOrganization, OutboundCall) {

    // get the current organization id
    var orgid = document.getElementById("settings-page").getAttribute("orgid");
    var outboundcallid = document.getElementById("settings-page").getAttribute("orgid");

    $scope.selectOptions = [{
        name: 'Disable(बंद करना)',
        value: '0'
    }, {
        name: 'Enable(चालू करना)',
        value: '1'
    }];
    
    $scope.incomingCheckBoxOptions = {
    		"order" : false,
            "feedback" : false,
            "response" : false
    };
    $scope.outgoingCheckBoxOptions = {
    		"order" : false,
    		"feedback" : false,
    		"response" : false
    };    

    var organization = Organization.get({
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

    });
    
    var outboundcall = OutboundCall.get({
        id: outboundcallid
    }, function() {
    	
    	//intialize 'checkbox' elements from outgoing call settings
        $scope.outgoingCheckBoxOptions.order = outboundcall.askOrder;
        $scope.outgoingCheckBoxOptions.feedback = outboundcall.askFeedback;
        $scope.outgoingCheckBoxOptions.response = outboundcall.askResponse;

    });
    
    

    // click function for 'save details' button in voice dashboard settings
    $scope.updateDashboardOpt = function() {

        $scope.organization = Organization.get({
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
            }, function() {});
        });
    };

    $scope.updateVoiceCallOpt = function() {

        $scope.organization = Organization.get({
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
            }, function() {});
        });
    };
    
    // click function for 'save details' button in incoming call settings
    $scope.updateIncomingCallOpt = function() {

        $scope.organization = Organization.get({
            id: orgid
        }, function() {

            //make changes in the $resource object
            $scope.organization.inboundCallAskOrder = $scope.incomingCheckBoxOptions.order;
            $scope.organization.inboundCallAskFeedback = $scope.incomingCheckBoxOptions.feedback;
            $scope.organization.inboundCallAskResponse = $scope.incomingCheckBoxOptions.response;

            //finally update the database
            $scope.organization.$update({
                id: orgid
            }, function() {});
        });
    };
    
    // click function for 'save details' button in outgoing call settings
    $scope.updateOutgoingCallOpt = function() {

        $scope.outboundcall = OutboundCall.get({
            id: orgid
        }, function() {

	        //make changes in the $resource object
	        $scope.outboundcall.askOrder = $scope.outgoingCheckBoxOptions.order;
	        $scope.outboundcall.askFeedback = $scope.outgoingCheckBoxOptions.feedback;
	        $scope.outboundcall.askResponse = $scope.outgoingCheckBoxOptions.response;
            
            //finally update the database
            $scope.outboundcall.$update({
                id: orgid
            }, function() {});
        });
    };
    
    
    
});