<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
<style>
    body {
        margin: 0;
        overflow-x: hidden;
    }

  #contractWorkmenContent {
    padding: 10px; /* Adjust the padding as needed */
    box-sizing: initial;
    overflow-y: auto;
    height: calc(100vh - 20px); /* Adjust the height calculation as needed */
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
   <!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
   <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script>
    function toggleSection(sectionId) {
        var sectionContent = document.getElementById(sectionId + 'Content');
        if (sectionContent.style.display === 'none') {
            sectionContent.style.display = 'block';
        } else {
            sectionContent.style.display = 'none';
        }
    }

    // Function to expand the first section and collapse the rest
    function expandFirstSection() {
        var sections = document.querySelectorAll('.section-content');
        for (var i = 0; i < sections.length; i++) {
            if (i === 0) {
                sections[i].style.display = 'block';
            } else {
                sections[i].style.display = 'none';
            }
        }
    }

    // Call expandFirstSection function when the document is ready
    document.addEventListener('DOMContentLoaded', function() {
        //expandFirstSection();
    });

    function handleWorkmenDetailClick() {
        expandFirstSection();
    }
    </script>
   </head>
<body>
 
 <div class="page-header">
        Contract Workmen Detail
        <div class="header-buttons">
            <button onclick="saveData()">Save</button>
            <button onclick="refreshPage()">Refresh</button>
        </div>
    </div>

<div id="contractWorkmenContent">
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
				<th><label class="custom-label"><span class="required-field">*</span> Employee Pass Number</label></th>
				<td><input type="text" value="9100000059"   onchange="setDataChanged();" style="width: 140px;height: 20px;" /></td>
				
			</tr>
			
			
			<!-- <tr>
				<th><label class="custom-label"><span class="required-field">*</span> EP Pass No</label></th>
				<td><input type="text" value="9100000059"  onchange="setDataChanged();" size="40" maxlength="50" /></td>
			</tr> --> 
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span> First Name</label></th>
				<td><input type="text" value="Bharathi"   onchange="setDataChanged();" style="width: 140px;height: 20px;" /></td>
				
				<th><label class="custom-label"><span class="required-field">*</span>  Last Name</label></th>
				<td><input type="text" value="Chekka"   onchange="setDataChanged();" style="width: 140px;height: 20px;" /></td>
			<!-- <td rowspan="4" colspan="3">
			<img id="imageId" src="img/profile.jpg" alt="profileImg" width="150" height="150" onclick="document.getElementById('importFile').click();" onchange="setDataChanged();"/>
				</td> -->
			</tr>
			
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span>Gender</label></th>
				<td >
					<select id="value(gender)" name="value(gender)" style="width: 147px;height: 25px;">
					<option value="Female">Female</option>
						<%-- <option  value="Male" <c:if test="${'Male' != gender}">disabled</c:if> <c:if test="${'Male' == gender}">selected</c:if>>Male</option>
						<option  value="Female" <c:if test="${'Female' != gender}">disabled</c:if> <c:if test="${'Female' == gender}">selected</c:if>>Female</option>
					 --%></select>
				</td>
				<th><label class="custom-label"><span class="required-field">*</span> Date of Birth</label></th>
				<td >
					<input id="value(dob)" readonly="true" name="value(dob)" onchange="setAge();" type=text style="width: 140px;height: 20px;" value="01/01/1990"></input>
					<%-- <kvl:date-selector-eot-bot-popup id="value(dob)" eot_bot_enable="bot" start_of_week="1" hide_text_field="true" text_label_field="label.dateofbirth" /> --%>
<!-- 					<span>*dd/mm/yyyy format only</span> -->
				</td>
			</tr>
			
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span>  Father/Husband Name</label></th>
				<td><input type="text" value="Ram"   onchange="setDataChanged();" style="width: 140px;height: 20px;" /></td>
				<td><label class="custom-label"><span class="required-field">*</span>ID Mark</label></td>
				<td><input type="text" value="A Mole on left hand"   onchange="setDataChanged();" style="width: 140px;height: 20px;" /></td>
			</tr>
			</tbody>
			</table>
    </div>
