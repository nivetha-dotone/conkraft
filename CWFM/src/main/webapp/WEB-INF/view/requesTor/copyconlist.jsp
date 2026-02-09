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
                            <script src="resources/js/cms/requestor.js"></script>
                            <title>Requester Form</title>
                            <style>
                                body {
                                    margin: 0;
                                    overflow-x: hidden;
                                    font-family: 'Noto Sans', sans-serif;
                                }

                                #requesterContent {
                                    padding: 20px 40px;
                                    margin-top: 40px;
                                    box-sizing: border-box;
                                    overflow-y: auto;
                                    height: calc(100vh - 60px);
                                }

                                .page-header {
                                    position: fixed;
                                    z-index: 1000;
                                    justify-content: space-between;
                                    align-items: center;
                                }

                                .header-buttons {
                                    display: flex;
                                    gap: 10px;
                                    margin-right: 20px;
                                }

                                /* Updated label styling for consistency with reference layout */
                                .custom-label {
                                    font-family: 'Noto Sans', sans-serif;
                                    text-align: left;
                                    display: block;
                                    margin-bottom: 8px;
                                    font-weight: 600;
                                    color: #333;
                                    font-size: 13px;
                                    width: 174px;
                                }

                                /* Improved input/select/textarea styling to match reference */
                                .custom-select,
                                .custom-input,
                                .custom-textarea {
                                    border: 1px solid #999;
                                    border-radius: 4px;
                                    box-sizing: border-box;
                                    font-size: 13px;
                                    font-family: Arial, sans-serif;
                                    display: block;
                                    width: 100%;
                                    padding: 8px 10px;
                                    background-color: #fff;
                                }

                                .custom-select {
                                    height: 38px;
                                    appearance: none;
                                    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
                                    background-repeat: no-repeat;
                                    background-position: right 8px center;
                                    background-size: 20px;
                                    padding-right: 30px;
                                }

                                .custom-input {
                                    height: 38px;
                                }

                                .custom-textarea {
                                    resize: vertical;
                                    min-height: 120px;
                                    padding: 10px;
                                }

                                .required-field {
                                    color: #d32f2f;
                                    font-weight: bold;
                                    margin-right: 4px;
                                }

                                /* 2-column grid layout matching reference design */
                                .form-table {
                                    width: 100%;
                                    border-collapse: collapse;
                                    margin-top: 70px;
                                    display: grid;
                                    grid-template-columns: 1fr 1fr;
                                    column-gap: 40px;
                                    row-gap: 20px;
                                    background-color: #fff;
                                    padding: 20px;
                                    padding-right: 758px;
                                    border: 1px solid #e0e0e0;
                                    border-radius: 4px;
                                }

                                .form-table tbody {
                                    display: contents;
                                }

                                .form-table tr {
                                    display: contents;
                                }

                                .form-table th,
                                .form-table td {
                                    background-color: transparent;
                                    border: none;
                                    padding: 0;
                                    width: auto;
                                    text-align: left;
                                    vertical-align: top;
                                }

                                /* Form field group - label above input stacked vertically */
                                .form-field-group {
                                    display: flex;
                                    flex-direction: row;
                                    gap: 6px;
                                }

                                .action-buttons {
                                    margin-top: 0;
                                    text-align: right;
                                    position: fixed;
                                    top: 68px;
                                    right: 38px;
                                    z-index: 1001;
                                    display: flex;
                                    gap: 12px;
                                    padding: 4px 36px;
                                }

                                .btn {

                                    margin: 0;
                                    border: none;
                                    border-radius: 4px;
                                    cursor: pointer;
                                    font-family: 'Noto Sans', sans-serif;
                                    font-size: 13px;
                                    font-weight: 600;
                                    transition: all 0.3s ease;
                                    padding: 1px 14px;
                                }

                                .btn-primary {
                                    background-color: #003d3d;
                                    color: white;
                                }

                                .btn-primary:hover {
                                    background-color: #002626;
                                }

                                .btn-success {
                                    background-color: #28a745;
                                    color: white;
                                }

                                .btn-success:hover {
                                    background-color: #218838;
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

                                .loading-overlay {
                                    position: fixed;
                                    top: 0;
                                    left: 0;
                                    width: 100%;
                                    height: 100%;
                                    background-color: rgba(0, 0, 0, 0.7);
                                    display: none;
                                    justify-content: center;
                                    align-items: center;
                                    z-index: 9999;
                                }

                                .loading-content {
                                    background-color: white;
                                    padding: 30px;
                                    border-radius: 10px;
                                    text-align: center;
                                    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
                                }

                                .spinner {
                                    border: 4px solid #f3f3f3;
                                    border-top: 4px solid #005151;
                                    border-radius: 50%;
                                    width: 50px;
                                    height: 50px;
                                    animation: spin 1s linear infinite;
                                    margin: 0 auto 20px;
                                }

                                @keyframes spin {
                                    0% {
                                        transform: rotate(0deg);
                                    }

                                    100% {
                                        transform: rotate(360deg);
                                    }
                                }

                                .loading-text {
                                    font-family: 'Noto Sans', sans-serif;
                                    font-size: 16px;
                                    color: #005151;
                                    margin-bottom: 10px;
                                }

                                .loading-subtext {
                                    font-family: 'Noto Sans', sans-serif;
                                    font-size: 14px;
                                    color: #666;
                                }

                                .btn:disabled {
                                    opacity: 0.6;
                                    cursor: not-allowed;
                                }

                                /* Full width fields span both columns */
                                .form-field-full {
                                    grid-column: 1 / -1;
                                }

                                small {
                                    color: #666;
                                    font-size: 12px;
                                    display: block;
                                    margin-top: 4px;
                                }

                                /* Save/Cancel button styling */
                                .btn1 {
                                    padding: 3px 17px;
                                    font-size: 13px;
                                    cursor: pointer;
                                    border: 1px solid #0176b2;
                                    border-radius: 18px;
                                    background-color: #fff;
                                    color: #0176b2;
                                    font-weight: 600;
                                    transition: all 0.3s ease;
                                }

                                .btn1:hover {
                                    background-color: #0176b2;
                                    color: #ffffff;
                                }

                                .btn1-primary {
                                    color: #0176b2;
                                    border-color: #0176b2;
                                    background-color: #fff;
                                }

                                .btn1-primary:hover {
                                    background-color: #0176b2;
                                    color: #ffffff;
                                }
                            </style>
                        </head>

                        <body>
                            <div id="loadingOverlay" class="loading-overlay">
                                <div class="loading-content">
                                    <div class="spinner"></div>
                                    <div class="loading-text" id="loadingText">Processing your request...</div>
                                    <div class="loading-subtext" id="loadingSubtext">Please wait while we save your data
                                    </div>
                                </div>
                            </div>

                            <div class="page-header">
                                <div class="action-buttons">
                                    <button type="button" class="btn1 btn1-primary"
                                        onclick="saveRequester()">Save</button>
                                    <button type="button" class="btn1 btn1-primary"
                                        onclick="cancelForm()">Cancel</button>
                                </div>
                            </div>

                            <div id="requesterContent">
                                <f:form id="requesterForm" action="/CWFM/requestor/saveRequestor"
                                    modelAttribute="requesterObj" method="post" enctype="multipart/form-data"
                                    autocomplete="off">

                                    <table class="form-table" cellspacing="0" cellpadding="0">
                                        <tbody>
                                            <tr>
                                                <th>
                                                    <div class="form-field-group">
                                                        <label class="custom-label">
                                                            <span class="required-field">*</span>
                                                            <spring:message code="label.principalEmployer" />
                                                        </label>
                                                        <select class="custom-select" id="principalEmployer"
                                                            name="principalEmployerId"
                                                            onchange="requester_getContractorsAndTrades(this.value, '${sessionScope.loginuser.userAccount}')">
                                                            <option value="">Please select Principal Employer</option>
                                                            <c:forEach var="pe" items="${PrincipalEmployer}">
                                                                <option value="${pe.id}"
                                                                    ${requesterObj.principalEmployerId eq pe.id
                                                                    ? 'selected="selected"' : '' }>
                                                                    ${pe.description}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                        <label id="error-principalEmployer"
                                                            class="error-label">Principal Employer is required</label>
                                                    </div>
                                                </th>
                                                <td>
                                                    <div class="form-field-group">
                                                        <label class="custom-label">
                                                            <span class="required-field">*</span>
                                                            Contractor
                                                        </label>
                                                        <select class="custom-select" id="contractor"
                                                            name="contractorId">
                                                            <option value="">Please select Contractor</option>
                                                            <c:forEach var="contractor" items="${contractors}">
                                                                <option value="${contractor.contractorId}"
                                                                    ${requesterObj.contractorId eq
                                                                    contractor.contractorId ? 'selected="selected"' : ''
                                                                    }>
                                                                    ${contractor.contractorName}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                        <label id="error-contractor" class="error-label">Contractor is
                                                            required</label>
                                                    </div>
                                                </td>
                                            </tr>

                                            <tr>
                                                <th>
                                                    <div class="form-field-group">
                                                        <label class="custom-label">
                                                            <span class="required-field">*</span>
                                                            Name
                                                        </label>
                                                        <input type="text" class="custom-input" id="name" name="name"
                                                            maxlength="20" placeholder="Enter Name"
                                                            value="${requesterObj.name}">
                                                        <label id="error-name" class="error-label">Name is
                                                            required</label>
                                                    </div>
                                                </th>
                                                <td>
                                                    <div class="form-field-group">
                                                        <label class="custom-label">
                                                            <span class="required-field">*</span>
                                                            Aadhar Number
                                                        </label>
                                                        <input type="text" class="custom-input" id="aadharNumber"
                                                            name="aadharNumber" pattern="[0-9]{12}" maxlength="12"
                                                            placeholder="Enter 12-digit Aadhar number"
                                                            value="${requesterObj.aadharNumber}"
                                                            oninput="this.value = this.value.replace(/[^0-9]/g, '')">
                                                        <label id="error-aadharNumber" class="error-label">Valid
                                                            12-digit Aadhar number is required</label>
                                                    </div>
                                                </td>
                                            </tr>

                                            <tr>
                                                <th>
                                                    <div class="form-field-group">
                                                        <label class="custom-label">
                                                            <span class="required-field">*</span>
                                                            <spring:message code="label.department" />
                                                        </label>
                                                        <select class="custom-select" id="department"
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
                                                    </div>
                                                </th>
                                                <td>
                                                    <div class="form-field-group">
                                                        <label class="custom-label">
                                                            <span class="required-field">*</span>
                                                            <spring:message code="label.academic" />
                                                        </label>
                                                        <select class="custom-select" id="academic" name="academicId">
                                                            <option value="">Select Educational Qualification</option>
                                                            <c:forEach var="option" items="${Academics}">
                                                                <option value="${option.gmId}" ${requesterObj.academicId
                                                                    eq option.gmId ? 'selected="selected"' : '' }>
                                                                    ${option.gmName}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                        <label id="error-academic" class="error-label">Academic
                                                            qualification is required</label>
                                                    </div>
                                                </td>
                                            </tr>

                                            <tr>
                                                <th colspan="2" class="form-field-full">
                                                    <div class="form-field-group">
                                                        <label class="custom-label">
                                                            Additional Qualification
                                                        </label>
                                                        <textarea class="custom-textarea" id="additionalQualification"
                                                            name="additionalQualification" maxlength="1000"
                                                            placeholder="Enter additional qualifications (max 1000 Character)"
                                                            onkeyup="updateCharCount()">${requesterObj.additionalQualification}</textarea>
                                                        <div style="text-align: right; font-size: 12px; color: #666;">
                                                            max 1000 characters
                                                        </div>
                                                    </div>
                                                </th>
                                            </tr>

                                            <tr>
                                                <th colspan="2" class="form-field-full">
                                                    <div class="form-field-group">
                                                        <label class="custom-label">
                                                            <span class="required-field">*</span>
                                                            Attach CV
                                                        </label>
                                                        <div class="file-upload">
                                                            <input type="file" id="attachCV" name="attachCV"
                                                                accept=".pdf" onchange="handleFileUpload(this)"
                                                                required>
                                                            <small>Only PDF files are allowed</small>
                                                        </div>
                                                        <label id="error-attachCV" class="error-label">CV attachment is
                                                            required</label>
                                                    </div>
                                                </th>
                                            </tr>

                                            <tr>
                                                <th colspan="2" class="form-field-full">
                                                    <div class="form-field-group">
                                                        <label class="custom-label">
                                                            Document Summary
                                                        </label>
                                                        <div class="auto-generated-info">
                                                            <textarea class="custom-textarea" id="shortNote"
                                                                name="shortNote" readonly
                                                                placeholder="Important information from uploaded document will appear here automatically..."></textarea>
                                                            <small>This field is auto-populated based on the uploaded
                                                                CV</small>
                                                        </div>
                                                    </div>
                                                </th>
                                            </tr>
                                        </tbody>
                                    </table>

                                </f:form>
                            </div>

                        </body>

                        </html>