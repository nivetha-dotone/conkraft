const hrUpdateEndpoint = window.location.pathname.includes("/CWFM/")
  ? "/CWFM/requestor/updateRequest"
  : "/requestor/updateRequest"

let hrCurrentPage = 1
let hrEntriesPerPage = 10
let hrFilteredData = []
let hrAllData = []
let hrCurrentTransactionId = null
let hrActiveRow = null

document.addEventListener("DOMContentLoaded", () => {
  hrInitializeData()
  hrUpdatePagination()
  hrSetupModalCloseHandlers()
})

function hrInitializeData() {
  const rows = document.querySelectorAll("#hrRequestorTableBody .hr-data-row")
  hrAllData = Array.from(rows).map((row) => {
    const cells = row.querySelectorAll("td")
    return {
      element: row,
      transactionId: hrGetCellText(cells[1]),
      name: hrGetCellText(cells[2]),
      aadharNumber: hrGetCellText(cells[3]),
      unitName: hrGetCellText(cells[4]),
      contractName: hrGetCellText(cells[5]),
      forPostId: hrGetCellText(cells[6]),
      academicString: hrGetCellText(cells[7]),
      additionalQualification: hrGetCellText(cells[8]),
      attachmentCv: cells[9].querySelector("a") ? cells[9].querySelector("a").href : "",
      updatedBy: hrGetCellText(cells[10]),
      updatedDate: hrGetCellText(cells[11]),
      approvedBy: hrGetCellText(cells[12]),
      approverRemark: hrGetCellText(cells[13]),
      status: (cells[14].dataset.status || "").toLowerCase(),
      statusLabel: cells[14].querySelector("span") ? cells[14].querySelector("span").textContent.trim() : "",
      checkbox: cells[0].querySelector('input[type="checkbox"]'),
      fullData: hrSafeParseJSON(row.dataset.fullData),
    }
  })
  hrFilteredData = [...hrAllData]
}

function hrSafeParseJSON(jsonString) {
  try {
    if (!jsonString || jsonString.trim() === "") {
      return {}
    }
    return JSON.parse(jsonString)
  } catch (error) {
    console.error("JSON parsing error:", error)
    console.error("Problematic JSON:", jsonString)
    return {}
  }
}

function hrGetCellText(cell) {
  return cell ? cell.textContent.trim() : ""
}

function hrSearchTable() {
  const searchTerm = document.getElementById("hrSearchInput").value.toLowerCase()

  if (searchTerm === "") {
    hrFilteredData = [...hrAllData]
  } else {
    hrFilteredData = hrAllData.filter((item) => {
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
        item.statusLabel,
      ]
      return fields.some((value) => (value || "").toLowerCase().includes(searchTerm))
    })
  }

  hrCurrentPage = 1
  hrUpdatePagination()
}

function hrChangeEntriesPerPage() {
  hrEntriesPerPage = Number.parseInt(document.getElementById("hrEntriesPerPage").value, 10)
  hrCurrentPage = 1
  hrUpdatePagination()
}

function hrUpdatePagination() {
  const totalEntries = hrFilteredData.length
  const totalPages = Math.ceil(totalEntries / hrEntriesPerPage)

  hrAllData.forEach((item) => {
    item.element.style.display = "none"
  })

  const startIndex = (hrCurrentPage - 1) * hrEntriesPerPage
  const endIndex = Math.min(startIndex + hrEntriesPerPage, totalEntries)

  for (let i = startIndex; i < endIndex; i++) {
    if (hrFilteredData[i]) {
      hrFilteredData[i].element.style.display = ""
    }
  }

  const showingStart = totalEntries > 0 ? startIndex + 1 : 0
  const showingEnd = endIndex
  document.getElementById("hrPaginationInfo").textContent =
    `Showing ${showingStart} to ${showingEnd} of ${totalEntries} entries`

  hrUpdatePaginationControls(totalPages)
}

