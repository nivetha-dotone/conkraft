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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <script src="resources/js/cms/newdashboard.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="resources/js/cms/workmen.js"></script>
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

.sec-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  margin-bottom: 10px;
  color: #333;
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

.security-banner {
  background: linear-gradient(90deg, #004d40, #00796b);
  border-radius: 12px;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
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
  gap: 15px;
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
  height: 103px;;
  opacity: 0.95;
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
  color: #555;
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
</div>



<!-- ROW 2: Workforce Distribution + Supervisor Coverage + Alerts -->
<!-- ROW 2: Workforce Distribution + Supervisor Coverage + Alerts -->
<div class="row row-3col">

  <!-- Workforce Distribution -->
  <div class="section chart-section">
    <div class="sec-header">
      <span>WORKFORCE DISTRIBUTION</span>
      <select>
        <option>All Plants</option>
      </select>
    </div>
    <div  style="height:200px;" class="chart-box">
      <canvas id="plantChart"></canvas>
    </div>
  </div>

  <!-- Supervisor Coverage -->
<div class="section coverage-section">
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
</div>

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
<div class="section alerts">
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
  </div>

  <!-- WORK ORDERS -->
  <c:set var="workOrderStatus"
         value="${dashboard.expiredworkorders <= 30 ? 'low' :
                 (dashboard.expiredworkorders <= 60 ? 'medium' : 'high')}" />

  <div class="alert-item ${workOrderStatus}">
    <div class="alert-left">
       <div class="icon-bg orange"><i class="fa-solid fa-triangle-exclamation"></i></div>
      <div class="alert-text">
        <div class="alert-title">${dashboard.expiredworkorders} Work Orders Expiring</div>
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
  <c:set var="gatePassStatus"
         value="${dashboard.expiredgatepasses <= 30 ? 'low' :
                 (dashboard.expiredgatepasses <= 60 ? 'medium' : 'high')}" />

  <div class="alert-item ${gatePassStatus}">
    <div class="alert-left">
      <div class="icon-bg purple"><i class="fa-solid fa-ban"></i></div>
      <div class="alert-text">
        <div class="alert-title">${dashboard.expiredgatepasses} Gate Passes Expiring</div>
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

  <!-- PF CHALLAN -->
<%--   <c:set var="gatePassStatus"
         value="${dashboard.expiredgatepasses <= 30 ? 'low' :
                 (dashboard.expiredgatepasses <= 60 ? 'medium' : 'high')}" />
                 <div class="alert-item ${gatePassStatus}">
    <div class="alert-left">
      <div class="icon-bg purple"><i class="fa-solid fa-ban"></i></div>
      <div class="alert-text">
        <div class="alert-title">${dashboard.expiredgatepasses} Gate Passes Expiring</div>
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
  </div> --%>
  
  <!-- <div class="alert-item medium">
    <div class="alert-left">
      <div class="icon-bg blue"><i class="fa-solid fa-file-invoice"></i></div>
      <div class="alert-text">
        <div class="alert-title">PF Challan Pending</div>
        <div class="alert-sub">4 contractors pending submission</div>
      </div>
    </div>
    <div class="alert-right medium-label">Medium</div>
  </div> -->
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
      <div><span class="dot green"></span> Approved <span id="approvedText">${dashboard.billList[0].approvedCount}</span> (${dashboard.billList[0].approvedPercent}%)</div>
      <div><span class="dot orange"></span> Pending Approval <span id="pendingText">${dashboard.billList[0].pendingCount}</span> (${dashboard.billList[0].pendingPercent}%)</div>
      <div><span class="dot red"></span> Rejected <span id="rejectedText">${dashboard.billList[0].rejectedCount}</span> (${dashboard.billList[0].rejectedPercent}%)</div>
      <span id="totalText" style="display:none">${dashboard.billList[0].totalCount}</span>
    </div>
  </div>
