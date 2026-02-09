function validation() {
    var newPassword = document.getElementById('newPasswordId').value;
    newPassword = $.trim(newPassword);
    var confirmPassword = document.getElementById('confirmPasswordId').value;
    confirmPassword = $.trim(confirmPassword);
    var valMsg1 = document.getElementById('valMsg1').value;
    var valMsg2 = document.getElementById('valMsg2').value;
    var valMsg3 = document.getElementById('valMsg3').value;
    var valMsg4 = document.getElementById('valMsg4').value;
    if (newPassword.length == 0) {
        document.getElementById("errormsg").innerHTML = valMsg1;
        return false;
    } else if (newPassword.length < 8) {
        document.getElementById("errormsg").innerHTML = valMsg2;
        return false;
    } else if (confirmPassword.length == 0) {
        document.getElementById("errormsg").innerHTML = valMsg3;
        return false;
    } else if (confirmPassword != newPassword) {
        document.getElementById("errormsg").innerHTML = valMsg4;
        return false;
    } else {
        document.getElementById("errormsg").innerHTML = "";
        document.getElementById("resetPasswordForm").submit();
        return true;
    }
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
