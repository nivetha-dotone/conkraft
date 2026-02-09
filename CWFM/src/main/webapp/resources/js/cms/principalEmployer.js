/* document.addEventListener("DOMContentLoaded", function() {
	    applyRowColoring();
	});

	// Function to apply row coloring
	function applyRowColoring() {
	    var table = document.getElementById("principalEmployerTable");
	    if (!table) {
	        console.error("Table element not found.");
	        return;
	    }

	    var rows = table.getElementsByTagName("tr");
	    
	    for (var i = 0; i < rows.length; i++) {
	        // Skip the header row
	        if (rows[i].getElementsByTagName("th").length > 0) {
	            continue;
	        }
	        
	        // Add row-odd or row-even class based on index
	        if (i % 2 === 0) {
	            rows[i].classList.add("row-even");
	        } else {
	            rows[i].classList.add("row-odd");
	        }
	    }
	}*/
	
	function redirectToPEEdit() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to edit.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var unitId = selectedRow.querySelector('[name="selectedUnitIds"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/principalEmployer/edit/" + unitId, true);
    xhr.send();
}

function redirectToPEView() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    var unitId = selectedRow.querySelector('[name="selectedUnitIds"]').value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/principalEmployer/view/" + unitId, true);
    xhr.send();
}

function redirectToPEAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/principalEmployer/add", true);
    xhr.send();
}
function submitForm() {
    var form = document.getElementById("addPEForm");
    var formData = new FormData(form);

    // Send the form data asynchronously using AJAX
    var xhr = new XMLHttpRequest();
    xhr.open("POST", form.action, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Check if the response indicates success or error
                var response = xhr.responseText;
                if (response.includes("redirect:/principalEmployer/list")) {
                    // Redirect to the list page if the response contains the redirect URL
                    document.getElementById("mainContent").innerHTML ="/principalEmployer/list";
                  //  window.location.href = "/principalEmployer/list";
                } else {
                    // Otherwise, update the main content with the response HTML
                    document.getElementById("mainContent").innerHTML = response;
                }
            } else {
                // Handle errors
                console.error("Error occurred during form submission");
            }
        }
    };
    xhr.send(formData);

    // Prevent the default form submission
    return false;
}

    function savePEData() {
    //event.preventDefault(); // Prevent default form submission behavior

    // Validate the address field
    var addressField = document.getElementById('address'); 
    var addressValue = addressField.value.trim();alert(addressValue); // Remove leading and trailing whitespaces

    if (addressValue === '') {
        alert("Please enter the address.");
        return; // Exit the function without submitting the form
    }

    // If the address field is valid, continue with form submission
    var form = document.getElementById('addPEForm');
    var formData = new FormData(form);
console.log(formData);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // Handle successful response
                alert("Data saved successfully!");
                // Optionally, update the UI or perform any other actions
            } else {
                // Handle error response
                alert("Failed to save data.");
            }
        }
    };

    xhr.open("POST", "/CWFM/principalEmployer/add", true); // Assuming there's an endpoint for saving the data
    xhr.send(formData);
}
        function editPEData(contextPath, id) {
        // Assuming you have jQuery available for making AJAX requests
        $.ajax({
            type: "POST",
            url: contextPath + "/principalEmployer/edit/" + id,
            data: $("#editForm").serialize(), // Replace "yourFormId" with the ID of your form
            success: function(response) {
                // Handle success response
                console.log("Data saved successfully:", response);
                 document.getElementById("mainContent").innerHTML = response;
                // Redirect to the desired page if needed
             //   window.location.href = contextPath + "/principalEmployer/list";
            },
            error: function(xhr, status, error) {
                // Handle error response
                console.error("Error saving data:", error);
            }
        });
    }
    
    function goBackToPEList(contextPath) {
   $.ajax({
        type: "GET", // Use GET method to fetch the list page
        url: contextPath + "/principalEmployer/list", // URL of the list page
        success: function(response) {
            // Update the main content section with the response
            document.getElementById("mainContent").innerHTML = response;
        },
        error: function(xhr, status, error) {
            console.error("Error loading list page:", error);
            // Handle error if needed
        }
    });// This will navigate back to the previous page in the browser history
}
 function toggleSelectAll() {
            var selectAllCheckbox = document.getElementById('selectAllCheckbox');
            var checkboxes = document.querySelectorAll('input[name="selectedUnitIds"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = selectAllCheckbox.checked;
            });
        }
        
        
 var sortingStates = {}; // Object to track sorting states for each column

        function sortTable(columnIndex) {
            var table, rows, switching, i, x, y, shouldSwitch, switchcount = 0;
            table = document.getElementById("principalEmployerTable");
            switching = true;
            var dir = "asc"; // Default sorting direction is ascending
            
            // Get the column name
            var columnName = getColumnName(columnIndex);
            
            // Check if sorting state exists for this column
            if (sortingStates[columnName] && sortingStates[columnName].dir === "asc") {
                dir = "desc"; // If the column was previously sorted in ascending order, switch to descending
            }
            
            // Reset all sort indicators
            resetSortIndicators();
            
            // Get the indicator for the clicked column
               var indicator = document.getElementById("sortIndicator" + columnName);
    console.log("Indicator element:", indicator); // Log the indicator element
    
    // Toggle the direction and set the indicator accordingly
    if (dir === "asc") {
        indicator.classList.remove("sort-desc");
        indicator.classList.add("sort-asc");
        indicator.innerHTML = "&#x25B2;"; // Upward-pointing triangle
    } else {
        indicator.classList.remove("sort-asc");
        indicator.classList.add("sort-desc");
        indicator.innerHTML = "&#x25BC;"; // Downward-pointing triangle
    }
            
            // Loop through table rows to perform sorting
            while (switching) {
                switching = false;
                rows = table.rows;
                for (i = 1; i < (rows.length - 1); i++) {
                    shouldSwitch = false;
                    x = rows[i].getElementsByTagName("td")[columnIndex];
                    y = rows[i + 1].getElementsByTagName("td")[columnIndex];
                    var xValue = x.textContent.trim();
                    var yValue = y.textContent.trim();
                    if (dir == "asc") {
                        if (compareValues(xValue, yValue) > 0) {
                            shouldSwitch = true;
                            break;
                        }
                    } else if (dir == "desc") {
                        if (compareValues(xValue, yValue) < 0) {
                            shouldSwitch = true;
                            break;
                        }
                    }
                }
                if (shouldSwitch) {
                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                    switching = true;
                    switchcount++;
                } else {
                    if (switchcount == 0 && dir == "asc") {
                        dir = "desc";
                        switching = true;
                    }
                }
            }
            
            // Update sorting state for the column
            sortingStates[columnName] = { dir: dir };
            
            console.log("Clicked column index: ", columnIndex);
            console.log("Clicked columnName: ", columnName);
            console.log("Initial sorting direction: ", dir);
        }




        function resetSortIndicators() {
            // Reset all indicators to the default upward-pointing triangle
            var indicators = document.querySelectorAll("[id^=sortIndicator]");
            for (var i = 0; i < indicators.length; i++) {
                indicators[i].innerHTML = "&#x25B2;"; // Upward-pointing triangle
            }
        }

        function getColumnName(columnIndex) {
            // Implement your logic to get column names based on index
            // For example, you can have an array of column names and return the name at the given index
            // Replace this with your actual logic
            var columnNames = ["", "Name", "Address", "ManagerName", "ManagerAddr", "BusinessType", "MaxWorkmen", "MaxCntrWorkmen", "BocwApp", "IsmwApp", "Code", "Organization", "PfCode", "LicenseNum", "WcNum", "EsicNum", "PtReg", "LwfReg", "FactoryLicNum", "IsrApp", "RcNum", "Attachment", "StateId"];
    return columnNames[columnIndex];// Replace this with your actual implementation
        }

        function compareValues(x, y) {
            // Custom comparison function for alphanumeric values and handling empty values
            if (x === "" && y === "") {
                return 0;
            } else if (x === "") {
                return 1;
            } else if (y === "") {
                return -1;
            } else if (!isNaN(x) && !isNaN(y)) {
                return parseFloat(x) - parseFloat(y);
            } else {
                return x.localeCompare(y);
            }
        }


        function searchPrincipalEmployers(contextPath) {
            var searchQuery = document.getElementById('searchInput').value.trim();
            // Assuming you have jQuery available for making AJAX requests
            $.ajax({
                type: "GET",
                url: contextPath + "/principalEmployer/search",
                data: { query: searchQuery }, // Pass the search query as data
                success: function(response) {
                    // Handle success response
                  //  console.log("Search results:", response);
                    document.getElementById("mainContent").innerHTML = response;
                },
                error: function(xhr, status, error) {
                    // Handle error response
                    console.error("Error searching:", error);
                }
            });
        }
        
        function exportToPrincCSV() {
            var selectedRows = document.querySelectorAll('input[name="selectedUnitIds"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAXCNTRWORKMEN,BOCWAPPLICABILITY,ISMWAPPLICABILITY,CODE,ORGANIZATION,PFCODE,LICENSENUMBER,WCNUMBER,ESICNUMBER,PTREGNO,LWFREGNO,FACTORYLICENCENUMBER,ISRCAPPLICABLE,RCNUMBER,ATTACHMENTNM,STATEID\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6), td:nth-child(7), td:nth-child(8), td:nth-child(9), td:nth-child(10), td:nth-child(11), td:nth-child(12), td:nth-child(13), td:nth-child(14), td:nth-child(15), td:nth-child(16), td:nth-child(17), td:nth-child(18), td:nth-child(19), td:nth-child(20), td:nth-child(21), td:nth-child(22), td:nth-child(23)'); // Adjust column indices as needed
                var rowArray = [];
                rowData.forEach(function(cell) {
                    rowArray.push(cell.innerText);
                });
                csvContent += rowArray.join(",") + "\n";
            });
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "PrincipalEmployerList.csv");
            document.body.appendChild(link);
            link.click();
        }
