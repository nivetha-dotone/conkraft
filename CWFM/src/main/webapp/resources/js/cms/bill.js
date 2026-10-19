function exportCSVFormat() {
            var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "TRANSACTIONID,UNITCODE,VENDORCODE,CONTRACTORNAME,WORKORDERNUMBER,BILLSTARTDATE,BILLENDDATE,STATUS,BILLCATEGORY,LASTAPPROVER,NEXTAPPROVER\n"; // Add headers here
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
            link.setAttribute("download", "BillVerification.csv");
            document.body.appendChild(link);
            link.click();
        }
    function redirectToBillView() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var transactionId = selectedRow.querySelector('[name="selectedWOs"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/billVerification/view/" + transactionId , true);
    xhr.send();
}   
function downloadDocTemp(gatePassId, userId, docType) {
    const baseUrl = '/CWFM/billVerification/downloadFile';
    
    // Construct the URL based on gatePassId, userId, and docType
    const url = `${baseUrl}/${docType}`;

    // Create a temporary anchor element
    const a = document.createElement('a');
    a.href = url;
    a.download = ''; // This attribute tells the browser to download the file

    // Append to the body to make it work in Firefox
    document.body.appendChild(a);

    // Programmatically click the anchor
    a.click();

    // Remove the anchor from the document
    document.body.removeChild(a);
}

// Toggle accordion section
function toggleSection(id) {
    $(".accordion-content").not("#" + id).slideUp();
    $("#" + id).slideToggle();
}
/////////////////////// Bill Admin Starts//////////////////////////
// Add Kronos or Statutory row
function addReportRow(section) {
    const containerId = section === 'kronos' ? '#kronosReportsContainer' : '#statutoryReportsContainer';
    const inputName = section === 'kronos' ? 'kronosReports' : 'statutoryReports';

    const row = `
        <div class="report-row">
            <input type="text" name="${inputName}" placeholder="Enter Report Name" required  style="text-transform: capitalize;"/>
            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
        </div>
    `;
    $(containerId).append(row);
}

// Add HR Checklist row
function addChecklistRow() {
    const index = $('.checklist-row').length;
    const row = `
        <div class="checklist-row">
            <input type="text" name="checklistNames" placeholder="Check Point Name" required style="text-transform: capitalize;"/>
            <label><input type="checkbox" name="licenseRequired${index}" /> Licence No. Required</label>
            <label><input type="checkbox" name="validUptoRequired${index}" /> Valid Upto Required</label>
            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
        </div>
    `;
    $("#checklistConfigContainer").append(row);
}

function saveReportConfig() {
    let isValid = true;
    let errorSections = [];
    $("#saveStatusMsg").text("").css("color", "green");

    // Reset all borders first
    $("input").css("border", "");

    const kronosReports = [];
    const statutoryReports = [];
    const checklistItems = [];

    const kronosSet = new Set();
    const statutorySet = new Set();
    const checklistSet = new Set();

    // ==== Kronos Reports ====
    $('input[name="kronosReports"]').each(function () {
        const value = $(this).val().trim();
        if (value) {
            if (kronosSet.has(value.toLowerCase())) {
                $(this).css("border", "2px solid red");
                isValid = false;
                if (!errorSections.includes("Kronos Reports")) {
                    errorSections.push("Kronos Reports");
                }
            } else {
                kronosSet.add(value.toLowerCase());
                kronosReports.push(value);
            }
        }
    });

    // ==== Statutory Attachments ====
    $('input[name="statutoryReports"]').each(function () {
        const value = $(this).val().trim();
        if (value) {
            if (statutorySet.has(value.toLowerCase())) {
                $(this).css("border", "2px solid red");
                isValid = false;
                if (!errorSections.includes("Statutory Reports")) {
                    errorSections.push("Statutory Reports");
                }
            } else {
                statutorySet.add(value.toLowerCase());
                statutoryReports.push(value);
            }
        }
    });

    // ==== HR Checklist Items ====
    $('#checklistConfigContainer .checklist-row').each(function () {
        const checkpointName = $(this).find('input[name="checklistNames"]').val().trim();
        const licenseRequired = $(this).find('input[type="checkbox"]').eq(0).is(":checked");
        const validUptoRequired = $(this).find('input[type="checkbox"]').eq(1).is(":checked");

        if (checkpointName) {
            if (checklistSet.has(checkpointName.toLowerCase())) {
                $(this).find('input[name="checklistNames"]').css("border", "2px solid red");
                isValid = false;
                if (!errorSections.includes("Checklist Items")) {
                    errorSections.push("Checklist Items");
                }
            } else {
                checklistSet.add(checkpointName.toLowerCase());
                checklistItems.push({ checkpointName, licenseRequired, validUptoRequired });
            }
        }
    });

    // ==== Final validations ====
    if (!kronosReports.length && !statutoryReports.length && !checklistItems.length) {
        $("#saveStatusMsg")
            .html("Please enter at least one item in any of the sections: <b>Kronos Reports, Statutory Reports, or Checklist Items</b>.")
            .css("color", "red");
        return;
    }

    if (!isValid) {
        $("#saveStatusMsg")
            .html("Duplicate entries found in the following section(s): <b>" + errorSections.join(", ") + "</b>. Please fix them.")
            .css("color", "red");
        return;
    }

    // All entries are valid, continue saving
    const requestData = {
        kronosReports,
        statutoryReports,
        checklistItems
    };

    $.ajax({
        url: "/CWFM/billSetup/saveReportConfigAjax",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        success: function () {
            alert("Configuration saved successfully!");
            loadCommonList('/billSetup/list', 'Bill Verification Setup');
        },
        error: function () {
            alert("Error saving configuration.");
        }
    });
}
///////////////////////////Bill Admin ENds//////////////////////
function redirectToBillAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
            setDateRange();
            initializeBillAutoSelects();
        }
    };
    xhr.open("GET", "/CWFM/billVerification/createBill", true);
    xhr.send();
}

