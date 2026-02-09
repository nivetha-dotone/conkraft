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
		         shpt[1].setAttribute('id', 'add_cmpny_st_state_id');alert(shpt[1].getAttribute('id'));
		         shpt[1].setAttribute('name', 'habCountryStatesByShiptoAddressState.stateName');
		      }
		      form.add_cmpny_st_state_id.value = form.add_cmpny_bt_state_id.value;
		      form.add_cmpny_st_city_id.value = form.add_cmpny_bt_city_id.value;
		      form.add_cmpny_st_zip_id.value = form.add_cmpny_bt_zip_id.value;
		      form.add_cmpny_st_ph_id.value = form.add_cmpny_bt_ph_id.value;
		      form.add_cmpny_st_em_id.value = form.add_cmpny_bt_em_id.value;
		   }
	}
		
		
		
		function clear_logo_filename(spantag_id) { 
			   var value = "<input name='edit_cmpny_logo_name' id='edit_cmpny_logo_id' type='file' class='inputfield span8' />";
			   document.getElementById(spantag_id).innerHTML = "";
			   document.getElementById(spantag_id).innerHTML = value;
			   $(":file").uniform({fileDefaultText: 'No file selected'});
			}
		
		
		
		function clearmessage() {
		    document.getElementById("errormsg1").innerHTML = "";
		}
		
		/*function add_page() {
			   
		      document.getElementById('first_page').value = 'Yes'; 
		      var first=document.getElementById('first_page').value;
		      if(doValidations2('companyeditform','companiesValidations','errormsg1',first))
		    
		}*/
		
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
		
		function isNumberKey(evt) {
		    var charCode = (evt.which) ? evt.which : event.keyCode;
		    if(charCode > 31 && (charCode < 48 || charCode > 57))
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
		function validateFloatKeyPress(el, evt) {
			   var charCode = (evt.which) ? evt.which : event.keyCode;
			   if (charCode != dec_charCode && charCode > 31 && (charCode < 48 || charCode > 57)) {
			      return false;
			   }
			   if (charCode == dec_charCode && el.value.indexOf('.') !== -1) {
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
			