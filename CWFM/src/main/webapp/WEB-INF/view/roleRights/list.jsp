<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
 <title>Role Rights List</title>
  <!--   <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Role Rights List</title>
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/commonjs.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css">  -->
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
        display: flex;
        align-items: center;
        justify-content: flex-end;  /* Distribute space between search and buttons */
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
        text-align: center; /* Center align text */
        padding: 4px; /* Reduced padding for the table header */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }
   
</style>
<script>

</script>
</head>
<body>
<div class="page-header">
    <%-- <form id="searchForm">
        <input type="text" class="search-box ng-pristine ng-untouched ng-valid ng-empty" id="searchInput" name="searchQuery" placeholder="Search..." autocomplete="off">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')">Search</button>
    </form> --%>
   <!--  <div> -->
    <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToRRAdd()">Add</button>
    <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" id="editButton" onclick="redirectToRREdit()">Edit</button>
    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteSelectedRoleRights()">Deactive</button>
    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="exportToRoleCSV()">Export</button>
<button type="submit" id="saveButton" class="btn btn-default" style="display: none;" onclick="saveUpdatedRoleRights()">Save</button>

<!-- </div> -->
</div>
<form id="updateForm" action="/CWFM/roleRights/update" method="POST">
   <!--  <button type="button" id="editButton">Edit</button>  
    <button type="submit" id="saveButton" style="display: none;">Save</button>   -->

    <div class="table-container">
    <table >
        <thead>
            <tr>
                <td>
                    <input type="checkbox" id="selectAllRightsCheckbox" onchange="toggleSelectRoleRights()">
                </td> 
                <th>Role Name</th>
                <th>Page Name</th>
                <th>Add Rights</th>
                <th>Edit Rights</th>
                <th>Delete Rights</th>
                <th>Export Rights</th>
                <th>View Rights</th>
                <th>List Rights</th>
            </tr>
        </thead>
        <tbody id="roleRightsTableBody">
            <c:forEach var="rights" items="${roleRightsList}">
                <tr data-roleRightId="${rights.roleRightId}">
                    <td>
                        <input type="checkbox" name="selectedRoleRights" value="${rights.roleRightId}" onchange="toggleRowSelection(this)">
                    </td>
                    <td>${rights.role.gmName}</td>
                    <td>${rights.page.gmName}</td>

                    <td class="editable">
                        <input type="checkbox" class="rights-checkbox" name="canAdd" ${rights.addRights == 1 ? 'checked' : ''} disabled>
                    </td>

                    <td class="editable">
                        <input type="checkbox" class="rights-checkbox" name="canEdit" ${rights.editRights == 1 ? 'checked' : ''} disabled>
                    </td>

                    <td class="editable">
                        <input type="checkbox" class="rights-checkbox" name="canDelete" ${rights.deleteRights == 1 ? 'checked' : ''} disabled>
                    </td>

                    <td class="editable">
                        <input type="checkbox" class="rights-checkbox" name="canExport" ${rights.exportRights == 1 ? 'checked' : ''} disabled>
                    </td>

                    <td class="editable">
                        <input type="checkbox" class="rights-checkbox" name="canView" ${rights.viewRights == 1 ? 'checked' : ''} disabled>
                    </td>

                    <td class="editable">
                        <input type="checkbox" class="rights-checkbox" name="canList" ${rights.listRights == 1 ? 'checked' : ''} disabled>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</form>
</body>
</html>
