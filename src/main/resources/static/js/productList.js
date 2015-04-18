website.factory("Product", function($resource) {
	return $resource("/app/api/products/:id");
});

website.controller("ProductsCtrl", function($scope, $route, Product) {

	$scope.saveProduct = function(data) {
		var product = new Product(data);
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

		    $scope.productName = product.name;
			$("#product-add-success-modal").modal('toggle');
		}, function(error) {
			$scope.failure = error.data;
			$("#product-add-failed-modal").modal('toggle');
		});
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

//deleting a product entry
$("#page-content").on("click", "#btnYesDelete", function(e) {  
    e.preventDefault();
    $(this).parent('tr').remove();
});
   
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

    angular.element($('#add-new-product')).scope().saveProduct(data);

    $('#add-product-modal').modal('toggle');

	$('#new-product-input').val("");
    $('#new-product-type-input').val("");
    $('#new-price-input').val("");
});

//Dynamic modal for edit
$("#page-content").on("click", ".open-edit-modal", function () {
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

});
