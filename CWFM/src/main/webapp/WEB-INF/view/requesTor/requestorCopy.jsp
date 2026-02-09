<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HR Requestor List</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
    <style>
        body {
            background-color: #FFFFFF;
            font-family: 'Noto Sans', sans-serif;
        }
        .controls-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem;
            background-color: #f8f8f8;
            margin-bottom: 1rem;
            flex-wrap: wrap;
            gap: 10px;
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
        .btn-success {
            background-color: #008b8b;

            color: white;
        }
        .btn-success:hover {
            background-color: rgb(0, 81, 81);

            color: white;
        }
        .btn-view {
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
        .btn-view:hover {
            background-color: rgb(0, 81, 81);
            color: white;
            text-decoration: none;
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
            color: #333;
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
            font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
            text-align: center;
            box-sizing: border-box;
        }
        .status-submit,
        .status-approved,
        .status-rejected {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 0.8em;
            font-weight: bold;
            color: #fff;
        }
        .status-submit {
            background-color: #28a745;
        }
        .status-approved {
            background-color: #007bff;
        }
        .status-rejected {
            background-color: #dc3545;
        }
        .open-btn {
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
        .modal-content.loading {
            opacity: 0.6;
            pointer-events: none;
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
        .update-section {
            margin-top: 20px;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
            border: 1px solid #dee2e6;
        }
        .update-section h3 {
            margin-top: 0;
            color: #495057;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #495057;
        }
        .form-control {
            width: 100%;
            padding: 8px 12px;
            font-size: 14px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-control:focus {
            outline: none;
            border-color: #80bdff;
            box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
        }
        textarea.form-control {
            resize: vertical;
            min-height: 80px;
        }
        .update-buttons {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
            margin-top: 20px;
        }
        .hidden {
            display: none;
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
            .modal-content {
                width: 95%;
                margin: 1% auto;
            }
            .update-buttons {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
<c:url value="/requestor/updateRequest" var="updateRequestUrl" />

<div class="controls-container">
    <div class="entries-control">
        <label>Show</label>
        <select id="entriesPerPage" onchange="changeEntriesPerPage()">
            <option value="10" selected>10</option>
            <option value="25">25</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
        <label>entries</label>
    </div>
    <div class="search-control">
        <input type="text" class="search-box" id="searchInput" placeholder="Search by Transaction ID, Name, Aadhar, etc..." onkeyup="searchTable()">
    </div>
    <div class="action-buttons">
        <button type="button" class="btn btn-primary" onclick="refreshTable()">Refresh</button>
    </div>
</div>

<div class="table-container">
    <table id="requestorTBL" cellspacing="0" cellpadding="0">
        <thead>
        <tr>
            <th style="width: 40px;">
                <input type="checkbox" id="selectAllRequestorCheckbox" onchange="toggleSelectAllRequestors()">
            </th>
            <th>Transaction ID</th>
            <th>Name</th>
            <th>Aadhar Number</th>
            <th>Unit Name</th>
            <th>Contract Name</th>
            <th>Post ID</th>
            <th>Academic</th>
            <th>Additional Qualification</th>
            <th>CV Attachment</th>
            <th>Requested By</th>
            <th>Requested Date</th>
            <th>Approved By</th>
            <th class="remark-header">Approver Remark</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody id="requestorTableBody">
        <c:if test="${requestorListHR.size() > 0}">
            <c:forEach items="${requestorListHR}" var="wo" varStatus="status">
                <tr class="data-row"
                    data-index="${status.index}"
                    data-transaction-id="${wo.transactionId}">
                    <td>
                        <input type="checkbox"
                               name="selectedWOs"
                               value="${wo.transactionId}"
                               data-record="${wo.transactionId}" />
                    </td>
                    <td class="transaction-cell">${wo.transactionId}</td>
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
                    <td class="requested-by-cell">${wo.updatedBy}</td>
                    <td class="date-cell">${wo.updatedDate}</td>
                    <td class="approved-by-cell">${wo.approveBy}</td>
                    <td class="remark-cell">${wo.remarkApprover}</td>
                    <td class="status-cell"
                        data-status="${wo.status == null ? '' : (wo.status ? 'true' : 'false')}">
                        <c:choose>
                            <c:when test="${wo.status == null}">
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
                    <td>
                        <button type="button" class="btn-view" onclick="viewRequestorDetails(this)">Update</button>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>

<div class="pagination-container">
    <div class="pagination-info" id="paginationInfo">
        Showing 0 to 0 of 0 entries
    </div>
    <div class="pagination" id="paginationControls">
        <button onclick="previousPage()" id="prevBtn">Previous</button>
        <div id="pageNumbers"></div>
        <button onclick="nextPage()" id="nextBtn">Next</button>
    </div>
</div>

<div id="viewModal" class="modal">
    <div class="modal-content" id="modalContent">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2>Requestor Details</h2>
        <table class="modal-table">
            <tbody id="modalTableBody">



                
            </tbody>
        </table>
        <div class="update-section">
            <h3>Update Request Status</h3>
            <div class="form-group">
                <label for="statusSelect">Status:</label>
                <select id="statusSelect" class="form-control">
                    <option value="">Select Status</option>
                    <option value="true">Approved</option>
                    <option value="false">Rejected</option>
                </select>
            </div>
            <div class="form-group">
                <label for="remarkText">Remark:</label>
                <textarea id="remarkText" class="form-control" placeholder="Enter your remark here..." rows="4"></textarea>
            </div>
            <div class="update-buttons">
                <button type="button" class="btn btn-success" onclick="updateRequest()">Update Request</button>
                <button type="button" class="btn" onclick="closeModal()">Cancel</button>
            </div>
        </div>
    </div>
</div>

<script>
    const updateEndpoint = '${updateRequestUrl}';
    let currentPage = 1;
    let entriesPerPage = 10;
    let filteredData = [];
    let allData = [];
    let currentTransactionId = null;
    let activeRow = null;

    document.addEventListener('DOMContentLoaded', () => {
        initializeData();
        updatePagination();
    });

    function initializeData() {
        const rows = document.querySelectorAll('#requestorTableBody .data-row');
        allData = Array.from(rows).map(row => {
            const cells = row.querySelectorAll('td');
            return {
                element: row,
                transactionId: getCellText(cells[1]),
                name: getCellText(cells[2]),
                aadharNumber: getCellText(cells[3]),
                unitName: getCellText(cells[4]),
                contractName: getCellText(cells[5]),
                forPostId: getCellText(cells[6]),
                academicString: getCellText(cells[7]),
                additionalQualification: getCellText(cells[8]),
                attachmentCv: cells[9].querySelector('a') ? cells[9].querySelector('a').href : '',
                updatedBy: getCellText(cells[10]),
                updatedDate: getCellText(cells[11]),
                approvedBy: getCellText(cells[12]),
                approverRemark: getCellText(cells[13]),
                status: (cells[14].dataset.status || '').toLowerCase(),
                statusLabel: cells[14].querySelector('span') ? cells[14].querySelector('span').textContent.trim() : '',
                checkbox: cells[0].querySelector('input[type="checkbox"]')
            };
        });
        filteredData = [...allData];
    }

    function getCellText(cell) {
        return cell ? cell.textContent.trim() : '';
    }

    function searchTable() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();

        if (searchTerm === '') {
            filteredData = [...allData];
        } else {
            filteredData = allData.filter(item => {
                const fields = [
                    item.transactionId,
                    item.name,
                    item.aadharNumber,
                    item.unitName,
                    item.contractName,
                    item.forPostId,
                    item.academicString,
                    item.additionalQualification,
                    item.updatedBy,
                    item.approvedBy,
                    item.approverRemark,
                    item.statusLabel
                ];
                return fields.some(value => (value || '').toLowerCase().includes(searchTerm));
            });
        }

        currentPage = 1;
        updatePagination();
    }

    function changeEntriesPerPage() {
        entriesPerPage = parseInt(document.getElementById('entriesPerPage').value, 10);
        currentPage = 1;
        updatePagination();
    }

    function updatePagination() {
        const totalEntries = filteredData.length;
        const totalPages = Math.ceil(totalEntries / entriesPerPage);

        allData.forEach(item => {
            item.element.style.display = 'none';
        });

        const startIndex = (currentPage - 1) * entriesPerPage;
        const endIndex = Math.min(startIndex + entriesPerPage, totalEntries);

        for (let i = startIndex; i < endIndex; i++) {
            if (filteredData[i]) {
                filteredData[i].element.style.display = '';
            }
        }

        const showingStart = totalEntries > 0 ? startIndex + 1 : 0;
        const showingEnd = endIndex;
        document.getElementById('paginationInfo').textContent =
            `Showing ${showingStart} to ${showingEnd} of ${totalEntries} entries`;

        updatePaginationControls(totalPages);
    }

    function updatePaginationControls(totalPages) {
        const prevBtn = document.getElementById('prevBtn');
        const nextBtn = document.getElementById('nextBtn');
        const pageNumbers = document.getElementById('pageNumbers');

        prevBtn.disabled = currentPage === 1;
        nextBtn.disabled = currentPage === totalPages || totalPages === 0;

        pageNumbers.innerHTML = '';

        if (totalPages <= 5) {
            for (let i = 1; i <= totalPages; i++) {
                pageNumbers.appendChild(createPageButton(i));
            }
        } else {
            pageNumbers.appendChild(createPageButton(1));

            if (currentPage > 3) {
                pageNumbers.appendChild(createEllipsis());
            }

            const start = Math.max(2, currentPage - 1);
            const end = Math.min(totalPages - 1, currentPage + 1);

            for (let i = start; i <= end; i++) {
                pageNumbers.appendChild(createPageButton(i));
            }

            if (currentPage < totalPages - 2) {
                pageNumbers.appendChild(createEllipsis());
            }

            if (totalPages > 1) {
                pageNumbers.appendChild(createPageButton(totalPages));
            }
        }
    }

    function createPageButton(pageNum) {
        const button = document.createElement('button');
        button.textContent = pageNum;
        button.onclick = () => goToPage(pageNum);
        if (pageNum === currentPage) {
            button.classList.add('active');
        }
        return button;
    }

    function createEllipsis() {
        const span = document.createElement('span');
        span.textContent = '...';
        span.style.padding = '8px';
        return span;
    }

    function goToPage(pageNum) {
        currentPage = pageNum;
        updatePagination();
    }

    function previousPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePagination();
        }
    }

    function nextPage() {
        const totalPages = Math.ceil(filteredData.length / entriesPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            updatePagination();
        }
    }

    function toggleSelectAllRequestors() {
        const selectAll = document.getElementById('selectAllRequestorCheckbox');
        const visibleCheckboxes = filteredData
            .slice((currentPage - 1) * entriesPerPage, currentPage * entriesPerPage)
            .map(item => item.checkbox);

        visibleCheckboxes.forEach(checkbox => {
            checkbox.checked = selectAll.checked;
        });
    }




    function viewRequestorDetails(buttonEl) {
        const row = buttonEl.closest('tr');
        if (!row) {
            alert('Unable to locate the selected record.');
            return;
        }
        console.log(row);
        

        const cells = row.querySelectorAll('td');

        activeRow = row;
        currentTransactionId = row.dataset.transactionId;

        console.log(cells);

        const recordData = [
            { label: 'Transaction ID', value: getCellText(cells[1])},
            { label: 'Name', value: getCellText(cells[2]) },
            { label: 'Aadhar Number', value: getCellText(cells[3])},
            { label: 'Unit Name', value: getCellText(cells[4])},
            { label: 'Contract Name', value: getCellText(cells[5]) },
            { label: 'Post ID', value: getCellText(cells[6])},
            { label: 'Academic', value: getCellText(cells[7])},
            { label: 'Additional Qualification', value: getCellText(cells[8])},
            {
                label: 'CV Attachment',
                value: cells[9].querySelector('a')
                    ? `<a href="${cells[9].querySelector('a').href}" target="_blank" class="open-btn">OPEN</a>`
                    : 'N/A'
            },
            { label: 'Requested By', value: getCellText(cells[10])},
            { label: 'Requested Date', value: getCellText(cells[11])},
            { label: 'Approved By', value: getCellText(cells[12])},
            { label: 'Approver Remark', value: getCellText(cells[13])},
            {
                label: 'Status',
                value: cells[14].querySelector('span')
                    ? cells[14].querySelector('span').textContent.trim()
                    : 'N/A'
            }
        ];

        console.log(recordData);

        const modalTableBody = document.getElementById('modalTableBody');
        modalTableBody.innerHTML = '';

        recordData.forEach(({ label, value }) => {
            const rowEl = document.createElement('tr');
            console.log(label,typeof(label));
            console.log(value,typeof(value));

            const td1 = document.createElement('th');
            td1.textContent = label;

            const td2 = document.createElement('td');
            if (value.startsWith('<a')) {
            td2.innerHTML = value;
            } else {
              td2.textContent = value;
                  }

            rowEl.appendChild(td1);
            rowEl.appendChild(td2);
            console.log(rowEl);
            modalTableBody.appendChild(rowEl);
        });

        const statusSelect = document.getElementById('statusSelect');
        const remarkText = document.getElementById('remarkText');
        const statusValue = row.querySelector('.status-cell').dataset.status;
        const remarkValue = getCellText(row.querySelector('.remark-cell'));

        if (statusValue === 'true' || statusValue === 'false') {
            statusSelect.value = statusValue;
        } else {
            statusSelect.value = '';
        }

        remarkText.value = remarkValue;

        document.getElementById('viewModal').style.display = 'block';
    }













    async function updateRequest() {
        const status = document.getElementById('statusSelect').value;
        const remark = document.getElementById('remarkText').value.trim();

        if (!status) {
            alert('Please select a status (Approved or Rejected).');
            return;
        }

        if (!remark) {
            alert('Please enter a remark.');
            return;
        }

        if (!currentTransactionId) {
            alert('No transaction selected.');
            return;
        }

        const statusText = status === 'true' ? 'Approved' : 'Rejected';
        const confirmed = window.confirm(`Are you sure you want to update this request as ${statusText}?`);
        if (!confirmed) {
            return;
        }

        const updateData = {
            status: status === 'true',
            reMark: remark,
            transactionId: currentTransactionId
        };

        const modal = document.getElementById('modalContent');
        modal.classList.add('loading');

        try {
            const response = await fetch(updateEndpoint, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updateData)
            });

            modal.classList.remove('loading');

            if (!response.ok) {
                const message = await extractErrorMessage(response);
                throw new Error(message);
            }

            alert('Request updated successfully!');
            closeModal();
            refreshTable();
        } catch (error) {
            modal.classList.remove('loading');
            console.error('Update failed:', error);
            alert('Failed to update request. Please try again.');
        }
    }

    async function extractErrorMessage(response) {
        try {
            const text = await response.text();
            return text || 'Request failed';
        } catch (err) {
            return 'Request failed';
        }
    }

    function refreshTable() {
        window.location.reload();
    }

    function closeModal() {
        document.getElementById('viewModal').style.display = 'none';
        currentTransactionId = null;
        activeRow = null;
        document.getElementById('statusSelect').value = '';
        document.getElementById('remarkText').value = '';
    }

    window.onclick = function(event) {
        const modal = document.getElementById('viewModal');
        if (event.target === modal) {
            closeModal();
        }
    };

    window.addEventListener('keydown', event => {
        if (event.key === 'Escape') {
            const modal = document.getElementById('viewModal');
            if (modal.style.display === 'block') {
                closeModal();
            }
        }
    });
</script>
</body>
</html>
