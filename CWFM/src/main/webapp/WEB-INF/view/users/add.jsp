<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
 <title>Users Add</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="resources/js/jquery.min.js"></script>
   <!--  <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script> -->
 <!--    <script src="resources/js/commonjs.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css">  -->

    <style>
      body {
    margin: 0;
    overflow-x: hidden;
    font-family: 'Noto Sans', sans-serif;
}

#principalEmployerContent {
    padding: 20px;
    box-sizing: border-box;
    overflow-y: auto;
    height: calc(100vh - 60px); /* Adjust based on header height */
}

/* .page-header {
    background-color: #005151;
    color: #fff;
    padding: 15px;
    text-align: center;
    font-size: 24px;
    font-family: 'Noto Sans', sans-serif;
    position: fixed;
    width: 100%;
    top: 0;
    left: 0;
    z-index: 1000;
} */

    .page-header {
        display: flex;
        align-items: center;
        justify-content: flex-end; /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
    }
.header-buttons {
    float: right;
    margin-right: 20px;
    gap:10px;
}

.tabs {
    overflow: hidden;
    border-bottom: 2px solid #005151;
    margin-bottom: 20px;
}

.tabs button {
    background-color: #fff; /* Tab background color */
    border: 1px solid #ddd; /* Optional: add a border for visibility */
     border-radius: 3px;
    outline: none;
    padding: 5px 10px;/* Reduced height */
    cursor: pointer;
    font-size: 12px;
    transition: background-color 0.3s, color 0.3s;
    color: #005151; /* Tab text color */
    font-family: 'Noto Sans', sans-serif;
     margin-right: 5px;
}
  

.tabs button.active {
    background-color: #005151; /* Active tab background color */
    color: #fff; /* Active tab text color */
    border-bottom: 2px solid #fff;
}

.tab-content {
    display: none;
    padding: 10px;
    background-color: white;
    border: 1px solid #ddd;
}

.tab-content.active {
    display: block;
}

.custom-label {
    font-family: 'Noto Sans', sans-serif;
    text-align: left;
    display: block;
    margin-bottom: 5px;
    color: #898989;/* Label text color */
    display: inline;
  padding: .2em .6em .3em;
  font-size: 85%;
  font-weight: 700;
  line-height: 1;
    white-space: nowrap;
  vertical-align: baseline;
  border-radius: .25em;
}

.custom-input-container {
    padding-left: 10px;
}

.custom-input, .custom-input-checkbox {
    height: 40px;
    font-family: 'Noto Sans', sans-serif;
    width: 100%;
    box-sizing: border-box;
}

.required-field {
    color: red;
    margin-right: 4px;
}



table.ControlLayout {
    border-collapse: separate; /* Ensure spacing is applied correctly */
    border-spacing: 10px; /* Adjust the value for the desired gap between cells */
}

table.ControlLayout th,
table.ControlLayout td {
    padding: 10px; /* Add padding inside cells for spacing around content */
    vertical-align: top; /* Align the content to the top of the cell */
}
     .error-message {
    color: red;
    font-family: Arial, sans-serif;
    font-size: 12px;
    font-weight:bold;
}
 .error {
    background-color: #ffcccc; /* Light red background */
}
  .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}
    </style>


    
</head>

<body>
    
<!-- <h1>Organization Level Entry</h1> -->
<div class="page-header">
		 <div class="page-header-buttons">
 <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveUser()">Save</button>
 <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="goBackToUserList()">Cancel</button>
 </div>
