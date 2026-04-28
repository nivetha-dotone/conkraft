 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div class="db">

  <!-- KPI -->
  <div class="sec-label">Key Metrics</div>

  <div class="kpi-row">
    <div class="kpi">
      <div class="lbl">Active Workmen</div>
      <div class="num">${dashboard.activeWorkmen}</div>
    </div>

    <div class="kpi warn">
      <div class="lbl">Active WO</div>
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
      <div class="lbl">Active ESIC</div>
      <div class="num">${dashboard.activeESIC}</div>
    </div>
  </div>

  <!-- WORKFORCE -->
 <div class="sec-label">Workforce Distribution</div>

  <div class="chart-row">

    <!-- 🔵 BAR CHART -->
    <div class="card row">
      <div class="ct">Active workmen by plant</div>
      <div style="height:200px;">
        <canvas id="plantChart"></canvas>
      </div>
    </div>

   <%--  <!-- 🟣 DONUT CHART -->
    <div class="card">
      <div class="ct">Workmen status breakdown</div>
      <div style="height:200px;">
        <canvas id="statusChart"></canvas>
      </div>
    </div> --%>

    <!-- PROGRESS -->
   <div class="card warn">
  <div class="ct">Supervisors per plant</div>

  <div class="prog-list">

    <c:forEach var="plant" items="${dashboard.plantContrWorkmenList}">

      <!-- Calculate % safely -->
      <c:set var="percent"
             value="${plant.activeCount > 0 ? (plant.contractorCount * 100) / plant.activeCount : 0}" />

      <div class="prog-item">

        <!-- HEADER -->
        <div class="pi-head">
          <span>${plant.plantName}</span>
          <span>
            ${plant.contractorCount} sup /
            ${plant.activeCount} wkm
          </span>
        </div>

        <!-- PROGRESS BAR -->
        <div class="prog-track">

          <div class="prog-fill"
               style="width:${percent}%;
               
               <c:choose>
                 <c:when test='${percent > 70}'>background:#2ecc71;</c:when>
                 <c:when test='${percent > 40}'>background:#378ADD;</c:when>
                 <c:otherwise>background:#f39c12;</c:otherwise>
               </c:choose>
               
               ">
          </div>

        </div>

      </div>

    </c:forEach>

  </div>
</div>

  </div>  <div class="chart-row">

  <!-- EXPIRY -->
  <!-- 🔹 CARD -->
<div class="card expiry-card danger">

    <div class="ct">
        License expiry alerts 
        <span class="view-all" onclick="openExpiryPopup()">View All</span>
   

    <!-- TOP 3 -->
    <div class="expiry-preview">
        <c:forEach var="item" items="${dashboard.expiryList}" begin="0" end="5">
            <div class="expiry-item">
                <span class="dot red"></span>
                ${item.type} — ${item.licenseNo} — ${item.contractorName}
                <div class="expiry-date">
                    ${item.daysLeft} days — ${item.expiryDate}
                </div>
            </div>
        </c:forEach>
    </div>
 </div>
</div>

</div> 
<!-- 🔥 MODAL -->
<div id="expiryModal" class="modal-overlay">

    <div class="modal-box">

        <!-- HEADER -->
        <div class="modal-header">
            <span>All Expiring Licenses</span>
            <span class="close-btn" onclick="closeExpiryPopup()">✖</span>
        </div>

        <!-- BODY -->
        <div class="modal-body">

            <c:forEach var="item" items="${dashboard.expiryList}">
                <div class="expiry-item">
                    <span class="dot red"></span>
                    ${item.type} — ${item.licenseNo} — ${item.contractorName}
                    <div class="expiry-date">
                        ${item.daysLeft} days — ${item.expiryDate}
                    </div>
                </div>
            </c:forEach>

        </div>

    </div>

</div>
  <!-- ACTIVE WO -->
  <div class="sec-label">Active Work Orders</div>

  <div class="card info">
    <table class="atbl">
      <thead>
        <tr>
          <th>WO Number</th>
          <th>Contractor</th>
          <th>Workmen</th>
          <th>Action</th>
        </tr>
      </thead>

      <tbody>
        <c:forEach var="wo" items="${dashboard.activeWOList}">
          <tr>
            <td>${wo.woNumber}</td>
            <td>${wo.contractorName}</td>
            <td>${wo.workmenCount}</td>
            <td>
              <button onclick="openWO(${wo.woId},${wo.contractorId })">View</button>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

</div>

<!-- 🔥 POPUP -->
<div id="woModal" class="modal-box"
     style="display:none; position:fixed; top:20%; left:30%; width:40%; z-index:1000;">

    <div class="modal-header">Workmen List  <span class="close-btn" onclick="$('#woModal').hide()">✖</span></div>

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

    <!-- <button onclick="$('#woModal').hide()">Close</button> -->
   
</div>

