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
            max-width: 520px;
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

        #messageDiv, #esicmessageDiv {
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
    
<tr>

<th><label class="custom-label"><span class="required-field">*</span><spring:message code="label.contractor"/></label></th>
    <td>
            <select id="contractor" name="contractorId" onchange="getReconciliationData()">
                <option value="">Please select Contractor</option>

                <c:forEach var="contr" items="${contList}">
                    <option value="${contr.id}"
                        ${contractorId eq contr.id ? 'selected="selected"' : ''}>
                        ${contr.description}
                    </option>
                </c:forEach>
            </select>
    </td>
</tr>
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

        <div id="pfMismatchSection" style="display:none;">
            <h5 class="text-danger mt-3">PF Unverified Data</h5>
            <div class="table-container">
                <table id="pfMismatchTable" class="display">
                    <thead>
                        <tr>
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

        <div id="esicMismatchSection" style="display:none;">
            <h5 class="text-danger mt-3">ESIC Unverified Data</h5>
            <div class="table-container">
                <table id="esicMismatchTable" class="display">
                    <thead>
                        <tr>
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
</div>

<script>
    var messageTimers = {};

    function showTab(tabId, btn) {
        $(".tab-content").removeClass("active").hide();
        $("#" + tabId).addClass("active").show();

        $(".tabs button").removeClass("active");
        $(btn).addClass("active");
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

    function initMismatchTable(tableId) {
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
                emptyTable: "No mismatch records found"
            }
        });
    }

    function showMessage(message, type, reconType) {
        var targetDiv = reconType === 'PF' ? "#messageDiv" : "#esicmessageDiv";
        var $div = $(targetDiv);

        if (messageTimers[targetDiv]) {
            clearTimeout(messageTimers[targetDiv]);
        }

        $div
            .stop(true, true)
            .removeClass()
            .addClass("alert alert-" + type)
            .html(message)
            .fadeIn();

        messageTimers[targetDiv] = setTimeout(function () {
            $div.fadeOut(500);
        }, 3000);
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
        var bodyId = type === 'PF' ? "#pfMismatchBody" : "#esicMismatchBody";
        var sectionId = type === 'PF' ? "#pfMismatchSection" : "#esicMismatchSection";
        var tableId = type === 'PF' ? "#pfMismatchTable" : "#esicMismatchTable";
        var html = "";

        if (list && list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                var m = list[i];
                html += "<tr>"
                    + "<td>" + nullSafe(m.gatePassId) + "</td>"
                    + "<td>" + nullSafe(m.workmenName) + "</td>"
                    + "<td>" + nullSafe(m.dbNumber) + "</td>"
                    + "<td>" + nullSafe(m.docNumber) + "</td>"
                    + "<td>" + nullSafe(m.dbAmount) + "</td>"
                    + "<td>" + nullSafe(m.docAmount) + "</td>"
                    + "<td>" + nullSafe(m.mismatchReason) + "</td>"
                    + "</tr>";
            }

            $(bodyId).html(html);
            $(sectionId).show();
            initMismatchTable(tableId);
        } else {
            $(bodyId).html("");
            $(sectionId).hide();
        }
    }

    function nullSafe(val) {
        return (val === null || val === undefined) ? "" : val;
    }

    $(document).ready(function () {
        $("#tab1").show();
        $("#tab2").hide();

        initReconTable('#pfTable');
        initReconTable('#esicTable');
    });
</script>

</body>
</html>