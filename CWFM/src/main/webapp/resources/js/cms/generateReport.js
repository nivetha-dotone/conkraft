var reportHistoryTable = null;

function initializeReportHistoryTable() {

    /*
     * Prevent DataTable reinitialisation
     */
    if ($.fn.DataTable.isDataTable("#reportHistoryTable")) {
        return $("#reportHistoryTable").DataTable();
    }

    reportHistoryTable = $("#reportHistoryTable").DataTable({

        /*
         * IMPORTANT:
         * Do NOT use scrollY here.
         *
         * This allows all 10 records to be displayed
         * normally at 100% browser zoom.
         */

        paging: true,
        searching: true,
        ordering: true,

        pageLength: 10,
        lengthChange: true,

        autoWidth: false,

        order: [],

        columnDefs: [

            {
                targets: 0,
                orderable: false,
                searchable: false
            },

            {
                targets: 5,
                orderable: false,
                searchable: false
            }
        ],

        language: {

            emptyTable: "No report history found",

            zeroRecords: "No matching report history found",

            search: "Search:",

            lengthMenu: "Show _MENU_ entries",

            info: "Showing _START_ to _END_ of _TOTAL_ entries",

            infoEmpty: "Showing 0 to 0 of 0 entries",

            infoFiltered: "(filtered from _MAX_ total entries)",

            paginate: {

                first: "First",

                last: "Last",

                next: "Next",

                previous: "Previous"
            }
        }
    });

    return reportHistoryTable;
}

 // PAGE LOAD
$(document).ready(function () {

    /*
     * Initialize only once
     */
    initializeReportHistoryTable();


    /*
     * Load existing report history
     */
    loadReportSearchHistory();

});


/* =========================================================
 * GET GATE PASS IDS
 * ========================================================= */
function getGatePassIds() {
    showLoader();
    const reportType = $("#reportType").val();

    const unitId = $("#principalEmployersId").val();

    const contractorId = $("#contractors").val();

    const departmentId = $("#department").val();

    const fromDate = $("#fromDate").val();

    const toDate = $("#toDate").val();


    if (!reportType) {

        alert("Please select Report Type");

       hideLoader();
       return;
    }


    if (!unitId) {

        alert("Please select Principal Employer");

       hideLoader();
       return;
    }


    if (!contractorId) {

        alert("Please select Contractor");

        hideLoader();
        return;
    }


    if (!departmentId) {

        alert("Please select Department");
            hideLoader();
            return;
    }


    if (!fromDate) {

        alert("Please select From Date");
             hideLoader();
             return;
    }


    if (!toDate) {

        alert("Please select To Date");
            hideLoader();
            return;
    }


    $.ajax({

        url: "/CWFM/reportGenerate/getGatePassIds",

        type: "POST",

        data: {

            reportType: reportType,

            unitId: unitId,

            contractorId: contractorId,

            departmentId: departmentId,

            fromDate: fromDate,

            toDate: toDate
        },


        success: function (response) {
            hideLoader();
            console.log("GatePass IDs:", response);


            /*
             * Refresh report history.
             *
             * IMPORTANT:
             * This does NOT reinitialize DataTables.
             */
            loadReportSearchHistory();

        },


        error: function (xhr) {

            console.error(xhr.responseText);
               hideLoader();
            alert("Unable to fetch GatePass IDs");

        }

    });

}


 // LOAD REPORT SEARCH HISTORY
function loadReportSearchHistory() {

    $.ajax({

        url: "/CWFM/reportGenerate/getReportSearchHistory",

        type: "GET",

        dataType: "json",


        success: function (data) {

            console.log(
                "Report History API Response:",
                data
            );


            /*
             * API returns:
             *
             * [
             *   {...},
             *   {...}
             * ]
             */
            if (Array.isArray(data)) {

                renderReportSearchHistory(data);

                return;
            }


            /*
             * API returns:
             *
             * {
             *     data: [...]
             * }
             */
            if (
                data &&
                Array.isArray(data.data)
            ) {

                renderReportSearchHistory(data.data);

                return;
            }


            /*
             * API returns:
             *
             * {
             *     history: [...]
             * }
             */
            if (
                data &&
                Array.isArray(data.history)
            ) {

                renderReportSearchHistory(data.history);

                return;
            }


            /*
             * Unexpected response
             */
            console.error(
                "Unexpected report history response:",
                data
            );


            renderReportSearchHistory([]);

        },


        error: function (xhr, status, error) {

            console.error(
                "Report History API Error"
            );

            console.error(
                "Status:",
                xhr.status
            );

            console.error(
                "Response:",
                xhr.responseText
            );

            console.error(
                "Error:",
                error
            );


            renderReportSearchHistory([]);

        }

    });

}


 // RENDER REPORT SEARCH HISTORY
