<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reconciliation</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css"/>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet"
      href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <script src="resources/js/cms/workmen.js"></script>

    <style>
        body {
            margin: 0;
            overflow-x: hidden;
            font-family: 'Noto Sans', sans-serif;
            background-color: #FFFFFF;
        }

        #principalEmployerContent {
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: calc(100vh - 20px);
        }

        .tabs-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 12px;
        }

        .tabs {
            display: flex;
            align-items: flex-end;
            border-bottom: 2px solid #005151;
            margin-bottom: 0;
            width: 100%;
            max-width: 680px;
        }

        .tabs button {
            background-color: #fff;
            border: 1px solid #d6d6d6;
            border-bottom: none;
            outline: none;
            padding: 10px 14px;
            cursor: pointer;
            font-size: 12px;
            font-weight: 500;
            color: #005151;
            font-family: 'Noto Sans', sans-serif;
            margin-right: 6px;
            border-radius: 4px 4px 0 0;
            height: 42px;
            line-height: 1.1;
        }

        .tabs button.active {
            background-color: #005151;
            color: #fff;
            border: 1px solid #005151;
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

        .table-container {
            overflow-x: auto;
            margin: 0;
            padding: 0;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
            font-size: 0.875rem;
            font-family: 'Noto Sans', sans-serif;
            color: #898989;
            white-space: nowrap;
            vertical-align: middle;
        }

        th {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
            font-size: 0.875rem;
            font-weight: bold;
            background-color: #DDF3FF;
            color: #005151;
            cursor: pointer;
            font-family: 'Noto Sans', sans-serif;
            line-height: 1.2rem;
        }

        table th {
            border-top: 0.0625rem solid #ddd;
            border-bottom: 1px solid #ddd;
            border-right: none;
            background-color: #DDF3FF;
            color: #005151;
            font-size: 0.85rem;
            line-height: 1.2rem;
            font-family: 'Noto Sans', sans-serif;
            font-weight: bold;
            text-align: left;
            padding: 8px;
            box-sizing: border-box;
        }

        #loaderOverlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.4);
            z-index: 9999;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .loader {
            width: 60px;
            height: 60px;
            border: 6px solid #ddd;
            border-top: 6px solid #1976d2;
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }

        .loader-text {
            margin-top: 15px;
            color: #fff;
            font-size: 16px;
            font-weight: 600;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

        .summary-box {
            margin-top: 10px;
            margin-bottom: 15px;
        }

        .summary-box .alert {
            margin-bottom: 10px;
            text-align: center;
            padding: 10px;
            border-radius: 4px;
            font-weight: 600;
        }

        .alert {
            padding: 10px;
            border-radius: 4px;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .alert-info {
            background: #e8f4fb;
            color: #14597a;
            border: 1px solid #b6d9ec;
        }

        .alert-secondary {
            background: #f1f1f1;
            color: #444;
            border: 1px solid #d7d7d7;
        }

        .alert-success {
            background: #e8f8ed;
            color: #207245;
            border: 1px solid #b9e3c7;
        }

        .alert-danger {
            background: #fdecec;
            color: #a12622;
            border: 1px solid #f3c2c0;
        }

        .alert-warning {
            background: #fff7e6;
            color: #8a5a00;
            border: 1px solid #f1d59a;
        }

        .upload-inline-wrap {
            display: flex;
            align-items: center;
            gap: 12px;
            flex-wrap: wrap;
            margin-bottom: 14px;
        }

        .upload-inline-wrap label {
            margin-right: 8px;
            min-width: 140px;
            font-weight: 600;
            color: #333;
        }

        .upload-inline-wrap input[type="file"] {
            display: inline-block;
            width: auto;
            max-width: 360px;
        }

        .upload-inline-wrap button {
            margin-top: 0 !important;
            vertical-align: middle;
        }

        #messageDiv, #esicmessageDiv, #bankMessageDiv {
            display: none;
            margin-bottom: 15px;
        }

        .text-danger {
            color: #c62828;
        }

        .process-footer-button-cancel {
            white-space: nowrap;
        }

        /* DataTables */
        .dataTables_wrapper {
            margin-top: 10px;
        }

        .dataTables_wrapper .dataTables_filter {
            float: left !important;
            text-align: left !important;
            margin-top: 12px;
            margin-bottom: 0;
        }

        .dataTables_wrapper .dataTables_filter label {
            color: #333 !important;
            font-weight: 600;
            font-size: 13px;
        }

        .dataTables_wrapper .dataTables_filter input {
            margin-left: 8px;
            border: 1px solid #bfbfbf !important;
            border-radius: 4px;
            height: 32px;
            padding: 4px 8px;
            color: #333 !important;
            background: #fff !important;
        }

        .dataTables_wrapper .dataTables_info {
            float: left !important;
            clear: left;
            padding-top: 14px;
            color: #333 !important;
            font-size: 13px;
        }

        .dataTables_wrapper .dataTables_paginate {
            float: right !important;
            text-align: right;
            padding-top: 10px;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button {
            color: #005151 !important;
            background: #fff !important;
            border: 1px solid #cfcfcf !important;
            border-radius: 4px !important;
            margin-left: 4px;
            min-width: 34px;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button.current,
        .dataTables_wrapper .dataTables_paginate .paginate_button.current:hover {
            color: #005151 !important;
            background: #d9eef7 !important;
            border: 1px solid #8cb9cc !important;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            color: #005151 !important;
            background: #eef8fc !important;
            border: 1px solid #8cb9cc !important;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button.disabled,
        .dataTables_wrapper .dataTables_paginate .paginate_button.disabled:hover {
            color: #888 !important;
            background: #f5f5f5 !important;
            border: 1px solid #ddd !important;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button,
        .dataTables_wrapper .dataTables_paginate .paginate_button span,
        .dataTables_wrapper .dataTables_paginate span a {
            color: #005151 !important;
        }

        .dataTables_wrapper:after {
            content: "";
            display: block;
            clear: both;
        }
        
        .dataTables_filter_row {
    width: 100%;
    overflow: hidden;
    margin-bottom: 10px;
}

.dataTables_footer {
    width: 100%;
    overflow: hidden;
    margin-top: 10px;
}

.dataTables_wrapper .dataTables_filter {
    float: left !important;
    text-align: left !important;
    margin-bottom: 10px;
}

.dataTables_wrapper .dataTables_info {
    float: left !important;
    clear: left;
}

.dataTables_wrapper .dataTables_paginate {
    float: right !important;
    text-align: right;
}

.dataTables_wrapper {
    width: 100%;
}

.table-container {
    width: 100%;
    overflow-x: auto;
}





/* Mismatch DataTable search */
#pfMismatchSection .dataTables_filter,
#esicMismatchSection .dataTables_filter,
#bankDetailsSection .dataTables_filter {
    display: block !important;
    float: left !important;
    width: 100%;
    text-align: left !important;
    margin: 10px 0 !important;
}

#pfMismatchSection .dataTables_filter input,
#esicMismatchSection .dataTables_filter input {
    display: inline-block !important;
    width: 220px;
    height: 32px;
    margin-left: 8px;
    padding: 5px 8px;
    border: 1px solid #bfbfbf;
    border-radius: 4px;
    background: #fff;
}

/* Mismatch DataTable information */
#pfMismatchSection .dataTables_info,
#esicMismatchSection .dataTables_info {
    display: block !important;
    float: left !important;
    clear: both;
    padding-top: 12px;
}

/* Mismatch DataTable pagination */
#pfMismatchSection .dataTables_paginate,
#esicMismatchSection .dataTables_paginate {
    display: block !important;
    float: right !important;
    padding-top: 8px;
    text-align: right !important;
}

/* Keep table wrapper full width */
#pfMismatchSection .dataTables_wrapper,
#esicMismatchSection .dataTables_wrapper {
    width: 100%;
    overflow: visible;
}

#pfMismatchSection .table-container,
#esicMismatchSection .table-container {
    width: 100%;
    overflow-x: auto;
}

        /* Bank tab follows the existing PF / ESIC filter and table appearance. */
        .bank-filter-row { display:flex; align-items:center; flex-wrap:wrap; gap:8px; margin-bottom:12px; }
        .bank-filter-row label { color:darkcyan; font-weight:600; margin:0; }
        .bank-filter-row select, .bank-filter-row input { color:gray; min-height:28px; }
        .bank-filter-row select { max-width:320px; }
        .bank-filter-row .datetimepickerActiveWorkmenformat { width:185px; }
        #bankExpectedSection, #bankMismatchSection { margin-top:16px; }
        #bankExpectedSection h5, #bankDetailsSection h5 { color:#005151; }
        #bankMismatchSection h5 { color:#c62828; }
        .bank-filter-row .bank-month { width:170px; }
        .bank-date-note { color:#666; font-size:12px; }
    </style>

    <%
        MasterUser user = (MasterUser) session.getAttribute("loginuser");
        String userId = user != null && user.getUserId() != null ? String.valueOf(user.getUserId()) : "";
    %>
   <script>
function submitForm() {
    var contractorId = document.getElementById("contractor").value;

    if (!contractorId) return;

    document.getElementById("reconForm").submit();
}
</script>
</head>
<body>

<div id="principalEmployerContent">

    <div class="tabs-container">
        <div class="tabs">
            <button type="button" class="active" data-target="tab1" onclick="showTab('tab1', this)">PF Reconciliation</button>
            <button type="button" data-target="tab2" onclick="showTab('tab2', this)">ESIC Reconciliation</button>
            <button type="button" data-target="tab3" onclick="showTab('tab3', this)">Bank Wage Reconciliation</button>
        </div>
        <div>
            <button type="button"
                    class="btn btn-default process-footer-button-cancel ng-binding"
                    onclick="loadCommonList('/contractor/reconciliation', 'Reconciliation');">
                Cancel
            </button>
        </div>
    </div>

    <f:form id="challanForm" autocomplete="off"></f:form>

    <div id="loaderOverlay" style="display:none;">
        <div class="loader"></div>
        <div class="loader-text">please wait...</div>
    </div>
    
<div id="pfEsicFilters">
<tr>

<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractor"/></label></th>
    <td>
            <select id="contractor" name="contractorId">
                <option value="">Please select Contractor</option>

                <c:forEach var="contr" items="${contList}">
                    <option value="${contr.id}"
                        ${contractorId eq contr.id ? 'selected="selected"' : ''}>
                        ${contr.description}
                    </option>
                </c:forEach>
            </select>
    </td>
				<label for="fromDate" style="color: darkcyan;">From Date:</label> 
				<input id="fromDate" name="fromDate" class="datetimepickerActiveWorkmenformat" type="text" autocomplete="off" style="color: gray;">
				<label for="toDate" style="color: darkcyan;">To Date:</label> 
				<input id="toDate" name="toDate" class="datetimepickerActiveWorkmenformat" type="text" autocomplete="off" style="color: gray;">
				
				<button type="button" id="exportBtn" class="btn btn-default process-footer-button-cancel ng-binding" onclick="getReconciliationData()">Search</button>
</tr>
</div>
    <!-- PF TAB -->
    <div id="tab1" class="tab-content active">

        <div id="messageDiv"></div>

        <div class="upload-inline-wrap">
            <label><b>Upload PF Challan</b></label>
            <input type="file" id="pfFile" class="form-control" accept=".pdf,.xls,.xlsx,.csv" />
            <button type="button"
                    class="btn btn-default process-footer-button-cancel ng-binding"
                    onclick="uploadChallan('PF')">
                Upload PF Challan
            </button>
        </div>

        <div class="row summary-box" id="pfSummary" style="display:none;">
            <div class="col-md-3">
                <div class="alert alert-info">Status: <span id="pfStatus"></span></div>
            </div>
            <div class="col-md-3">
                <div class="alert alert-secondary">Total: <span id="pfTotal"></span></div>
            </div>
            <div class="col-md-3">
                <div class="alert alert-success">Verified: <span id="pfVerified"></span></div>
            </div>
            <div class="col-md-3">
                <div class="alert alert-danger">Unverified: <span id="pfUnverified"></span></div>
            </div>
        </div>

        <div class="table-container">
            <table id="pfTable" class="display">
                <thead>
                    <tr>
                        <th>GatePass ID</th>
                        <th>Workmen Name</th>
                        <th>UAN Number</th>
                        <th>PF Number</th>
                        <th>PF Amount</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="w" items="${pfList}">
                        <tr>
                            <td>${w.gatePassId}</td>
                            <td>${w.workmenName}</td>
                            <td>${w.uanNumber}</td>
                            <td>${w.pfNumber}</td>
                            <td>${w.pfAmount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
<div id="pfMismatchExportContainer"
     style="display:none; text-align:right; margin-bottom:10px;">

    <button type="button"
            class="btn btn-default process-footer-button-cancel ng-binding"
            onclick="exportPFMismatches()">
        Export PF Mismatches
    </button>

</div>
        <div id="pfMismatchSection" style="display:none;">
            <h5 class="text-danger mt-3">PF Unverified Data</h5>
            <div class="table-container">
                <table id="pfMismatchTable" class="display">
                    <thead>
                        <tr>
                        <th>
                        <input type="checkbox"
                               class="select-all-mismatch"
                               data-table="pfMismatchTable"
                               title="Select All PF Rows">
                    </th>
                            <th>GatePass ID</th>
                            <th>Workmen Name</th>
                            <th>DB Number</th>
                            <th>Document Number</th>
                            <th>DB Amount</th>
                            <th>Document Amount</th>
                            <th>Mismatch Reason</th>
                        </tr>
                    </thead>
                    <tbody id="pfMismatchBody"></tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- ESIC TAB -->
    <div id="tab2" class="tab-content">

        <div id="esicmessageDiv"></div>

        <div class="upload-inline-wrap">
            <label><b>Upload ESIC Challan</b></label>
            <input type="file" id="esicFile" class="form-control" accept=".pdf,.xls,.xlsx,.csv" />
            <button type="button"
                    class="btn btn-default process-footer-button-cancel ng-binding"
                    onclick="uploadChallan('ESIC')">
                Upload ESIC Challan
            </button>
        </div>

        <div class="row summary-box" id="esicSummary" style="display:none;">
            <div class="col-md-3">
                <div class="alert alert-info">Status: <span id="esicStatus"></span></div>
            </div>
            <div class="col-md-3">
                <div class="alert alert-secondary">Total: <span id="esicTotal"></span></div>
            </div>
            <div class="col-md-3">
                <div class="alert alert-success">Verified: <span id="esicVerified"></span></div>
            </div>
            <div class="col-md-3">
                <div class="alert alert-danger">Unverified: <span id="esicUnverified"></span></div>
            </div>
        </div>

        <div class="table-container">
            <table id="esicTable" class="display">
                <thead>
                    <tr>
                        <th>GatePass ID</th>
                        <th>Workmen Name</th>
                        <th>ESIC Number</th>
                        <th>ESIC Amount</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="w" items="${esicList}">
                        <tr>
                            <td>${w.gatePassId}</td>
                            <td>${w.workmenName}</td>
                            <td>${w.esicNumber}</td>
                            <td>${w.esicAmount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
<div id="esicMismatchExportContainer"
     style="display:none; text-align:right; margin-bottom:10px;">

    <button type="button"
            class="btn btn-default process-footer-button-cancel ng-binding"
            onclick="exportESICMismatches()">
        Export ESIC Mismatches
    </button>

</div>
        <div id="esicMismatchSection" style="display:none;">
            <h5 class="text-danger mt-3">ESIC Unverified Data</h5>
            <div class="table-container">
                <table id="esicMismatchTable" class="display">
                    <thead>
                        <tr>
                         <th>
                        <input type="checkbox"
                               class="select-all-mismatch"
                               data-table="esicMismatchTable"
                               title="Select All ESIC Rows">
                    </th>
                            <th>GatePass ID</th>
                            <th>Workmen Name</th>
                            <th>DB Number</th>
                            <th>Document Number</th>
                            <th>DB Amount</th>
                            <th>Document Amount</th>
                            <th>Mismatch Reason</th>
                        </tr>
                    </thead>
                    <tbody id="esicMismatchBody"></tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- BANK WAGE TAB: same controls and tables as existing PF / ESIC -->
    <div id="tab3" class="tab-content">
        <div id="bankMessageDiv" role="alert" aria-live="polite"></div>

        <div class="bank-filter-row">
            <label for="bankPrincipalEmployer">Principal Employer <span class="required-field">*</span></label>
            <select id="bankPrincipalEmployer" name="principalEmployerId">
                <option value="">Please select Principal Employer</option>
                <c:forEach var="pe" items="${peList}">
                    <option value="${pe.id}"><c:out value="${pe.description}" /></option>
                </c:forEach>
            </select>
            <label for="bankContractor">Contractor <span class="required-field">*</span></label>
            <select id="bankContractor" name="bankContractorId">
                <option value="">Please select Contractor</option>
                <c:forEach var="contr" items="${contList}">
                    <option value="${contr.id}"><c:out value="${contr.description}" /></option>
                </c:forEach>
            </select>
        </div>

        <div class="bank-filter-row">
            <label for="bankFromDate">From Date:</label>
            <input id="bankFromDate" name="bankFromDate" type="text"
                   class="datetimepickerActiveWorkmenformat" autocomplete="off" />
            <label for="bankToDate">To Date:</label>
            <input id="bankToDate" name="bankToDate" type="text"
                   class="datetimepickerActiveWorkmenformat" autocomplete="off" />
            <button type="button" id="bankSearchBtn"
                    class="btn btn-default process-footer-button-cancel ng-binding"
                    onclick="searchBankExpectedWages()">Search</button>
        </div>

<div class="bank-filter-row" style="margin-top:18px;">
            <label for="bankWageMonth">Wage Month <span class="required-field">*</span></label>
            <input id="bankWageMonth" class="bank-month" type="month" />
            <span class="bank-date-note">Bank payment deadline: 7th of following month</span>
        </div>

        <div class="upload-inline-wrap">
            <label for="bankFile"><b>Upload Bank CSV</b></label>
            <input type="file" id="bankFile" class="form-control" accept=".csv,text/csv" />
            <button type="button" class="btn btn-default process-footer-button-cancel ng-binding"
                    onclick="uploadBankWages()">Upload Bank CSV</button>
        </div>

        <div class="row summary-box" id="bankSummary" style="display:none;">
            <div class="col-md-3"><div class="alert alert-success">Status: <span id="bankStatus"></span></div></div>
            <div class="col-md-3"><div class="alert alert-secondary">Upload ID: <span id="bankUploadId"></span></div></div>
            <div class="col-md-3"><div class="alert alert-info">Wage Month: <span id="bankSummaryMonth"></span></div></div>
            <div class="col-md-3"><div class="alert alert-info">Payment Due: <span id="bankDueDate"></span></div></div>
        </div>
        <div id="bankExpectedSection" style="display:none;">
            <h5>Wage Cost Report Data</h5>
            <div class="table-container">
                <table id="bankExpectedTable" class="display">
                    <thead><tr>
                        <th>GatePass ID</th><th>Workmen Name</th>
                        <th>Account Number</th><th>IFSC Code</th>
                        <th>Expected Net Wage</th>
                    </tr></thead>
                    <tbody id="bankExpectedBody"></tbody>
                </table>
            </div>
        </div>

        

        <div id="bankDetailsSection" style="display:none;">
            <h5>Bank Wage Workmen Scoreboard</h5>
            <div class="table-container">
                <table id="bankDetailsTable" class="display">
                    <thead><tr>
                        <th>GatePass ID</th><th>Workmen Name</th><th>Expected Amount</th>
                        <th>Paid Amount</th><th>Difference</th><th>Variance %</th>
                        <th>Rating</th><th>Rating Description</th><th>Account Number</th>
                        <th>IFSC Code</th><th>UTR Number</th><th>Amount Status</th>
                        <th>Payment Date</th><th>Due Date</th><th>Timeliness</th>
                    </tr></thead>
                    <tbody id="bankDetailsBody"></tbody>
                </table>
            </div>
        </div>

        <div id="bankMismatchSection" style="display:none;">
            <h5>Bank Unverified Data / Mismatch Reasons</h5>
            <div class="table-container">
                <table id="bankMismatchTable" class="display">
                    <thead><tr>
                        <th>GatePass ID</th><th>Workmen Name</th>
                        <th>Expected Amount</th><th>Paid Amount</th>
                        <th>Mismatch Reason</th>
                    </tr></thead>
                    <tbody id="bankMismatchBody"></tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    var messageTimers = {};

    function activateBankTab() {
        // Explicitly target the Bank pane. This also handles stale tab state
        // after asynchronous uploads in the dynamically loaded Conkraft page.
        showTab('tab3', $('.tabs button[data-target="tab3"]')[0]);
        $('#tab1, #tab2').removeClass('active').hide();
        $('#tab3').addClass('active').show();
        $('.tabs button').removeClass('active');
        $('.tabs button[data-target="tab3"]').addClass('active');
        $('#pfEsicFilters').hide();
    }

    function showTab(tabId, btn) {

        $(".tab-content").removeClass("active").hide();

        $("#" + tabId).addClass("active").show();
        $("#pfEsicFilters").toggle(tabId !== "tab3");

        $(".tabs button").removeClass("active");

        $(btn).addClass("active");
        // Messages stay inside their own tab.

        // Adjust tables after switching tabs.
        $("#" + tabId).find("table").each(function() {

            if ($.fn.DataTable.isDataTable(this)) {

                $(this).DataTable()
                    .columns.adjust()
                    .draw(false);

            }

        });
    }
    function showLoader() {
        $("#loaderOverlay").show();
    }

    function hideLoader() {
        $("#loaderOverlay").hide();
    }

    function initReconTable(tableId) {
        if ($.fn.DataTable.isDataTable(tableId)) {
            $(tableId).DataTable().destroy();
        }

        $(tableId).DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            pageLength: 5,
            dom: 'rt<"bottom"fi p>',
            language: {
                search: "Filter:",
                paginate: {
                    previous: "Previous",
                    next: "Next"
                },
                emptyTable: "No records found"
            }
        });
    }

    function initMismatchTable(tableSelector) {

        var $table = $(tableSelector);

        if ($table.length === 0) {
            console.error("Mismatch table not found:", tableSelector);
            return;
        }

        if (typeof $.fn.DataTable === "undefined") {
            console.error("DataTables library is not loaded.");
            return;
        }

        // Do not initialize an already initialized table.
        if ($.fn.DataTable.isDataTable($table[0])) {
            console.error("Table is already initialized:", tableSelector);
            return;
        }

        $table.DataTable({

            paging: true,
            searching: true,
            ordering: true,
            info: true,

            lengthChange: false,
            pageLength: 5,
            pagingType: "simple_numbers",

            autoWidth: false,
            processing: false,
            deferRender: false,

            // f = search, t = table, i = information, p = pagination.
            dom: '<"dt-search"f>rt<"dt-footer"ip>',

            language: {
                search: "Search:",
                searchPlaceholder: "Search mismatch records...",

                emptyTable: "No mismatch records found",
                zeroRecords: "No matching mismatch records found",

                info: "Showing _START_ to _END_ of _TOTAL_ records",
                infoEmpty: "Showing 0 to 0 of 0 records",
                infoFiltered: "(filtered from _MAX_ total records)",

                paginate: {
                    previous: "Previous",
                    next: "Next"
                }
            },

            initComplete: function () {

                var api = this.api();

                console.log(
                    tableSelector + " initialized successfully."
                );

                console.log(
                    "Registered mismatch records:",
                    api.rows().count()
                );
            }
        });
    }

    // BANK messages have a dedicated function and never use showMessage().
    // Keep all selectors scoped to the current reconciliation screen.
    function showBankMessage(message, type) {
        var $screen = $('#principalEmployerContent');
        var $bankPane = $screen.find('#tab3');
        var $bankMessage = $bankPane.find('#bankMessageDiv');

        if (!$bankPane.length || !$bankMessage.length) {
            console.error('Bank reconciliation message container not found.');
            return;
        }

        // Select BANK without changing the PF/ESIC result containers.
        $screen.find('.tab-content').removeClass('active').hide();
        $bankPane.addClass('active').show();
        $screen.find('.tabs button').removeClass('active');
        $screen.find('.tabs button[data-target="tab3"]').addClass('active');
        $screen.find('#pfEsicFilters').hide();

        // Only write into the Bank pane, never into ESIC/PF.
        $bankMessage.stop(true, true)
            .removeClass('alert alert-success alert-danger alert-warning alert-info')
            .addClass('alert alert-' + (type || 'info'))
            .attr('role', 'alert')
            .text(message || '')
            .show();

        // Recalculate widths when the Bank pane becomes visible.
        $bankPane.find('table').each(function () {
            if ($.fn.DataTable.isDataTable(this)) {
                $(this).DataTable().columns.adjust().draw(false);
            }
        });
    }

    function showMessage(message, type, reconType) {
        // Existing PF/ESIC message behavior is retained.
        var normalizedType = String(reconType || '').toUpperCase();
        var targetDiv = normalizedType === 'PF' ? '#messageDiv' :
                        normalizedType === 'ESIC' ? '#esicmessageDiv' : null;
        if (!targetDiv) return;
        var $div = $('#principalEmployerContent').find(targetDiv);
        if (messageTimers[targetDiv]) clearTimeout(messageTimers[targetDiv]);
        $div.stop(true, true).removeClass()
            .addClass('alert alert-' + (type || 'info'))
            .attr('role', 'alert').text(message || '').show();
        messageTimers[targetDiv] = setTimeout(function () {
            $div.fadeOut(500);
        }, 8000);
    }

    function getReconFilters() {
        var contractorId = $.trim($("#contractor").val() || "");
        var fromDate = $.trim($("#fromDate").val() || "");
        var toDate = $.trim($("#toDate").val() || "");
        if (!contractorId || !fromDate || !toDate) {
            alert("Please select Contractor, From Date and To Date.");
            return null;
        }
        return { contractorId: contractorId, fromDate: fromDate, toDate: toDate };
    }

    // Converts both yyyy-MM-dd and dd/MM/yyyy (existing datepicker) to yyyy-MM-dd.
    function bankIsoDate(value) {
        value = $.trim(value || '');
        var m;
        if ((m = /^(\d{4})-(\d{2})-(\d{2})$/.exec(value))) return value;
        if ((m = /^(\d{2})\/(\d{2})\/(\d{4})$/.exec(value)))
            return m[3] + '-' + m[2] + '-' + m[1];
        return null;
    }

    function bankFilters() {
        var peId = $.trim($('#bankPrincipalEmployer').val() || '');
        var contractorId = $.trim($('#bankContractor').val() || '');
        var fromDate = bankIsoDate($('#bankFromDate').val());
        var toDate = bankIsoDate($('#bankToDate').val());
        if (!peId || !contractorId) {
            showBankMessage('Please select Principal Employer and Contractor.', 'danger');
            return null;
        }
        if (!fromDate || !toDate) {
            showBankMessage('Please enter valid From Date and To Date.', 'danger');
            return null;
        }
        if (fromDate > toDate) {
            showBankMessage('From Date cannot be after To Date.', 'danger');
            return null;
        }
        return { principalEmployerId:peId, contractorId:contractorId,
                 fromDate:fromDate, toDate:toDate };
    }

    var bankSearchKey = null;
    function bankKey(f) {
        return [f.principalEmployerId, f.contractorId, f.fromDate, f.toDate].join('|');
    }

    function bankReplaceTable(tableId, bodyId, rows, fields) {
        var $table = $(tableId), $body = $(bodyId);
        if ($.fn.DataTable.isDataTable($table[0])) $table.DataTable().clear().destroy();
        $body.empty();
        $.each(rows || [], function (_, row) {
            var $tr = $('<tr>');
            $.each(fields, function (_, field) {
                $('<td>').text(nullSafe(row[field])).appendTo($tr);
            });
            $body.append($tr);
        });
        initReconTable(tableId);
    }

    function bankMismatchRows(errors) {
        return (errors || []).map(function (item) {
            if (typeof item === 'string') return { mismatchReason:item };
            return {
                gatePassId:item.gatePassId,
                workmenName:item.workmenName,
                expectedAmount:item.expectedAmount,
                paidAmount:item.paidAmount,
                mismatchReason:item.mismatchReason || item.reason || item.message
            };
        });
    }

    function showBankMismatches(errors) {
        var rows = bankMismatchRows(errors);
        if (!rows.length) { $('#bankMismatchSection').hide(); return; }
        bankReplaceTable('#bankMismatchTable', '#bankMismatchBody', rows,
            ['gatePassId','workmenName','expectedAmount','paidAmount','mismatchReason']);
        $('#bankMismatchSection').show();
    }

    function showBankScoreboard(details) {
        if (!Array.isArray(details) || !details.length) {
            $('#bankDetailsSection').hide(); return;
        }
        bankReplaceTable('#bankDetailsTable', '#bankDetailsBody', details,
            ['gatePassId','workmenName','expectedAmount','paidAmount','differenceAmount',
             'variancePercentage','rating','ratingDescription','accountNumber',
             'ifscCode','utrNumber','amountStatus','transactionDate',
             'dueDate','timelinessStatus']);
        $('#bankDetailsSection').show();
    }

    function searchBankExpectedWages() {
        var filters = bankFilters();
        if (!filters) return;
        bankSearchKey = null;
        $('#bankExpectedSection, #bankDetailsSection, #bankMismatchSection, #bankSummary').hide();
        showLoader();
        $.ajax({
            // Requires the Bank expected-wages read endpoint in the controller.
            url:'${pageContext.request.contextPath}/contractor/reconciliation/bank/expected',
            type:'GET', data:filters, dataType:'json',
            success:function(response) {
                hideLoader();
                var rows = Array.isArray(response) ? response :
                    (Array.isArray(response.data) ? response.data : response.expectedList);
                if (response.status === 'error') {
                    showBankMessage(response.message || 'Unable to fetch expected wages.', 'danger');
                    return;
                }
                if (!Array.isArray(rows) || !rows.length) {
                    showBankMessage('No Wage Cost Report data found for the selected filters.', 'warning');
                    return;
                }
                bankReplaceTable('#bankExpectedTable', '#bankExpectedBody', rows,
                    ['gatePassId','workmenName','accountNumber','ifscCode','expectedAmount']);
                bankSearchKey = bankKey(filters);
                $('#bankExpectedSection').show();
            },
            error:function(xhr) {
                hideLoader();
                showBankMessage(xhr.status === 404 ?
                    'Bank expected-wages search API is not implemented yet.' :
                    'Unable to load Wage Cost Report data. Check the server log.', 'danger');
            }
        });
    }

    function loadSavedBankDetails(uploadId) {
        if (!uploadId) {
            showBankMessage('Upload succeeded, but the server did not return an Upload ID.', 'warning');
            return;
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/contractor/reconciliation/bank/details',
            type: 'GET',
            dataType: 'json',
            data: { uploadId: uploadId },
            success: function(response) {
                activateBankTab();
                if (response && response.status === 'success') {
                    showBankScoreboard(response.data);
                    if (!response.data || !response.data.length) {
                        showBankMessage('Bank upload saved, but no detail records were returned.', 'warning');
                    }
                } else {
                    showBankMessage((response && response.message) ||
                        'Upload saved, but Bank details could not be loaded.', 'warning');
                }
            },
            error: function(xhr) {
                activateBankTab();
                showBankMessage('Bank upload saved, but unable to fetch BANK_WAGE_RECON_DETAIL. ' +
                    'Check the bank/details endpoint.', 'warning');
            }
        });
    }

    function uploadBankWages() {
        var filters = bankFilters();
        if (!filters) return;
        var wageMonth = $.trim($('#bankWageMonth').val() || '');
        var fileInput = $('#bankFile')[0];
        if (!/^\d{4}-\d{2}$/.test(wageMonth)) {
            showBankMessage('Please select Wage Month.', 'danger'); return;
        }
        if (!fileInput.files || !fileInput.files.length || !/\.csv$/i.test(fileInput.files[0].name)) {
            showBankMessage('Please select a bank CSV file.', 'danger'); return;
        }
        if (bankSearchKey !== bankKey(filters)) {
            showBankMessage('Please click Search and review the Wage Cost Report before uploading.', 'danger');
            return;
        }
        var formData = new FormData();
        formData.append('reconType', 'BANK');
        formData.append('principalEmployerId', filters.principalEmployerId);
        formData.append('contractorId', filters.contractorId);
        formData.append('wageMonth', wageMonth);
        formData.append('fromDate', filters.fromDate);
        formData.append('toDate', filters.toDate);
        formData.append('file', fileInput.files[0]);
        $('#bankSummary, #bankDetailsSection, #bankMismatchSection').hide();
        showLoader();
        $.ajax({
            url:'${pageContext.request.contextPath}/contractor/reconciliation/upload',
            type:'POST', data:formData, processData:false, contentType:false,
            success:function(response) {
                hideLoader();
                if (response && response.status === 'success') {
                    $('#bankStatus').text('ACCEPTED');
                    $('#bankUploadId').text(nullSafe(response.uploadId));
                    $('#bankSummaryMonth').text(wageMonth);
                    var p = wageMonth.split('-');
                    var due = new Date(Date.UTC(Number(p[0]), Number(p[1]), 7));
                    $('#bankDueDate').text(String(due.getUTCDate()).padStart(2,'0') + '/' +
                        String(due.getUTCMonth()+1).padStart(2,'0') + '/' + due.getUTCFullYear());
                    $('#bankSummary').show();
                    showBankMessage(response.message || 'Bank wage reconciliation completed.', 'success');
                    // Displays actual records only if the controller supplies them.
                    loadSavedBankDetails(response.uploadId);
                    showBankMismatches(response.mismatchList || (response.data && response.data.mismatchList));
                } else {
                    showBankMessage(response && response.message || 'Bank reconciliation failed.', 'danger');
                    showBankMismatches(response && (response.errors || response.mismatchList));
                }
            },
            error:function(xhr) {
                hideLoader();
                var response = xhr.responseJSON;
                showBankMessage(response && response.message || 'Bank CSV upload failed. Check server logs.', 'danger');
                showBankMismatches(response && (response.errors || response.mismatchList));
            }
        });
    }

    function uploadChallan(reconType) {
        var fileInput = reconType === 'PF' ? $("#pfFile")[0] : $("#esicFile")[0];

        if (!fileInput.files || fileInput.files.length === 0) {
            showMessage("Please select " + reconType + " challan file.", "danger", reconType);
            return;
        }

        var formData = new FormData();
        formData.append("reconType", reconType);
        formData.append("file", fileInput.files[0]);
        var filters = getReconFilters();
        if (!filters) return;
        formData.append("contractorId", filters.contractorId);
        formData.append("fromDate", filters.fromDate);
        formData.append("toDate", filters.toDate);

        showLoader();

        $.ajax({
            url: "${pageContext.request.contextPath}/contractor/reconciliation/upload",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                hideLoader();

                if (response.status === "success") {
                    var data = response.data;
                    console.log(
                            "Mismatch list count:",
                            data.mismatchList ? data.mismatchList.length : "mismatchList is missing"
                        );
                    bindSummary(reconType, data);
                    bindMismatch(reconType, data.mismatchList);

                    if (data.status === "VERIFIED") {
                        showMessage(reconType + " reconciliation verified successfully.", "success", reconType);
                    } else {
                        showMessage(reconType + " reconciliation completed with mismatches.", "warning", reconType);
                    }
                } else {
                    showMessage(response.message || "Error while processing reconciliation.", "danger", reconType);
                }
            },
            error: function() {
                hideLoader();
                showMessage("Error while uploading " + reconType + " challan.", "danger", reconType);
            }
        });
    }

    function bindSummary(type, data) {
        if (type === 'PF') {
            $("#pfSummary").show();
            $("#pfStatus").text(nullSafe(data.status));
            $("#pfTotal").text(nullSafe(data.totalCount));
            $("#pfVerified").text(nullSafe(data.verifiedCount));
            $("#pfUnverified").text(nullSafe(data.unverifiedCount));
        } else {
            $("#esicSummary").show();
            $("#esicStatus").text(nullSafe(data.status));
            $("#esicTotal").text(nullSafe(data.totalCount));
            $("#esicVerified").text(nullSafe(data.verifiedCount));
            $("#esicUnverified").text(nullSafe(data.unverifiedCount));
        }
    }

    function bindMismatch(type, list) {

        var bodySelector;
        var sectionSelector;
        var tableSelector;

        type = String(type).toUpperCase();

        if (type === "PF") {

            bodySelector = "#pfMismatchBody";
            sectionSelector = "#pfMismatchSection";
            tableSelector = "#pfMismatchTable";

        } else if (type === "ESIC") {

            bodySelector = "#esicMismatchBody";
            sectionSelector = "#esicMismatchSection";
            tableSelector = "#esicMismatchTable";

        } else {

            console.error("Invalid reconciliation type:", type);
            return;
        }

        var $table = $(tableSelector);
        var $body = $(bodySelector);
        var $section = $(sectionSelector);

        // Validate actual HTML elements.
        if ($table.length === 0 || $body.length === 0) {

            console.error("Mismatch HTML elements not found.", {
                table: tableSelector,
                tbody: bodySelector
            });

            return;
        }

        // STEP 1: Destroy previous DataTable BEFORE modifying tbody.
        if ($.fn.DataTable.isDataTable($table[0])) {

            $table.DataTable().clear().destroy();

        }

        // STEP 2: Clear previous mismatch rows.
        $body.empty();

        // STEP 3: Validate backend response.
        if (!Array.isArray(list)) {

            console.error(
                type + " mismatchList is not an array:",
                list
            );

            $section.hide();
            return;
        }

        console.log(
            type + " mismatch records received from backend:",
            list.length
        );

        // STEP 4: Handle empty mismatch records.
        if (list.length === 0) {

            $section.hide();
            return;
        }

        // STEP 5: Append all mismatch records.
        $.each(list, function(index, m) {

            var $row = $("<tr></tr>");

            $row.append(
                $("<td></td>").text(nullSafe(m.gatePassId))
            );

            $row.append(
                $("<td></td>").text(nullSafe(m.workmenName))
            );

            $row.append(
                $("<td></td>").text(nullSafe(m.dbNumber))
            );

            $row.append(
                $("<td></td>").text(nullSafe(m.docNumber))
            );

            $row.append(
                $("<td></td>").text(nullSafe(m.dbAmount))
            );

            $row.append(
                $("<td></td>").text(nullSafe(m.docAmount))
            );

            $row.append(
                $("<td></td>").text(nullSafe(m.mismatchReason))
            );

            $body.append($row);

        });

        // STEP 6: Verify rows are actually present in HTML.
        console.log(
            type + " HTML rows inserted:",
            $body.find("tr").length
        );

        // STEP 7: Show section.
        $section.show();

        // STEP 8: Initialize DataTable AFTER inserting all rows.
        initMismatchTable(tableSelector);

        // STEP 9: Verify actual DataTables records.
        var dt = $table.DataTable();

        console.log(
            type + " DataTables registered records:",
            dt.rows().count()
        );

        console.log(
            type + " DataTables total pages:",
            dt.page.info().pages
        );

        // Adjust columns when visible.
        if ($table.is(":visible")) {
            dt.columns.adjust().draw(false);
        }
    }
    function nullSafe(val) {
        return (val === null || val === undefined) ? "" : val;
    }
    function escapeMismatchHtml(value) {

        if (value === null || value === undefined) {
            return "";
        }

        return $("<div>").text(String(value)).html();
    }

    $(document).ready(function () {
        $("#tab1").show();
        $("#tab2").hide();
        $("#tab3").hide();

        initReconTable('#pfTable');
        initReconTable('#esicTable');
    });
    
</script>

</body>
</html>