function getContractorsForBill(selectElement, userAccount) {
	
	const selectedOption = selectElement.options[selectElement.selectedIndex];
	    const unitCode = selectedOption.getAttribute("data-code");
	    document.getElementById("unitCodeId").value = unitCode ? unitCode : "";
		
		const unitId = selectedOption.value;
		    const unitName = selectedOption.text;
		   
		
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
				option.setAttribute("data-code", contractor.contractorCode); 
                contractorSelect.appendChild(option);
            });
            // after populating dropdown
			  autoSelectAndTriggerBill("contractor");
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function() {
        console.error("Request failed");
    };

    xhr.send();
}
function formatDate(dateTimeStr) {
    if (!dateTimeStr) return "";

    const date = new Date(dateTimeStr);
    if (isNaN(date)) return "";

    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are 0-based
    const year = date.getFullYear();

    return `${day}-${month}-${year}`;
}
function getWorkordersForBill(selectElement) {
		var principalEmployerSelect = document.getElementById("unitId");
	    var unitId = principalEmployerSelect.value; // Get the selected principal employer value
		const selectedOption = selectElement.options[selectElement.selectedIndex];
		const contractorCode = selectedOption.getAttribute("data-code");
		document.getElementById("contractorCodeId").value = contractorCode ? contractorCode : "";
				
				const contractorId = selectedOption.value;
				const contractorName = selectedOption.text;
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
				const validFromFormatted = formatDate(workorder.validFrom);
				const validToFormatted = formatDate(workorder.validTo);
				option.setAttribute("data-fromDate", validFromFormatted); 
				option.setAttribute("data-toDate", validToFormatted);
                workorderSelect.appendChild(option);
            });
            // after populating dropdown
			autoSelectAndTriggerBill("workorder", function () {
				var workorderSelect = document.getElementById("workorder");
			    setDates(workorderSelect);
			});
            //setDates
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function() {
        console.error("Request failed");
    };

    xhr.send();
}

function setDates(selectElement){
	const selectedOption = selectElement.options[selectElement.selectedIndex];
			const validFrom = selectedOption.getAttribute("data-fromDate");
			document.getElementById("woValidFromId").value = validFrom ? validFrom : "";
			
			const validTo = selectedOption.getAttribute("data-toDate");
						document.getElementById("woValidToId").value = validTo ? validTo : "";
}

async function saveBtn() {

    showLoader();

    $("#docTabGlobalError").hide().text("");
    $("label[id^='error-']").hide();

    try {
        // 1. Run existing validations.
        const basicValid = validateBillFormData();
        const filesValid = validateMandatoryFiles();
        const statutoryfilesValid = validateStatutoryMandatoryFiles();

        console.log("Basic validation:", basicValid);
        console.log("Kronos file validation:", filesValid);
        console.log("Statutory file validation:", statutoryfilesValid);

        const errorTabs = [];

        if (!basicValid) {
            errorTabs.push("Basic");
        }

        if (!filesValid) {
            errorTabs.push("Wage/Attendance");
        }

        if (!statutoryfilesValid) {
            errorTabs.push("Statutory");
        }
        
         console.log("Existing validations passed. Checking Wage Cost Report...");

        const wageCostValid = await validateWageCostReport();

        console.log("Wage Cost Report validation:", wageCostValid);

        if (!wageCostValid) {
            errorTabs.push("Wage/Attendance");
        }
        //if (!wageCostValid) {
        //    hideLoader();
       //     return;
       // }
        // 4. Only now submit the bill.
        console.log("All validations passed. Submitting bill...");

        // 2. Stop if any existing validation fails.
        if (errorTabs.length > 0) {
            $("#docTabGlobalError")
                .text("Please check errors in: " + errorTabs.join(", ") + " tab(s).")
                .show();

            hideLoader();
            return;
        }

        submitBillVerification();

    } catch (error) {
        console.error("Save validation error:", error);

        hideLoader();

        $("#docTabGlobalError")
            .text("Unable to validate the form. Please try again.")
            .show();
    }
}


