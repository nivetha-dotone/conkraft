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

    #minWageContent {	
        padding: 20px;
        box-sizing: border-box;
        overflow-y: auto;
        height: calc(100vh - 20px);
    }

     tr {
        margin-bottom: 10px; /* Adjust the value as needed */
    }

    /* Add spacing between <td> elements */
    td {
        padding-bottom: 10px; /* Adjust the value as needed */
    }
    .custom-label {
    font-family: Arial, sans-serif; /* Replace 'Your-Desired-Font' with the actual font name */
    text-align: left;
    display: block;
    /* Add any other styling properties you need */
}

.custom-input-container {
    padding-left: 10px;
}

.custom-input {
    height: 40px; /* Adjust the height as needed */
    font-family: Arial, sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}

.custom-input-checkbox {
    height: 20px; /* Adjust the height as needed */
    font-family: Arial, sans-serif; /* Same font as the label */
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
        .bold {
        font-weight: bold;
    }
</style>
 <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

         td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
    </style>
   </head>
<body>
 <div class="page-header">
        Minimum Wage Master
        <div class="header-buttons">
           <!--  <button onclick="saveData()">Save</button> -->
            <button onclick="refreshPage()">Refresh</button>
        </div>
    </div>
<div id="minWageContent">
  <table>
    <tr>
	<th  class="last-child"><label>Principal Employer: </label>
	  <select id="selectedPE" name="value(unitId)"> 
	  <option value="1">DOT1 Solutions Pvt Ltd</option>
	  </select>
	</th>
						<th > <label>Search Date: </label>
						<input id="value(validFrom)" 
										name="value(validFrom)" 
										type=text size=20  value="01/01/2024">
								</input>
										
						</th>
	  <th>
                        <button id="cms.label.search" action="">
                        Search</button>
   </th>
   </tr>
   
  </table>
  
 <table class="ControlLayout" cellspacing="0" cellpadding="0">
	<thead style="background-color: rgb(189, 215, 238);">
	<br>
        <tr class="bold">
         	<td>Zone</td>
            <td>State</td>
            <td>Trade</td>
            <td>Skill</td>
            <td>Basic</td>
            <td>DA per Day</td>
            <td>Other Allowance</td>
            <td>Effective From</td>
            <td>Total</td>
        </tr>
    </thead>
    <tbody>
        <tr>
        	<td>ZONE-1</td>
            <td>Karnataka</td>
            <td>Helper</td>
            <td>Skilled</td>
            <td>250</td>
            <td>30</td>
            <td>70</td>
             <td>01/01/2024</td>
            <td>350</td>
        </tr>
        <tr>
        <td>ZONE-1</td>
            <td>Maharashtra</td>
            <td>Fitter</td>
            <td>Unskilled</td>
            <td>200</td>
            <td>20</td>
            <td>50</td>
             <td>01/02/2024</td>
            <td>270</td>
        </tr>
        <tr>
        <td>ZONE-2</td>
            <td>Karnataka</td>
            <td>Welder</td>
            <td>Semi Skilled</td>
            <td>230</td>
            <td>20</td>
            <td>60</td>
             <td>01/01/2024</td>
            <td>310</td>
        </tr>
    </tbody>
</table>
</div>

