'use strict';

var website = angular.module('ruralIvrs', ['ngRoute', 'angular-loading-bar'])

website.config(['$routeProvider', 'cfpLoadingBarProvider', function($routeProvider, cfpLoadingBarProvider) {

	$routeProvider
		.when('/home', {templateUrl: 'home.html', title: "Home"})
		.when('/products', {templateUrl: 'productList.html', title: "Products"})
		.when('/groups', {templateUrl: 'groupOperations.html', title: "Group Operations"})
		.when('/users', {templateUrl: 'users.html', title: "Users"})
		.when('/settings', {templateUrl: 'settings.html', title: "Settings"})
		.otherwise({redirectTo: '/home', title: "Home"});

	cfpLoadingBarProvider.includeSpinner = false;
}]);

website.run(['$location', '$rootScope', function($location, $rootScope) {
	  
	$rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
		$rootScope.title = current.$$route.title;
	    });
}]);


