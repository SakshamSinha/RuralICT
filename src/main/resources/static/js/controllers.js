'use strict';

var website = angular.module('ruralIvrs', ['ngRoute', 'angular-loading-bar'])

website.config(['$routeProvider', 'cfpLoadingBarProvider', function($routeProvider, cfpLoadingBarProvider) {

	$routeProvider
		.when('/home', {templateUrl: 'homePage', title: "Home"})
		.when('/products', {templateUrl: 'productsPage', title: "Products"})
		.when('/group', {templateUrl: 'groupPage', title: "Group Operations"})
		.when('/users', {templateUrl: 'usersPage', title: "Users"})
		.when('/settings', {templateUrl: 'settingsPage', title: "Settings"})
		.otherwise({redirectTo: '/home'});

	cfpLoadingBarProvider.includeSpinner = false;
}]);

website.run(['$location', '$rootScope', function($location, $rootScope) {
    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        if(current.$$route) {
            $rootScope.title = current.$$route.title;
        }
    });
}]);