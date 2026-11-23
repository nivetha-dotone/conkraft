<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<head>
 <title>Home</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <script src="resources/js/cms/newdashboard.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="resources/js/cms/workmen.js"></script>

<script src="resources/js/jquery.min.js"></script>

<script src="resources/js/bootstrap.bundle.min.js"></script>
 <script>
    window.applicationContextPath =
    	'<%= request.getContextPath() %>';
</script>
 <script src="resources/js/cms/chatbot.js"></script>
<style>


.quick-actions {
  background: #fff;
  border-radius: 10px;
  padding: 15px;
}

.quick-actions {
  background: #fff;
  border-radius: 10px;
  padding: 15px;
}

.action-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 15px;
  margin-top: 10px;
}

.action-btn {
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 20px 10px;
  text-align: center;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.action-btn:hover {
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
  transform: scale(1.05);
}

.icon {
  font-size: 26px;
  margin-bottom: 8px;
}

/* Icon colors */
.icon.green { color: #2ecc71; }
.icon.orange { color: #f39c12; }
.icon.blue { color: #378ADD; }
.icon.purple { color: #9b59b6; }
.icon.teal { color: #1abc9c; }
.icon.red { color: #e74c3c; }

.action-btn span {
  font-weight: 600;
  color: #333;
  font-size: 14px;
}
/* Color themes for each button */
.action-btn.green { background-color: #fff; }
.action-btn.orange { background-color: #fff; }
.action-btn.blue { background-color:  #fff; }
.action-btn.purple { background-color:  #fff; }
.action-btn.teal { background-color:  #fff; }
.action-btn.red { background-color:  #fff; }

.action-box {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 15px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.05);
  text-align: center;
  transition: all 0.2s ease;
}

.action-box:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  transform: scale(1.03);
}

.alerts {
  background: #fff;
  border-radius: 10px;
  padding: 15px;
}


/* .view-all {
  font-size: 13px;
  color: #378ADD;
  text-decoration: none;
} */

.alert-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f9fafc;
  border-radius: 10px;
  padding: 10px 12px;
  margin-bottom: 27px;
  transition: all 0.2s ease;
}

.alert-item:hover {
  box-shadow: 0 4px 10px rgba(0,0,0,0.08);
}

.alert-left {
  display: flex;
  align-items: center;
  gap: 10px;
}
.alert-text {
  display: flex;
  flex-direction: column;
}

.alert-title {
  font-weight: 600;
  color: #333;
  font-size: 14px;
}

.alert-sub {
  font-size: 12px;
  color: #666;
}

.alert-right {
  font-size: 12px;
  font-weight: 600;
  padding: 4px 10px;
  border-radius: 8px;
}

.high-label {
  background-color: #fff;
  color: #e74c3c;
}

.medium-label {
  background-color: #fff;
  color: #f39c12;
}

.icon-bg {
  width: 45px;
  height: 45px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon-bg.red { background-color: #ffeaea; color: #e74c3c; }
.icon-bg.orange { background-color: #fff4e6; color: #f39c12; }
.icon-bg.purple { background-color: #f3e9ff; color: #9b59b6; }
.icon-bg.blue { background-color: #e8f1ff; color: #378ADD; }

/* Assistant Banner */
.security-banner {
    background: linear-gradient(90deg, #6dc7f8, rgb(0 0 0 / 10%));
    border-radius: 12px;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 5px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

.banner-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.banner-left {
  display: flex;
  align-items: center;
  gap: 10px;
}

.banner-text {
  font-weight: 600;
  color: #004d47;
  font-size: 14px;
  white-space: nowrap; /* ✅ ensures text stays on one line */
  overflow: hidden;
  text-overflow: ellipsis; 
}


/* Responsive behavior */
@media (max-width: 1200px) {
  .kpi-row {
    grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  }
}
.banner-icon i {
  font-size: 48px;
  color: #a8f0b5;
}

.banner-text .title {
  font-size: 18px;
  font-weight: 700;
}

.banner-text .highlight {
  color: #a8f0b5;
}

.banner-text .subtitle {
  font-size: 14px;
  opacity: 0.9;
}

.banner-right img {
    height: auto;
    width: 90px;
    border-radius: 6px;
}
.coverage-section {
  background: #fff;
  border-radius: 10px;
  padding: 15px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
}

.sec-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

/* .view-all {
  font-size: 13px;
  color: #378ADD;
  text-decoration: none;
  cursor: pointer;
} */

.prog-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 10px;
  max-height: 400px; /* limit height when many rows */
  overflow-y: auto;
}

.prog-item {
  background: #f9fafc;
  border-radius: 8px;
  padding: 10px;
}

.pi-head {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 6px;
}

.prog-track {
  background: #e0e0e0;
  border-radius: 6px;
  height: 8px;
  overflow: hidden;
}

.prog-fill {
  height: 8px;
  border-radius: 6px;
}

.icon-bg.low { background-color: #e8f1ff; color: #378ADD; }
.icon-bg.medium { background-color: #fff4e6; color: #f39c12; }
.icon-bg.high { background-color: #ffeaea; color: #e74c3c; }

.alert-right.low-label { background-color: #e8f1ff; color: #378ADD; padding: 4px 10px; border-radius: 8px; font-weight: 600; }
.alert-right.medium-label { background-color: #fff4e6; color: #f39c12; padding: 4px 10px; border-radius: 8px; font-weight: 600; }
.alert-right.high-label { background-color: #ffeaea; color: #e74c3c; padding: 4px 10px; border-radius: 8px; font-weight: 600; }
.view-all {
  font-size: 13px;
  color: #378ADD;
  background: none;
  border: 1px solid #378ADD;
  border-radius: 6px;
  padding: 4px 10px;
  cursor: pointer;
}
.modal-overlay {
  display: none;
  position: fixed;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: rgba(0,0,0,0.4);
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-box {
  background: #fff;
  border-radius: 10px;
  width: 70%;
  max-height: 80%;
  overflow-y: auto;
  box-shadow: 0 4px 12px rgba(0,0,0,0.2);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  padding: 12px 20px;
  border-bottom: 1px solid #ddd;
}

.close-btn {
  cursor: pointer;
  font-size: 18px;
  color:#007bff;
}

.modal-body {
  padding: 15px 20px;
}
/* canvas { width: 120px; height: 120px; } */


/* .bill-chart-box canvas {
  width: 120px !important;
  height: 120px !important;
  display: block;
}

  .bill-chart-box {
   display: flex;
  justify-content: space-around;
  align-items: center;
  flex-wrap: wrap;
  min-height: 150px;
}	 */


/* .compliance-row {
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-wrap: wrap;
  padding: 10px 0;
}

.compliance-item {
  text-align: center;
  width: 150px;
}

.compliance-item canvas {
  width: 100px !important;
  height: 100px !important;
  margin-bottom: 8px;
}

.label span {
  font-weight: 600;
  font-size: 16px;
  color: #333;
}

.label small {
  font-size: 13px;
  color: #666;
}
.progress-bar {
  width: 80px;
  height: 4px;
  background: #e0e0e0;
  margin: 6px auto 0;
  border-radius: 2px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  border-radius: 2px;
} */
.compliance-row {
  display: flex;
  justify-content: space-evenly;
  align-items: flex-start;
  flex-wrap: wrap;
  gap: 30px; /* ✅ spacing between circles */
  padding: 20px 0;
}

.compliance-item {
  text-align: center;
  width: 180px;
}

.chart-container {
  position: relative;
  width: 120px;
  height: 120px;
  margin: 0 auto 10px;
}

.chart-center {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-weight: 700;
  font-size: 18px;
  color: #333;
}

.label {
  margin-top: 8px;
  font-weight: 600;
  font-size: 14px;
  color: #333;
}

.label small {
  font-size: 13px;
  color: #666;
}

.progress-bar {
  width: 100px;
  height: 5px;
  background: #e0e0e0;
  margin: 8px auto 0;
  border-radius: 3px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  border-radius: 3px;
}

/* .bill-chart-box {
  width: 220px;
  height: 220px;
  position: relative;
}

.bill-chart-box canvas {
  width: 100%;
  height: 100%;
  display: block;
}

.bill-chart-container {
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-wrap: wrap;
  padding: 10px 0;
}

.bill-summary {
  font-size: 14px;
  color: #333;
  line-height: 1.8;
}

.dot {
  height: 10px;
  width: 10px;
  border-radius: 50%;
  display: inline-block;
  margin-right: 6px;
}

.dot.green { background-color: #2ecc71; }
.dot.orange { background-color: #f39c12; }
.dot.red { background-color: #e74c3c; } */

.bill-chart-container {
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-wrap: wrap;
  padding: 20px 0;
}

.bill-chart-box {
  width: 240px;
  height: 240px;
  position: relative;
}

.bill-summary {
  font-size: 15px;
  color: #333;
  line-height: 2.4; /* Adds vertical spacing between rows */
  margin-left: 25px;
  display: flex;
  flex-direction: column;
  gap: 8px; /* Adds consistent row spacing */
}
.bill-summary div {
  display: flex;
  align-items: center;
  gap: 10px; /* Space between dot, label, count, and percentage */
font-size: medium;
}

.dot {
  height: 10px;
  width: 10px;
  border-radius: 50%;
  display: inline-block;
  margin-right: 8px;
}

.dot.green { background-color: #2ecc71; }
.dot.orange { background-color: #f39c12; }
.dot.red { background-color: #e74c3c; }
/* .kpi-card.teal {
  border-top: 4px solid #009688;
}
 */
/* Keep box size fixed */
#kpi-ai {
  width: 250px;       /* ✅ same width as other KPI cards */
  height: 140px;      /* ✅ same height as other KPI cards */
}

/* Ensure image fills box */
.ai-card {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.ai-image-box {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 8px;
  overflow: hidden;
}

.ai-image {
  width: 100%;
  height: 100%;
  object-fit: cover;  /* ✅ fills box perfectly without distortion */
  border-radius: 8px;
}

/* Overlay text */
.ai-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
  color: #fff;
  font-weight: 600;
  font-size: 14px;
  background: rgba(0, 0, 0, 0.4);
  padding: 5px 0;
}


/* ////////////// */
.kpi-row {
 display: flex;
    justify-content: space-between;
    grid-template-columns: repeat(5, 1fr); /* ✅ 4 KPI + 1 Assistant tile */
    align-items: stretch;
    gap: 20px;
    margin-bottom: 37px;
    margin-top: 23px;
}

.kpi-card {
  flex: 1;
  min-width: 220px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
  padding: 15px;
}

/* .security-banner {
  margin-top: 20px;
  padding: 0 20px; /* Match row padding for alignment */
} */
/* .lastrowkpi {
display: flex;
  width: 100%;
  max-width: 1400px; /* ✅ keeps consistent width with other rows */
  margin: 0 auto;    /* ✅ centers the row horizontally */
  padding: 0 20px;   /* ✅ equal left and right spacing */
  box-sizing: border-box;
}*/

.kpi-row4.row4 {
  display: grid;
  margin-left: 20px;
  gap:25px;
}

/* Overlay covers entire screen */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4); /* dim background */
  display: none; /* hidden by default */
  justify-content: center;
  align-items: center;
  z-index: 9999; /* above header */
}

/* Centered modal box */
.modal-box {
  background: #fff;
  border-radius: 8px;
  width: 60%;
  max-width: 800px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
  padding: 20px;
  position: relative;
}

/* Header styling */
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  font-size: 18px;
  margin-bottom: 10px;
}

/* Body content */
.modal-body {
  max-height: 400px;
  overflow-y: auto;
}

/* Expiry item styling */
.expiry-item {
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}

.dot.red {
  height: 10px;
  width: 10px;
  background-color: red;
  border-radius: 50%;
  display: inline-block;
  margin-right: 8px;
}
.expiry-table-container {
  width: 100%;
  max-height: 400px; /* adjust height as needed */
  overflow-y: auto;  /* vertical scrollbar */
  overflow-x: auto;  /* horizontal scrollbar if needed */
  border: 1px solid #ddd;
  margin-top: 10px;
}

.expiry-table {
  width: 100%;
  border-collapse: collapse;
}

.expiry-table th, .expiry-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

.expiry-table th {
  background-color: #f4f4f4;
  font-weight: 600;
}

.empty-state {
  text-align: center;
  color: #777;
  padding: 20px;
}
.lastdashboard {
    width: 102%;
    padding: 0 30px;
    box-sizing: border-box;
    margin-left: -6px;
}
/* Navy blue icon */
.icon.navyblue {
  color: #000080; /* Navy Blue */
}

/* Button style: white background, no border */
.action-btn.navyblue {
  background-color: #ffffff;
  border: none;
}
.approved-link {
  cursor: pointer;        /*  shows hand cursor */
  /* color: #28a745;         /* same green as dot */ */
  
  
}

.approved-link:hover {
  color: grey;         /* darker green on hover */
}


.ccs-panel{background:#fff;border-radius:10px;box-shadow:0 2px 6px rgba(0,0,0,.1);padding:22px;margin:22px 0;font-family:inherit}
.ccs-head{display:flex;justify-content:space-between;gap:15px;flex-wrap:wrap;align-items:center;margin-bottom:18px}
.ccs-head h3{margin:0;font-size:17px;font-weight:700;color:#253248}
.ccs-filters{display:flex;flex-wrap:wrap;gap:10px}
.ccs-filters label{font-size:12px;color:#64748b;display:flex;flex-direction:column;gap:4px}
.ccs-filters select,.ccs-filters input{border:1px solid #dbe1e8;border-radius:6px;padding:8px;background:#fff;min-width:140px}
.ccs-kpis{display:grid;grid-template-columns:repeat(3,minmax(0,1fr));gap:14px;margin-bottom:18px}
.ccs-kpi{padding:17px;border:1px solid #e5eaf1;border-radius:9px;border-left:4px solid #378add}
.ccs-kpi:nth-child(2){border-left-color:#f59e0b}.ccs-kpi:nth-child(3){border-left-color:#16a085}
.ccs-kpi .ccs-value{font-size:27px;font-weight:700;margin-top:5px}.ccs-kpi small{color:#64748b}
.ccs-chart-wrap{position:relative;height:320px;max-width:100%;margin-bottom:22px}
.ccs-table-wrap{overflow-x:auto}.ccs-table{width:100%;border-collapse:collapse;font-size:13px}
.ccs-table th,.ccs-table td{padding:11px;border-bottom:1px solid #e5eaf1;text-align:left}
.ccs-table th{background:#f8fafc}.ccs-status{font-size:12px;color:#64748b;margin:8px 0}
@media(max-width:700px){.ccs-kpis{grid-template-columns:1fr}.ccs-chart-wrap{height:260px}}


/* Responsive rules intentionally disabled on laptop/desktop widths. */
@media screen and (max-width: 1199.98px) {
/* Responsive dashboard overrides - keep this block LAST in the style section */
.db, .lastdashboard { width:100%; max-width:100%; margin-left:0; padding:0 20px; box-sizing:border-box; min-width:0; }
.db *, .lastdashboard * { box-sizing:border-box; }
.kpi-row, .quick-actions-row, .chart-row { display:grid; grid-template-columns:repeat(5,minmax(0,1fr)); gap:16px; align-items:stretch; }
.kpi-row > *, .quick-actions-row > *, .chart-row > * { min-width:0; max-width:100%; }
.kpi-card, #kpi-ai { width:auto; min-width:0; max-width:100%; height:auto; min-height:130px; }
.kpi-content, .kpi-text { min-width:0; }
.kpi-text, .kpi-title-alt, .lbl, .banner-text { overflow-wrap:anywhere; }
.section.contractor-workmen, .card.warn, .card.highlight-card, .db > .card,
.lastdashboard > .card { margin-left:0; margin-right:0; max-width:100%; }
.card-grid { margin-left:0; grid-template-columns:repeat(auto-fit,minmax(min(100%,220px),1fr)); }
.action-grid { grid-template-columns:repeat(auto-fit,minmax(min(100%,150px),1fr)); }
.chart-box, .chart-container, .ccs-chart-wrap { max-width:100%; min-width:0; }
.chart-container canvas, .chart-box canvas { max-width:100%; }
.table-wrapper, .expiry-table-container, .ccs-table-wrap { max-width:100%; overflow-x:auto; -webkit-overflow-scrolling:touch; }
.modal-box { width:min(92vw,900px); max-width:92vw; max-height:85dvh; overflow:auto; }
.ccs-kpis { grid-template-columns:repeat(3,minmax(0,1fr)); }
@media (max-width:1199.98px) {
  .kpi-row, .quick-actions-row, .chart-row { grid-template-columns:repeat(2,minmax(0,1fr)); }
  .ccs-kpis { grid-template-columns:repeat(2,minmax(0,1fr)); }
  .db, .lastdashboard { padding:0 14px; }
  .kpi-row4.row4 { margin-left:0; }
}
@media (max-width:767.98px) {
  .db, .lastdashboard { padding:0 10px; }
  .kpi-row, .quick-actions-row, .chart-row { gap:12px; margin-bottom:16px; }
  .kpi-card, #kpi-ai { padding:12px; min-height:110px; }
  .section.contractor-workmen, .card.warn, .card.highlight-card, .db > .card,
  .lastdashboard > .card { padding:15px; }
  .sec-header { margin-left:0; margin-right:0; }
  .banner-content { gap:10px; }
  .banner-left { min-width:0; }
  .banner-text { white-space:normal; text-overflow:clip; }
  .banner-right img { width:64px; height:auto; }
  .chart-container { height:auto; min-height:240px; }
  .ccs-chart-wrap { height:260px; }
  .bill-chart-container { gap:15px; }
  .bill-summary { margin-left:0; }
  .compliance-row { gap:12px; }
  .compliance-item { width:calc(50% - 8px); min-width:0; }
  .modal-box { padding:12px; }
  .modal-body { padding:12px; }
}
@media (max-width:479.98px) {
  .kpi-row, .quick-actions-row, .chart-row { grid-template-columns:minmax(0,1fr); }
  .ccs-kpis { grid-template-columns:minmax(0,1fr); }
  .kpi-card, #kpi-ai { min-height:100px; }
  .action-grid { grid-template-columns:repeat(2,minmax(0,1fr)); gap:10px; }
  .action-btn { padding:12px 6px; }
  .alert-item { gap:8px; flex-wrap:wrap; }
  .alert-left { min-width:0; }
  .modal-box { width:96vw; max-width:96vw; }
}


/* Mobile correction v2: actual dashboard rows use .row.row-3col,
   not .chart-row. Override Bootstrap .row and desktop column rules. */
.dashboard { width:100%; max-width:100%; min-width:0; }
.dashboard .row.row-3col { display:grid; grid-template-columns:repeat(3,minmax(0,1fr)); gap:18px; width:100%; max-width:100%; margin:0 0 20px; padding:0; }
.dashboard .row.row-3col > .section { width:auto; max-width:100%; min-width:0; flex:none; margin:0; box-sizing:border-box; }
.dashboard .row.row-3col .section { overflow:hidden; }
.dashboard .row.row-3col .sec-header { white-space:normal; overflow-wrap:anywhere; }
.dashboard .row.row-3col .chart-container { min-height:0; width:120px; height:120px; max-width:100%; margin:0 auto 10px; position:relative; }
.dashboard .row.row-3col .chart-container canvas { width:100% !important; height:100% !important; }
.dashboard .row.row-3col .bill-chart-box { width:min(240px,100%); height:auto; aspect-ratio:1; margin:auto; }
.dashboard .row.row-3col .bill-chart-box canvas { width:100% !important; height:100% !important; }
.dashboard .row.row-3col .bill-summary { min-width:0; margin-left:0; }
.dashboard .row.row-3col .bill-summary div { flex-wrap:wrap; }
.dashboard .row.row-3col .compliance-item { min-width:0; }
.dashboard .row.row-3col .alert-item, .dashboard .row.row-3col .alert-left { min-width:0; }
.dashboard .row.row-3col .alert-text { min-width:0; overflow-wrap:anywhere; }
.dashboard .row.row-3col .chart-section canvas { max-width:100%; }
@media(max-width:1199.98px) {
 .dashboard .row.row-3col { grid-template-columns:repeat(2,minmax(0,1fr)); }
}
@media(max-width:767.98px) {
 .dashboard .row.row-3col { grid-template-columns:minmax(0,1fr) !important; gap:14px; margin-bottom:16px; }
 .dashboard .row.row-3col > .section { width:100% !important; max-width:100% !important; padding:16px; }
 .dashboard .row.row-3col .compliance-row { display:grid; grid-template-columns:repeat(2,minmax(0,1fr)); gap:16px 8px; justify-items:center; }
 .dashboard .row.row-3col .compliance-item { width:100%; }
 .dashboard .row.row-3col .bill-chart-container { display:flex; flex-direction:column; align-items:center; gap:12px; }
 .dashboard .row.row-3col .bill-chart-box { width:min(220px,100%); }
 .dashboard .row.row-3col .bill-summary { width:100%; line-height:1.6; }
 .dashboard .row.row-3col .chart-section { min-height:260px; }
 .dashboard .row.row-3col .alert-item { margin-bottom:12px; }
}
@media(max-width:380px) {
 .dashboard .row.row-3col .chart-container { width:105px; height:105px; }
}


/* KPI mobile correction: align first tile exactly like other KPI tiles */
@media (max-width: 767.98px) {
  .dashboard > .kpi-row { width:100%; max-width:100%; margin-left:0; margin-right:0; padding:0; }
  .dashboard > .kpi-row > .kpi-card { width:100%; margin:0; min-width:0; display:flex; align-items:center; justify-content:flex-start; }
  .dashboard > .kpi-row > .kpi-card .kpi-content { display:flex; align-items:center; gap:16px; width:100%; min-width:0; }
  .dashboard > .kpi-row > .kpi-card .kpi-text { flex:1 1 auto; min-width:0; text-align:left; }
  .dashboard > .kpi-row > .kpi-card .kpiicon-bg { flex:0 0 auto; }
  .dashboard > .kpi-row > .kpi-card .lbl { line-height:1.3; }
  .dashboard > .kpi-row > .kpi-card .num { text-align:center; margin-top:8px; }
  .dashboard > .kpi-row > .kpi-card.green { border-top:5px solid #2ecc71; }
}

}
</style>


</head>

<div class="dashboard">

  <!-- HEADER -->
 <!--  <div class="header-bar">
    <div class="logo">CONKRAFT</div>
    <div class="title">Contract Labor Management System</div>
    <div class="user-info">
      <span class="role">Role: Contractor</span>
      <span class="avatar">BK</span>
      <span class="name">Brajesh Kumar</span>
    </div>
  </div>
 -->
  <!-- KPI ROW -->
<!-- KPI ROW -->
<div class="kpi-row">
  <div class="kpi-card green">
    <div class="kpi-content">
      <div class="kpiicon-bg green"><i class="fa-solid fa-users kpiicongreen"></i></div>
      <div class="kpi-text">
        <div class="lbl">ACTIVE WORKMEN</div>
        <div class="num">${dashboard.activeWorkmen}</div>
<!--         <div class="trend up">↑ 8.5% vs last month</div> -->
      </div>
    </div>
  </div>

  <div class="kpi-card orange">
    <div class="kpi-content">
      <div class="kpiicon-bg orange"><i class="fa-solid fa-file-contract kpiiconornge"></i></div>
      <div class="kpi-text">
        <div class="lbl">ACTIVE WORK ORDERS</div>
        <div class="num">${dashboard.activeWO}</div>
       <!--  <div class="trend up">↑ 12.0% vs last month</div> -->
      </div>
    </div>
  </div>

  <div class="kpi-card red">
    <div class="kpi-content">
      <div class="kpiicon-bg red"><i class="fa-solid fa-id-card kpiiconred"></i></div>
      <div class="kpi-text">
        <div class="lbl">ACTIVE LL</div>
        <div class="num">${dashboard.activeLL}</div>
        <!-- <div class="trend neutral">→ No change</div> -->
      </div>
    </div>
  </div>

  <div class="kpi-card blue">
    <div class="kpi-content">
      <div class="kpiicon-bg blue"><i class="fa-solid fa-building kpiiconblue"></i></div>
      <div class="kpi-text">
        <div class="lbl">ACTIVE WC</div>
        <div class="num">${dashboard.activeWC}</div>
        <!-- <div class="trend up">↑ 5.2% vs last month</div> -->
      </div>
    </div>
  </div>
 <div class="security-banner" id="kpi-security-banner">
   <div class="banner-content">
     <div class="banner-left">
       <div class="banner-text">
         <a href="javascript:void(0);" class="dashboard-tile" onclick="openChatBotModal();">
           <span class="tile-content"><i class="fa fa-comments"></i> Conkraft Assistant</span>
         </a>
       </div>
     </div>
     <div class="banner-right">
       <img src="resources/img/ai-image.png" alt="Conkraft Assistant" />
     </div>
   </div>
 </div>
</div>

<!-- Contractor Compliance Scoreboard: isolated from existing dashboard charts -->
<section id="contractorComplianceScoreboard" class="ccs-panel" data-api="${pageContext.request.contextPath}/dashboard/contractorComplianceScoreboard">
  <div class="ccs-head">
    <h3>CONTRACTOR COMPLIANCE SCOREBOARD</h3>
    <div class="ccs-filters">
      <label>Reporting Month
        <input type="month" id="ccsMonth" value="<%= java.time.LocalDate.now().minusMonths(1).toString().substring(0,7) %>" />
      </label>
      <label>Principal Employer
        <select id="ccsUnit"><option value="">All Principal Employers</option></select>
      </label>
      <label>Contractor
        <select id="ccsContractor"><option value="">All Contractors</option></select>
      </label>
    </div>
  </div>
  <div class="ccs-kpis">
    <div class="ccs-kpi"><small>PF AVERAGE RATING</small><div class="ccs-value" id="ccsPf">N/A</div><small>Out of 5</small></div>
    <div class="ccs-kpi"><small>ESIC AVERAGE RATING</small><div class="ccs-value" id="ccsEsic">N/A</div><small>Out of 5</small></div>
    <div class="ccs-kpi"><small>BANK WAGE AVERAGE RATING</small><div class="ccs-value" id="ccsBank">N/A</div><small>Out of 5</small></div>
  </div>
  <div class="ccs-chart-wrap"><canvas id="ccsChart"></canvas></div>
  <div class="ccs-table-wrap"><table class="ccs-table">
    <thead><tr>
    <th>Principal Employer</th>
<th>Contractor</th>
<th>PF</th>
<th>ESIC</th>
<th>Bank Wage</th>
    </tr></thead>
    <tbody id="ccsTableBody"></tbody>
  </table></div>
  <div id="ccsStatus" class="ccs-status" aria-live="polite"></div>
  <%-- Initial scoreboard records rendered from DashboardDTO, not hardcoded --%>
  <div id="ccsInitialData" hidden>
    <c:forEach var="score" items="${dashboard.complianceScores}">
        <span class="ccs-record"
              data-unit="${score.unitId}"
              data-unit-name="${fn:escapeXml(score.unitName)}"
              data-contractor="${score.contractorId}"
              data-contractor-name="${fn:escapeXml(score.contractorName)}"
              data-type="${fn:escapeXml(score.complianceType)}"
              data-rating="${score.averageRating}"
              data-count="${score.workmenCount}">
        </span>
    </c:forEach>
</div>
</section>

<!-- ROW 2: Workforce Distribution + Supervisor Coverage + Alerts -->
<!-- ROW 2: Workforce Distribution + Supervisor Coverage + Alerts -->
<div class="row row-3col">

  <!-- Workforce Distribution -->
  <div class="section chart-section">
    <div class="sec-header">
      <span>WORKFORCE DISTRIBUTION</span>
      <!-- <select>
        <option>All Plants</option>
      </select> -->
    </div>
    <div  style="height:200px;" class="chart-box">
      <canvas id="plantChart"></canvas>
    </div>
  </div>

  <!-- Supervisor Coverage -->
<%-- <div class="section coverage-section">
  <div class="sec-header">
    <span>SUPERVISOR COVERAGE</span>
    <c:if test="${fn:length(dashboard.plantContrWorkmenList) > 5}">
      <button class="view-all" onclick="openCoverageModal()">View all</button>
    </c:if>
  </div>

  <div class="prog-list">
    <c:forEach var="plant" items="${dashboard.plantContrWorkmenList}" varStatus="status">
      <c:if test="${status.index < 5}">
        <c:set var="percent"
               value="${plant.activeCount > 0 ? (plant.contractorCount * 100) / plant.activeCount : 0}" />
        <div class="prog-item">
          <div class="pi-head">
            <span>${plant.plantName}</span>
            <span>${plant.contractorCount} sup / ${plant.activeCount} wkm</span>
          </div>
          <div class="prog-track">
            <div class="prog-fill"
                 style="width:${percent}%;
                 <c:choose>
                   <c:when test='${percent > 70}'>background:#2ecc71;</c:when>
                   <c:when test='${percent > 40}'>background:#378ADD;</c:when>
                   <c:otherwise>background:#f39c12;</c:otherwise>
                 </c:choose>">
            </div>
          </div>
        </div>
      </c:if>
    </c:forEach>
  </div>
</div> --%>

<!-- MODAL FOR VIEW ALL -->
<div id="coverageModal" class="modal-overlay">
  <div class="modal-box modal-lg">
    <div class="modal-header">
      <span>SUPERVISOR COVERAGE - ALL PLANTS</span>
      <span class="close-btn" onclick="closeCoverageModal()">✖</span>
    </div>
    <div class="modal-body">
      <div class="prog-list">
        <c:forEach var="plant" items="${dashboard.plantContrWorkmenList}">
          <c:set var="percent"
                 value="${plant.activeCount > 0 ? (plant.contractorCount * 100) / plant.activeCount : 0}" />
          <div class="prog-item">
            <div class="pi-head">
              <span>${plant.plantName}</span>
              <span>${plant.contractorCount} sup / ${plant.activeCount} wkm</span>
            </div>
            <div class="prog-track">
              <div class="prog-fill"
                   style="width:${percent}%;
                   <c:choose>
                     <c:when test='${percent > 70}'>background:#2ecc71;</c:when>
                     <c:when test='${percent > 40}'>background:#378ADD;</c:when>
                     <c:otherwise>background:#f39c12;</c:otherwise>
                   </c:choose>">
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
</div>




  <!-- ALERTS & NOTIFICATIONS -->
<%-- <div class="section alerts">
  <div class="sec-header">
    <span>ALERTS & NOTIFICATIONS</span>
  </div>

  <!-- LICENSES -->
  <c:set var="licenseStatus"
         value="${dashboard.expiredLicensess <= 30 ? 'low' :
                 (dashboard.expiredLicensess <= 60 ? 'medium' : 'high')}" />

  <div class="alert-item ${licenseStatus}">
    <div class="alert-left">
       <div class="icon-bg red"><i class="fa-solid fa-shield-heart"></i>
      </div>
      <div class="alert-text">
        <div class="alert-title">${dashboard.expiredLicensess} Licenses Expiring Soon</div>
        <div class="alert-sub">Within next 15 days</div>
      </div>
    </div>
    <div class="alert-right ${licenseStatus}-label">
      <c:choose>
        <c:when test="${licenseStatus == 'low'}">Low</c:when>
        <c:when test="${licenseStatus == 'medium'}">Medium</c:when>
        <c:otherwise>High</c:otherwise>
      </c:choose>
    </div>
  </div> --%>


<div class="section alerts">
  <div class="sec-header">
    <span>ALERTS & NOTIFICATIONS</span>
  </div>

  <!-- LICENSES -->
  <c:set var="licenseCount" value="${fn:length(dashboard.LLLicenseList)}" />
  <c:set var="licenseStatus"
         value="${licenseCount <= 30 ? 'low' :
                 (licenseCount <= 60 ? 'medium' : 'high')}" />

  <div class="alert-item ${licenseStatus}" onclick="openLicenseExpiryPopup()">
    <div class="alert-left">
       <div class="icon-bg red"><i class="fa-solid fa-shield-heart"></i></div>
       <div class="alert-text">
         <div class="alert-title">
           <a href="javascript:void(0)" onclick="openLicenseExpiryPopup()">
             ${licenseCount} Licenses Expiring Soon
           </a>
         </div>
         <div class="alert-sub">Within next 30 days</div>
       </div>
    </div>
    <div class="alert-right ${licenseStatus}-label">
      <c:choose>
        <c:when test="${licenseStatus == 'low'}">Low</c:when>
        <c:when test="${licenseStatus == 'medium'}">Medium</c:when>
        <c:otherwise>High</c:otherwise>
      </c:choose>
    </div>
  </div>
  <!-- WORK ORDERS -->
  <c:set var="workOrderCount" value="${fn:length(dashboard.workorderalertsList)}" />
  <c:set var="workOrderStatus"
         value="${workOrderCount <= 30 ? 'low' :
                 (workOrderCount<= 60 ? 'medium' : 'high')}" />

  <div class="alert-item ${workOrderStatus}">
    <div class="alert-left">
       <div class="icon-bg orange"><i class="fa-solid fa-triangle-exclamation"></i></div>
      <div class="alert-text">
        <div class="alert-title">
           <a href="javascript:void(0)" onclick="openWorkorderExpiryPopup()">
             ${workOrderCount} Work Orders Expiring
           </a>
        </div>
        <div class="alert-sub">Within next 30 days</div>
      </div>
    </div>
    <div class="alert-right ${workOrderStatus}-label">
      <c:choose>
        <c:when test="${workOrderStatus == 'low'}">Low</c:when>
        <c:when test="${workOrderStatus == 'medium'}">Medium</c:when>
        <c:otherwise>High</c:otherwise>
      </c:choose>
    </div>
  </div>

  <!-- GATE PASSES -->
   <c:set var="gatepassCount" value="${fn:length(dashboard.gatepassExpiryList)}" />
  <c:set var="gatePassStatus"
         value="${gatepassCount <= 30 ? 'low' :
                 (gatepassCount <= 60 ? 'medium' : 'high')}" />

  <div class="alert-item ${gatePassStatus}">
    <div class="alert-left">
      <div class="icon-bg purple"><i class="fa-solid fa-ban"></i></div>
      <div class="alert-text">
        <div class="alert-title">
          <a href="javascript:void(0)" onclick="openGatepassExpiryPopup()">
            ${gatepassCount} Gate Passes Expiring
          </a>
        </div>
        <div class="alert-sub">Action Required Within next 30 days</div>
      </div>
    </div>
    <div class="alert-right ${gatePassStatus}-label">
      <c:choose>
        <c:when test="${gatePassStatus == 'low'}">Low</c:when>
        <c:when test="${gatePassStatus == 'medium'}">Medium</c:when>
        <c:otherwise>High</c:otherwise>
      </c:choose>
    </div>
  </div>

</div>

<!-- license Expiry Modal (hidden until opened) -->
<div id="licenseexpiryModal" class="modal-overlay">
  <div class="modal-box">
    <div class="modal-header">
      <span>All Expiring Licenses</span>
      <span class="close-btn" onclick="closeLicenseExpiryPopup()">✖</span>
    </div>
    <div class="modal-body">
      <c:choose>
        <c:when test="${not empty dashboard.LLLicenseList}">
          <table class="expiry-table">
            <thead>
              <tr>
                <th>License Number</th>
                <th>License Type</th>
                <th>Expiry Date</th>
                <th>Days Left</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="item" items="${dashboard.LLLicenseList}">
                <tr>
                  <td>${item.licenseNumber}</td>
                  <td>${item.licenseType}</td>
                  <td>${item.expiryDate}</td>
                  <td>${item.daysLeft}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="empty-state">No expiring licenses found</div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
<!-- Workorder Expiry Modal (hidden until opened) -->
<div id="workorderexpiryModal" class="modal-overlay">
  <div class="modal-box">
    <div class="modal-header">
      <span>All Expiring Workorders</span>
      <span class="close-btn" onclick="closeWorkorderExpiryPopup()">✖</span>
    </div>

    <div class="modal-body">
      <c:choose>
        <c:when test="${not empty dashboard.workorderalertsList}">
          <table class="expiry-table">
            <thead>
              <tr>
                <th>WorkOrder Number</th>
                <th>Expiry Date</th>
                <th>Days Left</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="item" items="${dashboard.workorderalertsList}">
                <tr>
                  <td>${item.workorderNumber}</td>
                  <td>${item.expiryDate}</td>
                  <td>${item.daysLeft}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="empty-state">No expiring workorders found</div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<!-- gatepass Expiry Modal (hidden until opened) -->
<div id="gatepassexpiryModal" class="modal-overlay">
  <div class="modal-box">
    <div class="modal-header">
      <span>All Expiring GatePasses</span>
      <span class="close-btn" onclick="closeGatepassExpiryPopup()">✖</span>
    </div>

    <div class="modal-body">
      <c:choose>
        <c:when test="${not empty dashboard.gatepassExpiryList}">
          <table class="expiry-table">
            <thead>
              <tr>
                <th>GatePassId</th>
                <th>Full Name</th>
                <th>Expiry Date</th>
                <th>Days Left</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="item" items="${dashboard.gatepassExpiryList}">
                <tr>
                  <td>${item.gatepassId}</td>
                  <td>${item.fullName}</td>
                  <td>${item.expiryDate}</td>
                  <td>${item.daysLeft}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="empty-state">No expiring Gatepasses found</div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>




</div>

<!-- ROW 3: Compliance Overview + Bill Verification + Quick Actions -->
<div class="row row-3col">

  <!-- Compliance Overview -->
 <div class="section compliance">
  <div class="sec-header">COMPLIANCE OVERVIEW</div>
  <div class="compliance-row">
    <div class="compliance-item">
      <div class="chart-container">
        <canvas id="workOrderChart"></canvas>
        <div class="chart-center" id="workOrderText">${dashboard.workorderList[0].woPercentage}%</div>
      </div>
      <div class="label">
        Work Orders Valid<br>
        <small>${dashboard.workorderList[0].activeWO} / ${dashboard.workorderList[0].totalWO}</small>
      </div>
      <div class="progress-bar">
        <div class="progress-fill" style="width:${dashboard.workorderList[0].woPercentage}%; background:#f39c12;"></div>
      </div>
    </div>

    <div class="compliance-item">
      <div class="chart-container">
        <canvas id="licenseChart"></canvas>
        <div class="chart-center" id="licenseText">${dashboard.licenseList[0].LLPercentage}%</div>
      </div>
      <div class="label">
        License Valid<br>
        <small>${dashboard.licenseList[0].activeLL} / ${dashboard.licenseList[0].totalLL}</small>
      </div>
      <div class="progress-bar">
        <div class="progress-fill" style="width:${dashboard.licenseList[0].LLPercentage}%; background:#2ecc71;"></div>
      </div>
    </div>

    <div class="compliance-item">
      <div class="chart-container">
        <canvas id="wcChart"></canvas>
        <div class="chart-center" id="wcText">${dashboard.WCList[0].WCPercentage}%</div>
      </div>
      <div class="label">
        WC Valid<br>
        <small>${dashboard.WCList[0].activeWC} / ${dashboard.WCList[0].totalWC}</small>
      </div>
      <div class="progress-bar">
        <div class="progress-fill" style="width:${dashboard.WCList[0].WCPercentage}%; background:#378ADD;"></div>
      </div>
    </div>

    <div class="compliance-item">
      <div class="chart-container">
        <canvas id="esicChart"></canvas>
        <div class="chart-center" id="esicText">${dashboard.ESICList[0].esicPercentage}%</div>
      </div>
      <div class="label">
        ESIC Valid<br>
        <small>${dashboard.ESICList[0].activeEsic} / ${dashboard.ESICList[0].totalEsic}</small>
      </div>
      <div class="progress-bar">
        <div class="progress-fill" style="width:${dashboard.ESICList[0].esicPercentage}%; background:#9b59b6;"></div>
      </div>
    </div>
  </div>
</div>

  <!-- Bill Verification Status -->
  <div class="section bills">
  <div class="sec-header">BILL VERIFICATION STATUS</div>
  <div class="bill-chart-container">
    <div class="bill-chart-box">
      <canvas id="billChart"></canvas>
    </div>
    <div class="bill-summary">
      <div><span class="dot green"></span><span class="approved-link" onclick="loadCommonList('/billVerification/listingFilter', 'Bill Verification');">Approved </span><span id="approvedText">${dashboard.billList[0].approvedCount}</span> (${dashboard.billList[0].approvedPercent}%)</div>
      <div><span class="dot orange"></span><span class="approved-link" onclick="loadCommonList('/billVerification/listingFilter', 'Bill Verification');"> Pending Approval</span> <span id="pendingText">${dashboard.billList[0].pendingCount}</span> (${dashboard.billList[0].pendingPercent}%)</div>
      <div><span class="dot red"></span><span class="approved-link" onclick="loadCommonList('/billVerification/listingFilter', 'Bill Verification');"> Rejected </span><span id="rejectedText">${dashboard.billList[0].rejectedCount}</span> (${dashboard.billList[0].rejectedPercent}%)</div>
      <span id="totalText" style="display:none">${dashboard.billList[0].totalCount}</span>
    </div>
  </div>
</div>








  <!-- Quick Actions -->
 <div class="section quick-actions">
  <div class="sec-header">QUICK ACTIONS</div>
  <div class="action-grid">
      <!-- Wage & Compliance Reconciliation -->
      <c:choose>
        <c:when test="${fn:contains(allowedPages, '/contractor/reconciliation/list')}">
          <button type="button" class="action-btn teal"
                  onclick="loadCommonList('/contractor/reconciliation/list', 'Wage & Compliance Reconciliation');">
            <i class="fa-solid fa-scale-balanced icon teal"></i>
            <span>Wage &amp; Compliance Reconciliation</span>
          </button>
        </c:when>
        <c:otherwise>
          <button type="button" class="action-btn teal"
                  onclick="alert('You are not an authorized person to access this page');">
            <i class="fa-solid fa-scale-balanced icon teal"></i>
            <span>Wage &amp; Compliance Reconciliation</span>
          </button>
        </c:otherwise>
      </c:choose>


  <!-- <div class="action-box"> -->
      <button class="action-btn green" onclick="redirectToWorkmenProjectAdd()"><i class="fa-solid fa-user-plus icon green"></i><span>Add Project Workmen</span></button>
      <button class="action-btn navyblue" onclick="redirectToWorkmenQuickAdd()">
    <i class="fa-solid fa-user-clock icon navyblue"></i>
    <span>Add Quick Workmen</span>
</button>

   <!-- </div> -->   
   <button class="action-btn teal" onclick="loadCommonList('/contractworkmen/renewFilter', 'Renew');"><i class="fa-solid fa-rotate-right icon teal"></i><span>Renew Workmen</span></button>
   <!-- <div class="action-box"> -->

      <button class="action-btn red" onclick="loadCommonList('/reports/list', 'Workmen Report');"><i class="fa-solid fa-file-circle-plus icon orange"></i><span>Workmen Report</span></button>
      <button class="action-btn orange" onclick="loadCommonList('/billVerification/listingFilter', 'Bill Verification');"><i class="fa-solid fa-file-invoice-dollar icon blue"></i><span>Verify Bill</span></button>

    <!-- </div> -->
     <!-- <div class="action-box"> -->
       <button class="action-btn blue" onclick="redirectToContractorRenew()"> <i class="fa-solid fa-user-tie icon purple"></i><span>Policy Renewal</span></button>
     <!-- </div> -->
     <!-- <div class="action-box"> -->

       <button  class="action-btn purple" onclick="loadCommonList('/requestor/mFaceRegistration', 'Face Registration');"><i class="fa-solid fa-face-smile icon teal"></i><span>Face Registration</span></button>
       <button class="action-btn teal" onclick="loadCommonList('/requestor/mobilePunchFace', 'Mobile Punch');"><i class="fa-solid fa-mobile-screen-button icon red"></i><span>Mobile Punch</span></button>
       
       <button class="action-btn teal" onclick="loadCommonList('/entryPassStatus/list', 'Gate Pass Status');"><i class="fa-solid fa-id-card icon teal"></i><span>GatePass Status Report</span></button>
     </div>
    </div>
  </div>



 <!-- ROW 4 KPI CARDS -->
  <div class="lastdashboard">  
<div class="kpi-row row4"> 
 
  <div class="kpi-card orange" id="kpi-bills">
    <div class="kpi-content">
      <div class="icon-bg orange"><i class="fa-solid fa-file-invoice-dollar icon"></i></div>
      <div class="kpi-text">
        <div class="lbl">PENDING BILLS</div>
        <div class="num">${dashboard.pendingBills}</div>
        <div class="trend">Across all sites</div>
      </div>
    </div>
  </div>

  <div class="kpi-card red" id="kpi-blacklisted">
    <div class="kpi-content">
      <div class="icon-bg red"><i class="fa-solid fa-user-slash icon"></i></div>
      <div class="kpi-text">
      <c:set var="licenseCount" value="${fn:length(dashboard.blackliestedGPList)}" />
        <div class="lbl"><a href="javascript:void(0)" onclick="openBlackListGPModalPopup()">BLACKLISTED WORKMEN</a></div>
        <div class="num">${licenseCount}</div>
        <div class="trend">Across all sites</div>
      </div>
    </div>
  </div>
</div>
</div>

<div id="blacklistedGPModal" class="modal-overlay">
  <div class="modal-box">
    <div class="modal-header">
      <span>All BlackListed GatePasses</span>
      <span class="close-btn" onclick="closeblacklistedGPModalPopup()">✖</span>
    </div>

    <div class="modal-body">
      <c:choose>
        <c:when test="${not empty dashboard.blackliestedGPList}">
          <table class="expiry-table">
            <thead>
              <tr>
                <th>GatePassId</th>
                <th>Full Name</th>
                <th>Aadhar Number</th>
                <th>Contractor</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="item" items="${dashboard.blackliestedGPList}">
                <tr>
                  <td>${item.gatepassId}</td>
                  <td>${item.fullname}</td>
                  <td>${item.aadharNumber}</td>
                  <td>${item.contractor}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="empty-state">No BlackListed Gatepasses found</div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<div class="modal fade"
     id="chatBotModal"
     tabindex="-1"
     role="dialog"
     aria-labelledby="chatBotModalTitle"
     aria-hidden="true">

    <div class="modal-dialog modal-dialog-centered modal-lg"
         role="document">

        <div class="modal-content chatbot-modal-content">

            <div class="modal-header">

                <h5 class="modal-title" id="chatBotModalTitle">
                    Conkraft AI Assistant
                </h5>

                <button type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-label="Close">

                    <span aria-hidden="true">&times;</span>
                </button>

            </div>

            <div class="modal-body p-0"
                 id="chatBotModalBody">

                <div class="text-center p-5">
                    Loading assistant...
                </div>

            </div>

        </div>

    </div>

</div>