// Global variables
var contextPath = "/CWFM"
var pdfjsLib // Declare pdfjsLib variable
var updateCharCount // Declare updateCharCount variable


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



window.capturedImageP = window.capturedImageP || null;
  let contextPathP = "/CWFM";

window.stream = window.stream || null;
window.video = window.video || null;
window.canvas = window.canvas || null;
window.ctx = window.ctx || null;
window.capturedImages = window.capturedImages || [];
window.overlay  = window.overlay  || null;
const TOTAL_IMAGES = 60;
window.overlay = null;
window.recognitionRunning = false;


let userLatitude = null;
let userLongitude = null;





document.addEventListener("DOMContentLoaded", function () {

    video =
        document.getElementById("video");

    overlay =
        document.getElementById("overlay");
});

async function startPunchRecognition()
{
    console.log("Start Recognition Clicked");
    if(userLatitude == null || userLongitude == null)
    {
        alert("Location not available.");
        requestLocationPermission();
        return;
    }

    try
    {
        video =
            document.getElementById("video");

        overlay =
            document.getElementById("overlay");

        stream =
            await navigator.mediaDevices.getUserMedia({
                video:true
            });

        video.srcObject = stream;

        video.onloadedmetadata = () =>
        {
            overlay.width =
                video.videoWidth;

            overlay.height =
                video.videoHeight;

            recognitionRunning = true;

            recognizeLoop();
        };
    }
    catch(error)
    {
        console.log(error);

        alert(error.message);
    }
}

async function recognizeLoop()
{
    document.getElementById("loaderOverlay").style.display = "flex";

    while (recognitionRunning)
    {
        try
        {
            const frame = captureRecognitionFrame();

           console.log("Sending Request...");
const API_BASE_URL = window.location.origin + "/Test/Face/recognize";
        console.log(API_BASE_URL)
          const response = await fetch(
             API_BASE_URL,
              {
                  method: "POST",
                  headers: {
                      "Content-Type": "application/json"
                  },
                  body: JSON.stringify({
                      image: frame,
                      latitude: userLatitude,
                      longitude: userLongitude
                  })
              }
          );

           console.log("Response Status =", response.status);

           const result = await response.json();

           console.log("Recognition Result =", result);

            drawRecognitionFace(result);

            // ==========================================
            // Unknown Person
            // ==========================================
            if (result.message === "Unknown Person")
            {
                document.getElementById("result").innerHTML =
                    "❌ Unknown Person";

                clearRecognitionFace();

                await sleep(500);

                continue;
            }

            // ==========================================
            // Outside Radius
            // ==========================================
            if (result.message === "User is outside configured radius")
            {
                recognitionRunning = false;

                stopFaceCamera();

                document.getElementById("loaderOverlay").style.display =
                    "none";

                document.getElementById("result").innerHTML =
                    "❌ User is outside configured radius";

                clearRecognitionFace();

                await sleep(500);

                break;
            }

            // ==========================================
            // Success Punch
            // ==========================================
            if (result.matched)
            {
                recognitionRunning = false;

                stopFaceCamera();

                document.getElementById("loaderOverlay").style.display =
                    "none";

                document.getElementById("result").innerHTML =
                    "✅ Punch Success<br>"
                    + result.personName
                    + " ("
                    + result.personId
                    + ")";

                clearRecognitionFace();

                break;
            }

            // ==========================================
            // No Face Detected
            // ==========================================
            if (!result.faceDetected)
            {
                clearRecognitionFace();

                document.getElementById("result").innerHTML =
                    "⚠️ No Face Detected";
            }

            await sleep(500);
        }
        catch (error)
        {
            console.error(error);

            document.getElementById("loaderOverlay").style.display =
                "none";

            document.getElementById("result").innerHTML =
                "❌ Server Error";

            await sleep(1000);
        }
    }
}

