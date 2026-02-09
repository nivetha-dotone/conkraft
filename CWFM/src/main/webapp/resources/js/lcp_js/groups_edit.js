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
    var groupname = document.getElementById('edit_groups_groupname_id').value;
    groupname = $.trim(groupname);
    var group_uniqueness = document.getElementById('group_uniquness_id').value;
    var group = document.getElementById('group').value;
    var name = document.getElementById('name').value;
    var cannot_blank = document.getElementById('cannot_blank').value;
    var already_exist = document.getElementById('already_exist').value;
    if (groupname === '') {
        document.getElementById('errormsg').innerHTML = group + ' ' + name + ' ' + cannot_blank;
        return false;
    } else if (group_uniqueness === 'false') {
        document.getElementById('errormsg').innerHTML = group + ' ' + name + ' ' + already_exist;
        return false;
    } else {
        document.getElementById('errormsg').innerHTML = '';
        return true;
    }
}
function clearmessage() {
    document.getElementById('errormsg').innerHTML = '';
}
function edit_page() {
    validation();
    if (validation()) {
        selectallascorgs();
        document.getElementById('groupseditform').submit();
    }
}
function uniqueness() {
    var old_group_name = document.getElementById('edit_groups_hidden_groupname_id').value;
    old_group_name = $.trim(old_group_name);
    var new_group_name = document.getElementById('edit_groups_groupname_id').value;
    new_group_name = $.trim(new_group_name);
    if (new_group_name !== '' && old_group_name !== new_group_name) {
        if (base_url === '' || base_url === null)
            base_url = document.getElementById('base_url').value;
        var url = base_url + 'ctrl_groups/uniqueness/group_uniquness_id/group_uniquness_name/' + new_group_name;
        ajax(url, 'unique_group_div');
    } else {
        document.getElementById('group_uniquness_id').value = 'true';
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
    var grpass = document.getElementById('edit_user_group_associations_id');
    var len = grpass.options.length;
    for (i = 0; i < len; i++) {
        grpass.options[i].selected = true;
    }
    var perass = document.getElementById('edit_userpermission_associations_id');
    var len2 = perass.options.length
    for (i = 0; i < len2; i++) {
        perass.options[i].selected = true;
    }
    var roleass = document.getElementById('edit_userrole_associations_id');
    var len3 = roleass.options.length;
    for (i = 0; i < len3; i++) {
        roleass.options[i].selected = true;
    }
}
