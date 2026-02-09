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
    td {
        padding-bottom: 10px; /* Adjust the value as needed */
    }
    .custom-label {
    font-family: 'Your-Desired-Font', sans-serif; /* Replace 'Your-Desired-Font' with the actual font name */
    text-align: left;
    display: block;
    /* Add any other styling properties you need */
}

.custom-input-container {
    padding-left: 10px;
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
        table {
        width: 100%;
        border-collapse: collapse;
        table-layout: fixed;
    }
    th, td {
        padding: 5px; /* Adjust padding as needed */
        text-align: left; /* Adjust text alignment as needed */
        /* border: 1px solid #ddd; */ /* Add borders for better visualization */
    }
</style>
 <style>
        body {
            margin: 0;
            overflow-x: hidden;
        }

        .section {
            margin-bottom: 20px;
        }

        .section-header {
            cursor: pointer;
            background-color: #005151;
            padding: 10px;
             color: white;
        }

        .section-content {
            display: block;
            padding: 10px;
        }
    </style>
    
    <script>
    $(document).ready(function(){
        $(".section-header").click(function(){
            $(this).next(".section-content").slideToggle();
        });

        // Collapse all sections except the first one on page load
        $(".section-content").not(":first").hide();
    });
    </script>
<script>  
var contextPath = '<%= request.getContextPath() %>';
/* var id = extractIdFromURL(); // Assuming you have a function to extract the id
function extractIdFromURL() {alert(1)
    var url = window.location.href;
    var lastSlashIndex = url.lastIndexOf('/');
    var id = url.substring(lastSlashIndex + 1);
    return id; alert(id);
} */
/* onsubmit="return validateForm()" */

</script>
   </head>
<body>
 <div class="page-header">
        Aadhar based Workmen on-boarding
        <div class="header-buttons">
            <button type="submit" onclick="submitForm()">Save</button>
        </div>
    </div>
    <div id="errorContainer"></div>
<div id="principalEmployerContent">
<f:form id="addAadharOBForm" action="/CWFM/contractworkmen/addAadharOB" modelAttribute="workmenbyAadhar" method="post"  >
     <div id="errorContainer">
<%--       <c:out value="${errorsLength}" /> --%>
      <c:if test="${not empty errors}">
      <p><span class="required-field">*</span> Indicates Required fields.</p>
        <div class="error-message">
             <f:errors path="*" cssClass="error" element="div" />
        </div> 
    </c:if>
    </div>
   
    
<div id="contractWorkmenContent">
 <div class="section">
    <div class="section-header" onclick="toggleSection('section1')">Basic Data:</div>
    <div id="section1Content" class="section-content">
       <table cellspacing="0" cellpadding="0">
		<tbody>
    <tr>
        <th><label class="custom-label"><span class="required-field">*</span>Enter Aadhar</label></th>
        <td>
            <div>
                <input id="aadharNumber" name="aadharNumber"  style="height: 20px;"  type="text" value="" size="18" maxlength="12" autocomplete="off">
                <button id="sendOTPButton">Send OTP</button>
            </div>
        </td>
          <th><label class="custom-label"><span class="required-field">*</span>OTP</label></th>
<td>
    <div>
        <input id="otp"  style="height: 20px;"  type="text" value="" size="10" maxlength="10" autocomplete="off">
        <button id="sendOTPButton">Validate</button>
    </div>
</td>
</tr>
<tr>
     
    <td colspan="3">
        <!-- Second column content -->
    </td>
    <td rowspan="5">
        <img id="imageId" width="150" height="150" onclick="openFilePicker();">
        <input type="file" id="fileInput" style="display: none;" accept="image/*" onchange="handleFileSelect(event)">
    </td>
</tr>
        <!-- <td rowspan="8" colspan="1">
            <img id="imageId" width="150" height="150" onclick="capturePhoto();" onchange="setDataChanged();">
        </td>
    </tr> -->
    <tr>
        <th><label class="custom-label"><span class="required-field">*</span>First Name</label></th>
        <td>
            <div style="padding-right: 15px;">
                <input id="firstName" name="firstName" style="width: 100%;height: 20px;"  type="text" value="" size="30" maxlength="30" autocomplete="off">
            </div>
        </td>
    </tr>
    <tr>
        <th><label class="custom-label"><span class="required-field">*</span>Last Name</label></th>
        <td>
         <div style="padding-right: 15px;">
            <input id="lastName" name="lastName" style="width: 100%;height: 20px;"  type="text" value="" size="30" maxlength="30" autocomplete="off">
        </div>
        </td>
    </tr>
    <tr>
        <th><label class="custom-label"><span class="required-field">*</span>Date of Birth</label></th>
        <td>
            <div style="padding-right: 15px;">
                <input id="dateOfBirth" name="dateOfBirth" style="width: 100%;height: 20px;"  type="text" value="" size="30" maxlength="30" autocomplete="off">
            </div>
        </td>
    </tr>
    <tr>
        <th><label class="custom-label"><span class="required-field">*</span>Date of Joining</label></th>
        <td>
         <div style="padding-right: 15px;">
            <input id="dateOfJoining" name="dateOfJoining" style="width: 100%;height: 20px;"  type="text" value="" size="30" maxlength="30" autocomplete="off">
         </div>
        </td>
    </tr>
    <tr>
        <th><label class="custom-label"><span class="required-field">*</span>Gender</label></th>
        <td>
         
            <select name="gender" style="width:97%;height: 20px;"   >
             <option value="">Please select Gender</option>
            <c:forEach var="option" items="${genderOptions}">
       <option value="${option.gmid}">${option.gmname}</option>
    </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <th><label class="custom-label"><span class="required-field">*</span>Father/Husband Name</label></th>
        <td>
         <div style="padding-right: 15px;">
            <input id="relationName" name="relationName" style="width: 100%;height: 20px;"  type="text" value="" size="30" maxlength="30" autocomplete="off">
        </div>
        </td>
    </tr>
    <tr>
        <th><label class="custom-label"><span class="required-field">*</span>ID Mark</label></th>
        <td>
         <div style="padding-right: 15px;">
            <input id="idMark" name="idMark" style="width: 100%;height: 20px;"  type="text" value="" size="30" maxlength="30" autocomplete="off">
       
       </div> </td>
    </tr>
</tbody>


			</table>
    </div>
</div>

<div class="section">
    <div class="section-header" onclick="toggleSection('section2')"  >Other Information:</div>
    <div id="section2Content" class="section-content">
       <table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
                        <!-- ----------------------------------------------------- -->
                        
                        
                        <tr>
                        <th><label class="custom-label"><span class="required-field"></span>Nearest Police Station</label></th>
                            <td>
                             <div style="padding-right: 15px;">
                             <f:input type="text" path="relationName" value="${workmenbyAadhar.relationName}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                             </div></td>
                       
                       <th><label class="custom-label"><span class="required-field"></span>PF Number</label></th>
                            <td>
                             <div style="padding-right: 15px;">
                            <f:input type="text" path="idMark" value="${workmenbyAadhar.idMark}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div>
                            </td>
                       
                        </tr>
                        <tr>
                        <th><label class="custom-label"><span class="required-field">*</span>Marital Status</label></th>
                            <td>   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Marital Status</option>
</select></td>
                            <%-- <td><f:input type="text" path="relationName" value="${workmenbyAadhar.relationName}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                       <th><label class="custom-label"><span class="required-field"></span>Referred By</label></th>
                            <td><div style="padding-right: 15px;"><f:input type="text" path="idMark" value="${workmenbyAadhar.idMark}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                        </tr>
                        <tr>
                        <th><label class="custom-label"><span class="required-field"></span>Academics</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="academics" value="${workmenbyAadhar.academics}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                       <th><label class="custom-label"><span class="required-field">*</span>Technical</label></th>
                           <td>   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Technical</option>
</select></td>
                            <%-- <td><f:input type="text" path="idMark" value="${workmenbyAadhar.idMark}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                        </tr>
                         <tr>
                        <th><label class="custom-label"><span class="required-field"></span>Is Police verification done ?</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="relationName" value="${workmenbyAadhar.relationName}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                       <th><label class="custom-label"><span class="required-field"></span>Police Verification Date</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="idMark" value="${workmenbyAadhar.idMark}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                        </tr>
                         <tr>
                        <th><label class="custom-label"><span class="required-field"></span>Vaccination Date</label></th>
                            <td> <div style="padding-right: 15px;"> 
                            <f:input type="text" path="relationName" value="${workmenbyAadhar.relationName}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                       <th><label class="custom-label"><span class="required-field"></span>PersonalID</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="idMark" value="${workmenbyAadhar.idMark}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                        </tr> 
                        <tr>
                        <th><label class="custom-label"><span class="required-field">*</span>Accommodation</label></th>
                          <td>   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Accommodation</option>
</select></td>
                           <%--  <td><f:input type="text" path="accomodation" value="${workmenbyAadhar.accomodation}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                       <th><label class="custom-label"><span class="required-field"></span>Mobile Number</label></th>
                            <td> <div style="padding-right: 15px;"><f:input type="text" path="mobileNumber" value="${workmenbyAadhar.mobileNumber}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                        </tr>
                        <tr>
                        <th><label class="custom-label"><span class="required-field"></span>Account Number</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="accountNo" value="${workmenbyAadhar.accountNo}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                       <th><label class="custom-label"><span class="required-field"></span>Bank IFSC Code</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="bankBranch" value="${workmenbyAadhar.bankBranch}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                        </tr>
                         <tr>
                        <th><label class="custom-label"><span class="required-field"></span>Prior Experience</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="relationName" value="${workmenbyAadhar.relationName}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                       <th><label class="custom-label"><span class="required-field"></span>Previous EP Number</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="idMark" value="${workmenbyAadhar.idMark}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                        </tr> 
                         <tr>
                        <th><label class="custom-label"><span class="required-field">*</span>Emergency Name</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="relationName" value="${workmenbyAadhar.emergencyName}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                       <th><label class="custom-label"><span class="required-field">*</span>Emergency Number</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="idMark" value="${workmenbyAadhar.emergencyNumber}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field"></span>Blood Group</label></th>
                            <td>
   <select name="bloodGroup" style="width: 97%;height: 20px;"   >
   <option value="">Please select Blood Group</option>
    <c:forEach var="option" items="${bloodGroupOptions}">
       <option value="${option.gmid}">${option.gmname}</option>
    </c:forEach>
</select>
</td>
                        <th><label class="custom-label"><span class="required-field">*</span>Date of Termination</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="dateOfTermination" value="${workmenbyAadhar.dateOfTermination}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                        
                        </tr>
                        </tbody>
                        </table>
    </div>
</div>
<!-- Statutory/Regulatory Data: -->
<div class="section">
    <div class="section-header" onclick="toggleSection('section3')" >Employment Information:</div>
    <div id="section3Content" class="section-content">
       <table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
                        
                        <!-- ------------------- -->
                        
                         <tr>
                        <th><label class="custom-label"><span class="required-field">*</span>Principal Employer</label></th>
                           <td>
   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Principal Employer</option>
</select>
</td>
                            <%-- <td><f:input type="text" path="unitId" value="${workmenbyAadhar.unitId}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                       <th><label class="custom-label"><span class="required-field">*</span>Contractor</label></th>
                          <td> <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Contractor</option>
</select></td>
                            <%-- <td><f:input type="text" path="contractorId" value="${workmenbyAadhar.contractorId}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                        </tr>
                         <tr>
                        <th><label class="custom-label"><span class="required-field">*</span>Workorder</label></th>
                        <td>   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Workorder</option>
</select></td>
                           <%--  <td><f:input type="text" path="contractorId" value="${workmenbyAadhar.contractorId}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                       <th><label class="custom-label"><span class="required-field">*</span>Trade</label></th>
                          <td>  <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Trade</option>
</select></td>
                            <%-- <td><f:input type="text" path="tradeId" value="${workmenbyAadhar.tradeId}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                        </tr>
                         <tr>
                        <th><label class="custom-label"><span class="required-field">*</span>Skill</label></th>
                         <td> <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Skill</option>
</select></td>
                            <%-- <td><f:input type="text" path="skillId" value="${workmenbyAadhar.skillId}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                       <th><label class="custom-label"><span class="required-field">*</span>Department</label></th>
                         <td>   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Department</option>
</select></td>
                            <%-- <td><f:input type="text" path="departmentId" value="${workmenbyAadhar.departmentId}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                        </tr>
                         <tr>
                        <th><label class="custom-label"><span class="required-field">*</span>Area</label></th>
                        <td>   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Area</option>
</select></td>
                            <%-- <td><f:input type="text" path="relationName" value="${workmenbyAadhar.relationName}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                       <th><label class="custom-label"><span class="required-field">*</span>Engineering-in-Charge</label></th>
                            <td>   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select EIC</option>
</select></td>
                           <%--  <td><f:input type="text" path="idMark" value="${workmenbyAadhar.idMark}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                        </tr>
                         <tr>
                        <th><label class="custom-label"><span class="required-field">*</span>Nature of Job</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="relationName" value="${workmenbyAadhar.relationName}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                       <th><label class="custom-label"><span class="required-field">*</span>WC Policy/ESIC Reg Number</label></th>
                            <td>   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select EIC</option>
</select></td>
                           <%--  <td><f:input type="text" path="idMark" value="${workmenbyAadhar.idMark}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                        </tr>
                         <tr>
                        <th><label class="custom-label"><span class="required-field">*</span>Hazardous Area</label></th>
                        <td>   <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Hazardous Area</option>
</select></td>
                            <%-- <td><f:input type="text" path="hazardousArea" value="${workmenbyAadhar.hazardousArea}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                       <th><label class="custom-label"><span class="required-field">*</span>Access Area</label></th>
                           <td> <select name="pe" style="width: 97%;height: 20px;"   >
   <option value="">Please select Access level</option>
</select></td>
                            <%-- <td><f:input type="text" path="accessLevel" value="${workmenbyAadhar.accessLevel}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" /></td>
                        --%>
                        </tr>
                         <tr>
                        <th><label class="custom-label"><span class="required-field"></span>UAN Number</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="uanNumber" value="${workmenbyAadhar.uanNumber}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                       <th><label class="custom-label"><span class="required-field"></span>Health Check Up Date</label></th>
                            <td> <div style="padding-right: 15px;">
                            <f:input type="text" path="idMark" value="${workmenbyAadhar.idMark}"  style="width: 100%;height: 20px;"   size="30" maxlength="30" autocomplete="off"/>
                            </div></td>
                       
                        </tr>
                      </tbody>
			</table>
    </div>
</div>  
</div>
    </f:form>
</div>

