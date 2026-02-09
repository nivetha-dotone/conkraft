 function loadReportsList(contextPath) {

      // Construct the URL using the contextPath variable
      var url = contextPath + '/reportSetup/list';
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
   function loadRunReport(contextPath) {

      // Construct the URL using the contextPath variable
      var url = contextPath + '/reportSetup/getReports';
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
  
  
function add() {
    var availableOptions = document.getElementById("availableOptions");
    var selectedOptions = document.getElementById("selectedOptions");

    var selectedIndices = [];
    for (var i = 0; i < availableOptions.options.length; i++) {
        if (availableOptions.options[i].selected) {
            selectedIndices.push(i);
        }
    }

    // Add options to the selected box
    for (var i = selectedIndices.length - 1; i >= 0; i--) {
        var option = availableOptions.options[selectedIndices[i]];
        availableOptions.remove(selectedIndices[i]);
        selectedOptions.add(option);
    }
}

function remove() {
    var availableOptions = document.getElementById("availableOptions");
    var selectedOptions = document.getElementById("selectedOptions");

    var selectedIndices = [];
    for (var i = 0; i < selectedOptions.options.length; i++) {
        if (selectedOptions.options[i].selected) {
            selectedIndices.push(i);
        }
    }

    // Remove options from the selected box
    for (var i = selectedIndices.length - 1; i >= 0; i--) {
        var option = selectedOptions.options[selectedIndices[i]];
        selectedOptions.remove(selectedIndices[i]);
        availableOptions.add(option);
    }
}
function addSelected() {
    var availableOptions = document.getElementById("availableOptions");
    var selectedOptions = document.getElementById("selectedOptions");

    var selectedIndices = [];
    for (var i = 0; i < availableOptions.options.length; i++) {
        if (availableOptions.options[i].selected) {
            selectedIndices.push(i);
        }
    }

    // Add options to the selected box
    for (var i = selectedIndices.length - 1; i >= 0; i--) {
        var option = availableOptions.options[selectedIndices[i]];
        availableOptions.remove(selectedIndices[i]);
        selectedOptions.add(option);
    }
}

function removeSelected() {
    var availableOptions = document.getElementById("availableOptions");
    var selectedOptions = document.getElementById("selectedOptions");

    var selectedIndices = [];
    for (var i = 0; i < selectedOptions.options.length; i++) {
        if (selectedOptions.options[i].selected) {
            selectedIndices.push(i);
        }
    }

    // Remove options from the selected box
    for (var i = selectedIndices.length - 1; i >= 0; i--) {
        var option = selectedOptions.options[selectedIndices[i]];
        selectedOptions.remove(selectedIndices[i]);
        availableOptions.add(option);
    }
}

function addAll() {
    var availableOptions = document.getElementById("availableOptions");
    var selectedOptions = document.getElementById("selectedOptions");

    while (availableOptions.options.length > 0) {
        var option = availableOptions.options[0];
        availableOptions.remove(0);
        selectedOptions.add(option);
    }
}

function removeAll() {
    var availableOptions = document.getElementById("availableOptions");
    var selectedOptions = document.getElementById("selectedOptions");

    while (selectedOptions.options.length > 0) {
        var option = selectedOptions.options[0];
        selectedOptions.remove(0);
        availableOptions.add(option);
    }
}
function redirectToReportAdd() {

    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/reportSetup/add", true);
    xhr.send();
}

/*function loadReport(reportName) {
    // Make an AJAX request to the server to fetch report data
    $.ajax({
        type: "GET",
        url: "/CWFM/reportSetup/reportPreferences",
        data: { reportName: reportName },
        success: function(response) {
            // Update HTML elements with the received report data
           // $('#reportData').html(response);
            document.getElementById("mainContent").innerHTML = response;
        },
        error: function(xhr, status, error) {
            console.error("Error loading report:", error);
        }
    });
}*/

function submitReportForm() {
    var form = document.getElementById("addReportForm");
    var formData = new FormData(form);

    // Send the form data asynchronously using AJAX
    var xhr = new XMLHttpRequest();
    xhr.open("POST", form.action, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Check if the response indicates success or error
                var response = xhr.responseText;
                if (response.includes("redirect:/reportSetup/list")) {
                    // Redirect to the list page if the response contains the redirect URL
                    window.location.href = "/reportSetup/list";
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
   function addSelected() {
            $('#availableOptions option:selected').appendTo('#selectedOptions');
        }
        
        function addAll() {
            $('#availableOptions option').appendTo('#selectedOptions');
        }
        
        function removeSelected() {
            $('#selectedOptions option:selected').appendTo('#availableOptions');
        }
        
        function removeAll() {
            $('#selectedOptions option').appendTo('#availableOptions');
        }

function toggleReportsSubMenu() {
    var submenu = document.getElementById("reports-submenu");
    if (submenu.style.display === "none") {
        submenu.style.display = "block";
    } else {
        submenu.style.display = "none";
    }
}
/*function submitReportForm() {
    var form = document.getElementById("addReportForm");
    var formData = new FormData(form);

    // Send the form data asynchronously using AJAX
    var xhr = new XMLHttpRequest();
    xhr.open("POST", form.action, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Check if the response indicates success or error
                var response = xhr.responseText;
                if (response.includes("redirect:/reports/list")) {
                    // Redirect to the list page if the response contains the redirect URL
                    document.getElementById("mainContent").innerHTML ="/reports/list";
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
}*/