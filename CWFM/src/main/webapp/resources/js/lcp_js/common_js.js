/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

var base_url = document.getElementById('base_url').value;
$("head").append('<script src="' + base_url + 'js/footable.js"></script>');
$("head").append('<script type="text/javascript" src="' + base_url + 'js/highlight.js"></script>');
$("head").append('<script type="text/javascript" src="' + base_url + 'js/jquery.accordion.js"></script>');
   $(function () {
        $('.audit-trail-bg').footable({
            toggleHTMLElement: '<span><img src='+base_url+'"img/plus.png" class="footable-toggle footable-expand" border="0" alt="Expand">'
                    + '<img src='+base_url+'"img/minus.png" class="footable-toggle footable-contract" border="0" alt="Contract"></span>'
        });
    });
    $(document).ready(function () {
        hljs.tabReplace = '    ';
        hljs.initHighlightingOnLoad();
        $.fn.slideFadeToggle = function (speed, easing, callback) {
            return this.animate({opacity: 'toggle', height: 'toggle'}, speed, easing, callback);
        };
        $('.accordion').accordion({
            defaultOpen: 'section1',
            cookieName: 'accordion_nav',
            speed: 'slow',
            animateOpen: function (elem, opts) {
                elem.next().stop(true, true).slideFadeToggle(opts.speed);
            },
            animateClose: function (elem, opts) {
                elem.next().stop(true, true).slideFadeToggle(opts.speed);
            }
        });
    });
    function submitFrom(id) {
    	var error = document.getElementById('errormsg').innerHTML;
    	if(error == "") document.getElementById(id).submit();
    }
