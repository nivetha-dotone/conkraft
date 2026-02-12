/*Contractor*/
 


  function loadContractorList(contextPath) {
      //  var contextPath = '<%= request.getContextPath() %>'; // This will be evaluated on the server side

        // Construct the URL using the contextPath variable
        var url = contextPath + '/contractor/list';
        console.log("Constructed URL:", url); // Log the constructed URL to the console

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("mainContent").innerHTML = this.responseText;
                  resetSessionTimer();
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }
    
	function searchContractorsBasedOnPE() {
			    var principalEmployerId = $('#principalEmployerId').val();
			    

			    $.ajax({
			        url: '/CWFM/contractor/getAllContractorsBasedOnPE',
			        type: 'POST',
			        data: {
			            principalEmployerId: principalEmployerId
			        },
			        success: function(response) {
			            var tableBody = $('#contractorTable tbody');
						  if ($.fn.DataTable.isDataTable('#contractorTable')) {
							$('#contractorTable').DataTable().destroy();
						}
			            tableBody.empty();
			            if (response.length > 0) {
			                $.each(response, function(index, contr) {
			                    var row = '<tr >' +
										'<td ><input type="checkbox" name="selectedContractorIds" value="' + contr.contractorId + '"></td>'+
			                              '<td >' + contr.contractorCode + '</td>' +
			                              '<td >' + contr.contractorName + '</td>' +
										  '<td >' + contr.contractorAddress + '</td>' +				                             
			                              '</tr>';
			                    tableBody.append(row);
			                });
			            } 						// ✅ Always init after rows are drawn
							initWorkmenTable("contractorTable");
			        },
			        error: function(xhr, status, error) {
			            console.error("Error fetching data:", error);
			        }
			    });
			}
 


function redirectToContrEdit() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to edit.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var unitId = selectedRow.querySelector('[name="selectedContractors"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractor/edit/" + unitId, true);
    xhr.send();
}

function redirectToContrView() {
	var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
	    if (selectedCheckboxes.length !== 1) {
	        alert("Please select exactly one row to view.");
	        return;
	    }
	    
	    var selectedRow = selectedCheckboxes[0].closest('tr');
	    var contractorId = selectedRow.querySelector('[name="selectedContractorIds"]').value;
		var principalEmployerId = $('#principalEmployerId').val();
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/contractor/view/" + contractorId+ "?principalEmployerId=" + principalEmployerId, true);
    xhr.send();
}
 function ContrExportToCSV() {
            var selectedRows = document.querySelectorAll('input[name="selectedContractorIds"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "CODE,NAME,ADDRESS\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "ContractorList.csv");
            document.body.appendChild(link);
            link.click();
        }
        
        function toggleSelectAllContrcators() {
            var selectAllCheckbox = document.getElementById('selectAllCheckbox');
            var checkboxes = document.querySelectorAll('input[name="selectedContractors"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = selectAllCheckbox.checked;
            });
        }
        
         function goBackToContractorList(contextPath,principalEmployerId) {
			  var url = contextPath + "/contractor/list?principalEmployerId=" + principalEmployerId;
    
   $.ajax({
        type: "GET", // Use GET method to fetch the list page
        url:url,// contextPath + "/contractor/list", // URL of the list page
        success: function(response) {
            // Update the main content section with the response
            document.getElementById("mainContent").innerHTML = response;
        },
        error: function(xhr, status, error) {
            console.error("Error loading list page:", error);
            // Handle error if needed
        }
    });// This will navigate back to the previous page in the browser history
}

	
	function getContractor(unitId, userId) {
    if (!unitId) {
        alert("Please select a Principal Employer.");
        return;
    }

    // Fetch contractors
    getContractor(unitId, userId);

   
}
 function addTaskRow() {
	   $('#tabularid tbody').append(`
			   <tr>
			   <td style="width:2%;border-top: thin solid; text-align:center"><button class="btn btn-sucess" onclick="addTaskRow()">+</button></td>
			   <td style="width:2%;border-top: thin solid; text-align:center"><button class="btn btn-danger" onclick="deleteRow(this)">-</button></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><select class="form-control"><option> Work Order1</option><option> Work Order2</option></select></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><select class="form-control"><option>job 1</option><option>job 2</option></select></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><select class="form-control"><option>ESIC</option><option>LL</option><option>WC</option></select></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="text" class="form-control" /></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="text" class="form-control" /></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="date" class="form-control datetimepickerformat1" onclick="initializeDatePicker()" /></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="date" class="form-control datetimepickerformat1" onclick="initializeDatePicker()"/></td>
               <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><input type="file" class="form-control" /></td>
           </tr>
           `);
	        $('.datepicker').datepicker();
   }
   function deleteRow(button) {
    const tableBody = $('#tabularid tbody');
    const rowCount = tableBody.find('tr').length;

    if (rowCount > 1) {
        $(button).closest('tr').remove();
    } else {
        alert('At least one row must remain in the table.');
    }
    }
     /*function generateContractorID() {
        const date = new Date();
        const contractorID = date.getTime(); // Use current timestamp as unique ID
        // Check if contractorID already has a value (set by the server)
        if (!document.getElementById("contractorID").value) {
            document.getElementById("contractorID").value = contractorID; // Set the value if not set by server
        }
    }

    // Call generateContractorID when the page loads
    window.onload = function() {
        generateContractorID();
    }*/
   
 
