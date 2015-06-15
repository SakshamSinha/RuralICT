website.factory("PresetQuantityCreate",function($resource){
	return $resource("/api/presetQuantities");
});

website.factory("PresetQuantityEdit",function($resource){
	
	return $resource("/api/presetQuantities/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
	    update: {method: "PATCH",params: {id: '@id'}}
	});
});

website.factory("PresetQuantityDelete",function($resource){
	return $resource("/api/presetQuantities/:id",{id:'@id'},{
		query: { method: "GET", isArray: false },
	    update: {method: "DELETE",params: {id: '@id'}}
	});
});

website.controller("PresetQuantitiesCtrl",function($scope, $http, $route, $location, PresetQuantityCreate, PresetQuantityEdit, PresetQuantityDelete) {
		
		var id;
		$scope.savePresetQuantity = function(data){
			$scope.presetQuantity = new PresetQuantityCreate();
			console.log("new product has been created");
			$scope.presetQuantity.quantity = data.quantity;
		    $scope.presetQuantity.productType = data.productType;
		    $scope.presetQuantity.organization = data.organization;
		    PresetQuantityCreate.save($scope.presetQuantity,function(){
				console.log("Done done");
			});
		}
		
		$scope.editPresetQuantity = function(value){
			
			$scope.presetQuantity = PresetQuantityEdit.get({id:$scope.id},function(){
				$scope.presetQuantity.quantity = value;
				console.log($scope.presetQuantity.quantity);
				console.log("Record to be updated fetched");
				console.log($scope.id);
				$scope.presetQuantity.$update({id:$scope.id},function(){
					console.log("Edit done");
				});
			});
		}
		
		$scope.deletePresetQuantity = function(){
			$scope.presetQuantity = PresetQuantityDelete.get({id:$scope.id},function(){
				
				$scope.presetQuantity.$update({id:$scope.id},function(){
					console.log("Record actually deleted");
				});
			});
			
		}
		
		$scope.setId = function(presetQuantityId){
			$scope.id=presetQuantityId; 
			console.log($scope.id);
		}
	
});


$("#page-content").on("click", "#producttable #checkall", function () {
    if ($("#producttable #checkall").is(':checked')) {
        $("#producttable input[type=checkbox]").each(function () {
            $(this).prop("checked", true);
        });
    } else {
        $("#producttable input[type=checkbox]").each(function () {
            $(this).prop("checked", false);
        });
    }
});

//adding new product
$("#page-content").on("click", "#add-new-quantity", function(e) {
    //e.preventDefault();
	console.log("Reaching here!!")
    var quantity = $.trim($('#new-quantity-input').val());
    var productType = $('#new-presetqty-product-type-input').val();
    var organizationId= $('#product-quantity').attr('organizationId');
    var organization = "organizations/"+organizationId;
    console.log(organization);
    console.log(productType);
    var data = {};
    data.name = quantity;
    data.productType = productType;
    data.organization = organization;
    console.log("Add product has been called");
    angular.element($('#add-new-quantity')).scope().savePresetQuantity(data);

    $('#add-qty-modal').modal('toggle');
	$('#new-quantity-input').val("");
    $('#new-presetqty-product-type-input').val("");
});


//deleting a product entry
$("#page-content").on("click", "#btn-qty-delete", function(e) {  
    e.preventDefault();
    presetQuantityId = $(this).attr("presetQuantityId");
	console.log(presetQuantityId);
	angular.element(this).scope().setId(presetQuantityId);

});

$("#page-content").on("click","#delete-qty",function(e){
	angular.element(this).scope().deletePresetQuantity();
	$("#delete-qty-modal").modal('toggle');		
});

//Dynamic modal for edit
$("#page-content").on("click", "#btn-qty-edit", function () {
	presetQuantityId = $(this).attr("presetQuantityId");
	console.log(presetQuantityId);
	presetQuantityType = $(this).attr("presetQuantityType");
	console.log(presetQuantityType);
	angular.element(this).scope().setId(presetQuantityId);
	$(".modal-header #HeadingEdit").html("Edit "+presetQuantityType+"preset quantity");
	$(".modal-body #update-quantity-input").html(presetQuantityType);
	
});

$("#page-content").on("click","#update-qty",function(e){
	e.preventDefault();
	value = $.trim($('.controls #update-quantity-input').val());
	console.log($('.controls #update-quantity-input').val());
	angular.element(this).scope().editPresetQuantity(value);
	$("#edit-qty-modal").modal('toggle');
	$('#update-quantity-input').val("");
});
