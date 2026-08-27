<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <script src="resources/js/cms/newdashboard.js"></script>
  <script src="resources/js/cms/dashboard.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.5.1"></script>

<script src="resources/js/cms/workmen.js"></script>
<style>
/* General KPI Card Styling */
/* .kpi-row {
  display: flex;
  justify-content: space-between;
  gap: 20px;
} */

.kpi-row {
  display: grid;
  grid-template-columns: repeat(5, 1fr); /* ✅ 4 KPI + 1 Assistant tile */
  gap: 20px;
  margin-bottom: 20px;
  align-items: stretch;
}

.kpi-card {
  flex: 1;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  height: 130px; /* ✅ uniform height for all cards */
  display: flex;
  align-items: center;
  justify-content: center;
}

/* AI Assistance Card */
.kpi-card.teal {
  border-top: 4px solid #009688;
}

.ai-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(90deg, #6dc7f8, rgb(0 0 0 / 10%));
  border-radius: 8px;
  padding: 10px 14px;
  color: #fff;
  width: 100%;
  height: 100%;
}

.ai-left {
  flex: 1;
}

.ai-title {
  font-size: 15px;
  font-weight: 700;
  letter-spacing: 0.5px;
}

/* Image Styling */
.ai-right {
  flex-shrink: 0;
}

.ai-image {
  width: 45px; /* ✅ very small image */
  height: auto;
  border-radius: 6px;
  object-fit: cover;
}
.quick-actions {
  background: #fff;
  border-radius: 15px;
  padding: 15px;
}