// Keep the actual bill submission separate.
// This contains your existing FormData and AJAX save logic.
function submitBillVerification() {

    const formData = new FormData();

    const unitSelect = document.getElementById("unitId");
    const unitId = unitSelect?.value;
    const unitName = unitSelect?.options[unitSelect.selectedIndex]?.text;

    const contractorSelect = document.getElementById("contractor");
    const contractorSelectId = contractorSelect?.value;
    const contractorSelectName =
        contractorSelect?.options[contractorSelect.selectedIndex]?.text;

    // Basic Info JSON Data
    const data = {
        wcTransId: $("#transactionId").val(),
        unitId: unitId,
        unitCode: $("#unitCodeId").val(),
        unitName: unitName,
        contractorId: contractorSelectId,
        contractorCode: $("#contractorCodeId").val(),
        contractorName: contractorSelectName,
        workOrderNumber: $("#workorder").val(),
        startDate: $("#billStartDateId").val(),
        endDate: $("#billEndDateId").val(),
        services: $("#billCategory").val(),
        woValidFrom: $("#woValidFromId").val(),
        woValidTo: $("#woValidToId").val(),
        billType: $("#billType").val(),
        actionPlan: $("#actionPlanId").val(),
        comments: $("#commentsId").val(),
        preComments: $("#preCommentsId").val()
    };

    formData.append("jsonData", JSON.stringify(data));

    // Kronos report files, including Wage Cost Report if uploaded.
    $("input[type='file'][name^='kronosFile_']").each(function () {
        if (this.files.length > 0) {
            formData.append($(this).attr("name"), this.files[0]);
        }
    });

    // Statutory attachment files.
    $("input[type='file'][name^='statutoryFile_']").each(function () {
        if (this.files.length > 0) {
            formData.append($(this).attr("name"), this.files[0]);
        }
    });

    // Checklist values.
    const checklist = [];

    $("#tab4 tbody tr").each(function () {
        const row = $(this);
        const hiddenName = row.find("input[type='hidden']").attr("name");
        const id = hiddenName ? hiddenName.split("_")[1] : "";

        const statusValue = row.find("select[name='statusValue']").val();

        let licenseNumber = "";

        if (row.find(".license-dropdown").length > 0) {
            licenseNumber = row.find(".license-dropdown option:selected")
                .text().trim();
        } else {
            licenseNumber = row.find("input[name^='licenseNumber_']").val() || "";
        }

        const validUpto =
            row.find("input[name^='validUpto_']").val() || "";

        checklist.push({
            id: id,
            statusValue: statusValue,
            licenseNumber: licenseNumber,
            validUpto: validUpto
        });
    });

    formData.append("checklistJson", JSON.stringify(checklist));

    // Existing save AJAX call.
    $.ajax({
        url: "/CWFM/billVerification/saveBill",
        method: "POST",
        data: formData,
        processData: false,
        contentType: false,

        success: function (response, status, xhr) {
            hideLoader();

            if (xhr.status === 200) {
                console.log("Bill Verification saved successfully");

                sessionStorage.setItem(
                    "successMessage",
                    "Bill Verification saved successfully!"
                );

                loadCommonList(
                    "/billVerification/listingFilter",
                    "Bill Verification List"
                );
            } else {
                alert("Unexpected response: " + xhr.status);
            }
        },

        error: function (xhr) {
            hideLoader();

            if (xhr.status === 204) {
                sessionStorage.setItem(
                    "errorMessage",
                    "Failed to save Bill Verification!"
                );

                console.error("Error:", xhr.status, xhr.responseText);
                return;
            }

            if (xhr.responseText) {
                alert("Error: " + xhr.responseText);
            } else {
                alert("Error occurred. Status: " + xhr.status);
            }
        }
    });
}

