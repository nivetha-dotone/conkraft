
$(document).ready(initializeComplianceAuditPage);

function initializeComplianceAuditPage() {

    $('#aadhaar').on('keydown', handleAadharKeydown);

}

function handleAadharInput() {

    var input = document.getElementById('aadhaar');

    input.value = input.value.replace(/[^0-9]/g, '').slice(0, 12);
}

function handleAadharKeydown(event) {

    if (event.key === 'Enter') {

        event.preventDefault();

        searchComplianceAudit();
    }
}

function searchComplianceAudit() {

    clearComplianceAuditMessage();

    var aadharNumber = $('#aadhaar').val().trim();

    if (!/^\d{12}$/.test(aadharNumber)) {

        showComplianceAuditMessage(
            'Please enter a valid 12-digit Aadhaar number.',
            'error'
        );

        $('#aadhaar').trigger('focus');

        return;
    }

    setSearchButtonLoading(true);

    showComplianceAuditTableMessage(
        'Loading compliance audit details...'
    );

    $.ajax({
        url: window.contextPath + '/complianceAudit/search',
        type: 'POST',
        dataType: 'json',

        data: {
            aadharNumber: aadharNumber
        },

        success: handleComplianceAuditSearchSuccess,

        error: handleComplianceAuditSearchError,

        complete: handleComplianceAuditSearchComplete
    });
}

function handleComplianceAuditSearchSuccess(response) {

    if (!Array.isArray(response)) {

        showComplianceAuditTableMessage(
            'Unexpected response received from the server.'
        );

        showComplianceAuditMessage(
            'Unable to process compliance audit details.',
            'error'
        );

        return;
    }

    renderComplianceAuditResults(response);

    if (response.length > 0) {

        showComplianceAuditMessage(
            response.length + ' compliance audit record(s) found.',
            'success'
        );

    } else {

        showComplianceAuditMessage(
            'No compliance audit records found.',
            'error'
        );
    }
}

function handleComplianceAuditSearchError(xhr) {

    var errorMessage =
        'Unable to retrieve compliance audit details. Please try again.';

    if (xhr.responseJSON && xhr.responseJSON.message) {

        errorMessage = xhr.responseJSON.message;

    } else if (xhr.status === 403) {

        errorMessage =
            'Request denied. Please refresh the page and try again.';
    }

    showComplianceAuditTableMessage(
        'Search could not be completed.'
    );

    showComplianceAuditMessage(errorMessage, 'error');
}

function handleComplianceAuditSearchComplete() {

    setSearchButtonLoading(false);
}

function renderComplianceAuditResults(data) {

    var $tableBody = $('#complianceAuditTableBody');

    $tableBody.empty();

    if (!Array.isArray(data) || data.length === 0) {

        showComplianceAuditTableMessage(
            'No compliance audit records found.'
        );

        return;
    }

    $.each(data, appendComplianceAuditRow);
}

function appendComplianceAuditRow(index, item) {

    var $row = $('<tr>');

    $row.append(createComplianceAuditCell(item.gatePassId));
    $row.append(createComplianceAuditCell(item.actionType));
    $row.append(createComplianceAuditCell(item.disability));
    $row.append(createComplianceAuditCell(item.uanNumber));
    $row.append(createComplianceAuditCell(item.pfNumber));
    $row.append(createComplianceAuditCell(item.bankAccount));
    $row.append(createComplianceAuditCell(item.ifsc));
    $row.append(createComplianceAuditCell(item.fromDate));
    $row.append(createComplianceAuditCell(item.toDate));

    $('#complianceAuditTableBody').append($row);
}

function createComplianceAuditCell(value) {

    if (value === null || value === undefined || value === '') {
        value = '-';
    }

    return $('<td>').text(value);
}

function showComplianceAuditTableMessage(message) {

    var $cell = $('<td>')
        .attr('colspan', 9)
        .addClass('no-records')
        .text(message);

    var $row = $('<tr>').append($cell);

    $('#complianceAuditTableBody')
        .empty()
        .append($row);
}

function showComplianceAuditMessage(message, type) {

    var $message = $('#complianceAuditMessage');

    $message
        .removeClass('compliance-error compliance-success')
        .text(message);

    if (type === 'success') {

        $message.addClass('compliance-success');

    } else {

        $message.addClass('compliance-error');
    }
}

function clearComplianceAuditMessage() {

    $('#complianceAuditMessage')
        .removeClass('compliance-error compliance-success')
        .text('');
}

function setSearchButtonLoading(isLoading) {

    $('#searchComplianceAudit')
        .prop('disabled', isLoading)
        .text(isLoading ? 'Searching...' : 'Search');
}

function clearComplianceAudit() {

    $('#aadhaar').val('');

    clearComplianceAuditMessage();

    showComplianceAuditTableMessage(
        'Enter an Aadhaar number and click Search.'
    );

    $('#aadhaar').trigger('focus');
}
