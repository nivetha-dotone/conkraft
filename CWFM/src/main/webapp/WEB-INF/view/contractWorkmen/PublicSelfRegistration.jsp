<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Workman Self Registration | Conkraft</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/cmsstyles.css">
<style>
/* Scoped styling: preserves Conkraft teal #005151, Noto Sans, 14px text. */
.sr-public{font-family:'Noto Sans',sans-serif;font-size:14px;color:#343a40;max-width:1100px;margin:auto;padding:12px}
.sr-public *{box-sizing:border-box}.sr-header{background:#005151;color:#fff;padding:16px;text-align:center;font-weight:bold;font-size:17px}
.sr-tabs{display:flex;gap:8px;overflow-x:auto;padding:12px 0;scrollbar-width:thin}.sr-tab{flex:0 0 auto;min-height:44px;background:white;border:2px solid #005151;color:#005151;border-radius:5px;padding:9px 14px;font:inherit;font-weight:600;cursor:pointer}.sr-tab[aria-selected="true"]{background:#005151;color:white}
.sr-panel{border:1px solid #d5dede;border-radius:6px;background:white;padding:18px}.sr-panel[hidden]{display:none}.sr-grid{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:16px 22px}.sr-field{min-width:0}.sr-field label{display:block;font-size:14px;color:#777;font-weight:600;margin-bottom:6px}.sr-field input:not([type=checkbox]),.sr-field select,.sr-field textarea{width:100%;min-height:42px;border:1px solid #cbd3d8;border-radius:4px;padding:9px;font:inherit;background:white}.sr-field textarea{min-height:90px}.sr-field input[readonly]{background:#f3f6f6}.sr-required{color:#d22}.sr-help{font-size:12px;color:#777}.sr-actions{display:flex;justify-content:space-between;gap:10px;margin-top:18px}.sr-btn{min-height:44px;padding:10px 20px;border:1px solid #005151;border-radius:5px;background:white;color:#005151;font:inherit;font-weight:bold;cursor:pointer}.sr-primary{background:#005151;color:white}.sr-btn:disabled{opacity:.5;cursor:not-allowed}.sr-alert{padding:12px;border:1px solid #e6c78b;background:#fff7e9;margin:12px 0}
@media(max-width:640px){.sr-public{padding:8px}.sr-grid{grid-template-columns:1fr;gap:14px}.sr-panel{padding:14px}.sr-actions .sr-btn{flex:1}.sr-tab{padding:10px 12px}}
</style>
<style>
    html,
    body {
        height: auto !important;
        min-height: 100% !important;
        overflow-y: auto !important;
        overflow-x: hidden !important;
    }

    .sr-shell {
        height: auto !important;
        max-height: none !important;
        overflow: visible !important;
    }

    .sr-card,
    .sr-card-body {
        height: auto !important;
        max-height: none !important;
    }

    @media (max-width: 640px) {
        html,
        body {
            -webkit-overflow-scrolling: touch;
        }
    }
</style>
</head><body>
<header class="sr-header">CONKRAFT | Workman Self Registration</header>
<main class="sr-public"><p>Complete all five sections. Employment details are supplied by your contractor.</p>
<c:if test="${empty registrationLink}"><div class="sr-alert">A valid registration link is required to access this form.</div></c:if>
<form id="publicRegistrationForm" method="post" enctype="multipart/form-data" autocomplete="off">
<input type="hidden" name="registrationToken" value="<c:out value='${registrationToken}'/>">
<c:if test="${not empty _csrf}"><input type="hidden" name="${_csrf.parameterName}" value="<c:out value='${_csrf.token}'/>" /></c:if>
<div class="sr-tabs" role="tablist" aria-label="Registration sections">
<button type="button" class="sr-tab" role="tab" aria-selected="true" aria-controls="sr-panel-0" id="sr-tab-0" onclick="srGo(0)">Basic Data</button>
<button type="button" class="sr-tab" role="tab" aria-selected="false" aria-controls="sr-panel-1" id="sr-tab-1" onclick="srGo(1)">Employment Information</button>
<button type="button" class="sr-tab" role="tab" aria-selected="false" aria-controls="sr-panel-2" id="sr-tab-2" onclick="srGo(2)">Other Information</button>
<button type="button" class="sr-tab" role="tab" aria-selected="false" aria-controls="sr-panel-3" id="sr-tab-3" onclick="srGo(3)">Wages</button>
<button type="button" class="sr-tab" role="tab" aria-selected="false" aria-controls="sr-panel-4" id="sr-tab-4" onclick="srGo(4)">Documents</button>
</div>
<section class="sr-panel" id="sr-panel-0" role="tabpanel" aria-labelledby="sr-tab-0" ><h3>Basic Data</h3><div class="sr-grid">
<div class="sr-field"><label for="aadharNumber">Aadhaar Number <span class="sr-required">*</span></label><input type="text" id="aadharNumber" name="aadharNumber" inputmode="numeric" pattern="[0-9]{12}" maxlength="12" required></div>
<div class="sr-field"><label for="firstName">First Name <span class="sr-required">*</span></label><input type="text" id="firstName" name="firstName" required></div>
<div class="sr-field"><label for="lastName">Last Name <span class="sr-required">*</span></label><input type="text" id="lastName" name="lastName" required></div>
<div class="sr-field"><label for="relationName">Father / Spouse Name </label><input type="text" id="relationName" name="relationName"></div>
<div class="sr-field"><label for="dateOfBirth">Date of Birth <span class="sr-required">*</span></label><input type="date" id="dateOfBirth" name="dateOfBirth" required></div>
<div class="sr-field"><label for="gender">Gender <span class="sr-required">*</span></label><select id="gender" name="gender" required><option value="">Select Gender</option><c:forEach var="option" items="${GenderOptions}"><option value="${option.gmId}"><c:out value="${option.gmName}"/></option></c:forEach></select></div>
<div class="sr-field"><label for="idMark">Identification Mark </label><input type="text" id="idMark" name="idMark"></div>
<div class="sr-field"><label for="mobileNumber">Mobile Number <span class="sr-required">*</span></label><input type="tel" id="mobileNumber" name="mobileNumber" inputmode="numeric" pattern="[6-9][0-9]{9}" maxlength="10" required></div>
<div class="sr-field"><label for="maritalStatus">Marital Status </label><select id="maritalStatus" name="maritalStatus"><option value="">Select Marital Status</option><option value="Married">Married</option><option value="Unmarried">Unmarried</option></select></div>
<div class="sr-field"><label for="disability">Disability </label><select id="disability" name="disability"><option value="">Select Disability</option><option value="Yes">Yes</option><option value="No">No</option></select></div>
<div class="sr-field"><label for="workmenType">Workman Type </label><select id="workmenType" name="workmenType"><option value="">Select Workman Type</option><c:forEach var="option" items="${WorkmenType}"><option value="${option.gmId}"><c:out value="${option.gmName}"/></option></c:forEach></select></div>
<div class="sr-field"><label for="address">Address <span class="sr-required">*</span></label><textarea id="address" name="address" required></textarea></div>
</div></section>
<section class="sr-panel" id="sr-panel-1" role="tabpanel" aria-labelledby="sr-tab-1" hidden><h3>Employment Information</h3><div class="sr-grid">
<div class="sr-field"><label for="principalEmployer">Principal Employer <span class="sr-required">*</span></label><input id="principalEmployer" readonly value="<c:out value='${employmentDetails.peName}'/>" /></div>
<div class="sr-field"><label for="contractor">Contractor <span class="sr-required">*</span></label><input id="contractor" readonly value="<c:out value='${employmentDetails.contractorName}'/>" /></div>
<div class="sr-field"><label for="workorder">Work Order <span class="sr-required">*</span></label><input id="workorder" readonly value="<c:out value='${employmentDetails.woNumber}'/>" /></div>
<div class="sr-field"><label for="department">Department <span class="sr-required">*</span></label><input id="department" readonly value="<c:out value='${selectedDepartmentId}'/>" /></div>
<div class="sr-field"><label for="subdepartment">Area <span class="sr-required">*</span></label><input id="subdepartment" readonly value="<c:out value='${selectedAreaId}'/>" /></div>
<div class="sr-field"><label for="eic">Engineering-in-Charge </label><input id="eic" readonly value="<c:out value='${selectedEicName}'/>" /></div>
<div class="sr-field"><label for="trade">Trade </label><input id="trade" readonly value="" /></div>
<div class="sr-field"><label for="skill">Skill </label><input id="skill" readonly value="" /></div>
<div class="sr-field"><label for="wcEsicNo">WC Policy / ESIC Reg Number <span class="sr-required">*</span></label><input id="wcEsicNo" readonly value="<c:out value='${employmentDetails.wcEsicNo}'/>" /></div>
<div class="sr-field"><label for="llNo">Labour Licence Number </label><input id="llNo" readonly value="<c:out value='${employmentDetails.llNo}'/>" /></div>
<div class="sr-field"><label for="hazardousArea">Hazardous Area <span class="sr-required">*</span></label><select id="hazardousArea" name="hazardousArea" required><option value="">Select Hazardous Area</option><option value="Yes">Yes</option><option value="No">No</option></select></div>
<div class="sr-field"><label for="accessArea">Access Area </label><select id="accessArea" name="accessArea"><option value="">Select Access Area</option><c:forEach var="option" items="${AccessArea}"><option value="${option.gmId}"><c:out value="${option.gmName}"/></option></c:forEach></select></div>
<div class="sr-field"><label for="pfNumber">PF Number </label><input type="text" id="pfNumber" name="pfNumber"></div>
<div class="sr-field"><label for="uanNumber">UAN Number </label><input type="text" id="uanNumber" name="uanNumber"></div>
<div class="sr-field"><label for="doj">Date of Joining <span class="sr-required">*</span></label><input type="date" id="doj" name="doj" required></div>
<div class="sr-field"><label for="healthCheckDate">Health Check Date </label><input type="date" id="healthCheckDate" name="healthCheckDate"></div>
<div class="sr-field"><label for="esicNumber">ESIC Number </label><input type="text" id="esicNumber" name="esicNumber"></div>
<div class="sr-field"><label for="pfApplicable">PF Applicable </label><input type="checkbox" id="pfApplicable" name="pfApplicable" value="true"></div>
</div></section>
<section class="sr-panel" id="sr-panel-2" role="tabpanel" aria-labelledby="sr-tab-2" hidden><h3>Other Information</h3><div class="sr-grid">
<div class="sr-field"><label for="bloodGroupId">Blood Group <span class="sr-required">*</span></label><select id="bloodGroupId" name="bloodGroupId" required><option value="">Select Blood Group</option><c:forEach var="option" items="${BloodGroup}"><option value="${option.gmId}"><c:out value="${option.gmName}"/></option></c:forEach></select></div>
<div class="sr-field"><label for="accommodation">Accommodation <span class="sr-required">*</span></label><select id="accommodation" name="accommodation" required><option value="">Select Accommodation</option><option value="Yes">Yes</option><option value="No">No</option></select></div>
<div class="sr-field"><label for="academicId">Academic Qualification </label><select id="academicId" name="academicId"><option value="">Select Academic Qualification</option><c:forEach var="option" items="${Academics}"><option value="${option.gmId}"><c:out value="${option.gmName}"/></option></c:forEach></select></div>
<div class="sr-field"><label for="technical">Technical Qualification</label><select id="technical" name="technical"><option value="">Select Technical Qualification</option><option value="Technical">Technical</option><option value="Non Technical">Non Technical</option></select></div>
<div class="sr-field"><label for="ifscCode">IFSC Code </label><input type="text" id="ifscCode" name="ifscCode"></div>
<div class="sr-field"><label for="accountNumber">Bank Account Number </label><input type="text" id="accountNumber" name="accountNumber"></div>
<div class="sr-field"><label for="emergencyName">Emergency Contact Name </label><input type="text" id="emergencyName" name="emergencyName"></div>
<div class="sr-field"><label for="emergencyNumber">Emergency Contact Number </label><input type="tel" id="emergencyNumber" name="emergencyNumber" inputmode="numeric"></div>
</div></section>
<section class="sr-panel" id="sr-panel-3" role="tabpanel" aria-labelledby="sr-tab-3" hidden><h3>Wages</h3><div class="sr-grid">
<div class="sr-field"><label for="wageCategoryId">Workman Wage Category <span class="sr-required">*</span></label><select id="wageCategoryId" name="wageCategoryId" required><option value="">Select Workman Wage Category</option><c:forEach var="option" items="${WageCategory}"><option value="${option.gmId}"><c:out value="${option.gmName}"/></option></c:forEach></select></div>
<div class="sr-field"><label for="bonusPayoutId">Bonus Payout <span class="sr-required">*</span></label><select id="bonusPayoutId" name="bonusPayoutId" required><option value="">Select Bonus Payout</option><c:forEach var="option" items="${BonusPayout}"><option value="${option.gmId}"><c:out value="${option.gmName}"/></option></c:forEach></select></div>
<div class="sr-field"><label for="zoneId">Zone <span class="sr-required">*</span></label><select id="zoneId" name="zoneId" required><option value="">Select Zone</option><c:forEach var="option" items="${Zones}"><option value="${option.zoneId}"><c:out value="${option.zoneName}"/></option></c:forEach></select></div>
<div class="sr-field"><label for="basic">Basic </label><input type="number" id="basic" name="basic" min="0" step="0.01"></div>
<div class="sr-field"><label for="da">DA </label><input type="number" id="da" name="da" min="0" step="0.01"></div>
<div class="sr-field"><label for="hra">HRA </label><input type="number" id="hra" name="hra" min="0" step="0.01"></div>
<div class="sr-field"><label for="washingAllowance">Washing Allowance </label><input type="number" id="washingAllowance" name="washingAllowance" min="0" step="0.01"></div>
<div class="sr-field"><label for="otherAllowance">Other Allowance </label><input type="number" id="otherAllowance" name="otherAllowance" min="0" step="0.01"></div>
<div class="sr-field"><label for="uniformAllowance">Uniform Allowance </label><input type="number" id="uniformAllowance" name="uniformAllowance" min="0" step="0.01"></div>
<div class="sr-field"><label for="pfCap">PF Cap </label><select id="pfCap" name="pfCap"><option value="">Select PF Cap</option><option value="Yes">Yes</option><option value="No">No</option></select></div>
</div></section>
<section class="sr-panel" id="sr-panel-4" role="tabpanel" aria-labelledby="sr-tab-4" hidden><h3>Documents</h3><div class="sr-grid">
<div class="sr-field"><label for="imageFile">Workman Photo <span class="sr-required">*</span></label><input type="file" id="imageFile" name="imageFile" accept="image/*" capture="environment" required></div>
<div class="sr-field"><label for="aadharFile">Aadhaar Document <span class="sr-required">*</span></label><input type="file" id="aadharFile" name="aadharFile" accept="image/*,.pdf" required></div>
<div class="sr-field"><label for="appointmentFile">Appointment Letter </label><input type="file" id="appointmentFile" name="appointmentFile" accept="image/*,.pdf"></div>
<div class="sr-field"><label for="policeFile">Police Verification Document </label><input type="file" id="policeFile" name="policeFile" accept="image/*,.pdf"></div>
<div class="sr-field"><label for="policeVerificationDate">Police Verification Date </label><input type="date" id="policeVerificationDate" name="policeVerificationDate"></div>
<div class="sr-field"><label for="comments">Comments </label><textarea id="comments" name="comments"></textarea></div>
<div class="sr-field"><label for="acceptCheck">Declaration <span class="sr-required">*</span></label><input type="checkbox" id="acceptCheck" name="acceptCheck" value="true" required></div>
</div></section>
<div class="sr-actions"><button class="sr-btn" type="button" id="sr-prev" onclick="srGo(srIndex-1)" disabled>Previous</button><button class="sr-btn sr-primary" type="button" id="sr-next" onclick="srGo(srIndex+1)">Next</button><button class="sr-btn sr-primary" type="button" id="sr-submit" onclick="srSubmit()" style="display:none">Submit Registration</button></div>
<p id="sr-message" class="sr-help" role="status" aria-live="polite"></p>
</form></main>
<script>
let srIndex=0;
function srGo(next){
 if(next<0||next>4)return;
 if(next>srIndex){
  for(let i=srIndex;i<next;i++){
   const panel=document.getElementById('sr-panel-'+i);
   const invalid=[...panel.querySelectorAll('input,select,textarea')].find(field=>!field.checkValidity());
   if(invalid){
    document.querySelectorAll('.sr-panel').forEach((el,j)=>el.hidden=j!==i);
    document.querySelectorAll('.sr-tab').forEach((el,j)=>el.setAttribute('aria-selected',String(j===i)));
    srIndex=i;
    invalid.reportValidity();
    return;
   }
  }
 }
 document.querySelectorAll('.sr-panel').forEach((el,i)=>el.hidden=i!==next);
 document.querySelectorAll('.sr-tab').forEach((el,i)=>el.setAttribute('aria-selected',String(i===next)));
 srIndex=next;
 document.getElementById('sr-prev').disabled=next===0;
 document.getElementById('sr-next').style.display=next===4?'none':'';
 document.getElementById('sr-submit').style.display=next===4?'':'none';
 document.getElementById('sr-tab-'+next).scrollIntoView({block:'nearest',inline:'nearest'});
}
async function srSubmit() {

    const form = document.getElementById('publicRegistrationForm');

    // Validate required fields across all five tabs
    for (const field of form.querySelectorAll('[required]')) {

        if (!field.checkValidity()) {

            const panel = field.closest('.sr-panel');
            const panels = [...document.querySelectorAll('.sr-panel')];
            const target = panels.indexOf(panel);

            if (target >= 0) {
                panels.forEach((el, i) => {
                    el.hidden = i !== target;
                });

                document.querySelectorAll('.sr-tab').forEach((el, i) => {
                    el.setAttribute(
                        'aria-selected',
                        String(i === target)
                    );
                });

                srIndex = target;

                document.getElementById('sr-prev').disabled = target === 0;
                document.getElementById('sr-next').style.display =
                    target === 4 ? 'none' : '';

                document.getElementById('sr-submit').style.display =
                    target === 4 ? '' : 'none';
            }

            field.reportValidity();
            return;
        }
    }

    const button = document.getElementById('sr-submit');
    const message = document.getElementById('sr-message');

    button.disabled = true;
    message.textContent = 'Submitting registration...';

    try {

        const token = document.querySelector(
            '[name="registrationToken"]'
        ).value;

        const response = await fetch(
            '${pageContext.request.contextPath}/register/' +
            encodeURIComponent(token) + '/submit',
            {
                method: 'POST',
                body: new FormData(form),
                credentials: 'same-origin',
                headers: {
                    'Accept': 'application/json'
                }
            }
        );

        const result = await response.json();

        if (!response.ok || result.success !== true) {
            throw new Error(
                result.message || 'Registration submission failed'
            );
        }

        
        // Registration saved successfully.
        // Redirect to the success page.
        //const registrationId = response.registrationId;
       /*  window.location.replace(
        		 "${pageContext.request.contextPath}/register/success?ref="
                + encodeURIComponent(registrationId)
        ); */
        
        window.location.replace(
       		 "${pageContext.request.contextPath}/register/success"
       );

    } catch (err) {

        console.error('Registration submission error:', err);

        message.textContent =
            err.message || 'Unable to submit registration';

        button.disabled = false;
    }
}
</script></body></html>