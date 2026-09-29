 var selectedWorkOrderRows = {};
 
 function loadWOList(contextPath) {
      //  var contextPath = '<%= request.getContextPath() %>'; // This will be evaluated on the server side

        // Construct the URL using the contextPath variable
        var url = contextPath + '/workorders/list';
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
function toggleSelectAllWOS() {

    var table = $('#workorderTable').DataTable();

    var checked = $('#selectAllWOCheckbox').prop('checked');

    table.rows({ search: 'applied' }).every(function () {

        var row = $(this.node());

        var checkbox = row.find('input[name="selectedWorkorderIds"]');

        checkbox.prop('checked', checked);

        var workOrderId = row.find('td:eq(1)').text().trim();

        if (checked) {
            selectedWorkOrderRows[workOrderId] = true;
        } else {
            delete selectedWorkOrderRows[workOrderId];
        }
    });
}
        
 
function redirectToWOView() {
	var principalEmployerId = document.getElementById("principalEmployerIds").value;
	 var contractorId = document.getElementById("contractorIds").value;
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckboxes.length !== 1) {
        alert("Please select exactly one row to view.");
        return;
    }
    
    var selectedRow = selectedCheckboxes[0].closest('tr');
    
    var unitId = selectedRow.querySelector('[name="selectedWorkorderIds"]').value;
    
    // Construct the URL with unit ID, principal employer ID, and contractor ID as query parameters
    var url = "/CWFM/workorders/view/" + unitId + "?principalEmployerId=" + principalEmployerId + "&contractorId=" + contractorId;
    
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the main content section with the response
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", url, true);
    xhr.send();
}
function goBackToWOList(contextPath) {
	 var principalEmployerId = document.getElementById("principalEmployerId").value;
	 var contractorId = document.getElementById("contractorId").value;
    // Construct the URL with principal employer ID and contractor ID as query parameters
    var url = contextPath + "/workorders/list?principalEmployerId=" + principalEmployerId + "&contractorId=" + contractorId;
    
    $.ajax({
        type: "GET",
        url: url,
        success: function(response) {
            // Update the main content section with the response
            document.getElementById("mainContent").innerHTML = response;
        },
        error: function(xhr, status, error) {
            console.error("Error loading work order list page:", error);
            // Handle error if needed
        }
    });
}

function searchWithPEContractorInWO(contextPath) {
	 var principalEmployerId = document.getElementById("principalEmployerId").value;
	 var contractorId = document.getElementById("contractorId").value;
 event.preventDefault();
     // Assuming you have jQuery available for making AJAX requests
     $.ajax({
         type: "GET",
         url: contextPath + "/workorders/list",
         data: { principalEmployerId: principalEmployerId,contractorId:contractorId}, // Pass the search query as data
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
 
function woListExportToCSV() {

    var table = $('#workorderTable').DataTable();

    var csvContent = "data:text/csv;charset=utf-8,";
    csvContent += "WORKORDERID,WORKORDER NUMBER,JOB,WORKORDER TYPE,AREA,VALID FROM,VALID TO,CONTRACTOR NAME,VENDOR CODE,UNIT NAME,STATUS\n";

    var selectedCount = 0;

    table.rows().every(function () {

        var row = $(this.node());

        var workOrderId = row.find('td:eq(1)').text().trim();

        if (selectedWorkOrderRows[workOrderId]) {

            selectedCount++;

            var rowData = [];

            row.find('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5),td:nth-child(6),td:nth-child(7),td:nth-child(8),td:nth-child(9),td:nth-child(10),td:nth-child(11),td:nth-child(12)')
               .each(function () {
                   rowData.push($(this).text().trim());
               });

            csvContent += rowData.join(",") + "\n";
        }
    });

    if (selectedCount === 0) {
        alert("Please select at least one record to export.");
        return;
    }

    var encodedUri = encodeURI(csvContent);

    var link = document.createElement("a");

    link.setAttribute("href", encodedUri);
    link.setAttribute("download", "WorkOrderList.csv");

    document.body.appendChild(link);

    link.click();

    document.body.removeChild(link);
}
		
		function searchWorkordersBasedOnPEAndContr() {
					    var principalEmployerId = $('#principalEmployerIds').val();
					    var contractorId = $("#contractorIds").val();

					    $.ajax({
					        url: '/CWFM/workorders/getAllWorkordersBasedOnPEAndContractor',
					        type: 'POST',
					        data: {
					            principalEmployerId: principalEmployerId,
								contractorId:contractorId
					        },
					        success: function(response) {
					            var tableBody = $('#workorderTable tbody');
								   if ($.fn.DataTable.isDataTable('#workorderTable')) {
									$('#workorderTable').DataTable().destroy();
								}
					            tableBody.empty();
								if (response.woList && response.woList.length > 0) {
								               $.each(response.woList, function(index, wo) {
								                   var row = '<tr >' +
								                       '<td ><input type="checkbox" name="selectedWorkorderIds" value="' + wo.workorderId + '"></td>' +
													   '<td >' + wo.workorderId + '</td>' +
													   '<td >' + wo.sapWorkorderNumber + '</td>' +
								                       '<td >' + wo.job + '</td>' +
								                       '<td >' + wo.typeId + '</td>' +
								                       '<td >' + wo.secId + '</td>' +
								                       '<td >' + wo.validFrom + '</td>' +
								                       '<td >' + wo.validTo + '</td>' +
								                       '<td >' + response.contractor.contractorName + '</td>' +
													   '<td >' + response.contractor.contractorCode + '</td>' +
								                       '<td >' + response.principalEmployer.name + '</td>' +
								                       '<td >' + wo.status + '</td>' +
								                     
								                       '</tr>';
								                   tableBody.append(row);
								               });
					            }										   // ✅ Always init after rows are drawn
										   initWorkmenTable("workorderTable");
					        },
					        error: function(xhr, status, error) {
					            console.error("Error fetching data:", error);
					        }
					    });
					}
        
					function getContractorsForWorkorder(unitId, userAccount, callback) {
					    $.ajax({
					        url:"/CWFM/contractworkmen/getAllContractors",
					        type: "GET",
					        data: {
					            unitId: unitId,
					            userAccount: userAccount
					        },
					        success: function (contractors) {

					            const $contractor = $("#contractorIds");
					            $contractor.empty();
					            $contractor.append('<option value="">Select Contractor</option>');

					            $.each(contractors, function (index, contractor) {
					                $contractor.append(
					                    '<option value="' + contractor.contractorId + '">' +
					                    contractor.contractorName +
					                    '</option>'
					                );
					            });

					            if (typeof callback === "function") {
					                callback();
					            }
					        },
					        error: function () {
					            console.error("Error loading contractors");
					        }
					    });
					}

$(document).on('change', 'input[name="selectedWorkorderIds"]', function () {

    var row = $(this).closest('tr');

    var workOrderId = row.find('td:eq(1)').text().trim();

    if ($(this).is(':checked')) {
        selectedWorkOrderRows[workOrderId] = true;
    } else {
        delete selectedWorkOrderRows[workOrderId];
    }
});
$('#workorderTable').on('draw.dt', function () {

    var table = $('#workorderTable').DataTable();

    table.rows({ page: 'current' }).every(function () {

        var row = $(this.node());

        var workOrderId = row.find('td:eq(1)').text().trim();

        row.find('input[name="selectedWorkorderIds"]')
            .prop('checked', selectedWorkOrderRows[workOrderId] === true);
    });
});