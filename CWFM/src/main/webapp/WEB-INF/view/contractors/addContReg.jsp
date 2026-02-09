<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
     <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
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

        #contractorContent {
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

        .tabs-container {
            display: flex;
            flex-direction: column;
        }

        .tabs {
            display: flex;
            align-items: center;
            border-bottom: 2px solid #005151;
            margin-bottom: 20px;
        }

        .tabs button {
            background-color: #fff; /* Tab background color */
            border: 1px solid #ddd; /* Optional: add a border for visibility */
            border-radius: 3px;
            outline: none;
            padding: 5px 10px; /* Reduced height */
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
            color: #898989; /* Label text color */
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
            padding: 10px; /* Add padding inside cells for spacing around content */
            vertical-align: top; /* Align the content to the top of the cell */
        }
.page-header > div {
            width: 100%; /* Full width for small screens */
            margin-top: 10px; /* Add space above buttons */
            flex-direction: column; /* Stack buttons vertically */
        }
        .action-buttons {
            margin-left: auto; /* Push the buttons to the right */
            display: flex;
            align-items: center;
        }

        .action-buttons button {
            margin-left: 10px; /* Space between buttons */
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
     #contractorRegistrationTable {
        width: 100%;
        border-collapse: collapse;
    }

    #contractorRegistrationTable th, #contractorRegistrationTable td {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem;
    }

    #contractorRegistrationTable th {
        background-color: #DDF3FF;
        color: #005151;
        cursor: pointer;
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        font-size: 0.75rem;
        line-height: 1.2rem;
        padding: 6px;
    }

    .header-text {
        font-family: 'Noto Sans', Arial, sans-serif;
        font-size: 14px;
        font-weight: 600;
        border: 1px solid #ddd;
        white-space: nowrap;
        padding: 8px 10px;
        background-color: #E0E0E0;
        color: #333;
    }

 
    #contractorRegistrationTable .addRow, #contractorRegistrationTable .removeRow {
        background-color: #f0f0f0;
        border: 1px solid #ddd;
        padding: 5px;
        cursor: pointer;
    }

    #contractorRegistrationTable .addRow:hover, #contractorRegistrationTable .removeRow:hover {
        background-color: #e0e0e0;
    }

    #contractorRegistrationTable input[type="text"], #contractorRegistrationTable select, #contractorRegistrationTable input[type="file"] {
        width: 100%;
        box-sizing: border-box;
    }
    
  #contractorRegistrationTable .actionColumn {
        display: flex; /* Use flexbox to align children horizontally */
        justify-content: center; /* Center the buttons horizontally */
        align-items: center; /* Center the buttons vertically */
        gap: 10px; /* Space between the buttons */
    }

    #contractorRegistrationTable .actionColumn button {
        background-color: #f0f0f0; /* Light background color */
        border: 1px solid #ddd; /* Border color */
        padding: 5px 10px; /* Padding around the text */
        cursor: pointer; /* Pointer cursor on hover */
        font-size: 1rem; /* Font size */
        display: inline-block; /* Display buttons inline */
        color: #333; /* Text color */
        border-radius: 4px; /* Rounded corners */
    }

    #contractorRegistrationTable .addRow {
        background-color: #4CAF50; /* Green background for add button */
        color: white; /* White text */
    }

    #contractorRegistrationTable .removeRow {
        background-color: #f44336; /* Red background for remove button */
        color: white; /* White text */
    }

    #contractorRegistrationTable .addRow:hover {
        background-color: #45a049; /* Darker green on hover */
    }

    #contractorRegistrationTable .removeRow:hover {
        background-color: #e53935; /* Darker red on hover */
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
        Contractor Registration Page
        <div class="header-buttons">
            <button type="button" onclick="history.back()">Back</button>
        </div>
    </div> -->

    <div id="contractorContent">
        <div class="tabs-container">
            <div class="tabs">
                <button class="active" data-target="tab1" onclick="showTab('tab1')">Contractor Information</button>
                <!-- <button data-target="tab2" onclick="showTab('tab2')">License Information</button> -->
                <div class="action-buttons">
                    <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="submitForm()">Save</button>
                    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="window.location.href='/path/to/cancel'">Cancel</button>
                </div>
            </div>
        </div>
        <div id="tab1" class="tab-content active">
            <form id="contractorForm" action="/CWFM/contractor/view" method="post">
                <table class="ControlLayout" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Contractor Registration ID</label></th>
                            <td>
                                <div>
                                    <input type="text" id="unitName" style="height: 20px;" size="30" maxlength="30" autocomplete="off"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Principal Employer</label></th>
                            <td>
                                <select class="custom-select"  name="pe">
                                    <option value="">Please select Principal Employer</option>
                                </select>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Vendor Code</label></th>
                            <td>
                                <select class="custom-select"  name="pe" >
                                    <option value="">Please select Vendor Code</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Manager Name</label></th>
                            <td>
                                <div style="padding-right: 15px;">
                                    <input type="text" id="address" style="height: 20px;text-transform: capitalize;" size="30" maxlength="30" autocomplete="off"/>
                                </div>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Location of Work</label></th>
                            <td>
                                <div style="padding-right: 15px;">
                                    <input type="text" id="address" style="height: 20px;text-transform: capitalize;" size="30" maxlength="30" autocomplete="off"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Total Strength</label></th>
                            <td><input type="text" style="height: 20px;" size="30" maxlength="30" autocomplete="off"/></td>
                            <th><label class="custom-label"><span class="required-field">*</span>RC Max Employees</label></th>
                            <td><input type="text" style="height: 20px;" size="30" maxlength="30" autocomplete="off"/></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>PF Number</label></th>
                            <td><input type="text" style="height: 20px;" size="30" maxlength="30" autocomplete="off"/></td>
                            <th><label class="custom-label"><span class="required-field">*</span>Nature of Work</label></th>
                            <td><input type="text" style="height: 20px;text-transform: capitalize;" size="30" maxlength="30" autocomplete="off"/></td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Contract From</label></th>
                            <td><input type="text" style="height: 20px;" size="30" maxlength="30" autocomplete="off"/></td>
                            <th><label class="custom-label">Contract To</label></th>
                            <td>
                                <div style="padding-right: 15px;">
                                    <input type="text" id="address" style="height: 20px;" size="30" maxlength="30" autocomplete="off"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th><label class="custom-label"><span class="required-field">*</span>Contractor Type</label></th>
                            <td>
                                <select name="pe" class="custom-select" >
                                    <option value="">Please select Contract Type</option>
                                </select>
                            </td>
                            <th><label class="custom-label"><span class="required-field">*</span>Is RC Verified?</label></th>
                            <td>
                                <input type="checkbox" id="ISMWAPPLICABILITY" name="ISMWAPPLICABILITY" />
                                <input type="hidden" name="ISMWAPPLICABILITY" value="false"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                
