/**
 * This file contains all the functionalities related to users
 */

//adding new user users.html
$("#page-content").on("click", "#add-new-user", function(e) {  
    e.preventDefault();
    var name = $.trim($('#new-user-name-input').val());
    console.log(name)
    var email = $.trim($('#new-user-email-input').val());
    console.log(email)
    var phone = $.trim($('#new-user-phone-input').val());
    console.log(phone)
    var address = $.trim($('#new-user-address-input').val());
    console.log(address)
    var role = $.trim($('#new-user-role-input').val());
    console.log(role)
    
    var new_row = $('<tr>\
    <td><input type="checkbox" class="checkthis" /></td>\
    <td>'+name+'</td>\
    <td>'+email+'</td>\
    <td>'+phone+'</td>\
 <td>'+role+'</td>\
 <td>'+address+'</td>\
 <td><p data-placement="top" data-toggle="tooltip" title="Edit">\
    <button class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" name = "'+name+'" email = "'+email+'" phone="'+phone+'" role="'+role+'" city="'+address+'"data-target="#edit" >\
    <i class="icon-white icon-pencil"></i>Edit</button></p></td>\
    <td><p data-placement="top" data-toggle="tooltip" title="Delete">\
    <button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" >\
    <i class="icon-white icon-trash"></i>Delete</button></p></td>\
</tr>');
    
    new_row.appendTo($('#usertable'));
    
    $('#new-user-name-input').val("");
    $('#new-user-address-input').val("");
    $('#new-user-email-input').val("");
    $('#new-user-phone-input').val("");
    $('#new-user-role-input').val("");
   
});