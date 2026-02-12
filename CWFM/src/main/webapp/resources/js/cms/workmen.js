var divIndex=0;
//let selectedDocumentTypes = new Set();
function searchWithPEContractorInWorkmenAadharList(contextPath) {
	 var principalEmployerId = document.getElementById("principalEmployerId").value;
	 var contractorId = document.getElementById("contractorId").value;
 event.preventDefault();
     // Assuming you have jQuery available for making AJAX requests
     $.ajax({
         type: "GET",
         url: contextPath + "/contractworkmen/aadharOnbordingList",
         data: { principalEmployerId: principalEmployerId,contractorId:contractorId}, // Pass the search query as data
         success: function(response) {
             // Handle success response
           //  console.log("Search results:", response);
             document.getElementById("mainContent").innerHTML = response;
         },
         error: function(xhr, status, error) {
             // Handle error response
             console.error("Error searching:", error);
         }
     });
 }
 
 function redirectToWorkmenAadharOBAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/addAadharOB", true);
    xhr.send();
}

function redirectToWorkmenQuickOBAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/addQuickOB", true);
    xhr.send();
}
function getContractorsAndTrades(unitId, userAccount) {
    if (!unitId) {
        alert("Please select a Principal Employer.");
        return;
    }

    // Fetch contractors
    getContractors(unitId, userAccount);

    // Fetch trades
    getTrades(unitId);
    
    getDepartments(unitId);
}


function getContractors(unitId, userAccount) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllContractors?unitId=" + unitId + "&userAccount=" + userAccount;
    //alert("URL: " + url);
    xhr.open("GET", url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            // Parse the response as a JSON array of contractor objects
            var contractors = JSON.parse(xhr.responseText);
            console.log("Response:", contractors);
            
            // Find the contractor select element
            var contractorSelect = document.getElementById("contractor");
            
            // Clear existing options
            contractorSelect.innerHTML = '<option value="">Please select Contractor</option>';
            
            // Populate the dropdown with the new list of contractors
            contractors.forEach(function(contractor) {
                var option = document.createElement("option");
                option.value = contractor.contractorId;
                option.text = contractor.contractorName;
                contractorSelect.appendChild(option);
            });
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function() {
        console.error("Request failed");
    };

    xhr.send();
}

function getWorkordersAndWC() {
	 var principalEmployerSelect = document.getElementById("principalEmployer");
    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
    var contractorSelect = document.getElementById("contractor");
    var contractorId = contractorSelect.value; // Get the selected contractor value
    
 if (!unitId) {
        alert("Please select a Principal Employer.");
        return;
    }
    
  if(!contractorId){
	 alert("Please select a Contractor.");
        return;
  }

    getWorkorders(unitId, contractorId);

    //getWC(unitId, contractorId);
}

function getWorkorders(unitId,contractorId) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllWorkOrders?unitId=" + unitId + "&contractorId=" + contractorId;
    //alert("URL: " + url);
    xhr.open("GET", url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            // Parse the response as a JSON array of workorder objects
            var workorders = JSON.parse(xhr.responseText);
            console.log("Response:", workorders);
            
            // Find the workorder select element
            var workorderSelect = document.getElementById("workorder");
            
            // Clear existing options
            workorderSelect.innerHTML = '<option value="">Please select Workorder</option>';
            
            // Populate the dropdown with the new list of workorders
            workorders.forEach(function(workorder) {
                var option = document.createElement("option");
                option.value = workorder.workorderId;
                option.text = workorder.name;
                workorderSelect.appendChild(option);
            });
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function() {
        console.error("Request failed");
    };

    xhr.send();
}

