
function redirectToContractorRenew() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/renewal/create", true);
    xhr.send();
}

function redirectToContractorRenewView() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }

    var selectedRow = selectedCheckboxes[0].closest('tr');
    var contractorregId = selectedRow.querySelector('[name="selectedWOs"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/renewal/view/" + contractorregId, true);
    xhr.send();
}

function setValueIfPresent(elementId, value) {
    var input = document.getElementById(elementId);
    input.value = value || "";
    if(elementId === "contractTypeId"){
		makeSelectReadOnly("contractTypeId");
	}
}

function getAllContractorDetailForRenewal(contractorId) {
    var principalEmployerSelect = document.getElementById("principalEmployerId");
    var unitId = principalEmployerSelect.value;
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/renewal/getAllContractorDetailForRenewal?unitId=" + unitId + "&contractorId=" + contractorId;

    xhr.open("GET", url, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var contractors = JSON.parse(xhr.responseText);
            console.log("Response:", contractors);

            setValueIfPresent("managerNameId", contractors.managerName);
            setValueIfPresent("locofWorkId", contractors.locofWork);
            setValueIfPresent("totalStrengthId", contractors.totalStrength);
            setValueIfPresent("rcMaxEmpId", contractors.rcMaxEmp);
            setValueIfPresent("pfNumId", contractors.pfNum);
            setValueIfPresent("natureOfWorkId", contractors.natureOfWork);
            const dateFrom = contractors.contractFrom.split(" ")[0];
            setValueIfPresent("contractFromId", dateFrom);
            const dateTo = contractors.contractTo.split(" ")[0];
            setValueIfPresent("contractToId", dateTo);
            setValueIfPresent("contractorNameId", contractors.contractorName);
            setValueIfPresent("emailId", contractors.email);
            setValueIfPresent("mobileId", contractors.mobile);
            setValueIfPresent("aadharId", contractors.aadhar);
            setValueIfPresent("panId", contractors.pan);
            setValueIfPresent("gstId", contractors.gst);
            setValueIfPresent("addressId", contractors.address);
            setValueIfPresent("contractTypeId", contractors.contractType);
			const pfApplydate = contractors.pfApplyDate.split(" ")[0];
			setValueIfPresent("pfApplyDateId", pfApplydate);
			
			const availableBox = document.getElementById("availableWorkOrders");
			            const selectedBox = document.getElementById("selectedWorkOrders");
			            availableBox.innerHTML = "";
			           selectedBox.innerHTML = "";

			            const selectedSet = new Set(contractors.selectedWos || []);

			            (contractors.availableWos || []).forEach(workOrder => {
			                const option = new Option(workOrder, workOrder);
							option.style.color = "black"; 
			                if (selectedSet.has(workOrder)) {
			                    selectedBox.add(option);
			                } else {
			                    availableBox.add(option);
			                }
			            });
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.error("Request failed");
    };

    xhr.send();
}



document.addEventListener('click', function (e) {
    if (e.target.matches('button.addRowNew')) {
        e.preventDefault();
        e.stopImmediatePropagation();
        setTimeout(() => addRowContNew(), 0); // prevent recursion
    } else if (e.target.matches('button.removeRowNew')) {
		e.preventDefault();
		       e.stopImmediatePropagation();
		       setTimeout(() => deleteRowContNew(e.target), 0); // prevent recursion
        //deleteRowContNew(e.target);
    }
});

function addRowContNew() {
    const tbody = document.getElementById("licenseBody");
    const today = new Date().toISOString().split('T')[0];

    const row = document.createElement("tr");
    row.innerHTML = `
        <td><button type="button" class="btn btn-success addRowNew" style="color:blue;background-color:white;">+</button></td>
        <td><button type="button" class="btn btn-danger removeRowNew" style="color:blue;background-color:white;">−</button></td>
        <td></td>
        <td><input type="text" class="form-control documentNumber" name="documentNumber" /></td>
        <td><input type="number" class="form-control coverage" name="coverage" min="0" step="1" /></td>
        <td><input type="date" class="form-control validFrom" name="validFrom" min="${today}" /></td>
        <td><input type="date" class="form-control validTo" name="validTo" min="${today}" /></td>
        <td><input type="file" class="form-control attachment" name="attachment" accept="application/pdf" /></td>
		<td><input type="checkbox" class="form-control panIndia" name="panIndia" /></td>
		<td><input type="checkbox" class="form-control subApplicable" name="subApplicable" /></td>
    `;

    const originalDropdown = document.querySelector('#licenseBody tr:first-child select.documentType');
    if (originalDropdown) {
        const clonedDropdown = originalDropdown.cloneNode(true);
        clonedDropdown.classList.add("documentType");
        clonedDropdown.name = "documentType";
        row.cells[2].appendChild(clonedDropdown);
    }

    tbody.appendChild(row);
}

