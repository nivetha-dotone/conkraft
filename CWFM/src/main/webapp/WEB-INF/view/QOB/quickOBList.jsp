<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aadhar based Workmen on-boarding List</title>
    
       <!-- assets -->
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> 
<script src="resources/bootstrap/js/bootstrap.bundle.min.js"></script> 
<!-- /assets -->
   
  
      <script src="resources/js/cms/wokmen.js"></script>
    <script src="resources/js/commonjs.js"></script>
      <style>
  thead th {
            font-size: 0.8em;
            height: 15px;
        }
      </style>
</head>
<body>

<div class="container mt-3">


		<div class="header-buttons">
        <button type="submit" onclick="redirectToWorkmenQuickOBAdd()" class="btn btn-sm btn-success">Add</button>
        <%-- <button type="submit" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button>  --%>
        <button type="submit" onclick="redirectToWOView()" class="btn btn-sm btn-success">View</button>
         <button type="button" onclick="woListExportToCSV()" class="btn btn-sm btn-success">Export</button>
        </div>
        <form id="searchForm">
            <input type="text" id="searchInput" name="searchQuery" placeholder="Search..." autocomplete="off">
             <button type="button" onclick="searchPrincipalEmployers('<%= request.getContextPath() %>')" class="btn btn-sm btn-success">Search</button> 
        </form>
      
     <form id="updateForm" action="/CWFM/workorders/update" method="POST" >
              <div class="table-responsive">             
    <table id="principalEmployerTable"  class="table table-hover">
        <thead class="table-success">
<tr>
                    <td >
                        <input type="checkbox" id="selectAllAadharWorkmenCheckbox" onchange="toggleSelectAllAadharWorkmen()">
                    </td> 
                    <!-- Add more table headers for each column -->
                    <th   >Person Number</th>
					<th   >First Name</th>
					<th  >Last Name</th>
					<th   >Gender</th>
					<th   >Date of Birth</th>
                    <th  >Aadhar Number</th>
                    <th   >Contractor Name</th>
                    <th   >Vendor Code</th>
                    <th  >Unit Name</th> 
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${persons}" var="wo">
              <tr>
                 <td ><input type="checkbox" name="selectedWOs" ></td>
                    <td >${wo.employeeCode}</td>
                    <td >${wo.firstName}</td>
                    <td >${wo.lastName}</td>
                    <td >${wo.gender.getGmdescription()}</td>
                    <td >${wo.dateOfBirth}</td>
                     <td >${wo.aadharNumber}</td>
                     
                    <td >${wo.getContractor().name}</td>
                    <td >${wo.getContractor().code}</td>
                    <td >${wo.getUnit().NAME}</td> 
                    <%--<td >${principalEmployer.NAME}</td>
                    <td >${wo.status}</td> --%>
                </tr>
            </c:forEach>
        </tbody>
    </table>
      </div> 
                        </form>
                      
</div>
 

</body>
</html>
