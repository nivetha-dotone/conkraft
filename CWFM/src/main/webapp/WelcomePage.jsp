<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
      <!--  <script src="resources/js/commonjs.js"></script> -->
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/contractor.js"></script>
    <script src="resources/js/cms/workorder.js"></script>
       <script src="resources/js/cms/workmen.js"></script>
    <script src="resources/js/cms/report.js"></script>
     <script src="resources/js/cms/bill.js"></script>
    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/jquery-3.6.0.min.js"></script>
   <!--    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    Include jQuery UI
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    
    Include jQuery UI CSS
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
     -->
    <script>
    var contextPath = '<%= request.getContextPath() %>';
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
	        minDate: 0              // Prevent selecting future dates
	    });
	}
function redirectToPEAdd() {
    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/principalEmployer/add", true);
    xhr.send();
}
function loadCommonList(path,heading) {
	 updateHeading(heading);
	    var url = contextPath + path;
    // Construct the URL using the contextPath variable SystemAdmin
  //  var url = contextPath + path;
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
function loadQobAdd(path,heading,userId) {
	//alert("loadQobAdd"+userId);
	 updateHeading(heading);
	    var url = contextPath + path + "?userId=" + encodeURIComponent(userId);
   // Construct the URL using the contextPath variable
 //  var url = contextPath + path;
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
function checkSessionStatus() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if (!response.sessionActive) {
                // Session has expired, redirect to login page
                window.location.href = "/CWFM";
            }
        }
    };
    xhr.open("GET", "/checkSessionStatus", true);
    xhr.send();
}
 function showTabNew(tabId) {
    var tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(function(content) {
        content.classList.remove('active');
    });
    var tabs = document.querySelectorAll('.tabs button');
    tabs.forEach(function(tab) {
        tab.classList.remove('active');
    });
    document.getElementById(tabId).classList.add('active');
    document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
 // Show the Approve Reject button only on the Documents tab
    var approveButton = document.getElementById('approveButton');
    var rejectButton = document.getElementById('rejectButton');
    var actionButton = document.getElementById('actionButton');
    if (tabId === 'tab5') { // Assuming 'tab5' is the Documents tab
    	approveButton.style.display = 'inline-block'; // Show Save button
    	rejectButton.style.display = 'inline-block'; // Show Save button
    	actionButton.style.display = 'inline-block'; // Show Save button
    } else {
    	approveButton.style.display = 'none'; // Hide Save button
    	rejectButton.style.display = 'none'; // Hide Save button
    	actionButton.style.display = 'none'; // Hide Save button
    }
} 
function validateCurrentTab() {
    let isValid = true;
    const activeTabId = document.querySelector('.tab-content.active').id;
    if (activeTabId === 'tab1') {
        isValid = validateBasicData();
    } 
    else if(activeTabId === 'tab2'){
    	isValid = validateEmploymentInformation();
    }else if(activeTabId === 'tab3'){
    	isValid = validateOtherInformation();
    }else if(activeTabId === 'tab4'){
    	isValid = validateWages();
    }else{
    	isValid = true;
    }

    return isValid;
}

// Function to show the selected tab
function showTab(tabId) {
    // Check if current tab fields are valid before switching tabs
    if (!validateCurrentTab()) {
     return; // Prevent tab switch if validation fails
    }

    // Hide all tab contents
    var tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(function(content) {
        content.classList.remove('active');
    });

    // Remove active class from all tabs
    var tabs = document.querySelectorAll('.tabs button');
    tabs.forEach(function(tab) {
        tab.classList.remove('active');
    });

    // Show the selected tab content and add active class to the clicked tab
    document.getElementById(tabId).classList.add('active');
    document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
    
 // Show the Save button only on the Documents tab
    var saveButton = document.getElementById('saveButton');
 	
    if (tabId === 'tab5') { // Assuming 'tab5' is the Documents tab
        saveButton.style.display = 'inline-block'; // Show Save button
    } else {
        saveButton.style.display = 'none'; // Hide Save button
    }
}

function showTabOther(tabId) {
    // Check if current tab fields are valid before switching tabs
    

    // Hide all tab contents
    var tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(function(content) {
        content.classList.remove('active');
    });

    // Remove active class from all tabs
    var tabs = document.querySelectorAll('.tabs button');
    tabs.forEach(function(tab) {
        tab.classList.remove('active');
    });

    // Show the selected tab content and add active class to the clicked tab
    document.getElementById(tabId).classList.add('active');
    document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
    
 // Show the Save button only on the Documents tab
    var saveButton = document.getElementById('saveButton');
 	
    if (tabId === 'tab5') { // Assuming 'tab5' is the Documents tab
        saveButton.style.display = 'inline-block'; // Show Save button
    } else {
        saveButton.style.display = 'none'; // Hide Save button
    }
}
function toggleSelectAllGMTYPE() {
    const checkboxes = document.querySelectorAll('input[name="selectedGMTIds"]');
    checkboxes.forEach(checkbox => checkbox.checked = document.getElementById('selectAllCheckbox').checked);
}

function deleteSelectedGMTYPE() {
    const selectedIds = Array.from(document.querySelectorAll('input[name="selectedGMTIds"]:checked')).map(cb => parseInt(cb.value, 10));
    if (selectedIds.length === 0) {
        alert("Please select at least one GM Type to delete.");
        return;
    }

   

    // Log the selected IDs to see what is being sent
    console.log("Selected GM Type IDs:", selectedIds);

    $.ajax({
        type: 'POST',
        url: '/CWFM/generalController/deleteGMTypes',
        contentType: 'application/json', // Set content type to JSON
        data: JSON.stringify(selectedIds), // Send data as JSON string
        success: function(response) {
            if (response.success) {
                alert("Selected items deleted successfully.");
                loadCommonList('/generalController/gmType', 'General Type');
            } else {
                alert(response.message || "Error deleting selected items.");
            }
        },
        error: function(xhr, status, error) {
            // Log the error response for debugging
            console.error("Error details:", xhr.responseText, status, error);
            alert("An unexpected error occurred. Please check the console for details.");
        }
    });
}
function setupOrgLevelTable() {
    const tableBody = document.querySelector("#orgLevelTable tbody");

    if (!tableBody) {
        console.error("Table body not found. Please ensure the ID 'orgLevelTable' is correct.");
        return;
    }

    // Add Row
    tableBody.addEventListener("click", function (event) {
        if (event.target.classList.contains("addRow")) {
            const row = event.target.closest("tr");
            const clonedRow = row.cloneNode(true);
            clonedRow.querySelectorAll("input").forEach(input => input.value = ""); // Clear input values
            tableBody.appendChild(clonedRow);
        }
    });

    // Remove Row
    tableBody.addEventListener("click", function (event) {
        if (event.target.classList.contains("removeRow")) {
            if (tableBody.querySelectorAll("tr").length > 1) {
                const row = event.target.closest("tr");
                tableBody.removeChild(row);
            } else {
                alert("At least one row is required.");
            }
        }
    });
}
function toggleSelectAllOrgLevel() {
    const checkboxes = document.querySelectorAll('input[name="selectedOrgLevels"]');
    checkboxes.forEach(checkbox => checkbox.checked = document.getElementById('selectAllOrgLevelCheckbox').checked);
}
//Add a new empty row
function addNewRow() {
        const tableBody = document.querySelector("table tbody");
        const newRow = document.createElement("tr");

        // Create a unique ID for each row
        const rowId = new Date().getTime();

        newRow.innerHTML = `
            <td class="checkbox-cell">
                <input type="checkbox" name="selectedOrgLevels" data-row-id="${rowId}">
            </td>
            <td><input type="text" name="orgLevelName[]" id="orgLevelName_${rowId}" placeholder="Org Level Name" required></td>
            <td><input type="text" name="shortName[]" id="shortName_${rowId}" placeholder="Short Name" required></td>
            <td><input type="number" name="hierarchy[]" id="hierarchy_${rowId}" placeholder="Hierarchy" required></td>
        `;
        tableBody.appendChild(newRow);
    }
function deleteSelectedOrgLevel() {
    const selectedIds = Array.from(document.querySelectorAll('input[name="selectedOrgLevels"]:checked')).map(input => parseInt(input.value));
    console.log("Selected Org Level IDs:", selectedIds);

    if (selectedIds.length === 0) {
        alert("Please select at least one Org Level to delete.");
        return;
    }

    $.ajax({
    	method: 'PUT',
        url: '/CWFM/org-level/update', // Correct endpoint
        contentType: 'application/json',
        data: JSON.stringify(selectedIds), // Send array of IDs as JSON
        success: function(response) {
            console.log("Successfully updated (set inactive) org levels:", response);
            alert(response); // Optional: Alert the success message
            // Optionally refresh the table or remove rows from the DOM
           loadCommonList('/org-level/list', 'Org Levels');
            const failedOrgLevels = response.failedOrgLevels || [];
            const successfulOrgLevels = response.successfulOrgLevels || [];

            console.log('Successfully updated org levels:', successfulOrgLevels);
            console.log('Failed to update org levels:', failedOrgLevels);

            successfulOrgLevels.forEach(orgId => {
                const orgElement = document.getElementById(`orgLevel_${orgId}`);
                if (orgElement) {
                    orgElement.remove();
                } else {
                    console.warn(`Element for Org Level ${orgId} not found.`);
                }
            });
        },
        error: function(xhr, status, error) {
            console.error("Error updating org levels:", error);
            alert("Failed to update some or all Org Levels. Check console for details.");
        }
    });
}
function submitOrgLevel() {
    const orgLevelRows = document.querySelectorAll('#orgLevelTable tbody tr'); // Select all rows in the table
    let orgLevelsData = [];
    let validData = true; // Track validity of the entire form

    orgLevelRows.forEach((row, index) => {
        const orgLevelDefIdElem = row.querySelector('input[name="orgLevelDefId[]"]');
        const orgLevelNameElem = row.querySelector('input[name="orgLevelName[]"]');
        const shortNameElem = row.querySelector('input[name="shortName[]"]');
        const hierarchyElem = row.querySelector('input[name="hierarchy[]"]');

        // Extract values or set defaults for missing elements
        const orgLevelDefId = orgLevelDefIdElem ? orgLevelDefIdElem.value.trim() : null;
        const orgLevelName = orgLevelNameElem ? orgLevelNameElem.value.trim() : '';
        const shortName = shortNameElem ? shortNameElem.value.trim() : '';
        const hierarchy = hierarchyElem ? hierarchyElem.value.trim() : '';

        console.log(`Processing Row ${index + 1}:`);
        console.log('OrgLevelDefId:', orgLevelDefId || 'New Entry (null)');
        console.log('Name:', orgLevelName || 'Not Found');
        console.log('Short Name:', shortName || 'Not Found');
        console.log('Hierarchy:', hierarchy || 'Not Found');

        // Validate fields for each row
        if (!orgLevelName || !shortName || !hierarchy) {
            console.error(`Row ${index + 1} is missing required fields.`);
            alert(`Please fill in all fields for row ${index + 1}.`);
            validData = false;
            return; // Skip processing this row
        }

        // Push valid data to the array
        orgLevelsData.push({
            orgLevelDefId: orgLevelDefId || null, // Assign null for new rows
            name: orgLevelName,
            shortName: shortName,
            orgHierarchyLevel: parseInt(hierarchy, 10)
        });
    });

    if (!validData || orgLevelsData.length === 0) {
        alert('Please fix errors before submitting.');
        return;
    }

    console.log('Collected Org Levels Data:', orgLevelsData);

    // AJAX request to send data to the server
    $.ajax({
        type: 'POST',
        url: '/CWFM/org-level/save',
        contentType: 'application/json',
        data: JSON.stringify(orgLevelsData),
        success: function (response) {
            console.log('Server Response:', response);
            alert('Org Levels saved successfully!');
            loadCommonList('/org-level/list', 'Org Levels');
        },
        error: function (xhr, status, error) {
            console.error('Error during request:', error);
            alert('Error during request: ' + xhr.responseText);
        }
    });
}