// Main function to handle redirection
function redirectToContractorView() {
    // Select all checked checkboxes
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');

    // Validate single row selection
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }

    // Retrieve the selected row and its data
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var contractorRenewId = selectedRow.querySelector('[name="selectedWOs"]').value.trim();
    var contractorRequest = selectedRow.querySelector('[name="requestType"]').value.trim();

    console.log("Contractor ID:", contractorRenewId, "Request Type:", contractorRequest);

    // Ensure contractorRenewId is not empty
    if (!contractorRenewId) {
        alert("Invalid contractor ID. Please try again.");
        return;
    }

    // Determine the URL based on the request type (case-sensitive comparison for uppercase and lowercase)
    var url = "";
    if (contractorRequest === "registration" || contractorRequest === "Registration" || contractorRequest === "REGISTRATION") {
        url = "/CWFM/contractor/contractorview/" + contractorRenewId;
    } else if (contractorRequest === "renewal" || contractorRequest === "Renewal" || contractorRequest === "RENEWAL") {
        url = "/CWFM/contractor/contractorRenewalview/" + contractorRenewId;
    } else {
        alert("Invalid request type. Please try again.");
        console.error("Unexpected request type:", contractorRequest);
        return;
    }

    // Perform the AJAX request
    performAjaxRequest(url);
}

// Function to perform the AJAX request and update the page content
function performAjaxRequest(url) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Update the content area with the response
                document.getElementById("mainContent").innerHTML = xhr.responseText;
            } else {
                // Handle errors
                alert("Failed to load content. Please check the server or your request.");
                console.error("Error loading content: ", xhr.status, xhr.statusText, "URL:", url, "Response:", xhr.responseText);
            }
        }
    };
    xhr.open("GET", url, true);
    xhr.send();
}





 function exportToCSVFormat() {
            var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "CONTRACTORREGID,VENDORCODE,CONTRACTORNAME,STATUS,REQUESTTYPE\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "ContractorRegistration.csv");
            document.body.appendChild(link);
            link.click();
        }
function exportCSVFormat() {
            var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
             csvContent += "CONTRACTORREGID,VENDORCODE,CONTRACTORNAME,STATUS,REQUESTTYPE\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "ContractorRenewal.csv");
            document.body.appendChild(link);
            link.click();
        }
        
 function saveRoleDetails(){
	
	
	const data = {
		firstName:$("#firstname").val().trim,
		lastName:$("#lastname").val().trim,
		userId:$("#userid").val().trim,
		emailId:$("#email").val.trim,
		contactNumber:$("#mobilenumber").val().trim,
		password:$("#password").val().trim,
		roleName:$("#role").val(),
	}
	
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractor/saveRole", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
		  
         loadCommonList('/contractor/role', 'Role');
       } else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
       }
   };
   
   xhr.onerror = function() {
       console.error("Request failed");
   };
   
   // Send the data object as a JSON string
   xhr.send(JSON.stringify(data));
   
	}
	function validateContractorFiles(aadharFile, policeFile,pfFile) {
	    let valid = true;

	    // Aadhaar file presence and size check
	    if (!aadharFile) {
	        $("#error-aadharDoc").text("Aadhar file is required.").show();
	        valid = false;
	    } else if (aadharFile.size > 5 * 1024 * 1024) {
	        $("#error-aadharDoc").text("Aadhar file must be less than 5 MB.").show();
	        valid = false;
	    } else {
	        $("#error-aadharDoc").hide(); // Clear error if valid
	    }

	    // Police file presence and size check
	    if (!policeFile) {
	        $("#error-panDoc").text("PAN file is required.").show();
	        valid = false;
	    } else if (policeFile.size > 5 * 1024 * 1024) {
	        $("#error-panDoc").text("PAN file must be less than 5 MB.").show();
	        valid = false;
	    } else {
	        $("#error-panDoc").hide(); // Clear error if valid
	    }
      if (!pfFile) {
	        $("#error-pfDoc").text("PF file is required.").show();
	        valid = false;
	    } else if (pfFile.size > 5 * 1024 * 1024) {
	        $("#error-pfDoc").text("PF file must be less than 5 MB.").show();
	        valid = false;
	    } else {
	        $("#error-pfDoc").hide(); // Clear error if valid
	    }
	    return valid;
	}

	  
