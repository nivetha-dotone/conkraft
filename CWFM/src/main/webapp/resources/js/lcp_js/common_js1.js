/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
   $(function () {
        $('.audit-trail-bg').footable({
            toggleHTMLElement: '<span><img src='+base_url+'"img/plus.png" class="footable-toggle footable-expand" border="0" alt="Expand">'
                    + '<img src='+base_url+'"img/minus.png" class="footable-toggle footable-contract" border="0" alt="Contract"></span>'
        });
    });
