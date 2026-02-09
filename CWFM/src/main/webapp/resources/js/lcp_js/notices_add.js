/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function back_to_notices() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_notifications';
}
function validation() {
    var group = document.getElementById('add_notification_group').value;
    var perms = document.getElementById('add_notification_perm').value;
    var role = document.getElementById('add_notification_role').value;
    var user = document.getElementById('add_notification_usr').value;
    var noticetype = document.getElementById('add_notification_type').value;
    var notes = document.getElementById('add_notification_notes').value;
    notes = $.trim(notes);
    var val_msg = document.getElementById('val_msg').value;
    val_msg = $.trim(val_msg);
    var val_msg1 = document.getElementById('val_msg1').value;
    val_msg1 = $.trim(val_msg1);
    var val_msg2 = document.getElementById('val_msg2').value;
    if (group === "" && perms === "" && role === "" && user === "") {
        document.getElementById('errormsg').innerHTML = val_msg;
        return false;
    } else if (noticetype === "" || noticetype === null) {
        document.getElementById('errormsg').innerHTML = val_msg1;
        return false;
    } else if (notes === "" || notes === null) {
        document.getElementById('errormsg').innerHTML = val_msg2;
        return false;
    } else {
        document.getElementById("errormsg").innerHTML = "";
        return true;
    }
}
function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
function add_page() {
    validation();
    if (validation()) {
        document.getElementById('noticesadd').submit();
    }
}
