<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
  <head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css">  -->
      <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
  <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/commonjs.js"></script> 
    <script src="resources/js/jquery-3.6.0.min.js"></script>
<style>
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
    font-weight: normal; /* Remove bold effect */
}
     tr {
        margin-bottom: 10px; /* Adjust the value as needed */
    }

    /* Add spacing between <td> elements */
    td {
        padding-bottom: 10px; /* Adjust the value as needed */
    }
    .custom-label {
    font-family: 'Your-Desired-Font', sans-serif; /* Replace 'Your-Desired-Font' with the actual font name */
    text-align: left;
    display: block;
    /* Add any other styling properties you need */
}

.custom-input-container {
    padding-left: 10px;
}

.custom-input {
    height: 40px; /* Adjust the height as needed */
    font-family: 'Your-Desired-Font', sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}

.custom-input-checkbox {
    height: 20px; /* Adjust the height as needed */
    font-family: 'Your-Desired-Font', sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}
.required-field {
    color: red; /* Change this to your desired color */
    margin-right: 4px; /* Add space to the right of the * */
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
          /*   margin-top: -40px; */
            margin-right: 10px;
        }
        table {
        width: 100%;
        border-collapse: collapse;
        table-layout: fixed;
    }
    th, td {
        padding: 5px; /* Adjust padding as needed */
        text-align: left; /* Adjust text alignment as needed */
        /* border: 1px solid #ddd; */ /* Add borders for better visualization */
    }
</style>
 <style>
        body {
            margin: 0;
            overflow-x: hidden;
        }

        .section {
            margin-bottom: 20px;
        }

        .section-header {
            cursor: pointer;
            background-color: #005151;
            padding: 10px;
             color: white;
        }

        .section-content {
            display: block;
            padding: 10px;
        }
    </style>
    <style>
    /* Apply a right border to each table cell */
    .Tabular td, .Tabular th {
        border-right: thin solid #000;
        width: 100%;
        border-collapse: collapse;
         /* Adds a right border to each cell */
    }
</style>
<script>
    function toggleSection(sectionId) {
        var content = document.getElementById(sectionId);
        if (content.style.display === "none" || content.style.display === "") {
            content.style.display = "block";  // Show content
        } else {
            content.style.display = "none";  // Hide content
        }
    }
</script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
    $(document).ready(function(){
        $(".section-header").click(function(){
            $(this).next(".section-content").slideToggle();
        });

        // Close Section 2 and Section 3 on page load
        $(".section-header").not(":first").next(".section-content").hide();
    });
    </script>
   </head>
<body>
 
<!--  <div class="page-header">
        BVR EDIT ADD
        <div class="header-buttons">
            <button onclick="saveData()">Save</button>
            <button onclick="refreshPage()">Refresh</button>
        </div>
    </div> -->

<div id="billVerificationContent">
 <div class="section">
    <div class="section-header" onclick="toggleSection('section1')">Basic Data:</div>
    <div id="section1Content" class="section-content">
       <table cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<th colspan="3"><label class="custom-label"><font color="grey"> * indicates fields that cannot be editable 
					</font></label>
				</th>
				<th style="display: none"><input type="text" value=""   onchange="setDataChanged();" size="40" maxlength="50" /></th>
			</tr>
			<tr >
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.transactioId"/></label></th>
				<td><input type="text" value="9104018702"   onchange="setDataChanged();" style="width: 180px;height: 20px;" /></td>
				
			</tr>
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.unitCode"/></label></th>
				<td><input type="text" value="590E"   onchange="setDataChanged();" style="width: 180px;height: 20px;" /></td>
				
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.unitName"/></label></th>
				<td><input type="text" value="AIIL-AHMEDABAD"   onchange="setDataChanged();" style="width: 180px;height: 20px;" /></td>
			<!-- <td rowspan="4" colspan="3">
			<img id="imageId" src="img/profile.jpg" alt="profileImg" width="150" height="150" onclick="document.getElementById('importFile').click();" onchange="setDataChanged();"/>
				</td> -->
			</tr>
			
			<tr>Bill End Date
				<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.vendorCode"/></label></th>
				<td><input type="text" value="135597"   onchange="setDataChanged();" style="width: 180px;height: 20px;" /></td>
				
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.contractorName"/></label></th>
				<td><input type="text" value="Innovsource Srvices Pvt Ltd"   onchange="setDataChanged();" style="width: 180px;height: 20px;" /></td>
					
			</tr>
			
			
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.billEndDate"/></label></th>
				<td >
					<input id="value(bed)" readonly="true" name="value(bed)" onchange="setAge();" type=text style="width: 180px;height: 20px;" value="08/10/2024"></input>
					<%-- <kvl:date-selector-eot-bot-popup id="value(dob)" eot_bot_enable="bot" start_of_week="1" hide_text_field="true" text_label_field="label.dateofbirth" /> --%>
