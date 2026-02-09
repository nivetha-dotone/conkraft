/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
$("head").append('<script src="' + base_url + 'js/footable.js"></script>');
$("head").append(
		'<script type="text/javascript" src="' + base_url
				+ 'js/highlight.js"></script>');
$("head").append(
		'<script type="text/javascript" src="' + base_url
				+ 'js/jquery.accordion.js"></script>');
$(function() {
	$('.audit-trail-bg')
			.footable(
					{
						toggleHTMLElement : '<span><img src='
								+ base_url
								+ '"img/plus.png" class="footable-toggle footable-expand" border="0" alt="Expand">'
								+ '<img src='
								+ base_url
								+ '"img/minus.png" class="footable-toggle footable-contract" border="0" alt="Contract"></span>'
					});
});
$(document).ready(function() {
	hljs.tabReplace = '    ';
	hljs.initHighlightingOnLoad();
	$.fn.slideFadeToggle = function(speed, easing, callback) {
		return this.animate({
			opacity : 'toggle',
			height : 'toggle'
		}, speed, easing, callback);
	};
	$('.accordion').accordion({
		defaultOpen : 'section1',
		cookieName : 'accordion_nav',
		speed : 'slow',
		animateOpen : function(elem, opts) {
			elem.next().stop(true, true).slideFadeToggle(opts.speed);
		},
		animateClose : function(elem, opts) {
			elem.next().stop(true, true).slideFadeToggle(opts.speed);
		}
	});
});
function submitFrom(id) {
	var error = document.getElementById('errormsg').innerHTML;
	if (error == "")
		document.getElementById(id).submit();
}
function clearmessage() {
	document.getElementById("errormsg").innerHTML = "";
}

function isNumberKey(evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57))

		return false;
	else
		return true;
}

function clearText(a) {
	if (a.defaultValue == a.value) {
		a.value = ""
	} else {
		if (a.value == "") {
			a.value = a.defaultValue
		}
	}
};
function hyphen_generate(evt, value, id) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (value.length === 3 && charCode !== 8)
		document.getElementById(id).value = value.concat('-');
	if (value.length === 7 && charCode !== 8)
		document.getElementById(id).value = value.concat('-');
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode === 43 || charCode === 45 || charCode > 31
			&& (charCode < 48 || charCode > 57))
		return false;
	else
		return true;
}
function getZoneAdderessDetails(id) {// alert(id);
	inputDependency('getAddLine1', 'add_addline1_id', id);
	inputDependency('getAddLine2', 'add_addline2_id', id);
	inputDependency('getCity', 'add_city_id', id);
	inputDependency('getZip', 'add_zip_id', id);
	inputDependency('getWnum', 'add_wnum_id', id);
	inputDependency('getPnum', 'add_phone_id', id);
	inputDependency('getEmail', 'add_email_id', id);
	listAddressDependency('getcountries', 'add_country_id', id);
	// listAddressDependency('getDefaultState','add_state_id',id);
}
function getComapnyAdderessDetails(id) {
	inputDependency('getzAddLine1', 'add_addline1_id', 'add_company_id');
	inputDependency('getzAddLine2', 'add_addline2_id', 'add_company_id');
	inputDependency('getzCity', 'add_city_id', 'add_company_id');
	inputDependency('getzZip', 'add_zip_id', 'add_company_id');
	inputDependency('getzWnum', 'add_wnum_id', 'add_company_id');
	inputDependency('getzPnum', 'add_phone_id', 'add_company_id');
	inputDependency('getzEmail', 'add_email_id', 'add_company_id');
	listAddressDependency('getzcountries', 'add_users_country_id',
			'add_company_id');
	// listAddressDependency('getzDefaultState','add_users_state_id','add_company_id');
}
function getRegionAdderessDetails(){
	inputDependency('get1AddLine1','add_addline1_id','add_region_id');
	inputDependency('getAddLine2','add_addline2_id','add_region_id');
	inputDependency('get1City','add_city_id','add_region_id');
	
	inputDependency('get1Zip','add_zip_id','add_region_id');
	inputDependency('get1Wnum','add_wnum_id','add_region_id');
	inputDependency('get1Pnum','add_mobile_id','add_region_id');
	inputDependency('get1Email','add_email_id','add_region_id');
	listAddressDependency('get1countries','add_country_id','add_region_id');
	listDependency('get1DependentStates','add_users_state_name','add_region_id');
	
	
}
function getCompanyResourceAdderessDetails(id){
	listAddressDependency('getTimeZones','add_timezone_id',id);
   	inputDependency('getCRAddLine1','add_add1_name','add_company_id');
   	inputDependency('getCRAddLine2','add_add2_name','add_company_id');
   	inputDependency('getCRCity','add_city_name','add_company_id');
   	inputDependency('getCRZip','add_zip_name','add_company_id');
   	inputDependency('getCRPnum','add_phone_id','add_company_id');
   	inputDependency('getCRwnum','add_mobile_id','add_company_id');
   	inputDependency('getCREmail','add_em_name','add_company_id');
   	listAddressDependency('getCRcountries','add_users_country_id',id);
   	listDependency('getCRDependentStates','add_state_id','add_company_id');
   	
   }
