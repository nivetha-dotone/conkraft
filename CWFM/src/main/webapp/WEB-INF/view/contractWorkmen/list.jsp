<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CMSPRINCIPALEMPLOYER List</title>
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
      <script src="resources/js/commonjs.js"></script>
      <script>
     
    </script>
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
<div class="page-header">
        Principal Employer List
        <div class="header-buttons">
        <button type="submit" onclick="redirectToPEAdd()">Add</button>
        <button type="submit" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button>
        <button type="submit" onclick="redirectToPEView('${cmSPRINCIPALEMPLOYER.UNITID}')">View</button>
         <button type="button" onclick="exportToCSV()">Export</button>
        </div>
        </div>
    <form id="searchForm">
    <input type="text" id="searchInput" name="searchQuery" placeholder="Search...">
    <button type="button" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')">Search</button>
</form>    

        <!-- <form action="/CWFM/principalEmployer/search" method="GET">
    <input type="text" name="query" placeholder="Search...">
    <button type="submit">Search</button>
</form> -->

     <form id="updateForm" action="/CWFM/workmenwage/update" method="POST" >
                         <div class="table-container">
    <table id="principalEmployerTable"  cellspacing="0" cellpadding="0">
        <thead>
        
            <!-- <tr>
            <td style="border: 1px solid black;">
    <label class="toggle">
        <input type="checkbox" id="selectAllCheckbox" onchange="toggleSelectAll()">
        <span class="slider"></span>
    </label>
</td> -->
<tr>
                    <td style="border: 1px solid black;">
                        <input type="checkbox" id="selectAllCheckbox" onchange="toggleSelectAll()">
                    </td> 
                    <!-- Add more table headers for each column -->
                    <th class="header-text"  onclick="sortTable(1)">Name<span id="sortIndicatorName" class="sort-indicator sort-asc">&#x25B2;</span></th>
                   <th class="header-text"  onclick="sortTable(2)">Address<span id="sortIndicatorAddress" class="sort-indicator sort-asc">&#x25B2;</span></th>
                   <th class="header-text"  onclick="sortTable(3)">Manager Name<span id="sortIndicatorManagerName" class="sort-indicator sort-asc">&#x25B2;</span></th>
                   <th class="header-text"  onclick="sortTable(4)">Manager Address<span id="sortIndicatorManagerAddr" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(5)">Business Type<span id="sortIndicatorBusinessType" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(6)">Max Workmen<span id="sortIndicatorMaxWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(7)">Max Contract Workmen<span id="sortIndicatorMaxCntrWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(8)">BOCW Applicability<span id="sortIndicatorBocwApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(9)">ISMW Applicability<span id="sortIndicatorIsmApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(10)">Code<span id="sortIndicatorCode" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(11)">Organization<span id="sortIndicatorOrganization" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(12)">PF Code<span id="sortIndicatorPfCode" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(13)">License Number<span id="sortIndicatorLicenseNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(14)">WC Number<span id="sortIndicatorWcNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(15)">ESIC Number<span id="sortIndicatorEsicNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(16)">PTREGNO<span id="sortIndicatorPtReg" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(17)">LWFREGNO<span id="sortIndicatorLwfReg" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(18)">Factory License Number<span id="sortIndicatorFactoryLicNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(19)">ISRC Applicable<span id="sortIndicatorIsrApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(20)">RC Number<span id="sortIndicatorRcNum" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(21)">Attachment Name<span id="sortIndicatorAttachment" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(22)">State ID<span id="sortIndicatorStateId" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <!-- Add more headers as needed -->
                <!-- <td style="border: 1px solid black;">ISACTIVE</td>
                <td style="border: 1px solid black;">UPDATEDTM</td>
                <td style="border: 1px solid black;">UPDATEDBY</td> -->
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${cmSPRINCIPALEMPLOYERs}" var="cmSPRINCIPALEMPLOYER">
                <tr>
                 <td style="border: 1px solid black;"><input type="checkbox" name="selectedUnitIds" value="${cmSPRINCIPALEMPLOYER.UNITID}"></td>
                    <%-- <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.UNITID}</td> --%>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.NAME}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ADDRESS}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.MANAGERNAME}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.MANAGERADDRS}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.BUSINESSTYPE}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.MAXWORKMEN}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.MAXCNTRWORKMEN}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.BOCWAPPLICABILITY}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ISMWAPPLICABILITY}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.CODE}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ORGANIZATION}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.PFCODE}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.LICENSENUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.WCNUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ESICNUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.PTREGNO}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.LWFREGNO}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.FACTORYLICENCENUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ISRCAPPLICABLE}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.RCNUMBER}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ATTACHMENTNM}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.STATEID}</td>
                  <%--   <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.ISACTIVE}</td> --%>
                    <%-- <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.UPDATEDTM}</td>
                    <td style="border: 1px solid black;">${cmSPRINCIPALEMPLOYER.UPDATEDBY}</td> --%>
                </tr>
                <input type="hidden" id="globalUnitId" value="${cmSPRINCIPALEMPLOYER.UNITID}">
            </c:forEach>
        </tbody>
    </table>
     </div>
                        </form>
               <!--          <div id="paginationButtons">
    <button type="button" id="prevPageBtn">Previous</button>
    <button type="button" id="nextPageBtn">Next</button>
</div> -->
</body>
</html>
