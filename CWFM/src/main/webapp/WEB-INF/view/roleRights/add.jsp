<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
 <title>Role Rights Add</title>
  <!--   <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Role Rights List</title>
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/commonjs.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css">  -->

    <style>
        .form-control-rounded {
            border-radius: 0.25rem;
        }
        .form-group {
            margin-bottom: 1rem;
        }
        
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
        justify-content: space-between; /* Distribute space between search and buttons */
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

</head>
<body>
      
  <form id="roleRightsForm" >
    <div id="roleRightsForm">
        <div class="table-container">
            <table id="roleRightsTable" class="table table-bordered">
                <thead>
                    <tr>
                        <th>Role</th>
                        <th>Page</th>
                        <th>Add</th>
                        <th>Edit</th>
                        <th>Delete</th>
                        <th>View</th>
                        <th>Import</th>
                        <th>Export</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="roleRight" items="${roleRightsForm.roleRights}" varStatus="status">
                        <tr>
                        <td>
    <select class="roleId form-control form-control-rounded"  name="roleRights[${status.index}].roleId" required style="padding:3px;color:gray;">
        <option value="">Select Role</option>
        <c:forEach var="role" items="${roles}">
            <option value="${role.gmId}" <c:if test="${roleRight.roleId == role.gmId}">selected</c:if>>
                ${role.gmName}
            </option>
        </c:forEach>
    </select>
</td>
<td>
    <select class="pageId form-control form-control-rounded"  name="roleRights[${status.index}].pageId" required style="padding:3px;color:gray;">
        <option value="">Select Page</option>
        <c:forEach var="page" items="${pages}">
            <option value="${page.gmId}" <c:if test="${roleRight.pageId == page.gmId}">selected</c:if>>
                ${page.gmName}
            </option>
        </c:forEach>
    </select>
</td>
                            <%-- <td>
                                <select id="roleId" name="roleRights[${status.index}].roleId" class="form-control form-control-rounded" required>
                                    <option value="">Select Role</option>
                                    <c:forEach var="role" items="${roles}">
                                        <option value="${role.gmId}" <c:if test="${roleRight.roleId == role.gmId}">selected</c:if>>
                                            ${role.gmName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <select id="pageId" name="roleRights[${status.index}].pageId" class="form-control form-control-rounded" required>
                                    <option value="">Select Page</option>
                                    <c:forEach var="page" items="${pages}">
                                        <option value="${page.gmId}" <c:if test="${roleRight.pageId == page.gmId}">selected</c:if>>
                                            ${page.gmName}
                                        </option>
                                    </c:forEach>
                                </select>

                            </td> --%>
                            <td>
    <input type="hidden" name="roleRights[${status.index}].addRights" value="0">
    <input type="checkbox" name="roleRights[${status.index}].addRights" value="1" <c:if test="${roleRight.addRights}">checked</c:if>>
</td>
<td>
    <input type="hidden" name="roleRights[${status.index}].editRights" value="0">
    <input type="checkbox" name="roleRights[${status.index}].editRights" value="1" <c:if test="${roleRight.editRights}">checked</c:if>>
</td>
<td>
    <input type="hidden" name="roleRights[${status.index}].deleteRights" value="0">
    <input type="checkbox" name="roleRights[${status.index}].deleteRights" value="1" <c:if test="${roleRight.deleteRights}">checked</c:if>>
</td>
<td>
    <input type="hidden" name="roleRights[${status.index}].viewRights" value="0">
    <input type="checkbox" name="roleRights[${status.index}].viewRights" value="1" <c:if test="${roleRight.viewRights}">checked</c:if>>
</td>
<td>
    <input type="hidden" name="roleRights[${status.index}].importRights" value="0">
    <input type="checkbox" name="roleRights[${status.index}].importRights" value="1" <c:if test="${roleRight.importRights}">checked</c:if>>
</td>
<td>
    <input type="hidden" name="roleRights[${status.index}].exportRights" value="0">
    <input type="checkbox" name="roleRights[${status.index}].exportRights" value="1" <c:if test="${roleRight.exportRights}">checked</c:if>>
</td>

                            <td><button type="button" class="btn btn-danger" style="color:blue;background-color:white;" onclick="removeRow(this)">-</button></td>
                        </tr>
                    </c:forEach>
                    <div id="error-message" style="display: none; color: red; font-weight: bold;"></div>
                </tbody>
            </table>
        </div>
        <div>
            <button type="button"  onclick="addNewRow1()" class="btn btn-default process-footer-button-cancel ng-binding" style="margin:6px;">Add New Row</button>
            <button type="button"  onclick="saveRoleRights()"class="btn btn-default process-footer-button-cancel ng-binding" style="margin:6px;">Submit</button>
        </div>
    </div>
</form>

</body>
</html>
