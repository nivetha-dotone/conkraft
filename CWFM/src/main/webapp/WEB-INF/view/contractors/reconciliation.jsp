<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workmen PF/ESIC Reconciliation</title>
    <script src="resources/js/cms/workmen.js"></script>


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

    body {
        background-color: #FFFFFF; /* White background for the page */
         font-family: 'Noto Sans', sans-serif;
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
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }

    .table-container {
        overflow-x: auto;
        margin: 0; /* Remove space before the table */
        padding: 0; /* Remove padding if any */
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    td {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
         font-family: 'Noto Sans', sans-serif;
         
    color: #898989;/* Label text color */
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

    .page-header {
        display: flex;
        align-items: center;
        justify-content: space-between; /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
    }

    .page-header > div {
        display: flex;
        gap: 10px; /* Space between buttons */
    }

    @media (max-width: 768px) {
        .page-header {
            flex-direction: column; /* Stack items vertically on small screens */
            align-items: flex-start; /* Align items to the start */
        }

        #searchForm {
            width: 100%; /* Full width for small screens */
            margin-right: 0; /* Remove margin on small screens */
        }

        .search-box {
            width: 100%; /* Full width for small screens */
        }

        .page-header > div {
            width: 100%; /* Full width for small screens */
            margin-top: 10px; /* Add space above buttons */
            flex-direction: column; /* Stack buttons vertically */
        }
    }
    .header-text-new {
        font-family: 'Noto Sans', Arial, sans-serif; /* Font family similar to grid header */
        font-size: 14px; /* Adjusted font size to match typical grid header size */
        font-weight: 600; /* Bold text for prominence */
        border: 1px solid #ddd; /* Lighter border for a cleaner look */
        white-space: nowrap; /* Prevent text from wrapping */
        padding: 8px 10px; /* Adjusted padding for better spacing */
          background-color: #E0E0E0;  /* Light background color to match grid header */
        color: #333; /* Text color for readability */
          font-weight: bold;
    }
       table th {
        border-top: 0.0625rem solid var(--zed_sys_color_border_lowEmphasis); /* Top border color */
        border-bottom: 1px solid var(--zed_sys_color_border_lowEmphasis); /* Bottom border color */
        border-right: none; /* No right border */
        background-color: #DDF3FF; /* Light green background color */
        color: var(--zed_sys_color_tableHeader_text); /* Text color */
        font-size: 0.75rem; /* Smaller font size */
        line-height: 1.2rem; /* Reduced line height */
        letter-spacing: normal; /* Letter spacing */
        font-family: 'Noto Sans', sans-serif; /* Font family */
         font-weight: bold;
        text-align: center; /* Center align text */
        padding: 4px; /* Reduced padding for the table header */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
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
</style>
<%
    	MasterUser user = (MasterUser) session.getAttribute("loginuser");
     String userId = user != null && user.getUserId() != null ? String.valueOf(user.getUserId()) : "";
        String roleName = user != null ? user.getRoleName() : "";
        String roleId = user!=null?user.getRoleId():"";
		%>
</head>
<body>
<div class="page-header">

       
<div >
        
        <div >


            <div id="messageDiv" style="display:none;"></div>

            <div class="row mb-3">
                <div class="col-md-3">
                    <label><b>Reconciliation Type</b></label>
                    <select id="reconType" class="form-control">
                        <option value="PF">PF</option>
                        <option value="ESIC">ESIC</option>
                    </select>
                </div>

                <div class="col-md-5">
                    <label><b>Upload Challan</b></label>
                    <input type="file" id="challanFile" class="form-control" accept=".pdf,.xls,.xlsx,.csv"/>
                </div>

                <div class="col-md-2 mt-4">
                    <button type="button" class="btn btn-success" onclick="uploadChallan()">Upload & Verify</button>
                </div>

                <div class="col-md-2 mt-4">
                    <button type="button" class="btn btn-secondary" onclick="reloadWorkmenList()">Refresh List</button>
                </div>
            </div>

            <!-- Summary -->
            <div class="row mb-3" id="summaryBlock" style="display:none;">
                <div class="col-md-3">
                    <div class="alert alert-info mb-2">
                        <b>Status:</b> <span id="overallStatus"></span>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="alert alert-secondary mb-2">
                        <b>Total Count:</b> <span id="totalCount"></span>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="alert alert-success mb-2">
                        <b>Verified Count:</b> <span id="verifiedCount"></span>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="alert alert-danger mb-2">
                        <b>Unverified Count:</b> <span id="unverifiedCount"></span>
                    </div>
                </div>
            </div>

            <!-- Workmen List -->
            <div class="table-responsive">
                <table class="table table-bordered table-striped table-hover" id="workmenTable">
                    <thead class="thead-dark">
                        <tr>
                            <th>GatePass ID</th>
                            <th>Workmen Name</th>
                            <th>PF Number</th>
                            <th>ESIC Number</th>
                            <th>PF Price</th>
                            <th>ESIC Price</th>
                        </tr>
                    </thead>
                    <tbody id="workmenTableBody">
                        <c:forEach var="w" items="${workmenList}">
                            <tr>
                                <td>${w.gatePassId}</td>
                                <td>${w.workmenName}</td>
                                <td>${w.pfNumber}</td>
                                <td>${w.esicNumber}</td>
                                <td>${w.pfPrice}</td>
                                <td>${w.esicPrice}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Mismatch Section -->
            <div id="mismatchSection" style="display:none;" class="mt-4">
                <h5 class="text-danger" style="font-size: 15px;font-weight: bold;">Unverified Data List</h5>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped" id="mismatchTable">
                        <thead class="thead-light">
                            <tr>
                                <th>GatePass ID</th>
                                <th>Workmen Name</th>
                                <th>DB Number</th>
                                <th>Document Number</th>
                                <th>DB Amount</th>
                                <th>Document Amount</th>
                                <th>Mismatch Reason</th>
                                <th>Type</th>
                            </tr>
                        </thead>
                        <tbody id="mismatchTbody"></tbody>
                    </table>
                </div>
            </div>

</div></div></div>
</body></html>