</div>

<div class="section">
    <div class="section-header" onclick="toggleSection('section2')">Address Details:</div>
    <div id="section2Content" class="section-content">
       <table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
		</tbody>
			
      		<tr>
      			<td colspan="2"><label class="custom-label"><span><font color="darkblue"><b>Present Address</b></font></span></label></td>
      			<td colspan="2"><label class="custom-label"><span><font color="darkblue"><b> 	Permanent Address</b></font></span></label></td>
      		</tr>
			<tr>
				<th><label class="custom-label">State</label></th>
				<td class="last-child">
					<select id="value(pStateId)" name="value(pStateId)" onchange="getDistricts();" style="width: 147px;height: 25px;">
							<option value="1">Andhra Pradesh</option>
					</select>
				</td>
				<td><label class="custom-label">State</label></th>
					<td class="last-child">
						<select id="value(permStateId)" name="value(permStateId)" onchange="getDistricts();" style="width: 147px;height: 25px;">
								<option value="1">Andhra Pradesh</option>
						</select>
					</td>
			</tr>
			
			<tr>
				<th><label class="custom-label">District</label></th>
				<td class="last-child">
					<select id="value(pDistrictId)" name="value(pDistrictId)" onchange="getTaluka();" style="width: 147px;height: 25px;">
							<option value="1">Visakhapatnam</option>
					</select>
				</td>
				<th><label class="custom-label">District</label></th>
				<td class="last-child">
					<select id="value(perDistrictId)" name="value(perDistrictId)" onchange="getTaluka();" style="width: 147px;height: 25px;">
							<option value="1">Vishakapatnam</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<th><label class="custom-label">Taluka</label></th>
				<td class="last-child">
					<select id="value(pTaluka)" name="value(pTaluka)" onchange="getTaluka();" style="width: 147px;height: 25px;">
							<option value="1">Gavarapalem</option>
					</select> 
				</td>
				<th><label class="custom-label">Taluka</label></th>
				<td class="last-child">
					<select id="value(permTaluka)" name="value(permTaluka)" onchange="getTaluka();" style="width: 147px;height: 25px;">
							<option value="1">Gavarapalem</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<th><label class="custom-label">Village/City</label></th>
				<td class="last-child">
					<select id="value(pVillage)" name="value(pVillage)" onchange="setDataChanged();" style="width: 147px;height: 25px;">
							<option value="1">Anakapalle</option>
					</select> 
				</td>
				<th><label class="custom-label">Village/City</label></th>
				<td class="last-child">
					<select id="value(permVillage)" name="value(permVillage)" onchange="setDataChanged();" style="width: 147px;height: 25px;">
							<option value="1">Anakapalle</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td><label class="custom-label"> Street</label></td>
				<td><input type="text" value="Park Street"   onchange="setDataChanged();" style="width: 140px;height: 20px;" size="40" maxlength="50" /></td>
				<td><label class="custom-label"> Street</label></td>
				<td><input type="text" value="Park Street"   onchange="setDataChanged();" size="40"  style="width: 140px;height: 20px;" maxlength="50" /></td>
			</tr>
			
			
			<tr>
				<td><label class="custom-label"> Pin Code</label></td>
				<td><input type="text" value="531001"   onchange="setDataChanged();" style="width: 140px;height: 20px;" /></td>
				<th><label class="custom-label"><span></span>Country</label></th>
				<td>
					<select id="value(country)" name="value(country)" onchange="setDataChanged()" style="width: 147px;height: 25px;">
						<option value="India">India</option>
					</select>
				</td>
		   </tr>
			</table>
    </div>
