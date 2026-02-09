/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

function cancle() {
    var base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_login';
}
function getXMLHTTP() {
    var xmlhttp = false;
    try {
        xmlhttp = new XMLHttpRequest();
    } catch (e) {
        try {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        } catch (e) {
            try {
                xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e1) {
                xmlhttp = false;
            }
        }
    }
    return xmlhttp;
}
function ajax1(url, id) {
    var strURL = url;
    var divid = id;
    var req = getXMLHTTP();
    if (req) {
        req.onreadystatechange = function () {
            if (req.readyState == 4) {
                if (req.status == 200) {
                    document.getElementById(divid).innerHTML = req.responseText;
                } else {
                }
            }
        }
        req.open("GET", strURL, false);
        req.send(null);
    }
}
function checkanswers() {
    var email = document.getElementById('edit_user_answer1_id').value;
    email = $.trim(email);
    var mail = document.getElementById('forgotemail').value;
    var name = document.getElementById('user_name').value;
    var id = document.getElementById('user_id').value;
    id = $.trim(id);
    if (email === "") {
        document.getElementById('input_switch').value = '0';
        document.getElementById("errormsg").innerHTML = "Please Enter Your Email";
    } else if (email !== "") {
        if (mail != email) {
            document.getElementById("errormsg").innerHTML = "Invalid email";
        } else {
            var base_url = document.getElementById('base_url').value;
            var url = base_url + "ctrl_forgotpassword/forget_password/" + email + '/' + id + '/' + name;
            ajax1(url, "switch");
            toggle();
        }
    }
}
var val = 0;
function toggle() {
    var maindiv = document.getElementById('maindiv_block');
    var subdiv = document.getElementById('subdiv_block');
    subdiv.style.display = 'block';
    maindiv.style.display = 'none';
}
function edit_page() {
    checkanswers();
    if (checkanswers()) {
        document.getElementById('forgotpassword').submit();
    }
}
function clearmessage() {
    document.getElementById("errormsg").innerHTML = "";
}
