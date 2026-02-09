//function submitFrom(id) {
//    	document.getElementById(id).submit();
//    }
function validateAction(actionPath){
	var form = document.getElementById('custom_listing_id');
	form.action = document.getElementById('base_url').value+actionPath;
	form.submit();	  
}
function clearmessage() {
document.getElementById("errormsg").innerHTML = "";
return true;
} 