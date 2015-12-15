website.controller("ProductsTypeCtrl",function($window, $scope, $http, $route, $location, ProductTypeCreate, ProductTypeEdit, ProductTypeDelete) 
{		
		var id;
		var index;
		var abbr = $('#organizationAbbr').val();
		var productTypeList= [];
		//function to save product
		$scope.saveProductType = function(data){
			$scope.productType = new ProductTypeCreate();
			$scope.productType.name = data.name;
			$scope.productType.organization=data.organization;
			
			console.log('enters save func');
			console.log("data"+ data.organizationId);
			ProductTypeCreate.save($scope.productType,function(){
			},function(error){
				if (error.status == "409")
					createAlert("Error Adding Product type","Product type already added. Add a different product type.");
			});
		}
		//function to edit product type
		$scope.editProductType = function(value){
			$scope.productType = ProductTypeEdit.get({id:$scope.id},function(){
				$scope.productType.name = value;
				$scope.productType.$update({id:$scope.id},function(){
				});
			});
		}
		//function to delete product
		$scope.deleteProductType = function(){
			$scope.productType = ProductTypeDelete.get({id:$scope.id},function(){	
				console.log('scope.product type= '+$scope.productType);
				$scope.productType.$update({id:$scope.id},function(){
				},function(error){
					if(error.status == "409")
						createAlert("Error Deleting Product type","You can't delete this product type.");
				});
			});	
		}
		//function to set id attribute
		$scope.setId = function(productTypeId){
			$scope.id=productTypeId; 
			console.log("Assigned value id "+ productTypeId);
		}
		//function to fetch list of products
		$scope.getList = function(){
			$scope.producttypelists = ProductTypeCreate.query(function() {
			    
			  }); 
		}
		
		//TODO hard refresh has to be eliminated
		$scope.reload = function(){
			setTimeout(function(){$http.get( API_ADDR + 'api/productTypes/search/findByorganization_abbreviationIgnoreCase?abbr=' + abbr +'&projection=producttype').
			success(function(data, status, headers, config) {

				// Store the data into Angular scope model variable
				$scope.productTypeList = data._embedded.productTypes;
				console.log("ajax query fired again");
				console.log($scope.productTypeList);
			}).
			error(function(data, status, headers, config) {
				createAlert("Error Fetching Data","There was some error in response from the remote server.");
			})},2000);
		}
		
		$http.get( API_ADDR + 'api/productTypes/search/findByorganization_abbreviationIgnoreCase?abbr=' + abbr +'&projection=producttype').
		success(function(data, status, headers, config) {

			// Store the data into Angular scope model variable
			$scope.productTypeList = data._embedded.productTypes;
			console.log($scope.productTypeList);
		}).
		error(function(data, status, headers, config) {
			createAlert("Error Fetching Data","There was some error in response from the remote server.");
		});
		
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

//add new product type on clicking the add button
$("#page-content").on("click", "#add-new-type", function(e) {
	e.preventDefault();
	var name = $.trim($('#new-product-type-input').val());
	var organizationId= $('#product-type').attr('organizationId');
	var organization = "organizations/"+organizationId;
	var data = {};
	data.name = name;
	data.organization = organization;
	data.organizationId= organizationId;
	angular.element('#add-new-type').scope().saveProductType(data);
	$('#add-type-modal').modal('toggle');
	$('#new-type-input').val("");
	angular.element(this).scope().reload();
});

//capture the id of product on clicking the edit button
$("#page-content").on("click", "#btn-edit-type", function () {
	productTypeId = $(this).attr("productTypeId");
	type=$(this).attr("productTypeName");
	angular.element(this).scope().setId(productTypeId);
	console.log("product type name= "+type);
	$(".modal-header #HeadingEdit").html("Edit product type "+type);
	$(".modal-body #update").html(type);
});

//update the product type on clicking the update button in edit modal
$("#page-content").on("click","#update-type",function(e){
	value = $.trim($('#update-type-input').val());
	angular.element(this).scope().editProductType(value);
	$("#edit-type-modal").modal('toggle');
	//TODO Eliminating this function doing hard refresh
	angular.element(this).scope().reload();
});

//capture the id of product type on clicking the delete button
$("#page-content").on("click", "#btn-delete-type", function(e) {  
	//e.preventDefault();
	productTypeId = $(this).attr("productTypeId");
	console.log('id'+productTypeId);
	angular.element(this).scope().setId(productTypeId);
});

//delete a product type entry on clicking the 'yes' delete button
$("#page-content").on("click","#delete-type",function(e){
	angular.element(this).scope().deleteProductType();
	$("#delete-type-modal").modal('toggle');
	//TODO Eliminating this function doing hard refresh
	angular.element(this).scope().reload();
});





