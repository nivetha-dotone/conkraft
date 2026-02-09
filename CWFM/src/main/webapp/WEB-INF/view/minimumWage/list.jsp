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
    
      <script src="resources/js/commonjs.js"></script>
</head>
<body>
<div class="page-header">
        Minimumwage List
        <div class="header-buttons">
      <%--   <button type="submit" onclick="redirectToPEAdd()">Add</button>
        <button type="submit" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button>
        <button type="submit" onclick="redirectToPEView('${cmSPRINCIPALEMPLOYER.UNITID}')">View</button>
         <button type="button" onclick="exportToCSV()">Export</button> --%>
        </div>
        </div>
   <form action="/CWFM/minimumwage/list" method="GET">
        <label for="principalEmployerId">Principal Employer:</label>
        
        <select id="principalEmployerId" name="principalEmployerId">
         <option value="">Select Principal Employer</option>
    <c:forEach items="${principalEmployers}" var="principalEmployer">
        <option value="${principalEmployer.UNITID}" ${principalEmployer.UNITID == selectedPrincipalEmployerId ? 'selected' : ''}>
            ${principalEmployer.NAME}
        </option>
    </c:forEach>
</select>
<%-- <span style="color: red;">${emptyPrincipalEmployerError}</span>

<label for="effectiveDate">Effective Date:</label>
<input type="date" id="effectiveDate" name="effectiveDate" value="${effectiveDate}">
<span style="color: red;">${emptyEffectiveDateError}</span> --%>
        <%-- <select id="principalEmployerId" name="principalEmployerId">
            <!-- Populate dropdown options dynamically using JSTL -->
            <c:forEach items="${principalEmployers}" var="principalEmployer">
                <option value="${principalEmployer.UNITID}">${principalEmployer.NAME}</option>
            </c:forEach>
        </select> --%>

        <label for="effectiveDate">Effective Date:</label>
        <input type="date" id="effectiveDate" name="effectiveDate" required>

 <input type="hidden" id="principalEmployerId" name="principalEmployerId">
    <input type="hidden" id="effectiveDate" name="effectiveDate">
        <button type="submit" onclick="searchWithPEInMinWage('<%= request.getContextPath() %>')">Search</button>
    </form>

   <table border="1">
        <thead>
            <tr>
                <th>Zone</th>
                  <th>State</th>
               <th>Trade</th>
                <th>Skill</th> 
               
                
               
                <th>Basic</th>
                <th>DA per Day</th>
                <th>Other Allowance</th>
                 <th>Effective From</th>
                 <th>Total</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${minimumWages}" var="minimumWage">
                <tr>
                    <td>${minimumWage.zone}</td>
                      <td>${minimumWage.state.stateName}</td>
            <td>${minimumWage.trade.name}</td>
            <td>${minimumWage.skill.skillName}</td>
                  
                   
                   
                    <%-- <td>${minimumWage.tradeName}</td>
                    <td>${minimumWage.skillName}</td> --%>
                    <td>${minimumWage.cmsWage.basic}</td>
                    <td>${minimumWage.cmsWage.da}</td>
                    <td>${minimumWage.cmsWage.allowance}</td>
                      <td>${minimumWage.frmDtm}</td>
                        <td>${minimumWage.cmsWage.basic + minimumWage.cmsWage.da + minimumWage.cmsWage.allowance}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
