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
    var groupname = document.getElementById('add_groupsname_id').value;
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
function add_page() {
    validation();
    if (validation()) {
        selectallascorgs();
        document.getElementById('groupaddform').submit();
    }
}
function uniqueness() {
    var group_name = document.getElementById('add_groupsname_id').value;
    group_name = $.trim(group_name);
    if (base_url === '' || base_url === null)
        base_url = document.getElementById('base_url').value;
    if (group_name !== '') {
        var url = base_url + 'ctrl_groups/uniqueness/group_uniquness_id/group_uniquness_name/' + group_name;
        ajax(url, 'unique_group_div');
    } else {
        var url = base_url + 'ctrl_groups/uniqueness/group_uniquness_id/group_uniquness_name/null';
        ajax(url, 'unique_group_div');
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
    var group_prm = document.getElementById('add_grouppermission_associations_id');
    var len = group_prm.options.length;
    for (i = 0; i < len; i++) {
        group_prm.options[i].selected = true;
    }
    var group_role = document.getElementById('add_grouprole_associations_id');
    var len2 = group_role.options.length;
    for (i = 0; i < len2; i++) {
        group_role.options[i].selected = true;
    }
    var group_user = document.getElementById('add_groupuser_associations_id');
    var len3 = group_user.options.length;
    for (i = 0; i < len3; i++) {
        group_user.options[i].selected = true;
    }
}