/*$(document).ready(function() {
    var rowsPerPage = 2;
    var currentPage = 1;
    var totalRows = $('#principalEmployerTable tbody tr').length;
    var totalPages = Math.ceil(totalRows / rowsPerPage);

    showPage(1);

    $('#nextPageBtn').click(function() {
        if (currentPage < totalPages) {
            showPage(++currentPage);
            console.log("Showing page:", currentPage);
        }
    });

    $('#prevPageBtn').click(function() {
        if (currentPage > 1) {
            showPage(--currentPage);
            console.log("Showing page:", currentPage);
        }
    });


        function showPage(page) {
            console.log("Showing page:", page);
            $('#principalEmployerTable tbody tr').hide();
            var startIndex = (page - 1) * rowsPerPage;
            console.log("startIndex page:", startIndex);
            var endIndex = startIndex + rowsPerPage;
            console.log("endIndex page:", endIndex);
            $('#principalEmployerTable tbody tr').slice(startIndex, endIndex).show();
        }
    });*/
    
    function updateCheckbox(checkbox) {
    if (checkbox.checked) {
        checkbox.value = "true";
    } else {
        checkbox.value = "false";
    }
}
function toggleSection(id) {
    $(".accordion-content").not("#" + id).slideUp();
    $("#" + id).slideToggle();
}
  
