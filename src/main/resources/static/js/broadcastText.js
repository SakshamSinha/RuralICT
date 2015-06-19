/* Javascript file for the Text Broadcast View Controller */
 
website.factory("Broadcast", function($resource) {
    return $resource("/api/broadcasts/:id");
});

website.factory("BroadcastRecipient", function($resource) {
    return $resource("/api/broadcastRecipient/:id", {
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

website.controller("textBroadcastCtrl", function($scope, $http, $routeParams, Broadcast, OutboundCall) {

    // get the current organization id
    //var orgid = document.getElementById("settings-page").getAttribute("orgid");
    //var outboundcallid = document.getElementById("settings-page").getAttribute("orgid");
    
	$scope.radioOptions = [false,false,false,false];
	
    
    $scope.saveTextBroadcastBtn = function() {
        
    	//create broadcast object
        $scope.broadcast = new Broadcast();
        
        // insert values into object
        var currentValue =  $scope.radioValues;
        
        // call the spring controller for the 'sendSMS' function of IVRSBase
        var data = 'message=' + $scope.textContent + '&broadcastid=3';
        
        $http.defaults.headers.post["Content-Type"] = 'application/x-www-form-urlencoded; charset=UTF-8';
        
        $http.post('/web/iitb/textBroadcast', data).
        success(function(data, status, headers, config) {
             console.log("data passed successfully");
        }).
        error(function(data, status, headers, config) {
          
        });
        
        
        
        $scope.radioOptions[currentValue]=true;
        
        console.log("order radio = " + $scope.radioOptions[0]);
        console.log("feedback radio = " + $scope.radioOptions[1]);
        console.log("response radio = " + $scope.radioOptions[2]);
        console.log("noresponse radio = " + $scope.radioOptions[3]);
        console.log("current value = " + currentValue);
        
        $scope.radioOptions[currentValue]=false;
        
    };
    
    
    
    
    
});