function renderReportSearchHistory(data) {

    var table = initializeReportHistoryTable();

    /*
     * Remove existing rows
     */
    table.clear();

    /*
     * No records
     */
    if (!Array.isArray(data) || data.length === 0) {

        table.draw();

        return;
    }

    /*
     * Add records
     */
    $.each(data, function (index, report) {

        let status =
            report.reportStatus
                ? String(report.reportStatus).toUpperCase()
                : "";

        let statusClass = "";

        if (status === "SUCCESS") {

            statusClass = "report-status-success";

        } else if (status === "FAILED") {

            statusClass = "report-status-failed";

        } else {

            statusClass = "report-status-processing";
        }


        let downloadButton = "";

        if (status === "SUCCESS") {

            downloadButton =
                '<button type="button" ' +
                'class="download-report-btn" ' +
                'onclick="downloadReport(' +
                Number(report.requestId) +
                ')">' +
                '<i class="fa fa-download"></i> Download' +
                '</button>';

        } else {

            downloadButton =
                '<button type="button" ' +
                'class="download-report-btn" ' +
                'onclick="downloadReport(' +
                Number(report.requestId) +
                ')">' +
                '<i class="fa fa-download"></i> Download' +
                '</button>';
        }


        table.row.add([

            index + 1,

            escapeHtml(report.reportType),

            escapeHtml(report.fromDate),

            escapeHtml(report.toDate),

            '<span class="' +
            statusClass +
            '">' +
            escapeHtml(status) +
            '</span>',

            downloadButton

        ]);

    });

    /*
     * Draw after adding all records
     */
    table.draw();
}


 // ESCAPE HTML
function escapeHtml(value) {

    if (
        value === null ||
        value === undefined
    ) {

        return "";
    }


    return String(value)

        .replace(/&/g, "&amp;")

        .replace(/</g, "&lt;")

        .replace(/>/g, "&gt;")

        .replace(/"/g, "&quot;")

        .replace(/'/g, "&#039;");
}


 // DOWNLOAD REPORT
function downloadReport(requestId) {

    if (!requestId) {
        alert("Invalid report request");
        return;
    }

    // Open the new tab immediately from the click event.
    const reportWindow = window.open("about:blank", "_blank");

    if (!reportWindow) {
        alert("Please allow pop-ups for this application.");
        return;
    }

    reportWindow.document.write(
        "<p style='font-family:Arial;padding:20px'>Loading report...</p>"
    );
    reportWindow.document.close();

    fetch("/CWFM/reportGenerate/viewReport", {
        method: "POST",
        credentials: "same-origin",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
        },
        body: new URLSearchParams({
            requestId: requestId
        })
    })
    .then(function (response) {
        if (!response.ok) {
            throw new Error("Unable to open report. HTTP " + response.status);
        }
        return response.text();
    })
    .then(function (html) {

        // Create a browser-managed Blob URL.
        const blob = new Blob([html], {
            type: "text/html;charset=UTF-8"
        });

        const blobUrl = URL.createObjectURL(blob);

        // Show the report using the Blob URL in the new tab.
        reportWindow.location.href = blobUrl;
    })
    .catch(function (error) {
        reportWindow.document.body.innerHTML =
            "<p style='font-family:Arial;color:red;padding:20px'>" +
            "The status report failed to generate. Please try again.</p>";

        console.error("Report viewer error:", error);
    });
}


function showLoader() {
    document.getElementById("loaderOverlay").style.display = "flex";
}

function hideLoader() {
    document.getElementById("loaderOverlay").style.display = "none";
}