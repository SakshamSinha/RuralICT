/**
 * This contains the group operations such as, add new, sort, etc.
 */

//add new group from index.html
$("#add-new-group").click(function() {  
    var groupName = $.trim($('#new-group-name-input').val());
    var ul = document.getElementById("group-names");
	var li = document.createElement("li");
	var new_group_li = $("<a href=\"#/groups\">"+groupName+"</a>");
	
	new_group_li.appendTo(ul);
});