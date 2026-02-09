<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
      <!--  <script src="resources/js/commonjs.js"></script> -->
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
.action-buttons {
        display: flex; /* Align buttons horizontally */
        align-items: center; /* Center buttons vertically */
    }

    .action-buttons button {
        margin-left: 10px; /* Space between buttons */
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
.Tabular td, .Tabular th {
       
        border-left: thin solid #000;
         border-right: thin solid #000;
        border-bottom: thin solid #000;
        border-top: thin solid #000;
        width: 100%;
        border-collapse: collapse;
         /* Adds a right border to each cell */
    }
    </style>
    <script>
        function showTab(tabId) {
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
        });
    </script>
</head>
<body>
    
    <div id="principalEmployerContent">
    <div class="tabs-container">
   
              <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" style="float: right;" onclick="loadCommonList('/contractor/contRegList','Contractor Master')">Cancel</button>
    		
        <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTabOther('tab1')">Basic Information</button>
            <button data-target="tab2" onclick="showTabOther('tab2')">Additional Information</button> 
           
     </div>
    
    		</div>
     <div id="tab1" class="tab-content active">
        
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <%-- <tr>
                <th><label class="custom-label"><span class="required-field">*</span>Contractor Renewal ID</label></th>
                <td><input type="text" id="contractorrenewalid" name="contractorrenewalid" value="${contractor.contractorrenewalid }" style="height: 20px;" size="30" maxlength="30" readonly="true" /></td>
             span class="required-field">*</span>Unit Code</label></th>
                <td>
                    <c:choose>
                        <c:when test="${principalEmployer.isActive == 1}}">
                            <input type="checkbox" name="isActive" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="isActive" value="0" style="height: 20px;" onclick="return false;"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr> --%>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorRegistrationId"/></label></th>
                <td><input type="text" name="contractorregId" value="${principalEmployer.contractorregId}" style="height: 20px;" size="30" maxlength="30" readonly /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.principalEmployer"/></label></th>
                <td><input type="text" name="principalEmployer" value="${principalEmployer.principalEmployer}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                
                 </tr>
            <tr>
            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorName"/></label></th>
                <td>
                	<input id="contractorNameId" name="contractorName" value="${principalEmployer.contractorName}" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" readonly/>
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.vendorCode"/></label></th>
                <td><input type="text" name="vendorCode" value="${principalEmployer.vendorCode}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
            </tr>
             <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.emailAddress"/></label></th>
                <td>
                	<input id="emailId" name="email"  value="${principalEmployer.email}" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" readonly  />
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mobileNumber"/></label></th>
                <td>
                	<input id="mobileId" name="mobile"  value="${principalEmployer.mobile}" style="width: 100%;height: 20px;" type="text" size="10" maxlength="10" readonly  />
                </td>
                
            </tr>
            
             <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorAadhar"/></label></th>
                <td>
                	<input id="aadharId" name="aadhar" value="${principalEmployer.aadhar}" style="width: 100%;height: 20px;" type="text" size="30" maxlength="12" readonly  />
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.aadharDoc"/></label></th>
                <td>
                 <a href="#" onclick="viewContractorFile('${principalEmployer.contractorregId}','${principalEmployer.createdBy }','${principalEmployer.aadharDoc }')">Download Aadhar</a>
                </td>
               
           </tr>
            <tr>
           	   
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorPan"/></label></th>
                <td>
                	<input id="panId" name="pan" value="${principalEmployer.pan}" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" readonly  />
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.panDoc"/></label></th>
                <td>
                 <a href="#" onclick="viewContractorFile('${principalEmployer.contractorregId}','${principalEmployer.createdBy }','${principalEmployer.panDoc }')">Download Pan</a>
               
                </td>
           </tr>
            <tr>
           	   <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorGst"/></label></th>
                <td>
                	<input id="gstId" name="gst" value="${principalEmployer.gst}" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" readonly  />
                </td>
               <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorAddress"/></label></th>
                <td>
                	<input id="addressId" name="address" value="${principalEmployer.address}" style="width: 100%;height: 20px;" type="text" size="30" maxlength="30" readonly  />
                </td>
               
           </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfNumber"/></label></th>
                <td><input type="text" name="pfNum" value="${principalEmployer.pfNum}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.natureOfWork"/></label></th>
                <td><input type="text" name="natureOfWork" value="${principalEmployer.natureOfWork}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.managerName"/></label></th>
                <td><input type="text" name="managerName" value="${principalEmployer.managerName}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.locationOfWork"/></label></th>
                <td><input type="text" name="locofWork" value="${principalEmployer.locofWork}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.totalStrength"/></label></th>
                <td><input type="text" name="totalStrength" value="${principalEmployer.totalStrength}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.rcMaxEmployees"/></label></th>
                <td><input type="text" name="rcMaxEmp" value="${principalEmployer.rcMaxEmp}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
            </tr>
           
             <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractFrom"/></label></th>
                <td><input type="text" name="contractFrom" value="${principalEmployer.contractFrom}" style="height: 20px;" size="30" maxlength="30"  readonly /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractTo"/></label></th>
                <td><input type="text" name="contractTo" value="${principalEmployer.contractTo}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
            </tr>
             <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractType"/></label></th>
                <td><input type="text" name="contractType" onchange="toggleMainContractorRow()" value="${principalEmployer.contractType}"  style="height: 20px;" size="30" maxlength="30"  readonly /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.isRcVerified"/></label></th>
          <td>
           <c:choose>
              <c:when test="${principalEmployer.rcVerified}">
                <input type="checkbox" name="rcVerified" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
              </c:when>
           <c:otherwise>
            <input type="checkbox" name="rcVerified" value="0" style="height: 20px;" onclick="return false;"/>
           </c:otherwise>
          </c:choose>
        </td>
 </tr>
            <tr id="mainContractorRow" style="display: none;">
            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mainContractor"/></label></th>
                <td><input type="text" name="mainContractor" value="${principalEmployer.mainContractor}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
            </tr>
            <tr>
