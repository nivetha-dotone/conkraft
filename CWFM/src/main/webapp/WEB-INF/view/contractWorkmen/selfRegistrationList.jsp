<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Workman Self-Registration Requests</title>
<style>body{font:14px 'Noto Sans',Arial,sans-serif;background:white;color:#333}.page-header{padding:12px;border-bottom:1px solid #ccc;display:flex;gap:12px;align-items:center;flex-wrap:wrap}label{color:darkcyan}select,button,input{padding:7px;border:1px solid #ccc;border-radius:4px}button{background:#005151;color:white;cursor:pointer}table{border-collapse:collapse;width:100%;margin-top:12px}th{background:#DDF3FF;color:#005151;font-size:12px}th,td{padding:9px;border:1px solid #ddd;text-align:left}td{font-size:13px}a{color:#005151}#error{color:#a00}</style></head><body>
<div class="page-header"><label>Principal Employer </label><select id="unitId">
<option value="">Select PE</option>
<c:forEach var="x" items="${peOptions}">
<option value="<c:out value='${x.id}'/>"><c:out value="${x.description}"/></option></c:forEach></select>

<label>Contractor</label> <select id="contractorId"><option value="">Select Contractor</option>
<c:forEach var="x" items="${contractorOptions}"><option value="<c:out value='${x.id}'/>">
<c:out value="${x.description}"/></option></c:forEach></select>
<button type="button" onclick="loadRows()">Search</button>
<input id="filter" placeholder="Search results" oninput="filterRows()"></div>
<div id="error" role="alert"></div><div style="overflow-x:auto"><table class="no-dt"><thead><tr><th>Reference</th><th>Workman Name</th><th>Aadhaar</th><th>Mobile</th><th>Principal Employer</th><th>Contractor</th><th>Status</th><th>View</th></tr></thead><tbody id="rows"></tbody></table></div>
<script>
const base='${pageContext.request.contextPath}/self-registration/review';
let cached=[];
function cell(tr,v){
	const td=document.createElement('td');
	td.textContent=v==null?'':String(v);
	tr.appendChild(td);
	return td;
	}
function filterRows() {

    const q = document.getElementById('filter').value.toLowerCase();
    const body = document.getElementById('rows');

    body.replaceChildren();

    cached
        .filter(r =>
            Object.values(r).some(v =>
                String(v ?? '').toLowerCase().includes(q)
            )
        )
        .forEach(r => {

            const tr = document.createElement('tr');

            cell(tr, r.RegistrationId);
            cell(tr, [r.FirstName, r.LastName].filter(Boolean).join(' '));
            cell(tr, 'XXXXXXXX' + r.AadhaarLast4);
            cell(tr, r.MobileNumber);
            cell(tr, r.PrincipalEmployer);
            cell(tr, r.ContractorName);
            cell(tr, r.Status);

            const td = cell(tr, '');

            const a = document.createElement('a');

            a.href =   '/CWFM/self-registration/review/view?registrationId=' + r.RegistrationId;
            a.textContent = 'View';
            a.className = 'btn btn-sm btn-primary';

            a.addEventListener('click', function(e) {
                e.preventDefault();

                loadCommonList(
                    '/self-registration/review/view?registrationId=' + r.RegistrationId,'Self Onboarding Details'
                );
            });

            

            td.appendChild(a);
            body.appendChild(tr);
        });
}

function viewRegistration(registrationId) {
    const url = base + '/' + registrationId;
    loadCommonList(url);
}
async function loadRows(){
	const unitId=document.getElementById('unitId').value,contractorId=document.getElementById('contractorId').value;
	const error=document.getElementById('error');error.textContent='';
	if(!unitId||!contractorId){error.textContent='Select Principal Employer and Contractor';return;}
	try{const res=await fetch(base+'/rows?'+new URLSearchParams({unitId,contractorId}),{credentials:'same-origin'});
	if(!res.ok)throw Error('Unable to load registrations (HTTP '+res.status+')');
	cached=await res.json();filterRows();}catch(e){cached=[];filterRows();error.textContent=e.message;}}
</script></body></html>