function hrUpdatePaginationControls(totalPages) {
  const prevBtn = document.getElementById("hrPrevBtn")
  const nextBtn = document.getElementById("hrNextBtn")
  const pageNumbers = document.getElementById("hrPageNumbers")

  prevBtn.disabled = hrCurrentPage === 1
  nextBtn.disabled = hrCurrentPage === totalPages || totalPages === 0

  pageNumbers.innerHTML = ""

  if (totalPages <= 5) {
    for (let i = 1; i <= totalPages; i++) {
      pageNumbers.appendChild(hrCreatePageButton(i))
    }
  } else {
    pageNumbers.appendChild(hrCreatePageButton(1))

    if (hrCurrentPage > 3) {
      pageNumbers.appendChild(hrCreateEllipsis())
    }

    const start = Math.max(2, hrCurrentPage - 1)
    const end = Math.min(totalPages - 1, hrCurrentPage + 1)

    for (let i = start; i <= end; i++) {
      pageNumbers.appendChild(hrCreatePageButton(i))
    }

    if (hrCurrentPage < totalPages - 2) {
      pageNumbers.appendChild(hrCreateEllipsis())
    }

    if (totalPages > 1) {
      pageNumbers.appendChild(hrCreatePageButton(totalPages))
    }
  }
}

function hrCreatePageButton(pageNum) {
  const button = document.createElement("button")
  button.textContent = pageNum
  button.onclick = () => hrGoToPage(pageNum)
  if (pageNum === hrCurrentPage) {
    button.classList.add("active")
  }
  return button
}

function hrCreateEllipsis() {
  const span = document.createElement("span")
  span.textContent = "..."
  span.style.padding = "8px"
  return span
}

function hrGoToPage(pageNum) {
  hrCurrentPage = pageNum
  hrUpdatePagination()
}

function hrPreviousPage() {
  if (hrCurrentPage > 1) {
    hrCurrentPage--
    hrUpdatePagination()
  }
}

function hrNextPage() {
  const totalPages = Math.ceil(hrFilteredData.length / hrEntriesPerPage)
  if (hrCurrentPage < totalPages) {
    hrCurrentPage++
    hrUpdatePagination()
  }
}

function hrToggleSelectAll() {
  const selectAll = document.getElementById("hrSelectAllCheckbox")
  const visibleCheckboxes = hrFilteredData
    .slice((hrCurrentPage - 1) * hrEntriesPerPage, hrCurrentPage * hrEntriesPerPage)
    .map((item) => item.checkbox)

  visibleCheckboxes.forEach((checkbox) => {
    checkbox.checked = selectAll.checked
  })
}

