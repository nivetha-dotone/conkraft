<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="db">

    <div class="page-title">${dashboard.roleName} Dashboard</div>

    <!-- KPI SECTION -->
    <div class="sec-label">Key Metrics</div>
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

        <%-- <div class="kpi purple">
            <div class="lbl">Pending Requests</div>
            <div class="num">${dashboard.pendingRequests}</div>
        </div> --%>
    </div>

    <!-- WORKFORCE -->
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
    </div>

    <!-- PLANT WISE CONTRACTOR + WORKMEN -->
    <c:if test="${isHR || isSecurity || isSafety || isMedical || isEIC || isSystemAdmin}">
        <div class="sec-label">Plant Wise Contractor + Workmen</div>

        <div class="card-grid">
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
    </c:if>

    <!-- CONTRACTOR WISE WORKMEN -->
    <c:if test="${isHR || isSecurity || isSafety || isMedical || isSystemAdmin}">
        <div class="sec-label">Contractor Wise Workmen</div>

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
        </div>
    </c:if>

    <!-- ACTIVE WORK ORDERS -->
    <c:if test="${isHR || isSystemAdmin}">
        <div class="sec-label">Active Work Orders</div>
        <div class="card warn">
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
        <div class="sec-label">Police Verification Expiry</div>
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
        <div class="sec-label">PVC Type Summary</div>

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

        <div class="sec-label">Acknowledgement Expiry</div>
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
        <div class="sec-label">Contractor + Department Wise Workmen</div>

        <div class="card-grid">
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
        </div>
    </c:if>

    <!-- SYSTEM ADMIN : BUSINESS TYPE WISE PE COUNT -->
    <c:if test="${isSystemAdmin}">
        <div class="sec-label">Business Type Wise PE Count</div>
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

        <div class="sec-label">PE Wise Contractor Count</div>
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