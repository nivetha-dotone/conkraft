<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
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
                    <script src="resources/js/cms/requestorHRList.js"></script>

                    <!-- DataTables CSS and JS -->
                    <!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css"> -->
                    <script type="text/javascript" charset="utf8"
                        src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>

                    <style>
                        body {
                            background-color: #FFFFFF;
                            font-family: 'Noto Sans', sans-serif;
                        }

                        .controls-container {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            padding: 9px;
                            background-color: #f8f8f8;
                            flex-wrap: wrap;
                            gap: 10px;
                            margin-left: 92%;
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

                       
td{
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

                        th {
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
                         .hr-btn-view {
                            background-color: #008b8b;
                            color: white;
                            padding: 4px 12px;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                            font-size: 0.8em;
                            text-decoration: none;
                            display: inline-block;
                        }

                        .hr-btn-view:hover {
                            background-color: rgb(0, 81, 81);
                            color: white;
                            text-decoration: none;
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
                            background-color: rgba(0, 0, 0, 0.4);
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

                        .hr-modal {
                            display: none;
                            position: fixed;
                            z-index: 1000;
                            left: 0;
                            top: 0;
                            width: 100%;
                            height: 100%;
                            overflow: auto;
                            background-color: rgba(0, 0, 0, 0.4);
                        }

                        .hr-modal-content {
                            background-color: #fefefe;
                            margin: 2% auto;
                            padding: 20px;
                            border: 1px solid #888;
                            width: 90%;
                            max-width: 1000px;
                            border-radius: 8px;
                            max-height: 90vh;
                            overflow-y: auto;
                            transition: opacity 0.2s ease;
                        }

                        .hr-modal-content.loading {
                            opacity: 0.6;
                            pointer-events: none;
                        }

                        .hr-close {
                            color: #aaa;
                            float: right;
                            font-size: 28px;
                            font-weight: bold;
                            cursor: pointer;
                        }

                        .hr-close:hover,
                        .hr-close:focus {
                            color: black;
                            text-decoration: none;
                        }

                        .hr-modal-table {
                            width: 100%;
                            border-collapse: collapse;
                            margin-top: 10px;
                        }

                        .hr-modal-table th,
                        .hr-modal-table td {
                            border: 1px solid #ddd;
                            padding: 12px;
                            text-align: left;
                        }

                        .hr-modal-table th {
                            background-color: #f2f2f2;
                            font-weight: bold;
                            width: 30%;
                        }

                        .hr-update-section {
                            margin-top: 20px;
                            padding: 20px;
                            background-color: #f8f9fa;
                            border-radius: 8px;
                            border: 1px solid #dee2e6;
                        }

                        .hr-update-section h3 {
                            margin-top: 0;
                            color: #495057;
                        }

                        .hr-form-group {
                            margin-bottom: 15px;
                        }

                        .hr-form-group label {
                            display: block;
                            margin-bottom: 5px;
                            font-weight: bold;
                            color: #495057;
                        }

                        .hr-form-control {
                            width: 100%;
                            padding: 8px 12px;
                            font-size: 14px;
                            border: 1px solid #ced4da;
                            border-radius: 4px;
                            box-sizing: border-box;
                        }

                        .hr-form-control:focus {
                            outline: none;
                            border-color: #80bdff;
                            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, .25);
                        }

                        textarea.hr-form-control {
                            resize: vertical;
                            min-height: 80px;
                        }

                        .hr-update-buttons {
                            display: flex;
                            gap: 10px;
                            justify-content: flex-end;
                            margin-top: 20px;
                        }

                        .hr-hidden {
                            display: none;
                        }

                        .hr-error-message {
                            color: #dc3545;
                            background-color: #f8d7da;
                            border: 1px solid #f5c6cb;
                            padding: 10px;
                            border-radius: 4px;
                            margin: 10px 0;
                        }

                        .hr-status-submit,
                        .hr-status-approved,
                        .hr-status-rejected {
                            display: inline-block;
                            padding: 4px 8px;
                            border-radius: 4px;
                            font-size: 0.8em;
                            font-weight: bold;
                            color: #fff;
                        }

                        .hr-status-submit {
                            background-color: #28a745;
                        }

                        .hr-status-approved {
                            background-color: #007bff;
                        }

                        .hr-status-rejected {
                            background-color: #dc3545;
                        }

                        .hr-open-btn {
                            background-color: #008b8b;
                            color: white;
                            padding: 4px 12px;
                            border: none;
                            border-radius: 4px;
                            cursor: pointer;
                            font-size: 0.8em;
                            text-decoration: none;
                            display: inline-block;
                        }

                        .hr-open-btn:hover {
                            background-color: #138496;
                            color: white;
                            text-decoration: none;
                        }


                        @media (max-width: 768px) {
                            .hr-controls-container {
                                flex-direction: column;
                                align-items: stretch;
                            }

                            .hr-search-box {
                                width: 100%;
                            }

                            .hr-pagination-container {
                                flex-direction: column;
                                gap: 10px;
                            }

                            .hr-modal-content {
                                width: 95%;
                                margin: 18% auto;
                            }

                            .hr-update-buttons {
                                flex-direction: column;
                            }
                        }
             

                        @media (max-width: 768px) {
                            .controls-container {
                                flex-direction: column;
                                align-items: stretch;
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


                        .btn {
  display: inline-block;
  margin-bottom: 0;
  font-weight: 400;
  text-align: center;
  vertical-align: middle;
  touch-action: manipulation;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  white-space: nowrap;
  padding: 0.125rem 0.75rem;
  font-size: 0.75rem;
  line-height: 1.66666667;
  border-radius: 0;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.btn-default {
  color: var(--zed_sys_color_action_enabled);
  border-color: var(--zed_sys_color_action_enabled);
  background-color: var(--zed_sys_color_background);
}

.btn:hover,
.btn:focus {
  color: var(--zed_sys_color_action_hover);
  border-color: var(--zed_sys_color_focus);
}
                    </style>
                </head>

                <body>

                    <div class="controls-container">


                        <!-- <div class="search-control">
        <input type="text" class="search-box" id="searchInput" placeholder="Search by Transaction ID, Name, Aadhar, etc..." onkeyup="searchTable()">
    </div> -->

                        <!-- <div class="action-buttons">
        <button type="button" class="btn btn-primary" onclick="viewSelectedRequestor()">View</button>
    </div> -->
                    </div>

                    <div class="table-container">
                        <table id="hrRequestorTBL" cellspacing="0" cellpadding="0">
                            <thead>
                                <tr>
                                    <th style="width: 40px;">
                                        <input type="checkbox" id="hrSelectAllCheckbox" onchange="hrToggleSelectAll()">
                                    </th>
                                    <th class="header-text">Transaction ID</th>
                                    <th class="header-text">Name</th>
                                    <th class="header-text">Aadhar Number</th>
                                    <th class="header-text">Unit Name</th>
                                    <th class="header-text">Contract Name</th>
                                    <th class="header-text">Department</th>
                                    <th class="header-text">Academic</th>
                                    <th class="header-text">Additional Qualification</th>
                                    <th class="header-text">CV Attachment</th>
                                    <th class="header-text">Requested By</th>
                                    <th class="header-text">Requested Date</th>
                                    <th class="header-text">Approved By</th>
                                    <th class="header-text hr-remark-header">Approver Remark</th>
                                    <th class="header-text">Status</th>
                                    <th class="header-text">Action</th>
                                </tr>
                            </thead>
                            <tbody id="hrRequestorTableBody">
                                <c:if test="${requestorListHR.size() > 0}">
                                    <c:forEach items="${requestorListHR}" var="wo" varStatus="status">
                                        <%-- Fixed JSON construction to properly handle null/empty
                                            requesterAttachmentDTOList --%>
                                            <c:set var="attachmentsJson" value="[]" />
                                            <c:if test="${not empty wo.requesterAttachmentDTOList}">
                                                <c:set var="attachmentsJsonBuilder" value="" />
                                                <c:forEach items="${wo.requesterAttachmentDTOList}" var="attachment"
                                                    varStatus="attStatus">
                                                    <c:set var="attachmentJson">
                                                        {
                                                        "attachmentId": "${fn:escapeXml(attachment.attachmentId)}",
                                                        "transactionId": "${fn:escapeXml(attachment.transactionId)}",
                                                        "fileName": "${fn:escapeXml(attachment.fileName)}",
                                                        "filePath": "${fn:escapeXml(attachment.filePath)}",
                                                        "fileType": "${fn:escapeXml(attachment.fileType)}",
                                                        "uploadedDate": "${fn:escapeXml(attachment.uploadedDate)}",
                                                        "uploadedBy": "${fn:escapeXml(attachment.uploadedBy)}"
                                                        }
                                                    </c:set>
                                                    <c:choose>
                                                        <c:when test="${attStatus.first}">
                                                            <c:set var="attachmentsJsonBuilder"
                                                                value="${attachmentJson}" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="attachmentsJsonBuilder"
                                                                value="${attachmentsJsonBuilder},${attachmentJson}" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                                <c:set var="attachmentsJson" value="[${attachmentsJsonBuilder}]" />
                                            </c:if>

                                            <tr class="hr-data-row" data-index="${status.index}"
                                                data-transaction-id="${wo.transactionId}" data-full-data='{
                        "transactionId": "${fn:escapeXml(wo.transactionId)}",
                        "unitID": ${wo.unitID},
                        "unitName": "${fn:escapeXml(wo.unitName)}",
                        "contractorId": "${wo.contractorId}",
                        "contractName": "${fn:escapeXml(wo.contractName)}",
                        "name": "${fn:escapeXml(wo.name)}",
                        "aadharNumber": "${fn:escapeXml(wo.aadharNumber)}",
                        "forPostId": "${wo.forPostId}",
                        "academicId": "${wo.academicId}",
                        "academicString": "${fn:escapeXml(wo.academicString)}",
                        "additionalQualification": "${fn:escapeXml(wo.additionalQualification)}",
                        "attachmentCv": "${fn:escapeXml(wo.attachmentCv)}",
                        "fileName": "${fn:escapeXml(wo.fileName)}",
                        "fileType": "${fn:escapeXml(wo.fileType)}",
                        "shortNote": "${fn:escapeXml(wo.shortNote)}",
                        "status": ${wo.status == null ? ' null' : wo.status}, "updatedDate"
                                                : "${fn:escapeXml(wo.updatedDate)}" , "updatedBy"
                                                : "${fn:escapeXml(wo.updatedBy)}" , "approveBy"
                                                : "${fn:escapeXml(wo.approveBy)}" , "remarkApprover"
                                                : "${fn:escapeXml(wo.remarkApprover)}" , "requesterAttachmentDTOList" :
                                                ${attachmentsJson} }'>
                                                <td>
                                                    <input type="checkbox" name="hrSelectedWOs"
                                                        value="${wo.transactionId}" data-record="${wo.transactionId}" />
                                                </td>
                                                <td class="hr-transaction-cell">${wo.transactionId}</td>
                                                <td>${wo.name}</td>
                                                <td>${wo.aadharNumber}</td>
                                                <td>${wo.unitName}</td>
                                                <td>${wo.contractName}</td>
                                                <td>${wo.forPostId}</td>
                                                <td>${wo.academicString}</td>
                                                <td>${wo.additionalQualification}</td>
                                                <td>
                                                    <c:if test="${not empty wo.attachmentCv}">
                                                        <a href="${wo.attachmentCv}" target="_blank"
                                                            class="hr-open-btn">OPEN</a>
                                                    </c:if>
                                                </td>
                                                <td class="hr-requested-by-cell">${wo.updatedBy}</td>
                                                <td class="hr-date-cell">${wo.updatedDate}</td>
                                                <td class="hr-approved-by-cell">${wo.approveBy}</td>
                                                <td class="hr-remark-cell">${wo.remarkApprover}</td>
                                                <td class="hr-status-cell"
                                                    data-status="${wo.status == null ? '' : (wo.status ? 'true' : 'false')}">
                                                    <c:choose>
                                                        <c:when test="${wo.status == null}">
                                                            <span class="hr-status-submit">Submit</span>
                                                        </c:when>
                                                        <c:when test="${wo.status == true}">
                                                            <span class="hr-status-approved">Approved</span>
                                                        </c:when>
                                                        <c:when test="${wo.status == false}">
                                                            <span class="hr-status-rejected">Rejected</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="hr-status-submit">Submit</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <button type="button" class="hr-btn-view"
                                                        onclick="hrViewRequestorDetails(this)">Update</button>
                                                </td>
                                            </tr>
                                    </c:forEach>
                                </c:if>
                            </tbody>
                        </table>
                    </div>


                    <div id="hrViewModal" class="hr-modal">
                        <div class="hr-modal-content" id="hrModalContent">
                            <span class="hr-close" onclick="hrCloseModal()">&times;</span>
                            <h2>Requestor Details</h2>
                            <div id="hrErrorContainer"></div>
                            <table class="hr-modal-table">
                                <tbody id="hrModalTableBody"></tbody>
                            </table>
                            <div class="hr-update-section">
                                <h3>Update Request Status</h3>
                                <div class="hr-form-group">
                                    <label for="hrStatusSelect">Status:</label>
                                    <select id="hrStatusSelect" class="hr-form-control">
                                        <option value="">Select Status</option>
                                        <option value="true">Approved</option>
                                        <option value="false">Rejected</option>
                                    </select>
                                </div>
                                <div class="hr-form-group">
                                    <label>HR Attachments:</label>
                                    <div id="hrAttachmentsList"></div>
                                </div>
                                <div class="hr-form-group">
                                    <label for="hrNewAttachments">Upload New Attachments (Optional):</label>
                                    <input type="file" id="hrNewAttachments" class="hr-form-control" multiple>
                                </div>
                                <div class="hr-form-group">
                                    <label for="hrRemarkText">Remark:</label>
                                    <textarea id="hrRemarkText" class="hr-form-control"
                                        placeholder="Enter your remark here..." rows="4"></textarea>
                                </div>
                                <div class="hr-update-buttons">
                                    <button type="button" class=".btn btn-default"
                                        onclick="hrUpdateRequest()">Update Request</button>
                                    <button type="button" class=".btn btn-default" onclick="hrCloseModal()">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>



                </body>

                </html>