<!-- 					<span>*dd/mm/yyyy format only</span> -->
				</td>
				<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billStartDate"/></label></th>
				<td >
					<input id="value(bsd)" readonly="true" name="value(bsd)" onchange="setAge();" type=text style="width: 180px;height: 20px;" value="01/10/2024"></input>
					<%-- <kvl:date-selector-eot-bot-popup id="value(dob)" eot_bot_enable="bot" start_of_week="1" hide_text_field="true" text_label_field="label.dateofbirth" /> --%>
<!-- 					<span>*dd/mm/yyyy format only</span> -->
				</td>
			</tr>
			
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.workorderCode"/></label></th>
				<td><input type="text" value="5703000909"   onchange="setDataChanged();" style="width: 180px;height: 20px;" /></td>
				
				<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billType"/></label></th>
				<td >
					<select id="value(billtype)" name="value(billtype)" style="width: 180px;height: 25px;">
					<option value="Female">Regular</option>
					</select>
				</td>			
			</tr>
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.workorderValidFrom"/></label></th>
				<td >
					<input id="value(wvf)" readonly="true" name="value(wvf)" onchange="setAge();" type=text style="width: 180px;height: 20px;" value="28/04/2021"></input>
					<%-- <kvl:date-selector-eot-bot-popup id="value(dob)" eot_bot_enable="bot" start_of_week="1" hide_text_field="true" text_label_field="label.dateofbirth" /> --%>
<!-- 					<span>*dd/mm/yyyy format only</span> -->
				</td>
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.workorderValidTo"/></label></th>
				<td >
					<input id="value(wvt)" readonly="true" name="value(wvt)" onchange="setAge();" type=text style="width: 180px;height: 20px;" value="01/01/2025"></input>
					<%-- <kvl:date-selector-eot-bot-popup id="value(dob)" eot_bot_enable="bot" start_of_week="1" hide_text_field="true" text_label_field="label.dateofbirth" /> --%>
<!-- 					<span>*dd/mm/yyyy format only</span> -->
				</td>
			</tr>
			<tr>
			<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.billCategory"/></label></th>
				<td >
					<select id="value(billcategory)" name="value(billcategory)" style="width: 180px;height: 25px;">
					<option value="Female">SLA with head count</option>
					</select>
				</td>		
			</tr>
			
			<tr>
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
			</tr>
			</tbody>
			</table>
    </div>
</div>

<div class="section">
    <div class="section-header" onclick="toggleSection('section2')">Kronos Reports:</div>
    <div id="section2Content" class="section-content">
       <table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.standardMusterRollReport"/></label></th>
				<td><input type="file" value="No file selected"   onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
				
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.billVerificationAbstractReport"/></label></th>
				<td><input type="file" value="No file selected"   onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
					
			</tr>
			
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.wageCostReport"/></label></th>
				<td><input type="file" value="No file selected"   onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
				
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.bonusReport"/></label></th>
				<td><input type="file" value="No file selected"   onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
					
			</tr>
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span> <spring:message code="label.extraHoursReport"/></label></th>
				<td><input type="file" value="No file selected"   onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
			</tr>
			</tbody>
		</table>
    </div>
