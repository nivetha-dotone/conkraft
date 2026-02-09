   function back_to_counters(){
       var base_url = document.getElementById('base_url').value
      window.location.href=base_url+'ctrl_questions'; 
   }
  function validation() {
      var val_msg2 = document.getElementById('val_msg2').value
        var id = document.getElementById('delete_questions_result_id').value;
        if (id === '1') {
            document.getElementById('errormsg').innerHTML = val_msg2;
            return false;
        } else {
           document.getElementById('errormsg').innerHTML = '';
           return true;
            //window.location.href = '<?= $this->config->base_url(); ?>ctrl_answers/delete/<?= $id; ?>';
        }
    }
    function delete_page() {
        var id = document.getElementById('delete_questions_result_id').value;
        if (id !== '1')
        document.getElementById('delete_questions_form').submit();
    }
