<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
<style>
    body {
        margin: 0;
        overflow-x: hidden;
    }

    #contractorContent {
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
  body {
    margin: 0;
    overflow-x: hidden;
  }

  .panel {
    margin-bottom: 20px;
  }

  .Tabular {
    border-collapse: collapse;
    width: 100%; /* Set table width to 100% of the page */
  }

  .Tabular th {
    background-color: #005151;
    color: white;
    padding: 8px;
    text-align: left;
  }

  .Tabular td {
    padding: 8px;
  }

  /* Additional styles */
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
</style>

   </head>
<body>
 <div class="page-header">
        Contractor Detail Page
        <div class="header-buttons">
            <button onclick="saveData()">Save</button>
            <button onclick="refreshPage()">Refresh</button>
        </div>
    </div>
<div id="contractorContent">
	<table class="ControlLayout" style="width:100%;" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<td>
					<table>
						<tr>
					
							<th width="10%"><label for="khtmlNameInput">Unit Code</label></th>
							<td width="10%"><input type="text" value="9999"
									styleId="khtmlNameInput" onchange="setDataChanged();" style="width:250px"
									maxlength="40" readonly="true" disabled="true"/></td>
							<th width="10%"><label for="khtmlNameInput">Organization</label></th>
							<td width="10%"><input type="text" value="DOT1 Solutions Pvt Ltd."
									 onchange="setDataChanged();" style="width:250px"
									maxlength="50" readonly="true" disabled="true"/></td>		
						</tr>
						
						<tr>
							<th><label for="khtmlNameInput">Contractor Code</label></th>
							<td ><input type="text" value="991212"
									styleId="khtmlNameInput" onchange="setDataChanged();" style="width:250px"
									maxlength="40" readonly="true" disabled="true"/></td>
							<th><label for="khtmlNameInput">Contractor Name</label></th>
							<td><input type="text" value="Sodexo Pvt Ltd"
									styleId="khtmlNameInput" onchange="setDataChanged();" style="width:250px"
									maxlength="50" readonly="true" disabled="true"/></td>		
						</tr>

						<tr>
							<th><label for="khtmlNameInput">
								Manager Name</label></th>
							<td><input type="text" value="Rama Krishna Chepana"readonly="true" disabled="true"
									styleId="khtmlNameInput" onchange="setDataChanged();" style="width:250px"
									maxlength="50" /></td>
									
								<th><label for="khtmlNameInput">
								Location of Work</label></th>
							<td><input type="text" value="Banglore" readonly="true" disabled="true"
									styleId="khtmlNameInput" onchange="setDataChanged();" style="width:250px"
									maxlength="50" /></td>
						</tr>
						<tr>
									<th><label for="khtmlNameInput">Supervisor Name</label></th>
							<td ><input type="text" value="Suresh Kumar"
									styleId="khtmlNameInput" onchange="setDataChanged();" style="width:250px"
									maxlength="50" readonly="true" disabled="true"/></td>
									<%-- <th><label for="khtmlNameInput">
								<fmt:message key="label.pfnumber" /></label></th> --%>
								<th><label for="khtmlNameInput">PF Code</label></th>
							<td><input type="text" value="PFK12534" style="width:250px"
									styleId="khtmlNameInput" readonly="true" disabled="true" onchange="setDataChanged();" /></td>
							
						</tr>
						<tr>
							<th><label for="khtmlNameInput">Email Address</label></th>
							<td ><input type="text" value="suresh.kumar@gmail.com"
									styleId="khtmlNameInput" onchange="setDataChanged();" style="width:250px"
									maxlength="50" /></td>
									<th><label for="khtmlNameInput">Mobile Number</label></th>
							<td><input type="text" value="8121378786"
									styleId="khtmlNameInput" onchange="setDataChanged();" style="width:250px"
									 maxlength="10" /></td>
						</tr>
						<tr>
							<th><label for="khtmlNameInput">ESIC Registration</label></th>
							<td ><input type="text" value="ESIC1234567132456"
									styleId="khtmlNameInput" readonly="true" disabled="true" style="width:250px"
									maxlength="50" /></td>
									<th><label for="khtmlNameInput">Contract Valid Till</label></th>
							<td><input type="text" value="01/01/2025" disabled="true" readonly="true"
									styleId="khtmlNameInput"  style="width:250px"
									 maxlength="10" /></td>
						</tr>
		<tr>
			<td><label for="khtmlNameInput">Is RC Verified?</label> </td>
			<td> <input type="checkbox" value="" checked="checked" style="height: 20px;" onchange="setDataChanged();" />
			</td>
			
			
			<td><label for="khtmlNameInput">Is LL Verified?</label> </td>
				<td> <input type="checkbox" value="" style="height: 20px;" onchange="setDataChanged();" />
				</td>
			
			</tr>
			
			<tr>
			<td><label for="khtmlNameInput">Is WC Verified?</label> </td>
				<td> <input type="checkbox" value="" checked="checked" style="height: 20px;" onchange="setDataChanged();" />
				</td>
			
			</tr>
						

		</tbody>
	</table>
</div>

<div class="panel second-child">
<table  class="Tabular" cellpadding="0" cellspacing="0">
	<thead>
		<tr><th> Labor License Number</th>
		<th>From Date</th>
		<th>To Date</th> 
		<th>Total</th>
		<th>Active Workmen Count</th>
		</tr> 		
	</thead>
	<tbody >
	<tr>
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
        </tr>
	</tbody>
</table>
</div>


<div class="panel second-child">
<table class="Tabular" cellpadding="0" cellspacing="0">
	<thead>
		<tr><th>WC Policy/ESIC Reg Number <%-- <fmt:message key="label.wc.code" /> --%></th>
		<th>License Type</th>
		<th>Job Name</th>
			<th>From Date</th>
		<th>To Date</th> 
		<th>Total</th>
		<th>Active Workmen Count</th>
		</tr> 		
	</thead>
	<tbody >
	<tr>
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
        </tr>
	</tbody>
</table>
</div>

<div class="panel second-child">
<table class="Tabular" cellpadding="0" cellspacing="0">
	<thead>
		<tr><th> Workorder Number</th>
			<th>From Date</th>
		<th>To Date</th> 
		<th>Active Workmen Count</th>
		<th>Contract Classification</th>
		</tr> 		
	</thead>
	<tbody >
	<tr>
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
        </tr>
	</tbody>
</table>
</div>
</td>
</tr>
</tbody>
</table>
</div>