</div>
<!-- Statutory/Regulatory Data: -->
<div class="section">
    <div class="section-header" onclick="toggleSection('section3')">Statuory Regulatory Attachments:</div>
    <div id="section3Content" class="section-content">
       <table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
		   <tr>
		   		<th><label class="custom-label"><span></span><spring:message code="label.formA"/></label></th>
				<td>
					<input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" />
				</td>
				<th><label class="custom-label"><spring:message code="label.formB"/></label></th>
				<td><input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
		   </tr>
		   
			   <tr>
		   		<th><label class="custom-label"><span></span><spring:message code="label.formC"/></label></th>
				<td>
					<input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" />
				</td>
				<th><label class="custom-label"><spring:message code="label.formD"/></label></th>
				<td><input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
		   </tr>
			 <tr>
		   		<th><label class="custom-label"><span></span><spring:message code="label.ecrpf"/></label></th>
				<td>
					<input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" />
				</td>
				<th><label class="custom-label"><spring:message code="label.challanandCopyofRemittancePF"/></label></th>
				<td><input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
		   </tr>
			
			<tr>
		   		<th><label class="custom-label"><span></span><spring:message code="label.esicpf"/></label></th>
				<td>
					<input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" />
				</td>
				<th><label class="custom-label"><spring:message code="label.challanandCopyofRemittanceesic"/></label></th>
				<td><input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
		   </tr>
			
			
			<tr>
		   		<th><label class="custom-label"><span></span><spring:message code="label.bankStatement"/></label></th>
				<td>
					<input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" />
				</td>
				<th><label class="custom-label"><spring:message code="label.annualReturn"/></label></th>
				<td><input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
		   </tr>
			
			
			<tr>
		   		<th><label class="custom-label"><span><spring:message code="label.bonusRegister"/></span></label></th>
				<td>
					<input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" />
				</td>
				<th><label class="custom-label"><spring:message code="label.lwfChallanandRemittance"/></label></th>
				<td><input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
		   </tr>
			
				<tr>
		   		<th><label class="custom-label"><spring:message code="label.challanandCopyofRemittancePT"/></label></th>
				<td>
					<input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" />
				</td>
				<th><label class="custom-label"><spring:message code="label.userAttachment1"/></label></th>
				<td><input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
		   </tr>
				<tr>
		   		<th><label class="custom-label"><spring:message code="label.userAttachment2"/></label></th>
				<td>
					<input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" />
				</td>
				<th><label class="custom-label"><spring:message code="label.userAttachment3"/></label></th>
				<td><input type="file" value="No file selected"  onchange="setDataChanged();" style="width: 165px;height: 21px;" /></td>
		   </tr>
		</tbody>
			</table>
    </div>
</div>
<div class="section">
    <div class="section-header" onclick="toggleSection('section4')">Check List - HR Clearance</div>
    <div id="section3Content" class="section-content">
        <table class="Tabular" cellspacing="0" cellpadding="0" style ="width:140%;">
  <thead> 
 <tr>
    <td style=" border-top: thin solid; border-left: thin solid; border-right: thin solid;" style="text-align: left;"  colspan="7"><b>Part A - Statutory Compliance Status</b></td>
    
  </tr>
