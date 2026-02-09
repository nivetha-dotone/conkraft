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
    top: 0; body {
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
    /* td {
        padding-bottom: 10px; /* Adjust the value as needed */
    } */
    .custom-label {
    font-family: 'Your-Desired-Font', sans-serif; /* Replace 'Your-Desired-Font' with the actual font name */
    text-align: left;
    display: block;
    /* Add any other styling properties you need */
}
.Tabular {
    border-collapse: collapse;
    width: 100%;
}

.Tabular th, .Tabular td {
    border: 1px solid black;
    padding: 8px;
    text-align: left;
}
.custom-input-container {
    padding-left: 10px;
}
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
            <button class="active" data-target="tab1" onclick="showTabOther('tab1')">Unit Information</button>
           <!--  <button data-target="tab2" onclick="showTabOther('tab2')">License Information</button> -->
        </div>
       <div class="action-buttons" > 
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/workorders/list','Work Order')">Cancel</button>
       </div>
     </div>

        <div id="tab1" class="tab-content active">
          <form id="editForm" action="/CWFM/principalEmployer/view/${principalEmployer.unitId
          }" method="post">
    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                        <tr>
                           
                        
                         <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workOrderNumber"/></label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="sapWorkorderNumber" value="${workorder.sapWorkorderNumber}" style="height: 20px;"  size="30" maxlength="30" readonly/>
                              </div></td>
                        <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitName"/></label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="name" value="${principalEmployer.name}" style="height: 20px;"  size="30" maxlength="30" readonly />
                              </div></td>
                             <!--  <td >  <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/workorders/list','Work Order');">Cancel</button> -->
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorName"/></label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" name="contractorname" value="${contractor.contractorName}" style="height: 20px;"  size="30" maxlength="30" readonly />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorCode"/></label></th>
                            <td><input type="text" name="contractorcode" value="${contractor.contractorCode}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.validFrom"/></label></th>
                            <td><input type="text" name="validFrom" value="${workorder.validFrom}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.validTo"/></label></th>
                            <td><input type="text" name="validTo" value="${workorder.validTo}" style="height: 20px;"  size="30" maxlength="30"  readonly/></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.workOrderType"/></label></th>
                            <td><input type="text" name="typeId" value="${workorder.typeId}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.departmentName"/></label></th>
                            <td><input type="text" name="depId" value="${workorder.depId}"  style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.areaName"/></label></th>
                            <td><input type="text" name="secId" value="${workorder.secId}"  style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.costCentre"/></label></th>
                            <td><input type="text" name="costCenter" value="${workorder.costCenter}" style="height: 20px;"  size="30" maxlength="30" readonly/></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.glCode"/></label></th>
                            <td><input type="text" name="glCode" value="${workorder.glCode}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.jobDescription"/></label></th>
                            <td><input type="text" name="job" value="${workorder.glCode}" style="height: 20px;"  size="30" maxlength="30" readonly /></td>
                        
                        </tr>
        </tbody>
    </table>
   <!--  <div style="text-align: center;">
        <button type="submit" class="btn btn-primary">Submit</button>
        <button type="button" class="btn btn-secondary" onclick="history.back();">Back</button>
    </div> -->
</form>

        </div>
        
         <div id="tab2" class="tab-content">
            <table cellspacing="0" cellpadding="0" style="width:100%;border: 1px solid #ddd;background-color: aliceblue;">
                   
       <thead>
		<tr style=" border: 1px solid #ddd;">
					<th style="color:gray;"><spring:message code="label.job"/></th>
					<th style="color:gray;"><spring:message code="label.serviceCode"/></th>
					<th style="color:gray;"><spring:message code="label.trade"/></th>
					<th style="color:gray;"><spring:message code="label.skill"/></th>
					<th style="color:gray;"><spring:message code="label.itemQuantity"/></th>
					<th style="color:gray;"><spring:message code="label.rate"/></th>
					<th style="color:gray;"><spring:message code="label.serviceEntryQty"/></th>
				    <th style="color:gray;"><spring:message code="label.wbsCode"/></th>
				    <th style="color:gray;"><spring:message code="label.uom"/></th>
					
		</tr>
	</thead>
	<tbody>
         <c:forEach var="wo" items="${workOrders}">
		   <tr style=" border: 1px solid #ddd;background-color: #f9f9f9 ;">
			<td style="color:black ;text-align:left">${wo.job}</td>
			<td style="color:black ;text-align:left">${wo.serviceCode}</td>
			<td style="color:black ;text-align:left">${wo.trade}</td>
			<td style="color:black ;text-align:left">${wo.skill}</td>
			<td style="color:black ;text-align:left">${wo.itemQuantity}</td>
			<td style="color:black ;text-align:left">${wo.rate}</td>
			<td style="color:black ;text-align:left">${wo.serviceEntryQty}</td>
			<td style="color:black ;text-align:left">${wo.wbsCode}</td>
			<td style="color:black ;text-align:left">${wo.uom}</td>
		 </tr>
	  </c:forEach>
	</tbody>
   </table>
            </div>
        
    </div>
</body>
</html>