<table  cellspacing="0" cellpadding="0">
<thead> 
<tr>		
 <td colspan="2"><label ><span><font color="darkblue" ><b>  Additional Details Required<span style="color:red"> *</span></b></font></span></label></td>
	</tr>			</thead>
</table>

<table id="contractorRegistrationTable">
    <thead>
        <tr>
         <th>Action</th>
            <th>Work Order Number</th>
            <th>Nature of Job</th>
            <th>Document Type</th>
            <th>WC Code</th>
            <th>WC Total</th>
            <th>WC From</th>
            <th>WC To</th>
            <th>Attachment</th>
        </tr>
    </thead>
    <tbody>
        <tr>
          <td class="actionColumn">
    <!-- "+" and "-" buttons -->
    <button type="button" class="addRow">+</button>
    <button type="button" class="removeRow">-</button>
</td>
                <!-- Input for Work Order Number -->
               <!--  <input type="text" name="workOrderNumber" /> -->
               <td>
                <select class="custom-select" name="pe" >
                    <option value="">Please select Workorder</option>
                </select>
            </td>
            <td>
                <select class="custom-select" name="pe" >
                    <option value="">Please select Nature of Job</option>
                </select>
            </td>
            <td>
                <select class="custom-select" name="pe" >
                    <option value="">Please select Document Type</option>
                </select>
            </td>
            <td>
                <!-- Input for WC Code -->
                <input type="text" name="wcCode" autocomplete="off"/>
            </td>
            <td>
                <!-- Input for WC Total -->
                <input type="text" name="wcTotal" autocomplete="off"/>
            </td>
            <td>
                <!-- Input for WC From -->
                <input type="text" name="wcFrom" placeholder="DD/MM/YYYY" autocomplete="off"/>
            </td>
            <td>
                <!-- Input for WC To -->
                <input type="text" name="wcTo" placeholder="DD/MM/YYYY" autocomplete="off"/>
            </td>
            <td>
                <!-- Input for Attachment -->
                <input type="file" name="attachment" autocomplete="off"/>
            </td>
          <!--   <td><button type="button" class="removeRow">-</button></td> -->
        </tr>
        <!-- Rows will be added dynamically here -->
    </tbody>
</table>
            </form>
        </div>
        
    </div>
    
</body>
</html>
