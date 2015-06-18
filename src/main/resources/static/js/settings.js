/**
 *  Javascript file for the Settings Controller
 */
website.factory("Organization", function($resource) {
    return $resource("/api/organizations/:id", {
        id: '@id'
    }, {
        query: {
            method: "GET",
            isArray: false
        },
        update: {
            method: "PATCH",
            params: {
                id: '@id'
            }
        }
    });
});

website.controller("SettingsCtrl", function($scope, $routeParams, Organization) {

    // get the current organization id
    var orgid = document.getElementById("settings-page").getAttribute("orgid");

    $scope.selectOptions = [{
        name: 'Disable(बंद करना)',
        value: '0'
    }, {
        name: 'Enable(चालू करना)',
        value: '1'
    }];

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
});