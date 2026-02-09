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
        body {
	background-color: #FFFFFF; /* White background for the page */
	font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
	overflow-y: scroll; /* Adds a vertical scroll bar */
	 overflow-x: auto; 
}
        
    </style>
     <%
    	MasterUser user = (MasterUser) session.getAttribute("loginuser");
        String userId = user != null ? user.getUserId() : "";
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
        showTab('tab1');
        
    });
    function validateTab() {
        let isValid = true;

        // Call a validation function for the single tab
        isValid = validateRenewFormData();

        return isValid;
    }   

    
  
  

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
            <button  data-target="tab1" onclick="showTab('tab1')">Basic Data</button>
           <!--  <button data-target="tab2" onclick="showTab('tab2')">Employment Information</button>
            <button data-target="tab3" onclick="showTab('tab3')">Other Information</button>
            <button data-target="tab4" onclick="showTab('tab4')">Wages</button>
            <button data-target="tab5" onclick="showTab('tab5')">Documents</button> -->
        </div>
         <div class="action-buttons" >
            <button id="saveButton"  type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveRenewDetails()">Save</button>
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitForm()">Cancel</button>
        </div> 
    </div> 

        <%-- <f:form id="addAadharOBForm" action="/CWFM/contractworkmen/addAadharOB" modelAttribute="workmenbyAadhar" method="post">
            <div id="errorContainer">
                <c:if test="${not empty errors}">
                    <p><span class="required-field">*</span> Indicates Required fields.</p>
                    <div class="error-message">
                        <f:errors path="*" cssClass="error" element="div" />
                    </div> 
                </c:if>
            </div>
            </f:form> --%>
<div id="tab1" class="tab-content active ">
    <table cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorRenewalId"/></label></th>
                <td>
                	<input id="renewalid" name="renewalid" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-renewalid" style="color: red;display: none;">Renewal ID is required</label>
                </td>
           </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitCode"/></label></th>
                <td>
                	<input id="unitcode" name="unitcode" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	 <label id="error-unitcode" style="color: red;display: none;">Unit Code is required</label>
                </td>
          
            
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.organization"/></label></th>
                <td>
                	<input id="organization" name="organization" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	 <label id="error-organization" style="color: red;display: none;">Organization is required</label>
                </td>
               
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorCode"/></label></th>
                <td>
                	<input id="contractorcode" name="contractorcode" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	 <label id="error-contractorcode" style="color: red;display: none;">Contractor Code is required</label>
                </td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorName"/></label></th>
                <td>
                	<input id="contractorname" name="contractorname" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-contractorname" style="color: red;display: none;">Contractor name is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.managerName"/></label></th>
                <td>
                	<input id="managername" name="managername" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-managername"style="color: red;display: none;">Manger Name is required</label>
                </td>
                 <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.locationOfWork"/></label></th>
                <td>
                	<input id="locationofwork" name="locationofwork" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-locationofwork"style="color: red;display: none;">Location Of Work is required</label>
                </td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.supervisorName"/></label></th>
                <td>
                	<input id="supervisorname" name="supervisorname" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-supervisorname" style="color: red;display: none;">Supervisor Name is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfCode"/></label></th>
                <td>
                	<input id="pfcode" name="pfcode" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-pfcode" style="color: red;display: none;">PF Code is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.emailAddress"/></label></th>
                <td>
                	<input id="emailaddress" name="emailaddress" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-emailaddress" style="color: red;display: none;">Email Address is required</label>
                </td>
            </tr>
           <tr>
           	 <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mobileNumber"/></label></th>
                <td>
                	<input id="mobilenumber" name="mobilenumber" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-mobilenumber" style="color: red;display: none;">Mobile Number is required</label>
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.esicRegistration"/></label></th>
                <td>
                	<input id="esicregistration" name="esicregistration" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-esicregistration" style="color: red;display: none;">ESIC Registration is required</label>
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractValidTill"/></label></th>
                <td>
                	<input id="contractvalidtill" name="contractvalidtill" style="width: 100%;height: 20px; color: black;" type="date" size="30" maxlength="30" autocomplete="off">
                	<label id="error-contractvalidtill" style="color: red;display: none;">Contract Valid Till is required</label>
                </td>
           </tr>
           <tr>
           	 <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorClassification"/></label></th>
                <td>
                	<input id="contractorclass" name="contractorclass" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-contractorclass" style="color: red;display: none;">Contractor Classification is required</label>
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractType"/></label></th>
                <td>
                	<input id="contractortype" name="contractortype" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off">
                	<label id="error-contractortype" style="color: red;display: none;">Contractor Type is required</label>
                </td>
           </tr>
        </tbody>
    </table>
   </div>

