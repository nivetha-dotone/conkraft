<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Requestor List</title>
    <!-- <script src="resources/js/jquery.min.js"></script> -->
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> --> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/workmen.js"></script>
    <script src="resources/js/commonjs.js"></script>
    <!-- <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> -->
        <script src="resources/js/cms/requestorList.js"></script>
    <!-- DataTables CSS and JS -->
    <!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css"> -->
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>
    
<style>
    body {
        background-color: #FFFFFF;
        font-family: 'Noto Sans', sans-serif;
    }

    .controls-container {
        display: flex;
        justify-content:space-between;
        align-items: center;
        padding: 9px;
        background-color: #f8f8f8;
        flex-wrap: wrap;
        gap: 10px;
        margin-left: 95%;
        margin-top: 2%;
    }

    .entries-control {
        display: flex;
        align-items: center;
        gap: 5px;
    }

    .entries-control select {
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    .search-control {
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .search-box {
        width: 250px;
        padding: 8px;
        font-size: 14px;
        border: 1px solid #ccc;
        border-radius: 4px;
        outline: none;
    }

    .action-buttons {
        display: flex;
        gap: 10px;
    }

    .btn {
        padding: 8px 16px;
        font-size: 14px;
        cursor: pointer;
        border: 1px solid #ccc;
        border-radius: 4px;
        background-color: #fff;
    }

    .btn:hover {
        background-color: #f0f0f0;
    }

    .btn-primary {
    color: #0176b2;
    padding: 0.100rem 0.75rem;
    font-size: 0.75rem;
    margin-bottom: 0;
    border: 1px solid transparent;
    border-color: #0176b2;
    line-height: 1.66666667;
    border-radius: 18px;
    }

    .btn-primary:hover {
        background-color: #0176b2;
    color: #ffffff;

    }

    /* Updated table-container to be a scrollable inner box with fixed height */
    .table-container {
        overflow-x: auto;
        overflow-y: auto;
        margin: 0px 10px;
        padding: 0;
        border: 1px solid #ddd;
        border-radius: 4px;
        background-color: #fff;
        /* height: calc(100vh - 350px);
        min-height: 400px; */
        height: 93%;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 13px;
    }

    /* Enhanced td styling for dynamic row heights and text wrapping */
    /* td {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem;
        font-family: 'Noto Sans', sans-serif;
        color: #333;
        vertical-align: middle;
        word-wrap: break-word;
        overflow-wrap: break-word;
        word-break: break-word;
        max-width: 200px;
        white-space: normal;
        line-height: 1.5;
    } */
  th {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
          font-weight: bold;
   
        background-color: #DDF3FF; /* Light green for the table header */
        color: #005151; /* Text color from side nav bar */
        cursor: pointer;
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        font-size: 0.75rem; /* Decreased font size for table header */
        line-height: 1.2rem; /* Adjust line-height for better fit */
        padding: 6px; /* Reduced padding for table header */
    }
    td {
    /* padding: 10px; */
    /* text-align: left;
    border: 1px solid #ddd; */
    /* font-size: 0.875rem; */
    /* font-family: 'Noto Sans', sans-serif;
    color: #333;
    vertical-align: middle;
    word-wrap: break-word;
    overflow-wrap: break-word;
    word-break: break-word;
    max-width: 200px; */
    /* white-space: normal; */
    /* line-height: 1.5; */
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

    /* Added responsive max-width for different screen sizes */
    @media (max-width: 1200px) {
        td {
            max-width: 150px;
        }
    }

    @media (max-width: 768px) {
        td {
            max-width: 120px;
            padding: 8px;
            font-size: 0.75rem;
        }
    }

    th 
    {
        padding: 10px;
        text-align: left;
        /* border: 1px solid #ddd; */
        font-size: 0.875rem;
        font-weight: bold;
        background-color: #DDF3FF;
        color: #005151;
        cursor: pointer;
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        text-align: center;
        box-sizing: border-box;
    }
    /* {
    padding: 10px;
    text-align: left;   
    border: 1px solid #ddd;
    font-size: 0.875rem;
    font-weight: bold;
    /* background-color: #DDF3FF; */
    /* color: #005151; */
    /* cursor: pointer; */
    /* font-family: 'Volte Rounded', 'Noto Sans', sans-serif; */
    /* text-align: center; */
    /* box-sizing: border-box; */
/* } */

    .status-submit {
        background-color: #28a745;
        color: #fff;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 8px;
        font-weight: bold;
    }

    .status-approved {
        background-color: #007bff;
        color: #fff;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 7px;
        font-weight: bold;
    }
    .status-rejected {
        background-color: #dc3545;
        color: #fff;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 7px;
        font-weight: bold;
    }

    .open-btn {
        background-color: #17a2b8;
        color: white;
        padding: 4px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 0.8em;
        text-decoration: none;
        display: inline-block;
    }

    .open-btn:hover {
        background-color: #138496;
        color: white;
        text-decoration: none;
    }

    .pagination-container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 1rem;
        background-color: #f8f8f8;
        margin-top: 1rem;
    }

    .pagination-info {
        font-size: 14px;
        color: #666;
    }

    .pagination {
        display: flex;
        gap: 5px;
        align-items: center;
    }

    .pagination button {
        padding: 8px 12px;
        border: 1px solid #ccc;
        background-color: white;
        cursor: pointer;
        border-radius: 4px;
    }

    .pagination button:hover:not(:disabled) {
        background-color: #f0f0f0;
    }

    .pagination button.active {
       background-color: #eef0f3;
    color: #333232;
    border-color: #131414;
    }

    .pagination button:disabled {
        opacity: 0.5;
        cursor: not-allowed;
    }

    /* Modal styles */
    .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0,0,0,0.4);
    }

    .modal-content {
        background-color: #fefefe;
        margin: 5% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 90%;
        max-width: 1000px;
        border-radius: 8px;
        max-height: 80vh;
        overflow-y: auto;
    }

    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
        cursor: pointer;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
    }

    .modal-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
    }

    .modal-table th,
    .modal-table td {
        border: 1px solid #ddd;
        padding: 12px;
        text-align: left;
    }

    .modal-table th {
        background-color: #f2f2f2;
        font-weight: bold;
        width: 30%;
    }

    .hidden {
        display: none;
    }

    @media (max-width: 768px) {
        .controls-container {
            flex-direction: column;
            align-items: stretch;
                    margin-left: 83%;
        }

        .search-box {
            width: 100%;
        }

        .pagination-container {
            flex-direction: column;
            gap: 10px;
        }

        /* Adjust table container height for mobile devices */
        .table-container {
            height: calc(100vh - 300px);
            min-height: 300px;
        }
    }
