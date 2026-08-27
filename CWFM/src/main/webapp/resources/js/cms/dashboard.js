function getMyDashboard() {
    var employerId = document.getElementById("principalEmployerId").value;
    var deptId = document.getElementById("deptId").value;

    if (employerId === "" ) {
        alert("Please select Principal Employer ");
        return;
    }

    fetch("/CWFM/dashboard/view?principalEmployerId=" + employerId + "&deptId=" + deptId, {
        method: "GET"
    })
    .then(response => response.text())
    .then(html => {
    // Inject dashboard JSP response inside main content div
    document.getElementById("mainContent").innerHTML = html;

  })
    .catch(error => console.error("Error loading dashboard:", error));
}

function openCoverageModal() {
	  document.getElementById('coverageModal').style.display = 'flex';
	}
	function closeCoverageModal() {
	  document.getElementById('coverageModal').style.display = 'none';
	}
	
function openLicenseExpiryPopup() {
  document.getElementById("licenseexpiryModal").style.display = "flex";
}
function closeLicenseExpiryPopup() {
  document.getElementById("licenseexpiryModal").style.display = "none";
}
function openWorkorderExpiryPopup() {
  document.getElementById("workorderexpiryModal").style.display = "flex";
}
function closeWorkorderExpiryPopup() {
  document.getElementById("workorderexpiryModal").style.display = "none";
}	
function openGatepassExpiryPopup() {
  document.getElementById("gatepassexpiryModal").style.display = "flex";
}
function closeGatepassExpiryPopup() {
  document.getElementById("gatepassexpiryModal").style.display = "none";
}	
function openWorkmensRetiredModalPopup() {
  document.getElementById("workmensRetiredModal").style.display = "flex";
}
function closeWorkmensRetiredModalPopup() {
  document.getElementById("workmensRetiredModal").style.display = "none";
}	