function showFileNameBill(input, id) {
        const fileName = input.files[0]?.name || "No file chosen";
        document.getElementById('fileName_' + id).textContent = fileName;
    }
    
    function showFileNameBill0(input, id) {
        const fileName = input.files[0]?.name || "No file chosen";
        document.getElementById('statfileName_' + id).textContent = fileName;
    }
	function searchBillBasedOnPE() {
	    var principalEmployerId = $('#peId').val();
	    var deptId = $("#deptId").val();

	    $.ajax({
	        url: '/CWFM/billVerification/list',
	        type: 'POST',
	        dataType: 'json',
	        data: {
	            principalEmployerId: principalEmployerId,
	            deptId: deptId
	        },
	        success: function(response) {

	            var table = $('#workmenTable');
	            var tableBody = $('#workmenTable tbody');

	            if ($.fn.DataTable.isDataTable('#workmenTable')) {
	                table.DataTable().clear().destroy();
	            }

	            tableBody.empty();

	            var list = [];

	            if (Array.isArray(response)) {
	                list = response;
	            } else if (response && Array.isArray(response.data)) {
	                list = response.data;
	            }

	            if (list.length > 0) {
	                $.each(list, function(index, wo) {
	                    var row =
	                        '<tr>' +
	                            '<td><input type="checkbox" name="selectedWOs" value="' + (wo.wcTransId || '') + '"></td>' +
	                              // Transaction ID Hyperlink
                            '<td>' +
                                '<a href="javascript:void(0);" ' +
                                   'onclick="redirectToBillViewById(\'' + wo.wcTransId + '\')">' +
                                   wo.wcTransId +
                                '</a>' +
                            '</td>' +
                            
	                           // '<td>' + (wo.wcTransId || '') + '</td>' +
	                            '<td>' + (wo.unitCode || '') + '</td>' +
	                            '<td>' + (wo.contractorCode || '') + '</td>' +
	                            '<td>' + (wo.contractorName || '') + '</td>' +
	                            '<td>' + (wo.workOrderNumber || '') + '</td>' +
	                            '<td>' + (wo.startDate || '') + '</td>' +
	                            '<td>' + (wo.endDate || '') + '</td>' +
	                            '<td>' + (wo.statusValue || '') + '</td>' +
	                            '<td>' + (wo.services || '') + '</td>' +
	                        '</tr>';

	                    tableBody.append(row);
	                });
	            }

	            initWorkmenTable("workmenTable");
	        },
	        error: function(xhr, status, error) {
	            console.error("Error fetching data:", error);

	            var table = $('#workmenTable');
	            var tableBody = $('#workmenTable tbody');

	            if ($.fn.DataTable.isDataTable('#workmenTable')) {
	                table.DataTable().clear().destroy();
	            }

	            tableBody.empty();

	            initWorkmenTable("workmenTable");
	        }
	    });
	}

				



					function approveRejectBill(status){
					    showLoader();
						let isValid=true;
					
						 const approvercomments = $("#approvercomments").val().trim();
					if (approvercomments === "" && status==5) {
					    $("#error-approvercomments").show();
					    alert("Comments Required in Comments Tab");
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
							approverRole : $("#roleName").val().trim(),
							roleId :$("#roleId").val().trim(),
							type : '1',
							unitId: $("#unitId").val().trim(),
						};
							  const xhr = new XMLHttpRequest();
					xhr.open("POST", "/CWFM/billVerification/approveRejectBill", true); // Replace with your actual controller URL
					xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
					xhr.onload = function() {
					hideLoader();
					    if (xhr.status === 200) {
					        // Handle successful response
					        console.log("Data saved successfully:", xhr.responseText);
							sessionStorage.setItem("successMessage", "Bill Verification approved/rejected successfully!");
					                loadCommonList('/billVerification/listingFilter', 'Bill Verification');
					            
					    } else {
					        // Handle error response
					        console.error("Error saving data:", xhr.statusText);
							sessionStorage.setItem("errorMessage", "Failed to approve/reject Bill!");
					    }
					};

					xhr.onerror = function() {
					    console.error("Request failed");
						sessionStorage.setItem("errorMessage", "Failed to approve/reject Bill!");
						hideLoader();
					};

					// Send the data object as a JSON string
					xhr.send(JSON.stringify(data));
						}else{
							//error 
						}
						}//eofunc
						
