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
#workOrderContent {
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
</style>
<style>
.Tabular {
    border-collapse: collapse;
    width: 100%;
}

.Tabular th, .Tabular td {
    border: 1px solid black;
    padding: 8px;
    text-align: left;
}
       /*  table {
            border-collapse: collapse;
            width: 100%;
        }

         th,td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        } */
    </style>
   </head>
<body>
 <div class="page-header">
        Work Order Details
        <div class="header-buttons">
            <button onclick="saveData()">Save</button>
            <button onclick="refreshPage()">Refresh</button>
        </div>
    </div>
<div id="workOrderContent">
	
	<table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                    <table>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Work Order Number</label></th>
                            <td>  <div style="padding-right: 15px;">
                            <input type="text" value="9876543210" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" readonly="true" />
                            </div>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Unit Name</label></th>
                            <td> 
                            <input type="text" value="DOT1 Solutions Pvt Ltd." style="height: 20px;" size="40" maxlength="30"   />
                            
                            </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Contractor Name</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" value="Sodexo Pvt Ltd" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>Vendor Code</label></th>
                            <td><input type="text" value="991212" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Valid From</label></th>
                            <td><input type="text" value="01/01/2024" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Valid To</label></th>
                            <td><input type="text" value="31/12/2024" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Work Order Type</label></th>
                            <td><input type="text" value="BSR" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Department Name</label></th>
                        
                            <td><select id="selectId" name="value(depId)"  style="height: 25px;width:100%" > 
	    						<option value="1">Administration</option>
									</select></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Area Name</label></th>
                            <td><select id="areaSelect" name="value(depId)" style="height: 25px;width:95%" > 
	    <option value="1">Administration</option>
</select></td>
                            <th><label class="custom-label">Cost Centre</label></th>
                            <td><input type="text" value="CC001" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" disabled="true" /></td>
                        </tr>
                        <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>GLCode</label></th>
                            <td><input type="text" value="GL001" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>Job Description</label></th>
                            <td><input type="text" value="Manpower Supply" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>
<div class="panel second-child">	
	<table  class="Tabular" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
					<th>Job</th>
					<th>Service Code</th>
					<th>Trade</th>
					<th>Skill</th>
					<th>Item Quantity</th>
					<th>Rate</th>
					<th>Service Entry Qty</th>
				    <th>WBS Code</th>
				    <th>UOM</th>
					
		</tr>
	</thead>
	<tbody>
	<tr>
            <td>ENGG - BLR</td>
            <td>5000001</td>
            <td>Helper</td>
            <td>Skilled</td>
            <td>1</td>
            <td>990</td>
            <td>20</td>
            <td>350</td>
            <td>EA</td>
        </tr>
        <tr>
            <td>HR - BLR</td>
            <td>5000002</td>
            <td>Fitter</td>
            <td>Unskilled</td>
            <td>20</td>
            <td>18</td>
            <td>20</td>
            <td>270</td>
            <td>M3</td>
        </tr>
        <tr>
            <td>QA - BLR</td>
            <td>5000003</td>
            <td>Welder</td>
            <td>Semi Skilled</td>
            <td>10</td>
            <td>456</td>
            <td>10</td>
            <td>310</td>
               <td>NOS</td>
        </tr>
				
	</tbody>
</table>
</div>
					
				
</div>
