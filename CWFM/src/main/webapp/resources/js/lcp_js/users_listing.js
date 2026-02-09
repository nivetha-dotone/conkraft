/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

//var base_url = document.getElementById('base_url').value;
var p_s_record = document.getElementById('p_s_record').value;
var selectedrecordid = "";
function selectedrow(id) {
	selectedrecordid = id;
}
function uncheckRadioButton() {
    selectedrecordid = "";
    var rbs = document.getElementsByName('radio');
    for (var i = 0; i < rbs.length; i++) {
        if (rbs[i].checked) {
            rbs[i].checked = false;
        }
    }
}
function export_user_records() {
    var search = document.getElementById('searchterm_id').value.split("#").join("ha_sh_sm-bl").split("^").join("%5E").split('"').join("%22").split("'").join("%27").split("?").join("qu_stn_mrk_sm-bl").split("/").join("fr_wrd_sl_sh_sm-bl").split("<").join("%3C").split(">").join("%3E").split("[").join("%5B").split("]").join("%5D").split("{").join("%7B").split("}").join("%7D").split("`").join("%60").split(".").join("dt_sm-bl").split("(").join("opn_br_ckt_sm-bl").split(")").join("cl_sd_br_ckt_sm-bl").split("$").join("dlr_sm-bl");
    search = $.trim(search);
    if (search == '' || search == null)
        search = "empty";
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_users/export_users/' + search;
}
function delete_page() {
    if (selectedrecordid == null || selectedrecordid == "") {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_users/delete_page/' + id + '/delete';
    }
}
function view_page() {
    if (selectedrecordid == null || selectedrecordid == "") {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_users/view_page/' + id;
    }
}
function add_page() {
    if (selectedrecordid != '' || selectedrecordid != null) {
        uncheckRadioButton();
    }
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_users/add_page';
}
function edit_page() {
    if (selectedrecordid == null || selectedrecordid == "") {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_users/edit_page/' + id;
    }
}
function launch_file() {
    if (selectedrecordid == '' || selectedrecordid == null) {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_users/launch_file/' + id;
    }
}
function launch_barcodefile() {
    if (selectedrecordid == '' || selectedrecordid == null) {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_users/launch_barcodefile/' + id;
    }
}
function groups() {
    if (selectedrecordid == '') {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_groups/index/' + id + "/" + 'groups';
    }
}
function roles() {
    if (selectedrecordid == '') {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_roles/index/' + id + "/" + 'roles';
    }
}
function notifications() {
    if (selectedrecordid == '') {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_notifications/index/' + id + "/" + 'notifications';
    }
}
function permissions() {
    if (selectedrecordid == '') {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_permissions/index/' + id + "/" + 'permissions';
    }
}
function login_history() {
    if (selectedrecordid == '') {
        if (p_s_record == '' || p_s_record == null)
            p_s_record = document.getElementById('p_s_record').value;
        alert(p_s_record);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url + 'ctrl_loginhistory/index/null/' + id + "/" + 'users';
    }
}
