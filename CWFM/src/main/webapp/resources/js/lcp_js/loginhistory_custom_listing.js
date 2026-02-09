/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
var loginuserjquerydateformat = document.getElementById('j_date_format').value;
$("head").append('<script type="text/javascript" src="' + base_url + 'js/ajax_function.js' + '"></script>');
$("head").append('<script type="text/javascript" src="' + base_url + 'js/jquery-1.9.1.js' + '"></script>');
$("head").append('<script type="text/javascript" src="' + base_url + 'js/jquery-ui.js' + '"></script>');
function go() {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    window.location.href = base_url + 'ctrl_loginhistory';
}
function compareDates(fromdate, todate) {
    if (todate.getFullYear() < fromdate.getFullYear()) {
        return false;
    } else if (todate.getFullYear() === fromdate.getFullYear()) {
        if (todate.getMonth() < fromdate.getMonth()) {
            return false;
        } else if (todate.getMonth() === fromdate.getMonth()) {
            if (todate.getDate() < fromdate.getDate()) {
                return false;
            } else if (todate.getDate() >= fromdate.getDate()) {
                return true;
            }
        } else if (todate.getMonth() > fromdate.getMonth()) {
            return true;
        }
    } else if (todate.getFullYear() > fromdate.getFullYear()) {
        return true;
    }
}
jQuery = jQuery.noConflict();
jQuery(function () {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    if (loginuserjquerydateformat == '' || loginuserjquerydateformat == null)
        loginuserjquerydateformat = document.getElementById('j_date_format').value;
    for (i = 0; i < 10; i++) {
        jQuery("#custom_fromdate_id").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: loginuserjquerydateformat,
            showOn: "button",
            buttonImage: base_url + "img/icon_calendar.gif",
            buttonImageOnly: true,
            maxDate: '0'
        });
    }
});
jQuery(function () {
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
    if (loginuserjquerydateformat == '' || loginuserjquerydateformat == null)
        loginuserjquerydateformat = document.getElementById('j_date_format').value;
    jQuery("#custom_todate_id").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: loginuserjquerydateformat,
        showOn: "button",
        buttonImage: base_url + "img/icon_calendar.gif",
        buttonImageOnly: true,
        maxDate: '0'
    });
});
function date_validations() {
    var custom_fromdate_id = document.getElementById('custom_fromdate_id').value;
    if (custom_fromdate_id == "") {

        document.getElementById('errormsg').innerHTML = "<?=lang('LAN_COMMON_COLUMNTDATE_PROMPT').' '.lang('LAN_COMMON_COLUMNTFROM_PROMPT').' '.lang('LAN_MESSAGE_CANNOTBEBLANK_PROMPT');?>";
        return false;
    } else {
        document.getElementById('errormsg').innerHTML = "";
        return true;
    }
}
function clearmessage() {
    document.getElementById('errormsg').innerHTML = "";
}
function getfromdate() {
    var fromdate = document.getElementById("custom_fromdate_id").value;
    var todate;
    var todate1 = new Date(fromdate);
    todate = todate1.setDate(todate1.getDate() + 7);
    var todate2 = new Date(todate);
    var dd = todate2.getDate();
    var mm = todate2.getMonth() + 1;
    var yyyy = todate2.getFullYear();
    todateactl = yyyy + '-' + mm + '-' + dd;
}
$('#custom_fromdate_id').change(function () {
});
function set_dates_to_url() {
    if (date_validations() === true) {
        var from_date = document.getElementById('custom_fromdate_id').value;
        var to_date = document.getElementById('add_noofdays_id').value;
        var url = document.getElementById('custom_listing').action;
        if (from_date === "" || from_date === null) {
            from_date = "null";
        }
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        url = base_url + 'ctrl_loginhistory/index/' + from_date + 'd' + to_date + '/date';
        alert(url);
        window.location.href = url;
    }
}
function search() {
    var id = document.getElementById('custom_listing').submit();
}
