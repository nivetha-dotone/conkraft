/* Application Name = Hire A Bus
 Version = 1.0
 Release Date = March 01, 2015
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

 function clearmessage() {
     document.getElementById("errormsg").innerHTML = "";
  }
 function editformValidate() {
     var bushirestatus = document.getElementById('add_bushirestatus_id').value.trim(); 
     var status = document.getElementById('status_id').value; 
     var val_msg0 = document.getElementById('val_msg0').value;
     var val_msg1 = document.getElementById('val_msg1').value;
     var val_msg2 = document.getElementById('val_msg2').value;
     var val_msg3 = document.getElementById('val_msg3').value;
     var val_msg4 = document.getElementById('val_msg4').value;
     var val_msg5 = document.getElementById('val_msg5').value;
     var val_msg6 = document.getElementById('val_msg6').value;
   if(status =='Bus Hire Created'){
     var amt_dist = document.getElementById('amt_dist_id').value.trim();
     var amt_price = document.getElementById('amt_price_id').value.trim();
      if (amt_dist === '' || amt_dist === null) { 
        document.getElementById('errormsg').innerHTML = val_msg2+' '+val_msg0;
        return false;
     } else if (parseFloat(amt_dist) == '0') {
        document.getElementById('errormsg').innerHTML = val_msg2+' '+val_msg1;
        return false;
     } else if (amt_price === '' || amt_price === null) {
        document.getElementById('errormsg').innerHTML = val_msg3+' '+val_msg0;
        return false;
     } else if (parseFloat(amt_price) == '0') {
        document.getElementById('errormsg').innerHTML = val_msg3+' '+val_msg1;
        return false;
     }else if (bushirestatus === '') {
        document.getElementById('errormsg').innerHTML = val_msg6+' '+val_msg0;
        return false;
     }else {
           document.getElementById('errormsg').innerHTML="";
           return true;
        }
  }else if(status =='Bus Hire Started'){
     var actl_dist = document.getElementById('actl_dist_id').value.trim();
     var actl_price = document.getElementById('actl_price_id').value.trim();
      if (actl_dist === '' || actl_dist === null) { 
        document.getElementById('errormsg').innerHTML = val_msg4+' '+val_msg0;
        return false;
     } else if (parseFloat(actl_dist) == '0') {
        document.getElementById('errormsg').innerHTML = val_msg4+' '+val_msg1;
        return false;
     } else if (actl_price === '' || actl_price === null) {
        document.getElementById('errormsg').innerHTML = val_msg5+' '+val_msg0;
        return false;
     } else if (parseFloat(actl_price) == '0') {
        document.getElementById('errormsg').innerHTML = val_msg5+' '+val_msg1;
        return false;
     }else if (bushirestatus === '' ) {
        document.getElementById('errormsg').innerHTML = val_msg6+' '+val_msg0;
        return false;
     }else {
           document.getElementById('errormsg').innerHTML="";
           return true;
        }
   }else if (bushirestatus === '' ) {
        document.getElementById('errormsg').innerHTML = val_msg6+' '+val_msg0;
        return false;
     }else {
           document.getElementById('errormsg').innerHTML="";
           return true;
        }
  }
 function price(divId, tagId, tagName) { 
     if (divId == 'price2') {
        //var dist = document.getElementById('actl_dist_id').value;
        inputDependency('getBHprice','actl_price_id','add_companyname_id','add_depot_id','add_bustype_id','add_acneeded_id','actl_dist_id','add_busesneeded_id');
     } else {
        //var dist = document.getElementById('amt_dist_id').value;
        inputDependency('getBHprice','amt_price_id','add_companyname_id','add_depot_id','add_bustype_id','add_acneeded_id','amt_dist_id','add_busesneeded_id');
     }
     //var url = '<?= $this->config->base_url(); ?>ctrl_bushires/price/' + tagId + '/' + tagName + '/' + <?php echo $selected_record[0]['COMPANY_ID'] ?> + '/' + <?php echo $selected_record[0]['DEPOT_ID'] ?> + '/' + <?php echo $selected_record[0]['BUS_TYPE'] ?> + '/' + dist+ '/' + <?php echo $selected_record[0]['NUMBER_OF_BUSES_NEEDED'] ?>+ '/' + <?php echo $selected_record[0]['AC_NEEDED'] ?>; 
     //ajax(url, divId);
     
  }
 function isNumberKey(evt) {
     var charCode = (evt.which) ? evt.which : event.keyCode;
     if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
     else
        return true;
  }
 function edit_page() {
     editformValidate();
     if (editformValidate()) { 
        document.getElementById('bushireEditform').submit(); 
        return true;
     }else
        return false;
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
