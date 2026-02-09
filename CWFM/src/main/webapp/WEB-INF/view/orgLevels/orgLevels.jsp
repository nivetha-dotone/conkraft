<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Org Levels</title>
   <!--  <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
    <script src="resources/js/orglevel.js"></script> -->  <!-- Include JS file specific to orgLevel -->
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
        justify-content: space-between; /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
        gap: 10px;
    }

    .page-header > div {
        display: flex;
        gap: 10px; /* Space between buttons */
         align:right;
         
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
    .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}
</style>
  
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

   <div class="page-header">
    <div class="page-header-buttons">
        <button type="button" class="btn btn-default"  onclick="addNewRow()">Add New Row</button>
        <button type="submit" class="btn btn-default" onclick="submitOrgLevel()">Save</button>
        <button type="submit" class="btn btn-default" onclick="deleteOrgLevel()">Delete Selected</button>
        <!-- <button  type="submit" class="btn btn-default" onclick="exportOrgLevelCSV()">Export</button> -->
    </div>
</div>
       <div class="table-container">
        <form method="post" action="/save" autocomplete="off">
    <table id="orgLevelTable">
         <thead>
                <tr>
                    <th class="checkbox-cell">
                        <input type="checkbox" id="selectAllOrgLevelCheckbox" onchange="toggleSelectAllOrgLevel()">
                    </th>
                    <th>Org Level Name</th>
                    <th>Short Name</th>
                    <th>Hierarchy</th>
                </tr>
            </thead>
            <tbody>
                <!-- Render rows from server -->
                <c:forEach items="${orgLevels}" var="orgLevel">
                    <tr data-row-id="${orgLevel.orgLevelDefId}">
                        <td class="checkbox-cell">
                        <input type="hidden" name="orgLevelDefId[]" value="${orgLevel.orgLevelDefId}">
                            <input type="checkbox" name="selectedOrgLevels" value="${orgLevel.orgLevelDefId}" data-row-id="${orgLevel.orgLevelDefId}">
                        </td>
                        <td><input type="text" name="orgLevelName[]" value="${orgLevel.name}" required style="color:gray; text-transform: capitalize;" autocomplete="off"></td>
                        <td><input type="text" name="shortName[]" value="${orgLevel.shortName}" required style="color:gray; text-transform: capitalize;" autocomplete="off"></td>
                        <td><input type="number" name="hierarchy[]" value="${orgLevel.orgHierarchyLevel}" required style="color:gray;" autocomplete="off"></td>
                        
                    </tr>
                </c:forEach> 
          
                <!-- Default empty row when no data -->
                <c:if test="${orgLevels == null || orgLevels.isEmpty()}">
                    <tr>
                        <td class="checkbox-cell">
                            <input type="checkbox" name="selectedOrgLevels">
                        </td>
                        <td><input type="text" name="orgLevelName[]" placeholder="Org Level Name" required autocomplete="off" style="text-transform: capitalize;"></td>
                        <td><input type="text" name="shortName[]" placeholder="Short Name" required autocomplete="off" style="text-transform: capitalize;"></td>
                        <td><input type="number" name="hierarchy[]" placeholder="Hierarchy" required autocomplete="off" style="text-transform: capitalize;"></td>
                    </tr>
                </c:if>
            </tbody>
    </table>
</form>

    </div>

</body>
</html>
