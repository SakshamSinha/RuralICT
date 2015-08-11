function redirectForGenerateBill(){
	var groupDropDownList = document.getElementById("groupDropDownList");
	var groupId = groupDropDownList.options[groupDropDownList.selectedIndex].value;
	var url = "generateBillGroup/"+groupId;
	var win = window.open(url, '_blank');
	win.focus();
}