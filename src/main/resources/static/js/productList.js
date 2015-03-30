$(document).ready(function(){
$("#producttable #checkall").click(function () {
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
    
    //$("[data-toggle=modal]").tooltip();

//deleting a product entry
$('#btnYesDelete').on('click', function(e) {  
    e.preventDefault();
    $(this).parent('tr').remove();
});
   
//adding new product
$('#add-new').click(function(e) {  
    e.preventDefault();
    var product = $.trim($('#new-product-input').val());
    var price = $.trim($('#new-price-input').val());
    
    var new_row = $('<tr>\
    <td><input type="checkbox" class="checkthis" /></td>\
    <td>'+product+'</td>\
    <td>'+price+'</td>\
    <td><p data-placement="top" data-toggle="tooltip" title="Edit">\
    <button class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" data-product = "'+product+'" data-price = "'+price+'"data-target="#edit" >\
    <i class="icon-white icon-pencil"></i>Edit</button></p></td>\
    <td><p data-placement="top" data-toggle="tooltip" title="Delete">\
    <button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" >\
    <i class="icon-white icon-trash"></i>Delete</button></p></td>\
</tr>');
    
    new_row.appendTo($('#producttable'));
    
    $('#new-product-input').val("");
    $('#new-price-input').val("");
    alert("added");
   
});


//Dynamic modal for edit
$(document).on("click", ".open-edit-modal", function () {
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

function getProductName() {
    var product = document.getElementById("product0");
    
    return product;
}

function getProductPrice() {
    var price = document.getElementById("price0").val();
    
    return price;
}

	
});
