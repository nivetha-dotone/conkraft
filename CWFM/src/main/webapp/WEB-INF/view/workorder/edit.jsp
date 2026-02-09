<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit CMSPRINCIPALEMPLOYER</title>
</head>
<body>
    <h1>Edit CMSPRINCIPALEMPLOYER</h1>
    <form action="update" method="post">
        <input type="hidden" id="unitId" name="unitId" value="${cmSPRINCIPALEMPLOYER.UNITID}">
        <label for="name">Unit Name:</label>
        <input type="text" id="name" name="name" value="${cmSPRINCIPALEMPLOYER.NAME}" required><br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${cmSPRINCIPALEMPLOYER.ADDRESS}" required><br>
        <label for="managerName">Manager Name:</label>
        <input type="text" id="managerName" name="managerName" value="${cmSPRINCIPALEMPLOYER.MANAGERNAME}" required><br>
        <label for="managerAddress">Manager Address:</label>
        <input type="text" id="managerAddress" name="managerAddress" value="${cmSPRINCIPALEMPLOYER.MANAGERADDRS}" required><br>
        <label for="businessType">Type of Business:</label>
        <input type="text" id="businessType" name="businessType" value="${cmSPRINCIPALEMPLOYER.BUSINESSTYPE}" required><br>
        <label for="maxWorkmen">Maximum Number of Workmen:</label>
        <input type="text" id="maxWorkmen" name="maxWorkmen" value="${cmSPRINCIPALEMPLOYER.MAXWORKMEN}" required><br>
        <!-- Add more input fields for other fields if needed -->
        <button type="submit">Update</button>
    </form>
</body>
</html> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <script src="resources/js/commonjs.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/jquery.min.js"></script>
<style>
body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            overflow: auto; /* Remove scroll bar */
        }
     #principalEmployerContent {
        padding: 20px;
        box-sizing: border-box;
        /* overflow-y: auto; */
      height: calc(85vh - 20px);
         /* height: calc(100vh - 20px);  */
    } 
th label {
    text-align: left;
    display: block;
    font-weight: normal; /* Remove bold effect */
}
     tr {
        margin-bottom: 10px; /* Adjust the value as needed */
    }

    

</style>
<!-- <script>
function saveData() { alert(1);
    // Get the ID of the principal employer from the URL
    var url = window.location.href;
    var lastSlashIndex = url.lastIndexOf('/');
    var id = url.substring(lastSlashIndex + 1);

    // Redirect to the controller with the ID
    window.location.href = "/CWFM/principalEmployer/edit/" + id;
}
</script> -->
<script>  
var contextPath = '<%= request.getContextPath() %>';
/* var id = extractIdFromURL(); // Assuming you have a function to extract the id
function extractIdFromURL() {alert(1)
    var url = window.location.href;
    var lastSlashIndex = url.lastIndexOf('/');
    var id = url.substring(lastSlashIndex + 1);
    return id; alert(id);
} */


</script>
   </head>
<body>
 <div class="page-header">
        Principal Employer Edit Page
        <div class="header-buttons">
            <button type="submit" onclick="editPEData(contextPath, '${principalEmployer.UNITID}')">Save</button>
        </div>
    </div>
<div id="principalEmployerContent">
<f:form id="editForm" action="/CWFM/principalEmployer/edit/${principalEmployer.UNITID}" method="post" modelAttribute="principalEmployer">
<div id="errorContainer">
<%--       <c:out value="${errorsLength}" /> --%>
      <c:if test="${not empty errors}">
      <p><span class="required-field">*</span> Indicates Required fields.</p>
        <div class="error-message">
             <f:errors path="*" cssClass="error" element="div" />
        </div> 
    </c:if>
    </div>
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Unit Name</label></th>
                            <td><!--   <div style="padding-right: 15px;"> -->
                            <f:input type="text" path="NAME"  style="height: 20px;"  size="30" maxlength="30" readonly="true" />
                          <!--   </div> -->
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>PE Inactive</label></th>
                            <td>
                         <input type="checkbox" id="ISACTIVE" name="ISACTIVE" ${principalEmployer.ISACTIVE ? 'checked' : ''}/>
                       <input type="hidden" name="ISACTIVE" value="false"/>
 <%--                        <input type="hidden" name="ISACTIVE_HIDDEN" value="${principalEmployer.ISACTIVE ? 'true' : 'false'}">
<input type="checkbox" id="ISACTIVE_CHECKBOX" name="ISACTIVE_CHECKBOX" ${principalEmployer.ISACTIVE ? 'checked' : ''} onclick="updateCheckboxValue('ISACTIVE_CHECKBOX', 'ISACTIVE_HIDDEN')">
 --%></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Unit Code</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <f:input type="text" path="CODE" value="${principalEmployer.CODE}" style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/>
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>Organization</label></th>
                            <td><f:input type="text" path="ORGANIZATION"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Address</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <f:input type="text" path="ADDRESS"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/>
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>State</label></th>
                             <td>
   <select name="selectedState"  >
    <c:forEach var="state" items="${states}">
        <option value="${state.stateId}" ${state.stateId == principalEmployer.STATEID ? 'selected' : ''}>${state.stateName}</option>
    </c:forEach>