function getWC() {
	var principalEmployerSelect = document.getElementById("principalEmployer");
    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
    var contractorSelect = document.getElementById("contractor");
    var contractorId = contractorSelect.value; 
     var workorderSelect =document.getElementById("workorder");
     var workorderId = workorderSelect.value; 
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllWC?unitId=" + unitId + "&contractorId=" + contractorId +"&workorderId=" + workorderId;
    //alert("URL: " + url+" "+unitId+" "+contractorId+" "+workorderId);
    xhr.open("GET", url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            var wcs = JSON.parse(xhr.responseText);
            console.log("Response:", wcs);
            
            var wcSelect = document.getElementById("wc");
			var llSelect = document.getElementById("ll");
            wcSelect.innerHTML = '<option value="">Please select WC Policy/ESIC Reg Number</option>';
			llSelect.innerHTML = '<option value="">Please select Labor License</option>';
            wcs.forEach(function(wc) {
                var option = document.createElement("option");
                option.value = wc.wcId;
                option.text = wc.wcCode;
				 option.setAttribute("data-code", wc.licenceType);
				if (wc.licenceType === "LL") {
				                    llSelect.appendChild(option);
				                } else if (wc.licenceType === "WC" || wc.licenceType === "ESIC") {
				                    wcSelect.appendChild(option);
				                }
            });
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function() {
        console.error("Request failed");
    };

    xhr.send();
}


function getTrades(unitId) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllTrades?unitId=" + unitId;
    console.log("Fetching trades from URL:", url);
    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var trades = JSON.parse(xhr.responseText);
            console.log("Trades:", trades);
            var tradeSelect = document.getElementById("trade");

            // Clear existing options
            tradeSelect.innerHTML = '<option value="">Please select Trade</option>';

            // Populate the trade dropdown
            trades.forEach(function (trade) {
                var option = document.createElement("option");
                option.value = trade.tradeId;
                option.text = trade.tradeName;
                tradeSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching trades:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed while fetching trades");
    };

    xhr.send();
}
function getSkills(unitId,tradeId) {
	var principalEmployerSelect = document.getElementById("principalEmployer");
    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
    var tradeSelect = document.getElementById("trade");
    var tradeId = tradeSelect.value; 
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllSkills?unitId=" + unitId + "&tradeId=" + tradeId;
    console.log("Fetching trades from URL:", url);
    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var skills = JSON.parse(xhr.responseText);
            console.log("Skills:", skills);
            var skillSelect = document.getElementById("skill");

            // Clear existing options
            skillSelect.innerHTML = '<option value="">Please select Skill</option>';

            // Populate the trade dropdown
            skills.forEach(function (skill) {
                var option = document.createElement("option");
                option.value = skill.skillId;
                option.text = skill.skill;
                skillSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching skills:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed while fetching skill");
    };

    xhr.send();
}

function getDepartments(unitId) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllDepartments?unitId=" + unitId;
    console.log("Fetching departments from URL:", url);
    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var departments = JSON.parse(xhr.responseText);
            console.log("Departments:", departments);
            var departmentSelect = document.getElementById("department");

            // Clear existing options
            departmentSelect.innerHTML = '<option value="">Please select Department</option>';

            // Populate the trade dropdown
            departments.forEach(function (department) {
                var option = document.createElement("option");
                option.value = department.departmentId;
                option.text = department.department;
                departmentSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching departments:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed while fetching departments");
    };

    xhr.send();
}

function getAreabyDept(unitId,departmentId) {
	var principalEmployerSelect = document.getElementById("principalEmployer");
    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
    var departmentSelect = document.getElementById("department");
    var departmentId = departmentSelect.value; 
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllSubDepartments?unitId=" + unitId + "&departmentId=" + departmentId;
    console.log("Fetching subdepartment from URL:", url);
    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var subdepartment = JSON.parse(xhr.responseText);
            console.log("Areas:", subdepartment);
            var subdepartmentSelect = document.getElementById("subdepartment");

            // Clear existing options
            subdepartmentSelect.innerHTML = '<option value="">Please select Area</option>';

            // Populate the trade dropdown
            subdepartment.forEach(function (subdepartment) {
                var option = document.createElement("option");
                option.value = subdepartment.subDepartmentId;
                option.text = subdepartment.subDepartment;
                subdepartmentSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching subdepartments:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed while fetching subdepartment");
    };

    xhr.send();
}

function initializeDatePicker() {
        $('.datetimepickerformat').datepicker({
            dateFormat: 'yy-mm-dd', // Set the date format
            changeMonth: true,      // Allow changing month via dropdown
            changeYear: true,       // Allow changing year via dropdown
            yearRange: "-100:+0",   // Set the year range from 100 years ago to the current year
            maxDate: 0              // Prevent selecting future dates
        });
    }
	function initializeDatePicker1() {
	    $('.datetimepickerformat1').datepicker({
	        dateFormat: 'yy-mm-dd', // Set the date format
	        changeMonth: true,      // Allow changing month via dropdown
	        changeYear: true,       // Allow changing year via dropdown
	        yearRange: "0:+100", 
	        minDate: 0,
			maxDate: +15               // Prevent selecting future dates
	    });
	} 
    function validateBasicData() {
    let isValid = true;
	let aadharCheckPassed = false;
    const aadharNumber = $("#aadharNumber").val().trim();

	//const transactionId=$("#transactionId").val().trim();
    if (aadharNumber === "" || aadharNumber.length !== 12 || isNaN(aadharNumber)) {

        $("#error-aadhar").show();
        isValid = false;
    }else{
		 // $("#error-aadhar").hide();
		 $.ajax({
		 				     url: "/CWFM/contractworkmen/checkAadharExistsCreation",
		 				     type: "GET",
		 				     data: {
		 				         aadharNumber: aadharNumber,
		 				         gatePassId: $("#gatePassId").val(),        // NULL for draft
		 				         transactionId: $("#transactionId").val()   // always present for draft/renewal
		 				     },
		 				     async: false,
		 				     success: function (response) {

		 						let status = response.status ? response.status.trim() : '';
		 						if (status !== "Unique" && status !== "Invalid" && status !== "") {
		 						    $("#error-aadhar").text("Aadhaar already exists").show();
		 						    aadharCheckPassed = false;
		 						} else if (status === "Invalid") {
		 						    $("#error-aadhar").text("Invalid Aadhar Number").show();
		 						    aadharCheckPassed = false;
		 						} else {
		 						    $("#error-aadhar").hide();
		 						    aadharCheckPassed = true;
		 						}

		 				     },
		 				     error: function () {
		 				         $("#error-aadhar").text("Unable to verify Aadhaar").show();
		 				         aadharCheckPassed = false;
		 				     }
		 				 });


		     }
	
    const firstName = $("#firstName").val().trim();
    const firstnameRegex = /^[A-Za-z\s]{2,}$/;
    if (!firstnameRegex.test(firstName)) {
    $("#error-firstName").show(); 
    isValid = false;
    } else {
    $("#error-firstName").hide(); 
    }
    const lastName = $("#lastName").val().trim();
    const lastnameRegex = /^[A-Za-z\s]{1,}$/;
    if (!lastnameRegex.test(lastName)) {
        $("#error-lastName").show();
        isValid = false;
    }else{
		 $("#error-lastName").hide();
	}
	/*if (firstName.toLowerCase() === lastName.toLowerCase()) {
    $("#error-equalNames").show();
    isValid = false;
    } else {
    $("#error-equalNames").hide();
    }*/
    const dateOfBirth = $("#dateOfBirth").val().trim();
    if (dateOfBirth === "") {
        $("#error-dateOfBirth").show();
        isValid = false;
    }else{
		 $("#error-dateOfBirth").hide();
	}
    const gender = $("#gender").val();
    if (gender === "") {
        $("#error-gender").show();
        isValid = false;
    }else{
		$("#error-gender").hide();
	}
    const relationName = $("#relationName").val().trim();
    const relationnameRegex = /^[A-Za-z\s]{2,}$/;  // Only alphabetic characters, at least 2 letters
    if (!relationnameRegex.test(relationName)) {
    $("#error-relationName").show(); // Show error if invalid
    isValid = false;
    } else {
    $("#error-relationName").hide(); // Hide error if valid
    }
    const idMark = $("#idMark").val().trim();
    const idmarkRegex=/^[A-Za-z\s]+$/;
    if (!idmarkRegex.test(idMark)) {
        $("#error-idMark").show();
        isValid = false;
    }else{
		 $("#error-idMark").hide();
	}
	
	const mobileInput = $("#mobileNumber").val().trim();
	const mobileNumberRegex = /^[6-9]\d{9}$/;
	if (!mobileNumberRegex.test(mobileInput)) {
                 $("#error-mobileNumber").show();
        			isValid = false;
     }else{
		 $("#error-mobileNumber").hide();
	 }
	 
	 const maritalInput = $("#maritalStatus").val();
	 if (maritalInput === "") {
        $("#error-maritalStatus").show();
        isValid = false;
    }else{
		$("#error-maritalStatus").hide();
	}
	const workmenType = $("#workmenType").val();
	 if (workmenType === "") {
        $("#error-workmenType").show();
        isValid = false;
    }else{
		$("#error-workmenType").hide();
	}
	const disability = $("#disability").val();
	 if (disability === "") {
        $("#error-disability").show();
        isValid = false;
    }else{
		$("#error-disability").hide();
	}
	const address=$("#address").val().trim();
	const addressRegex=/^[A-Za-z0-9\s,.'-]{5,}$/;
	if (!addressRegex.test(address)) {
                 $("#error-address").show();
        			isValid = false;
     }else{
		 $("#error-address").hide();
	 }
console.log(isValid);
    return isValid && aadharCheckPassed;

}

function validateEmploymentInformation(){
	let type = $("#gatePassType").val();
	let isValid = true;
    const principalEmp = $("#principalEmployer").val();
     if (principalEmp === "") {
        $("#error-principalEmployer").show();
        isValid = false;
    }else{
		$("#error-principalEmployer").hide();
	}
	const cont = $("#contractor").val();
     if (cont === "") {
        $("#error-contractor").show();
        isValid = false;
    }else{
		$("#error-contractor").hide();
	}
	const wo = $("#workorder").val();
     if (wo === "") {
        $("#error-workorder").show();
        isValid = false;
    }else{
		$("#error-workorder").hide();
	}
	const trade = $("#trade").val();
     if (trade === "") {
        $("#error-trade").show();
        isValid = false;
    }else{
		$("#error-trade").hide();
	}
	const skill = $("#skill").val();
     if (skill === "") {
        $("#error-skill").show();
        isValid = false;
    }else{
		$("#error-skill").hide();
	}
	const dept = $("#department").val();
     if (dept === "") {
        $("#error-department").show();
        isValid = false;
    }else{
		$("#error-department").hide();
	}
	const subdept = $("#subdepartment").val();
     if (subdept === "") {
        $("#error-area").show();
        isValid = false;
    }else{
		$("#error-area").hide();
	}
	const eic = $("#eic").val();
     if (eic === "") {
        $("#error-eic").show();
        isValid = false;
    }else{
		$("#error-eic").hide();
	}
	if(type ==="regular" || type === "quick"){
	 const noj = $("#natureOfJob").val().trim();
	const nojRegex = /^(?=.*[A-Za-z]{2,})[A-Za-z\s]+$/;
    if (!nojRegex.test(noj)) {
        $("#error-natureOfJob").show();
        isValid = false;
    }else{
		 $("#error-natureOfJob").hide();
	}
	const wc = $("#wc").val();
     if (wc === "") {
        $("#error-wc").show();
        isValid = false;
    }else{
		$("#error-wc").hide();
	}
	//const ll = $("#ll").val();
    // if (ll === "") {
     //   $("#error-ll").show();
    //    isValid = false;
    //}else{
	//	$("#error-ll").hide();
	//}
	const ha = $("#hazardousArea").val();
     if (ha === "") {
        $("#error-hazardous").show();
        isValid = false;
    }else{
		$("#error-hazardous").hide();
	}
	const aa = $("#accessArea").val();
     if (aa === "") {
        $("#error-accessArea").show();
        isValid = false;
    }else{
		$("#error-accessArea").hide();
	}
	let uanCheckPassed = false;
	
	 const uan = $("#uanNumber").val().trim();
	 
	 const aadharNumber = $("#aadharNumber").val().trim();
	 const uanRegex = /^\d{12}$/;
    if (!uanRegex.test(uan)) {
        $("#error-uanNumber").show();
        isValid = false;
    }else if (uan === "000000000000") {
		    // UAN is 12 zeros, skip duplicate check
		    $("#error-uanNumber").hide();
		    uanCheckPassed = true;
		}else{
	  // Check in backend if Aadhar exists
		         $.ajax({
		             url: "/CWFM/contractworkmen/checkUanExists",
		             type: "GET",
		             data: { uan: uan,aadharNumber : aadharNumber },
		             async: false, // NOTE: synchronous to block form submission
		             success: function (response) {
		                 if (response.exists) {
                             $("#error-uanNumber").text("UAN already exists with Aadhaar Number: " + response.otherAadhar).show();
							 uanCheckPassed = true;
                              isValid = false;
                           } else {
		                     $("#error-uanNumber").hide();
		                     uanCheckPassed = true;
		                 }
		             },
		             error: function () {
		                 $("#error-uanNumber").text("Error checking UAN").show();
		                 isValid = false;
		             }
		         });
		     }       

	const healthCheckDate = $("#healthCheckDate").val().trim();
    if (healthCheckDate === "") {
        $("#error-healthCheckDate").show();
        isValid = false;
    }else{
		 $("#error-healthCheckDate").hide();
	}
	let pfNumberCheckPassed = false;
	const pfNumber = $("#pfNumber").val().trim();
	const cleanedPf = pfNumber.trim().replace(/\s+/g, '').toLowerCase();
	if (!cleanedPf ) {
	    $("#error-pfNumber").show();
	    isValid = false;
	} 
	else if (cleanedPf === "newjoinee") {
	    // Special case → skip validation, do NOT show error
	    $("#error-pfNumber").hide();
	    pfNumberCheckPassed = true;
	}else {
	    const pfRegex = /^[A-Z]{2}[A-Z]{3}\d{7}\d{2}[A-Z0-9]\d{7}$/;
	    
		const cmpPfRegex = /^CMPPF[A-Z0-9]+$/i;

	    let isPfValid = false;

	    if (pfNumber.length === 22) {
	        if (pfRegex.test(pfNumber)) {
	            isPfValid = true;
	        } else {
	            $("#error-pfNumber").text("Invalid PF Number format. Expected 22-character structured PF number.").show();
	            isValid = false;
	        }
	    } else if (cmpPfRegex.test(pfNumber)) {
	        isPfValid = true;
	    } else {
	        $("#error-pfNumber").text("Invalid PF Number. It should either be 22 characters in PF format or start with CMPPF.").show();
	        isValid = false;
	    }

	    // Only proceed with AJAX call if PF format is valid
	    if (isPfValid) {
	        $.ajax({
	            url: "/CWFM/contractworkmen/checkpfNumberExists",
	            type: "GET",
	            data: { pfNumber: pfNumber, aadharNumber: aadharNumber },
	            async: false,
	            success: function (response) {
	                if (response.exists) {
	                    $("#error-pfNumber").text("PF Number already exists with Aadhaar Number: " + response.otherAadhar).show();
	                    isValid = false;
	                } else {
	                    $("#error-pfNumber").hide();
	                    pfNumberCheckPassed = true;
	                }
	            },
	            error: function () {
	                $("#error-pfNumber").text("Error checking PF Number").show();
	                isValid = false;
	            }
	        });
	    }
	}
    
	const selectedOption = $("#wc").find(":selected");
	//const licenceType = selectedOption.data("licencetype");
	const licenceType = selectedOption.attr("data-code");
	const esicNumber = $("#esicNumber").val().trim();

	if (licenceType === "ESIC") {
showEsic();
    if ( esicNumber === "") {
        $("#error-esicNumber").text("ESIC Number is required").show();
        isValid = false;
    } else {
        $("#error-esicNumber").hide(); // ✅ hides properly
    }

} else {
    $("#error-esicNumber").hide(); // ✅ hides properly
}


	const doj = $("#doj").val().trim();
    if (doj === "") {
        $("#error-doj").show();
        isValid = false;
    }else{
		 $("#error-doj").hide();
	}
	}
	return isValid;
}

function validateOtherInformation(){
	let isValid = true;
    const bg = $("#bloodGroup").val();
     if (bg === "") {
        $("#error-bloodGroup").show();
        isValid = false;
    }else{
		$("#error-bloodGroup").hide();
	}
	 const accom = $("#accommodation").val();
     if (accom === "") {
        $("#error-accommodation").show();
        isValid = false;
    }else{
		$("#error-accommodation").hide();
	}
	 const academic = $("#academic").val();
     if (academic === "") {
        $("#error-academic").show();
        isValid = false;
    }else{
		$("#error-academic").hide();
	}
	const tech = $("#technical").val();
     if (tech === "") {
        $("#error-technical").show();
        isValid = false;
    }else{
		$("#error-technical").hide();
	}
	const ifscInput = $("#ifscCode").val().trim();
    const ifscCodeRegex = /^[A-Z]{4}0[A-Z0-9]{6}$/;
    if (!(ifscCodeRegex.test(ifscInput) || ifscInput.toUpperCase() === "NEW JOINEE")) {
    $("#error-ifscCode").show();
    isValid = false;
      } else {
    $("#error-ifscCode").hide();
     }

	const accountInput = $("#accountNumber").val().trim();
    const accountNumberRegex = /^[0-9]{9,18}$/;
	if (!(accountNumberRegex.test(accountInput) || accountInput.toUpperCase() === "NEW JOINEE")) {
    $("#error-accountNumber").show();
    isValid = false;
    } else {
    $("#error-accountNumber").hide();
    }

	const emergencyName = $("#emergencyName").val().trim();
	const firstnameRegex = /^[A-Za-z\s]{2,}$/;
    if (!firstnameRegex.test(emergencyName)) {
        $("#error-emergencyName").show();
        isValid = false;
    }else{
		$("#error-emergencyName").hide();
	}
	const emergencyNoInput = $("#emergencyNumber").val().trim();
	const mobileNumberRegex = /^[6-9]\d{9}$/;
	if (!mobileNumberRegex.test(emergencyNoInput)) {
                 $("#error-emergencyNumber").show();
        			isValid = false;
     }else{
		 $("#error-emergencyNumber").hide();
	 }
	return isValid;
}

function validateWages(){
	let isValid = true;
    const wage = $("#wageCategory").val();
     if (wage === "") {
        $("#error-wageCategory").show();
        isValid = false;
    }else{
		$("#error-wageCategory").hide();
	}
	const bonus = $("#bonusPayout").val();
     if (bonus === "") {
        $("#error-bonusPayout").show();
        isValid = false;
    }else{
		$("#error-bonusPayout").hide();
	}
	const zone = $("#zone").val();
     if (zone === "") {
        $("#error-zone").show();
        isValid = false;
    }else{
		$("#error-zone").hide();
	}
	const allowanceRegex = /^[0-9]{1,6}(\.[0-9]{1,2})?$/;
	const basic = $("#basic").val().trim();
	const da = $("#da").val().trim();
	const hra = $("#hra").val().trim();
	const washing = $("#washingAllowance").val().trim();
	const other = $("#otherAllowance").val().trim();
	const uniform = $("#uniformAllowance").val().trim();
	if(basic === ""){
		//$("#basic").val("0.00"); 
		$("#error-basic").show();
		isValid = false;
	//not mandatory	
	}
	/*else 	if ( !allowanceRegex.test(basic) ) {
             $("#error-basic").show();
       		 isValid = false; 
			  
      }*/
      else{
		 $("#error-basic").hide();
	  }
	  if(da === ""){
		//$("#da").val("0.00"); 
		$("#error-da").show();
		isValid = false;
	  }/*else	  if ( !allowanceRegex.test(da) ) {
             $("#error-da").show();
       		 isValid = false;    
      }*/
      else{
		 $("#error-da").hide();
	  }
	  if(hra === ""){
		//$("#hra").val("0.00");
		  $("#error-hra").show();
		  isValid = false; 
	  }
	 /* else	  if ( !allowanceRegex.test(hra) ) {
             $("#error-hra").show();
       		 isValid = false;    
      }*/
      else{
		 $("#error-hra").hide();
	  }
	  if(washing === ""){
		//$("#washingAllowance").val("0.00"); 
		 $("#error-washingAllowance").show();
		 isValid = false;
	  }
	  /*else	  if ( !allowanceRegex.test(washing) ) {
             $("#error-washingAllowance").show();
       		 isValid = false;    
      }*/
      else{
		 $("#error-washingAllowance").hide();
	  }
	  if(other === ""){
		//$("#otherAllowance").val("0.00"); 
		 $("#error-otherAllowance").show();
		 isValid = false;
		  	  }
		/*  	  else	  if ( !allowanceRegex.test(other) ) {
             $("#error-otherAllowance").show();
       		 isValid = false;    
      }*/
      else{
		 $("#error-otherAllowance").hide();
	  }
	  if(uniform === ""){
		//$("#uniformAllowance").val("0.00"); 
		 $("#error-uniformAllowance").show();
		 isValid = false;
	  }
	/*  else	  if ( !allowanceRegex.test(uniform) ) {
             $("#error-uniformAllowance").show();
       		 isValid = false;    
      }*/
      else{
		 $("#error-uniformAllowance").hide();
	  }
	  
	return isValid;
}

function fileUpload(){
	let isValid = true;
        // Get the selected files
        var aadharFile = $("#aadharFile").prop("files")[0];
        var policeFile = $("#policeFile").prop("files")[0];
		var profilePic =$("imageFile").prop("files")[0];
		var appointmentFile = $("#appointmentFile").prop("files")[0];
        // Validate the files (optional)
        if (!validateFiles(aadharFile, policeFile,profilePic,appointmentFile)) {
            isValid=false; // Stop the upload if validation fails
        }

        // Create a FormData object
        var formData = new FormData();
        if (aadharFile) {
            formData.append("aadharFile", aadharFile);
        }
        if (policeFile) {
            formData.append("policeFile", policeFile);
        }
		if(profilePic){
			formData.append("profilePic",profilePic);
		}if(appointmentFile){
			formData.append("appointmentFile",appointmentFile);
		}

        // Submit the form data using AJAX
        $.ajax({
            url: "/CWFM/contractworkmen/uploadDocuments", // Your server-side upload handling URL
            type: "POST",
            data: formData,
            contentType: false, // Tell jQuery not to set contentType
            processData: false, // Tell jQuery not to process the data
            success: function(response) {
                // Display success message
                $("#uploadMessage").text("Documents uploaded successfully!").css("color", "green");
            },
            error: function(xhr, status, error) {
                // Display error message
                $("#uploadMessage").text("Error uploading documents: " + xhr.responseText).css("color", "red");
            }
        });
   return isValid;
}
function validateFiles(aadharFile, policeFile, profilePc,appointmentFile) {
    let valid = true;

    // Aadhar File - Mandatory & Size check
    if (!aadharFile) {
        $("#aadharError").text("Aadhar file is required").addClass("error-bold");
        valid = false;
    } else if (aadharFile.size > 5 * 1024 * 1024) {
        $("#aadharError").text("Aadhar file must be less than 5MB").addClass("error-bold");
        valid = false;
    } else {
        $("#aadharError").text("");
    }

    // Police Verification File - Mandatory & Size check
    if (!policeFile) {
        $("#policeError").text("Police verification file is required").addClass("error-bold");
        valid = false;
    } else if (policeFile.size > 5 * 1024 * 1024) {
        $("#policeError").text("Police file must be less than 5MB").addClass("error-bold");
        valid = false;
    } else {
        $("#policeError").text("");
    }
//appointment File - Mandatory & Size check
 if (!appointmentFile) {
        $("#appointmentError").text("Appointment Letter is required").addClass("error-bold");
        valid = false;
    } else if (appointmentFile.size > 5 * 1024 * 1024) {
        $("#appointmentError").text("Police file must be less than 5MB").addClass("error-bold");
        valid = false;
    } else {
        $("#appointmentError").text("");
    }
// Police Verification Date - Mandatory  check
const policeVerificationDate = $("#policeVerificationDate").val().trim();
    if (policeVerificationDate === "") {
        $("#error-policeVerificationDate").show();
        valid = false;
    }else {
        $("#error-policeVerificationDate").hide("");
    }
    
    // Profile Photo - Mandatory & Size check
    if (!profilePc) {
        $("#profilePcError").text("Profile photo is required").addClass("error-bold");
        valid = false;
    } else if (profilePc.size > 5 * 1024 * 1024) {
        $("#profilePcError").text("Photo/Image must be less than 5MB").addClass("error-bold");
        valid = false;
    } else {
        $("#profilePcError").text("");
    }

	const comments = $("#comments").val().trim();
		    if (comments === "") {
		        $("#error-comments").show();
		        valid = false;
		    }else{
				 $("#error-comments").hide();
			}
			// Accept Checkbox validation
			    if (!$("#acceptCheck").is(":checked")) {
			        $("#acceptError").show();
			        valid = false;
			    } else {
			        $("#acceptError").hide();
			    }		
    return valid;
}

    
  
	
	function redirectToWorkmenView() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;
    //var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
    var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed

         if (status.toLowerCase() == "draft") {
             alert("Status 'Draft' record can not View.");
            return;
           }
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/view/" + transactionId, true);
    xhr.send();
}

 function preventEdit(event) {
        // Prevent further changes to the dropdown
        event.target.disabled = true;
    }
    
    function approveRejectGatePass(status,type){
		showLoader();
		let isValid=true;let gatePassType=1;
		if(type === "project"){
			gatePassType=12;
		}
		 const approvercomments = $("#approvercomments").val().trim();
    if (approvercomments === "" && status==5) {
        $("#error-approvercomments").show();
        alert("Comments Required in Documents");
        isValid = false;
        hideLoader();
    }else{
		$("#error-approvercomments").hide();
	}
	if(isValid){
		const data = {
			approverId : $("#userId").val().trim(),
			comments : $("#approvercomments").val().trim(),
			status : status,
			transactionId : $("#transactionId").val().trim(),
			gatePassId : $("#gatePassId").val().trim(),
			approverRole : $("#roleName").val().trim(),
			roleId :$("#roleId").val().trim(),
			gatePassType : gatePassType,
		};
			  const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
    xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
    xhr.onload = function() {
		hideLoader();
        if (xhr.status === 200) {
            // Handle successful response
            console.log("Data saved successfully:", xhr.responseText);
			sessionStorage.setItem("successMessage", "Gatepass approved/rejected successfully!");
            if(type=== "regular"){
                    loadCommonList('/contractworkmen/list', 'On-Boarding List');
                    //hideLoader();
                }else  if(type=== "quick"){
                    loadCommonList('/contractworkmen/quickOnboardingList', 'Quick Onboarding List');
                    //hideLoader();
                }else{
					loadCommonList('/contractworkmen/projectOnboardingList', 'Project Gatepass List');
					//hideLoader();
				}
        }			else if (xhr.status === 400) {  
							       const msg = xhr.responseText.trim();
							       console.error("Server validation failed: " + msg);
								   showLicenseError(msg);
								  // hideLoader();
							       //alertap(msg); // or show in UI better
							       //sessionStorage.setItem("errorMessage", msg);
								   return;
							   } 
		else {
            // Handle error response
            console.error("Error saving data:", xhr.statusText);
			sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass!");
			hideLoader();
        }
    };
    
    xhr.onerror = function() {
		//hideLoader();
        console.error("Request failed");
		sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass!");
		hideLoader();
    };
    
    // Send the data object as a JSON string
    xhr.send(JSON.stringify(data));
		}else{
			//error 
		}
		}//eofunc
		
		
		
		function trimIfPresent(val) {
		    return val ? val.trim() : val;
		}


    

	function submitGatePass(userId,type) {
		showLoader();
    let basicValid = true;
    let employmentValid = true;
    let otherValid = true;
    let wagesValid = true;
    let documentValid = true;

    var aadharFile = $("#aadharFile").prop("files")[0];
    var policeFile = $("#policeFile").prop("files")[0];
	var profilePic = $("#imageFile").prop("files")[0];
	var appointmentFile = $("#appointmentFile").prop("files")[0];

    // Validate the files (optional)
    if (!validateFiles(aadharFile, policeFile,profilePic,appointmentFile)) {
        documentValid = false; // Stop the upload if validation fails
        hideLoader();
    }

    if (!validateBasicData()) {
        basicValid = false;
    }
	if(type=== "project"){
		if (!validateProjectEmploymentInformation()) {
		        employmentValid = false;
		         hideLoader();
		    }
	}else{
    if (!validateEmploymentInformation()) {
        employmentValid = false;
         hideLoader();
    }
	if (!validatePfForm11Requirement()) {
	        documentValid = false;
	         hideLoader();
	    }
	}
	if(type=== "regular"){
        if (!validateOtherInformation()) {
            otherValid = false;
             hideLoader();
        }

        if (!validateWages()) {
            wagesValid = false;
             hideLoader();
        }
    }else{
		otherValid = true;
		wagesValid = true;
		$("#uniformAllowance").val("0.00"); 
		$("#washingAllowance").val("0.00");  	
		$("#hra").val("0.00");  	
		$("#da").val("0.00"); 
		$("#basic").val("0.00"); 
		$("#otherAllowance").val("0.00"); 
	}
  
    console.log("basicValid: " + basicValid);
    console.log("employmentValid: " + employmentValid);
    console.log("otherValid: " + otherValid);
    console.log("wagesValid: " + wagesValid);
    console.log("documentValid: " + documentValid);

    // ✅ Utility function for Capital Case
    function toCapitalCase(str) {
        return str
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }

    // ✅ Capital case transformation
    const firstName = toCapitalCase($("#firstName").val().trim());
    const lastName = toCapitalCase($("#lastName").val().trim());
    const relationName = toCapitalCase($("#relationName").val().trim());
    const address = toCapitalCase($("#address").val().trim());
    const idMark = toCapitalCase($("#idMark").val().trim());
	let natureOfJob="";let emergencyName="";let pfApplicable="No";
	if(type=== "regular" || type==="quick"){
		 natureOfJob = toCapitalCase($("#natureOfJob").val().trim());
		    emergencyName = toCapitalCase($("#emergencyName").val().trim());
		    pfApplicable = $("#pfApplicable").is(":checked") ? "Yes" : "No";
		}
    if (basicValid && employmentValid && otherValid && wagesValid && documentValid) {
        const data = new FormData();
        const jsonData = {
			transactionId:trimIfPresent($("#transactionId").val()),
            aadhaarNumber: trimIfPresent($("#aadharNumber").val()),
            firstName: firstName,
            lastName: lastName,
            dateOfBirth:trimIfPresent( $("#dateOfBirth").val()),
            gender: $("#gender").val(),
            relationName: relationName,
            idMark: idMark,
            mobileNumber: trimIfPresent($("#mobileNumber").val()),
            maritalStatus: $("#maritalStatus").val(),
            principalEmployer: $("#principalEmployer").val(),
            contractor: $("#contractor").val(),
            workorder: $("#workorder").val(),
            trade: $("#trade").val(),
            skill: $("#skill").val(),
            department: $("#department").val(),
            subdepartment: $("#subdepartment").val(),
            eic: $("#eic").val(),
            natureOfJob: natureOfJob,
            wcEsicNo: $("#wc").val(),
			llNo:$("#ll").val(),
            hazardousArea: $("#hazardousArea").val(),
            accessArea: $("#accessArea").val(),
            uanNumber: trimIfPresent($("#uanNumber").val()),
            healthCheckDate: trimIfPresent($("#healthCheckDate").val()),
            pfNumber:trimIfPresent($("#pfNumber").val()),
			esicNumber:trimIfPresent($("#esicNumber").val()),
            bloodGroup: $("#bloodGroup").val(),
            accommodation: $("#accommodation").val(),
            academic: $("#academic").val(),
            technical: $("#technical").val(),
            ifscCode: trimIfPresent($("#ifscCode").val()),
            accountNumber: trimIfPresent($("#accountNumber").val()),
            emergencyName: emergencyName,
            emergencyNumber: trimIfPresent($("#emergencyNumber").val()),
            wageCategory: $("#wageCategory").val(),
            bonusPayout: $("#bonusPayout").val(),
            pfCap: $("#pfCap").val(),
            zone: $("#zone").val(),
            basic: trimIfPresent($("#basic").val()),
            da: trimIfPresent($("#da").val()),
            hra: trimIfPresent($("#hra").val()),
            washingAllowance: trimIfPresent($("#washingAllowance").val()),
            otherAllowance:trimIfPresent( $("#otherAllowance").val()),
            uniformAllowance: trimIfPresent($("#uniformAllowance").val()),
            userId: userId,
            gatePassAction: "save",
            comments: trimIfPresent($("#comments").val()),
			address: address,
			doj:$("#doj").val(),
			pfApplicable:pfApplicable,
            policeVerificationDate: trimIfPresent($("#policeVerificationDate").val()),
            disability:$("#disability").val(),
            workmenType:$("#workmenType").val(),
			onboardingType:type,
        };

        const jsonString = JSON.stringify(jsonData);
		data.append("jsonData", jsonString);

        if (aadharFile) data.append("aadharFile", aadharFile);
        if (policeFile) data.append("policeFile", policeFile);
		if(profilePic) data.append("profilePic",profilePic);
		if(appointmentFile) data.append("appointmentFile",appointmentFile);

    	const additionalFields = document.querySelectorAll('.document-field');
        additionalFields.forEach((field) => {
            const docType = field.querySelector('select[name="documentType"]').value;
            const fileInput = field.querySelector('input[type="file"]');
            if (docType && fileInput.files[0]) {
                data.append('additionalFiles', fileInput.files[0]);
                data.append('documentTypes', docType);
            }
        });

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/CWFM/contractworkmen/saveGatePass", true);

        xhr.onload = function () {
			hideLoader();
            if (xhr.status === 200) {
                console.log("Data saved successfully:", xhr.responseText);
				sessionStorage.setItem("successMessage", "Gatepass saved successfully!");
                if(type=== "regular"){
                    loadCommonList('/contractworkmen/list', 'On-Boarding List');
                    //hideLoader();
                }else if(type=== "quick"){
                    loadCommonList('/contractworkmen/quickOnboardingList', 'Quick Onboarding List');
                   // hideLoader();
                }else{
					loadCommonList('/contractworkmen/projectOnboardingList', 'Project Gatepass List');
					//hideLoader();
				}
            }else if (xhr.status === 400) {  
				       const msg = xhr.responseText.trim();
				       console.error("Server validation failed: " + msg);
					   showLicenseError(msg);
				       //alert(msg); // or show in UI better
				       //sessionStorage.setItem("errorMessage", msg);
					   return;
				   }
				   else {
				       console.error("Error saving data:", xhr.status, xhr.responseText);
				       sessionStorage.setItem("errorMessage", "Failed to save Gatepass!");
				   }
        };

        xhr.onerror = function () {
			//hideLoader();
            console.error("Request failed");
			sessionStorage.setItem("errorMessage", "Failed to save Gatepass!");
			 hideLoader();
        };

        xhr.send(data);
    } else {
        console.error("Validation failed for one or more fields.");
    }
}

function showLicenseError(msg) {
    const div = document.getElementById("licenseError");
    div.innerHTML = msg;
    div.style.display = "block";
}
function viewDoc(transactionId, userId, docType) {
    // Prepare data for secure encoding
    const data = { transactionId, userId, docType };

    // Encode as Base64 JSON (URL-safe)
    const encodedData = btoa(JSON.stringify(data));

    // Build masked endpoint URL
    const url = `/CWFM/contractworkmen/viewFile/${encodedData}`;

    // Fetch the file and open in a new tab
    fetch(url)
        .then(response => {
            if (!response.ok) throw new Error("File not found or server error");
            return response.blob();
        })
        .then(blob => {
            const blobUrl = window.URL.createObjectURL(blob);
            window.open(blobUrl, '_blank'); // ✅ Opens inline in a new tab
            setTimeout(() => URL.revokeObjectURL(blobUrl), 5000);
        })
        .catch(err => {
            console.error("Error opening file:", err);
            alert("Unable to open file.");
        });
}




//let divIndex = 0;

function additionalDocUpload() {
    const currentCount = $(".document-field").length;
    if (currentCount >= 7) {
        alert("You can add a maximum of 7 additional documents.");
        return;
    }

    divIndex++;

    const docOptions = [
        "Bank", "Id2", "Other", "Medical",
        "Education", "Training", "Form11"
    ];

    // Generate options dynamically while avoiding already selected ones
    const selectedValues = $(".document-field select").map(function () {
        return $(this).val();
    }).get();

    let optionsHtml = '<option value="">Select Document Type</option>';
    docOptions.forEach(function (opt) {
        if (!selectedValues.includes(opt)) {
            optionsHtml += '<option value="' + opt + '">' + opt + '</option>';
        }
    });

    // Unique IDs for the new row
    const fileNameId = 'fileName-' + divIndex;

    const newField = `
        <div class="document-field" id="document-field-${divIndex}" style="display: flex; align-items: center; margin-bottom: 10px;">
            <select name="documentType" onchange="updateDocTypeDropdowns()" style="margin-right: 10px;color:black;">
                ${optionsHtml}
            </select>
            <input type="file" accept="application/pdf" style="margin-right: 10px;" onchange="displayFileName(this, '${fileNameId}')">
            <span id="${fileNameId}" style="margin-right: 10px;color:black;"></span>
            <button type="button" onclick="removeDocument(${divIndex})" style="color:black;">Remove</button>
        </div>
    `;

    $("#additionalDoc").append(newField);
    updateDocTypeDropdowns(); // Refresh all dropdowns to disable already-selected options
}

function removeDocument(index) {
    $(`#document-field-${index}`).remove();
    updateDocTypeDropdowns(); // Update dropdowns after removal
}

function displayFileName(inputElement, displayId) {
    const displayElement = document.getElementById(displayId);
    if (inputElement.files.length > 0) {
        displayElement.textContent = inputElement.files[0].name;
    } else {
        displayElement.textContent = '';
    }
}

function updateDocTypeDropdowns() {
    const allOptions = [
        "Bank", "Id2", "Other", "Medical",
        "Education", "Training", "Form11"
    ];

    const selectedValues = $(".document-field select").map(function () {
        return $(this).val();
    }).get();

    $(".document-field select").each(function () {
        const currentSelect = $(this);
        const currentValue = currentSelect.val();

        currentSelect.empty().append('<option value="">Select Document Type</option>');

        allOptions.forEach(function (opt) {
            const isDisabled = selectedValues.includes(opt) && opt !== currentValue;
            const optionTag = $('<option>', {
                value: opt,
                text: opt,
                disabled: isDisabled,
                selected: opt === currentValue
            });
            currentSelect.append(optionTag);
        });
    });
}


function displayFileName1(inputId, displayId) {
    const fileInput = document.getElementById(inputId);
    const displayElement = document.getElementById(displayId);

    // Get the selected file's name
    if (fileInput.files.length > 0) {
        const fileName = fileInput.files[0].name;
        displayElement.textContent = fileName; // Display the file name
    } else {
        displayElement.textContent = ''; // Clear the display if no file is selected
    }
}

	function submitCancel(userId, gatePassType) {
showLoader();
    let isValid = true;

    const comments = $("#comments").val().trim();
    const reason = $("#reasonofOffboarding").val();

    // Validate comments
    if (comments === "") {
        $("#error-comments").show();
        isValid = false;
    } else {
        $("#error-comments").hide();
    }

    // Validate reasoning
    if (reason === "") {
        $("#error-reasonofOffboarding").show();
        alert("Reason of Offboarding Required in Reasoning Tab");
        hideLoader();
        isValid = false;
    } else {
        $("#error-reasonofOffboarding").hide();
    }

    if (!isValid) return;

    // Files
    var exitFile = $("#exitFile")[0].files[0];
    var fnfFile = $("#FNFFile")[0].files[0];
    var feedbackFile = $("#feedbackFormFile")[0].files[0];
    var rateManagerFile = $("#rateManagerFile")[0].files[0];
    var locFile = $("#LOCFile")[0].files[0];

    const formData = new FormData();

    // JSON Data
    const jsonData = {
        createdBy: userId,
        comments: comments,
        transactionId: $("#transactionId").val().trim(),
        gatePassId: $("#gatePassId").val().trim(),
        gatePassType: gatePassType,
        reasoning: reason
    };

    formData.append("jsonData", JSON.stringify(jsonData));

    if (exitFile) formData.append("exitFile", exitFile);
    if (fnfFile) formData.append("fnfFile", fnfFile);
    if (feedbackFile) formData.append("feedbackFile", feedbackFile);
    if (rateManagerFile) formData.append("rateManagerFile", rateManagerFile);
    if (locFile) formData.append("locFile", locFile);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true);

    // ❌ DO NOT SET Content-Type manually for FormData
    // xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
		showLoader();
        if (xhr.status === 200) {
            console.log("Cancel Saved:", xhr.responseText);
            sessionStorage.setItem("successMessage", "Gatepass cancel request raised successfully!");
            loadCommonList('/contractworkmen/cancelFilter', 'Cancel List');
        } else {
            console.error("Error:", xhr.responseText);
            sessionStorage.setItem("errorMessage", "Failed to raise Gatepass cancel request!");
        }
    };

    xhr.onerror = function () {
        console.error("Request failed");
        sessionStorage.setItem("errorMessage", "Request failed!");
        hideLoader();
    };

    xhr.send(formData);
}

function approveRejectCancel(status,gatePassType){
	showLoader();
	let isValid=true;
	 const approvercomments = $("#approvercomments").val().trim();
   if (approvercomments === "" && status==5) {
       $("#error-approvercomments").show();
       alert("Comments Required in Documents");
       isValid = false;
       hideLoader();
   }else{
	$("#error-approvercomments").hide();
}
if(isValid){
	const data = {
		approverId : $("#userId").val().trim(),
		comments : $("#approvercomments").val().trim(),
		status : status,
		transactionId : $("#transactionId").val().trim(),
		gatePassId : $("#gatePassId").val().trim(),
		approverRole : $("#roleName").val().trim(),
		roleId :$("#roleId").val().trim(),
		gatePassType : gatePassType,
	};
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
	   hideLoader();
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
		   sessionStorage.setItem("successMessage", "Gatepass cancel request approved/rejected successfully!");
         loadCommonList('/contractworkmen/cancelFilter', 'Cancel List');
         //hideLoader();
       } else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
		   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass cancel request!");
		   //hideLoader();
       }
   };
   
   xhr.onerror = function() {
	  // hideLoader();
       console.error("Request failed");
	   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass cancel request!");
	    hideLoader();
   };
   
   // Send the data object as a JSON string
   xhr.send(JSON.stringify(data));
	}else{
		//error 
	}
	}//eofunc
	
		function submitBlock(userId, gatePassType) {
showLoader();
    let isValid = true;

    const comments = $("#comments").val().trim();
    const reason = $("#reasonofOffboarding").val();

    if (comments === "") {
        $("#error-comments").show();
        isValid = false;
    } else {
        $("#error-comments").hide();
    }

    if (reason === "") {
        $("#error-reasonofOffboarding").show();
        alert("Reason of Offboarding Required in Reasoning Tab");
        hideLoader();
        isValid = false;
    } else {
        $("#error-reasonofOffboarding").hide();
    }

    if (!isValid) return;

    // Files
    var exitFile = $("#exitFile")[0].files[0];
    var fnfFile = $("#FNFFile")[0].files[0];
    var feedbackFile = $("#feedbackFormFile")[0].files[0];
    var rateManagerFile = $("#rateManagerFile")[0].files[0];
    var locFile = $("#LOCFile")[0].files[0];

    const formData = new FormData();

    // JSON Data
    const jsonData = {
        createdBy: userId,
        comments: comments,
        transactionId: $("#transactionId").val().trim(),
        gatePassId: $("#gatePassId").val().trim(),
        gatePassType: gatePassType,
        reasoning: reason
    };

    formData.append("jsonData", JSON.stringify(jsonData));

    if (exitFile) formData.append("exitFile", exitFile);
    if (fnfFile) formData.append("fnfFile", fnfFile);
    if (feedbackFile) formData.append("feedbackFile", feedbackFile);
    if (rateManagerFile) formData.append("rateManagerFile", rateManagerFile);
    if (locFile) formData.append("locFile", locFile);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true);

    // ❌ DO NOT SET content-type manually
    // xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
		showLoader();
        if (xhr.status === 200) {
            console.log("Saved:", xhr.responseText);
            sessionStorage.setItem("successMessage", "Gatepass block request raised successfully!");
            loadCommonList('/contractworkmen/blockListFilter', 'Block List');
        } else {
            console.error("Error:", xhr.responseText);
            sessionStorage.setItem("errorMessage", "Failed to raise Gatepass block request!");
        }
    };

    xhr.onerror = function () {
        console.error("Request failed");
        sessionStorage.setItem("errorMessage", "Request failed!");
        hideLoader();
    };

    xhr.send(formData);
}

	function approveRejectBlock(status,gatePassType){
		showLoader();
		let isValid=true;
		 const approvercomments = $("#approvercomments").val().trim();
	   if (approvercomments === "" && status==5 ) {
	       $("#error-approvercomments").show();
	       alert("Comments Required in Documents");
	       isValid = false;
	       hideLoader();
	   }else{
		$("#error-approvercomments").hide();
	}
	if(isValid){
		const data = {
			approverId : $("#userId").val().trim(),
			comments : $("#approvercomments").val().trim(),
			status : status,
			transactionId : $("#transactionId").val().trim(),
			gatePassId : $("#gatePassId").val().trim(),
			approverRole : $("#roleName").val().trim(),
			roleId :$("#roleId").val().trim(),
			gatePassType : gatePassType,
		};
			  const xhr = new XMLHttpRequest();
	   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
	   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
	   xhr.onload = function() {
		   hideLoader();
	       if (xhr.status === 200) {
	           // Handle successful response
	           console.log("Data saved successfully:", xhr.responseText);
			   sessionStorage.setItem("successMessage", "Gatepass block request approved/rejected successfully!");
	         loadCommonList('/contractworkmen/blockListFilter', 'Block List');
	         //hideLoader();
	       } else {
	           // Handle error response
	           console.error("Error saving data:", xhr.statusText);
			   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass block request!");
			  // hideLoader();
	       }
	   };
	   
	   xhr.onerror = function() {
	       console.error("Request failed");
	       hideLoader();
	   };
	   
	   // Send the data object as a JSON string
	   xhr.send(JSON.stringify(data));
		}else{
			//error 
		}
		}//eofunc
		
				function submitUnblock(userId, gatePassType) {
showLoader();
    let isValid = true;

    const comments = $("#comments").val().trim();
    const reason = $("#reasonofOffboarding").val();

    // Validate comments
    if (comments === "") {
        $("#error-comments").show();
        isValid = false;
    } else {
        $("#error-comments").hide();
    }

    // Validate reason
    if (reason === "") {
        $("#error-reasonofOffboarding").show();
        alert("Reason of Offboarding Required in Reasoning Tab");
        hideLoader();
        isValid = false;
    } else {
        $("#error-reasonofOffboarding").hide();
    }

    if (!isValid) return;

    // Files
    var exitFile = $("#exitFile")[0].files[0];
    var fnfFile = $("#FNFFile")[0].files[0];
    var feedbackFile = $("#feedbackFormFile")[0].files[0];
    var rateManagerFile = $("#rateManagerFile")[0].files[0];
    var locFile = $("#LOCFile")[0].files[0];

    const formData = new FormData();

    // JSON Payload
    const jsonData = {
        createdBy: userId,
        comments: comments,
        transactionId: $("#transactionId").val().trim(),
        gatePassId: $("#gatePassId").val().trim(),
        gatePassType: gatePassType,
        reasoning: reason
    };

    formData.append("jsonData", JSON.stringify(jsonData));

    if (exitFile) formData.append("exitFile", exitFile);
    if (fnfFile) formData.append("fnfFile", fnfFile);
    if (feedbackFile) formData.append("feedbackFile", feedbackFile);
    if (rateManagerFile) formData.append("rateManagerFile", rateManagerFile);
    if (locFile) formData.append("locFile", locFile);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true);

    // ❌ DO NOT set Content-Type manually for FormData

    xhr.onload = function () {
		showLoader();
        if (xhr.status === 200) {
            console.log("Unblock saved:", xhr.responseText);
            sessionStorage.setItem("successMessage", "Gatepass unblock request raised successfully!");
            loadCommonList('/contractworkmen/unblockListFilter', 'Unblock List');
        } else {
            console.error("Error:", xhr.responseText);
            sessionStorage.setItem("errorMessage", "Failed to raise Gatepass unblock request!");
        }
    };

    xhr.onerror = function () {
        console.error("Request failed");
        sessionStorage.setItem("errorMessage", "Request failed!");
        hideLoader();
    };

    xhr.send(formData);
}

		function approveRejectUnblock(status,gatePassType){
			showLoader();
			let isValid=true;
			 const approvercomments = $("#approvercomments").val().trim();
		   if (approvercomments === "" && status==5) {
		       $("#error-approvercomments").show();
		       alert("Comments Required in Documents");
		       isValid = false;
		       hideLoader();
		   }else{
			$("#error-approvercomments").hide();
		}
		if(isValid){
			const data = {
				approverId : $("#userId").val().trim(),
				comments : $("#approvercomments").val().trim(),
				status : status,
				transactionId : $("#transactionId").val().trim(),
				gatePassId : $("#gatePassId").val().trim(),
				approverRole : $("#roleName").val().trim(),
				roleId :$("#roleId").val().trim(),
				gatePassType : gatePassType,
			};
				  const xhr = new XMLHttpRequest();
		   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
		   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
		   xhr.onload = function() {
			   hideLoader();
		       if (xhr.status === 200) {
		           // Handle successful response
		           console.log("Data saved successfully:", xhr.responseText);
				   sessionStorage.setItem("successMessage", "Gatepass unblock approved/rejected successfully!");
		         loadCommonList('/contractworkmen/unblockListFilter', 'Unblock List');
		         //hideLoader();
		       } else {
		           // Handle error response
		           console.error("Error saving data:", xhr.statusText);
				   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass unblock request!");
				   //hideLoader();
		       }
		   };
		   
		   xhr.onerror = function() {
			  // hideLoader();
		       console.error("Request failed");
			   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass unblock request!");
			   hideLoader();
		   };
		   
		   // Send the data object as a JSON string
		   xhr.send(JSON.stringify(data));
			}else{
				//error 
			}
			}//eofunc
			
	function submitBlack(userId, gatePassType) {
showLoader();
    let isValid = true;

    const comments = $("#comments").val().trim();
    const reason = $("#reasonofOffboarding").val();

    // Validation
    if (comments === "") {
        $("#error-comments").show();
        isValid = false;
    } else {
        $("#error-comments").hide();
    }

    if (reason === "") {
        $("#error-reasonofOffboarding").show();
        alert("Reason of Offboarding Required in Reasoning Tab");
        hideLoader();
        isValid = false;
    } else {
        $("#error-reasonofOffboarding").hide();
    }

    if (!isValid) return;

    // Files
    var exitFile = $("#exitFile")[0]?.files[0];
    var fnfFile = $("#FNFFile")[0]?.files[0];
    var feedbackFile = $("#feedbackFormFile")[0]?.files[0];
    var rateManagerFile = $("#rateManagerFile")[0]?.files[0];
    var locFile = $("#LOCFile")[0]?.files[0];

    const formData = new FormData();

    // JSON Data
    const jsonData = {
        createdBy: userId,
        comments: comments,
        transactionId: $("#transactionId").val().trim(),
        gatePassId: $("#gatePassId").val().trim(),
        gatePassType: gatePassType,
        reasoning: reason
    };

    formData.append("jsonData", JSON.stringify(jsonData));

    if (exitFile) formData.append("exitFile", exitFile);
    if (fnfFile) formData.append("fnfFile", fnfFile);
    if (feedbackFile) formData.append("feedbackFile", feedbackFile);
    if (rateManagerFile) formData.append("rateManagerFile", rateManagerFile);
    if (locFile) formData.append("locFile", locFile);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true);

    // ❌ DO NOT SET CONTENT TYPE manually
    // xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
		showLoader();
        if (xhr.status === 200) {
            console.log("Saved:", xhr.responseText);
            sessionStorage.setItem("successMessage", "Gatepass blacklist request raised successfully!");
            loadCommonList('/contractworkmen/blackListFilter', 'Black List');
        } else {
            console.error("Error:", xhr.responseText);
            sessionStorage.setItem("errorMessage", "Failed to raise Gatepass blacklist request!");
        }
    };

    xhr.onerror = function () {
        console.error("Request failed");
        sessionStorage.setItem("errorMessage", "Request failed!");
        hideLoader();
    };

    xhr.send(formData);
}

			function approveRejectBlack(status,gatePassType){
				showLoader();
let isValid=true;
 const approvercomments = $("#approvercomments").val().trim();
					   if (approvercomments === "" && status==5 ) {
					       $("#error-approvercomments").show();
					       alert("Comments Required in Documents");
					       isValid = false;
					       hideLoader();
					   }else{
$("#error-approvercomments").hide();
					}
					if(isValid){
const data = {
	approverId : $("#userId").val().trim(),
	comments : $("#approvercomments").val().trim(),
	status : status,
	transactionId : $("#transactionId").val().trim(),
	gatePassId : $("#gatePassId").val().trim(),
	approverRole : $("#roleName").val().trim(),
	roleId:$("#roleId").val().trim(),
	gatePassType : gatePassType,
};
	  const xhr = new XMLHttpRequest();
					   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
					   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
					   xhr.onload = function() {
						   hideLoader();
					       if (xhr.status === 200) {
					           // Handle successful response
					           console.log("Data saved successfully:", xhr.responseText);
							   sessionStorage.setItem("successMessage", "Gatepass blacklist request approved/rejected successfully!");
					         loadCommonList('/contractworkmen/blackListFilter', 'Black List');
					         //hideLoader();
					       } else {
					           // Handle error response
					           console.error("Error saving data:", xhr.statusText);
					       }
					   };
					   
					   xhr.onerror = function() {
						   //hideLoader();
					       console.error("Request failed");
						   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass blacklist request!");
						   hideLoader();
					   };
					   
					   // Send the data object as a JSON string
					   xhr.send(JSON.stringify(data));
					   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass blacklist request!");
					   //hideLoader();
}else{
	//error 
}
}//eofunc

				function submitDeblack(userId, gatePassType) {
showLoader();
    let isValid = true;

    const comments = $("#comments").val().trim();
    const reason = $("#reasonofOffboarding").val();

    // Validate comments
    if (comments === "") {
        $("#error-comments").show();
        isValid = false;
    } else {
        $("#error-comments").hide();
    }

    // Validate reasoning
    if (reason === "") {
        $("#error-reasonofOffboarding").show();
        alert("Reason of Offboarding Required in Reasoning Tab");
        hideLoader();
        isValid = false;
    } else {
        $("#error-reasonofOffboarding").hide();
    }

    if (!isValid) return;

    // Files
    var exitFile = $("#exitFile")[0].files[0];
    var fnfFile = $("#FNFFile")[0].files[0];
    var feedbackFile = $("#feedbackFormFile")[0].files[0];
    var rateManagerFile = $("#rateManagerFile")[0].files[0];
    var locFile = $("#LOCFile")[0].files[0];

    const formData = new FormData();

    // JSON Payload
    const jsonData = {
        createdBy: userId,
        comments: comments,
        transactionId: $("#transactionId").val().trim(),
        gatePassId: $("#gatePassId").val().trim(),
        gatePassType: gatePassType,
        reasoning: reason
    };

    formData.append("jsonData", JSON.stringify(jsonData));

    if (exitFile) formData.append("exitFile", exitFile);
    if (fnfFile) formData.append("fnfFile", fnfFile);
    if (feedbackFile) formData.append("feedbackFile", feedbackFile);
    if (rateManagerFile) formData.append("rateManagerFile", rateManagerFile);
    if (locFile) formData.append("locFile", locFile);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true);

    // ❌ DO NOT SET Content-Type manually for FormData
    // xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
		showLoader();
        if (xhr.status === 200) {
            console.log("Deblack saved:", xhr.responseText);
            sessionStorage.setItem("successMessage", "Gatepass deblacklist request raised successfully!");
            loadCommonList('/contractworkmen/deblackListFilter', 'Deblack List');
        } else {
            console.error("Error:", xhr.responseText);
            sessionStorage.setItem("errorMessage", "Failed to raise Gatepass deblacklist request!");
        }
    };

    xhr.onerror = function () {
        console.error("Request failed");
        sessionStorage.setItem("errorMessage", "Request failed!");
        hideLoader();
    };

    xhr.send(formData);
}

