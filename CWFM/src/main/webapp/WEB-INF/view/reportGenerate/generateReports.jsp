<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Report Generate</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/css/cmsstyles.css">
<script src="resources/js/cms/principalEmployer.js"></script>
<script src="resources/js/cms/contractor.js"></script>
<script src="resources/js/cms/workmen.js"></script>
<script src="resources/js/cms/report.js"></script>
<script src="resources/js/cms/generateReport.js"></script>

<style>
/* Add your styles here */
.success {
	color: green;
	font-weight: bold;
	padding: 10px;
	background-color: #e0ffe0;
	border: 1px solid green;
	margin-bottom: 1rem;
}

.error {
	color: red;
	font-weight: bold;
	padding: 10px;
	background-color: #ffe0e0;
	border: 1px solid red;
	margin-bottom: 1rem;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	padding: 10px;
	text-align: left;
	border: 1px solid #ddd;
}

th {
	background-color: #DDF3FF;
	color: #005151;
}

.checkbox-cell input[type="checkbox"] {
	margin: 0;
}

.action-bar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 1rem;
	background-color: #f8f8f8;
}

.action-buttons {
	display: flex;
	gap: 10px;
}

.action-buttons button {
	padding: 0.5rem 1rem;
	font-size: 1rem;
	cursor: pointer;
}

.success {
	color: green;
	font-weight: bold;
	padding: 10px;
	background-color: #e0ffe0;
	border: 1px solid green;
	margin-bottom: 1rem;
}

.error {
	color: red;
	font-weight: bold;
	padding: 10px;
	background-color: #ffe0e0;
	border: 1px solid red;
	margin-bottom: 1rem;
}

label {
	color: black;
}

body {
	background-color: #FFFFFF; /* White background for the page */
	font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
}

.action-bar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 1rem;
	background-color: #f8f8f8;
	margin-bottom: 1rem;
}

.action-buttons {
	display: flex;
	gap: 10px;
}

.action-buttons button {
	padding: 0.5rem 1rem;
	font-size: 1rem;
	cursor: pointer;
}

#searchForm {
	display: flex;
	align-items: center;
	flex-grow: 1;
	margin-right: 10px;
}

.search-box {
	width: 200px; /* Adjust width to fit layout */
	padding: 0.25rem; /* Reduced padding for height */
	font-size: 0.875rem; /* Smaller font size */
	border: 1px solid #ccc; /* Border to match design */
	border-radius: 4px; /* Slightly rounded corners */
	outline: none; /* Remove default outline */
	margin-right: 10px; /* Space between input and button */
	box-sizing: border-box;
	/* Include padding and border in element's total width and height */
}

/*  table {
        width: 100%;
        border-collapse: collapse;
    } */
th, td {
	padding: 10px;
	text-align: left;
	border: 1px solid #ddd;
	font-size: 0.875rem; /* Smaller text size matching the side nav bar */
	color: grey;
}

td {
	padding: 10px;
	text-align: left;
	border: 1px solid #ddd;
	font-size: 0.875rem; /* Smaller text size matching the side nav bar */
	font-family: 'Noto Sans', sans-serif;
	color: #898989; /* Label text color */
	padding: .2em .6em .3em;
	font-size: 85%;
	font-weight: 700;
	line-height: 1;
	white-space: nowrap;
	vertical-align: baseline;
	border-radius: .25em;
}

th {
	padding: 10px;
	text-align: left;
	border: 1px solid #ddd;
	font-size: 0.875rem; /* Smaller text size matching the side nav bar */
	font-weight: bold;
}

th {
	background-color: #DDF3FF; /* Light green for the table header */
	color: #005151; /* Text color from side nav bar */
	cursor: pointer;
	font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
	font-size: 0.75rem; /* Decreased font size for table header */
	line-height: 1.2rem; /* Adjust line-height for better fit */
	padding: 6px; /* Reduced padding for table header */
}

