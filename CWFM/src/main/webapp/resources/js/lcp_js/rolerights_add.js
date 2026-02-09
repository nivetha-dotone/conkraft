/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function addFormValidate() {
    var roleid = document.getElementById("add_rolerights_role_id").value;
    var pageid = document.getElementById("add_rolerights_page_id").value;
    var role = document.getElementById("role").value;
    var page_name = document.getElementById("page_name").value;
    var cannot_blank = document.getElementById("cannot_blank").value;
    var val_msg = document.getElementById("val_msg").value;
    if (roleid === "" || roleid === null) {
        document.getElementById("errormsg").innerHTML = role + ' ' + cannot_blank;
        return false;
    } else if (pageid === "" || pageid === null) {
        document.getElementById("errormsg").innerHTML = page_name + ' ' + cannot_blank;
        return false;
    } else {
        var unique = document.getElementById("unique_role_id").value;
        if (unique === "false") {
            document.getElementById("errormsg").innerHTML = val_msg;
            return false;
        } else {
            document.getElementById("errormsg").innerHTML = "";
            return true;
        }
    }
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
function uniqueness() {
    var role = document.getElementById("add_rolerights_role_id").value;
    role = role.split('<$>');
    var page = document.getElementById("add_rolerights_page_id").value;
    page = page.split('<$>');
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    var url = base_url + 'ctrl_rolerights/uniqueness/' + role[0] + '/' + page[0];
    ajax(url, 'div_uniq_id');
}
function setRights() {
    var add_right = document.getElementById('add_rolerights_add_right').value.split('<$>');
    var delete_right = document.getElementById('add_rolerights_delete_right').value.split('<$>');
    var edit_right = document.getElementById('add_rolerights_edit_right').value.split('<$>');
    var export_right = document.getElementById('add_rolerights_export_right').value.split('<$>');
    var launch_right = document.getElementById('add_rolerights_launch_right').value.split('<$>');
    var view_right = document.getElementById('add_rolerights_view_right').value.split('<$>');
    var flag_yes = document.getElementById('flag_yes').value;
    var value = document.getElementById('value1').value;
    if (add_right[0] === value || delete_right[0] === value || edit_right[0] === value || export_right[0] === value || launch_right[0] === value || view_right[0] === value) {
        document.getElementById('add_rolerights_list_right').value = value.concat(flag_yes);
    }
}
function setRights_Basedon_list() {
    var list_right = document.getElementById('add_rolerights_list_right').value.split('<$>');
    var value = document.getElementById('value2').value;
    if (list_right[0] === value) {
        document.getElementById('add_rolerights_add_right').value = document.getElementById('add_rolerights_list_right').value;
        document.getElementById('add_rolerights_delete_right').value = document.getElementById('add_rolerights_list_right').value;
        document.getElementById('add_rolerights_edit_right').value = document.getElementById('add_rolerights_list_right').value;
        document.getElementById('add_rolerights_export_right').value = document.getElementById('add_rolerights_list_right').value;
        document.getElementById('add_rolerights_launch_right').value = document.getElementById('add_rolerights_list_right').value;
        document.getElementById('add_rolerights_view_right').value = document.getElementById('add_rolerights_list_right').value;
    }
}
function add_page() {
    addFormValidate();
    if (addFormValidate()) {
        document.getElementById('rolerights_add_form').submit();
    }
}
function backToList() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_rolerights';
}

