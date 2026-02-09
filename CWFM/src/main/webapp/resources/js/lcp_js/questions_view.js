 function back_to_counters(){
    var base_url=document.getElementById('base_url').value;
      window.location.href=base_url+'ctrl_answers'; 
   }
  function view_page() {
      validations();
      if(validations()) {
         document.getElementById('view_answers_form').submit();
      }
   }