function approveRejectDeblacklist(status,gatePassType){
	showLoader();
			let isValid=true;
			 const approvercomments = $("#approvercomments").val().trim();
		if (approvercomments === "" && status==5) {
		    $("#error-approvercomments").show();
		    alert("Comments Required in Documents");
		    isValid = false;
		    hideLoader();
		}else{
			$("#error-approvercomments").hide();
		}
		if(isValid){
			const data = {
				approverId : $("#userId").val().trim(),
				comments : $("#approvercomments").val().trim(),
				status : status,
				transactionId : $("#transactionId").val().trim(),
				gatePassId : $("#gatePassId").val().trim(),
				approverRole : $("#roleName").val().trim(),
				roleId :$("#roleId").val().trim(),
				gatePassType : gatePassType,
			};
				  const xhr = new XMLHttpRequest();
		xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
		xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
		xhr.onload = function() {
			hideLoader();
		    if (xhr.status === 200) {
		        // Handle successful response
		        console.log("Data saved successfully:", xhr.responseText);
				sessionStorage.setItem("successMessage", "Gatepass deblacklist request approved/rejected successfully!");
		      loadCommonList('/contractworkmen/deblackListFilter', 'Deblack List');
		      //hideLoader();
		    } else {
		        // Handle error response
		        console.error("Error saving data:", xhr.statusText);
				sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass deblacklist request!");
				//hideLoader();
		    }
		};
		
		xhr.onerror = function() {
			//hideLoader();
		    console.error("Request failed");
			sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass deblacklist request!");
			 hideLoader();
		};
		
		// Send the data object as a JSON string
		xhr.send(JSON.stringify(data));
			}else{
				//error 
			}
			}//eofunc
			function submitLostOrDamage(userId,gatePassType){
				showLoader();
let isValid=true;
 const comments = $("#comments").val().trim();
if (comments === "") {
    $("#error-comments").show();
    isValid = false;
}else{
$("#error-comments").hide();
}
if(isValid){
const data = {
	createdBy : userId,
	comments : $("#comments").val().trim(),
	transactionId : $("#transactionId").val().trim(),
	gatePassId : $("#gatePassId").val().trim(),
	gatePassType : gatePassType,
};
	  const xhr = new XMLHttpRequest();
xhr.open("POST", "/CWFM/contractworkmen/gatePassAction", true); // Replace with your actual controller URL
xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
xhr.onload = function() {
	hideLoader();
    if (xhr.status === 200) {
        // Handle successful response
        console.log("Data saved successfully:", xhr.responseText);
		sessionStorage.setItem("successMessage", "Gatepass lost or damage request raised successfully!");
	   loadCommonList('/contractworkmen/lostordamageFilter', 'Lost or Damage List');
	   //hideLoader();
    } else {
        // Handle error response
        console.error("Error saving data:", xhr.statusText);
		sessionStorage.setItem("errorMessage", "Failed to raise Gatepass lost or damage request!");
		//hideLoader();
    }
};

xhr.onerror = function() {
	//hideLoader();
    console.error("Request failed");
	sessionStorage.setItem("errorMessage", "Failed to raise Gatepass lost or damage request!");
	 hideLoader();
};

// Send the data object as a JSON string
xhr.send(JSON.stringify(data));
}else{
	//error 
}
}//eofunc
function redirectToWorkmenCancelView(mode) {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
	var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
	if(mode === "add"){
	 												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed

	 												    if (gatePassType.toLowerCase() === "cancel" && (status.toLowerCase() === "approved" || status.toLowerCase() === "approval pending")) {
                                                       alert("Cancel request already created.");
                                                      return;
                                                   }}


    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/cancelview/" + gatePassId+"/"+mode, true);
    xhr.send();
}
function redirectToWorkmenBlockView(mode) {
 var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
 if (selectedCheckboxes.length !== 1) {
     alert("Please select exactly one row to view.");
     return;
 }
 
 var selectedRow = selectedCheckboxes[0].closest('tr');
 var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
 var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
 												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
												   if(mode === "add"){
 												   if (gatePassType.toLowerCase() === "block" && (status.toLowerCase() === "approved" || status.toLowerCase() === "approval pending")) {
                                                       alert("Block request already created.");
                                                      return;
                                                   }

												   }
 var xhr = new XMLHttpRequest();
 xhr.onreadystatechange = function() {
     if (xhr.readyState == 4 && xhr.status == 200) {
         document.getElementById("mainContent").innerHTML = xhr.responseText;
     }
 };
 xhr.open("GET", "/CWFM/contractworkmen/blockview/" + gatePassId+ "/" + mode, true);
 xhr.send();
 }
 function redirectToWorkmenUnblockView(mode) {
  var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
  if (selectedCheckboxes.length !== 1) {
      alert("Please select exactly one row to view.");
      return;
  }
  
  var selectedRow = selectedCheckboxes[0].closest('tr');
  var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
  var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
  												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
												   if(mode === "add"){
  												    if (gatePassType.toLowerCase() === "unblock" && (status.toLowerCase() === "approval pending" || status.toLowerCase() === "approval pending")) {
                                                       alert("UnBlock request already created.");
                                                      return;
                                                   }}
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
      if (xhr.readyState == 4 && xhr.status == 200) {
          document.getElementById("mainContent").innerHTML = xhr.responseText;
      }
  };
  xhr.open("GET", "/CWFM/contractworkmen/unblockview/" + gatePassId+"/"+mode, true);
  xhr.send();
  }
  function redirectToWorkmenBlackView(mode) {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
	var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
													   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
													   if(mode === "add"){
													    if (gatePassType.toLowerCase() === "blacklist" && (status.toLowerCase() === "approved" || status.toLowerCase() === "approval pending")) {
                                                       alert("Blacklist request already created.");
                                                      return;
                                                   }}
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/blackview/" + gatePassId+ "/" + mode, true);
    xhr.send();
    }
	function redirectToWorkmenDeblackView(mode) {
	  var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	  if (selectedCheckboxes.length !== 1) {
	      alert("Please select exactly one row to view.");
	      return;
	  }
	  
	  var selectedRow = selectedCheckboxes[0].closest('tr');
	  var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
	  var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
	  												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
													   if(mode === "add"){
	  												    if (gatePassType.toLowerCase() === "deblacklist" && (status.toLowerCase() === "approved" || status.toLowerCase() === "approval pending")) {
                                                       alert("DeBlack request already created.");
                                                      return;
                                                   }}
	  var xhr = new XMLHttpRequest();
	  xhr.onreadystatechange = function() {
	      if (xhr.readyState == 4 && xhr.status == 200) {
	          document.getElementById("mainContent").innerHTML = xhr.responseText;
	      }
	  };
	  xhr.open("GET", "/CWFM/contractworkmen/deblackview/" + gatePassId+"/"+mode, true);
	  xhr.send();
	  }
	  function redirectToWorkmenLostView(mode) {
	    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	    if (selectedCheckboxes.length !== 1) {
	        alert("Please select exactly one row to view.");
	        return;
	    }
	    
	    var selectedRow = selectedCheckboxes[0].closest('tr');
	    var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
		var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
														   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed
														   if(mode === "add"){
														   if (gatePassType.toLowerCase() === "lost/damage" && (status.toLowerCase() === "approved" || status.toLowerCase() === "approval pending")) {
                                                       alert("Lost/Damage request already created.");
                                                      return;
                                                   }}
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/contractworkmen/lostordamageview/" + gatePassId+"/"+mode, true);
	    xhr.send();
	    }	
