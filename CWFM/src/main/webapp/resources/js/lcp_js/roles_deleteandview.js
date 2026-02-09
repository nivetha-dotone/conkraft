/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function roles_page() {
    if (base_url === '' || base_url === null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_roles';
}
function validation() {
    var delete_dep = document.getElementById('roles_delete_dependency').value;
    delete_dep = $.trim(delete_dep);
    if (delete_dep === '1') {
        var role = document.getElementById('role').value;
        var name = document.getElementById('name').value;
        var cannot_delete = document.getElementById('cannot_delete').value;
        document.getElementById('errormsg').innerHTML = role + ' ' + name + ' ' + cannot_delete;
        return false;
    } else {
        document.getElementById('errormsg').innerHTML = '';
        return true;
    }
}
function delete_page() {
    validation();
    if (validation()) {
        document.getElementById('group_viewdelete').submit();
    }
}