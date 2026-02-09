

function validateAction(actionPath){
	var selectedId = document.getElementById('selectedRecordIdId').value;
	if(selectedId=="") {
		var p_s_record = document.getElementById('p_s_record').value;
		alert(p_s_record);
	}
	else {
		var form = document.getElementById('loginhistorydeleteform');
		form.action = document.getElementById('base_url').value+actionPath;
		form.submit();alert("record deleting");
	}
}

function uncheckRadioButton() {
    var rbs = document.getElementsByName('radio');
    var len = rbs.length;
    for (var i = 0; i < len; i++) {
        if (rbs[i].checked) {
            rbs[i].checked = false;
        }
    }
}
function delete_page() {
	var deleteDependency = document.getElementById('deleteDependencyValue').value;
	if(deleteDependency == "False") {
		return false;
	} else {
		document.getElementById('userDeleteViewform').submit();
	}
}