</div>
<form id="userFormID" >
<div id="tab1" class="tab-content active">
   
    <table class="ControlLayout">
        <!-- Form fields -->
        <tr>
            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.firstName"/></label></th>
            <td><input type="text" name="firstName" style="height: 20px;text-transform: capitalize;" size="30" maxlength="30" autocomplete="off" required/>
              <div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.firstNameRegax"/></span>
       </div>
             <span id="firstNameError" class="error-message"></span>
             </td>
           
            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.lastName"/></label></th>
            <td><input type="text" name="lastName" style="height: 20px;text-transform: capitalize;" size="30" maxlength="30" autocomplete="off"/>
           <div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.lastNameRegax"/></span>
    </div>
            <span id="lastNameError" class="error-message"></span>
            </td>
            
        </tr>
        <tr>
             <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.emailAddress"/></label></th>
        <td style="color: black;"><input type="email" name="emailId" style="height: 20px;" size="30" maxlength="30" autocomplete="off"/>
        <div style="text-align: left;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.emailAddressRegax"/></span>
       </div>
        <span id="emailIdError" class="error-message"></span>
        </td>
        
            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contactNumber"/></label></th>
            <td><input type="text" name="contactNumber" style="height: 20px;" size="30" maxlength="10" autocomplete="off"  pattern="[6-9][0-9]{9}"/>
            <div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.mobileNumberRegax"/></span>
       </div>
            <span id="contactNumberError" class="error-message"></span>
            </td>
             
        </tr>
        <tr>
            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.password"/></label></th>
            <td style="color: black;"><input type="password" name="password" style="height: 20px;" size="30" maxlength="30" autocomplete="new-password" autocomplete="off"/>
           <div style="text-align:right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.passwordRegax"/></span>
       </div>
            <span id="passwordError" class="error-message"></span>
            </td>
            
            <th><label class="custom-label"><span class="required-field">*</span>User Account:</label></th>
            <td><input type="text" name="userAccount" style="height: 20px;" size="30" maxlength="30" autocomplete="new-userAccount"/>
            <div style="text-align:right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.userAccountRegax"/></span>
       </div>
            <span id="userAccountError" class="error-message"></span>
            </td>
        
        </tr>
        <!-- <tr>
        </tr> -->
        <tr>
            <th><label class="custom-label"><span class="required-field">*</span>Roles:</label></th>
            <td>
                <table style="width: 100%;">
                
                    <tr>
                        <th style="text-align: left;color:darkcyan;">Select</th>
                        <th style="text-align: left;color:darkcyan;">Role Name </th>
                         
                    </tr>
                    
                    
                    <c:forEach var="role" items="${roles}">
                <tr>
               
                    <td><input type="checkbox" name="roleIds" value="${role.gmId}" /></td>
                    <td style="font-size: 11.9px;font-weight: 690;font-family: 'Noto Sans', sans-serif;color: #898989;padding: -0.8em .6em .3em;">${role.gmName}</td>
                </tr>
                
            </c:forEach>
            <tr>
                    <td>
                    </td>
                     <th><span id="roleError" class="error-message" style="color: red;"></span></th>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    </div>
</form>



<!-- Form for Adding/Editing Entries -->
<!-- <h3>Add Entry</h3> -->
<%-- <div>
    <input type="hidden" name="orgLevelEntryId" id="orgLevelEntryId" value="0">
    <input type="hidden" name="orgLevelDefId" id="orgLevelDefId" value="${param.orgLevelDefId}">

    <label for="entryName">Entry Name:</label>
    <input type="text" name="entryName" id="entryName" required>

    <label for="description">Description:</label>
    <input type="text" name="description" id="description">

    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveOrgEntries()">Save</button>
</div>
<hr/> --%>

<!-- Table for displaying entries -->
<%-- <table border="1" id="table-body">
    <thead>
        <tr>
            <th>ID</th>
            <th>Entry Name</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${not empty entries}">
                <c:forEach var="entry" items="${entries}">
                    <tr id="row-${entry.orgLevelEntryId}">
                        <td>${entry.orgLevelEntryId}</td>
                        <td>${entry.name}</td>
                        <td>${entry.description}</td>
                        <td>
                            <button onclick="editEntry(${entry.orgLevelEntryId}, '${entry.name}', '${entry.description}', ${param.orgLevelDefId})">Edit</button>
                            <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteOrgLevelEntry(${entry.orgLevelEntryId}, ${orgLevelDefId})">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="4">No entries available for the selected organization level.</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tbody>
</table> --%>
</body>
</html>