@media ( max-width : 768px) {
	.page-header {
		flex-direction: column; /* Stack items vertically on small screens */
		align-items: flex-start; /* Align items to the start */
	}
	#searchForm {
		width: 100%;
		margin-right: 0; /* Remove margin on small screens */
	}
	.search-box {
		width: 100%; /* Full width for small screens */
	}
	.page-header>div {
		width: 100%; /* Full width for small screens */
		margin-top: 10px; /* Add space above buttons */
		flex-direction: column; /* Stack buttons vertically */
	}
}

.header-text-new {
	font-family: 'Noto Sans', Arial, sans-serif;
	/* Font family similar to grid header */
	font-size: 14px;
	/* Adjusted font size to match typical grid header size */
	font-weight: 600; /* Bold text for prominence */
	border: 1px solid #ddd; /* Lighter border for a cleaner look */
	white-space: nowrap; /* Prevent text from wrapping */
	padding: 8px 10px; /* Adjusted padding for better spacing */
	background-color: #E0E0E0;
	/* Light background color to match grid header */
	color: #333; /* Text color for readability */
}

table th {
	border-top: 0.0625rem solid var(--zed_sys_color_border_lowEmphasis);
	/* Top border color */
	border-bottom: 1px solid var(--zed_sys_color_border_lowEmphasis);
	/* Bottom border color */
	border-right: none; /* No right border */
	background-color: #DDF3FF; /* Light green background color */
	color: var(--zed_sys_color_tableHeader_text); /* Text color */
	font-size: 0.75rem;
	line-height: 1.2rem; /* Reduced line height */
	letter-spacing: normal; /* Letter spacing */
	font-family: 'Noto Sans', sans-serif; /* Font family */
	font-weight: bold;
	text-align: center; /* Center align text */
	padding: 4px; /* Reduced padding for the table header */
	box-sizing: border-box;
	/* Include padding and border in element's total width and height */
}

.error-row {
	background-color: #ffcccc !important;
}

.error-message-row .error-message-cell {
	color: red;
	font-weight: bold;
	font-size: 13px;
	padding: 6px 10px;
	background-color: #ffe5e5;
	border-top: none;
}

.page-header {
	display: flex;
	align-items: center;
	gap: 10px;
	padding: 8px;
	width: 100%;
	box-sizing: border-box;
	background: #fff;
	border-bottom: 1px solid #ccc;
}

.page-header label {
	white-space: nowrap;
	color: darkcyan;
	font-weight: 600;
}

#principalEmployers {
	width: 200px;
}

#contractors {
	width: 180px;
}

#startDate, #endDate {
	width: 140px;
	min-width: 140px;
	color: gray;
}

.page-header-buttons {
	margin-left: auto;
	display: flex;
	gap: 10px;
	white-space: nowrap;
	flex-shrink: 0;
}

.report-search-grid {
	display: grid;
	grid-template-columns: 1fr 1fr;
	column-gap: 40px;
	row-gap: 15px;
	width: 100%;
}

.report-field {
	display: flex;
	align-items: center;
	min-width: 0;
}

.report-field label {
	min-width: 140px;
	margin-right: 10px;
	margin-bottom: 0;
	white-space: nowrap;
}

.report-field select, .report-field input {
	flex: 1;
	min-width: 0;
	box-sizing: border-box;
}

/* Allow the entire browser page to scroll */
html, body {
	min-height: 100%;
	height: auto;
	overflow-y: auto !important;
	overflow-x: auto;
}

/* Let the report history table expand naturally */
.table-container {
	width: 100%;
	max-width: 100%;
	height: auto;
	max-height: none;
	overflow: visible;
	margin-top: 25px;
}

/* DataTables wrapper must not restrict page height */
#reportHistoryTable_wrapper {
	width: 100%;
	height: auto;
	max-height: none;
	overflow: visible;
}

/* Do not constrain table rows */
#reportHistoryTable {
	width: 100% !important;
	height: auto;
	border-collapse: collapse;
}

/* Keep columns readable */
#reportHistoryTable th, #reportHistoryTable td {
	white-space: nowrap;
	padding: 8px 10px;
}

