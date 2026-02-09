<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
 <title>General Type</title>
    <!-- <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
      <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
  <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/commonjs.js"></script> 
    <script src="resources/js/jquery-3.6.0.min.js"></script> -->
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
        /* display: flex; */
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
    }
    .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}
</style>
<style>
    #gmTypeName {
        text-transform: uppercase;
    }
</style>
 <script>
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
        function convertToUppercase(element) {
            element.value = element.value.toUpperCase();
        }
    </script>
</head>
<body>
 <div>
        <c:if test="${not empty successMessage}">
            <p class="success">${successMessage}</p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>
    </div>
   <%-- <form id="hiddenForm">
    <input type="text" id="pageId" value="${sessionScope.selectedPageId}" />
    <input type="text" id="selectedRoleId" value="${sessionScope.selectedRoleId}" />
</form> --%>
<div class="page-header">
 <div class="page-header-buttons"> 
  <label for="gmTypeName"style=" color: darkcyan;">GM Type Name:</label>
  
        <input type="text" id="gmTypeName" name="gmTypeName" required  oninput="convertToUppercase(this)" autocomplete="off"> 
      <%--  <c:if test="${userRights[0].addRights == 0}"> --%>
      
		<div class="page-header-buttons">
        <button type="submit"  class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGMTYPE()">Save</button>
   <%-- </c:if>
    <c:if test="${userRights[0].deleteRights == 0}"> --%>
    <button type="submit"   class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteSelectedGMTYPE()">Delete Selected</button>
    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="exportGMTYPECSV()">Export</button>
</div> 
</div>
<%-- </c:if> --%>
<!-- <div>
<form action="/CWFM/generalController/saveGMType" method="post">
        <label for="gmTypeName">GM Type Name:</label>
        <input type="text" id="gmTypeName" name="gmTypeName" required>
        <button type="submit" type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGMTYPE()">Save</button>
    </form>
   </div> -->
</div>
<%-- <div class="page-header">
    <form id="searchForm">
        <input type="text" class="search-box ng-pristine ng-untouched ng-valid ng-empty" id="searchInput" name="searchQuery" placeholder="Search...">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')">Search</button>
    </form>
    <div>
        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToPEAdd()">Add</button>
        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button>
        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToPEView('${cmSPRINCIPALEMPLOYER.UNITID}')">View</button>
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="exportToCSV()">Export</button>
    </div>
</div> --%>
<div class="table-container">
    <table class="no-dt" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
                <th class="checkbox-cell">
                    <input type="checkbox" id="selectAllCheckbox" onchange="toggleSelectAllGMTYPE()">
                </th>
                <th>GM Type Name</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${gmTypes}" var="gmType">
                <tr>
                    <td class="checkbox-cell">
                        <input type="checkbox" name="selectedGMTIds" value="${gmType.gmTypeId}">
                    </td>
                    <td>${gmType.gmType}</td>
                </tr>
            </c:forEach>
          <div id="error-gmType" style="display: none; color: red; font-weight: bold;"></div>
        </tbody>
    </table>
</div>
</body>
</html>
