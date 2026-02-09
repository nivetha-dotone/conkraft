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
    padding: 4px; /* Add padding inside cells for spacing around content */
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
            padding: 0px;
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
        <div class="tabs">
            <button class="active" data-target="tab1" onclick="showTabOther('tab1')">Basic Information</button>
            <button data-target="tab2" onclick="showTabOther('tab2')">Add New Documents</button> 
            <button data-target="tab3" onclick="showTabOther('tab3')">Map Documents to Work order</button> 
    </div>
     <div class="action-buttons" >
            <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="loadCommonList('/contractor/contRegList','Contractor Registration List')">Cancel</button>
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
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorRenewalId"/></label></th>
                <td><input type="text" name="contractorrenewId" value="${contractRenew.contractorRenewId}" style="height: 20px;" size="30" maxlength="30" readonly /></td>
                 </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.unitCode"/></label></th>
                <td><input type="text" name="unitcode" value="${contractRenew.unitCode}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.organization"/></label></th>
                <td><input type="text" name="organization" value="${contractRenew.organisation}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                 <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorCode"/></label></th>
                <td><input type="text" name="contractorcode" value="${contractRenew.contractorCode}" style="height: 20px;width:170px;" size="30" maxlength="30" readonly  /></td>
           
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorName"/></label></th>
                <td><input type="text" name="contractorname" value="${contractRenew.contractorName}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.managerName"/></label></th>
                <td><input type="text" name="managername" value="${contractRenew.managerName}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.locationOfWork"/></label></th>
                <td><input type="text" name="locationofwork" value="${contractRenew.locationOfWork}" style="height: 20px;width:170px;" size="30" maxlength="30" readonly  /></td>
           
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.supervisorName"/></label></th>
                <td><input type="text" name="supervisorname" value="${contractRenew.supervisorName}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.pfCode"/></label></th>
                <td><input type="text" name="pfcode" value="${contractRenew.pfCode}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.emailAddress"/></label></th>
                <td><input type="text" name="emailaddress" value="${contractRenew.emailAdd}" style="height: 20px;width:170px;" size="30" maxlength="30" readonly  /></td>
           
            </tr>
            <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.mobileNumber"/></label></th>
                <td><input type="text" name="mobilenumber" value="${contractRenew.mobileNumber}" style="height: 20px;" size="30" maxlength="30" readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.esicRegistration"/></label></th>
                <td><input type="text" name="esicregistration" value="${contractRenew.esicReg}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractValidTill"/></label></th>
                <td><input type="date" name="contractvalidto" value="${contractRenew.contractorValidTo}" style="height: 20px; color:black;width:170px;" size="30" maxlength="30"  readonly  /></td>
          
            </tr>
             <tr>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractorClassification"/></label></th>
                <td><input type="text" name="contractclass" value="${contractRenew.contractorClass}" style="height: 20px;" size="30" maxlength="30"  readonly /></td>
                <th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractType"/></label></th>
                <td><input type="text" name="contracttype" value="${contractRenew.contractorType}" style="height: 20px;" size="30" maxlength="30"  readonly  /></td>
            </tr>
            
        </tbody>
    </table>
   

        </div>
    <div id="tab2" class="tab-content">
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
            <c:forEach var="item" items="${renewDetails}" varStatus="status"> 
                <tr style=" border: 1px solid #ddd;background-color: #f9f9f9 ;">
                    <td style="color:black ;text-align:center">${item.documentType }</td>
                    <td style="color:black;text-align:center">${item.documentNumber }</td >
                    <td style="color:black;text-align:center">${item.coverage }</td>
                    <td style="color:black;text-align:center">${item.validFrom }</td>
                    <td style="color:black;text-align:center">${item.validTo }</td>
                    <td style="color:black;text-align:center">${item.attachment }</td >
                    <td style="color:black;text-align:center">${item.panIndia}</td>
                    <td style="color:black;text-align:center">${item.subContractor} </td>
                    
                </tr>
             </c:forEach> 
        </tbody>
                </table>
            </div>
            <div id="tab3" class="tab-content">
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
