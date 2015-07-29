website.controller("OrganizationCtrl", function($scope, $http, $routeParams, $window) {

	// get the current organization Attributes
	var orgid = $('#organizationId').val();
	var abbr = $('#organizationAbbr').val();
	var userid = document.getElementById("organization-page-ids").getAttribute("data-userid");

	$scope.test = [{
		name: 'Disable',
		value: '0'
	}, {
		name: 'Enable',
		value: '1'
	}];


	
});