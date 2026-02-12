<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.*" %>
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

/* #preview {
            width: 200px;
            height: 200px;
            border: 1px solid #ddd;
            margin-top: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        } */
        #preview img {
            max-width: 100%;
            max-height: 100%;
        }
       
          .error-bold {
    color: red;
    font-weight: bold !important;
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
%>

	
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
        showTab('tab1');
        initializeDatePicker();
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
            <button class="active" data-target="tab1" onclick="showTab('tab1')">Basic Data</button>
            <button data-target="tab2" onclick="showTab('tab2')">Employment Information</button>
            <button data-target="tab3" onclick="showTab('tab3')">Other Information</button>
            <button data-target="tab4" onclick="showTab('tab4')">Wages</button>
            <button data-target="tab5" onclick="showTab('tab5')">Documents</button>
           <!--  <button data-target="tab6" onclick="showTab('tab6')">Previous Documents</button> -->
        </div>
         <div class="action-buttons" >
            <button id="saveButton" style="display:none;" type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="renewGatePass('${sessionScope.loginuser.userId}')">Save</button>
<%--             <button   type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGatePass('${sessionScope.loginuser.userId}')">Draft</button> --%>
                       <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/contractworkmen/renewFilter', 'Renew List');">Cancel</button>
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
        <tbody>
       <!--  <tr>
    <th><label class="custom-label"><span class="required-field">*</span>Entry Pass Type</label></th>
    <td>
        <input type="radio" name="entryPassType" id="quickOnboarding" value="quickOnboarding">
        <label class="custom-label-inline" for="quickOnboarding"> Quick Onboarding</label>
    </td>
    <td>
        <input type="radio" name="entryPassType" id="regular" value="regular">
        <label class="custom-label-inline" for="regular"> Regular</label>
    </td>
</tr> -->
<tr>
	<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.transactionId"/></label></th>
    <td>
    	
    		<input id="transactionId" name="transactionId" style="width: 100%;height: 20px;" type="text" size="30" maxlength="12" value="${transactionId}" readonly>
   </td>
   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.gatePassId"/></label></th>
    <td>
    	
    		<input id="gatePassId" name="gatePassId" style="width: 100%;height: 20px;" type="text" size="30" maxlength="12" value="${GatePassObj.gatePassId}" readonly>
   </td>
</tr>
            <tr>
    <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.aadharNumber"/></label></th>
    <td>
    <c:if test="${empty GatePassObj.aadhaarNumber }">
    	<input id="aadharNumber" name="aadharNumber" style="width: 100%;height: 20px;" type="text" size="30" maxlength="12">
    </c:if>
     <c:if test="${not empty GatePassObj.aadhaarNumber }">
    	<input id="aadharNumber" name="aadharNumber" style="width: 100%;height: 20px;" type="text" size="30" maxlength="12" value="${GatePassObj.aadhaarNumber }" readonly>
    </c:if>
    	<div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.aadharNumberRegax"/></span>
    </div>
    	 <label id="error-aadhar" style="color: red;display: none;">Please enter a valid 12-digit Aadhar number</label>
    </td>
    
    <%-- <td> <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" ><spring:message code="label.generateotp"/></button></td>
    
    <td>
    	<input id="otp" name="otp" style="width: 100%;height: 20px;" type="text" size="30" maxlength="12" placeholder="Enter otp here">
    </td>
     <td> <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" >Verify OTP</button></td> --%>
    <!--  <td rowspan="6" class="image-container">
        <img id="imageId" width="150" height="150" onclick="openFilePicker();">
        <input type="file" id="fileInput" style="display: none;" accept="image/*" onchange="handleFileSelect(event)">
    </td> -->
    </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.firstName"/></label></th>
                <td class="capitalize">
                <c:if test="${empty GatePassObj.firstName }">
                	<input id="firstName" name="firstName" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30">
                </c:if>
                
                <c:if test="${not empty GatePassObj.firstName }">
                	<input id="firstName" name="firstName" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" value="${GatePassObj.firstName  }" readonly>
                </c:if>
                <div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.firstNameRegax"/></span>
       </div>
                	<label id="error-firstName" style="color: red;display: none;">Please enter a valid First Name.</label>
                </td>
           
            
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.lastName"/></label></th>
                <td>
                 <c:if test="${empty GatePassObj.lastName }">
                 	<input id="lastName" name="lastName" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30">
                 </c:if>
                	 <c:if test="${not empty GatePassObj.lastName }">
                 	<input id="lastName" name="lastName" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" value="${GatePassObj.lastName }" readonly>
                 </c:if>
                 <div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.lastNameRegax"/></span>
    </div>
                	 <label id="error-lastName" style="color: red;display: none;">Please enter a valid Last Name.</label>
                </td>
          
            </tr>
            <tr  id="error-equalNames" style="display: none;" >
           <label  id="error-equalNames" style="display:none;">First name cannot be the same as last name.</label>
            </tr>
            <tr>
            
            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.fatherHusbandName"/></label></th>
                <td>
                	<c:if test="${empty GatePassObj.relationName }">
                		<input id="relationName" name="relationName" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30">
                	</c:if>
                	<c:if test="${not empty GatePassObj.relationName }">
                		<input id="relationName" name="relationName" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" value="${GatePassObj.relationName }" readonly>
                	</c:if>
                	<div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.fatherHusbandNameRegax"/></span>
    </div>
                	<label id="error-relationName" style="color: red;display: none;">Please enter a valid Father / Husband name</label>
                </td>
                
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.dateOfBirth"/></label></th>
               <td>
               	<input id="dateOfBirth" name="dateOfBirth"  style="width: 100%; height: 20px;" type="text" size="30" maxlength="30" value="${not empty GatePassObj.dateOfBirth ? GatePassObj.dateOfBirth : ''}" readonly>   
					   <div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.dateOfBirthRegax"/></span>
                                </div>
					  <label id="error-dateOfBirth" style="color: red;display: none;">Please enter a valid Date Of Birth</label>
			</td>
               
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.gender"/></label></th>
                <td>
                    
				<select class="custom-select" name="gender" id="gender">
				<option value="">Please select Gender</option>
						<c:forEach var="option" items="${GenderOptions}">
							        <option value="${option.gmId}" ${GatePassObj.gender eq option.gmId ? 'selected="selected"' : ''}>
							         ${option.gmName}
        							</option>
						</c:forEach>
				</select>
                    <label id="error-gender" style="color: red;display: none;">Gender is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.idMark"/></label></th>
                <td>
                	<c:if test="${empty GatePassObj.idMark }">
                		<input id="idMark" name="idMark" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" autocomplete="off">
                	</c:if>
                	<c:if test="${not empty GatePassObj.idMark }">
                		<input id="idMark" name="idMark" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" value="${ GatePassObj.idMark }" autocomplete="off">
                	</c:if>
                	<div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.idMarkRegax"/></span>
    </div>
                	<label id="error-idMark"style="color: red;display: none;">Please enter a valid ID Mark</label>
                </td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mobileNumber"/></label></th>
                <td>
                	<c:if test="${empty GatePassObj.mobileNumber }">
                		<input id="mobileNumber" name="mobileNumber" style="width: 100%;height: 20px;" type="text" size="10" maxlength="10" autocomplete="off" inputmode="numeric"  pattern="^[6-9][0-9]{9}$"  oninput="this.value = this.value.replace(/[^0-9]/g, '')" onkeypress="if(this.value.length==0 && !/[6-9]/.test(event.key)) return false;">
                	</c:if>
                	<c:if test="${not empty GatePassObj.mobileNumber }">
                		<input id="mobileNumber" name="mobileNumber" style="width: 100%;height: 20px;" type="text" size="10" maxlength="10" value="${GatePassObj.mobileNumber }" autocomplete="off" inputmode="numeric"  pattern="^[6-9][0-9]{9}$"  oninput="this.value = this.value.replace(/[^0-9]/g, '')" onkeypress="if(this.value.length==0 && !/[6-9]/.test(event.key)) return false;">
                	</c:if>
                	<div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.mobileNumberRegax"/></span>
    </div>
                	<label id="error-mobileNumber" style="color: red;display: none;">Please enter a valid Mobile Number</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.maritalStatus"/></label></th>
                <td>
                	<select class="custom-select" name="maritalStatus" id="maritalStatus">
                        <option value="">Please select Marital Status</option>
                        	<option value="Unmarried" ${GatePassObj.maritalStatus eq 'Unmarried' ? 'selected="selected"' : ''}>Unmarried</option>
    						<option value="Married" ${GatePassObj.maritalStatus eq 'Married' ? 'selected="selected"' : ''}>Married</option>
                    </select>
                	<label id="error-maritalStatus"style="color: red;display: none;">Marital Status is required</label>
                </td>
            </tr>
            <tr>
            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.disability"/></label></th>				
				<td >
				    <select class="custom-select" id="disability"   name="disability"  >
						<option value="" >Select Disability</option>
						<option value="Yes" ${GatePassObj.disability eq 'Yes' ? 'selected="selected"' : ''}>Yes</option>
						<option value="No" ${GatePassObj.disability eq 'No' ? 'selected="selected"' : ''}>No</option>
				    </select>
						<label id="error-disability"style="color: red;display: none;">Disability is required</label>
						</td>
                  <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workmenType"/></label></th>
                            <td>  
                            <select class="custom-select"  id="workmenType" name="workmenType" >
       								 <option value="">Please select Workmen Type</option>
									
        							<c:forEach var="option" items="${WorkmenType}">
										<option value="${option.gmId}" ${GatePassObj.workmenType eq option.gmId ? 'selected="selected"' : ''}>
										${option.gmName}</option>
                        			</c:forEach>
									
    								</select>
    								 <label id="error-workmenType"style="color: red;display: none;">Workmen Type is required</label>
    						</td>
                  </tr>
           <tr>
           	 <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.address"/></label></th>
                <td>
                <c:if test="${empty GatePassObj.address }">
                	<input id="address" name="address" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" autocomplete="off">
                </c:if>
                <c:if test="${not empty GatePassObj.address }">
                	<input id="address" name="address" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" value="${GatePassObj.address  }" autocomplete="off">
                </c:if>
                <div style="text-align: right;">
        <span style="color: #666; font-size: 11px;"><spring:message code="label.addressRegax"/></span>
    </div>
                	<label id="error-address" style="color: red;display: none;">Please enter a valid Address</label>
                </td>
           </tr>
        </tbody>
    </table>
   
</div>

            <div id="tab2" class="tab-content">
                <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.principalEmployer"/></label></th>
                            <td><select class="custom-select" id="principalEmployer" name="principalEmployerId" onchange="getContractorsAndTrades(this.value, '${sessionScope.loginuser.userAccount}')">
                                <option value="">Please select Principal Employer</option>
                                
								<c:forEach var="pe" items="${PrincipalEmployer}">
								
                					<option value="${pe.id}"
									${GatePassObj.unitId eq pe.id ? 'selected="selected"':''}>
									${pe.description}</option>
            					</c:forEach>
							
                                </select>
                                <label id="error-principalEmployer"style="color: red;display: none;">Principal Employer is required</label>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractor"/></label></th>
                            <td>
                          
                            <select class="custom-select" id="contractor" name="contractorId" onchange="getWorkordersAndWC()">
            						<option value="">Please select Contractor</option>
									<c:forEach var="contr" items="${Contractors}">
										
                					<option value="${contr.contractorId}" ${GatePassObj.contractor eq contr.contractorId ? 'selected="selected"':''}>
									${contr.contractorName}</option>
            					</c:forEach>
        						</select>
        						
        						<label id="error-contractor"style="color: red;display: none;">Contractor is required</label>
        					</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workorder"/></label></th>
                            <td><select class="custom-select" id="workorder" name="workorderId" onchange="getWC()">
                                <option value="">Please select Workorder</option>
								<c:forEach var="wo" items="${Workorders}">
								
                					<option value="${wo.workorderId}"
									${GatePassObj.workorder eq wo.workorderId ? 'selected="selected"':''}>
									${wo.name}</option>
            					</c:forEach>
                                </select>
                                <label id="error-workorder" style="color: red;display: none;">Workorder is required</label>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.trade"/></label></th>
                            <td><select class="custom-select" id="trade" name="tradeId" onchange="getSkills()" >
                                <option value="">Please select Trade</option>
								<c:forEach var="pe" items="${Trades}">
								
                					<option value="${pe.tradeId}"
									${GatePassObj.trade eq pe.tradeId ? 'selected="selected"' : ''}>
									${pe.tradeName}</option>
            					</c:forEach>
								
								</select>
                                <label id="error-trade"style="color: red;display: none;">Trade is required</label>
                                </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.skill"/></label></th>
                            <td><select class="custom-select" id="skill" name="skillId" >
                                	<option value="">Please select Skill</option>
									
                                	 <c:forEach var="s" items="${Skills}">
									 
                						<option value="${s.skillId}"	${GatePassObj.skill eq s.skillId ? 'selected="selected"':''}>
										${s.skill}</option>
            						</c:forEach>
            						
                                </select>
                                <label id="error-skill" style="color: red;display: none;">Skill is required</label>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.department"/></label></th>
                            <td><select class="custom-select" id="department" name="departmentId" onchange="getAreabyDept(); getEic();">
                                <option value="">Please select Department</option>
								
                                 <c:forEach var="dept" items="${Departments}">
                						<option value="${dept.departmentId}" ${GatePassObj.department eq dept.departmentId ? 'selected="selected"':''}>
										${dept.department}</option>
            						</c:forEach>
								
                                </select>
                                <label id="error-department"style="color: red;display: none;">Department is required</label>
                                </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.area"/></label></th>
                            <td><select class="custom-select" id="subdepartment" name="subdepartmentId" >
                                <option value="">Please select Area</option>
								
                                 <c:forEach var="dept" items="${Subdept}">
                						<option value="${dept.subDepartmentId}" ${GatePassObj.subdepartment eq dept.subDepartmentId ? 'selected="selected"':''}>
										${dept.subDepartment}</option>
            						</c:forEach>
									
                                </select>
                                <label id="error-area"style="color: red;display: none;">Area is required</label>
                                </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.engineeringInCharge"/></label></th>
                            <td>
                           
                                <select class="custom-select" id="eic" name="eicId" >
            						<option value="">Please select EIC</option>
									<c:forEach var="pe" items="${Eic}">
									<option value="${pe.userId}"
									${GatePassObj.eic eq pe.fullName.toString() ? 'selected="selected"':''}>
									${pe.fullName}</option>
            					</c:forEach>
        						</select>
                                <label id="error-eic"style="color: red;display: none;">EIC is required</label>
                                </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.natureOfJob"/></label></th>
                            <td>
                            	<input id="natureOfJob" name="natureOfJob" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30"  value="${not empty GatePassObj.natureOfJob ? GatePassObj.natureOfJob : ''}" autocomplete="off">
                            	  <div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.natureOfJobRegax"/></span>
                                </div>
                            	  <label id="error-natureOfJob"style="color: red;display: none;">Please enter a valid Nature of Job</label>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.wcPolicyesicRegNumber"/></label></th>
                            <td><%-- <select class="custom-select" id="wc" name="wcId" >
                                <option value="">Please select WC Policy/ESIC Reg Number</option>
                                <c:forEach var="pe" items="${Wcs}">
								<c:if test="${pe.licenceType eq 'WC' || pe.licenceType eq 'ESIC'}">
                					<option value="${pe.wcId}"  ${GatePassObj.wcEsicNo eq pe.wcCode ? 'selected="selected"' : ''}>
									${pe.wcCode}</option>
									</c:if>
            					</c:forEach>
                                </select> --%>
                               <select class="custom-select" id="wc" name="wcId" onchange="onWcChange(this)">
    <option value="">Please select WC Policy / ESIC Reg Number</option>

    <c:forEach var="pe" items="${Wcs}">
        <c:if test="${pe.licenceType eq 'WC' || pe.licenceType eq 'ESIC'}">
           
                <option value="${pe.wcId}"
                 data-code="${pe.licenceType}"
                ${GatePassObj.wcEsicNo eq pe.wcCode ? 'selected="selected"' : ''}>
                ${pe.wcCode}
            </option>
        </c:if>
    </c:forEach>
</select>
                                <label id="error-wc"style="color: red;display: none;">WC Policy/ESIC Reg Number is required</label>
                                </td>
                                
                                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.labourLicenseNumber"/></label></th>
                            <td><select class="custom-select" id="ll" name="llId" >
                                <option value="">Please select Labor License Number</option>
                                <c:forEach var="pe" items="${Wcs}">
								<c:if test="${pe.licenceType eq 'LL'}">
                					<option value="${pe.wcId}"
									${GatePassObj.llNo eq pe.wcCode ? 'selected="selected"':''}>
									${pe.wcCode}</option>
									</c:if>
            					</c:forEach>
                                </select>
                                <label id="error-ll"style="color: red;display: none;">Labor License Number is required</label>
                                </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.hazardeousArea"/></label></th>
                            <td><select class="custom-select"  id="hazardousArea" name="hazardousArea">
                                <option value="">Please select Hazardous Area</option>
								<option value="Yes" ${GatePassObj.hazardousArea eq 'Yes' ? 'selected="selected"' : ''}>Yes</option>
								<option value="No" ${GatePassObj.hazardousArea eq 'No' ? 'selected="selected"' : ''}>No</option>
                                </select>
                                 <label id="error-hazardous"style="color: red;display: none;">Hazardous Area is required</label>
                                </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.accessArea"/></label></th>
                            <td>  
                            <select class="custom-select"  id="accessArea" name="accessArea" >
       								 <option value="">Please select Access Area</option>
									
        							<c:forEach var="option" items="${AccessArea}">
										<option value="${option.gmId}" ${GatePassObj.accessArea eq option.gmId ?'selected="selected"':''}>
										${option.gmName}</option>
                        			</c:forEach>
									
    								</select>
    								 <label id="error-accessArea"style="color: red;display: none;">Access Area is required</label>
    						</td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><spring:message code="label.uanNumber"/></label></th>
                            <td>
                            <c:if test="${empty GatePassObj.uanNumber }">
                            	<input id="uanNumber" name="uanNumber" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" autocomplete="off">
                            </c:if>
                            
                            <c:if test="${not empty GatePassObj.uanNumber }">
                            	<input id="uanNumber" name="uanNumber" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" value="${GatePassObj.uanNumber }" autocomplete="off">
                            </c:if>
                            <div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.uanNumberRegax"/></span>
                                </div>
                            		<label id="error-uanNumber"style="color: red;display: none;">Please enter a valid UAN Number</label>
                            </td>
                            <th><label class="custom-label"><spring:message code="label.healthCheckUpDate"/></label></th>
                            <td>
                            	<c:if test="${ empty GatePassObj.healthCheckDate }">
                            		<input id="healthCheckDate" name="healthCheckDate" class="datetimepickerformat" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                            
                            	</c:if>
                            	<c:if test="${ not empty GatePassObj.healthCheckDate }">
                            		<input id="healthCheckDate" name="healthCheckDate" class="datetimepickerformat" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" value="${GatePassObj.healthCheckDate }" autocomplete="off">
                            
                            	</c:if>
                            	 <div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.healthCheckUpDateRegax"/></span>
                                </div>
                            		<label id="error-healthCheckDate"style="color: red;display: none;">Please enter a valid Health Check Up Date</label>
                            	</td>
                        </tr>
                        <tr>

                        

                            <th><label class="custom-label"><spring:message code="label.pfNumber"/></label></th>
                            <td>
                            <c:if test="${empty GatePassObj.pfNumber }">
                            	<input id="pfNumber" name="pfNumber" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" autocomplete="off">
                            </c:if>
                            
                            <c:if test="${not empty GatePassObj.pfNumber }">
                            	<input id="pfNumber" name="pfNumber" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" value="${GatePassObj.pfNumber }" autocomplete="off">
                            </c:if>
                            <div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.pfNumberRegax"/></span>
                                </div>
                            		<label id="error-pfNumber"style="color: red;display: none;">Please enter a valid PF Number</label>
                            </td>
                           <th>
   <label class="custom-label">
    <span id="esicRequiredStar" class="required-field" style="display:none">*</span>
    <spring:message code="label.esicNumber"/>
</label>


</th>

<td>
    <div id="esicNumberSection" style="display:none;">
    <input id="esicNumber"
           name="esicNumber"
           type="text"
           maxlength="10"
           autocomplete="off"
           inputmode="numeric"
           pattern="[0-9]*"
           oninput="this.value=this.value.replace(/[^0-9]/g,'')">

    <label id="error-esicNumber" style="color:red;display:none;">
        Please enter a valid ESIC Number
    </label>
</div>

</td>
                        </tr>
                        <tr>
                         <th><label class="custom-label"><spring:message code="label.dateOfJoining"/></label></th>
                        	<td>
                        	<c:if test="${ empty GatePassObj.doj }">
                        		<input id="doj" name="doj" class="datetimepickerformat1" style="width: 100%; height: 20px;" type="text" size="30" maxlength="30"  autocomplete="off"  >
                        	</c:if>
    				
    						<c:if test="${ not empty GatePassObj.doj }">
                        		<input id="doj" name="doj" class="datetimepickerformat1" style="width: 100%; height: 20px;" type="text" size="30" maxlength="30"    value="${ GatePassObj.doj}" autocomplete="off">
                        	</c:if>
                        	 <div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.dateOfJoiningRegax"/></span>
                                </div>
					  <label id="error-doj" style="color: red;display: none;">Please enter a valid Date Of Joining</label>
			</td>
			<th><label class="custom-label"><spring:message code="label.pfApplicable"/></label></th>
                      <td><input type="checkbox" id="pfApplicable" name="pfApplicable"
    <c:if test="${GatePassObj.pfApplicable eq 'Yes'}">checked</c:if>
    onclick="validatePfForm11Requirement()" />

                     <!--  <label for="pfApplicable">Yes</label> -->
                      <!-- <label id="error-pfApplicable"style="color: red;display: none;">Please check pfApplicable</label> -->
                     </td>
                        </tr>
                        <%-- <tr>
                            <th><label class="custom-label"><spring:message code="label.pfNumber"/></label></th>
                            <td>
                            	<input id="pfNumber" name="pfNumber" style="width: 100%;height: 20px;" type="text" value="${GatePassObj.pfNumber }" readonly>
                            </td>
                            <th><label class="custom-label"><spring:message code="label.esicNumber"/></label></th>
                            <td>
                            	<input id="esicNumber" name="esicNumber"  style="width: 100%;height: 20px;" type="text" value="${GatePassObj.esicNumber }" readonly>
                            	</td>
                        </tr> --%>
                       <%--  <tr>
                            <th><label class="custom-label"><spring:message code="label.pfApplicable"/></label></th>
                            
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
				<select class="custom-select" id="bloodGroup"   name="bloodGroupId"  >
						<option value="" >Select Blood Group</option>
						
						<c:forEach var="option" items="${BloodGroup}">
                            <option value="${option.gmId}" ${GatePassObj.bloodGroup eq option.gmId ? 'selected="selected"' :''}>
							${option.gmName}</option>
                        </c:forEach>
						
						</select>
						<label id="error-bloodGroup"style="color: red;display: none;">Blood Group is required</label>
				</td>
				
			
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.accommodation"/></label></th>				
				<td ><select class="custom-select" id="accommodation"   name="accommodation"  >
						<option value="" >Select Accommodation</option>
						<option value="Yes" ${GatePassObj.accommodation eq 'Yes' ? 'selected="selected"' : ''}>Yes</option>
								<option value="No" ${GatePassObj.accommodation eq 'No' ? 'selected="selected"' : ''}>No</option>
						
						
						</select>
						<label id="error-accommodation"style="color: red;display: none;">Accommodation is required</label>
						</td>
				 </tr>
			<tr>
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.academic"/></label></th>				
				<td >
					<select class="custom-select"  id="academic"   name="academicId" >
					<option value="" >Select Educational Qualification</option>
					
							<c:forEach var="option" items="${Academics}">
                            <option value="${option.gmId}" ${GatePassObj.academic eq option.gmId? 'selected="selected"' :''}>
							${option.gmName}</option>
                        </c:forEach>
						
					</select>
					
					<label id="error-academic"style="color: red;display: none;">Academic is required</label>
				</td>
				
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.technical"/></label></th>				
				<td >
					<select class="custom-select" id="technical"    name="technical"  >
						<option value="" >Select Technical</option>
						
						<option value="Technical" ${GatePassObj.technical eq 'Technical' ? 'selected="selected"' : ''}>Technical</option>
						<option value="NonTechnical" ${GatePassObj.technical eq 'NonTechnical' ? 'selected="selected"' : ''}>NonTechnical</option>
								
						
					</select>
					<label id="error-technical"style="color: red;display: none;">Technical is required</label>
				</td>
				</tr>
			
		   <tr>
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.ifscCode"/></label></th>
				<td >
				<c:if test="${ empty GatePassObj.ifscCode }">
				<input  style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" id="ifscCode" name="ifscCode"    maxlength="11"  autocomplete="off" />
				
				</c:if>
				
				<c:if test="${not empty GatePassObj.ifscCode }">
				<input  style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" id="ifscCode" name="ifscCode"    maxlength="11"  value="${ GatePassObj.ifscCode}" autocomplete="off"/>
				
				</c:if>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.ifscCodeRegax"/></span>
                                </div>
				<label id="error-ifscCode"style="color: red;display: none;">Please enter a valid IFSC Code</label>
				</td>
			<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.accountNumber"/></label></th>
				<td >
				<c:if test="${ empty GatePassObj.accountNumber }">
				<input style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" id="accountNumber" name="accountNumber"  autocomplete="off"  />
				</c:if>
				<c:if test="${not  empty GatePassObj.accountNumber }">
				<input style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" id="accountNumber" name="accountNumber"   value="${GatePassObj.accountNumber  }" autocomplete="off"/>
				</c:if>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.accountNumberRegax"/></span>
                                </div>
				<label id="error-accountNumber"style="color: red;display: none;">Please enter a valid Account Number</label>
				</td>
			</tr>
			
		   <tr>
				<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.emergencyContactName"/></label></th>
				<td>
				<c:if test="${ empty GatePassObj.emergencyName }">
					<input style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" id="emergencyName" name="emergencyName"    maxlength="30"  autocomplete="off"/>
				</c:if>
				<c:if test="${not  empty GatePassObj.emergencyName }">
					<input style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" id="emergencyName" name="emergencyName"    maxlength="30"  value="${GatePassObj.emergencyName }" autocomplete="off"/>
				</c:if>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.emergencyContactNameRegax"/></span>
                                </div>
					<label id="error-emergencyName"style="color: red;display: none;">Please enter a valid Emergency Name</label>
				</td>			
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.emergencyContactNumber"/></label></th>
				<td>
				<c:if test="${ empty GatePassObj.emergencyNumber }">
				<input style="width: 100%;height: 20px;" type="text" size="30" id="emergencyNumber" name="emergencyNumber"    maxlength="10"   autocomplete="off" inputmode="numeric"  pattern="^[6-9][0-9]{9}$"  oninput="this.value = this.value.replace(/[^0-9]/g, '')" onkeypress="if(this.value.length==0 && !/[6-9]/.test(event.key)) return false;">
				</c:if>
				
				<c:if test="${not empty GatePassObj.emergencyNumber }">
				<input style="width: 100%;height: 20px;" type="text" size="30" id="emergencyNumber" name="emergencyNumber"    maxlength="10"   value="${GatePassObj.emergencyNumber }"  autocomplete="off" inputmode="numeric"  pattern="^[6-9][0-9]{9}$"  oninput="this.value = this.value.replace(/[^0-9]/g, '')" onkeypress="if(this.value.length==0 && !/[6-9]/.test(event.key)) return false;">
				</c:if>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.emergencyContactNumberRegax"/></span>
                                </div>
					<label id="error-emergencyNumber"style="color: red;display: none;">Please enter a valid Emergency Number</label>
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
					<select class="custom-select" id="wageCategory"    name="wageCategoryId"  >
						<option value=""> Select Workmen Wage Category</option>
						
						<c:forEach var="option" items="${WageCategory}">
                            <option value="${option.gmId}" ${GatePassObj.wageCategory eq option.gmId?'selected="selected"':''}>
							${option.gmName}</option>
                        </c:forEach>
						
					</select>
						<label id="error-wageCategory"style="color: red;display: none;">Workmen Wage Category is required</label>
				</td>
				
				<th><label class="custom-label"><spring:message code="label.bonusPayout"/></label></th>
				<td >
					<select class="custom-select" id="bonusPayout"    name="bonusPayoutId"  >
						<option value=""> Select Bonus Payout</option>
						
						<c:forEach var="option" items="${BonusPayout}">
                            <option value="${option.gmId}" ${GatePassObj.bonusPayout eq option.gmId?'selected="selected"':''}>
							${option.gmName}</option>
                        </c:forEach>
						
					</select>
					<label id="error-bonusPayout"style="color: red;display: none;">Bonus Payout is required</label>
				</td>
				</tr>
        <tr>
				<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.zone"/></label></th>
				<td >
					<select class="custom-select" id="zone"    name="zoneId"  >
						<option value=""> Select Zone</option>
						<c:forEach var="option" items="${Zone}">
                            <option value="${option.gmId}" ${GatePassObj.zone eq option.gmId ? 'selected="selected"' : ''}>${option.gmName}</option>
                        </c:forEach>
					</select>
					<label id="error-zone"style="color: red;display: none;">Zone is required</label>
				</td>
		
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.basic"/></label></th>
			
				<td><input style="width: 100%;height: 20px;" type="text" size="30" name="basic" id="basic"   value="${not empty GatePassObj.basic ? GatePassObj.basic : ''}" autocomplete="off"/>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.basicRegax"/></span>
                                </div>
				<label id="error-basic"style="color: red;display: none;">Enter valid Basic</label>
				</td>						
			</tr>
        <tr>
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.da"/></label></th>
				<td ><input style="width: 100%;height: 20px;" type="text" size="30" name="da" id="da" value="${not empty GatePassObj.da ? GatePassObj.da : ''}" autocomplete="off"/>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.daRegax"/></span>
                                </div>
				<label id="error-da"style="color: red;display: none;">Enter valid DA</label>
				</td>				
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.hra"/></label></th>
				<td ><input style="width: 100%;height: 20px;" type="text" size="30" name="hra"  id="hra" value="${not empty GatePassObj.hra ? GatePassObj.hra : ''}" autocomplete="off"/>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.hraRegax"/></span>
                                </div>
				<label id="error-hra"style="color: red;display: none;">Enter valid HRA</label>
				</td>				
			
			</tr>
         <tr>
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.washingAllowance"/></label></th>
				<td>
				<input style="width: 100%;height: 20px;" type="text" size="30" name="washingAllowance" id="washingAllowance" value="${not empty GatePassObj.washingAllowance ? GatePassObj.washingAllowance : ''}" autocomplete="off"/>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.washingAllowanceRegax"/></span>
                                </div>
				<label id="error-washingAllowance"style="color: red;display: none;">Enter valid Washing Allowance</label>
				</td>				
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.otherAllowance"/></label></th>
				<td><input style="width: 100%;height: 20px;" type="text" size="30" name="otherAllowance" id="otherAllowance"  value="${not empty GatePassObj.otherAllowance ? GatePassObj.otherAllowance : ''}" autocomplete="off"/>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.otherAllowanceRegax"/></span>
                                </div>
				<label id="error-otherAllowance"style="color: red;display: none;">Enter valid Other Allowance</label>
				</td>				
			</tr>
        <tr>
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.uniformAllowance"/></label></th>
				<td><input style="width: 100%;height: 20px;" type="text" size="30" name="uniformAllowance" id="uniformAllowance" value="${not empty GatePassObj.uniformAllowance ? GatePassObj.uniformAllowance : ''}" autocomplete="off"/>
				<div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;"><spring:message code="label.uniformAllowanceRegax"/></span>
                                </div>
				<label id="error-uniformAllowance"style="color: red;display: none;">Enter valid Uniform Allowance</label>
				</td>				
			
			<th><label class="custom-label"><span class="required-field"></span><spring:message code="label.pfcap"/></label></th>
			 <td>
        <select class="custom-select" id="pfCap" name="pfCap"   >
            <option value="Yes" <c:if test="${pfCap == 'Yes'}">selected</c:if>>Yes</option>
            <option value="No" <c:if test="${pfCap == 'No'}">selected</c:if>>No</option>
        </select>
        <label id="error-pfCap"style="color: red;display: none;">PF Cap is required</label>
    </td>
			</tr>
               </tbody>
                </table>
            </div>

              <div id="tab5" class="tab-content">
            <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                   <tr>
                   

                  <td>

  <!-- Label -->
  <label for="imageFile">
    <span class="required-field">*</span>
    <spring:message code="label.uploadPhoto"/>
  </label>

  <!-- Preview Box -->
  <div id="preview" style="display: flex; flex-direction: column; justify-content: flex-end; height: 200px; width: 200px; border: 1px solid #ccc; margin-bottom: 10px;">
  </div>

  <!-- File & Camera Side by Side -->
  <div style="display: flex; align-items: center;">
    <!-- 📁 Choose File -->
    <input type="file" id="imageFile" name="imageFile" accept="image/*"
           onchange="previewImage(event,'imageFile','imageFileName')"
           style="overflow: hidden;"/>

    <!--  Use Camera -->

     <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="openCamera()">
      Use Camera
    </button> 

  </div>

  <!-- File Name Display -->
  <span id="imageFileName" style="margin-left: 10px; color: black;"></span>

  <!-- Webcam Stream -->
  <video id="webcam" autoplay playsinline style="width: 200px; display: none; border: 1px solid gray; margin-top: 10px;"></video>

  <!-- Capture / Cancel Buttons -->
  <div id="cameraButtons" style="display: none; margin-top: 8px;">
    <button type="button" onclick="captureImage()" style="color: black;">Capture</button>
    <button type="button" onclick="closeCamera()" style="color: black; margin-left: 10px;">Cancel</button>
  </div>

  <!-- Hidden Canvas -->
  <canvas id="canvas" style="display: none;"></canvas>

  <!-- Error Display -->
  <div id="profilePcError"></div>

</td>

						
</tr><tr>
                		<td>
                		 	<label for="aadharFile"><span class="required-field">*</span><spring:message code="label.uploadAadharCard"/></label>
       					 	<input type="file" id="aadharFile" name="aadharFile" accept="application/pdf,image/jpeg,image/png" onchange="displayFileName1('aadharFile', 'aadharFileName')">
       					 	  <span id="aadharFileName" style="margin-left: 10px;color:black;"></span> 
        					<div id="aadharError"></div> <!-- Error message for Aadhar file -->
                		</td>
                       <td>
                		 	<label for="appointmentFile"><span class="required-field">*</span><spring:message code="label.uploadappointmentCard"/></label>
       					 	<input type="file" id="appointmentFile" name="appointmentFile" accept="application/pdf,image/jpeg,image/png" onchange="displayFileName1('appointmentFile', 'appointmentFileName')">
       					 	  <span id="appointmentFileName" style="margin-left: 10px;color:black;"></span> 
        					<div id="appointmentError"></div> <!-- Error message for appointment file -->
                		</td>
                </tr>  		
           <tr>
                	  <td>
                			<label for="policeFile"><span class="required-field">*</span><spring:message code="label.uploadPoliceVerificationReport"/></label> 

                			<input type="file"	id="policeFile" name="policeFile" accept="application/pdf,image/jpeg,image/png" onchange="displayFileName1('policeFile', 'policeFileName')">
                			  <span id="policeFileName" style="margin-left: 10px;color:black;"></span> 
							<div id="policeError"></div> <!-- Error message for Police file -->
						</td>

						
					
                         <td><label for="policeDate"><span class="required-field">*</span><spring:message code="label.policeVerificationDate"/></label>

                       <input id="policeVerificationDate" name="policeVerificationDate" class="datetimepickerformat3" style="margin-left: 10px;color:black;" type="text" size="30" maxlength="30"  autocomplete="off"  >
                        	<%-- <c:if test="${ empty GatePassObj.policeVerificationDate }">
                        		<input id="policeVerificationDate" name="policeVerificationDate" class="datetimepickerformat3" style="margin-left: 10px;color:black;" type="text" size="30" maxlength="30"  autocomplete="off"  >
                        	</c:if>    				
    						<c:if test="${ not empty GatePassObj.policeVerificationDate }">
                        		<input id="policeVerificationDate" name="policeVerificationDate" class="datetimepickerformat3" style="margin-left: 10px;color:black;" type="text" size="30" maxlength="30"    value="${ GatePassObj.policeVerificationDate}" autocomplete="off">
                        	</c:if> --%>
                        	<!-- <div style="text-align: right;">
                                    <span style="color: #666; font-size: 11px;">Within Last 1 Year from Today</span>
                                </div> -->
					  <label id="error-policeVerificationDate" style="color: red;display: none;">Please enter a valid Police Verification Date </label>
			         </td>

            		</tr>
            		
            		
            		<tr><td>
            		<a href="#" id="add_field_button" onclick="additionalDocUpload()"><spring:message code="label.addDocument"/></a>
            		<label>You can add a maximum of 7 additional documents.</label>
            		<div id="form11-error-message" style="color: red; display: none; margin-top: 5px; font-weight: bold "></div>
            		
            		</td>
            		<td><div id="additionalDoc" ></div></td></tr>
      		
        
        <tr>
            <td colspan="20">
                <div>
                    <p id="p3"><b><font color="darkblue" size="3"><span class="required-field">*</span><spring:message code="label.comments"/></font></b></p>
                    <hr10 style="color:rgb(0, 102, 204);">
                </div>
            </td>
        </tr>
        <tr>
				<!-- <th><label class="custom-label">Previous Comment</label></th>
				<td><input type="textarea" name="value(prevComment)" style="width:220px;height:100px;text-transform: capitalize;" readonly="true" cols="35" rows="7"  onchange="setDataChanged();"/></td>
				 --><th><label class="custom-label"><spring:message code="label.comment"/></label></th>
				<td><textarea id="comments"  name="comments" placeholder="Type here..." style="width: 501px; height: 70px;text-transform: capitalize;"></textarea>
				<label id="error-comments" style="color: red;display: none;">Enter comments</label>
				</td>
			</tr>
		<tr>
		</tr>
      		<tr >    	
				<td colspan="6" style="font-family: Arial, sans-serif; color: #898989; font-size: 14px; line-height: 1.5;"><b>
				<input type="checkbox" name="acceptCheck" id="acceptCheck"  /> 
				I hereby certify that the details given above are true and correct to the best of my or our knowledge and belief, and nothing has been concealed herein. I or my company will take full responsibility for the conduct and behavior of the persons engaged by me or our company to work or visit premises. I/we will ensure that they are briefed on all traffic, safety, and security rules and procedures of company where they have been engaged by us for work. In case of any breach or violation of rules, regulations, safety policy, or other applicable procedures by the above person, we will be solely responsible and liable for suitable action as per company's safety and security policy
				</b>
				<label id="acceptError" style="color: red;display: none;">You must accept the declaration</label></td>
			</tr>
				<!-- <tr >
				<td colspan="4"><input type="checkbox" name="acceptCheck" id="acceptCheck"  />
				<label class="custom-label"> I acceptthe <u>Terms and Conditions</u></label> 
				</td>
			   </tr> -->
        <!-- Add any additional rows for Approver Comments -->
                    
                </tbody>
                </table>
            </div>
          <%@ page import="java.util.*" %>

<%-- <div id="tab6" class="tab-content">
  <div class="card-body p-3">
        <table class="table table-bordered table-striped align-middle">
            <thead class="table-light">
                <tr class="text-center">
                    <th style="width: 35%;color:black;">Document Type</th>
                    <th style="width: 45%;color:black;">File Name</th>
                    <th style="width: 20%;color:black;">Version</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="doc" items="${PreviousDocuments}" varStatus="loop">
                    <c:choose>
                        
                        <c:when test="${loop.first or doc.DOCTYPE ne PreviousDocuments[loop.index - 1].DOCTYPE}">
                            <tr>
                                <td style="color:black;">${doc.DOCTYPE}</td>
                                <td>
                                    <a href="javascript:void(0);" 
                                       onclick="downloadPreviousFile('${transactionId}', '${doc.FILENAME}')"
                                       class="text-primary text-decoration-underline">
                                        ${doc.FILENAME}
                                    </a>
                                </td>
                                <td style="color:black;">
                                    <span class="badge bg-info text-dark">V${doc.VERSIONNO}</span>
                                    <span>V${doc.VERSIONNO}</span>
                                </td>
                            </tr>
                        </c:when>

                        
                        <c:otherwise>
                            <tr>
                                <td></td> <!-- Empty cell to visually group -->
                                <td>
                                    <a href="javascript:void(0);" 
                                       onclick="downloadPreviousFile('${transactionId}', '${doc.FILENAME}')"
                                       class="text-primary text-decoration-underline">
                                        ${doc.FILENAME}
                                    </a>
                                </td>
                                <td style="color:black;"><!-- class="text-center"> -->
                                    <span>V${doc.VERSIONNO}</span>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div> --%>



        </f:form>
    </div>

</body>
 
 
</html>
