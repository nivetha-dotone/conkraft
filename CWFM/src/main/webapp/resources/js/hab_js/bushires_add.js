/* Application Name = Hire A Bus
 Version = 1.0
 Release Date = March 01, 2015
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

function addItem() {
    //var total_table = document.getElementById('total_table_id');
    var table = document.getElementById('table_id');
    var rowCount = table.rows.length;
    var comp = document.getElementById('add_depot_id').value.trim().split("(~)");
    var comp_id = comp[0];
    var rows_status = row_validation();
    if (comp_id !== '' && addformValidate() /*&& plus1()*/ && plus() && rows_status === 'true') {
       document.getElementById('tab_id').value = (rowCount + 1);
    var row = table.insertRow(rowCount);
       //First Cell
       var cell1 = row.insertCell(0);
       var tfrom = document.createElement("input");
       tfrom.type = "text";
       tfrom.name = "add_from_name" + rowCount;
       tfrom.id = "add_from_id" + rowCount;
       tfrom.setAttribute("maxlength", "20");
       tfrom.setAttribute("class", "inputfield span2 multiple-select");
       tfrom.setAttribute("title", "From");
       tfrom.setAttribute("autocomplete", "off");
       tfrom.setAttribute("readonly", "true");
       tfrom.setAttribute("style", "text-transform: capitalize");//style="text-transform: capitalize"
       if (rowCount === 0) {
          var to = document.getElementById('add_to_idB').value.trim();
          tfrom.setAttribute("value", to);
       } else {
          var to = document.getElementById('add_to_id' + (rowCount - 1)).value.trim();
          tfrom.setAttribute("value", to);
       }
       cell1.appendChild(tfrom);
       //Second Cell
       var cell2 = row.insertCell(1);
       var tto = document.createElement("input");
       tto.type = "text";
       tto.name = "add_to_name" + rowCount;
       tto.id = "add_to_id" + rowCount;
       tto.setAttribute("maxlength", "20");
       tto.setAttribute("class", "inputfield span2 multiple-select");
       tto.setAttribute("title", "To");
       tto.setAttribute("autocomplete", "off");
       tto.setAttribute("style", "text-transform: capitalize");
       var to = document.getElementById('add_from_idA').value.trim();
       tto.setAttribute("value", to);
       tto.onkeyup = function() {
          var to1 = document.getElementById("add_to_id" + rowCount).value;
          var p = rowCount;
          p = p + 1;
          document.getElementById("add_from_id" + p).value = to1;
          return true;
       };
       cell2.appendChild(tto);
       //Third Cell
       var cell3 = row.insertCell(2);
       var cecd = document.createElement("input");
       cecd.type = "text";
       cecd.name = "add_custdist_name" + rowCount;
       cecd.id = "add_custdist_id" + rowCount;
       cecd.setAttribute("maxlength", "10");
       cecd.setAttribute("class", "inputfield span2 multiple-select");
       cecd.setAttribute("title", "Customer Estimated Distance");
       cecd.setAttribute("autocomplete", "off");
       cecd.onkeypress = function(evt) {
          document.getElementById("errormsg").innerHTML = "";
          var ordered_qty_value = document.getElementById("add_custdist_id" + rowCount).value;
          var charCode = (evt.which) ? evt.which : event.keyCode;
          if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
             return false;
          }
          if (charCode == 46 && ordered_qty_value.indexOf('.') !== -1) {
             return false;
          }
          return true;
       };
       cecd.onkeyup = function() {
          var a1 = document.getElementById('add_custdist_idA').value;
          var a2 = document.getElementById('add_custdist_idB').value;
          var a = (Number(a1) + Number(a2));
          var a3 = Number(rowCount) + 1;
          while (a3--) {
             a = (Number(a) + Number(document.getElementById('add_custdist_id' + a3).value));
             document.getElementById('app_dist_id').value = a;
          }
         //price('price','app_price_id','app_price_name');
         inputDependency('getBHprice','app_price_id','add_companyname_id','add_depot_id','add_bustype_id','add_acneeded_id','app_dist_id','add_busesneeded_id');
       }
       cell3.appendChild(cecd);
    } else {
       if (!addformValidate()) {

       } else if (!plus()) {
                final1();
       } else {
          document.getElementById('errormsg').innerHTML = "Cannot add after to depot as initial depot";
          return false;
       }
    }
 }
