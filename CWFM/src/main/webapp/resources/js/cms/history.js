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


function fillTable(tableId, rows) {

    let table = document.getElementById(tableId);
    table.innerHTML = "";

    if (!rows || rows.length === 0) {
        table.innerHTML = "<tr><td>No data</td></tr>";
        return;
    }

    // header
    let header = "<tr>";
    Object.keys(rows[0]).forEach(col => {
        header += "<th>" + col + "</th>";
    });
    header += "</tr>";

    table.innerHTML += header;

    // rows
    rows.forEach(row => {
        let tr = "<tr>";
        Object.values(row).forEach(val => {
            tr += "<td>" + (val ?? "") + "</td>";
        });
        tr += "</tr>";
        table.innerHTML += tr;
    });
}
