<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
    <script src="resources/js/cms/workmen.js"></script>
   
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



@media screen and (max-width: 768px) {
    table,
    tbody,
    tr,
    th,
    td {
        display: block;
        width: 100%;
        box-sizing: border-box;
    }

    table tr {
        margin-bottom: 15px;
        border-bottom: 1px solid #ddd;
        padding-bottom: 10px;
    }

    table th {
        text-align: left;
        padding: 5px 0;
        font-weight: bold;
        background: none;
    }

    table td {
        padding: 5px 0;
    }

    input,
    select,
    textarea
     {
        width: 100% !important;
        max-width: 100%;
        box-sizing: border-box;
    }

    textarea {
        min-height: 70px;
    }

    /* button {
        margin-top: 6px;
    } */

    label[id^="error-"],
    #otpMessage,
    #otpError {
        display: block;
        margin-top: 4px;
        font-size: 12px;
    }

    #preview,
    video,
    canvas {
        width: 100% !important;
        height: auto;
    }

    div[style*="display: flex"] {
        flex-direction: column !important;
        align-items: stretch !important;
    }
/* 
    button[onclick*="openCamera"] {
        margin-left: 0 !important;
        margin-top: 6px;
        width: 100%;
    } */

/* ///////////////////////////////////////// */
    

    body {
        overflow-x: hidden;
        font-size: 14px;
    }

    .container,
    .main-container,
    .content,
    .form-container {
        width: 100% !important;
        padding: 10px !important;
        margin: 0 !important;
    }

    h1, h2, h3 {
        font-size: 18px;
        text-align: center;
    }

    form {
        width: 100%;
    }

    .form-group,
    .row {
        display: block !important;
        width: 100% !important;
    }

    label {
        font-size: 13px;
        margin-bottom: 5px;
        display: block;
    }

    input[type="text"],
    input[type="date"],
    input[type="email"],
    input[type="number"],
    select,
    textarea {
        width: 100% !important;
        font-size: 14px;
        padding: 8px;
        margin-bottom: 10px;
    }

    button,
    .btn {
        width: 100%;
        font-size: 13px;
        padding: 3px;
        margin-bottom: 10px;
    }

    .tabs,
    .tab-container {
        flex-direction: column;
        display: block;
    }

    .tab {
        width: 100%;
        text-align: center;
        margin-bottom: 5px;
    }

    table {
        width: 100%;
        font-size: 12px;
        display: block;
        overflow-x: auto;
        white-space: nowrap;
    }

    th, td {
        padding: 6px;
    }

    .modal,
    .popup {
        width: 95% !important;
        margin: auto;
    }

    .tabs-container {
        display: block !important;
        width: 100%;
    }

      .tabs {
        display: flex !important;
        flex-wrap: nowrap;
        overflow-x: auto;
        /* gap: 6px;
        padding-bottom: 6px; */
        scrollbar-width: none; 
    }

    .tabs::-webkit-scrollbar {
        display: none; 
    }

    .tabs button {
        flex: 0 0 auto;
        white-space: nowrap;
        padding: 6px 10px;
        font-size: 12px;
        min-width: auto;
        border-radius: 4px;
    }
    /* .action-buttons {
        display: block !important;
        width: 100%;
        margin-top: 15px;
    } */

    /* .action-buttons button {
        width: 100%;
        margin: 5px 0;
        padding: 10px;
        font-size: 14px;
        box-sizing: border-box;
    } */

    
  input[type="date"] {
        position: static !important;
        width: auto !important;
        max-width: 100% !important;
    }
    input[type="date"] {
        display: inline-block !important;
    }
      #ui-datepicker-div,
    #ui-datepicker-div table,
    #ui-datepicker-div thead,
    #ui-datepicker-div tbody,
    #ui-datepicker-div tr,
    #ui-datepicker-div th,
    #ui-datepicker-div td {
        /* display: table !important; */
        width: auto !important;
        max-width: none !important;
    }

    #ui-datepicker-div tr {
        display: table-row !important;
    }

    #ui-datepicker-div th,
    #ui-datepicker-div td {
        display: table-cell !important;
        padding: 4px !important;
    }

    #ui-datepicker-div {
        font-size: 12px;
        z-index: 9999 !important;
    }

     .declaration-row,
    .declaration-row td {
        display: block !important;
        width: 100% !important;
    }

    .declaration-row td {
        padding: 12px !important;
        font-size: 13px !important;
        line-height: 1.5 !important;
        white-space: normal !important;
    }

    .declaration-row input[type="checkbox"] {
        margin-right: 8px;
        transform: scale(1.2);
        vertical-align: top;
    }

    .declaration-row b {
        display: block;
        font-weight: 500;
    }

    #acceptError {
        display: block;
        margin-top: 6px;
        font-size: 12px;
    }
    

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
.ocr-box {
    display: inline-block;      /* Prevents column stretching */
    max-width: 380px;           /* Keeps box controlled */
    padding: 10px;
    border: 1px solid #d9d9d9;
    border-radius: 10px;
    background-color: #f7f7f7;
    box-sizing: border-box;
}

