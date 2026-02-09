let currentPage = 1;
let entriesPerPage = 10;
let filteredData = [];
let allData = [];
let selectedRecord = null;

// Initialize data from JSP
document.addEventListener('DOMContentLoaded', function() {
  // Get all table rows
  const rows = document.querySelectorAll('#tableBody .data-row');
  allData = Array.from(rows).map(row => {
    const cells = row.querySelectorAll('td');
    const checkbox = cells[0].querySelector('input[type="checkbox"]');
    
    return {
      element: row,
      checkbox: checkbox,
      refMppId: cells[1].textContent.trim(),
      unitName: cells[2].textContent.trim(),
      stateName: cells[3].textContent.trim(),
      zoneName: cells[4].textContent.trim(),
      isActive: cells[5].textContent.trim(),
      recordData: checkbox ? JSON.parse(checkbox.getAttribute('data-record')) : null
    };
  });
  
  filteredData = [...allData];
  updatePagination();
  formatDates();
});

function handleCheckboxChange(checkbox) {
  if (checkbox.checked) {
    // Uncheck all other checkboxes
    document.querySelectorAll('.row-checkbox').forEach(cb => {
      if (cb !== checkbox) {
        cb.checked = false;
      }
    });
  }
  
  // Uncheck select all if any individual checkbox changes
  document.getElementById('selectAllCheckbox').checked = false;
}

function toggleSelectAll() {
  const selectAll = document.getElementById('selectAllCheckbox');
  const visibleCheckboxes = filteredData
    .slice((currentPage - 1) * entriesPerPage, currentPage * entriesPerPage)
    .map(item => item.checkbox)
    .filter(cb => cb !== null);
  
  // If select all is checked, uncheck all (since we only want single selection)
  if (selectAll.checked) {
    selectAll.checked = false;
  }
}

function searchTable() {
  const searchTerm = document.getElementById('searchInput').value.toLowerCase();
  
  if (searchTerm === '') {
    filteredData = [...allData];
  } else {
    filteredData = allData.filter(item => {
      return item.refMppId.toLowerCase().includes(searchTerm) ||
             item.unitIdS.toLowerCase().includes(searchTerm) ||
             item.unitName.toLowerCase().includes(searchTerm) ||
             item.stateS.toLowerCase().includes(searchTerm) ||
             item.stateName.toLowerCase().includes(searchTerm) ||
             item.zoneS.toLowerCase().includes(searchTerm) ||
             item.zoneName.toLowerCase().includes(searchTerm) ||
             item.isActive.toLowerCase().includes(searchTerm);
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
      const ellipsis = document.createElement('span');
      ellipsis.textContent = '...';
      ellipsis.style.padding = '8px';
      pageNumbers.appendChild(ellipsis);
    }
    
    const start = Math.max(2, currentPage - 1);
    const end = Math.min(totalPages - 1, currentPage + 1);
    
    for (let i = start; i <= end; i++) {
      pageNumbers.appendChild(createPageButton(i));
    }
    
    if (currentPage < totalPages - 2) {
      const ellipsis = document.createElement('span');
      ellipsis.textContent = '...';
      ellipsis.style.padding = '8px';
      pageNumbers.appendChild(ellipsis);
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

function openUpdateModal() {
  const selectedCheckbox = document.querySelector('.row-checkbox:checked');
  
  if (!selectedCheckbox) {
    alert('Please select a record to update.');
    return;
  }
  
  selectedRecord = JSON.parse(selectedCheckbox.getAttribute('data-record'));
  
  // Determine current status
  const currentIsActive = selectedRecord.isActive === '1' || 
                          selectedRecord.isActive === 1 || 
                          selectedRecord.isActive === true || 
                          selectedRecord.isActive === 'true';
  
  // Prepare display data
  const displayData = {
    'Ref ID': selectedRecord.refMppId,
    'Unit Name': selectedRecord.unitName,
    'State Name': selectedRecord.stateName,
    'Zone Name': selectedRecord.zoneName,
    'Current Status': currentIsActive ? '<span class="status-active">Active</span>' : '<span class="status-inactive">Inactive</span>'
  };
  
  // Populate modal
  const modalTableBody = document.getElementById('modalTableBody');
  modalTableBody.innerHTML = '';
  
  Object.entries(displayData).forEach(([key, value]) => {
    const row = document.createElement('tr');
    const th = document.createElement("th");
    th.textContent = key || "";

    const td = document.createElement("td");
    td.innerHTML = value || "";

    if (value instanceof Date) {
    td.classList.add("date-cell");
    console.log(td.classList);
    }

    row.innerHTML = ""; 
    row.appendChild(th);
    row.appendChild(td);

    modalTableBody.appendChild(row);
  });
  
  const statusRow = document.createElement('tr');
  const newStatus = currentIsActive ? 'Inactive' : 'Active';
  const newStatusValue = !currentIsActive;
  
  const select = document.createElement("select");
select.className = "status-dropdown";
select.id = "statusDropdown";


const option1 = document.createElement("option");
option1.value = newStatusValue || "";
option1.textContent = "Select Flag" || "";

const option2 = document.createElement("option");
option2.value = newStatusValue || "";
option2.textContent = newStatus || "";

select.appendChild(option1);
select.appendChild(option2);

statusRow.innerHTML = "<th>Change Status To</th><td></td>";
statusRow.querySelector("td").appendChild(select);

  modalTableBody.appendChild(statusRow);
  
  // Show modal
  document.getElementById('updateModal').style.display = 'block';
}

function closeModal() {
  document.getElementById('updateModal').style.display = 'none';
  selectedRecord = null;
}

function confirmUpdate() {
  if (!selectedRecord) {
    alert('No record selected.');
    return;
  }
  
  const newStatus = document.getElementById('statusDropdown').value === 'true';
  const statusText = newStatus ? 'Active' : 'Inactive';
  
  // Show confirmation alert
  if (confirm(`Are you sure you want to change the status to ${statusText}?`)) {
    updatePlantZoneMapping(selectedRecord.refMppId);
  }
}

function updatePlantZoneMapping(refMppId) {
  const contextPath = '${pageContext.request.contextPath}';
  
  $.ajax({
    url: contextPath + '/plantZone/toggle/' + refMppId,
    type: 'PUT',
    success: function(response) {
      alert('Status updated successfully!');
      closeModal();
      // Reload page to show updated data
      window.location.reload();
    },
    error: function(xhr, status, error) {
      alert('Status updated successfully!' + error);
      console.error('Update error:', error);
    }
  });
}

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

// Close modal when clicking outside
window.onclick = function(event) {
  const modal = document.getElementById('updateModal');
  if (event.target === modal) {
    closeModal();
  }
}