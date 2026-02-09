<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Workflow</title>
   <script src="resources/js/cms/workflow.js"></script>
    <style>
        /* Add your styles here */
        .success {
            color: green;
            font-weight: bold;
            padding: 10px;
            background-color: #e0ffe0;
            border: 1px solid green;
            margin-bottom: 1rem;
        }

        .error {
            color: red;
            font-weight: bold;
            padding: 10px;
            background-color: #ffe0e0;
            border: 1px solid red;
            margin-bottom: 1rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;

            border: 1px solid #ddd;
        }

        th {
            background-color: #DDF3FF;
            color: #005151;
        }

        .checkbox-cell input[type="checkbox"] {
            margin: 0;
        }

        .action-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem;
            background-color: #f8f8f8;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
        }

        .action-buttons button {
            padding: 0.5rem 1rem;
            font-size: 1rem;
            cursor: pointer;
        }
         .success {
        color: green;
        font-weight: bold;
        padding: 10px;
        background-color: #e0ffe0;
        border: 1px solid green;
        margin-bottom: 1rem;
    }
    .error {
        color: red;
        font-weight: bold;
        padding: 10px;
        background-color: #ffe0e0;
        border: 1px solid red;
        margin-bottom: 1rem;
    }
 label {
    color: black;
}
    body {
        background-color: #FFFFFF; /* White background for the page */
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
    }

    .action-bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 1rem;
        background-color: #f8f8f8;
        margin-bottom: 1rem;
    }

    .action-buttons {
        display: flex;
        gap: 10px;
    }

    .action-buttons button {
        padding: 0.5rem 1rem;
        font-size: 1rem;
        cursor: pointer;
    }

    #searchForm {
        display: flex;
        align-items: center;
        flex-grow: 1;
        margin-right: 10px;
    }

    .search-box {
        width: 200px; /* Adjust width to fit layout */
        padding: 0.25rem; /* Reduced padding for height */
        font-size: 0.875rem; /* Smaller font size */
        border: 1px solid #ccc; /* Border to match design */
        border-radius: 4px; /* Slightly rounded corners */
        outline: none; /* Remove default outline */
        margin-right: 10px; /* Space between input and button */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }

    .table-container {
        overflow-x: auto;
        margin: 0; /* Remove space before the table */
        padding: 0; /* Remove padding if any */
    }

    table {
        width: 30%;
        border-collapse: collapse;
    }

    th, td {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
    }

    th {
        background-color: #DDF3FF; /* Light green for the table header */
        color: #005151; /* Text color from side nav bar */
        cursor: pointer;
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        font-size: 0.75rem; /* Decreased font size for table header */
        line-height: 1.2rem; /* Adjust line-height for better fit */
        padding: 6px; /* Reduced padding for table header */
    }

    .page-header {
        display: flex;
        align-items: center;
        justify-content: flex-start; /* Align elements to the left */
    gap: 10px;  /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
    }

    .page-header > div {
        display: flex;
        gap: 10px; /* Space between buttons */
    }

    @media (max-width: 768px) {
        .page-header {
            flex-direction: column; /* Stack items vertically on small screens */
            align-items: flex-start; /* Align items to the start */
        }

        #searchForm {
            width: 100%; 
            margin-right: 0; /* Remove margin on small screens */
        }

        .search-box {
            width: 100%; /* Full width for small screens */
        }

        .page-header > div {
            width: 100%; /* Full width for small screens */
            margin-top: 10px; /* Add space above buttons */
            flex-direction: column; /* Stack buttons vertically */
        }
    }
    .header-text-new {
        font-family: 'Noto Sans', Arial, sans-serif; /* Font family similar to grid header */
        font-size: 14px; /* Adjusted font size to match typical grid header size */
        font-weight: 600; /* Bold text for prominence */
        border: 1px solid #ddd; /* Lighter border for a cleaner look */
        white-space: nowrap; /* Prevent text from wrapping */
        padding: 8px 10px; /* Adjusted padding for better spacing */
          background-color: #E0E0E0;  /* Light background color to match grid header */
        color: #333; /* Text color for readability */
    }
       table th {
        border-top: 0.0625rem solid var(--zed_sys_color_border_lowEmphasis); /* Top border color */
        border-bottom: 1px solid var(--zed_sys_color_border_lowEmphasis); /* Bottom border color */
        border-right: none; /* No right border */
        background-color: #DDF3FF; /* Light green background color */
        color: var(--zed_sys_color_tableHeader_text); /* Text color */
        font-size: 0.75rem; /* Smaller font size */
        line-height: 1.2rem; /* Reduced line height */
        letter-spacing: normal; /* Letter spacing */
        font-family: 'Noto Sans', sans-serif; /* Font family */
        font-weight: 400; /* Font weight */
        text-align: center; /* Center align text */
        padding: 4px; /* Reduced padding for the table header */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }
    
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
     .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}
    </style>
  <script src="resources/js/cms/workflow.js"></script>
 <!--  <script>
  function overrideActionsBasedOnModule() {
	    const moduleDropdown = document.getElementById("module");
	    const actionDropdown = document.getElementById("actionDropdown");

	    const selectedModuleName = moduleDropdown.options[moduleDropdown.selectedIndex].text.trim();

	    // Define manual override actions
	    const moduleActionsMap = {
	        "Workmen Onboarding": [
	            "CREATE", "BLOCK", "UNBLOCK", "BLACKLIST",
	            "DEBLACKLIST", "LOST DAMAGE", "RENEW", "CANCEL"
	        ],
	        "Contractor": ["Contractor Registration"],
	        "Bill Verification": ["Bill Creation"]
	    };

	    // Clear and reset action dropdown in all cases
	    actionDropdown.innerHTML = '<option value="">Please select Action</option>';

	    // If a valid module is selected and it exists in the map, populate actions
	    if (selectedModuleName && moduleActionsMap[selectedModuleName]) {
	        const actions = moduleActionsMap[selectedModuleName];

	        actions.forEach(action => {
	            const option = document.createElement("option");
	            option.value = action;
	            option.text = action;
	            actionDropdown.appendChild(option);
	        });
	    }

	    // If no module is selected, the action dropdown remains empty (just the default option)
	}

  </script> -->
