// Global variables
var contextPath = "/CWFM"
var pdfjsLib // Declare pdfjsLib variable
var updateCharCount // Declare updateCharCount variable

console.log("  Page loaded - requester.jsp initialized")
console.log("  Context path:", contextPath)
console.log("  Current URL:", window.location.href)

function showLoading(text = "Processing your request...", subtext = "Please wait while we save your data") {
  console.log("  Showing loading overlay:", text)
  const overlay = document.getElementById("loadingOverlay")
  const loadingText = document.getElementById("loadingText")
  const loadingSubtext = document.getElementById("loadingSubtext")

  if (overlay && loadingText && loadingSubtext) {
    loadingText.textContent = text
    loadingSubtext.textContent = subtext
    overlay.style.display = "flex"

    // Disable all buttons
    const buttons = document.querySelectorAll(".btn")
    buttons.forEach((btn) => (btn.disabled = true))
  }
}
function validateNamePASS(input) {
    // Allow only letters and spaces
    input.value = input.value.replace(/[^A-Za-z\s]/g, '');

    // Limit to 40 characters
    if (input.value.length > 40) {
        input.value = input.value.substring(0, 40);
    }
}


  
function formatName2() {
  const inputField = document.getElementById("name")
  if (!inputField) return

  let text = inputField.value

  // Remove invalid characters (allow only a-z, A-Z, and spaces)
  text = text.replace(/[^a-zA-Z ]/g, "")

  // Split by spaces and capitalize first letter of each word
  const words = text.split(/\s+/)
  const formattedText = words
    .map((word) => {
      if (word.length > 0) {
        return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()
      }
      return word
    })
    .join(" ")

  inputField.value = formattedText

  // Show/hide error message
  const errorLabel = document.getElementById("error-name")
  if (errorLabel) {
    if (text.trim() && /^[a-zA-Z\s]+$/.test(text.trim())) {
      errorLabel.style.display = "none"
    }
  }
}

function formatAadharNumber() {
  const inputField = document.getElementById("aadharNumber")
  if (!inputField) return

  let text = inputField.value

  // Remove all non-digit characters
  text = text.replace(/\D/g, "")

  // Limit to 12 digits
  text = text.substring(0, 12)

  inputField.value = text

  // Show/hide error message
  const errorLabel = document.getElementById("error-aadharNumber")
  if (errorLabel) {
    if (text.length === 12) {
      errorLabel.style.display = "none"
    } else if (text.length > 0) {
      errorLabel.style.display = "block"
      errorLabel.textContent = `Aadhar number must be exactly 12 digits (${text.length}/12)`
    }
  }

  // Update character count
  updateAadharCount()
}

function updateAadharCount() {
  const aadharInput = document.getElementById("aadharNumber")
  const aadharCount = document.getElementById("aadharCount")
  if (aadharInput && aadharCount) {
    aadharCount.textContent = aadharInput.value.length + "/12"
  }
}

function formatAdditionalQualification() {
  const inputField = document.getElementById("additionalQualification")
  if (!inputField) return

  let text = inputField.value

  // Limit to 1000 characters
  if (text.length > 1000) {
    text = text.substring(0, 1000)
    inputField.value = text
  }

  // Show/hide error message and character count
  const errorLabel = document.getElementById("error-additionalQualification")
  const charCountLabel = document.getElementById("additionalQualCount")

  if (charCountLabel) {
    charCountLabel.textContent = text.length + "/1000"
  }

  if (errorLabel) {
    if (text.length <= 1000) {
      errorLabel.style.display = "none"
    } else {
      errorLabel.style.display = "block"
      errorLabel.textContent = "Additional Qualification cannot exceed 1000 characters"
    }
  }
}

function hideLoading() {
  console.log("  Hiding loading overlay")
  const overlay = document.getElementById("loadingOverlay")
  if (overlay) {
    overlay.style.display = "none"

    // Re-enable all buttons
    const buttons = document.querySelectorAll(".btn")
    buttons.forEach((btn) => (btn.disabled = false))
  }
}