function searchWorkmenWithGatePassId(){
 var gatePassId = $('#searchInput').val();

 $.ajax({
     url: '/CWFM/contractworkmen/getWorkmenDetailBasedOnId',
     type: 'POST',
     data: {
         gatePassId: gatePassId
     },
     success: function(response) {
         var tableBody = $('#workmenTable tbody');
         tableBody.empty();
			if (response.length > 0) {
             $.each(response, function(index, wo) {
                 var row = '<tr  >' +
	'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
                           '<td  >' + wo.gatePassId + '</td>' +
                           '<td  >' + wo.firstName + '</td>' +
	  '<td  >'+ wo.lastName + '</td>' +	
	  '<td  >' + wo.gender + '</td>' +	
	  '<td  >' + wo.dateOfBirth + '</td>' +	
	  '<td  >' + wo.aadhaarNumber + '</td>' +	
	  '<td  >' + wo.contractorName + '</td>' +	
	  '<td  >' + wo.vendorCode + '</td>' +	
	  '<td  >' + wo.unitName + '</td>' +	
	  '<td  >' + wo.gatePassType + '</td>' +	
	  '<td  >' + wo.status + '</td>' +				                             
                           '</tr>';
                 tableBody.append(row);
             });
         } else {
             tableBody.append('<tr><td colspan="3">No resources found</td></tr>');
         }
     },
     error: function(xhr, status, error) {
         console.error("Error fetching data:", error);
     }
 });
}	


