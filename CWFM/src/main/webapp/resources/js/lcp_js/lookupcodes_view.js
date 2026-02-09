/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
    function cancle() {
        if (base_url == '' || base_url == null)
            base_url = document.getElementById('base_url').value;
        window.location.href = base_url+'ctrl_lookupcodes';
    }
