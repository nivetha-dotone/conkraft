
    var sessionTimeout;

function startSessionTimer() {
    // Set session timeout to 3 minutes (in milliseconds)
    var timeoutDuration = 3 * 60 * 1000;

    // Start the timer
    sessionTimeout = setTimeout(function() {
        // Redirect to the login page when session expires
        window.location.href = "/CWFM"; // Replace "/login" with your actual login page URL
    }, timeoutDuration);
}

function resetSessionTimer() {
    // Reset the timer
    clearTimeout(sessionTimeout);
    startSessionTimer();
}

// Initialize session timer when the page loads
document.addEventListener("DOMContentLoaded", function() {
    startSessionTimer();

    // Reset session timer on user activity
    document.addEventListener("mousemove", resetSessionTimer);
    document.addEventListener("keypress", resetSessionTimer);
    document.addEventListener("click", resetSessionTimer);
});

function checkSessionStatus() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if (!response.sessionActive) {
                // Session has expired, redirect to login page
                window.location.href = "/CWFM";
            }
        }
    };
    xhr.open("GET", "/checkSessionStatus", true);
    xhr.send();
}

// Check session status before navigating back in the browser history
window.addEventListener('popstate', function(event) {
    // Check session status before allowing backward navigation
    checkSessionStatus();
});

        function loadPrincipalEmployerPage() {
            document.getElementById("principalEmployerContent").innerHTML = '<iframe src="principalEmployer.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        
        function loadContractor() {
            document.getElementById("contractorContent").innerHTML = '<iframe src="ContractorHRProfile.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        function loadWorkorder() {
            console.log('Loading Workorder'); // Add this line
            document.getElementById("workOrderContent").innerHTML = '<iframe src="workorder.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        function loadMinWage() {
            document.getElementById("minWageContent").innerHTML = '<iframe src="minimumwagelist.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        function loadContractWorkmen() {
            document.getElementById("contractWorkmenContent").innerHTML = '<iframe src="workmenDetail.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        function toggleSection(sectionId) {
            var sectionContent = document.getElementById(sectionId + 'Content');
            if (sectionContent.style.display === 'none') {
                sectionContent.style.display = 'block';
            } else {
                sectionContent.style.display = 'none';
            }
        }

        // Function to expand the first section and collapse the rest
        function expandFirstSection() {
            var sections = document.querySelectorAll('.section-content');
            for (var i = 0; i < sections.length; i++) {
                if (i === 0) {
                    sections[i].style.display = 'block';
                } else {
                    sections[i].style.display = 'none';
                }
            }
        }

        // Call expandFirstSection function when the document is ready
       /* document.addEventListener('DOMContentLoaded', function() {
          //  expandFirstSection();
        });*/
        function handleWorkmenDetailClick() {
            expandFirstSection();
        }
        function loadWorkmenWage() {
            document.getElementById("workmenWageContent").innerHTML = '<iframe src="wageMasterData.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        function loadWageCostReport() {
            document.getElementById("mainContent").innerHTML = '<h2>Wage Cost Report</h2><p>This is the content for the Wage Cost Report which is Work in Progress.</p>';
        }
        function loadSampleReport1() {
            document.getElementById("mainContent").innerHTML = '<h2>Register of contractor</h2><p>This is the content for Register of contractor which is Work in Progress..</p>';
        }

        function loadSampleReport2() {
            document.getElementById("mainContent").innerHTML = '<h2>Register of contract workers</h2><p>This is the content for Register of contract workers which is Work in Progress..</p>';
        }

        function loadSampleReport3() {
            document.getElementById("mainContent").innerHTML = '<h2>Reg Workers wage cum muster roll</h2><p>This is the content for Reg Workers wage cum muster roll which is Work in Progress..</p>';
        }
        function loadMusterRoleReport() {
            document.getElementById("mainContent").innerHTML = '<h2>Muster Role Report</h2><p>This is the content for the Muster Role Report which is Work in Progress.</p>';
        }
        function loadOperationalReport1() {
        	  document.getElementById("mainContent").innerHTML = '<h2>Operational Report 1</h2><p>This is the content for the Operational Report which is Work in Progress.</p>';
        }
        function loadReport(title, content) {
            document.getElementById("mainContent").innerHTML = '<h2>' + title + '</h2><p>' + content + '</p>';
        }

        function loadOperationalReport2() {
        	  document.getElementById("mainContent").innerHTML = '<h2>Operational Report 2</h2><p>This is the content for the Operational Report which is Work in Progress.</p>';
        }

        function loadOperationalReport3() {
        	  document.getElementById("mainContent").innerHTML = '<h2>Operational Report 3</h2><p>This is the content for the Operational Report which is Work in Progress.</p>';
        }
        function loadPFValidation() {
            // Replace this with the code to load PF Validation report
            alert("Loading PF Validation report");
        }

        function loadESICValidation() {
            // Replace this with the code to load ESIC Validation report
            alert("Loading ESIC Validation report");
        }

        function loadAadharValidationList() {
            // Replace this with the code to load Aadhar Validation List
            alert("Loading Aadhar Validation List");
        }

        function toggleComplianceSubMenu() {
            var submenu = document.getElementById("compliance-submenu");
            var arrowUp = document.querySelector("#compliance-item .arrow-up");
            var arrowDown = document.querySelector("#compliance-item .arrow-down");

            if (submenu.style.display === "none" || submenu.style.display === "") {
                submenu.style.display = "block";
                arrowUp.style.display = "inline-block";
                arrowDown.style.display = "none";
            } else {
                submenu.style.display = "none";
                arrowUp.style.display = "none";
                arrowDown.style.display = "inline-block";
            }
        }
 function logout(contextPath) {
   	/* var contextPath = '<%= request.getContextPath() %>'; */
   	 var logoutUrl = contextPath + '/user/logout';
       // Redirect to the logout URL
       window.location.href = logoutUrl;
   }
    function loadContent(jspFile) {
        var links = document.querySelectorAll('.sub-menu li'); // Get all submenu links
        links.forEach(function(link) {
            link.classList.remove('highlighted'); // Remove 'highlighted' class from all links
        });

        // Add 'highlighted' class to the parent li element of the clicked link
        event.target.parentElement.classList.add('highlighted');

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("mainContent").innerHTML = this.responseText;
                expandFirstSection();
            }
        };
        xhttp.open("GET", jspFile, true);
        xhttp.send();
    }
    function loadUserList() {
        var contextPath = '<%= request.getContextPath() %>'; // This will be evaluated on the server side

        // Construct the URL using the contextPath variable
        var url = contextPath + '/user/userlist';
        console.log("Constructed URL:", url); // Log the constructed URL to the console

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("mainContent").innerHTML = this.responseText;
                  resetSessionTimer();
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }
    function loadPrincipalEmployerList(contextPath) {
      //  var contextPath = '<%= request.getContextPath() %>'; // This will be evaluated on the server side

        // Construct the URL using the contextPath variable
        var url = contextPath + '/principalEmployer/list';
        console.log("Constructed URL:", url); // Log the constructed URL to the console

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("mainContent").innerHTML = this.responseText;
                  resetSessionTimer();
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }
   
    
        function showDashboard() {
            var mainContent = document.getElementById('mainContent');
            mainContent.innerHTML = '<h2>Dashboard</h2>'; // Clear existing content
            
            // Check if the canvas element exists before rendering the chart
            var principalEmployerCanvas = document.getElementById('principalEmployerChart');
            if (principalEmployerCanvas) {
                renderPieChart("principalEmployerChart", principalEmployerData);
                renderPieChart("contractorChart", contractorData);
                renderPieChart("workOrderChart", workOrderData);
                renderPieChart("workmenChart", workmenData);
            } else {
                console.error("Canvas element not found. Unable to render pie chart.");
            }
        }
        
        	function showReports() {
        	    console.log("Show reports function called");
        	    var subReports = document.querySelector('.sub-report');
        	    var arrowUpIcon = document.querySelector('.reports-link .arrow-up');
        	    var arrowDownIcon = document.querySelector('.reports-link .arrow-down');

        	    // Toggle the display of the sub-reports
        	    subReports.style.display = subReports.style.display === 'block' ? 'none' : 'block';
        	    var subReportLink = document.querySelector('.reports-link');
        	    subReportLink.classList.toggle('active');

        	    // Set the arrow icon based on the current state of the sub-reports
        	   console.log("Sub-reports display state:", subReports.style.display);
if (subReports.style.display === 'block') {
    console.log("Displaying up arrow");
    arrowUpIcon.style.display = 'inline-block'; // Show up arrow
    arrowDownIcon.style.display = 'none'; // Hide down arrow
} else {
    console.log("Displaying down arrow");
    arrowUpIcon.style.display = 'none'; // Hide up arrow
    arrowDownIcon.style.display = 'inline-block'; // Show down arrow
}
        	}
         function toggleSubMenu() {
             var submenu = document.querySelector('.sub-menu');
             submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
             var mainmenuLink = document.querySelector('.mainmenu-link');
             mainmenuLink.classList.toggle('active');

             var arrowUpIcon = document.querySelector('.mainmenu-link .arrow-up');
             var arrowDownIcon = document.querySelector('.mainmenu-link .arrow-down');
             if (submenu.style.display === 'block') {
                 arrowUpIcon.style.display = 'inline-block';
                 arrowDownIcon.style.display = 'none';
             } else {
                 arrowUpIcon.style.display = 'none';
                 arrowDownIcon.style.display = 'inline-block';
             }
         }
          function toggleSubOnboard() {
             var submenu = document.querySelector('.sub-onboard');
             submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
             var mainmenuLink = document.querySelector('.onboard-link');
             mainmenuLink.classList.toggle('active');

             var arrowUpIcon = document.querySelector('.onboard-link .arrow-up');
             var arrowDownIcon = document.querySelector('.onboard-link .arrow-down');
             if (submenu.style.display === 'block') {
                 arrowUpIcon.style.display = 'inline-block';
                 arrowDownIcon.style.display = 'none';
             } else {
                 arrowUpIcon.style.display = 'none';
                 arrowDownIcon.style.display = 'inline-block';
             }
         }

        	function toggleSubReport(categoryId) {
        	    var reportCategory = document.getElementById(categoryId);
        	    var reportList = reportCategory.querySelector(".sub-report");
        	    var arrowUp = reportCategory.querySelector(".arrow-up");
        	    var arrowDown = reportCategory.querySelector(".arrow-down");

        	    if (reportList.style.display === "none") {
        	        reportList.style.display = "block";
        	        arrowUp.style.display = "none";
        	        arrowDown.style.display = "inline";
        	    } else {
        	        reportList.style.display = "none";
        	        arrowUp.style.display = "inline";
        	        arrowDown.style.display = "none";
        	    }
        	}

        
        	function toggleStatutoryReports() {
        	    var reportsList = document.getElementById("statutory-reports-list");
        	    var arrowUp = document.querySelector("#statutory-reports .arrow-up");
        	    var arrowDown = document.querySelector("#statutory-reports .arrow-down");

        	    reportsList.style.display = reportsList.style.display === "none" ? "block" : "none";

        	    if (reportsList.style.display === "block") {
        	        arrowUp.style.display = "inline";
        	        arrowDown.style.display = "none";
        	    } else {
        	        arrowUp.style.display = "none";
        	        arrowDown.style.display = "inline";
        	    }
        	}

        	function toggleComplianceReports() {
        	    var reportsList = document.getElementById("compliance-reports-list");
        	    var arrowUp = document.querySelector("#compliance-reports-item .arrow-up");
        	    var arrowDown = document.querySelector("#compliance-reports-item .arrow-down");

        	    reportsList.style.display = reportsList.style.display === "none" ? "block" : "none";

        	    if (reportsList.style.display === "block") {
        	        arrowUp.style.display = "inline";
        	        arrowDown.style.display = "none";
        	    } else {
        	        arrowUp.style.display = "none";
        	        arrowDown.style.display = "inline";
        	    }
        	}

        	function toggleOperationalReports() {
        	    var reportsList = document.getElementById("operational-reports-list");
        	    var arrowUp = document.querySelector("#operational-reports-item .arrow-up");
        	    var arrowDown = document.querySelector("#operational-reports-item .arrow-down");

        	    reportsList.style.display = reportsList.style.display === "none" ? "block" : "none";

        	    if (reportsList.style.display === "block") {
        	        arrowUp.style.display = "inline";
        	        arrowDown.style.display = "none";
        	    } else {
        	        arrowUp.style.display = "none";
        	        arrowDown.style.display = "inline";
        	    }
        	}
      
         function toggleUsersSubMenu() {
        	    var subUsersMenu = document.querySelector('.sub-users');
        	    subUsersMenu.style.display = subUsersMenu.style.display === 'block' ? 'none' : 'block';
        	    
        	    var usersLink = document.querySelector('.users-link');
        	    usersLink.classList.toggle('active');

        	    var arrowUpIcon = usersLink.querySelector('.arrow-up');
        	    var arrowDownIcon = usersLink.querySelector('.arrow-down');
        	    
        	    if (subUsersMenu.style.display === 'block') {
        	        arrowUpIcon.style.display = 'inline-block';
        	        arrowDownIcon.style.display = 'none';
        	    } else {
        	        arrowUpIcon.style.display = 'none';
        	        arrowDownIcon.style.display = 'inline-block';
        	    }
        	}

    
   function toggleSidebar() {
	    var sidebar = document.querySelector('.sidebar');
	    var formContent = document.querySelector('.form-content');

	    if (sidebar.classList.contains('closed')) {
	        sidebar.classList.remove('closed');
	        formContent.classList.remove('close'); // Remove the close class
	    } else {
	        sidebar.classList.add('closed');
	        formContent.classList.add('close'); // Add the close class
	    }
	}
 
 /*function exportToCSV() {
    var selectedRows = document.querySelectorAll('input[name="selectedUnitIds"]:checked');
    if (selectedRows.length === 0) {
        alert("Please select at least one record to export.");
        return;
    }

    var csvContent = "data:text/csv;charset=utf-8,";
    csvContent += "NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAXCNTRWORKMEN,BOCWAPPLICABILITY,ISMWAPPLICABILITY,CODE,ORGANIZATION,PFCODE,LICENSENUMBER,WCNUMBER,ESICNUMBER,PTREGNO,LWFREGNO,FACTORYLICENCENUMBER,ISRCAPPLICABLE,RCNUMBER,ATTACHMENTNM,STATEID,ISACTIVE,UPDATEDTM,UPDATEDBY\n"; // Add headers here

   var rowData = [];
console.log("Before querySelectorAll");
var cells = row.closest("tr").querySelectorAll('td[data-column]');
console.log("After querySelectorAll");// Select all cells in the row with data-column attribute
cells.forEach(function(cell) {
    var columnName = cell.getAttribute('data-column'); // Get the column name from the cell's data-column attribute
    if (columnName) {
        var cellText = cell.innerText.trim(); // Get the text content of the cell and remove leading/trailing whitespace
        console.log("Column Name: " + columnName + ", Cell Text: " + cellText); // Log column name and cell text to console
        rowData.push(cellText); // Push cell text to rowData array
    }
});

console.log(rowData);

    var encodedUri = encodeURI(csvContent);
    var link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    link.setAttribute("download", "PrincipalEmployerList.csv");
    document.body.appendChild(link);
    link.click();
}*/

      /* function exportToCSV() {
            var selectedRows = document.querySelectorAll('input[name="selectedUnitIds"]:checked');
            if (selectedRows.length === 0) {
                alert("Please select at least one record to export.");
                return;
            }

            var csvContent = "data:text/csv;charset=utf-8,";
            csvContent += "NAME,ADDRESS,MANAGERNAME,MANAGERADDRS,BUSINESSTYPE,MAXWORKMEN,MAXCNTRWORKMEN,BOCWAPPLICABILITY,ISMWAPPLICABILITY,CODE,ORGANIZATION,PFCODE,LICENSENUMBER,WCNUMBER,ESICNUMBER,PTREGNO,LWFREGNO,FACTORYLICENCENUMBER,ISRCAPPLICABLE,RCNUMBER,ATTACHMENTNM,STATEID,ISACTIVE,UPDATEDTM,UPDATEDBY\n"; // Add headers here
            selectedRows.forEach(function(row) {
                var rowData = row.parentNode.parentNode.querySelectorAll('td:nth-child(2), td:nth-child(3)'); // Adjust column indices as needed
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
        }*/
        
    	/*function saveData(contextPath, id) { alert(id)
    var url = contextPath;
    var lastSlashIndex = url.lastIndexOf('/');
    var id = url.substring(lastSlashIndex + 1);

    window.location.href = "/CWFM/principalEmployer/edit/" + id;
}*/
  /* function updateWorkmenWages() {
       var form = document.getElementById("updateForm");
       var selectedIds = [];
       
       // Retrieve selectedIds from input fields
       var selectedIdsInput = form.querySelectorAll('input[name="selectedIds"]:checked');
       selectedIdsInput.forEach(function(input) {
           selectedIds.push(input.value);
       });

       // Create JSON object with form data
       var formData = {
           selectedIds: selectedIds,
           workmenWage: {}
       };
       
       // Populate workmenWage object with form field values
       form.querySelectorAll('input[name^="workmenWage."]').forEach(function(input) {
           var fieldName = input.name.substring(input.name.indexOf(".") + 1);
           formData.workmenWage[fieldName] = input.value;
       });
       alert(form.action);
       // Send JSON data to server
       var xhr = new XMLHttpRequest();
       alert("Form action: " + form.action);
       xhr.open("POST", form.action, true);
      // xhr.setRequestHeader("Content-Type", "application/json");
       xhr.onreadystatechange = function() {
           if (xhr.readyState === 4) {
               if (xhr.status === 200) {
                   console.log("Update successful");
                   // Redirect to the specified URL
                   // You may need to adjust this function based on your application
                   loadWorkmenWageList(); 
               } else {
                   console.error("Update failed");
                   console.log(xhr.responseText);
               }
           }
       };
       xhr.send(JSON.stringify(formData));
   }*/
   
   
   //Prinipal Employer validations
   function validatePEAddForm() {
    var unitName = document.getElementById('unitName').value;
    var unitCode = document.getElementById('unitCode').value;
    var validationMessages = [];

    // Basic validation
    if (unitName === "") {
        validationMessages.push("Unit Name is required");
    }
    if (unitCode === "") {
        validationMessages.push("Unit Code is required");
    }

    // Additional validation (e.g., email format)
   /* if (email !== "" && !isValidEmail(email)) {
        validationMessages.push("Invalid Email");
    }*/

    // Display validation messages
    if (validationMessages.length > 0) {
        displayValidationMessages(validationMessages);
        return false;
    }

    // Validation passed
    return true;
}
function submitPEAddForm() {
    var form = document.getElementById("addPEForm");
    form.submit();
}
function isValidEmail(email) {
    // Regular expression to check email format
    var regex = /^\S+@\S+\.\S+$/;
    return regex.test(email);
}