</head>
<body>

   
<div class="page-header" method="POST" onsubmit="return validateMasterValue()">
    
    <label for="gmTypeId" style="color: darkcyan;">Principal Employer:</label>
        <select class="custom-select" id="principalEmployer" name="principalEmployerId" onchange="getBusinessType(this.value)" required style="color:gray;padding:3px;">
                                <option value="">Please select Principal Employer</option>
								<c:forEach var="pe" items="${PrincipalEmployer}">
                					<option value="${pe.unitId}">${pe.name}</option>
            					</c:forEach>
                                </select>
        
       
  
    
    
       <!--  <label for="masterName" style="color: darkcyan;">Business Type</label>
         <select class="custom-select" id="businessType" name="businessTypeId" required style="color:gray;padding:3px;">
                                <option value="">Please select Business Type</option>
								
                                </select> -->
       

        <label for="masterValue" style="color: darkcyan;">Module:</label>
         <select class="custom-select" id="module" name="moduleId"  onchange="overrideActionsBasedOnModule()" required style="color:gray;padding:3px;">
                                <option value="">Please select Module</option>
								<c:forEach var="pe" items="${Modules}">
                					<option value="${pe.gmId}">${pe.gmName}</option>
            					</c:forEach>
                                </select>
                                <label for="masterValue" style="color: darkcyan;">Action:</label>
                                  <select class="custom-select" name="action" id="actionDropdown" onchange="onModuleChange()"  required style="color:gray;padding:3px;">
                        <option value="">Please select Action</option>
                        <%-- <c:forEach var="pe" items="${Actions}">
                            <option value="${pe.gmId}">${pe.gmName}</option>
                        </c:forEach> --%>
                    </select>
<div class="page-header-buttons">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="exportGMMasterCSV()">Export</button>
         <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/workflow/list','Workflow')">Cancel</button>
          <div id="formErrorMessage" class="error-message" style="display: none; color: red; font-weight: bold;"></div>
    </div>
    </div>

  <div style="padding:10px;">

    <h3 style=" color: darkcyan;">Workflow Type</h3>
    <label><input type="radio" name="workflowType" value="1" onchange="toggleHierarchyColumn()"> Auto</label>
    <label><input type="radio" name="workflowType" value="2" onchange="toggleHierarchyColumn()"> Sequential</label>
    <label><input type="radio" name="workflowType" value="3" onchange="toggleHierarchyColumn()"> Parallel</label>

    <h3 style="color: darkcyan;">Approver Hierarchy</h3>
    <button type="button" onclick="addRow()" class="btn btn-default process-footer-button-cancel ng-binding" style="margin:6px;">Add New Row</button>
    <button type="button" onclick="deleteSelected()" class="btn btn-default process-footer-button-cancel ng-binding" style="margin:6px;">Delete Selected</button>
    <br><br>

    <table id="approverTable" border="1" class="no-dt">
        <thead>
            <tr>
                <th>Select</th>
                
                <th>Role Name</th>
                <th class="hierarchy-col">Hierarchy Index</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>

    <!-- Hidden Template Row -->
    <table style="display: none;" class="no-dt">
        <tbody>
            <tr id="templateRow">
                <td><input type="checkbox" class="rowSelect"></td>
                
                <td>
                    <select class="custom-select" name="role" required style="color:gray;padding:3px;">
                        <option value="">Please select Role</option>
                        <c:forEach var="pe" items="${Roles}">
                            <option value="${pe.gmId}">${pe.gmName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td class="hierarchy-col">
                    <input type="number" name="hierarchyIndex" required autocomplete="off" style="color:gray;">
                </td>
            </tr>
        </tbody>
    </table>

    <br>
    <button type="button" onclick="saveWorkflow()" class="btn btn-default process-footer-button-cancel ng-binding" style="margin:6px;" >Save Workflow</button>
    </div>
</body>
</html>
