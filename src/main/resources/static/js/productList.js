website.factory("ProductCreate",function($resource){
	return $resource("/api/products",{
		query: {method: "GET", isArray: false}
	});
});

website.factory("ProductEdit",function($resource){	
	return $resource("/api/products/:id", {id: '@id'}, {
		query: { method: "GET", isArray: false },
	    update: {method: "PATCH",params: {id: '@id'}}
	});
});

website.factory("ProductDelete",function($resource){
	return $resource("/api/products/:id",{id:'@id'},{
		query: { method: "GET", isArray: false },
	    update: {method: "DELETE",params: {id: '@id'}}
	});
});

website.factory("ProductListGet",function($resource){
	return $resorce("/api/products",{
		query: {method: "GET", isArray: true}
	});
});

website.controller("ProductsCtrl",function($scope, $http, $route, $location, ProductCreate, ProductEdit, ProductDelete) {
		
		var id;
		//function to save product
		$scope.saveProduct = function(data){
			$scope.product = new ProductCreate();
			$scope.product.name = data.name;
			$scope.product.unitRate = data.unitRate;
		    $scope.product.productType = data.productType;
			ProductCreate.save($scope.product,function(){
				console.log("Done done");
			});
		}
		//function to edit product
		$scope.editProduct = function(value){
			console.log($scope.id);
			$scope.product = ProductEdit.get({id:$scope.id},function(){
				$scope.product.unitRate = value;
				console.log($scope.product.unitRate);
				console.log("Record to be updated fetched");
				$scope.product.$update({id:$scope.id},function(){
					console.log("Edit done");
				});
			});
		}
		//function to delete product
		$scope.deleteProduct = function(data){
			$scope.product = ProductDelete.get({id:$scope.id},function(){				
				$scope.product.$update({id:$scope.id},function(){
					console.log("Record actually deleted");
				});
			});
			
		}
		//function to set id attribute
		$scope.setId = function(productId){
			$scope.id=productId; 
			console.log($scope.id);
			
		}
		//function to fetch list of products
		$scope.getList = function(){
			$scope.products = ProductCreate.query(function() {
			    console.log($scope.products);
			  }); 
		}
});
/*
$("#page-content").ready(function(){
	angular.element(this).scope().getList();
	
});*/

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

//add new product on clicking the add button
$("#page-content").on("click", "#add-new-product", function(e) {
    e.preventDefault();
    var product = $.trim($('#new-product-input').val());
    var price = $.trim($('#new-price-input').val());
    var productType = $('#new-product-type-input').val();
    var data = {};
    data.name = product;
    data.unitRate = price;
    data.productType = productType;
    console.log("Add product has been called");
    angular.element($('#add-new-product')).scope().saveProduct(data);
    
    $('#add-product-modal').modal('toggle');

	$('#new-product-input').val("");
    $('#new-product-type-input').val("");
    $('#new-price-input').val("");
});

//capture the id of product on clicking the delete button
$("#page-content").on("click", "#btn-delete", function(e) {  
    e.preventDefault();
    productId = $(this).attr("productid");
	console.log(productId);
	angular.element(this).scope().setId(productId);
	angular.element(this).scope().getList();
});

//delete a product entry on clicking the 'yes' delete button
$("#page-content").on("click","#delete-product",function(e){
	angular.element(this).scope().deleteProduct();
	$("#delete-product-modal").modal('toggle');
	
});

//capture the id of product on clicking the edit button
$("#page-content").on("click", "#btn-edit", function () {
	
	productId = $(this).attr("productId");
	console.log(productId);
	productName = $(this).attr("productName");
	console.log(productName);
	angular.element(this).scope().setId(productId);
	$(".modal-header #HeadingEdit").html("Edit "+productName+"'s price");
	$(".modal-body #update-product-input").html(productName);

});

//update the product on clicking the update button in edit modal
$("#page-content").on("click","#update-product",function(e){
	value = $.trim($('#update-price-input').val());
	angular.element(this).scope().editProduct(value);
	$("#edit-product-modal").modal('toggle');
	$('#update-price-input').val("");
});

