function validateEmail() {
    var email = document.getElementById('edit_user_answer1_id').value;
    email = $.trim(email);
    var mail = document.getElementById('forgotemail').value;
	var valMsg1 = document.getElementById('valMsg1').value;
	var valMsg2 = document.getElementById('valMsg2').value;
    if (email === "") {
        document.getElementById('input_switch').value = '0';
        document.getElementById("errormsg").innerHTML = valMsg1;
    } else if (email !== "") {
        if (mail != email) {
            document.getElementById("errormsg").innerHTML = valMsg2;
        } else {
        	document.getElementById('forgotpassword').submit();
        	document.getElementById("submit1").disabled = true;
        	document.getElementById("submit2").disabled = true;
        }
    }
}

function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