function drawRecognitionFace(result)
{
    if(!overlay)
    {
        return;
    }

    const ctx =
        overlay.getContext("2d");

    ctx.clearRect(
        0,
        0,
        overlay.width,
        overlay.height
    );

    if(result.faceDetected)
    {
        ctx.strokeStyle = "lime";

        ctx.lineWidth = 3;

        ctx.strokeRect(
            result.x,
            result.y,
            result.width,
            result.height
        );
    }
}
function clearRecognitionFace()
{
    if(!overlay)
    {
        return;
    }

    const ctx = overlay.getContext("2d");

    ctx.clearRect(
        0,
        0,
        overlay.width,
        overlay.height
    );
}
function captureRecognitionFrame()
{
    const temp =
        document.createElement("canvas");

    temp.width =
        video.videoWidth;

    temp.height =
        video.videoHeight;

    const tctx =
        temp.getContext("2d");

    tctx.drawImage(
        video,
        0,
        0
    );

    return temp.toDataURL(
        "image/jpeg",
        0.9
    );
}

async function startFaceCamera() {
    try {

        video = document.getElementById("video");

        if (!video) {
            alert("Video element not found");
            return;
        }

        stream = await navigator.mediaDevices.getUserMedia({ video: true });

        video.srcObject = stream;

        await video.play(); // important for mobile browsers

    } catch (err) {
        console.error(err);
        alert(err.message);
    }
}
function stopFaceCamera()
{
    if(stream)
    {
        stream.getTracks().forEach(
            track => track.stop()
        );
    }
}

function sleep(ms)
{
    return new Promise(
        resolve => setTimeout(resolve, ms)
    );
}


 function applyMobilePunchRestriction()
 {
//     if(!isRealMobileDevice())
     if(true)
     {
         document.getElementById("status").innerHTML =
             "<span style='color:red'>Face Registration works only on Mobile Devices.</span>";
     }
 }


async function captureFacesForRegistration() {

    console.log("Capture started");

    const select = document.getElementById("workmanId");

    if (!select || select.value === "") {
        alert("Select Workman");
        return;
    }

    if (!stream) {
        alert("Start Camera First");
        return;
    }

    // 🔥 IMPORTANT FIX HERE
    if (!initCameraComponents()) {
        alert("Camera components not initialized yet");
        return;
    }

    if (!video.videoWidth || !video.videoHeight) {
        await new Promise(resolve => {
            video.onloadedmetadata = resolve;
        });
    }

    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;

    capturedImages = [];

    document.getElementById("loaderOverlay").style.display = "flex";
    document.getElementById("status").innerText = "Capturing Images...";

    for (let i = 1; i <= TOTAL_IMAGES; i++) {

        ctx.drawImage(video, 0, 0, canvas.width, canvas.height);

        const image = canvas.toDataURL("image/jpeg", 0.9);
        capturedImages.push(image);

        document.getElementById("progress").style.width =
            ((i / TOTAL_IMAGES) * 100) + "%";

        document.getElementById("status").innerText =
            "Captured " + i + " / " + TOTAL_IMAGES;

        await sleep(250);
    }

    await uploadRegistrationImages();
}



function initCameraComponents() {

    video = document.getElementById("video");
    canvas = document.getElementById("canvas");

    if (!video || !canvas) {
        console.error("Video or Canvas not found in DOM");
        return false;
    }

    ctx = canvas.getContext("2d");

    return true;
}

async function uploadRegistrationImages() {
    try {
        const select = document.getElementById("workmanId");

        if (!select || select.value === "") {
            alert("Please select Workman");
            return;
        }

        const employeeId = select.value;

        const employeeName = select.options[
            select.selectedIndex
        ].text.split("-").slice(1).join("-").trim();

        const payload = {
            employeeId: employeeId,
            employeeName: employeeName,
            images: capturedImages
        };

        console.log("Payload Sent:", payload);

        document.getElementById("loaderOverlay").style.display = "flex";

        const API_BASE_URL = window.location.origin + "/Test/Face/register";
        console.log(API_BASE_URL);
        const response = await fetch(
           API_BASE_URL,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            }
        );

        document.getElementById("loaderOverlay").style.display = "none";

        const result = await response.text();

        if (response.ok) {

            stopFaceCamera();

            document.getElementById("status").innerHTML =
                "Registration Completed";

            alert(result);

        } else {

            alert("Registration Failed\n" + result);
        }

    } catch (error) {

        document.getElementById("loaderOverlay").style.display = "none";

        console.error(error);

        alert("Server Error");
    }
}

 function openPunchTab() {

    document.getElementById("principalEmployerContent").style.display = "block";

    applyMobilePunchRestriction();

    requestLocationPermission();

}

  // function isRealMobileDevice() {
  //     return (
  //         /Android|iPhone|iPad|iPod/i.test(navigator.userAgent) &&
  //         'ontouchstart' in window &&
  //         navigator.maxTouchPoints > 1
  //     );
  // }