function previewImage(event, inputId, displayId) {
    const fileInput = document.getElementById(inputId);
    const displayElement = document.getElementById(displayId);

    // Display the file name
    if (fileInput.files.length > 0) {
        const fileName = fileInput.files[0].name;
        displayElement.textContent = fileName;
    } else {
        displayElement.textContent = '';
    }

    // Preview image from file
    const file = event.target.files[0];
    if (file && file.type.startsWith("image/")) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const previewDiv = document.getElementById("preview");
            previewDiv.innerHTML = ""; // Clear previous content
            previewDiv.innerHTML = `<img src="${e.target.result}" alt="Image Preview" style="max-width: 100%; max-height: 100%;">`;
        };
        reader.readAsDataURL(file);
    }
}

        
        function exportCSVFormat() {
            var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
            console.log("selectedRows.length"+selectedRows.length);
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "GatePass Id,First Name,Last Name,Gender,Date of Birth,Aadhar Number,Contractor Name,Vendor Code,Unit Name,GatePass Type,Status\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6),td:nth-child(7),td:nth-child(8),td:nth-child(9),td:nth-child(10),td:nth-child(11),td:nth-child(12)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "QuickOnboarding.csv");
            document.body.appendChild(link);
            link.click();
        }
		function searchGatePassBasedOnPE(type) {
					    var principalEmployerId = $('#principalEmployerId').val();
					    
						var deptId=$("#deptId").val();
					    $.ajax({
					        url: '/CWFM/contractworkmen/quickOBList',
					        type: 'POST',
					        data: {
					            principalEmployerId: principalEmployerId,
								deptId:deptId,
								type:type
					        },
					        success: function(response) {
					            var tableBody = $('#workmenTable tbody');
								// 🔄 Clear previous DataTable and its config
								           if ($.fn.DataTable.isDataTable('#workmenTable')) {
								               $('#workmenTable').DataTable().destroy();
								           }
										   tableBody.empty();
					            if (Array.isArray(response) &&response.length > 0) {
					                $.each(response, function(index, wo) {
					                    var row = '<tr  >' +
												'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
												'<td  >' + wo.transactionId + '</td>' +
												 '<td  >' + wo.gatePassId + '</td>' +
					                              '<td  >' + wo.firstName+' ' +wo.lastName + '</td>' +
												 
												  
												  
												  '<td  >' + wo.aadhaarNumber + '</td>' +	
												  '<td  >' + wo.contractorName + '</td>' +	
												 
												  '<td  >' +wo.unitName + '</td>' +	
												  '<td  >' + wo.gatePassType + '</td>' +
												  '<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +
												  '<td  >' + wo.status + '</td>' +				                             
					                              '</tr>';
					                    tableBody.append(row);
					                });
									
					            } 								

																	            // ✅ Always init after rows are drawn
																	            initWorkmenTable("workmenTable");
																	        },
					       
					        error: function(xhr, status, error) {
					            console.error("Error fetching data:", error);
					        }
					    });
					}
					function searchBlockList() {
										    var principalEmployerId = $('#principalEmployerId').val();
										    
											var deptId=$("#deptId").val();
										    $.ajax({
										        url: '/CWFM/contractworkmen/blockList',
										        type: 'POST',
										        data: {
										            principalEmployerId: principalEmployerId,
													deptId:deptId
										        },
										        success: function(response) {
										            var tableBody = $('#workmenTable tbody');
													// 🔄 Clear previous DataTable and its config
																					           if ($.fn.DataTable.isDataTable('#workmenTable')) {
																					               $('#workmenTable').DataTable().destroy();
																					           }
										            tableBody.empty();
										            if (Array.isArray(response) &&response.length > 0) {
										                $.each(response, function(index, wo) {
										                    var row = '<tr  >' +
'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '" class="bulk-check"  data-transaction="'+wo.transactionId+'"	data-gatepass="'+wo.gatePassId+'"  data-type="'+wo.gatePassTypeId+'"></td>'+
																	'<td  >' + wo.transactionId + '</td>' +
										                              '<td  >' + wo.gatePassId + '</td>' +
										                              '<td  >' + wo.firstName + ' '+wo.lastName +'</td>' +
																	  
																	  
																	  
																	  '<td  >' + wo.aadhaarNumber + '</td>' +	
																	  '<td  >' + wo.contractorName + '</td>' +	
																	
																	  '<td  >' +wo.unitName + '</td>' +	
																	  '<td  >' + wo.gatePassType + '</td>' +
																	  '<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +	
																	  '<td  >' + wo.status + '</td>' +				                             
										                              '</tr>';
										                    tableBody.append(row);
										                });
										            }
													// ✅ Always init after rows are drawn
													 initWorkmenTable("workmenTable");
													 
										        },
										        error: function(xhr, status, error) {
										            console.error("Error fetching data:", error);
										        }
										    });
										}
										function searchUnBlockList() {
										    var principalEmployerId = $('#principalEmployerId').val();
										    var deptId = $("#deptId").val();

										    $.ajax({
										        url: '/CWFM/contractworkmen/unblockList',
										        type: 'POST',
										        data: {
										            principalEmployerId: principalEmployerId,
										            deptId: deptId
										        },
										        success: function(response) {
										            var tableBody = $('#workmenTable tbody');

										            if ($.fn.DataTable.isDataTable('#workmenTable')) {
										                $('#workmenTable').DataTable().destroy();
										            }

										            tableBody.empty();

										            if (Array.isArray(response) && response.length > 0) {
										                $.each(response, function(index, wo) {
										                    var row = '<tr>' +
										                        '<td><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>' +
										                        '<td>' + wo.transactionId + '</td>' +
										                        '<td>' + wo.gatePassId + '</td>' +
										                        '<td>' + wo.firstName + ' ' + wo.lastName + '</td>' +
										                        '<td>' + wo.aadhaarNumber + '</td>' +
										                        '<td>' + wo.contractorName + '</td>' +
										                        '<td>' + wo.unitName + '</td>' +
										                        '<td>' + wo.gatePassType + '</td>' +
										                        '<td>' + toCapitalCase(wo.onboardingType) + '</td>' +
										                        '<td>' + wo.status + '</td>' +
										                        '</tr>';
										                    tableBody.append(row);
										                });
										            } 

										            // ✅ Always init after rows are drawn
										            initWorkmenTable("workmenTable");
										        },
										        error: function(xhr, status, error) {
										            console.error("Error fetching data:", error);
										        }
										    });
										}




										function searchBlackList() {
										var principalEmployerId = $('#principalEmployerId').val();
										var deptId=$("#deptId").val();
										$.ajax({
										    url: '/CWFM/contractworkmen/blackList',
										    type: 'POST',
										    data: {
										        principalEmployerId: principalEmployerId,
												deptId:deptId
										    },
										    success: function(response) {
										        var tableBody = $('#workmenTable tbody');
												if ($.fn.DataTable.isDataTable('#workmenTable')) {
																			               $('#workmenTable').DataTable().destroy();
																			           }
										        tableBody.empty();
										        if (Array.isArray(response) &&response.length > 0) {
										            $.each(response, function(index, wo) {
										                var row = '<tr  >' +
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
																											'<td  >' + wo.transactionId + '</td>' +
																											'<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + ' '+ wo.lastName +'</td>' +
																
																											  
															
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
																	
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +
																'<td  >' + wo.status + '</td>' +				                             
										                          '</tr>';
										                tableBody.append(row);
										            });
										        }
												// ✅ Always init after rows are drawn
												 initWorkmenTable("workmenTable");
												 },
										    error: function(xhr, status, error) {
										        console.error("Error fetching data:", error);
										    }
										});
										}
										
										function searchDeBlackList() {
										var principalEmployerId = $('#principalEmployerId').val();
										var deptId=$("#deptId").val();
										$.ajax({
										    url: '/CWFM/contractworkmen/deblackList',
										    type: 'POST',
										    data: {
										        principalEmployerId: principalEmployerId,
												deptId:deptId
										    },
										    success: function(response) {
										        var tableBody = $('#workmenTable tbody');
												if ($.fn.DataTable.isDataTable('#workmenTable')) {
													 $('#workmenTable').DataTable().destroy();
												}
										        tableBody.empty();
										        if (Array.isArray(response) &&response.length > 0) {
										            $.each(response, function(index, wo) {
										                var row = '<tr  >' +
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
																											'<td  >' + wo.transactionId + '</td>' +
																											'<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + ' '  + wo.lastName + '</td>' +
																
																											  
																
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
																 	
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +
																'<td  >' + wo.status + '</td>' +				                             
										                          '</tr>';
										                tableBody.append(row);
										            });
										        } 
												// ✅ Always init after rows are drawn
												 initWorkmenTable("workmenTable");
												 },
										    error: function(xhr, status, error) {
										        console.error("Error fetching data:", error);
										    }
										});
										}
										function searchCancel() {
										var principalEmployerId = $('#principalEmployerId').val();
										var deptId=$("#deptId").val();
										$.ajax({
										    url: '/CWFM/contractworkmen/cancel',
										    type: 'POST',
										    data: {
										        principalEmployerId: principalEmployerId,
												deptId:deptId
										    },
										    success: function(response) {
										        var tableBody = $('#workmenTable tbody');
												if ($.fn.DataTable.isDataTable('#workmenTable')) {
													 $('#workmenTable').DataTable().destroy();
												}
										        tableBody.empty();
										        if (Array.isArray(response) &&response.length > 0) {
										            $.each(response, function(index, wo) {
										                var row = '<tr  >' +
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
																											'<td  >' + wo.transactionId + '</td>' +
																											'<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName + ' '+ wo.lastName +'</td>' +
																
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
														
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +
																'<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +	
																'<td  >' + wo.status + '</td>' +				                             
										                          '</tr>';
										                tableBody.append(row);
										            });
										        }
												// ✅ Always init after rows are drawn
												 initWorkmenTable("workmenTable");
												 },
										    error: function(xhr, status, error) {
										        console.error("Error fetching data:", error);
										    }
										});
										}
										function searchLost() {
										var principalEmployerId = $('#principalEmployerId').val();
										var deptId=$("#deptId").val();
										$.ajax({
										    url: '/CWFM/contractworkmen/lostordamage',
										    type: 'POST',
										    data: {
										        principalEmployerId: principalEmployerId,
												deptId:deptId
										    },
										    success: function(response) {
										        var tableBody = $('#workmenTable tbody');
												if ($.fn.DataTable.isDataTable('#workmenTable')) {
													 $('#workmenTable').DataTable().destroy();
												}
										        tableBody.empty();
										        if (Array.isArray(response) &&response.length > 0) {
										            $.each(response, function(index, wo) {
										                var row = '<tr  >' +
																											'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
																											'<td  >' + wo.transactionId + '</td>' +
																											'<td  >' + wo.gatePassId + '</td>' +
										                          '<td  >' + wo.firstName +' '+ wo.lastName + '</td>' +
																
																'<td  >' + wo.aadhaarNumber + '</td>' +	
																'<td  >' + wo.contractorName + '</td>' +	
															
																'<td  >' +wo.unitName + '</td>' +	
																'<td  >' + wo.gatePassType + '</td>' +	
																'<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +
																'<td  >' + wo.status + '</td>' +				                             
										                          '</tr>';
										                tableBody.append(row);
										            });
										        } 
												// ✅ Always init after rows are drawn
												 initWorkmenTable("workmenTable");
												 },
										    error: function(xhr, status, error) {
										        console.error("Error fetching data:", error);
										    }
										});
										}
										
										function getEic() {
											 var principalEmployerSelect = document.getElementById("principalEmployer");
										    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
										    var deptSelect = document.getElementById("department");
										    var deptId = deptSelect.value; // Get the selected contractor value
										    
										 if (!unitId) {
										        alert("Please select a Principal Employer.");
										        return;
										    }
										    
										  if(!deptId){
											 alert("Please select a Department.");
										        return;
										  }

										    getEicList(unitId, deptId);

										    
										}
										function getEicList(unitId,deptId) {
										    var xhr = new XMLHttpRequest();
										    var url = contextPath + "/contractworkmen/getAllEic?unitId=" + unitId + "&deptId=" + deptId;
										    //alert("URL: " + url);
										    xhr.open("GET", url, true);

										    xhr.onload = function() {
										        if (xhr.status === 200) {
										            // Parse the response as a JSON array of workorder objects
										            var eics = JSON.parse(xhr.responseText);
										            console.log("Response:", eics);
										            
										            // Find the workorder select element
										            var eicSelect = document.getElementById("eic");
										            
										            // Clear existing options
										            eicSelect.innerHTML = '<option value="">Please select EIC</option>';
										            
										            // Populate the dropdown with the new list of workorders
										            eics.forEach(function(eiclist) {
										                var option = document.createElement("option");
										                option.value = eiclist.userId;
										                option.text = eiclist.fullName;
										                eicSelect.appendChild(option);
										            });
										        } else {
										            console.error("Error:", xhr.statusText);
										        }
										    };

										    xhr.onerror = function() {
										        console.error("Request failed");
										    };

										    xhr.send();
										}
								function draftGatePass(userId) {
									showLoader();
											    // ✅ Utility function for Capital Case
                                                   function toCapitalCase(str) {
                                                     return str
                                                    .toLowerCase()
                                                    .split(' ')
                                                    .map(word => word.charAt(0).toUpperCase() + word.slice(1))
                                                    .join(' ');
                                                   }
                                         // ✅ Capital case transformation
                                          const firstName = toCapitalCase($("#firstName").val().trim());
                                          const lastName = toCapitalCase($("#lastName").val().trim());
                                          const relationName = toCapitalCase($("#relationName").val().trim());
                                          const natureOfJob = toCapitalCase($("#natureOfJob").val().trim());
                                          const emergencyName = toCapitalCase($("#emergencyName").val().trim());
                                          const address = toCapitalCase($("#address").val().trim());
                                          const idMark = toCapitalCase($("#idMark").val().trim());
                                          const pfApplicable = $("#pfApplicable").is(":checked") ? "Yes" : "No";
										  const data = new FormData();
										
										        const jsonData = {
													transactionId:$("#transactionId").val().trim(),
										            aadhaarNumber: $("#aadharNumber").val().trim(),
										            firstName: firstName,
										            lastName: lastName,
										            dateOfBirth: $("#dateOfBirth").val().trim(),
										            gender: $("#gender").val(),
										            relationName: relationName,
										            idMark: idMark,
										            mobileNumber: $("#mobileNumber").val().trim(),
										            maritalStatus: $("#maritalStatus").val(),
										            principalEmployer: $("#principalEmployer").val(),
										            contractor: $("#contractor").val(),
										            workorder: $("#workorder").val(),
										            trade: $("#trade").val(),
										            skill: $("#skill").val(),
										            department: $("#department").val(),
										            subdepartment: $("#subdepartment").val(),
										            eic: $("#eic").val(),
										            natureOfJob: natureOfJob,
										            wcEsicNo: $("#wc").val(),
													llNo:$("#ll").val(),
										            hazardousArea: $("#hazardousArea").val(),
										            accessArea: $("#accessArea").val(),
										            uanNumber: $("#uanNumber").val().trim(),
										            healthCheckDate: $("#healthCheckDate").val().trim(),
										            pfNumber:$("#pfNumber").val().trim(),
			                                        esicNumber:$("#esicNumber").val().trim(),
										            bloodGroup: $("#bloodGroup").val(),
										            accommodation: $("#accommodation").val(),
										            academic: $("#academic").val(),
										            technical: $("#technical").val(),
										            ifscCode: $("#ifscCode").val().trim(),
										            accountNumber: $("#accountNumber").val().trim(),
										            emergencyName: emergencyName,
										            emergencyNumber: $("#emergencyNumber").val().trim(),
										            wageCategory: $("#wageCategory").val(),
										            bonusPayout: $("#bonusPayout").val(),
										            pfCap: $("#pfCap").val(),
										            zone: $("#zone").val(),
										            basic: $("#basic").val().trim(),
										            da: $("#da").val().trim(),
										            hra: $("#hra").val().trim(),
										            washingAllowance: $("#washingAllowance").val().trim(),
										            otherAllowance: $("#otherAllowance").val().trim(),
										            uniformAllowance: $("#uniformAllowance").val().trim(),
										            userId: userId,
										            gatePassAction: "save",
										            comments: $("#comments").val().trim(),
													address:address,
													doj:$("#doj").val(),
													pfApplicable:pfApplicable,
                                                    policeVerificationDate: $("#policeVerificationDate").val().trim(),
			                                        disability:$("#disability").val(),
                                                    workmenType:$("#workmenType").val(),
										        };

										        // Serialize the JSON object to a string
												const jsonString = JSON.stringify(jsonData);

												// Append the JSON data to FormData
												data.append("jsonData", jsonString);
										   const xhr = new XMLHttpRequest();
										        xhr.open("POST", "/CWFM/contractworkmen/draftGatePass", true);

										        xhr.onload = function () {
													hideLoader();
										            if (xhr.status === 200) {
										                console.log("Data saved successfully:", xhr.responseText);
														sessionStorage.setItem("successMessage", "Gatepass drafted successfully!");
										                loadCommonList('/contractworkmen/list', 'On-Boarding List');
														//hideLoader();
										            } else {
										                console.error("Error saving data:", xhr.status, xhr.responseText);
														sessionStorage.setItem("errorMessage", "Failed to draft Gatepass!");
														loadCommonList('/contractworkmen/list', 'On-Boarding List');
														//hideLoader();
										            }
										        };

										        xhr.onerror = function () {
													hideLoader();
										            console.error("Request failed");
													sessionStorage.setItem("errorMessage", "Failed to draft Gatepass!");
													loadCommonList('/contractworkmen/list', 'On-Boarding List');
										        };

										        // Send the FormData object
										        xhr.send(data);
										    } 
											
												
												function redirectToWorkmenEdit() {
											    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
											    if (selectedCheckboxes.length !== 1) {
											        alert("Please select exactly one row to view.");
											        return;
											    }
											    
											    var selectedRow = selectedCheckboxes[0].closest('tr');
											    var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;
												var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
												   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed

												   if (gatePassType.toLowerCase() !== "create" || status.toLowerCase() !== "draft") {
												       alert("Edit is only allowed when Gate Pass Type is 'Create' and Status is 'Draft'.");
												       return;
												   }
											    var xhr = new XMLHttpRequest();
											    xhr.onreadystatechange = function() {
											        if (xhr.readyState == 4 && xhr.status == 200) {
											            document.getElementById("mainContent").innerHTML = xhr.responseText;
														setDateRange();
											        }
											    };
											    xhr.open("GET", "/CWFM/contractworkmen/getDraftDetails/" + transactionId, true);
											    xhr.send();
											}
											function searchRenew() {
											var principalEmployerId = $('#principalEmployerId').val();

																	var deptId=$("#deptId").val();
											$.ajax({
											    url: '/CWFM/contractworkmen/renewList',
											    type: 'POST',
											    data: {
											        principalEmployerId: principalEmployerId,
																			deptId:deptId
											    },
											    success: function(response) {
											        var tableBody = $('#workmenTable tbody');
													  if ($.fn.DataTable.isDataTable('#workmenTable')) {
														$('#workmenTable').DataTable().destroy();
													}
											        tableBody.empty();
											        if (Array.isArray(response) &&response.length > 0) {
											            $.each(response, function(index, wo) {
											                var row = '<tr  >' +
																							'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
																							'<td  >' + wo.transactionId + '</td>' +
																							 '<td  >' + wo.gatePassId + '</td>' +
											                          '<td  >' + wo.firstName + ' ' + wo.lastName +'</td>' +
																							 
																							  '<td  >' + wo.aadhaarNumber + '</td>' +	
																							  '<td  >' + wo.contractorName + '</td>' +	
																							
																							  '<td  >' +wo.unitName + '</td>' +	
																							  '<td  >' + wo.gatePassType + '</td>' +
																							  '<td  >' + toCapitalCase(wo.onboardingType) + '</td>' +	
																							  '<td  >' + wo.status + '</td>' +				                             
											                          '</tr>';
											                tableBody.append(row);
											            });
											        } 
													// ✅ Always init after rows are drawn
														initWorkmenTable("workmenTable");
											    },
											    error: function(xhr, status, error) {
											        console.error("Error fetching data:", error);
											    }
											});
																}
																
