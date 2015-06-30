website.controller("PresetQuantitiesCtrl",function($window, $scope, $http, $route, $location, PresetQuantityCreate, PresetQuantityEdit, PresetQuantityDelete) {
		
		var id;
		//function to save the preset quantity 
		$scope.savePresetQuantity = function(data){
			$scope.presetQuantity = new PresetQuantityCreate();
			$scope.presetQuantity = data;
			console.log("Receiving quantity at savePresetQuantity "+ $scope.presetQuantity.quantity);
			PresetQuantityCreate.save($scope.presetQuantity,function(){	
			},function(error){
				if (error.status == "409")
					alert("Same Product Quantity already present. Add a different quantity");
			});
		}
		
		//function to edit the preset quantity
		$scope.editPresetQuantity = function(value){
			
			$scope.presetQuantity = PresetQuantityEdit.get({id:$scope.id},function(){
				$scope.presetQuantity.quantity = value;
				$scope.presetQuantity.$update({id:$scope.id},function(){
				},function(error){
					console.log(error);
					if (error.status == "409")
						alert("Same Product Quantity already present. Update with different quantity not already present.");
				});
			});
		}
		
		//function to delete the preset quantity 
		$scope.deletePresetQuantity = function(){
			$scope.presetQuantity = PresetQuantityDelete.get({id:$scope.id},function(){
				$scope.presetQuantity.$update({id:$scope.id},function(){
				});
			});
			
		}
		//function to set the preset quantity id
		$scope.setId = function(presetQuantityId){
			$scope.id=presetQuantityId; 
		}
		
		//TODO hard refresh has to be eliminated
		$scope.reload = function(){
			setTimeout($window.location.reload.bind(window.location),2000);
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

//adding new product on pressing the 'Add new product' button
$("#page-content").on("click", "#add-new-quantity", function(e) {
	var quantity = $.trim($('#new-quantity-input').val());
	var productType = $('#new-presetqty-product-type-input').val();
	var organizationId= $('#product-quantity').attr('organizationId');
	var organization = "organizations/"+organizationId;
	console.log("Receiving quantity: "+ quantity);
	var data = {};
	data.quantity = quantity;
	data.productType = productType;
	data.organization = organization;
	angular.element($('#add-new-quantity')).scope().savePresetQuantity(data);
	$('#add-qty-modal').modal('toggle');
	$('#new-quantity-input').val("");
	$('#new-presetqty-product-type-input').val("");
	//TODO Eliminating this function doing hard refresh
	angular.element($('#add-new-quantity')).scope().reload();
});


//capturing the preset quantity id on pressing the delete button
$("#page-content").on("click", "#btn-qty-delete", function(e) {  
	e.preventDefault();
	presetQuantityId = $(this).attr("presetQuantityId");
	angular.element(this).scope().setId(presetQuantityId);
});

//deleting a quantity entry on pressing the 'yes' delete button
$("#page-content").on("click","#delete-qty",function(e){
	angular.element(this).scope().deletePresetQuantity();
	$("#delete-qty-modal").modal('toggle');
	//TODO Eliminating this function doing hard refresh
	angular.element(this).scope().reload();
});

//capture the id on clicking the edit button
$("#page-content").on("click", "#btn-qty-edit", function () {
	presetQuantityId = $(this).attr("presetQuantityId");
	presetQuantityType = $(this).attr("presetQuantityType");
	angular.element(this).scope().setId(presetQuantityId);
	$(".modal-header #HeadingEdit").html("Edit "+presetQuantityType+"preset quantity");
	$(".modal-body #update-quantity-input").html(presetQuantityType);
	
});
//update quantity on clicking the update button
$("#page-content").on("click","#update-qty",function(e){
	e.preventDefault();
	value = $.trim($('.controls #update-quantity-input').val());
	angular.element(this).scope().editPresetQuantity(value);
	$("#edit-qty-modal").modal('toggle');
	$('.controls #update-quantity-input').val("");
	//TODO Eliminating this function doing hard refresh
	angular.element(this).scope().reload();
});
