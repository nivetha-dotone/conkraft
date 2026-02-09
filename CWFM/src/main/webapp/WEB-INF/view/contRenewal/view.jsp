<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
    
   
   
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
   
		
		<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
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
            <button data-target="tab2" onclick="showTabNew('tab2')">License Information</button>
            <button data-target="tab3" onclick="showTabNew('tab3')">Work Order Information</button>
           <button data-target="tab5" onclick="showTabNew('tab5')">Comments</button>
           <!-- <button data-target="tab4" onclick="showTabNew('tab4')">Previous Documents</button> -->
        </div>
         <div class="action-buttons" >
             <% if (user != null && !"Contractor".equals(roleName)) { %>
    			<button id="approveButton"  type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="approveRejectContRenew('4')">Approve</button>
   				 <button id="rejectButton"    type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="approveRejectContRenew('5')">Reject</button>
			<% } %>
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/renewal/listingFilter', 'Contractor Renewal');">Cancel</button>
  
      
        </div> 
    </div> 

  
<div id="tab1" class="tab-content active ">

    <table cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
            <input type="hidden" id="userId" name="userId" value="<%= userId %>">
			<input type="hidden" id="roleName" name="roleName" value="<%= roleName %>">
			<input type="hidden" id="roleId" name="roleId" value="<%= roleId %>">
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorRenewalId"/> </label></th>
                <td>
                	<input id="contractorregId" name="contractorregId" value="${contractor.contractorregId}" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" readonly>
                	<label id="error-registrationid" style="color: red;display: none;">Registration ID is required</label>
                </td>
                 <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.principalEmployer"/></label></th>
				 <td><input type="text" value="${contractor.principalEmployer}" readonly>
				 <input type="hidden" id="unitId" value="${contractor.unitId}" readonly>
				 </td>
           </tr>
            <tr>
              <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorName"/></label></th>
                <td> 
                	<input id="contractorNameId" name="contractorName"  value="${contractor.contractorName}"  style="width: 100%;height: 20px; text-transform: capitalize;" type="text" size="30" maxlength="30" autocomplete="off" readonly>
                	<label id="error-contractorname" style="color: red;display: none;">Contractor name is required</label>
                </td>
                 
          <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.vendorCode"/></label></th>
                <td>
                	<input id="vendorCodeId" name="vendorCode" value="${contractor.vendorCode}" style="width: 100%;height: 20px; text-transform: capitalize;" type="text" size="30" maxlength="30" autocomplete="off" readonly>
            						
            		<!-- <input id="vendorCodeId" name="vendorCode" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30"> -->
                	 <label id="error-vendorCode" style="color: red;display: none;">Vendor Code is required</label>
                </td>
               
            </tr>
             <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.emailAddress"/></label></th>
                <td>
                	<input id="emailId" name="email" style="width: 100%;height: 20px;" type="text" value="${contractor.email}" readonly size="30" maxlength="30" autocomplete="off">
                	<label id="error-email" style="color: red;display: none;">Email is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mobileNumber"/></label></th>
                <td>
                	<input id="mobileId" name="mobile" style="width: 100%;height: 20px;" type="text" size="10" maxlength="10" autocomplete="off" value="${contractor.mobile}" readonly>
                	<label id="error-mobile"style="color: red;display: none;">Mobile number is required</label>
                </td>
                
            </tr>
            
             <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorAadhar"/></label></th>
                <td>
                	<input id="aadharId" name="aadhar" style="width: 100%;height: 20px;" type="text" size="30" maxlength="12" autocomplete="off" value="${contractor.aadhar}" readonly>
                	<label id="error-aadhar" style="color: red;display: none;">Aadhar Number is required</label>
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.aadharDoc"/></label></th>
                <td>
                 <a href="#" onclick="viewContractorFile('${contractor.contractorregId}','${contractor.createdBy }','${contractor.aadharDoc }')">Download Aadhar</a>
                </td>
               
           </tr>
            <tr>
           	   
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorPan"/></label></th>
                <td>
                	<input id="panId" name="pan" style="width: 100%;height: 20px;text-transform: uppercase;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.pan}" readonly>
                	<label id="error-pan" style="color: red;display: none;">Pan Number is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.panDoc"/></label></th>
                <td>
                 <a href="#" onclick="viewContractorFile('${contractor.contractorregId}','${contractor.createdBy }','${contractor.panDoc }')">Download Pan</a>
               
                </td>
           </tr>
            <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorGst"/></label></th>
                <td>
                	<input id="gstId" name="gst" style="width: 100%;height: 20px;text-transform: uppercase;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.gst}" readonly>
                	<label id="error-gst" style="color: red;display: none;">GST is required</label>
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorAddress"/></label></th>
                <td>
                	<input id="addressId" name="address" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.address}" readonly>
                	<label id="error-address" style="color: red;display: none;">Address is required</label>
                </td>
               
           </tr>
           <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfNumber"/></label></th>
                <td>
                	<input id="pfNumId" name="pfNum" style="width: 100%;height: 20px;text-transform: uppercase;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.pfNum}" readonly>
                	<label id="error-pfnumber" style="color: red;display: none;">PF Number is required</label>
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.natureOfWork"/></label></th>
                <td>
                	<input id="natureOfWorkId" name="natureOfWork" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.natureOfWork}" readonly>
                	<label id="error-natureOfWork" style="color: red;display: none;">Nature Of Work is required</label>
                </td>
               
           </tr>
           <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.managerName"/></label></th>
                <td>
                	<input id="managerNameId" name="managerName" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.managerName}" readonly>
                	<label id="error-managername" style="color: red;display: none;">Manager name is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.locationOfWork"/></label></th>
                <td>
                	<input id="locofWorkId" name="locofWork" style="width: 100%;height: 20px;text-transform: capitalize;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.locofWork}" readonly>
                	<label id="error-locofwork"style="color: red;display: none;">Location Of Work is required</label>
                </td>
                
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.totalStrength"/></label></th>
                <td>
                	<input id="totalStrengthId" name="totalStrength" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.totalStrength}" readonly>
                	<label id="error-totalStrength" style="color: red;display: none;">Total Strength is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.rcMaxEmployees"/></label></th>
                <td>
                	<input id="rcMaxEmpId" name="rcMaxEmp" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.rcMaxEmp}" readonly>
                	<label id="error-rcmaxemployees" style="color: red;display: none;">RC Max Employees is required</label>
                </td>
               
            </tr>
            <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractFrom"/></label></th>
                <td>
                	<input id="contractFromId" name="contractFrom" style="width: 100%;height: 20px; color: black;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.contractFrom}" readonly>
                	<label id="error-contractFrom" style="color: red;display: none;">Contract From is required</label>
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractTo"/></label></th>
                <td>
                	<input id="contractToId" name="contractTo" style="width: 100%;height: 20px; color: black;" type="text" size="30" maxlength="30" autocomplete="off" value="${contractor.contractTo}" readonly>
                	<label id="error-contractTo" style="color: red;display: none;">Contract To is required</label>
                </td>
               
           </tr>
         <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractType"/></label></th>
           <td>
    <!-- Dropdown for contractor type -->
             <input id="contractTypeId" name="contractType" type="text" style="width: 100%; height: 25px;color:black;" value="${contractor.contractType}" readonly>
   
