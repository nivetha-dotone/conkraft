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
      
      <script src="resources/js/jquery.min.js"></script>
      <script src="resources/js/commonjs.js"></script>
       <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
    <!-- Link to Bootstrap for responsive design -->
   <!--  <link rel="stylesheet" href="resources/bootstrap-4.5.3/css/bootstrap.min.css"> -->
    <!-- <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.3/css/bootstrap.min.css"> -->
    <!-- Add your CSS imports or inline styles here -->
    <script>
    var contextPath = '<%= request.getContextPath() %>';
    </script>
</head>
<body>

<div class="page-header">
        Workmen Wages
        <div class="header-buttons">
        <button type="submit" id="updateButton" onclick="updateWorkmenWages(contextPath)">Update</button>
           <!--  <button onclick="saveData()">Save</button> -->
          <!--   <button onclick="refreshPage()">Refresh</button> -->
        </div>
    </div>

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
  <!-- Check for update success message -->
<%-- Check for update success message --%>
<%-- <% if (request.getAttribute("updateSuccess") != null) { %>
    <div class="alert alert-success" role="alert">
        Workmen wages updated successfully.
    </div>
<% } %>

Check for update failure message
<% if (request.getAttribute("updateFailed") != null) { %>
    <div class="alert alert-danger" role="alert">
        Failed to update workmen wages. Please try again.
    </div>
<% } %> --%>
  <form id="updatewageForm" action="/CWFM/workmenwage/update" method="POST" >
                         <div class="table-container">
                         
                         <table id="principalEmployerTable"  cellspacing="0" cellpadding="0">
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
                            <%--   <c:forEach items="${wwageList}" var="emp" varStatus="status"> --%>
                               <c:forEach items="${wwageList}" var="emp" varStatus="status">
                    <tr>
                    <td><input type="checkbox" name="selectedIds" value="${emp.WORKMEN_ID}"></td>
        <td>${emp.WORKMEN_ID}</td>
        <td><input type="text" id="insuranceType_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].insuranceType" value="${emp.INSURANCE_TYPE}"></td>
      <%--   <td><input type="text" id="wageType_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].wageType" value="${emp.WAGE_TYPE}"></td> --%>
        <td><input type="text" id="workmenType_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].workmenType" value="${emp.WKMEN_CATOGERY}"></td>
        <td><input type="text" id="basic_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].basic" value="${emp.BASICPAY}"></td>
        <td><input type="text" id="da_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].da" value="${emp.DA}"></td>
        <td><input type="text" id="hra_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].hra" value="${emp.HRA}"></td>
        <td><input type="text" id="otherAllowances_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].otherAllowances" value="${emp.getOther_Allowances()}"></td>
        <td><input type="text" id="uniformAllowances_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].uniformAllowances" value="${emp.getUniform_Allowance()}"></td>
        <td><input type="text" id="washingAllowances_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].washingAllowances" value="${emp.getWashing_allowance()}"></td>
        <td><input type="text" id="statutoryBonus_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].statutoryBonus" value="${emp.getStatutory_Bonus()}"></td>
        <td><input type="text" id="leaveEncashment_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].leaveEncashment" value="${emp.getLeave_Encashment()}"></td>
        <td><input type="text" id="exServicemanAllowance_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].exServicemanAllowance" value="${emp.getEX_Serviceman_Allowance()}"></td>
        <td><input type="text" id="supervisorAllowance_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].supervisorAllowance" value="${emp.getSupervisor_Allowance()}"></td>
        <td><input type="text" id="hardshipAllowance_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].hardshipAllowance" value="${emp.getHardship_Allowance()}"></td>
        <td><input type="text" id="gunmanAllowance_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].gunmanAllowance" value="${emp.getGunman_Allowance()}"></td>
        <td><input type="text" id="technicalAllowance_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].technicalAllowance" value="${emp.getTechnical_Allowance()}"></td>
        <td><input type="text" id="driverAllowance_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].driverAllowance" value="${emp.getDriver_Allowance()}"></td>
        <td><input type="text" id="qrtAllowance_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].qrtAllowance" value="${emp.getQRT_Allowance()}"></td>
        <td><input type="text" id="validFrom_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].validFrom" value="${emp.getVALID_FROM()}"></td>
        <td><input type="text" id="validTo_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].validTo" value="${emp.getVALID_TO()}"></td>
        <td><input type="text" id="pfCap_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].pfCap" value="${emp.getPF_CAP()}"></td>
        <td><input type="text" id="bonusPayout_${emp.WORKMEN_ID}" name="workmenWages[${status.index}].bonusPayout" value="${emp.getBonusPayout()}"></td>
    
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
</body>
</html>
