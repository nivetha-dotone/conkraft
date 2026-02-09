function getMyDashboard() {
    var employerId = document.getElementById("principalEmployerId").value;
    var deptId = document.getElementById("deptId").value;

    if (employerId === "" || deptId === "") {
        alert("Please select both Employer and Department");
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