<div class="action-buttons z" >
            <button id="saveButton" style="display:none;" type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitGatePass('${sessionScope.loginuser.userId}')">Save</button>
           <!--  <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitForm()">Cancel</button> -->
 </div>
 <div  class="tab-content ">Additional Documents</div>
            <div id="tab2" class="tab-content active ">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
        <thead>
            <tr style=" border: 1px solid #ddd;">
            
                <th><label class="custom-label"></th>
                <th><label class="custom-label"></th>
                <th><label class="custom-label"><spring:message code="label.documentType"/></th>
				<th><label class="custom-label"><spring:message code="label.documentNumber"/></th>
				<th><label class="custom-label"><spring:message code="label.coverage"/></th>
				<th><label class="custom-label"><spring:message code="label.validFrom"/></th>
				<th><label class="custom-label"><spring:message code="label.validTo"/></th>
				<th><label class="custom-label"><spring:message code="label.attachment"/></th>
				<th><label class="custom-label"><spring:message code="label.panIndia"/></th>
				<th><label class="custom-label"><spring:message code="label.subContractorApplication"/></th>
				
            </tr>
        </thead>
        <tbody>
            <%-- <c:forEach var="approver" items="${approvers}" varStatus="status"> --%>
                <tr style=" border: 1px solid #ddd;background-color: #f9f9f9 ;">
                   <td ><button class="btn btn-success" onclick="addTaskRow()">+</button></td>
                   <td ><button class="btn btn-danger" onclick="deleteRow(this)">-</button></td>
                   <td style="color:black">
                       <select id="documentDropId" name="value()" >
                          <option value="-1">Select Document Type</option>
                          <option value="ESIC" >ESIC</option>
                          <option value="LL" >LL</option>
                          <option value="RC">RC</option>
                          <option value="WC" >WC</option>
                    </select>
                </td>
                 <td style= "color:black"> <input type="text"  value=" " id="documentnumber" style="width:100px;" autocomplete="off"/></td> 
				<td style="color:black"> <input type="text"  value=" " id="coverage" style="width:100px;" autocomplete="off"/> </td>
				<td style="color:black"><input type="date" value=" " id="validfrom"   " placeholder = "DD/MM/YYYY" autocomplete="off" /></td>
				<td style="color:black"><input type="date" value=" "  id="validto"  " placeholder = "DD/MM/YYYY" autocomplete="off"/></td>
				<td style= "color:black"><input type="file" value="No file selected" id="attachments" style="width:100px;"/></td>
	            <td style= "color:black"><input type="checkbox" value=" " id="panindia"/></td>
	            <td style= "color:black"><input type="checkbox" value=" " id="subcontractor"/></td>
             </tr>
            <%-- </c:forEach> --%>
        </tbody>
                </table>
            </div>
 <div id="tab3" class="tab-content active">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
             <tr>
			<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.availableWorkOrders"/></label></th>
				<td>
					<select id="value(availableworkorders)" name="value(availableworkorders)" style="width: 256px;height: 150px;"></select>
				</td>	
				<td>
				<ul  style="list-style-type:none; padding-top:3px;padding-left:70px;"><br>
					 <li><input type="button" onclick="Add();validateFunction();"  value="Add"  style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF" /> </li> </br>
                     <li> <input type="button" onclick="AddAll();validateFunction();" value="Add All" style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF" /> </li></br>
                     <li> <input type="button" onclick="Remove();validateFunction();" value="Remove" style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF"  /></li></br>
                     <li> <input type="button" onclick="RemoveAll();validateFunction();" value="Remove All" style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF"   /></li></br> 
      			  </ul> 
				</td>
				<td ><select id="ListBox2" name="ListBox22"    style="width: 265px;height: 150px;">
				 </select></td>
			</tr>
			 <tr>
			<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.availableInsurenceLaborLicense"/></label></th>
				<td>
					<select id="value(availablinsurence)" name="value(availablinsurence)" style="width: 256px;height: 150px;"></select>
				</td>	
				<td>
				<ul  style="list-style-type:none; padding-top:3px;padding-left:70px;"><br>
					 <li><input type="button" onclick="Add();validateFunction();"  value="Add"  style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF" /> </li> </br>
                     <li> <input type="button" onclick="AddAll();validateFunction();" value="Add All" style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF" /> </li></br>
                     <li> <input type="button" onclick="Remove();validateFunction();" value="Remove" style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF"  /></li></br>
                     <li> <input type="button" onclick="RemoveAll();validateFunction();" value="Remove All" style="width: 100px; height: 20px;background-color:#365DBB;color:#FFFFFF"   /></li></br> 
      			  </ul> 
				</td>
				<td ><select id="ListBox2" name="ListBox22"    style="width: 265px;height: 150px;">
				 </select></td>
			</tr>
			<tr>
				<th><label class="custom-label"><spring:message code="label.previousComment"/></label></th>
				<td><input type="text" value=" "   onchange="setDataChanged();" style="width: 265px;height: 150px;" /></td>
				
				<th><label class="custom-label"><spring:message code="label.comment"/></label></th>
				<td><input type="text" value=" "   onchange="setDataChanged();" style="width: 265px;height: 150px;" /></td>
				</tr>
                </table>
            </div>
        
 
 </div>
</body>
</html>
