function fetchData(module) {
    
    let unitId = $('#principalEmployer').val();
    if (!module) return;
	// Get module name (selected option's text)
	  let moduleName = $('#module option:selected').text();
    $.ajax({
        url: '/CWFM/export/fetchModuleData',
        method: 'GET',
        data: {
            module: moduleName.trim(),
            unitId: unitId
        },
        success: function (response) {
            let columns = response.columns;
            let rows = response.rows;
            let headerHtml = '<th><input type="checkbox" id="selectAll" onchange="toggleExportSelectAll()"/></th>';
            columns.forEach(col => { headerHtml += `<th>${col}</th>`; });
            $('#tableHeader').html(headerHtml);

            let bodyHtml = '';
            rows.forEach(row => {
                bodyHtml += '<tr><td><input type="checkbox" class="rowCheckbox" name="selectedUnitIds"  /></td>';
                columns.forEach(col => { bodyHtml += `<td>${row[col]}</td>`; });
                bodyHtml += '</tr>';
            });
            $('#tableBody').html(bodyHtml);

            $('#dynamicTable').show();
            $('#exportBtn').show();
        }
    });
}

/*$(document).on('change', '#selectAll', function () {
    $('.rowCheckbox').prop('checked', this.checked);
});*/

function toggleExportSelectAll() {
           var selectAllCheckbox = document.getElementById('selectAll');
           var checkboxes = document.querySelectorAll('input[name="selectedUnitIds"]');
           checkboxes.forEach(function(checkbox) {
               checkbox.checked = selectAllCheckbox.checked;
           });
       }

	   function exportModuleCSV() {
	       let moduleName = $('#module option:selected').text().trim();

	       // ✅ Only split CSV if module is "Contract Workmen"
	       if (moduleName === "Contract Workmen") {
	           let headers = [];
	           let employmentStatusIndex = -1;

	           // Collect headers and find the index of "Employment Status"
	           $('#dynamicTable thead th').each(function (index) {
	               if (index === 0) return; // Skip checkbox column
	               let headerText = $(this).text().trim();
	               headers.push(headerText);
	               if (headerText === "Employment Status") {
	                   employmentStatusIndex = index - 1;
	               }
	           });

	           if (employmentStatusIndex === -1) {
	               alert("Employment Status column not found.");
	               return;
	           }

	           let activeRows = [];
	           let terminatedRows = [];

	           $('#dynamicTable tbody tr').each(function () {
	               if ($(this).find('.rowCheckbox').is(':checked')) {
	                   let row = [];
	                   $(this).find('td:not(:first-child)').each(function () {
	                       row.push($(this).text().trim());
	                   });

	                   let status = row[employmentStatusIndex];
	                   if (status === "Active") {
	                       activeRows.push(row);
	                   } else if (status === "Terminated") {
	                       terminatedRows.push(row);
	                   }
	               }
	           });

	           if (activeRows.length > 0) {
	               downloadCSV(headers, activeRows, moduleName + "_Active.csv");
	           }

	           if (terminatedRows.length > 0) {
	               downloadCSV(headers, terminatedRows, moduleName + "_Terminated.csv");
	           }

	           if (activeRows.length === 0 && terminatedRows.length === 0) {
	               alert("No selected rows with Employment Status 'Active' or 'Terminated'.");
	           }
	       } else {
	           // ✅ Default single CSV export for all other modules
	           let headers = [];
	           $('#dynamicTable thead th').each(function (index) {
	               if (index === 0) return;
	               headers.push($(this).text().trim());
	           });

	           let rows = [];
	           $('#dynamicTable tbody tr').each(function () {
	               if ($(this).find('.rowCheckbox').is(':checked')) {
	                   let row = [];
	                   $(this).find('td:not(:first-child)').each(function () {
	                       row.push($(this).text().trim());
	                   });
	                   rows.push(row);
	               }
	           });

	           if (rows.length > 0) {
	               downloadCSV(headers, rows, moduleName + ".csv");
	           } else {
	               alert("No rows selected.");
	           }
	       }
	   }

	   function downloadCSV(headers, rows, fileName) {
	       let csv = headers.join(',') + '\n';
	       rows.forEach(row => {
	           csv += row.map(value => `${value}`).join(',') + '\n';
	       });

	       const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
	       const link = document.createElement("a");
	       link.href = URL.createObjectURL(blob);
	       link.download = fileName;
	       link.style.display = "none";
	       document.body.appendChild(link);
	       link.click();
	       document.body.removeChild(link);
	   }

