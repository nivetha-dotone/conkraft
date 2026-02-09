<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/contractor.js"></script>
    <script src="resources/js/cms/workorder.js"></script>
    <script src="resources/js/cms/workmen.js"></script>
     <script src="resources/js/cms/unitDeptMapping.js"></script>
     <style>
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
            font-weight: bold;
            font-size: 15px; /* Reduced font size */
        }
        body {
            background-color: #FFFFFF;
            font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        }
        .action-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem;
            background-color: #f8f8f8;
           /* margin-bottom: 1rem; */
        }
        .action-buttons {
            display: flex;
            gap: 0px;
        }
         .action-buttons button {
        margin-left: 10px; /* Space between buttons */
    }
        #searchForm {
            display: flex;
            align-items: center;
            flex-grow: 1;
            margin-right: 10px;
        }
        .search-box {
            width: 150px; /* Reduced width */
            padding: 0.25rem;
            font-size: 0.875rem;
            border: 1px solid #ccc;
            border-radius: 4px;
            outline: none;
            margin-right: 10px;
            box-sizing: border-box;
        }
        .table-container {
            overflow-x: auto;
            margin: 0;
            padding: 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 5px; /* Reduced padding */
            text-align: left;
            border: 1px solid #ddd;
            font-size: 0.875rem;
        }
        th {
            background-color: #DDF3FF;
            color: #005151;
            font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
            font-size: 0.75rem;
            padding: 4px; /* Reduced padding */
        }
         .page-header {
        display: flex;
        align-items: center;
        justify-content: flex-end; /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        /* border-bottom: 1px solid #ccc; /* Subtle border for separation */ */
        margin-top: 5px;
    }
        select, input[type="text"] {
            width: 20%; /* Full width */
            padding: 6px; /* Reduced padding */
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 12px; /* Reduced font size */
            color: #000; /* Change text color to black */
            box-sizing: border-box;
        }
        select {
            background-color: #f9f9f9;
            color: #000;
        }
        input[type="text"] {
            background-color: #ffffff;
            color: #000;
        }
        .error-message {
    color: red;
    font-size: 14px;
    display: inline-block; /* Keeps it in a single row */
    white-space: nowrap; /* Prevents line breaks */
    overflow: hidden;
    text-overflow: ellipsis; /* Adds "..." if text is too long */
    max-width: 100%; /* Adjust width based on layout */
}
 /* Container looks like a table */
    #rowsContainer {
        width: 100%;
        border-collapse: separate;
        border-spacing: 0 10px; /* <-- gap between rows */
       max-height: 400px; 
        overflow-y: auto;
         border: 1px solid #ccc; 
         padding: 10px; 
         margin-bottom: 15px;
        
    }

    /* Each row styled like a table row */
    .row-block {
        display: flex;
        align-items: center;
        padding: 3px;
        background: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        margin-bottom: 8px; /* Extra spacing */
    }

    .row-block label {
        font-weight: bold;
        color: darkcyan;
        margin-right: 8px;
        min-width: 140px;
    }

    .row-block select {
        margin-right: 15px;
        flex: 1;
    }

    .row-block button {
        margin-left: 10px;
    }

    .btn-danger {
        background: red;
        color: white;
        border: none;
        border-radius: 5px;
        padding: 5px 10px;
    }

    .btn {
        border-radius: 20px;
        padding: 2px 13px;
        margin-top: 0px;
    }
     .tabs-container {
        display: flex;
        justify-content: space-between; /* Distribute space between tabs and buttons */
        align-items: center; /* Align items vertically */
    }

    .tabs {
        display: flex;
        flex-wrap: nowrap; /* Prevent wrapping of tabs */
    }

    .tabs button {
        margin-right: 10px; /* Space between tabs */
    }

    .action-buttons {
        display: flex; /* Align buttons horizontally */
        align-items: center; /* Center buttons vertically */
    }

    .action-buttons button {
        margin-left: 10px; /* Space between buttons */
    }
    </style> 
   <!--  <script>
    document.getElementById("addRow").addEventListener("click", function () {
        let container = document.getElementById("rowsContainer");
        let firstRow = container.querySelector(".row-block");

        // clone the first row
        let newRow = firstRow.cloneNode(true);

        // clear selected values
        newRow.querySelectorAll("select").forEach(sel => sel.value = "");

        container.appendChild(newRow);
    });

    // Event delegation for delete button
    document.getElementById("rowsContainer").addEventListener("click", function (e) {
        if (e.target.classList.contains("remove-row")) {
            let row = e.target.closest(".row-block");

            // At least one row must remain
            if (document.querySelectorAll(".row-block").length > 1) {
                row.remove();
            } else {
                alert("At least one row is required.");
            }
        }
    });
</script> -->
    
</head>

<body>
<div class="page-header">
     
         <div class="action-buttons" >
    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" id="addRow" onclick="addRow()">Add</button>
    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveDepartmentAreaData()">Save</button>
    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="goBackToDeptMappingList()">Cancel</button>
        </div> 
   </div>
<f:form id="addAadharOBForm" action="/CWFM/contractworkmen/addAadharOB" modelAttribute="workmenbyAadhar" method="post" autocomplete="off">
<!-- Dropdown Row -->
<div id="rowsContainer" style="margin: 10px;background-color: #DDF3FF">

    <!-- Default Row -->
    <div class="row-block mb-2">
        <!-- <label style="color: darkcyan;">Principal Employer:</label> -->
        <select class="custom-select" id="principalEmployer" name="principalEmployerId" required style="padding:3px;color:gray;">
            <option value="">Please select Principal Employer</option>
            <c:forEach var="pe" items="${PrincipalEmployer}">
                <option value="${pe.id}">${pe.description}</option>
            </c:forEach>
        </select>

        <!-- <label style="color: darkcyan;">Department:</label> -->
        <select class="custom-select" id="department" name="departmentId" style="padding:3px;color:gray;">
            <option value="">Please select Department</option>
            <c:forEach var="dept" items="${Department}">
                <option value="${dept.gmId}">${dept.gmName}</option>
            </c:forEach>
        </select>

        <!-- <label style="color: darkcyan;">Sub Department:</label> -->
        <select class="custom-select" id="subDepartment" name="subDepartmentId" style="padding:3px;color:gray;">
            <option value="">Please select Sub Department</option>
            <c:forEach var="sub" items="${AreaOptions}">
                <option value="${sub.gmId}">${sub.gmName}</option>
            </c:forEach>
        </select>
        <button class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteRow(this)">Delete</button>
<!-- <button type="button" class="remove-btn" onclick="deleteRow()" >Delete</button> -->
        <!-- <button type="button" class="btn btn-danger btn-sm remove-row">Delete</button> -->
    </div>
    
</div>
 </f:form>

</body>
</html>