function requestLocationPermission(callback) {

    if (!navigator.geolocation) {
        alert("Geolocation is not supported on this device.");
        return;
    }

    if (navigator.permissions) {

        navigator.permissions.query({ name: "geolocation" }).then(function (permissionStatus) {

            if (permissionStatus.state === "granted") {

                getUserLocation(callback);

            } 
            else if (permissionStatus.state === "prompt") {

                getUserLocation(callback); // browser will show popup

            } 
            else if (permissionStatus.state === "denied") {

                showLocationBlockedAlert();

            }

        });

    } else {

        getUserLocation(callback);

    }

}




  function getUserLocation(callback) {

    navigator.geolocation.getCurrentPosition(

        function (position) {

            userLatitude = position.coords.latitude;
            userLongitude = position.coords.longitude;

            console.log("Latitude:", userLatitude);
            console.log("Longitude:", userLongitude);

            if (callback) callback();

        },

       function (error) {

    if (error.code === 1) {

        showLocationBlockedAlert();

    }
    else if (error.code === 2) {

        alert("Location unavailable. Turn ON GPS.");

    }
    else if (error.code === 3) {

        alert("Location request timed out.");

    }

},

        {
            enableHighAccuracy: true,
            timeout: 15000,
            maximumAge: 0
        }

    );

}
function showLocationBlockedAlert() {

    const isIOS = /iphone|ipad|ipod/i.test(navigator.userAgent);

    if (isIOS) {

        alert(
            "Location access is blocked.\n\n" +
            "For iPhone:\n\n" +
            "1. Tap 'aA' in the address bar\n" +
            "2. Tap 'Website Settings'\n" +
            "3. Allow Location\n" +
            "4. Refresh the page."
        );

    } else {

        alert(
            "Location access is blocked.\n\n" +
            "For Android:\n\n" +
            "1. Tap ⋮ menu\n" +
            "2. Site settings\n" +
            "3. Permissions\n" +
            "4. Allow Location\n" +
            "5. Refresh page."
        );

    }

}

  function checkLocationEnabled(callback) {

    if (!navigator.geolocation) {
        alert("Geolocation is not supported.");
        return;
    }

    navigator.geolocation.getCurrentPosition(

        function (position) {

            userLatitude = position.coords.latitude;
            userLongitude = position.coords.longitude;

            console.log("Latitude:", userLatitude);
            console.log("Longitude:", userLongitude);

            if (callback) callback();

        },

        function (error) {

            console.log(error);

            if (error.code === 1) {
                alert("Location permission denied. Allow location for this site.");
            } 
            else if (error.code === 2) {
                alert("Location unavailable. Turn on GPS.");
            } 
            else if (error.code === 3) {
                alert("Location request timeout.");
            }

        },

        {
            enableHighAccuracy: true,
            timeout: 15000,
            maximumAge: 0
        }
    );
}












 function isRealMobileDevice()
 {
     const ua = navigator.userAgent.toLowerCase();

     return (
         ua.includes("android") ||
         ua.includes("iphone") ||
         ua.includes("ipad") ||
         ua.includes("ipod")
     );
 }







// Select option
options.forEach(option => {

    option.addEventListener('click', function () {

        const value = this.getAttribute('data-value');

        const text = this.textContent;

        hiddenInput.value = value;

        selectedOption.innerHTML =
            "<b>Selected:</b> " + text;

        searchBox.value = text;

    });

});








