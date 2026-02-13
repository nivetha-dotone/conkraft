<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/contractor.js"></script>
    <script src="resources/js/cms/workorder.js"></script>
    <script src="resources/js/cms/workmen.js"></script>
    <script src="resources/js/cms/report.js"></script>
    <script src="resources/js/jquery.min.js"></script>
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

.page-header {
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
}

.header-buttons {
    float: right;
    margin-right: 20px;
}

.tabs {
    overflow: hidden;
    border-bottom: 2px solid #005151;
    margin-bottom: 20px;
}

.tabs button {
    background-color: #fff; /* Tab background color */
    border: 2px solid #005151; /* Tab border color */
    outline: none;
    padding: 10px 20px; /* Reduced height */
    cursor: pointer;
    font-size: 17px;
    transition: background-color 0.3s, color 0.3s;
    color: #005151; /* Tab text color */
    font-family: 'Noto Sans', sans-serif;
}

.tabs button.active {
    background-color: #005151; /* Active tab background color */
    color: #fff; /* Active tab text color */
    border-bottom: 2px solid #fff;
}

.tab-content {
    display: none;
    padding: 10px;
    background-color: #f9f9f9;
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
            font-weight: normal;
        }
        .custom-label {
            font-family: Arial, sans-serif;
            text-align: left;
            display: block;
        }
        .required-field {
            color: red;
            margin-right: 4px; 
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
            margin-right: 10px;
        }
        .tabs {
            display: flex;
            margin-bottom: 10px;
        }
       
        .tabs button {
        padding: 5px 10px; /* Adjust padding to decrease size */
        font-size: 12px; /* Decrease font size */
        margin-right: 5px; /* Space between tabs */
        border: 1px solid #ddd; /* Optional: add a border for visibility */
        border-radius: 3px; /* Optional: rounded corners */
    }
        .tabs button.active {
            background-color: #005151;
            color: white;
            border-bottom: 2px solid #005151;
        }
        .tab-content {
            display: none;
            padding: 20px;
            background-color: white;
            border: 1px solid #ccc;
        }
        .tab-content.active {
            display: block;
        }
        .custom-select {
    display: inline-block;
    width: 100%; /* Reduced the width to 50%, adjust as needed */
    height: 25px;
    padding: 5px;
    font-size: 12px;
    font-family: Arial, sans-serif;
    line-height: 1.5;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

    .custom-select:focus {
        border-color: #80bdff;
        outline: 0;
        box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
    }
    .image-container {
        text-align: center;
        padding: 10px;
    }

    .image-container img {
        margin: 10px;
        cursor: pointer;
    }
   

    .tabs-container {
        display: flex;
        justify-content: space-between; /* Distribute space between tabs and buttons */
        align-items: center; /* Align items vertically */
    }

    .tabs {
        display: flex;
        flex-wrap: nowrap; /* Prevent wrapping of tabs */
    }

    .tabs button {
        margin-right: 10px; /* Space between tabs */
    }

    .action-buttons {
        display: flex; /* Align buttons horizontally */
        align-items: center; /* Center buttons vertically */
    }

    .action-buttons button {
        margin-left: 10px; /* Space between buttons */
    }
    .custom-mb {
    margin-bottom: 2.5rem; /* Example custom margin */
}
.custom-radio {
    margin-right: 5px; /* Adjust the spacing between the radio button and the label */
    vertical-align: middle; /* Align the radio button with the label text */
}

.custom-label-inline {
    display: inline-block;
    vertical-align: middle; /* Align the label text with the radio button */
    margin-left: 3px; /* Adjust this value to control the space between the radio button and the label */

}
td {
    padding: 5px; /* Add padding to cells for spacing */
}

input[type="radio"] {
    margin-right: 5px; /* Space between radio button and label */
    vertical-align: middle; /* Align radio button with label text */
}

label {
    vertical-align: middle; /* Align label text with radio button */
    display: inline-block; /* Ensure label appears on the same line as radio button */
    color: #495057; /* Set the text color to a dark shade */
    font-family: Arial, sans-serif;
}
textarea {
            color: gray; /* Set text color to gray */
            width: 300px; /* Optional width */
            height: 150px; /* Optional height */
        }
          #loaderOverlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.4);
    z-index: 9999;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.loader {
    width: 60px;
    height: 60px;
    border: 6px solid #ddd;
    border-top: 6px solid #1976d2;
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

.loader-text {
    margin-top: 15px;
    color: #fff;
    font-size: 16px;
    font-weight: 600;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
    </style>
     <%
    	MasterUser user = (MasterUser) session.getAttribute("loginuser");
     String userId = user != null && user.getUserId() != null ? String.valueOf(user.getUserId()) : "";
        String roleName = user != null ? user.getRoleName() : "";
        String roleId = user!=null?user.getRoleId():"";
        String contextPath =  request.getContextPath() ;
		%>
		<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script>
 // Function to validate fields in the current active tab
    function validateCurrentTab() {
        // Example of validation logic; customize based on your tab's fields
        let isValid = true;

        // Example: Validate fields in the current active tab
        const activeTabId = document.querySelector('.tab-content.active').id;

        // Validate specific fields based on the active tab
        if (activeTabId === 'tab1') {
            const aadharNumber = document.getElementById("aadharNumber").value.trim();
            if (aadharNumber === "" || aadharNumber.length !== 12 || isNaN(aadharNumber)) {
                document.getElementById("error-aadhar").textContent = "Please enter a valid 12-digit Aadhar number.";
                isValid = false;
            } else {
                document.getElementById("error-aadhar").textContent = ""; // Clear previous error
            }

            // Add more validation logic for other fields in tab1
        } 
        // Repeat similar validation checks for other tabs if necessary

        return isValid;
    }

    // Function to show the selected tab
    function showTab(tabId) {
        // Check if current tab fields are valid before switching tabs
        if (!validateCurrentTab()) {
            return; // Prevent tab switch if validation fails
        }

        // Hide all tab contents
        var tabContents = document.querySelectorAll('.tab-content');
        tabContents.forEach(function(content) {
            content.classList.remove('active');
        });

        // Remove active class from all tabs
        var tabs = document.querySelectorAll('.tabs button');
        tabs.forEach(function(tab) {
            tab.classList.remove('active');
        });

        // Show the selected tab content and add active class to the clicked tab
        document.getElementById(tabId).classList.add('active');
        document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
    }

   



    /* function showTab(tabId) {
        // Hide all tab contents
        var tabContents = document.querySelectorAll('.tab-content');
        tabContents.forEach(function(content) {
            content.classList.remove('active');
        });

        // Remove active class from all tabs
        var tabs = document.querySelectorAll('.tabs button');
        tabs.forEach(function(tab) {
            tab.classList.remove('active');
        });

        // Show the selected tab content and add active class to the clicked tab
        document.getElementById(tabId).classList.add('active');
        document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
    } */

    document.addEventListener("DOMContentLoaded", function() {
        // Set the default tab
        showTabNew('tab1');
        
    });
       

    
  
   

    </script>
</head>
<body>
        <!-- <div >
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGatePass()">Save</button>
             <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitForm()">Cancel</button>
        </div>  -->
       
    <div id="principalEmployerContent">
       <div class="tabs-container">
        <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTabNew('tab1')">Basic Data</button>
            <button data-target="tab2" onclick="showTabNew('tab2')">Employment Information</button>
            <!-- <button data-target="tab3" onclick="showTabNew('tab3')">Other Information</button>
            <button data-target="tab4" onclick="showTabNew('tab4')">Wages</button> -->
            <button data-target="tab5" onclick="showTabNew('tab5')">Documents</button>
             <button data-target="tab6" onclick="showTabNew('tab6')">Approval Status</button> 
        </div>
         <div class="action-buttons" >
            <button id="saveButton" style="display:none;" type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGatePass('${sessionScope.loginuser.userId}')">Save</button>
            <% if (user != null && !"Contractor".equals(roleName)) { %>
    			<button id="approveButton"  type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="approveRejectGatePass('4','project')">Approve</button>
   				 <button id="rejectButton"  type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="approveRejectGatePass('5','project')">Reject</button>
			<% } %>
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/contractworkmen/projectOnboardingList', 'Project Gatepass List');">Cancel</button>
        </div> 
    </div>

        <f:form id="addAadharOBForm" action="/CWFM/contractworkmen/addAadharOB" modelAttribute="workmenbyAadhar" method="post">
            <div id="errorContainer">
                <c:if test="${not empty errors}">
                    <p><span class="required-field">*</span> Indicates Required fields.</p>
                    <div class="error-message">
                        <f:errors path="*" cssClass="error" element="div" />
                    </div> 
                </c:if>
            </div>
            <div id="loaderOverlay" style="display:none;">
    <div class="loader"></div>
    <div class="loader-text">please wait...</div>
</div>
            <div id="tab1" class="tab-content active">
            
            
            <table cellspacing="0" cellpadding="0">
            <tr><td>
    <table cellspacing="0" cellpadding="0">
        <tbody>
       
            <tr>
            
            <th>
            
            <label class="custom-label"><span class="required-field">*</span><spring:message code="label.transactionId"/></label></th>
            	<td>
            	<input id="transactionId" name="transactionId" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.transactionId }" readonly>
   
            	</td>
            	<th>    	<label class="custom-label"><span class="required-field">*</span><spring:message code="label.gatePassId"/></label></th>
            	<td>
            	<input id="gatePassId" name="gatePassId" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.gatePassId }" readonly>
   
            	</td>
            	</tr>
    
    		<input type="hidden" id="userId" name="userId" value="<%= userId %>">
			<input type="hidden" id="roleName" name="roleName" value="<%= roleName %>">
			<input type="hidden" id="roleId" name="roleId" value="<%= roleId %>">
		<tr>	
    <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.aadharNumber"/></label></th>
    <td>
    	<input id="aadharNumber" name="aadharNumber" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.aadhaarNumber }" readonly>
    </td>
    <th></th>
    <td></td>
    
    </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.firstName"/></label></th>
                <td>
                	<input id="firstName" name="firstName" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.firstName }" readonly>
                </td>
           
            
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.lastName"/></label></th>
                <td>
                	<input id="lastName" name="lastName" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.lastName}">
                </td>
          
            </tr>
            <tr>
             <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.fatherHusbandName"/></label></th>
                <td>
                	<input id="relationName" name="relationName" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.relationName }" readonly>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.dateOfBirth"/></label></th>
               <td>
    				<input id="dateOfBirth" name="dateOfBirth" class="datetimepickerformat" style="width: 100%; height: 20px;" type="text" value="${GatePassObj.dateOfBirth}" readonly >
			</td>
               
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.gender"/></label></th>
                <td>
                	<input id="gender" name="gender" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.gender }" readonly>
                     </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.idMark"/></label></th>
                <td>
                	<input id="idMark" name="idMark" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.idMark }" readonly>
                </td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mobileNumber"/></label></th>
                <td>
                	<input id="mobileNumber" name="mobileNumber" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.mobileNumber }" readonly>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.maritalStatus"/></label></th>
                <td>
                	<input id="maritalStatus" name="maritalStatus" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.maritalStatus }" readonly>
                	 </td>
            </tr>
            <tr>
            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.disability"/></label></th>				
				<td ><input id="disability" name="disability" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.disability }" readonly></td>
          
           <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workmenType"/></label></th>
                            <td><input id="workmenType" name="workmenType" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.workmenType }" readonly>  </td>
            </tr>
           <tr>
           	 <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.address"/></label></th>
                <td>
                	<%-- <input id="address" name="address" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.address }" readonly> --%>
                	<textarea id="address" name="address" style="width: 100%; height: 60px; text-transform: capitalize;" readonly>${GatePassObj.address}</textarea>
                	
                	<label id="error-address" style="color: red;display: none;">Address is required</label>
                </td>
           </tr>
        </tbody>
    </table></td>
    <td></td>
    <td>
                   		 <div id="preview" style="display: flex; flex-direction: column; justify-content: flex-end; height: 200px; width: 200px; border: 1px solid #ccc;">
        					<img class="target" src="/imageinline/${GatePassObj.createdBy }/${GatePassObj.transactionId}/${GatePassObj.photoName }" alt="Image" style="max-width: 100%; height: auto;">
    					</div></td></tr></table>