function hrViewRequestorDetails(buttonEl) {
  const errorContainer = document.getElementById("hrErrorContainer")
  errorContainer.innerHTML = ""

  try {
    const row = buttonEl.closest("tr")
    if (!row) {
      throw new Error("Unable to locate the selected record.")
    }

    hrActiveRow = row
    hrCurrentTransactionId = row.dataset.transactionId

    if (!hrCurrentTransactionId) {
      throw new Error("Transaction ID not found.")
    }

    const fullDataString = row.dataset.fullData
    if (!fullDataString) {
      throw new Error("Record data not found.")
    }

    const fullData = hrSafeParseJSON(fullDataString)
    if (!fullData || Object.keys(fullData).length === 0) {
      throw new Error("Failed to parse record data.")
    }

    const recordData = [
      { label: "Transaction ID", value: fullData.transactionId || "N/A" },
      { label: "Name", value: fullData.name || "N/A" },
      { label: "Aadhar Number", value: fullData.aadharNumber || "N/A" },
      { label: "Unit Name", value: fullData.unitName || "N/A" },
      { label: "Contract Name", value: fullData.contractName || "N/A" },
      { label: "Department", value: fullData.forPostId || "N/A" },
      { label: "Academic", value: fullData.academicString || "N/A" },
      { label: "Additional Qualification", value: fullData.additionalQualification || "N/A" },
      {
        label: "CV Attachment",
        value:
          fullData.attachmentCv && fullData.attachmentCv.trim() !== ""
            ? `<a href="${fullData.attachmentCv}" target="_blank" class="hr-open-btn">OPEN</a>`
            : "N/A",
      },
      { label: "Requested By", value: fullData.updatedBy || "N/A" },
      { label: "Requested Date", value: fullData.updatedDate || "N/A" },
      { label: "Approved By", value: fullData.approveBy || "N/A" },
      { label: "Approver Remark", value: fullData.remarkApprover || "N/A" },
      {
        label: "Status",
        value: fullData.status === null ? "Submit" : fullData.status ? "Approved" : "Rejected",
      },
    ]

    const modalTableBody = document.getElementById("hrModalTableBody")
    modalTableBody.innerHTML = ""

    console.log(recordData)
    recordData.forEach(({ label, value }) => {
      const rowEl = document.createElement("tr")
      rowEl.innerHTML = "<th>" + label + "</th><td>" + value + "</td>"
      modalTableBody.appendChild(rowEl)
    })

    const hrAttachmentsList = document.getElementById("hrAttachmentsList")
    hrAttachmentsList.innerHTML = ""

    if (
      fullData.requesterAttachmentDTOList &&
      Array.isArray(fullData.requesterAttachmentDTOList) &&
      fullData.requesterAttachmentDTOList.length > 0
    ) {
      const ul = document.createElement("ul")
      fullData.requesterAttachmentDTOList.forEach((attachment) => {
        if (attachment && attachment.fileName && attachment.filePath) {
          const li = document.createElement("li")
          console.log(attachment)
          li.innerHTML =
            '<a href="' +
            attachment.filePath +
            '" target="_blank" class="hr-open-btn" style="margin-right: 5px;">' +
            attachment.fileName +
            "</a>"
          console.log(li)
          ul.appendChild(li)
        }
      })
      hrAttachmentsList.appendChild(ul)
    } else {
      hrAttachmentsList.textContent = "No HR attachments."
    }

    const statusSelect = document.getElementById("hrStatusSelect")
    const remarkText = document.getElementById("hrRemarkText")
    const statusValue = fullData.status === null ? "" : fullData.status ? "true" : "false"
    const remarkValue = fullData.remarkApprover || ""

    statusSelect.value = statusValue
    remarkText.value = remarkValue

    document.getElementById("hrViewModal").style.display = "block"
  } catch (error) {
    console.error("Error in hrViewRequestorDetails:", error)
    errorContainer.innerHTML = `<div class="hr-error-message">Error loading record details: ${error.message}</div>`
    document.getElementById("hrViewModal").style.display = "block"
  }
}

// async function hrUpdateRequest() {
//   const status = document.getElementById("hrStatusSelect").value
//   const remark = document.getElementById("hrRemarkText").value.trim()
//   const newAttachmentsInput = document.getElementById("hrNewAttachments")
//   const newFiles = newAttachmentsInput.files

//   if (!status) {
//     alert("Please select a status (Approved or Rejected).")
//     return
//   }

//   if (!remark) {
//     alert("Please enter a remark.")
//     return
//   }

//   if (!hrCurrentTransactionId) {
//     alert("No transaction selected.")
//     return
//   }

//   const statusText = status === "true" ? "Approved" : "Rejected"
//   const confirmed = window.confirm(`Are you sure you want to update this request as ${statusText}?`)
//   if (!confirmed) {
//     return
//   }

//   const updateData = {
//     status: status === "true",
//     reMark: remark,
//     transactionId: hrCurrentTransactionId,
//   }

//   const formData = new FormData()
//   formData.append("data", JSON.stringify(updateData))

//   for (let i = 0; i < newFiles.length; i++) {
//     formData.append("files", newFiles[i])
//   }

//   const modal = document.getElementById("hrModalContent")
//   modal.classList.add("loading")

//   try {
//     const response = await fetch(hrUpdateEndpoint, {
//       method: "PUT",
//       body: formData,
//     })

//     modal.classList.remove("loading")

