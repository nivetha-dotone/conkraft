function calculateLinkExpiry() {
    const days = parseInt($("#linkExpiry").val(), 10);

    if (!Number.isInteger(days) || days <= 0) {
        return null;
    }

    const expiryDate = new Date();
    expiryDate.setDate(expiryDate.getDate() + days);

    // LocalDateTime-compatible format
    const localDate = new Date(
        expiryDate.getTime()
        - expiryDate.getTimezoneOffset() * 60000
    );

    return localDate.toISOString().slice(0, 19);
}

 function copyRegistrationLinkBtn () {
    const link = $("#generatedRegistrationLink").val();

    if (link) {
        navigator.clipboard.writeText(link);
    }
}

function generateRegistrationLinkBtn () {

    const expiryDate = calculateLinkExpiry();

    const data = {
        unitId: $("#principalEmployer").val(),
        contractorId: $("#contractor").val(),
        workorderId: $("#workorder").val(),
        departmentId: $("#department").val(),
        areaId: $("#subdepartment").val(),
        wcEsicNo: $("#wc").val(),
		llNo:$("#ll").val(),
        expiryDate: expiryDate
    };

   /* if (Object.values(data).some(value => !value)) {
        alert("Please select all mandatory fields");
        return;
    }*/

    const $button = $(this);
    $button.prop("disabled", true);

    $.ajax({
        url: "/CWFM/contractworkmen/registrationLinks/generate",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),

        success: function (response) {
            $("#generatedRegistrationLink")
                .val(response.registrationUrl);

            $("#generatedLinkSection").show();
        },

        error: function (xhr) {
            alert(
                xhr.responseJSON?.message ||
				
                "Unable to generate registration link"
            );
        },

        complete: function () {
            $button.prop("disabled", false);
        }
    });
}

function redirectToSelfRegistration() {
    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            // Load self-registration page
            document.getElementById("mainContent").innerHTML = xhr.responseText;

            // Initialize dates
            setDateRange();

            // Initialize auto-selects
            initializeAutoSelects();
        }
    };

    xhr.open("GET", "/CWFM/contractworkmen/selfRegisrtationOB", true);
    xhr.send();
}