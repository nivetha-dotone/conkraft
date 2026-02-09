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
</style>

   </head>
<body>
 <div class="page-header">
        Principal Employer Page
        <div class="header-buttons">
            <button onclick="saveData()">Save</button>
            <button onclick="refreshPage()">Refresh</button>
        </div>
    </div>
<div id="principalEmployerContent" >

    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                    <table>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Unit Name</label></th>
                            <td>  <div style="padding-right: 15px;">
                            <input type="text" value="DOT1 Solutions Pvt Ltd." style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" readonly="true" />
                            </div>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>PE Inactive</label></th>
                            <td> 
                            <input type="checkbox" value="" style="height: 20px;" onchange="setDataChanged();" />
                            
                            </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Address</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" value="Silver Soft Tech Park, White field, Banglore" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>State</label></th>
                            <td><input type="text" value="Karnataka" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Manager Name</label></th>
                            <td><input type="text" value="Rama Krishna Chepana" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Manager Address</label></th>
                            <td><input type="text" value="Silver Soft Tech Park, White field, Banglore" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Type of Business</label></th>
                            <td><input type="text" value="Attendance & Time Keeping" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Maximum Number of Workmen</label></th>
                            <td><input type="text" value="50" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Maximun Number of Contract Workmen</label></th>
                            <td><input type="text" value="30" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                            <th><label class="custom-label">Current count of Contract Workmen</label></th>
                            <td><input type="text" value="20" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" disabled="true" /></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>BOCWAct Applicability</label></th>
                            <td><input type="checkbox" value="" checked="true" style="height: 20px;" onchange="setDataChanged();" /></td>
                       <th><label class="custom-label"><span class="required-field">*</span>ISMW Act Applicability</label></th>
                            <td><input type="checkbox" value="" checked="false" style="height: 20px;" onchange="setDataChanged();" /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>License Number</label></th>
                            <td><input type="text" value="BNGLR18765" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>PF Code</label></th>
                            <td><input type="text" value="HDHKSNK6728798" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                         <th><label class="custom-label"><span class="required-field">*</span>ESI/EC</label></th>
                            <td><input type="text" value="KRNTK1234567815627" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        
                            <th><label class="custom-label"><span class="required-field">*</span>Factory Licence No</label></th>
                            <td><input type="text" value="NOT APPLICABLE" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>
</div>