.file-row {
    display: flex;
    align-items: center;
    gap: 8px;
    width: 100%;
}

.file-row input[type="file"] {
    flex-shrink: 0;            /* Prevent shrinking */
}

/* Style your custom filename */
.file-name {
    margin-left: 10px;
    max-width: 220px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-size: 13px;
    color: #555;
}

.ocr-btn {
    margin-top: 8px;
    white-space: nowrap;
}
/* Hide default filename text but keep button */
.custom-file-input {
    width: 120px;          /* show only button area */
    overflow: hidden;
}

/* For Chrome, Edge, Safari */
.custom-file-input::-webkit-file-upload-button {
    cursor: pointer;
}

/* For modern browsers */
.custom-file-input::file-selector-button {
    cursor: pointer;
}
.file-row input[type="file"] {
    flex-shrink: 0;
     overflow: hidden;
}


    </style>
     <%
    MasterUser user = (MasterUser) session.getAttribute("loginuser");
    String userId = user != null && user.getUserId() != null ? String.valueOf(user.getUserId()) : "";
%>

	
<script>
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

   





    document.addEventListener("DOMContentLoaded", function() {
        // Set the default tab
        showTab('tab1');
        initializeDatePicker();
    });
       
   
</script>
</head>
<body>
   
    <div id="principalEmployerContent">
       <div class="tabs-container">
        <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTab('tab1')">Employment Information</button>
          
        </div>
         <div class="action-buttons" >
            <button id="saveButton" style="display:none;" type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGatePass('${sessionScope.loginuser.userId}','regular')">Save</button>
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="goBackToonboardingList()">Cancel</button>
        </div> 
    </div>

<div id="tab1" class="tab-content active">
                <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
  <input type="hidden" id="loggedInUserAccount" value="${sessionScope.loginuser.userAccount}">