function deleteRowContNew(buttonElement) {
    const row = buttonElement.closest('tr');
    const tbody = document.getElementById("licenseBody");

    const dataRows = Array.from(tbody.querySelectorAll('tr')).filter(r => r.querySelector('button.removeRowNew'));
	console.log(dataRows.length);
    if (dataRows.length > 1) {
        row.remove();
    } else {
        alert("At least one row must be present.");
    }
}

function moveSelected(fromId, toId) {
    const from = document.getElementById(fromId);
    const to = document.getElementById(toId);
    Array.from(from.selectedOptions).forEach(opt => {
        const newOption = new Option(opt.text, opt.value);
        to.add(newOption);
        from.remove(opt.index);
    });
}

function toCapitalCase(str) {
       return str.toLowerCase().split(' ').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
   }
function saveRenewalDetails() {
    // Step 1: Optional validations
    if (!validateFormData()) return;
    if (!validateDocumentsForRenewal()) return;

    const data = new FormData();

    const aadharFile = $("#aadharDocId").prop("files")[0];
    const panFile = $("#panDocId").prop("files")[0];
    const pfFile = $("#pfDocId").prop("files")[0];
	const selectedOption = $("#vendorCodeId option:selected");
   const selectedPEOption = $("#principalEmployerId option:selected");

    const contractorName = toCapitalCase($("#contractorNameId").val().trim());
    const managerName = toCapitalCase($("#managerNameId").val().trim());
    const locofWork = toCapitalCase($("#locofWorkId").val().trim());
    const natureOfWork = toCapitalCase($("#natureOfWorkId").val().trim());
    const address = toCapitalCase($("#addressId").val().trim());

    // Step 2: Build JSON data
    const jsonData = {
        contractorregId: $("#contractorregId").val().trim(),
        contractorId:selectedOption.val(),
        principalEmployer: $("#principalEmployerId").val(),
       unitId: selectedPEOption.val(),
        //principalEmployer:selectedPEOption.val(),
        contractorName: contractorName,
		vendorCode: selectedOption.data("code"),
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
        mainContractor: $("#mainContractorId").val().trim(),
        requestType: "Renewal",
        renewalDocuments: [],
        selectedWorkOrders: []
    };

    // Step 3: Collect selected work orders from right box
    const selectedWorkOrders = Array.from(document.getElementById("selectedWorkOrders").options)
        .map(opt => opt.value);
    jsonData.selectedWorkOrders = selectedWorkOrders;

    // Step 4: Collect license/doc rows
    $("#licenseBody tr").each(function () {
        const row = $(this);
        const doc = {
            documentType: row.find(".documentType").val(),
            documentNumber: row.find(".documentNumber").val(),
            coverage: row.find(".coverage").val(),
            validFrom: row.find(".validFrom").val(),
            validTo: row.find(".validTo").val(),
            panIndia: row.find(".panIndia").is(":checked"),
            subApplicable: row.find(".subApplicable").is(":checked")
        };
        jsonData.renewalDocuments.push(doc);

        const fileInput = row.find(".attachment")[0];
        if (fileInput && fileInput.files.length > 0) {
            data.append("renewalAttachments", fileInput.files[0]);
        } else {
            data.append("renewalAttachments", new Blob([])); // placeholder
        }
    });

    // Step 5: Append files
    if (aadharFile) data.append("aadharFile", aadharFile);
    if (panFile) data.append("panFile", panFile);
    if (pfFile) data.append("pfFile", pfFile);

    // Step 6: Append JSON
    data.append("jsonData", JSON.stringify(jsonData));

    // Debug log
    for (const [key, value] of data.entries()) {
        console.log(key, value instanceof File ? value.name || "Empty file" : value);
    }

    // Step 7: Send AJAX
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/renewal/save", true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            alert("Renewal saved successfully!");
            loadCommonList('/renewal/list', 'Contractor Renewal List');
        } else {
            alert("Failed to save renewal.");
            console.error("Error:", xhr.status, xhr.responseText);
        }
    };

    xhr.onerror = function () {
        alert("Error occurred while saving renewal.");
    };

    xhr.send(data);
}

