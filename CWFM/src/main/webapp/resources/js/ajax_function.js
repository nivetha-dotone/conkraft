function getXMLHTTP(){
   var xmlhttp = false;
   try{
      xmlhttp = new XMLHttpRequest();
   }catch (e){
      try{
         xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
      }catch (e){
         try{
            xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
         }catch (e1){
           xmlhttp = false;
         }
      }
   }
  return xmlhttp;
}
function ajax(url,id){
    
   var strURL=url;
   var divid=id;
   var req = getXMLHTTP();
   if (req){
     req.onreadystatechange = function(){
       if (req.readyState == 4){
          if (req.status == 200){
               
             document.getElementById(divid).innerHTML = req.responseText;
          } else {
       }
     }
   }
   req.open("GET", strURL, true);
   req.send(null);
 }
}
