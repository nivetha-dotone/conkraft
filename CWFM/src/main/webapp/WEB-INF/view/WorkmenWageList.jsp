<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
     <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workmen Wage List</title>
    <!--   <link rel="stylesheet" href="styles.css"> -->
      <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
      <script src="resources/js/jquery.min.js"></script>
      <script src="resources/js/commonjs.js"></script>
    <!-- Link to Bootstrap for responsive design -->
   <!--  <link rel="stylesheet" href="resources/bootstrap-4.5.3/css/bootstrap.min.css"> -->
    <!-- <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.3/css/bootstrap.min.css"> -->
    <!-- Add your CSS imports or inline styles here -->
    <script>
    /* function updateWorkmenWages() {
	    var form = document.getElementById("updateForm");
	    var selectedIds = []; // Populate selectedIds array with the IDs you want to send
	    
	    // Example: If selectedIds are obtained from a form field with name 'selectedIds'
	    var selectedIdsInput = document.querySelector('input[name="selectedIds"]');
	    if (selectedIdsInput) {
	        selectedIds = selectedIdsInput.value.split(','); // Assuming selectedIds are comma-separated values
	    }

	    var xhr = new XMLHttpRequest();
	    // Append selectedIds to the form.action URL
	    xhr.open("POST", form.action + '?selectedIds=' + selectedIds.join(','), true);
	    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState === 4) {
	            if (xhr.status === 200) {
	                console.log("Update successful");
	                // Redirect to the specified URL
	                loadWorkmenWageList(); // Adjust the URL as needed
	            } else {
	                console.error("Update failed");
	                console.log(xhr.responseText);
	            }
	        }
	    };
	    // Send an empty string as the request body since selectedIds are now in the URL
	    xhr.send('');
	} */
    </script>
   <%--   <script>
     
     function updateWorkmenWages() {
         var selectedIds = []; // Your logic to get selected IDs goes here
         
         // Example AJAX call
         $.ajax({
             type: "POST",
             url: "<%= request.getContextPath() %>/workmenwage/update",
             data: { selectedIds: selectedIds },
             success: function(data) {
                 console.log("Update successful");
                 // Redirect to the workmen wage list page
                 window.location.href = "<%= request.getContextPath() %>/workmenwage/workmenWageList";
             },
             error: function(xhr, textStatus, errorThrown) {
                 console.error("Update failed");
                 console.log(xhr.responseText);
             }
         });
     }
</script> --%>
</head>
<body>

<div class="page-header">
        Workmen Wages
        <div class="header-buttons">
        <button id="updateButton" onclick="updateWorkmenWages()">Update</button>
           <!--  <button onclick="saveData()">Save</button> -->
          <!--   <button onclick="refreshPage()">Refresh</button> -->
        </div>
    </div>
    <!-- Search Bar -->
   <!--  <form action="searchServlet" method="GET">
        <input type="text" name="query" placeholder="Search...">
        <button type="submit">Search</button>
    </form> -->
<!--  <header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">User Management System</a>
        Add a responsive hamburger menu for mobile
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            Replace the list with a div for menu items
            <div class="navbar-nav ml-auto">
                Add navigation links here
                <a class="nav-item nav-link" href="#">Home</a>
                <a class="nav-item nav-link" href="#">Add User</a>
                <a class="nav-item nav-link" href="#">Edit User</a>
            </div>
        </div>
    </nav>
