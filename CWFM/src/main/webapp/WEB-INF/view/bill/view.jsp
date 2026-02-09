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
    <script src="resources/js/cms/bill.js"></script>
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
       
        
    </style>
   

	
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
     <%
	MasterUser user = (MasterUser) session.getAttribute("loginuser");
 String userId = user != null && user.getUserId() != null ? String.valueOf(user.getUserId()) : "";
    String roleName = user != null ? user.getRoleName() : "";
    String roleId = user!=null?user.getRoleId():"";
    String contextPath =  request.getContextPath() ;
	%>
</head>
<body>
    
    <div id="principalEmployerContent">
    <div class="tabs-container">
        <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTabNew('tab1')">Basic Information</button>
            <button data-target="tab2" onclick="showTabNew('tab2')">Kronos Reports</button> 
            <button data-target="tab3" onclick="showTabNew('tab3')">Statuory Regulatory Attachments</button> 
            <button data-target="tab4" onclick="showTabNew('tab4')">Check List - HR Clearance</button> 
             <button data-target="tab5" onclick="showTabNew('tab5')">Comments</button> 
            
    </div>
   <div class="action-buttons" >
 
            <% if (user != null && !"Contractor".equals(roleName)) { %>
    			<button id="approveButton"  type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="approveRejectBill('4')">Approve</button>
   				 <button id="rejectButton"    type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="approveRejectBill('5')">Reject</button>
			<% } %>
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/billVerification/listingFilter', 'Bill Verification List');">Cancel</button>
        </div> 
        </div>
        <div id="tab1" class="tab-content active">
        
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
           <tbody>
			<tr>
			<input type="hidden" id="userId" name="userId" value="<%= userId %>">
			<input type="hidden" id="roleName" name="roleName" value="<%= roleName %>">
			<input type="hidden" id="roleId" name="roleId" value="<%= roleId %>">
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.transactioId"/></label></th>
                <td><input type="text" id="transactionId" name="transactionId" value="${bvr.wcTransId}" style="height: 20px;" size="30" maxlength="30" readonly /></td>
                 </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitName"/></label></th>
                <td>
                
	<input type="text" value="${bvr.unitName}" style="height: 20px;width: 100%;" readonly />                           
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitCode"/></label></th>
                <td><input type="text" id="unitCodeId" name="unitCode"  style="height: 20px;width: 100%;" value="${bvr.unitCode}"  readonly  />
                	<input type="hidden" id="unitId" name="unitId"  style="height: 20px;width: 100%;" value="${bvr.unitId}"  readonly  />
                </td>
             </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorName"/></label></th>
                <td>
                <input type="text" value="${bvr.contractorName}" style="height: 20px;width: 100%;" readonly />      
                 </td>
                
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.vendorCode"/></label></th>
                <td><input type="text" id="contractorCodeId" name="contractorCode" style="height: 20px;width: 100%;" value="${bvr.contractorCode}" readonly  /></td>
                
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billStartDate"/></label></th>
                <td>
                <input id="billStartDateId" name="billStartDate" style="width: 100%;height: 20px; color: black;" type="text"  value="${bvr.startDate}"  readonly >
                	
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billEndDate"/></label></th>
                <td>
                <input id="billEndDateId" name="billEndDate" style="width: 100%;height: 20px; color: black;" type="text"  value="${bvr.endDate}"  readonly>
                	<label id="error-billEndDate" style="color: red;display: none;">Bill end date is required</label>
               
                </td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workorderCode"/></label></th>
                <td>
                 <input type="text" value="${bvr.workOrderNumber}" style="height: 20px;width: 100%;" readonly /> 
                </td>
                            
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billType"/></label></th>
                <td>
                <input type="text" value="${bvr.billType}" style="height: 20px;width: 100%;" readonly /> 
                </td>
            </tr>
             <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workorderValidFrom"/></label></th>
                <td><input type="text" id="woValidFromId" name="woValidFrom" value="${bvr.woValidFrom}" style="height: 20px;width: 100%;" size="30" maxlength="30"  readonly /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workorderValidTo"/></label></th>
                <td><input type="text" id="woValidToId" name="woValidTo" value="${bvr.woValidTo}" style="height: 20px;width: 100%;" size="30" maxlength="30"  readonly  /></td>
            </tr>
			<tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billCategory"/></label></th>
                <td>
                	<input type="text" value="${bvr.services}" style="height: 20px;width: 100%;" readonly /> 
                	 </td>
                 </tr>
			<%-- <tr>
			<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.engineeringInCharge"/></label></th>
				<td>
					<select id="value(engincharge)" name="value(engincharge)" style="width: 256px;height: 150px;"></select>
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
			</tr> --%>
			</tbody>
    </table>
   

        </div>
    <div id="tab2" class="tab-content">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;">
                   
        <tbody>
            <c:forEach var="file" items="${kronosFiles}">
    <tr style="font-size: 100%;font-weight: bold;padding;8px;color: #898989;">
       <%--  <td style="font-family: Arial, sans-serif;display: block; text-align: left;color:grey;">${file.reportName} --%>
       <td  >${file.reportName}
       <a href="#" onclick="openFile('${file.reportType}','${file.transactionId }','${file.fileName}')">Download</a></td>
    </tr>
