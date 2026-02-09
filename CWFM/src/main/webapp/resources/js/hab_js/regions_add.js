var base_url = document.getElementById('base_url').value.trim();
function set_date() {
	date = jQuery('#add_sp_startdate_id').datepicker('getDate');
	change_date_format(date, 'hidden_add_sp_startdate_id');
}
function change_date_format(date, input_id) {
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	if (month < 10) {
		month = '0' + month;
	}
	if (day < 10) {
		day = '0' + day;
	}
	document.getElementById(input_id).value = year + '-' + month + '-' + day;

}
function back_to_regions() {
	var base_url = document.getElementById('base_url').value.trim();
	window.location.href = base_url + 'ctrl_regions';
}
function clearmessage() {
	document.getElementById('errormsg').innerHTML = '';
}
function add_page() {
	// findUnique();
	FormValidation();
	if (FormValidation()) {
		document.getElementById('regions_addform').submit();
	}
}
function isNumberValue(el, evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode != dec_charCode && charCode > 31
			&& (charCode < 48 || charCode > 57)) {
		return false;
	}
	if (charCode == dec_charCode && el.value.indexOf(dec_point) !== -1) {
		return false;
	}
	return true;
}
function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    else
        return true;
}
function hyphen_generate(evt, value, id) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (value.length === 3 && charCode !== 8)
		document.getElementById(id).value = value.concat('-');
	if (value.length === 7 && charCode !== 8)
		document.getElementById(id).value = value.concat('-');
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode === 43 || charCode === 45 || charCode > 31
			&& (charCode < 48 || charCode > 57))
		return false;
	else
		return true;
}
function nospecialchar(evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode !== 32 && charCode !== 39 && charCode !== 46
			&& charCode !== 45 && charCode !== 45 && charCode > 31
			&& (charCode < 48 || charCode > 57)
			&& (charCode < 65 || charCode > 90)
			&& (charCode < 97 || charCode > 122)) {
		return false;
	} else {
		return true;
	}
}
function state_Name(country_name) {
	   //var atrbtypeid = document.getElementById('default_attribute_type').value;
	   //var atrbttype_ind = atrbt_type.split('(~)');
	   if (country_name = 'add_country_name') {
	      document.getElementById('stateId').innerHTML = 'State<span>*</span>:';
	   } else {
		   document.getElementById('stateId').innerHTML = '<span> </span>';
	   }
}
function isLetter(evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)
			&& (charCode < 65 || charCode > 90)
			&& (charCode < 97 || charCode > 122)) {
		return false;
	} else {
		return true;
	}
}
(function() {
	var previous;

	$("select[name=add_country_name]").focus(function() {
		// Store the current value on focus, before it changes
		previous = this.value;
	}).change(
			function() {
				// Do soomething with the previous value after the change
				document.getElementById("log").innerHTML = "<b>Previous: </b>"
						+ previous;

				previous = this.value;
			});
})();