function validateFormData() {
    let isValid = true;
    const principalemployer = $("#principalEmployerId").val();
    if (principalemployer === "") {
        $("#error-principalemployer").show();
        isValid = false;
    }else{
		$("#error-principalemployer").hide();
	}
    const vendorCode = $("#vendorCodeId").val().trim();
    if (vendorCode === "") {
        $("#error-vendorCode").show();
        isValid = false;
    }else{
		 $("#error-vendorCode").hide();
	}
    const managername = $("#managerNameId").val().trim();
    const nameRegex = /^[A-Za-z\s]+$/;
    if (!nameRegex.test(managername)) {
                 $("#error-managername").show();
        			isValid = false;
     }else{
		 $("#error-managername").hide();
	 }
	 const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	 const email = $("#emailId").val().trim();
	 if (!emailRegex.test(email)) {
	                 $("#error-email").show();
	        			isValid = false;
	     }else{
	 	 $("#error-email").hide();
	  }
	  const mobileInput = $("#mobileId").val().trim();
	  	const mobileNumberRegex = /^[6-9]\d{9}$/;
	  	if (!mobileNumberRegex.test(mobileInput)) {
	                   $("#error-mobile").show();
	          			isValid = false;
	       }else{
	  		 $("#error-mobile").hide();
	  	 }
		 const aadharNumber = $("#aadharId").val().trim();
		     if (aadharNumber === "" || aadharNumber.length !== 12 || isNaN(aadharNumber)) {
		         $("#error-aadhar").show();
		         isValid = false;
		     }else{
		 		  $("#error-aadhar").hide();
		 	}
			const aadharDoc = $("#aadharDocId")[0]?.files?.[0] || null;
			const panDoc = $("#panDocId")[0]?.files?.[0] || null;
			const pfDoc = $("#pfDocId")[0]?.files?.[0] || null;
	const isFileValid = validateContractorFiles(aadharDoc, panDoc,pfDoc);
	console.log("file is:"+isFileValid);
				
				   if (!isFileValid) {
				          isValid = false; // Stop the upload if validation fails
				      }
				  const pan = $("#panId").val().trim();
				     if (pan === "") {
				         $("#error-pan").show();
				         isValid = false;
				     }else{
				  	 $("#error-pan").hide();
				  }
					  const gst = $("#gstId").val().trim();
				  			     if (gst === "") {
				  			         $("#error-gst").show();
				  			         isValid = false;
				  			     }else{
				  			  	 $("#error-gst").hide();
				  			  }
							  const address = $("#addressId").val().trim();
							  				  			     if (address === "") {
							  				  			         $("#error-address").show();
							  				  			         isValid = false;
							  				  			     }else{
							  				  			  	 $("#error-address").hide();
							  				  			  }		
														  	  
	 const contractorName = $("#contractorNameId").val().trim();
	 if (!nameRegex.test(contractorName)) {
	                  $("#error-contractorname").show();
	         			isValid = false;
	      }else{
	 		 $("#error-contractorname").hide();
	 	 }
    const locofwork = $("#locofWorkId").val();
     const locofworkRegex = /^[A-Za-z\s]+$/;
     if (!locofworkRegex.test(locofwork)) {
                 $("#error-locofwork").show();
        			isValid = false;
     }else{
		 $("#error-locofwork").hide();
	 }
    const totalStrength = $("#totalStrengthId").val().trim();
    const totalStrengthRegex = /^[0-9]+$/;
    if (!totalStrengthRegex.test(totalStrength)) {
                 $("#error-totalStrength").show();
        			isValid = false;
     }else{
		 $("#error-totalStrength").hide();
	 }
	const rcmaxemployees = $("#rcMaxEmpId").val().trim();
    const rcmaxemployRegex = /^[0-9]+$/;
    if (!rcmaxemployRegex.test(rcmaxemployees)) {
                 $("#error-rcmaxemployees").show();
        			isValid = false;
     }else{
		 $("#error-rcmaxemployees").hide();
	 }
	
	const pfInput = $("#pfNumId").val().trim();
	const pfNumberRegex = /^[A-Z]{2}[0-9]{5}[A-Z]{1}[0-9]{4}$/; 
	if (!pfNumberRegex.test(pfInput)) {
                 $("#error-pfnumber").show();
        			isValid = false;
     }else{
		 $("#error-pfnumber").hide();
	 }
	const natureOfWork = $("#natureOfWorkId").val();
	 if (!locofworkRegex.test(natureOfWork)) {
        $("#error-natureOfWork").show();
        isValid = false;
    }else{
		$("#error-natureOfWork").hide();
	}
	/*const contractFrom = $("#contractFromId").val();
	 if (contractFrom === "" || contractFrom <= today) {
        $("#error-contractFrom").show();
        isValid = false;
    }else{
		$("#error-contractFrom").hide();
	}
	const contractTo = $("#contractToId").val();
	 if (contractTo === "" || contractTo <= today || contractTo <= contractFrom) {
        $("#error-contractTo").show();
        isValid = false;
    }else{
		$("#error-contractTo").hide();
	}*/
	
// Get today's date in yyyy-mm-dd format
const today = new Date().toISOString().split("T")[0];

const contractFrom = $("#contractFromId").val();
const contractTo = $("#contractToId").val();

/* --------------------------------
   Validate Contract From
----------------------------------*/
if (!contractFrom || contractFrom.trim() === "") {
    $("#error-contractFrom").show();
    isValid = false;
} 
else if (contractFrom < today) {
    // Past date is invalid, today & future allowed
    $("#error-contractFrom").text("Contract From must be today or a future date").show();
    isValid = false;
} 
else {
    $("#error-contractFrom").hide();
}

/* --------------------------------
   Validate Contract To
----------------------------------*/
if (!contractTo || contractTo.trim() === "") {
    $("#error-contractTo").show();
    isValid = false;
} 
else if (contractTo < today) {
    // Past date invalid
    $("#error-contractTo").text("Contract To must be today or a future date").show();
    isValid = false;
}
else if (contractTo <= contractFrom) {
    // Must be strictly after Contract From
    $("#error-contractTo").text("Contract To must be after Contract From").show();
    isValid = false;
}
else {
    $("#error-contractTo").hide();
}

const pfApplyDate = $("#pfApplyDateId").val();
	 if (!pfApplyDate || pfApplyDate.trim() == "") {
    $("#error-pfApplyDate").text("PF Apply Date is required").show();
    isValid = false;
} 
else if (pfApplyDate > today) {
    // today or future is invalid — must be strictly past
    $("#error-pfApplyDate").text("PF Apply Date must be a past date").show();
    isValid = false;
} 
else {
    $("#error-pfApplyDate").hide();
}
	const contractType = $("#contractTypeId").val();
	 if (contractType === "") {
        $("#error-contractType").show();
        isValid = false;
    }else{
		$("#error-contractType").hide();
	}
	const isRcVerified = $("#rcVerifiedId").val();
	 if (isRcVerified === "") {
        $("#error-isRcVerified").show();
        isValid = false;
    }else{
		$("#error-isRcVerified").hide();
	}
    return isValid;

}
function saveRenewDetails(){
	
	const data = {
		contractorRenewId:$("#renewalid").val(),
		unitCode:$("#unitcode").val(),
		organization:$("#organization").val(),
		contractorCode:$("#contractorcode").val().trim(),
		contractorName:$("#contractorname").val().trim(),
		managerName:$("#managername").val().trim(),
		locationOfWork:$("#locationofwork").val().trim(),
		supervisorName:$("#supervisorname").val().trim(),
		pfCode:$("#pfcode").val().trim(),
		emailAdd:$("#emailaddress").val(),
		mobileNumber:$("#mobilenumber").val(),
		esicReg:$("#esicregistration").val(),
		contractorValidTo:$("#contractvalidtill").val().trim(),
		contractorClass:$("#contractorclass").val().trim(),
		contractorType:$("#contractortype").val().trim(),
		/*documentType:$("#documentDropId").val().trim(),
		documentNumber:$("#documentnumber").val().trim(),
		coverage:$("#coverage").val().trim(),
		validFrom:$("#validfrom").val().trim(),
		validTo:$("#validto").val().trim(),
		attachment:$("#attachments").val().trim(),
		panIndia:$("#panindia").val().trim(),
		subContractor:$("#subcontractor").val().trim(),
		*/
	};
	console.log(data);
		  const xhr = new XMLHttpRequest();
   xhr.open("POST", "/CWFM/contractor/saveRenew", true); // Replace with your actual controller URL
   xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
   xhr.onload = function() {
       if (xhr.status === 200) {
           // Handle successful response
           console.log("Data saved successfully:", xhr.responseText);
		  
         loadCommonList('/contractor/contRegList', 'Contractor Renewal');
       } else {
           // Handle error response
           console.error("Error saving data:", xhr.statusText);
       }
   };
   
   xhr.onerror = function() {
       console.error("Request failed");
   };
   
   // Send the data object as a JSON string
   xhr.send(JSON.stringify(data));
	}//eofunc
