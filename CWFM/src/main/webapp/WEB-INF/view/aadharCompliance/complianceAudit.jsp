
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Compliance Audit Search</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        * {
            box-sizing: border-box;
        }

        html,
        body {
            width: 100%;
            min-height: 100%;
            margin: 0;
            padding: 0;
            background-color: #FFFFFF;
            font-family: 'Noto Sans', sans-serif;
        }

        /* Main page content */
        .compliance-container {
            display: block;
            width: 100%;
            max-width: none;
            margin: 0;
            padding: 10px 2px;
        }

        /* Search controls stay together on the left */
        .page-header {
            display: flex;
            flex-direction: row;
            justify-content: flex-start;
            align-items: center;
            flex-wrap: wrap;
            gap: 12px;
            width: 100%;
            margin: 0 0 15px 0;
            padding: 8px 0 14px 0;
            background-color: #FFFFFF;
            border-bottom: 1px solid #ccc;
        }

        .page-header label {
            flex: 0 0 auto;
            margin: 0;
            color: darkcyan;
            font-family: 'Noto Sans', sans-serif;
            font-size: 16px;
            font-weight: 600;
        }

        #aadhaar {
            flex: 0 1 260px;
            width: 260px;
            max-width: 100%;
            height: 25px;
            margin: 0;
            padding: 6px 10px;
            border: 1px solid #999;
            border-radius: 6px;
            background-color: #FFFFFF;
            color: #000000;
            font-family: 'Noto Sans', sans-serif;
            font-size: 15px;
        }

        /* Preserve existing button classes and theme */
        .page-header button {
            flex: 0 0 auto;
            margin: 0;
            white-space: nowrap;
        }

        /* Message area */
        #complianceAuditMessage {
            display: none;
            width: 100%;
            margin: 0 0 15px 0;
            padding: 10px;
            font-size: 14px;
            overflow-wrap: anywhere;
        }

        #complianceAuditMessage.compliance-success,
        #complianceAuditMessage.success {
            display: block;
            color: green;
            font-weight: bold;
            padding: 2px;
            /* background-color: #e0ffe0;
            border: 1px solid green; */
            margin-bottom: 0rem;
        }

        #complianceAuditMessage.compliance-error,
        #complianceAuditMessage.error {
            display: block;
            color: red;
            font-weight: bold;
            padding: 2px;
            /* background-color: #ffe0e0;
            border: 1px solid red; */
            margin-bottom: 0rem;
        }

        /* Table heading */
        #complianceAuditTableHeading {
            width: 100%;
            margin: 15px 0 12px 0;
            color: darkcyan;
            font-family: 'Noto Sans', sans-serif;
            font-size: 16px;
            font-weight: 400;
        }

        /* Full-width table wrapper */
        .table-responsive {
            display: block;
            width: 100%;
            max-width: none;
            margin: 0;
            padding: 0;
            overflow-x: auto;
        }

        /* Target this table only */
        #complianceAuditTable {
            width: 100% !important;
            min-width: 100%;
            margin: 0 0 25px 0;
            border-collapse: separate;
            border-spacing: 0;
            table-layout: auto;
            font-family: 'Noto Sans', sans-serif;
        }

        #complianceAuditTable thead th {
            background-color: #DDF3FF;
            color: #005151;
            font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
            font-size: 0.75rem;
            font-weight: bold;
            line-height: 1.2rem;
            text-align: center;
            padding: 8px 10px;
            border-top: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
            border-right: 1px solid #ddd;
            white-space: nowrap;
            vertical-align: middle;
        }

        #complianceAuditTable thead th:first-child {
            border-left: 1px solid #ddd;
            border-top-left-radius: 6px;
        }

        #complianceAuditTable thead th:last-child {
            border-top-right-radius: 6px;
        }

        #complianceAuditTable tbody td {
            padding: 10px;
            border: 1px solid #ddd;
            color: #898989;
            font-family: 'Noto Sans', sans-serif;
            font-size: 85%;
            font-weight: 700;
            line-height: 1.4;
            text-align: left;
            vertical-align: middle;
            white-space: nowrap;
        }

        #complianceAuditTable tbody tr:hover td {
            /* Keep the existing row colors unchanged */
        }

        #complianceAuditTable .no-records {
            width: 100%;
            padding: 15px;
            text-align: center;
            font-weight: 600;
            color: #666;
            white-space: normal;
        }

        /* Small screens */
        @media screen and (max-width: 768px) {
            .compliance-container {
                padding: 12px;
            }

            .page-header {
                justify-content: flex-start;
                gap: 10px;
            }

            #aadhaar {
                flex: 1 1 220px;
                width: 100%;
            }

            .table-responsive {
                overflow-x: auto;
            }

            #complianceAuditTable {
                min-width: 900px;
            }
        }
    </style>
</head>

<body>

    <div class="compliance-container">

        <!-- Search section -->
        <div class="page-header">

            <label for="aadhaar">
                Aadhaar Number:
            </label>

            <input type="text"
                   id="aadhaar"
                   name="aadhaar"
                   class="search-box"
                   placeholder="Enter 12-digit Aadhaar Number"
                   inputmode="numeric"
                   pattern="[0-9]{12}"
                   maxlength="12"
                   autocomplete="off"
                   oninput="handleAadharInput()">

            <button type="button"
                    id="searchComplianceAudit"
                    class="btn btn-default process-footer-button-cancel ng-binding"
                    onclick="searchComplianceAudit()">
                Search
            </button>

            <button type="button"
                    id="clearComplianceAudit"
                    class="btn btn-default process-footer-button-cancel ng-binding"
                    onclick="clearComplianceAudit()">
                Clear
            </button>

        </div>

        <!-- Search status -->
        <div id="complianceAuditMessage"
             class="compliance-message"
             role="status"
             aria-live="polite">
        </div>

        <!-- Results heading -->
        <h5 id="complianceAuditTableHeading">
            Compliance Audit Details
        </h5>

        <!-- Full-width results table -->
        <div class="table-responsive">

            <table id="complianceAuditTable" class="no-dt">

                <thead>
                    <tr>
                        <th>Gate Pass ID</th>
                        <th>Action Type</th>
                        <th>Disability</th>
                        <th>UAN Number</th>
                        <th>PF Number</th>
                        <th>Bank Account</th>
                        <th>IFSC</th>
                        <th>From Date</th>
                        <th>To Date</th>
                    </tr>
                </thead>

                <tbody id="complianceAuditTableBody">
                    <tr>
                        <td colspan="9" class="no-records">
                          No data available in table.
                        </td> 
                    </tr>
                </tbody>

            </table>

        </div>

    </div>

</body>
</html>