.quick-actions {
  background: #fff;
  border-radius: 10px;
  padding: 15px;
      margin-bottom: 20px;
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
.icon.yellow { color: #e74c3c;; }

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
/* .security-banner {
  background: linear-gradient(90deg, #6dc7f8, rgb(0 0 0 / 10%));
  border-radius: 12px;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 5px;
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
} */

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
/* ///////////////// */
.db {
  width: 102%;
  padding: 0 30px; /* Equal left & right padding for all rows */
  box-sizing: border-box;
  margin-left: -6px;
}

.kpi-row,
.quick-actions-row,
.chart-row {
  display: flex;
  justify-content: space-between;
  align-items: stretch;
  gap: 20px; /* Equal gap between cards */
  margin-bottom: 25px; /* Equal vertical spacing between rows */
}

.kpi-card,
.action-card,
.chart-card {
  flex: 1;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  height: 130px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.section.contractor-workmen {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  padding: 25px 30px; /* ✅ same left/right spacing as Quick Actions */
  margin: 0 2px 17px 25px; /* ✅ adds equal gap from page edges */
  box-sizing: border-box;
  margin-bottom:20px;
}

.section.contractor-workmen .sec-header {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 15px;
  color: #333;
}

.chart-box {
  overflow-x: auto;
  width: 100%;
}

.chart-container {
  min-width: 100%;
  height: 650px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.card.warn {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  padding: 25px 30px; /* internal spacing */
  margin: 0 7px 25px 30px; /* ✅ equal gap from left and right edges */
  box-sizing: border-box;
  overflow-x: auto; /* allows scroll if table overflows */
}

.sec-header {
  font-weight: 600;
  font-size: 14px;
  margin: 0 30px 15px 30px; /* ✅ aligns header with box margins */
  color: #333;
}

.ct {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 500;
  margin-bottom: 15px;
}

.view-all {
  color: #378ADD;
  cursor: pointer;
  font-size: 13px;
  font-weight: 600;
}

.table-wrapper {
  width: 100%;
  overflow-x: auto;
  padding-right: 10px; /* ✅ adds small space at right end */
  box-sizing: border-box;
}

.atbl {
  width: 100%;
  border-collapse: collapse;
  min-width: 100%;
}

.atbl th, .atbl td {
  padding: 10px 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.atbl th {
  background-color: #f9f9f9;
  font-weight: 600;
}
.card.highlight-card {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  padding: 25px 30px; /* internal spacing */
  margin: 0 8px 25px 30px; /* ✅ equal left/right gap like Active Work Orders */
  box-sizing: border-box;
}

.sec-header {
  font-weight: 600;
  font-size: 14px;
  margin: 0 30px 15px 30px; /* ✅ aligns header with box margins */
  color: #333;
}

.ct {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 500;
  margin-bottom: 15px;
}

.view-all {
  color: #378ADD;
  cursor: pointer;
  font-size: 13px;
  font-weight: 600;
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
  margin-top: 10px;
  margin-left: 28px;
    margin-bottom: 10px;
}

.mini-card {
  background: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.mini-title {
  font-weight: 600;
  color: #333;
}

.mini-subtitle {
  font-size: 13px;
  color: #666;
  margin-top: 5px;
}

.mini-value {
  font-size: 14px;
  font-weight: 600;
  color: #E67E22;
  margin-top: 8px;
}

.mini-date {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.empty-state {
  text-align: center;
  color: #777;
  font-size: 14px;
  padding: 20px;
}
.card {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  padding: 25px 30px; /* internal spacing */
  margin: 0 7px 25px 30px; /* ✅ equal left/right gap like Active Work Orders */
  box-sizing: border-box;
  overflow-x: auto;
}

.sec-header {
  font-weight: 600;
  font-size: 14px;
  margin: 0 30px 15px 30px; /* ✅ aligns header with box margins */
  color: #333;
}

.ct {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 500;
  margin-bottom: 15px;
}

.card-grid {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
  padding-right: 10px; /* ✅ adds small space at right end */
  box-sizing: border-box;
}

.mini-card {
  background: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.mini-title {
  font-weight: 600;
  color: #333;
}

.mini-value {
  font-size: 16px;
  font-weight: 600;
  color: #008080;
  margin-top: 8px;
}

.empty-state {
  text-align: center;
  color: #777;
  font-size: 14px;
  padding: 20px;
}

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
}

.banner-right img {
  width: 90px; /* ✅ small image size */
  height: auto;
  border-radius: 6px;
}

/* Responsive behavior */
@media (max-width: 1200px) {
  .kpi-row {
    grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  }
}
/* Modal overlay */
.modal-body {
  max-height: 70vh; /* 70% of viewport height */
  overflow-y: auto;
  padding: 20px;
  box-sizing: border-box;
  max-height: 400px;
}

/* Table container */
.expiry-table-container {
  width: 100%;
  max-height: 60vh; /* responsive height */
  overflow-y: auto;
  overflow-x: auto;
  border: 1px solid #ddd;
  border-radius: 6px;
  margin-top: 10px;
  box-sizing: border-box;
  padding-bottom: 5px; /*  ensures last row border is visible */
}

/* Table styling */
.expiry-table {
  width: 100%;
  border-collapse: collapse;
   border-bottom: 1px solid #ddd; /*  adds closing line */
}

.expiry-table th,
.expiry-table td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: center;
}

.expiry-table th {
  background-color: #f4f4f4;
  font-weight: 600;
}
.expiry-table td {
  background-color: white;
}
.expiry-table tr:nth-child(even) {
  background-color: #fafafa;
}

/* Scrollbar styling */
.expiry-table-container::-webkit-scrollbar {
  width: 8px;
}

.expiry-table-container::-webkit-scrollbar-thumb {
  background-color: #bbb;
  border-radius: 4px;
}

.expiry-table-container::-webkit-scrollbar-track {
  background-color: #f4f4f4;
}

/* Bottom padding for clean look */
.expiry-table-container {
  padding-bottom: 10px;
}

.expiry-table-container {
  scrollbar-gutter: stable; /* keeps space for scrollbar */
}


</style>

</head>
<div class="db">

    <div class="page-title">${dashboard.roleName} Dashboard</div>

    <!-- KPI SECTION -->
    <%-- <div class="sec-label">Key Metrics</div>
    <div class="kpi-row">
        <div class="kpi">
            <div class="lbl">Active Workmen</div>
            <div class="num">${dashboard.activeWorkmen}</div>
        </div>

        <div class="kpi warn">
            <div class="lbl">Active Work Orders</div>
            <div class="num">${dashboard.activeWO}</div>
        </div>

        <div class="kpi danger">
            <div class="lbl">Active LL</div>
            <div class="num">${dashboard.activeLL}</div>
        </div>

        <div class="kpi info">
            <div class="lbl">Active WC</div>
            <div class="num">${dashboard.activeWC}</div>
        </div>

        <div class="kpi purple">
            <div class="lbl">Pending Requests</div>
            <div class="num">${dashboard.pendingRequests}</div>
        </div>
    </div>
 --%>
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
  <!-- AI Assistance Card -->
<div class="security-banner" id="kpi-security-banner">
  <div class="banner-content">
    <div class="banner-left">
      <div class="banner-icon">
        
      </div>
      <div class="banner-text">
        <!-- <div class="title">Securing <span class="highlight">Workforce.</span></div> -->
        <div class="title"> 
       <!--  <a href="javascript:void(0);" onclick="loadCommonList('/showChatBot', 'AI Assistance');"><span>AI ASSISTANCE</span></a> -->
       <a href="javascript:void(0);"
   class="dashboard-tile"
   onclick="openChatBotModal();">

    <div class="tile-content">
        <i class="fa fa-comments"></i>
        <span>Conkraft Assistant</span>
    </div>

   </div>
      </div></a>
    </div>
    <div class="banner-right">
      <img src='resources/img/ai-image.png' alt="Workforce Security" />
    </div>
  </div>
</div> 
  </div>
</div>


  

    <%-- <!-- WORKFORCE -->
    <div class="sec-label">Workforce Distribution</div>

    <div class="chart-row">

        <!-- BAR CHART -->
        <div class="card row">
            <div class="ct">Active workmen by plant</div>
            <div style="height:200px;">
                <canvas id="plantChart"></canvas>
            </div>
        </div>

        <!-- PROGRESS -->
        <div class="card warn">
            <div class="ct">Supervisors per plant</div>

            <div class="prog-list">
                <c:forEach var="plant" items="${dashboard.plantContrWorkmenList}">
                    <c:set var="percent"
                           value="${plant.activeCount > 0 ? (plant.contractorCount * 100) / plant.activeCount : 0}" />

                    <div class="prog-item">
                        <div class="pi-head">
                            <span>${plant.plantName}</span>
                            <span>
                                ${plant.contractorCount} sup /
                                ${plant.activeCount} wkm
                            </span>
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
    </div> --%>
    <div class="row row-3col">
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

    <%-- <!-- PLANT WISE CONTRACTOR + WORKMEN -->
    <c:if test="${isHR || isSecurity || isSafety || isMedical || isEIC || isSystemAdmin}">
        <!-- <div class="sec-label">Plant Wise Contractor + Workmen</div> -->

        <div class="card-grid">
        <div class="sec-header">
      <span>Plant Wise Contractor + Workmen</span>
    </div>
            <c:choose>
                <c:when test="${not empty dashboard.plantContrWorkmenList}">
                    <c:forEach var="p" items="${dashboard.plantContrWorkmenList}">
                        <div class="mini-card teal-card">
                            <div class="mini-title">${p.plantName}</div>
                            <div class="mini-subtitle">Contractors: ${p.contractorCount}</div>
                            <div class="mini-value">${p.activeCount}</div>
                            <div class="mini-date">Workmen Count</div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">No records found</div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if> --%>
    <!-- PLANT WISE CONTRACTOR + WORKMEN -->
<%-- <c:if test="${isHR || isSecurity || isSafety || isMedical || isEIC}">
  <div class="section plant-section">
    <div class="sec-header">
      <span>PLANT WISE CONTRACTOR WORKMEN</span>
    </div>

    <div id="plantWiseCard" class="kpi-card-alt">
      <div class="kpi-content-alt">
        <div class="kpi-title-alt">${dashboard.plantContrWorkmenList[0].plantName}</div>

        <div class="kpi-row-alt">
          <div class="kpi-item-alt">
            <div class="icon-bg-alt orange-bg-alt">
              <i class="fa-solid fa-user-tie"></i>
            </div>
            <div class="kpi-text-alt">
              <span class="kpi-label-alt">Contractors:</span>
              <span class="kpi-number-alt">
                <c:out value="${empty dashboard.plantContrWorkmenList[0].contractorCount ? 0 : dashboard.plantContrWorkmenList[0].contractorCount}" />
              </span>
            </div>
          </div>

          <div class="kpi-item-alt">
            <div class="icon-bg-alt blue-bg-alt">
              <i class="fa-solid fa-users"></i>
            </div>
            <div class="kpi-text-alt">
              <span class="kpi-label-alt">Workmen Count:</span>
              <span class="kpi-number-alt">
                <c:out value="${empty dashboard.plantContrWorkmenList[0].activeCount ? 0 : dashboard.plantContrWorkmenList[0].activeCount}" />
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</c:if> --%>
    
</div>
<div class="row row-3col">
<div class="section quick-actions">
  <div class="sec-header"><span>QUICK ACTIONS</span></div>
  <div class="action-grid">
   <%-- <c:if test="${UserPermission.addRights eq 1 }">
      <button class="action-btn green" onclick="redirectToWorkmenAdd()"><i class="fa-solid fa-user-plus icon green"></i><span>Add Workmen</span></button>
      </c:if> --%>
       <c:if test="${fn:contains(allowedPages, '/contractworkmen/list')}">
            <button class="action-btn green" onclick="loadCommonList('/contractworkmen/list', 'Create');"><i class="fa-solid fa-user-plus icon green"></i><span>View Workmens</span></button>
      </c:if>
      <c:if test="${!fn:contains(allowedPages, '/contractworkmen/list')}">
           <button class="action-btn green" onclick="alert('You are not an authorized person to access this page')"><i class="fa-solid fa-user-plus icon green"></i><span>View Workmens</span></button>
      </c:if>
      <c:if test="${fn:contains(allowedPages, '/data/importExport')}">
           <button class="action-btn teal" onclick="loadCommonList('/data/importExport', 'Master Data Import');"><i class="fa-solid fa-file-import icon teal"></i><span>Import Tools</span></button>
      </c:if>
       <c:if test="${!fn:contains(allowedPages, '/data/importExport')}">
            <button class="action-btn teal" onclick="alert('You are not an authorized person to access this page')"><i class="fa-solid fa-file-import icon teal"></i></i><span>Import Tools</span></button>
      </c:if>
       <c:if test="${fn:contains(allowedPages, '/reports/list')}">
            <button class="action-btn red" onclick="loadCommonList('/reports/list', 'Workmen Report');"><i class="fa-solid fa-file-circle-plus icon orange"></i><span>Workmen Report</span></button>
      </c:if>
       <c:if test="${!fn:contains(allowedPages, '/reports/list')}">
            <button class="action-btn red" onclick="alert('You are not an authorized person to access this page')"><i class="fa-solid fa-file-circle-plus icon orange"></i><span>Workmen Report</span></button>
      </c:if>
      <c:if test="${fn:contains(allowedPages, '/billVerification/listingFilter')}">
            <button class="action-btn orange" onclick="loadCommonList('/billVerification/listingFilter', 'Bill Verification');"><i class="fa-solid fa-file-invoice-dollar icon blue"></i><span>Verify Bill</span></button>
      </c:if>
      <c:if test="${!fn:contains(allowedPages, '/billVerification/listingFilter')}">
            <button class="action-btn orange" onclick="alert('You are not an authorized person to access this page')"><i class="fa-solid fa-file-invoice-dollar icon blue"></i><span>Verify Bill</span></button>
      </c:if>
      <c:if test="${fn:contains(allowedPages, '/contractworkmen/renewFilter')}">
            <button class="action-btn teal" onclick="loadCommonList('/contractworkmen/renewFilter', 'Renew');"><i class="fa-solid fa-rotate-right icon purple"></i><span>Renew Workmen</span></button>
      </c:if>
      <c:if test="${!fn:contains(allowedPages, '/contractworkmen/renewFilter')}">
             <button class="action-btn teal" onclick="alert('You are not an authorized person to access this page')"><i class="fa-solid fa-rotate-right icon purple"></i><span>Renew Workmen</span></button>
       </c:if>
       <c:if test="${fn:contains(allowedPages, '/entryPassStatus/list')}">
            <button class="action-btn teal" onclick="loadCommonList('/entryPassStatus/list', 'Gate Pass Status');"><i class="fa-solid fa-id-card icon teal"></i><span>GatePass Status Report</span></button>
      </c:if>
       <c:if test="${!fn:contains(allowedPages, '/entryPassStatus/list')}">
           <button class="action-btn teal" onclick="alert('You are not an authorized person to access this page')"><i class="fa-solid fa-id-card icon teal"></i><span>GatePass Status Report</span></button>
      </c:if>
      <c:if test="${isHR}">
      <button class="action-btn teal" onclick="openWorkmensRetiredModalPopup()"><i class="fa-solid fa-person-cane icon yellow"></i><span><spring:message code="label.workmenApproachingRetirementAge"/></span></button>
      </c:if>
     </div>
    </div>
</div>
    <!-- CONTRACTOR WISE WORKMEN -->
    <c:if test="${isHR || isSecurity || isSafety || isMedical || isSystemAdmin}">
        <%-- <div class="sec-label">Contractor Wise Workmen</div>

        <div class="card-grid">
            <c:choose>
                <c:when test="${not empty dashboard.contractorWorkmenList}">
                    <c:forEach var="c" items="${dashboard.contractorWorkmenList}">
                        <div class="mini-card blue-card">
                            <div class="mini-title">${c.contractorName}</div>
                            <div class="mini-value">${c.workmenCount}</div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">No records found</div>
                </c:otherwise>
            </c:choose>
        </div> --%>
      <!-- <div class="sec-label">Contractor Wise Workmen</div> -->
      <div class="section contractor-workmen">
<div class="sec-header">
      <span>CONTRACTOR WISE WORKMEN</span>
    </div>
<!-- ✅ Scrollbar appears only if content overflows -->
<div style="overflow-x:auto; width:100%;">
  <div style="min-width:100%; height:650px; display:flex; justify-content:center; align-items:center;">
    <canvas id="contractorWorkmenChart"></canvas>
  </div>
</div>
</div>
<!-- Hidden elements to pass data -->
<div id="contractorWorkmenDataContainer" style="display:none;">
  <c:forEach var="c" items="${dashboard.contractorWorkmenList}">
    <div class="contractorWorkmenItem"
         data-label="${c.contractorName}"
         data-value="${c.workmenCount}"></div>
  </c:forEach>
</div>


        
    </c:if>

 <c:if test="${isHR}">
        <!-- <div class="sec-label">Active Work Orders</div> -->

  <div id="workmensRetiredModal" class="modal-overlay">
  <div class="modal-box">
    <div class="modal-header">
      <span>All Workmens Approaching Retired Age</span>
      <span class="close-btn" onclick="closeWorkmensRetiredModalPopup()">✖</span>
    </div>
    <div class="modal-body">
      <c:choose>
        <c:when test="${not empty dashboard.workmensReachedRetiredAgeList}">
          <table class="expiry-table">
            <thead>
              <tr>
                <th>GatepassId</th>
                <th>FullName</th>
                <th>Date of Birth</th>
                <th>Days Left</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="item" items="${dashboard.workmensReachedRetiredAgeList}">
                <tr>
                  <td>${item.gatepassId}</td>
                  <td>${item.fullname}</td>
                  <td>${item.dateOfBirth}</td>
                  <td>${item.daysLeft}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="empty-state">No records found for Workmens Approaching Retirement Age.</div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
    </c:if>

    <!-- ACTIVE WORK ORDERS -->
    <c:if test="${isHR || isSystemAdmin}">
        <!-- <div class="sec-label">Active Work Orders</div> -->
        <div class="sec-header"><span>ACTIVE WORK ORDERS</span></div>
        <div class="card highlight-card">
            <div class="ct">
                Active Work Orders per Plant
                <span class="view-all" onclick="openCommonPopup('activeWOModal')">View All</span>
            </div>

            <table class="atbl">
                <thead>
                    <tr>
                        <th>Plant</th>
                        <th>WO Number</th>
                        <th>Contractor</th>
                        <th>Workmen</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty dashboard.activeWOList}">
                            <c:forEach var="wo" items="${dashboard.activeWOList}" begin="0" end="1">
                                <tr>
                                    <td>${wo.plantName}</td>
                                    <td>${wo.woNumber}</td>
                                    <td>${wo.contractorName}</td>
                                    <td>${wo.workmenCount}</td>
                                    <td>
                                        <button type="button" onclick="openWO('${wo.woId}','${wo.contractorId}')">View</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5">No records found</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </c:if>

    <!-- HR : POLICE VERIFICATION EXPIRY -->
    <c:if test="${isHR}">
        <!-- <div class="sec-label">Police Verification Expiry</div> -->
        <div class="sec-header"><span>POLICE VERIFICATION EXPIRY</span></div>
        <div class="card highlight-card">
            <div class="ct">
                Gate passes with police verification expiring in next 30 days
                <span class="view-all" onclick="openCommonPopup('hrExpiryModal')">View All</span>
            </div>

            <div class="card-grid">
                <c:choose>
                    <c:when test="${not empty dashboard.expiryList}">
                        <c:forEach var="item" items="${dashboard.expiryList}" begin="0" end="5">
                            <div class="mini-card orange-card">
                                <div class="mini-title">${item.licenseNo}</div>
                                <div class="mini-subtitle">${item.contractorName}</div>
                                <div class="mini-value small-value">${item.daysLeft} days</div>
                                <div class="mini-date">${item.expiryDate}</div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state">No records found</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:if>

    <!-- SECURITY : PVC TYPE -->
    <c:if test="${isSecurity}">
        <!-- <div class="sec-label">PVC Type Summary</div> -->
        <div class="sec-header"><span>PVC TYPE SUMMARY</span></div>

        <div class="card-grid">
            <c:choose>
                <c:when test="${not empty dashboard.pvcTypeList}">
                    <c:forEach var="p" items="${dashboard.pvcTypeList}">
                        <div class="mini-card purple-card">
                            <div class="mini-title">${p.pvcType}</div>
                            <div class="mini-value">${p.totalCount}</div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">No records found</div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- <div class="sec-label">Acknowledgement Expiry</div> -->
        <div class="sec-header"><span>ACKNOWLEDGEMENT EXPIRY</span></div>
        <div class="card highlight-card">
            <div class="ct">
                Acknowledgements expiring in next 7 days
                <span class="view-all" onclick="openCommonPopup('ackExpiryModal')">View All</span>
            </div>

            <div class="card-grid">
                <c:choose>
                    <c:when test="${not empty dashboard.ackExpiryList}">
                        <c:forEach var="item" items="${dashboard.ackExpiryList}" begin="0" end="5">
                            <div class="mini-card red-card">
                                <div class="mini-title">${item.gatePassId}</div>
                                <div class="mini-subtitle">${item.workmanName}</div>
                                <div class="mini-subtitle">${item.contractorName}</div>
                                <div class="mini-value small-value">${item.daysLeft} days</div>
                                <div class="mini-date">${item.expiryDate}</div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state">No records found</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:if>

    <!-- EIC : CONTRACTOR + DEPARTMENT -->
    <c:if test="${isEIC}">
        <!-- <div class="sec-label">Contractor + Department Wise Workmen</div> -->
 <!-- <div class="sec-label" style="overflow-x:auto; width:100%;">Contractor + Department Wise Workmen</div> -->
 <div class="section contractor-workmen">
 <div class="sec-header" style="overflow-x:auto; width:100%;"><span>CONTRACTOR DEPARTMENT WISE WORKMEN</span></div>
  <div class="chart-box" style="height:300px;">
    <canvas id="contractorDeptChart"></canvas>
  </div>
</div>
  <!-- Hidden elements to pass data -->
  <div id="contractorDeptDataContainer" style="display:none;">
    <c:forEach var="d" items="${dashboard.contractorDeptWorkmenList}">
      <div class="contractorDeptItem"
           data-label="${d.contractorName} - ${d.departmentName}"
           data-value="${d.workmenCount}"></div>
    </c:forEach>
  </div>
        <%-- <div class="card-grid">
            <c:choose>
                <c:when test="${not empty dashboard.contractorDeptWorkmenList}">
                    <c:forEach var="d" items="${dashboard.contractorDeptWorkmenList}">
                        <div class="mini-card green-card">
                            <div class="mini-title">${d.contractorName}</div>
                            <div class="mini-subtitle">${d.departmentName}</div>
                            <div class="mini-value">${d.workmenCount}</div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">No records found</div>
                </c:otherwise>
            </c:choose>
        </div> --%>
    </c:if>

    <!-- SYSTEM ADMIN : BUSINESS TYPE WISE PE COUNT -->
    <c:if test="${isSystemAdmin}">
        <!-- <div class="sec-label">Business Type Wise PE Count</div> -->
          <div class="sec-header"><span>BUSINESS TYPE WISE PE COUNT</span></div>
        <div class="card">
            <div class="ct">Business Type-wise PE Count</div>

            <div class="card-grid">
                <c:choose>
                    <c:when test="${not empty dashboard.businessTypePEList}">
                        <c:forEach var="b" items="${dashboard.businessTypePEList}">
                            <div class="mini-card teal-card">
                                <div class="mini-title">${b.businessType}</div>
                                <div class="mini-value">${b.peCount}</div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state">No records found</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
<br>
        <!-- <div class="sec-label">PE Wise Contractor Count</div> -->
          <div class="sec-header"><span>PE WISE CONTRACTOR COUNT</span></div>
        <div class="card">
            <div class="ct">PE-wise Contractor Count</div>

            <div class="card-grid">
                <c:choose>
                    <c:when test="${not empty dashboard.peContractorList}">
                        <c:forEach var="p" items="${dashboard.peContractorList}">
                            <div class="mini-card blue-card">
                                <div class="mini-title">${p.peName}</div>
                                <div class="mini-value">${p.contractorCount}</div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state">No records found</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:if>
</div>

<!-- HR MODAL -->
<div id="hrExpiryModal" class="modal-overlay">
    <div class="modal-box">
        <div class="modal-header">
            <span>Police Verification Expiry List</span>
            <span class="close-btn" onclick="closeCommonPopup('hrExpiryModal')">✖</span>
        </div>
        <div class="modal-body">
            <c:choose>
                <c:when test="${not empty dashboard.expiryList}">
                    <c:forEach var="item" items="${dashboard.expiryList}">
                        <div class="expiry-item">
                            <span class="dot orange"></span>
                            ${item.licenseNo} - ${item.contractorName}
                            <div class="expiry-date">${item.daysLeft} days - ${item.expiryDate}</div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="expiry-item">No records found</div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<!-- SECURITY MODAL -->
<div id="ackExpiryModal" class="modal-overlay">
    <div class="modal-box">
        <div class="modal-header">
            <span>Acknowledgement Expiry List</span>
            <span class="close-btn" onclick="closeCommonPopup('ackExpiryModal')">✖</span>
        </div>
        <div class="modal-body">
            <c:choose>
                <c:when test="${not empty dashboard.ackExpiryList}">
                    <c:forEach var="item" items="${dashboard.ackExpiryList}">
                        <div class="expiry-item">
                            <span class="dot red"></span>
                            ${item.gatePassId} - ${item.workmanName} - ${item.contractorName}
                            <div class="expiry-date">${item.daysLeft} days - ${item.expiryDate}</div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="expiry-item">No records found</div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<!-- ACTIVE WO MODAL -->
<div id="activeWOModal" class="modal-overlay">
    <div class="modal-box modal-lg">
        <div class="modal-header">
            <span>Active Work Orders</span>
            <span class="close-btn" onclick="closeCommonPopup('activeWOModal')">✖</span>
        </div>
        <div class="modal-body">
            <table class="atbl">
                <thead>
                    <tr>
                        <th>Plant</th>
                        <th>WO Number</th>
                        <th>Contractor</th>
                        <th>Workmen</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty dashboard.activeWOList}">
                            <c:forEach var="wo" items="${dashboard.activeWOList}">
                                <tr>
                                    <td>${wo.plantName}</td>
                                    <td>${wo.woNumber}</td>
                                    <td>${wo.contractorName}</td>
                                    <td>${wo.workmenCount}</td>
                                    <td>
                                        <button type="button" onclick="openWO('${wo.woId}','${wo.contractorId}')">View</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5">No records found</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- WO MODAL -->
<div id="woModal" class="modal-overlay child-modal" style="display:none;">
    <div class="modal-box modal-md">
        <div class="modal-header">
            <span>Workmen List</span>
            <span class="close-btn" onclick="closeWOModal()">✖</span>
        </div>

        <div class="modal-body">
            <table class="atbl">
                <thead>
                    <tr>
                        <th>Gatepass Id</th>
                        <th>Aadhar Number</th>
                        <th>Name</th>
                    </tr>
                </thead>
                <tbody id="workmenBody"></tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    function openCommonPopup(id) {
        var el = document.getElementById(id);
        if (el) {
            el.style.display = "flex";
        }
    }

    function closeCommonPopup(id) {
        var el = document.getElementById(id);
        if (el) {
            el.style.display = "none";
        }
    }

    function openWO(woId, contractorId) {
        $.ajax({
            url: '${pageContext.request.contextPath}/dashboard/getWorkmenByWO',
            type: 'GET',
            data: {
                woId: woId,
                contractorId: contractorId
            },
            success: function(response) {
                var rows = '';

                if (response && response.length > 0) {
                    $.each(response, function(i, item) {
                        rows += '<tr>'
                             + '<td>' + (item.gatepassId || '') + '</td>'
                             + '<td>' + (item.aadharNumber || '') + '</td>'
                             + '<td>' + (item.fullname || '') + '</td>'
                             + '</tr>';
                    });
                } else {
                    rows = '<tr><td colspan="3">No records found</td></tr>';
                }

                $('#workmenBody').html(rows);
                openCommonPopup('woModal');
            },
            error: function() {
                $('#workmenBody').html('<tr><td colspan="3">Error loading data</td></tr>');
                openCommonPopup('woModal');
            }
        });
    }

    window.onclick = function(event) {
        var modals = document.getElementsByClassName('modal-overlay');
        for (var i = 0; i < modals.length; i++) {
            if (event.target === modals[i]) {
                modals[i].style.display = 'none';
            }
        }
    };


</script>