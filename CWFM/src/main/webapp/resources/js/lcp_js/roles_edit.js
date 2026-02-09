/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function users_page() {
    if (base_url === '' || base_url === null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_roles';
}
function role_validation() {
    var role = document.getElementById('edit_role_id').value;
    role = $.trim(role);
    var role_uniqueness = document.getElementById('role_uniquness_id').value;
    var role_ = document.getElementById('role').value;
    var name = document.getElementById('name').value;
    var cannot_blank = document.getElementById('cannot_blank').value;
    var already_exist = document.getElementById('cannot_blank').value;
    if (role === '' || role === null) {
        document.getElementById('errormsg').innerHTML = role_ + ' ' + name + ' ' + cannot_blank;
        return false;
    } else if (role_uniqueness === 'false') {
        document.getElementById('errormsg').innerHTML = role_ + ' ' + name + ' ' + already_exist;
        return false;
    } else {
        document.getElementById('errormsg').innerHTML = '';
        return uniqueValidation();
    }
}
function clearmessage() {
    document.getElementById('errormsg').innerHTML = '';
}
function edit_page() {
    role_validation();
    if (role_validation()) {
        document.getElementById('roleeditform').submit();
    }
}
function editUniqueness(divId, tagId, tagName) {
    var old_role_name = document.getElementById('edit_hidden_old_role_id').value;
    old_role_name = $.trim(old_role_name);
    var role_name = document.getElementById('edit_role_id').value;
    role_name = $.trim(role_name);
    if (role_name !== '' && role_name !== old_role_name) {
        if (base_url === '' || base_url === null)
            base_url = document.getElementById('base_url').value;
        var url = base_url + 'ctrl_roles/edit_Uniqueness/' + tagId + '/' + tagName + '/' + role_name;
        ajax(url, divId);
    } else {
        document.getElementById('role_uniquness_id').value = 'true';
    }
}
function uniqueValidation() {
    var validation = document.getElementById('role_uniquness_id').value;
    var role_ = document.getElementById('role').value;
    var name = document.getElementById('name').value;
    var already_exist = document.getElementById('cannot_blank').value;
    if (validation === 'false') {
        document.getElementById('errormsg').innerHTML = role_ + ' ' + name + ' ' + already_exist;
        return false;
    } else {
        selectallascorgs();
        document.getElementById('errormsg').innerHTML = '';
        return true;
    }
}
function SelectMoveRows(SS1, SS2, del, name, id) {
    var SelID = '';
    var SelText = '';
    j = 1;
    var len = SS1.options.length;
    for (i = len - 1; i >= 0; i--) {
        if (SS1.options[i].selected === true) {
            SelID = SS1.options[i].value;
            SelText = SS1.options[i].text;
            var newRow = new Option(SelText, SelID);
            SS2.options[SS2.length] = newRow;
            SS1.options[i] = null;
            if (del === '1')
                removeascorgs(SelID, name, id);
        }
    }
    SelectSort(SS2);
}
function SelectSort(SelList) {
    var ID = '';
    var Text = '';
    var len = SelList.length;
    for (x = 0; x < len - 1; x++) {
        for (y = x + 1; y < len; y++) {
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
    delorg.setAttribute('name', name);
    delorg.setAttribute('type', 'hidden');
    delorg.setAttribute('value', val);
    document.getElementById(id).appendChild(delorg);
}
function MoveAllRows(SS1, SS2, del, name, id) {
    var SelID = '';
    var SelText = '';
    var len = SS1.options.length;
    for (i = len - 1; i >= 0; i--) {
        SelID = SS1.options[i].value;
        SelText = SS1.options[i].text;
        var newRow = new Option(SelText, SelID);
        SS2.options[SS2.length] = newRow;
        SS1.options[i] = null;
        if (del === '1')
            removeascorgs(SelID, name, id);
    }
    SelectSort(SS2);
}
function selectallascorgs() {
    var grpass = document.getElementById('edit_role_group_associations_id');
    var len = grpass.options.length;
    for (i = 0; i < len; i++) {
        grpass.options[i].selected = true;
    }
    var perass = document.getElementById('edit_rolepermission_associations_id');
    var len2 = perass.options.length;
    for (i = 0; i < len2; i++) {
        perass.options[i].selected = true;
    }
    var roleass = document.getElementById('edit_userrole_associations_id');
    var len3 = roleass.options.length;
    for (i = 0; i < len3; i++) {
        roleass.options[i].selected = true;
    }
}