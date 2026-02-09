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
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <script src="resources/js/commonjs.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/jquery.min.js"></script>
<style>
    body {
        margin: 0;
        overflow-x: hidden;
    }

     #principalEmployerContent {
        padding: 20px;
        box-sizing: border-box;
        overflow-y: auto;
        height: calc(100vh - 20px);
    } 
th label {
    text-align: left;
    display: block;
    font-weight: normal; /* Remove bold effect */
}
     tr {
        margin-bottom: 10px; /* Adjust the value as needed */
    }

    /* Add spacing between <td> elements */
    /* td {
        padding-bottom: 10px; /* Adjust the value as needed */
    } */
    .custom-label {
    font-family: 'Your-Desired-Font', sans-serif; /* Replace 'Your-Desired-Font' with the actual font name */
    text-align: left;
    display: block;
    /* Add any other styling properties you need */
}

.custom-input-container {
    padding-left: 10px;
}

.custom-input {
    height: 40px; /* Adjust the height as needed */
    font-family: 'Your-Desired-Font', sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}

.custom-input-checkbox {
    height: 20px; /* Adjust the height as needed */
    font-family: 'Your-Desired-Font', sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}
.required-field {
    color: red; /* Change this to your desired color */
    margin-right: 4px; /* Add space to the right of the * */
}
        .page-header {
            background-color: #005151;
            color: #fff;
            padding: 10px;
            text-align: center;
            font-size: 20px;
        }

        .header-buttons {
            float: right;
          /*   margin-top: -40px; */
            margin-right: 10px;
        }
</style>
<script>  
var contextPath = '<%= request.getContextPath() %>';


</script>
   </head>
<body>
 <div class="page-header">
        Principal Employer View Page
        <div class="header-buttons">
            <button type="submit" onclick="goBackToPEList(contextPath)">Back</button>
        </div>
    </div>
<div id="principalEmployerContent">
<form id="editForm" action="/CWFM/principalEmployer/view/${principalEmployer.UNITID}" method="post">
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Unit Name</label></th>
                            <td><!--   <div style="padding-right: 15px;"> -->
                            <input type="text" name="NAME" value="${principalEmployer.NAME}" style="height: 20px;"  size="30" maxlength="30" readonly="true" />
                          <!--   </div> -->
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>PE Inactive</label></th>
                           <td>
    <c:choose>
        <c:when test="${principalEmployer.ISACTIVE}">
    <input type="checkbox" name="ISACTIVE" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
</c:when>
        <c:otherwise>
            <input type="checkbox" name="ISACTIVE" value="0" style="height: 20px;" onclick="return false;"/>
        </c:otherwise>
    </c:choose>
</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Unit Code</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="CODE" value="${principalEmployer.CODE}" style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>Organization</label></th>
                            <td><input type="text" name="ORGANIZATION" value="${principalEmployer.ORGANIZATION}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Address</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="ADDRESS" value="${principalEmployer.ADDRESS}" style="height: 20px;"  size="30" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>State</label></th>
                            <td><input type="text" name="STATEID" value="${principalEmployer.STATEID}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Manager Name</label></th>
                            <td><input type="text" name="MANAGERNAME" value="${principalEmployer.MANAGERNAME}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Manager Address</label></th>
                            <td><input type="text" name="MANAGERADDRS" value="${principalEmployer.MANAGERADDRS}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Type of Business</label></th>
                            <td><input type="text" name="BUSINESSTYPE" value="${principalEmployer.BUSINESSTYPE}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Maximum Number of Workmen</label></th>
                            <td><input type="text" name="MAXWORKMEN" value="${principalEmployer.MAXWORKMEN}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Maximun Number of Contract Workmen</label></th>
                            <td><input type="text" name="MAXCNTRWORKMEN" value="${principalEmployer.MAXCNTRWORKMEN}" style="height: 20px;"  size="30" maxlength="30" /></td>
                            <th><label class="custom-label">Current count of Contract Workmen</label></th>
                            <td><input type="text" name="" value="0" style="height: 20px;"  size="30" maxlength="30" disabled="true" /></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>BOCWAct Applicability</label></th>
                            <td>
    <c:choose>
        <c:when test="${principalEmployer.BOCWAPPLICABILITY}">
    <input type="checkbox" name="BOCWAPPLICABILITY" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
</c:when>
        <c:otherwise>
            <input type="checkbox" name="BOCWAPPLICABILITY" value="0" style="height: 20px;" onclick="return false;"/>
        </c:otherwise>
    </c:choose>
</td>
                            <!-- <td><input type="checkbox" name="" value="" checked="true" style="height: 20px;"  /></td> -->
                       <th><label class="custom-label"><span class="required-field">*</span>ISMW Act Applicability</label></th>
                        <td>
    <c:choose>
        <c:when test="${principalEmployer.ISMWAPPLICABILITY}">
    <input type="checkbox" name="ISMWAPPLICABILITY" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
</c:when>
        <c:otherwise>
            <input type="checkbox" name="ISMWAPPLICABILITY" value="0" style="height: 20px;" onclick="return false;"/>
        </c:otherwise>
    </c:choose>
</td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>License Number</label></th>
                            <td><input type="text" name="LICENSENUMBER" value="${principalEmployer.LICENSENUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>PF Code</label></th>
                            <td><input type="text" name="PFCODE" value="${principalEmployer.PFCODE}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>WC Number</label></th>
                            <td><input type="text" name="WCNUMBER" value="${principalEmployer.WCNUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>ESIC Number</label></th>
                            <td><input type="text" name="ESICNUMBER" value="${principalEmployer.ESICNUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        </tr>
                        
                         <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>PT Registration No.</label></th>
                            <td><input type="text" name="PTREGNO" value="${principalEmployer.PTREGNO}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>LWF Registration No.</label></th>
                            <td><input type="text" name="LWFREGNO" value="${principalEmployer.LWFREGNO}" style="height: 20px;"  size="30" maxlength="30" /></td>
                        </tr>
                        
                         <tr>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>Factory Licence No</label></th>
                            <td><input type="text" name="FACTORYLICENCENUMBER" value="${principalEmployer.FACTORYLICENCENUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                       <th><label class="custom-label"><span class="required-field">*</span>IS RC Applicable ?</label></th>
                           <td>
    <c:choose>
        <c:when test="${principalEmployer.ISRCAPPLICABLE}">
    <input type="checkbox" name="ISRCAPPLICABLE" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
</c:when>
        <c:otherwise>
            <input type="checkbox" name="ISRCAPPLICABLE" value="0" style="height: 20px;" onclick="return false;"/>
        </c:otherwise>
    </c:choose>
</td>
                        
                        </tr>
                         <tr>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>RC Number</label></th>
                            <td><input type="text" name="RCNUMBER" value="${principalEmployer.RCNUMBER}" style="height: 20px;"  size="30" maxlength="30" /></td>
                         
                            <th><label class="custom-label"><span class="required-field">*</span>Attachment Name</label></th>
                            <td>
                        <c:if test="${not empty principalEmployer.ATTACHMENTNM}">
    <a href="#" onclick="viewFile('/CWFM/principalEmployer/viewFile?unitCode=${principalEmployer.CODE}&fileName=${principalEmployer.ATTACHMENTNM}')">${principalEmployer.ATTACHMENTNM}</a>
</c:if>
                            <%-- <input type="text" name="ATTACHMENTNM" value="${principalEmployer.ATTACHMENTNM}" style="height: 20px;"  size="30" maxlength="30" /> --%>
                            </td>
                        
                        </tr>
        </tbody>
    </table>
    </form>
</div>