</div>
<!-- Statutory/Regulatory Data: -->
<div class="section">
    <div class="section-header" onclick="toggleSection('section3')">Other Information:</div>
    <div id="section3Content" class="section-content">
       <table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
		   <tr>
		   		<td><label class="custom-label"><span></span>Nearest Police Station</label></td>
				<td>
					<input type="text" value="Anakaplalle PS"  onchange="setDataChanged();" style="width: 140px;height: 20px;" />
				</td>
				<th><label class="custom-label">PF Number</label></th>
				<td><input type="text" value="New Joinee"  onchange="setDataChanged();" style="width: 140px;height: 20px;" /></td>
		   </tr>
		   
			<tr>
				<td><label class="custom-label"><span class="required-field">*</span>Marital Status</label></td>
				<td>
					<select id="value(marstatus)" name="value(marstatus)" onchange="setDataChanged()" style="width: 147px;height: 25px;">
<option value="1">Married</option>
					</select>
				</td>
				<td><label class="custom-label">Referred By</label></td>
				<td><input type="text" value="Rama"   style="width: 140px;height: 20px;" onchange="setDataChanged();" /></td>
			</tr>
			
			<tr>
				<td><label class="custom-label">Academics</label></td>
<%-- 				<td><html:text property="value(academic)" style="width: 140px;height: 20px;"  onchange="setDataChanged();" /></td> --%>
				
				<td>
					<select id="value(academic)" name="value(academic)" style="width: 147px;height: 25px;">
					<option value="1">B.Tech</option>
					</select>
				</td>
				
				
				<td><label class="custom-label"><span class="required-field">*</span> Technical</label></td>
				<td>
					<select id="value(technical)" name="value(technical)" style="width: 147px;height: 25px;" onchange="setDataChanged()">
					<option value="1">Technical</option>
					</select>
				</td>
			</tr>
			
			<tr>
			
				<th><label class="custom-label"><span></span>Is Police verification done ? </label></th>
				<td>
					<select id="value(policeVarificationNum)" name="value(policeVarificationNum)" style="width: 147px;height: 25px;" onchange="setDataChanged()">
						<option value="1">Yes</option>
					</select>
				</td>
				
<%-- 				<td><html:text property="value(bloodgroup)" style="width: 140px;height: 20px;"  onchange="setDataChanged();" /></td> --%>
				
				<td><label class="custom-label">Police Verification Date </label></td>
<%-- 				<td><html:text property="value(policeVarificationNum)" style="width: 140px;height: 20px;"  onchange="setDataChanged();" /></td> --%>
				
				<td >
					<input  id="value(policeVarDate)" name="value(policeVarDate)" onchange="setDataChanged(true);" type=text style="width: 140px;height: 20px;" value="01/02/2024"></input>
					<span>*dd/mm/yyyy</span>
				</td>
				
			</tr>
			
			<tr>
				<td><label class="custom-label"> Vaccination Date</label></td>
