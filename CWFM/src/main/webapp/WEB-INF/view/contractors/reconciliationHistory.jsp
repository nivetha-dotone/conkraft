<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.recon-history { background:#fff; font-family:'Noto Sans',sans-serif; }
.recon-history .page-header { display:flex; align-items:center; justify-content:space-between; padding:8px; background:#fff; border-bottom:1px solid #ccc; gap:10px; flex-wrap:wrap; }
.recon-history .history-filters { display:flex; align-items:center; flex-wrap:wrap; gap:10px; }
.recon-history .history-filters label { color:darkcyan; margin:0; font-size:13px; }
.recon-history .history-filters select { color:gray; padding:3px; max-width:190px; font-size:13px; }
.recon-history .table-container { width:100%; overflow-x:auto; margin:0; padding:0; }
.recon-history .dataTables_wrapper { width:100%; }
.recon-history #reconciliationHistoryTable { width:100% !important; table-layout:auto; }
.recon-history #reconciliationHistoryTable th, .recon-history #reconciliationHistoryTable td { box-sizing:border-box; }
.recon-history table { width:100%; border-collapse:collapse; }
.recon-history table th { border:1px solid #ddd; background:#DDF3FF; color:#005151; font-family:'Noto Sans',sans-serif; font-size:.75rem; font-weight:bold; text-align:center; padding:6px; white-space:nowrap; }
.recon-history table td { border:1px solid #ddd; color:#898989; font-family:'Noto Sans',sans-serif; font-size:12px; padding:6px; white-space:nowrap; }
.recon-history .transaction-link { color:#007b9a; text-decoration:underline; cursor:pointer; }
.recon-history .history-message { padding:8px; font-size:13px; }
.recon-history .recon-detail-label { color:#005151; font-weight:600; }
.recon-history .recon-section-title { color:#005151; font-size:14px; font-weight:600; margin:12px 0; }
.recon-history .history-loading { display:none; padding:12px; text-align:center; color:#005151; }
@media(max-width:768px) { .recon-history .page-header { align-items:flex-start; } .recon-history .history-filters { width:100%; } }
</style>
<div class="recon-history" id="reconHistoryRoot">
  <div class="page-header">
    <div class="history-filters">
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
        <th>Upload ID</th><th>Type</th><th>Contractor</th>
        <th>Period</th><th>Total Workmen</th><th>Verified</th><th>Unverified</th>
        <th>Rating</th><th>Status</th><th>Uploaded By</th><th>Uploaded Date</th>
      </tr></thead>
      <tbody></tbody>
    </table>
  </div>
  

</div>
<script>
window.initReconciliationHistoryPage = function() {
    'use strict';
    var root = $('#reconHistoryRoot');
    if (root.data('reconInitialized')) return;
    root.data('reconInitialized', true);
    var historyRows = [];
    var historyTable;
    var historyUrl = '<c:url value="/contractor/reconciliation/history"/>';
    var detailsPageUrl = '<c:url value="/contractor/reconciliation/history/view"/>';

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
        var status = root.find('#historyStatus');
        contractor.empty().append($('<option>', {value:'', text:'All Contractors'}));
        status.empty().append($('<option>', {value:'', text:'All Statuses'}));
        var contractorIds = {}, statuses = {};
        rows.forEach(function(r) {
            if (r.contractorId != null) contractorIds[String(r.contractorId)] = r.contractorName || String(r.contractorId);
            if (r.overallStatus) statuses[String(r.overallStatus)] = true;
        });
        Object.keys(contractorIds).sort().forEach(function(id) {
            contractor.append($('<option>', {value:id, text:contractorIds[id]}));
        });
        Object.keys(statuses).sort().forEach(function(s) {
            status.append($('<option>', {value:s, text:s}));
        });
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
        historyTable.columns.adjust();
    }
    function initHistoryTable() {
        var table = root.find('#reconciliationHistoryTable');
        if ($.fn.DataTable.isDataTable(table[0])) table.DataTable().destroy();
        historyTable = table.DataTable({
            data: [], pageLength:10, autoWidth:true, scrollX:false, order:[[10,'desc']],
            columns: [
                {data:'uploadId', render:function(v,t,r) {
                    if (t !== 'display') return v;
                    return '<a href="#" class="transaction-link recon-history-detail" data-upload-id="' +
                        escapeHtml(v) + '" data-recon-type="' + escapeHtml(r.reconType) + '">' + escapeHtml(v) + '</a>';
                }},
                {data:'reconType', render:valueOrDash},
                {data:'contractorId', render:function(v,t,r) { return valueOrDash(r.contractorName || v); }},
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
    root.off('click.reconHistory', '#historySearchBtn').on('click.reconHistory', '#historySearchBtn', render);
    root.off('click.reconHistory', '.recon-history-detail').on('click.reconHistory', '.recon-history-detail', function(e) {
        e.preventDefault();
        var id = $(this).attr('data-upload-id');
        var type = $(this).attr('data-recon-type');
        if (!/^\d+$/.test(String(id)) || !/^(BANK|PF|ESIC)$/.test(String(type))) return;
        $.ajax({
            url: detailsPageUrl,
            type: 'GET',
            dataType: 'html',
            data: {uploadId: id, reconType: type},
            success: function(html) {
                $('#mainContent').html(html);
                if (typeof window.initReconciliationDetailsPage === 'function') {
                    window.initReconciliationDetailsPage(id, type);
                } else {
                    alert('Details page initialization is unavailable. Check browser console.');
                }
            },
            error: function(xhr) {
                if (xhr.status === 401) showMessage('Session expired. Please log in again.');
                else if (xhr.status === 403) showMessage('Access denied for this reconciliation.');
                else showMessage('Unable to open reconciliation details (HTTP ' + xhr.status + ').');
            }
        });
    });
    initHistoryTable();
    loadHistory();
};
if ($('#reconHistoryRoot').length) window.initReconciliationHistoryPage();
</script>

