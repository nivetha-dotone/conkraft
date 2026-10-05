function getGatePassIds() {
    const reportType=$("#reportType").val();
    const unitId = $("#principalEmployersId").val();
    const contractorId = $("#contractors").val();
    const departmentId = $("#department").val();
    const fromDate = $("#fromDate").val();
    const toDate = $("#toDate").val();
    
    if (!reportType) {
        alert("Please select Report Type");
        return;
    }
    
    if (!unitId) {
        alert("Please select Principal Employer");
        return;
    }

    if (!contractorId) {
        alert("Please select Contractor");
        return;
    }

    if (!departmentId) {
        alert("Please select Department");
        return;
    }

    if (!fromDate) {
        alert("Please select From Date");
        return;
    }

    if (!toDate) {
        alert("Please select To Date");
        return;
    }

    $.ajax({
        url: "/CWFM/reportGenerate/getGatePassIds",
        type: "POST",
        data: {
            reportType:reportType,
            unitId: unitId,
            contractorId: contractorId,
            departmentId: departmentId,
            fromDate: fromDate,
            toDate: toDate
        },
        success: function(response) {

            console.log("GatePass IDs:", response);

            // response contains the GatePass IDs
            // Continue with report generation here.
        },
        error: function(xhr) {
            console.error(xhr.responseText);
            alert("Unable to fetch GatePass IDs");
        }
    });
}