<%-- 				<td><html:text property="value(vaccinationDate)" style="width: 140px;height: 20px;"  onchange="setDataChanged();" /></td> --%>
				
				<td >
					<input  id="value(vaccinationDate)" name="value(vaccinationDate)" onchange="setDataChanged(true);" type=text style="width: 140px;height: 20px;" value="01/01/2022"></input>
					<span>*dd/mm/yyyy</span>
				</td>
				
				<td><label class="custom-label">PersonalID </label></td>
				<td><input type="text" value=""  style="width: 140px;height: 20px;"  onchange="setDataChanged();" /></td>
			</tr>
			
			<tr>
				<th><label class="custom-label"><span class="required-field">*</span>Accommodation</label></th>
				<td>
					<select id="value(Accommodation)" name="value(Accommodation)" style="width: 147px;height: 25px;" onchange="setDataChanged()">
					<option value="1">No</option>
					</select>
				</td>
				<th><label class="custom-label"><span></span> 	Mobile Number</label></th>
				<td><input type="text" value="8121312132"  style="width: 140px;height: 20px;"  onchange="setDataChanged();" /></td>
			</tr>
			
			<tr>
				<th><label class="custom-label">Account Number</label></th>
				<td><input type="text" value="11880000003546"  style="width: 140px;height: 20px;" onchange="setDataChanged();" /></td>
				<th><label class="custom-label">Bank IFSC Code</label></th>
				<td><input type="text" value="YESB0001187"  style="width: 140px;height: 20px;"  onchange="setDataChanged();" /></td>
			</tr>
			
			<tr>
				<th><label class="custom-label"><span></span>Prior Experience</label></th>
				<td>
					<select id="value(EssarExp)" name="value(EssarExp)" style="width: 147px;height: 25px;" onchange="setDataChanged()">
						<option value="1">Yes</option>
					</select>
					<th><label class="custom-label"><span></span>Previous EP Number</label></th>
					<td><input type="text" value=""  style="width: 140px;height: 20px;"  onchange="setDataChanged();" /></td>
				</td>
			</tr>
			
			<tr>
				<th><label class="custom-label"><span></span>  Age</label></th>
				<td><input type="text" value="24"  style="width: 140px;height: 20px;"  onchange="setDataChanged();" readonly="true"/></td>
				<th><label class="custom-label"><span></span>  	Date of Termination</label></th>
				<td><input type="text" value="31/3/2024"  style="width: 140px;height: 20px;"  onchange="setDataChanged();" readonly="true"/></td>
			</tr>
			
			<tr>
			<td><label class="custom-label"><span class="required-field">*</span>Emergency Name</label></td>
				<td><input type="text" value="Ram" style="width: 140px;height: 20px;"   maxlength="30" onchange="setDataChanged();" /></td>				
			<td><label class="custom-label"><span class="required-field">*</span>Emergency Number</label></td>
				<td><input type="text" value="8121312125"  style="width: 140px;height: 20px;"   maxlength="10" onchange="setDataChanged();" /></td>				
			
			</tr>
			<tr>
			<td><label class="custom-label"> Blood Group</label></td>
				<td><%--<html:text property="value(bloodgroup)" style="width: 140px;height: 20px;"  onchange="setDataChanged();" /> --%>
				<select id="value(bloodgroup)" name="value(bloodgroup)" onchange="setDataChanged()" style="width: 147px;height: 25px;">
						<option value="1">B+</option>
					</select>
				</td>
			
			</tr>
			
		</tbody>
			</table>
    </div>
