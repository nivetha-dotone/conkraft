function submitFrom(id) {
   	var error = document.getElementById('errormsg').innerHTML;
   	if(error == "") document.getElementById(id).submit();
   }