<tr>
<tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.principalEmployer"/></label></th>
                            <td><select class="custom-select" id="principalEmployer" name="principalEmployerId" onchange="getContractorsAndTrades(this.value,document.getElementById('loggedInUserAccount').value)">
                                <option value="">Please select Principal Employer</option>
                                
                                
                                
								<c:forEach var="pe" items="${principalEmployers}">
								
                					<option value="${pe.id}" 
									>
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
										
                					<option value="${contr.contractorId}" ${GatePassObj.contractor eq contr.contractorId ? 'selected="selected"' : ''}>
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
									${GatePassObj.workorder eq wo.workorderId ? 'selected="selected"' : ''}>
									${wo.name}</option>
            					</c:forEach>
                                </select>
                                <label id="error-workorder" style="color: red;display: none;">Workorder is required</label>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.department"/></label></th>
                            <td><select class="custom-select" id="department" name="departmentId" onchange="getAreabyDept(); getEic();">
                                <option value="">Please select Department</option>
								
                                 <c:forEach var="dept" items="${Departments}">
                						<%-- <option value="${dept.departmentId}" ${GatePassObj.department eq dept.departmentId ? 'selected="selected"' : ''}>
										${dept.department}</option> --%>
								   <option value="${dept.departmentId}" ${GatePassObj.department != null 
										        and GatePassObj.department != '' and GatePassObj.department.toString() eq dept.departmentId.toString() ? 'selected="selected"' : ''}>
                                                      ${dept.department}
                                         </option>
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
                						<option value="${dept.subDepartmentId}" ${GatePassObj.subdepartment eq dept.subDepartmentId.toString() ? 'selected="selected"' : ''}>
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
									${not empty GatePassObj.eic and GatePassObj.eic.toString() == pe.fullName.toString() ? 'selected="selected"' : ''}>
 										 ${pe.fullName}
									</option>
									
            					</c:forEach>
        						</select>
                                <label id="error-eic"style="color: red;display: none;">EIC is required</label>
                                </td>
                        </tr>
                        <tr>
                        <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.wcPolicyesicRegNumber"/></label></th>
                            <td><select class="custom-select" id="wc" name="wcId" onchange="onWcChange(this)">
                                  <option value="">Please select WC Policy / ESIC Reg Number</option>
                                      <c:forEach var="pe" items="${Wcs}">
                                           <c:if test="${pe.licenceType eq 'WC' || pe.licenceType eq 'ESIC'}">
                                             <option value="${pe.wcId}" data-code="${pe.licenceType}"
                                                ${GatePassObj.wcEsicNo eq pe.wcCode ? 'selected="selected"' : ''}>
                                                  ${pe.wcCode}
                                             </option>
                                           </c:if>
                                      </c:forEach>
                                </select>
                                 <label id="error-wc"style="color: red;display: none;">WC Policy/ESIC Reg Number is required</label>
                             </td>
                                 <th><label class="custom-label"><spring:message code="label.labourLicenseNumber"/></label></th>
                            <td><select class="custom-select" id="ll" name="llId" >
                                <option value="">Please select Labor License Number</option>
                                <c:forEach var="pe" items="${Wcs}">
								<c:if test="${pe.licenceType eq 'LL'}">
                					<option value="${pe.wcId}" ${GatePassObj.llNo eq pe.wcCode ? 'selected="selected"' : ''}>
									${pe.wcCode}
									</option>
									</c:if>
            					</c:forEach>
                                </select>
                                <label id="error-ll"style="color: red;display: none;">Labor License Number is required</label>
                                </td>
                        </tr>
                        <tr>
                         <th><label class="custom-label"><span id="esicRequiredStar" class="required-field" style="display:none">*</span><spring:message code="label.esicNumber"/></label></th>
                              <td>
                                <div id="esicNumberSection" style="display:none;">
                                 <!--  <input id="esicNumber" name="esicNumber" type="text" maxlength="10" autocomplete="off" inputmode="numeric" pattern="[0-9]*" oninput="this.value=this.value.replace(/[^0-9]/g,'')"> -->
                                     <!-- <label id="error-esicNumber" style="color:red;display:none;">Please enter a valid ESIC Number</label> -->
                                     
                            <c:if test="${empty GatePassObj.esicNumber }">
                            	<input id="esicNumber" name="esicNumber"  type="text" size="30" maxlength="10" autocomplete="off" inputmode="numeric" pattern="[0-9]*"  oninput="this.value = this.value.replace(/[^0-9]/g, '')">
                            </c:if>
                            
                            <c:if test="${not empty GatePassObj.esicNumber }">
                            	<input id="esicNumber" name="esicNumber" type="text" size="30" maxlength="10" value="${GatePassObj.esicNumber }" autocomplete="off" inputmode="numeric" pattern="[0-9]*"  oninput="this.value = this.value.replace(/[^0-9]/g, '')">
                            </c:if>
                            
                                </div>
                                   <label id="error-esicNumber" style="color:red;display:none;">Please enter a valid ESIC Number</label>
                               </td>
                </tr>
            </tbody></table>
</div>
   
</body>
</html>
