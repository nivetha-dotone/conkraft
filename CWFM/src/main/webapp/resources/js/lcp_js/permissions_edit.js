/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function permissions_page() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_permissions';
}
function validation() {
    var permission_name = document.getElementById('edit_permissions_permissioname_id').value;
    permission_name = $.trim(permission_name);
    var uniq_pname = document.getElementById('permissioname_unique_id').value;
    uniq_pname = $.trim(uniq_pname);
    var prmsn_valid = document.getElementById('permission_cannot_beblank').value;
    prmsn_valid = $.trim(prmsn_valid);
    var uniqe_valid = document.getElementById('permission_unique_valid').value;
    uniqe_valid = $.trim(uniqe_valid);
    if (permission_name === '' || permission_name === null) {
        document.getElementById("errormsg").innerHTML = prmsn_valid;
        return false;
    } else if (uniq_pname === 'false') {
        document.getElementById("errormsg").innerHTML = uniqe_valid;
        return false;
    } else {
        selectallascorgs();
        document.getElementById("errormsg").innerHTML = "";
        return true;
    }
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
    return true;
}
function edit_page() {
    validation();
    if (validation()) {
        selectallascorgs();
        document.getElementById('permission_addform').submit();
    }
}
function isValiedName(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode === 34 || charCode === 37 || charCode === 39 || charCode === 92)
        return false;
    else
        return true;
}
function Uniqueness(value, div_Id, tag_Id, tag_Name) {
    var oldname = document.getElementById('permissions_permission_oldname').value;
    oldname = $.trim(oldname);
    var newname = value;
    value = value.split("#").join("ha_sh_sm-bl").split("^").join("%5E").split("?").join("qu_stn_mrk_sm-bl").split("/").join("fr_wrd_sl_sh_sm-bl").split("<").join("%3C").split(">").join("%3E").split("[").join("%5B").split("]").join("%5D").split("{").join("%7B").split("}").join("%7D").split("`").join("%60").split(".").join("dt_sm-bl").split("(").join("opn_br_ckt_sm-bl").split(")").join("cl_sd_br_ckt_sm-bl").split("$").join("dlr_sm-bl");
    value = $.trim(value);
    if (value !== "" && oldname !== newname) {
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        var url = base_url + 'ctrl_permissions/uniqueness/' + tag_Id + '/' + tag_Name + '/' + value;
        ajax(url, div_Id);
    } else {
        document.getElementById('permissioname_unique_id').value = "true";
    }
}
function SelectMoveRows(SS1, SS2, del, name, id) {
    var SelID = '';
    var SelText = '';
    j = 1;
    for (i = SS1.options.length - 1; i >= 0; i--) {
        if (SS1.options[i].selected == true) {
            SelID = SS1.options[i].value;
            SelText = SS1.options[i].text;
            var newRow = new Option(SelText, SelID);
            SS2.options[SS2.length] = newRow;
            SS1.options[i] = null;
            if (del == '1')
                removeascorgs(SelID, name, id);
        }
    }
    SelectSort(SS2);
}
function SelectSort(SelList) {
    var ID = '';
    var Text = '';
    for (x = 0; x < SelList.length - 1; x++) {
        for (y = x + 1; y < SelList.length; y++) {
            if (SelList[x].text > SelList[y].text) {
                ID = SelList[x].value;
                Text = SelList[x].text;
                SelList[x].value = SelList[y].value;
                SelList[x].text = SelList[y].text;
                SelList[y].value = ID;
                SelList[y].text = Text;
            }
        }
    }
}
function removeascorgs(val, name, id) {
    var delorg = document.createElement('input');
    delorg.setAttribute("name", name);
    delorg.setAttribute("type", "hidden");
    delorg.setAttribute("value", val);
    document.getElementById(id).appendChild(delorg);
}
function MoveAllRows(SS1, SS2, del, name, id) {
    var SelID = '';
    var SelText = '';
    for (i = SS1.options.length - 1; i >= 0; i--) {
        SelID = SS1.options[i].value;
        SelText = SS1.options[i].text;
        var newRow = new Option(SelText, SelID);
        SS2.options[SS2.length] = newRow;
        SS1.options[i] = null;
        if (del == '1')
            removeascorgs(SelID, name, id);
    }
    SelectSort(SS2);
}
function selectallascorgs() {
    var grpass = document.getElementById('edit_permission_group_associations_id');
    for (i = 0; i < grpass.options.length; i++) {
        grpass.options[i].selected = true;
    }
    var perass = document.getElementById('edit_userpermission_associations_id');
    for (i = 0; i < perass.options.length; i++) {
        perass.options[i].selected = true;
    }
    var roleass = document.getElementById('edit_permissionrole_associations_id');
    for (i = 0; i < roleass.options.length; i++) {
        roleass.options[i].selected = true;
    }
}
