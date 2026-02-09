var base_url = document.getElementById('base_url').value;
function businessypeattr_page(){
      if (base_url === '' || base_url === null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url+'ctrl_custom_attributes';
   }
   function deletebusattr(){
      var val_msg1 = document.getElementById('val_msg1').value;
      val_msg1 = $.trim(val_msg1);
      var id=document.getElementById('delete_busitypeattr_result_id').value;
      if(id ==='1'){
         document.getElementById('errormsg').innerHTML=val_msg1;
         return false;
      }
   }
   function delete_page(){
      var val_msg2 = document.getElementById('val_msg2').value;
      val_msg2 = $.trim(val_msg2);
      var id=document.getElementById('delete_busitypeattr_result_id').value;
      if(id ==='1'){
         document.getElementById('errormsg').innerHTML=val_msg2;
         return false;
      }else {
          document.getElementById('form').submit();
       }
   }