</div>








  <!-- Quick Actions -->
 <div class="section quick-actions">
  <div class="sec-header">QUICK ACTIONS</div>
  <div class="action-grid">
  <div class="action-box">
      <button class="action-btn green" onclick="redirectToWorkmenAdd()"><i class="fa-solid fa-user-plus icon green"></i></i><span>Add Workmen</span></button>
   </div>   
   <div class="action-box">
      <button class="action-btn red" onclick="loadCommonList('/reports/list', 'Workmen Report');"><i class="fa-solid fa-file-circle-plus icon orange"></i><span>Workmen Report</span></button>
    </div>
    <div class="action-box">  
      <button class="action-btn orange" onclick="loadCommonList('/billVerification/listingFilter', 'Bill Verification');"><i class="fa-solid fa-file-invoice-dollar icon blue"></i><span>Verify Bill</span></button>
    </div>
     <div class="action-box">
       <button class="action-btn blue" onclick="redirectToContractorRenew()"> <i class="fa-solid fa-user-tie icon purple"></i><span>Add Contractor</span></button>
     </div>
     <div class="action-box">
       <button  class="action-btn purple" onclick="loadCommonList('/requestor/mFaceRegistration', 'Face Registration');"><i class="fa-solid fa-face-smile icon teal"></i><span>Face Registration</span></button>
     </div>
     <div class="action-box">
       <button class="action-btn teal" onclick="loadCommonList('/requestor/mobilePunchFace', 'Mobile Punch');"><i class="fa-solid fa-mobile-screen-button icon red"></i><span>Mobile Punch</span></button>
     </div>
    </div>
  </div>


</div>
  



 <!-- ROW 4 KPI CARDS -->
<div class="kpi-row row4">
  <!-- <div class="kpi-card green" id="kpi-contractors">
    <div class="kpi-content">
      <div class="icon-bg green"><i class="fa-solid fa-user-tie icon"></i></div>
      <div class="kpi-text">
        <div class="lbl">TOTAL CONTRACTORS</div>
        <div class="num">11</div>
        <div class="trend">↑ 3% vs last month</div>
      </div>
    </div>
  </div>

  <div class="kpi-card blue" id="kpi-gatepasses">
    <div class="kpi-content">
      <div class="icon-bg blue"><i class="fa-solid fa-id-card icon"></i></div>
      <div class="kpi-text">
        <div class="lbl">TOTAL GATE PASSES</div>
        <div class="num">11</div>
        <div class="trend">↑ 15.8% vs last month</div>
      </div>
    </div>
  </div> -->

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
        <div class="lbl">BLACKLISTED WORKMEN</div>
        <div class="num">${dashboard.blackliestedGP}</div>
        <div class="trend">Across all sites</div>
      </div>
    </div>
  </div>

<div class="security-banner" id="kpi-security-banner">
  <div class="banner-content">
    <div class="banner-left">
      <div class="banner-icon">
        
      </div>
      <div class="banner-text">
        <!-- <div class="title">Securing <span class="highlight">Workforce.</span></div> -->
        <div class="title"> AI ASSISTENCE</div>
      </div>
    </div>
    <div class="banner-right">
      <img src='resources/img/ai-image.png' alt="Workforce Security" />
    </div>
  </div>
</div>

 <!--  <div class="kpi-card teal" id="kpi-ai">
  <div class="kpi-content">
    <div class="ai-image-box">
      <img src="resources/img/ai-image.png" alt="AI Assistance" class="ai-image">
      <div class="ai-overlay">AI Assistance</div>
    </div>
  </div>
</div> -->

</div>
  </div>
  <!-- SECURITY WORKFORCE BANNER -->
<!-- <div class="security-banner" id="kpi-security-banner">
  <div class="banner-content">
    <div class="banner-left">
      <div class="banner-icon">
        <i class="fa-solid fa-shield"></i>
      </div>
      <div class="banner-text">
        <div class="title">Securing <span class="highlight">Workforce.</span></div>
        <div class="subtitle">Empowering Enterprises.</div>
      </div>
    </div>
    <div class="banner-right">
      <img src='resources/img/workforce-security.png' alt="Workforce Security" />
    </div>
  </div>
</div>  -->

  <!-- COMPLIANCE CALENDAR -->
  <!-- <div class="section calendar">
    <div class="sec-header">Compliance Calendar</div>
    <div class="calendar-box">
      <div class="date">July 2026</div>
      <div class="desc">Stay Ahead of Compliance</div>
      <button>Open Calendar</button>
    </div>
  </div> -->