<label id="error-contractType" style="color: red; display: none;">Contract Type is required</label>
              
             <label id="error-contractType" style="color: red; display: none;">Contract Type is required</label>
         </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.isRcVerified"/></label></th>
                <td>
                	<input id="rcVerifiedId" name="rcVerified" style="height: 20px;color: black;" type="checkbox" size="30" maxlength="30">
                	<label id="error-isRcVerified" style="color: red;display: none;">Is RC Verified is required</label>
                </td>
                
               
        </tr>
        
           <!-- Main Contractor row -->
       <tr id="mainContractorRow" style="display: none;"><th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mainContractor"/></label></th>
    <td>
        <select class="custom-select" id="mainContractorId" name="mainContractor"  style="color:black">
            						<option value="">Please select Main Contractor</option>
            		</select>
        <label id="error-mainContractor" style="color: red; display: none;">Main Contractor is required</label>
    </td>
   </tr> 
   
<tr>
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfApplyDate"/></label></th>
                <td>
                	<input id="pfApplyDateId" name="pfApplyDate" style="width: 100%;height: 20px; color: black;" type="text" size="30" value="${contractor.pfApplyDate}" readonly> 
                	<label id="error-contractFrom" style="color: red;display: none;">PF apply date is required</label>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfDoc"/></label></th>
                <td>
                 <a href="#" onclick="viewContractorFile('${contractor.contractorregId}','${contractor.createdBy }','${contractor.pfDoc }')">Download PF</a>
                </td>