function handleFileUpload(input) {
  const file = input.files[0]
  if (file && file.type === "application/pdf") {
    showLoading("Processing PDF document...", "Extracting information from your CV")
    extractTextFromPDF(file)
  } else {
    alert("Please select a valid PDF file")
    input.value = ""
  }
}

function extractTextFromPDF(file) {
  const shortNote = document.getElementById("shortNote")
  shortNote.value = "Processing PDF document..."

  const fileReader = new FileReader()
  fileReader.onload = () => {
    // Load PDF.js library dynamically
    if (typeof pdfjsLib === "undefined") {
      const script = document.createElement("script")
      script.src = "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.min.js"
      script.onload = () => {
        pdfjsLib = window["pdfjsLib"] // Assign pdfjsLib from window
        pdfjsLib.GlobalWorkerOptions.workerSrc =
          "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js"
        processPDF(fileReader.result)
      }
      document.head.appendChild(script)
    } else {
      processPDF(fileReader.result)
    }
  }
  fileReader.readAsArrayBuffer(file)
}

function processPDF(arrayBuffer) {
  const shortNote = document.getElementById("shortNote")

  pdfjsLib
    .getDocument(arrayBuffer)
    .promise.then((pdf) => {
      let extractedText = ""
      const pagePromises = []

      for (let i = 1; i <= Math.min(pdf.numPages, 3); i++) {
        // Extract from first 3 pages
        pagePromises.push(
          pdf
            .getPage(i)
            .then((page) =>
              page.getTextContent().then((textContent) => textContent.items.map((item) => item.str).join(" ")),
            ),
        )
      }

      Promise.all(pagePromises)
        .then((pages) => {
          extractedText = pages.join(" ")

          // Extract key information
          const summary = extractKeyInfo(extractedText)
          shortNote.value = summary
          hideLoading()
        })
        .catch((error) => {
          console.error("Error extracting text:", error)
          shortNote.value = "Error extracting information from PDF. Please check the document format."
          hideLoading()
        })
    })
    .catch((error) => {
      console.error("Error loading PDF:", error)
      shortNote.value = "Error loading PDF document. Please try again with a different file."
      hideLoading()
    })
}

function extractKeyInfo(text) {
  const lowerText = text.toLowerCase()
  let summary = "CV Summary:\n\n"

  // Extract name patterns
  const namePatterns = /name[:\s]*([a-zA-Z\s]{2,30})/gi
  const nameMatch = namePatterns.exec(text)
  if (nameMatch) {
    summary += "• Name: " + nameMatch[1].trim() + "\n"
  }

  // Extract experience
  if (lowerText.includes("experience") || lowerText.includes("work") || lowerText.includes("employment")) {
    summary += "• Professional experience mentioned\n"
  }

  // Extract education
  const educationKeywords = ["education", "qualification", "degree", "diploma", "certificate", "university", "college"]
  if (educationKeywords.some((keyword) => lowerText.includes(keyword))) {
    summary += "• Educational qualifications found\n"
  }

  // Extract skills
  if (lowerText.includes("skill") || lowerText.includes("technical") || lowerText.includes("proficient")) {
    summary += "• Technical skills and competencies listed\n"
  }

  // Extract contact info
  const emailPattern = /[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}/g
  const phonePattern = /[+]?[1-9]?[\d\s\-$$$$]{8,15}/g
  if (emailPattern.test(text) || phonePattern.test(text)) {
    summary += "• Contact information available\n"
  }

  summary += "\nDocument processed successfully."
  return summary
}

// function saveRequester() {
//   console.log("  ========== SAVE REQUESTER FUNCTION STARTED ==========")
//   console.log("  Function called at:", new Date().toISOString())
//   console.log("  Browser info:", navigator.userAgent)

//   const requiredElements = [
//     "principalEmployer",
//     "contractor",
//     "name",
//     "aadharNumber",
//     "department",
//     "academic",
//     "additionalQualification",
//     "shortNote",
//     "attachCV",
//   ]