</div>

            <div id="tab2" class="tab-content">
                <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.principalEmployer"/></label></th>
                            <td><input id="principalEmployer" name="principalEmployer" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.principalEmployer }" readonly></td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractor"/></label></th>
                            <td><input id="contractor" name="contractor" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.contractor }" readonly></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workorder"/></label></th>
                            <td><input id="workorder" name="workorder" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.workorder }" readonly> </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.trade"/></label></th>
                            <td><input id="trade" name="trade" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.trade }" readonly> </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.skill"/></label></th>
                            <td><input id="skill" name="skill" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.skill }" readonly></td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.department"/></label></th>
                            <td><input id="department" name="department" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.department }" readonly></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.area"/></label></th>
                            <td><input id="subdepartment" name="subdepartment" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.subdepartment }" readonly> </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.engineeringInCharge"/></label></th>
                            <td><input id="eic" name="eic" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.eic }" readonly></td>
                        </tr>
                       <%--  <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.natureOfJob"/></label></th>
                            <td>
                            	<input id="natureOfJob" name="natureOfJob" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.natureOfJob }" readonly>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.wcPolicyesicRegNumber"/></label></th>
                            <td><input id="wcEsicNo" name="wcEsicNo" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.wcEsicNo }" readonly> </td>
                       		<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.labourLicenseNumber"/></label></th>
                            <td><input id="llNo" name="llNo" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.llNo }" readonly> </td>
                      
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.hazardeousArea"/></label></th>
                            <td><input id="hazardousArea" name="hazardousArea" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.hazardousArea }" readonly></td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.accessArea"/></label></th>
                            <td><input id="accessArea" name="accessArea" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.accessArea }" readonly>  </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.uanNumber"/></label></th>
                            <td>
                            	<input id="uanNumber" name="uanNumber" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.uanNumber }" readonly>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.healthCheckUpDate"/></label></th>
                            <td>
                            	<input id="healthCheckDate" name="healthCheckDate" class="datetimepickerformat" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.healthCheckDate }" readonly>
                            	</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfNumber"/></label></th>
                            <td>
                            	<input id="pfNumber" name="pfNumber" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.pfNumber }" readonly>
                            </td>
                            <th><label class="custom-label"><spring:message code="label.esicNumber"/></label></th>
                            <td>
                            	<input id="esicNumber" name="esicNumber"  style="width: 100%;height: 20px;" type="text" value="${GatePassObj.esicNumber }" readonly>
                            	</td>
                        </tr>
                        <tr>
                         <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.dateOfJoining"/></label></th>
                        	<td>
    				<input id="doj" name="doj" class="datetimepickerformat" style="width: 100%; height: 20px;" type="text" 
     value="${GatePassObj.doj }" readonly>
			</td>
			
			 <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.dateOfTermination"/></label></th>
                        	<td>
    				<input id="dot" name="dot" class="datetimepickerformat" style="width: 100%; height: 20px;" type="text" 
     value="${GatePassObj.dot }" readonly>
			</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfApplicable"/></label></th>
                            
                            <td>
        <input type="checkbox" id="pfApplicable" name="pfApplicable"
               <c:if test="${GatePassObj.pfApplicable eq 'Yes'}">checked</c:if>
               disabled />
    </td>
                        </tr> --%>
                    </tbody>
                </table>
            </div>

            <div id="tab3" class="tab-content">
              <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
          	 
		   <tr>
				<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.bloodGroup"/></label></th>				
				<td >
				<input id="bloodGroup" name="bloodGroup" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.bloodGroup }" readonly>
				</td>
				
			
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.accommodation"/></label></th>				
				<td ><input id="accommodation" name="accommodation" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.accommodation }" readonly></td>
				 </tr>
			<tr>
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.academic"/></label></th>				
				<td ><input id="academic" name="academic" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.academic }" readonly>
					</td>
				
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.technical"/></label></th>				
				<td ><input id="technical" name="technical" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.technical }" readonly>
					</td>
				</tr>
			
		   <tr>
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.ifscCode"/></label></th>
				<td >
				<input  style="width: 100%;height: 20px;" type="text"  id="ifscCode" name="ifscCode"    value="${GatePassObj.ifscCode }" readonly   />
				</td>
			<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.accountNumber"/></label></th>
				<td >
				<input style="width: 100%;height: 20px;" type="text"  id="accountNumber" name="accountNumber"   value="${GatePassObj.accountNumber }" readonly />
				</td>
			</tr>
			
		   <tr>
				<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.emergencyContactName"/></label></th>
				<td>
				<input style="width: 100%;height: 20px;" type="text" id="emergencyName" name="emergencyName"   value="${GatePassObj.emergencyName }" readonly  />
				</td>			
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.emergencyContactNumber"/></label></th>
				<td><input style="width: 100%;height: 20px;" type="text"  id="emergencyNumber" name="emergencyNumber"   value="${GatePassObj.emergencyNumber}" readonly    />
				</td>				
			
			</tr>
			</tbody>
                </table>