</c:forEach>
            
            
			
           
			</tbody>
                </table>
            </div>
            <div id="tab3" class="tab-content">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;">
                   
            <tbody>
            	<c:forEach var="file" items="${statutoryFiles}">
    <tr>
        <td style="font-size: 100%;font-weight: bold;padding;8px;color: #898989;">${file.reportName} : 
        
        
        <a href="#" onclick="openFile('${file.reportType}','${file.transactionId }','${file.fileName}')">Download</a></td>
    </tr>
</c:forEach>
            	
			</tbody>
                </table>
                
            </div>
        <div id="tab4" class="tab-content">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;color:black;">
               <thead>
               <tr style="color: #898989;font-weight: bold;">
               
               	<td>Check points </td>
               	<td>Status Y/N </td>
               	<td>License No./Policy No./Code No. </td>
               	<td>Date of Compliance/Valid Upto </td>
               </tr>
               </thead>    
            <tbody>
				<c:forEach var="item" items="${checklistItems}">
            <tr style="color: #898989;">
        <td>${item.id}</td>
        <td>${item.statusValue}</td>
        <td>${item.licenseNumber}</td>
        <td>${item.validUpto}</td>
    </tr>
        </c:forEach>
   
      </tbody>
     </table>
    </div> 
  <div id="tab5" class="tab-content">
            <div class="Panel">
             
	<table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
		<tr>
				<th  style="width: 100px;height:20px;"><label class="custom-label"> Action Plan</label></th>
				 <td><input type="text" id="actionPlanId" name="actionPlan" value="${bvr.actionPlan}" style="height: 20px;" size="30" maxlength="30"    readonly/></td>
 
		</tr>
			
		
			<tr>
				<th><label class="custom-label"> <spring:message code="label.previousComment"/></label></th>
			<td><input type="text" id="preCommentsId" name="preComments" value=""  style="width: 265px;height: 150px;"  readonly  /></td>
 	
				<th><label class="custom-label"><spring:message code="label.comment"/></label></th>
				<td><input type="text" id="commentsId" value="${bvr.comments}"  name="comments" style="width: 265px;height: 150px;" readonly/></td>
				</tr>
				<tr>
				
				 <% if (user != null && !"Contractor Supervisor".equals(roleName)) { %>
				 <th><label class="custom-label"><spring:message code="label.approveComment"/></label></th>
				<td><textarea id="approvercomments"  name="approvercomments" placeholder="Type here..."></textarea>
				<label id="error-approvercomments" style="color: red;display: none;">Comments is required</label>
				</td>
				<% } %>
			</tr>
		</tbody>
 </table> 
	
</div>
</div>	


            </div>  
                    
  
</body>
</html>