//   console.log("  Checking if all required elements exist:")
//   const missingElements = []
//   requiredElements.forEach((id) => {
//     const element = document.getElementById(id)
//     if (!element) {
//       missingElements.push(id)
//       console.error("  Missing element:", id)
//     } else {
//       console.log("  Element found:", id, "- Value:", element.value || element.files?.length || "N/A")
//     }
//   })

//   if (missingElements.length > 0) {
//     console.error("  CRITICAL ERROR: Missing elements:", missingElements)
//     alert("Form elements are missing. Please refresh the page and try again.")
//     return
//   }

//   console.log("  All elements found, proceeding with validation")

//   if (!confirm("Are you sure you want to add this new request? Please click OK to confirm.")) {
//     console.log("  User cancelled the save operation")
//     return
//   }

//   try {
//     if (validateForm()) {
//       console.log("  Form validation passed, building form data")

//       showLoading("Saving requester data...", "Please wait while we process your information")

//       const formData = new FormData()

//       const principalEmployerValue = document.getElementById("principalEmployer").value
//       const contractorValue = document.getElementById("contractor").value
//       const nameValue = document.getElementById("name").value
//       const aadharValue = document.getElementById("aadharNumber").value
//       const departmentValue = document.getElementById("department").value
//       const academicValue = document.getElementById("academic").value
//       const additionalQualValue = document.getElementById("additionalQualification").value
//       const shortNoteValue = document.getElementById("shortNote").value
//       const fileInput = document.getElementById("attachCV")

//       console.log("  Form values collected:", {
//         principalEmployer: principalEmployerValue,
//         contractor: contractorValue,
//         name: nameValue,
//         aadhar: aadharValue,
//         department: departmentValue,
//         academic: academicValue,
//         additionalQual: additionalQualValue,
//         shortNote: shortNoteValue,
//         hasFile: fileInput.files.length > 0,
//         fileName: fileInput.files[0]?.name || "No file",
//       })

//       const requesterData = {
//         transactionId: generateTransactionId(),
//         prEmpId: Number.parseInt(principalEmployerValue) || 0,
//         contractorId: Number.parseInt(contractorValue) || 432,
//         name: nameValue || "",
//         aadharNumber: aadharValue || "",
//         forPostId: Number.parseInt(departmentValue) || 546,
//         academicId: Number.parseInt(academicValue) || 0,
//         additionalQualification: additionalQualValue || "",
//         attachmentCv: fileInput.files[0]?.name || "",
//         shortNote: shortNoteValue || "",
//         status: null, // Always null as per requirement
//         updatedBy: "${sessionScope.loginuser.userAccount}" || "system",
//       }

//       console.log("  JSON data prepared:", JSON.stringify(requesterData, null, 2))

//       formData.append("request", JSON.stringify(requesterData))

//       if (fileInput.files[0]) {
//         formData.append("attachCV", fileInput.files[0])
//         console.log("  File attached:", fileInput.files[0].name, "Size:", fileInput.files[0].size, "bytes")
//       }

//       console.log("  FormData contents:")
//       for (const pair of formData.entries()) {
//         if (pair[1] instanceof File) {
//           console.log("  FormData -", pair[0] + ":", "FILE -", pair[1].name, pair[1].size + " bytes")
//         } else {
//           console.log("  FormData -", pair[0] + ":", pair[1])
//         }
//       }

//       console.log("  Creating XMLHttpRequest...")

//       // Submit via AJAX
//       const xhr = new XMLHttpRequest()
//       const url = "/CWFM/requestor/saveRequestor"
//       console.log("  Target URL:", url)

//       xhr.open("POST", url, true)
//       console.log("  XMLHttpRequest opened")

//       xhr.onloadstart = () => {
//         console.log("  XMLHttpRequest - Load started")
//       }

//       xhr.onprogress = (e) => {
//         if (e.lengthComputable) {
//           const progress = Math.round((e.loaded / e.total) * 100)
//           console.log("  XMLHttpRequest - Progress:", progress + "%")
//           showLoading("Uploading data...", `Progress: ${progress}%`)
//         }
//       }

