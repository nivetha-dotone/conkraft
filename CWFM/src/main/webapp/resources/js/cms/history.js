function redirectToHistoryView() {
		    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
		    if (selectedCheckboxes.length !== 1) {
		        alert("Please select exactly one row to view.");
		        return;
		    }
		    
		    var selectedRow = selectedCheckboxes[0].closest('tr');
		    var aadharnumber = selectedRow.querySelector('[name="selectedWOs"]').value;

		    var xhr = new XMLHttpRequest();
		    xhr.onreadystatechange = function() {
		        if (xhr.readyState == 4 && xhr.status == 200) {
		            document.getElementById("mainContent").innerHTML = xhr.responseText;
		        }
		    };
		  xhr.open("GET", "/CWFM/entrypassstatus/historyview?aadhaar=" + aadharnumber, true);
		    xhr.send();
		}
	/*function loadHistory() {

    var aadhaar = document.getElementById("aadhaar").value;

    fetch("<%=request.getContextPath()%>/aadharSearchHistory/history", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "aadhaar=" + encodeURIComponent(aadhaar)
    })
    .then(res => res.json())
    .then(data => {

        fillTable("summaryTable", data.table1);
        fillTable("currentTable", data.table2);
        fillTable("previousTable", data.table3);
        fillTable("auditTable", data.table4);

    });
}*/
function loadHistory() {

    var aadhaar = document.getElementById("aadhaar").value.trim();

    if (!aadhaar) {
        alert("Please enter Aadhaar Number");
        return;
    }

    var params = new URLSearchParams();
    params.append("aadhaar", aadhaar);

    fetch("/CWFM/aadharSearchHistory/history", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        body: params.toString()
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("HTTP error " + res.status);
        }
        return res.json();
    })
    .then(data => {
        console.log("Response:", data);

        fillTable("summaryTable", data.summary);
        fillTable("currentTable", data.currentEmployment);
        fillTable("previousTable", data.previousEmployment);
        fillTable("auditTable", data.auditTrail);
    })
    .catch(err => console.error("Error:", err));
}
/*function fillTable(tableId, rows) {

    let table = document.getElementById(tableId);
    table.innerHTML = "";

    if (!rows || rows.length === 0) {
        table.innerHTML = "<tr><td>No data</td></tr>";
        return;
    }
    // 🔹 Columns to hide
    const hiddenColumns = ["source"];

    // 🔹 Custom Header Mapping
    const headerMap = {
        aadharNumber: "Aadhaar Number",
        firstName: "First Name",
        lastName: "Last Name",
        dob: "Date of Birth",
        gender: "Gender",
        mobileNumber: "Mobile Number",
        gatePassId: "GatePassID",
        gatePassStatus: "GatePass Status",
        updatedBy: "Updated By",
        updatedDate: "Updated Date",

        principalEmployer: "Principal Employer",
        contractor: "Contractor Name",
        workorder: "WorkOrder",
        trade: "Trade",
        skill: "Skill",
        department: "Department",
        area: "Area",
        natureOfJob: "Nature of Job",
        accessAreaId: "Access Area",
        doj: "Date of Joining",
        dot: "Date of Termination",

        gatePassType: "GatePass Type",
        status: "Status",
        actionType: "Action Type",
        remarks: "Reason of Offboarding",
        actionDate: "Action Date",
        fromDate: "From Date",
        toDate: "To Date"
    };

    // 🔹 Get visible columns (exclude hidden ones)
    const columns = Object.keys(rows[0]).filter(col => !hiddenColumns.includes(col));

     // 🔹 Header row
    let header = "<tr>";
    columns.forEach(col => {
        let displayName = headerMap[col] || formatHeader(col);
        header += "<th>" + displayName + "</th>";
    });
    header += "</tr>";
    table.innerHTML += header;

    // 🔹 Data rows
    rows.forEach(row => {
        let tr = "<tr>";
        columns.forEach(col => {
            tr += "<td>" + (row[col] ?? "") + "</td>";
        });
        tr += "</tr>";
        table.innerHTML += tr;
    });
}*/
function fillTable(tableId, rows) {

    let table = document.getElementById(tableId);
    table.innerHTML = "";

    if (!rows || rows.length === 0) {
        table.innerHTML = "<tbody><tr><td class='no-data'>No data available</td></tr></tbody>";
        return;
    }

    const hiddenColumns = ["source"];

    const headerMap = {
        aadharNumber: "Aadhaar Number",
        firstName: "First Name",
        lastName: "Last Name",
        dob: "Date of Birth",
        gender: "Gender",
        mobileNumber: "Mobile Number",
        gatePassId: "GatePass ID",
        gatePassStatus: "GatePass Status",
        updatedBy: "Updated By",
        updatedDate: "Updated Date",
        principalEmployer: "Principal Employer",
        contractor: "Contractor Name",
        workorder: "WorkOrder",
        trade: "Trade",
        skill: "Skill",
        department: "Department",
        area: "Area",
        natureOfJob: "Nature of Job",
        accessAreaId: "Access Area",
        doj: "Date of Joining",
        dot: "Date of Termination",
        gatePassType: "GatePass Type",
        status: "Status",
        actionType: "Action Type",
        remarks: "Reason of Offboarding",
        actionDate: "Action Date",
        fromDate: "From Date",
        toDate: "To Date",
        lastApprover:"Last Approver",
        nextApprover:"Next Approver"
    };

    const columns = Object.keys(rows[0]).filter(col => !hiddenColumns.includes(col));

    // Create THEAD
    let thead = "<thead><tr>";
    columns.forEach(col => {
        let displayName = headerMap[col] || col;
        thead += "<th>" + displayName + "</th>";
    });
    thead += "</tr></thead>";

    // Create TBODY
    let tbody = "<tbody class='table-body-style'>";
    rows.forEach(row => {
        tbody += "<tr>";
        columns.forEach(col => {
            tbody += "<td>" + (row[col] ?? "") + "</td>";
        });
        tbody += "</tr>";
    });
    tbody += "</tbody>";

    table.innerHTML = thead + tbody;
}