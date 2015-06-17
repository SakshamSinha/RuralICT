'use strict';

var website = angular.module('ruralIvrs', ['ngRoute', 'ngResource', 'angular-loading-bar'])

website.config(['$routeProvider', '$provide', '$httpProvider', 'cfpLoadingBarProvider', function($routeProvider, $provide, $httpProvider, cfpLoadingBarProvider) {

	$routeProvider
		.when('/home', {templateUrl: 'homePage', title: "Home"})
		.when('/products', {templateUrl: 'productsPage', title: "Products"})
		.when('/productquantity', {templateUrl: 'productQuantityPage', title: "Product Quantity"})
		.when('/orderSummaryProducts', {templateUrl: 'orderSummaryProductPage', title: "Order Summary(products)"})
		.when('/orderSummaryGroups', {templateUrl: 'orderSummaryGroupPage', title: "Order Summary(groups)"})
		.when('/users', {templateUrl: 'usersPage', title: "Users"})
		.when('/settings', {templateUrl: 'settingsPage', title: "Settings"})
		.when('/group/:groupId', {templateUrl: function(params){ return 'groupPage/' + params.groupId; }, title: "Group Operations"})
		.otherwise({redirectTo: '/home'});

	$provide.factory('myHttpInterceptor', function($q) {
		return {
			'response': function(response) {
				if ($(response.data).filter('title').text().search('Login') != -1) {
					/*
					 * Assuming the login page's title has the word 'Login' in it. Is there a better way without losing
					 * auto-redirect to /login ?  --Ankit
					 */
					document.location.reload(true);
					return $q.reject(response);
				}
				return response || $q.when(response);
			},

			'responseError': function(rejection) {
				if (rejection.status == 403) {
					// TODO: Access denied
				}
				return $q.reject(rejection);
			}
		};
	});

	$httpProvider.interceptors.push('myHttpInterceptor');

	cfpLoadingBarProvider.includeSpinner = false;
}]);

website.run(['$location', '$rootScope', function($location, $rootScope) {
	$rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
		if(current.$$route) {
			$rootScope.title = current.$$route.title;
		}
	});
}]);