//       xhr.onload = () => {
//         console.log("  ========== AJAX RESPONSE RECEIVED ==========")
//         console.log("  Status:", xhr.status)
//         console.log("  Status Text:", xhr.statusText)
//         console.log("  Response Headers:", xhr.getAllResponseHeaders())
//         console.log("  Response Text:", xhr.responseText)
//         console.log("  Response Type:", xhr.responseType)

//         hideLoading()

        
//         if (xhr.status === 200) {
//           console.log("  SUCCESS - Request completed successfully")
//           alert("Requester saved successfully!")
//           resetFormData()
//           sessionStorage.setItem("successMessage", "Gatepass saved successfully!");
//           loadCommonList('/requestor/getRequestorList', 'Requestor List');
//         } else if (xhr.status === 0) {
//           console.error("  ERROR - Network error or CORS issue")
//           alert("Network error. Please check if the server is running and accessible.")
          
//         } else {
//           console.error("  ERROR - Server returned error status")
//           alert("Error saving requester. Status: " + xhr.status + ". Response: " + xhr.responseText)
//         }
//       }

//       xhr.onerror = () => {
//         console.error("  ========== AJAX ERROR OCCURRED ==========")
//         console.error("  Network error occurred")
//         console.error("  Status:", xhr.status)
//         console.error("  Ready State:", xhr.readyState)
//         hideLoading()
//         alert("Network error. Please check your connection and server status.")
//       }

//       xhr.ontimeout = () => {
//         console.error("  XMLHttpRequest - Timeout occurred")
//         hideLoading()
//         alert("Request timeout. Please try again.")
//       }

//       xhr.onreadystatechange = () => {
//         console.log(
//           "  Ready state changed to:",
//           xhr.readyState,
//           "(" + ["UNSENT", "OPENED", "HEADERS_RECEIVED", "LOADING", "DONE"][xhr.readyState] + ")",
//         )
//       }

//       xhr.timeout = 30000 // 30 seconds

//       console.log("  Sending AJAX request...")
//       console.log("  Request will timeout after 30 seconds")
//       xhr.send(formData)
//       console.log("  XMLHttpRequest.send() called")
//     } else {
//       console.log("  Form validation failed - stopping execution")
//     }
//   } catch (error) {
//     console.error("  ========== JAVASCRIPT ERROR IN SAVE FUNCTION ==========")
//     console.error("  Error:", error)
//     console.error("  Stack trace:", error.stack)
//     hideLoading()
//     alert("An error occurred while processing the form. Please check the console for details.")
//   }

//   console.log("  ========== SAVE REQUESTER FUNCTION ENDED ==========")
// }

function saveRequester() {

    // ---- VALIDATIONS ----
    if (!validateForm()) {
        console.error("Validation failed.");
        return;
    }

    if (!confirm("Are you sure you want to add this new request?")) {
        return;
    }

    // ---- CAPITAL CASE UTILITY ----
    function toCapitalCase(str) {
        return str
            .toLowerCase()
            .split(" ")
            .map(w => w.charAt(0).toUpperCase() + w.slice(1))
            .join(" ");
    }

    // ---- INPUT VALUES ----
    const name = toCapitalCase($("#name").val().trim());
    const shortNote = toCapitalCase($("#shortNote").val().trim());
    const additionalQualification = toCapitalCase($("#additionalQualification").val().trim());

    const fileInput = $("#attachCV")[0].files[0];

    // ---- BUILD JSON ----
    const jsonData = {
        transactionId: generateTransactionId(),
        prEmpId: parseInt($("#principalEmployer").val()) || 0,
        contractorId: parseInt($("#contractor").val()) || 0,
        name: name,
        aadharNumber: $("#aadharNumber").val().trim(),
        forPostId: parseInt($("#department").val()) || 0,
        academicId: parseInt($("#academic").val()) || 0,
        additionalQualification: additionalQualification,
        attachmentCv: fileInput ? fileInput.name : "",
        shortNote: shortNote,
        status: null,
        updatedBy: $("#loggedUser").val() || "system"
    };


    const data = new FormData();
    
  
    data.append("request", JSON.stringify(jsonData));
    if (fileInput) data.append("attachCV", fileInput);


    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/requestor/saveRequestor", true);


    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log("Requester saved successfully");
            alert("Requester saved successfully!");

            sessionStorage.setItem("successMessage", "Requester saved successfully!");

            loadCommonList('/requestor/getRequestorList', 'Requestor List');
        } 
        else {
            console.error("Error:", xhr.status, xhr.responseText);
            alert("Error saving requester: " + xhr.responseText);
        }
    };

    xhr.onerror = function () {
        console.error("Network error");
        alert("Network error. Try again.");
    };

    xhr.send(data);
}


