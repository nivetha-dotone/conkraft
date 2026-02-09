/* Application Name = Hire A Bus System
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
    var valMsg1 = document.getElementById("valMsg1").value;
    valMsg1 = $.trim(valMsg1);
    var valMsg2 = document.getElementById("valMsg2").value;
    valMsg2 = $.trim(valMsg2);
    var valMsg3 = document.getElementById("valMsg3").value;
    valMsg3 = $.trim(valMsg3);
    var valMsg4 = document.getElementById("valMsg4").value;
    valMsg4 = $.trim(valMsg4);
    var valMsg5 = document.getElementById("valMsg5").value;
    valMsg5 = $.trim(valMsg5);
    var valMsg6 = document.getElementById("valMsg6").value;
    valMsg6 = $.trim(valMsg6);
    var valMsg7 = document.getElementById("valMsg7").value;
    valMsg7 = $.trim(valMsg7);
    /*if (newpassword === "" || newpassword === null) {
        document.getElementById("errormsg").innerHTML = valMsg4 +" "+ valMsg2 ;
        return false;
    } else if (newpassword.length < 8) {
        document.getElementById("errormsg").innerHTML = valMsg4 +" "+ valMsg5;
        return false;
    } else if (confirmpassword === "" || confirmpassword === null) {
        document.getElementById("errormsg").innerHTML = valMsg6 +" "+ valMsg2;
        return false;
    } */ if (newpassword !== confirmpassword) {
        document.getElementById("errormsg").innerHTML = valMsg3;
        return false;
    } else if (ansone === "" || ansone === null) {
        document.getElementById("errormsg").innerHTML = valMsg1 +" "+ 1 +" "+ valMsg2;
        return false;
    } else if (anstwo === "" || anstwo === null) {
        document.getElementById("errormsg").innerHTML = valMsg1 +" "+ 2 +" "+ valMsg2;
        return false;
    } else if (ansthree === "" || ansthree === null) {
        document.getElementById("errormsg").innerHTML = valMsg1 +" "+ 3 +" "+ valMsg2;
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
//    validation();
    if (validation()) {
        document.getElementById('preferenceForm').submit();
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
