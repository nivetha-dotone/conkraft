<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contractor Renewal List</title>
    <script src="resources/js/cms/contRenewal.js"></script>


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
        display: flex-start;
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
   .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}
</style>
</head>
<body>
	<div class="page-header">
	     <div>
   <label for="principalEmployerId" style=" color: darkcyan;"   >Principal Employer:</label>
         <select id="principalEmployerId" name="principalEmployerId" style="color:gray;padding:3px;">
         <option value="">Select Principal Employer</option>
    <c:forEach items="${principalEmployers}" var="pe">
       <%--  <option value="${principalEmployer.unitId}" ${principalEmployer.unitId == selectedPrincipalEmployerId ? 'selected' : ''}>
            ${principalEmployer.name}
        </option> --%>
        <option value="${pe.id}">${pe.description}</option>
        <%-- <option value="${pe.id}"  <c:if test="${principalEmployers.size() == 1}">selected</c:if>>${pe.description}</option> --%>
    </c:forEach>
</select>
<input type="hidden" id="principalEmployerId" name="principalEmployerId">

<label for="departmentId" style=" color: darkcyan;"   >Contractor:</label>
         <select id="deptId" name="deptId" style="color:gray;padding:3px;">
         <option value="">Select Contractor</option>
    <c:forEach items="${Contr}" var="c">
       <%--  <option value="${principalEmployer.unitId}" ${principalEmployer.unitId == selectedPrincipalEmployerId ? 'selected' : ''}>
            ${principalEmployer.name}
        </option> --%>
        <option value="${c.id}" <c:if test="${Contr.size() == 1}">selected</c:if>>${c.description}</option>
    </c:forEach>
</select>
<input type="hidden" id="deptId" name="deptId">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding"  onclick="searchContRenewBasedOnPE()">Search</button>
  </div>
		  <div class="page-header-buttons">
    <c:if test="${UserPermission.addRights eq 1 }">
         <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToContractorRenew()">Add</button> 
    </c:if>
   <%--  <c:if test="${UserPermission.editRights eq 1 }">
         <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button> 
     </c:if> --%>
     <c:if test="${UserPermission.viewRights eq 1 }">

        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToContractorRenewView()">View</button>



     </c:if>
       <c:if test="${UserPermission.exportRights eq 1 }">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="exportToCSVFormat()">Export</button>
    	</c:if>
    </div>
	</div>
	<div class="table-container">
		<table id="contractorlisttable" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<td  ><input type="checkbox"
						id="selectAllCheckbox" onchange="toggleSelectAll()">
					</td>
					<!-- Add more table headers for each column -->
					<th class="header-text" onclick="sortTable(1)"><spring:message code="label.contractorRegistrationId"/><span
						id="sortTransactionid" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<%-- <th class="header-text" onclick="sortTable(2)"><spring:message code="label.unitCode"/><span
						id="sortUnitcode" class="sort-indicator sort-asc">&#x25B2;</span></th> --%>
					<th class="header-text" onclick="sortTable(3)"><spring:message code="label.vendorCode"/> <span
						id="sortVendorcode" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text" onclick="sortTable(4)"><spring:message code="label.contractorName"/><span
						id="sortContractorname" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text" onclick="sortTable(5)"><spring:message code="label.status"/><span
						id="sortStatus" class="sort-indicator sort-asc">&#x25B2;</span></th>
					<th class="header-text" onclick="sortTable(6)"><spring:message code="label.requestType"/><span
						id="sortRequesttype" class="sort-indicator sort-asc">&#x25B2;</span></th>
				</tr>
			</thead>
			<tbody>
				<%-- <c:forEach items="${contractorlist}" var="wo">
					<tr>
						<td ><input type="checkbox"
							name="selectedWOs" value="${wo.contractorregId}"></td>
						<td  >${wo.contractorregId}</td>
						<td  >${wo.principalEmployer}</td>
						<td  >${wo.vendorCode}</td>
						<td  >${wo.contractorName}</td>
						<td  >${wo.status}</td>
						<td  >${wo.requestType}</td>
						<td  >${principalEmployer.NAME}</td>
                    <td  >${${wo.requestType}}</td>
					</tr>
				</c:forEach> --%>
			</tbody>
		</table>
		</div>
		<c:if test="${principalEmployers.size() == 1 && Contr.size() == 1}">
    <script>
        setTimeout(function () {
        	searchContRenewBasedOnPE();
        }, 10); // Delay ensures DOM is rendered after innerHTML
    </script>
    </c:if>
</body>
</html>