function validateDocumentsForRenewal() {
    const rows = document.querySelectorAll('#licenseBody tr');
    const today = new Date();
    const messages = [];

    const seenDocTypes = new Set();
    const seenDocNumbers = new Set();

    let hasESIC = false;
    let hasWC = false;

    if (rows.length === 0) {
        messages.push("At least one document row must be present.");
    }

    rows.forEach((row, index) => {
        const rowNum = index + 1;

        const docTypeElem = row.querySelector('.documentType');
        const docType = docTypeElem?.value?.trim();
        const docTypeText = docTypeElem?.selectedOptions?.[0]?.text?.trim();
        const docNumber = row.querySelector('.documentNumber')?.value?.trim();
        const validFromVal = row.querySelector('.validFrom')?.value;
        const validToVal = row.querySelector('.validTo')?.value;
        const fileInput = row.querySelector('.attachment');

        const validFrom = validFromVal ? new Date(validFromVal) : null;
        const validTo = validToVal ? new Date(validToVal) : null;
        const isFilePresent = fileInput?.files?.length > 0;

        // Check for ESIC/WC
        if (docTypeText?.toUpperCase() === "ESIC") hasESIC = true;
        if (docTypeText?.toUpperCase() === "WC") hasWC = true;

        // Required fields
        if (!docType) messages.push(`Row ${rowNum}: Document type is required.`);
        if (!docNumber) messages.push(`Row ${rowNum}: Document number is required.`);
        if (!isFilePresent) messages.push(`Row ${rowNum}: File attachment is required.`);

        if (!validFrom || validFrom <= today) {
            messages.push(`Row ${rowNum}: "Valid From" must be a future date.`);
        }

        if (!validTo || validTo <= today || (validFrom && validTo <= validFrom)) {
            messages.push(`Row ${rowNum}: "Valid To" must be after "Valid From" and a future date.`);
        }

        // File size
        if (isFilePresent && fileInput.files[0].size > 3 * 1024 * 1024) {
            const file = fileInput.files[0];
            messages.push(`Row ${rowNum}: File "${file.name}" is ${(file.size / (1024 * 1024)).toFixed(2)} MB. Max allowed size is 3 MB.`);
        }

        // Uniqueness validations (only if fields are present)
        if (docType) {
            if (seenDocTypes.has(docType)) {
                messages.push(`Row ${rowNum}: Duplicate document type "${docTypeText}". Each type must be unique.`);
            } else {
                seenDocTypes.add(docType);
            }
        }

        if (docNumber) {
            if (seenDocNumbers.has(docNumber)) {
                messages.push(`Row ${rowNum}: Duplicate document number "${docNumber}". Each must be unique.`);
            } else {
                seenDocNumbers.add(docNumber);
            }
        }
    });

    // Enforce ESIC/WC condition
    if (!hasESIC && !hasWC) {
        messages.push(`At least one document must be of type "ESIC" or "WC".`);
    }

    // Display or clear validation messages
    const messageContainer = document.getElementById("validationMessages");
    if (messages.length > 0) {
        messageContainer.innerHTML = `
            <ul style="list-style: disc; padding-left: 20px; color: red;">
                ${messages.map(msg => `<li>${msg}</li>`).join('')}
            </ul>
        `;
        return false;
    } else {
        messageContainer.innerHTML = '';
        return true;
    }
}

