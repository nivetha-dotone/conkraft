/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

function listDependency(urlValue, dependerId, tagId1, tagId2, tagId3, tagId4, tagId5, tagId6, tagId7, tagId8, tagId9, tagId10) {//alert('hai');
	var val1 = $('#'+tagId1).val();alert(val1);
	var val2 = $('#'+tagId2).val();
	var val3 = $('#'+tagId3).val();
	var val4 = $('#'+tagId4).val();
	var val5 = $('#'+tagId5).val();
	var val6 = $('#'+tagId6).val();
	var val7 = $('#'+tagId7).val();
	var val8 = $('#'+tagId8).val();
	var val9 = $('#'+tagId9).val();
	var val10 = $('#'+tagId10).val();
    $.ajax({
		url : urlValue,
		method : 'get',
		ContentType : 'json',
		data : {
			postedValue1 : val1,
			postedValue2 : val2,
			postedValue3 : val3,
			postedValue4 : val4,
			postedValue5 : val5,
			postedValue6 : val6,
			postedValue7 : val7,
			postedValue5 : val8,
			postedValue6 : val9,
			postedValue7 : val10
        },
       	success : function(response) {
        	var options = '';
	        if (response != null) {
    	    	$(response).each(function(index, value) {
        	    	var x = value.split("(~)");
         			options = options + '<option value="'+value+'">' + x[1] + '</option>';
	         	});
    	        $('#'+dependerId).html(options);
       		}
     	},
       	error:function (xhr, ajaxOptions, thrownError){ 
        	alert(xhr.status);
        	alert(thrownError);
   		}
    });
}

function uniqueness(urlValue, dependerId, dependerName, tagId1, tagId2, tagId3, tagId4 ) {
	var val1 = $('#'+tagId1).val();
    $.ajax({
		url : urlValue,
		method : 'get',
		ContentType : 'json',
		data : {
			postedValue1 : val1,
			postedValue2 : val2,
			postedValue3 : val3,
			postedValue4 : val4
        },
       	success : function(response) {
       		$('#'+dependerId).val(response);
        	//$('#'+dependerId).html(response);alert(response);
     	},
       	error:function (xhr, ajaxOptions, thrownError) {
        	alert(xhr.status);
        	alert(thrownError);
   		}
    });
}
