/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function users_page() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_users';
}
function validation() {
    var delete_dep = document.getElementById('users_delete_dependency').value;
    delete_dep = $.trim(delete_dep);
    var val_msg = document.getElementById('val_msg').value;
    val_msg = $.trim(val_msg);
    if (delete_dep === "1") {
        document.getElementById("errormsg").innerHTML = val_msg;
        return false;
    } else {
        document.getElementById("errormsg").innerHTML = "";
        return true;
    }
}
function delete_page() {
    validation();
    if (validation()) {
        document.getElementById('useraddform').submit();
    }
}