function saveTab2AndGoToTab3() {
    if (!validateFormData()) return;
    if (!validateDocumentsForRenewal()) return;

    const data = new FormData();
    const aadharFile = $("#aadharDocId").prop("files")[0];
    const panFile = $("#panDocId").prop("files")[0];
    const pfFile = $("#pfDocId").prop("files")[0];
    const selectedOption = $("#vendorCodeId option:selected");
    const selectedPEOption = $("#principalEmployerId option:selected");

    const jsonData = {
        contractorregId: $("#contractorregId").val().trim(),
        contractorId: selectedOption.val(),
        vendorCode: selectedOption.data("code"),
        principalEmployer: $("#principalEmployerId").val(),
       // principalEmployer:selectedPEOption.val(),
        unitId: selectedPEOption.val(),
        contractorName: toCapitalCase($("#contractorNameId").val().trim()),
        email: $("#emailId").val().trim(),
        mobile: $("#mobileId").val().trim(),
        aadhar: $("#aadharId").val().trim(),
        pan: $("#panId").val().trim().toUpperCase(),
        gst: $("#gstId").val().trim().toUpperCase(),
        address: toCapitalCase($("#addressId").val().trim()),
        pfApplyDate: $("#pfApplyDateId").val().trim(),
        managerName: toCapitalCase($("#managerNameId").val().trim()),
        locofWork: toCapitalCase($("#locofWorkId").val().trim()),
        totalStrength: $("#totalStrengthId").val().trim(),
        rcMaxEmp: $("#rcMaxEmpId").val().trim(),
        pfNum: $("#pfNumId").val().trim().toUpperCase(),
        natureOfWork: toCapitalCase($("#natureOfWorkId").val().trim()),
        contractFrom: $("#contractFromId").val(),
        contractTo: $("#contractToId").val(),
        contractType: $("#contractTypeId").val(),
        rcVerified: $("#rcVerifiedId").is(":checked") ? "Yes" : "No",
        mainContractor: $("#mainContractorId").val().trim(),
        requestType: "Renewal",
        renewalDocuments: []
    };

    $("#licenseBody tr").each(function () {
        const row = $(this);
        const doc = {
            documentType: row.find(".documentType").val(),
            documentNumber: row.find(".documentNumber").val(),
            coverage: row.find(".coverage").val(),
            validFrom: row.find(".validFrom").val(),
            validTo: row.find(".validTo").val(),
            panIndia: row.find(".panIndia").is(":checked"),
            subApplicable: row.find(".subApplicable").is(":checked")
        };
        jsonData.renewalDocuments.push(doc);

        const fileInput = row.find(".attachment")[0];
        if (fileInput && fileInput.files.length > 0) {
            data.append("renewalAttachments", fileInput.files[0]);
        } else {
            data.append("renewalAttachments", new Blob([]));
        }
    });

    if (aadharFile) data.append("aadharFile", aadharFile);
    if (panFile) data.append("panFile", panFile);
     if (pfFile) data.append("pfFile", pfFile);
    data.append("jsonData", JSON.stringify(jsonData));

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/renewal/save", true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            alert("Basic & license info saved successfully.");
			// ✅ Disable all fields in tab1 and tab2
			    disableTabs(['tab1', 'tab2']);

			    // ✅ Hide Save button
			    const saveButton = document.getElementById('saveButton');
			    if (saveButton) saveButton.style.display = 'none';
            populateTab3Data(jsonData.contractorId, jsonData.principalEmployer,jsonData.vendorCode,jsonData.contractorregId);
        } else {
            alert("Error saving.");
        }
    };
    xhr.send(data);
}
function disableTabs(tabIds) {
    tabIds.forEach(tabId => {
        const tab = document.getElementById(tabId);
        if (!tab) return;
        
        const inputs = tab.querySelectorAll("input, select, textarea, button");
        inputs.forEach(el => {
            el.disabled = true;
            el.readOnly = true;
            if (el.type === "checkbox" || el.type === "radio") {
                el.disabled = true;
            }
        });
    });
}

function populateTab3Data(contractorId, unitId, vendorCode, contractorregId) {
    fetch(`/CWFM/renewal/getAllAvailableWoAndLicense?unitId=${unitId}&contractorId=${contractorId}&vendorCode=${vendorCode}&contractorregId=${contractorregId}`)
        .then(res => {
            if (!res.ok || res.status === 204) {
                alert("No data found for selected contractor.");
                return null;
            }
            return res.json();
        })
        .then(data => {
            if (!data) return;

            const availableBox = document.getElementById("availableWorkOrders");
            const licenseDropdown = document.getElementById("availableLicense");
            availableBox.innerHTML = "";
            licenseDropdown.innerHTML = "";

            (data.availableWos || []).forEach(wo => {
                const option = new Option(wo, wo);
                option.style.color = "black";
                availableBox.add(option);
            });

            (data.availableLicenses || []).forEach(lic => {
                const option = new Option(lic, lic);
				option.style.color = "black";
                licenseDropdown.add(option);
            });
			showTabOther('tab3');
        })
        .catch(err => {
            console.error("Error fetching Tab 3 data:", err);
            alert("Error loading work orders and licenses.");
        });
}