function redirectToWorkmenRenewEdit() {
	var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	if (selectedCheckboxes.length !== 1) {
			alert("Please select exactly one row to view.");
			return;
	}
																 
	var selectedRow = selectedCheckboxes[0].closest('tr');
	var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
	var gatePassType = selectedRow.cells[7].innerText.trim(); // Adjust index if needed
	   var status = selectedRow.cells[9].innerText.trim(); // Adjust index if needed

	   if (gatePassType.toLowerCase() !== "create" || status.toLowerCase() !== "approved") {
	       alert("Edit is only allowed when Gate Pass Type is 'Create' and Status is 'Approved'.");
	       return;
	   }
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
	if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("mainContent").innerHTML = xhr.responseText;
			setDateRange();
	}
	};
	xhr.open("GET", "/CWFM/contractworkmen/renew/" + gatePassId, true);
	xhr.send();
}
function renewGatePass(userId) {
	showLoader();
    let basicValid = true;
    let employmentValid = true;
    let otherValid = true;
    let wagesValid = true;
    let documentValid = true;

    var aadharFile = $("#aadharFile").prop("files")[0];
    var policeFile = $("#policeFile").prop("files")[0];
	var profilePic = $("#imageFile").prop("files")[0];
	var appointmentFile = $("#appointmentFile").prop("files")[0];
    // Validate the files (optional)
    if (!validateFiles(aadharFile, policeFile,profilePic,appointmentFile)) {
        documentValid = false; // Stop the upload if validation fails
        hideLoader();
    }

    if (!validateBasicData()) {
        basicValid = false;
    }

    if (!validateEmploymentInformation()) {
        employmentValid = false;
    }

    if (!validateOtherInformation()) {
        otherValid = false;
    }

    if (!validateWages()) {
        wagesValid = false;
    }

    console.log("basicValid: " + basicValid);
    console.log("employmentValid: " + employmentValid);
    console.log("otherValid: " + otherValid);
    console.log("wagesValid: " + wagesValid);
    console.log("documentValid: " + documentValid);
 const pfApplicable = $("#pfApplicable").is(":checked") ? "Yes" : "No";
    if (basicValid && employmentValid && otherValid && wagesValid && documentValid) {
        const data = new FormData();
        const jsonData = {
			transactionId:$("#transactionId").val().trim(),
			gatePassId:$("#gatePassId").val().trim(),
            aadhaarNumber: $("#aadharNumber").val().trim(),
            firstName: $("#firstName").val().trim(),
            lastName: $("#lastName").val().trim(),
            dateOfBirth: $("#dateOfBirth").val().trim(),
            gender: $("#gender").val(),
            relationName: $("#relationName").val().trim(),
            idMark: $("#idMark").val().trim(),
            mobileNumber: $("#mobileNumber").val().trim(),
            maritalStatus: $("#maritalStatus").val(),
            principalEmployer: $("#principalEmployer").val(),
            contractor: $("#contractor").val(),
            workorder: $("#workorder").val(),
            trade: $("#trade").val(),
            skill: $("#skill").val(),
            department: $("#department").val(),
            subdepartment: $("#subdepartment").val(),
            eic: $("#eic").val(),
            natureOfJob: $("#natureOfJob").val().trim(),
            wcEsicNo: $("#wc").val(),
			llNo:$("#ll").val(),
            hazardousArea: $("#hazardousArea").val(),
            accessArea: $("#accessArea").val(),
            uanNumber: $("#uanNumber").val().trim(),
            healthCheckDate: $("#healthCheckDate").val().trim(),
            pfNumber:$("#pfNumber").val(),
			esicNumber:$("#esicNumber").val(),
            bloodGroup: $("#bloodGroup").val(),
            accommodation: $("#accommodation").val(),
            academic: $("#academic").val(),
            technical: $("#technical").val(),
            ifscCode: $("#ifscCode").val().trim(),
            accountNumber: $("#accountNumber").val().trim(),
            emergencyName: $("#emergencyName").val().trim(),
            emergencyNumber: $("#emergencyNumber").val().trim(),
            wageCategory: $("#wageCategory").val(),
            bonusPayout: $("#bonusPayout").val(),
            pfCap: $("#pfCap").val(),
            zone: $("#zone").val(),
            basic: $("#basic").val().trim(),
            da: $("#da").val().trim(),
            hra: $("#hra").val().trim(),
            washingAllowance: $("#washingAllowance").val().trim(),
            otherAllowance: $("#otherAllowance").val().trim(),
            uniformAllowance: $("#uniformAllowance").val().trim(),
            userId: userId,
            gatePassAction: "save",
            comments: $("#comments").val().trim(),
			address:$("#address").val().trim(),
			doj:$("#doj").val(),
			pfApplicable:pfApplicable,
            policeVerificationDate: $("#policeVerificationDate").val().trim(),
            disability:$("#disability").val(),
            workmenType:$("#workmenType").val(),
        };

        // Serialize the JSON object to a string
		const jsonString = JSON.stringify(jsonData);

		// Append the JSON data to FormData
		data.append("jsonData", jsonString);

        // Append the files to the FormData
        if (aadharFile) {
            data.append("aadharFile", aadharFile);
        }
        if (policeFile) {
            data.append("policeFile", policeFile);
        }
		
		
		if(profilePic){
			data.append("profilePic",profilePic);
		}
		if(appointmentFile){
			data.append("appointmentFile",appointmentFile);
		}
		
    	const additionalFields = document.querySelectorAll('.document-field');
    additionalFields.forEach((field, index) => {
        const docType = field.querySelector('select[name="documentType"]').value;
        const fileInput = field.querySelector('input[type="file"]');

        if (docType && fileInput.files[0]) {
            data.append('additionalFiles', fileInput.files[0]);
            data.append('documentTypes', docType);
        }
    });
        
		/*// Log FormData content
for (const [key, value] of data.entries()) {
    console.log(key, value instanceof File ? value.name : value); // Log filename if it's a File
}*/
        // Send the data to the server using AJAX
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/CWFM/contractworkmen/renewGatePass", true);

        xhr.onload = function () {
			hideLoader();
            if (xhr.status === 200) {
                console.log("Data saved successfully:", xhr.responseText);
				sessionStorage.setItem("successMessage", "Gatepass renew request raised successfully!");
                loadCommonList('/contractworkmen/renewFilter', 'Renew List');
				//hideLoader();
            } else {
                console.error("Error saving data:", xhr.status, xhr.responseText);
				sessionStorage.setItem("errorMessage", "Failed to raise Gatepass renew request!");
            }
        };

        xhr.onerror = function () {
			hideLoader();
            console.error("Request failed");
			sessionStorage.setItem("errorMessage", "Failed to raise Gatepass renew request!");
        };

        // Send the FormData object
        xhr.send(data);
    } else {
        console.error("Validation failed for one or more fields.");
    }
}

function approveRejectRenew(status,gatePassType){
	showLoader();
	let isValid=true;
	 const approvercomments = $("#approvercomments").val().trim();
   if (approvercomments === "" && status==5) {
       $("#error-approvercomments").show();
       alert("Comments Required in Documents");
       isValid = false;
       hideLoader();
   }else{
	$("#error-approvercomments").hide();
}
if(isValid){
	const data = {
		approverId : $("#userId").val().trim(),
		comments : $("#approvercomments").val().trim(),
		status : status,
		transactionId : $("#transactionId").val().trim(),
		gatePassId : $("#gatePassId").val().trim(),
		approverRole : $("#roleName").val().trim(),
		roleId :$("#roleId").val().trim(),
		gatePassType : gatePassType,
	};
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractworkmen/approveRejectGatePass", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
	   hideLoader();
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
		   sessionStorage.setItem("successMessage", "Gatepass renew request approved/rejected successfully!");
         loadCommonList('/contractworkmen/renewFilter', 'Renew List');
         //hideLoader();
       } 		 else if (xhr.status === 400) {  
		 							       const msg = xhr.responseText.trim();
		 							       console.error("Server validation failed: " + msg);
		 								   showLicenseError(msg);
		 							       //alert(msg); // or show in UI better
		 							       //sessionStorage.setItem("errorMessage", msg);
		 								   //hideLoader();
		 								   return;
		 							   }
	   else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
		   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass renew request!");
		   //hideLoader();
       }
   };
   
   xhr.onerror = function() {
	   //hideLoader();
       console.error("Request failed");
	   sessionStorage.setItem("errorMessage", "Failed to approve/reject Gatepass renew request!");
	    hideLoader();
   };
   
   // Send the data object as a JSON string
   xhr.send(JSON.stringify(data));
	}else{
		//error 
	}
	}//eofunc
	

		function redirectToWorkmenRenewView() {
	    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	    if (selectedCheckboxes.length !== 1) {
	        alert("Please select exactly one row to view.");
	        return;
	    }
	    
	    var selectedRow = selectedCheckboxes[0].closest('tr');
	    var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;

	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/contractworkmen/renewview/" + gatePassId, true);
	    xhr.send();
	}

	
	

	function generateOtp() {
	    const aadhaarNumber = document.getElementById("aadharNumber").value;

	    // Simple validation
	    if (!aadhaarNumber || aadhaarNumber.length !== 12 || !/^\d{12}$/.test(aadhaarNumber)) {
	        document.getElementById("otpError").innerText = "Please enter a valid 12-digit Aadhaar number.";
	        document.getElementById("otpMessage").innerText = "";
	        return;
	    }

	    var xhr = new XMLHttpRequest();
	    var url = "/CWFM/contractworkmen/generateOtp"; // Your mapped Spring Controller URL

	    xhr.open("POST", url, true);
	    xhr.setRequestHeader("Content-Type", "application/json");

	    xhr.onload = function () {
	        var response;
	        try {
	            response = JSON.parse(xhr.responseText);
	        } catch (e) {
	            document.getElementById("otpError").innerText = "Invalid response from server.";
	            document.getElementById("otpMessage").innerText = "";
	            return;
	        }

	        if (xhr.status === 200 || xhr.status === 422) {
	            if (response.success) {
	                document.getElementById("otpMessage").innerText = response.message;
	                document.getElementById("otpError").innerText = "";
	            } else {
	                document.getElementById("otpError").innerText = response.message +" "+response.status;
	                document.getElementById("otpMessage").innerText = "";
	            }
	        } else {
	            document.getElementById("otpError").innerText = "Error: " + (response.message || xhr.statusText);
	            document.getElementById("otpMessage").innerText = "";
	        }
	    };

	    xhr.onerror = function () {
	        document.getElementById("otpError").innerText = "Request failed.";
	        document.getElementById("otpMessage").innerText = "";
	    };

	    var data = JSON.stringify({ aadhaarNumber: aadhaarNumber });
	    xhr.send(data);
	}
	
	function verifyOtp() {
	    const otp = document.getElementById("otp").value;

	    if (!otp || otp.length < 6 || !/^\d+$/.test(otp)) {
	        document.getElementById("otpError").innerText = "Please enter a valid numeric OTP.";
	        document.getElementById("otpMessage").innerText = "";
	        return;
	    }

	    const data = {
	        otp: otp,
	        clientId: "aadhaar_v2_vtzQlmkayhLKnPYgkEmy"
	    };

	    const xhr = new XMLHttpRequest();
	    xhr.open("POST", "/CWFM/contractworkmen/verifyOtp", true);
	    xhr.setRequestHeader("Content-Type", "application/json");

	    xhr.onload = function () {
	        let response;
	        try {
	            response = JSON.parse(xhr.responseText);
	        } catch (e) {
	            document.getElementById("otpError").innerText = "Invalid server response.";
	            return;
	        }

	        if (xhr.status === 200 && response.success) {
	            document.getElementById("otpMessage").innerText = "OTP Verified Successfully.";
	            document.getElementById("otpError").innerText = "";

	            // Populate form fields
	            document.getElementById("firstName").value = response.fullName?.split(" ")[0] || "";
	            document.getElementById("lastName").value = response.fullName?.split(" ").slice(1).join(" ") || "";
	            document.getElementById("dateOfBirth").value = response.dob || "";
	            document.getElementById("gender").value = response.gender || "";
	            document.getElementById("address").value = response.address || "";
	        } else {
	            document.getElementById("otpError").innerText = response.message || "OTP verification failed.";
	        }
	    };

	    xhr.onerror = function () {
	        document.getElementById("otpError").innerText = "Request failed.";
	    };

	    xhr.send(JSON.stringify(data));
	}

