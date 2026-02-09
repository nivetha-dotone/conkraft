/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function cancel_delete() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_loginhistory';
}
function deleteaction() {
    var id = document.getElementById("delete_result_id").value;
    var selectedrowid = document.getElementById("selectedrow").value;
    var record = document.getElementById('record').value;
    var cannot_delete = document.getElementById('cannot_delete').value;
    if (id === '1') {
        document.getElementById("errormsg").innerHTML = record + ' ' + cannot_delete;
        return false;
    } else {
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_loginhistory/delete/' + selectedrowid;
    }
}