function validateRenewFormData() {
    let isValid = true;
    const renewalid = $("#renewalid").val();
    if (renewalid === "") {
        $("#error-renewalid").show();
        isValid = false;
    }else{
		$("#error-renewalid").hide();
	}
    const unitcode = $("#unitcode").val().trim();
    if (unitcode === "") {
        $("error-unitcode").show();
        isValid = false;
    }else{
		 $("error-unitcode").hide();
	}
    const organization = $("#organization").val().trim();
    const organizationRegex = /^[A-Za-z\s]+$/;
    if (!organizationRegex.test(organization)) {
                 $("#error-organization").show();
        			isValid = false;
     }else{
		 $("#error-organization").hide();
	 }
    const contractorcode = $("#contractorcode").val();
     const contractorcodeRegex = /^[0-9]+$/;
     if (!contractorcodeRegex.test(contractorcode)) {
                 $("#error-contractorcode").show();
        			isValid = false;
     }else{
		 $("#error-contractorcode").hide();
	 }
    const contractorname = $("#contractorname").val().trim();
    const contractornameRegex = /^[A-Za-z\s]+$/;
    if (!contractornameRegex.test(contractorname)) {
                 $("#error-contractorname").show();
        			isValid = false;
     }else{
		 $("#error-contractorname").hide();
	 }
	const managername = $("#managername").val().trim();
    const managernameRegex = /^[A-Za-z\s]+$/;
    if (!managernameRegex.test(managername)) {
                 $("#error-managername").show();
        			isValid = false;
     }else{
		 $("#error-managername").hide();
	 }
	
	const locationofwork = $("#locationofwork").val().trim();
	const locationofworkRegex = /^[A-Za-z\s]+$/; 
	if (!locationofworkRegex.test(locationofwork)) {
                 $("#error-locationofwork").show();
        			isValid = false;
     }else{
		 $("#error-locationofwork").hide();
	 }
	const supervisorname = $("#supervisorname").val();
	const supervisornameRegex = /^[A-Za-z\s]+$/; 
	if (!supervisornameRegex.test(supervisorname)) {
                 $("#error-supervisorname").show();
        			isValid = false;
     }else{
		 $("#error-supervisorname").hide();
	 }
	 const pfInput = $("#pfcode").val().trim();
	const pfNumberRegex = /^[A-Z]{2}[0-9]{5}[A-Z]{1}[0-9]{4}$/; 
	if (!pfNumberRegex.test(pfInput)) {
                 $("#error-pfcode").show();
        			isValid = false;
     }else{
		 $("#error-pfcode").hide();
	 }
	 
	const email = $("#emailaddress").val().trim();
	const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$/; 
	if (!emailRegex.test(email)) {
                 $("#error-emailaddress").show();
        			isValid = false;
     }else{
		 $("#error-emailaddress").hide();
	 }
	
	const mobile = $("#mobilenumber").val().trim();
	const mobilenumberRegex = /^[6-9]\d{9}$/; 
	if (!mobilenumberRegex.test(mobile)) {
                 $("#error-mobilenumber").show();
        			isValid = false;
     }else{
		 $("#error-mobilenumber").hide();
	 }
	 
	const esicregistration = $("#esicregistration").val().trim();
	const esicregistrationRegex = /^[A-Za-z0-9]*\\d{10}[A-Za-z0-9]*$/; 
	if (!esicregistrationRegex.test(esicregistration)) {
                 $("#error-esicregistration").show();
        			isValid = false;
     }else{
		 $("#error-esicregistration").hide();
	 }
	const contractvalidtill = $("#contractvalidtill").val();
	 if (contractvalidtill === "") {
        $("#error-contractvalidtill").show();
        isValid = false;
    }else{
		$("#error-contractvalidtill").hide();
	}
	const classification = $("#contractorclass").val();
	const classificationRegex = /^[A-Za-z\s]+$/; 
	if (!classificationRegex.test(classification)) {
                 $("#error-contractorclass").show();
        			isValid = false;
     }else{
		 $("#error-contractorclass").hide();
	 }
	const contractortype = $("#contractortype").val();
	const contractortypeRegex = /^[A-Za-z\s]+$/; 
	if (!contractortypeRegex.test(contractortype)) {
                 $("#error-contractortype").show();
        			isValid = false;
     }else{
		 $("#error-contractortype").hide();
	 }
	
    return isValid;
}

	
	
	function getAllContractorsForReg(unitId) {
	    var xhr = new XMLHttpRequest();
	    var url = contextPath + "/contractor/getAllContractors?unitId=" + unitId ;
	    //alert("URL: " + url);
	    xhr.open("GET", url, true);

	    xhr.onload = function() {
	        if (xhr.status === 200) {
	            // Parse the response as a JSON array of contractor objects
	            var contractors = JSON.parse(xhr.responseText);
	            console.log("Response:", contractors);
	            
	            // Find the contractor select element
	            var contractorSelect = document.getElementById("vendorCodeId");
	            
	            // Clear existing options
	            contractorSelect.innerHTML = '<option value="">Please select Vendor Code</option>';
	            
	            // Populate the dropdown with the new list of contractors
	            contractors.forEach(function(contractor) {
	                var option = document.createElement("option");
	                option.value = contractor.contractorId;
	                option.text =contractor.contractorCode+" | "+ contractor.contractorName;
					option.setAttribute("data-code", contractor.contractorCode);
					option.setAttribute("data-name", contractor.contractorName);
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
	
	function setValueIfPresent(elementId, value) {
	    var input = document.getElementById(elementId);
	    input.value = value || "";
	}
	
	function getAllContractorDetailForReg(contractorId) {
		var principalEmployerSelect = document.getElementById("principalEmployerId");
		   var unitId = principalEmployerSelect.value;
		    var xhr = new XMLHttpRequest();
		    var url = contextPath + "/contractor/getAllContractorDetail?unitId=" + unitId + "&contractorId=" + contractorId;
		    //alert("URL: " + url);
		    xhr.open("GET", url, true);

		    xhr.onload = function() {
		        if (xhr.status === 200) {
		            // Parse the response as a JSON array of contractor objects
		            var contractors = JSON.parse(xhr.responseText);
		            console.log("Response:", contractors);
					
					setValueIfPresent("managerNameId", contractors.managerName);
					setValueIfPresent("locofWorkId", contractors.locationOfWork);
					setValueIfPresent("totalStrengthId", contractors.totalStrength);
					setValueIfPresent("rcMaxEmpId", contractors.maxNoEmp);
					setValueIfPresent("pfNumId", contractors.pfNum);
					setValueIfPresent("natureOfWorkId", contractors.natureOfWork);
					setValueIfPresent("contractFromId", contractors.validFrom);
					setValueIfPresent("contractToId", contractors.validTo);
				
		        } else {
		            console.error("Error:", xhr.statusText);
		        }
		    };

		    xhr.onerror = function() {
		        console.error("Request failed");
		    };

		    xhr.send();
		}
		
		function redirectToContractorReg() {
		   
		    var xhr = new XMLHttpRequest();
		    xhr.onreadystatechange = function() {
		        if (xhr.readyState == 4 && xhr.status == 200) {
		            document.getElementById("mainContent").innerHTML = xhr.responseText;
		            setDateRange();
		        }
		    };
		    xhr.open("GET", "/CWFM/contractor/contReg" , true);
		    xhr.send();
		}
		
		function redirectToContractorRegView() {
		    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
		    if (selectedCheckboxes.length !== 1) {
		        alert("Please select exactly one row to view.");
		        return;
		    }
		    
		    var selectedRow = selectedCheckboxes[0].closest('tr');
		    var contractorregId = selectedRow.querySelector('[name="selectedWOs"]').value;

		    var xhr = new XMLHttpRequest();
		    xhr.onreadystatechange = function() {
		        if (xhr.readyState == 4 && xhr.status == 200) {
		            document.getElementById("mainContent").innerHTML = xhr.responseText;
		        }
		    };
		    xhr.open("GET", "/CWFM/contractor/contractorview/" + contractorregId, true);
		    xhr.send();
		}

		
	function saveRegistrationDetails() {
    let basicValid = validateFormData();
    if (!basicValid) return;

    let otherFields = validateDocuments();
    if (!otherFields) return;

    const data = new FormData();
    const aadharFile = $("#aadharDocId").prop("files")[0];
    const panFile = $("#panDocId").prop("files")[0];
    const pfFile = $("#pfDocId").prop("files")[0];
    const selectedOption = $("#vendorCodeId option:selected");
    const selectedPEOption = $("#principalEmployerId option:selected");
    // ✅ Utility function for Capital Case
    function toCapitalCase(str) {
        return str
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }

    // ✅ Capital case transformation
    const contractorName = toCapitalCase($("#contractorNameId").val().trim());
    const managerName = toCapitalCase($("#managerNameId").val().trim());
    const locofWork = toCapitalCase($("#locofWorkId").val().trim());
    //const pan = toCapitalCase($("#panId").val().trim());
    const natureOfWork = toCapitalCase($("#natureOfWorkId").val().trim());
    const address = toCapitalCase($("#addressId").val().trim());
	let mainContractor;
	const contractorType = document.getElementById("contractTypeId").value;
	if(contractorType === "Main Contractor"){
		mainContractor = $("#vendorCodeId").val();
	}else{
		mainContractor =  $("#mainContractorId").val().trim();
	}

    // Build the JSON object
    const jsonData = {
        contractorregId: $("#contractorregId").val().trim(),
        principalEmployer: $("#principalEmployerId").val(),
        unitId: selectedPEOption.val(),
        contractorId: selectedOption.val(),
        vendorCode: selectedOption.data("code"),
        contractorName: contractorName,
        email: $("#emailId").val().trim(),
        mobile: $("#mobileId").val().trim(),
        aadhar: $("#aadharId").val().trim(),
        pan: $("#panId").val().trim().toUpperCase(),
        gst: $("#gstId").val().trim().toUpperCase(),
        address: address,
        pfApplyDate: $("#pfApplyDateId").val().trim(),
        managerName: managerName,
        locofWork: locofWork,
        totalStrength: $("#totalStrengthId").val().trim(),
        rcMaxEmp: $("#rcMaxEmpId").val().trim(),
        pfNum: $("#pfNumId").val().trim().toUpperCase(),
        natureOfWork: natureOfWork,
        contractFrom: $("#contractFromId").val(),
        contractTo: $("#contractToId").val(),
        contractType: $("#contractTypeId").val(),
        rcVerified: $("#rcVerifiedId").is(":checked") ? "Yes" : "No",
        mainContractor:mainContractor,
        status: "1",
        requestType: "Create",
        regPolicy: []
    };

    // Loop through each work order/policy row
    $("#workOrderBody tr").each(function () {
        const row = $(this);

		const woSelect = row.find(".woNumber");

		    const woId = woSelect.val();
		    const woName = woSelect.find("option:selected").data("woname");

		    const policy = {
		    woNumber: woId,        // existing (ID)
		    sapWoNumber: woName,        // ⭐ NEW
            natureOfJob: row.find(".natureOfJob").val(),
            documentType: row.find(".documentType").val(),
            documentNumber: row.find(".documentNumber").val(),
            coverage: row.find(".coverage").val(),
            validFrom: row.find(".validFrom").val(),
            validTo: row.find(".validTo").val()
        };

        jsonData.regPolicy.push(policy);

        const fileInput = row.find(".attachment")[0];
        if (fileInput && fileInput.files.length > 0) {
            data.append("policyAttachments", fileInput.files[0]);
        } else {
            data.append("policyAttachments", new Blob([])); // Placeholder
        }
    });

    if (aadharFile) {
        data.append("aadharFile", aadharFile);
    }
    if (panFile) {
        data.append("panFile", panFile);
    }
     if (pfFile) {
        data.append("pfFile", pfFile);
    }

    // Append JSON data
    data.append("jsonData", JSON.stringify(jsonData));

    // Debug log
    for (const [key, value] of data.entries()) {
        console.log(key, value instanceof File ? value.name || "Empty file" : value);
    }

    // AJAX call
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/contractor/saveReg", true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            alert("Contractor saved successfully!");
            loadCommonList('/contractor/contRegList', 'Contractor Master');
        } else {
            alert("Failed to save contractor.");
            console.error("Error:", xhr.status, xhr.responseText);
        }
    };

    xhr.onerror = function () {
        alert("Error occurred while saving contractor.");
    };

    xhr.send(data);
}

// Row buttons event handler
document.addEventListener('click', function (e) {
    if (e.target.classList.contains('addRow')) {
        addRowCont();
         setDateRange();   // ✅ IMPORTANT
    } else if (e.target.classList.contains('removeRow')) {
        deleteRowCont(e.target);
    }
});


	
			
	

		function deleteRowCont(buttonElement) {
		    const row = buttonElement.closest('tr');
		    const tbody = document.getElementById("workOrderBody");

		    if (tbody.rows.length > 1) {
		        row.remove();
		    } else {
		        alert("At least one row must be present.");
		    }
		}
		
		
		
		let globalWorkOrders = [];
		
		function toggleMainContractorRow() {
		    const contractorType = document.getElementById("contractTypeId").value;
		    const mainContractorRow = document.getElementById("mainContractorRow");
		    const principalEmployerSelect = document.getElementById("principalEmployerId");
		    const unitId = principalEmployerSelect.value;

		    // Clear existing work orders in the global array
		    globalWorkOrders = [];

		    // Clear the work order dropdown for all rows
		    const woSelects = document.querySelectorAll('.woNumber');
		    woSelects.forEach(select => select.innerHTML = '<option value="">Select</option>');

		    if (contractorType === "Sub Contractor") {
		        mainContractorRow.style.display = "table-row";

		        const xhr = new XMLHttpRequest();
		        const url = contextPath + "/contractor/getAllContractors?unitId=" + unitId;

		        xhr.open("GET", url, true);
		        xhr.onload = function() {
		            if (xhr.status === 200) {
		                const contractors = JSON.parse(xhr.responseText);
		                const contractorSelect = document.getElementById("mainContractorId");
		                contractorSelect.innerHTML = '<option value="">Please select Main Contractor</option>';

		                contractors.forEach(function(contractor) {
		                    const option = document.createElement("option");
		                    option.value = contractor.contractorId;
		                    option.text = contractor.contractorCode + " | " + contractor.contractorName;
		                    contractorSelect.appendChild(option);
		                });

		                // Add an onchange listener for when a main contractor is selected
		                contractorSelect.onchange = function() {
		                    const selectedContractorId = contractorSelect.value;
		                    if (selectedContractorId) {
		                        // Fetch the work orders for the selected main contractor
		                        getAllWorkorderForReg(selectedContractorId);
		                    }
		                };
		            } else {
		                console.error("Error fetching contractors:", xhr.statusText);
		            }
		        };

		        xhr.onerror = function() {
		            console.error("Request failed");
		        };

		        xhr.send();
		    } else if (contractorType === "Main Contractor") {
		        mainContractorRow.style.display = "none";

		        // Fetch and update work orders for the selected Main Contractor
		        const contractorId = $("#vendorCodeId").val();
		        if (contractorId === "") {
		           // 
		        }else{
					getAllWorkorderForReg(contractorId);
				}
		    }
		}


		function fetchWorkOrders(contractorId) {
		    const principalEmployerSelect = document.getElementById("principalEmployerId");
		    const unitId = principalEmployerSelect.value;
		    const xhr = new XMLHttpRequest();
		    const url = contextPath + "/contractor/getAllWorkorders?unitId=" + unitId + "&contractorId=" + contractorId;

		    xhr.open("GET", url, true);
		    xhr.onload = function() {
		        if (xhr.status === 200) {
		            // Store the fetched work orders globally
		            globalWorkOrders = JSON.parse(xhr.responseText);
		            console.log("Work Orders Updated:", globalWorkOrders);
					
		        } else {
		            console.error("Error fetching work orders:", xhr.statusText);
		        }
		    };

		    xhr.onerror = function() {
		        console.error("Request failed");
		    };

		    xhr.send();
		}


		function addRowCont() {
		    const tbody = document.getElementById("workOrderBody");
		    const today = new Date().toISOString().split('T')[0];

		    // Create a new table row
		    const row = document.createElement("tr");

		    // Generate the Work Order options from the global array
		  /*  let workOrderOptions = '<option value="">Select</option>';
		    globalWorkOrders.forEach(workOrder => {
		        workOrderOptions += `<option value="${workOrder.workorderId}">${workOrder.name}</option>`;
		    });*/
			
			// Generate the Work Order options from the global array
			let workOrderOptions = '<option value="">Select</option>';

			globalWorkOrders.forEach(workOrder => {
			    workOrderOptions += `
			        <option 
			            value="${workOrder.workorderId}" 
			            data-woname="${workOrder.name}">
			            ${workOrder.name}
			        </option>`;
			});


		    row.innerHTML = `
		        <td><button type="button" class="btn btn-success addRow" style="color:blue;background-color:white;">+</button></td>
		        <td><button type="button" class="btn btn-danger removeRow" style="color:blue;background-color:white;">−</button></td>
		        <td>
		            <select class="form-control woNumber" name="workOrderNumber">
		                ${workOrderOptions}
		            </select>
		        </td>
		        <td><input type="text" class="form-control natureOfJob" name="natureOfJob" /></td>
		        <td></td> <!-- placeholder for the cloned documentType select -->
		        <td><input type="text" class="form-control documentNumber" name="documentNumber" /></td>
		        <td><input type="number" class="form-control coverage" name="coverage" min="0" step="1" /></td>
		        <td><input type="text" class="form-control validFrom expirydatetimepicker" name="validFrom" min="${today}" /></td>
		        <td><input type="text" class="form-control validTo expirydatetimepicker" name="validTo" min="${today}" /></td>
		        <td><input type="file" class="form-control attachment" name="attachment" accept="application/pdf"/></td>
		    `;

			// Clone the documentType dropdown from the first row
				    const originalDropdown = document.querySelector('#workOrderBody tr:first-child select.documentType');
				    const clonedDropdown = originalDropdown.cloneNode(true);
				    clonedDropdown.classList.add("documentType");
				    clonedDropdown.name = "documentType";

				    // Insert the cloned dropdown into the correct <td>
				    row.cells[4].appendChild(clonedDropdown);
					
		    // Append the row to the tbody
		    tbody.appendChild(row);
		}
		
		function getAllWorkorderForReg(contractorId) {
		    const principalEmployerSelect = document.getElementById("principalEmployerId");
		    const unitId = principalEmployerSelect.value;
		    const xhr = new XMLHttpRequest();
		    const url = contextPath + "/contractor/getAllWorkorders?unitId=" + unitId + "&contractorId=" + contractorId;

		    xhr.open("GET", url, true);
		    xhr.onload = function() {
		        if (xhr.status === 200) {
		            // Store the fetched work orders globally
		            globalWorkOrders = JSON.parse(xhr.responseText);
		            console.log("Work Orders Updated:", globalWorkOrders);

		            // Clear existing work order dropdowns before populating new ones
		            const woSelects = document.querySelectorAll('.woNumber');
		            woSelects.forEach(select => {
		                select.innerHTML = '<option value="">Select</option>'; // Clear previous options
		            });

		            // Populate the work order dropdown with new values
		            globalWorkOrders.forEach(workOrder => {
		                const option = document.createElement("option");
		                option.value = workOrder.workorderId;
		                option.text = workOrder.name;
						option.setAttribute("data-woname", workOrder.name);
		                // Append the options to all work order dropdowns
		                woSelects.forEach(select => {
		                    select.appendChild(option);
		                });
		            });
		        } else {
		            console.error("Error fetching work orders:", xhr.statusText);
		        }
		    };

		    xhr.onerror = function() {
		        console.error("Request failed");
		    };

		    xhr.send();
		}
		
		function validateDocuments() {
		    const rows = document.querySelectorAll('#workOrderBody tr');
		    const rcVerified = document.querySelector('#rcVerifiedId')?.checked || false;
		    const today = new Date();
		    const workOrderMap = {};      // Tracks document types per work order
		    const docNumberMap = {};      // Tracks doc numbers per work order
		    const allDocNumbers = new Set(); // Tracks all doc numbers globally
		    const workOrderDisplayMap = {};
		    let messages = [];

		    rows.forEach((row, index) => {
		        const rowNum = index + 1;
		        const workOrderSelect = row.querySelector('.woNumber');
		        const workOrderId = workOrderSelect?.value?.trim();
		        const workOrderName = workOrderSelect?.selectedOptions[0]?.text?.trim();
		        const docType = row.querySelector('.documentType')?.selectedOptions[0]?.text?.trim();
		        const docNumber = row.querySelector('.documentNumber')?.value?.trim();
		        const validFromVal = row.querySelector('.validFrom')?.value;
		        const validToVal = row.querySelector('.validTo')?.value;
		        const validFrom = validFromVal ? new Date(validFromVal) : null;
		        const validTo = validToVal ? new Date(validToVal) : null;
		        const fileInput = row.querySelector('.attachment');

		        if (!workOrderId || !docType) return;

		        const isFilePresent = fileInput?.files?.length > 0;
		        const isDocNumberPresent = !!docNumber;

		        workOrderDisplayMap[workOrderId] = workOrderName;

		        // Validation messages
		        if (!isDocNumberPresent) {
		            messages.push(`Row ${rowNum}: Document number is required.`);
		        }

		        if (!isFilePresent) {
		            messages.push(`Row ${rowNum}: File attachment is required.`);
		        }

		        if (!validFrom || validFrom <= today) {
		            messages.push(`Row ${rowNum}: "Valid From" must be a future date.`);
		        }

		        if (!validTo || validTo <= today || validTo <= validFrom) {
		            messages.push(`Row ${rowNum}: "Valid To" must be after "Valid From" and a future date.`);
		        }

		        if (isFilePresent && fileInput.files[0].size > 3 * 1024 * 1024) {
		            const file = fileInput.files[0];
		            messages.push(`Row ${rowNum}: File "${file.name}" is ${(file.size / (1024 * 1024)).toFixed(2)} MB. Maximum allowed size is 3 MB.`);
		        }

		        // Only proceed if document number and file are present
		        if (isDocNumberPresent && isFilePresent) {
		            if (!workOrderMap[workOrderId]) workOrderMap[workOrderId] = new Set();
		            if (!docNumberMap[workOrderId]) docNumberMap[workOrderId] = new Set();

		            // Duplicate document type per work order
		            if (workOrderMap[workOrderId].has(docType)) {
		                messages.push(`Row ${rowNum}: Duplicate document type "${docType}" for work order "${workOrderName}".`);
		            } else {
		                workOrderMap[workOrderId].add(docType);
		            }

		            // Duplicate document number per work order
		            if (docNumberMap[workOrderId].has(docNumber)) {
		                messages.push(`Row ${rowNum}: Duplicate document number "${docNumber}" within work order "${workOrderName}".`);
		            } else {
		                docNumberMap[workOrderId].add(docNumber);
		            }

		            // Global duplicate document number
		            if (allDocNumbers.has(docNumber)) {
		                messages.push(`Row ${rowNum}: Document number "${docNumber}" is already used in another work order.`);
		            } else {
		                allDocNumbers.add(docNumber);
		            }
		        }
		    });

		    if (Object.keys(workOrderMap).length === 0) {
		        messages.push("At least one valid work order with complete document details is required.");
		    }

		    // ESIC or WC check
		    for (const workOrderId in workOrderMap) {
		        const docSet = workOrderMap[workOrderId];
		        const displayName = workOrderDisplayMap[workOrderId] || workOrderId;
		        if (!(docSet.has('ESIC') || docSet.has('WC'))) {
		            messages.push(`Work order "${displayName}" must include either ESIC or WC.`);
		        }
		    }

		    // RC check
		    if (rcVerified) {
		        const hasRC = Object.values(workOrderMap).some(set => set.has('RC'));
		        if (!hasRC) {
		            messages.push(`RC document is required since RC Verified is checked.`);
		        }
		    }

		    const messageContainer = document.getElementById("validationMessages");
		    if (messages.length > 0) {
		        messageContainer.innerHTML = `
		            <ul style="list-style: disc; padding-left: 20px; color: red;">
		                ${messages.map(msg => `<li>${msg}</li>`).join('')}
		            </ul>
		        `;
		        return false;
		    } else {
		        messageContainer.innerHTML = ''; // Clear previous messages
		        return true;
		    }
		}
		
		function viewContractorFile(contregId, userId, docName) {
    // Prepare secure JSON data
    const data = { contregId, userId, docName };

    // Encode using Base64 (URL-safe)
    const encodedData = btoa(JSON.stringify(data));

    // Build masked endpoint URL
    const url = `/CWFM/contractor/viewFile/${encodedData}`;

    // Fetch the file securely
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("File not found or server error");
            }
            return response.blob();
        })
        .then(blob => {
            // Create a temporary URL for the blob
            const blobUrl = window.URL.createObjectURL(blob);
            
            // Open in new tab securely
            window.open(blobUrl, '_blank');
            
            // Revoke blob after some time
            setTimeout(() => URL.revokeObjectURL(blobUrl), 5000);
        })
        .catch(err => {
            console.error("Error opening file:", err);
            alert("Unable to open file.");
        });
}
 function toggleSelectAll() {
            var selectAllCheckbox = document.getElementById('selectAllCheckbox');
            var checkboxes = document.querySelectorAll('input[name="selectedWOs"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = selectAllCheckbox.checked;
            });
        }