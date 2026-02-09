var base_url = document.getElementById('base_url').value;
function back_to_listing() {
   if (base_url === '' || base_url === null)
   base_url = document.getElementById('base_url').value;
   window.location.href = base_url + 'ctrl_custom_attributes';
}
function validation() {
   //var atrbt_type = document.getElementById('add_bnsstype_attributes_atrbttypeid').value.trim();
   var cmpny = document.getElementById('add_bnsstype_attributes_cmpnyid').value;
   var lookup_type = document.getElementById('add_bnsstype_attributes_lookuptpid').value.trim();
   var lookuptype_val = document.getElementById('lookup_type_disable').style.display;
   var atrbt_name = document.getElementById('add_bnsstype_attributes_atrbtnameid').value.trim();
   var validation = document.getElementById('add_bstatr_uniqueid').value;
   var atrbt_desc = document.getElementById('add_bnsstype_attributes_atrbtdesc_id').value.trim();
   var data = document.getElementById('add_custom_attributes_dtid').value.trim();
   var order = document.getElementById('add_custom_attributes_orderid').value.trim();
   var maxlength = document.getElementById('add_custom_attributes_maxlength_id').value.trim();
   // var level = document.getElementById('add_custom_attributes_levelid').value.trim();
   var level = document.getElementById('add_custom_attributes_levelid').value;
   var status = document.getElementById('status_id').value;
   level = $.trim(level);
   var val_msg1 = document.getElementById('val_msg1').value;
   val_msg1 = $.trim(val_msg1);
   var val_msg2 = document.getElementById('val_msg2').value;
   val_msg2 = $.trim(val_msg2);
   var val_msg3 = document.getElementById('val_msg3').value;
   val_msg3 = $.trim(val_msg3);
   var val_msg4 = document.getElementById('val_msg4').value;
   val_msg4 = $.trim(val_msg4);
   var val_msg5 = document.getElementById('val_msg5').value;
   val_msg5 = $.trim(val_msg5);
   var val_msg6 = document.getElementById('val_msg6').value;
   val_msg6 = $.trim(val_msg6);
   var val_msg7 = document.getElementById('val_msg7').value;
   val_msg7 = $.trim(val_msg7);
   var val_msg8 = document.getElementById('val_msg8').value;
   val_msg8 = $.trim(val_msg8);
   var val_msg9 = document.getElementById('val_msg9').value;
   val_msg9 = $.trim(val_msg9);
   var val_msg10 = document.getElementById('val_msg10').value;
   val_msg10 = $.trim(val_msg10);
   var val_msg11 = document.getElementById('val_msg11').value;
   val_msg11 = $.trim(val_msg11);
   var val_msg12 = document.getElementById('val_msg12').value;
   val_msg12 = $.trim(val_msg12);
   var val_msg13 = document.getElementById('val_msg13').value;
   val_msg13 = $.trim(val_msg13);
   if (cmpny === '' || cmpny === null) {
      document.getElementById('errormsg').innerHTML = val_msg5;
      return false;
   }
   else if (lookuptype_val === 'none' && (lookup_type === '' || lookup_type === null)) {
      document.getElementById('errormsg').innerHTML = val_msg1;
      return false;
   } else if (data === '' || data === null) {
      document.getElementById('errormsg').innerHTML = val_msg2;
      return false;
   } else if (validation === 'false') {
      document.getElementById('errormsg').innerHTML = val_msg3;
      return false;
   } else if (level === '' || level === null) {
      document.getElementById('errormsg').innerHTML = val_msg4;
      return false;
   } else if (atrbt_name === '' || atrbt_name === null) {
      document.getElementById('errormsg').innerHTML = val_msg7;
      return false;
   } else if (atrbt_desc === '' || atrbt_desc === null) {
      document.getElementById('errormsg').innerHTML = val_msg8;
      return false;
   } else if (order === '' || order === null) {
      document.getElementById('errormsg').innerHTML = val_msg9;
      return false;
   } else if (order !== '' && parseFloat(order) === 0) {
      document.getElementById('errormsg').innerHTML = val_msg10;
      return false;
   } else if (maxlength === '' || maxlength === null) {
      document.getElementById('errormsg').innerHTML = val_msg11;
      return false;
   } else if (parseFloat(maxlength) === 0) {
      document.getElementById('errormsg').innerHTML = val_msg12;
      return false;
   } else if (maxlength > 500) {
      document.getElementById('errormsg').innerHTML = val_msg13;
      return false;
   }
   else {
      if (status === 'NO') {
         document.getElementById('status_id').value = 'YES';
         document.getElementById('errormsg').innerHTML = '';
         return true;
      } else {
         document.getElementById('errormsg').innerHTML = val_msg6;
         return false;
      }
   }
}
function clearmessage() {
   document.getElementById('errormsg').innerHTML = '';
}
function isValiedName(evt) {
   var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode === 34 || charCode === 37 || charCode === 39 || charCode === 92)
      return false;
   else
      return true;
}
function NumberKey(evt) {
   var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;
   else
      return true;
}
function getlookup_types(divId) {
   var cmpny = document.getElementById('add_bnsstype_attributes_cmpnyid').value;
   if (cmpny !== '') {
      cmpny = cmpny.split('<$>');
      base_url = document.getElementById('base_url').value;
      var url = base_url + 'ctrl_usercustom_attributes' / 'get_looktypes/' + cmpny[0];
      //var url = '<?= $this->config->base_url(); ?>ctrl_usercustom_attributes/get_looktypes/' + cmpny[0];
      ajax(url, divId);
   } else {
      base_url = document.getElementById('base_url').value;
      var url = base_url + 'ctrl_usercustom_attributes' / 'get_looktypes/' + 'null';
//      var url = '<?= $this->config->base_url(); ?>ctrl_usercustom_attributes/get_looktypes/' + 'null';
      ajax(url, divId);
   }
}
function lookup_name(atrbt_type) {
   var atrbtypeid = document.getElementById('default_attribute_type').value;
   var atrbttype_ind = atrbt_type.split('<$>');
   if (atrbttype_ind[0] === atrbtypeid) {
      document.getElementById('lookup_type').style.display = 'block';
      document.getElementById('lookup_type_disable').style.display = 'none';
   } else {
      add_bnsstype_attributes_lookuptpid.value = '';
      document.getElementById('lookup_type').style.display = 'none';
      document.getElementById('lookup_type_disable').style.display = 'block';
   }
}