function draftRequester() {
  console.log("  Draft button clicked - starting draftRequester function")

  showLoading("Saving draft...", "Please wait while we save your draft")

  // Similar to save but with draft status
  if (validateBasicFields()) {
    console.log("  Basic validation passed for draft")
    const formData = new FormData()

    const requesterData = {
      transactionId: generateTransactionId(),
      prEmpId: Number.parseInt(document.getElementById("principalEmployer").value) || 0,
      contractorId: Number.parseInt(document.getElementById("contractor").value) || 432,
      name: document.getElementById("name").value || "",
      aadharNumber: document.getElementById("aadharNumber").value || "",
      forPostId: Number.parseInt(document.getElementById("department").value) || 546,
      academicId: Number.parseInt(document.getElementById("academic").value) || 0,
      additionalQualification: document.getElementById("additionalQualification").value || "",
      attachmentCv: document.getElementById("attachCV").files[0]?.name || "",
      shortNote: document.getElementById("shortNote").value || "",
      status: 1, // Draft status
      // updatedBy: '${sessionScope.loginuser.userAccount}' || 'system'
    }

    console.log("  Draft JSON data prepared:", requesterData)

    formData.append("request", JSON.stringify(requesterData))

    const fileInput = document.getElementById("attachCV")
    if (fileInput.files[0]) {
      formData.append("attachCV", fileInput.files[0])
    }

    const xhr = new XMLHttpRequest()
    xhr.open("POST", "${pageContext.request.contextPath}" + "/requestor/saveRequestor", true)

    xhr.onload = () => {
      console.log("  Draft AJAX response - Status:", xhr.status)
      console.log("  Draft response text:", xhr.responseText)

      hideLoading()

      if (xhr.status === 200) {
        alert("Draft saved successfully!")
      } else {
        alert("Error saving draft. Status: " + xhr.status + ". Please try again.")
      }
    }

    xhr.onerror = () => {
      console.log("  Draft AJAX network error occurred")
      hideLoading()
      alert("Network error while saving draft. Please try again.")
    }

    xhr.send(formData)
  } else {
    console.log("  Basic validation failed for draft")
    hideLoading()
  }
}

function generateTransactionId() {
  return Date.now().toString() + Math.floor(Math.random() * 1000)
}

function cancelForm() {
  if (confirm("Are you sure you want to cancel? All unsaved changes will be lost.")) {
    window.history.back()
  }
}

