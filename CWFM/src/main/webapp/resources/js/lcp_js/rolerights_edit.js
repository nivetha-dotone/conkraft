/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function listing() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + "ctrl_rolerights";
}
function setRights() {
    var add_right = document.getElementById('edit_rolerights_add_id').value.split('<$>');
    var delete_right = document.getElementById('edit_rolerights_delete_id').value.split('<$>');
    var edit_right = document.getElementById('edit_rolerights_edit_id').value.split('<$>');
    var export_right = document.getElementById('edit_rolerights_export_id').value.split('<$>');
    var launch_right = document.getElementById('edit_rolerights_launch_id').value.split('<$>');
    var view_right = document.getElementById('edit_rolerights_view_id').value.split('<$>');
    var flag_yes = document.getElementById('flag_yes').value;
    var value = document.getElementById('value1').value;
    if (add_right[0] === value || delete_right[0] === value || edit_right[0] === value || export_right[0] === value || launch_right[0] === value || view_right[0] === value) {
        edit_rolerights_list_id.value = value.concat(flag_yes);
    }
}
function setRights_Basedon_list() {
    var list_right = document.getElementById('edit_rolerights_list_id').value.split('<$>');
    var value = document.getElementById('value2').value;
    if (list_right[0] === value) {
        document.getElementById('edit_rolerights_add_id').value = document.getElementById('edit_rolerights_list_id').value;
        document.getElementById('edit_rolerights_delete_id').value = document.getElementById('edit_rolerights_list_id').value;
        document.getElementById('edit_rolerights_edit_id').value = document.getElementById('edit_rolerights_list_id').value;
        document.getElementById('edit_rolerights_export_id').value = document.getElementById('edit_rolerights_list_id').value;
        document.getElementById('edit_rolerights_launch_id').value = document.getElementById('edit_rolerights_list_id').value;
        document.getElementById('edit_rolerights_view_id').value = document.getElementById('edit_rolerights_list_id').value;
    }
}
function edit_page() {
    document.getElementById('rolerights_edit_form').submit();
}

