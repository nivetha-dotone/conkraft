<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.recon-view { background:#fff; font-family:'Noto Sans',sans-serif; padding:0 8px 20px; }
.recon-view .page-header { display:flex; justify-content:space-between; align-items:center; gap:12px; padding:10px 6px; border-bottom:1px solid #ccc; flex-wrap:wrap; }
.recon-view .page-title { font-size:16px; font-weight:600; color:#005151; margin:0; }
.recon-view .recon-meta { display:grid; grid-template-columns:repeat(4,minmax(150px,1fr)); gap:12px; padding:14px 6px; }
.recon-view .recon-meta-item { border:1px solid #e1e1e1; padding:8px 10px; min-width:0; }
.recon-view .recon-meta-label { color:darkcyan; font-size:12px; margin-bottom:4px; }
.recon-view .recon-meta-value { color:#555; font-size:13px; overflow-wrap:anywhere; }
.recon-view .section-title { color:#005151; font-weight:600; font-size:14px; margin:18px 6px 8px; }
.recon-view .table-container { width:100%; overflow-x:auto; }
.recon-view .table-container .dataTables_wrapper { width:100%; }
.recon-view .table-container .dataTables_scroll { width:100%; }
.recon-view .table-container .dataTables_scrollHeadInner,
.recon-view .table-container .dataTables_scrollHeadInner table { width:100% !important; }
.recon-view .table-container table.dataTable { width:100% !important; margin:0 !important; }
.recon-view .table-container table.dataTable thead th,
.recon-view .table-container table.dataTable tbody td { box-sizing:border-box; }
.recon-view table { width:100%; border-collapse:collapse; }
.recon-view table th { background:#DDF3FF; color:#005151; border:1px solid #ddd; text-align:center; font-size:.75rem; font-weight:bold; padding:6px; white-space:nowrap; }
.recon-view table td { border:1px solid #ddd; color:#777; font-size:12px; padding:6px; white-space:nowrap; }
.recon-view .recon-message { padding:10px 6px; font-size:13px; color:#a22; }
.recon-view .recon-loading { padding:10px 6px; color:#005151; }
@media(max-width:800px) { .recon-view .recon-meta { grid-template-columns:repeat(2,minmax(130px,1fr)); } }
</style>
<div class="recon-view" id="reconDetailsRoot" data-upload-id="${uploadId}" data-recon-type="${reconType}">
  <div class="page-header">
    <h4 class="page-title">Reconciliation Details <span id="reconDetailsTitle"></span></h4>
    <button type="button" id="reconBackBtn" class="btn btn-default process-footer-button-cancel ng-binding">Back to History</button>
  </div>
  <div id="reconDetailsMessage" class="recon-message" style="display:none;" role="alert"></div>
  <div id="reconDetailsLoading" class="recon-loading">Loading reconciliation details...</div>
  <div id="reconDetailsContent" style="display:none;">
    <div class="recon-meta">
      <div class="recon-meta-item"><div class="recon-meta-label">Upload ID</div><div class="recon-meta-value" id="detailUploadId"></div></div>
      <div class="recon-meta-item"><div class="recon-meta-label">Type</div><div class="recon-meta-value" id="detailReconType"></div></div>
      <div class="recon-meta-item"><div class="recon-meta-label">Contractor</div><div class="recon-meta-value" id="detailContractor"></div></div>
      <div class="recon-meta-item"><div class="recon-meta-label">Status</div><div class="recon-meta-value" id="detailStatus"></div></div>
    </div>
    <h5 class="section-title">Workmen Compliance Scoreboard</h5>
    <div class="table-container">
      <table id="reconSavedDetailsTable" class="display" style="width:100%">
        <thead><tr><th>GatePass ID</th><th>Workmen Name</th><th>Expected</th><th>Paid / Challan</th><th>Difference</th><th>Payment %</th><th>Rating</th><th>Compliance Status</th><th>Timeliness</th></tr></thead>
        <tbody></tbody>
      </table>
    </div>
    <div id="reconMismatchSection" style="display:none;">
      <h5 class="section-title">Mismatch Details</h5>
      <div class="table-container">
        <table id="reconSavedMismatchTable" class="display" style="width:100%">
          <thead><tr><th>GatePass ID</th><th>Workmen Name</th><th>DB Number</th><th>Document Number</th><th>DB Amount</th><th>Document Amount</th><th>Mismatch Reason</th></tr></thead>
          <tbody></tbody>
        </table>
      </div>
    </div>
  </div>
</div>
<script>
window.initReconciliationDetailsPage = function(selectedUploadId, selectedReconType) {
    'use strict';
    var root = $('#reconDetailsRoot');
    if (root.data('reconInitialized')) return;
    root.data('reconInitialized', true);
    var detailsUrl = '<c:url value="/contractor/reconciliation/history/details"/>';
    var historyPageUrl = '<c:url value="/contractor/reconciliation/list"/>';
    var historyDataUrl = '<c:url value="/contractor/reconciliation/history"/>';
    // AJAX-loaded JSPs do not change window.location; use arguments from the listing.
    var uploadId = selectedUploadId != null ? String(selectedUploadId) : String($('#reconDetailsRoot').attr('data-upload-id') || '');
    var reconType = String(selectedReconType || $('#reconDetailsRoot').attr('data-recon-type') || '').toUpperCase();
    var detailsTable = null, mismatchTable = null;

    function escapeHtml(v) { return $('<div>').text(v == null ? '' : String(v)).html(); }
    function valueOrDash(v) { return v == null || v === '' ? '-' : escapeHtml(v); }
    function amount(v, type) {
        if (v == null || v === '') return '-';
        var n = Number(v);
        if (!Number.isFinite(n)) return '-';
        return type === 'display' ? n.toFixed(2) : n;
    }
    function message(text) { root.find('#reconDetailsMessage').text(text).show(); }
    function setTable(selector, rows, columns, emptyText) {
        var table = root.find(selector);
        if ($.fn.DataTable.isDataTable(table[0])) table.DataTable().destroy();
        table.find('tbody').empty();
        return table.DataTable({
            data:rows, columns:columns, pageLength:10, scrollX:false,
            autoWidth:true, destroy:true,
            initComplete:function() {
                var api = this.api();
                requestAnimationFrame(function() { api.columns.adjust(); });
            },
            language:{emptyTable:emptyText}
        });
    }
    function renderDetails(rows) {
        var bank = reconType === 'BANK';
        detailsTable = setTable('#reconSavedDetailsTable', rows, [
            {data:'gatePassId', defaultContent:'-', render:valueOrDash},
            {data:'workmenName', defaultContent:'-', render:valueOrDash},
            {data:'expectedAmount', defaultContent:null, render:amount},
            {data:bank ? 'paidAmount' : 'challanAmount', defaultContent:null, render:amount},
            {data:'differenceAmount', defaultContent:null, render:amount},
            {data:'paymentPercentage', defaultContent:null, render:function(v,t) { return v == null ? '-' : amount(v,t) + (t === 'display' ? '%' : ''); }},
            {data:'rating', defaultContent:'-', render:valueOrDash},
            {data:bank ? 'amountStatus' : 'complianceStatus', defaultContent:'-', render:valueOrDash},
            {data:bank ? 'timelinessStatus' : null, defaultContent:'-', render:function(v) { return bank ? valueOrDash(v) : 'N/A'; }}
        ], 'No saved workmen details found for this upload.');
    }
    function renderMismatches(rows) {
        var show = reconType !== 'BANK' && rows.length > 0;
        root.find('#reconMismatchSection').toggle(show);
        if (!show) return;
        mismatchTable = setTable('#reconSavedMismatchTable', rows, [
            {data:'gatePassId', defaultContent:'-', render:valueOrDash},
            {data:'workmenName', defaultContent:'-', render:valueOrDash},
            {data:'dbNumber', defaultContent:'-', render:valueOrDash},
            {data:'docNumber', defaultContent:'-', render:valueOrDash},
            {data:'dbAmount', defaultContent:null, render:amount},
            {data:'docAmount', defaultContent:null, render:amount},
            {data:'mismatchReason', defaultContent:'-', render:valueOrDash}
        ], 'No mismatches found.');
    }
    root.off('click.reconView', '#reconBackBtn').on('click.reconView', '#reconBackBtn', function() {
        $.ajax({
            url: historyPageUrl,
            type: 'GET',
            dataType: 'html',
            success: function(html) {
                $('#mainContent').html(html);
                if (typeof window.initReconciliationHistoryPage === 'function') {
                    window.initReconciliationHistoryPage();
                } else {
                    alert('History page initialization is unavailable. Check browser console.');
                }
            },
            error: function(xhr) {
                message('Unable to return to reconciliation history (HTTP ' + xhr.status + ').');
            }
        });
    });
    if (!/^\d+$/.test(String(uploadId)) || !/^(BANK|PF|ESIC)$/.test(reconType)) {
        root.find('#reconDetailsLoading').hide();
        message('Invalid reconciliation upload ID or type.');
        return;
    }
    $.ajax({
        url:detailsUrl, type:'GET', dataType:'json', cache:false,
        data:{uploadId:uploadId, reconType:reconType},
        success:function(response) {
            if (!response || response.status !== 'SUCCESS') {
                message(response && response.message ? response.message : 'Unable to retrieve reconciliation details.');
                return;
            }
            root.find('#reconDetailsTitle').text('- ' + reconType + ' #' + uploadId);
            root.find('#detailUploadId').text(uploadId);
            root.find('#detailReconType').text(reconType);
            root.find('#detailContractor').text('-');
            root.find('#detailStatus').text('-');
            // Load master summary from the already-secured history listing API.
            $.getJSON(historyDataUrl, function(historyResponse) {
                if (!historyResponse || historyResponse.status !== 'SUCCESS') return;
                var record = (historyResponse.data || []).find(function(r) {
                    return String(r.uploadId) === String(uploadId) && r.reconType === reconType;
                });
                if (record) {
                    root.find('#detailContractor').text(record.contractorName || (record.contractorId == null ? '-' : record.contractorId));
                    root.find('#detailStatus').text(record.overallStatus || '-');
                }
            });
            root.find('#reconDetailsContent').show();
            renderDetails(response.details || []);
            renderMismatches(response.mismatches || []);
            requestAnimationFrame(function() {
                if (detailsTable) detailsTable.columns.adjust();
                if (mismatchTable) mismatchTable.columns.adjust();
            });
        },
        error:function(xhr) {
            if (xhr.status === 401) message('Session expired. Please log in again.');
            else if (xhr.status === 403) message('You are not authorized to view this reconciliation.');
            else if (xhr.status === 404) message('Reconciliation details endpoint not found.');
            else message('Unable to load reconciliation details.');
        },
        complete:function() { root.find('#reconDetailsLoading').hide(); }
    });
};
if ($('#reconDetailsRoot').length) window.initReconciliationDetailsPage();
</script>

