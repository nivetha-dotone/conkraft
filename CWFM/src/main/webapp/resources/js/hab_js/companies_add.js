
      
	   
function add_page() {
   
      document.getElementById('first_page').value = 'Yes'; 
      var first=document.getElementById('first_page').value;
      var second=document.getElementById('second_page').value;
      var third=document.getElementById('third_page').value;
      if(doValidationsCompany('companyaddform','companiesValidations','errormsg1',first,second,third))
    	nextshowdiv();
}

function comp_resource_add_page() {
	      document.getElementById('first_page').value = 'No';
	      document.getElementById('second_page').value = 'Yes';
	      var first=document.getElementById('first_page').value;
	      var second=document.getElementById('second_page').value;
	      var third=document.getElementById('third_page').value;
	      if(doValidationsCompany('companyaddform','companiesValidations','errormsg2',first,second,third))
	    	nextshowdiv1();
	   
	}
function contracts_add_page3() { 
	document.getElementById('first_page').value = 'No'; 
	document.getElementById('second_page').value = 'No';
	
	document.getElementById('third_page').value = 'Yes';
	var first=document.getElementById('first_page').value;
    var second=document.getElementById('second_page').value;
    var third=document.getElementById('third_page').value;      
	    if(doValidationsCompany('companyaddform','companiesValidations','errormsg3',first,second,third))
	    	nextshowdivThirdPage();
	  
	}