function validateBillFormData() {
    let isValid = true;
    const vendorCode = $("#contractor").val().trim();
    if (vendorCode === "") {
        $("#error-contractor").show();
        isValid = false;
    }else{
		 $("#error-contractor").hide();
	}
    const principalemployer = $("#unitId").val().trim();
    if (principalemployer === "") {
        $("#error-principalEmployer").show();
        isValid = false;
    }else{
		$("#error-principalEmployer").hide();
	}
    
	/*const billStartDate = $("#billStartDateId").val().trim();
	if (billStartDate === "") {
	  $("#error-billStartDate").show();
	  isValid = false;
	  }else{
	$("#error-billStartDate").hide();
	}
    const billEndDate = $("#billEndDateId").val().trim();
	  if (billEndDate === "") {
		$("#error-billEndDate").show();
		isValid = false;
		}else{
		 $("#error-billEndDate").hide();
	}*/
	const billStartDateStr = $("#billStartDateId").val().trim();
const billEndDateStr   = $("#billEndDateId").val().trim();

const today = new Date();
today.setHours(0, 0, 0, 0); // normalize

let startDate = null;
let endDate = null;

/* ---------- Bill Start Date ---------- */
if (billStartDateStr === "") {
    $("#error-billStartDate").text("Bill Start Date is required").show();
    isValid = false;
} else {
    startDate = new Date(billStartDateStr);
    startDate.setHours(0, 0, 0, 0);

    // must be strictly past date
    if (startDate > today) {
        $("#error-billStartDate")
            .text("Bill Start Date must be a past date")
            .show();
        isValid = false;
    } else {
        $("#error-billStartDate").hide();
    }
}

/* ---------- Bill End Date ---------- */
if (billEndDateStr === "") {
    $("#error-billEndDate").text("Bill End Date is required").show();
    isValid = false;
} else {
    endDate = new Date(billEndDateStr);
    endDate.setHours(0, 0, 0, 0);

    // can be past or today, NOT future
    if (endDate > today) {
        $("#error-billEndDate")
            .text("Bill End Date cannot be a future date")
            .show();
        isValid = false;
    } else {
        $("#error-billEndDate").hide();
    }
}

/* ---------- End Date > Start Date ---------- */
if (startDate && endDate) {
    if (endDate <= startDate) {
        $("#error-billEndDate")
            .text("Bill End Date must be greater than Bill Start Date")
            .show();
        isValid = false;
    }
}

	const workorderCode = $("#workorder").val().trim();
      if (workorderCode === "") {
		 $("#error-workorder").show();
		 isValid = false;
	   }else{
		 $("#error-workorder").hide();
		}												  	  
	 const billType = $("#billType").val().trim();
	  if (billType === "") {
		  $("#error-billType").show();
           isValid = false;
           }else{
           $("#error-billType").hide();
         }	
 const billCategory = $("#billCategory").val().trim();
    if (billCategory === "") {
        $("#error-billCategory").show();
        isValid = false;
    }else{
		 $("#error-billCategory").hide();
	}
    return isValid;
}				
/*function validateMandatoryFiles() {

    let isValid = true;

    // Hide errors initially
    $("#error-kronosFile").hide();
    $("#error-statutoryFile").hide();

    // ===== Validate Kronos Files =====
    $("input[type='file'][name^='kronosFile_']").each(function () {
        if (this.files.length === 0) {
            isValid = false;
        }
    });

    if (!isValid) {
        $("#error-kronosFile").show();
    }

    // Reset for statutory check
    let statutoryValid = true;

    // ===== Validate Statutory Files =====
    $("input[type='file'][name^='statutoryFile_']").each(function () {
        if (this.files.length === 0) {
            statutoryValid = false;
        }
    });

    if (!statutoryValid) {
        $("#error-statutoryFile").show();
    }

    return isValid && statutoryValid;
}*/
function validateStatutoryMandatoryFiles() {

    // Reset for statutory check
    let statutoryValid = true;

    // ===== Validate Statutory Files =====
    $("input[type='file'][name^='statutoryFile_']").each(function () {
        if (this.files.length === 0) {
            statutoryValid = false;
        }
    });

    if (!statutoryValid) {
        $("#error-statutoryFile").show();
    }
    
 $("#docTabGlobalError").hide();
    return statutoryValid;
}
function validateMandatoryFiles() {

    let isValid = true;

    $("#error-kronosFile").hide();

    // ✅ Normalize bill type
    const billType = ($('#billCategory option:selected').text() || "")
                        .toLowerCase()
                        .replace(/\s+/g, ' ')
                        .trim();

    let kronosMissing = false;

    $("input[type='file'][name^='kronosFile_']").each(function () {

        const reportName = ($(this).data("report-name") || "")
                                .toLowerCase()
                                .replace(/\s+/g, ' ')
                                .trim();

        // ✅ Correct skip condition
        if ((billType === 'sla with head count' || billType === 'sla without head count')  && reportName === 'wage cost report') {
            return true; // skip validation for this file
        }

        if (this.files.length === 0) {
            kronosMissing = true;
        }
    });

    if (kronosMissing) {

        // ✅ Correct message
        if (billType === 'sla with head count' || billType === 'sla without head count') {
            $("#error-kronosFile").text("All Kronos reports are mandatory except Wage Cost Report");
        } else {
            $("#error-kronosFile").text("All Kronos reports are mandatory");
        }

        $("#error-kronosFile").show();
        isValid = false;
    }

    return isValid;
}

	function initializeBillAutoSelects() {
    autoSelectAndTriggerBill("unitId");
}		
/*function autoSelectAndTriggerBill(selectId, callback, triggerChange = true) {
    const $select = $("#" + selectId);
    const options = $select.find("option[value!='']");

    if (options.length === 1 && !$select.val()) {
        const value = options.first().val();
        $select.val(value);

        if (callback) {
            callback(value);
        }

        if (triggerChange) {
            $select.trigger("change");
        }
    }
}*/
function autoSelectAndTriggerBill(selectId) {

    const select = document.getElementById(selectId);

    if (!select) return;

    // Get only valid options (ignore placeholder "")
    const validOptions = Array.from(select.options).filter(opt => opt.value !== "");

    // Condition:
    // ✔ Only ONE option
    // ✔ Nothing already selected
    if (validOptions.length === 1 && !select.value) {

        select.value = validOptions[0].value;

        // Trigger change so dependent dropdown loads
        select.dispatchEvent(new Event("change"));
    }
}
 // SPECIAL LICENSE CHECKPOINT

const SPECIAL_LICENSE_CHECKPOINT =
    "Licence copy obtained by Vendor under Contract Labour Act 1970.";


 // NORMALIZE CHECKPOINT NAME
function normalizeCheckpoint(value) {

    return (value || "")
        .toString()
        .replace(/\u00A0/g, " ")       // non-breaking space
        .replace(/\s+/g, " ")          // multiple spaces -> one
        .replace(/\./g, "")            // remove dots
        .trim()
        .toLowerCase();
}


 // CHECK WHETHER THIS IS THE SPECIAL LICENSE CHECKPOINT
function isSpecialLicenseCheckpoint(checkpointName) {

    return normalizeCheckpoint(checkpointName) ===
           normalizeCheckpoint(SPECIAL_LICENSE_CHECKPOINT);
}


 //GET LICENSE CELL
function getLicenseCell(row) {

    return row.find(".license-cell");
}

 // GET VALID UPTO INPUT
function getValidUptoInput(row) {

    return row.find(".valid-upto-input");
}

 // CREATE NORMAL TEXTBOX
function createLicenseTextbox(row) {

    const licenseCell = getLicenseCell(row);

    // Get existing input name before replacing
    let inputName = licenseCell.find("input, select").attr("name");

    if (!inputName) {

        const id = row.find("input[type='hidden']")
                     .attr("name")
                     .replace("id_", "");

        inputName = "licenseNumber_" + id;
    }

    licenseCell.html(
        '<input type="text" ' +
        'class="license-input" ' +
        'name="' + inputName + '" ' +
        'placeholder="Enter License Number" />'
    );
}


 // RESET VALID UPTO TO MANUAL MODE