function addReportRow() {
	console.log("Adding document row...");
    const row = `
        <div class="document-row">
                            <input type="text" name="peDoc"  placeholder="Enter Document name" autocomplete="off" style="text-transform:capitalize;" />
                            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
                        </div>
    `;
    $("#documentsContainer").append(row);
}

function saveDocuments() {
    const inputs = document.querySelectorAll('#documentsContainer input[name="peDoc"]');
    const docNames = [];
    const docNameMap = new Map();
    let isValid = true;
    let errorMessages = [];

    // Clear previous styles
    inputs.forEach(input => input.style.border = "");

    // Convert to Capital Case
    function toCapitalCase(str) {
        return str
            .toLowerCase()
            .split(' ')
            .map(word => word.charAt(0).toUpperCase() + word.slice(1))
            .join(' ');
    }

    // Validate and check duplicates
    inputs.forEach((input, index) => {
        const value = toCapitalCase(input.value.trim());
        if (value) {
            if (docNameMap.has(value)) {
                isValid = false;
                input.style.border = "2px solid red";
                errorMessages.push(`• Duplicate Document "${value}" found at row ${index + 1}`);
            } else {
                docNameMap.set(value, true);
                docNames.push(value);
            }
        }
    });

    // No documents entered
    if (docNames.length === 0) {
        showMessage("Please enter at least one document.", "error");
        return;
    }

    // Show duplicate errors
    if (!isValid) {
        const message = `<b>Errors Found:</b><br>${errorMessages.join("<br>")}`;
        showMessage(message, "error");
        return;
    }

    // Send data to server
    const formData = new URLSearchParams();
    docNames.forEach(name => formData.append("peDoc", name));

    fetch("/CWFM/pedocsetup/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: formData
    })
    .then(async response => {
        const text = await response.text();
        if (!response.ok) throw new Error(text);

        showMessage("Documents saved successfully!", "success");

        // ✅ Redirect to list page after 1.5 seconds
        setTimeout(() => {
			loadCommonList('/pedocsetup/list', 'Principal Employer Documents');
           // window.location.href = "/CWFM/pedocsetup/list";  // 🔁 Change this to your actual list page URL
        }, 1500);
    })
    .catch(error => {
        showMessage(error.message || "Save failed.", "error");
    });
}



function resetDocumentForm() {
    // Remove all dynamically added document rows
    const container = document.getElementById("documentsContainer");
    container.innerHTML = "";
}

