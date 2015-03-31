'use strict';

var website = angular.module('ruralIvrs', ['ngRoute', 'angular-loading-bar'])

website.config(['$routeProvider', 'cfpLoadingBarProvider', function($routeProvider, cfpLoadingBarProvider) {

	$routeProvider
		.when('/home', {templateUrl: 'home', title: "Home"})
		.when('/products', {templateUrl: 'products', title: "Products"})
		.when('/group', {templateUrl: 'group', title: "Group Operations"})
		.when('/users', {templateUrl: 'users', title: "Users"})
		.when('/settings', {templateUrl: 'settings', title: "Settings"})
		.otherwise({redirectTo: '/home', title: "Home"});

	cfpLoadingBarProvider.includeSpinner = false;
}]);

website.run(['$location', '$rootScope', function($location, $rootScope) {
	  
	$rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
		$rootScope.title = current.$$route.title;
	    });
}]);


