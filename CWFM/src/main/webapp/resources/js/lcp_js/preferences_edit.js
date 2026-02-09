/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

function cancle() {
    var base_url = document.getElementById('base_url').value;
    var login_home_page = document.getElementById('login_home_page').value;
    window.location.href = base_url + login_home_page;
}
function validation() {
    var newpassword = document.getElementById('edit_preferences_newpassword_id').value;
    newpassword = $.trim(newpassword);
    var confirmpassword = document.getElementById('edit_preferences_confirmpassword_id').value;
    confirmpassword = $.trim(confirmpassword);
    var ansone = document.getElementById('ans1').value;
    ansone = $.trim(ansone);
    var anstwo = document.getElementById("ans2").value;
    anstwo = $.trim(anstwo);
    var ansthree = document.getElementById("ans3").value;
    ansthree = $.trim(ansthree);
    var val_msg1 = document.getElementById("val_msg1").value;
    val_msg1 = $.trim(val_msg1);
    var val_msg2 = document.getElementById("val_msg2").value;
    val_msg2 = $.trim(val_msg2);
    var val_msg3 = document.getElementById("val_msg3").value;
    val_msg3 = $.trim(val_msg3);
    var val_msg4 = document.getElementById("val_msg4").value;
    val_msg4 = $.trim(val_msg4);
    var val_msg5 = document.getElementById("val_msg5").value;
    val_msg5 = $.trim(val_msg5);
    var val_msg6 = document.getElementById("val_msg6").value;
    val_msg6 = $.trim(val_msg6);
    var val_msg7 = document.getElementById("val_msg7").value;
    val_msg7 = $.trim(val_msg7);
    if (newpassword === "" || newpassword === null) {
        document.getElementById("errormsg").innerHTML = val_msg1;
        return false;
    } else if (newpassword.length < 8) {
        document.getElementById("errormsg").innerHTML = val_msg2;
        return false;
    } else if (confirmpassword === "" || confirmpassword === null) {
        document.getElementById("errormsg").innerHTML = val_msg3;
        return false;
    } else if (newpassword !== confirmpassword) {
        document.getElementById("errormsg").innerHTML = val_msg4;
        return false;
    } else if (ansone === "" || ansone === null) {
        document.getElementById("errormsg").innerHTML = val_msg5;
        return false;
    } else if (anstwo === "" || anstwo === null) {
        document.getElementById("errormsg").innerHTML = val_msg6;
        return false;
    } else if (ansthree === "" || ansthree === null) {
        document.getElementById("errormsg").innerHTML = val_msg7;
        return false;
    } else {
        document.getElementById("errormsg").innerHTML = "";
        return true;

    }
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
function edit_page() {
    validation();
    if (validation()) {
        document.getElementById('preferences').submit();
    }
}
function secretquestion(id) {
    if (id != "") {
        var value = document.getElementById(id).value;
        value = $.trim(value);
        var value = value.split("<$>");
        var qtnone = document.getElementById('qtn1').value;
        qtnone = $.trim(qtnone);
        var onevalue = qtnone.split("<$>");
        var qtntwo = document.getElementById("qtn2").value;
        qtntwo = $.trim(qtntwo);
        var twovalue = qtntwo.split("<$>");
        var qtnthree = document.getElementById("qtn3").value;
        qtnthree = $.trim(qtnthree);
        var threevalue = qtnthree.split("<$>");
        var base_url = document.getElementById('base_url').value;
        if (id == "qtn1") {
            document.getElementById("ans1").value = "";
            var url = base_url + 'ctrl_preferences/dep_secretqtns/' + onevalue[0] + '/' + threevalue[0] + '/' + twovalue[0] + '/' + 'qtn2';
            ajax(url, 'div-qtn2');
            var url = base_url + 'ctrl_preferences/dep_secretqtns/' + onevalue[0] + '/' + twovalue[0] + '/' + threevalue[0] + '/' + 'qtn3';
            ajax(url, 'div-qtn3');
        } else if (id == "qtn2") {
            document.getElementById("ans2").value = "";
            var url = base_url + 'ctrl_preferences/dep_secretqtns/' + twovalue[0] + '/' + threevalue[0] + '/' + onevalue[0] + '/' + 'qtn1';
            ajax(url, 'div-qtn1');
            var url = base_url + 'ctrl_preferences/dep_secretqtns/' + onevalue[0] + '/' + twovalue[0] + '/' + threevalue[0] + '/' + 'qtn3';
            ajax(url, 'div-qtn3');
        } else if (id == "qtn3") {
            document.getElementById("ans3").value = "";
            var url = base_url + 'ctrl_preferences/dep_secretqtns/' + twovalue[0] + '/' + threevalue[0] + '/' + onevalue[0] + '/' + 'qtn1';
            ajax(url, 'div-qtn1');
            var url = base_url + 'ctrl_preferences/dep_secretqtns/' + onevalue[0] + '/' + threevalue[0] + '/' + twovalue[0] + '/' + 'qtn2';
            ajax(url, 'div-qtn2');
        }
    }
}