</div>

            <div id="tab4" class="tab-content">
            <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                     
		   <tr>
				
				<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workmenWageCategory"/></label></th>
				<td >
				<input style="width: 100%;height: 20px;" type="text"  id="wageCategory" name="wageCategory"   value="${GatePassObj.wageCategory}" readonly    />
						</td>
				
				<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.bonusPayout"/></label></th>
				<td ><input style="width: 100%;height: 20px;" type="text"  id="bonusPayout" name="bonusPayout"   value="${GatePassObj.bonusPayout}" readonly    />
					</td>
				</tr>
        <tr>
				<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.zone"/></label></th>
				<td ><input style="width: 100%;height: 20px;" type="text"  id="zone" name="zone"   value="${GatePassObj.zone}" readonly    />
					</td>
		
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.basic"/></label></th>
			
				<td><input style="width: 100%;height: 20px;" type="text"  name="basic" id="basic"  value="${GatePassObj.basic }" readonly/>
				</td>						
			</tr>
        <tr>
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.da"/></label></th>
				<td ><input style="width: 100%;height: 20px;" type="text"  name="da" id="da" value="${GatePassObj.da }" readonly/>
				</td>				
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.hra"/></label></th>
				<td ><input style="width: 100%;height: 20px;" type="text"  name="hra"  id="hra" value="${GatePassObj.hra }" readonly/>
				</td>				
			
			</tr>
         <tr>
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.washingAllowance"/></label></th>
				<td>
				<input style="width: 100%;height: 20px;" type="text" name="washingAllowance" id="washingAllowance" value="${GatePassObj.washingAllowance }" readonly/>
				</td>				
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.otherAllowance"/></label></th>
				<td><input style="width: 100%;height: 20px;" type="text"  name="otherAllowance" id="otherAllowance" value="${GatePassObj.otherAllowance }" readonly />
				</td>				
			</tr>
        <tr>
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.uniformAllowance"/></label></th>
				<td><input style="width: 100%;height: 20px;" type="text"  name="uniformAllowance" id="uniformAllowance" value="${GatePassObj.uniformAllowance }" readonly />
				</td>				
			
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.pfcap"/></label></th>
	<td><input style="width: 100%;height: 20px;" type="text"  name="pfCap"  id="pfCap" value="${GatePassObj.pfCap }" readonly/></td>
			</tr>
               </tbody>
                </table>
            </div>

            <div id="tab5" class="tab-content">
            <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                     
                    <tr>
                		<td style="color:black"><spring:message code="label.profilePhoto"/></td>
                <td>
                   <a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','${GatePassObj.photoName }')">Download Photo</a>
                </td>
            		</tr> 
            		
                   <tr>
                		<td style="color:black"><spring:message code="label.aadharDocument"/></td>
                <td>
                   <a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','aadhar')">Download Aadhar</a>
                </td>
            		</tr>
            		<%-- <tr>
                		<td style="color:black"><spring:message code="label.appointmentDocument"/></td>
                <td>
                   <a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','appointment')">Download Appointment Document</a>
                </td>
            		</tr> --%>
            		<tr>
                		<td style="color:black"><spring:message code="label.policeVerificationDocument"/></td>
                <td>
                    <a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','police')">Download Police Verification Document</a>
                </td>
                 <th><label class="custom-label"><spring:message code="label.policeVerificationDate"/></label></th>
                        	<td>
    				<input id="policeVerificationDate" name="policeVerificationDate" class="datetimepickerformat3" style="width: 100%; height: 20px;" type="text" 
            value="${GatePassObj.policeVerificationDate }" readonly>
			</td>
            		</tr>
            		<c:if test="${not empty GatePassObj.appointmentDocName}">
            		<tr>
                		<td style="color:black"><spring:message code="label.appointmentDocument"/></td>
                		<td>
                    	<a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','appointment')">Download Appointment Document</a>
                		</td>
            		</tr>
      				</c:if>
            		<c:if test="${not empty GatePassObj.bankDocName}">
            		<tr>
                		<td style="color:black"><spring:message code="label.bankDocument"/></td>
                		<td>
                    	<a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','bank')">Download Bank Document</a>
                		</td>
            		</tr>
      				</c:if>
      				
      				<c:if test="${not empty GatePassObj.trainingDocName}">
            		<tr>
                		<td style="color:black"><spring:message code="label.trainingDocument"/></td>
                		<td>
                    	<a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','training')">Download Training Document</a>
                		</td>
            		</tr>
      				</c:if>
      				
      				<c:if test="${not empty GatePassObj.otherDocName}">
            		<tr>
                		<td style="color:black"><spring:message code="label.otherDocument"/></td>
                		<td>
                    	<a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','other')">Download Other Document</a>
                		</td>
            		</tr>
      				</c:if>
      				
      				<c:if test="${not empty GatePassObj.idProof2DocName}">
            		<tr>
                		<td style="color:black"><spring:message code="label.idProof2Document"/></td>
                		<td>
                    	<a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','id2')">Download Id Proof2 Document</a>
                		</td>
            		</tr>
      				</c:if>
      				
      				<c:if test="${not empty GatePassObj.medicalDocName}">
            		<tr>
                		<td style="color:black"><spring:message code="label.medicalDcocument"/></td>
                		<td>
                    	<a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','medical')">Download Medical Document</a>
                		</td>
            		</tr>
      				</c:if>
      				
      				<c:if test="${not empty GatePassObj.educationDocName}">
            		<tr>
                		<td style="color:black"><spring:message code="label.educationDocument"/></td>
                		<td>
                    	<a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','education')">Download Education Document</a>
                		</td>
            		</tr>
      				</c:if>
      				
      				<c:if test="${not empty GatePassObj.form11DocName}">
            		<tr>
                		<td style="color:black"><spring:message code="label.form11Document"/></td>
                		<td>
                    	<a href="#" onclick="viewDoc('${GatePassObj.transactionId}','${GatePassObj.createdBy }','form11')">Download Form11 Document</a>
                		</td>
            		</tr>
      				</c:if>
        
        <%-- <tr>
            <td colspan="20">
                <div>
                    <p id="p3"><b><font color="darkblue" size="3">Comments</font></b></p>
                    <hr10 style="color:rgb(0, 102, 204);">
                </div>
            </td>
        </tr> --%>
        <c:if test="${ not empty GatePassObj.comments}">
        <tr>
        <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorSupervisorComment"/></label></th>
        <td>
        <textarea id="comments" name="comments" readonly>${GatePassObj.comments}</textarea>
        </td>
        </tr>
        </c:if>
        <tr>
				<!-- <th><label class="custom-label">Previous Comment</label></th>
				<td><input type="textarea" name="value(prevComment)" style="width:220px;height:100px;text-transform: capitalize;" readonly="true" cols="35" rows="7"  onchange="setDataChanged();"/></td>
				 -->
				 <% if (user != null && !"Contractor Supervisor".equals(roleName)) { %>
				 <th><label class="custom-label"><spring:message code="label.approveComment"/></label></th>
				<td><textarea id="approvercomments"  name="approvercomments" placeholder="Type here..."></textarea>
				<label id="error-approvercomments" style="color: red;display: none;">Comments is required</label>
				</td>
				<% } %>
			</tr>
		<tr>
		</tr>
      		
        <!-- Add any additional rows for Approver Comments -->
                    
                </tbody>
                </table>
            </div>
            
            
            
            <div id="tab6" class="tab-content">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
        <thead>
            <tr style=" border: 1px solid #ddd;">
                <th><label class="custom-label"><spring:message code="label.role"/></label></th>
                <th><label class="custom-label"><spring:message code="label.status"/></label></th>
                <th><label class="custom-label"><spring:message code="label.comments"/></label></th>
                <th><label class="custom-label"><spring:message code="label.timestamp"/></label></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="approver" items="${approvers}" varStatus="status">
                <tr style=" border: 1px solid #ddd;background-color: ${status.index % 2 == 0 ? '#f9f9f9' : '#ffffff'};">
                    
                    <td style="color:black">${approver.userRole}</td>
                    <td style="color:black">
                      ${approver.status}
                    </td >
                    <td style="color:black">${approver.comments}</td>
                    <td style="color:black">${approver.timestamp != null ? approver.timestamp : 'N/A'}</td>
                </tr>
            </c:forEach>
        </tbody>
                </table>
            </div>
        </f:form>
    </div>
   
  
</body>
 
</html>