function nextshowdiv() { 
	
	   var cmpdiv1 = document.getElementById('companydetails-block').style.display;
	   var cmpresourcediv1 = document.getElementById('companyresource-block').style.display;
	   var cmpcontractsdiv1 = document.getElementById('contractsdetails-block').style.display;
	   var cmpdiv = document.getElementById('companydetails-block');
	   var cmpresourcediv = document.getElementById('companyresource-block');
	   var contractsdiv1 = document.getElementById('contractsdetails-block');
	   if ((cmpresourcediv1 == 'none' || cmpresourcediv1 == '')) {
	      cmpresourcediv.style.display = 'block';
	    
	      cmpdiv.style.display = 'none';
	   }
	}
	function nextshowdiv1() {
	   var cmpdiv1 = document.getElementById('companydetails-block').style.display;
	   var cmpresourcediv1 = document.getElementById('companyresource-block').style.display;
	   var cmpcontractsdiv1 = document.getElementById('contractsdetails-block').style.display;
	   var cmpdiv = document.getElementById('companydetails-block');
	   var cmpresourcediv = document.getElementById('companyresource-block');
	   var contractsdiv1 = document.getElementById('contractsdetails-block');
	   if ((cmpcontractsdiv1 == 'none' || cmpcontractsdiv1 == '')) {
	      cmpresourcediv.style.display = 'none';
	      contractsdiv1.style.display = 'block';
	      
	   }
	}
	function nextshowdivThirdPage() {
		
	   var cmpdiv1 = document.getElementById('companydetails-block').style.display;
	   var cmpresourcediv1 = document.getElementById('companyresource-block').style.display;
	   var cmpcontractsdiv1 = document.getElementById('contractsdetails-block').style.display;
	   var paydetailsdiv = document.getElementById('paymentdetailss-block').style.display;
	   var cmpdiv = document.getElementById('companydetails-block');
	   var cmpresourcediv = document.getElementById('companyresource-block');
	   var contractsdiv1 = document.getElementById('contractsdetails-block');
	   var paydetailsdiv1 = document.getElementById('paymentdetailss-block');
	   if ((paydetailsdiv == 'none' || cmpcontractsdiv1 == '')) {
	      cmpresourcediv.style.display = 'none';
	      contractsdiv1.style.display = 'none';
	      paydetailsdiv1.style.display = 'block';
	      
	   }
	}
	
	function prevshowdiv() {
		   
		   //document.getElementById('expand_collapse_id').style.display = 'inline-block';
		   var cmpdiv1 = document.getElementById('companydetails-block').style.display;
		   var cmpresourcediv1 = document.getElementById('companyresource-block').style.display;
		   var contractsdiv1 = document.getElementById('contractsdetails-block').style.display;
		   var cmpresourcediv = document.getElementById('companyresource-block');
		   var contractsdiv1 = document.getElementById('contractsdetails-block');
		   var cmpdiv = document.getElementById('companydetails-block');
		   if (contractsdiv1 == 'block' || contractsdiv1 != '') {
		      cmpresourcediv.style.display = 'block';
		      cmpdiv.style.display = 'none';
		      contractsdiv1.style.display = 'none';
		   } else {
		      cmpresourcediv1.style.display = 'none';
		      contractsdiv1.style.display = 'block';
		      cmpdiv.style.display = 'none';
		   }
		}
		function prevshowdiv1() {
		   
		  // document.getElementById('expand_collapse_id').style.display = 'inline-block';
		   var cmpresourcediv2 = document.getElementById('companyresource-block').style.display;
		   var contractsdiv2 = document.getElementById('contractsdetails-block').style.display;
		   var cmpresourcediv = document.getElementById('companyresource-block');
		   var contractsdiv2 = document.getElementById('contractsdetails-block');
		   var cmpdiv = document.getElementById('companydetails-block');
		   if (cmpresourcediv2 == 'block' || cmpresourcediv2 != '') {
		      cmpresourcediv.style.display = 'none';
		      cmpdiv.style.display = 'block';
		   } else {
		      cmpresourcediv.style.display = 'block';
		      cmpdiv.style.display = 'none';
		   }
		}
		function prevshowdiv2() {
		   
		   var cmpresourcediv2 = document.getElementById('companyresource-block').style.display;
		   var contractsdiv2 = document.getElementById('contractsdetails-block').style.display;
		   var paydetailsdiv = document.getElementById('paymentdetailss-block').style.display;
		   var cmpresourcediv = document.getElementById('companyresource-block');
		   var contractsdiv2 = document.getElementById('contractsdetails-block');
		   var cmpdiv = document.getElementById('companydetails-block');
		   var paydetailsdiv2 = document.getElementById('paymentdetailss-block');
		   if (paydetailsdiv == 'block' || paydetailsdiv != '') {
		      cmpresourcediv.style.display = 'none';
		      contractsdiv2.style.display = 'block';
		   } else {
		      cmpresourcediv.style.display = 'none';
		      contractsdiv2.style.display = 'none';
		      paydetailsdiv2.style.display = 'block';
		   }
		}
	
	
	
	function should_save_all_pages() {
		document.getElementById('first_page').value = 'No'; 
		document.getElementById('second_page').value = 'No'; 
		document.getElementById('third_page').value = 'No';
		  
		   var first_page = document.getElementById('first_page').value;
		   var second_page = document.getElementById('second_page').value;
		   var third_page = document.getElementById('third_page').value;
		   if (first_page === 'No' && second_page === 'No' && third_page === 'No') {
		      return true;
		   } else {
		      return false;
		   }
		}
	
	
	function cmpnyradioCopyBillTo(form, copy) {
		   if (copy === 'BillTo') {
		      form.add_cmpny_rt_add1_id.value = form.add_cmpny_bt_add1_id.value;
		      form.add_cmpny_rt_add2_id.value = form.add_cmpny_bt_add2_id.value;
		      form.add_cmpny_rt_cntry_id.value = form.add_cmpny_bt_cntry_id.value;
		      document.getElementById('add_cmpny_rt_state_div').innerHTML = document.getElementById('add_cmpny_bt_state_div').innerHTML;
		      var shpt = document.getElementById('add_cmpny_rt_state_div').children;
		      if (shpt[1].tagName === 'SELECT') {
		         shpt[1].setAttribute('id', 'add_cmpny_rt_state_id');
		         shpt[1].setAttribute('name', 'habCountryStatesByRemittoAddressState.stateName');
		      }
		      form.add_cmpny_rt_state_id.value = form.add_cmpny_bt_state_id.value;
		      form.add_cmpny_rt_city_id.value = form.add_cmpny_bt_city_id.value;
		      form.add_cmpny_rt_zip_id.value = form.add_cmpny_bt_zip_id.value;
		      form.add_cmpny_rt_ph_id.value = form.add_cmpny_bt_ph_id.value;
		      form.add_cmpny_rt_em_id.value = form.add_cmpny_bt_em_id.value;
		   } else {
		      form.add_cmpny_rt_add1_id.value = form.add_cmpny_st_add1_id.value;
		      form.add_cmpny_rt_add2_id.value = form.add_cmpny_st_add2_id.value;
		      form.add_cmpny_rt_cntry_id.value = form.add_cmpny_st_cntry_id.value;
		      document.getElementById('add_cmpny_rt_state_div').innerHTML = document.getElementById('add_cmpny_st_state_div').innerHTML;
		      var shpt = document.getElementById('add_cmpny_rt_state_div').children;
		      if (shpt[1].tagName === 'SELECT') {
		         shpt[1].setAttribute('id', 'add_cmpny_rt_state_id');
		         shpt[1].setAttribute('name', 'habCountryStatesByRemittoAddressState.stateName');
		      }
		      form.add_cmpny_rt_state_id.value = form.add_cmpny_st_state_id.value;
		      form.add_cmpny_rt_city_id.value = form.add_cmpny_st_city_id.value;
		      form.add_cmpny_rt_zip_id.value = form.add_cmpny_st_zip_id.value;
		      form.add_cmpny_rt_ph_id.value = form.add_cmpny_st_ph_id.value;
		      form.add_cmpny_rt_em_id.value = form.add_cmpny_st_em_id.value;
		   }
		}
	function cmpnyCopyBillTo(form) {
		   if (form.add_cmpny_copy_bt.checked === true) {
		      form.add_cmpny_st_add1_id.value = form.add_cmpny_bt_add1_id.value;
		      form.add_cmpny_st_add2_id.value = form.add_cmpny_bt_add2_id.value;
		      form.add_cmpny_st_cntry_id.value = form.add_cmpny_bt_cntry_id.value;
		      document.getElementById('add_cmpny_st_state_div').innerHTML = document.getElementById('add_cmpny_bt_state_div').innerHTML;
		      var shpt = document.getElementById('add_cmpny_st_state_div').children;
		      if (shpt[1].tagName === 'SELECT') {
		         shpt[1].setAttribute('id', 'add_cmpny_st_state_id');
		         shpt[1].setAttribute('name', 'habCountryStatesByShiptoAddressState.stateName');
		      }
		      form.add_cmpny_st_state_id.value = form.add_cmpny_bt_state_id.value;
		      form.add_cmpny_st_city_id.value = form.add_cmpny_bt_city_id.value;
		      form.add_cmpny_st_zip_id.value = form.add_cmpny_bt_zip_id.value;
		      form.add_cmpny_st_ph_id.value = form.add_cmpny_bt_ph_id.value;
		      form.add_cmpny_st_em_id.value = form.add_cmpny_bt_em_id.value;
		   }
	}
