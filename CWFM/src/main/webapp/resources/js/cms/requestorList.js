  const $ = window.jQuery

  function initRequestorTable(tablename) {
    const selector = "#" + tablename

    if ($.fn.DataTable.isDataTable(selector)) {
      $(selector).DataTable().destroy()
    }

    $(selector).DataTable({
      paging: true,
      searching: true,
      ordering: true,
      pageLength: 25,
      lengthMenu: [
        [10, 25, 50, 100],
        [10, 25, 50, 100],
      ],
      language: {
        search: "Search:",
        lengthMenu: "Show _MENU_ entries",
        info: "Showing _START_ to _END_ of _TOTAL_ entries",
        paginate: {
          first: "First",
          last: "Last",
          next: "Next",
          previous: "Previous",
        },
      },
    })

    //  removeAscSortingIfPresent();
  }

  function refreshRequestorData() {
    console.log("[v0] Refreshing requestor data")
    loadRequestorData()
  }

  // Declare the loadRequestorData function before using it
  function loadRequestorData() {
    // Placeholder for loadRequestorData implementation
    console.log("Loading requestor data...")
  }

  function filterByStatus(status) {
    console.log("[v0] Filtering by status:", status)

    if ($.fn.DataTable.isDataTable("#requestorTable")) {
      const table = $("#requestorTable").DataTable()

      if (status === "all") {
        table.column(13).search("").draw()
      } else {
        let searchTerm = ""
        switch (status) {
          case "submit":
            searchTerm = "Submit"
            break
          case "approved":
            searchTerm = "Approved"
            break
          case "rejected":
            searchTerm = "Rejected"
            break
        }
        table.column(13).search(searchTerm).draw()
      }
    }
  }

  function getSelectedRequestorIds() {
    const selectedCheckboxes = document.querySelectorAll('input[name="selectedRequestors"]:checked')
    const ids = []

    selectedCheckboxes.forEach((checkbox) => {
      ids.push(checkbox.value)
    })

    return ids
  }

  function bulkUpdateStatus(newStatus) {
    const selectedIds = getSelectedRequestorIds()

    if (selectedIds.length === 0) {
      alert("Please select requestors to update")
      return
    }

    if (confirm(`Are you sure you want to update ${selectedIds.length} requestor(s) status to ${newStatus}?`)) {
      console.log("[v0] Bulk updating status for IDs:", selectedIds)

      $.ajax({
        url: "/CWFM/RequestorCon/bulkUpdateStatus",
        type: "POST",
        data: {
          transactionIds: selectedIds,
          status: newStatus,
          updatedBy: "${sessionScope.loginuser.userAccount}",
        },
        success: (response) => {
          console.log("[v0] Bulk update successful:", response)
          alert("Status updated successfully")
          loadRequestorData() // Refresh the table
        },
        error: (xhr, status, error) => {
          console.error("[v0] Bulk update failed:", error)
          alert("Error updating status: " + error)
        },
      })
    }
  }

  // Utility function to format date
  function formatDate(dateString) {
    if (!dateString) return ""

    const date = new Date(dateString)
    return date.toLocaleDateString() + " " + date.toLocaleTimeString()
  }

  // Utility function to truncate text
  function truncateText(text, maxLength) {
    if (!text) return ""

    if (text.length <= maxLength) return text

    return text.substring(0, maxLength) + "..."
  }


  function removeAscSortingIfPresent() {
    if (!$.fn.DataTable.isDataTable("#requestorTable")) return;

    const table = $("#requestorTable").DataTable();

    // Get current sort info: example → [ [2, "asc"] ]
    const orderInfo = table.order();

    // Check if ANY column is sorted ASC
    const hasAsc = orderInfo.some(o => o[1] === "asc");

    if (hasAsc) {
        console.log("ASC sorting detected → removing sorting!");

        // Clear all sorting
        table.order([]).draw();
    }
}


  // Function to handle sorting
  // function sortTable(columnIndex) {
  //   if ($.fn.DataTable.isDataTable("#requestorTable")) {
  //     const table = $("#requestorTable").DataTable()
     
  //   }
  // }

  // Function to clear all filters
  function clearAllFilters() {
    console.log("[v0] Clearing all filters")

    if ($.fn.DataTable.isDataTable("#requestorTable")) {
      const table = $("#requestorTable").DataTable()
      table.search("").columns().search("").draw()
    }

    document.getElementById("searchInput").value = ""
  }

  // Function to print table
  function printRequestorTable() {
    console.log("[v0] Printing requestor table")

    const printWindow = window.open("", "_blank")
    const tableHtml = document.getElementById("requestorTable").outerHTML

    printWindow.document.write(`
          <html>
          <head>
              <title>Requestor List</title>
              <style>
                  table { border-collapse: collapse; width: 100%; }
                  th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                  th { background-color: #f2f2f2; }
                  .status-submit { background-color: #ffc107; color: #000; padding: 2px 8px; border-radius: 4px; }
                  .status-approved { background-color: #28a745; color: #fff; padding: 2px 8px; border-radius: 4px; }
                  .status-rejected { background-color: #dc3545; color: #fff; padding: 2px 8px; border-radius: 4px; }
              </style>
          </head>
          <body>
              <h1>Requestor List</h1>
              ${tableHtml}
          </body>
          </html>
      `)

    printWindow.document.close()
    printWindow.print()
  }

  let currentPage = 1;
  let entriesPerPage = 10;
  let filteredData = [];
  let allData = [];

  // Initialize data from JSP
  document.addEventListener('DOMContentLoaded', function() {
      // Get all table rows
      const rows = document.querySelectorAll('#requestorTableBody .data-row');
      allData = Array.from(rows).map(row => {
          const cells = row.querySelectorAll('td');
          return {
              element: row,
              transactionId: cells[1].textContent.trim(),
              name: cells[2].textContent.trim(),
              aadharNumber: cells[3].textContent.trim(),
              unitName: cells[4].textContent.trim(),
              contractName: cells[5].textContent.trim(),
              forPostId: cells[6].textContent.trim(),
              academicString: cells[7].textContent.trim(),
              additionalQualification: cells[8].textContent.trim(),
              attachmentCv: cells[9].querySelector('a') ? cells[9].querySelector('a').href : '',
              updatedBy: cells[10].textContent.trim(),
              updatedDate: cells[11].textContent.trim(),
              approveBy: cells[12].textContent.trim(),
              remarkApprover: cells[13].textContent.trim(),
              status: cells[14].getAttribute('data-status'),
              checkbox: cells[0].querySelector('input[type="checkbox"]')
          };
      });
      
      filteredData = [...allData];
      
      // Format dates
      formatDates();
      
      // Initialize pagination
      updatePagination();
  });

  function formatDates() {
      document.querySelectorAll('.date-cell').forEach(cell => {
          const dateStr = cell.textContent.trim();
          if (dateStr && dateStr !== '') {
              try {
                  // Parse the date string (format: "2025-09-22 16:24:31.063")
                  const date = new Date(dateStr);
                  if (!isNaN(date.getTime())) {
                      // Format to 12-hour format
                      const options = {
                          year: 'numeric',
                          month: '2-digit',
                          day: '2-digit',
                          hour: '2-digit',
                          minute: '2-digit',
                          second: '2-digit',
                          hour12: true
                      };
                      cell.textContent = date.toLocaleString('en-US', options);
                  }
              } catch (e) {
                  console.error('Error formatting date:', dateStr, e);
              }
          }
      });
  }

  function searchTable() {
      const searchTerm = document.getElementById('searchInput').value.toLowerCase();
      
      if (searchTerm === '') {
          filteredData = [...allData];
      } else {
          filteredData = allData.filter(item => {
              return item.transactionId.toLowerCase().includes(searchTerm) ||
                    item.name.toLowerCase().includes(searchTerm) ||
                    item.aadharNumber.toLowerCase().includes(searchTerm) ||
                    item.unitName.toLowerCase().includes(searchTerm) ||
                    item.contractName.toLowerCase().includes(searchTerm) ||
                    item.forPostId.toLowerCase().includes(searchTerm) ||
                    item.academicString.toLowerCase().includes(searchTerm) ||
                    item.additionalQualification.toLowerCase().includes(searchTerm) ||
                    item.updatedBy.toLowerCase().includes(searchTerm) ||
                    item.approveBy.toLowerCase().includes(searchTerm) ||
                    item.remarkApprover.toLowerCase().includes(searchTerm);
          });
      }
      
      currentPage = 1;
      updatePagination();
  }

  function changeEntriesPerPage() {
      entriesPerPage = parseInt(document.getElementById('entriesPerPage').value);
      currentPage = 1;
      updatePagination();
  }

  function updatePagination() {
      const totalEntries = filteredData.length;
      const totalPages = Math.ceil(totalEntries / entriesPerPage);
      
      // Hide all rows first
      allData.forEach(item => {
          item.element.style.display = 'none';
      });
      
      // Show current page rows
      const startIndex = (currentPage - 1) * entriesPerPage;
      const endIndex = Math.min(startIndex + entriesPerPage, totalEntries);
      
      for (let i = startIndex; i < endIndex; i++) {
          if (filteredData[i]) {
              filteredData[i].element.style.display = '';
          }
      }
      
      // Update pagination info
      const showingStart = totalEntries > 0 ? startIndex + 1 : 0;
      const showingEnd = endIndex;
      document.getElementById('paginationInfo').textContent = 
          `Showing ${showingStart} to ${showingEnd} of ${totalEntries} entries`;
      
      // Update pagination controls
      updatePaginationControls(totalPages);
  }

  function updatePaginationControls(totalPages) {
      const prevBtn = document.getElementById('prevBtn');
      const nextBtn = document.getElementById('nextBtn');
      const pageNumbers = document.getElementById('pageNumbers');
      
      // Update Previous button
      prevBtn.disabled = currentPage === 1;
      
      // Update Next button
      nextBtn.disabled = currentPage === totalPages || totalPages === 0;
      
      // Update page numbers
      pageNumbers.innerHTML = '';
      
      if (totalPages <= 5) {
          // Show all pages if 5 or fewer
          for (let i = 1; i <= totalPages; i++) {
              const pageBtn = createPageButton(i);
              pageNumbers.appendChild(pageBtn);
          }
      } else {
          // Show first page
          pageNumbers.appendChild(createPageButton(1));
          
          if (currentPage > 3) {
              const ellipsis = document.createElement('span');
              ellipsis.textContent = '...';
              ellipsis.style.padding = '8px';
              pageNumbers.appendChild(ellipsis);
          }
          
          // Show current page and neighbors
          const start = Math.max(2, currentPage - 1);
          const end = Math.min(totalPages - 1, currentPage + 1);
          
          for (let i = start; i <= end; i++) {
              const pageBtn = createPageButton(i);
              pageNumbers.appendChild(pageBtn);
          }
          
          if (currentPage < totalPages - 2) {
              const ellipsis = document.createElement('span');
              ellipsis.textContent = '...';
              ellipsis.style.padding = '8px';
              pageNumbers.appendChild(ellipsis);
          }
          
          // Show last page
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

  function viewSelectedRequestor() {
      const selectedCheckboxes = document.querySelectorAll('input[name="selectedWOs"]:checked');

      if (selectedCheckboxes.length === 0) {
          alert('Please select at least one record to view.');
          return;
      }

      if (selectedCheckboxes.length > 1) {
          alert('Please select only one record to view.');
          return;
      }

      // Get the selected record data
      const selectedRow = selectedCheckboxes[0].closest('tr');
      const record = JSON.parse(selectedCheckboxes[0].getAttribute('data-record'));
      console.log(selectedCheckboxes[0].getAttribute('data-record'));
      

      const recordData = {
          'Transaction ID': record.transactionId,
          'Candidate Name': record.name,
          'Aadhar Number': record.aadharNumber,
          'Unit Name': record.unitName,
          'Contractor Name': record.contractName,
          'Department': record.forPostId,
          'Academic': record.academicString,
          'Additional Qualification': record.additionalQualification,
          'CV Attachment': record.attachmentCv ? record.attachmentCv : 'N/A',
          'Requested By': record.updatedBy,
          'Requested Date': record.updatedDate,
          'Approved By': record.approveBy,
          'Approver Remark': record.remarkApprover,
          'Status': selectedRow.querySelector('span') ? selectedRow.querySelector('span').textContent.trim() : 'N/A'
      };


      console.log(recordData);

      // Populate modal
      const modalTableBody = document.getElementById('modalTableBody');
      modalTableBody.innerHTML = '';

      Object.entries(recordData).forEach(([key, value]) => {
          const row = document.createElement('tr');
          let cellContent = value;

          // Handle CV Attachment special case
          if (key === 'CV Attachment' && value !== 'N/A') {
              cellContent = '<a href="' + value + '" target="_blank" class="open-btn">OPEN</a>';
          }

          row.innerHTML = '<th>' + key + '</th><td>' + cellContent + '</td>';
          modalTableBody.appendChild(row);
      });

      if (record.requesterAttachmentDTOList && record.requesterAttachmentDTOList.length > 0) {
          const attachmentsRow = document.createElement('tr');
          const attachmentsHeader = document.createElement('th');
          attachmentsHeader.textContent = 'Approver Attachments';
          attachmentsRow.appendChild(attachmentsHeader);

          const attachmentsCell = document.createElement('td');
          let attachmentLinks = '';
          record.requesterAttachmentDTOList.forEach(attachment => {
              attachmentLinks += '<a href="' + attachment.filePath + '" target="_blank" class="open-btn" style="margin-right: 5px;">' + attachment.fileName + '</a>';
          });
          attachmentsCell.innerHTML = attachmentLinks;
          attachmentsRow.appendChild(attachmentsCell);
          modalTableBody.appendChild(attachmentsRow);
      } else {
          const noAttachmentsRow = document.createElement('tr');
          noAttachmentsRow.innerHTML = '<th>Approver Attachments</th><td>No attachments available</td>';
          modalTableBody.appendChild(noAttachmentsRow);
      }

      // Show modal
      document.getElementById('viewModal').style.display = 'block';
  }

  function closeModal() {
      document.getElementById('viewModal').style.display = 'none';
  }

  // Close modal when clicking outside of it
  window.onclick = function(event) {
      const modal = document.getElementById('viewModal');
      if (event.target === modal) {
          modal.style.display = 'none';
      }
  }