</style>
</head>
<body>

<div class="controls-container">

    
    <!-- <div class="search-control">
        <input type="text" class="search-box" id="searchInput" placeholder="Search by Transaction ID, Name, Aadhar, etc..." onkeyup="searchTable()">
    </div> -->
    
    <div class="action-buttons">
        <button type="button" class="btn btn-primary" onclick="viewSelectedRequestor()">View</button>
    </div>
</div>

<div class="table-container">
    <table id="requestorTBL" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
                <th style="width: 40px;">
                    <input type="checkbox" id="selectAllRequestorCheckbox" onchange="toggleSelectAllRequestors()">
                </th>
                <th class="header-text">Transaction ID</th>
                <th class="header-text">Candidate Name</th>
                <th class="header-text">Aadhar Number</th>
                <th class="header-text">Unit Name</th>
                <th class="header-text">Contractor Name</th>
                <th class="header-text">Department</th>
                <th class="header-text">Academic</th>
                <th class="header-text">Additional Qualification</th>
                <th class="header-text">CV Attachment</th>
                <th class="header-text">Requested By</th>
                <th class="header-text">Requested Date</th>
                <th class="header-text">Approved By</th>
                <th class="header-text">Approver Remark</th>
                <th class="header-text">Status</th>
            </tr>
        </thead>
        <tbody id="requestorTableBody">
            <c:if test="${requestorList.size() > 0}">
                <c:forEach items="${requestorList}" var="wo" varStatus="status">
                    <tr class="data-row" data-index="${status.index}">
                        <td>
                            <input type="checkbox" name="selectedWOs" value="${wo.transactionId}"
                                   data-record='{
                                       "transactionId": "${wo.transactionId}",
                                       "unitID": "${wo.unitID}",
                                       "unitName": "${fn:escapeXml(wo.unitName)}",
                                       "contractorId": "${wo.contractorId}",
                                       "contractName": "${fn:escapeXml(wo.contractName)}",
                                       "name": "${fn:escapeXml(wo.name)}",
                                       "aadharNumber": "${wo.aadharNumber}",
                                       "forPostId": "${wo.forPostId}",
                                       "academicId": "${wo.academicId}",
                                       "academicString": "${fn:escapeXml(wo.academicString)}",
                                       "additionalQualification": "${fn:escapeXml(wo.additionalQualification)}",
                                       "attachmentCv": "${wo.attachmentCv}",
                                       "fileName": "${fn:escapeXml(wo.fileName)}",
                                       "fileType": "${wo.fileType}",
                                       "shortNote": "${fn:escapeXml(wo.shortNote)}",
                                       "status": ${empty wo.status ? 'null' : wo.status},
                                       "updatedDate": "${wo.updatedDate}",
                                       "updatedBy": "${wo.updatedBy}",
                                       "approveBy": "${fn:escapeXml(wo.approveBy)}",
                                       "remarkApprover": "${fn:escapeXml(wo.remarkApprover)}",
                                       "requesterAttachmentDTOList": [
                                           <c:forEach items="${wo.requesterAttachmentDTOList}" var="attachment" varStatus="attStatus">
                                               {
                                                   "attachmentId": "${attachment.attachmentId}",
                                                   "transactionId": "${attachment.transactionId}",
                                                   "fileName": "${fn:escapeXml(attachment.fileName)}",
                                                   "filePath": "${attachment.filePath}",
                                                   "fileType": "${attachment.fileType}",
                                                   "uploadedDate": "${attachment.uploadedDate}",
                                                   "uploadedBy": "${attachment.uploadedBy}"
                                               }<c:if test="${!attStatus.last}">,</c:if>
                                           </c:forEach>
                                       ]
                                   }'>
                        </td>
                        <td>${wo.transactionId}</td>                       
                        <td>${wo.name}</td>
                        <td>${wo.aadharNumber}</td>
                        <td>${wo.unitName}</td>
                        <td>${wo.contractName}</td>
                        <td>${wo.forPostId}</td>
                        <td>${wo.academicString}</td>
                        <td>${wo.additionalQualification}</td>
                        <td>
                            <c:if test="${not empty wo.attachmentCv}">
                                <a href="${wo.attachmentCv}" target="_blank" class="open-btn">OPEN</a>
                            </c:if>
                        </td>
                        <td>${wo.updatedBy}</td>
                        <td class="date-cell">${wo.updatedDate}</td>
                        <td>${wo.approveBy}</td>
                        <td>${wo.remarkApprover}</td>
                        <td class="status-cell" data-status="${wo.status}">
                            <c:choose>
                                <c:when test="${empty wo.status}">
                                    <script>console.log("status is ${wo.status}")</script> 
                                    <span class="status-submit">Submit</span>
                                </c:when>
                                <c:when test="${wo.status == true}">
                                    <span class="status-approved">Approved</span>
                                </c:when>
                                <c:when test="${wo.status == false}">
                                    <span class="status-rejected">Rejected</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-submit">Submit</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
        </tbody>
    </table>
</div>


<!-- Modal for viewing selected record -->
<div id="viewModal" class="modal">
    <div class="modal-content">
        <h2>Requestor Details</h2>
        <table class="modal-table" id="modalTable">
            <tbody id="modalTableBody">
                <!-- Modal content will be populated here -->
            </tbody>
        </table>
    </div>
</div>



</body>
</html>
