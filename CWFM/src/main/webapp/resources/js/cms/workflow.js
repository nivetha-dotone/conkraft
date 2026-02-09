function getBusinessType(unitId) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/workflow/getAllBusinessType?unitId=" + unitId ;
    //alert("URL: " + url);
    xhr.open("GET", url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            // Parse the response as a JSON array of contractor objects
            var businessTypes = JSON.parse(xhr.responseText);
            console.log("Response:", businessTypes);
            
            // Find the contractor select element
            var businessSelect = document.getElementById("businessType");
            
            // Clear existing options
            businessSelect.innerHTML = '<option value="">Please select Business Type</option>';
            
            // Populate the dropdown with the new list of contractors
            businessTypes.forEach(function(business) {
                var option = document.createElement("option");
                option.value = business.buId;
                option.text = business.buName;
                businessSelect.appendChild(option);
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


function initWorkflowPage() {
    // Optional initialization logic
}

/*function onModuleChange() {
    const unitId = document.getElementById("principalEmployer").value;
    const businessType = document.getElementById("businessType").value;
    const moduleId = document.getElementById("module").value;

    if (unitId && businessType && moduleId) {
        $.ajax({
            type: "GET",
            url: "/CWFM/workflow/getExistingWorkflow",
            data: { unitId, businessType, moduleId },
            success: function (data) {
                document.querySelector(`input[name='workflowType'][value='${data.workflowType}']`).checked = true;
                toggleHierarchyColumn();

                const tbody = document.querySelector("#approverTable tbody");
                tbody.innerHTML = "";

				data.hierarchy.forEach(row => {
				    const newRow = createRowFromTemplate();

				    // Set action dropdown value and update label
				    const actionSelect = newRow.querySelector("select[name='action']");
				    actionSelect.value = row.actionId;
				    if (row.actionName) {
				        const exists = [...actionSelect.options].some(opt => opt.value === row.actionId);
				        if (!exists) {
				            actionSelect.add(new Option(row.actionName, row.actionId));
				        }
				    }

				    // Set role dropdown value and update label
				    const roleSelect = newRow.querySelector("select[name='role']");
				    roleSelect.value = row.roleId;
				    if (row.roleName) {
				        const exists = [...roleSelect.options].some(opt => opt.value === row.roleId);
				        if (!exists) {
				            roleSelect.add(new Option(row.roleName, row.roleId));
				        }
				    }

				    newRow.querySelector("input[name='hierarchyIndex']").value = row.hierarchy;
				    tbody.appendChild(newRow);
				});

            },
            error: function () {
                alert("Failed to load workflow data.");
            }
        });
    }
}*/

function toggleHierarchyColumn() {
    const selectedWorkflow = document.querySelector("input[name='workflowType']:checked")?.value;
    const isParallel = selectedWorkflow === "3";

    const headerCols = document.querySelectorAll("th.hierarchy-col");
    const rowCols = document.querySelectorAll("td.hierarchy-col");

    headerCols.forEach(th => th.style.display = isParallel ? "none" : "");
    rowCols.forEach(td => td.style.display = isParallel ? "none" : "");

    // Optional: disable hierarchy input
    document.querySelectorAll("input[name='hierarchyIndex']").forEach(input => {
        input.disabled = isParallel;
    });
}

function createRowFromTemplate() {
    const template = document.getElementById("templateRow");
    const clone = template.cloneNode(true);
    clone.removeAttribute("id");
    return clone;
}

function addRow() {
    const tbody = document.querySelector("#approverTable tbody");
    const newRow = createRowFromTemplate();
    tbody.appendChild(newRow);
    toggleHierarchyColumn(); // Apply current visibility state
}

function deleteSelected() {
    document.querySelectorAll(".rowSelect:checked").forEach(checkbox => {
        checkbox.closest("tr").remove();
    });
}


function onModuleChange() {
    const unitId = document.getElementById("principalEmployer").value;
    //const businessType = document.getElementById("businessType").value;
    const moduleId = document.getElementById("module").value;
	const actionSelect = document.getElementById("actionDropdown");
	 const actionId = actionSelect?.value;
	 const actionName = actionSelect?.options[actionSelect.selectedIndex]?.text;
    const tbody = document.querySelector("#approverTable tbody");
	//if (unitId && businessType && moduleId) {
    if (unitId  && moduleId) {
        $.ajax({
            type: "GET",
            url: "/CWFM/workflow/getExistingWorkflow",
			//data: { unitId, businessType, moduleId,actionName },
            data: { unitId,  moduleId,actionName },
            success: function (data) {
                tbody.innerHTML = "";

                // Uncheck all radio buttons first
                document.querySelectorAll("input[name='workflowType']").forEach(radio => radio.checked = false);

                if (data && data.workflowType !== undefined) {
                    const selectedRadio = document.querySelector(`input[name='workflowType'][value='${data.workflowType}']`);
                    if (selectedRadio) selectedRadio.checked = true;

                    toggleHierarchyColumn();

                    if (Array.isArray(data.hierarchy) && data.hierarchy.length > 0) {
                        data.hierarchy.forEach(row => {
                            const newRow = createRowFromTemplate();

                            // Set role dropdown value
                            const roleSelect = newRow.querySelector("select[name='role']");
                            roleSelect.value = row.roleId;
                            if (row.roleName && ![...roleSelect.options].some(opt => opt.value === row.roleId)) {
                                roleSelect.add(new Option(row.roleName, row.roleId));
                            }

                            // Set hierarchy index
                            const hierarchyInput = newRow.querySelector("input[name='hierarchyIndex']");
                            hierarchyInput.value = (row.hierarchy !== null && row.hierarchy !== undefined) ? row.hierarchy : 
                                (data.workflowType === "3" ? 0 : ""); 

                            tbody.appendChild(newRow);
                        });
                    }
                }
            },
            error: function () {
                alert("Failed to load workflow data.");
                tbody.innerHTML = "";
                document.querySelectorAll("input[name='workflowType']").forEach(radio => radio.checked = false);
            }
        });
    } else {
        tbody.innerHTML = "";
        document.querySelectorAll("input[name='workflowType']").forEach(radio => radio.checked = false);
    }
}

function saveWorkflow() {
    const workflowType = document.querySelector("input[name='workflowType']:checked")?.value;
    const unitId = document.getElementById("principalEmployer").value;
    ///const businessType = document.getElementById("businessType").value;
    const moduleId = document.getElementById("module").value;

    const actionSelect = document.getElementById("actionDropdown");
    const actionId = actionSelect?.value;
    const actionName = actionSelect?.options[actionSelect.selectedIndex]?.text;

    //if (!unitId || !businessType || !moduleId || !workflowType || !actionId) {
		if (!unitId  || !moduleId || !workflowType || !actionId) {
        alert("Please fill all required fields.");
        return;
    }

    const roleMap = new Map();
    const hierarchyMap = new Map();
    const hierarchy = [];
    let hasDuplicates = false;

    // Clear previous highlights and messages
    document.querySelectorAll("#approverTable tbody tr").forEach(row => {
        row.classList.remove("error-row");
        const nextRow = row.nextElementSibling;
        if (nextRow && nextRow.classList.contains("error-message-row")) {
            nextRow.remove();
        }
    });

    document.querySelectorAll("#approverTable tbody tr").forEach((row) => {
        const roleSelect = row.querySelector("select[name='role']");
        const roleId = roleSelect.value;
        const roleName = roleSelect.options[roleSelect.selectedIndex]?.text;

        //let hierarchyIndex = row.querySelector("input[name='hierarchyIndex']")?.value;
        //hierarchyIndex = workflowType === "3" ? (hierarchyIndex ? parseInt(hierarchyIndex) : 0) : hierarchyIndex;

		let hierarchyIndex;

		    if (workflowType === "3") {
		        // Auto-increment for parallel workflow
		        hierarchyIndex = hierarchy.length;
		    } else {
		        hierarchyIndex = row.querySelector("input[name='hierarchyIndex']")?.value;
		    }
			
        let duplicateRole = false;
        let duplicateHierarchy = false;

        if (roleMap.has(roleId)) {
            duplicateRole = true;
            roleMap.get(roleId).classList.add("error-row");
        } else {
            roleMap.set(roleId, row);
        }

        if (workflowType !== "3") {
            if (hierarchyMap.has(hierarchyIndex)) {
                duplicateHierarchy = true;
                hierarchyMap.get(hierarchyIndex).classList.add("error-row");
            } else {
                hierarchyMap.set(hierarchyIndex, row);
            }
        }

        if (duplicateRole || duplicateHierarchy) {
            hasDuplicates = true;
            row.classList.add("error-row");

            // Generate appropriate message
            let message = "";
            if (duplicateRole && duplicateHierarchy) {
                message = "Duplicate role name and hierarchy index found.";
            } else if (duplicateRole) {
                message = "Duplicate role name found.";
            } else if (duplicateHierarchy) {
                message = "Duplicate hierarchy index found.";
            }

            const errorRow = document.createElement("tr");
            errorRow.classList.add("error-message-row");

            const td = document.createElement("td");
            td.colSpan = 3;
            td.className = "error-message-cell";
            td.innerText = message;

            errorRow.appendChild(td);
            row.parentNode.insertBefore(errorRow, row.nextSibling);

            return;
        }

        hierarchy.push({
            actionId,
            actionName,
            roleId,
            roleName,
            hierarchy: hierarchyIndex
        });
    });

    if (hasDuplicates) return;

    const payload = {
        unitId,
        //businessType,
        moduleId,
        workflowType,
        hierarchy,
		actionName,
		actionId
    };

    $.ajax({
        type: "POST",
        url: "/CWFM/workflow/saveWorkflow",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: function () {
            alert("Workflow saved successfully!");
            loadCommonList('/workflow/list', 'Workflow');
        },
        error: function () {
            alert("Error saving workflow.");
        }
    });
}

 function overrideActionsBasedOnModule() {
    const moduleDropdown = document.getElementById("module");
    const actionDropdown = document.getElementById("actionDropdown");

    // Get selected module text and convert to uppercase (for case-insensitive match)
    const selectedModuleName = moduleDropdown.options[moduleDropdown.selectedIndex].text.trim().toUpperCase();

    // Define manual override actions (keys in uppercase)
    const moduleActionsMap = {
        "WORKMEN ONBOARDING": [
            "CREATE", "BLOCK", "UNBLOCK", "BLACKLIST",
            "DEBLACKLIST", "RENEW", "CANCEL","PROJECT GATEPASS"
        ],
        "CONTRACTOR RENEWAL": ["CONTRACTOR RENEWAL"],
        "BILL VERIFICATION": ["Bill Creation"]
    };

    // Reset action dropdown
    actionDropdown.innerHTML = '<option value="">Please select Action</option>';

    // Check case-insensitively
    if (selectedModuleName && moduleActionsMap[selectedModuleName]) {
        moduleActionsMap[selectedModuleName].forEach(action => {
            const option = document.createElement("option");
            option.value = action;
            option.text = action;
            actionDropdown.appendChild(option);
        });
    }
}