</div>
<div class="section">
    <div class="section-header" onclick="toggleSection('section4')">Employment Information</div>
    <div id="section4Content" class="section-content">
        <table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<td><label class="custom-label"><span class="required-field">*</span>Vendor Code</label></td>
				<td>							
					<select id="value(ccode)" name="value(ccode)"  style="width: 147px;height: 25px;">
					<option value="1">991212</option>
						<%-- <c:forEach var="ccode" items="${availContrCodes}">
							<option  value="<c:out value="${ccode.contractorid}"/>" <c:if test="${ccode.contractorid == contrid}">selected</c:if> <c:if test="${ccode.contractorid != contrid}">disabled</c:if>>
								<c:out value="${ccode.vendorCode}" /> - <c:out value="${ccode.contractorName}" />
							</option>
						</c:forEach> --%>
					</select>
				</td>
				
				
				<td><label class="custom-label"><span class="required-field">*</span> Work Order Code</label></td>
				<td>							
					<select id="value(workorder)" name="value(workorder)"  style="width: 147px;height: 25px;">
					<option value="1">9876543210</option>
							<%-- <c:forEach var="worder" items="${availworkorders}">
								<option  value="<c:out value="${worder.workorderId}"/>" <c:if test="${worder.wkNum == WOID}">selected</c:if> <c:if test="${worder.wkNum != WOID}">disabled</c:if>>
									<c:out value="${worder.wkNum}" />
								</option>
							</c:forEach> --%>
					</select>
				</td>
			</tr>
			
			<tr>
				<td><label class="custom-label"><span class="required-field">*</span> Trade</label></td>
				<td>
					<select id="value(tradeId)" name="value(tradeId)" style="width: 147px;height: 25px;">
							<option value="1">SE</option>
							<%-- <c:forEach var="trade" items="${availTradeNames}">
								<option value="<c:out value="${trade.tradeId}"/>"
									<c:if test="${trade.tradeId == tId}">selected</c:if> <c:if test="${trade.tradeId != tId}">disabled</c:if>>
									<c:out value="${trade.tradeName}" />
								</option>
						</c:forEach> --%>
					</select>
				</td>

				<td><label class="custom-label"><span class="required-field">*</span> Skill</label></td>
				<td>
					<select id="value(skillId)" name="value(skillId)"  style="width: 147px;height: 25px;">
							<option value="1">Skilled</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td><label class="custom-label"><span class="required-field">*</span>Engineering-in-Charge</label></td>
				<td><input type="text" value="7000001"   style="width: 140px;height: 20px;" readonly="true"/></td>
				<td><label class="custom-label"><span class="required-field">*</span>Department</label></td>
				<td><input type="text" value="Administration"   style="width: 140px;height: 20px;" onchange="setDataChanged();" readonly="true"/></td>
			</tr>
			
			<tr>
				<td><label class="custom-label"><span class="required-field">*</span> Nature of Job</label> </td>
				<td>
					<select id="value(nojobs)" name="value(nojobs)" style="width: 147px;height: 25px;">
						<option value="1">Manpower</option>
					</select>
				</td>
				
				<td><label class="custom-label"><span class="required-field">*</span>WC Policy/ESIC Reg Number</td>
				<td>
					<select id="value(wcnumber)" name="value(wcnumber)" onchange="setDataChanged();" style="width: 147px;height: 25px;">
						<option value="1">ESIC12345</option>
					</select>
				</td>
				
<%-- 				<td><label class="custom-label"><span class="required-field">*</span> <fmt:message key="label.basic" /></label></td> --%>
<%-- 				<td><html:text property="value(basic)" onkeypress="return isNumberAndDot(event)"  onchange="setDataChanged()" style="width: 140px;height: 20px;" /></td> --%>
			</tr>
			
			<tr>
				<td><label class="custom-label"><span class="required-field">*</span>Hazardous Area</label></td>
				<td>
					<select id="value(HazardousArea)" name="value(HazardousArea)" onchange="setDataChanged()" style="width: 147px;height: 25px;">
					<option value="1">No</option>
					</select>
				</td>
				<td><label class="custom-label"><span class="required-field">*</span> Access Area</label></td>
				<td>
					<select id="value(AccessGroup)" name="value(AccessGroup)" style="width: 147px;height: 25px;">
							<option value="1">L1</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td><label class="custom-label"><span class="required-field">*</span>ID Proof Type</label></td>
				<td>
					<select id="value(IDPType)" name="value(IDPType)" onchange="setDataChanged()" style="width: 147px;height: 25px;">
					<option value="1">Aadhar Card</option>
					</select>
				</td>
				<td><label class="custom-label"><span class="required-field">*</span>Aadhaar Number</label></td>
				<td><input type="text" value="750463236747"  onchange="setDataChanged();"  style="width: 140px;height: 20px;"/></td>
			</tr>
			
			
			<tr>
				
				<td><label class="custom-label"><span> </span>Health Check Up Date</label></td>
				<td >
					<input  id="value(OHCDate)" name="value(OHCDate)" onchange="setDataChanged(true);" type=text style="width: 140px;height: 20px;" value="01/01/2024"></input>
					<span>*dd/mm/yyyy format only</span>
				</td>
				<td><label class="custom-label"><span> </span>UAN Number</label></td>
				<td><input type="text" value="New Joinee"   onchange="setDataChanged();" style="width: 140px;height: 20px;"/></td>
		
			</tr>
		</tbody>
			</table>
    </div>
</div>
	
	
</div>
 
</body>
</html>
