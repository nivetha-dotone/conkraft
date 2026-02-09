/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
function cancle() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_lookupcodes';
}
function validation() {
    var lookuptype = document.getElementById("add_lookups_type_id").value;
    lookuptype = $.trim(lookuptype);
    var lookupcode = document.getElementById("add_lookups_code_id").value;
    lookupcode = $.trim(lookupcode);
    var pc_status = document.getElementById('uniquness').value;
    var meaning = document.getElementById("add_lookups_meaning_id").value;
    meaning = $.trim(meaning);
    var val_msg1 = document.getElementById("val_msg1").value;
    val_msg1 = $.trim(val_msg1);
    var val_msg2 = document.getElementById("val_msg2").value;
    val_msg2 = $.trim(val_msg2);
    var val_msg3 = document.getElementById("val_msg3").value;
    val_msg3 = $.trim(val_msg3);
    var val_msg4 = document.getElementById("val_msg4").value;
    val_msg4 = $.trim(val_msg4);
    if (lookuptype === '' || lookuptype === null) {
        document.getElementById("errormsg").innerHTML = val_msg1;
        return false;
    } else if (lookupcode === '' || lookupcode === null) {
        document.getElementById("errormsg").innerHTML = val_msg2;
        return false;
    } else if (pc_status === "1") {
        document.getElementById('errormsg').innerHTML = val_msg3;
        return false;
    } else if (meaning === '' || meaning === null) {
        document.getElementById("errormsg").innerHTML = val_msg4;
        return false;
    } else {
        document.getElementById('errormsg').innerHTML = "";
        return true;
    }
}
function uniqueness() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    var lookup_type_id = document.getElementById('add_lookups_type_id').value;
    var lookupcode = document.getElementById('add_lookups_code_id').value.split("#").join("ha_sh_sm-bl").split("^").join("%5E").split("?").join("qu_stn_mrk_sm-bl").split("/").join("fr_wrd_sl_sh_sm-bl").split("<").join("%3C").split(">").join("%3E").split("[").join("%5B").split("]").join("%5D").split("{").join("%7B").split("}").join("%7D").split("`").join("%60").split(".").join("dt_sm-bl").split("(").join("opn_br_ckt_sm-bl").split(")").join("cl_sd_br_ckt_sm-bl").split("$").join("dlr_sm-bl");
     lookupcode = $.trim(lookupcode);
    if (lookup_type_id !== "" && lookupcode !== "") {
        var url = base_url + "ctrl_lookupcodes/uniquness_lookupcode/" + lookupcode + "/" + lookup_type_id;
        ajax(url, 'uniquness_div');
    } else {
        var url = base_url + "ctrl_lookupcodes/uniquness_lookupcode/null/null";
        ajax(url, 'uniquness_div');
    }
}
function isValiedName(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode === 34 || charCode === 37 || charCode === 39 || charCode === 92)
        return false;
    else
        return true;
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
function add_page() {
    validation();
    if (validation()) {
        document.getElementById('lookup_add_form').submit();
    }
}