/* Keep pagination and search controls visible */
#reportHistoryTable_wrapper .dataTables_length,
	#reportHistoryTable_wrapper .dataTables_filter,
	#reportHistoryTable_wrapper .dataTables_info,
	#reportHistoryTable_wrapper .dataTables_paginate {
	display: block;
	visibility: visible;
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

@
keyframes spin { 0% {
	transform: rotate(0deg);
}

100
%
{
transform
:
rotate(
360deg
);
}
}

/* Department transfer control */
.report-field.department-field {
	grid-column: 1/-1;
	align-items: flex-start;
}

.department-field>label {
	min-width: 140px;
	padding-top: 10px;
}

.department-transfer {
	display: grid;
	grid-template-columns: minmax(180px, 1fr) 48px minmax(180px, 1fr);
	gap: 12px;
	align-items: center;
	width: 100%;
	min-width: 0;
}

.department-panel {
	min-width: 0;
	padding: 12px;
	background: #f8fbfc;
	border: 1px solid #d5e0e5;
	border-radius: 6px;
}

.department-panel label {
	display: block;
	margin-bottom: 8px;
	color: #005151;
	font-size: 13px;
	font-weight: 600;
}

.department-panel select {
	width: 100%;
	min-height: 210px;
	padding: 6px;
	color: #444;
	background: #fff;
	border: 1px solid #cbd5dc;
	border-radius: 4px;
	box-sizing: border-box;
}

.department-panel select option {
	padding: 7px;
	color: grey;
}

.department-panel select option:checked {
	background: #d9eeee linear-gradient(#d9eeee, #d9eeee);
	color: #005151;
}

.department-actions {
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 8px;
}

.department-actions button {
	width: 38px;
	height: 34px;
	color: #fff;
	background: #005151;
	border: 1px solid #005151;
	border-radius: 4px;
	cursor: pointer;
}

.department-actions button:hover {
	background: #007070;
}

.department-actions button:focus-visible {
	outline: 2px solid #007bff;
	outline-offset: 2px;
}

