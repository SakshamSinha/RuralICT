website.controller("ProductsCtrl",function($window, $scope, $http, $route, $location, ProductCreate, ProductEdit, ProductDelete) {
		
		var id;
		
		var abbr = $('#organizationAbbr').val();
		
		// Initialize the table
		$http.get( API_ADDR + 'api/products/search/productlist?abbr=' + abbr + '&projection=productproj').
			success(function(data, status, headers, config) {
				
				
				// Store the data into Angular scope model variable
				$scope.products = data._embedded.products;
			}).
			error(function(data, status, headers, config) {
				createAlert("Error Fetching Data","There was some error in response from the remote server.");
			});
		
		//function to save product
		$scope.saveProduct = function(data){
			$scope.newproduct = new ProductCreate();
			$scope.newproduct.name = data.name;
			$scope.newproduct.unitRate = data.unitRate;
		    $scope.newproduct.productType = data.productType;
			ProductCreate.save($scope.newproduct,function(){
				$scope.products.push($scope.newproduct);
				createAlert("Success","The new Product was added Successfully.");
			},function(error){
				if (error.status == "409")
					createAlert("Error Adding Product","Product already added. Add a different product");
			});
		}
		//function to edit product
		$scope.editCurrentProduct = function(product){
			
			$scope.id = this.product.productId;
			
			$(".modal-header #HeadingEdit").html("Edit Product");
			$(".modal-body #update-product-input").html("Price");
			$(".modal-body #update-product-list-input-name").html("Name");
			$("#update-product-name-input").val(this.product.name);
			$("#update-price-input").val(this.product.unitRate);
			
			$scope.updateEditCurrentProduct = function() {
			
				newprice = $.trim($('#update-price-input').val());
				newname  = $.trim($('#update-product-name-input').val());
				
				if(! $.isNumeric(newprice) ){
					createAlert("Invalid Input","Enter valid Price input as numerical value.");
				}
				else if(newprice < 0)
				{
					createAlert("Negative Input not allowed.");
				}
				else
				{
					
					$scope.editproduct = ProductEdit.get({id:$scope.id},function(){
						$scope.editproduct.unitRate = newprice;
						$scope.editproduct.name = newname;
						$scope.editproduct.$update({id:$scope.id},function(){
							product.unitRate = $scope.editproduct.unitRate;
							product.name = $scope.editproduct.name;
						});
					});
					
					$("#edit-product-modal").modal('toggle');
					$('#update-price-input').val("");
					$('#update-product-name-input').val("");
				}
			
			}
			
			
		}
		//function to delete product
		$scope.deleteCurrentProduct = function(product){
			
			$scope.id = this.product.productId;
			
			$scope.yesButtonDeleteProduct = function()
			{
				$scope.deleteproduct = ProductDelete.get({id:$scope.id},function(){				
					$scope.deleteproduct.$update({id:$scope.id},function(){
						
						// refetch the data
						$http.get( API_ADDR + 'api/products/search/productlist?abbr=' + abbr + '&projection=productproj').
						success(function(data, status, headers, config) {
							
							
							// Store the data into Angular scope model variable
							$scope.products = data._embedded.products;
						}).
						error(function(data, status, headers, config) {
							createAlert("Error Fetching Data","There was some error in response from the remote server.");
						});
						
						$("#delete-product-modal").modal('toggle');
						
					},function(error){
						if(error.status == "409")
							createAlert("Error Deleting Product","You can't delete this product.");
					});
				});	
			}
		}
		
		//function to fetch list of products
		$scope.getList = function(){
			
			$http.get( API_ADDR + 'api/products/search/productlist?abbr=' + abbr + '&projection=productproj').
			success(function(data, status, headers, config) {
				
				
				// Store the data into Angular scope model variable
				$scope.products = data._embedded.products;
			}).
			error(function(data, status, headers, config) {
				createAlert("Error Fetching Data","There was some error in response from the remote server.");
			});
			
		}
		
		//TODO hard refresh has to be eliminated
		$scope.reload = function(){
			setTimeout($window.location.reload.bind(window.location),2000);
		}
});

//add new product on clicking the add button
$("#page-content").on("click", "#add-new-product", function(e) {
	e.preventDefault();
	var price = $.trim($('#new-price-input').val());
	var productType = $.trim($('#new-product-type-input').val());
	var product = $.trim($('#new-product-input').val());
	if(! $.isNumeric(price)||price<0){
		createAlert("Invalid Input","Please enter valid price input as positive numerical value.");
	}
	else if(product == ''){
		createAlert("Invalid Input","Please enter a name for Product");
	}
	else if(productType == ""){
		createAlert("Invalid Input","Please select one of the Product Type(s)");
	}
	else
	{
		var data = {};
		data.name = product;
		data.unitRate = price;
		data.productType = productType;
		angular.element($('#add-new-product')).scope().saveProduct(data);
		$('#add-product-modal').modal('toggle');
		$('#new-product-input').val("");
		$('#new-product-type-input').val("");
		$('#new-price-input').val("");
	}	
});