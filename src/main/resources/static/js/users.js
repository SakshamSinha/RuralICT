/**
 * This file contains all the functionalities related to users
 */

//adding new user users.html
$('#add-new').click(function(e) {  
    e.preventDefault();
    var name = $.trim($('#new-user-name-input').val());
    var email = $.trim($('#new-user-email-input').val());
    var phone = $.trim($('#new-user-phone-input').val());
    var address = $.trim($('#new-user-address-input').val());
    var role = $.trim($('#new-user-role-input').val());
    
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