function saveWorkOrderInfo() {
    const selectedWOs = Array.from(document.getElementById("selectedWorkOrders").options)
        .map(opt => opt.value);
        
        if (!selectedWOs || selectedWOs.length === 0) {
        alert("Please select at least one Work Order.");
        return; // ⛔ stop execution
    }
    const licenseId = 		Array.from(document.getElementById("selectedLicense").options)
		        .map(opt => opt.value);
		        
		        if (!licenseId || licenseId.length === 0) {
        alert("Please select at least one License.");
        return; // ⛔ stop execution
    }
    const contractorRegId = document.getElementById("contractorregId").value;
	const selectedOption = $("#vendorCodeId option:selected");
	const contractorId =  selectedOption.val();
	const unitId = $("#principalEmployerId").val().trim();
    const payload = {
        contractorRegId,
        selectedWOs,
        licenseId,
		unitId,
		contractorId
		
    };

    fetch("/CWFM/renewal/saveWorkOrderInfo", {
        method: "POST",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
    .then(res => {
        if (res.ok) {
            alert("Work order info saved.");
            loadCommonList("/renewal/listingFilter", "Contractor Renewal List");
        } else {
            alert("Failed to save work order info.");
        }
    });
}
function makeSelectReadOnly(selectId) {
    const select = document.getElementById(selectId);
    select.addEventListener("mousedown", e => e.preventDefault());
    select.addEventListener("keydown", e => e.preventDefault());
}

function searchContRenewBasedOnPE() {
					    var principalEmployerId = $('#principalEmployerId').val();
					    
						var deptId=$("#deptId").val();
					    $.ajax({
					        url: '/CWFM/renewal/list',
					        type: 'POST',
					        data: {
					            principalEmployerId: principalEmployerId,
								deptId:deptId
					        },
					        success: function(response) {
					            var tableBody = $('#contractorlisttable tbody');
								   if ($.fn.DataTable.isDataTable('#contractorlisttable')) {
									$('#contractorlisttable').DataTable().destroy();
								}
					            tableBody.empty();
					            if (response.length > 0) {
					                $.each(response, function(index, wo) {
					                    var row = '<tr  >' +
												'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.contractorregId + '"></td>'+
												'<td  >' + wo.contractorregId + '</td>' +
												 '<td  >' + wo.vendorCode + '</td>' +
					                              '<td  >' + wo.contractorName + '</td>' +
												  '<td  >' + wo.statusValue + '</td>' +	
												  
												  '<td  >' + wo.requestType + '</td>' +
												 
												 			                             
					                              '</tr>';
					                    tableBody.append(row);
					                });
					            } 								// ✅ Always init after rows are drawn
									initWorkmenTable("contractorlisttable");
					        },
					        error: function(xhr, status, error) {
					            console.error("Error fetching data:", error);
					        }
					    });
					}  
					function approveRejectContRenew(status){
											let isValid=true;
										
											 const approvercomments = $("#approvercomments").val().trim();
										if (approvercomments === "" && status==5) {
										    $("#error-approvercomments").show();
										    alert("Comments Required in Comments");
										    isValid = false;
										}else{
											$("#error-approvercomments").hide();
										}
										if(isValid){
											const data = {
												approverId : $("#userId").val().trim(),
												comments : $("#approvercomments").val().trim(),
												status : status,
												transactionId : $("#contractorregId").val().trim(),
												approverRole : $("#roleName").val().trim(),
												roleId :$("#roleId").val().trim(),
												type : '1',
												unitId:$("#unitId").val().trim(),
											};
												  const xhr = new XMLHttpRequest();
										xhr.open("POST", "/CWFM/renewal/approveRejectContRenew", true); // Replace with your actual controller URL
										xhr.setRequestHeader("Content-Type", "application/json"); // Set content type for JSON
										xhr.onload = function() {
										    if (xhr.status === 200) {
										        // Handle successful response
										        console.log("Data saved successfully:", xhr.responseText);
												sessionStorage.setItem("successMessage", "Contractor Renewal approved/rejected successfully!");
										      
										                loadCommonList('/renewal/listingFilter', 'Contractor Renewal');
										            
										    } else {
										        // Handle error response
										        console.error("Error saving data:", xhr.statusText);
												sessionStorage.setItem("errorMessage", "Failed to approve/reject Contractor Renewal!");
										    }
										};

										xhr.onerror = function() {
										    console.error("Request failed");
											sessionStorage.setItem("errorMessage", "Failed to approve/reject Contractor Renewal!");
										};

										// Send the data object as a JSON string
										xhr.send(JSON.stringify(data));
											}else{
												//error 
											}
											}//eofunc
											
															