function row_validation() {
    var rowone = document.getElementsByName("add_from_name[]");
    var rowtwo = document.getElementsByName("add_to_name[]");
    var rowthree = document.getElementsByName("add_custdist_name[]");
    var rowfour = document.getElementsByName("add_amtdist_name[]");
    var rowfrom1 = document.getElementById('add_from_idA').value.trim();
    var rows_validation = 'true';
    for (var i = 0; i < rowone.length; i++) {
       if (rowone[i].value === "") {
          rows_validation = 'false';
          document.getElementById('errormsg').innerHTML = "Fill above Route Details at From" + (i + 2);
          break;
       } else if (rowtwo[i].value === "") {
          rows_validation = 'false';
          document.getElementById('errormsg').innerHTML = "Fill above Route Details at To" + (i + 2);
          break;
       } else if (rowtwo[i].value == rowfrom1) {
          rows_validation = 'false';
          document.getElementById('errormsg').innerHTML = "Cannot add after to depot as initial depot";
          break;
       }
    }
    return rows_validation;
 }
  function deleteRow() { 
	clearmessage();
    var table = document.getElementById('table_id'); 
    var rowCount = table.rows.length - 1; 
    var rows = table.rows; 
    if (rowCount > 0 || rowCount == 0) 
       rows[rowCount].parentNode.removeChild(rows[rowCount]);
 }
 function delete_allrows() {
    var table = document.getElementById('table_id');
    var rows = table.rows;
    var i = rows.length;
    while (i--) {
       rows[i].parentNode.removeChild(rows[i]);
    }
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
  function isSpaceKey(evt) {
     var charCode = (evt.which) ? evt.which : event.keyCode;
     if (charCode === 32)
        return false;
     else
        return true;
  }
  function isUseracc(evt) {
     var charCode = (evt.which) ? evt.which : event.keyCode;
     if (charCode === 34 || charCode === 37 || charCode === 39 || charCode === 92)
        return false;
     else
        return true;
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
  function get_fromto(depot) {
     var depot = add_depot_id.value;
     depot = depot.split("(~)");
     depot = depot[1];
     add_from_idA.value = depot;
     add_to_idB.value = depot;
  }
  function press(to) {
     var to = add_to_idA.value;
     add_from_idB.value = to;
     return true;
  }
  function plus() {
      var rowfrom1 = document.getElementById('add_from_idA').value.trim();
      var rowto2 = document.getElementById('add_to_idB').value.trim();
      if (rowfrom1 === rowto2) {
         document.getElementById('errormsg').innerHTML = "Cannot add after to depot as initial depot";
         return false;
      } else
         return true;
   }
   function first() {
      var f1 = document.getElementById('add_to_idB').value.trim();
      document.getElementById('add_from_id0').value = f1;
   }
   function NumberKey(evt) {
	      var charCode = (evt.which) ? evt.which : event.keyCode;
	      if (charCode === 17 && charCode > 31 && (charCode < 48 || charCode > 57))
	         return false;
	      else
	         return true;
	   }
   function hyphen_generate(evt, value, id) {
	      var charCode = (evt.which) ? evt.which : event.keyCode;
	      if (value.length === 3 && charCode != 8)
	         document.getElementById(id).value = value.concat("-");
	      if (value.length === 7 && charCode != 8)
	         document.getElementById(id).value = value.concat("-");
	   }
   function total() {
	      var a1 = document.getElementById('add_custdist_idA').value;
	      var a2 = document.getElementById('add_custdist_idB').value;
	      var a = (Number(a1) + Number(a2));
	      document.getElementById('app_dist_id').value = a;
	      //price('price','app_price_id','app_price_name'); 
	      inputDependency('getBHprice','app_price_id','add_companyname_id','add_depot_id','add_bustype_id','add_acneeded_id','app_dist_id','add_busesneeded_id');
	   }
   function total1() {
	      document.getElementById('add_custdist_idA').value = null;
	      document.getElementById('add_custdist_idB').value = null;
	      var a3 = Number(document.getElementById('tab_id').value);
	      while (a3--) {
	         document.getElementById('add_custdist_id' + a3).value = null;
	      }
	   }
   function decimal_validation(value, evt) {
	      var charCode = (evt.which) ? evt.which : event.keyCode;
	      if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
	         return false;
	      }
	      if (charCode == 46 && value.indexOf('.') !== -1) {
	         return false;
	      }
	      return true;
	   }
   function toggle_visibility(address_details_block) {
	      var e = document.getElementById(address_details_block);
	      var c = document.getElementById('add_companyname_id').value;
	      var d = document.getElementById('add_depot_id').value;
	      if ((c == '') || (d == ''))
	         e.style.display = 'none';
	      else
	         e.style.display = 'block';
	   }

   var check = 0;
   function toggle_visibility1(customer_details_block1, customer_details_block) {
      var e = document.getElementById(customer_details_block1);
      var f = document.getElementById(customer_details_block);
      if (e.style.display == 'none') {
         check = 1;
         e.style.display = 'block';
         f.style.display = 'none';
      } else {
         check = 0;
         e.style.display = 'none';
         f.style.display = 'block';
      }
   }
  function cmpnyCopyBillTo(form) {
		   if (form.add_cmpny_copy_bt.checked === true) {
		      form.add_cmpny_st_add1_name.value = form.add_cmpny_bt_add1_name.value;
		      form.add_cmpny_st_add2_name.value = form.add_cmpny_bt_add2_name.value;
		      form.add_cmpny_st_cntry_name.value = form.add_cmpny_bt_cntry_name.value;
		      document.getElementById('add_cmpny_st_state_div').innerHTML = document.getElementById('add_cmpny_bt_state_div').innerHTML;
		      var shpt = document.getElementById('add_cmpny_st_state_div').children;
		      if (shpt[1].tagName === 'SELECT') {
		         shpt[1].setAttribute('id', 'add_cmpny_st_state_id');
		         shpt[1].setAttribute('name', 'add_cmpny_st_state_name');
		      }
		      form.add_cmpny_st_state_name.value = form.add_cmpny_bt_state_name.value;
		      form.add_cmpny_st_city_name.value = form.add_cmpny_bt_city_name.value;
		      form.add_cmpny_st_zip_name.value = form.add_cmpny_bt_zip_name.value;
		      form.add_cmpny_st_ph_name.value = form.add_cmpny_bt_ph_name.value;
		      form.add_cmpny_st_em_name.value = form.add_cmpny_bt_em_name.value;
		   }
	}
  function login(divId, tagId, tagName) {
      var compny = document.getElementById('add_companyname_id').value;
      compny = compny.split("(~)");
      var useraccount1 = document.getElementById('add_users_account_id1').value;
      useraccount1 = $.trim(useraccount1);
      var pwd1 = document.getElementById('add_users_password_id1').value;
      pwd1 = $.trim(pwd1);
      if (pwd1 !== "") {
         //var url = '<?= $this->config->base_url(); ?>ctrl_bushires/userLogin/' + tagId + '/' + tagName + '/' + useraccount1 + '/' + pwd1+ '/' + compny[0];
         //ajax(url, divId);
    	  inputDependency('getBHuserLogin','password_id','add_users_account_id1','add_users_password_id1','add_companyname_id');
      }
   }
  function adduserUniqueness(div_Id, tag_Id, tag_Name) {
      var user_name = document.getElementById('add_users_account_id').value.split("#").join("ha_sh_sm-bl").split("^").join("%5E").split("?").join("qu_stn_mrk_sm-bl").split("/").join("fr_wrd_sl_sh_sm-bl").split("<").join("%3C").split(">").join("%3E").split("[").join("%5B").split("]").join("%5D").split("{").join("%7B").split("}").join("%7D").split("`").join("%60").split(".").join("dt_sm-bl").split("(").join("opn_br_ckt_sm-bl").split(")").join("cl_sd_br_ckt_sm-bl").split("$").join("dlr_sm-bl");
      user_name = $.trim(user_name);
      //var url = '<?= $this->config->base_url(); ?>ctrl_companies/add_useruniqueness/' + tag_Id + '/' + tag_Name + '/' + user_name;
      //ajax(url, div_Id);  
      inputDependency('getBHuserUniq','add_usercommon_id','add_users_account_id');
   }
  function add_page() {
      if (addformValidate()  && row_validation() && contact() && final1()) {
         document.getElementById('bushireaddform').submit();
      } else
         return false;
   }
  //function addformValidate() {return true;}
  function addformValidate() {
      var company_name = document.getElementById('add_companyname_id').value.trim();
      var depot_name = document.getElementById('add_depot_id').value.trim();
      var bustype_name = document.getElementById('add_bustype_id').value.trim();
      var seats_name = document.getElementById('add_seatsneeded_id').value.trim();
      var buses_name = document.getElementById('add_busesneeded_id').value.trim();
      var reqdfrdtt_name = document.getElementById('add_reqdfrdt_id').value.trim();
      var reqdtodt_name = document.getElementById('add_reqdtodt_id').value.trim();
      var custesticumdist_name = document.getElementById('app_dist_id').value.trim();
      var custesticumprice_name = document.getElementById('app_price_id').value.trim();
      var bushireprps_name = document.getElementById('add_bushireprps_id').value.trim();
      var rowfrom1 = document.getElementById('add_from_idA').value.trim();
      var rowto1 = document.getElementById('add_to_idA').value.trim();
      var rowto2 = document.getElementById('add_to_idB').value.trim();
      var tab1 = document.getElementById('tab_id').value;
      var s = (rowfrom1.toUpperCase() === rowto1.toUpperCase());
      //var ss = (rowfrom1.toUpperCase() != rowto2.toUpperCase()); 
      var val_msg0 = document.getElementById('val_msg0').value;
      var val_msg1 = document.getElementById('val_msg1').value;
      var val_msg2 = document.getElementById('val_msg2').value;
      var val_msg3 = document.getElementById('val_msg3').value;
      var val_msg4 = document.getElementById('val_msg4').value;
      var val_msg5 = document.getElementById('val_msg5').value;
      var val_msg6 = document.getElementById('val_msg6').value;
      var val_msg7 = document.getElementById('val_msg7').value;
      var val_msg8 = document.getElementById('val_msg8').value;
      var val_msg9 = document.getElementById('val_msg9').value;
      var val_msg10 = document.getElementById('val_msg10').value;
      var val_msg11 = document.getElementById('val_msg11').value;
      var val_msg12 = document.getElementById('val_msg12').value;
      var val_msg13 = document.getElementById('val_msg13').value;
      var val_msg14 = document.getElementById('val_msg14').value;
      var val_msg15 = document.getElementById('val_msg15').value;

      if (company_name === '' ) {
         document.getElementById('errormsg').innerHTML = val_msg1+' '+val_msg0;
         return false;
      } else if (depot_name === '' ) {
         document.getElementById('errormsg').innerHTML = val_msg2+' '+val_msg0;
         return false;
      } else if (bustype_name === '') {
         document.getElementById('errormsg').innerHTML = val_msg3+' '+val_msg0;
         return false;
      }  else if (reqdfrdtt_name === '' || reqdfrdtt_name === null) {
          document.getElementById('errormsg').innerHTML = val_msg5+' '+val_msg0;
          return false;
      } else if (reqdtodt_name === '' || reqdtodt_name === null) {
          document.getElementById('errormsg').innerHTML = val_msg6+' '+val_msg0;
          return false;
      } else if (reqdfrdtt_name > reqdtodt_name) {
          document.getElementById('errormsg').innerHTML = val_msg5+' '+val_msg14+' '+val_msg6;
          return false;
      } else if (seats_name === '' ) {
         document.getElementById('errormsg').innerHTML = val_msg7+' '+val_msg0;
         return false;
      } else if (seats_name === 0) {
         document.getElementById('errormsg').innerHTML = val_msg7+' '+val_msg13;
         return false;
      } else if (buses_name === '' || buses_name === null) {
         document.getElementById('errormsg').innerHTML = val_msg8+' '+val_msg0;
         return false;
      } else if (parseInt(buses_name) == '0') {
         document.getElementById('errormsg').innerHTML = val_msg8+' '+val_msg13;
         return false;
      } else if (custesticumdist_name === '' || custesticumdist_name === null) {
         document.getElementById('errormsg').innerHTML = val_msg9+' '+val_msg0;
         return false;
      } else if (parseFloat(custesticumdist_name) == '0') {
         document.getElementById('errormsg').innerHTML = val_msg9+' '+val_msg13;
         return false;
      } else if (custesticumprice_name === '' || custesticumprice_name === null) {
         document.getElementById('errormsg').innerHTML = val_msg10+' '+val_msg0;
         return false;
      } else if (parseFloat(custesticumprice_name) == '0') {
         document.getElementById('errormsg').innerHTML = val_msg10+' '+val_msg13;
         return false;
      } else if (bushireprps_name === '' || bushireprps_name === null) {
         document.getElementById('errormsg').innerHTML = val_msg11+' '+val_msg0;
         return false;
      } else if (rowto1 === "") {
         document.getElementById('errormsg').innerHTML = val_msg12+' line 1 To details '+val_msg0;
         return false;
      } else if (s) {
         document.getElementById('errormsg').innerHTML = val_msg15;
         return false;
      } else if (rowto2 === "") {
         document.getElementById('errormsg').innerHTML = val_msg12+' line 2 To details '+val_msg0;
         return false;
      } else {
         document.getElementById("errormsg").innerHTML = "";
         return true;
      }
   }
//  function final1() {return true;}
  function final1() { 
      var rowfrom1 = document.getElementById('add_from_idA').value.trim();
      var rowfrom2 = document.getElementById('add_from_idB').value.trim();
      var rowto2 = document.getElementById('add_to_idB').value.trim();
      var tab1 = document.getElementById('tab_id').value;
      var count = document.getElementById('additional_info_count_id').value.trim();
      var ss = (rowfrom1.toUpperCase() != rowto2.toUpperCase());
      var sss = (rowfrom2.toUpperCase() === rowto2.toUpperCase());
      if (tab1 > 0) {
         tab1 = parseInt(tab1);
         tab1 = tab1 - 1;
         var fr = document.getElementById('add_from_idA').value.trim();
         var toto = document.getElementById('add_to_id' + tab1).value.trim();
         for (var q = 0; q < tab1; q++) {
            var fromq = document.getElementById('add_from_id' + q).value.trim();
            var toq = document.getElementById('add_to_id' + q).value.trim();
            if (fromq.toUpperCase() == toq.toUpperCase()) {
               document.getElementById('errormsg').innerHTML = "Bus route From Depot and Bus route To Depot are same at Line" + (q + 3);
               return false;
            }
         }
         if (fr.toUpperCase() != toto.toUpperCase()) {
            document.getElementById('errormsg').innerHTML = "Bus route starting depot and Bus route ending depot are not same";
            return false;
         }else if (sss) {
         document.getElementById('errormsg').innerHTML = "Bus route From Depot and Bus route To Depot are same at Line 2";
         return false;
      }
      } else if (ss) {
         document.getElementById('errormsg').innerHTML = "Bus route starting depot and Bus route ending depot are not same";
         return false;
      } else if (count > 0) {
          for (var i = 0; i < count; i++) {
              var key = document.getElementById(i).innerHTML;  
              if (key.indexOf("<span>*</span>") > -1) { 
                 var attribute = key.split("<span>*</span>"); 
                 var attribute_name = attribute[0].replace(" ", ""); 
                 var attribute_value = document.getElementById(attribute_name + i + '_id').value.trim(); 
                 var unique_value = document.getElementById('addl_info_unique_id' + i).value.trim(); 
                 if (attribute_value == "") {
                    document.getElementById('errormsg').innerHTML = attribute[0] + "Cannot be blank";
                    return false;
                 } else if (unique_value === "false") {
                    document.getElementById("errormsg").innerHTML = attribute[0] + "Already Exists";
                    return false;
                 }
              }
           } 
        } 
      	return true;
   }  
  //function contact() {return true;}
  function contact() {
      if (check === 0) {
         document.getElementById('log_id').value = check;
         var fname = document.getElementById('add_compres_fname_id').value.trim();
         var lname = document.getElementById('add_compres_lname_id').value.trim();
         var email_validator = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+[\.]{1}[a-zA-Z]{2,4}$/;
         var cmpny_bt_add1 = document.getElementById('add_cmpny_bt_add1_id').value.trim();
         var cmpny_bt_state = document.getElementById('add_cmpny_bt_state_id').value;
         var cmpny_bt_city = document.getElementById('add_cmpny_bt_city_id').value.trim();
         var cmpny_bt_zip = document.getElementById('add_cmpny_bt_zip_id').value.trim();
         var cmpny_bt_ph = document.getElementById('add_cmpny_bt_ph_id').value.trim();
         cmpny_bt_ph = cmpny_bt_ph.replace("-", "").replace("-", "");
         var cmpny_bt_em = document.getElementById('add_cmpny_bt_em_id').value.trim();
         var cmpny_st_add1 = document.getElementById('add_cmpny_st_add1_id').value.trim();
         var cmpny_st_state = document.getElementById('add_cmpny_st_state_id').value;
         var cmpny_st_city = document.getElementById('add_cmpny_st_city_id').value.trim();
         var cmpny_st_zip = document.getElementById('add_cmpny_st_zip_id').value.trim();
         var cmpny_st_ph = document.getElementById('add_cmpny_st_ph_id').value.trim();
         cmpny_st_ph = cmpny_st_ph.replace("-", "").replace("-", "");
         var cmpny_st_em = document.getElementById('add_cmpny_st_em_id').value.trim();
         
         var useraccount = document.getElementById('add_users_account_id').value.trim();
         var pwd = document.getElementById('add_users_password_id').value.trim();
         var confirmpwd = document.getElementById('add_users_confirmpassword_id').value.trim();
         var username_val = document.getElementById('add_usercommon_id').value;
         
         var val_msg0 = document.getElementById('val_msg0').value;
         var val_msg16 = document.getElementById('val_msg16').value;
         var val_msg17 = document.getElementById('val_msg17').value;
         var val_msg18 = document.getElementById('val_msg18').value;
         var val_msg19 = document.getElementById('val_msg19').value;
         var val_msg20 = document.getElementById('val_msg20').value;
         var val_msg21 = document.getElementById('val_msg21').value;
         var val_msg22 = document.getElementById('val_msg22').value;
         var val_msg23 = document.getElementById('val_msg23').value;
         var val_msg24 = document.getElementById('val_msg24').value;
         var val_msg25 = document.getElementById('val_msg25').value;
         var val_msg26 = document.getElementById('val_msg26').value;

         if (fname === '' || fname === null) {
            document.getElementById('errormsg').innerHTML = val_msg16;
            return false;
         } else if (lname === '' || lname === null) {
            document.getElementById('errormsg').innerHTML = val_msg17;
            return false;
         } else if (cmpny_bt_add1 === '' || cmpny_bt_add1 === null) {
            document.getElementById('errormsg').innerHTML = val_msg18;
            return false;
         } else if (cmpny_bt_state === '' ) {
            document.getElementById('errormsg').innerHTML = 'State '+val_msg0;
            return false;
         } else if (cmpny_bt_city === '' || cmpny_bt_city === null) {
            document.getElementById('errormsg').innerHTML = val_msg19;
            return false;
         } else if (cmpny_bt_zip === '' || cmpny_bt_zip === null) {
            document.getElementById('errormsg').innerHTML = val_msg20;
            return false;
         } else if (cmpny_bt_ph === '' || cmpny_bt_ph === null) {
            document.getElementById('errormsg').innerHTML = val_msg21+' '+val_msg0;
            return false;
         } else if (cmpny_bt_ph.length < 10) {
            document.getElementById('errormsg').innerHTML = 'Mobile number must be 10 digits';
            return false;
         } else if (cmpny_bt_em === '' || cmpny_bt_em === null) {
            document.getElementById('errormsg').innerHTML = val_msg22;
            return false;
         } else if (email_validator.test(cmpny_bt_em) !== true) {
            document.getElementById('errormsg').innerHTML = 'Invalid Email';
            return false;
         } else if (useraccount === '' || useraccount === null) {
            document.getElementById("errormsg").innerHTML = val_msg23;
            return false;
         } else if (username_val != 0) {
            document.getElementById('errormsg').innerHTML = 'User ID already exist';
            return false;
         } else if (pwd === '' || pwd === null) {
            document.getElementById("errormsg").innerHTML = val_msg24;
            return false;
         } else if (pwd.length < 8) {
            document.getElementById("errormsg").innerHTML = val_msg25;
            return false;
         } else if (confirmpwd === '' || confirmpwd === null) {
            document.getElementById("errormsg").innerHTML = 'Confirm '+val_msg24;
            return false;
         } else if (pwd != confirmpwd) {
            document.getElementById("errormsg").innerHTML = val_msg26;
            return false;
         } else if (cmpny_st_add1 === '' || cmpny_st_add1 === null) {
            document.getElementById('errormsg').innerHTML = 'Ship to '+val_msg18;
            return false;
         } else if (cmpny_st_state === '' ) {
            document.getElementById('errormsg').innerHTML = 'Ship to State '+val_msg0;
            return false;
         } else if (cmpny_st_city === '' || cmpny_st_city === null) {
            document.getElementById('errormsg').innerHTML = 'Ship to '+val_msg19;
            return false;
         } else if (cmpny_st_zip === '' || cmpny_st_zip === null) {
            document.getElementById('errormsg').innerHTML = 'Ship to '+val_msg20;
            return false;
         } else if (cmpny_st_ph === '' || cmpny_st_ph === null) {
            document.getElementById('errormsg').innerHTML = 'Ship to '+val_msg21+' '+val_msg0;
            return false;
         } else if (cmpny_st_ph.length < 10) {
            document.getElementById('errormsg').innerHTML = 'Ship to Mobile number must be 10 digits';
            return false;
         } else if (cmpny_st_em === '' || cmpny_st_em === null) {
            document.getElementById('errormsg').innerHTML = 'Ship to '+val_msg22;
            return false;
         } else if (email_validator.test(cmpny_st_em) !== true) {
            document.getElementById('errormsg').innerHTML = 'Invalid Ship to Email';
            return false;
         }
      } else if (check === 1) {
         document.getElementById('log_id').value = check;
         var useraccount1 = document.getElementById('add_users_account_id1').value;
         useraccount1 = $.trim(useraccount1);
         var pwd1 = document.getElementById('add_users_password_id1').value;
         pwd1 = $.trim(pwd1);
         var pwd2 = document.getElementById('password_id').value.trim();
         
         var val_msg23 = document.getElementById('val_msg23').value;
         var val_msg24 = document.getElementById('val_msg24').value;
         var val_msg25 = document.getElementById('val_msg25').value;
         
         if (useraccount1 === '' || useraccount1 === null) {
            document.getElementById("errormsg").innerHTML = val_msg23;
            return false;
         } else if (pwd1 === '' || pwd1 === null) {
            document.getElementById("errormsg").innerHTML = val_msg24;
            return false;
         } else if (pwd1.length < 8) {
            document.getElementById("errormsg").innerHTML = val_msg25;
            return false;
         } else if (pwd2 == 0) {
            document.getElementById("errormsg").innerHTML = 'Invalid User Account/Password';
            return false;
         }
      }
      document.getElementById('errormsg').innerHTML = "";
      return true;
   }
  
  
// aditional information functions
function additional_information() { 
  var company = document.getElementById('add_companyname_id').value.split("(~)");
  if (company[0] != '') {
	//inputDependency('getADDL','addl_id','add_companyname_id');
	  getAdditionalInfo();
	//AddlDisplay();
  } else {
	  document.getElementById('addl_id').value=null;
	 // AddlDisplay();
  }
}
function getAdditionalInfo() {
	var company = document.getElementById('add_companyname_id').value; 
	var divId = 'additional_infromation_div';
	if(company !== '') {
		document.getElementById('additional_infromation_div').style.display='block';
		$.ajax({
			url : 'getADDL',
			method : 'get',
			ContentType : 'json',
			data : {
				postedValue1 : company
			},
			success: function(data) {
				$('#'+divId).html(data);
			},
			error:function (xhr, ajaxOptions, thrownError) {
				$('#'+divId).html("");
			}
		});
	} else {alert('ekse');
		document.getElementById('additional_infromation_div').style.display='none';
	}
}
function AddlDisplay(){
	var add_info = document.getElementById('additional_infromation_div');
	var addl = document.getElementById('addl_id').value;
	if(addl > 0){
		//add_info.style.display = "block";
	} else {
		//add_info.style.display = "none";
	}
}
function addl_uniqueness(value_id, attri_id, attribute_type, lov_id, divId, tagId, $tagName) { 
	var value = document.getElementById(value_id).value;
	var attribute_id = document.getElementById(attri_id).value;
	if (value !== "") {
     var field_value = "";
     if (attribute_type === lov_id) {
        value = value.split("(~)");
        field_value = value[0];
     } else {
        field_value = value;
     }
     //var url = "<?= $this->config->base_url(); ?>ctrl_bushires/addl_uniqueness/" + tagId + "/" + $tagName + "/" + attribute_id + "/" + field_value;
     //ajax(url, divId);
     AddlUniq('getADDLuniq', tagId, value_id, attri_id, attribute_type, lov_id);
  } else {
     document.getElementById(tagId).value = "true";
  }
}
function AddlUniq(urlValue, dependerId, tagId1, tagId2, attribute_type, lov_id) { 
	var value = $('#'+tagId1).val(); 
	var val2 = $('#'+tagId2).val(); 
	var val1;
	if (attribute_type === lov_id) {
        value = value.split("(~)");
        val1 = value[0];
     } else {
    	 val1 = value;
     }
	$.ajax({
		url : urlValue,
		method : 'get',
		ContentType : 'json',
		data : {
			postedValue1 : val1,
			postedValue2 : val2 
        },
       	success : function(response) { 
       		if(response != null || response != 0){
       			$('#'+dependerId).val("true");
       		}else{
       			$('#'+dependerId).val("false");	
       		}
       	},
       	error:function (xhr, ajaxOptions, thrownError) {
        	//alert(xhr.status);
        	//alert(thrownError);
   		}
    });
}
function state_Name(country_name) {
	   if (country_name.value != '') {
	      document.getElementById('state_star').innerHTML = 'State<span>*</span>:';
	   }
	   if(country_name.value != '') {
		   document.getElementById('state_star1').innerHTML = 'State<span>*</span>:';
	   }
	}
