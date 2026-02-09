 /* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */
 
 var base_url = document.getElementById('base_url').value;
 var p_s_record = document.getElementById('p_s_record').value;
 var alert_1 = document.getElementById('alert_1').value;
 var selectedrecordid='';
   function selectedrow(id){
      selectedrecordid=id;
   }
   function uncheckRadioButton(){
      selectedrecordid='';
      var rbs = document.getElementsByName('radio');
      for (var i=0; i< rbs.length; i++) {
         if (rbs[i].checked) {
            rbs[i].checked=false;
         }
      }
   }
   
   function addrecord(){
    if (selectedrecordid != '' || selectedrecordid != null) {
        uncheckRadioButton();
    }
    if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
      window.location.href = base_url +'CustomAttributesController/listcustomattributes'; 
   }
   
   function export_records(){
      var search_term = document.getElementById('searchterm_id').value.split('#').join('ha_sh_sm-bl').split('^').join('%5E').split('"').join('%22').split("'").join('%27').split('?').join('qu_stn_mrk_sm-bl').split('/').join('fr_wrd_sl_sh_sm-bl').split('<').join('%3C').split('>').join('%3E').split('[').join('%5B').split(']').join('%5D').split('{').join('%7B').split('}').join('%7D').split('`').join('%60').split('.').join('dt_sm-bl').split('(').join('opn_br_ckt_sm-bl').split(')').join('cl_sd_br_ckt_sm-bl').split('$').join('dlr_sm-bl');
    search_term = $.trim(search_term);
    if (search_term == '' || search_term == null)
        search_term = 'empty';
        if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
       window.location.href = base_url +'ctrl_custom_attributes/toExport/'+search_term;
      
   }
   function edit_page(){
      if (selectedrecordid == '' || selectedrecordid == null) {
       var alert_1 = document.getElementById('alert_1').value;
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        var controller = document.getElementById('controller').value;
        if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
        window.location.href = base_url +'ctrl_custom_attributes/edit/'+id;
    } 
    
   }
   function delete_page(){
      if (selectedrecordid == '' || selectedrecordid == null) {
       var alert_1 = document.getElementById('alert_1').value;
       alert(alert_1);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        var controller = document.getElementById('controller').value;
        if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
        window.location.href = base_url +'ctrl_custom_attributes/delete_page/'+id+'/delete';
    }
   }
   function view_page(){
      if (selectedrecordid == '' || selectedrecordid == null) {
       var alert_1 = document.getElementById('alert_1').value;
       alert(alert_1);
    } else {
        var id = selectedrecordid;
        uncheckRadioButton();
        var controller = document.getElementById('controller').value;
        if (base_url == '' || base_url == null)
        base_url = document.getElementById('base_url').value;
        window.location.href = base_url +'ctrl_custom_attributes/view_page/'+id+'/view';
    } 
    
   }
   function companies(){
      if(selectedrecordid==''){
         var alert_1 = document.getElementById('alert_1').value;
       alert(alert_1);
      }else{
         var id=selectedrecordid;uncheckRadioButton();
         window.location.href='ctrl_companies/index/'+id+'/'+'custtypeattribute'; 
      }
   }
