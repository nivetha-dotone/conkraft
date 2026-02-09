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
                            <script src="resources/js/cms/principalEmployer.js"></script>
                            <script src="resources/js/cms/contractor.js"></script>
                            <script src="resources/js/cms/workmen.js"></script>
                            <title>Requester Form</title>
                            <style>
                                body {
                                    margin: 0;
                                    overflow-x: hidden;
                                    font-family: 'Noto Sans', sans-serif;
                                }

                                #requesterContent {
                                    padding: 20px;
                                    box-sizing: border-box;
                                    overflow-y: auto;
                                    height: calc(100vh - 60px);
                                }

                                .page-header {
                                    background-color: #005151;
                                    color: #fff;
                                    padding: 0px;
                                    text-align: center;
                                    font-size: 15px;
                                    font-family: 'Noto Sans', sans-serif;
                                    position: fixed;
                                    width: 100%;
                                    top: 0;
                                    left: 0;
                                    z-index: 1000;
                                }

                                .custom-label {
                                    font-family: 'Noto Sans', sans-serif;
                                    text-align: left;
                                    display: block;
                                    margin-bottom: 5px;
                                    font-weight: bold;
                                }

                                .custom-select,
                                .custom-input,
                                .custom-textarea {
                                    width: 100%;
                                    padding: 8px;
                                    border: 1px solid #ccc;
                                    border-radius: 4px;
                                    font-family: 'Noto Sans', sans-serif;
                                    font-size: 14px;
                                }

                                .custom-textarea {
                                    resize: vertical;
                                    min-height: 100px;
                                }

                                .required-field {
                                    color: red;
                                    font-weight: bold;
                                }

                                .form-table {
                                    width: 100%;
                                    border-collapse: collapse;
                                    margin-top: 70px;
                                }

                                .form-table th,
                                .form-table td {
                                    padding: 10px;
                                    text-align: left;
                                    border-bottom: 1px solid #ddd;
                                    vertical-align: top;
                                }

                                .form-table th {
                                    background-color: #f5f5f5;
                                    width: 200px;
                                }

                                .action-buttons {
                                    margin-top: 20px;
                                    text-align: center;
                                }

                                .btn {
                                    padding: 10px 20px;
                                    margin: 0 5px;
                                    border: none;
                                    border-radius: 4px;
                                    cursor: pointer;
                                    font-family: 'Noto Sans', sans-serif;
                                    font-size: 14px;
                                }

                                .btn-primary {
                                    background-color: #005151;
                                    color: white;
                                }

                                .btn-secondary {
                                    background-color: #6c757d;
                                    color: white;
                                }

                                .btn-success {
                                    background-color: #28a745;
                                    color: white;
                                }

                                .error-label {
                                    color: red;
                                    display: none;
                                    font-size: 12px;
                                    margin-top: 5px;
                                }

                                .file-upload {
                                    position: relative;
                                    display: inline-block;
                                }

                                .auto-generated-info {
                                    background-color: #f8f9fa;
                                    border: 1px solid #dee2e6;
                                    border-radius: 4px;
                                    padding: 10px;
                                    margin-top: 10px;
                                }
                            </style>
                        </head>

                        <body>
                            <div class="page-header">
                                <h1>REQUESTER</h1>
                            </div>

                            <div id="requesterContent">
                                <f:form id="requesterForm" action="/CWFM/RequestorCon/saveRequestor"
                                    modelAttribute="requesterObj" method="post" enctype="multipart/form-data"
                                    autocomplete="off">

                                    <table class="form-table" cellspacing="0" cellpadding="0">
                                        <tbody>
                                            <!-- Principal Employer -->
                                            <tr>
                                                <th>
                                                    <label class="custom-label">
                                                        <span class="required-field">*</span>
                                                        <spring:message code="label.principalEmployer" />
                                                    </label>
                                                </th>
                                                <td>
                                                    <select class="custom-select" id="principalEmployer"
                                                        name="principalEmployerId"
                                                        onchange="getContractorsAndTrades(this.value, '${sessionScope.loginuser.userAccount}')">
                                                        <option value="">Please select Principal Employer</option>
                                                        <c:forEach var="pe" items="${PrincipalEmployer}">
                                                            <option value="${pe.id}" ${GatePassObj.principalEmployer eq
                                                                pe.id ? 'selected="selected"' : '' }>
                                                                ${pe.description}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <label id="error-principalEmployer" class="error-label">Principal
                                                        Employer is required</label>
                                                </td>
                                            </tr>

                                            <!-- Contractor -->
                                            <tr>
                                                <th>
                                                    <label class="custom-label">
                                                        <span class="required-field">*</span>
                                                        Contractor
                                                    </label>
                                                </th>
                                                <td>
                                                    <select class="custom-select" id="contractor" name="contractorId">
                                                        <option value="">Please select Contractor</option>
                                                        <c:forEach var="contractor" items="${Contractors}">
                                                            <option value="${contractor.contractorId}"
                                                                ${GatePassObj.contractorId eq contractor.contractorId
                                                                ? 'selected="selected"' : '' }>
                                                                ${contractor.contractorName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <label id="error-contractor" class="error-label">Contractor is
                                                        required</label>
                                                </td>
                                            </tr>
                                            <!-- Name -->
                                                
                                             <tr>
                                                <th>
                                                    <label class="custom-label">
                                                        <span class="required-field">*</span>
                                                        Name
                                                    </label>
                                                </th>
                                                <td>
                                                    <input type="text" class="custom-input" id="name"
                                                        name="name" maxlength="20"
                                                        placeholder="Enter Name"
                                                        value="${requesterObj.name}"
                                                       >
                                                    <label id="error-name" class="error-label">Name is required</label>
                                                </td>
                                            </tr>
                                            
                                            <!-- Aadhar Number -->
                                            <tr>
                                                <th>
                                                    <label class="custom-label">
                                                        <span class="required-field">*</span>
                                                        Aadhar Number
                                                    </label>
                                                </th>
                                                <td>
                                                    <input type="text" class="custom-input" id="aadharNumber"
                                                        name="aadharNumber" pattern="[0-9]{12}" maxlength="12"
                                                        placeholder="Enter 12-digit Aadhar number"
                                                        value="${requesterObj.aadharNumber}"
                                                        oninput="this.value = this.value.replace(/[^0-9]/g, '')">
                                                    <label id="error-aadharNumber" class="error-label">Valid 12-digit
                                                        Aadhar number is required</label>
                                                </td>
                                            </tr>

                                            <!-- Department -->
                                            <tr>
                                                <th>
                                                    <label class="custom-label">
                                                        <span class="required-field">*</span>
                                                        <spring:message code="label.department" />
                                                    </label>
                                                </th>
                                                <td>
                                                    <select class="custom-select" id="department" name="departmentId"
                                                        onchange="getAreabyDept(); getEic();">
                                                        <option value="">Please select Department</option>
                                                        <c:forEach var="dept" items="${Departments}">
                                                            <option value="${dept.departmentId}"
                                                                ${GatePassObj.department eq dept.departmentId
                                                                ? 'selected="selected"' : '' }>
                                                                ${dept.department}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <label id="error-department" class="error-label">Department is
                                                        required</label>
                                                </td>
                                            </tr>

                                            <!-- Academic Qualification -->
                                            <tr>
                                                <th>
                                                    <label class="custom-label">
                                                        <span class="required-field">*</span>
                                                        <spring:message code="label.academic" />
                                                    </label>
                                                </th>
                                                <td>
                                                    <select class="custom-select" id="academic" name="academicId">
                                                        <option value="">Select Educational Qualification</option>
                                                        <c:forEach var="option" items="${Academics}">
                                                            <option value="${option.gmId}" ${GatePassObj.academic eq
                                                                option.gmId ? 'selected="selected"' : '' }>
                                                                ${option.gmName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <label id="error-academic" class="error-label">Academic
                                                        qualification is required</label>
                                                </td>
                                            </tr>

                                            <!-- Additional Qualification -->
                                            <tr>
                                                <th>
                                                    <label class="custom-label">
                                                        Additional Qualification
                                                    </label>
                                                </th>
                                                <td>
                                                    <textarea class="custom-textarea" id="additionalQualification"
                                                        name="additionalQualification" maxlength="1000"
                                                        placeholder="Enter additional qualifications (max 1000 Character)"
                                                        onkeyup="updateCharCount()">${requesterObj.additionalQualification}</textarea>
                                                    <div style="text-align: right; font-size: 12px; color: #666;">
                                                        <span id="charCount">max 1000 characters
                                                    </div>
                                                </td>
                                            </tr>

                                            <!-- Attach CV -->
                                            <tr>
                                                <th>
                                                    <label class="custom-label">
                                                        <span class="required-field">*</span>
                                                        Attach CV
                                                    </label>
                                                </th>
                                                <td>
                                                    <div class="file-upload">
                                                        <input type="file" id="attachCV" name="attachCV" accept=".pdf"
                                                            onchange="handleFileUpload(this)" required>
                                                        <small style="color: #666;">Only PDF files are allowed</small>
                                                    </div>
                                                    <label id="error-attachCV" class="error-label">CV attachment is
                                                        required</label>
                                                </td>
                                            </tr>

                                            <!-- Auto-generated Information -->
                                            <tr>
                                                <th>
                                                    <label class="custom-label">
                                                        Document Summary
                                                    </label>
                                                </th>
                                                <td>
                                                    <div class="auto-generated-info">
                                                        <textarea class="custom-textarea" id="shortNote"
                                                            name="shortNote" readonly
                                                            placeholder="Important information from uploaded document will appear here automatically..."></textarea>
                                                        <small style="color: #666;">This field is auto-populated based
                                                            on the uploaded CV</small>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>

                                    <div class="action-buttons">
                                        <button type="button" class="btn btn-secondary"
                                            onclick="draftRequester()">Draft</button>
                                        <button type="button" class="btn btn-success"
                                            onclick="saveRequester()">Save</button>
                                        <button type="button" class="btn btn-primary"
                                            onclick="cancelForm()">Cancel</button>
                                    </div>

                                </f:form>
                            </div>

                            <script>
                                // Character count for additional qualification
                                function updateCharCount() {
                                    const textarea = document.getElementById('additionalQualification');
                                    const charCount = document.getElementById('charCount');
                                    charCount.textContent = textarea.value.length;
                                }

                                // Initialize character count on page load
                                document.addEventListener('DOMContentLoaded', function () {
                                    updateCharCount();
                                });

                                // Handle file upload and extract text
                                function handleFileUpload(input) {
                                    const file = input.files[0];
                                    if (file && file.type === 'application/pdf') {
                                        // Simulate document text extraction (in real implementation, you would use a PDF parsing library)
                                        extractTextFromPDF(file);
                                    } else {
                                        alert('Please select a valid PDF file');
                                        input.value = '';
                                    }
                                }

                                // Simulate PDF text extraction (placeholder function)
                                function extractTextFromPDF(file) {
                                    // In a real implementation, you would use a library like PDF.js or send to server for processing
                                    // For now, we'll simulate with a placeholder
                                    const shortNote = document.getElementById('shortNote');
                                    shortNote.value = "Auto-extracted information from " + file.name + ":\n\n" +
                                        "• Document contains professional experience details\n" +
                                        "• Educational qualifications mentioned\n" +
                                        "• Contact information available\n" +
                                        "• Skills and certifications listed\n\n" +
                                        "Note: This is a simulated extraction. In production, actual PDF content would be analyzed.";
                                }

                                // Form submission functions
                                function draftRequester() {
                                    if (validateForm()) {
                                        document.getElementById('requesterForm').action = '/CWFM/RequestorCon/saveRequestor';

                                        // Create hidden input for status
                                        const statusInput = document.createElement('input');
                                        statusInput.type = 'hidden';
                                        statusInput.name = 'status';
                                        statusInput.value = '1'; // Draft status
                                        document.getElementById('requesterForm').appendChild(statusInput);

                                        document.getElementById('requesterForm').submit();
                                    }
                                }

                                function saveRequester() {
                                    if (validateForm()) {
                                        document.getElementById('requesterForm').action = '/CWFM/RequestorCon/saveRequestor';

                                        // Create hidden input for status
                                        const statusInput = document.createElement('input');
                                        statusInput.type = 'hidden';
                                        statusInput.name = 'status';
                                        statusInput.value = '2'; // Save status
                                        document.getElementById('requesterForm').appendChild(statusInput);

                                        document.getElementById('requesterForm').submit();
                                    }
                                }

                                function cancelForm() {
                                    if (confirm('Are you sure you want to cancel? All unsaved changes will be lost.')) {
                                        window.history.back();
                                    }
                                }

                                // Form validation
                                function validateForm() {
                                    let isValid = true;

                                    // Reset all error messages
                                    const errorLabels = document.querySelectorAll('.error-label');
                                    errorLabels.forEach(label => label.style.display = 'none');

                                    // Validate Principal Employer
                                    const principalEmployer = document.getElementById('principalEmployer');
                                    if (!principalEmployer.value) {
                                        document.getElementById('error-principalEmployer').style.display = 'block';
                                        isValid = false;
                                    }

                                    // Validate Contractor
                                    const contractor = document.getElementById('contractor');
                                    if (!contractor.value) {
                                        document.getElementById('error-contractor').style.display = 'block';
                                        isValid = false;
                                    }

                                    // Validate Aadhar Number
                                    const aadharNumber = document.getElementById('aadharNumber');
                                    if (!aadharNumber.value || aadharNumber.value.length !== 12) {
                                        document.getElementById('error-aadharNumber').style.display = 'block';
                                        isValid = false;
                                    }

                                    // Validate Department
                                    const department = document.getElementById('department');
                                    if (!department.value) {
                                        document.getElementById('error-department').style.display = 'block';
                                        isValid = false;
                                    }

                                    // Validate Academic
                                    const academic = document.getElementById('academic');
                                    if (!academic.value) {
                                        document.getElementById('error-academic').style.display = 'block';
                                        isValid = false;
                                    }

                                    // Validate CV attachment
                                    const attachCV = document.getElementById('attachCV');
                                    if (!attachCV.files.length) {
                                        document.getElementById('error-attachCV').style.display = 'block';
                                        isValid = false;
                                    }

                                    return isValid;
                                }

                                // Function to get contractors based on principal employer (similar to original)
                                function getContractorsAndTrades(principalEmployerId, userAccount) {
                                    if (principalEmployerId) {
                                        // AJAX call to get contractors
                                        // This would be implemented based on your existing contractor.js
                                        if (!unitId) {
                                            alert("Please select a Principal Employer.");
                                            return;
                                        }

                                        // Fetch contractors
                                        getContractors(unitId, userAccount);

                                        getDepartments(unitId);
                                        console.log('Getting contractors for Principal Employer ID: ' + principalEmployerId);


                                    }
                                }

                                function getDepartments(unitId) {
                                    var xhr = new XMLHttpRequest();
                                    var url = contextPath + "/requestor/getAllDepartments?unitId=" + unitId;
                                    console.log("Fetching departments from URL:", url);
                                    xhr.open("GET", url, true);

                                    xhr.onload = function () {
                                        if (xhr.status === 200) {
                                            var departments = JSON.parse(xhr.responseText);
                                            console.log("Departments:", departments);
                                            var departmentSelect = document.getElementById("department");

                                            // Clear existing options
                                            departmentSelect.innerHTML = '<option value="">Please select Department</option>';

                                            // Populate the trade dropdown
                                            departments.forEach(function (department) {
                                                var option = document.createElement("option");
                                                option.value = department.departmentId;
                                                option.text = department.department;
                                                departmentSelect.appendChild(option);
                                            });
                                        } else {
                                            console.error("Error fetching departments:", xhr.statusText);
                                        }
                                    };

                                    xhr.onerror = function () {
                                        console.error("Request failed while fetching departments");
                                    };

                                    xhr.send();
                                }

                                function getContractors(unitId, userAccount) {
                                    var xhr = new XMLHttpRequest();
                                    var url = contextPath + "/requestor/getAllContractors?unitId=" + unitId + "&userAccount=" + userAccount;
                                    //alert("URL: " + url);
                                    xhr.open("GET", url, true);

                                    xhr.onload = function () {
                                        if (xhr.status === 200) {
                                            // Parse the response as a JSON array of contractor objects
                                            var contractors = JSON.parse(xhr.responseText);
                                            console.log("Response:", contractors);

                                            // Find the contractor select element
                                            var contractorSelect = document.getElementById("contractor");

                                            // Clear existing options
                                            contractorSelect.innerHTML = '<option value="">Please select Contractor</option>';

                                            // Populate the dropdown with the new list of contractors
                                            contractors.forEach(function (contractor) {
                                                var option = document.createElement("option");
                                                option.value = contractor.contractorId;
                                                option.text = contractor.contractorName;
                                                contractorSelect.appendChild(option);
                                            });
                                        } else {
                                            console.error("Error:", xhr.statusText);
                                        }
                                    };

                                    xhr.onerror = function () {
                                        console.error("Request failed");
                                    };

                                    xhr.send();
                                }
                                // Function to get areas by department (similar to original)
                                function getAreabyDept() {
                                    const departmentId = document.getElementById('department').value;
                                    if (departmentId) {
                                        console.log('Getting areas for Department ID: ' + departmentId);
                                    }
                                }

                                // Function to get EIC (similar to original)
                                function getEic() {
                                    console.log('Getting EIC information');
                                }
                            </script>

                        </body>

                        </html>