</header> -->

 <table>
    <tr>
	<td  class="last-child"><label>Principal Employer: </label>
	  <select id="selectedPE" name="value(unitId)"> 
	  <option value="1">DOT1 Solutions Pvt Ltd</option>
	  </select>
	</td>
						<th > <label>Search Date: </label>
						<input id="value(validFrom)" 
										name="value(validFrom)" 
										type=text size=20  value="01/01/2024">
								</input>
										
						</th>
	  <th>
                        <button id="cms.label.search" action="">
                        Search</button>
   </th>
   </tr>
   
  </table>
  <form id="updateForm" action="/CWFM/workmenwage/update" method="POST" >
                         <div class="table-container">
                         <table class="ControlLayout" cellspacing="0" cellpadding="0">
                            <thead>
                                <tr>
                                <td>Select</td>
                                    <td>Workmen Id</td>
                                    <td>Insurance Type</td>
                                    <td>Workmen Type</td>
                                    <td>Basic</td>
                                    <td>DA</td>
                                    <td>HRA</td>
                                     <td>Other Allowances</td>
                                    <td>Uniform Allowances</td>
                                    <td>Washing Allowances</td>
                                    <td>Statutory Bonus</td>
                                    <td>Leave Encashment</td>
                                    <td>EX Serviceman Allowance</td>
                                     <td>Supervisor Allowance</td>
                                    <td>Hardship Allowance</td>
                                    <td>Gunman Allowance</td>
                                    <td>Technical Allowance</td>
                                    <td>Driver Allowance</td>
                                    <td>QRT Allowance</td>
                                    
                                     <td>Valid From</td>
                                    <td>Valid To</td>
                                    <td>PF Cap</td>
                                    <td>Bonus Payout</td>
                                </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${wwageList}" var="emp">
                    <tr>
                        <td><input type="checkbox" name="selectedIds" value="${emp.WORKMEN_ID}"></td>
                        <td>${emp.WORKMEN_ID}</td>
                        <td><input type="text" name="workmenWage.insuranceType" value="${emp.INSURANCE_TYPE}"></td>
                        <td><input type="text" name="workmenWage.workmenType" value="${emp.WKMEN_CATOGERY}"></td>
                        <td><input type="text" name="workmenWage.basic" value="${emp.BASICPAY}"></td>
                        <td><input type="text" name="workmenWage.da" value="${emp.DA}"></td>
                        <td><input type="text" name="workmenWage.hra" value="${emp.HRA}"></td>
                        <td><input type="text" name="workmenWage.otherAllowances" value="${emp.getOther_Allowances()}"></td>
                        <td><input type="text" name="workmenWage.uniformAllowances" value="${emp.getUniform_Allowance()}"></td>
                        <td><input type="text" name="workmenWage.washingAllowances" value="${emp.getWashing_allowance()}"></td>
                        <td><input type="text" name="workmenWage.statutoryBonus" value="${emp.getStatutory_Bonus()}"></td>
                        <td><input type="text" name="workmenWage.leaveEncashment" value="${emp.getLeave_Encashment()}"></td>
                        <td><input type="text" name="workmenWage.exServicemanAllowance" value="${emp.getEX_Serviceman_Allowance()}"></td>
                        <td><input type="text" name="workmenWage.supervisorAllowance" value="${emp.getSupervisor_Allowance()}"></td>
                        <td><input type="text" name="workmenWage.hardshipAllowance" value="${emp.getHardship_Allowance()}"></td>
                        <td><input type="text" name="workmenWage.gunmanAllowance" value="${emp.getGunman_Allowance()}"></td>
                        <td><input type="text" name="workmenWage.technicalAllowance" value="${emp.getTechnical_Allowance()}"></td>
                        <td><input type="text" name="workmenWage.driverAllowance" value="${emp.getDriver_Allowance()}"></td>
                        <td><input type="text" name="workmenWage.qrtAllowance" value="${emp.getQRT_Allowance()}"></td>
                        <td><input type="text" name="workmenWage.validFrom" value="${emp.getVALID_FROM()}"></td>
                        <td><input type="text" name="workmenWage.validTo" value="${emp.getVALID_TO()}"></td>
                        <td><input type="text" name="workmenWage.pfCap" value="${emp.getPF_CAP()}"></td>
                        <td><input type="text" name="workmenWage.bonusPayout" value="${emp.getBonusPayout()}"></td>
                    </tr>
                </c:forEach>
                            </tbody>
                        </table>
                        </div>
                        </form>
                        <!-- Pagination -->
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}">Previous</a>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="page">
            <a href="?page=${page}">${page}</a>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="?page=${currentPage + 1}">Next</a>
        </c:if>
    </div>
   <!--  <script> function updateWorkmenWages() {
	    var form = document.getElementById("updateForm");
	    var selectedIds = [];
	    
	    // Retrieve selectedIds from input fields
	    var selectedIdsInput = form.querySelectorAll('input[name="selectedIds"]:checked');
	    selectedIdsInput.forEach(function(input) {
	        selectedIds.push(input.value);
	    });

	    // Create JSON object with form data
	    var formData = {
	        selectedIds: selectedIds,
	        workmenWage: {}
	    };
	    
	    // Populate workmenWage object with form field values
	    form.querySelectorAll('input[name^="workmenWage."]').forEach(function(input) {
	        var fieldName = input.name.substring(input.name.indexOf(".") + 1);
	        formData.workmenWage[fieldName] = input.value;
	    });
	    
	    // Send JSON data to server
	    var xhr = new XMLHttpRequest();
	    xhr.open("POST", form.action, true);
	    xhr.setRequestHeader("Content-Type", "application/json");
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState === 4) {
	            if (xhr.status === 200) {
	                console.log("Update successful");
	                // Redirect to the specified URL
	                loadWorkmenWageList(); // Adjust the URL as needed
	            } else {
	                console.error("Update failed");
	                console.log(xhr.responseText);
	            }
	        }
	    };
	    xhr.send(JSON.stringify(formData));
	}

	</script> -->
</body>
</html>
