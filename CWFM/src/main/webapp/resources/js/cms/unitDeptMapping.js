// Function to add a row
function addRow() {
    const container = document.getElementById("rowsContainer");
    const firstRow = container.querySelector(".row-block");

    if (!firstRow) {
        alert("No template row found!");
        return;
    }

    // Clone the first row
    const newRow = firstRow.cloneNode(true);

    // Reset select values in cloned row
    newRow.querySelectorAll("select").forEach(sel => sel.value = "");

    // Append the new row
    container.appendChild(newRow);
}

function deleteRow(button) {
    let container = document.getElementById("rowsContainer");
    let totalRows = container.querySelectorAll(".row-block").length;

    if (totalRows === 1) {
        // ✅ Clear values instead of deleting the last row
        alert("At least one row is required.");
        let row = container.querySelector(".row-block");

        // Reset selects to default
        row.querySelectorAll("select").forEach(select => {
            select.selectedIndex = 0;
        });
    } else {
        // ✅ Safe to delete
        button.closest(".row-block").remove();
    }
}


    
    function saveDepartmentAreaData() {
    const rows = document.querySelectorAll("#rowsContainer .row-block");

    if (rows.length === 0) {
        alert("Please add at least one row before saving.");
        return;
    }

    let data = [];
    let isValid = true;
    let seen = new Set();

    rows.forEach((row, index) => {
        const principalEmployerId = row.querySelector("select[name='principalEmployerId']").value?.trim();
        const departmentId = row.querySelector("select[name='departmentId']").value?.trim();
        const subDepartmentId = row.querySelector("select[name='subDepartmentId']").value?.trim();

        row.style.backgroundColor = "";

         if (!principalEmployerId) {
            alert("Please select Principal Employer in row " + (index + 1));
            isValid = false;
            return;
        }

        if (!departmentId) {
            alert("Please select Department in row " + (index + 1));
            isValid = false;
            return;
        }
        if (!subDepartmentId) {
            alert("Please select Sub-Department in row " + (index + 1));
            isValid = false;
            return;
        }

        // 🔹 If SubDepartment is selected without Department (should not happen now, but safe check)
        if (!departmentId && subDepartmentId) {
            alert("Please select Department in row " + (index + 1) + " before selecting Sub Department");
            isValid = false;
            return;
        }


        // 🔹 Duplicate Check
        let key;
        /*if (!subDepartmentId || subDepartmentId === "0") {
            // Case 1: No SubDepartment → check PE + Dept
            key = principalEmployerId + "-" + departmentId;
            if (seen.has(key)) {
                alert("Duplicate found in row " + (index + 1) +
                      ": Principal Employer + Department must be unique when SubDepartment is not provided.");
                isValid = false;
                row.style.backgroundColor = "lightcoral";
                return;
            }
        } else {*/
            // Case 2: With SubDepartment → check PE + Dept + SubDept
            key = principalEmployerId + "-" + departmentId + "-" + subDepartmentId;
            if (seen.has(key)) {
                alert("Duplicate found in row " + (index + 1) +
                      ": Principal Employer + Department + SubDepartment must be unique.");
                isValid = false;
                row.style.backgroundColor = "lightcoral";
                return;
            }
       /* }*/
        seen.add(key);

        // 🔹 Push valid row
        data.push({
            principalEmployerId,
            departmentId,
            subDepartmentId
        });
    });

    if (!isValid || data.length === 0) {
        return;
    }

    console.log("Saving data:", data);

    fetch("/CWFM/departmentMapping/save", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(resp => resp.text().then(msg => {
        if (!resp.ok) {
            throw new Error(msg);
        }
        return msg;
    }))
    .then(msg => {
        alert("Saved successfully: " + msg);
       loadCommonList('/departmentMapping/existinglist', 'Department Area Mapping');
    })
    .catch(err => {
        console.error("Error saving:", err);
        alert(err.message);
    });
}



    function refreshTable() {
        // If table has at least 1 data row, hide the "no records" row
        if ($("#myTable tbody tr").length > 1) {
            $("#myTable .no-records-found").hide();
        } else {
            $("#myTable .no-records-found").show();
        }
    }
    
    function redirectToMappingAdd() {
   
	    // Fetch the content of add.jsp using AJAX
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/departmentMapping/list", true);
	    xhr.send();
	}
	
	function goBackToDeptMappingList() {
    	 loadCommonList('/departmentMapping/existinglist', 'Department Area Mapping');
    }
    
    function redirectToTradeSkillMappingAdd() {
   
	    // Fetch the content of add.jsp using AJAX
	    var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
	            // Update the mainContent element with the fetched content
	            document.getElementById("mainContent").innerHTML = xhr.responseText;
	        }
	    };
	    xhr.open("GET", "/CWFM/departmentMapping/addTradeSkill", true);
	    xhr.send();
	}
	function addTradeSkillRow() {
    const container = document.getElementById("rowsContainer");
    const firstRow = container.querySelector(".row-block");

    if (!firstRow) {
        alert("No template row found!");
        return;
    }

    // Clone the first row
    const newRow = firstRow.cloneNode(true);

    // Reset select values in cloned row
    newRow.querySelectorAll("select").forEach(sel => sel.value = "");

    // Append the new row
    container.appendChild(newRow);
}
	function saveTradeSkillData() {
    const rows = document.querySelectorAll("#rowsContainer .row-block");

    if (rows.length === 0) {
        alert("Please add at least one row before saving.");
        return;
    }

    let data = [];
    let isValid = true;
    let seen = new Set();

    rows.forEach((row, index) => {
        const principalEmployerId = row.querySelector("select[name='principalEmployerId']").value;
        const tradeId = row.querySelector("select[name='tradeId']").value;
        const skillId = row.querySelector("select[name='skillId']").value;

        row.style.backgroundColor = "";

        /*if (!principalEmployerId && tradeId) {
            alert("Please select Principal Employer and Trade in row " + (index + 1));
            isValid = false;
            return;
        }
*/
        if (!principalEmployerId && !tradeId && !skillId) {
            alert("Please select Principal Employer, Trade and Skill in row " + (index + 1));
            isValid = false;
            return;
        }
         if (!principalEmployerId && tradeId && skillId) {
            alert("Please select Principal Employer " + (index + 1));
            isValid = false;
            return;
        }
     if (principalEmployerId && !tradeId && !skillId) {
            alert("Please select Trade and Skill in row " + (index + 1));
            isValid = false;
            return;
        }
        if (principalEmployerId && tradeId && !skillId) {
            alert("Please select Skill in row " + (index + 1));
            isValid = false;
            return;
        }

        if (!tradeId && skillId) {
            alert("Please select Trade in row " + (index + 1) + " before selecting Skill");
            isValid = false;
            return;
        }

       // ✅ Duplicate check (PE + Dept combination must be unique)
        let key = principalEmployerId + "-" + tradeId + "-" +skillId;
        if (seen.has(key)) {
            alert("Duplicate found in row " + (index + 1) + ": Principal Employer + Trade + Skill must be unique.");
            isValid = false;
            row.style.backgroundColor = "lightcoral"; // highlight duplicate row
            return;
        }
        seen.add(key);

        // ✅ Push valid row to data array
        data.push({
            principalEmployerId,
            tradeId,
            skillId
        });
    });

    if (!isValid || data.length === 0) {
        return;
    }

    console.log("Saving data:", data);

    fetch("/CWFM/departmentMapping/saveTradeSkill", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(resp => resp.text().then(msg => {
        if (!resp.ok) {
            throw new Error(msg); // ❌ server error → pass message to catch
        }
        return msg; // ✅ success → return msg
    }))
    .then(msg => {
        alert("Saved successfully: " + msg);
        loadCommonList('/departmentMapping/existingTradeSkilllist', 'Trade Skill Mapping');
    })
    .catch(err => {
        console.error("Error saving:", err);
        alert(err.message); // show server error directly
    });
}

function goBackToTradeMappingList() {
    	 loadCommonList('/departmentMapping/existingTradeSkilllist', 'Trade Skill Mapping');
    }
    
function deleteSelectedDeptMappings() {
    let mappings = [];
    document.querySelectorAll("input[name='selectedWOs']:checked").forEach(checkbox => {
        const row = checkbox.closest("tr");
        const principalEmployerId = row.querySelector(".principalEmployerId").value;
        const departmentId = row.querySelector(".departmentId").value;
        const subDepartmentId = row.querySelector(".subDepartmentId").value;
        mappings.push({
            principalEmployerId: principalEmployerId,
            departmentId: departmentId,
            subDepartmentId: subDepartmentId
        });
    });

    if (mappings.length === 0) {
        alert("Please select at least one mapping to delete.");
        return;
    }

    if (!confirm("Are you sure you want to delete the selected mappings?")) {
        return;
    }

   $.ajax({
    url: '/CWFM/departmentMapping/deleteDepartmentMapping',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(mappings),
    success: function(response) {
        if (response.status === "success") {
            alert(response.message);
            loadCommonList('/departmentMapping/existinglist', 'Department Area Mapping');
        } else if (response.status === "partial") {
            alert(response.message + "\nDeleted: " + response.deleted.length + ", Skipped: " + response.skipped.length);
            loadCommonList('/departmentMapping/existinglist', 'Department Area Mapping');
        } else {
            alert(response.message);
            loadCommonList('/departmentMapping/existinglist', 'Department Area Mapping');
        }
        // reload table or refresh UI here
    },
    error: function(xhr) {
        alert("Error: " + xhr.responseText);
    }
});
}

function deleteSelectedTradeMappings() {
    let mappings = [];

    document.querySelectorAll("input[name='selectedWOs']:checked").forEach(checkbox => {
        const row = checkbox.closest("tr");
        const principalEmployerId = row.querySelector(".principalEmployerId").value;
        const tradeId = row.querySelector(".tradeId").value;
        const skillId = row.querySelector(".skillId").value;

        mappings.push({
            principalEmployerId: principalEmployerId,
            tradeId: tradeId,
            skillId: skillId
        });
    });

    if (mappings.length === 0) {
        alert("Please select at least one mapping to delete.");
        return;
    }

    if (!confirm("Are you sure you want to delete the selected mappings?")) {
        return;
    }
$.ajax({
    url: '/CWFM/departmentMapping/deleteTradeMapping',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(mappings),
    success: function(response) {
        if (response.status === "success") {
            alert(response.message);
             loadCommonList('/departmentMapping/existingTradeSkilllist', 'Trade Skill Mapping');
        } else if (response.status === "partial") {
            alert(response.message + "\nDeleted: " + response.deleted.length + ", Skipped: " + response.skipped.length);
             loadCommonList('/departmentMapping/existingTradeSkilllist', 'Trade Skill Mapping');
        } else {
            alert(response.message);
             loadCommonList('/departmentMapping/existingTradeSkilllist', 'Trade Skill Mapping');
        }
        // reload table or refresh UI here
    },
    error: function(xhr) {
        alert("Error: " + xhr.responseText);
    }
});

}
