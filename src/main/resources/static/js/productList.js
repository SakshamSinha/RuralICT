website.factory("ProductCreate",function($resource){
	return $resource("/api/products");
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

website.filter('pages', function () {
  return function (input, currentPage, totalPages, range) {
    currentPage = parseInt(currentPage);
    totalPages = parseInt(totalPages);
    range = parseInt(range);

    var minPage = (currentPage - range < 0) ? 0 : (currentPage - range > (totalPages - (range * 2))) ? totalPages - (range * 2) : currentPage - range;
    var maxPage = (currentPage + range > totalPages) ? totalPages : (currentPage + range < range * 2) ? range * 2 : currentPage + range;

    for(var i=minPage; i<maxPage; i++) {
      input.push(i);
    }

    return input;
  };
});

website.controller("ProductsCtrl",function($scope, $http, $route, $location, ProductCreate, ProductEdit, ProductDelete) {
		
		
		$scope.saveProduct = function(data){
			alert("reached here");
			$scope.product = new ProductCreate();
			$scope.product.name = data.name;
			$scope.product.unitRate = data.unitRate;
		    $scope.product.productType = data.productType;
			ProductCreate.save($scope.product,function(){
				console.log("Done done");
			});
		}
		
		$scope.editProduct = function(data){
			$scope.id = data.productId;
			console.log($scope.id);
			$scope.product = ProductEdit.get({id:$scope.id},function(){
				$scope.product.unitRate = data.unitRate;
				console.log($scope.product.unitRate);
				$scope.product.$update({id:$scope.id},function(){
					console.log("Edit done");
				});
			});
		}
		
		$scope.deleteProduct = function(data){
			$scope.id = data.productId;
			console.log($scope.id);
			$scope.product = ProductDelete.get({id:$scope.id},function(){
				console.log("Record to be deleted fetched");
				$scope.product.$update({id:$scope.id},function(){
					console.log("Record actually deleted");
				});
			});
			
		}
		var search = $location.search();
		var page = search.page||0;
		var size = search.size||20;
		var sort = search.sort||'type,desc';
		 
		$http({method: 'GET', url: '/productPages?page=' + page + '&size=' + size + '&sort=' + sort})
		     .success(function(data) {
		    	 console.log("Reaching HTTP success portion here!!")
		        $scope.widgets = data.content;
		        $scope.page = data.page;
		        $scope.sort = sort;
		 
		        angular.forEach(data.links, function(value) {
		          if(value.rel === 'next') {
		            $scope.nextPageLink = value.href;
		          }
		 
		          if(value.rel === 'prev') {
		            $scope.prevPageLink = value.href;
		          }
		        });
		      })
		      .error(function(error) {
		    	  console.log("Reaching HTTP failure portion here!!")
		        $scope.widgetsError = error;
		});
		/*
		$scope.saveProduct = function(data) {
		alert("reached here");
		var product = new Product(data);
		//var product = data;
		
		product.$save(function(product) {
			
			var new_row = $('<tr>\
			      <td><input type="checkbox" class="checkthis"/></td>\
			      <td>'+product.name+'</td>\
			      <td>'+product.unitRate+'</td>\
			      <td>\
			        <p data-placement="top" data-toggle="tooltip" title="Edit">\
			          <button class="open-edit-modal btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" data-target="#edit">\
			          <i class="icon-white icon-pencil"></i> Edit</button>\
			        </p>\
			      </td>\
			      <td>\
			        <p data-placement="top" data-toggle="tooltip" title="Delete">\
			          <button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete">\
			          <i class="icon-white icon-trash"></i> Delete</button>\
			        </p>\
			      </td>\
			    </tr>');

		    new_row.appendTo($('#producttable > tbody'));
		    console.log("This has come till here atleast")
		    $scope.productName = product.name;
			$("#product-add-success-modal").modal('toggle');
		}, function(error) {
			$scope.failure = error.data;
			$("#product-add-failed-modal").modal('toggle');
		});
	}
	*/

});

$("#page-content").ready(function(){
	
	$('button').each(function() {
  	  $.each(this.attributes, function() {
  	    // this.attributes is not a plain object, but an array
  	    // of attribute nodes, which contain both the name and value
  	    if(this.specified) {
  	      console.log(this.name, this.value);
  	    }
  	  });
  	});
	//'<button class="btn btn-danger btn-xs" id='+ id+ 'data-title="Delete" data-toggle="modal" data-target="#delete-product-modal" >'
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

/*$("#page-content").on("click","#btnYesDelete",function(){
	$("#delete-product-modal").modal('toggle');	
});
*/

//adding new product
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
//deleting a product entry
$("#page-content").on("click", "#btn-delete", function(e) {  
    e.preventDefault();
    //$('#delete-product-modal').modal('toggle');
    data={};
    data.productId = $(this).attr("productid");
    console.log(data.productId);
    //alert($(this).attr("productid"));
    $("#page-content").on("click","#delete-product",function(e){
    		//e.preventDefault();
    		angular.element($('#delete-product')).scope().deleteProduct(data);
    		$("#delete-product-modal").modal('toggle');
    		//present.parent().parent().parent().remove();		
    });
    
    /*var uniqueId = this.id;
    console.log(this.id);
    $('.list').remove();*/
});
   
//Dynamic modal for edit
$("#page-content").on("click", "#btn-edit", function () {
	var data={};
	data.productId = $(this).attr("productid");
	console.log(data.productId);
	$("#page-content").on("click","#update-product",function(e){
		//e.preventDefault();
		data.unitRate = $.trim($('#update-price-input').val());
		angular.element($('#update-product')).scope().editProduct(data);
		$("#edit-product-modal").modal('toggle');
		$('#update-price-input').val("");
	});
	
	/*
    var productName = $(this).data('product');
    $(".modal-header #HeadingEdit").html("Edit "+productName+"'s price");
    $(".modal-body #update-product-input").html(productName);
    
    var price = $(this).data('price');
    var priceUpdate = $(this).data('price');
    $(".modal-body #update-price-input").val(price);
    
  //updating a product entry
    $('#update').click(function(e){
    	e.preventDefault();
    	var cost = $("#update-price-input").val();
    	document.getElementById("price0").innerHTML = cost;
    });
    */

});