//     if (!response.ok) {
//       const message = await hrExtractErrorMessage(response)
//       throw new Error(message)
//     }

//     alert("Request updated successfully!")
//     hrCloseModal()


//   } catch (error) {
//     modal.classList.remove("loading")
//     console.error("Update failed:", error)
//     alert("Failed to update request. Please try again.")
//   }
// }


function hrUpdateRequest() {
    const status = document.getElementById("hrStatusSelect").value;
    const remark = document.getElementById("hrRemarkText").value.trim();
    const newAttachmentsInput = document.getElementById("hrNewAttachments");
    const newFiles = newAttachmentsInput.files;

    if (!status) {
        alert("Please select a status (Approved or Rejected).");
        return;
    }

    if (!remark) {
        alert("Please enter a remark.");
        return;
    }

    if (!hrCurrentTransactionId) {
        alert("No transaction selected.");
        return;
    }

    const statusText = status === "true" ? "Approved" : "Rejected";
    const confirmed = confirm(`Are you sure you want to update this request as ${statusText}?`);
    if (!confirmed) return;

    // Prepare JSON
    const updateData = {
        status: status === "true",
        reMark: remark,
        transactionId: hrCurrentTransactionId
    };

    const formData = new FormData();
    formData.append("data", JSON.stringify(updateData));

    // Attach new files
    for (let i = 0; i < newFiles.length; i++) {
        formData.append("files", newFiles[i]);
    }

    const modal = document.getElementById("hrModalContent");
    modal.classList.add("loading");

    const xhr = new XMLHttpRequest();
    xhr.open("PUT", hrUpdateEndpoint, true);

    xhr.onload = function () {
        modal.classList.remove("loading");
        console.log("XHR Status:", xhr.status);
        console.log("Response:", xhr.responseText);

        if (xhr.status === 200) {
            console.log("HR Request updated successfully.");

            // Store success message
            sessionStorage.setItem("successMessage", "HR Request updated successfully!");

            // Close modal
            hrCloseModal();

            // Redirect via AJAX (same as your Gatepass logic)
            loadCommonList('/requestor/getListHRequestor', 'Requestor List');
        }
        else if (xhr.status === 400) {
            const msg = xhr.responseText.trim();
            console.error("Validation error:", msg);
            showLicenseError(msg);
            return;
        }
        else {
            console.error("Server error:", xhr.status, xhr.responseText);
            sessionStorage.setItem("errorMessage", "Failed to update HR request!");
            alert("Failed to update request.");
        }
    };

    xhr.onerror = function () {
        modal.classList.remove("loading");
        console.error("Network error");
        sessionStorage.setItem("errorMessage", "Failed to update HR request!");
        alert("Network error occurred.");
    };

    xhr.send(formData);
}

async function hrExtractErrorMessage(response) {
  try {
    const text = await response.text()
    return text || "Request failed"
  } catch (err) {
    return "Request failed"
  }
}

function hrRefreshTable() {
  window.location.reload()
}

function hrCloseModal() {
  document.getElementById("hrViewModal").style.display = "none"
  document.getElementById("hrErrorContainer").innerHTML = ""
  hrCurrentTransactionId = null
  hrActiveRow = null
  document.getElementById("hrStatusSelect").value = ""
  document.getElementById("hrRemarkText").value = ""
  document.getElementById("hrNewAttachments").value = ""
  document.getElementById("hrAttachmentsList").innerHTML = ""
}

function hrSetupModalCloseHandlers() {
  const modal = document.getElementById("hrViewModal")
  const modalContent = document.getElementById("hrModalContent")
  const closeButton = document.querySelector(".hr-close")

  // Close button click handler
  if (closeButton) {
    closeButton.addEventListener("click", hrCloseModal)
  }

  // Click outside modal handler - checks if click is on modal background, not on modal-content
  modal.addEventListener("click", (event) => {
    if (event.target === modal) {
      hrCloseModal()
    }
  })

  // Escape key handler
  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
      if (modal.style.display === "block") {
        hrCloseModal()
      }
    }
  })
}