function formatToTwoDecimalPlaces(input) {
  let value = parseFloat(input.value);
  if (isNaN(value)) {
    input.value = '0.00';
  } else {
    input.value = value.toFixed(2);
  }
}
function redirectToWorkmenQuickAdd() {
console.log("redirectToWorkmenQuickAdd called");
    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
			setDateRange();
        }
    };
    xhr.open("GET", "/CWFM/contractworkmen/quickOnboardingCreation", true);
    xhr.send();
}
function redirectToWorkmenAdd(){
	// Fetch the content of add.jsp using AJAX
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
				setDateRange();
	        }
	    };
	    xhr.open("GET", "/CWFM/contractworkmen/addQuickOB", true);
	    xhr.send();
}
function setDateRange() {
	const today = new Date();

	// Person must be between 18 and 70 years old
	const maxDate = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate()); // youngest allowed DOB
	const minDate = new Date(today.getFullYear() - 70, today.getMonth(), today.getDate()); // oldest allowed DOB

	$(".datetimepickerformat").datepicker({
	    dateFormat: 'yy-mm-dd',
	    changeMonth: true,
	    changeYear: true,
	    yearRange: `${minDate.getFullYear()}:${maxDate.getFullYear()}`, // dynamic range
	    minDate: minDate,
	    maxDate: maxDate,
	    defaultDate: maxDate // 👈 ensures calendar opens at the 18-year-old boundary
	});

    $('.datetimepickerformat1').datepicker({//date of joiing
        dateFormat: 'yy-mm-dd', // Set the date format
        changeMonth: true,      // Allow changing month via dropdown
        changeYear: true,       // Allow changing year via dropdown
        yearRange: "0:+100", 
        minDate: 0 ,
		maxDate: +15             // Prevent selecting future dates
    });
    const sixMonthsAgo = new Date();
    sixMonthsAgo.setMonth(today.getMonth() - 6);

    $(".datetimepickerformat2").datepicker({//health check date
        dateFormat: 'yy-mm-dd',
        changeMonth: true,
        changeYear: true,
        minDate: sixMonthsAgo,
        maxDate: today,
        yearRange: `${sixMonthsAgo.getFullYear()}:${today.getFullYear()}`
    });
   
const oneYearAgo = new Date();
oneYearAgo.setFullYear(today.getFullYear() - 1);
   $(".datetimepickerformat3").datepicker({
    dateFormat: 'yy-mm-dd',
    changeMonth: true,
    changeYear: true,
    minDate: oneYearAgo,
    maxDate: today,
    yearRange: `${today.getFullYear() - 1}:${today.getFullYear()}`
});
$(".grantdatetimepicker").datepicker({
    dateFormat: 'yy-mm-dd',
    changeMonth: true,
    changeYear: true,
    yearRange: "1900:+0",   // from 1900 to current year
    maxDate: 0,              // ❌ cannot select future date
    defaultDate: 0           // today selected by default
});
$(".expirydatetimepicker").datepicker({
    dateFormat: 'yy-mm-dd',
    changeMonth: true,
    changeYear: true,
    yearRange: "+0:+50",     // from current year to 50 years future
    minDate: 1,               // ❌ cannot select today or past
    defaultDate: +1           // tomorrow selected by default
});
$(".pfapplydatetimepicker").datepicker({
    dateFormat: 'yy-mm-dd',
    changeMonth: true,
    changeYear: true,
    yearRange: "1900:+0",   // from 1900 to current year
    maxDate: -1,              // ❌ cannot select future date
    defaultDate: 0           // today selected by default
});
$(".compliancedatetimepicker").datepicker({
    dateFormat: 'yy-mm-dd',
    changeMonth: true,
    changeYear: true,
    yearRange: "1900:+50",   // past 1900 to 50 years in future
    minDate: null,            // allow past
    maxDate: null,            // allow future
    defaultDate: null
});

}

function validatePfForm11Requirement() {
    const pfApplicable = document.getElementById("pfApplicable").checked;
    const form11ErrorContainer = document.getElementById("form11-error-message");
    let form11Present = false;

    if (!pfApplicable) {
        const additionalFields = document.querySelectorAll('.document-field');

        additionalFields.forEach((field) => {
            const docType = field.querySelector('select[name="documentType"]')?.value;
            const file = field.querySelector('input[type="file"]')?.files[0];

            if (docType === "Form11" && file) {
                form11Present = true;
            }
        });

        if (!form11Present) {
            form11ErrorContainer.textContent = "Form11 document is mandatory since PF is not applicable.";
            form11ErrorContainer.style.display = "block";
            return false;
        }
    }

    // Clear error if condition passes or PF is applicable
    form11ErrorContainer.textContent = "";
    form11ErrorContainer.style.display = "none";
    return true;
}



  
  


let cameraStream = null;

function previewImage(event, inputId, fileNameSpanId) {
    const input = document.getElementById(inputId);
    const preview = document.getElementById("preview");
    const fileNameSpan = document.getElementById(fileNameSpanId);

    preview.innerHTML = "";
    fileNameSpan.textContent = "";

    if (!input.files || input.files.length === 0) return;

    const file = input.files[0];
    fileNameSpan.textContent = file.name;

    if (inputId === "mobileCameraInput") {
        const dt = new DataTransfer();
        dt.items.add(file);
        document.getElementById("imageFile").files = dt.files;
    }

    if (!file.type.startsWith("image/")) return;

    const reader = new FileReader();
    reader.onload = function (e) {
        const img = document.createElement("img");
        img.src = e.target.result;
        img.style.maxWidth = "100%";
        img.style.maxHeight = "100%";
        preview.appendChild(img);
    };
    reader.readAsDataURL(file);
}

function isMobileDevice() {
    return /Android|iPhone|iPad|iPod/i.test(navigator.userAgent);
}

function openCamera() {

    if (isMobileDevice()) {
        document.getElementById("mobileCameraInput").click();
        return;
    }

    navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
            cameraStream = stream;
            const video = document.getElementById("webcam");
            video.srcObject = stream;

            video.onloadedmetadata = () => {   
                video.play();
                video.style.display = "block";
                document.getElementById("cameraButtons").style.display = "block";
            };
        })
        .catch(() => {
            alert("Camera not available or permission denied");
        });
}
function captureImage() {
    const video = document.getElementById("webcam");
    const canvas = document.getElementById("canvas");

    if (video.videoWidth === 0) {
        alert("Camera still loading... Try again.");
        return;
    }

    const ctx = canvas.getContext("2d");

    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;

    ctx.drawImage(video, 0, 0, canvas.width, canvas.height);

    canvas.toBlob(blob => {
        const file = new File([blob], "camera-image.jpg", { type: "image/jpeg" });

        const dt = new DataTransfer();
        dt.items.add(file);
        document.getElementById("imageFile").files = dt.files;

        previewImage(
            { target: document.getElementById("imageFile") },
            "imageFile",
            "imageFileName"
        );

        closeCamera();
    }, "image/jpeg", 0.95);
}

function closeCamera() {
    if (cameraStream) {
        cameraStream.getTracks().forEach(track => track.stop());
        cameraStream = null;
    }
    document.getElementById("webcam").style.display = "none";
    document.getElementById("cameraButtons").style.display = "none";
}



  
  function goBackToonboardingList() {
    	 loadCommonList('/contractworkmen/list', 'On-Boarding List');
    }
    function goBackToquickonboardingList() {
    	 loadCommonList('/contractworkmen/quickOnboardingList', 'Quick Onboarding List');
    }

    
	function initWorkmenTable(tablename) {
	    const selector = '#' + tablename;

	    if ($.fn.DataTable.isDataTable(selector)) {
	        $(selector).DataTable().destroy();
	    }

	    $(selector).DataTable({
	        paging: true,
	        searching: true,
	        ordering: true
	    });
	}

   function searchGatePassStatus() {
    const transactionId = $('#transactionId').val().trim();
    const gatepassId = $('#gatePassId').val().trim();

   if (!transactionId && !gatepassId) {
    alert("Please enter Transaction ID or Gate Pass ID");
    return;
   }

    $.ajax({
        url: '/CWFM/entrypassstatus/statusList',
        type: 'POST',
        data: {
            transactionId: transactionId,
            gatepassId: gatepassId
        },
        success: function (response) {
            const tableBody = $('#workmenTable tbody');
            tableBody.empty();

            if (response.length > 0) {
                response.forEach(function (wo) {
                    const row = `<tr>
                        <td><input type="checkbox" name="selectedWOs" value="${wo.transactionId || ''}"></td>
                        <td>${wo.transactionId || ''}</td>
                        <td>${wo.firstName || ''} ${wo.lastName || ''}</td>
                        <td>${wo.lastName || ''}</td>
                        <td>${wo.aadhaarNumber || ''}</td>
                        <td>${wo.approvedBy || ''}</td>
                        <td>${wo.pendingWith || ''}</td>
                    </tr>`;
                    tableBody.append(row);
                });
            } else {
                tableBody.append('<tr><td colspan="8">No data found</td></tr>');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching data:", error);
        }
    });
}

 function searchGatePassHistory() {
    const aadharNumber = $('#aadharNumber').val().trim();

    $.ajax({
        url: '/CWFM/entrypassstatus/history',
        type: 'POST',
        data: {
            aadharNumber: aadharNumber
        },
        success: function (response) {
            const tableBody = $('#workmenTable tbody');
            tableBody.empty();

            if (response.length > 0) {
                response.forEach(function (wo) {
                    const row = `<tr>
                        <td> <input type="checkbox" name="selectedWOs" value="${wo.transactionId}"></td>
                        <td>${wo.transactionId || ''}</td>
                         <td>${wo.gatePassId || ''}</td>
                        <td>${wo.firstName || ''} ${wo.lastName || ''}</td>
                        <td>${wo.lastName || ''}</td>
                         <td>${wo.gatePassType || ''}</td>
                         <td>${wo.status || ''}</td>
                        <td>${wo.unitName || ''}</td>
                       
                    </tr>`;
                    tableBody.append(row);
                });
            } else {
                tableBody.append('<tr><td colspan="8">No data found</td></tr>');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching data:", error);
        }
    });
}  

function loadDepartments(unitId) {
    if (!unitId) {
        $("#deptId").html("<option value=''>Select Department</option>");
        return;
    }

    $.ajax({
        url: "/CWFM/contractworkmen/getAllDepartments",
        type: "GET",
        data: { unitId: unitId },
        success: function (departments) {
            var deptSelect = $("#deptId");
            deptSelect.empty();
            deptSelect.append("<option value=''>Select Department</option>");
            $.each(departments, function (i, dept) {
                deptSelect.append(
                    "<option value='" + dept.departmentId + "'>" + dept.department + "</option>"
                );
            });
        },
        error: function () {
            alert("Error loading departments!");
        }
    });
}
function verifyOtpDemo() {
	    const aadharNo = document.getElementById("aadharNumber").value;


	    
	    
	        if (aadharNo === '992158496671') {
	           
	            document.getElementById("firstName").value =  "Shashikant";
	            document.getElementById("lastName").value = "Shukla";
	            document.getElementById("dateOfBirth").value = "2002-07-07";
	            document.getElementById("gender").value = "11";
	            document.getElementById("address").value =  "Anakapalli rebaka";
				document.getElementById("mobileNumber").value =  "9876543210";
	        } else if(aadharNo === '550188039490'){
								document.getElementById("firstName").value =  "Nivetha";
					            document.getElementById("lastName").value = "Mohansingh";
					            document.getElementById("dateOfBirth").value = "1991-07-07";
					            document.getElementById("gender").value = "12";
					            document.getElementById("address").value =  "SVA anada nilaya,ist cross immadihalli road, nagondanahalli,Bengaluru-560066";
								document.getElementById("mobileNumber").value =  "9876543111";
	          }							else if(aadharNo === '880188039490'){
															document.getElementById("firstName").value =  "Hemalatha";
												            document.getElementById("lastName").value = "Karanam";
												            document.getElementById("dateOfBirth").value = "1991-08-07";
												            document.getElementById("gender").value = "12";
												            document.getElementById("address").value =  "Ardente Office One, nagondanahalli,Bengaluru-560066";
															document.getElementById("mobileNumber").value =  "8876543111";
								          }
	   

	  
	}
	
	function generateToken(){
		const aadhaarNumber = document.getElementById("aadharNumber").value;
		const transactionId= document.getElementById("transactionId").value;
		let valid=false;
			    // Simple validation
			    if (!aadhaarNumber || aadhaarNumber.length !== 12 || !/^\d{12}$/.test(aadhaarNumber)) {
			        document.getElementById("otpError").innerText = "Please enter a valid 12-digit Aadhaar number.";
			        document.getElementById("otpMessage").innerText = "";
			        return;
			    }else{
					valid = aadharDuplicateDBCheck(aadhaarNumber);
				}				
if(valid){
			    var xhr = new XMLHttpRequest();
			    var url = "/CWFM/contractworkmen/generateToken"; // Your mapped Spring Controller URL

			    xhr.open("POST", url, true);
			    xhr.setRequestHeader("Content-Type", "application/json");

			    xhr.onload = function () {
			        var response;
			        try {
			            response = JSON.parse(xhr.responseText);
			        } catch (e) {
			            document.getElementById("otpError").innerText = "Invalid response from server.";
			            document.getElementById("otpMessage").innerText = "";
			            return;
			        }

			        if (xhr.status === 200 || xhr.status === 422) {
						if (response.url) {
							    openDigiModal(response.token);

						}

			           
						 else {
			                document.getElementById("otpError").innerText = response.message +" "+response.status;
			                document.getElementById("otpMessage").innerText = "";
			            }
			        } else {
			            document.getElementById("otpError").innerText = "Error: " + (response.message || xhr.statusText);
			            document.getElementById("otpMessage").innerText = "";
			        }
			    };

			    xhr.onerror = function () {
			        document.getElementById("otpError").innerText = "Request failed.";
			        document.getElementById("otpMessage").innerText = "";
			    };

			    var data = JSON.stringify({ aadhaarNumber: aadhaarNumber });
			    xhr.send(data);
				}
	}
	
	function openDigiModal(token) {
	    const modal = document.getElementById("digiModal");
	    const container = document.getElementById("digilocker-sdk-button");

	    if (modal) {
	        modal.style.display = "block";
	    }

	    // 🔑 Clear old buttons before SDK re-renders
	    if (container) {
	        container.innerHTML = "";
	        container.style.display = "block"; // reset if hidden on success
	    }

	    // Initialize Digi SDK inside modal with token
	    window.DigiboostSdk({
	        gateway: "production", // sandbox or production
	        token: token, // directly from response
	        onSuccess: handleSuccess,
	        onFailure: handleFailure,
	        selector: "#digilocker-sdk-button",
	        style: {
	            width: "100%",
	            margin: "0px"
	        }
	    });
	}

	function closeModal() {
	    const modal = document.getElementById("digiModal");
	    const container = document.getElementById("digilocker-sdk-button");

	    if (modal) {
	        modal.style.display = "none";
	    }

	    // 🔑 Reset container on close too
	    if (container) {
	        container.innerHTML = "";
	        container.style.display = "block";
	    }
	}


	function handleSuccess(data) {
			//Do something here for success case
			console.log("Verification successful:", data);
	        document.getElementById("status").innerHTML = 
	            '<div class="success"> Verification completed successfully!</div>';
	            document.getElementById("digilocker-sdk-button").style.display = "none";
	        
	        //window.location.href = "/CWFM/contractworkmen/digiClientId?id=" + encodeURIComponent(data.client_id);
			
			var xhr = new XMLHttpRequest();
					    var url = "/CWFM/contractworkmen/digiClientId?id=" + encodeURIComponent(data.client_id);

					    xhr.open("GET", url, true);
					    xhr.setRequestHeader("Content-Type", "application/json");

					    xhr.onload = function () {
					        var response;
					        try {
					            response = JSON.parse(xhr.responseText);
					        } catch (e) {
					            document.getElementById("otpError").innerText = "Invalid response from server.";
					            document.getElementById("otpMessage").innerText = "";
					            return;
					        }

					        if (xhr.status === 200 || xhr.status === 422) {
								if (response.data) {
									  console.log(response.data.aadhaar_xml_data);
									  closeModal();
									  let firstname = response.data.aadhaar_xml_data.full_name ?.split(" ")[0] || "";
									  let lastname = response.data.aadhaar_xml_data.full_name?.split(" ").slice(1).join(" ") || "";
									  let dob = response.data.aadhaar_xml_data.dob|| "";
									 // let fathername = response.data.aadhaar_xml_data.care_of|| "";
									 let careOf = response.data.aadhaar_xml_data.care_of || "";
									 let match = careOf.match(/^\s*([A-Za-z]\/O:)?\s*(.*)$/i);
									 let relation = "";
									 let fathername = "";
									 if (match) {
									     relation = (match[1] || "").trim();  // e.g. "S/O:" or ""
									     fathername = (match[2] || "").trim(); // e.g. "Sankar Singh"
									 }
									  let g = response.data.aadhaar_xml_data.gender|| "";
									  let gender;
									  if(g === "F"){
										gender = "12";
									  }else{
										gender = "11";
									  }
									  let address= response.data.aadhaar_xml_data.full_address|| ""+" "+response.data.aadhaar_xml_data.zip|| "";
									  document.getElementById("firstName").value = firstname;
									   document.getElementById("lastName").value =lastname;
									  document.getElementById("dateOfBirth").value =dob;
									  	document.getElementById("gender").value = gender
									  document.getElementById("address").value =  address;
									  document.getElementById("relationName").value =  fathername;
									  
									  document.getElementById("aadharNumber").readOnly = true;
									  document.getElementById("firstName").readOnly = true;
									         
											 document.getElementById("dateOfBirth").disabled = true;

									         document.getElementById("gender").disabled = true;   // if it's a <select>
									         document.getElementById("address").readOnly = true;
									        
											 document.querySelector('button[onclick="generateToken()"]').disabled = true;
								}

					           
								 else {
					                document.getElementById("otpError").innerText = response.message +" "+response.status;
					                document.getElementById("otpMessage").innerText = "";
					            }
					        } else {
					            document.getElementById("otpError").innerText = "Error: " + (response.message || xhr.statusText);
					            document.getElementById("otpMessage").innerText = "";
					        }
					    };

					    xhr.onerror = function () {
					        document.getElementById("otpError").innerText = "Request failed.";
					        document.getElementById("otpMessage").innerText = "";
					    };

					   
					    xhr.send();	
			
		}

		function handleFailure() {
			//Do something here for failure case
			console.log("Verification failed:", error);
	        document.getElementById("status").innerHTML = 
	            '<div class="error"> Verification failed or was cancelled</div>';
		}
		

		
		function aadharDuplicateDBCheck(aadharNumber) {
		    let aadharCheckPassed = false;

			$.ajax({
				     url: "/CWFM/contractworkmen/checkAadharExistsCreation",
				     type: "GET",
				     data: {
				         aadharNumber: aadharNumber,
				         gatePassId: $("#gatePassId").val(),        // NULL for draft
				         transactionId: $("#transactionId").val()   // always present for draft/renewal
				     },
				     async: false,
				     success: function (response) {

						let status = response.status ? response.status.trim() : '';
						if (status !== "Unique" && status !== "Invalid" && status !== "") {
						    $("#error-aadhar").text("Aadhaar already exists").show();
						    aadharCheckPassed = false;
						} else if (status === "Invalid") {
						    $("#error-aadhar").text("Invalid Aadhar Number").show();
						    aadharCheckPassed = false;
						} else {
						    $("#error-aadhar").hide();
						    aadharCheckPassed = true;
						}

				     },
				     error: function () {
				         $("#error-aadhar").text("Unable to verify Aadhaar").show();
				         aadharCheckPassed = false;
				     }
				 });

		    return aadharCheckPassed;  // ✅ return after ajax finishes
		}
		function downloadPreviousFile(transactionId, fileName) {
  if (!transactionId || !fileName) {
    alert("Missing transaction or file name.");
    return;
  }

  const url = `/CWFM/contractworkmen/downloadPreviousDoc?transactionId=${encodeURIComponent(transactionId)}&fileName=${encodeURIComponent(fileName)}`;

  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error("File not found or server error");
      }
      return response.blob();  // get file as blob
    })
    .then(blob => {
      const blobUrl = URL.createObjectURL(blob); // create secure blob link
      window.open(blobUrl, '_blank');            // open in new tab

      // optional cleanup after a short delay
      setTimeout(() => URL.revokeObjectURL(blobUrl), 30000);
    })
    .catch(err => {
      console.error("Error opening file:", err);
      alert("Unable to open file.");
    });
}
function onWcChange(selectElement) {

    // handle empty selection safely
    if (!selectElement || selectElement.selectedIndex === 0) {
        hideEsic();
        return;
    }


	const selectedOption = selectElement.options[selectElement.selectedIndex];
	    const licenceType = selectedOption.getAttribute("data-code");
    console.log("Licence Type:", licenceType);

    if (licenceType === "ESIC") {
        showEsic();
    } else {
        hideEsic();
    }
}