<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfApplyDate"/></label></th>
                <td>
                	<input id="pfApplyDateId" name="pfApplyDate" value ="${principalEmployer.pfApplyDate}" style="width: 100%;height: 20px; color: black;" type="text" size="30" maxlength="30" readonly />
                </td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfDoc"/></label></th>
                <td>
                 <a href="#" onclick="viewContractorFile('${principalEmployer.contractorregId}','${principalEmployer.createdBy }','${principalEmployer.pfDoc }')">Download PF</a>
                </td>
</tr>
        </tbody>
    </table>
   

        </div>
    <div id="tab2" class="tab-content">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
        <thead>
            <tr style=" border: 1px solid #ddd;">
                <th><label class="custom-label"><spring:message code="label.workOrderNumber"/></th>
				<th><label class="custom-label"> <spring:message code="label.natureOfJob"/></th>
				<th><label class="custom-label"><spring:message code="label.documentType"/></th>
				<th><label class="custom-label"><spring:message code="label.documentNumber"/></th>
				<th><label class="custom-label"><spring:message code="label.coverage"/></th>
				<th><label class="custom-label"><spring:message code="label.validFrom"/></th>
				<th><label class="custom-label"><spring:message code="label.validTo"/></th>
				<th><label class="custom-label"><spring:message code="label.attachment"/></th>
				
            </tr>
        </thead>
        <tbody>
             <c:forEach var="item" items="${additionalDetails}" varStatus="status"> 
                <tr style=" border: 1px solid #ddd;background-color: #f9f9f9 ;">
                    <td style="color:black ;text-align:center">${item.woNumber }</td>
                    <td style="color:black;text-align:center">${item.natureOfJob }</td >
                    <td style="color:black;text-align:center">${item.documentType }</td>
                    <td style="color:black;text-align:center">${item.documentNumber }</td>
                    <td style="color:black;text-align:center">${item.coverage }</td>
                    <td style="color:black;text-align:center">${item.validFrom }</td >
                    <td style="color:black;text-align:center">${item.validTo}</td>
                    <td style="color:black;text-align:center">
                     <a href="#" onclick="viewContractorFile('${principalEmployer.contractorregId}','${principalEmployer.createdBy }','${item.fileName }')">${item.fileName}</a>
               
                     </td>
                    
                </tr>
             </c:forEach> 
        </tbody>
                </table>
            </div>
        
    </div>
</body>
</html>