function validateForm() {
  let isValid = true;

  // Hide all errors first
  document.querySelectorAll(".error-label").forEach((label) => {
    label.style.display = "none";
  });

  console.log("===== VALIDATION STARTED =====");

  // Validate Principal Employer
  const principalEmployer = document.getElementById("principalEmployer");
  if (!principalEmployer.value) {
    document.getElementById("error-principalEmployer").style.display = "block";
    isValid = false;
  }

  // Validate Contractor
  const contractor = document.getElementById("contractor");
  if (!contractor.value) {
    document.getElementById("error-contractor").style.display = "block";
    isValid = false;
  }

  // Validate Department
  const department = document.getElementById("department");
  if (!department.value) {
    document.getElementById("error-department").style.display = "block";
    isValid = false;
  }

  // Validate Name (letters, spaces, max 20 chars)
  const name = document.getElementById("name");
  if (
    !name.value.trim() ||
    !/^[A-Za-z\s]+$/.test(name.value.trim()) ||
    name.value.trim().length > 20
  ) {
    document.getElementById("error-name").style.display = "block";
    document.getElementById("error-name").textContent =
      "Name must contain only letters and spaces (max 20 characters)";
    isValid = false;
  }

  // Validate Aadhar Number (exactly 12 digits)
  const aadharNumber = document.getElementById("aadharNumber");
  if (!aadharNumber.value || !/^\d{12}$/.test(aadharNumber.value)) {
    document.getElementById("error-aadharNumber").style.display = "block";
    document.getElementById("error-aadharNumber").textContent =
      "Aadhar number must be exactly 12 digits";
    isValid = false;
  }

  // Validate Academic Qualification
  const academic = document.getElementById("academic");
  if (!academic.value) {
    document.getElementById("error-academic").style.display = "block";
    isValid = false;
  }

  // Validate Additional Qualification (max 1000 chars)
  const additionalQual = document.getElementById("additionalQualification");
  if (additionalQual.value.length > 1000) {
    document.getElementById("error-additionalQualification").style.display = "block";
    document.getElementById("error-additionalQualification").textContent =
      "Additional Qualification cannot exceed 1000 characters";
    isValid = false;
  }

  // Validate CV Attachment
  const attachCV = document.getElementById("attachCV");
  if (!attachCV.files.length) {
    document.getElementById("error-attachCV").style.display = "block";
    isValid = false;
  }

  console.log("===== VALIDATION RESULT:", isValid, "=====");
  return isValid;
}


function validateBasicFields() {
  console.log("  Starting basic field validation")
  // Less strict validation for draft
  const name = document.getElementById("name")
  const aadharNumber = document.getElementById("aadharNumber")

  if (name.value.trim() && !/^[a-zA-Z\s]+$/.test(name.value.trim())) {
    console.log("  Basic name validation failed:", name.value)
    alert("Name should contain only letters and spaces")
    return false
  }

  if (aadharNumber.value && !/^\d{12}$/.test(aadharNumber.value)) {
    console.log("  Basic aadhar validation failed:", aadharNumber.value)
    alert("Aadhar number should be exactly 12 digits")
    return false
  }

  console.log("  Basic validation passed")
  return true
}

function requester_getContractorsAndTrades(principalEmployerId, userAccount) {
  if (principalEmployerId) {
    console.log("Getting contractors for Principal Employer ID: " + principalEmployerId)
    console.log("User Account: " + userAccount)

    // Call getContractors with unitId and userAccount
    requester_getContractors(principalEmployerId, userAccount)

    // Call getDepartments with unitId
    requester_getDepartments(principalEmployerId)
  } else {
    // Clear dependent dropdowns
    document.getElementById("contractor").innerHTML = '<option value="">Please select Contractor</option>'
    document.getElementById("department").innerHTML = '<option value="">Please select Department</option>'
  }
}

function requester_getDepartments(unitId) {
  if (!unitId) {
    console.log("No unitId provided for getDepartments")
    return
  }

  const xhr = new XMLHttpRequest()
  const url = contextPath + "/requestor/getAllDepartments?unitId=" + unitId
  console.log("Fetching departments from URL:", url)

  xhr.open("GET", url, true)
  xhr.setRequestHeader("Content-Type", "application/json")

  xhr.onload = () => {
    if (xhr.status === 200) {
      try {
        const departments = JSON.parse(xhr.responseText)
        console.log("Departments received:", departments)

        const departmentSelect = document.getElementById("department")
        departmentSelect.innerHTML = '<option value="">Please select Department</option>'

        if (Array.isArray(departments)) {
          departments.forEach((department) => {
            const option = document.createElement("option")
            option.value = department.departmentId || department.id
            option.text = department.department || department.name
            departmentSelect.appendChild(option)
          })
        }
      } catch (e) {
        console.error("Error parsing departments response:", e)
      }
    } else {
      console.error("Error fetching departments:", xhr.status, xhr.statusText)
    }
  }

  xhr.onerror = () => {
    console.error("Network error while fetching departments")
  }

  xhr.send()
}