</thead>
	<tbody>
		<tr>
    <td style=" border-left: thin solid;border-bottom: thin solid;border-top: thin solid;width:2%; "><b><spring:message code="label.s.no"/></b></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;width:40%;"><b><spring:message code="label.checkPoints"/></b></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;width:30%;"><b><spring:message code="label.statusy/n"/></b></td>
    <td style=" border-top: thin solid; border-bottom: thin solid; text-align: left;width:24%;"><b><spring:message code="label.licenseNoPolicyCodeNo"/></b></td>
    <td style=" border-top: thin solid; border-bottom: thin solid;width:30%;"><b><spring:message code="label.dateofCompilanceValidUpto"/> </b></td>
   </tr>
   <tr>
	 <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >1</td>
    <td  style="text-align: left;border-bottom: thin solid;color: black;" >Licence copy obtained by Vendor under Contract Labour Act 1970.</td>
    			<td style="text-align: center;border-bottom: thin solid;">
					<select id="value(LLStatus)"  name="value(LLStatus)" style="width: 100px;height:20px;">
					  <option  value="1">Yes</option> 
					</select>
	</td>
					<td style="text-align: center;border-bottom: thin solid;" >LL135597</td>
					<td style="text-align: center;border-bottom: thin solid;" >31/12/2025 </td>
				
  </tr> 
 <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >2</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Employee Register ( Form  A ) Rule 2(I)</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(EmpReg)"  name="value(EmpReg)" style="width: 100px;height:20px;">
						<option  value="1"  >Not Applicable</option> 
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
  </tr>
 <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >3</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Wages Register (Form B, Under Central Rule)</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(WageReg)"  name="value(WageReg)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >4</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Register of Loan/ Recovery ( Form  C, Under  Central Rule)</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(LoanRecovery)"  name="value(LoanRecovery)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >5</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Attendance Register  (Form D  Under Central Rule)</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(AttenReg)"  name="value(AttenReg)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >6</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Unified Annual Return under Contract Labour Act Rules 1971( In the month of December every year)</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(AnnualReturn)"  name="value(AnnualReturn)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >7</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Challan & Remittance Confirmation Slip of PF</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(PFSlip)"  name="value(PFSlip)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;width: auto;">
    <input id="value(PFSlipDate)"name="value(PFSlipDate)" onchange="setDataChanged(true);"  type=text style="width: 140px;height: 20px;" value="DD/MM/YYYY">
    </td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >8</td>
    <td  style="text-align: left;border-bottom: thin solid;" >ECR copy of PF</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(PFEcr)"  name="value(PFEcr)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;width: auto;">
    <input id="value(PFECRDate)"name="value(PFECRDate)" onchange="setDataChanged(true);"  type=text style="width: 140px;height: 20px;" value="DD/MM/YYYY">
    </td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >9</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Challan & Remittance Confirmation Slip of ESIC</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(ESICSlip)"  name="value(ESICSlip)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;width: auto;">
    <input id="value(ESICSlipDate)"name="value(ESICSlipDate)" onchange="setDataChanged(true);"  type=text style="width: 140px;height: 20px;" value="DD/MM/YYYY">
    </td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >10</td>
    <td  style="text-align: left;border-bottom: thin solid;" >ECR copy of ESIC</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(ESICEcr)"  name="value(ESICEcr)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;width: auto;">
    <input id="value(ESICECRDate)"name="value(ESICECRDate)" onchange="setDataChanged(true);"  type=text style="width: 140px;height: 20px;" value="DD/MM/YYYY">
    </td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >11</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Bank Statement of wages paid to workmen submitted</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(BankStmntStatus)"  name="value(BankStmntStatus)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;">-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >12</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Challan Copy of Professional Tax</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(PTaxChallan)"  name="value(PTaxChallan)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;width: auto;">
    <input id="value(PTaxDate)"name="value(PTaxDate)" onchange="setDataChanged(true);"  type=text style="width: 140px;height: 20px;" value="DD/MM/YYYY">
    </td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >13</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Accident Policy under Workmen Compensation Act ,in case of workers drawing salary more than Rs 21,000/= Gross Per Month</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(AccidentPolicy)"  name="value(AccidentPolicy)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;">- </td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >14</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Labour Welfare Fund Act (Only in the Month of June & December every year)</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(LaborWelfare)"  name="value(LaborWelfare)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;width: auto;">
    <input id="value(LabWelFundActDate)"name="value(LabWelFundActDate)" onchange="setDataChanged(true);"  type=text style="width: 140px;height: 20px;" value="DD/MM/YYYY">
    </td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >15</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Bonus Register Form C  Rule 4(c ) ( Under Bonus Act 1965)</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(BonusReg)"  name="value(BonusReg)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;">-</td>
    <td style="text-align: center;border-bottom: thin solid;">-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >16</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Leave with Wages</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(LeaveWihtWages)"  name="value(LeaveWihtWages)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;">-</td>
    <td style="text-align: center;border-bottom: thin solid;">-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >17</td>
    <td  style="text-align: left;border-bottom: thin solid;" >Previous Month Wages paid on</td>
   <td style="text-align: center;border-bottom: thin solid;">
					<select id="value(PreMonWage)"  name="value(PreMonWage)" style="width: 100px;height:20px;">
						<option   value="1"> Not Applicable </option>
					</select>
				</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;width: auto;">
    <input id="value(PreMonWageDate)"name="value(PreMonWageDate)" onchange="setDataChanged(true);"  type=text style="width: 140px;height: 20px;" value="DD/MM/YYYY">
    </td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >18</td>
    <td  style="text-align: left;border-bottom: thin solid;width: auto;" ><input type="text"  onchange="setDataChanged();" size="80" maxlength="200" style="width:100% " /></td>
     <td style="text-align: center;border-bottom: thin solid;">-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >19</td>
    <td  style="text-align: left;border-bottom: thin solid;width: auto;" ><input type="text"   onchange="setDataChanged();" size="80" maxlength="200" style="width:100% "/></td>
     <td style="text-align: center;border-bottom: thin solid;">-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
  </tr>
  <tr>
    <td style="border-left: thin solid; border-bottom: thin solid;text-align: center;" >20</td>
    <td  style="text-align: left;border-bottom: thin solid;width: auto;" ><input type="text"   onchange="setDataChanged();" size="80" maxlength="200" style="width:100% " /></td>
     <td style="text-align: center;border-bottom: thin solid;">-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
    <td style="text-align: center;border-bottom: thin solid;" >-</td>
  </tr>
</tbody>
</table>
 </div>
</div>

<div class="Panel">
	<table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
		<tr>
				<th  style="width: 100px;height:20px;">Action Plan</th>
				<td> <input type="text"   readonly="true"    onchange="setDataChanged();" size="100" maxlength="200"/> 
				</td>
		</tr>
			
		</tbody>
   </table> 
</div>	

<table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<th><label class="custom-label"> <spring:message code="label.previousComment"/></label></th>
				<td><input type="text" value=" "   onchange="setDataChanged();" style="width: 265px;height: 150px;" /></td>
				
				<th><label class="custom-label"><spring:message code="label.comment"/></label></th>
				<td><input type="text" value=" "   onchange="setDataChanged();" style="width: 265px;height: 150px;" /></td>
				</tr>
		</tbody>
 </table> 
	
</div>
 
</body>
</html>