function resetValidUpto(row) {

    const input = getValidUptoInput(row);

    if (!input.length) {
        return;
    }

    // Clear auto populated value
    input.val("");

    // Remove auto mode
    input
        .prop("readonly", false)
        .removeAttr("data-auto");

    // Recreate datepicker
    if (input.hasClass("hasDatepicker")) {

        input.datepicker("destroy");
    }

    input.datepicker({
        dateFormat: "yy-mm-dd"
    });
}


 //SET VALID UPTO AUTO MODE

function setValidUptoAuto(row, date) {

    const input = getValidUptoInput(row);

    if (!input.length) {
        return;
    }

    // Destroy calendar
    if (input.hasClass("hasDatepicker")) {

        input.datepicker("destroy");
    }

    // Set auto date
    input
        .val(date)
        .prop("readonly", true)
        .attr("data-auto", "1");
}


 // LOAD LICENSE DROPDOWN

function loadLicenseDropdown(row) {

    const workOrder = $("#workorder option:selected")
        .text()
        .trim();

    if (!workOrder) {

        alert("Please select Work Order first.");

        row.find(".statusValue").val("");

        return;
    }

    const licenseCell = getLicenseCell(row);

    const existingElement =
        licenseCell.find("input, select").first();

    let inputName = existingElement.attr("name");

    if (!inputName) {

        const id = row.find("input[type='hidden']")
                     .attr("name")
                     .replace("id_", "");

        inputName = "licenseNumber_" + id;
    }

    // Show loading state
    licenseCell.html(
        '<select class="license-dropdown" ' +
        'name="' + inputName + '">' +
        '<option value="">Loading licenses...</option>' +
        '</select>'
    );

    $.ajax({

        url: "/CWFM/billVerification/getLicenseNumbers",

        method: "GET",

        data: {
            workOrder: workOrder
        },

        success: function (response) {

            const dropdown =
                licenseCell.find(".license-dropdown");

            dropdown.empty();

            dropdown.append(
                '<option value="">Select License</option>'
            );

            if (response && response.length > 0) {

                response.forEach(function (item) {

                    dropdown.append(
                        $("<option>", {
                            value: item.id,
                            text: item.licenseNumber
                        })
                    );

                });

            } else {

                dropdown.append(
                    '<option value="">No licenses available</option>'
                );
            }

        },

        error: function () {

            alert("Failed to load license numbers.");

            // Restore textbox
            createLicenseTextbox(row);

            // Manual valid upto
            resetValidUpto(row);
        }
    });
}


 // STATUS CHANGE

$(document).on(
    "change",
    "select.statusValue",
    function () {

        const statusSelect = $(this);

        const row = statusSelect.closest("tr");

         // IMPORTANT:Get checkpoint from TR.Do NOT get it from .license-input because.license-input can be replaced by .license-dropdown.
        const checkpointName =
            row.attr("data-checkpoint") || "";

        const statusText =
            statusSelect
                .find("option:selected")
                .text()
                .trim();

        console.log(
            "Checkpoint:",
            checkpointName,
            "Status:",
            statusText
        );


         // ONLY THIS CHECKPOINT GETS LICENSE DROPDOWN

        if (
            isSpecialLicenseCheckpoint(checkpointName) &&
            statusText.toLowerCase() === "yes"
        ) {

            console.log(
                "SPECIAL LICENSE CHECKPOINT + YES"
            );

            loadLicenseDropdown(row);

             // Don't allow previous manual date to remain.
            const validInput = getValidUptoInput(row);

            if (validInput.length) {

                validInput.val("");

                validInput
                    .prop("readonly", false)
                    .removeAttr("data-auto");
            }

            return;
        }


         //ACEPT Licence copy obtained by Vendor under Contract Labour Act 1970 MUST HAVE TEXTBOX + MANUAL CALENDAR

        console.log(
            "Normal license textbox mode"
        );

         // If dropdown currently exists,convert it back to textbox.
        if (row.find(".license-dropdown").length > 0) {

            createLicenseTextbox(row);
        }

         //Enable manual valid-upto calendar.
        resetValidUpto(row);
    }
);

 // LICENSE DROPDOWN CHANGE
 // This event is ONLY possible for the special checkpoint.

$(document).on(
    "change",
    ".license-dropdown",
    function () {

        const dropdown = $(this);

        const row = dropdown.closest("tr");

        const checkpointName =
            row.attr("data-checkpoint") || "";

         // Safety check: Never process license date for another checkpoint.
        if (!isSpecialLicenseCheckpoint(checkpointName)) {

            console.warn(
                "Ignoring license dropdown for non-license checkpoint."
            );

            return;
        }

        const licenseNumber =
            dropdown
                .find("option:selected")
                .text()
                .trim();


        const validInput =
            getValidUptoInput(row);


        if (!validInput.length) {
            return;
        }

        //No license selected 
        if (
            !licenseNumber ||
            licenseNumber === "Select License" ||
            licenseNumber === "No licenses available"
        ) {

            resetValidUpto(row);

            return;
        }


        console.log(
            "Selected License:",
            licenseNumber
        );

         // Get license validity date
        $.ajax({

            url:
                "/CWFM/billVerification/getLicenseValidDate",

            method: "GET",

            data: {
                licenseNumber: licenseNumber
            },

            success: function (response) {

                console.log(
                    "License valid date response:",
                    response
                );


                let date = "";


                if (typeof response === "string") {

                    date = response;

                } else if (
                    response &&
                    response.wcToDtm
                ) {

                    date = response.wcToDtm;
                }

                 // If date exists
                if (date) {

                     // Example:2027-09-12 00:00:00  becomes:2027-09-12
                    date =
                        date
                            .toString()
                            .split(" ")[0]
                            .trim();

                    setValidUptoAuto(
                        row,
                        date
                    );

                } else {

                     // No date from server:allow manual calendar
                    resetValidUpto(row);
                }
            },

            error: function () {

                console.error(
                    "Failed to get license valid date."
                );

                resetValidUpto(row);
            }
        });
    }
);


 // PREVENT EDITING AUTO DATE