function requester_getContractors(unitId, userAccount) {
  if (!unitId || !userAccount) {
    console.log("Missing parameters for getContractors:", { unitId, userAccount })
    return
  }

  const xhr = new XMLHttpRequest()
  const url =
    contextPath + "/requestor/getAllContractors?unitId=" + unitId + "&userAccount=" + encodeURIComponent(userAccount)
  console.log("Fetching contractors from URL:", url)

  xhr.open("GET", url, true)
  xhr.setRequestHeader("Content-Type", "application/json")

  xhr.onload = () => {
    if (xhr.status === 200) {
      try {
        const contractors = JSON.parse(xhr.responseText)
        console.log("Contractors received:", contractors)

        const contractorSelect = document.getElementById("contractor")
        contractorSelect.innerHTML = '<option value="">Please select Contractor</option>'

        if (Array.isArray(contractors)) {
          contractors.forEach((contractor) => {
            const option = document.createElement("option")
            option.value = contractor.contractorId || contractor.id
            option.text = contractor.contractorName || contractor.name
            contractorSelect.appendChild(option)
          })
        }
      } catch (e) {
        console.error("Error parsing contractors response:", e)
      }
    } else {
      console.error("Error fetching contractors:", xhr.status, xhr.statusText)
    }
  }

  xhr.onerror = () => {
    console.error("Network error while fetching contractors")
  }

  xhr.send()
}

function requester_getEic() {
  console.log("Getting EIC information")
  // Implementation can be added here if needed
}


function resetFormData() {
  console.log("  Resetting form data")
  document.getElementById("requesterForm").reset()
  document.getElementById("shortNote").value = ""
  document.getElementById("name").value = ""
  document.getElementById("aadharNumber").value = ""
  document.getElementById("additionalQualification").value = ""

  // Reset character counts
  updateCharCount()
  updateAadharCount()
  formatAdditionalQualification()

  // Hide error messages
  const errorLabels = document.querySelectorAll(".error-label")
  errorLabels.forEach((label) => (label.style.display = "none"))

  console.log("  Form data reset successfully")
}

window.addEventListener("error", (e) => {
  console.error("  ========== GLOBAL ERROR CAUGHT ==========")
  console.error("  Error message:", e.message)
  console.error("  Error filename:", e.filename)
  console.error("  Error line:", e.lineno)
  console.error("  Error column:", e.colno)
  console.error("  Error object:", e.error)
})

window.addEventListener("unhandledrejection", (e) => {
  console.error("  ========== UNHANDLED PROMISE REJECTION ==========")
  console.error("  Reason:", e.reason)
  console.error("  Promise:", e.promise)
})

document.addEventListener("DOMContentLoaded", () => {
  console.log("  DOM Content Loaded - Setting up input handlers")

  // Name input handler with real-time validation
  const nameInput = document.getElementById("name")
  if (nameInput) {
    nameInput.addEventListener("input", (e) => {
      formatName2()
    })
    console.log("  Name input handler attached")
  } else {
    console.error("  Name input not found!")
  }

  // Aadhar input handler with real-time validation
  const aadharInput = document.getElementById("aadharNumber")
  if (aadharInput) {
    aadharInput.addEventListener("input", (e) => {
      formatAadharNumber()
    })
    console.log("  Aadhar input handler attached")
  } else {
    console.error("  Aadhar input not found!")
  }

  // Additional Qualification input handler with character limit
  const additionalQualInput = document.getElementById("additionalQualification")
  if (additionalQualInput) {
    additionalQualInput.addEventListener("input", (e) => {
      formatAdditionalQualification()
    })
    console.log("  Additional Qualification input handler attached")
  } else {
    console.error("  Additional Qualification input not found!")
  }

  // Initialize character count
  updateCharCount = () => {
    const shortNote = document.getElementById("shortNote")
    const charCount = document.getElementById("charCount")
    if (shortNote && charCount) {
      charCount.textContent = shortNote.value.length
    }
  }
  updateCharCount()
  console.log("  Character count initialized")

  updateAadharCount()
  console.log("  Aadhar count initialized")

  formatAdditionalQualification()
  console.log("  Additional Qualification count initialized")
})
