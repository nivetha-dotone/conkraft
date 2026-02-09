/* Application Name = Hire A Bus
 Version = 1.0
 Release Date = March 01, 2015
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

   var selectedrecordid='';
   function BHselectedrow(id){
         id=id.split('(~)');
         document.getElementById('selectedRecordIdId').value = id[0];
         if(id[1]=='Bus Hire Started'){
             document.getElementById('cancel').style.display = "none";
             document.getElementById('delete').style.display = "none";
             document.getElementById('edit').style.display = "block";
             document.getElementById('pay').style.display = "none";
          }else if(id[1]=='Bus Hire Ended'){
             document.getElementById('cancel').style.display = "none";
             document.getElementById('delete').style.display = "none";
             document.getElementById('edit').style.display = "none";
             document.getElementById('pay').style.display = "block";
          }else if(id[1]=='Bus Hire Canceled'){
             document.getElementById('cancel').style.display = "none";
             document.getElementById('edit').style.display = "none";
             document.getElementById('pay').style.display = "none";
             document.getElementById('delete').style.display = "none";
          }else if(id[1]=='Bus Hire Created'){
             document.getElementById('pay').style.display = "none";
             document.getElementById('cancel').style.display = "block";
             document.getElementById('delete').style.display = "none";
             document.getElementById('edit').style.display = "block";
          }else if(id[1]=='Bus Hire Verified'){
             document.getElementById('pay').style.display = "block";
             document.getElementById('cancel').style.display = "block";
             document.getElementById('delete').style.display = "none";
             document.getElementById('edit').style.display = "none";
          }else if(id[1]=='Bus Hire Paid'){
             document.getElementById('pay').style.display = "none";
             document.getElementById('cancel').style.display = "block";
             document.getElementById('delete').style.display = "none";
             document.getElementById('edit').style.display = "block";
          }else if(id[1]=='Bus Hire Confirmed'){
             document.getElementById('pay').style.display = "none";
             document.getElementById('cancel').style.display = "block";
             document.getElementById('delete').style.display = "none";
             document.getElementById('edit').style.display = "block";
          }else if(id[1]=='Bus Hire Closed'){
             document.getElementById('pay').style.display = "none";
             document.getElementById('cancel').style.display = "none";
             document.getElementById('delete').style.display = "none";
             document.getElementById('edit').style.display = "none";
          }else{
             document.getElementById('cancel').style.display = "block";
             document.getElementById('delete').style.display = "none";
             document.getElementById('edit').style.display = "block";
             document.getElementById('pay').style.display = "block";
          }
   }
   function submitBHForm(id) { 
	var error = document.getElementById('errormsg').innerHTML; 
	if(error == "") document.getElementById(id).submit();
}

