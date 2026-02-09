    var base_url = document.getElementById('base_url').value;
    function back_to_listing() {
        if (base_url === '' || base_url === null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url+'ctrl_custom_attributes';
    }
    function validation() {
        var atrbt_name = document.getElementById('edit_bnsstype_attributes_atrbtnameid').value.trim();
        var validation1 = document.getElementById('edit_bstatr_uniqueid').value;
        var atrbt_desc = document.getElementById('edit_bnsstype_attributes_atrbtdesc_id').value.trim();
        var order = document.getElementById('edit_custom_attributes_orderid').value.trim();
        var maxlength = document.getElementById('edit_custom_attributes_maxlength_id').value.trim();
        //var level = document.getElementById('edit_custom_attributes_levelid').value.trim();
        var level = document.getElementById('edit_custom_attributes_levelid').value;
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
        if (atrbt_name === '' || atrbt_name === null) {
            document.getElementById('errormsg').innerHTML = val_msg1;
            return false;
        } else if (validation1 === 'false') {
            document.getElementById('errormsg').innerHTML = val_msg2;
            return false;
        } else if (atrbt_desc === '' || atrbt_desc === null) {
            document.getElementById('errormsg').innerHTML = val_msg3;
            return false;
        } else if (order === '' || order === null) {
            document.getElementById('errormsg').innerHTML = val_msg4;
            return false;
        } else if(order !== '' && parseFloat(order) === 0){
         document.getElementById('errormsg').innerHTML= val_msg5;
         return false;
        } else if (maxlength === '' || maxlength === null) {
            document.getElementById('errormsg').innerHTML = val_msg6;
            return false;
        } else if(parseFloat(maxlength) === 0){
         document.getElementById('errormsg').innerHTML= val_msg7;
         return false;
        } else if (maxlength > 500) {
            document.getElementById('errormsg').innerHTML = val_msg8;
            return false;
        } else if (level === '' || level === null) {
            document.getElementById('errormsg').innerHTML = val_msg9;
            return false; 
        } else {
            document.getElementById('errormsg').innerHTML = '';
            return true;
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
    function editUniqueness(div_Id, tag_Id, tag_Name) {
        var cmpId=document.getElementById('edit_select_businesstype_cmp_id').value.trim();
        var selectid = document.getElementById('edit_hidden_us_attr_type_id').value.trim();
        var level =document.getElementById('edit_select_businesstype_level_id').value;
        base_url = document.getElementById('base_url').value;
        var attrname = document.getElementById('edit_bnsstype_attributes_atrbtnameid').value.trim().split('#').join('ha_sh_sm-bl').split('^').join('%5E').split('?').join('qu_stn_mrk_sm-bl').split('/').join('fr_wrd_sl_sh_sm-bl').split('<').join('%3C').split('>').join('%3E').split('[').join('%5B').split(']').join('%5D').split('{').join('%7B').split('}').join('%7D').split('`').join('%60').split('.').join('dt_sm-bl').split('(').join('opn_br_ckt_sm-bl').split(')').join('cl_sd_br_ckt_sm-bl').split('$').join('dlr_sm-bl');
        if (attrname !== '') {
             var url =  base_url+'ctrl_custom_attributes/editUniqueness/' + tag_Id + '/' + tag_Name + '/' + selectid + '/' + cmpId +'/' + attrname+'/'+level;
             ajax(url, div_Id);
        } else {
            var url = base_url+'ctrl_custom_attributes/editUniqueness/' + tag_Id + '/' + tag_Name + '/' + selectid + '/' + null+'/'+level;
            ajax(url, div_Id);
        }
    }
    function uniqueValidation() {
        var val_msg10 = document.getElementById('val_msg10').value;
        val_msg10 = $.trim(val_msg10);
        var validation = document.getElementById('edit_bstatr_uniqueid').value;
        if (validation === 'false') {
            document.getElementById('errormsg').innerHTML = val_msg10;
            return false;
        } else {
            document.getElementById('errormsg').innerHTML = '';
            return true;
        }
    }
    function getlookup_types(divId) {
        var cmpny = document.getElementById('edit_bnsstype_attributes_cmpnyid').value;
        if (cmpny !== '') {
            cmpny = cmpny.split('<$>');
            var url = '<?php echo  $this->config->base_url(); ?>ctrl_usercustom_attributes/get_looktypes/' + cmpny[0];
            ajax(url, divId);
        } else {
            var url = '<?php echo  $this->config->base_url(); ?>ctrl_usercustom_attributes/get_looktypes/' + 'null';
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
            edit_bnsstype_attributes_lookuptpid.value = '';
            document.getElementById('lookup_type').style.display = 'none';
            document.getElementById('lookup_type_disable').style.display = 'block';
        }
    }
    function edit_page() {
        validation();
        //uniqueValidation();
        if (validation()) {
            document.getElementById('editform').submit();
        }
    }