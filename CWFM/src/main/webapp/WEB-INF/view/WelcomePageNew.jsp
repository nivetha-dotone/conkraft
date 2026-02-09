<%-- <%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<!-- Your custom styles -->
<link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css">

<!-- jQuery first -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- jQuery UI after jQuery -->
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- DataTables CSS and JS -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>


 <link rel="stylesheet" type="text/css" href="resources/css/cms/dashboard.css" />    

     <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/contractor.js"></script>
    <script src="resources/js/cms/workorder.js"></script>
       <script src="resources/js/cms/workmen.js"></script>
    <script src="resources/js/cms/report.js"></script>
     <script src="resources/js/cms/bill.js"></script>

      <script src="resources/js/cms/workflow.js"></script>

<script src="resources/js/cms/users.js"></script>
     <script src="resources/js/cms/dataimportexport.js"></script>


      <script src="resources/js/cms/export.js"></script>
       <script src="resources/js/cms/contRenewal.js"></script>
       <script src="resources/js/cms/dashboard.js"></script>
    <script>
    var contextPath = '<%= request.getContextPath() %>';
  
    const today = new Date();
    const currentYear = today.getFullYear();
    const maxDate = new Date(currentYear - 18, 11, 31); // Person must be at least 18 years old
    const minDate = new Date(currentYear - 70, 0, 1);
       $(".datetimepickerformat").datepicker({//dob
       	dateFormat: 'yy-mm-dd',
           changeMonth: true,
           changeYear: true,
           yearRange: `${currentYear - 70}:${currentYear - 18}`, // only show valid years
           minDate: minDate,
           maxDate: maxDate
       });
       $('.datetimepickerformat1').datepicker({//date of joiing
           dateFormat: 'yy-mm-dd', // Set the date format
           changeMonth: true,      // Allow changing month via dropdown
           changeYear: true,       // Allow changing year via dropdown
           yearRange: "0:+100", 
           minDate: 0              // Prevent selecting future dates
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
       
    
  
    function showSection(type) {
        document.getElementById("sequentialSection").style.display = (type === 'sequential') ? 'block' : 'none';
        document.getElementById("parallelSection").style.display = (type === 'parallel') ? 'block' : 'none';
        document.getElementById("autoSection").style.display = (type === 'auto') ? 'block' : 'none';
    }

function fileUploadTemplateSideBar(){
	$(".openSidebar").click(function (event) {
        event.preventDefault();
        $("#sidebar").css("width", "300px");
    });

    $("#closeSidebar").click(function () {
        $("#sidebar").css("width", "0");
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
/* function loadCommonList(path,heading) {
	 updateHeading(heading);
	    var url = contextPath + path;
    // Construct the URL using the contextPath variable SystemAdmin
  //  var url = contextPath + path;
    console.log("Constructed URL:", url); // Log the constructed URL to the console
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
        	 document.getElementById("mainContent").innerHTML = this.responseText;
            
            
            const successMessage = sessionStorage.getItem("successMessage");
            const errorMessage = sessionStorage.getItem("errorMessage");
            const messageDiv = document.getElementById("messageDiv");

            if (messageDiv) {
                if (successMessage) {
                    messageDiv.innerHTML = successMessage;
                    messageDiv.style.color = "green";
                    sessionStorage.removeItem("successMessage"); // Clear message after displaying
                } else if (errorMessage) {
                    messageDiv.innerHTML = errorMessage;
                    messageDiv.style.color = "red";
                    sessionStorage.removeItem("errorMessage"); // Clear message after displaying
                }
             // Clear message after displaying
                sessionStorage.removeItem("successMessage");
                sessionStorage.removeItem("errorMessage");

                // Hide message after 5 seconds
                setTimeout(() => {
                    messageDiv.style.display = "none";
                }, 5000);
            }
              resetSessionTimer();
              //setDateRange();
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
} */

function loadCommonList(path, heading) {
    updateHeading(heading);
    var url = contextPath + path;
    console.log("Constructed URL in load:", url);

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const mainContent = document.getElementById("mainContent");
            mainContent.innerHTML = this.responseText;

              
                    const tables = mainContent.querySelectorAll("table");
                    tables.forEach(table => {
                    	if (!table.classList.contains("no-dt")) {  // ✅ Skip tables with class 'no-dt'
                        $(table).DataTable({
                            paging: true,
                            searching: true,
                            ordering: true,
                            lengthChange: true,
                            info: true,
                            dom: '<"top"f>rt<"bottom"lip><"clear">'
                        });
                    	}
                    });
                
          


           // ✅ Run inline scripts
            const scripts = mainContent.querySelectorAll("script");
            scripts.forEach(script => {
                const newScript = document.createElement("script");
                if (script.src) {
                    newScript.src = script.src;
                    newScript.async = false;
                } else {
                    newScript.textContent = script.textContent;
                }
                document.body.appendChild(newScript);
                script.remove();
            }); 

            // ✅ Show messages if any
            const successMessage = sessionStorage.getItem("successMessage");
            const errorMessage = sessionStorage.getItem("errorMessage");
            const messageDiv = document.getElementById("messageDiv");

            if (messageDiv) {
                if (successMessage) {
                    messageDiv.innerHTML = successMessage;
                    messageDiv.style.color = "green";
                } else if (errorMessage) {
                    messageDiv.innerHTML = errorMessage;
                    messageDiv.style.color = "red";
                }
                sessionStorage.removeItem("successMessage");
                sessionStorage.removeItem("errorMessage");

                setTimeout(() => {
                    messageDiv.style.display = "none";
                }, 5000);
            }

            setDateRange();
            resetSessionTimer();
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}

function loadCommonListDashboard(path,heading) {
    updateHeading(heading);
    var url =  path;
    console.log("Constructed URL in load:", url);

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const mainContent = document.getElementById("mainContent");
            mainContent.innerHTML = this.responseText;

              
                    const tables = mainContent.querySelectorAll("table");
                    tables.forEach(table => {
                    	if (!table.classList.contains("no-dt")) {  // ✅ Skip tables with class 'no-dt'
                        $(table).DataTable({
                            paging: true,
                            searching: true,
                            ordering: true,
                            lengthChange: true,
                            info: true,
                            dom: '<"top"f>rt<"bottom"lip><"clear">'
                        });
                    	}
                    });
                
          


           // ✅ Run inline scripts
            const scripts = mainContent.querySelectorAll("script");
            scripts.forEach(script => {
                const newScript = document.createElement("script");
                if (script.src) {
                    newScript.src = script.src;
                    newScript.async = false;
                } else {
                    newScript.textContent = script.textContent;
                }
                document.body.appendChild(newScript);
                script.remove();
            }); 

            // ✅ Show messages if any
            const successMessage = sessionStorage.getItem("successMessage");
            const errorMessage = sessionStorage.getItem("errorMessage");
            const messageDiv = document.getElementById("messageDiv");

            if (messageDiv) {
                if (successMessage) {
                    messageDiv.innerHTML = successMessage;
                    messageDiv.style.color = "green";
                } else if (errorMessage) {
                    messageDiv.innerHTML = errorMessage;
                    messageDiv.style.color = "red";
                }
                sessionStorage.removeItem("successMessage");
                sessionStorage.removeItem("errorMessage");

                setTimeout(() => {
                    messageDiv.style.display = "none";
                }, 5000);
            }

            setDateRange();
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
    	//let basic = validateBasicData();
    	//let aadhar =  validateAadharBeforeNextTab();
        	//isValid = basic && aadhar;
        	//console.log(isValid+ " "+basic+" "+aadhar);
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
           // alert(response); // Optional: Alert the success message
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
/* function submitOrgLevel() {
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
 */

 function submitOrgLevel() {
	    const orgLevelRows = document.querySelectorAll('#orgLevelTable tbody tr'); 
	    let orgLevelsData = [];
	    let validData = true;
	    
	    let nameSet = new Set();  // Track unique Org Level Names
	    let shortNameSet = new Set();  // Track unique Short Names
	    let hierarchySet = new Set();  // Track unique Hierarchy Levels

	 // ✅ Utility function to convert to Capital Case
	    function toCapitalCase(str) {
	        return str
	            .toLowerCase()
	            .split(' ')
	            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
	            .join(' ');
	    }

	    orgLevelRows.forEach((row, index) => {
	        const orgLevelDefIdElem = row.querySelector('input[name="orgLevelDefId[]"]');
	        let orgLevelNameElem = row.querySelector('input[name="orgLevelName[]"]');
	        let shortNameElem = row.querySelector('input[name="shortName[]"]');
	        const hierarchyElem = row.querySelector('input[name="hierarchy[]"]');

	        const orgLevelDefId = orgLevelDefIdElem ? orgLevelDefIdElem.value.trim() : null;
	        let orgLevelName = orgLevelNameElem ? orgLevelNameElem.value.trim() : '';
	        let shortName = shortNameElem ? shortNameElem.value.trim() : '';
	        const hierarchy = hierarchyElem ? hierarchyElem.value.trim() : '';

	        console.log(`Processing Row ${index + 1}:`);
	        console.log('OrgLevelDefId:', orgLevelDefId || 'New Entry (null)');
	        console.log('Name:', orgLevelName || 'Not Found');
	        console.log('Short Name:', shortName || 'Not Found');
	        console.log('Hierarchy:', hierarchy || 'Not Found');

	        // Validation: Check for empty fields
	        if (!orgLevelName || !shortName || !hierarchy) {
	            console.error(`Row ${index + 1} is missing required fields.`);
	            row.style.backgroundColor = '#ffdddd'; // Highlight invalid row
	            validData = false;
	            return;
	        } else {
	            row.style.backgroundColor = ''; // Reset color if valid
	        }

	     // ✅ Capitalize fields
	        orgLevelName = toCapitalCase(orgLevelName);
	        shortName = toCapitalCase(shortName);

	        // **Duplicate Check**
	        const duplicateName = nameSet.has(orgLevelName);
	        const duplicateShort = shortNameSet.has(shortName);
	        const duplicateHierarchy = hierarchySet.has(hierarchy);

	        if (duplicateName || duplicateShort || duplicateHierarchy) {
	            console.error(`Duplicate detected in Row ${index + 1}`);
	            row.style.backgroundColor = '#ffcccb'; // Highlight duplicate row
	            alert(`Duplicate entry found in row ${index + 1}. Please correct before submitting.`);
	            validData = false;
	            return;
	        }

	        // Add to Sets to track unique values
	        nameSet.add(orgLevelName);
	        shortNameSet.add(shortName);
	        hierarchySet.add(hierarchy);

	        orgLevelsData.push({
	            orgLevelDefId: orgLevelDefId && !isNaN(orgLevelDefId) ? parseInt(orgLevelDefId) : null,
	            name: orgLevelName,
	            shortName: shortName,
	            orgHierarchyLevel: parseInt(hierarchy, 10)
	        });
	    });

	    if (!validData || orgLevelsData.length === 0) {
	        alert('Please fix errors before submitting.');
	        return;
	    }

	    console.log('Final Data Sent:', orgLevelsData);

	    // AJAX request to send data to the server
	    $.ajax({
	        type: 'POST',
	        url: '/CWFM/org-level/save',
	        contentType: 'application/json',
	        data: JSON.stringify(orgLevelsData),
	        success: function (response) {
	            console.log('Server Response:', response);
	            if (response.status === 'partial') {
	                alert('Some entries were duplicates and not saved.');
	            } else {
	                alert('Org Levels saved successfully!');
	            }
	            loadCommonList('/org-level/list', 'Org Levels');
	        },
	        error: function (xhr) {
	            console.error('Error:', xhr.responseText);
	            alert('Error: ' + xhr.responseText);
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
        	   if ($.fn.DataTable.isDataTable('#table-body')) {
        			$('#table-body').DataTable().destroy();
        		}
            document.getElementById("mainContent").innerHTML = xhr.responseText;
            initWorkmenTable("table-body");
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
    let entryName = document.getElementById('entryName').value.trim();
    let description = document.getElementById('description').value.trim();

    const errorBox = document.getElementById("formErrorMessage");

    if (!errorBox) {
        console.error("Error: formErrorMessage element is missing in the HTML.");
        return;
    }

    errorBox.style.display = "none"; // Hide previous errors
    errorBox.innerText = ''; // Clear previous messages
 // ✅ Utility function to convert to Capital Case
    function toCapitalCase(str) {
        return str
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }
    // ✅ Capitalize fields
    entryName = toCapitalCase(entryName);
    description = toCapitalCase(description);

    let errorMessages = []; // Array to store all error messages

    // Validate Org Level
    if (!orgLevelDefId) {
        errorMessages.push('Please select an Organization Level.');
    }

    // Validate Entry Name
    if (entryName === '') {
        errorMessages.push('Entry Name is required.');
    }

    // Validate Description
    if (description === '') {
        errorMessages.push('Description is required.');
    }

    // If there are errors, display all messages and stop submission
    if (errorMessages.length > 0) {
        errorBox.innerHTML = errorMessages.join('<br>'); // Show all errors
        errorBox.style.display = "block";
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
            var response = JSON.parse(xhr.responseText); 
            if (response.success) {
                updateTable(); 
            } else {
                errorBox.innerHTML = response.message;
                errorBox.style.display = "block";
            }
        } else {
            errorBox.innerHTML = "An error occurred while saving the Org Level Entry.";
            errorBox.style.display = "block";
        }
    };

    xhr.onerror = function() {
        errorBox.innerHTML = "An error occurred while saving the Org Level Entry.";
        errorBox.style.display = "block";
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
/* function saveOrgLevelMappings() {
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
 */


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
        	  /*  if ($.fn.DataTable.isDataTable('#gmTable')) {
        			$('#gmTable').DataTable().destroy();
        		} */
            document.getElementById("mainContent").innerHTML = xhr.responseText;
            //initWorkmenTable("gmTable");
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
    console.log("🚀 saveGMMaster() function called!"); // Check if function runs

    const gmTypeId = document.getElementById('gmTypeId').value;
    let masterName = document.getElementById('masterName').value.trim();
    let masterValue = document.getElementById('masterValue').value.trim();
    const errorBox = document.getElementById("formErrorMessage");

    errorBox.style.display = "none"; // Hide previous errors

    if (!gmTypeId) {
        errorBox.innerText = "Please select GM Type.";
        errorBox.style.display = "block";
        return;
    }

    if (!masterName || !masterValue) {
        errorBox.innerText = "Please enter Name and Value.";
        errorBox.style.display = "block";
        return;
    }
 // ✅ Convert to Capital Case
    function toCapitalCase(str) {
        return str
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }

    masterName = toCapitalCase(masterName);
    masterValue = masterValue;
    
    console.log("📌 Valid Input:", { gmTypeId, masterName, masterValue }); // Log data

    // **Duplicate Check in Existing Table**
    const existingNames = [];
    const existingValues = [];
    document.querySelectorAll("tbody tr").forEach((row) => {
        const nameInput = row.querySelector("input[id='name']");
        const descInput = row.querySelector("input[id='value']");
        if (nameInput && descInput) {
            existingNames.push(nameInput.value.trim());
            existingValues.push(descInput.value.trim());
        }
    });

    if (existingNames.includes(masterName)) {
        errorBox.innerText = "Duplicate Name found. Please enter a unique Name.";
        errorBox.style.display = "block";
        return;
    }

   /*  if (existingValues.includes(masterValue)) {
        errorBox.innerText = "Duplicate Value found. Please enter a unique Value.";
        errorBox.style.display = "block";
        return;
    } */

    // **Proceed with Saving Data**
    const data = "gmTypeId=" + encodeURIComponent(gmTypeId) + 
                 "&gmName=" + encodeURIComponent(masterName) + 
                 "&gmDescription=" + encodeURIComponent(masterValue);

    console.log("📤 Sending Data:", data); // Log request data

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/generalController/saveGeneralMaster", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onload = function() {
        console.log("🔄 Response Received", xhr.status, xhr.responseText); // Log response
        if (xhr.status === 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText; // Reload content
        } else {
            alert("Failed to save entry. Please try again.");
        }
    };

    xhr.onerror = function() {
        console.error("❌ Error sending request");
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
function addNewRow1() {
    var table = document.getElementById('roleRightsTable').getElementsByTagName('tbody')[0];
    var newRow = table.rows[0].cloneNode(true); // Clone the first row
    var rowIndex = table.rows.length;

    // Update the index for cloned rows
    newRow.querySelectorAll('input, select').forEach(function(element) {
        element.name = element.name.replace(/\[\d+\]/, '[' + rowIndex + ']'); // Update the index
        if (element.type === "checkbox") {
            element.checked = false; // Ensure checkboxes are cleared
        } else if (element.tagName === "SELECT") {
            element.selectedIndex = 0; // Reset dropdowns
        }
    });

    table.appendChild(newRow);
}

function saveRoleRights() {
    const errorBox = document.getElementById("error-message");
    errorBox.style.display = "none";
    errorBox.innerText = "";

    const rows = document.querySelectorAll("#roleRightsTable tbody tr");
    let selectedCombinations = new Set();
    let roleRightsList = [];
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

        // Prevent duplicate Role-Page combinations
        const key = roleId + "-" + pageId;
        if (selectedCombinations.has(key)) {
            validationErrors.push("Row " + (index + 1) + ": Duplicate Role-Page combination detected.");
            return;
        } else {
            selectedCombinations.add(key);
        }

        let addCheckbox = row.querySelector("input[name='roleRights[" + index + "].addRights']");
        console.log("Row:", index + 1, "Checkbox:", addCheckbox); // Check if the checkbox exists
        console.log("Row:", index + 1, "Checked?:", addCheckbox ? addCheckbox.checked : "Checkbox Not Found");

        let roleRight = {
                roleId: roleId,
                pageId: pageId,
                addRights: row.querySelector("input[name='roleRights[" + index + "].addRights'][type='checkbox']")?.checked ? "1" : "0",
                editRights: row.querySelector("input[name='roleRights[" + index + "].editRights'][type='checkbox']")?.checked ? "1" : "0",
                deleteRights: row.querySelector("input[name='roleRights[" + index + "].deleteRights'][type='checkbox']")?.checked ? "1" : "0",
                viewRights: row.querySelector("input[name='roleRights[" + index + "].viewRights'][type='checkbox']")?.checked ? "1" : "0",
                importRights: row.querySelector("input[name='roleRights[" + index + "].importRights'][type='checkbox']")?.checked ? "1" : "0",
                exportRights: row.querySelector("input[name='roleRights[" + index + "].exportRights'][type='checkbox']")?.checked ? "1" : "0"
            };

            console.log("Row:", index + 1, "Checkbox (Corrected):", roleRight.addRights);


        if (
            roleRight.addRights === "0" &&
            roleRight.editRights === "0" &&
            roleRight.deleteRights === "0" &&
            roleRight.viewRights === "0" &&
            roleRight.importRights === "0" &&
            roleRight.exportRights === "0"
        ) {
            validationErrors.push("Row " + (index + 1) + ": Please select at least one permission.");
            return;
        }

        roleRightsList.push(roleRight);
    });

    // **Show validation errors if any**
    if (validationErrors.length > 0) {
        errorBox.innerHTML = validationErrors.join("<br>");
        errorBox.style.display = "block";
        return;
    }

    // **Prepare JSON payload**
    let roleRightsForm = {
        roleRights: roleRightsList
    };

    console.log("Payload Sent:", JSON.stringify(roleRightsForm)); // Debugging: Check data in console before sending

    // **Send to backend**
    fetch("/CWFM/roleRights/saveRoleRights", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(roleRightsForm)
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
/* function saveUser() {
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
} */
function showError(field, message) {
    console.log(`showError called for: ${field}`);  // Debugging line

    const errorElement = document.getElementById(field + 'Error');
    if (errorElement) {
        errorElement.innerText = message;
        errorElement.style.color = "red";
    } else {
        console.error(`❌ Error: Element '${field}Error' not found in HTML!`);
    }
}
function checkUserExists(userAccount) {
    if (!userAccount.trim()) {
        showError("userAccount", "User Account is required.");
        return Promise.resolve(false);  // Return a resolved promise with 'false'
    }

    return fetch("/CWFM/usersController/checkUserExists?userAccount=" + userAccount)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server error: ${response.statusText}`);
            }
            return response.json(); // Ensure JSON response
        })
        .then(data => {
            console.log("User exists check response:", data);
            return data.exists; // Ensure it's a boolean
        })
        .catch(error => {
            console.error("Error checking user account:", error);
            return false;  // Return false on error
        });
}


function saveUser() {
    const form = document.getElementById('userFormID');
    if (!form) {
        console.error("Error: Form not found!");
        return;
    }
    // Reusable function to convert to Capital Case
    function toCapitalCase(str) {
        return str
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }
    // Fetch input values
    let firstName = form.elements['firstName'] ? form.elements['firstName'].value.trim() : "";
    let lastName = form.elements['lastName'] ? form.elements['lastName'].value.trim() : "";
    const emailId = form.elements['emailId'] ? form.elements['emailId'].value.trim() : "";
    const contactNumber = form.elements['contactNumber'] ? form.elements['contactNumber'].value.trim() : "";
    const password = form.elements['password'] ? form.elements['password'].value.trim() : "";
    let userAccount = form.elements['userAccount'] ? form.elements['userAccount'].value.trim() : "";
    // 👉 Apply Capital Case here before validation and saving
    firstName = toCapitalCase(firstName);
    lastName = toCapitalCase(lastName);
    userAccount = toCapitalCase(userAccount); // Optional: If you want account name capitalized

    let isValid = true;
    document.querySelectorAll(".error-message").forEach(e => e.innerText = "");

    if (!firstName) { showError("firstName", "First Name is required."); isValid = false; }
    if (!lastName) { showError("lastName", "Last Name is required."); isValid = false; }
    if (!emailId || !/\S+@\S+\.\S+/.test(emailId)) { showError("emailId", "Enter a valid email."); isValid = false; }
    if (!contactNumber || !/^\d{10}$/.test(contactNumber)) { showError("contactNumber", "Enter a valid 10-digit phone number."); isValid = false; }
  //  if (!password || password.length < 8) { showError("password", "Password must be at least 8 characters long."); isValid = false; }
   const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/;
    if (!passwordRegex.test(password)) {
    	showError("password", "Weak password! Must be 8+ chars, A-Z, a-z, 0-9, and special character.");
        isValid = false;
    } 
  if (!userAccount) { showError("userAccount", "User Account is required."); isValid = false; }

    // Role selection validation
    const roleErrorElement = document.getElementById('roleError');
    roleErrorElement.innerText = "";
    const roleIds = [];
    form.querySelectorAll('input[name="roleIds"]:checked').forEach(input => {
        roleIds.push(Number(input.value));
    });

    if (roleIds.length === 0) {
        roleErrorElement.innerText = "Please select at least one role.";
        return;
    }

    if (!isValid) return;

    // Check if user exists before submitting
    checkUserExists(userAccount).then(exists => {
        if (exists) {
            showError("userAccount", "User Account already exists.");
            return;
        }

        const user = { firstName, lastName, emailId, contactNumber, password, userAccount };
        const payload = { user, roleIds };

        fetch('/CWFM/usersController/saveUsers', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload),
        })
        .then(response => {
            console.log('Fetch response received:', response);
            return response.text();  // Read response as text
        })
        .then(text => {
            try {
                const data = JSON.parse(text); // Try parsing as JSON
                alert(data.message); // Show success message
            } catch (e) {
                alert(text); // If not JSON, show plain text response
            }
            loadCommonList('/usersController/userList', 'Users');
        })
        .catch(error => {
            console.error('Error saving user:', error);
            alert(`Error: ${error.message}`);
        });
    });
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
if (section.sectionIcon) {
    section.sectionIcon.split(' ').forEach(cls => icon.classList.add(cls.trim())); 
} else {
    icon.classList.add('fa', 'fa-cog'); // Default icon if not found
}
icon.classList.add('nav-icon'); // Ensures additional class is applied
        sectionLink.appendChild(icon);
       /*  const icon = document.createElement('i');
        icon.classList.add('fa', 'fa-cog', 'nav-icon');
        sectionLink.appendChild(icon); */

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
    if (selectedRoleId) {
        // Show loader
        document.getElementById("loader").style.display = "flex"; // Show

        fetch('/CWFM/updateRole', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Sending JSON data
            },
            body: JSON.stringify({ roleId: selectedRoleId, roleName: selectedRoleName }) // Sending roleId and roleName
        })
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse the JSON response
            } else {
                throw new Error('Failed to update role. Please try again.');
            }
        })
        .then(data => {
            console.log('Sidebar update data:', data);

            if (selectedRoleName === 'System Admin') {
                // maybe system admin dashboard load
            } else {
                // Fetch the role-based dashboard
                console.log("inside change role");
                return fetch('/CWFM/contractworkmen/demo', {
                    method: 'GET'
                });
            }
        })
        .then(response => response ? response.text() : null)
        .then(html => {
            if (html) {
                const mainContent = document.getElementById("mainContent");
                mainContent.innerHTML = html;

                // ✅ Initialize DataTables for all tables inside mainContent (except those with class 'no-dt')
                const tables = mainContent.querySelectorAll("table");
                tables.forEach(table => {
                    if (!table.classList.contains("no-dt")) {  
                        $(table).DataTable({
                            paging: true,
                            searching: true,
                            ordering: true,
                            lengthChange: true,
                            info: true,
                            dom: '<"top"f>rt<"bottom"lip><"clear">'
                        });
                    }
                });
            }
        })
        .catch(error => {
            console.error("Error:", error);
        })
        .finally(() => {
            // Hide loader
            document.getElementById("loader").style.display = "none";  // Hide
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
        /* .then(response => {
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            return response.json();
        }) */
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
function convertToUppercase(element) {
    element.value = element.value.toUpperCase();
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
function clearErrors() {
    document.querySelectorAll("span[id$='Error']").forEach(span => span.innerText = "");
}

function saveOrgLevelMapping() {
    clearErrors();
    
    let name = document.getElementById('name').value.trim();
    let description = document.getElementById('description').value.trim();
    
 // ✅ Utility function to convert to Capital Case
    function toCapitalCase(str) {
        return str
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }
    // ✅ Capitalize fields
    name = toCapitalCase(name);
    description = toCapitalCase(description);
    
    // ✅ Set capitalized values back to input fields (optional if you want user to see it too)
    document.getElementById('name').value = name;
    document.getElementById('description').value = description;
    if (name === "") {
        showError("name", "Name cannot be empty");
        return;
    }
    if (description === "") {
        showError("description", "Description cannot be empty");
        return;
    }

    // AJAX call to check if name exists in MASTERUSER table and if it's a duplicate
    $.ajax({
        url: '/CWFM/org-level-mapping/validate-name',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ name }),
        success: function(response) {
            if (!response.validUser) {
                showError("name", "Name must match a user account");
                return;
            }
            if (response.duplicate) {
                showError("name", "Duplicate name not allowed");
                return;
            }
            
            // Proceed with saving if no validation errors
            submitOrgLevelMapping();
        },
        error: function() {
            alert("Validation failed. Try again.");
        }
    });
}


function submitOrgLevelMapping() {
    let data = [];

    const shortName = document.getElementById('name').value;
    const longDescription = document.getElementById('description').value;

    document.querySelectorAll('.tab-content').forEach(function(tabContent) {
        const orgLevelDefId = tabContent.id.split('-')[1];

        const selectedElement = document.getElementById('selected-' + orgLevelDefId);
        if (!selectedElement) {
            console.warn(`No 'selected' list found for orgLevelDefId: ${orgLevelDefId}`);
            return;
        }

        const selectedOptions = Array.from(selectedElement.options)
            .map(option => option.value)
            .filter(value => value !== "0");

        if (selectedOptions.length > 0) {
            data.push({
                shortName,
                longDescription,
                selectedEntryIds: selectedOptions
            });
        }
    });

    if (data.length === 0) {
        showError("name", "Please select at least one entry before submitting.");
        return;
    }

    $.ajax({
        url: '/CWFM/org-level-mapping/saveOrgLevelEntries',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function() {
            alert("Data saved successfully!");
            loadCommonList('/org-level-mapping/list', 'Users');
        },
        error: function() {
            alert("Failed to save data.");
        }
    });
}


//Function to handle the save button click and send the selected data to the server
/* function saveOrgLevelMapping() {
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

 */


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
    var selectedCheckbox = document.querySelector('input[name="selectedOrgMap"]:checked');

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
 async function fetchIntData() {
    const apiUrl = document.getElementById('apiUrl').value;
    const accessToken = document.getElementById('accessToken').value;
    const csvFileName = document.getElementById('csvFileName').value;

    if (!apiUrl || !accessToken || !csvFileName) {
        alert("Please fill all fields!");
        return;
    }

    try {
        // Fetch data from API
          const response = await fetch(`/api/data/fetch-data?apiUrl=${apiUrl}&accessToken=${accessToken}`);
            const data = await response.json();
            

        // Display data
        const responseDataDiv = document.getElementById('responseData');
        responseDataDiv.innerHTML = JSON.stringify(data, null, 2);

        // Export data to CSV
        exportToCSV(data, csvFileName);
    } catch (error) {
        console.error("Error fetching data:", error);
    }
}
 
// Function to export JSON data to CSV
function exportToCSV(data, fileName) {
    const json = JSON.parse(data);
    const csv = convertToCSV(json);
    const blob = new Blob([csv], { type: 'text/csv' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName + ".csv";
    a.click();
}

// Helper function to convert JSON to CSV
function convertToCSV(json) {
    const keys = Object.keys(json[0]);
    const csv = [
        keys.join(','), // header row
        ...json.map(row => keys.map(key => row[key]).join(','))
    ].join('\n');
    return csv;
}
    </script>
    <style>
    
    

    
    /* Fix DataTable full width to match the control row */
div.dataTables_wrapper {
    display: flex;
    flex-direction: column;
    width: auto !important;
    margin: 0 auto; /* Center align if needed */
}

/* Table itself should not stretch unnecessarily */
table.dataTable {
    width: auto !important;
    margin: 0 !important;
}

/* Align filter and length selectors in one row */
.dataTables_wrapper .dataTables_length,
.dataTables_wrapper .dataTables_filter {
    display: inline-block;
    vertical-align: top;
}

/* Optional: Align both controls to justify content between them */
.dataTables_wrapper .dataTables_length,
.dataTables_wrapper .dataTables_filter {
    margin: 0 10px;
}

/* Optional: Full alignment with Search input */
.dataTables_wrapper .dataTables_filter {
    float: right;
}
    
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
.error-message {
    display: block;
    color: red;
    font-size: 12px;
    max-width: 200px; /* Set a max width */
    white-space: normal; /* Allow text to wrap */
    word-wrap: break-word;
}
.form-group {
            display: flex;
            align-items: center;
            gap: 10px; /* Space between elements */
        }

        .form-group label {
            width: 100px; /* Fixed width for labels */
            text-align: left;
        }

     /*    .form-group input {
            flex: 1; /* Inputs take available space */
            padding: 5px;
        } */
        
        .error-row {
    background-color: #ffcccc !important;
}

.error-message-row .error-message-cell {
    color: red;
    font-weight: bold;
    font-size: 13px;
    padding: 6px 10px;
    background-color: #ffe5e5;
    border-top: none;
}
        
    </style>
</head>
<body >
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

  <c:if test="${not empty sessionScope.roles and sessionScope.roles.size() > 1}">
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
</c:if>

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
    


</nav>

    <!-- Main Content Area -->
    <div id="mainContent" class="form-content">
    
     <jsp:include page="/WEB-INF/view/contractWorkmen/approverList.jsp"></jsp:include>
    </div>

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

   /*  function loadChangePassword() {
        console.log("Change Password clicked");
        window.location.href = window.location.origin + '/CWFM/changePassword'; 
     } */
    function loadLogout() {
    	console.log("Logout clicked");
        document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        
        // Optionally, clear any additional session storage or local storage
        sessionStorage.clear();
        localStorage.clear();
        
        // Redirect to the login page or a logout API endpoint
        window.location.href = 'UserLogin1.jsp';
    }

    function resetSessionTimer() {
        // Placeholder for session timer reset logic
    }
   /*  function changePassword() {
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
    } */

    function submitGMTYPE() {
        const gmTypeName = document.getElementById("gmTypeName").value.trim().toUpperCase();
        const errorBox = document.getElementById("error-gmType"); // Error message div

        // Hide previous error messages
        errorBox.style.display = "none";

        // Check if input is empty
        if (!gmTypeName) {
            errorBox.innerText = "GM Type name cannot be empty!";
            errorBox.style.display = "block";
            return;
        }

        const data = new URLSearchParams();
        data.append("gmTypeName", gmTypeName);

        fetch("/CWFM/generalController/saveGMType", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: data
        })
        .then(response => response.json().catch(() => null).then(result => ({ response, result })))
        .then(({ response, result }) => {
            if (response.status === 400 || response.status === 409) {
                // Display error message if GM Type is duplicate or invalid
                errorBox.innerText = result.message || "An error occurred.";
                errorBox.style.display = "block";
            } else if (response.ok) {
                // Save successful, no message needed, refresh the list
                loadCommonList('/generalController/gmType', 'General Type');
            } else {
                throw new Error("Unexpected server response");
            }
        })
        .catch(() => {
            errorBox.innerText = "Failed to communicate with the server.";
            errorBox.style.display = "block";
        });
    }
    function changePassword() {
    	var password = document.getElementById("newPassword").value;
        var message = document.getElementById("passwordMessage").innerHTML;
        
        if (message !== "") {
            alert("Please enter a strong password!");
            return;
        }

        var data = {
            userAccount: document.getElementById("userAccount").value,
            oldPassword: document.getElementById("oldPassword").value,
            newPassword: password
        };

        $.ajax({
            type: "POST",
            url: "/CWFM/usersController/changePassword",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(response) {
                alert("Password changed successfully!");
                document.getElementById("mainContent").innerHTML ='';
            },
            error: function(xhr) {
                alert("Error: " + xhr.responseText);
            }
        });
    }
    

    function loadChangePassword() {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // Update the mainContent element with the fetched content
                document.getElementById("mainContent").innerHTML = xhr.responseText;
            }
        };
        xhr.open("GET", "/CWFM/usersController/loadChangePwdPage", true);
        xhr.send();
    }
   
    function resetPassword() {
    	var password = document.getElementById("resetNewPassword").value;
        var message = document.getElementById("resetPasswordMessage").innerHTML;
        
        if (message !== "") {
            alert("Please enter a strong password!");
            return;
        }

        var data = {
            userAccount: document.getElementById("resetUserAccount").value,
            newPassword: password
        };

        $.ajax({
            type: "POST",
            url: "/CWFM/usersController/resetPassword",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(response) {
                alert("Password reset successfully!");
                document.getElementById("mainContent").innerHTML ='';
                updateHeading('Contract Labor Management System');
            },
            error: function(xhr) {
                alert("Error: " + xhr.responseText);
            }
        });
    }
    function validatePassword() {
        var password = document.getElementById("newPassword").value;
        var message = document.getElementById("passwordMessage");
        
        var regex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/;
        
        if (!regex.test(password)) {
            message.innerHTML = "Password must be at least 8 characters, include 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.";
        } else {
            message.innerHTML = "";
        }
    }
    function validateResetPassword() {
        var password = document.getElementById("resetNewPassword").value;
        var message = document.getElementById("resetPasswordMessage");

        var regex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/;

        if (!regex.test(password)) {
            message.innerHTML = "Password must be at least 8 characters, include 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.";
        } else {
            message.innerHTML = "";
        }
    }
    function redirectToUsersView() {
        var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
        if (selectedCheckboxes.length !== 1) {
            alert("Please select exactly one row to view.");
            return;
        }
        
        var selectedRow = selectedCheckboxes[0].closest('tr');
        var userId = selectedRow.querySelector('[name="selectedUserIds"]').value.trim();
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                document.getElementById("mainContent").innerHTML = xhr.responseText;
            }
        };
        xhr.open("GET", "/CWFM/usersController/userview/" + userId , true);
        xhr.send();
    } 
    function toggleSelectAllUsers() {
        const checkboxes = document.querySelectorAll('input[name="selectedUserIds"]');
        checkboxes.forEach(checkbox => checkbox.checked = document.getElementById('selectAllUsers').checked);
    }
    function goBackToUserList() {
    	 loadCommonList('/usersController/userList', 'Users');
    }
    
    function redirectToUsersEdit() {
        var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
        if (selectedCheckboxes.length !== 1) {
            alert("Please select exactly one row to edit.");
            return;
        }
        
        var selectedRow = selectedCheckboxes[0].closest('tr');
        var userId = selectedRow.querySelector('[name="selectedUserIds"]').value.trim();
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                document.getElementById("mainContent").innerHTML = xhr.responseText;
            }
        };
        xhr.open("GET", "/CWFM/usersController/edit/" + userId , true);
        xhr.send();
    }
    function updateUser() {
        const form = document.getElementById('userEditFormID');
        const formData = new FormData(form);

        // Prepare user object
        const user = {
            userId: form.elements['userId'].value.trim(),  // Ensure userId is included
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

        // Make PUT request
        fetch('/CWFM/usersController/updateUser', {
            method: 'POST',  // Should be PUT, but keeping POST for consistency
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        })
            .then(response => {
                if (response.ok) {
                    alert('User updated successfully!');
                    loadCommonList('/usersController/userList', 'Users');
                } else {
                    response.text().then(text => alert(`Failed to update user: ${text}`));
                }
            })
            .catch(error => console.error('Error updating user:', error));
    }
    function deleteSelectedUsers() {
        let selectedUserIds = [];
        document.querySelectorAll("input[name='selectedUserIds']:checked").forEach(checkbox => {
            selectedUserIds.push(checkbox.value);
        });

        if (selectedUserIds.length === 0) {
            alert("Please select at least one user to delete.");
            return;
        }

        if (!confirm("Are you sure you want to delete the selected users?")) {
            return;
        }

        fetch('/CWFM/usersController/deleteUsers', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userIds: selectedUserIds })
        })
        .then(response => response.text())
        .then(data => {
            alert("Users deleted successfully!");
            loadCommonList('/usersController/userList', 'Users');
        })
        .catch(error => {
            console.error("Error deleting users:", error);
            alert("An error occurred while deleting users.");
        });
    }
    function usersExportToCSV() {
        var selectedRows = document.querySelectorAll('input[name="selectedUserIds"]:checked');
        if (selectedRows.length === 0) {
            alert("Please select at least one record to export.");
            return;
        }

        var userIds = Array.from(selectedRows).map(row => row.value);

        fetch('/CWFM/usersController/getUserRoles', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userIds: userIds })
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.json();
        })
        .then(roleData => {
            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "User Account,Email,Full Name,Contact Number,Status,Roles\n";

            selectedRows.forEach(row => {
                var rowData = row.closest("tr").querySelectorAll("td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6)");
                var rowArray = [];

                rowData.forEach(cell => rowArray.push(cell.innerText.trim()));

                var userId = row.value;
                var roles = roleData[userId] ? roleData[userId].join(" | ") : "N/A";
                rowArray.push(roles);

                csvContent += rowArray.join(",") + "\n";
            });

            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "UserList.csv");
            document.body.appendChild(link);
            link.click();
        })
        .catch(error => console.error("Error fetching roles:", error));
    }

    function exportGMTYPECSV() {
        var selectedRows = document.querySelectorAll('input[name="selectedGMTIds"]:checked');
        if (selectedRows.length === 0) {
            alert("Please select at least one record to export.");
            return;
        }

        var csvContent = "data:text/csv;charset=utf-8,";
        csvContent += "GMTYPENMAE\n"; // Add headers here
        selectedRows.forEach(function(row) {
            var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2)'); // Adjust column indices as needed
            var rowArray = [];
            rowData.forEach(function(cell) {
                rowArray.push(cell.innerText);
            });
            csvContent += rowArray.join(",") + "\n";
        });
        var encodedUri = encodeURI(csvContent);
        var link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "GMTypeName.csv");
        document.body.appendChild(link);
        link.click();
    }
    function toggleSelectAllGMMaster() {
        const checkboxes = document.querySelectorAll('input[name="selectedGMMaster"]');
        checkboxes.forEach(checkbox => checkbox.checked = document.getElementById('selectAllGMMCheckbox').checked);
    }
    function exportGMMasterCSV() {
        var selectedRows = document.querySelectorAll('input[name="selectedGMMaster"]:checked');

        if (selectedRows.length === 0) {
            alert("Please select at least one record to export.");
            return;
        }

        var csvContent = "data:text/csv;charset=utf-8,";
        csvContent += "GMTYPE,MASTERNAME,MASTERVALUE\n"; // CSV Headers

        selectedRows.forEach(function(checkbox) {
            var row = checkbox.closest("tr"); // Get the parent <tr> of the checkbox

            if (row) {
                var gmType = row.querySelector("td:nth-child(2)").innerText.trim(); // Extract GM Type (text inside <td>)
                var masterName = row.querySelector('input[id="name"]').value.trim(); // Extract Master Name (from <input>)
                var masterValue = row.querySelector('input[id="value"]').value.trim(); // Extract Master Value (from <input>)

                var rowArray = [gmType, masterName, masterValue];
                console.log("Extracted Data:", rowArray); // Debugging

                csvContent += rowArray.join(",") + "\n";
            }
        });

        console.log("Final CSV Content:\n", csvContent); // Debugging

        var encodedUri = encodeURI(csvContent);
        var link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "GMMaster.csv");
        document.body.appendChild(link);
        link.click();
    }

    function exportToRoleCSV() {
        var selectedRows = document.querySelectorAll('input[name="selectedRoleRights"]:checked');
        if (selectedRows.length === 0) {
            alert("Please select at least one record to export.");
            return;
        }

        var csvContent = "data:text/csv;charset=utf-8,";
        csvContent += "Role Name,Page Name,Add Rights,Edit Rights,Delete Rights,Export Rights,View Rights,List Rights\n"; // Headers

        selectedRows.forEach(function(row) {
            var rowData = row.closest("tr").querySelectorAll("td:nth-child(2), td:nth-child(3), td:nth-child(4) input, td:nth-child(5) input, td:nth-child(6) input, td:nth-child(7) input, td:nth-child(8) input, td:nth-child(9) input");
            var rowArray = [];

            rowData.forEach(function(cell, index) {
                if (index < 2) {
                    rowArray.push(cell.innerText.trim()); // Role Name & Page Name
                } else {
                    rowArray.push(cell.checked ? "Yes" : "No"); // Checkbox values
                }
            });

            csvContent += rowArray.join(",") + "\n";
        });

        var encodedUri = encodeURI(csvContent);
        var link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "Role_Rights.csv");
        document.body.appendChild(link);
        link.click();
    }



    	// Function to get checkbox value (1 if checked, 0 if unchecked)
    	function getCheckboxState(row, index) {
    // Accessing the checkbox with a dynamic name based on the index
    let checkbox = row.querySelector(`input[name='canAdd_${index}']`);
    
    if (checkbox) {
        return checkbox.checked ? 1 : 0;
    }
    console.log(`Checkbox with name 'canAdd_${index}' not found`);
    return 0; // Default if not found
}



  /*   // Function to fetch checkbox value properly
    function getCheckboxValue(parentRow, name) {
        var checkbox = parentRow.querySelector(`td input[name="${name}"]`);
        if (!checkbox) {
            console.warn(`Checkbox '${name}' not found inside row:`, parentRow);  
            return "No";  
        }
        return checkbox.checked || checkbox.hasAttribute("checked") ? "Yes" : "No";
    } */



    function exportOrgLevelCSV() {
        var selectedRows = document.querySelectorAll('input[name="selectedOrgLevels"]:checked');

        if (selectedRows.length === 0) {
            alert("Please select at least one record to export.");
            return;
        }

        var csvContent = "data:text/csv;charset=utf-8,";
        csvContent += "ORGLEVELNAME,SHORTNAME,HIERARCHY\n"; // CSV Headers

        selectedRows.forEach(function(checkbox) {
            var row = checkbox.closest("tr"); // Get the parent <tr> of the checkbox

            if (row) {
                // Extract values from input fields inside the row
                var orgLevelName = row.querySelector('input[name="orgLevelName[]"]').value.trim();
                var shortName = row.querySelector('input[name="shortName[]"]').value.trim();
                var hierarchy = row.querySelector('input[name="hierarchy[]"]').value.trim();

                var rowArray = [orgLevelName, shortName, hierarchy];
                console.log("Extracted Data:", rowArray); // Debugging

                csvContent += rowArray.join(",") + "\n";
            }
        });

        console.log("Final CSV Content:\n", csvContent); // Debugging

        var encodedUri = encodeURI(csvContent);
        var link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "OrgLevel.csv");
        document.body.appendChild(link);
        link.click();
    }

    function exportOrgLevelEntryCSV() {
        var selectedRows = document.querySelectorAll('input[name="selectedGMMaster"]:checked');
        if (selectedRows.length === 0) {
            alert("Please select at least one record to export.");
            return;
        }

        var csvContent = "data:text/csv;charset=utf-8,";
        csvContent += "ENTRYNAME,DESCRIPTION\n"; // Add headers here
        selectedRows.forEach(function(row) {
            var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2),td:nth-child(3)'); // Adjust column indices as needed
            var rowArray = [];
            rowData.forEach(function(cell) {
                rowArray.push(cell.innerText);
            });
            csvContent += rowArray.join(",") + "\n";
        });
        var encodedUri = encodeURI(csvContent);
        var link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "OrgLevelEntry.csv");
        document.body.appendChild(link);
        link.click();
    }
    function exportOrgLevelMapCSV() {
        var selectedRows = document.querySelectorAll('input[name="selectedOrgMap"]:checked');
        if (selectedRows.length === 0) {
            alert("Please select at least one record to export.");
            return;
        }

        var csvContent = "data:text/csv;charset=utf-8,";
        csvContent += "SHORTNAME,LONGDESCRIPTION\n"; // Add headers here
        selectedRows.forEach(function(row) {
            var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2),td:nth-child(3)'); // Adjust column indices as needed
            var rowArray = [];
            rowData.forEach(function(cell) {
                rowArray.push(cell.innerText);
            });
            csvContent += rowArray.join(",") + "\n";
        });
        var encodedUri = encodeURI(csvContent);
        var link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "OrgLevelMap.csv");
        document.body.appendChild(link);
        link.click();
    }
    function toggleSelectAllOrgMap() {
        const checkboxes = document.querySelectorAll('input[name="selectedOrgMap"]');
        checkboxes.forEach(checkbox => checkbox.checked = document.getElementById('selectAllOrgMapCheckbox').checked);
    }
    
    function editOrgLevelMapping() {
        clearErrors();
        
        // Name and Description are now read-only, no need for validation
        document.getElementById('name').readOnly = true;
        document.getElementById('description').readOnly = true;

        // Proceed with updating values directly
        updateOrgLevelMapping();
    }

    function updateOrgLevelMapping() {
        let data = [];

        const shortName = document.getElementById('name').value;
        const longDescription = document.getElementById('description').value;
        const orgAcctSetId = document.querySelector('input[name="orgAcctSetId"]').value; // Get hidden orgAcctSetId

        document.querySelectorAll('.tab-content').forEach(function(tabContent) {
            const orgLevelDefId = tabContent.id.split('-')[1];

            const selectedElement = document.getElementById('selected-' + orgLevelDefId);
            if (!selectedElement) return;

            const selectedOptions = Array.from(selectedElement.options)
                .map(option => option.value)
                .filter(value => value !== "0");

            if (selectedOptions.length > 0) {
                data.push({
                    shortName, // Include shortName
                    longDescription, // Include longDescription
                    orgAcctSetId: parseInt(orgAcctSetId, 10), // Ensure number format
                    orgLevelDefId,
                    selectedEntryIds: selectedOptions
                });
            }
        });

        console.log("Sending data:", JSON.stringify(data)); // Debugging

        $.ajax({
            url: '/CWFM/org-level-mapping/updateOrgLevelEntries',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function() {
                alert("Data updated successfully!");
                loadCommonList('/org-level-mapping/list', 'Users');
            },
            error: function(xhr, status, error) {
                console.error("AJAX Request Failed:", error);
                console.error("Status:", status);
                console.error("Response Text:", xhr.responseText);
                alert("Error: " + xhr.responseText);
            }
        });
    }

    function deleteSelectedRoleRights() {
        let selectedUserIds = [];
        document.querySelectorAll("input[name='selectedRoleRights']:checked").forEach(checkbox => {
            selectedUserIds.push(checkbox.value);
        });

        if (selectedUserIds.length === 0) {
            alert("Please select at least one Role Right to delete.");
            return;
        }

        if (!confirm("Are you sure you want to delete the selected Role Rights?")) {
            return;
        }

        fetch('/CWFM/roleRights/deleteRights', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ roleIds: selectedUserIds })
        })
        .then(response => response.text())
        .then(data => {
            alert("Role Rights deleted successfully!");
            loadCommonList('/roleRights/roleRightsList', 'Role Rights List'); 
        })
        .catch(error => {
            alert("An error occurred while deleting Role Rights.");
        });
    }
    function toggleSelectRoleRights() {
        const checkboxes = document.querySelectorAll('input[name="selectedRoleRights"]');
        checkboxes.forEach(checkbox => checkbox.checked = document.getElementById('selectAllRightsCheckbox').checked);
    }
   /*  function redirectToRREdit() {
        const checkboxes = document.querySelectorAll("input[name='selectedRoleRights']:checked");
        
        if (checkboxes.length === 0) {
            alert("Please select at least one row to edit.");
            return;
        }

        checkboxes.forEach(cb => {
            const row = cb.closest("tr");
            row.querySelectorAll("input[type='checkbox']").forEach(checkbox => {
                checkbox.disabled = false; // Enable editing
            });
        });

        document.getElementById("saveChanges").style.display = "block"; // Show save button
    } */

   /*  function saveUpdatedRoleRights() {
        const selectedRows = document.querySelectorAll("input[name='selectedRoleRights']:checked");
        
        if (selectedRows.length === 0) {
            alert("No rows selected for updating.");
            return;
        }

        let updatedRoleRights = [];

        selectedRows.forEach(cb => {
            const row = cb.closest("tr");

            const roleName = row.querySelector("#rolename").innerText;
            const pageName = row.querySelector("#pagename").innerText;
            
            updatedRoleRights.push({
                roleName,
                pageName,
                addRights: row.querySelector("td:nth-child(4) input").checked ? 0 : 1,
                editRights: row.querySelector("td:nth-child(5) input").checked ? 0 : 1,
                deleteRights: row.querySelector("td:nth-child(6) input").checked ? 0 : 1,
                exportRights: row.querySelector("td:nth-child(7) input").checked ? 0 : 1,
                viewRights: row.querySelector("td:nth-child(8) input").checked ? 0 : 1,
                listRights: row.querySelector("td:nth-child(9) input").checked ? 0 : 1
            });
        });

        fetch("/CWFM/roleRights/update", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedRoleRights)
        })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            location.reload(); // Refresh page after update
        })
        .catch(error => {
            console.error("Update Error:", error);
            alert("Failed to update role rights.");
        });
    } */
    function redirectToRREdit() {
        let selectedRows = document.querySelectorAll("input[name='selectedRoleRights']:checked");

        if (selectedRows.length === 0) {
            alert("Please select at least one row to edit.");
            return;
        }

        selectedRows.forEach(selectedCheckbox => {
            let row = selectedCheckbox.closest("tr"); // Get the selected row
            row.classList.add("editing");

            let checkboxes = row.querySelectorAll(".rights-checkbox");
            checkboxes.forEach(checkbox => {
                checkbox.disabled = false; // Enable checkboxes
            });
        });

        // Show Save button, Hide Edit button
        document.getElementById("editButton").style.display = "none";
        document.getElementById("saveButton").style.display = "inline";
    }

    function toggleRowSelection(roleCheckbox) {
        let row = roleCheckbox.closest("tr");
        row.classList.toggle("selected", roleCheckbox.checked);
    }

    function saveUpdatedRoleRights() {
        let editedRows = document.querySelectorAll("tr.editing");

        if (editedRows.length === 0) {
            alert("Please select a row to edit before saving.");
            return;
        }

        let roleRightsUpdates = [];

        editedRows.forEach(row => {
            let roleRightId = row.getAttribute("data-roleRightId");
            let roleName = row.cells[1].textContent.trim();
            let pageName = row.cells[2].textContent.trim();

            // Get checkbox values (1 if checked, 0 if unchecked)
            let canAdd = row.querySelector("input[name='canAdd']").checked ? 1 : 0;
            let canEdit = row.querySelector("input[name='canEdit']").checked ? 1 : 0;
            let canDelete = row.querySelector("input[name='canDelete']").checked ? 1 : 0;
            let canExport = row.querySelector("input[name='canExport']").checked ? 1 : 0;
            let canView = row.querySelector("input[name='canView']").checked ? 1 : 0;
            let canList = row.querySelector("input[name='canList']").checked ? 1 : 0;

           
            roleRightsUpdates.push({
                roleRightId: roleRightId,
                roleName: roleName,
                pageName: pageName,
                addRights: canAdd,   // Change `canAdd` → `addRights`
                editRights: canEdit, // Change `canEdit` → `editRights`
                deleteRights: canDelete,
                exportRights: canExport,
                viewRights: canView,
                listRights: canList
            });
            // Disable checkboxes after saving
            row.querySelectorAll(".rights-checkbox").forEach(checkbox => {
                checkbox.disabled = true;
            });

            row.classList.remove("editing");
        });

        console.log("Final Payload:", JSON.stringify({ roleRights: roleRightsUpdates }));
        console.log("Final Payload:", JSON.stringify({ roleRights: roleRightsUpdates }, null, 2));

        fetch("/CWFM/roleRights/update", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ roleRights: roleRightsUpdates })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            alert("Role rights updated successfully!");
            loadCommonList('/roleRights/roleRightsList', 'Role Rights List'); 
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Failed to update role rights.");
        });

        // Show Edit button, Hide Save button
        document.getElementById("editButton").style.display = "inline";
        document.getElementById("saveButton").style.display = "none";
    }
    function deleteOrgLevel() {
        const selectedIds = Array.from(document.querySelectorAll('input[name="selectedOrgLevels"]:checked')).map(cb => parseInt(cb.value, 10));
        if (selectedIds.length === 0) {
            alert("Please select at least one Org Level to delete.");
            return;
        }
        // Log the selected IDs to see what is being sent
        console.log("Selected Org Level IDs:", selectedIds);

        $.ajax({
            type: 'POST',
            url: '/CWFM/org-level/deleteOrgLevel',
            contentType: 'application/json', // Set content type to JSON
            data: JSON.stringify(selectedIds), // Send data as JSON string
            success: function(response) {
                if (response.success) {
                    alert("Selected items deleted successfully.");
                    loadCommonList('/org-level/list', 'Org Levels');
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
    
   /*  function fetchTemplateOptions() {
        var dropdown = document.getElementById("templateType");
        var selectedTemplate = dropdown.value;

        if (selectedTemplate) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "/CWFM/data/getTemplateOptions?selectedTemplate=" + selectedTemplate, true);
            xhr.setRequestHeader("Content-Type", "application/json");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var response = JSON.parse(xhr.responseText);

                    document.getElementById("templateOptions").style.display = "block";
                    document.getElementById("templateMessage").innerText = "What would you like to do with this template?";
                    document.getElementById("infoTemplate").href = response.infoUrl;
                    document.getElementById("viewTemplate").href = response.viewUrl;
                    document.getElementById("downloadTemplate").href = response.downloadUrl;
                }
            };

            xhr.send();
        } else {
            document.getElementById("templateOptions").style.display = "none";
        }
    } */
    function fetchTemplateOptions() {
        var selectedTemplate = $("#templateType").val();

        // Hide the message initially
        $("#templateOptions").hide();

        if (selectedTemplate) {
            $.ajax({
                url: "/CWFM/data/getTemplateOptions",
                type: "GET",
                data: { selectedTemplate: selectedTemplate },
                
                success: function(response) {
                	console.log(1);
                    if (response) {
                        $("#templateMessage").text(response);  // Set response text
                        $("#templateOptions").fadeIn();  // Show message smoothly
                    }
                },
                error: function() {
                    console.error("Error fetching template options");
                }
            });
        }
    }
    function fetchTemplateInfo() {
        var selectedTemplate = document.getElementById("templateType").value;

        if (!selectedTemplate) {
            alert("Please select a template first.");
            return;
        }

        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/CWFM/data/getTemplateInfo?templateType=" + encodeURIComponent(selectedTemplate), true);
        xhr.setRequestHeader("Content-Type", "application/json");
       console.log(2);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                console.log(5);
                document.getElementById("modalTitle").innerText = response.title;
                document.getElementById("modalDescription").innerText = response.description;

                var tableHtml = "<table border='1' style='width:100%;'>";
                tableHtml += "<tr><th>Field Name</th><th>Field Type</th><th>Example</th></tr>";

                response.fields.forEach(function (field) {
                    tableHtml += "<tr><td>" + field.name + "</td><td>" + field.type + "</td><td>" + field.example + "</td></tr>";
                });

                tableHtml += "</table>";
                document.getElementById("modalTable").innerHTML = tableHtml;

                document.getElementById("templateModal").style.display = "block"; // Show modal
            } else if (xhr.readyState === 4) {
                alert("Error fetching template details.");
            }
        };

        xhr.send();
    }

    function closeModal() {
        document.getElementById("templateModal").style.display = "none"; // Hide modal
    }
    function viewTemplateInfo() {
        var selectedTemplate = document.getElementById("templateType").value;

        if (!selectedTemplate) {
            alert("Please select at least one template.");
            return; // Stop execution if no template is selected
        }

        // Hide sidebar
        document.getElementById("sidebar").style.display = "none";  

        // Hide import/export buttons & arrows
        document.querySelector(".button-container").style.display = "none";
        document.querySelector(".arrow-container").style.display = "none";

        // Show the table container
        var tableContainer = document.getElementById("viewTemplateContainer");
        tableContainer.style.display = "block";  

        // Get table header and body elements
        var tableHeaderRow = document.getElementById("tableHeaderRow");
        var tableBody = document.getElementById("tableBody");

        // Clear previous headers and rows
        tableHeaderRow.innerHTML = "";
        tableBody.innerHTML = "";

        // Define headers based on the selected template
        var headers = [];
        if (selectedTemplate === "generalMaster") {
            headers = ["GM Name", "GM Description", "GM TypeID","IS Active","Created Time","Updated Time","Updated By"];
        }
        else if (selectedTemplate === "minimumWage") {
            headers = ["Trade", "Skill", "Basic","Da","Allowance","From Date","Unit Code","Organization"];
        }
        else if (selectedTemplate === "workorder") {
            headers = ["Work Order Number","Item","Line","Line Number","Service Code","Short Text","Delivery Completion","Item Changed ON","Vendor Code","Vendor Name",
            	"Vendor Address","Blocked Vendor","Work Order Validitiy From","Work Order Validitiy To","Work Order Type","Plant code","Section Code","Department Code",
            	"G/L Code","Cost Center","Nature of Job","Rate / Unit","Quantity","Base Unit of Measure","Work Order Released","PM Order No","WBS Element","Qty Completed",
            	"Work Order Release Date","Service Entry Created Date","Service Entry Updated Date","Purchase Org Level","Company_code"];
        }
        else if (selectedTemplate === "contractor") {
            headers = ["Contractor Name", "Contractor Address","City","Plant Code","Contractor Manager Name","License Num","License Valid From","License Valid To",
            	"License Coverage","Total Strength","Maximum Number of Workmen","Nature of Work","Location of Work","Contractor Validity Start Date","Contractor Validity End Date",
            	"Contractor Id","PF Code","EC/WC Number","EC/WC Validity Start Date","EC/WC Validity End Date","Coverage","PF Number","PF Apply Date","Reference","Mobile Number",
            	"ESI Number","ESI Valid From","ESI Valid To","Organisation","Main Contractor Code","Work Order Number"];
        }
        else if (selectedTemplate === "principalEmployer") {
            headers = ["Organization","Plant Code","Name", "Address","Manager Name","Manager Address","Bussiness Type","Max Workmen","Max Contract Workmen","BOCW Applicability",
            	"Is MW Applicability","License Number","PF Code","ESWC Number","Factory License Number",,"State"];
        }
        else if (selectedTemplate === "workmenbulkupload") {
            headers = ["First Name","Last Name","Father's Name or Husband's Name","Date of Birth","Trade","Skil","Nature of Work","Hazardous Area","Aadhar/Id proof number",
            	"Vendor Code","Gender","Date of Joining","Department","Area","Work Order Number","PF A/C Number","Marital Status" ,"Technical/Non Technical","Academic",
            	"Blood Group","Accommodation","Bank Name","Account Number","Mobile Number","Emergency Contact Number","Police verification Date Valid To","Health chekup Date",
            	"Access Levels","ESIC Number","UNIT CODE","Organization name" ,"EIC Number","EC number","UAN Number","Emergency Contact Person",
            	"Is eligible for PF","SpecializationName","Insurance type","LL number","Address","Zone","IdMark"];
        }
        else if (selectedTemplate === "workmenbulkuploaddraft") {
            headers = ["First Name","Last Name","Father's Name or Husband's Name","Date of Birth","Trade","Skil","Nature of Work","Hazardous Area","Aadhar/Id proof number",
            	"Vendor Code","Gender","Date of Joining","Department","Area","Work Order Number","PF A/C Number","Marital Status" ,"Technical/Non Technical","Academic",
            	"Blood Group","Accommodation","Bank Name","Account Number","Mobile Number","Emergency Contact Number","Police verification Date Valid To","Health chekup Date",
            	"Access Levels","ESIC Number","UNIT CODE","Organization name" ,"EIC Number","EC number","UAN Number","Emergency Contact Person",
            	"Is eligible for PF","SpecializationName","Insurance type","LL number","Address","Zone","IdMark"];
        }
        // Populate table headers
        headers.forEach(function(header) {
            var th = document.createElement("th");
            th.style.border = "1px solid #ddd";
            th.style.padding = "8px";
            th.textContent = header;
            tableHeaderRow.appendChild(th);
        });

        // Add empty rows (5 rows as an example)
        for (var i = 0; i < 5; i++) {  
            var tr = document.createElement("tr");
            headers.forEach(function() {
                var td = document.createElement("td");
                td.style.border = "1px solid #ddd";
                td.style.padding = "8px";
                td.textContent = ""; // Ensuring empty rows
                tr.appendChild(td);
            });
            tableBody.appendChild(tr); // Append row to tbody
        }
    }
    function openFileSidebar() {
        document.getElementById("fileUploadSidebar").style.width = "300px"; // Open sidebar
    }

    function closeFileSidebar() {
        document.getElementById("fileUploadSidebar").style.width = "0"; // Close sidebar
    }
   
    /* function cancelButton() {
        // Hide all other sections (viewTemplate, sidebar, etc.)
        document.getElementById("viewTemplateContainer").style.display = "none";
        document.getElementById("fileUploadSidebar").style.width = "0";

        // Show the initial section
        document.querySelector(".button-container").style.display = "block";
    } */

   



   /*  function submitFile() {
        var fileInput = document.getElementById("fileInput");
        
        if (fileInput.files.length === 0) {
            alert("Please choose a file before uploading.");
        } else {
            alert("File uploaded successfully!");
            closeFileSidebar(); // Close sidebar after upload
        }
    } */

    
    function uploadTemplateFile() {
        const fileInput = document.getElementById("fileInput");
        const templateType = document.getElementById("templateType").value;

        if (!fileInput.files[0] || !templateType) {
            alert("Please select a template and a file.");
            return;
        }

        const formData = new FormData();
        formData.append("file", fileInput.files[0]);
        formData.append("templateType", templateType);

        fetch("/CWFM/data/uploadTemplate", {
            method: "POST",
            body: formData
        })
        .then(response => response.json())
        .then(result => {
            console.log("Server Response:", result);

            const messageDiv = document.getElementById("uploadMessage");
            messageDiv.innerHTML = "";
            messageDiv.style.display = "block";

            let messageColor = "black";
            if (result.status === "success") {
                messageColor = "green";
            } else if (result.status === "partial") {
                messageColor = "orange";
            } else {
                messageColor = "red";
            }

            messageDiv.innerText = result.message || "Unknown result.";
            messageDiv.style.color = messageColor;

            // Automatically hide message after 5 seconds
            setTimeout(() => {
                messageDiv.style.display = "none";
            }, 5000);

            // Clear previous error display
            document.getElementById("errorContainer").innerHTML = "";

            const successData = result.data?.successData ?? [];
            const errorData = result.data?.errorData ?? [];
            let anyDataProcessed = false;

            if (successData.length > 0) {
                renderUploadedData(successData, templateType);
                anyDataProcessed = true;
            }

            if (errorData.length > 0) {
                renderErrors(errorData);
                anyDataProcessed = true;
            }

            closeFileSidebar();
            fileInput.value = "";
        })
        .catch(err => {
            console.error("Upload error", err);
            const messageDiv = document.getElementById("uploadMessage");
            messageDiv.innerText = "Something went wrong during file upload.";
            messageDiv.style.color = "red";
            messageDiv.style.display = "block";

            // Automatically hide error after 5 seconds
            setTimeout(() => {
                messageDiv.style.display = "none";
            }, 5000);
        });
    }





  


    function renderUploadedData(data, templateType) {
        const tableBody = document.getElementById("tableBody");
        const tableHeaderRow = document.getElementById("tableHeaderRow");
        tableBody.innerHTML = "";
        tableHeaderRow.innerHTML = "";

        if (!data || data.length === 0) {
            const tr = document.createElement("tr");
            const td = document.createElement("td");
            td.colSpan = 10;
            td.textContent = "No data found.";
            tr.appendChild(td);
            tableBody.appendChild(tr);
            return;
        }

        let headers = [];
        let fieldMap = [];

        if (templateType === "generalMaster") {
            headers = ["GM Name", "GM Description", "GM TypeID", "IS Active", "Created Time", "Updated Time", "Updated By"];
            fieldMap = ["gmName", "gmDescription", "gmTypeId", "isActive", "createdTM", "updatedTM", "updatedBy"];
        } else if (templateType === "minimumWage") {
            headers = ["Trade", "Skill", "Basic", "Da", "Allowance", "From Date", "Unit Code", "Organization"];
            fieldMap = ["trade", "skill", "basic", "da", "allowamce", "fromDate", "unitCode", "organization"];
        } else if (templateType === "workorder") {
            headers = ["Work Order Number","Item","Line","Line Number","Service Code","Short Text","Delivery Completion","Item Changed ON","Vendor Code","Vendor Name","Vendor Address","Blocked Vendor","Work Order Validitiy From","Work Order Validitiy To","Work Order Type","Plant code","Section Code","Department Code","G/L Code","Cost Center","Nature of Job","Rate / Unit","Quantity","Base Unit of Measure","Work Order Released","PM Order No","WBS Element","Qty Completed","Work Order Release Date","Service Entry Created Date","Service Entry Updated Date","Purchase Org Level","Company_code"];
            fieldMap = ["workOrderNumber", "item", "line", "lineNumber", "serviceCode", "shortText", "deliveryCompletion","itemChangedON", "vendorCode", "vendorName", "vendorAddress", "blockedVendor","workOrderValiditiyFrom", "workOrderValiditiyTo", "workOrderType", "plantcode", "sectionCode","departmentCode", "GLCode", "costCenter", "natureofJob", "rateUnit", "quantity", "baseUnitofMeasure","workOrderReleased", "PMOrderNo", "WBSElement", "qtyCompleted", "workOrderReleaseDate","serviceEntryCreatedDate", "serviceEntryUpdatedDate", "purchaseOrgLevel",  "companycode"];
        } else if (templateType === "contractor") {
            headers = ["CONTRACTOR NAME", "CONTRACTOR ADDRESS", "City", "Contractor MANAGER NAME", "LICENSE NUM", "LICENCSE VALID FROM", "LICENCSE VALID TO", "LICENCSE COVERAGE", "TOTAL STRENGTH", "MAXIMUM NUMBER OF WORKMEN", "NATURE OF WORK", "LOCATION OF WORK", "CONTRACTOR VALIDITY START DATE", "CONTRACTOR VALIDITY END DATE", "CONTRACTOR ID", "PF CODE", "EC/WC number", "EC/WC Validity Start Date", "EC/WC Validity End Date", "Coverage", "PF NUMBER", "PF APPLY DATE", "Reference", "Mobile Number", "ESI NUMBER", "ESI VALID FROM", "ESI VALID TO", "Main Contractor Code", "Work Order Number"];
            fieldMap = ["contractorName", "contractorAddress", "city", "managerNm", "licenseNumber", "licenseValidFrom", "licenseValidTo", "coverage", "totalStrength", "maxNoEmp", "natureofWork", "locationofWork", "periodStartDt", "periodEndDt", "contractorId", "pfCode", "wcCode", "wcFromDtm", "wcToDtm", "wcTotal", "pfNum", "pfApplyDt", "reference", "mobileNumber", "esiwc", "esiValidFrom", "esiValidTo", "contractorCode", "workOrderNumber"];
        } else if (templateType === "principalEmployer") {
            headers = ["Organization", "Plant Code", "Name", "Address", "Manager Name", "Manager Address", "Business Type", "Max Workmen", "Max Contract Workmen", "BOCW Applicability", "Is MW Applicability", "License Number", "PF Code", "ESWC", "Factory License Number","State"];
            fieldMap = ["organization", "code", "name", "address", "managerName", "managerAddrs", "businessType", "maxWorkmen", "maxCntrWorkmen", "bocwApplicability", "isMwApplicability", "licenseNumber", "pfCode", "wcNumber", "factoryLicenseNumber","stateNM"];
        }else if (templateType === "workmenbulkupload") {
            headers = ["First Name", "Last Name", "Father's Name or Husband's Name", "Date of Birth", "Trade", "Skill", "Nature of Work", "Hazardous Area", "Aadhar/Id proof number", "Vendor Code", "Gender", "Date of Joining", "Department", "Area", "Work Order Number","PF A/C Number","Marital Status","Technical","Academic","Blood Group","Accommodation","Bank Name Branch","Account Number","Mobile Number","Emergency Contact Number","Police verification Date","Health chekup Date","Access Levels","ESIC Number","UNIT CODE","Organization name","EIC Number","EC number","UAN Number","Emergency Contact Person","Is eligible for PF","SpecializationName","Insurance Type","LL number","Address","Zone","IdMark"];
            fieldMap = ["firstName", "lastName", "relationName", "dateOfBirth", "trade", "skill", "natureOfWork", "hazardousArea",  "aadhaarNumber", "vendorCode", "gender", "doj", "department", "area", "workorderNumber","pfNumber", "maritalStatus", "technical", "academic","bloodGroup", "accommodation", "bankName", "accountNumber", "mobileNumber", "emergencyNumber", "policeVerificationDate", "healthCheckDate", "accessArea", "esicNumber", "unitCode", "organizationName","EICNumber", "ECnumber", "uanNumber", "emergencyName", "pfApplicable", "specializationName", "insuranceType", "LLnumber","address","zone","idMark"];
        }
        else if (templateType === "workmenbulkuploaddraft") {
            headers = ["First Name", "Last Name", "Father's Name or Husband's Name", "Date of Birth", "Trade", "Skill", "Nature of Work", "Hazardous Area", "Aadhar/Id proof number", "Vendor Code", "Gender", "Date of Joining", "Department", "Area", "Work Order Number","PF A/C Number","Marital Status","Technical","Academic","Blood Group","Accommodation","Bank Name Branch","Account Number","Mobile Number","Emergency Contact Number","Police verification Date","Health chekup Date","Access Levels","ESIC Number","UNIT CODE","Organization name","EIC Number","EC number","UAN Number","Emergency Contact Person","Is eligible for PF","SpecializationName","Insurance Type","LL number","Address","Zone","IdMark"];
            fieldMap = ["firstName", "lastName", "relationName", "dateOfBirth", "trade", "skill", "natureOfWork", "hazardousArea",  "aadhaarNumber", "vendorCode", "gender", "doj", "department", "area", "workorderNumber","pfNumber", "maritalStatus", "technical", "academic","bloodGroup", "accommodation", "bankName", "accountNumber", "mobileNumber", "emergencyNumber", "policeVerificationDate", "healthCheckDate", "accessArea", "esicNumber", "unitCode", "organizationName","EICNumber", "ECnumber", "uanNumber", "emergencyName", "pfApplicable", "specializationName", "insuranceType", "LLnumber","address","zone","idMark"];
        }

        headers.forEach(header => {
            const th = document.createElement("th");
            th.style.border = "1px solid grey";
            th.style.padding = "8px";
            th.textContent = header;
            tableHeaderRow.appendChild(th);
        });

        const safe = val => (val !== null && val !== undefined ? val : "");

        data.forEach(row => {
            const tr = document.createElement("tr");
            fieldMap.forEach(field => {
                const td = document.createElement("td");
                td.style.border = "1px solid grey";
                td.style.padding = "8px";
                td.textContent = safe(row[field]);
                tr.appendChild(td);
            });
            tableBody.appendChild(tr);
        });
    }

    function renderErrors(errorData) {
        const container = document.getElementById("errorContainer");
        container.innerHTML = "";

        if (!errorData || errorData.length === 0) {
            container.textContent = "No errors found.";
            return;
        }

        const table = document.createElement("table");
        table.classList.add("table", "table-bordered");
        table.style.marginTop = "10px";
        table.style.borderCollapse = "collapse";
        table.style.width = "100%";

        const headerRow = document.createElement("tr");
        ["Row", "Field", "Error Message"].forEach(text => {
            const th = document.createElement("th");
            th.innerText = text;
            th.style.border = "1px solid #999";
            th.style.padding = "6px";
            headerRow.appendChild(th);
        });
        table.appendChild(headerRow);

        errorData.forEach(error => {
            if (error.fieldErrors) {
                // Loop through all field-level errors
                for (const [field, message] of Object.entries(error.fieldErrors)) {
                    const row = document.createElement("tr");

                    const rowCell = document.createElement("td");
                    rowCell.textContent = error.row || "-";
                    rowCell.style.border = "1px solid #999";
                    rowCell.style.padding = "6px";

                    const fieldCell = document.createElement("td");
                    fieldCell.textContent = field;
                    fieldCell.style.border = "1px solid #999";
                    fieldCell.style.padding = "6px";

                    const messageCell = document.createElement("td");
                    messageCell.textContent = message;
                    messageCell.style.border = "1px solid #999";
                    messageCell.style.padding = "6px";

                    row.appendChild(rowCell);
                    row.appendChild(fieldCell);
                    row.appendChild(messageCell);
                    table.appendChild(row);
                }
            } else {
                // General row-level error
                const row = document.createElement("tr");

                const rowCell = document.createElement("td");
                rowCell.textContent = error.row || "-";
                rowCell.style.border = "1px solid #999";
                rowCell.style.padding = "6px";

                const fieldCell = document.createElement("td");
                fieldCell.textContent = "-";
                fieldCell.style.border = "1px solid #999";
                fieldCell.style.padding = "6px";

                const messageCell = document.createElement("td");
                messageCell.textContent = error.error || "Unknown error";
                messageCell.style.border = "1px solid #999";
                messageCell.style.padding = "6px";

                row.appendChild(rowCell);
                row.appendChild(fieldCell);
                row.appendChild(messageCell);
                table.appendChild(row);
            }
        });

        container.appendChild(table);
    }





    function searchUserByShortName() {
        var shortName = $('#shortNameSearch').val().trim();
        console.log("Searching for userAccount:", shortName);
        if (!shortName) {
            alert("Please enter a short name to search.");
            return;
        }

        $.ajax({
            url: '/CWFM/org-level-mapping/getUserWithShortName',
            type: 'GET',
            data: { shortName: shortName },
            success: function(response) {
            	console.log("Response received:", response); // ✅ Debug
                var tableBody = $('#OrgLevelTable tbody');
                tableBody.empty();

                if (response.length > 0) {
                    $.each(response, function(index, user) {
                        var row = '<tr>' +
                            '<td><input type="checkbox" name="selectedOrgMap" value="' + user.orgAcctSetId + '"></td>' +
                            '<td>' + user.shortName + '</td>' +
                            '<td>' + user.longDescription + '</td>' +
                            '</tr>';
                        tableBody.append(row);
                    });
                } else {
                    tableBody.append('<tr><td colspan="3">No data found</td></tr>');
                }
            },
            error: function(xhr, status, error) {
                console.error("Error during search:", error);
            }
        });
    }
  
    function redirectToOrgLevelMappingView() {
    	 var selectedCheckbox = document.querySelector('input[name="selectedOrgMap"]:checked');

    	    if (!selectedCheckbox) {
    	        alert("Please select a record to view.");
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
    	    xhr.open("GET", "/CWFM/org-level-mapping/orglevelmappingview?id=" + selectedId, true);
    	    xhr.send();
       
    } 
    function downloadTemplateInfo() {
    	 const templateType = document.getElementById("templateType").value;

        if (!templateType) {
            alert("Please select a template to download.");
            return;
        }

        // Trigger the CSV file download from server
        const downloadUrl = "/CWFM/data/downloadTemplate?templateType=" + encodeURIComponent(templateType);
        window.location.href = downloadUrl;
    }
    function saveSelectedRows() {
        const selected = [];
        document.querySelectorAll('input[type="checkbox"][name="selectedWOs"]:checked').forEach(cb => {
            selected.push(parseInt(cb.value)); // Ensure it's an integer
        });

        const successDiv = document.getElementById("successMessage");
        const errorDiv = document.getElementById("errorMessage");

        // Clear previous messages
        successDiv.style.display = "none";
        errorDiv.style.display = "none";
        successDiv.innerText = "";
        errorDiv.innerText = "";

        if (selected.length === 0) {
            errorDiv.innerText = "Please select at least one row to save.";
            errorDiv.style.display = "block";
            setTimeout(() => errorDiv.style.display = "none", 5000);
            return;
        }

        fetch('/CWFM/workmenBulkUpload/validateAndSave', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(selected)
        })
        .then(res => res.json())
        .then(result => {
            if (result.status === "success") {
                successDiv.innerText = result.message;
                successDiv.style.display = "block";
            } else if (result.status === "partial") {
                successDiv.innerText = result.message;
                successDiv.style.display = "block";
            } else {
                errorDiv.innerText = result.message || "Something went wrong.";
                errorDiv.style.display = "block";
            }

            // ✅ Delay the reload until after message disappears
            setTimeout(() => {
                successDiv.style.display = "none";
                errorDiv.style.display = "none";
                loadCommonList('/workmenBulkUpload/list', 'Workmen Bulk Upload');
            }, 5000);
        })
        .catch(err => {
            console.error("Save error:", err);
            errorDiv.innerText = "An unexpected error occurred.";
            errorDiv.style.display = "block";

            // Auto-hide error and reload after 5 seconds
            setTimeout(() => {
                errorDiv.style.display = "none";
                loadCommonList('/workmenBulkUpload/list', 'Workmen Bulk Upload');
            }, 5000);
        });
    }


    function ContrExportToCSV() {
        var selectedRows = document.querySelectorAll('input[name="selectedWOs"]:checked');
        if (selectedRows.length === 0) {
            alert("Please select at least one record to export.");
            return;
        }

        var csvContent = "data:text/csv;charset=utf-8,";
        csvContent += "Transaction Id,First Name,Last Name,	Gender,Date of Birth,Aadhar Number,	Contractor Name,Unit Name,Record Status\n"; // Add headers here
        selectedRows.forEach(function(row) {
            var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4),td:nth-child(5), td:nth-child(6), td:nth-child(7),td:nth-child(8), td:nth-child(9), td:nth-child(10)'); // Adjust column indices as needed
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

    function searchGatePassStatus() {
	    var transactionId = $('#transactionId').val().trim();
	    
		var gatepassId=$("#gatepassId").val().trim();
	    $.ajax({
	        url: '/CWFM/entrypassstatus/list',
	        type: 'POST',
	        data: {
	        	transactionId: transactionId,
	            gatepassId:gatepassId
	        },
	        success: function(response) {
	             var tableBody = $('#workmenTable tbody');
	            tableBody.empty();
	            if (response.length > 0) {
	                $.each(response, function(index, wo) {
	                    var row = '<tr  >' +
								'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.transactionId + '"></td>'+
								'<td  >' + wo.transactionId + '</td>' +
								 '<td  >' + wo.gatePassId + '</td>' +
	                              '<td  >' + wo.firstName+' ' +wo.lastName + '</td>' +
								  '<td  >' + wo.aadhaarNumber + '</td>' +	
								  '<td  >' +wo.approvedby + '</td>' +	
								  '<td  >' + wo.pendingwith + '</td>' +
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

    </script>
    <!-- Add this anywhere, preferably just before closing </body> -->
<div id="loader" style="display: none;">
  <div class="spinner"></div>
</div>

    
</body>

</html> --%>