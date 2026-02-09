/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function users_page() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_users';
}
function hyphen_generate(evt, value) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (value.length === 3 && charCode != 8)
        document.getElementById('add_users_phone_id').value = value.concat("-");
    if (value.length === 7 && charCode != 8)
        document.getElementById('add_users_phone_id').value = value.concat("-");
}
function NumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    else
        return true;
}
function getHomepage(fieldId, tagId, tagName, condVar) {
    var selected_role_val = document.getElementById(fieldId).value;
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    if (selected_role_val !== "" && condVar === "HOMEPAGE") {
        var selected_role_arr = selected_role_val.split('<$>');
        var url = base_url + 'ctrl_users/homePageList/' + tagId + '/' + tagName + '/' + selected_role_arr[0];
        ajax(url, 'homepage_div');
    } else {
        var url = base_url + 'ctrl_users/homePageList/' + tagId + '/' + tagName + '/Null';
        ajax(url, 'homepage_div');
    }
}
function validation() {
    var role = document.getElementById('add_users_rolename_id').value;
    role = $.trim(role);
    var firstname = document.getElementById('add_compres_fname_id').value;
    firstname = $.trim(firstname);
    var lastname = document.getElementById('add_compres_lname_id').value;
    lastname = $.trim(lastname);
    var uniq_name = document.getElementById('add_users_uniquename_id').value;
    uniq_name = $.trim(uniq_name);
    var addressline1 = document.getElementById('add_users_addressline1_id').value;
    addressline1 = $.trim(addressline1);
    var state = document.getElementById('add_users_state_id').value;
    var city = document.getElementById('add_users_city_id').value;
    city = $.trim(city);
    var zip = document.getElementById('add_users_zip_id').value;
    zip = $.trim(zip);
    var phone = document.getElementById('add_users_phone_id').value;
    phone = $.trim(phone);
    var emailformat = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+[\.]{1}[a-zA-Z]{2,4}$/;
    var email = document.getElementById('add_users_email_id').value;
    email = $.trim(email);
    var altemail = document.getElementById('add_users_altemail_id').value;
    altemail = $.trim(altemail);
    var ssoid = document.getElementById('add_users_ssoid_id').value;
    ssoid = $.trim(ssoid);
    var uniq_sso = document.getElementById('user_uniquessoid_id').value;
    uniq_sso = $.trim(uniq_sso);
    var useraccount = document.getElementById('add_users_account_id').value;
    useraccount = $.trim(useraccount);
    var uniq_acc = document.getElementById('user_uniquness_name_id').value;
    uniq_acc = $.trim(uniq_acc);
    var password = document.getElementById('add_users_password_id').value;
    password = $.trim(password);
    var confirmpassword = document.getElementById('add_users_confirmpassword_id').value;
    confirmpassword = $.trim(confirmpassword);
    var val_msg1 = document.getElementById('val_msg1').value;
    val_msg1 = $.trim(val_msg1);
    var val_msg2 = document.getElementById('val_msg2').value;
    val_msg2 = $.trim(val_msg2);
    var val_msg3 = document.getElementById('val_msg3').value;
    val_msg3 = $.trim(val_msg3);
    var val_msg4 = document.getElementById('val_msg4').value;
    val_msg4 = $.trim(val_msg4);
    var val_msg5 = document.getElementById('val_msg5').value;
    val_msg5 = $.trim(val_msg5);
    var val_msg6 = document.getElementById('val_msg6').value;
    val_msg6 = $.trim(val_msg6);
    var val_msg7 = document.getElementById('val_msg7').value;
    val_msg7 = $.trim(val_msg7);
    var val_msg8 = document.getElementById('val_msg8').value;
    val_msg8 = $.trim(val_msg8);
    var val_msg9 = document.getElementById('val_msg9').value;
    val_msg9 = $.trim(val_msg9);
    var val_msg10 = document.getElementById('val_msg10').value;
    val_msg10 = $.trim(val_msg10);
    var val_msg11 = document.getElementById('val_msg11').value;
    val_msg11 = $.trim(val_msg11);
    var val_msg12 = document.getElementById('val_msg12').value;
    val_msg12 = $.trim(val_msg12);
    var val_msg13 = document.getElementById('val_msg13').value;
    val_msg13 = $.trim(val_msg13);
    var val_msg14 = document.getElementById('val_msg14').value;
    val_msg14 = $.trim(val_msg14);
    var val_msg15 = document.getElementById('val_msg15').value;
    val_msg15 = $.trim(val_msg15);
    var val_msg16 = document.getElementById('val_msg16').value;
    val_msg16 = $.trim(val_msg16);
    var val_msg17 = document.getElementById('val_msg17').value;
    val_msg17 = $.trim(val_msg17);
    var val_msg18 = document.getElementById('val_msg18').value;
    val_msg18 = $.trim(val_msg18);
    var val_msg19 = document.getElementById('val_msg19').value;
    val_msg19 = $.trim(val_msg19);
    var val_msg20 = document.getElementById('val_msg20').value;
    val_msg20 = $.trim(val_msg20);
    var val_msg21 = document.getElementById('val_msg21').value;
    val_msg21 = $.trim(val_msg21);
    if (role === '' || role === null) {
        document.getElementById("errormsg").innerHTML = val_msg1;
        return false;
    } else if (firstname === '' || firstname === null) {
        document.getElementById("errormsg").innerHTML = val_msg2;
        return false;
    } else if (lastname === '' || lastname === null) {
        document.getElementById("errormsg").innerHTML = val_msg3;
        return false;
    } else if (uniq_name === 'false') {
        document.getElementById("errormsg").innerHTML = val_msg4;
        return false;
    } else if (addressline1 === '' || addressline1 === null) {
        document.getElementById("errormsg").innerHTML = val_msg5;
        return false;
    } else if (state === '' || state === null) {
        document.getElementById("errormsg").innerHTML = val_msg6;
        return false;
    } else if (city === '' || city === null) {
        document.getElementById("errormsg").innerHTML = val_msg7;
        return false;
    } else if (zip === '' || zip === null) {
        document.getElementById("errormsg").innerHTML = val_msg8;
        return false;
    } else if (phone === '' || phone === null) {
        document.getElementById("errormsg").innerHTML = val_msg9;
        return false;
    } else if (email === '' || email === null) {
        document.getElementById("errormsg").innerHTML = val_msg10;
        return false;
    } else if (email.search(emailformat) === -1 && email !== '') {
        document.getElementById("errormsg").innerHTML = val_msg11;
        return false;
    } else if (altemail === '' || altemail === null) {
        document.getElementById("errormsg").innerHTML = val_msg12;
        return false;
    } else if (altemail.search(emailformat) === -1 && altemail !== '') {
        document.getElementById("errormsg").innerHTML = val_msg13;
        return false;
    } else if (ssoid === '' || ssoid === null) {
        document.getElementById("errormsg").innerHTML = val_msg14;
        return false;
    } else if (uniq_sso === 'false') {
        document.getElementById("errormsg").innerHTML = val_msg15;
        return false;
    } else if (useraccount === '' || useraccount === null) {
        document.getElementById("errormsg").innerHTML = val_msg16;
        return false;
    } else if (uniq_acc === 'DupName') {
        document.getElementById("errormsg").innerHTML = val_msg17;
        return false;
    } else if (password === '' || password === null) {
        document.getElementById("errormsg").innerHTML = val_msg18;
        return false;
    } else if (password.length < 8) {
        document.getElementById("errormsg").innerHTML = val_msg19;
        return false;
    } else if (confirmpassword === '' || confirmpassword === null) {
        document.getElementById("errormsg").innerHTML = val_msg20;
        return false;
    } else if (password !== confirmpassword) {
        document.getElementById("errormsg").innerHTML = val_msg21;
        return false;
    } else {
        selectallascorgs();
        document.getElementById("errormsg").innerHTML = "";
        return true;
    }
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
function add_page() {
    validation();
    if (validation()) {
        selectallascorgs();
        document.getElementById('useraddform').submit();
    }
}
function getStates(CountryId, div_Id, tag_Id, tag_Name) {
    var val_msg22 = document.getElementById('val_msg22').value;
    val_msg22 = $.trim(val_msg22);
    if (CountryId === "" || CountryId === val_msg22) {
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        var url = base_url + 'ctrl_users/dep_country/' + null + '/' + tag_Id + '/' + tag_Name;
        ajax(url, div_Id);
    } else {
        var cntry_Id = CountryId.split('<$>');
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        var url = base_url + 'ctrl_users/dep_country/' + cntry_Id[0] + '/' + tag_Id + '/' + tag_Name;
        ajax(url, div_Id);
    }
}
function uniqueness(field_id, dep_div, func_name) {
    var field_val = "";
    field_val = document.getElementById(field_id).value.split("#").join("ha_sh_sm-bl").split("^").join("%5E").split("?").join("qu_stn_mrk_sm-bl").split("/").join("fr_wrd_sl_sh_sm-bl").split("<").join("%3C").split(">").join("%3E").split("[").join("%5B").split("]").join("%5D").split("{").join("%7B").split("}").join("%7D").split("`").join("%60").split(".").join("dt_sm-bl").split("(").join("opn_br_ckt_sm-bl").split(")").join("cl_sd_br_ckt_sm-bl").split("$").join("dlr_sm-bl");
    field_val = $.trim(field_val);
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    var url = base_url + "ctrl_users/" + func_name + "/" + field_val;
    ajax(url, dep_div);
}
function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode !== 43 && charCode !== 45 && charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    else
        return true;
}
function isSpaceKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode === 32)
        return false;
    else
        return true;
}
function isUseracc(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode === 32 || charCode === 34 || charCode === 37 || charCode === 39 || charCode === 92)
        return false;
    else
        return true;
}
function isValiedName(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode === 34 || charCode === 37 || charCode === 39 || charCode === 92)
        return false;
    else
        return true;
}
function addUniqueness(div_Id, tag_Id, tag_Name) {
    var first_name = document.getElementById('add_compres_fname_id').value.split("#").join("ha_sh_sm-bl").split("^").join("%5E").split("?").join("qu_stn_mrk_sm-bl").split("/").join("fr_wrd_sl_sh_sm-bl").split("<").join("%3C").split(">").join("%3E").split("[").join("%5B").split("]").join("%5D").split("{").join("%7B").split("}").join("%7D").split("`").join("%60").split(".").join("dt_sm-bl").split("(").join("opn_br_ckt_sm-bl").split(")").join("cl_sd_br_ckt_sm-bl").split("$").join("dlr_sm-bl");
    first_name = $.trim(first_name);
    var last_name = document.getElementById('add_compres_lname_id').value.split("#").join("ha_sh_sm-bl").split("^").join("%5E").split("?").join("qu_stn_mrk_sm-bl").split("/").join("fr_wrd_sl_sh_sm-bl").split("<").join("%3C").split(">").join("%3E").split("[").join("%5B").split("]").join("%5D").split("{").join("%7B").split("}").join("%7D").split("`").join("%60").split(".").join("dt_sm-bl").split("(").join("opn_br_ckt_sm-bl").split(")").join("cl_sd_br_ckt_sm-bl").split("$").join("dlr_sm-bl");
    last_name = $.trim(last_name);
    if (last_name === "" || first_name === "") {
        first_name = "null";
        last_name = "null";
    }
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    var url = base_url + 'ctrl_users/add_uniqueness/' + tag_Id + '/' + tag_Name + '/' + first_name + '/' + last_name;
    ajax(url, div_Id);
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
    var grpass = document.getElementById('add_user_group_associations_id');
    for (i = 0; i < grpass.options.length; i++) {
        grpass.options[i].selected = true;
    }
    var perass = document.getElementById('add_userpermission_associations_id');
    for (i = 0; i < perass.options.length; i++) {
        perass.options[i].selected = true;
    }
    var roleass = document.getElementById('add_userrole_associations_id');
    for (i = 0; i < roleass.options.length; i++) {
        roleass.options[i].selected = true;
    }
}