function loadOrgLevels() {
    // You can reload the Org Levels from the server or update the DOM accordingly
    location.reload(); // Refresh the page for now
}

function fetchOrgLevelEntries() {
    var orgLevelDefId = document.getElementById("orgLevelDropdown").value;
    
    // If no org level is selected, clear the entries
    if (orgLevelDefId === "") {
        document.getElementById("orgLevelEntries").innerHTML = "<p>No entries available for the selected org level.</p>";
        return;
    }
    
    // Perform an AJAX request to fetch entries for the selected org level
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/CWFM/org-level-entryController/org-level-entry?orgLevelDefId=" + orgLevelDefId, true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            document.getElementById("orgLevelEntries").innerHTML = xhr.responseText;
            loadCommonList('/org-level-entryController/org-level-entry', 'Org Levels');
        } else {
            alert("Failed to load org level entries.");
        }
    };
    xhr.send();
}
function addNewEntryRow() {
    const tableBody = document.querySelector("table tbody");
    const newRow = document.createElement("tr");

    // Create a unique ID for each row
    const rowId = new Date().getTime();  // Using current timestamp as a unique ID

    newRow.innerHTML = `
        <td><input type="text" name="name[]" id="name_${rowId}" placeholder="Name" required></td>
        <td><input type="text" name="description[]" id="description_${rowId}" placeholder="Description" required></td>
        <td>
            <button type="button" onclick="removeEntryRow(this)">Remove</button>
        </td>
    `;

    tableBody.appendChild(newRow);  // Append the new row to the table body
}