$(document).on(
    "focus",
    "input.valid-upto-input[data-auto='1']",
    function () {

        this.blur();
    }
);
function redirectToBillViewById(transactionId) {

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {

        if (xhr.readyState == 4 && xhr.status == 200) {

            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };

    xhr.open("GET", "/CWFM/billVerification/view/" + transactionId, true);

    xhr.send();
}
function showLoader() {
    document.getElementById("loaderOverlay").style.display = "flex";
}

function hideLoader() {
    document.getElementById("loaderOverlay").style.display = "none";
}
function validateWageCostReport() {

    const $file = $("input[type='file'][name^='kronosFile_']")
        .filter(function () {
            return (($(this).attr("data-report-name") || "")
                .toLowerCase()
                .replace(/\s+/g, " ")
                .trim()) === "wage cost report";
        })
        .first();

    // Preserve the existing optional-file behavior.
    if (!$file.length ||
        !$file[0].files ||
        $file[0].files.length === 0) {

        console.log(
            "Wage Cost Report not uploaded. Skipping validation."
        );

        return Promise.resolve(true);
    }

    $("#error-kronosFile").hide().text("");

    // Use your actual existing transaction ID field.
    const transactionId =
        ($("#transactionId").val() || "").trim();

    if (!transactionId) {
        $("#error-kronosFile")
            .text("Transaction ID is required to save the Wage Cost Report.")
            .show();

        return Promise.resolve(false);
    }

    const formData = new FormData();

    formData.append("file", $file[0].files[0]);
    formData.append("transactionId", transactionId);

    return $.ajax({
        url: "/CWFM/billVerification/validateWageCostReport",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        dataType: "json"

    }).then(function (response) {

        if (!response || response.success !== true) {
            $("#error-kronosFile")
                .text(
                    response?.message ||
                    "Wage Cost Report validation failed."
                )
                .show();

            return false;
        }

        $("#error-kronosFile").hide().text("");

        // Every validation passed and the original CSV was saved.
        return true;

    }).catch(function (xhr) {

        console.error(
            "Wage Cost Report validation failed:",
            xhr.status,
            xhr.responseText
        );

        const message =
            xhr.responseJSON?.message ||
            "Unable to validate Wage Cost Report. Please try again.";

        $("#error-kronosFile")
            .text(message)
            .show();

        return false;
    });
}


function openFile(reportType, transactionId, fileName) {

    const payload = {
        reportType: reportType,
        transactionId: transactionId,
        fileName: fileName
    };

    const encodedData = btoa(JSON.stringify(payload))
        .replace(/\+/g, '-')
        .replace(/\//g, '_')
        .replace(/=+$/, '');

    const url = `/CWFM/billVerification/viewFile/${encodedData}`;

    // Identify CSV files without affecting the existing file flow.
    const isCsv =
        /\.csv$/i.test(fileName || '') ||
        /wage\s*cost\s*report/i.test(reportType || '');

    // Open synchronously to reduce browser popup-blocking issues.
    const newTab = window.open('', '_blank');

    if (!newTab) {
        alert('Please allow pop-ups for this site to open the file.');
        return;
    }

    newTab.document.open();
    newTab.document.write(
        '<!DOCTYPE html><html><head><title>Loading file...</title></head>' +
        '<body style="font-family:Arial,sans-serif;padding:24px;">' +
        '<p>Loading file, please wait...</p></body></html>'
    );
    newTab.document.close();

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('File not found or error fetching file.');
            }
            return response.blob();
        })
        .then(blob => {

            if (isCsv) {
                return blob.text().then(csvText => {
                    displayCsvInNewTab(csvText, newTab, fileName);
                });
            }

            // Preserve the existing behavior for non-CSV files.
            const blobUrl = URL.createObjectURL(blob);
            newTab.location.href = blobUrl;

            // Release the object URL after the browser has had time to load it.
            setTimeout(() => URL.revokeObjectURL(blobUrl), 60000);
        })
        .catch(error => {
            console.error('Error opening file:', error);

            if (newTab && !newTab.closed) {
                newTab.document.open();
                newTab.document.write(
                    '<!DOCTYPE html><html><body>' +
                    '<h3>Unable to open file</h3>' +
                    '<p>The file may not exist on the server.</p>' +
                    '</body></html>'
                );
                newTab.document.close();
            }

            alert('Unable to open file. It may not exist on the server.');
        });
}


