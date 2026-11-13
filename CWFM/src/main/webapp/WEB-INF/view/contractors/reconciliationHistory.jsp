<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reconciliation History</title>
<style>
.recon-history { background:#fff; font-family:'Noto Sans',sans-serif; }
.recon-history .page-header { display:flex; align-items:center; justify-content:space-between; padding:8px; background:#fff; border-bottom:1px solid #ccc; gap:10px; flex-wrap:wrap; }
.recon-history .history-filters { display:flex; align-items:center; flex-wrap:wrap; gap:10px; }
.recon-history .history-filters label { color:darkcyan; margin:0; font-size:13px; }
.recon-history .history-filters select { color:gray; padding:3px; max-width:190px; font-size:13px; }
.recon-history .table-container { overflow-x:auto; margin:0; padding:0; }
.recon-history table { width:100%; border-collapse:collapse; }
.recon-history table th { border:1px solid #ddd; background:#DDF3FF; color:#005151; font-family:'Noto Sans',sans-serif; font-size:.75rem; font-weight:bold; text-align:center; padding:6px; white-space:nowrap; }
.recon-history table td { border:1px solid #ddd; color:#898989; font-family:'Noto Sans',sans-serif; font-size:12px; padding:6px; white-space:nowrap; }
.recon-history .transaction-link { color:#007b9a; text-decoration:underline; cursor:pointer; }
.recon-history .history-message { padding:8px; font-size:13px; }
.recon-history .modal-content { font-family:'Noto Sans',sans-serif; }
.recon-history .modal-header { background:#005151; color:white; }
.recon-history .modal-title { font-size:16px; }
.recon-history .recon-detail-label { color:#005151; font-weight:600; }
.recon-history .recon-section-title { color:#005151; font-size:14px; font-weight:600; margin:12px 0; }
.recon-history .history-loading { display:none; padding:12px; text-align:center; color:#005151; }
@media(max-width:768px) { .recon-history .page-header { align-items:flex-start; } .recon-history .history-filters { width:100%; } }
</style>
</head>
<body>
<div class="recon-history" id="reconHistoryRoot">
  <div class="page-header">
    <div class="history-filters">
      <label for="historyPE">Principal Employer:</label>
      <select id="historyPE" disabled><option value="">All Principal Employers</option></select>
      <label for="historyContractor">Contractor:</label>
      <select id="historyContractor"><option value="">All Contractors</option></select>
      <label for="historyReconType">Reconciliation Type:</label>
      <select id="historyReconType">
        <option value="">All Types</option><option value="BANK">Bank Wage</option><option value="PF">PF</option><option value="ESIC">ESIC</option>
      </select>
      <label for="historyStatus">Status:</label>
      <select id="historyStatus"><option value="">All Statuses</option></select>
      <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" id="historySearchBtn">Search</button>
    </div>
  </div>
  <div id="historyMessage" class="history-message" role="alert" style="display:none;"></div>
  <div id="historyLoading" class="history-loading">Please wait...</div>
  <div class="table-container">
    <table id="reconciliationHistoryTable" cellspacing="0" cellpadding="0">
      <thead><tr>
        <th>Upload ID</th><th>Type</th><th>Principal Employer</th><th>Contractor</th>
        <th>Period</th><th>Total Workmen</th><th>Verified</th><th>Unverified</th>
        <th>Rating</th><th>Status</th><th>Uploaded By</th><th>Uploaded Date</th>
      </tr></thead>
      <tbody></tbody>
    </table>
  </div>
  
<div class="modal fade" id="reconHistoryDetailsModal"
     tabindex="-1" aria-hidden="true">

    <div class="modal-dialog modal-xl modal-dialog-scrollable">
        <div class="modal-content">

            <div class="modal-header"
                 style="background:#005151;color:white;">
                <h5 class="modal-title">
                    Reconciliation Details
                    <span id="reconDetailsTitle"></span>
                </h5>
                <button type="button" class="btn-close btn-close-white recon-modal-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"></button>
            </div>

            <div class="modal-body">

                <div class="row mb-3">
                    <div class="col-md-3">
                        <strong>Upload ID:</strong>
                        <span id="detailUploadId"></span>
                    </div>
                    <div class="col-md-3">
                        <strong>Type:</strong>
                        <span id="detailReconType"></span>
                    </div>
                    <div class="col-md-3">
                        <strong>Contractor:</strong>
                        <span id="detailContractor"></span>
                    </div>
                    <div class="col-md-3">
                        <strong>Status:</strong>
                        <span id="detailStatus"></span>
                    </div>
                </div>

                <h6 style="color:#005151;">Workmen Compliance Scoreboard</h6>

                <div class="table-responsive">
                    <table id="reconSavedDetailsTable"
                           class="table table-bordered table-striped"
                           style="width:100%">
                        <thead style="background:#DDF3FF;color:#005151;">
                            <tr>
                                <th>GatePass ID</th>
                                <th>Workmen Name</th>
                                <th>Expected</th>
                                <th>Paid / Challan</th>
                                <th>Difference</th>
                                <th>Payment %</th>
                                <th>Rating</th>
                                <th>Compliance Status</th>
                                <th>Timeliness</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>

                <div id="reconMismatchSection" class="mt-4"
                     style="display:none;">

                    <h6 style="color:#005151;">Mismatch Details</h6>

                    <div class="table-responsive">
                        <table id="reconSavedMismatchTable"
                               class="table table-bordered table-striped"
                               style="width:100%">
                            <thead style="background:#DDF3FF;color:#005151;">
                                <tr>
                                    <th>GatePass ID</th>
                                    <th>Workmen Name</th>
                                    <th>DB Number</th>
                                    <th>Document Number</th>
                                    <th>DB Amount</th>
                                    <th>Document Amount</th>
                                    <th>Mismatch Reason</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>

            </div>

            <div class="modal-footer">
                <button type="button"
                        class="process-footer-button-cancel recon-modal-close"
                        data-bs-dismiss="modal">
                    Close
                </button>
            </div>

        </div>
    </div>
</div>
  
</div>
<script>
(function () {
    'use strict';
    var root = $('#reconHistoryRoot');
    var historyRows = [];
    var historyTable;
    var detailsTable;
    var mismatchTable;
    var historyUrl = '<c:url value="/contractor/reconciliation/history"/>';
    var detailsUrl = '<c:url value="/contractor/reconciliation/history/details"/>';
    var modal = root.find('#reconHistoryDetailsModal');

    function escapeHtml(value) {
        return $('<div>').text(value == null ? '' : String(value)).html();
    }
    function showMessage(message) {
        root.find('#historyMessage').text(message).show();
    }
    function hideMessage() {
        root.find('#historyMessage').hide().text('');
    }
    function displayDate(value) {
        if (!value) return '-';
        if (Array.isArray(value)) {
            var pad = function(n) { return String(n || 0).padStart(2, '0'); };
            return pad(value[2]) + '/' + pad(value[1]) + '/' + value[0] +
                ' ' + pad(value[3]) + ':' + pad(value[4]);
        }
        var s = String(value).replace('T', ' ');
        // Supports yyyy-MM-dd HH:mm:ss and dd/MM/yyyy HH:mm:ss.
        var match = s.match(/^(\d{4})-(\d{2})-(\d{2})(.*)$/);
        return match ? match[3] + '/' + match[2] + '/' + match[1] + (match[4] || '') : s;
    }
    function sortDate(value) {
        if (!value) return '';
        if (Array.isArray(value)) {
            return String(value[0]).padStart(4,'0') + '-' + String(value[1]).padStart(2,'0') + '-' +
                String(value[2]).padStart(2,'0') + ' ' + String(value[3] || 0).padStart(2,'0') + ':' +
                String(value[4] || 0).padStart(2,'0') + ':' + String(value[5] || 0).padStart(2,'0');
        }
        var s = String(value);
        var m = s.match(/^(\d{2})\/(\d{2})\/(\d{4})(.*)$/);
        return m ? m[3] + '-' + m[2] + '-' + m[1] + (m[4] || '') : s;
    }
    function amount(value, type) {
        if (value == null || value === '') return '-';
        if (type && type !== 'display') return Number(value);
        var n = Number(value);
        return Number.isFinite(n) ? n.toFixed(2) : '-';
    }
    function valueOrDash(value) {
        return value == null || value === '' ? '-' : escapeHtml(value);
    }
    function populateFilters(rows) {
        var contractor = root.find('#historyContractor');
        var pe = root.find('#historyPE');
        var status = root.find('#historyStatus');
        contractor.empty().append($('<option>', {value:'', text:'All Contractors'}));
        pe.empty().append($('<option>', {value:'', text:'All Principal Employers'}));
        status.empty().append($('<option>', {value:'', text:'All Statuses'}));
        var contractorIds = {}, peIds = {}, statuses = {};
        rows.forEach(function(r) {
            if (r.contractorId != null) contractorIds[String(r.contractorId)] = true;
            if (r.principalEmployerId != null) peIds[String(r.principalEmployerId)] = true;
            if (r.overallStatus) statuses[String(r.overallStatus)] = true;
        });
        Object.keys(contractorIds).sort().forEach(function(id) {
            contractor.append($('<option>', {value:id, text:id}));
        });
        Object.keys(peIds).sort().forEach(function(id) {
            pe.append($('<option>', {value:id, text:id}));
        });
        Object.keys(statuses).sort().forEach(function(s) {
            status.append($('<option>', {value:s, text:s}));
        });
        // PF/ESIC master records do not currently contain PrincipalEmployerId.
        pe.prop('disabled', true);
    }
    function filteredRows() {
        var contractor = root.find('#historyContractor').val();
        var reconType = root.find('#historyReconType').val();
        var status = root.find('#historyStatus').val();
        return historyRows.filter(function(r) {
            return (!contractor || String(r.contractorId) === contractor)
                && (!reconType || r.reconType === reconType)
                && (!status || r.overallStatus === status);
        });
    }
    function render() {
        historyTable.clear().rows.add(filteredRows()).draw();
    }
    function initHistoryTable() {
        var table = root.find('#reconciliationHistoryTable');
        if ($.fn.DataTable.isDataTable(table[0])) table.DataTable().destroy();
        historyTable = table.DataTable({
            data: [], pageLength:10, autoWidth:false, scrollX:true, order:[[11,'desc']],
            columns: [
                {data:'uploadId', render:function(v,t,r) {
                    if (t !== 'display') return v;
                    return '<a href="#" class="transaction-link recon-history-detail" data-upload-id="' +
                        escapeHtml(v) + '" data-recon-type="' + escapeHtml(r.reconType) + '">' + escapeHtml(v) + '</a>';
                }},
                {data:'reconType', render:valueOrDash},
                {data:'principalEmployerId', render:valueOrDash},
                {data:'contractorId', render:valueOrDash},
                {data:'reconPeriod', render:valueOrDash},
                {data:'totalWorkmen', render:valueOrDash},
                {data:'verifiedCount', render:valueOrDash},
                {data:'unverifiedCount', render:valueOrDash},
                {data:'contractorRating', render:function(v,t) { return v == null ? '-' : amount(v,t); }},
                {data:'overallStatus', render:valueOrDash},
                {data:'uploadedBy', render:valueOrDash},
                {data:'uploadedDate', render:function(v,t) { return t === 'display' ? escapeHtml(displayDate(v)) : sortDate(v); }}
            ],
            language:{emptyTable:'No reconciliation history found'}
        });
    }
    function loadHistory() {
        hideMessage();
        root.find('#historyLoading').show();
        $.ajax({
            url:historyUrl, type:'GET', dataType:'json', cache:false,
            success:function(response) {
                if (!response || response.status !== 'SUCCESS') {
                    showMessage(response && response.message ? response.message : 'Unable to load reconciliation history.');
                    return;
                }
                historyRows = response.data || [];
                populateFilters(historyRows);
                render();
            },
            error:function(xhr) {
                showMessage(xhr.status === 401 ? 'Session expired. Please log in again.' : 'Error loading reconciliation history.');
            },
            complete:function() { root.find('#historyLoading').hide(); }
        });
    }
    function initOrRefreshTable(selector, oldTable, rows, columns, emptyMessage) {
        var table = root.find(selector);
        if ($.fn.DataTable.isDataTable(table[0])) table.DataTable().destroy();
        table.find('tbody').empty();
        return table.DataTable({
            data:rows, columns:columns, pageLength:10, scrollX:true, autoWidth:false,
            language:{emptyTable:emptyMessage}
        });
    }
    function populateDetails(rows, reconType) {
        var isBank = reconType === 'BANK';
        detailsTable = initOrRefreshTable('#reconSavedDetailsTable', detailsTable, rows, [
            {data:'gatePassId', defaultContent:'-', render:valueOrDash},
            {data:'workmenName', defaultContent:'-', render:valueOrDash},
            {data:'expectedAmount', render:amount},
            {data:isBank ? 'paidAmount' : 'challanAmount', defaultContent:null, render:amount},
            {data:'differenceAmount', render:amount},
            {data:'paymentPercentage', render:function(v,t) {return v == null ? '-' : amount(v,t) + (t === 'display' ? '%' : '');}},
            {data:'rating', render:valueOrDash},
            {data:isBank ? 'amountStatus' : 'complianceStatus', defaultContent:'-', render:valueOrDash},
            {data:isBank ? 'timelinessStatus' : null, defaultContent:'-', render:function(v) {return isBank ? valueOrDash(v) : 'N/A';}}
        ], 'No saved workmen compliance details found for this upload.');
    }
    function populateMismatches(rows, reconType) {
        var show = reconType !== 'BANK' && rows.length > 0;
        root.find('#reconMismatchSection').toggle(show);
        if (!show) {
            var t = root.find('#reconSavedMismatchTable');
            if ($.fn.DataTable.isDataTable(t[0])) t.DataTable().destroy();
            t.find('tbody').empty();
            return;
        }
        mismatchTable = initOrRefreshTable('#reconSavedMismatchTable', mismatchTable, rows, [
            {data:'gatePassId', render:valueOrDash},
            {data:'workmenName', render:valueOrDash},
            {data:'dbNumber', render:valueOrDash},
            {data:'docNumber', render:valueOrDash},
            {data:'dbAmount', render:amount},
            {data:'docAmount', render:amount},
            {data:'mismatchReason', render:valueOrDash}
        ], 'No mismatches found.');
    }
    function showModal() {
        var el = modal[0];
        if (window.bootstrap && bootstrap.Modal) {
            bootstrap.Modal.getOrCreateInstance(el).show();
        } else if ($.fn.modal) {
            modal.modal('show');
        } else {
            showMessage('Bootstrap modal JavaScript is not loaded.');
        }
    }
    function closeModal() {
        if (window.bootstrap && bootstrap.Modal) {
            var instance = bootstrap.Modal.getInstance(modal[0]);
            if (instance) instance.hide();
        } else if ($.fn.modal) {
            modal.modal('hide');
        }
    }
    function openDetails(uploadId, reconType) {
        hideMessage();
        root.find('#historyLoading').show();
        $.ajax({
            url:detailsUrl, type:'GET', dataType:'json',
            data:{uploadId:uploadId, reconType:reconType},
            success:function(response) {
                if (!response || response.status !== 'SUCCESS') {
                    showMessage(response && response.message ? response.message : 'Unable to load reconciliation details.');
                    return;
                }
                var record = historyRows.find(function(r) {
                    return String(r.uploadId) === String(uploadId) && r.reconType === reconType;
                });
                root.find('#reconDetailsTitle').text(' - ' + reconType + ' #' + uploadId);
                root.find('#detailUploadId').text(uploadId);
                root.find('#detailReconType').text(reconType);
                root.find('#detailContractor').text(record ? record.contractorId : '-');
                root.find('#detailStatus').text(record ? record.overallStatus : '-');
                populateDetails(response.details || [], reconType);
                populateMismatches(response.mismatches || [], reconType);
                showModal();
            },
            error:function(xhr) {
                if (xhr.status === 401) showMessage('Session expired. Please log in again.');
                else if (xhr.status === 403) showMessage('You are not authorized to view this reconciliation.');
                else showMessage('Unable to load reconciliation details.');
            },
            complete:function() { root.find('#historyLoading').hide(); }
        });
    }
    root.off('click.reconHistory', '#historySearchBtn').on('click.reconHistory', '#historySearchBtn', render);
    root.off('click.reconHistory', '.recon-history-detail').on('click.reconHistory', '.recon-history-detail', function(e) {
        e.preventDefault();
        openDetails($(this).attr('data-upload-id'), $(this).attr('data-recon-type'));
    });
    root.off('click.reconHistory', '.recon-modal-close').on('click.reconHistory', '.recon-modal-close', closeModal);
    modal.off('shown.bs.modal.reconHistory').on('shown.bs.modal.reconHistory', function() {
        if (detailsTable) detailsTable.columns.adjust();
        if (mismatchTable && root.find('#reconMismatchSection').is(':visible')) mismatchTable.columns.adjust();
    });
    initHistoryTable();
    loadHistory();
})();
</script>
</body>
</html>