</tr>
   
   </tbody>
  </table>
 </div>
   
    
 <div  class="tab-content "><spring:message code="label.additionalDocumets"/></div>
            <div id="tab2" class="tab-content  ">
            <div id="validationMessages" style="color: red; font-weight: bold; padding: 10px;"></div>
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
        <thead>
            <tr style=" border: 1px solid #ddd;">
            
                
				<th><label class="custom-label"> <spring:message code="label.documentType"/></th>
				<th><label class="custom-label"> <spring:message code="label.documentNumber"/></th>
				<th><label class="custom-label"> <spring:message code="label.coverage"/></th>
				<th><label class="custom-label"><spring:message code="label.validFrom"/></th>
				<th><label class="custom-label"><spring:message code="label.validTo"/></th>
				<th><label class="custom-label"><spring:message code="label.attachment"/></th>
				
            </tr>
        </thead>
      <tbody id="licenseBody">
    <c:forEach var="item" items="${policies}">
                    <tr style=" border: 1px solid #ddd;background-color: ${status.index % 2 == 0 ? '#f9f9f9' : '#ffffff'};">
                      
                    <td style="color:#2c2c2c;text-align:center">${item.documentType }</td>
                    <td style="color:#2c2c2c;text-align:center">${item.documentNumber }</td>
                    <td style="color:#2c2c2c;text-align:center">${item.coverage }</td>
                    <td style="color:#2c2c2c;text-align:center">${item.validFrom }</td >
                    <td style="color:#2c2c2c;text-align:center">${item.validTo}</td>
                    <td style="color:#2c2c2c;text-align:center">
                    <a href="#" onclick="viewContractorFile('${contractor.contractorregId}','${contractor.createdBy }','${item.fileName }')">${item.fileName}</a>
                     </td>
                    </tr>
                </c:forEach>
</tbody>
      
                </table>
             
            </div>

    <!-- ✅ TAB 3 – LLWC -->
   <div id="tab3" class="tab-content  ">
        <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
            <thead>
                <tr style=" border: 1px solid #ddd;">
                    <th><label class="custom-label">Work Order Number</label></th>
                    <th><label class="custom-label">License Type</label></th>
                    <th><label class="custom-label">WC Code</label></th>
                    
                    <th><label class="custom-label">Created Date</label></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="ll" items="${llwcRecords}">
                    <tr style=" border: 1px solid #ddd;background-color: ${status.index % 2 == 0 ? '#f9f9f9' : '#ffffff'};">
                        <td><input type="text" value="${ll.workOrderNumber}" readonly></td>
                        <td><input type="text" value="${ll.licenseType}" readonly></td>
                        <td><input type="text" value="${ll.wcCode}" readonly></td>
                        <td>
                         <c:if test="${not empty ll.createdDtm}">
                           <input type="text" value="<fmt:formatDate value='${ll.createdDtm}' pattern='yyyy-MM-dd'/>" readonly>
                         </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <div id="tab5" class="tab-content">
            <%-- <div class="Panel">
            
            <table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
		<tr>
				<c:if test="${ not empty comments.comments}">
        <tr>
        <th><label class="custom-label"><spring:message code="label.approveComment"/></label></th>
        <td>
        <textarea id="approvercomments" name="approvercomments" readonly>${comments.comments}</textarea>
        </td>
        </tr>
        </c:if>
				 <% if (user != null && !"Contractor".equals(roleName)) { %>
				 <th><label class="custom-label"><spring:message code="label.approveComment"/></label></th>
				<td><textarea id="approvercomments"  name="approvercomments" placeholder="Type here..."></textarea>
				<label id="error-approvercomments" style="color: red;display: none;">Comments is required</label>
				</td>
				<% } %>
				
				
			</tr>
		</tbody>
 </table> 
	
</div> --%>

<div class="Panel">
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>

           <c:choose>

    
    <c:when test="${roleName eq 'Contractor'}">
        <tr>
            <th>
                <label class="custom-label">
                    <spring:message code="label.approveComment"/>
                </label>
            </th>
            <td>
                <textarea id="approvercomments"
                          name="approvercomments"
                          readonly>
                    <c:out value="${comments.comments}" default=""/>
                </textarea>
            </td>
        </tr>
    </c:when>

    
    <c:otherwise>
        <tr>
            <th>
                <label class="custom-label">
                    <spring:message code="label.approveComment"/>
                </label>
            </th>
            <td>
                <textarea id="approvercomments"
                          name="approvercomments"
                          placeholder="Type here..."></textarea>

                <label id="error-approvercomments"
                       style="color:red; display:none;">
                    Comments is required
                </label>
            </td>
        </tr>
    </c:otherwise>

</c:choose>

        </tbody>
    </table>
</div>

</div>
<div id="tab4" class="tab-content">
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
                                      onclick="viewContractorFile('${contractor.contractorregId}','${contractor.createdBy }','${contractor.aadharDoc }')"
                                       class="text-primary text-decoration-underline">
                                        ${doc.FILENAME}
                                    </a>
                                </td>
                                <td style="color:black;">
                                    <%-- <span class="badge bg-info text-dark">V${doc.VERSIONNO}</span> --%>
                                    <span>V${doc.VERSIONNO}</span>
                                </td>
                            </tr>
                        </c:when>

                        
                        <c:otherwise>
                            <tr>
                                <td></td> <!-- Empty cell to visually group -->
                                <td>
                                    <a href="javascript:void(0);" 
                                       onclick="viewContractorFile('${contractor.contractorregId}','${contractor.createdBy }','${contractor.aadharDoc }')"
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
</div>	
</div>



</body>
</html>
