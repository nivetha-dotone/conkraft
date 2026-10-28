<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- Read-only fragment for loadCommonList(); requires item, fields, documents model attributes. --%>
<style>
.srr {font-family:'Noto Sans',Arial,sans-serif; padding:14px; color:#374151;}
.srr .srr-header {background:#005151;color:#fff;padding:13px 18px;font-size:20px;font-weight:600;border-radius:4px 4px 0 0;}
.srr .srr-toolbar {display:flex;justify-content:space-between;align-items:center;gap:12px;flex-wrap:wrap;margin:12px 0;}
.srr .srr-tabs {display:flex;gap:6px;overflow-x:auto;border-bottom:2px solid #005151;padding-bottom:0;}
.srr .srr-tab {background:#fff;color:#005151;border:1px solid #005151;border-bottom:0;border-radius:4px 4px 0 0;padding:9px 13px;white-space:nowrap;cursor:pointer;font-size:13px;}
.srr .srr-tab.active {background:#005151;color:#fff;}
.srr .srr-panel {display:none;border:1px solid #d7dfdf;background:#fff;padding:20px 16px;}
.srr .srr-panel.active {display:block;}
.srr .srr-grid {display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:16px 24px;}
.srr .srr-field label {display:block;font-size:12px;color:#777;font-weight:600;margin-bottom:5px;}
.srr .srr-field input,.srr .srr-field textarea {display:block;width:100%;box-sizing:border-box;border:1px solid #ced4da;border-radius:4px;background:#f8faf9;color:#374151;font:13px 'Noto Sans',Arial,sans-serif;padding:7px 9px;min-height:34px;}
.srr .srr-field textarea {min-height:64px;resize:vertical;}
.srr .srr-back {background:#fff;border:1px solid #005151;color:#005151;border-radius:4px;padding:8px 15px;cursor:pointer;}
.srr .srr-docs {width:100%;border-collapse:collapse;font-size:13px;}
.srr .srr-docs th,.srr .srr-docs td {border:1px solid #d9e0e0;text-align:left;padding:10px;color:#333 !important;-webkit-text-fill-color:#333 !important;}
.srr .srr-docs a.srr-view-document {color:#005151 !important;-webkit-text-fill-color:#005151 !important;text-decoration:underline;font-weight:600;}
.srr .srr-docs th {background:#e9f3f2;color:#005151;}
@media(max-width:700px){.srr .srr-grid{grid-template-columns:1fr}.srr{padding:8px}.srr .srr-panel{padding:12px}}
</style>
<div class="srr" id="selfRegReview">
<!--   <div class="srr-toolbar">
    <button type="button" class="srr-back" onclick="selfRegReviewBack()">Back to List</button>
  </div> -->
  <div class="srr-tabs" role="tablist" aria-label="Registration details">
    <button type="button" class="srr-tab active" data-panel="srr-tab1" onclick="selfRegReviewTab(1)">Basic Data</button>
    <button type="button" class="srr-tab" data-panel="srr-tab2" onclick="selfRegReviewTab(2)">Employment Information</button>
    <button type="button" class="srr-tab" data-panel="srr-tab3" onclick="selfRegReviewTab(3)">Other Information</button>
    <button type="button" class="srr-tab" data-panel="srr-tab4" onclick="selfRegReviewTab(4)">Wages</button>
    <button type="button" class="srr-tab" data-panel="srr-tab5" onclick="selfRegReviewTab(5)">Documents</button>
  <button type="button" class="srr-back" onclick="selfRegReviewBack()">Back to List</button>
  </div>
  <section id="srr-tab1" class="srr-panel active"><div class="srr-grid">
    <div class="srr-field"><label>Registration ID</label><input type="text" readonly value="<c:out value='${item.RegistrationId}'/>"/></div>
    <div class="srr-field"><label>Status</label><input type="text" readonly value="<c:out value='${item.Status}'/>"/></div>
    <div class="srr-field"><label>Aadhaar Number</label><input type="text" readonly value="<c:out value='${fields.aadharNumber}'/>"/></div>
    <div class="srr-field"><label>First Name</label><input type="text" readonly value="<c:out value='${fields.firstName}'/>"/></div>
    <div class="srr-field"><label>Last Name</label><input type="text" readonly value="<c:out value='${fields.lastName}'/>"/></div>
    <div class="srr-field"><label>Father / Husband Name</label><input type="text" readonly value="<c:out value='${fields.relationName}'/>"/></div>
    <div class="srr-field"><label>Date of Birth</label><input type="text" readonly value="<c:out value='${fields.dateOfBirth}'/>"/></div>
    <div class="srr-field"><label>Gender</label><input type="text" readonly value="<c:out value='${fields.genderName}'/>"/></div>
    <div class="srr-field"><label>Identification Mark</label><input type="text" readonly value="<c:out value='${fields.idMark}'/>"/></div>
    <div class="srr-field"><label>Mobile Number</label><input type="text" readonly value="<c:out value='${fields.mobileNumber}'/>"/></div>
    <div class="srr-field"><label>Marital Status</label><input type="text" readonly value="<c:out value='${fields.maritalStatus}'/>"/></div>
    <div class="srr-field"><label>Disability</label><input type="text" readonly value="<c:out value='${fields.disability}'/>"/></div>
    <div class="srr-field"><label>Workmen Type</label><input type="text" readonly value="<c:out value='${fields.workmenTypeName}'/>"/></div>
    <div class="srr-field"><label>Address</label><textarea readonly><c:out value="${fields.address}"/></textarea></div>
  </div></section>
  <section id="srr-tab2" class="srr-panel"><div class="srr-grid">
    <div class="srr-field"><label>Principal Employer</label><input type="text" readonly value="<c:out value='${item.PrincipalEmployer}'/>"/></div>
    <div class="srr-field"><label>Contractor</label><input type="text" readonly value="<c:out value='${item.ContractorName}'/>"/></div>
    <div class="srr-field"><label>Work Order</label><input type="text" readonly value="<c:out value='${item.WorkOrderNumber}'/>"/></div>
    <div class="srr-field"><label>Department</label><input type="text" readonly value="<c:out value='${fields.departmentName}'/>"/></div>
    <div class="srr-field"><label>Area</label><input type="text" readonly value="<c:out value='${fields.areaName}'/>"/></div>
    <div class="srr-field"><label>Engineering In Charge</label><input type="text" readonly value="<c:out value='${eicName}'/>"/></div>
    <div class="srr-field"><label>WC / ESIC Reference</label><input type="text" readonly value="<c:out value='${wcEsicName}'/>"/></div>
    <div class="srr-field"><label>Labour Licence Reference</label><input type="text" readonly value="<c:out value='${llName}'/>"/></div>
    <div class="srr-field"><label>Hazardous Area</label><input type="text" readonly value="<c:out value='${fields.hazardousArea}'/>"/></div>
    <div class="srr-field"><label>Access Area</label><input type="text" readonly value="<c:out value='${fields.accessAreaName}'/>"/></div>
    <div class="srr-field"><label>PF Number</label><input type="text" readonly value="<c:out value='${fields.pfNumber}'/>"/></div>
    <div class="srr-field"><label>UAN Number</label><input type="text" readonly value="<c:out value='${fields.uanNumber}'/>"/></div>
    <div class="srr-field"><label>Date of Joining</label><input type="text" readonly value="<c:out value='${fields.doj}'/>"/></div>
    <div class="srr-field"><label>Health Checkup Date</label><input type="text" readonly value="<c:out value='${fields.healthCheckDate}'/>"/></div>
    <div class="srr-field"><label>ESIC Number</label><input type="text" readonly value="<c:out value='${fields.esicNumber}'/>"/></div>
    <div class="srr-field"><label>PF Applicable</label><input type="text" readonly value="<c:out value='${fields.pfApplicable}'/>"/></div>
  </div></section>
  <section id="srr-tab3" class="srr-panel"><div class="srr-grid">
    <div class="srr-field"><label>Blood Group</label><input type="text" readonly value="<c:out value='${fields.bloodGroupIdName}'/>"/></div>
    <div class="srr-field"><label>Accommodation</label><input type="text" readonly value="<c:out value='${fields.accommodation}'/>"/></div>
    <div class="srr-field"><label>Academic Qualification</label><input type="text" readonly value="<c:out value='${fields.academicIdName}'/>"/></div>
    <div class="srr-field"><label>Technical Qualification</label><input type="text" readonly value="<c:out value='${fields.technical}'/>"/></div>
    <div class="srr-field"><label>IFSC Code</label><input type="text" readonly value="<c:out value='${fields.ifscCode}'/>"/></div>
    <div class="srr-field"><label>Bank Account Number</label><input type="text" readonly value="<c:out value='${fields.accountNumber}'/>"/></div>
    <div class="srr-field"><label>Emergency Contact Name</label><input type="text" readonly value="<c:out value='${fields.emergencyName}'/>"/></div>
    <div class="srr-field"><label>Emergency Contact Number</label><input type="text" readonly value="<c:out value='${fields.emergencyNumber}'/>"/></div>
  </div></section>
  <section id="srr-tab4" class="srr-panel"><div class="srr-grid">
    <div class="srr-field"><label>Wage Category</label><input type="text" readonly value="<c:out value='${fields.wageCategoryIdName}'/>"/></div>
    <div class="srr-field"><label>Bonus Payout</label><input type="text" readonly value="<c:out value='${fields.bonusPayoutIdName}'/>"/></div>
    <div class="srr-field"><label>Zone ID</label><input type="text" readonly value="<c:out value='${fields.zoneId}'/>"/></div>
    <div class="srr-field"><label>Basic</label><input type="text" readonly value="<c:out value='${fields.basic}'/>"/></div>
    <div class="srr-field"><label>DA</label><input type="text" readonly value="<c:out value='${fields.da}'/>"/></div>
    <div class="srr-field"><label>HRA</label><input type="text" readonly value="<c:out value='${fields.hra}'/>"/></div>
    <div class="srr-field"><label>Washing Allowance</label><input type="text" readonly value="<c:out value='${fields.washingAllowance}'/>"/></div>
    <div class="srr-field"><label>Other Allowance</label><input type="text" readonly value="<c:out value='${fields.otherAllowance}'/>"/></div>
    <div class="srr-field"><label>Uniform Allowance</label><input type="text" readonly value="<c:out value='${fields.uniformAllowance}'/>"/></div>
    <div class="srr-field"><label>PF Cap</label><input type="text" readonly value="<c:out value='${fields.pfCap}'/>"/></div>
  </div></section>
  <section id="srr-tab5" class="srr-panel">
    <div class="srr-grid" style="margin-bottom:15px">
      <div class="srr-field"><label>Police Verification Date</label><input type="text" readonly value="<c:out value='${fields.policeVerificationDate}'/>"/></div>
      <div class="srr-field"><label>Comments</label><textarea readonly><c:out value="${fields.comments}"/></textarea></div>
    </div>
    <table class="srr-docs no-dt"><thead><tr><th>Document Type</th><th>Stored File</th><th>Action</th></tr></thead><tbody>
      <c:forEach var="doc" items="${documents}"><tr>
        <td><c:out value="${doc.DocumentType}"/></td>
        <td><c:out value="${doc.StoredFileName}"/></td>
        <td><a class="srr-view-document" target="_blank" rel="noopener noreferrer"
          href="${pageContext.request.contextPath}/self-registration/review/document/${doc.DocumentId}">View</a></td>
      </tr></c:forEach>
      <c:if test="${empty documents}"><tr><td colspan="3">No documents available</td></tr></c:if>
    </tbody></table>
    <c:if test="${item.Status eq 'REJECTED'}">
      <div class="srr-field" style="margin-top:20px">
        <label style="color:#b42318;font-weight:700">Rejection Reason</label>
        <textarea readonly style="min-height:85px"><c:out value="${item.ReviewComments}"/></textarea>
      </div>
    </c:if>
  </section>
  <c:if test="${item.Status eq 'SUBMITTED' or item.Status eq 'UNDER_REVIEW'}">
    <div style="margin:18px 0;padding:15px;border:1px solid #d9e4e4;border-radius:8px">
      <label for="srrRejectReason" style="display:block;font-weight:600;margin-bottom:7px">Rejection Reason (required)</label>
      <textarea id="srrRejectReason" maxlength="1000" rows="3" style="width:100%;padding:10px;color:#222"></textarea>
      <button type="button" id="srrAcceptButton" onclick="selfRegAccept()"
        style="margin-right:12px;background:#005151;color:white;border:0;border-radius:5px;padding:10px 20px;cursor:pointer">Accept &amp; Create Draft</button>
      <button type="button" id="srrRejectButton" onclick="selfRegReject()"
        style="margin-top:10px;background:#b42318;color:white;border:0;border-radius:5px;padding:10px 20px;cursor:pointer">Reject Registration</button>
      <span id="srrRejectMessage" role="status" style="margin-left:12px"></span>
    </div>
  </c:if>
</div>
<script>
async function selfRegAccept() {
    if (!confirm('Accept this registration and create a Gate Pass draft?')) return;
    const btn = document.getElementById('srrAcceptButton');
    const msg = document.getElementById('srrRejectMessage');
    btn.disabled = true;
    msg.textContent = 'Creating draft...';
    try {
        const params = new URLSearchParams();
        params.set('registrationId', '${item.RegistrationId}');
        const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
        const headers = {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'};
        if (csrfToken && csrfHeader) headers[csrfHeader] = csrfToken;
        const response = await fetch('${pageContext.request.contextPath}/self-registration/review/accept', {
            method:'POST', credentials:'same-origin', headers, body:params.toString()
        });
        if (!response.ok) throw new Error('Draft creation failed (HTTP ' + response.status + '): ' + (await response.text()).slice(0,200));
        const data = await response.json();
        alert('Draft created successfully. Transaction ID: ' + data.transactionId);
        selfRegReviewBack();
    } catch (error) {
        msg.textContent = error.message;
        btn.disabled = false;
    }
}
async function selfRegReject() {
    const reason = document.getElementById('srrRejectReason').value.trim();
    const msg = document.getElementById('srrRejectMessage');
    const button = document.getElementById('srrRejectButton');
    if (!reason) { msg.textContent = 'Please enter a rejection reason.'; return; }
    if (!confirm('Reject this registration?')) return;
    button.disabled = true;
    msg.textContent = 'Submitting...';
    try {
        const params = new URLSearchParams();
        params.set('registrationId', '${item.RegistrationId}');
        params.set('reason', reason);
        const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
        const headers = {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'};
        if (csrfToken && csrfHeader) headers[csrfHeader] = csrfToken;
        const response = await fetch('${pageContext.request.contextPath}/self-registration/review/reject', {
            method:'POST', credentials:'same-origin', headers, body:params.toString()
        });
        if (!response.ok) { const data = await response.text(); throw new Error('Request failed (' + response.status + ') ' + data.slice(0,120)); }
        alert('Registration rejected successfully.');
        selfRegReviewBack();
    } catch (error) { msg.textContent = error.message; button.disabled = false; }
}
function selfRegReviewTab(index) {
    const root = document.getElementById('selfRegReview');
    if (!root) return;
    root.querySelectorAll('.srr-tab').forEach(btn =>
        btn.classList.toggle('active', btn.dataset.panel === 'srr-tab' + index));
    root.querySelectorAll('.srr-panel').forEach(panel =>
        panel.classList.toggle('active', panel.id === 'srr-tab' + index));
}
function selfRegReviewBack() {
    // loadCommonList() in this application prepends the /CWFM context path.
    if (typeof loadCommonList === 'function') {
        loadCommonList('/self-registration/review');
    } else {
        window.location.href = '${pageContext.request.contextPath}/self-registration/review';
    }
}
</script>