function removeEntryRow(button) {
    // Removes the row containing the clicked remove button
    const row = button.closest("tr");
    row.remove();
}
function fetchDataByOrgLevel() {
    const orgLevelId = document.getElementById('orgLevelDropdown').value;

    $.ajax({
        type: 'GET',
        url: '/CWFM/org-level-entryController/getGMTypesByOrgLevel',  // Adjust this URL to your backend endpoint
        data: { orgLevelId: orgLevelId },  // Pass selected Org Level
        success: function(response) {
            if (response.success) {
                // Clear current table rows and populate with new data
                const tableBody = document.querySelector('table tbody');
                tableBody.innerHTML = ''; // Clear existing rows

                response.data.forEach(gmType => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td><input type="checkbox" name="selectedGMTIds" value="${gmType.gmTypeId}"></td>
                        <td>${gmType.gmType}</td>
                    `;
                    tableBody.appendChild(row);
                });
            } else {
                alert(response.message || 'Error fetching GM Types for selected Org Level');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('An error occurred while fetching data.');
        }
    });
}
/* function navigateToOrgLevel() {
    const orgLevelDefId = document.getElementById('orgLevelDefId').value; // Get selected value
alert(orgLevelDefId);
    if (orgLevelDefId) {
        // Redirect to the correct endpoint with the selected orgLevelDefId
        window.location.href = `/CWFM/org-level-entryController/showorg-level-entry/${orgLevelDefId}`;
    } else {
        alert("Please select a valid Organization Level."); // Handle empty selection
    }
} */

function navigateToOrgLevel() {
    const orgLevelDefId = document.getElementById('orgLevelDefId').value;
    if (!orgLevelDefId) {
        alert("Please select a valid Organization Level.");
        return;
    }
    document.getElementById("mainContent").innerHTML = "<p>Loading data...</p>";
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/CWFM/org-level-entryController/showorg-level-entry/" + orgLevelDefId, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        } else {
            alert("Failed to load organization level data. Please try again.");
            document.getElementById("mainContent").innerHTML = "<p>Error loading data. Please try again.</p>";
        }
    };
    xhr.onerror = function () {
        alert("An error occurred while making the request.");
        document.getElementById("mainContent").innerHTML = "<p>Error loading data. Please check your connection and try again.</p>";
    };
    xhr.send();
}
function saveOrgEntries() {
    const orgLevelEntryId = document.getElementById('orgLevelEntryId').value;
    const orgLevelDefId = document.getElementById('orgLevelDefId').value;
    const entryName = document.getElementById('entryName').value;
    const description = document.getElementById('description').value;

    // Validate the orgLevelDefId
    if (!orgLevelDefId) {
        alert('Please select an Organization Level.');
        return;
    }

    if (entryName === '' || description === '') {
        alert('Please enter Name and Description.');
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/org-level-entryController/save-org-level-entry", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    var data = JSON.stringify({
        orgLevelEntryId: orgLevelEntryId,
        orgLevelDefId: orgLevelDefId,
        name: entryName,
        description: description
    });

    xhr.onload = function() {
        if (xhr.status === 200) {
            var response = JSON.parse(xhr.responseText); // Parse response data
            if (response.success) {
                alert(response.message); // Display success message
                updateTable(); // Call the updateTable function to refresh the table content
            } else {
                console.error("Failed to save entry: " + response.message);
                alert("Failed to save entry. Please try again.");
            }
        } else {
            console.error("Request error: " + xhr.statusText);
            alert("An error occurred while saving the Org Level Entry.");
        }
    };

    xhr.onerror = function() {
        console.error("Request error");
        alert("An error occurred while saving the Org Level Entry.");
    };

    xhr.send(data);
}

function updateTable() {
    const orgLevelDefId = document.getElementById('orgLevelDefId').value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/CWFM/org-level-entryController/showorg-level-entry/" + orgLevelDefId, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText; // Update the table body with the new content
        } else {
            console.error("Request error: " + xhr.statusText);
        }
    };

    xhr.send();
}


function deleteOrgLevelEntry(orgLevelEntryId, orgLevelDefId) {
    if (confirm("Are you sure you want to delete this entry?")) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/CWFM/org-level-entryController/delete-org-level-entry", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        
        var data = "orgLevelEntryId=" + encodeURIComponent(orgLevelEntryId) + "&orgLevelDefId=" + encodeURIComponent(orgLevelDefId);
        
        xhr.onload = function() {
            if (xhr.status == 200) {
            	document.getElementById("mainContent").innerHTML = xhr.responseText;
                // Successfully deleted, reload the page or update the view
                //window.location.reload(); // Reload to reflect changes
            } else {
                console.error("Failed to delete entry: " + xhr.statusText);
                alert("Failed to delete entry. Please try again.");
            }
        };
        
        xhr.send(data);
    }
}
function editEntry(id, name, desc) {
    console.log("Editing Entry ID:", id);
    console.log("Editing Entry name:", name);
    console.log("Editing Entry desc:", desc);

    // Access the table cells for the entry
    const nameTd = document.getElementById(`name-${id}`);
    const descTd = document.getElementById('name-${id}');

    console.log("Editing Entry nameTd:", nameTd);
    console.log("Editing Entry descTd:", descTd);
    if (nameTd && descTd) {
        // Clear the current content
        nameTd.innerHTML = '';
        descTd.innerHTML = '';

        // Create input fields for editing
        const nameInput = document.createElement('input');
        nameInput.type = 'text';
        nameInput.id = `edit-name-${id}`;
        nameInput.value = name;

        const descInput = document.createElement('input');
        descInput.type = 'text';
        descInput.id = `edit-desc-${id}`;
        descInput.value = desc;

        // Append input fields to their respective table cells
        nameTd.appendChild(nameInput);
        descTd.appendChild(descInput);

        // Change the action buttons to include 'Save' and 'Cancel'
        const actionField = document.getElementById(`action-${id}`);
        actionField.innerHTML = `
            <button onclick="saveEntry(${id})">Save</button>
            <button onclick="cancelEdit(${id}, '${name}', '${desc}')">Cancel</button>
        `;
    } else {
        console.error(`Editable fields not found for ID: ${id}`);
    }
}

function saveEntry(id) {
    const nameInput = document.getElementById(`edit-name-${id}`);
    const descInput = document.getElementById(`edit-desc-${id}`);

    if (nameInput && descInput) {
        // Get the edited values
        const newName = nameInput.value.trim();
        const newDesc = descInput.value.trim();

        if (newName && newDesc) {
            // Update the entry with the new values (implement this update logic as needed)
            updateEntry(id, newName, newDesc);

            // After saving, reset to display mode
            displayEntryMode(id, newName, newDesc);
        } else {
            alert("Please enter valid values for name and description.");
        }
    } else {
        console.error(`Editable fields not found for ID: ${id}`);
    }
}

function cancelEdit(id, originalName, originalDesc) {
    displayEntryMode(id, originalName, originalDesc);
}

function displayEntryMode(id, name, desc) {
    const nameTd = document.getElementById(`name-${id}`);
    const descTd = document.getElementById(`desc-${id}`);

    if (nameTd && descTd) {
        nameTd.innerHTML = name;
        descTd.innerHTML = desc;

        const actionField = document.getElementById(`action-${id}`);
        actionField.innerHTML = `
            <button onclick="editEntry(${id}, '${name}', '${desc}')">Edit</button>
            <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteOrgLevelEntry(${id})">Delete</button>
        `;
    } else {
        console.error(`Display mode fields not found for ID: ${id}`);
    }
}
function redirectToOrgMapAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
            initializeOrgMapping();
        }
    };
    xhr.open("GET", "/CWFM/org-level-mapping/new", true);
    xhr.send();
}


function moveOptions(sourceId, targetId) {
    var source = document.getElementById(sourceId);
    var target = document.getElementById(targetId);
    var selectedOptions = Array.from(source.selectedOptions);
    
    selectedOptions.forEach(function(option) {
        source.removeChild(option);
        target.appendChild(option);
    });
}

function moveRight(orgLevelDefId) {
    moveOptions('available-' + orgLevelDefId, 'selected-' + orgLevelDefId);
}

function moveLeft(orgLevelDefId) {
    moveOptions('selected-' + orgLevelDefId, 'available-' + orgLevelDefId);
}
function saveOrgLevelMappings() {
    var formData = [];

    // Loop through all select elements with class 'selected-options'
    document.querySelectorAll('.selected-options').forEach(function(selectElement) {
        var orgLevelDefId = selectElement.getAttribute('data-org-level-id');

        // Collect selected options
        var selectedOptions = Array.from(selectElement.options)
            .filter(option => option.selected)
            .map(option => option.value);

        // Ensure we only add valid data
        if (selectedOptions.length > 0) {
            formData.push({
                orgLevelDefId: orgLevelDefId,
                selectedValues: selectedOptions
            });
        }
    });

    // Send data to the server
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/org-level-mapping/saveOrgLevelEntries", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Handle successful response
            console.log('Data saved successfully');
        } else if (xhr.readyState == 4) {
            // Handle error
            console.error('Error:', xhr.status, xhr.responseText);
        }
    };

    xhr.send(JSON.stringify(formData));
}



function fetchGmData() {
    const gmTypeId = document.getElementById('gmTypeId').value;
    if (!gmTypeId) {
        alert("Please select a valid General Type.");
        return;
    }
    document.getElementById("mainContent").innerHTML = "<p>Loading data...</p>";
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/CWFM/generalController/getGmData/" + gmTypeId, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        } else {
            alert("Failed to load organization level data. Please try again.");
            document.getElementById("mainContent").innerHTML = "<p>Error loading data. Please try again.</p>";
        }
    };
    xhr.onerror = function () {
        alert("An error occurred while making the request.");
        document.getElementById("mainContent").innerHTML = "<p>Error loading data. Please check your connection and try again.</p>";
    };
    xhr.send();
}

function deleteGMManager(gmId,gmTypeId) {
    if (confirm("Are you sure you want to delete this entry?")) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/CWFM/generalController/deleteGmData", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        
        var data = "gmId=" + encodeURIComponent(gmId) + "&gmTypeId=" + encodeURIComponent(gmTypeId);
        
        xhr.onload = function() {
            if (xhr.status == 200) {
            	document.getElementById("mainContent").innerHTML = xhr.responseText;
            } else {
                alert("Failed to delete entry. Please try again.");
            }
        };
        
        xhr.send(data);
    }
}
function saveGMMaster() {
    const gmTypeId = document.getElementById('gmTypeId').value;
    const masterName = document.getElementById('masterName').value;
    const masterValue = document.getElementById('masterValue').value;

    // Validate fields
    if (!gmTypeId) {
        alert('Please select GMType.');
        return;
    }

    if (!masterName || !masterValue) {
        alert('Please enter Name and Value.');
        return;
    }

    const data = JSON.stringify({
        gmTypeId: gmTypeId,
        gmName: masterName,
        gmDescription: masterValue,
    });

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/generalController/saveGeneralMaster", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onload = function () {
        if (xhr.status === 200) {
        	document.getElementById("mainContent").innerHTML = xhr.responseText; 
            // Redirect to the updated list page
            /* document.open();
            document.write(xhr.responseText);
            document.close(); */
        } else {
            alert("Error: " + xhr.statusText);
        }
    };
  //

    xhr.onerror = function () {
        alert("An error occurred while saving the General Master.");
    };

    xhr.send(data);
}

function addNewRowFromTemplate() {
    const placeholder = document.getElementById('placeholderRow');
    const newRow = placeholder.cloneNode(true);
    newRow.removeAttribute('id'); // Remove the ID from the cloned row
    const tableBody = document.getElementById('roleRightsTable').querySelector('tbody');
    tableBody.appendChild(newRow);
}
var rowIndex = 0; // Initial row index

function addNewRow1() {
    var table = document.getElementById('roleRightsTable').getElementsByTagName('tbody')[0];
    var newRow = table.rows[0].cloneNode(true); // Clone the first row
    var rowIndex = table.rows.length;

    // Update the index for cloned rows
    newRow.querySelectorAll('input, select').forEach(function(element) {
        element.name = element.name.replace(/\[\d+\]/, '[' + rowIndex + ']'); // Update the index
    });

    table.appendChild(newRow);
}

function saveRoleRights() {
    const form = document.getElementById('roleRightsForm'); // Now selects a <form>
    const formData = new FormData(form);

    fetch('/CWFM/roleRights/saveRoleRights', {
        method: 'POST',
        body: formData,
    })
    .then(response => {
        if (response.ok) {
            // Load the specific list page with default styles
            loadCommonList('/roleRights/roleRightsList', 'Role Rights');
        } else {
            console.error('Failed to save role rights.');
        }
    })
    .catch(error => {
        console.error('Error saving role rights:', error);
    });
}



/* function removeRow(button) {
    const row = button.closest('tr');
    row.remove();
} */



// Function to remove a row
function removeRow(button) {
    const row = button.closest('tr');
    const tableBody = document.querySelector('#roleRightsTable tbody');
    if (tableBody.rows.length > 1) {
        row.remove();
    } else {
        alert('At least one row is required.');
    }
}

// Form validation
function validateRRForm() {
    const rows = document.querySelectorAll('#roleRightsTable tbody tr');
    for (let row of rows) {
        const roleSelect = row.querySelector("select[name*='.roleId']");
        const pageSelect = row.querySelector("select[name*='.pageId']");
        if (roleSelect.value === "" || pageSelect.value === "") {
            alert("Please select both Role and Page for each row.");
            return false;
        }
    }
    return true;
}
function redirectToRRAdd() {
	alert(1);
	    // Fetch the content of add.jsp using AJAX
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/roleRights/add", true);
	    xhr.send();
	}
function redirectToUserAdd() {
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/usersController/new", true);
	    xhr.send();
	}
function saveUser() {
    const form = document.getElementById('userFormID');
    const formData = new FormData(form);

    // Prepare user object
    const user = {
        firstName: form.elements['firstName'].value,
        lastName: form.elements['lastName'].value,
        emailId: form.elements['emailId'].value,
        contactNumber: form.elements['contactNumber'].value,
        password: form.elements['password'].value,
        userAccount: form.elements['userAccount'].value,
    };

    // Prepare role IDs
    const roleIds = [];
    form.querySelectorAll('input[name="roleIds"]:checked').forEach(input => {
        roleIds.push(Number(input.value));
    });

    // Construct payload
    const payload = {
        user,
        roleIds,
    };

    console.log('Payload:', payload);

    // Make POST request
    fetch('/CWFM/usersController/saveUsers', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    })
        .then(response => {
            console.log('Fetch response received:', response);
            if (response.ok) {
                alert('User saved successfully!');
                loadCommonList('/usersController/userList', 'Users');
            } else {
                response.text().then(text => alert(`Failed to save user: ${text}`));
            }
        })
        .catch(error => console.error('Error saving user:', error));
}
function updateSidebar(sections, selectedRole) {
    if (!selectedRole) {
        console.warn("Skipping sidebar update: No role selected.");
        return;
    }

    const submenu = document.getElementById('dynamic-menu');
    submenu.innerHTML = ''; // Clear existing menu items

    console.log("Updating sidebar for role:", selectedRole);

    sections.forEach(section => {
        if (!section.sectionId) {
            console.error("Skipping section with missing ID:", section);
            return;
        }

        const menuItem = document.createElement('li');
        const sectionLink = document.createElement('a');
        sectionLink.href = '#';
        sectionLink.classList.add('nav-link');
        sectionLink.onclick = function () {
            toggleSubMenu(section.sectionId, this);
        };

        // Section icon
        const icon = document.createElement('i');
        icon.classList.add('fa', 'fa-cog', 'nav-icon');
        sectionLink.appendChild(icon);

        // Section name
        const sectionText = document.createElement('span');
        sectionText.classList.add('nav-text');
        sectionText.textContent = section.sectionName;
        sectionLink.appendChild(sectionText);

        // Arrows
        const upArrow = document.createElement('img');
        upArrow.src = 'resources/img/uarrow.png';
        upArrow.alt = 'Arrow Up';
        upArrow.classList.add('arrow-up');
        upArrow.style.display = 'none';

        const downArrow = document.createElement('img');
        downArrow.src = 'resources/img/darrow.png';
        downArrow.alt = 'Arrow Down';
        downArrow.classList.add('arrow-down');
        downArrow.style.display = 'inline-block';

        sectionLink.appendChild(upArrow);
        sectionLink.appendChild(downArrow);

        menuItem.appendChild(sectionLink);

        // Submenu (Fix for subsections)
        const subMenu = document.createElement('ul');
        subMenu.classList.add('sub-menu');
        subMenu.id = 'sub-menu-' + section.sectionId;
        subMenu.style.display = 'none';

        if (section.pages && section.pages.length > 0) {
            section.pages.forEach(page => {
               /*  if (page.role === selectedRole || selectedRole === 'SystemAdmin') { */
                    const pageItem = document.createElement('li');
                    const pageLink = document.createElement('a');
                    pageLink.href = page.url || '#';
                    pageLink.textContent = page.pageName;
                    pageLink.classList.add('nav-link');
                    pageLink.onclick = function () {
                        loadCommonList(page.pageUrl, page.pageName);
                    };

                    pageItem.appendChild(pageLink);
                    subMenu.appendChild(pageItem);
               /*  } */
            });

            if (subMenu.childNodes.length > 0) {
                menuItem.appendChild(subMenu);
            } else {
                console.warn(`No accessible pages for section: ${section.sectionName}`);
            }
        } else {
            console.warn(`Skipping section ${section.sectionName} due to missing pages.`);
        }

        submenu.appendChild(menuItem);
    });
}
// ✅ Ensure `updateSidebar` is available before `changeRole`
window.onload = function() {
    let savedRoleId = localStorage.getItem("selectedRoleId");
    let savedRoleName = localStorage.getItem("selectedRoleName");

    let roleDropdown = document.getElementById("roleSelect");

    if (roleDropdown) {
        for (let i = 0; i < roleDropdown.options.length; i++) {
            if (roleDropdown.options[i].value === savedRoleId) {
                roleDropdown.options[i].selected = true;
                break;
            }
        }
    }

    if (savedRoleId && savedRoleName) {
        console.log("Restoring previous role:", savedRoleName);
        changeRole(savedRoleId, savedRoleName);
    } else {
        console.warn("No role selected. Sidebar will remain empty.");
        document.getElementById('dynamic-menu').innerHTML = ""; // Clear sidebar
    }
};

function changeRole(selectedRoleId, selectedRoleName) {
    alert(selectedRoleName);  // To check the selected role name
    if (selectedRoleId) {
    	 localStorage.setItem("selectedRoleId", selectedRoleId);
         localStorage.setItem("selectedRoleName", selectedRoleName);
    	 if (selectedRoleName === 'System Admin') {
    	        // Show the "Admin" menu or take other actions
    	        showAdminMenu();
    	    } else {
    	        // Hide the "Admin" menu or show a different menu
    	        showOtherMenus();
    	    }
        fetch('/CWFM/updateRole', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Sending JSON data
            },
            body: JSON.stringify({ roleId: selectedRoleId, roleName: selectedRoleName }) // Sending roleId and roleName
        })
         .then(response => {
            if (response.ok) {
            	 // loadCommonList('/generalMaster/menu.jsp', 'CONTRACT WORKFORCE MANAGEMENT SYSTEM');
                return response.json(); // Parse the JSON response
            } else {
                throw new Error('Failed to update role. Please try again.');
            }
        })
        .then(data => {
        	 console.log('Sidebar update data:', data); 
            updateSidebar(data,selectedRoleName); // Update the sidebar with the fetched pages
          
        })
        .catch(error => {
            console.error("Error:", error);
        });
    }
}

function showAdminMenu() {
    // Code to show admin menu
    console.log('Displaying Admin menu...');
    document.getElementById("adminMenu").style.display = 'block';
}

function showOtherMenus() {
    // Code to show other menus based on role
    console.log('Displaying other menus...');
    document.getElementById("adminMenu").style.display = 'none';
}


function onSectionChange() {
    const sectionId = document.getElementById("sectionDropdown").value;
    if (sectionId) {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/CWFM/generalController/getSectionPages?sectionId=" + encodeURIComponent(sectionId), true);

        xhr.onload = function() {
            if (xhr.status == 200) {
                try {
                    const data = JSON.parse(xhr.responseText); // Parse the response JSON
                    console.log("Fetched pages:", data);

                    // Find the boxes in the DOM
                    const availableBox = document.getElementById("availableBox");
                    const selectedBox = document.getElementById("selectedBox");

                    // Check if the elements exist
                    if (!availableBox || !selectedBox) {
                        console.error("Available or selected box element not found in the DOM.");
                        return;
                    }

                    // Clear existing options
                    availableBox.innerHTML = "";
                    selectedBox.innerHTML = "";

                    // Populate available options
                    data.availablePages.forEach(page => {
                        const option = document.createElement("option");
                        option.value = page.gmId;
                        option.text = page.gmName;
                        availableBox.appendChild(option);
                    });

                    // Populate selected options
                    data.selectedPages.forEach(page => {
                        const option = document.createElement("option");
                        option.value = page.gmId;
                        option.text = page.gmName;
                        selectedBox.appendChild(option);
                    });

                } catch (err) {
                    console.error("Error parsing JSON response:", err);
                }
            } else {
                console.error("Failed to fetch pages. HTTP status:", xhr.status);
            }
        };

        xhr.onerror = function() {
            console.error("Error during the request.");
        };

        xhr.send();
    } else {
        console.error("Section ID is missing");
    }
}
function addPageToSelected() {
    const availableBox = document.getElementById("availableBox");
    const selectedBox = document.getElementById("selectedBox");

    if (!availableBox || !selectedBox) {
        console.error("Available or Selected box not found in the DOM.");
        return;
    }

    // Move selected options from availableBox to selectedBox
    Array.from(availableBox.selectedOptions).forEach(option => {
        selectedBox.appendChild(option); // Move the option
    });
}

function removePageFromSelected() {
    const availableBox = document.getElementById("availableBox");
    const selectedBox = document.getElementById("selectedBox");

    if (!availableBox || !selectedBox) {
        console.error("Available or Selected box not found in the DOM.");
        return;
    }

    // Move selected options from selectedBox to availableBox
    Array.from(selectedBox.selectedOptions).forEach(option => {
        availableBox.appendChild(option); // Move the option
    });
}

function saveMapping() {
    const sectionId = document.getElementById("sectionDropdown").value;
    const selectedPages = Array.from(document.getElementById("selectedBox").options)
                                .map(option => parseInt(option.value));

    if (sectionId && selectedPages.length > 0) {
        const data = {
            sectionId: parseInt(sectionId),
            pageIds: selectedPages
        };

        fetch('/CWFM/generalController/saveMapping', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            return response.json();
        })
        .then(data => {
            alert("Mapping saved successfully!");
            console.log(data);
        })
        .catch(err => {
            //alert("Error saving mapping.");
            console.error(err);
        });
    } else {
        alert("Please select a section and pages to save.");
    }
}

function saveRoleRights() {
    const errorBox = document.getElementById("error-message");
    errorBox.style.display = "none";
    errorBox.innerText = "";

    const rows = document.querySelectorAll("#roleRightsTable tbody tr");
    let selectedCombinations = new Set();
    let roleRights = [];
    let validationErrors = [];

    rows.forEach((row, index) => {
        const roleSelect = row.querySelector(".roleId");
        const pageSelect = row.querySelector(".pageId");

        if (!roleSelect || !pageSelect) {
            console.error("Row " + (index + 1) + ": Role/Page dropdown NOT found!");
            return;
        }

        const roleId = roleSelect.value ? roleSelect.value.trim() : "";
        const pageId = pageSelect.value ? pageSelect.value.trim() : "";

        if (!roleId || !pageId) {
            validationErrors.push("Row " + (index + 1) + ": Please select both Role and Page.");
            return;
        }
        const key = roleId + "-" + pageId;
        if (selectedCombinations.has(key)) {
            validationErrors.push("Row " + (index + 1) + ": Duplicate Role-Page combination detected.");
            return;
        } else {
            selectedCombinations.add(key);
        }

        let permissions = [];
        let permissions = [];
        
        document.querySelectorAll("#roleRightsTable tbody tr").forEach((row, index) => {
            let pageId = row.querySelector("input[name='roleRights[" + index + "].pageId']").value;
            let roleId = document.getElementById("roleDropdown").value; // Assuming a dropdown for role selection

            let roleRight = {
                roleId: roleId,
                pageId: pageId,
                addRights: row.querySelector("input[name='roleRights[" + index + "].addRights']").checked ? "1" : "0",
                editRights: row.querySelector("input[name='roleRights[" + index + "].editRights']").checked ? "1" : "0",
                deleteRights: row.querySelector("input[name='roleRights[" + index + "].deleteRights']").checked ? "1" : "0",
                viewRights: row.querySelector("input[name='roleRights[" + index + "].viewRights']").checked ? "1" : "0",
                importRights: row.querySelector("input[name='roleRights[" + index + "].importRights']").checked ? "1" : "0",
                exportRights: row.querySelector("input[name='roleRights[" + index + "].exportRights']").checked ? "1" : "0"
            };

            permissions.push(roleRight);
        });

        console.log("Permissions Data Sent:", permissions); // Debugging: Check data in console before sending
        if (permissions.length === 0) {
            validationErrors.push("Row " + (index + 1) + ": Please select at least one permission.");
            return;
        }
        // Wrap inside RoleRightsForm
        let roleRightsForm = {
            roleRights: permissions
        };
        /* row.querySelectorAll('input[type="checkbox"]:checked').forEach(cb => {
            permissions.push(cb.name.split('.').pop());
        }); */

        

        roleRights.push({ roleId, pageId, permissions });
    });

    // **If validation errors exist, show them in the UI**
    if (validationErrors.length > 0) {
        errorBox.innerHTML = validationErrors.join("<br>");
        errorBox.style.display = "block";
        return;
    }

    if (roleRights.length > 0) {
        fetch("/CWFM/roleRights/saveRoleRights", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ roleRights })
        })
        .then(response => response.json().then(data => ({ status: response.status, body: data })))
        .then(({ status, body }) => {
            if (status !== 200) {
                throw body;
            }
            if (body.status === "error") {
                errorBox.innerHTML = body.errors ? body.errors.join("<br>") : body.message;
                errorBox.style.display = "block";
            } else {
                alert(body.message); // Show success message
                loadCommonList('/roleRights/roleRightsList', 'Role Rights List'); 
            }
        })
        .catch(error => {
            console.error("Error:", error);
            if (error.errors && error.errors.length > 0) {
                errorBox.innerHTML = error.errors.join("<br>");
            } else if (error.message) {
                errorBox.innerHTML = error.message;
            } else {
                errorBox.innerHTML = "An unexpected error occurred. Please try again.";
            }
            errorBox.style.display = "block";
        });
    }
}



function populatePageDropdowns(availablePages, selectedPages) {
    const availableBox = document.getElementById("availablePages");
    const selectedBox = document.getElementById("selectedPages");

    // Clear existing options
    availableBox.innerHTML = "";
    selectedBox.innerHTML = "";

    // Populate available pages
    availablePages.forEach(page => {
        const option = document.createElement("option");
        option.value = page.gmId;
        option.textContent = page.gmName;
        availableBox.appendChild(option);
    });

    // Populate selected pages
    selectedPages.forEach(page => {
        const option = document.createElement("option");
        option.value = page.gmId;
        option.textContent = page.gmName;
        selectedBox.appendChild(option);
    });
}
/* function changeRole(selectedRoleId) {
    alert(selectedRoleId);
    if (selectedRoleId) {
        $.ajax({
            url: '/CWFM/updateRole',
            type: 'POST',
            contentType: 'application/json', // Send data as JSON
            data: JSON.stringify({ roleId: selectedRoleId }), // Send roleId as a JSON object
            success: function(data) {
                alert("Role updated successfully");
                console.log(data);
                // You can handle the sidebar update here with the returned data
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
                alert("Failed to update role: " + error);
            }
        });
    } else {
        console.warn("No role selected.");
    }
} */
function fetchSectionData() {
	  var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/generalController/addSection", true);
	    xhr.send();
}
</script>
<script>
function initializeOrgMapping() {
    console.log("🔥 Initializing Org Mapping...");

    let tabsContainer = document.getElementById("tabs");
    if (!tabsContainer) {
        console.error("❌ Tabs container not found!");
        return;
    }

    let tabLinks = document.querySelectorAll("#tabs ul li a");
    let tabContents = document.querySelectorAll(".tab-content");

    // Show only the first tab content by default
    tabContents.forEach(content => content.style.display = "none");
    if (tabContents.length > 0) {
        tabContents[0].style.display = "block";
    }

    // Attach click event to each tab
    tabLinks.forEach(tab => {
        tab.addEventListener("click", function (event) {
            event.preventDefault();

            // Hide all tab contents
            tabContents.forEach(content => content.style.display = "none");

            // Show only the clicked tab's content
            let selectedTabId = this.getAttribute("href").substring(1);
            let selectedTab = document.getElementById(selectedTabId);
            if (selectedTab) {
                selectedTab.style.display = "block";
            }
        });
    });

    // Move options between multi-select boxes
   /*  function moveOptions(sourceId, targetId) {
        let sourceSelect = document.getElementById(sourceId);
        let targetSelect = document.getElementById(targetId);

        if (sourceSelect && targetSelect) {
            let selectedOptions = Array.from(sourceSelect.selectedOptions);
            selectedOptions.forEach(option => {
                targetSelect.appendChild(option);
            });
        }
    } */
    function moveOptions(sourceId, targetId) {
        let sourceSelect = document.getElementById(sourceId);
        let targetSelect = document.getElementById(targetId);

        if (sourceSelect && targetSelect) {
            let selectedOptions = Array.from(sourceSelect.selectedOptions);
            selectedOptions.forEach(option => {
                targetSelect.appendChild(option);
            });
        } else {
            console.error(`❌ Could not find source or target select for IDs: ${sourceId}, ${targetId}`);
        }
    }
    
/* 
    window.moveRight = function(orgLevelDefId) {
        console.log(`Moving right for orgLevelDefId: ${orgLevelDefId}`);

        let availableSelect = document.getElementById('available-' + orgLevelDefId);
        let selectedSelect = document.getElementById('selected-' + orgLevelDefId);

        // Check if availableSelect and selectedSelect exist
        if (!availableSelect || !selectedSelect) {
            console.error(`❌ Select elements not found for orgLevelDefId: ${orgLevelDefId}`);
            return;
        }

        // Log available options
        console.log('Available options:');
        Array.from(availableSelect.options).forEach(option => {
            console.log(`Option value: "${option.value}", Option text: "${option.text}"`);
        });

        // Get selected options in available select
        let selectedOptions = Array.from(availableSelect.selectedOptions);
        console.log('Selected options:', selectedOptions);

        if (selectedOptions.length === 0) {
            console.log(`❌ No options selected for orgLevelDefId: ${orgLevelDefId}`);
            return;
        }

        // Move each selected option to the selected select dropdown
        selectedOptions.forEach(option => {
            // Check if option value and text are valid
            if (option.value && option.text) {
                console.log(`Attempting to move option: ${option.text} (${option.value})`);

                // Move the option to the selected select dropdown
                selectedSelect.appendChild(option);

                console.log(`Moved option: ${option.text} (${option.value}) to selected list.`);
            } else {
                console.warn(`❌ Invalid option: ${option.value}, ${option.text}`);
            }
        });
    };



    window.moveLeft = function (orgLevelDefId) {
        console.log(`Moving left for orgLevelDefId: ${orgLevelDefId}`);

        // Ensure orgLevelDefId is present
        if (!orgLevelDefId) {
            console.error("❌ orgLevelDefId is empty or undefined!");
            return;
        }

        // Get the available and selected select elements by their IDs
        let availableSelect = document.getElementById('available-' + orgLevelDefId);
        let selectedSelect = document.getElementById('selected-' + orgLevelDefId);

        console.log(`Available select for ${orgLevelDefId}:`, availableSelect);
        console.log(`Selected select for ${orgLevelDefId}:`, selectedSelect);

        // If elements are found, move the selected options
        if (selectedSelect && availableSelect) {
            // Get selected options in selected select
            let selectedOptions = Array.from(selectedSelect.selectedOptions);
            
            if (selectedOptions.length === 0) {
                console.log(`No options selected for orgLevelDefId: ${orgLevelDefId}`);
            }

            // Move selected options to available select
            selectedOptions.forEach(option => {
                console.log(`Attempting to move option: ${option.text} (${option.value})`);

                // Check if the option has a value and text
                if (option.value && option.text) {
                    // Add option to the available select dropdown
                    availableSelect.appendChild(option);
                    console.log(`Moved option: ${option.text} (${option.value}) to available list.`);
                } else {
                    console.warn(`❌ Invalid option: ${option.value}, ${option.text}`);
                }
            });
        } else {
            console.error("❌ Couldn't find select elements.");
        }
    };
 */

}
function moveRight(orgLevelDefId) {
    const availableSelect = document.getElementById('available-' + orgLevelDefId);
    const selectedSelect = document.getElementById('selected-' + orgLevelDefId);

    if (!availableSelect || !selectedSelect) {
        console.error(`Element not found: available-${orgLevelDefId} or selected-${orgLevelDefId}`);
        return;
    }

    const selectedOptions = Array.from(availableSelect.selectedOptions);

    if (selectedOptions.length === 0) {
        alert('Please select at least one entry.');
        return;
    }

    selectedOptions.forEach(option => {
        if (!option.value.trim()) {
            console.warn("Skipping invalid option with empty ID:", option);
            return;
        }

        console.log(`Moving option -> ID: ${option.value}, Name: ${option.text}`);

        // Prevent duplicates
        if (!Array.from(selectedSelect.options).some(opt => opt.value === option.value)) {
            selectedSelect.appendChild(option);
        }
    });

    // Log after moving
    console.log("After moveRight, selected options:", 
        Array.from(selectedSelect.options).map(opt => ({ id: opt.value, name: opt.text })));
}



// Move selected option(s) from selected to available list
// Move selected option(s) from selected to available list
function moveLeft(orgLevelDefId) {
    const availableSelect = document.getElementById('available-' + orgLevelDefId);
    const selectedSelect = document.getElementById('selected-' + orgLevelDefId);

    if (!availableSelect || !selectedSelect) {
        console.error(`Element not found: available-${orgLevelDefId} or selected-${orgLevelDefId}`);
        return;
    }

    // Get the selected options from the selected select box
    const selectedOptions = Array.from(selectedSelect.selectedOptions);

    if (selectedOptions.length === 0) {
        alert('Please select an entry to move back.');
        return;
    }

    selectedOptions.forEach(option => {
        if (!option.value.trim()) {
            console.warn("Skipping invalid option with empty ID:", option);
            return;
        }

        console.log(`Moving option back -> ID: ${option.value}, Name: ${option.text}`);

        // Prevent duplicates in the available select box
        if (!Array.from(availableSelect.options).some(opt => opt.value === option.value)) {
            availableSelect.appendChild(option);
        }
    });

    // Log after moving
    console.log("After moveLeft, available options:", 
        Array.from(availableSelect.options).map(opt => ({ id: opt.value, name: opt.text })));
}


//Function to handle the save button click and send the selected data to the server
function saveOrgLevelMapping() {
    let data = [];

    const shortName = document.getElementById('name').value;
    const longDescription = document.getElementById('description').value;

    document.querySelectorAll('.tab-content').forEach(function(tabContent) {
        const orgLevelDefId = tabContent.id.split('-')[1];

        const selectedElement = document.getElementById('selected-' + orgLevelDefId);
        if (!selectedElement) {
            console.warn(`No 'selected' list found for orgLevelDefId: ${orgLevelDefId}`);
            return; // Skip this iteration if no selected list exists
        }

        const selectedOptions = Array.from(selectedElement.options)
            .map(option => option.value)
            .filter(value => value !== "0"); // Ignore invalid values

        console.log(`Selected values for orgLevelDefId ${orgLevelDefId}:`, selectedOptions);

        if (selectedOptions.length > 0) {
            data.push({
                shortName,
                longDescription,
                selectedEntryIds: selectedOptions
            });
        }
    });

    if (data.length === 0) {
        alert("Please select at least one entry before submitting.");
        return;
    }

    console.log("Final data being sent:", JSON.stringify(data));  // Debugging log

    $.ajax({
        url: '/CWFM/org-level-mapping/saveOrgLevelEntries',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(response) {
            alert("Data saved successfully!");
            loadCommonList('/org-level-mapping/list', 'Users');
        },
        error: function(error) {
            console.error("❌ Error saving data", error);
            alert("Failed to save data.");
        }
    });
}




// Validate if the name matches the user account (e.g., check with a session variable or user profile)
function validateName(name) {
    const userAccountName = "userAccountName"; // This should be dynamically fetched (e.g., session or API call)
    return name === userAccountName;
}

// Check for duplicate name before saving (use AJAX to query the database)
function isDuplicateName(name) {
    let isDuplicate = false;
    $.ajax({
        url: '/CWFM/org-level-mapping/checkDuplicateName',
        method: 'GET',
        data: { name: name },
        async: false,  // Wait for the response before proceeding
        success: function(response) {
            if (response.isDuplicate) {
                isDuplicate = true;
            }
        }
    });
    return isDuplicate;
}
function redirectToOrgMapEdit() {
    var selectedCheckbox = document.querySelector('input[name="selectedmapId"]:checked');

    if (!selectedCheckbox) {
        alert("Please select a record to edit.");
        return;
    }

    var selectedId = selectedCheckbox.value;
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
            initializeOrgMapping(selectedId); // Initialize with selected ID
        }
    };
    xhr.open("GET", "/CWFM/org-level-mapping/edit?id=" + selectedId, true);
    xhr.send();
}

    </script>
    <style>
    
  /* Tabs */
#tabs {
    width: 100%;
    margin-top: 10px;
}

#tabs ul {
    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;
    border-bottom: 2px solid #ddd;
}

#tabs ul li {
    margin-right: 10px;
}

#tabs ul li a {
    display: block;
    padding: 10px 15px;
    background: #f5f5f5;
    text-decoration: none;
    border-radius: 5px 5px 0 0;
    border: 1px solid #ddd;
}

#tabs ul li a:hover {
    background: #ddd;
}

/* Hide all tab contents except active one */
.tab-content {
    display: none;
    padding: 20px;
    border: 1px solid #ddd;
    background: #fff;
}

/* Ensure selected tab content is visible */
#tabs .ui-tabs-panel {
    display: block;
}

.multi-select-container {
    display: flex;
    align-items: center;
    justify-content: left;
    gap: 20px; /* Adds space between elements */
}

.multi-select-group {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.multi-select-label {
    font-weight: bold;
    margin-bottom: 5px;
    text-align: center;
}

.multi-select-box {
    width: 250px;  /* Wider box */
    height: 300px; /* Taller box */
    border: 1px solid #ccc;
    padding: 5px;
    overflow-y: auto; /* Scrollbar if too many options */
}

.button-group {
    display: flex;
    flex-direction: column;
    gap: 10px;
}
.select-box {
    width: 200px;
    height: 350px;
}



.button-group button {
    margin: 5px 0;
    padding: 5px 10px;
}


     /* General Styles */
   body {
    margin: 0;
    padding: 0;
    font-family: 'Titillium Web', sans-serif;
    overflow-y: scroll; /* Adds a vertical scroll bar */
}

/* Top Navigation Bar Styles */
.top-nav {
    background-color: rgb(0, 81, 81);
    color: white;
    height: 50px;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 10px;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1001;
    box-sizing: border-box;
    font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
    font-size: 1.25rem;
    font-weight: 600;
    line-height: 1.5rem;
}

.top-nav .logo {
    height: 40px;
    width: auto;
    margin: 0 5px;
}

.top-nav .logo.dot1 {
    height: 30px;
}

.top-nav .heading {
    color: white;
    font-size: 1rem;
    font-weight: bold;
    text-align: center;
    flex-grow: 1;
}

.top-nav .dropdown span {
    color: white;
    font-size: 1rem;
    font-weight: bold;
}

.top-nav .dropdown-content {
    display: none;
    position: absolute;
    background-color: rgb(0, 81, 81);
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
    right: 0;
}

.top-nav .dropdown-content a {
    color: white;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    font-family: 'Noto Sans', sans-serif;
    font-size: .875rem;
    font-weight: 500;
    line-height: 2.5rem;
}

.top-nav .dropdown-content a:hover {
    background-color: #004d40;
}

.top-nav .dropdown:hover .dropdown-content {
    display: block;
}

/* Side Navigation Bar Styles */
.main-menu {
    background: rgb(0, 81, 81);
    position: fixed;
    top: 50px;
    bottom: 0;
    height: calc(100% - 50px);
    left: 0;
    width: 60px;
    overflow: hidden;
    transition: width 0.3s ease;
    z-index: 1000;
    color: white;
}

.main-menu.expanded {
    width: 250px;
}

.main-menu > ul {
    margin: 7px 0;
    padding: 0;
    list-style: none;
}

.main-menu li {
    position: relative;
    width: 100%;
}

.main-menu li > a {
    display: flex;
    align-items: center;
    color: white;
    font-size: 14px;
    text-decoration: none;
    padding: 10px 15px;
    transition: background-color 0.1s linear;
}

.main-menu .nav-icon {
    font-size: 20px;
    color: white;
    margin-right: 15px;
}

.main-menu .nav-text {
    flex-grow: 1;
    overflow: hidden;
    white-space: nowrap;
}

.main-menu .sub-menu {
    display: none;
    padding: 0;
    padding-left:55px;
    margin: 0;
    background: #00796b;
    list-style: none;
}

.main-menu .sub-menu li a {
    padding: 10px 30px;
    font-size: 13px;
    color: white;
    text-decoration: none;
    margin: 0;
    display: block;
    box-sizing: border-box;
}

.main-menu .sub-menu li a:hover {
    background: #004d40;
}

.main-menu li.active > a,
.main-menu li:hover > a {
    background-color: #009688;
}

.main-menu li.active .sub-menu {
    display: block;
}

/* Content Area */
.form-content {
    float: left;
    background: #ffffff;
    height: calc(100% - 50px);
    margin-top: 50px;
    margin-left: 60px;
    width: calc(100% - 60px);
    transition: margin-left 0.3s ease, width 0.3s ease;
    box-sizing: border-box;
}

.main-menu.expanded + .form-content {
    margin-left: 250px;
    width: calc(100% - 250px);
}

.main-menu:not(.expanded) + .form-content {
    margin-left: 60px;
    width: calc(100% - 60px);
}

/* Table Header Size Adjustment */
table th {
    font-size: 0.875rem;
    padding: 8px;
}

/* Navigation Pills Styles */
.navmenu .nav-pills a, .navmenu .nav-pills .expander {
    font-family: 'Noto Sans Display', 'Noto Sans', sans-serif;
    font-size: .875rem;
    font-weight: 500;
    line-height: 1.125rem;
    letter-spacing: normal;
    border-radius: 0 999px 999px 0;
    margin: 0;
    min-height: 2.75rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
}

.navmenu a, .navmenu .expander {
    padding: .5rem .5rem .5rem 1.5rem;
    min-height: 2.75rem;
    text-overflow: ellipsis;
    overflow: hidden;
    word-wrap: break-word;
    color: var(--zed_sys_color_mainMenuAction_enabled);
}

.menu-links-container div {
    width: 100%;
}

*, :after, :before {
    box-sizing: border-box;
}

.navmenu .nav-pills {
    flex: 1;
    padding-right: 1rem;
}

.nav {
    display: flex;
    flex-wrap: wrap;
    padding-left: 0;
    margin-bottom: 0;
    list-style: none;
}

.navmenu {
    position: absolute;
    left: -19.5rem;
    transform: translate(0);
    z-index: 1033;
    transition: all 0.3s ease;
}

.navmenu.show {
    left: 0;
}

/* Responsive Styles */
@media (max-width: 768px) {
    .top-nav, .main-menu {
        display: block;
    }

    .top-nav {
        position: relative;
        height: auto;
    }

    .main-menu {
        width: 100%;
        height: auto;
        position: static;
        display: block;
    }

    .main-menu.expanded {
        width: 100%;
    }

    .main-menu .nav-icon {
        font-size: 18px;
        margin-right: 10px;
    }
}

.active {
    background-color: #004d40;
    color: white;
}
.user-info {
    display: flex;
    align-items: center;
}

.user-logo {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background-color: #005151; /* Default background color for initials */
    margin-right: 10px;
}

.user-logo-img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    display: none; /* Hide by default */
}

.user-initials {
    color: #fff;
    font-weight: bold;
    font-size: 16px;
    display: none; /* Hide by default */
}

.user-name {
    font-size: 16px;
    color: #005151;
    font-family: Volte Rounded, Noto Sans, sans-serif;
}

/* Show user logo if available, hide initials */
.user-logo img[src*="user.logo"] {
    display: block;
}

.user-logo img[src=""] + .user-initials {
    display: block; /* Show initials if logo is not available */
}
.dropdown {
    position: relative;
    display: inline-block;
    cursor: pointer;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown:hover .dropdown-content {
    display: block;
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}

.dropdown-content a:hover {
    background-color: #f1f1f1;
}
.user-initials {
    width: 40px;
    height: 40px;
    background-color: #005151;
    color: white;
    text-align: center;
    line-height: 40px;
    border-radius: 50%;
    font-weight: bold;
    font-size: 20px;
}
.initials-icon {
            display: inline-block;
            width: 40px; /* Adjust size as needed */
            height: 40px; /* Adjust size as needed */
            border-radius: 50%;
            background-color: #3498db; /* Background color of the circle */
            color: white; /* Text color */
            text-align: center;
            line-height: 40px; /* Center text vertically */
            font-size: 18px; /* Font size of initials */
            font-weight: bold; /* Make initials bold */
            margin-right: 10px; /* Space between icon and text */
        }
        /* Side Navigation Bar Styles */
.main-menu {
    background: rgb(0, 81, 81);
    position: fixed;
    top: 50px;
    bottom: 0;
    height: calc(100% - 50px);
    left: 0;
    width: 60px;
    overflow-y: auto; /* Enable vertical scrolling */
    overflow-x: hidden; /* Hide horizontal scroll if not needed */
    transition: width 0.3s ease;
    z-index: 1000;
    color: white;
}

.main-menu.expanded {
    width: 250px;
}

/* Optional: Add a scrollbar style for webkit browsers */
.main-menu::-webkit-scrollbar {
    width: 8px;
}

.main-menu::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.5);
    border-radius: 4px;
}

.main-menu::-webkit-scrollbar-thumb:hover {
    background: rgba(0, 0, 0, 0.7);
}
.arrow-up, .arrow-down {
    width: 10px;
    height: 8px;
    filter: invert(100%); /* Turns black arrows white */
}
.home-icon {
    padding: 10px;
    cursor: pointer;
}

.home-icon i {
    font-size: 24px; /* Adjust size as needed */
    color: #ffffff; /* Change color as needed */
}
/* Dropdown hover effect */
.role-select:hover {
    border-color: #00796b;
}

/* Apply the same hover styles for role-right */
.role-dropdown .role-select:hover,
.role-dropdown .role-select option:hover {
    background-color: #f1f1f1; /* Light gray background */
    color: #fff; /* Black text */
    text-decoration: none;
}
.role-dropdown {
    position: relative;
    display: inline-block;
}

.role-dropdown label {
    font-weight: bold;
    margin-right: 8px;
    color: #333; /* Default label color */
}

.role-dropdown .role-select {
    padding: 5px 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
    color: #333;
    background-color: #fff;
    appearance: none; /* Remove default browser styles */
    cursor: pointer;
}

/* Hover effect for the select box */
.role-dropdown .role-select:hover {
    border-color: #007bff; /* Match the hover color of Forgot Password */
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Subtle shadow for focus */
}

/* Style for options (limited customization due to browser restrictions) */
.role-dropdown .role-select option {
    color: #333;
    background-color: #fff;
}

/* Placeholder color */
.role-dropdown .role-select option:disabled {
    color: #999;
}

/* Force a visible hover-like experience using JavaScript */
.role-dropdown:hover .role-select {
    border-color: #0056b3; /* Darker blue border */
    color: #0056b3; /* Darker blue text */
}
.role-label {
    font-size: 0.9rem; /* Decrease text size */
    color: #fff; /* Optional: Adjust label color */
    margin-bottom: 5px; /* Add some spacing */
}

.role-dropdown {
    margin-top: 5px;
}

.role-select {
    background-color: green; /* Set dropdown background color */
    color: white; /* Set dropdown text color */
    border: 1px solid #ccc; /* Optional: Border styling */
    border-radius: 4px; /* Optional: Rounded corners */
    padding: 5px; /* Optional: Padding for better appearance */
    font-size: 1rem; /* Font size for dropdown text */
}

.role-select option {
    background-color: green; /* Dropdown option background */
    color: white; /* Dropdown option text */
}

/* Alignment tweaks for different sections */
.heading,
.role-dropdown,
.dropdown {
    margin: 0 10px;
}

#dynamic-menu {
    list-style-type: none;
    padding-left: 0;
}

#dynamic-menu li {
    margin-bottom: 0px;
}

#dynamic-menu a {
    text-decoration: none;
    color: white;
}
.sidebar-menu {
    list-style: none;
    padding: 0;
}

.sidebar-menu > li {
    margin: 10px 0;
}

.nav-link {
    display: flex;
    align-items: center;
    justify-content: space-between;
    text-decoration: none;
    padding: 10px;
    cursor: pointer;
}

.sub-menu {
    list-style: none;
    padding-left: 10px;
    margin-top: 2px;
}

.sub-menu li {
    padding: 2px 0;
}

    </style>
</head>
<body>
<% 
    String initials = (String) session.getAttribute("userInitials");
    MasterUser user = (MasterUser) session.getAttribute("loginuser");
    
%>

    <!-- Top Navigation Bar -->
    <div class="top-nav">
    
        <!-- <img src="resources/img/Adani_2012_logo.png" alt="Company Logo" class="logo"> -->
        <div class="heading">Contract Labor Management System</div>
<c:choose>
    <c:when test="${not empty sessionScope.roles and sessionScope.roles.size() > 1}">
        <label for="roleSelect" class="role-label">Role:</label>
        <div class="role-dropdown">
            <select id="roleSelect" name="roleId" onchange="changeRole(this.value, this.options[this.selectedIndex].text)" class="role-select">
                <option value="" disabled selected>Select Role</option>
                <c:forEach var="role" items="${sessionScope.roles}">
                    <option value="${role.gmId}" 
                        <c:if test="${role.gmId == sessionScope.selectedRole}">selected</c:if>>
                        <c:out value="${role.gmName}" />
                    </option>
                </c:forEach>
            </select>
        </div>
    </c:when>

 <c:when test="${not empty sessionScope.roles and sessionScope.roles.size() == 1}">
        <c:set var="selectedRole" value="${sessionScope.roles[0].gmId}" scope="session"/>
        <c:set var="selectedRoleName" value="${sessionScope.roles[0].gmName}" scope="session"/>
        <div class="role-label">
            <label for="roleSelect" class="role-label">Role:</label>
             <c:out value="${selectedRoleName}" />
        </div>
        <script>
            window.onload = function() {
                changeRole('${selectedRole}', '${selectedRoleName}');
            };
        </script>
    </c:when>
</c:choose>

  <%-- <c:if test="${not empty sessionScope.roles and sessionScope.roles.size() > 1}">
    <label for="roleSelect" class="role-label">Role:</label>
    <div class="role-dropdown">
        <select id="roleSelect" name="roleId" onchange="changeRole(this.value, this.options[this.selectedIndex].text)" class="role-select">
            <option value="" disabled selected>Select Role</option>
            <c:forEach var="role" items="${sessionScope.roles}">
                <option value="${role.gmId}" 
                    <c:if test="${role.gmId == sessionScope.selectedRole}">selected</c:if>>
                    <c:out value="${role.gmName}" />
                </option>
            </c:forEach>
        </select>
    </div>
</c:if> --%>

    <div class="dropdown">
        <span class="initials-icon">
            <c:out value="${sessionScope.userInitials}" />
        </span>
        <span class="user-name">
            <c:out value="${sessionScope.loginuser.firstName}" /> 
            <c:out value="${sessionScope.loginuser.lastName}" />
        </span>

        <div class="dropdown-content">
            <a href="#" onclick="loadChangePassword()">Change Password</a>
            <a href="#" onclick="loadLogout()">Logout</a>
        </div>
    </div>

    <img src="resources/img/Dot1.png" alt="Dot1 Logo" class="logo dot1">
</div>

   
    <!-- Side Navigation Bar -->
   <nav class="main-menu">
    <ul id="dynamic-menu" class="sidebar-menu">
        <!-- Admin menu rendering -->
        <c:if test="${sessionScope.isAdmin}">
            <c:forEach var="section" items="${sessionScope.sections}">
    <li>
        <a href="#" class="nav-link" onclick="toggleSubMenu('${section.sectionId}', this)">
            <i class="fa fa-folder nav-icon"></i>
            <span class="nav-text">${section.sectionName}</span>
            <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" id="arrow-up-${section.sectionId}" style="width: 10px; height: 8px; display: none;">
            <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" id="arrow-down-${section.sectionId}" style="width: 10px; height: 8px; display: inline-block;">
        </a>
        <!-- Ensure submenu is correctly set with unique ID -->
        <ul class="sub-menu" id="sub-menu-${section.sectionId}" style="display: none;">
            <c:forEach var="page" items="${section.pages}">
                <a href="#" onclick="loadCommonList('${page.pageUrl}', '${page.pageName}')">${page.pageName}</a>
                            
            </c:forEach>
        </ul>
    </li>
</c:forEach>

        </c:if>

        <!-- Non-Admin menu rendering -->
        <c:if test="${not sessionScope.isAdmin}">
            <c:forEach var="section" items="${sessionScope.sections}">
                <li>
                    <a href="#" class="nav-link" onclick="toggleSubMenu('${section.sectionId}', this)">
                        <i class="fa fa-folder nav-icon"></i>
                        <span class="nav-text">${section.sectionName}</span>
                        <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" id="arrow-up-${section.sectionId}" style="width: 10px; height: 8px; display: none;">
            <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" id="arrow-down-${section.sectionId}" style="width: 10px; height: 8px; display: inline-block;">
        </a>
                    <ul class="sub-menu" id="sub-menu-${section.sectionId}" style="display: none;">
                        <c:forEach var="page" items="${section.pages}">
                            <c:if test="${page.accessibleForRole}">
                                <a href="#" onclick="loadCommonList('${page.pageUrl}', '${page.pageName}')">${page.pageName}</a>
                            </c:if>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </c:if>
    </ul>


        <ul id="adminMenu" style="display:none;">
         <li>
            <a href="#" class="nav-link" onclick="toggleGeneralManagementSubMenu(this)">
                <i class="fa fa-cog nav-icon"></i> <!-- Icon for General Management -->
                <span class="nav-text">General Management</span>
                <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="width: 10px; height: 8px; display: none;">
                <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="width: 10px; height: 8px; display: inline-block;">
            </a>
            <ul class="sub-menu" id="general-management-sub-menu">
              <li><a href="#" onclick="loadCommonList('/generalController/gmType', 'General Type')">General Type</a></li>
<li><a href="#" onclick="loadCommonList('/generalController/generalMaster', 'General Master')">General Master</a></li>
<li><a href="#" onclick="loadCommonList('/roleRights/roleRightsList', 'Role Rights')">Role Rights</a></li>
<li><a href="#" onclick="loadCommonList('/usersController/userList', 'Users')">Users</a></li>
<li><a href="#" onclick="loadCommonList('/generalController/addSection', 'Sections')">Sections</a></li>
            </ul>
        </li>
    </ul>
</nav>

   <%--  <ul id="dynamic-menu" class="sidebar-menu">
   <c:if test="${sessionScope.roleName eq 'Admin'}">
    <c:forEach var="section" items="${sections}">
        <div class="section">
            <h3>${section.sectionName}</h3>
            <c:forEach var="page" items="${section.pages}">
                <div class="page">
                    <a href="${page.pageUrl}">${page.pageName}</a>
                </div>
            </c:forEach>
        </div>
    </c:forEach>
</c:if>

<c:if test="${sessionScope.roleName ne 'Admin'}">
    <c:forEach var="section" items="${sections}">
        <div class="section">
            <h3>${section.sectionName}</h3>
            <c:forEach var="page" items="${section.pages}">
                <c:if test="${page.accessibleForRole}">
                    <div class="page">
                        <a href="${page.pageUrl}">${page.pageName}</a>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </c:forEach>
</c:if>



   
        </ul> --%>
    <%-- <nav class="main-menu">
     <li>
    <a href="#" class="nav-link" onclick="toggleSubMenu('dynamic-menu')">
        <i class="fa fa-database nav-icon"></i>
        <span class="nav-text">Dynamic Menu</span>
        <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="display: none;">
        <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="display: inline-block;">
    </a>
    <ul class="menu">
    <c:forEach var="page" items="${rolePages}">
        <li>
            <a href="${page.url}" onclick="loadCommonList('${page.url}', '${page.name}')">${page.name}</a>
        </li>
    </c:forEach>
</ul>
</li>
        <ul>
         <!-- New Section for General Management -->
        <li>
            <a href="#" class="nav-link" onclick="toggleSubMenu('general-management-sub-menu')">
                <i class="fa fa-cog nav-icon"></i> <!-- Icon for General Management -->
                <span class="nav-text">General Management</span>
                <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="width: 10px; height: 8px; display: none;">
                <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="width: 10px; height: 8px; display: inline-block;">
            </a>
            <ul class="sub-menu" id="general-management-sub-menu">
              <li><a href="#" onclick="loadCommonList('/generalController/gmType', 'General Type')">General Type</a></li>
<li><a href="#" onclick="loadCommonList('/generalController/generalMaster', 'General Master')">General Master</a></li>
<li><a href="#" onclick="loadCommonList('/roleRights/roleRightsList', 'Role Rights')">Role Rights</a></li>
<li><a href="#" onclick="loadCommonList('/usersController/userList', 'Users')">Users</a></li>
            </ul>
        </li>
        
        <li>
            <a href="#" class="nav-link" onclick="toggleSubMenu('admin-sub-menu')">
                <i class="fa fa-cog nav-icon"></i> <!-- Icon for General Management -->
                <span class="nav-text">Admin</span>
                <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="width: 10px; height: 8px; display: none;">
                <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="width: 10px; height: 8px; display: inline-block;">
            </a>
            <ul class="sub-menu" id="admin-sub-menu">
              <li><a href="#" onclick="loadCommonList('/org-level/list', 'Org Levels')">Org Levels</a></li>
<li><a href="#" onclick="loadCommonList('/org-level-entryController/org-level-entry', 'Org Level Entries')">Org Level Entries</a></li>
<li><a href="#" onclick="loadCommonList('/org-level-mapping/list', 'Org Level Mappings')">Org Level Mappings</a></li>
            </ul>
        </li>
       
            <li class="main-menu-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleSubMenu('clms-sub-menu'); return false;" class="mainmenu-link">
        <i class="fa fa-dashboard nav-icon"></i>
        <span class="nav-text">CLMS</span>
         <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="display: none;">
         <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="display: inline-block;">
    </a>
    <ul class="sub-menu" id="clms-sub-menu" style="display: none;">
        <li><a href="#" onclick="loadCommonList('/principalEmployer/list','Principal Employer')">Principal Employer</a></li>
        <li><a href="#" onclick="loadCommonList('/contractor/list','Contractor')">Contractor</a></li>
        <li><a href="#" onclick="loadCommonList('/workOrder/list', 'Work Order')">Work Order</a></li>
       <!--  <li><a href="#" onclick="loadCommonList('/minimumWage/list', 'Minimum Wage Master')">Minimum Wage Master</a></li> -->
        <li><a href="#" onclick="loadCommonList('/workmenDetail.jsp', 'Contract Workmen')">Contract Workmen</a></li>
        <!-- <li><a href="#" onclick="loadCommonList('/workmenWage/list', 'Workmen Wages')">Workmen Wages</a></li> -->
    </ul>
</li>
    
  <li>
                <a href="#" class="nav-link" onclick="toggleSubMenu('contractor-sub-menu')">
                    <i class="fa fa-users nav-icon"></i>
                    <span class="nav-text">Contractor</span>
                    <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="width: 10px; height: 8px; display: none;">
                    <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="width: 10px; height: 8px; display: inline-block;">
                </a>
                <ul class="sub-menu" id="contractor-sub-menu">
                    <li><a href="#" onclick="loadCommonList('/contractor/contReg','Contractor Registration')">Contractor Registration</a></li>
                    <li><a href="#" onclick="loadContractorRenewal()">Renewal</a></li>
                </ul>
            </li>
            <li>
                <a href="#" class="nav-link" onclick="toggleSubMenu('workmen-onboarding-sub-menu')">
                    <i class="fa fa-briefcase nav-icon"></i>
                    <span class="nav-text">Workmen Onboarding</span>
                    <img src="resources/img/uarrow.png" alt="Arrow Up" class="arrow-up" style="width: 10px; height: 8px; display: none;">
                    <img src="resources/img/darrow.png" alt="Arrow Down" class="arrow-down" style="width: 10px; height: 8px; display: inline-block;">
                </a>
                <ul class="sub-menu" id="workmen-onboarding-sub-menu">
                    <li><a href="#" onclick="loadCommonList('/contractworkmen/quickOBList', 'On-Bording List')">List</a></li>
                    <li><a href="#" onclick="loadQobAdd('/contractworkmen/addQuickOB', 'On-Boarding','${sessionScope.loginuser.userId}')">On-Boarding</a></li>
                    <li><a href="#" onclick="loadWorkmenRenew()">Renew</a></li>
                    <li><a href="#" onclick="loadWorkmenBlock()">Block</a></li>
                    <li><a href="#" onclick="loadWorkmenUnblock()">Unblock</a></li>
                    <li><a href="#" onclick="loadWorkmenBlacklist()">Blacklist</a></li>
                    <li><a href="#" onclick="loadWorkmenDeBlacklist()">De-blacklist</a></li>
                    <li><a href="#" onclick="loadWorkmenCancel()">Cancel</a></li>
                    <li><a href="#" onclick="loadWorkmenExpat()">Expat</a></li>
                    <li><a href="#" onclick="loadWorkmenLostDamage()">Lost or Damage</a></li>
                </ul>
            </li>
             <li>
            <a href="#" onclick="loadCommonList('/billVerification/list', 'Bill Verification')">
                <i class="fa fa-credit-card nav-icon"></i> <!-- Updated icon for Bill Verification -->
                <span class="nav-text">Bill Verification</span>
            </a>
        </li>
        <!-- <li>
            <a href="#" onclick="loadCommonList('/contractor/contReg', 'Contractor Registration')">
                <i class="fa fa-users nav-icon"></i> Updated icon for Contractor Registration
                <span class="nav-text">Contractor Registration</span>
            </a>
        </li> -->
        <li>
            <a href="#" onclick="loadCommonList('/reports/list', 'Reports')">
                <i class="fa fa-bar-chart nav-icon"></i> <!-- Updated icon for Reports -->
                <span class="nav-text">Reports</span>
            </a>
        </li>
        </ul>
    </nav> --%>
    

    <!-- Main Content Area -->
    <div id="mainContent" class="form-content"></div>
<!-- <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script> -->
    <script>
 // Function to toggle the General Management submenu
    function toggleGeneralManagementSubMenu(linkElement) {
        const submenuId = 'general-management-sub-menu'; // ID of the General Management submenu
        const subMenu = document.getElementById(submenuId);

        if (!subMenu) {
            console.error(`No submenu found for section ID: ${submenuId}`);
            return; // Exit if submenu doesn't exist
        }

        // Get the up and down arrows
        const upArrow = linkElement.querySelector('.arrow-up');
        const downArrow = linkElement.querySelector('.arrow-down');

        // Toggle submenu visibility
        if (subMenu.style.display === 'none' || subMenu.style.display === '') {
            subMenu.style.display = 'block'; // Show the submenu
            upArrow.style.display = 'inline-block'; // Show up arrow
            downArrow.style.display = 'none'; // Hide down arrow
        } else {
            subMenu.style.display = 'none'; // Hide the submenu
            upArrow.style.display = 'none'; // Hide up arrow
            downArrow.style.display = 'inline-block'; // Show down arrow
        }
    }

    function toggleSubMenu(sectionId, linkElement) {
        console.log('Toggling submenu for section ID:', sectionId);

        // Find the submenu by section ID
        const submenu = document.getElementById('sub-menu-'+sectionId);
        console.log('Toggling submenu for submenu ID:', submenu);

        if (!submenu) {
            console.error(`No submenu found for section ID: ${sectionId}`);
            return;
        }

        // Toggle submenu visibility
        const isHidden = submenu.style.display === 'none';
        submenu.style.display = isHidden ? 'block' : 'none';

        // Toggle arrow visibility
        const upArrow = linkElement.querySelector('.arrow-up');
        const downArrow = linkElement.querySelector('.arrow-down');
        if (upArrow && downArrow) {
            upArrow.style.display = isHidden ? 'inline-block' : 'none';
            downArrow.style.display = isHidden ? 'none' : 'inline-block';
        }
    }

   /*  function updateSidebar(sections, selectedRole) {
        const submenu = document.getElementById('dynamic-menu');

        // Clear existing submenu items
        submenu.innerHTML = '';

        // Loop through sections and pages
        sections.forEach(section => {
            if (!section.sectionId) {
                console.error('Missing sectionId for section:', section);
                return; // Skip sections without valid IDs
            }

            // Create the main menu item container
            const menuItem = document.createElement('li');

            // Create the link for the section
            const sectionLink = document.createElement('a');
            sectionLink.href = '#';
            sectionLink.classList.add('nav-link'); // Add your nav-link class for styling
            sectionLink.onclick = function () {
                toggleSubMenu(section.sectionId, this); // Pass sectionId directly
            };

            console.log('Section ID:', section.sectionId);

            // Add section icon (if needed)
            const icon = document.createElement('i');
            icon.classList.add('fa', 'fa-cog', 'nav-icon'); // Add appropriate icon classes
            sectionLink.appendChild(icon);

            // Add section name
            const sectionText = document.createElement('span');
            sectionText.classList.add('nav-text');
            sectionText.textContent = section.sectionName;
            sectionLink.appendChild(sectionText);

            // Add up and down arrows
            const upArrow = document.createElement('img');
            upArrow.src = 'resources/img/uarrow.png';
            upArrow.alt = 'Arrow Up';
            upArrow.classList.add('arrow-up');
            upArrow.style.width = '10px';
            upArrow.style.height = '8px';
            upArrow.style.display = 'none';

            const downArrow = document.createElement('img');
            downArrow.src = 'resources/img/darrow.png';
            downArrow.alt = 'Arrow Down';
            downArrow.classList.add('arrow-down');
            downArrow.style.width = '10px';
            downArrow.style.height = '8px';
            downArrow.style.display = 'inline-block';

            sectionLink.appendChild(upArrow);
            sectionLink.appendChild(downArrow);

            menuItem.appendChild(sectionLink);

            // Create the submenu
            const subMenu = document.createElement('ul');
            subMenu.classList.add('sub-menu'); // Add your submenu class
            subMenu.id = 'sub-menu-' + section.sectionId; // Set submenu ID
            subMenu.style.display = 'none'; // Initially hidden
            console.log('Submenu ID:', subMenu.id);

            // Loop through pages within the section and filter by role
            if (section.pages && section.pages.length > 0) {
                section.pages.forEach(page => {
                    console.log('Page Role:', page.role, 'Selected Role:', selectedRole);
                        const pageItem = document.createElement('li');
                        const pageLink = document.createElement('a');
                        pageLink.href = page.url || '#';
                        pageLink.textContent = page.pageName;
                        pageLink.classList.add('nav-link'); // Add appropriate class
                        pageLink.onclick = function () {
                            loadCommonList(page.pageUrl, page.pageName);
                        };

                        pageItem.appendChild(pageLink);
                        subMenu.appendChild(pageItem);
                });
            } else {
                console.warn(`No pages found for section: ${section.sectionName}`);
            }

            menuItem.appendChild(subMenu);
            submenu.appendChild(menuItem);
        });
    } */





   /*  function toggleSubMenu(menuId) {
        const menu = document.getElementById(menuId);
        const arrowUp = menu.previousElementSibling.querySelector('.arrow-up');
        const arrowDown = menu.previousElementSibling.querySelector('.arrow-down');
        
        if (menu) {
            if (menu.style.display === "none") {
                menu.style.display = "block";
                if (arrowUp) arrowUp.style.display = "inline-block";
                if (arrowDown) arrowDown.style.display = "none";
            } else {
                menu.style.display = "none";
                if (arrowUp) arrowUp.style.display = "none";
                if (arrowDown) arrowDown.style.display = "inline-block";
            }
        }
    } */

  /*   function toggleSubMenu(menuId) {
        const menu = document.getElementById(menuId);

        
        if (menu) {
            const arrowUp = menu.previousElementSibling.querySelector('.arrow-up');
            const arrowDown = menu.previousElementSibling.querySelector('.arrow-down');
            console.log(menu.previousElementSibling);
            // Ensure that previousElementSibling exists before trying to toggle the arrows
            if (menu.previousElementSibling) {
                if (menu.style.display === "none") {
                    menu.style.display = "block";
                    if (arrowUp) arrowUp.style.display = "inline-block";
                    if (arrowDown) arrowDown.style.display = "none";
                } else {
                    menu.style.display = "none";
                    if (arrowUp) arrowUp.style.display = "none";
                    if (arrowDown) arrowDown.style.display = "inline-block";
                }
            }
        }
    } */
   /*  function toggleSubMenu(menuId) {
        const submenu = document.getElementById(menuId);
        const arrowUpIcon = submenu.previousElementSibling.querySelector('.arrow-up');
        const arrowDownIcon = submenu.previousElementSibling.querySelector('.arrow-down');

        if (submenu.style.display === 'block') {
            submenu.style.display = 'none';
            arrowUpIcon.style.display = 'none';
            arrowDownIcon.style.display = 'inline-block';
        } else {
            submenu.style.display = 'block';
            arrowUpIcon.style.display = 'inline-block';
            arrowDownIcon.style.display = 'none';
        }
    } */

    document.addEventListener('DOMContentLoaded', () => {
        let submenuVisible = false;
        let submenuSelected = false;

        function toggleSubMenu() {
            const submenu = document.querySelector('#clms-sub-menu');
            const arrowUpIcon = document.querySelector('.arrow-up');
            const arrowDownIcon = document.querySelector('.arrow-down');

            submenuVisible = !submenuVisible;
            submenu.style.display = submenuVisible ? 'block' : 'none';
            arrowUpIcon.style.display = submenuVisible ? 'inline-block' : 'none';
            arrowDownIcon.style.display = submenuVisible ? 'none' : 'inline-block';
        }

        const clmsMenuLink = document.querySelector('.mainmenu-link');
        if (clmsMenuLink) {
            clmsMenuLink.addEventListener('click', (event) => {
                event.preventDefault(); // Prevent default link behavior
                toggleSubMenu();
            });
        }

        // Ensure submenu closes when the sidebar is hovered out
        const mainMenu = document.querySelector('.main-menu');
        mainMenu.addEventListener('mouseenter', function() {
            this.classList.add('expanded');
        });

        mainMenu.addEventListener('mouseleave', function() {
            if (!submenuSelected) {
                this.classList.remove('expanded');
            }
        });

        // Handle submenu selection
        document.querySelectorAll('#clms-sub-menu a').forEach(link => {
            link.addEventListener('click', function() {
                submenuSelected = true; // Mark submenu as selected
                highlightMenuItem(this.parentElement);
                loadContent(this.textContent);
                setTimeout(() => submenuSelected = false, 300); // Reset after a short delay
            });
        });

        function highlightMenuItem(menuItem) {
            const menuItems = document.querySelectorAll('#clms-sub-menu li');
            menuItems.forEach(item => item.classList.remove('active'));
            menuItem.classList.add('active');
        }

        function loadContent(text) {
            const headingText = text;
            updateHeading(headingText);
            document.getElementById("mainContent").innerHTML = `<h1>${text}</h1><p>Content for ${text} will go here.</p>`;
            resetSessionTimer();
        }

        function updateHeading(text) {
            const headingElement = document.querySelector('.top-nav .heading');
            headingElement.textContent = text;
        }

        function resetSessionTimer() {
            console.log('Session timer reset');
        }
    });


    function loadContent(link) {
        const headingText = link.textContent;
        updateHeading(headingText);
        const urlMatch = link.getAttribute('onclick').match(/'(.*?)'/);
        if (urlMatch) {
            const url = urlMatch[1]; // Extract URL from onclick
            console.log("Constructed URL:", url);
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("mainContent").innerHTML = this.responseText;
                    resetSessionTimer();
                }
            };
            xhttp.open("GET", url, true);
            xhttp.send();
        } else {
            console.error("Failed to extract URL from onclick attribute");
        }
    }
   

    function updateHeading(text) {
        const headingElement = document.querySelector('.top-nav .heading');
        headingElement.textContent = text;
    }

   
    function resetSidebar() {
        // Hide all sub-menus
        const subMenus = document.querySelectorAll('.sub-menu');
        subMenus.forEach(menu => {
            menu.style.display = 'none';
        });

        // Reset all menu item selections (optional, depends on how selections are handled)
        const menuItems = document.querySelectorAll('.main-menu-item');
        menuItems.forEach(item => {
            item.classList.remove('selected'); // Adjust according to your CSS class for selected items
        });

        // Show the default menu or reset to initial state
        // You might want to set a specific state here, or just leave it empty
    }
    
   
    function loadPrincipalEmployerList() {
        updateHeading('Principal Employer');
        var url = contextPath + '/principalEmployer/list';
        console.log("Constructed URL:", url);

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4) {
                if (this.status == 200) {
                    document.getElementById("mainContent").innerHTML = this.responseText;
                    resetSessionTimer();
                } else {
                    console.error("Failed to load content:", this.status, this.statusText);
                }
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }
    function loadWorkmenQOB() {
        updateHeading('Quick On-boarding');
        var url = contextPath + '/contractworkmen/quickOnbordingList';
        console.log("Constructed URL:", url);

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4) {
                if (this.status == 200) {
                    document.getElementById("mainContent").innerHTML = this.responseText;
                    resetSessionTimer();
                } else {
                    console.error("Failed to load content:", this.status, this.statusText);
                }
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }

    function loadContractorList() {
        updateHeading('Contractor');
        var url = contextPath + '/contractor/list';
        console.log("Constructed URL:", url);

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

    function loadChangePassword() {
        console.log("Change Password clicked");
        window.location.href = window.location.origin + '/CWFM/changePassword'; 
     }
    function loadLogout() {
    	console.log("Logout clicked");
        document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        
        // Optionally, clear any additional session storage or local storage
        sessionStorage.clear();
        localStorage.clear();
        
        // Redirect to the login page or a logout API endpoint
        window.location.href = 'UserLogin.jsp';
    }

    function resetSessionTimer() {
        // Placeholder for session timer reset logic
    }
    function changePassword() {
        const oldPassword = document.getElementById("oldPassword").value.trim();
        const newPassword = document.getElementById("newPassword").value.trim();
        const confirmPassword = document.getElementById("confirmPassword").value.trim();
        const messageElement = document.getElementById("message");

        // Reset error message
        messageElement.innerText = "";

        // Validate inputs
        if (!oldPassword || !newPassword || !confirmPassword) {
            messageElement.innerText = "All fields are required.";
            return;
        }

        if (newPassword !== confirmPassword) {
            messageElement.innerText = "New Password and Confirm Password do not match.";
            return;
        }

        // Prepare data for submission
        const data = JSON.stringify({
            oldPassword: oldPassword,
            newPassword: newPassword
        });

        // AJAX request to the server
        fetch("/changePassword", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: data
        })
            .then(response => {
                if (response.ok) {
                    alert("Password changed successfully!");
                    window.location.href = "UserLogin.jsp"; // Redirect on success
                } else if (response.status === 400) {
                    messageElement.innerText = "Old password is incorrect.";
                } else {
                    throw new Error("Unexpected error occurred.");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                messageElement.innerText = "Failed to change password. Please try again.";
            });
    }

    function submitGMTYPE() {
        const data = new FormData();
        const gmTypeName = document.getElementById("gmTypeName").value.trim(); // Sanitize input
        data.append("gmTypeName", gmTypeName);

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/CWFM/generalController/saveGMType", true);

        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log("Data saved successfully:", xhr.responseText);
                loadCommonList('/generalController/gmType', 'General Type');
            } else {
                console.error("Error saving data:", xhr.status, xhr.responseText);
            }
        };

        xhr.onerror = function () {
            console.error("Request failed");
        };

        xhr.send(data);
    }

    </script>
</body>

</html>
