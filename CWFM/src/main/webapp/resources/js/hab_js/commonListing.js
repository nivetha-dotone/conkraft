
function selectedrow(id) {
	document.getElementById('selectedRecordIdId').value = id;
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

function validateAction(actionPath) {
	var selectedId = document.getElementById('selectedRecordIdId').value;
	if(selectedId=="") {
		var p_s_record = document.getElementById('p_s_record').value;
		alert(p_s_record);
	} else {
		var form = document.getElementById('selectedFromId');//alert(form);
		form.action = document.getElementById('base_url').value+actionPath;
		form.submit();
	}
}
function exportData(actionPath) {
	 var form = document.getElementById('selectedFromId');
	 form.action = document.getElementById('base_url').value+actionPath;
	 form.submit();
	}