function showMessage(message, type) {
    const msgBox = document.getElementById("saveStatusMsg");
    msgBox.innerHTML = message;
    msgBox.style.color = type === "success" ? "green" : "red";
    msgBox.style.fontWeight = "bold";
    msgBox.style.display = "block";

    // Auto-hide after 5 seconds
    setTimeout(() => {
        msgBox.style.display = "none";
    }, 5000);
}



function addFile(docType) {
    const container = document.getElementById("container_" + docType);

    const row = document.createElement("div");
    row.className = "file-row";
    row.style.display = "flex";
    row.style.alignItems = "center";
    row.style.gap = "10px";
    row.style.marginBottom = "10px";
    row.style.color = "black";
    const input = document.createElement("input");
    input.type = "file";
    input.name = "files_" + docType;
    input.required = true;
    input.accept = ".pdf,.jpg,.jpeg,.png"; // ✅ restrict file chooser

    const delBtn = document.createElement("button");
    delBtn.type = "button";
    delBtn.className  = "btn btn-default process-footer-button-cancel ng-binding";
    delBtn.innerText = "Delete";
    delBtn.style.marginLeft = "5px";
    delBtn.style.color = "#0176b2";
    delBtn.onmouseover = function () {
    delBtn.style.color = "white";
};
    const preview = document.createElement("span");
    preview.className = "preview";
    preview.style.marginLeft = "10px";
    preview.style.color = "black";

    // Allowed MIME types and extensions
    const allowedTypes = ["application/pdf", "image/jpeg", "image/png"];
    const allowedExts = ["pdf", "jpg", "jpeg", "png"];
    const maxSizeMB = 5;

    input.onchange = function () {
        const file = input.files[0];
        preview.innerHTML = "";

        if (!file) return;

        const fileName = file.name;
        const ext = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        // Validate extension and MIME type
        if (!allowedExts.includes(ext) || !allowedTypes.includes(file.type)) {
            alert("Only PDF, JPG, or PNG files are allowed.");
            input.value = "";
            return;
        }

        // Validate size
        if (file.size > maxSizeMB * 1024 * 1024) {
            alert("Max file size is 5MB.");
            input.value = "";
            return;
        }

        // Preview
        if (file.type.startsWith("image/")) {
            const reader = new FileReader();
            reader.onload = function (e) {
                preview.innerHTML = `<img src="${e.target.result}" width="80"/>`;
            };
            reader.readAsDataURL(file);
        } else if (file.type === "application/pdf") {
            preview.innerHTML = `<span>📄 ${fileName}</span>`;
        }
    };

    // Delete logic
    delBtn.onclick = function () {
        row.remove();
        //addFile(docType);
    };

    // Append elements
    row.appendChild(input);
    row.appendChild(delBtn);
    row.appendChild(preview);

    container.appendChild(row);
}




function savePeDocuments() {
    const employerId = $("#employerId").val();
    const data = new FormData();
    const docData = [];

    $(".doc-section").each(function () {
        const docType = $(this).find("h4").text().trim();
        const fileInputs = $(this).find("input[type='file']");

        fileInputs.each(function () {
            const file = this.files[0];
            if (file) {
                const fieldName = "files_" + docType;
                data.append(fieldName, file);
                docData.push({
                    docType: docType,
                    fileName: file.name
                });
            }
        });
    });

    const payload = {
        employerId: employerId,
        documents: docData
    };

    data.append("jsonData", JSON.stringify(payload));

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/CWFM/principalEmployer/saveAll", true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            alert("Documents uploaded successfully.");
            loadCommonList('/principalEmployer/list', 'Principal Employer');
        } else {
            alert("Upload failed: " + xhr.responseText);
        }
    };

    xhr.onerror = function () {
        alert("Network error.");
    };

    xhr.send(data);
}


/*function downloadFile(docId) {
    const url = `/CWFM/principalEmployer/download/${docId}`;
    window.open(url, '_blank');
}*/
/*function downloadFile(encodedId) {
    const url = `/CWFM/principalEmployer/download/${encodedId}`;
    window.open(url, '_blank'); // Opens securely in a new tab
}
*/
function downloadFile(encodedId) {
    const url = `/CWFM/principalEmployer/download/${encodedId}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("File not found or server error");
            }
            return response.blob();
        })
        .then(blob => {
            // Create an object URL for the blob
            const blobUrl = window.URL.createObjectURL(blob);

            // Open the file in a new tab
            window.open(blobUrl, '_blank');

            // Optional: revoke after a few seconds to free memory
            setTimeout(() => URL.revokeObjectURL(blobUrl), 5000);
        })
        .catch(err => {
            console.error("Error opening file:", err);
            alert("Unable to open file.");
        });
}

