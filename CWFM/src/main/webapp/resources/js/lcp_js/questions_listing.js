    var selectedrecordid = null;
    function selectedrow(id) {
        selectedrecordid = id;
    }
    function uncheckRadioButton() {
        selectedrecordid = '';
        var rbs = document.getElementsByName('radio');
        for (var i = 0; i < rbs.length; i++) {
            if (rbs[i].checked) {
                rbs[i].checked = false;
            }
        }
    }
    function add() {
       var base_url = document.getElementById('base_url').value.trim();
        if (selectedrecordid !== '' || selectedrecordid !== null) {
            uncheckRadioButton();
        }
        window.location.href = base_url+'ctrl_questions/add';
    }
    function edit() {
       var base_url = document.getElementById('base_url').value.trim();
       var val_msg2 = document.getElementById('val_msg2').value.trim();
      if(selectedrecordid === '' || selectedrecordid === null){
         alert(val_msg2);
      } else {
         var id=selectedrecordid;uncheckRadioButton();
         window.location.href=base_url+'ctrl_questions/edit/'+id;
      }
    }
    function delete_page() {
       var base_url = document.getElementById('base_url').value.trim();
    var val_msg2 = document.getElementById('val_msg2').value.trim();
      if(selectedrecordid === '' || selectedrecordid === null){
         alert(val_msg2);
      } else {
         var id=selectedrecordid;uncheckRadioButton();
         window.location.href=base_url+'ctrl_questions/delete/'+id;
      }
    }
     
    function view() {
       var base_url = document.getElementById('base_url').value.trim();
    var val_msg2 = document.getElementById('val_msg2').value.trim();
      if(selectedrecordid === '' || selectedrecordid === null){
         alert(val_msg2);
      } else {
         var id=selectedrecordid;uncheckRadioButton();
         window.location.href=base_url+'ctrl_questions/view/'+id;
      }
    }
     function export_records() {
        var base_url = document.getElementById('base_url').value.trim();
      var search= document.getElementById('searchterm_id').value.split("#").join("ha_sh_sm-bl").split("^").join("%5E").split('"').join("%22").split("'").join("%27").split("?").join("qu_stn_mrk_sm-bl").split("/").join("fr_wrd_sl_sh_sm-bl").split("<").join("%3C").split(">").join("%3E").split("[").join("%5B").split("]").join("%5D").split("{").join("%7B").split("}").join("%7D").split("`").join("%60").split(".").join("dt_sm-bl").split("(").join("opn_br_ckt_sm-bl").split(")").join("cl_sd_br_ckt_sm-bl").split("$").join("dlr_sm-bl");
      search=search.trim(); 
      if(search==='' || search=== null) search= 'empty';
         window.location.href=base_url+'ctrl_questions/toExport/'+search;
   }
    

