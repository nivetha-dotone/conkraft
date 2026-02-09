/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

function cancel() {
    var base_url = document.getElementById('base_url').value;
    window.location.href = base_url+'ctrl_login';
}
function validation() {
    var new_password = document.getElementById('new_password_id').value;
    new_password = $.trim(new_password);
    var confirm_password = document.getElementById('confirm_password_id').value;
    confirm_password = $.trim(confirm_password);
    var val_msg1 = document.getElementById('val_msg1').value;
    val_msg1 = $.trim(val_msg1);
    var val_msg2 = document.getElementById('val_msg2').value;
    val_msg2 = $.trim(val_msg2);
    var val_msg3 = document.getElementById('val_msg3').value;
    val_msg3 = $.trim(val_msg3);
    var val_msg4 = document.getElementById('val_msg4').value;
    val_msg4 = $.trim(val_msg4);
    if (new_password.length == 0) {
        document.getElementById("errormsg").innerHTML = val_msg1;
        return false;
    } else if (new_password.length < 8) {
        document.getElementById("errormsg").innerHTML = val_msg2;
        return false;
    } else if (confirm_password.length == 0) {
        document.getElementById("errormsg").innerHTML = val_msg3;
        return false;
    } else if (confirm_password != new_password) {
        document.getElementById("errormsg").innerHTML = val_msg4;
        return false;
    } else if (Object.hasOwnProperty.call(window, "ActiveXObject") && !window.ActiveXObject) {
        document.getElementById("browser_is_ie11_id").value = "IE11";
        document.getElementById("errormsg").innerHTML = "";
        return true;
    } else {
        document.getElementById("errormsg").innerHTML = "";
        return true;
    }
}
function submit_page() {
    validation();
    if (validation()) {
        document.getElementById('resetpassword').submit();
    }
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
