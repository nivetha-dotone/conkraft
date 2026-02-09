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

						    const originalTradeDropdown = document.querySelector('#tradeSkillBody tr:first-child select.tradeType');
						    if (originalTradeDropdown) {
						        const clonedTradeDropdown = originalTradeDropdown.cloneNode(true);
						        clonedTradeDropdown.classList.add("tradeType");
						        clonedTradeDropdown.name = "tradeType";
								clonedTradeDropdown.onchange = function () {
								    getSkillsBasedOnUnitAndTrade(this);
								};

						        row.cells[2].appendChild(clonedTradeDropdown);
						    }
							const originalSkillDropdown = document.querySelector('#tradeSkillBody tr:first-child select.skillType');
													    if (originalSkillDropdown) {
													        const clonedSkillDropdown = originalSkillDropdown.cloneNode(true);
													        clonedSkillDropdown.classList.add("skillType");
													        clonedSkillDropdown.name = "skillType";
													        row.cells[3].appendChild(clonedSkillDropdown);
													    }
														const originalProLevelDropdown = document.querySelector('#tradeSkillBody tr:first-child select.proType');
																				    if (originalProLevelDropdown) {
																				        const clonedProLevelDropdown = originalProLevelDropdown.cloneNode(true);
																				        clonedProLevelDropdown.classList.add("proType");
																				        clonedProLevelDropdown.name = "proType";
																				        row.cells[4].appendChild(clonedProLevelDropdown);
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

						    const data = new FormData();

						    const jsonData = {
						        gatePassId: $("#gatePassId").val().trim(),
						        tradeSkills: []
						    };

						    // loop dynamic rows
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

						    data.append("jsonData", JSON.stringify(jsonData));

						    console.log("Sending:", jsonData);

						    const xhr = new XMLHttpRequest();
						    xhr.open("POST",  "/CWFM/tradeSkillMatrix/saveTradeSkill", true);

						    xhr.onload = function () {
						        if (xhr.status === 200) {
									sessionStorage.setItem("successMessage", "Gatepass saved successfully!");
						            console.log("Saved TSP successfully!");
									loadCommonList('/tradeSkillMatrix/list', 'Trade Skill Mapping');
						        } else {
						            console.log("Save failed");
									sessionStorage.setItem("errorMessage", "Failed to save trade skill mapping!");
						            console.error(xhr.responseText);
						        }
						    };

						    xhr.onerror = function () {
						        console.log("Error while saving");
								sessionStorage.setItem("errorMessage", "Failed to save trade skill mapping!");
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

