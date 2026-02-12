function searchGatePassIdBasedOnPE() {
					    var principalEmployerId = $('#principalEmployerId').val();
					    
						//var deptId=$("#deptId").val();
					    $.ajax({
					        url: '/CWFM/tradeSkillMatrix/listingDetails',
					        type: 'POST',
					        data: {
					            principalEmployerId: principalEmployerId
					        },
					        success: function(response) {
					            var tableBody = $('#workmenTable tbody');
								// 🔄 Clear previous DataTable and its config
								           if ($.fn.DataTable.isDataTable('#workmenTable')) {
								               $('#workmenTable').DataTable().destroy();
								           }
										   tableBody.empty();
					            if (Array.isArray(response) &&response.length > 0) {
					                $.each(response, function(index, wo) {
					                    var row = '<tr  >' +
												'<td  ><input type="checkbox" name="selectedWOs" value="' + wo.gatePassId + '"></td>'+
												 '<td  >' + wo.gatePassId + '</td>' +
					                              '<td  >' + wo.name + '</td>' +
												  '<td  >' + wo.aadharNumber + '</td>' +	
												  '<td  >' + wo.unitName + '</td>' +	
												  '<td  >' +wo.contractorCode + '</td>' +	
												  '<td  >' + wo.contractorName + '</td>' +
					                              '</tr>';
					                    tableBody.append(row);
					                });
									
					            } 								

																	            // ✅ Always init after rows are drawn
																	            initWorkmenTable("workmenTable");
																	        },
					       
					        error: function(xhr, status, error) {
					            console.error("Error fetching data:", error);
					        }
					    });
					}
					function redirectToTradeAdd() {
					console.log("redirectToTradeAdd called");
					var principalEmployerSelect = document.getElementById("principalEmployerId");
					    var unitId = principalEmployerSelect.value; 
						
						var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
						    if (selectedCheckboxes.length !== 1) {
						        alert("Please select exactly one row to add.");
						        return;
						    }
						    
						    var selectedRow = selectedCheckboxes[0].closest('tr');
						    var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
					    // Fetch the content of add.jsp using AJAX
					    var xhr = new XMLHttpRequest();
					    xhr.onreadystatechange = function() {
					        if (xhr.readyState == 4 && xhr.status == 200) {
					            // Update the mainContent element with the fetched content
					            document.getElementById("mainContent").innerHTML = xhr.responseText;
								setDateRange();
					        }
					    };
					    xhr.open("GET", "/CWFM/tradeSkillMatrix/createTradeSkillMapping?unitId=" + unitId+ "&gatePassId=" + gatePassId, true);
					    xhr.send();
					}
					
					function goBackToTradeSkillList() {
					    	 loadCommonList('/tradeSkillMatrix/list', 'Trade Skill Mapping');
					    }
						
						document.addEventListener('click', function (e) {
						    if (e.target.matches('button.addRowTrade')) {
						        e.preventDefault();
						        e.stopImmediatePropagation();
						        setTimeout(() => addRowTradeSkill(), 0); // prevent recursion
						    } else if (e.target.matches('button.removeRowTrade')) {
								e.preventDefault();
								       e.stopImmediatePropagation();
								       setTimeout(() => deleteRowTradeSkill(e.target), 0); // prevent recursion
						        //deleteRowContNew(e.target);
						    }
							else if (e.target.matches('button.addRowCert')) {
        e.preventDefault();
        e.stopImmediatePropagation();

        setTimeout(() => {
            addRowCertification();
            setDateRange();   // ✅ IMPORTANT
        }, 0);
    }else if (e.target.matches('button.removeRowCert')) {
															e.preventDefault();
															       e.stopImmediatePropagation();
															       setTimeout(() => deleteRowCertification(e.target), 0); // prevent recursion
													        //deleteRowContNew(e.target);
													    }
						});

						

						function addRowTradeSkill() {

    const tbody = document.getElementById("tradeSkillBody");

    const row = document.createElement("tr");
    row.innerHTML = `
        <td><button type="button" class="btn btn-success addRowTrade" style="color:blue;background-color:white;">+</button></td>
        <td><button type="button" class="btn btn-danger removeRowTrade" style="color:blue;background-color:white;">−</button></td>
        <td></td>
        <td></td>
        <td></td>
    `;

    // ===== TRADE DROPDOWN =====
    const originalTrade = document.querySelector('#tradeSkillBody tr:first-child select.tradeType');
    if (originalTrade) {
        const tradeClone = originalTrade.cloneNode(true);
        tradeClone.value = ""; // clear selected value
        tradeClone.removeAttribute("id"); // avoid duplicate id
        tradeClone.name = "tradeType";

        tradeClone.onchange = function () {
            getSkillsBasedOnUnitAndTrade(this);
        };

        row.cells[2].appendChild(tradeClone);
    }

    // ===== SKILL DROPDOWN =====
    const originalSkill = document.querySelector('#tradeSkillBody tr:first-child select.skillType');
    if (originalSkill) {
        const skillClone = originalSkill.cloneNode(true);
        skillClone.value = ""; 
        skillClone.removeAttribute("id");
        skillClone.name = "skillType";

        row.cells[3].appendChild(skillClone);
    }

    // ===== PROFICIENCY DROPDOWN =====
    const originalProf = document.querySelector('#tradeSkillBody tr:first-child select.proType');
    if (originalProf) {
        const profClone = originalProf.cloneNode(true);
        profClone.value = "";
        profClone.removeAttribute("id");
        profClone.name = "proType";

        row.cells[4].appendChild(profClone);
    }

    tbody.appendChild(row);
}

						function deleteRowTradeSkill(buttonElement) {
						    const row = buttonElement.closest('tr');
						    const tbody = document.getElementById("tradeSkillBody");

						    const dataRows = Array.from(tbody.querySelectorAll('tr')).filter(r => r.querySelector('button.removeRowTrade'));
							console.log(dataRows.length);
						    if (dataRows.length > 1) {
						        row.remove();
						    } else {
						        alert("At least one row must be present.");
						    }
						}
						
						

						function getSkillsBasedOnUnitAndTrade(selectElement) {

						    const row = selectElement.closest("tr");

						    // get unit value
						    const unitId = document.getElementById("unitId").value;

						    // trade selected in THIS row
						    const tradeId = selectElement.value;

						    // skill dropdown in THIS row
						    const skillSelect = row.querySelector("select.skillType");

						    if (!unitId || !tradeId) {
						        skillSelect.innerHTML = '<option value="">Please select Skill</option>';
						        return;
						    }

						    const xhr = new XMLHttpRequest();
						    const url = contextPath + "/contractworkmen/getAllSkills?unitId=" + unitId + "&tradeId=" + tradeId;

						    console.log("Fetching skills from URL:", url);

						    xhr.open("GET", url, true);

						    xhr.onload = function () {
						        if (xhr.status === 200) {
						            const skills = JSON.parse(xhr.responseText);
						            console.log("Skills:", skills);

						            // clear existing options
						            skillSelect.innerHTML = '<option value="">Please select Skill</option>';

						            // populate
						            skills.forEach(function (skill) {
						                const option = document.createElement("option");
						                option.value = skill.skillId;
						                option.text = skill.skill;
						                skillSelect.appendChild(option);
						            });

						        } else {
						            console.error("Error fetching skills:", xhr.statusText);
						        }
						    };

						    xhr.onerror = function () {
						        console.error("Request failed while fetching skills");
						    };

						    xhr.send();
						}
						
						function saveTradeSkillPro() {
                            
							 if (!validateTradeSkill()) {
                                  return;
                              }
                              if (!validateCertification()) {
                                    return;
                                 }
                                 if (!validateTradeSkillDuplicates()) {
									 return;
									 }
                                   if (!checkCertificationDuplicates()){
	                                     return;
                                          }                                
						    const data = new FormData();

						    const jsonData = {
						        gatePassId: $("#gatePassId").val().trim(),
						        tradeSkills: [],
						        certifications: []
						    };

						    // ===== TRADE SKILL TABLE =====
						    $("#tradeSkillBody tr").each(function () {

						        const row = $(this);

						        const trade = row.find(".tradeType").val();
						        const skill = row.find(".skillType").val();
						        const prof  = row.find(".proType").val();

						        if (trade && skill && prof) {
						            jsonData.tradeSkills.push({
						                tradeId: trade,
						                skillId: skill,
						                proficiencyId: prof
						            });
						        }
						    });

						    // ===== CERTIFICATION TABLE =====
						    $("#certBody tr").each(function () {

						        const row = $(this);

						        const cert = row.find(".certType").val();
						        const prof = row.find(".certProType").val();
						        const grant = row.find(".grantDate").val();
						        const expiry = row.find(".expiryDate").val();

						        if (cert && prof) {
						            jsonData.certifications.push({
						                certificationId: cert,
						                proficiencyId: prof,
						                grantDate: grant,
						                expiryDate: expiry
						            });
						        }
						    });

						    data.append("jsonData", JSON.stringify(jsonData));

						    console.log("Sending:", jsonData);

						    const xhr = new XMLHttpRequest();
						    xhr.open("POST", "/CWFM/tradeSkillMatrix/saveTradeSkill", true);

						    xhr.onload = function () {

						        if (xhr.status === 200) {
						            alert("Saved successfully");
						            loadCommonList('/tradeSkillMatrix/list',
						                'Trade Skill Mapping');

						        } else if (xhr.status === 409) {
						            alert("Duplicate combination exists");
						        } else {
						            alert("Save failed");
						        }
						    };

						    xhr.send(data);
						}

						
							function redirectToTradeView() {
						    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
						    if (selectedCheckboxes.length !== 1) {
						        alert("Please select exactly one row to view.");
						        return;
						    }
						    
						    var selectedRow = selectedCheckboxes[0].closest('tr');
						    var gatePassId = selectedRow.querySelector('[name="selectedWOs"]').value;
						    var xhr = new XMLHttpRequest();
						    xhr.onreadystatechange = function() {
						        if (xhr.readyState == 4 && xhr.status == 200) {
						            document.getElementById("mainContent").innerHTML = xhr.responseText;
						        }
						    };
						    xhr.open("GET", "/CWFM/tradeSkillMatrix/viewTradeSkill/" + gatePassId, true);
						    xhr.send();
						}
						function addRowCertification() {
    const tbody = document.getElementById("certBody");

    const row = document.createElement("tr");
    row.innerHTML = `
        <td><button type="button" class="btn btn-success addRowCert" style="color:blue;background-color:white;">+</button></td>
        <td><button type="button" class="btn btn-danger removeRowCert" style="color:blue;background-color:white;">−</button></td>
        <td></td>
        <td></td>
        <td>
            <input type="text"  class= "grantDate grantdatetimepicker">
            <small class="text-danger error-grantDate" style="display:none;">Grant Date must be past</small>
        </td>
        <td>
            <input type="text"  class="expiryDate expirydatetimepicker" >
            <small class="text-danger error-expiryDate" style="display:none;">Expiry Date must be future</small>
        </td>
    `;

    // ===== CERTIFICATION DROPDOWN =====
    const originalCert = document.querySelector('#certBody tr:first-child select.certType');
    if (originalCert) {
        const certClone = originalCert.cloneNode(true);
        certClone.value = "";                 // clear selected value
        certClone.selectedIndex = 0;          // force reset
        certClone.removeAttribute("id");      // avoid duplicate id
        certClone.name = "certType";

        row.cells[2].appendChild(certClone);
    }

    // ===== PROFICIENCY DROPDOWN =====
    const originalPro = document.querySelector('#certBody tr:first-child select.certProType');
    if (originalPro) {
        const proClone = originalPro.cloneNode(true);
        proClone.value = "";
        proClone.selectedIndex = 0;
        proClone.removeAttribute("id");
        proClone.name = "certProType";

        row.cells[3].appendChild(proClone);
    }

    tbody.appendChild(row);
     //$(row).find(".grantdatetimepicker").datepicker(grantDateConfig);
   // $(row).find(".expirydatetimepicker").datepicker(expiryDateConfig);
}

												function deleteRowCertification(buttonElement) {
												    const row = buttonElement.closest('tr');
												    const tbody = document.getElementById("certBody");

												    const dataRows = Array.from(tbody.querySelectorAll('tr')).filter(r => r.querySelector('button.removeRowCert'));
													console.log(dataRows.length);
												    if (dataRows.length > 1) {
												        row.remove();
												    } else {
												        alert("At least one row must be present.");
												    }
												}
       
