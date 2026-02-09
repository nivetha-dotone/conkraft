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
         justify-content: space-between;
    }

    .action-buttons button {
        margin-left: 10px; /* Space between buttons */
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
    <!-- <div class="page-header">
        Principal Employer View Page
        <div class="header-buttons">
            <button type="button" onclick="history.back()">Back</button>
        </div>
    </div> -->

    <div id="principalEmployerContent">
    <div class="tabs-container">
         <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTabOther('tab1')">Basic Information</button>
            <button data-target="tab2" onclick="showTabOther('tab2')">License Information</button>
            <button data-target="tab3" onclick="showTabOther('tab3')">Policy Information</button>
            <button data-target="tab4" onclick="showTabOther('tab4')">WorkOrder Information</button>
         </div>
          <div class="action-buttons" > 
              <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/contractor/list','Contractor');">Cancel</button> 
        </div> 
    </div>
    <!-- <div class="tabs-container">
        <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTabOther('tab1')">Unit Information</button>
            <button data-target="tab2" onclick="showTabOther('tab2')">License Information</button>
        
        </div>     <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/principalEmployer/list','PrincipalEmployer');">Cancel</button>
     <div class="action-buttons" > 
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/principalEmployer/list','PrincipalEmployer')">Cancel</button>
     </div> 
    </div> -->
        <div id="tab1" class="tab-content active">
          <form id="editForm" action="/CWFM/principalEmployer/view/${principalEmployer.unitId}" method="post">
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
             <tr>
                <td>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitName"/></label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="code" value="${principalEmployer.name}" style="height: 20px;"  size="30" maxlength="30" readonly />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.organization"/></label></th>
                            <td><input type="text" name="organization" value="${principalEmployer.organization}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                       <!-- <td >  <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/contractor/list','Contractor');">Cancel</button>
     </td> -->
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorName"/></label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="code" value="${contractor.contractorName}" style="height: 20px;"  size="30" maxlength="30" readonly />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.address"/></label></th>
                            <td><input type="text" name="name" value="${contractor.contractorAddress}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.managerName"/></label></th>
                            <td><input type="text" name="managerNm" value="${contractorPEMM.managerName}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.managerAddress"/></label></th>
                            <td><input type="text" name="pfNum" value="${contractor.managerAddress}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.emailAddress"/></label></th>
                            <td><input type="text" name="managerEmail" value="${contractor.emailaddress}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mobileNumber"/></label></th>
                            <td><input type="text" name="managerMobile"  value="${contractor.mobileNumber == -1 ? '' : contractor.mobileNumber}"  style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.esicRegistration"/></label></th>
                            <td><input type="text" name="esicNum" value="${contractorPEMM.esiwc}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                            <th><label class="custom-label"><spring:message code="label.contractValidTill"/></label></th>
                            <td><input type="text" name="" value="${contractorPEMM.validTo }" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        </tr>
                        <%-- <tr>
                            <th><label class="custom-label"><spring:message code="label.isRcVerified"/></label></th>
                            <td>
    <c:choose>
        <c:when test="${contractorPEMM.rcValidated}">
    <input type="checkbox" name="rcValidated" value="1" checked="checked" style="height: 20px;" onclick="return false;"/>
</c:when>
        <c:otherwise>
            <input type="checkbox" name="rcValidated" value="0" style="height: 20px;" onclick="return false;"/>
        </c:otherwise>
    </c:choose>
</td>
                        </tr> --%>
                     <%--    <tr>
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
                            <input type="text" name="ATTACHMENTNM" value="${principalEmployer.ATTACHMENTNM}" style="height: 20px;"  size="30" maxlength="30" />
                            </td>
                        
                        </tr> --%>
            
        </tbody>
    </table>
   <!--  <div style="text-align: center;">
        <button type="submit" class="btn btn-primary">Submit</button>
        <button type="button" class="btn btn-secondary" onclick="history.back();">Back</button>
    </div> -->
</form>

        </div>
        
         <div id="tab2" class="tab-content">
            <form id="additionalForm" action="/CWFM/principalEmployer/viewAdditional/${principalEmployer.unitId}" method="post">
               <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                    <thead>
	<tr style=" border: 1px solid #ddd;">
		<th style="color:gray;"> <spring:message code="label.labourLicenseNumber"/></th>
		<th style="color:gray;"><spring:message code="label.fromDate"/></th>
		<th style="color:gray;"><spring:message code="label.toDate"/></th> 
		<th style="color:gray;"><spring:message code="label.total"/></th>
		<th style="color:gray;"><spring:message code="label.activeWorkmenCount"/></th>
	</tr> 		
	</thead>
	<tbody >
	<c:forEach items="${laborLicenses}" var="laborLicenses">
                <tr style=" border: 1px solid #ddd;background-color: #f9f9f9 ;">
                     <td style="color:black;">${laborLicenses.wcCode}</td>
                     <td style="color:black;">${laborLicenses.wcFromDtm}</td>
                     <td style="color:black;">${laborLicenses.wcToDtm}</td>
                     <td style="color:black;">${laborLicenses.wcTotal}</td>
                     <td style="color:black;">${laborLicenses.activeWorkmenCount}</td>
                </tr>
            </c:forEach>
	<!-- <tr>
            <td>L.C.6/CLRA/Licence/CLRA/GNR/2023/CLL/20 </td>
            <td> 28/9/2023 </td>
            <td> 01/01/2025 </td>
            <td>50</td>
            <td>10</td>
        </tr>
        <tr>
             <td>L.C.6/CLRA/Licence/CLRA/GNR/2024/CLL/20 </td>
            <td> 01/01/2024 </td>
            <td> 01/01/2025 </td>
            <td>70</td>
            <td>10</td>
        </tr> -->
	         </tbody>
	         </table>
            </form>
        </div>
        
        <div id="tab3" class="tab-content">
            <form id="additionalForm" action="/CWFM/principalEmployer/viewAdditional/${principalEmployer.unitId}" method="post">
               <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
	<thead>
		<tr style=" border: 1px solid #ddd;">
		<th style="color:gray;"><spring:message code="label.wcPolicyesicRegNumber"/> <%-- <fmt:message key="label.wc.code" /> --%></th>
		<th style="color:gray;"><spring:message code="label.licenseType"/></th>
		<th style="color:gray;"><spring:message code="label.jobName"/></th>
		<th style="color:gray;"><spring:message code="label.fromDate"/></th>
		<th style="color:gray;"><spring:message code="label.toDate"/></th> 
		<th style="color:gray;"><spring:message code="label.total"/></th>
		<th style="color:gray;"><spring:message code="label.activeWorkmenCount"/></th>
		</tr> 		
	</thead>
	<tbody >
	<c:forEach items="${contractorWCList}" var="contractorWCList">
                <tr style=" border: 1px solid #ddd;background-color: #f9f9f9 ;">
                   <td style="color:black;">${contractorWCList.wcCode}</td>
                   <td style="color:black;">${contractorWCList.licenceType}</td>
                   <td style="color:black;">${contractorWCList.natureOfId}</td>
                   <td style="color:black;">${contractorWCList.wcFromDtm}</td>
                   <td style="color:black;">${contractorWCList.wcToDtm}</td>
                   <td style="color:black;">${contractorWCList.wcTotal}</td>
                   <td style="color:black;">${contractorWCList.activeWorkmenCount}</td>
                </tr>
            </c:forEach>
	<!-- <tr>
            <td>12312317</td>
            <td>WC</td>
            <td>OPERATION, MECHANICAL, CIVIL, SERVICES </td>
            <td> 28/9/2021 </td>
            <td> 01/01/2025 </td>
            <td>50</td>
            <td>10</td>
        </tr>
        <tr>
             <td>130522127110005000</td>
            <td>ESIC</td>
            <td>Operational Support </td>
            <td> 01/01/2021 </td>
            <td> 01/01/2025 </td>
            <td>70</td>
            <td>10</td>
        </tr>
       <tr>
            <td>123123189</td>
            <td>WC</td>
            <td>OPERATION, MECHANICAL, CIVIL, SERVICES </td>
            <td> 28/9/2022 </td>
            <td> 01/01/2025 </td>
            <td>40</td>
            <td>10</td>
        </tr>
        <tr>
             <td>WERTYUIOPQ1234567</td>
            <td>ESIC</td>
            <td>Operational Support </td>
            <td> 01/01/2023 </td>
            <td> 01/01/2025 </td>
            <td>80</td>
            <td>20</td>
        </tr> -->
	</tbody>
   </table>
  </form>
</div>
        <div id="tab4" class="tab-content">
            <form id="additionalForm" action="/CWFM/principalEmployer/viewAdditional/${principalEmployer.unitId}" method="post">
 <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
	<thead>
	 <tr style=" border: 1px solid #ddd;">
		<th style="color:gray;"><spring:message code="label.workOrderNumber"/></th>
		<th style="color:gray;"><spring:message code="label.fromDate"/></th>
		<th style="color:gray;"><spring:message code="label.toDate"/></th> 
		<th style="color:gray;"><spring:message code="label.activeWorkmenCount"/></th>
		<th style="color:gray;"><spring:message code="label.contractorClassification"/></th> 
	 </tr> 		
	</thead>
	<tbody >
	<c:forEach items="${workOrderList}" var="workOrderList">
                <tr style=" border: 1px solid #ddd;background-color: #f9f9f9 ;">
                      <td style="color:black;text-align:left">${workOrderList.sapWorkorderNumber}</td>
                      <td style="color:black;text-align:left">${workOrderList.validFrom}</td>
                      <td style="color:black;text-align:left">${workOrderList.validTo}</td>
                      <td style="color:black;text-align:left">${workOrderList.activeWorkmenCount}</td>
                      <td style="color:black;text-align:left">${workOrderList.classification}</td>
            
                  <%--  <td>${workOrderList.wcTotal}
                   <c:if test="${item.cType == null}">
				&nbsp;
			</c:if>
			<c:if test="${item.cType == '0'}">
				SLA with headcount
			</c:if>
			<c:if test="${item.cType == '1'}">
				Manpower Services
			</c:if>
			<c:if test="${item.cType == '2'}">
				SLA without headcount
			</c:if>
                   </td> --%>
            <td style="color:black;text-align:left"></td>
                </tr>
            </c:forEach>
	<!-- <tr>
            <td>9876543210</td>
            <td> 28/9/2021 </td>
            <td> 01/01/2025 </td>
            <td>20</td>
            <td>Manpower Services</td>
        </tr>
        <tr>
            <td>9876543210</td>
            <td>01/01/2021 </td>
            <td> 28/4/2025 </td>
            <td>30</td>
            <td>Manpower Services</td>
        </tr> -->
	      </tbody>
       </table>
      </form>
     </div>
    </div>
</body>
</html>
