/* Application Name = Login Control Panel
 Version = 1.0
 Release Date = Dec 01, 2014
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

//Dependency for Select list
function listDependency(urlValue, dependerId, tagId1, tagId2, tagId3, tagId4,
		tagId5, tagId6, tagId7, tagId8, tagId9, tagId10) {
	var company = document.getElementById(tagId1).value;
	var val1 = $('#' + tagId1).val();// alert(val1);
	var val2 = $('#' + tagId2).val();
	var val3 = $('#' + tagId3).val();
	var val4 = $('#' + tagId4).val();
	var val5 = $('#' + tagId5).val();
	var val6 = $('#' + tagId6).val();
	var val7 = $('#' + tagId7).val();
	var val8 = $('#' + tagId8).val();
	var val9 = $('#' + tagId9).val();
	var val10 = $('#' + tagId10).val();
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
				options = options + '<option value="">'
						+ "click here to select" + '</option>';
				$(response).each(
						function(index, value) {
							var x = value.split("(~)");
							options = options + '<option value="' + value
									+ '">' + x[1] + '</option>';
						});
				$('#' + dependerId).prop('disabled', false);
				$('#' + dependerId).html(options);
			}
			if (response == '') {
				$('#' + dependerId).prop('disabled', true);
			}
		},

		error : function(xhr, ajaxOptions, thrownError) {
			var options = '';
			$('#' + dependerId).prop('disabled', true);
			options = options + '<option value="">' + "click here to select"
					+ '</option>';
			$('#' + dependerId).html(options);
			// alert(xhr.status);
			// alert(thrownError);
		}
	});
	if (urlValue == "getzonesList") {
		if (dependerId == '') {
			document.getElementById('add_zone_id').options[0].selected = true;
			document.getElementById('add_zone_id').disabled = true;
		}
		document.getElementById('add_region_id').options[0].selected = true;
		document.getElementById('add_region_id').disabled = true;
		document.getElementById('add_depot_id').options[0].selected = true;
		document.getElementById('add_depot_id').disabled = true;
	}
	if (urlValue == "getregions") {
		document.getElementById('add_region_id').options[0].selected = true;
		document.getElementById('add_region_id').disabled = true;
		document.getElementById('add_depot_id').options[0].selected = true;
		document.getElementById('add_depot_id').disabled = true;
	}
}

function uniqueness(urlValue, dependerId, dependerName, tagId1, tagId2, tagId3,
		tagId4) {
	var val1 = $('#' + tagId1).val();
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
			$('#' + dependerId).val(response);
			// $('#'+dependerId).html(response);alert(response);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			// alert(xhr.status);
			// alert(thrownError);
		}
	});
}
function doValidations(formId, validationUrl, errormsg) {
	var str = $("#" + formId).serialize();
	$.ajax({
		type : "post",
		data : str,
		url : validationUrl,
		async : false,
		dataType : "json",
		success : function(response) {
			if (response.status == "FAIL") {
				$('#' + errormsg).html(response.result);
				$('#' + errormsg).show('slow');
			} else {
				document.getElementById(formId).submit();
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
		}
	});
}
// Dependency for text area
function listDependency1(urlValue, dependerId, tagId1, tagId2, tagId3, tagId4,
		tagId5, tagId6, tagId7, tagId8, tagId9, tagId10) {// alert('hello');
	var val1 = $('#' + tagId1).val();// alert(val1);
	var val2 = $('#' + tagId2).val();
	var val3 = $('#' + tagId3).val();
	var val4 = $('#' + tagId4).val();
	var val5 = $('#' + tagId5).val();
	var val6 = $('#' + tagId6).val();
	var val7 = $('#' + tagId7).val();
	var val8 = $('#' + tagId8).val();
	var val9 = $('#' + tagId9).val();
	var val10 = $('#' + tagId10).val();
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
		success : function(response) {// alert('hello123');
			var options = '';
			if (response != null) {
				$(response).each(function(index, value) { // alert('hello2'+index+'value'+value+'depender'+dependerId);
					// var x = value.split("(~)");
					options = options + '<input value="' + value + '">';
					options = value;// alert('hello in ajax');

				});
				document.getElementById(dependerId).value = options;
//				$('#' + dependerId).html(options);
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
			$('#' + dependerId).html(null);
			// alert(xhr.status);
			// alert(thrownError);
		}
	});
}

// Dependency for Input box
function inputDependency(urlValue, dependerId, tagId1, tagId2, tagId3, tagId4,
		tagId5, tagId6) {
	var val1 = $('#' + tagId1).val();
	var val2 = $('#' + tagId2).val();
	var val3 = $('#' + tagId3).val();
	var val4 = $('#' + tagId4).val();
	var val5 = $('#' + tagId5).val();
	var val6 = $('#' + tagId6).val();
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
			postedValue6 : val6
		},
		success : function(response) {
			$('#' + dependerId).val(response);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			$('#' + dependerId).val(null);
		}
	});
}
function listAddressDependency(urlValue, dependerId, tagId1, tagId2, tagId3,
		tagId4, tagId5, tagId6, tagId7, tagId8, tagId9, tagId10) {
	var val1 = $('#' + tagId1).val();
	var val2 = $('#' + tagId2).val();
	var val3 = $('#' + tagId3).val();
	var val4 = $('#' + tagId4).val();
	var val5 = $('#' + tagId5).val();
	var val6 = $('#' + tagId6).val();
	var val7 = $('#' + tagId7).val();
	var val8 = $('#' + tagId8).val();
	var val9 = $('#' + tagId9).val();
	var val10 = $('#' + tagId10).val();
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
			if (response != null) {//alert();
				$(response).each(function(index, value) {//alert("1");
					document.getElementById(dependerId).value = value;
				});
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
		}
	});
}
function countryStatesDependency(urlValue, dependerId, tagId1, tagId2, tagId3,
		tagId4, tagId5, tagId6, tagId7, tagId8, tagId9, tagId10) {
	var val1 = $('#' + tagId1).val();
	var val2 = $('#' + tagId2).val();
	var val3 = $('#' + tagId3).val();
	var val4 = $('#' + tagId4).val();
	var val5 = $('#' + tagId5).val();
	var val6 = $('#' + tagId6).val();
	var val7 = $('#' + tagId7).val();
	var val8 = $('#' + tagId8).val();
	var val9 = $('#' + tagId9).val();
	var val10 = $('#' + tagId10).val();
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
			postedValue8 : val8,
			postedValue9 : val9,
			postedValue10 : val10
		},
		success : function(response) {
			var options = '';
			if (response != null) {
				$(response).each(
						function(index, value) {
							listAddressDependency('getDefaultState',
									'add_state_id', 'add_zone_id');

							listAddressDependency('getzDefaultState',
									'add_users_state_id', 'add_company_id');
							listAddressDependency('get1DefaultState',
									'add_state_id', 'tagId1');
							listAddressDependency('getCRDefaultState',
									'add_state_id', 'add_company_id');
							var x = value.split("(~)");
							options = options + '<option value="' + value
									+ '">' + x[1] + '</option>';
						});
				$('#' + dependerId).html(options);
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
			// alert(xhr.status);
			// alert(thrownError);
		}
	});
}
function doValidationsCompany(formId, validationUrl, errormsg, pageOne,
		pageTwo, pageThree) {
	var str = $("#" + formId).serialize();
	$.ajax({
		type : "post",
		data : str,
		url : validationUrl,
		async : false,
		dataType : "json",
		success : function(response) {

			if (response.status == "FAIL") {
				$('#' + errormsg).html(response.result);
				$('#' + errormsg).show('slow');
			} else {

				if (pageOne == "Yes") {
					nextshowdiv();
					document.getElementById('first_page').value = 'No';
				} else if (pageTwo == "Yes") {
					nextshowdiv1();
					document.getElementById('second_page').value = 'No';
				} else if (pageThree == "Yes") {
					nextshowdivThirdPage();
					document.getElementById('third_page').value = 'No';
				} else {
					document.getElementById(formId).submit();
				}

			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
			// alert(xhr.status);
			// alert(thrownError);
		}
	});
}
function listDependency2(urlValue, dependerId, tagId1, tagId2, tagId3, tagId4, tagId5, tagId6, tagId7, tagId8, tagId9, tagId10) {//alert(urlValue);
	//var company = document.getElementById(tagId1).value; //alert(company);
	var val1 = $('#'+tagId1).val(); //alert(tagId1);
	var val2 = $('#'+tagId2).val();//alert(tagId2);
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
	        	options = options + '<option value="">' + "click here to select" + '</option>'; 
		    	$(response).each(function(index, value) {
	    	    	var x = value.split("(~)"); 
	     			options = options + '<option value="'+value+'">' + x[1] + '</option>'; 
	         	});
		    	$('#'+dependerId).prop('disabled', false);
		        $('#'+dependerId).html(options);
	   		}
	        if(response ==''){ 
	   			$('#'+dependerId).prop('disabled', true);
	   		}
	 	},
	   	error:function (xhr, ajaxOptions, thrownError){ 
	   		var options = '';
	   		$('#'+dependerId).prop('disabled', true);
	   		options = options + '<option value="">' + "click here to select" + '</option>';
	   		$('#'+dependerId).html(options);
	    	//alert(xhr.status);
	    	//alert(thrownError);
			}
	}); 
	if(urlValue == "getuser_role"){ alert(urlValue == "getuser_role");
		if(dependerId == '') {
	    	document.getElementById('add_userrole_id').options[0].selected = true;
	    	document.getElementById('add_userrole_id').disabled = true;
		}
		document.getElementById('add_entity_id').options[0].selected = true;  
		document.getElementById('add_entity_id').disabled = true;
		document.getElementById('add_entity_resource_id').options[0].selected = true; 
		document.getElementById('add_entity_resource_id').disabled = true;
	  }if(urlValue == "getentity_role"){ alert('add_entity_id');
		document.getElementById('add_entity_id').options[0].selected = true;
		document.getElementById('add_entity_id').disabled = true;
		document.getElementById('add_entity_resource_id').options[0].selected = true;
		document.getElementById('add_entity_resource_id').disabled = true;
		
	}
	}
function countryStatesDependency1(urlValue, dependerId, tagId1, tagId2, tagId3,
		tagId4, tagId5, tagId6, tagId7, tagId8, tagId9, tagId10) {
	var val1 = $('#' + tagId1).val();
	var val2 = $('#' + tagId2).val();
	var val3 = $('#' + tagId3).val();
	var val4 = $('#' + tagId4).val();
	var val5 = $('#' + tagId5).val();
	var val6 = $('#' + tagId6).val();
	var val7 = $('#' + tagId7).val();
	var val8 = $('#' + tagId8).val();
	var val9 = $('#' + tagId9).val();
	var val10 = $('#' + tagId10).val();
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
			postedValue8 : val8,
			postedValue9 : val9,
			postedValue10 : val10
		},
		success : function(response) {
			var options = '';
			if (response != null) {
				$(response).each(
						function(index, value) {
							listAddressDependency('getDefaultState1',
									'add_state_id', 'add_entity_resource_id');
							var x = value.split("(~)");
							options = options + '<option value="' + value
									+ '">' + x[1] + '</option>';
						});
				$('#' + dependerId).html(options);
			}
		},
		error : function(xhr, ajaxOptions, thrownError) {
			// alert(xhr.status);
			// alert(thrownError);
		}
	});
}