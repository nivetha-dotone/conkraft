<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aadhar based Workmen on-boarding List</title>
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/workmen.js"></script>
      <script src="resources/js/commonjs.js"></script>
      <script>
     
    </script>
      <style>
  /*  #principalEmployerTable {
    border-collapse: collapse;
    width: 100%;
}

#principalEmployerTable th,
#principalEmployerTable td {
    border: 1px solid black;
    padding: 8px; 
}

/* Header styles */
/* #principalEmployerTable th {
    background-color: lightgreen; 
} */

/* Row styles */
/* .row-odd {
    background-color: lightgray; 
}

.row-even {
    background-color: lightblue; 
}  */*/
      </style>
</head>
<body>
<div class="page-header">
       Aadhar based Workmen on-boarding List
        <div class="header-buttons">
        <button type="submit" onclick="redirectToWorkmenAadharOBAdd()">Add</button>
        <%-- <button type="submit" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button>  --%>
        <button type="submit" onclick="redirectToWOView()">View</button>
         <button type="button" onclick="woListExportToCSV()">Export</button>
        </div>
        </div>
    <%-- <form id="searchForm">
    <label for="principalEmployerId">Principal Employer :</label>
        
        <select id="principalEmployerId" name="principalEmployerId">
         <option value="">Select Principal Employer</option>
    <c:forEach items="${principalEmployers}" var="principalEmployer">
        <option value="${principalEmployer.UNITID}" ${principalEmployer.UNITID == selectedPrincipalEmployerId ? 'selected' : ''}>
            ${principalEmployer.NAME}
        </option>
    </c:forEach>
</select>

 <label for="contractorId">Contractor :</label>
        
        <select id="contractorId" name="contractorId">
         <option value="">Select Contractor</option>
    <c:forEach items="${contractors}" var="contractor">
        <option value="${contractor.contractorId}" ${contractor.contractorId == selectedContractorId ? 'selected' : ''}>
            ${contractor.name}
        </option>
    </c:forEach>
</select>
        <button type="submit" onclick="searchWithPEContractorInWorkmenAadharList('<%= request.getContextPath() %>')">Search</button>
</form> --%>
     <form id="updateForm" action="/CWFM/workorders/update" method="POST" >
                         <div class="table-container">
    <table id="principalEmployerTable"  cellspacing="0" cellpadding="0">
        <thead>
<tr>
                    <td style="border: 1px solid black;">
                        <input type="checkbox" id="selectAllAadharWorkmenCheckbox" onchange="toggleSelectAllAadharWorkmen()">
                    </td> 
                    <!-- Add more table headers for each column -->
                    <th class="header-text"  onclick="sortTable(1)">Person Number<span id="sortIndicatorName" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text"  onclick="sortTable(2)">First Name<span id="sortIndicatorAddress" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text"  onclick="sortTable(3)">Last Name<span id="sortIndicatorManagerName" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text"  onclick="sortTable(4)">Gender<span id="sortIndicatorManagerAddr" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text"  onclick="sortTable(5)">Date of Birth<span id="sortIndicatorBusinessType" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(6)">Aadhar Number<span id="sortIndicatorMaxWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(7)">Contractor Name<span id="sortIndicatorMaxCntrWorkmen" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(8)">Vendor Code<span id="sortIndicatorBocwApp" class="sort-indicator sort-asc">&#x25B2;</span></th>
                    <th class="header-text"  onclick="sortTable(9)">Unit Name<span id="sortIndicatorIsmApp" class="sort-indicator sort-asc">&#x25B2;</span></th> 
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${persons}" var="wo">
               <tr>
                 <td style="border: 1px solid black;"><input type="checkbox" name="selectedWOs" value="${wo.employeeId}"></td>
                    <td style="border: 1px solid black;">${wo.employeeCode}</td>
                    <td style="border: 1px solid black;">${wo.firstName}</td>
                    <td style="border: 1px solid black;">${wo.lastName}</td>
                    <td style="border: 1px solid black;">${wo.gender.getGmdescription()}</td>
                    <td style="border: 1px solid black;">${wo.dateOfBirth}</td>
                     <td style="border: 1px solid black;">${wo.aadharNumber}</td>
                     
                    <td style="border: 1px solid black;">${wo.getContractor().name}</td>
                    <td style="border: 1px solid black;">${wo.getContractor().code}</td>
                    <td style="border: 1px solid black;">${wo.getUnit().NAME}</td> 
                    <%--<td style="border: 1px solid black;">${principalEmployer.NAME}</td>
                    <td style="border: 1px solid black;">${wo.status}</td> --%>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
                        </form>
                         </div>
</body>
</html>