function validateFloatKeyPress(el, evt) {
var charCode = (evt.which) ? evt.which : event.keyCode;
if (charCode != dec_charCode && charCode > 31 && (charCode < 48 || charCode > 57)) {
 return false;
}
if (charCode == dec_charCode && el.value.indexOf(dec_point) !== -1) {
return false;
}
return true;
 }

function clearmessage() {
   // document.getElementById('errormsg').innerHTML = '';
    document.getElementById('errormsg1').innerHTML = '';
    document.getElementById('errormsg2').innerHTML = '';
    document.getElementById('errormsg3').innerHTML = '';
    }



function submitFrom(id) {
   	var error = document.getElementById('errormsg').innerHTML;
   	if(error == "") document.getElementById(id).submit();
   }
function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if(charCode > 31 && (charCode < 48 || charCode > 57))
       return false;
    else
       return true;
 }
function isNumberKeyForAmount(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if(charCode > 31 && ((charCode < 48 || charCode > 57) && charCode!=46 && charCode!=44))
       return false;
    else
       return true;
 }
function zip(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode; 
    if(charCode !== 45 && charCode > 31 && (charCode < 48 || charCode > 57) && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122)) {
       return false;
    }
    else{
       return true;
    } 
}


var format='#,###.##';
format=format.toString();
var thousands_sep=format.charAt(format.length-7);
var dec_point=format.charAt(format.length-3);
var regex = ''; dec_charCode='';
if(dec_point=='.') {
regex = /^\d{1,4}(\.\d{1,2})?$/;
dec_charCode='46';
}else if(dec_point==',') {
regex = /^\d{1,4}(\,\d{1,2})?$/;
dec_charCode='44';
}else if(dec_point==' ') {
regex = /^\d{1,4}(\ \d{1,2})?$/;
dec_charCode='32';
}else {
regex = /^\d{1,4}(\.\d{1,2})?$/;
dec_charCode='46';
}


function validateFloatKeyPress(el, evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode != dec_charCode && charCode > 31 && (charCode < 48 || charCode > 57)) {
	 return false;
	}
	if (charCode == dec_charCode && el.value.indexOf(dec_point) !== -1) {
	 return false;
	}
	return true;
 }
function isSpaceKey(evt) {
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode == 32)
	      return false;
	   else
	      return true;
	}
function isAllowedCmp(evt) {
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode === 34 || charCode === 37 || charCode === 39 || charCode === 92)
	      return false;
	   else
	      return true;
	}
function hyphen_generate(evt, value, id) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	 if (value.length === 3 && charCode !== 8)
	    document.getElementById(id).value = value.concat('-');
	 if (value.length === 7 && charCode !== 8)
	  document.getElementById(id).value = value.concat('-');
	var charCode = (evt.which) ? evt.which : event.keyCode;
	 if(charCode === 43 ||charCode ===45 || charCode > 31 && (charCode < 48 || charCode > 57))
	   return false;
	else
	  return true;
	}