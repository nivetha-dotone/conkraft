/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function listing() {
    if (base_url === '' || base_url === null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_groups';
}
function validation() {
    var delete_dep = document.getElementById('groups_delete_dependency').value;
    delete_dep = $.trim(delete_dep);	
    var group = document.getElementById('group').value;
    var cannot_delete = document.getElementById('cannot_delete').value;
    if (delete_dep === '1') {
        document.getElementById('errormsg').innerHTML = group + ' ' + cannot_delete;
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