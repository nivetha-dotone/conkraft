<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
 <title>General Master</title>
<script >

function editRow(gmId) {
    // Enable editing of the input fields
    const nameInput = document.getElementById(`editName-${gmId}`);
    const valueInput = document.getElementById(`editValue-${gmId}`);
    nameInput.readOnly = false;
    valueInput.readOnly = false;

    // Highlight the inputs to indicate they're editable
    nameInput.style.backgroundColor = "#fff";
    valueInput.style.backgroundColor = "#fff";

    // Change the Edit button text to "Editing..."
    const editButton = document.getElementById(`editBtn-${gmId}`);
    editButton.textContent = "Editing...";
    editButton.disabled = true; // Disable the button while editing
}</script>
   <style>
 
 
    body {
        background-color: #FFFFFF; /* White background for the page */
         font-family: 'Noto Sans', sans-serif;
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
        width: 100%;
        border-collapse: collapse;
    }

    td {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
         font-family: 'Noto Sans', sans-serif;
         
    color: #898989;/* Label text color */
  padding: .2em .6em .3em;
  font-size: 85%;
  font-weight: 700;
  line-height: 1;
    white-space: nowrap;
  vertical-align: baseline;
  border-radius: .25em;
    }
     th {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
          font-weight: bold;
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
       /*  display: flex; */
        align-items: center;
        justify-content: space-between; /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
         gap: 10px; 
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
            width: 100%; /* Full width for small screens */
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
          font-weight: bold;
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
         font-weight: bold;
        text-align: left; /* Center align text */
        padding: 4px; /* Reduced padding for the table header */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
   margin:4px;
    }
   

        .error-message {
    color: red;
    font-size: 12px;
    display: inline-block; /* Keeps it in a single row */
    white-space: nowrap; /* Prevents line breaks */
    overflow: hidden;
    text-overflow: ellipsis; /* Adds "..." if text is too long */
    max-width: 100%; /* Adjust width based on layout */
}
 label {
            color: black;
            font-weight: bold;
            font-size: 12px; /* Reduced font size */
        }
         .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}
    </style>
</head>
<body>
<div class="page-header" method="POST" onsubmit="return validateMasterValue()">
<div class="page-header-buttons"> 
    <label for="gmTypeId" style="color: darkcyan;">Select GM Type:</label>
         <select  style="color:gray;padding:3px;" id="gmTypeId" name="gmTypeId" onchange="fetchGmData()" required>
        <option value="" style="color:gray;"> Select GM Type </option>
        <c:forEach items="${gmTypes}" var="gmType">
            <option value="${gmType.gmTypeId}" <c:if test="${gmType.gmTypeId == gmTypeId}">selected</c:if>>${gmType.gmType}</option>
        </c:forEach>
    </select>
    
    
        <label for="masterName" style="color: darkcyan;">Master Name:</label>
        <input type="text" id="masterName" name="masterName" style="color:gray; text-transform: capitalize;" required autocomplete="off">

        <label for="masterValue" style="color: darkcyan;">Master Value:</label>
        <input type="text" id="masterValue" name="masterValue" style="color:gray;  required autocomplete="off">
<div class="page-header-buttons"> 
        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveGMMaster()">Save</button>
        <!-- <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/generalController/generalMaster', 'General Master')">Cancel</button> -->
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="exportGMMasterCSV()">Export</button>
</div>     
            <input type="hidden" name="gmTypeId" id="gmTypeId" value="${param.gmTypeId}">
            <input type="hidden" id="existingMasterValues" value="${existingMasterValues.join(',')}">
         <!--  <div id="error-gmMaster" style="display: none; color: red; font-weight: bold;"></div>   -->
          <!-- <div id="formErrorMessage" class="error-message" style="display: none; color: red; font-weight: bold;"></div> -->
    </div>
    <div id="formErrorMessage" class="error-message" style="display: none; color: red; font-weight: bold;"></div>
</div>
    <h4 style="color:gray;margin-left:5px;">Existing General Masters</h4>
    <table id="gmTable" class="no-dt">
        <thead>
            <tr>
            <td>
                        <input type="checkbox" id="selectAllGMMCheckbox" onchange="toggleSelectAllGMMaster()">
                    </td> 
               <!--  <th>ID</th> -->
                <th>GM Type</th>
                <th>Master Name</th>
                <th>Master Value</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
         <c:choose>
            <c:when test="${not empty generalMasters}">
                <c:forEach var="entry" items="${generalMasters}">
                    <tr id="row-${entry.gmId}">
                    <td><input type="checkbox"
							name="selectedGMMaster" value="${entry.gmTypeName}"></td>
                        <%-- <td>${entry.gmId}</td> --%>
                        <td>${entry.gmTypeName}</td>
                        <td><input type="text" id="name" value="${entry.gmName}"readonly  style="width: 100%;height: 35px;color:gray;"/></td>
                        <td><input type="text"  id="value" value="${entry.gmDescription}" readonly  style="width: 100%;height: 35px;color:gray;"/></td>             
                       <%-- <td>
                           <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" id="editBtn-${entry.gmId}" onclick="editRow('${entry.gmId}')">Edit</button>
                            <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteGMManager(${entry.gmId},${gmTypeId})">Delete</button>
                        </td> --%>
                        <td>
                        <!-- Action Buttons -->
                        <div id="action-edit-${entry.gmId}">

                           <%--  <button type="button" class="btn btn-primary" 
                                    onclick="editRow('${entry.gmId}')">Edit</button> --%>
                            <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteGMManager(${entry.gmId}, ${gmTypeId})">Delete</button>
                            <!-- class="btn btn-danger" 
                                    onclick="deleteGMManager(${entry.gmId}, ${gmTypeId})">Delete</button> --> 
                       
                           <%--  <button type="button" class="btn btn-success" 
                                    onclick="saveRow('${entry.gmId}')">Save</button>

                            <button type="button" class="btn btn-secondary" 
                                    onclick="cancelEdit('${entry.gmId}')">Cancel</button> --%>
                        </div>
                    </td>
                    </tr>
                </c:forEach>
            </c:when>
           
        </c:choose>
        </tbody>
    </table>
</body>
</html>