function validateTradeSkill() {

    let hasValidRow = false;
    let isValid = true;

    // ❌ Remove all old error rows/messages first
    $(".tradeErrorRow").remove();
    $(".tradeError").remove();

    $("#tradeSkillBody tr").each(function (index) {

        const row = $(this);
        const trade = row.find(".tradeType").val();
        const skill = row.find(".skillType").val();
        const prof  = row.find(".proType").val();

        const rowNo = index + 1;

        // If user entered something in row
        if (trade || skill || prof) {

            // If any field missing → show error
            if (!trade || !skill || !prof) {
                isValid = false;

                // Show error BELOW this row (only once)
                row.after(`
                    <tr class="tradeErrorRow">
                        <td colspan="5" style="color:red;">
                            Row ${rowNo}: Trade, Skill & Proficiency required
                        </td>
                    </tr>
                `);
            }
            else {
                hasValidRow = true;
            }
        }
    });

    if (!isValid) {
        return false;
    }

    if (!hasValidRow) {
        alert("At least one Trade, Skill and Proficiency is required");
        return false;
    }

    return true;
}


/*function validateCertification() {

    let hasValidCertRow = false;
    let today = new Date();
    today.setHours(0, 0, 0, 0); // remove time

    $("#certBody tr").each(function () {

        const cert   = $(this).find(".certType").val();
        const prof   = $(this).find(".certProType").val();
        const grant  = $(this).find(".grantDate").val();
        const expiry = $(this).find(".expiryDate").val();

        if (cert && prof && grant && expiry) {

            let grantDate  = new Date(grant);
            let expiryDate = new Date(expiry);

            // ❌ Grant date cannot be future
            if (grantDate > today) {
                alert("Grant Date cannot be future date");
                hasValidCertRow = false;
                return false; // break loop
            }

            // ❌ Expiry date must be future
            if (expiryDate <= today) {
                alert("Expiry Date must be future date");
                hasValidCertRow = false;
                return false;
            }

            // ❌ Expiry must be after Grant
            if (expiryDate <= grantDate) {
                alert("Expiry Date must be after Grant Date");
                hasValidCertRow = false;
                return false;
            }

            // ✅ Valid row found
            hasValidCertRow = true;
            return false; // break loop
        }
    });

    if (!hasValidCertRow) {
        alert("At least one Certification with Proficiency, Grant Date and Expiry Date is required");
        return false;
    }

    return true;
}

*/
function validateCertification() {

    let hasValidRow = false;
    let isValid = true;

    // Remove old error rows/messages
    $(".certErrorRow").remove();

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    $("#certBody tr").each(function (index) {

        const row = $(this);
        const cert   = row.find(".certType").val();
        const prof   = row.find(".certProType").val();
        const grant  = row.find(".grantDate").val();
        const expiry = row.find(".expiryDate").val();

        const rowNo = index + 1;
        let rowError = "";

        // If user entered something in this row
        if (cert || prof || grant || expiry) {

            // Mandatory check
            if (!cert || !prof || !grant || !expiry) {
                rowError = "Certification, Proficiency, Grant Date & Expiry Date required";
            }

            // Grant Date Past Validation
            if (grant) {
                let grantDateObj = new Date(grant);
                if (grantDateObj > today) {
                    rowError = "Grant Date must be Past Date";
                }
            }

            // Expiry Date Future Validation
            if (expiry) {
                let expiryDateObj = new Date(expiry);
                if (expiryDateObj < today) {
                    rowError = "Expiry Date must be Future Date";
                }
            }

            // Show row error
            if (rowError !== "") {
                isValid = false;
                row.after(`
                    <tr class="certErrorRow">
                        <td colspan="6" style="color:red;">
                            Row ${rowNo}: ${rowError}
                        </td>
                    </tr>
                `);
            } else {
                hasValidRow = true;
            }
        }
    });

    if (!isValid) return false;

    if (!hasValidRow) {
        alert("At least one Certification row is required");
        return false;
    }

    return true;
}
function validateTradeSkillDuplicates() {

    let isValid = true;
    let map = {};
    
    // remove previous highlights
    $("#tradeSkillBody tr").removeClass("dupRow");

    $("#tradeSkillBody tr").each(function (index) {

        const row = $(this);
        row.removeClass("dupRow"); 
        const trade = row.find(".tradeType").val();
        const skill = row.find(".skillType").val();
        const prof  = row.find(".proType").val();

        if (trade && skill && prof) {

            const key = trade + "_" + skill + "_" + prof;

            if (map[key]) {
                // duplicate found
                row.addClass("dupRow");
                map[key].addClass("dupRow");
                isValid = false;
            } else {
                map[key] = row;
            }
        }
    });

    if (!isValid) {
        alert("Duplicate Trade + Skill + Proficiency not allowed");
    }

    return isValid;
}
function checkCertificationDuplicates() {

    let gatePassId = $("#gatePassId").val().trim();
    let certMap = {};
    let hasDuplicate = false;

    $("#certBody tr").each(function () {

        const row = $(this);
        const cert = row.find(".certType").val();

        row.removeClass("dupRow");

        if (cert) {
            let key = gatePassId + "_" + cert;

            if (certMap[key]) {
                row.addClass("dupRow");
                certMap[key].addClass("dupRow");
                hasDuplicate = true;
            } else {
                certMap[key] = row;
            }
        }
    });

    if (hasDuplicate) {
        alert("Duplicate Certification for same GatePass is not allowed");
    }

    return !hasDuplicate; // ✅ true = OK to save, false = stop save
}