</select>
</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Manager Name</label></th>
                            <td><f:input type="text" path="MANAGERNAME"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Manager Address</label></th>
                            <td><f:input type="text" path="MANAGERADDRS"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Type of Business</label></th>
                            <td><f:input type="text" path="BUSINESSTYPE"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Maximum Number of Workmen</label></th>
                            <td><f:input type="text" path="MAXWORKMEN"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Maximun Number of Contract Workmen</label></th>
                            <td><f:input type="text" path="MAXCNTRWORKMEN"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                            <th><label class="custom-label">Current count of Contract Workmen</label></th>
                            <td><f:input type="text" path="" value="0" style="height: 20px;"  size="30" maxlength="30" disabled="true" autocomplete="off"/></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>BOCWAct Applicability</label></th>
                           <td>
                         <input type="checkbox" id="BOCWAPPLICABILITY" name="BOCWAPPLICABILITY" ${principalEmployer.BOCWAPPLICABILITY ? 'checked' : ''}/>
                       <input type="hidden" name="BOCWAPPLICABILITY" value="false"/>
   <%--                      <input type="hidden" name="BOCWAPPLICABILITY_HIDDEN" value="${principalEmployer.BOCWAPPLICABILITY ? 'true' : 'false'}">
<input type="checkbox" id="BOCWAPPLICABILITY_CHECKBOX" name="BOCWAPPLICABILITY_CHECKBOX" ${principalEmployer.BOCWAPPLICABILITY ? 'checked' : ''} onclick="updateCheckboxValue('BOCWAPPLICABILITY_CHECKBOX', 'BOCWAPPLICABILITY_HIDDEN')">
 --%>
</td>
                       <th><label class="custom-label"><span class="required-field">*</span>ISMW Act Applicability</label></th>
                        <td>
                         <input type="checkbox" id="ISMWAPPLICABILITY" name="ISMWAPPLICABILITY" ${principalEmployer.ISMWAPPLICABILITY ? 'checked' : ''}/>
                       <input type="hidden" name="ISMWAPPLICABILITY" value="false"/>
 <%--                        <input type="hidden" name="ISMWAPPLICABILITY_HIDDEN" value="${principalEmployer.ISMWAPPLICABILITY ? 'true' : 'false'}">
<input type="checkbox" id="ISMWAPPLICABILITY_CHECKBOX" name="ISMWAPPLICABILITY_CHECKBOX" ${principalEmployer.ISMWAPPLICABILITY ? 'checked' : ''} onclick="updateCheckboxValue('ISMWAPPLICABILITY_CHECKBOX', 'ISMWAPPLICABILITY_HIDDEN')">
 --%></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>License Number</label></th>
                            <td><f:input type="text" path="LICENSENUMBER"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                        <th><label class="custom-label"><span class="required-field">*</span>PF Code</label></th>
                            <td><f:input type="text" path="PFCODE"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                        
                        </tr>
                        <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>WC Number</label></th>
                            <td><f:input type="text" path="WCNUMBER"  style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>ESIC Number</label></th>
                            <td><f:input type="text" path="ESICNUMBER"  style="height: 20px;"  size="30" maxlength="30"autocomplete="off" /></td>
                        </tr>
                        
                         <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>PT Registration No.</label></th>
                            <td><f:input type="text" path="PTREGNO" value="${principalEmployer.PTREGNO}" style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>LWF Registration No.</label></th>
                            <td><f:input type="text" path="LWFREGNO" value="${principalEmployer.LWFREGNO}" style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                        </tr>
                        
                         <tr>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>Factory Licence No</label></th>
                            <td><f:input type="text" path="FACTORYLICENCENUMBER" value="${principalEmployer.FACTORYLICENCENUMBER}" style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                       <th><label class="custom-label"><span class="required-field">*</span>IS RC Applicable ?</label></th>
                         <td>
                       <input type="checkbox" id="ISRCAPPLICABLE" name="ISRCAPPLICABLE" ${principalEmployer.ISRCAPPLICABLE ? 'checked' : ''}/>
                       <input type="hidden" name="ISRCAPPLICABLE" value="false"/>
</td>
                        
                        </tr>
                         <tr>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>RC Number</label></th>
                            <td><f:input type="text" path="RCNUMBER" value="${principalEmployer.RCNUMBER}" style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>Attachment Name</label></th>
                            <td><f:input type="text" path="ATTACHMENTNM" value="${principalEmployer.ATTACHMENTNM}" style="height: 20px;"  size="30" maxlength="30" autocomplete="off"/></td>
                        
                        </tr>
        </tbody>
    </table>
    </f:form>
</div>