@media ( max-width : 768px) {
	.report-field.department-field {
		flex-direction: column;
	}
	.department-transfer {
		grid-template-columns: 1fr;
	}
	.department-actions {
		flex-direction: row;
		justify-content: center;
		flex-wrap: wrap;
	}
	.department-field>label {
		padding-top: 0;
		margin-bottom: 8px;
	}
}
</style>
</head>
<body>

	<%
	MasterUser user = (MasterUser) session.getAttribute("loginuser");
	String userId = user != null && user.getUserId() != null ? String.valueOf(user.getUserId()) : "";
	%>


	<div class="page-header">


		<div class="report-search-grid">
			<input type="hidden" id="loggedInUserAccount"
				value="${sessionScope.loginuser.userAccount}">
			<!-- Row 1 - Column 1 -->
			<%-- <div class="report-field">
            <label for="reportType" style="color: darkcyan;">Report Type:</label>
              <select class="custom-select" id="reportType" name="reportType" style="color: gray; padding: 3px;">
                <option value="">Select Report Type</option>
                <c:forEach var="option" items="${ReportType}">
                    <option value="${option.gmId}">${option.gmName}</option>
                </c:forEach>
            </select>
        </div> --%>
			<div class="report-field">
				<label for="reportType" style="color: darkcyan;"> Report
					Type: </label> <select class="custom-select" id="reportType"
					name="reportType" style="color: gray; padding: 3px;">
					<option value="">Select Report Type</option>
					<c:forEach var="option" items="${ReportTypes}">
						<option value="${option.reportId}"
							data-report-name="${option.reportType}">
							<c:out value="${option.reportType}" />
						</option>
					</c:forEach>
				</select>
			</div>
			<!-- Row 1 - Column 2 -->
			<input type="hidden" id="autoSearchFunction"> <input
				type="hidden" id="autoSearchParam" value="">
			<div class="report-field">
				<label for="principalEmployers" style="color: darkcyan;">Principal
					Employer:</label> <select class="custom-select" id="principalEmployersId"
					name="principalEmployerId"
					onchange="getContractorsAndTrades(this.value,document.getElementById('loggedInUserAccount').value)"
					style="color: gray; padding: 3px;">
					<option value="">Please select Principal Employer</option>
					<c:forEach var="pe" items="${PrincipalEmployer}">
						<option value="${pe.id}">${pe.description}</option>
					</c:forEach>
				</select>
			</div>
			<!-- Row 2 - Column 1 -->
			<div class="report-field">
				<label for="contractors" style="color: darkcyan;">Contractor:</label>
				<select class="custom-select" id="contractors" name="contractors"
					style="color: gray; padding: 3px;">
					<option value="">Please select Contractor</option>
					<c:forEach items="${Contractors}" var="c">
						<option value="${c.id}">${c.description}</option>
					</c:forEach>
				</select>
			</div>
			<!-- Row 2 - Column 2 -->
			<!-- <div class="report-field">
				<label for="department" style="color: darkcyan;">Department:</label>
				<select class="custom-select" id="department" name="department"
					style="color: gray; padding: 3px;">
					<option value="">Please select Department</option>
				</select>
			</div> -->
			<div class="report-field department-field">
				<label>Department:</label>

				<div class="department-transfer">

					<!-- Available Departments -->
					<div class="department-panel">
						<label for="availableDepartments"> Available Departments </label>

						<select id="availableDepartments" multiple="multiple" size="8"
							aria-label="Available Departments">
						</select>
					</div>

					<!-- Transfer Buttons -->
					<div class="department-actions">
						<button type="button" id="addDepartments"
							title="Add selected departments">
							<i class="fa fa-angle-right"></i>
						</button>

						<button type="button" id="addAllDepartments"
							title="Add all departments">
							<i class="fa fa-angle-double-right"></i>
						</button>

						<button type="button" id="removeDepartments"
							title="Remove selected departments">
							<i class="fa fa-angle-left"></i>
						</button>

						<button type="button" id="removeAllDepartments"
							title="Remove all departments">
							<i class="fa fa-angle-double-left"></i>
						</button>
					</div>

					<!-- Selected Departments -->
					<div class="department-panel">
						<label for="selectedDepartments"> Selected Departments </label> <select
							id="selectedDepartments" multiple="multiple" size="8"
							aria-label="Selected Departments">
						</select>
					</div>

				</div>

				<!-- Retain the original ID for existing department-loading code.
         This hidden select is the source of available department options. -->
				<select id="department" name="department" style="display: none;">
					<option value="">Please select Department</option>
				</select>
			</div>
			<!-- Row 3 - Column 1 -->
			<div class="report-field">
				<label for="fromDate" style="color: darkcyan;">From Date:</label> <input
					id="fromDate" name="fromDate"
					class="datetimepickerActiveWorkmenformat" type="text"
					autocomplete="off" style="color: gray;">
			</div>
			<!-- Row 3 - Column 2 -->
			<div class="report-field">
				<label for="toDate" style="color: darkcyan;">To Date:</label> <input
					id="toDate" name="toDate" class="datetimepickerActiveWorkmenformat"
					type="text" autocomplete="off" style="color: gray;">
			</div>
		</div>

		<!-- Generate Button -->
		<div style="margin-top: 15px; text-align: center;">
			<button type="button" id="exportBtn"
				class="btn btn-default process-footer-button-cancel ng-binding"
				onclick="getGatePassIds()">Generate</button>
		</div>
	</div>
	<div id="loaderOverlay" style="display: none;">
		<div class="loader"></div>
		<div class="loader-text">please wait...</div>
	</div>
	<!-- Report History Table -->
	<div class="table-container" style="margin-top: 25px;">

		<table id="reportHistoryTable">

			<thead>
				<tr>
					<th>S.No</th>
					<th>Report Type</th>
					<th>From Date</th>
					<th>To Date</th>
					<th>Report Status</th>
					<th>Download Report</th>
				</tr>
			</thead>

			<tbody id="reportHistoryBody">
			</tbody>

		</table>

	</div>
</body>
</html>
