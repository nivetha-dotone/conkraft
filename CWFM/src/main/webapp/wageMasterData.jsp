<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        body {
            margin: 0;
            overflow-x: hidden;
        }

        #workmenWageContent {
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: calc(100vh - 20px);
        }

        .page-header {
            background-color: #005151;
            color: #fff;
            padding: 10px;
            text-align: center;
            font-size: 20px;
            margin: 0;
            display: flex; /* Use flexbox */
            justify-content: space-between; /* Align content at the start and end */
            align-items: center; /* Center items vertically */
        }

        .header-buttons {
            /* float: right; Remove this line */
        }

        .custom-label {
            text-align: left;
            display: block;
            font-weight: normal;
        }

        .custom-input {
            height: 20px;
        }

        .required-field {
            color: red;
        }

        table {
            width: 100%;
        }

        th, td {
            padding: 5px;
            text-align: left;
        }
    </style>
</head>
<body>
    <div class="page-header">
        Workmen Wages
        <div class="header-buttons">
            <!-- <button onclick="saveData()">Save</button> -->
            <button onclick="refreshPage()">Refresh</button>
        </div>
    </div>
<div id="workmenWageContent">

    <table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
            <tr>
                <td>
                    <table>
                    <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Entry Pass Number</label></th>
                            <td>  <div style="padding-right: 15px;">
                            <input type="text" value="9100000059" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" readonly="true" />
                            </div>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Unit Name</label></th>
                            <td>  <div style="padding-right: 15px;">
                            <input type="text" value="DOT1 Solutions Pvt Ltd." style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" readonly="true" />
                            </div>
                            </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Vendor Code</label></th>
                            <td>  <div style="padding-right: 15px;">
                            <input type="text" value="991212" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" readonly="true" />
                            </div>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Trade</label></th>
                            <td> 
                           <input type="text" value="SE" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" readonly="true" />
                            
                            </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Skill</label></th>
                            <td>
                              <div style="padding-right: 15px;">
                              <input type="text" value="Skilled" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" />
                              </div></td>
                         <th><label class="custom-label"><span class="required-field">*</span>Effective Date</label></th>
                            <td><input type="text" value="01/01/2024" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                       
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Wage Type</label></th>
                            <td><input type="text" value="Piece Rate" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>Basic</label></th>
                            <td><input type="text" value="500" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                       
                        </tr>
                        
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>DA</label></th>
                            <td><input type="text" value="100" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field">*</span>House Rent Allowance</label></th>
                            <td><input type="text" value="5000" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field"></span>Conveyance</label></th>
                            <td><input type="text" value="30" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                            <th><label class="custom-label">Fair Wages</label></th>
                            <td><input type="text" value="20" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field"></span>Washing Allowance</label></th>
                            <td><input type="text" value="500"  style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30"/></td>
                       <th><label class="custom-label"><span class="required-field"></span>Leave Wages</label></th>
                            <td><input type="text" value="100"  style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30"/></td>
                        
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field"></span>Uniform Allowance</label></th>
                            <td><input type="text" value="500" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        <th><label class="custom-label"><span class="required-field"></span>Skill Allowance</label></th>
                            <td><input type="text" value="1000" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        
                        </tr>
                        <tr>
                         <th><label class="custom-label"><span class="required-field"></span>Special Allowance</label></th>
                            <td><input type="text" value="1000" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        
                            <th><label class="custom-label"><span class="required-field"></span>Food Allowance</label></th>
                            <td><input type="text" value="0.00" style="height: 20px;" onchange="setDataChanged();" size="40" maxlength="30" /></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>
</div>
</body>
</html>