function displayValidationMessages(messages) {
    // Concatenate all messages into a single string
    var errorMessage = messages.join("\n");
    
    // Display the error message at the top of the page after the header
    var errorContainer = document.getElementById('errorContainer');
    if (errorContainer) {
        errorContainer.innerText = errorMessage;
    } else {
        alert(errorMessage);
    }
}

/*Minimum wage*/
function loadMinimumwageList(contextPath) {
    

      // Construct the URL using the contextPath variable
      var url = contextPath + '/minimumwage/list';
      console.log("Constructed URL:", url); // Log the constructed URL to the console

      var xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
          if (this.readyState == 4 && this.status == 200) {
              document.getElementById("mainContent").innerHTML = this.responseText;
                resetSessionTimer();
          }
      };
      xhttp.open("GET", url, true);
      xhttp.send();
  }
 function searchWithPEInMinWage(contextPath) {
	 var principalEmployerId = document.getElementById("principalEmployerId").value;
	    var effectiveDate = document.getElementById("effectiveDate").value;
 event.preventDefault();
     // Assuming you have jQuery available for making AJAX requests
     $.ajax({
         type: "GET",
         url: contextPath + "/minimumwage/list",
         data: { principalEmployerId: principalEmployerId, effectiveDate: effectiveDate }, // Pass the search query as data
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
 
 
function loadWorkmenWageList(contextPath) {

      // Construct the URL using the contextPath variable
      var url = contextPath + '/workmenwage/workmenWageList';
      console.log("Constructed URL:", url); // Log the constructed URL to the console
      var xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
          if (this.readyState == 4 && this.status == 200) {
              document.getElementById("mainContent").innerHTML = this.responseText;
                resetSessionTimer();
          }
      };
      xhttp.open("GET", url, true);
      xhttp.send();
  }
 
 function updateWorkmenWages(contextPath) {
	    var form = document.getElementById("updatewageForm");
	    var selectedIds = [];
	    var formData = new FormData(form);

	    // Get all the checkboxes with name 'selectedIds' and collect their values
	    var checkboxes = document.querySelectorAll('input[name="selectedIds"]:checked');
	    checkboxes.forEach(function(checkbox) {
	        selectedIds.push(checkbox.value);
	    });

	    // Check if any checkbox is selected
	    if (selectedIds.length === 0) {
	        alert("Please select at least one item.");
	        return;
	    }

	    var updatedRecords = [];
	    // Loop through each selected ID to fetch updated data
	    selectedIds.forEach(id => {
	        // Fetch updated data from input fields for each ID
	        var updatedData = {
	            WORKMEN_ID: id,
	            INSURANCE_TYPE: document.getElementById('insuranceType_' + id).value,
	            WKMEN_CATOGERY: document.getElementById('workmenType_' + id).value,
	            BASICPAY: document.getElementById('basic_' + id).value,
	            DA: document.getElementById('da_' + id).value,
	            HRA: document.getElementById('hra_' + id).value,
	            Other_Allowances: document.getElementById('otherAllowances_' + id).value,
	            Uniform_Allowance: document.getElementById('uniformAllowances_' + id).value,
	            Washing_allowance: document.getElementById('washingAllowances_' + id).value,
	            Statutory_Bonus: document.getElementById('statutoryBonus_' + id).value,
	            Leave_Encashment: document.getElementById('leaveEncashment_' + id).value,
	            EX_Serviceman_Allowance: document.getElementById('exServicemanAllowance_' + id).value,
	            Supervisor_Allowance: document.getElementById('supervisorAllowance_' + id).value,
	            Hardship_Allowance: document.getElementById('hardshipAllowance_' + id).value,
	            Gunman_Allowance: document.getElementById('gunmanAllowance_' + id).value,
	            Technical_Allowance: document.getElementById('technicalAllowance_' + id).value,
	            Driver_Allowance: document.getElementById('driverAllowance_' + id).value,
	            QRT_Allowance: document.getElementById('qrtAllowance_' + id).value,
	            VALID_FROM: document.getElementById('validFrom_' + id).value,
	            VALID_TO: document.getElementById('validTo_' + id).value,
	            PF_CAP: document.getElementById('pfCap_' + id).value,
	            BonusPayout: document.getElementById('bonusPayout_' + id).value
	            // Add more fields as needed
	        };

	        // Push the updated data to the array
	        updatedRecords.push(updatedData);
	    });

	    // Now you can use the selectedIds array and updatedRecords array to send to the server
	    console.log('Selected IDs:', selectedIds);
	    console.log('Updated Records:', updatedRecords);
	    const requestBody = {
	    	    selectedIds: selectedIds,
	    	    updatedData: updatedRecords
	    	};

	    	// Make sure selectedIds is included in requestBody
	    	if (!requestBody.selectedIds || requestBody.selectedIds.length === 0) {
	    	    console.error('selectedIds are required');
	    	    return; // or handle the error accordingly
	    	}

	    	var url = contextPath + '/workmenwage/update';
	    	// Send updated data to the server using AJAX
	    	fetch(url, {
	    	    method: 'POST',
	    	    headers: {
	    	        'Content-Type': 'application/json'
	    	    },
	    	    body: JSON.stringify(requestBody)
	    	})
	    	.then(response => {
        // Check if the response is successful (status code 200)
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        // Parse the JSON response
        return response.json();
    })
    .then(data => {
        // Handle the data returned from the server
        console.log('Response from server:', data);
        // Optionally, update the UI based on the response
    })
	    	
	    	.catch(error => {
	    	    // Handle network errors or server errors
	    	    console.error('Failed to update records:', error);
	    	    // Provide feedback to the user (e.g., display error message)
	    	});
	}

function loadAadharOnbordingList(contextPath) {

      // Construct the URL using the contextPath variable
      var url = contextPath + '/contractworkmen/aadharOnbordingList';
      console.log("Constructed URL:", url); // Log the constructed URL to the console
      var xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
          if (this.readyState == 4 && this.status == 200) {
              document.getElementById("mainContent").innerHTML = this.responseText;
                resetSessionTimer();
          }
      };
      xhttp.open("GET", url, true);
      xhttp.send();
  }
  
  function loadCommonList(contextPath,path) {

      // Construct the URL using the contextPath variable
      var url = contextPath + path;
      console.log("Constructed URL:", url); // Log the constructed URL to the console
      var xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
          if (this.readyState == 4 && this.status == 200) {
              document.getElementById("mainContent").innerHTML = this.responseText;
                resetSessionTimer();
          }
      };
      xhttp.open("GET", url, true);
      xhttp.send();
  }