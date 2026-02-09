<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <%@ page isELIgnored="false" %>
            <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
                <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
                        <!DOCTYPE html>
                        <html>

                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <link rel="stylesheet"
                                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
                            <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css">
                            <!-- <script src="resources/js/cms/principalEmployer.js"></script>
                            <script src="resources/js/cms/contractor.js"></script>
                            <script src="resources/js/cms/workmen.js"></script> -->
                            <script src="resources/js/cms/requestor.js"></script>
                            <title>Requester Form</title>
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
                                    height: calc(100vh - 60px);
                                    /* Adjust based on header height */
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
                                    background-color: #fff;
                                    /* Tab background color */
                                    border: 2px solid #005151;
                                    /* Tab border color */
                                    outline: none;
                                    padding: 10px 20px;
                                    /* Reduced height */
                                    cursor: pointer;
                                    font-size: 17px;
                                    transition: background-color 0.3s, color 0.3s;
                                    color: #005151;
                                    /* Tab text color */
                                    font-family: 'Noto Sans', sans-serif;
                                }

                                .tabs button.active {
                                    background-color: #005151;
                                    /* Active tab background color */
                                    color: #fff;
                                    /* Active tab text color */
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
                                    color: #898989;
                                    /* Label text color */
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

                                .custom-input,
                                .custom-input-checkbox {
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
                                    border-collapse: separate;
                                    /* Ensure spacing is applied correctly */
                                    border-spacing: 10px;
                                    /* Adjust the value for the desired gap between cells */
                                }

                                table.ControlLayout th,
                                table.ControlLayout td {
                                    padding: 10px;
                                    /* Add padding inside cells for spacing around content */
                                    vertical-align: top;
                                    /* Align the content to the top of the cell */
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
                                    padding: 5px 10px;
                                    /* Adjust padding to decrease size */
                                    font-size: 12px;
                                    /* Decrease font size */
                                    margin-right: 5px;
                                    /* Space between tabs */
                                    border: 1px solid #ddd;
                                    /* Optional: add a border for visibility */
                                    border-radius: 3px;
                                    /* Optional: rounded corners */
                                }

                                .tabs button.active {
                                    background-color: #005151;
                                    color: white;
                                    border-bottom: 2px solid #005151;
                                }

                                .tab-content {
                                    display: none;
                                    padding: 20px;
                                    background-color: white;
                                    border: 1px solid #ccc;
                                }

                                .tab-content.active {
                                    display: block;
                                }

                                .custom-select {
                                    display: inline-block;
                                    width: 100%;
                                    /* Reduced the width to 50%, adjust as needed */
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
                                    justify-content: space-between;
                                    /* Distribute space between tabs and buttons */
                                    align-items: center;
                                    /* Align items vertically */
                                }

                                .tabs {
                                    display: flex;
                                    flex-wrap: nowrap;
                                    /* Prevent wrapping of tabs */
                                }

                                .tabs button {
                                    margin-right: 10px;
                                    /* Space between tabs */
                                }

                                .action-buttons {
                                    display: flex;
                                    /* Align buttons horizontally */
                                    align-items: center;
                                    /* Center buttons vertically */
                                }

                                .action-buttons button {
                                    margin-left: 10px;
                                    /* Space between buttons */
                                }

                                .custom-mb {
                                    margin-bottom: 2.5rem;
                                    /* Example custom margin */
                                }

                                .custom-radio {
                                    margin-right: 5px;
                                    /* Adjust the spacing between the radio button and the label */
                                    vertical-align: middle;
                                    /* Align the radio button with the label text */
                                }

                                .custom-label-inline {
                                    display: inline-block;
                                    vertical-align: middle;
                                    /* Align the label text with the radio button */
                                    margin-left: 3px;
                                    /* Adjust this value to control the space between the radio button and the label */

                                }

                                td {
                                    padding: 5px;
                                    /* Add padding to cells for spacing */
                                }

                                input[type="radio"] {
                                    margin-right: 5px;
                                    /* Space between radio button and label */
                                    vertical-align: middle;
                                    /* Align radio button with label text */
                                }

                                label {
                                    vertical-align: middle;
                                    /* Align label text with radio button */
                                    display: inline-block;
                                    /* Ensure label appears on the same line as radio button */
                                    color: #495057;
                                    /* Set the text color to a dark shade */
                                    font-family: Arial, sans-serif;
                                }

                                /* #preview {
            width: 200px;
            height: 200px;
            border: 1px solid #ddd;
            margin-top: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        } */
                                #preview img {
                                    max-width: 100%;
                                    max-height: 100%;
                                }

                                .error-label {
                                    color: #d32f2f;
                                    display: none;
                                    font-size: 12px;
                                    margin-top: 4px;
                                    font-weight: 500;
                                }

                                .file-upload {
                                    position: relative;
                                    display: block;
                                    width: 100%;
                                }

                                .file-upload input[type="file"] {
                                    padding: 8px 10px;
                                    border: 1px solid #999;
                                    border-radius: 4px;
                                    width: 100%;
                                    box-sizing: border-box;
                                    background-color: #fff;
                                    font-size: 13px;
                                    cursor: pointer;
                                }

                                .auto-generated-info {
                                    background-color: #f5f5f5;
                                    border: 1px solid #ddd;
                                    border-radius: 4px;
                                    padding: 12px;
                                }

                                .auto-generated-info .custom-textarea {
                                    background-color: #fff;
                                    border: 1px solid #ddd;
                                    margin-bottom: 8px;
                                }
                            </style>
                            <% MasterUser user=(MasterUser) session.getAttribute("loginuser"); String userId=user !=null
                                && user.getUserId() !=null ? String.valueOf(user.getUserId()) : "" ; %>


                                <script>
                                    // Function to validate fields in the current active tab
                                    function validateCurrentTab() {
                                        // Example of validation logic; customize based on your tab's fields
                                        let isValid = true;

                                        // Example: Validate fields in the current active tab
                                        const activeTabId = document.querySelector('.tab-content.active').id;

                                        // Validate specific fields based on the active tab
                                        if (activeTabId === 'tab1') {
                                            const aadharNumber = document.getElementById("aadharNumber").value.trim();
                                            if (aadharNumber === "" || aadharNumber.length !== 12 || isNaN(aadharNumber)) {
                                                document.getElementById("error-aadhar").textContent = "Please enter a valid 12-digit Aadhar number.";
                                                isValid = false;
                                            } else {
                                                document.getElementById("error-aadhar").textContent = ""; // Clear previous error
                                            }

                                            // Add more validation logic for other fields in tab1
                                        }
                                        // Repeat similar validation checks for other tabs if necessary

                                        return isValid;
                                    }

                                    // Function to show the selected tab
                                    function showTab(tabId) {
                                        // Check if current tab fields are valid before switching tabs
                                        if (!validateCurrentTab()) {
                                            return; // Prevent tab switch if validation fails
                                        }

                                        // Hide all tab contents
                                        var tabContents = document.querySelectorAll('.tab-content');
                                        tabContents.forEach(function (content) {
                                            content.classList.remove('active');
                                        });

                                        // Remove active class from all tabs
                                        var tabs = document.querySelectorAll('.tabs button');
                                        tabs.forEach(function (tab) {
                                            tab.classList.remove('active');
                                        });

                                        // Show the selected tab content and add active class to the clicked tab
                                        document.getElementById(tabId).classList.add('active');
                                        document.querySelector('button[data-target="' + tabId + '"]').classList.add('active');
                                    }





                                    /* function showTab(tabId) {
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
                                    } */

                                    document.addEventListener("DOMContentLoaded", function () {
                                        // Set the default tab
                                        showTab('tab1');
                                        initializeDatePicker();
                                    });






                                </script>
                        </head>

                                <body>

                                    <div id="principalEmployerContent">
                                        <div class="tabs-container">
                                            <div class="tabs">
                                                <button class="active" data-target="tab1" onclick="showTabOther('tab1')">Basic
                                                    Information</button>

                                            </div>
                                            <div class="action-buttons">
                                                <button id="saveButton" type="submit"
                                                    class="btn btn-default process-footer-button-cancel ng-binding"
                                                    onclick="saveRequester()">Save</button>
                                                <button type="submit"
                                                    class="btn btn-default process-footer-button-cancel ng-binding"
                                                    onclick="cancelForm()">Cancel</button>
                                            </div>
                                        </div>
                                        <div id="tab1" class="tab-content active">

                                            <table class="ControlLayout" cellspacing="0" cellpadding="0">
                                                <tbody>
                                                <tbody>
                                                    <tr>
                                                        <th><label class="custom-label">
                                                                <span class="required-field">*</span>
                                                                <spring:message code="label.principalEmployer" />
                                                            </label></th>
                                                        <td><select class="custom-select" id="principalEmployer"
                                                                name="principalEmployerId"
                                                                onchange="requester_getContractorsAndTrades(this.value, '${sessionScope.loginuser.userAccount}')">
                                                                <option value="">Please select Principal Employer</option>
                                                                <c:forEach var="pe" items="${PrincipalEmployer}">
                                                                    <option value="${pe.id}" ${requesterObj.principalEmployerId
                                                                        eq pe.id ? 'selected="selected"' : '' }>
                                                                        ${pe.description}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                            <label id="error-principalEmployer" class="error-label">Principal
                                                                Employer is required</label>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th><label class="custom-label">
                                                                <span class="required-field">*</span>
                                                                <spring:message code="label.principalEmployer" />
                                                                Contractor
                                                            </label></th>
                                                        <td>

                                                            <select class="custom-select" id="contractor" name="contractorId">
                                                                <option value="">Please select Contractor</option>
                                                                <c:forEach var="contractor" items="${contractors}">
                                                                    <option value="${contractor.contractorId}"
                                                                        ${requesterObj.contractorId eq contractor.contractorId
                                                                        ? 'selected="selected"' : '' }>
                                                                        ${contractor.contractorName}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                            <label id="error-contractor" class="error-label">Contractor is
                                                                required</label>
                                                        </td>

                                                        <th><label class="custom-label">
                                                                <span class="required-field">*</span>
                                                                Name
                                                            </label></th>
                                                        <td>

                                                            <input type="text" style="height: 20px; width: 231px;" size="30"
                                                                maxlength="30" class="custom-input" id="name" name="name"
                                                                oninput="validateNamePASS(this)" placeholder="Enter Name" value="${requesterObj.name}"
                                                                />

                                                            <label id="error-name" class="error-label">Name is
                                                                required</label>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th><label class="custom-label">
                                                                <span class="required-field">*</span>
                                                                Aadhar Number
                                                            </label></th>
                                                        <td>
                                                            <input type="text" style="height: 20px;" size="30"
                                                                class="custom-input" id="aadharNumber" name="aadharNumber"
                                                                pattern="[0-9]{12}" maxlength="12"
                                                                placeholder="Enter 12-digit Aadhar number"
                                                                value="${requesterObj.aadharNumber}"
                                                                oninput="this.value = this.value.replace(/[^0-9]/g, '')">
                                                            <label id="error-aadharNumber" class="error-label">Valid
                                                                12-digit Aadhar number is required</label>
                                                        </td>

                                                        <th><label class="custom-label">
                                                                <span class="required-field">*</span>
                                                                <spring:message code="label.department" />
                                                            </label></th>
                                                        <td><select class="custom-select" style="width: 231px;" id="department"
                                                                name="departmentId">
                                                                <option value="">Please select Department</option>
                                                                <c:forEach var="dept" items="${departments}">
                                                                    <option value="${dept.departmentId}"
                                                                        ${requesterObj.departmentId eq dept.departmentId
                                                                        ? 'selected="selected"' : '' }>
                                                                        ${dept.department}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                            <label id="error-department" class="error-label">Department is
                                                                required</label>
                                                        </td>

                                                    </tr>
                                                    <tr>
                                                        <th><label class="custom-label">
                                                                <span class="required-field">*</span>
                                                                <spring:message code="label.academic" />
                                                            </label></th>
                                                        <td>
                                                            <select class="custom-select" id="academic" name="academicId">
                                                                <option value="">Select Educational Qualification</option>
                                                                <c:forEach var="option" items="${Academics}">
                                                                    <option value="${option.gmId}" ${requesterObj.academicId eq
                                                                        option.gmId ? 'selected="selected"' : '' }>
                                                                        ${option.gmName}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                            <label id="error-academic" class="error-label">Academic
                                                                qualification is required</label>
                                                        </td>

                                                        <th><label class="custom-label">
                                                                Additional Qualification
                                                            </label></th>
                                                        <td>
                                                            <textarea class="custom-textarea" id="additionalQualification"
                                                                name="additionalQualification" maxlength="1000"
                                                                style=" height: 113px; width: 441px;"
                                                                placeholder="Enter additional qualifications (max 1000 Character)"
                                                                onkeyup="updateCharCount()">${requesterObj.additionalQualification}</textarea>
                                                            <label id="error-additionalQualification"
                                                                class="error-label">Additional Qualification cannot exceed 1000
                                                                characters</label>
                                                            <!-- <div style="text-align: right; font-size: 12px; color: #666;">
                                                                    max 1000 characters
                                                                </div> -->

                                                        </td>

                                                    </tr>
                                                    <tr>
                                                        <th><label class="custom-label">
                                                                <span class="required-field">*</span>
                                                                Attach CV
                                                            </label></th>
                                                        <td>
                                                            <input type="file" id="attachCV" name="attachCV" accept=".pdf"
                                                                onchange="handleFileUpload(this)" required>
                                                            <small>Only PDF files are allowed</small>
                                        </div>
                                        <label id="error-attachCV" class="error-label">CV attachment is
                                            required</label>

                                        <th> <label class="custom-label">
                                                Document Summary
                                            </label></th>
                                        <td>
                                            <textarea class="custom-textarea" style=" height: 113px; width: 441px;"
                                                id="shortNote" name="shortNote" readonly
                                                placeholder="Important information from uploaded document will appear here automatically..."></textarea>
                                            <small>This field is auto-populated based on the uploaded
                                                CV</small>
                                        </td>
                                        </tr>
                                        </tbody>
                                        </table>


                                    </div>
                                    </div>


                                </body>

                        </html>