/**
 * Parse CSV correctly, including:
 * - Commas inside quoted values
 * - Escaped quotes ("")
 * - Newlines inside quoted values
 * - Windows and Unix line endings
 */
function parseCsvForDisplay(csvText) {

    const rows = [];
    let row = [];
    let value = '';
    let insideQuotes = false;

    for (let i = 0; i < csvText.length; i++) {

        const char = csvText[i];

        if (char === '"') {

            if (insideQuotes && csvText[i + 1] === '"') {
                value += '"';
                i++;
            } else {
                insideQuotes = !insideQuotes;
            }

        } else if (char === ',' && !insideQuotes) {

            row.push(value);
            value = '';

        } else if (
            (char === '\n' || char === '\r') &&
            !insideQuotes
        ) {

            // Treat CRLF as a single line break.
            if (char === '\r' && csvText[i + 1] === '\n') {
                i++;
            }

            row.push(value);

            if (row.some(cell => cell.trim() !== '')) {
                rows.push(row);
            }

            row = [];
            value = '';

        } else {
            value += char;
        }
    }

    if (insideQuotes) {
        throw new Error('The CSV contains an unclosed quoted field.');
    }

    // Add the final row if the file does not end with a newline.
    if (value.length > 0 || row.length > 0) {
        row.push(value);

        if (row.some(cell => cell.trim() !== '')) {
            rows.push(row);
        }
    }

    // Remove UTF-8 BOM from the first header if present.
    if (rows.length && rows[0].length) {
        rows[0][0] = rows[0][0].replace(/^\uFEFF/, '');
    }

    return rows;
}


/**
 * Display CSV contents as a table in the already-opened tab.
 */
function displayCsvInNewTab(csvText, newTab, fileName) {

    const rows = parseCsvForDisplay(csvText);

    if (!rows.length) {
        newTab.document.open();
        newTab.document.write(
            '<!DOCTYPE html><html><body>' +
            '<h3>Wage Cost Report</h3>' +
            '<p>The CSV file is empty.</p>' +
            '</body></html>'
        );
        newTab.document.close();
        return;
    }

    const headers = rows[0];
    const dataRows = rows.slice(1);

    // Escape all CSV content before placing it in HTML.
    const escapeHtml = value => String(value ?? '')
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');

    let tableHtml = '<table><thead><tr>';

    headers.forEach(header => {
        tableHtml += `<th>${escapeHtml(header)}</th>`;
    });

    tableHtml += '</tr></thead><tbody>';

    dataRows.forEach(row => {

        tableHtml += '<tr>';

        headers.forEach((header, index) => {
            const cellValue = row[index] ?? '';
            tableHtml += `<td>${escapeHtml(cellValue)}</td>`;
        });

        tableHtml += '</tr>';
    });

    tableHtml += '</tbody></table>';

    const safeTitle = escapeHtml(fileName || 'Wage Cost Report');

    const html = `<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${safeTitle}</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            color: #222;
        }

        h2 {
            color: #005151;
            margin-bottom: 8px;
        }

        .report-info {
            margin-bottom: 16px;
            color: #555;
        }

        .table-container {
            width: 100%;
            max-height: 75vh;
            overflow: auto;
            border: 1px solid #ccc;
        }

        table {
            border-collapse: collapse;
            width: max-content;
            min-width: 100%;
            font-size: 13px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px 12px;
            text-align: left;
            white-space: pre-wrap;
            overflow-wrap: anywhere;
            max-width: 350px;
        }

        th {
            background: #005151;
            color: white;
            position: sticky;
            top: 0;
            z-index: 1;
        }

        tbody tr:nth-child(even) {
            background: #f5f7f7;
        }

        tbody tr:hover {
            background: #e8f2f2;
        }

        #searchBox {
            padding: 9px;
            width: 320px;
            max-width: 100%;
            margin: 10px 0 15px;
            border: 1px solid #aaa;
            border-radius: 4px;
        }

        .count {
            margin: 8px 0;
            font-size: 13px;
            color: #555;
        }

        @media print {
            body {
                margin: 0;
            }

            .table-container {
                max-height: none;
                overflow: visible;
            }

            th {
                position: static;
            }

            #searchBox {
                display: none;
            }
        }
    </style>
</head>
<body>
    
    <div class="report-info">${safeTitle}</div>

    <input
        id="searchBox"
        type="search"
        placeholder="Search report values..."
        aria-label="Search report values"
    >

    <div class="count">
        Total data rows: <span id="rowCount">${dataRows.length}</span>
    </div>

    <div class="table-container">
        ${tableHtml}
    </div>

    <script>
        const searchBox = document.getElementById('searchBox');
        const tableRows = Array.from(
            document.querySelectorAll('tbody tr')
        );
        const rowCount = document.getElementById('rowCount');

        searchBox.addEventListener('input', function () {
            const searchValue = this.value.toLowerCase();
            let visibleCount = 0;

            tableRows.forEach(function (row) {
                const matches = row.textContent
                    .toLowerCase()
                    .includes(searchValue);

                row.style.display = matches ? '' : 'none';

                if (matches) {
                    visibleCount++;
                }
            });

            rowCount.textContent = visibleCount;
        });
    <\/script>
</body>
</html>`;

    newTab.document.open();
    newTab.document.write(html);
    newTab.document.close();
}