function showEsic() {
    document.getElementById("esicNumber").required = true;
    document.getElementById("esicNumberSection").style.display = "";
    document.getElementById("esicRequiredStar").style.display = "";
	//document.getElementById("error-esicNumber").style.display = "";
	$("#error-esicNumber").hide();
	
}

function hideEsic() {
    document.getElementById("esicNumber").required = false;
    document.getElementById("esicNumber").value = "";
    document.getElementById("error-esicNumber").style.display = "none";
    document.getElementById("esicNumberSection").style.display = "none";
    document.getElementById("esicRequiredStar").style.display = "none";
}


function searchGatePassReportBasedOnPE() {
					    var principalEmployerId = $('#principalEmployerId').val();
					    
						
					    $.ajax({
					        url: '/CWFM/entryPassStatus/report',
					        type: 'POST',
					        data: {
					            principalEmployerId: principalEmployerId
					        },
					        success: function(response) {
					            var tableBody = $('#workmenTable tbody');
								// 🔄 Clear previous DataTable and its config
								           if ($.fn.DataTable.isDataTable('#workmenTable')) {
								               $('#workmenTable').DataTable().destroy();
								           }
										   tableBody.empty();
					            if (Array.isArray(response) &&response.length > 0) {
					                $.each(response, function(index, wo) {
					                    var row = '<tr  >' +
												'<td  ><input type="checkbox" name="selectedUnitIds" value="' + wo.transactionId + '"></td>'+
												'<td  >' + wo.transactionId + '</td>' +
												 '<td  >' + wo.entryPassNo + '</td>' +
					                              '<td  >' + wo.contractWorkmenCode+'</td>' +
												   '<td  >' +wo.firstName + '</td>' +
												  '<td  >' + wo.lastName + '</td>' +	
												  '<td  >' + wo.department + '</td>' +	
												  '<td  >' +wo.vendorCode + '</td>' +	
												  '<td  >' + wo.vendorName + '</td>' +
												  '<td  >' + wo.workOrder + '</td>' +
												  '<td  >' + wo.eicNumber + '</td>' +
												  '<td  >' + wo.entryPassAction + '</td>' +
												  '<td  >' +  toCapitalCase(wo.entryPassType) + '</td>' +
												  '<td  >' + wo.lastApprover + '</td>' +
												  '<td  >' + wo.nextApprover + '</td>' +
												  '<td  >' + wo.status + '</td>' +				                             
					                              '</tr>';
					                    tableBody.append(row);
					                });
									
					            } 								

																	            // ✅ Always init after rows are drawn
																	            initWorkmenTable("workmenTable");
																	        },
					       
					        error: function(xhr, status, error) {
					            console.error("Error fetching data:", error);
					        }
					    });
					}
					function toggleSelectAll() {
					           var selectAllCheckbox = document.getElementById('selectAllCheckbox');
					           var checkboxes = document.querySelectorAll('input[name="selectedUnitIds"]');
					           checkboxes.forEach(function(checkbox) {
					               checkbox.checked = selectAllCheckbox.checked;
					           });
					       }
						   
						   function exportToEntryPassStatusCSV() {
						       var selectedRows = document.querySelectorAll('input[name="selectedUnitIds"]:checked');

						       if (selectedRows.length === 0) {
						           alert("Please select at least one record to export.");
						           return;
						       }

						       var csvContent = "data:text/csv;charset=utf-8,";
						       csvContent += "Transaction ID,Entry Pass No,Contract Workmen Code,First Name,Last Name,Department,Vendor Code,Vendor Name,Workorder,Eic Number,Entry Pass Action,Entry Pass Type,Last Approver,Next Approver,Status\n";

						       selectedRows.forEach(function (checkbox) {
						           var row = checkbox.closest("tr");

						           var rowData = row.querySelectorAll(
						               "td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6), td:nth-child(7), td:nth-child(8), td:nth-child(9), td:nth-child(10), td:nth-child(11), td:nth-child(12), td:nth-child(13), td:nth-child(14), td:nth-child(15), td:nth-child(16)"
						           );

						           var rowArray = [];
						           rowData.forEach(function (cell) {
						               rowArray.push('"' + cell.innerText.replace(/"/g, '""') + '"');
						           });

						           csvContent += rowArray.join(",") + "\n";
						       });

						       var encodedUri = encodeURI(csvContent);
						       var link = document.createElement("a");
						       link.setAttribute("href", encodedUri);
						       link.setAttribute("download", "EntryPassStatus.csv");
						       document.body.appendChild(link);
						       link.click();
						       document.body.removeChild(link);
						   }
						   
						   function redirectToWorkmenProjectAdd() {
						   console.log("redirectToWorkmenProjectAdd called");
						       // Fetch the content of add.jsp using AJAX
						       var xhr = new XMLHttpRequest();
						       xhr.onreadystatechange = function() {
						           if (xhr.readyState == 4 && xhr.status == 200) {
						               // Update the mainContent element with the fetched content
						               document.getElementById("mainContent").innerHTML = xhr.responseText;
						   			setDateRange();
						           }
						       };
						       xhr.open("GET", "/CWFM/contractworkmen/projectOnboardingCreation", true);
						       xhr.send();
						   }
						   function goBackToProjectOnboardingList() {
						      	 loadCommonList('/contractworkmen/projectOnboardingList', 'Project Gatepass List');
						      }
							  function validateProjectEmploymentInformation(){
							  	let isValid = true;
							      const principalEmp = $("#principalEmployer").val();
							       if (principalEmp === "") {
							          $("#error-principalEmployer").show();
							          isValid = false;
							      }else{
							  		$("#error-principalEmployer").hide();
							  	}
							  	const cont = $("#contractor").val();
							       if (cont === "") {
							          $("#error-contractor").show();
							          isValid = false;
							      }else{
							  		$("#error-contractor").hide();
							  	}
							  	const wo = $("#workorder").val();
							       if (wo === "") {
							          $("#error-workorder").show();
							          isValid = false;
							      }else{
							  		$("#error-workorder").hide();
							  	}
							  	const trade = $("#trade").val();
							       if (trade === "") {
							          $("#error-trade").show();
							          isValid = false;
							      }else{
							  		$("#error-trade").hide();
							  	}
							  	const skill = $("#skill").val();
							       if (skill === "") {
							          $("#error-skill").show();
							          isValid = false;
							      }else{
							  		$("#error-skill").hide();
							  	}
							  	const dept = $("#department").val();
							       if (dept === "") {
							          $("#error-department").show();
							          isValid = false;
							      }else{
							  		$("#error-department").hide();
							  	}
							  	const subdept = $("#subdepartment").val();
							       if (subdept === "") {
							          $("#error-area").show();
							          isValid = false;
							      }else{
							  		$("#error-area").hide();
							  	}
							  	const eic = $("#eic").val();
							       if (eic === "") {
							          $("#error-eic").show();
							          isValid = false;
							      }else{
							  		$("#error-eic").hide();
							  	}
							  	 
							  	return isValid;
							  }	
function aadharValidation(){
	let aadharCheckPassed = false;
	    const aadharNumber = $("#aadharNumber").val().trim();

		//const transactionId=$("#transactionId").val().trim();
	    if (aadharNumber === "" || aadharNumber.length !== 12 || isNaN(aadharNumber)) {

	        $("#error-aadhar").show();
	        isValid = false;
	    }else{
			 // $("#error-aadhar").hide();
			 $.ajax({
			 				     url: "/CWFM/contractworkmen/checkAadharExistsCreation",
			 				     type: "GET",
			 				     data: {
			 				         aadharNumber: aadharNumber,
			 				         gatePassId: $("#gatePassId").val(),        // NULL for draft
			 				         transactionId: $("#transactionId").val()   // always present for draft/renewal
			 				     },
			 				     async: false,
			 				     success: function (response) {

			 						let status = response.status ? response.status.trim() : '';
			 						if (status !== "Unique" && status !== "Invalid" && status !== "") {
			 						    $("#error-aadhar").text("Aadhaar already exists").show();
			 						    aadharCheckPassed = false;
			 						} else if (status === "Invalid") {
			 						    $("#error-aadhar").text("Invalid Aadhar Number").show();
			 						    aadharCheckPassed = false;
			 						} else {
			 						    $("#error-aadhar").hide();
			 						    aadharCheckPassed = true;
			 						}

			 				     },
			 				     error: function () {
			 				         $("#error-aadhar").text("Unable to verify Aadhaar").show();
			 				         aadharCheckPassed = false;
			 				     }
			 				 });


			     }
				 return aadharCheckPassed;
}
function showLoader() {
    document.getElementById("loaderOverlay").style.display = "flex";
}

function hideLoader() {
    document.getElementById("loaderOverlay").style.display = "none";
}



function uploadAadhaarFile() {

    showLoader();

    const fileInput = document.getElementById("aadhaarFile");

    if (!fileInput.files.length) {
        hideLoader();
        alert("Please select Aadhaar file");
        return;
    }

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractworkmen/aadhaarOCRValidate", true);

    xhr.onload = function () {

        let response;
        try {
            response = JSON.parse(xhr.responseText);
        } catch (e) {
            hideLoader();
            document.getElementById("otpError").innerText = "Invalid server response";
            return;
        }

        if ((xhr.status === 200 || xhr.status === 422) && response.success) {

            if (!response.data || !response.data.ocr_fields) {
                hideLoader();
                document.getElementById("otpError").innerText = "No OCR data found";
                return;
            }

            // ✅ Read existing values (important merge fix)
            let firstname = document.getElementById("firstName").value || "";
            let lastname = document.getElementById("lastName").value || "";
            let dob = document.getElementById("dateOfBirth").value || "";
            let gender = document.getElementById("gender").value || "";
            let fathername = document.getElementById("relationName").value || "";
            let address = document.getElementById("address").value || "";
            let aadharNumber = document.getElementById("aadharNumber").value || "";

            // ✅ Loop OCR blocks (front/back/combined safe)
            response.data.ocr_fields.forEach(ocr => {

                const type = ocr.document_type;

                // FRONT SIDE
                if (type === "aadhaar_front_bottom") {

                    const fullName = ocr.full_name?.value;
                    if (fullName) {
                        firstname = fullName.split(" ")[0] || firstname;
                        lastname = fullName.split(" ").slice(1).join(" ") || lastname;
                    }

                    if (ocr.dob?.value) dob = ocr.dob.value;

                    let g = ocr.gender?.value;
                    if (g) gender = (g === "F") ? "12" : "11";
                }

                // BACK SIDE
                if (type === "aadhaar_back") {

                    if (ocr.care_of?.value)
                        fathername = ocr.care_of.value;

                    if (ocr.address?.value || ocr.zip?.value)
                        address = (ocr.address?.value || "") + " " + (ocr.zip?.value || "");
                }

                // Aadhaar number (common)
                if (ocr.aadhaar_number?.value)
                    aadharNumber = ocr.aadhaar_number.value;
            });

            // ✅ Populate merged values
            document.getElementById("aadharNumber").value = aadharNumber;
            document.getElementById("firstName").value = firstname;
            document.getElementById("lastName").value = lastname;
            document.getElementById("dateOfBirth").value = dob;
            document.getElementById("gender").value = gender;
            document.getElementById("address").value = address;
            document.getElementById("relationName").value = fathername;

            // ✅ Lock fields
            document.getElementById("aadharNumber").readOnly = true;
            document.getElementById("firstName").readOnly = true;
            document.getElementById("dateOfBirth").disabled = true;
            document.getElementById("gender").disabled = true;
            document.getElementById("address").readOnly = true;

            

            hideLoader();

        } else {
            hideLoader();
            document.getElementById("otpError").innerText =
                response.message || "Validation failed";
            document.getElementById("otpMessage").innerText = "";
        }
    };

    xhr.onerror = function () {
        hideLoader();
        document.getElementById("otpError").innerText = "Request failed.";
        document.getElementById("otpMessage").innerText = "";
    };

    xhr.send(formData);
}

function showSelectedFileName() {
    const fileInput = document.getElementById("aadhaarFile");
    const label = document.getElementById("selectedFileName");

    if (fileInput.files.length > 0) {
        label.innerText = "Selected: " + fileInput.files[0].name;
    } else {
        label.innerText = "";
    }
}
function toggleAll(source) {
    document.querySelectorAll(".bulk-check").forEach(cb => {
        cb.checked = source.checked;
    });
}
function bulkApprove(status) {

    const selected = document.querySelectorAll(".bulk-check:checked");

    if (selected.length === 0) {
        alert("Please select at least one record");
        return;
    }

    const records = [];
	const commentsVal = ($("#approvercomments").val() || "").trim();
    selected.forEach(cb => {
        records.push({
            transactionId: cb.dataset.transaction,
            gatePassId: cb.dataset.gatepass,
            gatePassType: cb.dataset.type,
            approverId: $("#userId").val(),
            approverRole: $("#roleName").val(),
            roleId: $("#roleId").val(),
            comments: commentsVal,
            status: status
        });
    });

    showLoader();

    fetch("/CWFM/contractworkmen/bulkApproveGatePass", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(records)
    })
    .then(res => res.text())
    .then(msg => {
        hideLoader();
        sessionStorage.setItem("successMessage", msg);
        loadCommonList('/contractworkmen/blockListFilter', 'Block List');
    })
    .catch(err => {
        hideLoader();
        alert("Bulk approval failed");
        console.error(err);
    });
}

