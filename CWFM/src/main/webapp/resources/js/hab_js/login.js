/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

function validation() {
    var useraccount = document.getElementById('login_user_account_id').value;
    useraccount = $.trim(useraccount);
    var password = document.getElementById('login_password_id').value;
    password = $.trim(password);
    if (useraccount === "" || useraccount === "null") {
        var val_mag1 = document.getElementById('val_mag1').value;
        document.getElementById("errormsg").innerHTML = val_mag1;
        return false;
    } else if (password === "" || password === "null") {
        var val_mag2 = document.getElementById('val_mag2').value;
        document.getElementById("errormsg").innerHTML = val_mag2;
        return false;
    } else if (Object.hasOwnProperty.call(window, "ActiveXObject") && !window.ActiveXObject) {
        document.getElementById("browser_is_ie11_id").value = "IE11";
        return true;
    }
}
function refresh() {
    document.getElementById("errormsg").innerHTML = "";
}
function Login1() {
    var useraccount = document.getElementById('login_user_account_id').value;
    useraccount = $.trim(useraccount);
    if (useraccount === "" || useraccount === "null") {
        var val_mag1 = document.getElementById('val_mag1').value;
        document.getElementById("errormsg").innerHTML = val_mag1;
        return false;
    } else {
        var base_url = document.getElementById('base_url').value;
        window.location.href = base_url+'ctrl_forgotpassword/forget/' + useraccount;
    }
}
function login(){
    validation();
    if (validation()) {
        document.getElementById('login_form').submit();
    }
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
function forgotValidations() {
	var userName = document.getElementById('login_user_account_id').value;
	var valMsg1 = document.getElementById('valMsg1').value;
	var valMsg2 = document.getElementById('valMsg2').value;
	userName = userName.toLowerCase().trim();
	var temp = "";
	var submit = "";
	if(userName == "") {
		submit = "false";
		document.getElementById('errormsg').innerHTML = valMsg1;
	} else {
		$('#userListId option').each(function() {
			temp = this.value.toLowerCase();
			if(userName == temp) {
				submit = "true";
			}
		});
		if(submit != "true") 
			document.getElementById('errormsg').innerHTML = valMsg2;
	}
	var form = document.getElementById('login_form');
	if(submit == "true") {
		form.action = "forgotpassword";
		form.submit();
	};
}