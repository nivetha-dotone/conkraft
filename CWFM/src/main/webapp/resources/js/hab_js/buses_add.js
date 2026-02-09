 var base_url= document.getElementById('base_url').value.trim();
   function set_date() {
      date = jQuery('#add_sp_startdate_id').datepicker('getDate');
      change_date_format(date, 'hidden_add_sp_startdate_id');
   }
      function change_date_format(date, input_id) {
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      if (month < 10) {
         month = '0' + month;
      }
      if (day < 10) {
         day = '0' + day;
      }
      document.getElementById(input_id).value = year + '-' + month + '-' + day;
      
   }
   function back_to_regions() { 
      var base_url= document.getElementById('base_url').value.trim();
      window.location.href = base_url+'ctrl_regions';
   }
   function getStates(CountryId, div_Id, tag_Id, tag_Name){
       var base_url= document.getElementById('base_url').value.trim();
      if (CountryId === '') {
         var url = base_url+'ctrl_companies/dep_country/' + null + '/' + tag_Id + '/' + tag_Name;
         ajax(url, div_Id);
      } else {
         var country01 = CountryId.replace('$', '<$>');
         var cntry_Id = country01.split('<$>');
         var cntry_Id01 = cntry_Id[0].split('<');
         var url = base_url+'ctrl_regions/dep_country/' + cntry_Id01[0] + '/' + tag_Id + '/' + tag_Name;
         ajax(url, div_Id);
      }
   }
   function FormValidation() {
      var email_validator = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+[\.]{1}[a-zA-Z]{2,4}$/;
      var companyname = document.getElementById('add_regions_cmpnyid').value;
      var zone = document.getElementById('add_zone_id').value.trim();
      var region = document.getElementById('add_compres_fname_id').value.trim();
      var region_code = document.getElementById('add_compres_lname_id').value.trim();
      var addrsln1 = document.getElementById('add_compres_addr1_id').value.trim();
      var state = document.getElementById('add_resource_state_id').value.trim();
      var city = document.getElementById('add_compres_city_id').value.trim();
      var zip = document.getElementById('add_compres_zip_id').value.trim();
      var val_msg1 = document.getElementById('val_msg1').value.trim();
      var val_msg2 = document.getElementById('val_msg2').value.trim();
      var val_msg3 = document.getElementById('val_msg3').value.trim();
      var val_msg4 = document.getElementById('val_msg4').value.trim();
      var val_msg5 = document.getElementById('val_msg5').value.trim();
      var val_msg6 = document.getElementById('val_msg6').value.trim();
      var val_msg7= document.getElementById('val_msg7').value.trim();
      var val_msg8= document.getElementById('val_msg8').value.trim();
      var val_msg9 = document.getElementById('val_msg9').value.trim();
      var val_msg10 = document.getElementById('val_msg10').value.trim();
      var val_msg11 = document.getElementById('val_msg11').value.trim();
      var val_msg12 = document.getElementById('val_msg12').value.trim();
      var val_msg13 = document.getElementById('val_msg13').value.trim();
      var val_msg14 = document.getElementById('val_msg14').value.trim();
      //var  worknum = document.getElementById('add_compres_worknum_id').value.trim();
      //worknum = worknum.replace('-', '').replace('-', '');
      var phone = document.getElementById('add_compres_phone_id').value.trim();
      phone = phone.replace('-', '').replace('-', '');
      var email = document.getElementById('add_compres_email_id').value.trim();
      if (companyname === '' || companyname === null) {
         document.getElementById('errormsg').innerHTML = val_msg1;
         return false;
      } else if (zone === '' || zone === null) {
         document.getElementById('errormsg').innerHTML =val_msg2; 
         return false;
      } else if (region === '' || region === null) {
         document.getElementById('errormsg').innerHTML = val_msg3;
         return false;
      } else if ((region_code === '' || region_code === null) ) {
         document.getElementById('errormsg').innerHTML = val_msg4;
         return false;
      } else if(region_code.length<3){
           document.getElementById('errormsg').innerHTML=val_msg5;
           return false;
      } else if (addrsln1 === '' || addrsln1 === null) {
         document.getElementById('errormsg').innerHTML = val_msg6;
         return false;
      } else if (state === '' || state === val_msg7){
         document.getElementById('errormsg').innerHTML = val_msg8;
         return false;
      } else if (city === '' || city === null) {
         document.getElementById('errormsg').innerHTML = val_msg9;
         return false;
      } else if (zip === '' || zip === null) {
         document.getElementById('errormsg').innerHTML = val_msg10;
         return false;
      }/* else if (worknum !== '' && worknum.length < 10) {
         document.getElementById('errormsg').innerHTML = '<?php echo lang('LAN_COMMON_CONTACT_PROMPT') . ' ' . lang('LAN_COMMON_COLUMNTWORKNUMBER_TOOLTIP') . ' ' . lang('LAN_MESSAGE_MUSTBE_PROMPT') . ' ' . '10' . ' ' . lang('LAN_COMMON_NUMBERS_PROMPT'); ?>';
         return false;
      }*/ else if (phone === '' || phone === null) {
         document.getElementById('errormsg').innerHTML =val_msg11;
         return false;
      } else if (phone.length < 10) {
         document.getElementById('errormsg').innerHTML = val_msg12;
         return false;
      } else if (email === '' || email === null) {
         document.getElementById('errormsg').innerHTML = val_msg13;
         return false;
      } else if (email_validator.test(email) !== true) {
         document.getElementById('errormsg').innerHTML = val_msg14;
         return false;
      } else {
         document.getElementById('errormsg').innerHTML = '';
         return codeValidation();
      }
   }
   function addcodeUniqueness(div_Id,tag_Id,tag_Name){ 
      var compny= document.getElementById('add_regions_cmpnyid').value;
      compny=compny.split('<$>');//alert(compny);
      var zone= document.getElementById('add_zone_id').value.split('<$>');//alert(zone);
      var region_name= document.getElementById('add_compres_fname_id').value.split('#').join('ha_sh_sm-bl').split('^').join('%5E').split('?').join('qu_stn_mrk_sm-bl').split('/').join('fr_wrd_sl_sh_sm-bl').split('<').join('%3C').split('>').join('%3E').split('[').join('%5B').split(']').join('%5D').split('{').join('%7B').split('}').join('%7D').split('`').join('%60').split('.').join('dt_sm-bl').split('(').join('opn_br_ckt_sm-bl').split(')').join('cl_sd_br_ckt_sm-bl').split('$').join('dlr_sm-bl');
   region_name=region_name.trim();
      var region_code= document.getElementById('add_compres_lname_id').value.split('#').join('ha_sh_sm-bl').split('^').join('%5E').split('?').join('qu_stn_mrk_sm-bl').split('/').join('fr_wrd_sl_sh_sm-bl').split('<').join('%3C').split('>').join('%3E').split('[').join('%5B').split(']').join('%5D').split('{').join('%7B').split('}').join('%7D').split('`').join('%60').split('.').join('dt_sm-bl').split('(').join('opn_br_ckt_sm-bl').split(')').join('cl_sd_br_ckt_sm-bl').split('$').join('dlr_sm-bl');
  region_code=region_code.trim();
   var base_url= document.getElementById('base_url').value.trim();
      var url=base_url+'ctrl_regions/add_codeuniqueness/'+tag_Id+'/'+tag_Name+'/'+compny[0]+'/'+zone[0]+'/'+region_name+'/'+region_code;
     ajax(url,div_Id);
   } 
   function codeValidation() {
        var validation = document.getElementById('add_codecommon_id').value;
        var val_msg15 = document.getElementById('val_msg15').value.trim();
        var val_msg16 = document.getElementById('val_msg16').value.trim();
        if (validation === 'name_false') {
            document.getElementById('errormsg').innerHTML = val_msg15;
            return false;
        } if (validation === 'code_false') {
            document.getElementById('errormsg').innerHTML = val_msg16;
            return false;
        }  else {
            document.getElementById('errormsg').innerHTML = '';
            return true;
        }
    }
   function clearmessage() {
      document.getElementById('errormsg').innerHTML = '';
   }
   function add_page() {
      //findUnique();
      FormValidation();
      if (FormValidation()) {
         document.getElementById('regions_addform').submit();
      }
   }
   function isNumberValue(el, evt) {
      var charCode = (evt.which) ? evt.which : event.keyCode;
      if (charCode != dec_charCode && charCode > 31 && (charCode < 48 || charCode > 57)) {
         return false;
      }
      if (charCode == dec_charCode && el.value.indexOf(dec_point) !== -1) {
         return false;
      }
      return true;
   }
    function hyphen_generate(evt, value, id) {  //alert();
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
   function get_zone_list(divId, tagId, tagName) {
      var company = document.getElementById('add_regions_cmpnyid').value.split('<$>');
      if (company[0]!== ''){  
//         company[0] = 'null';
      var base_url= document.getElementById('base_url').value.trim();
      var url = base_url+'ctrl_regions/get_zone_list/' + company[0] + '/' + tagId + '/' + tagName;
      ajax(url, divId);
      }else{
       var base_url= document.getElementById('base_url').value.trim();
      var url = base_url+'ctrl_regions/get_zone_list/' + null + '/' + tagId + '/' + tagName;
      ajax(url, divId);
   }

   }
   function nospecialchar(evt) { 
      var charCode = (evt.which) ? evt.which : event.keyCode; 
      if(charCode !== 32 && charCode !== 39 && charCode !== 46 && charCode !== 45 && charCode !== 45 && charCode > 31 && (charCode < 48 || charCode > 57) && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122)) {
         return false;
      }
      else{
         return true;
    } 
    }
   function isLetter(evt) {
       var charCode = (evt.which) ? evt.which : event.keyCode;
       if (charCode > 31 && (charCode < 48 || charCode > 57) && (charCode < 65 || charCode > 90) && (charCode < 97 || charCode > 122)) {
           return false;
       }
       else {
           return true;
       }
   }

   function isNumberKey(evt) {
	     var charCode = (evt.which) ? evt.which : event.keyCode;
	     if (charCode > 31 && (charCode < 48 || charCode > 57))
	        return false;
	     else
	        return true;
	  }
	 
 
   function clearText(a){if(a.defaultValue==a.value){a.value=""}else{if(a.value==""){a.value=a.defaultValue}}};
  
   
   function submitFrom(id) {
   	var error = document.getElementById('errormsg').innerHTML;
   	if(error == "") document.getElementById(id).submit();
   }
   function getDepency() {
	   if(add_company_id == ""){
		   listDependency('getzonesList','add_zone_id','add_company_id');
		   listDependency('getregions','add_region_id','add_zone_id');
		   listDependency('getdepots','add_depot_id','add_region_id');
      } if(add_zone_id == ""){
    	  listDependency('getregions','add_region_id','add_zone_id');
		  listDependency('getdepots','add_depot_id','add_region_id');
	  } if(add_region_id == ""){
		  listDependency('getdepots','add_depot_id','add_region_id');
	  }
   
	   }
   // New Code End.
    function get_zone_address_details(zone) {
        var company = document.getElementById('add_regions_cmpnyid').value;
        if(company != '' && zone != '' && zone != null) {
            zone = zone.split('<$>');
             base_url= document.getElementById('base_url').value.trim();
            var url=base_url+'ctrl_regions/get_zone_address_details/'+zone[0]+'/add_compres_addr1_id/add_compres_addr1_name/add_compres_addr2_id/add_compres_addr2_name/add_resource_country_id/add_resource_country/add_resource_state_id/add_resource_state_name/add_compres_city_id/add_compres_city_name/add_compres_zip_id/add_compres_zip_name/add_compres_worknum_id/add_compres_worknum_name/add_compres_phone_id/add_compres_phone_name/add_compres_email_id/add_compres_email_name/add_sp_bt_state_div';
            ajax(url,'address_details_block');
        } else {
            var url=base_url+'ctrl_regions/get_zone_address_details/null/add_compres_addr1_id/add_compres_addr1_name/add_compres_addr2_id/add_compres_addr2_name/add_resource_country_id/add_resource_country/add_resource_state_id/add_resource_state_name/add_compres_city_id/add_compres_city_name/add_compres_zip_id/add_compres_zip_name/add_compres_worknum_id/add_compres_worknum_name/add_compres_phone_id/add_compres_phone_name/add_compres_email_id/add_compres_email_name/add_sp_bt_state_div';
            ajax(url,'address_details_block');
        }
    }