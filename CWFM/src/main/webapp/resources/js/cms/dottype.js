function saveDotSelection() {
    const principalEmployerId = document.getElementById("principalEmployer").value;
    
    const dotType = document.querySelector('input[name="dotType"]:checked')?.value;

    if (!principalEmployerId  || !dotType) {
        alert("Please select Principal Employer and Dot Type.");
        return;
    }

    const formData = new URLSearchParams();
    formData.append("principalEmployerId", principalEmployerId);
    
    formData.append("dotType", dotType);

    fetch("/CWFM/dottype/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: formData
    })
    .then(async response => {
    const text = await response.text();
    if (!response.ok) {
        throw new Error(text);
    }

    // ✅ Show message in green
    const box = document.getElementById("messageBox");
    const msg = document.getElementById("messageText");
    msg.textContent = text;
    //msg.style.backgroundColor = "#d4edda";  // light green
    msg.style.color = "green";            // dark green text
   // msg.style.border = "1px solid #c3e6cb"; // border
    box.style.display = "block";

    // Auto-hide after 5 seconds
    setTimeout(() => {
        box.style.display = "none";
    }, 5000);

    // ✅ Optional: clear form after success
    document.getElementById("principalEmployer").value = "";
    document.querySelectorAll('input[name="dotType"]').forEach(el => el.checked = false);
})

   .catch(error => {
    const box = document.getElementById("messageBox");
    const msg = document.getElementById("messageText");
    msg.textContent = error.message;
    //msg.style.backgroundColor = "#f8d7da";  // light red
    msg.style.color = "red";            // dark red
    //msg.style.border = "1px solid #f5c6cb";
    box.style.display = "block";

    setTimeout(() => {
        box.style.display = "none";
    }, 5000);
});

}

function loadOrgLevelEntries() {
    const $entry = $("#entryName");
    const $desc  = $("#description"); // make sure your input has id="description"
    const orgLevelDefId = $("#orgLevelDefId").val();

    // Bind entry change handler once
    if (!$entry.data("handler-bound")) {
        $entry.on("change", function () {
            const text = $(this).find(":selected").data("description") || "";
            $desc.val(text);
        });
        $entry.data("handler-bound", true);
    }

    // If nothing selected → clear both and stop
    if (!orgLevelDefId) {
        $entry.html("<option value=''>Select Entry Name</option>")
              .val("")
              .trigger("change"); // clears description via the handler
        return;
    }

    $.ajax({
        url: "/CWFM/org-level-entryController/org-level-entry-dropdowns", // <-- correct endpoint
        type: "GET",
        data: { orgLevelDefId: orgLevelDefId },
        success: function (entries) {
            // reset both before filling
            $entry.empty().append("<option value=''>Select Entry Name</option>");
            $desc.val("");

            // fill options with description in data-attribute
            $.each(entries, function (i, entry) {
                $("<option>")
                    .val(entry.entryName)
                    .text(entry.entryName)
                    .attr("data-description", entry.description || "")
                    .appendTo($entry);
            });

            // keep dropdown blank & ensure description cleared
            $entry.val("").trigger("change");
        },
        error: function () {
            alert("Error loading entries!");
            // also clear UI on error
            $entry.html("<option value=''>Select Entry Name</option>").val("").trigger("change");
        }
    });
}

function fetchOrgLevelSavedEntries() {
    var orgLevelDefId = document.getElementById("orgLevelDefId").value;
    
    // If no org level is selected, clear the entries
    if (orgLevelDefId === "") {
        document.getElementById("orgLevelEntries").innerHTML = "<p>No entries available for the selected org level.</p>";
        return;
    }
    
    // Perform an AJAX request to fetch entries for the selected org level
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/CWFM/org-level-entryController/org-level-entrys?orgLevelDefId=" + orgLevelDefId, true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            document.getElementById("orgLevelEntries").innerHTML = xhr.responseText;
            loadCommonList('/org-level-entryController/org-level-entrys', 'Org Levels View');
        } else {
            alert("Failed to load org level entries.");
        }
    };
    xhr.send();
}

function onBusinessTypeChange() {
    const principalEmployerId = document.getElementById("principalEmployer").value;
    

    if (!principalEmployerId ) return;

    $.ajax({
        url: '/CWFM/dottype/getDotTypeByEmployerAndBusiness',
        type: 'GET',
        data: {
            principalEmployerId: principalEmployerId
        },
        success: function (dotType) {
            // Uncheck all radio buttons first
            $("input[name='dotType']").prop("checked", false);

            let selectedValue = null;

            // ✅ Case 1: backend returns JSON like { name: "WCWO", status: 4 }
            if (typeof dotType === 'object' && dotType !== null) {
                if (dotType.name) {
                    selectedValue = dotType.name; // Use enum name
                } else if (dotType.status) {
                    selectedValue = dotType.status; // Use numeric status
                }
            }
            // ✅ Case 2: backend returns plain string like "WCWO"
            else if (typeof dotType === 'string') {
                selectedValue = dotType;
            }
            // ✅ Case 3: backend returns numeric status (like 4)
            else if (typeof dotType === 'number') {
                selectedValue = dotType.toString();
            }

            // ✅ Now check radio button matching either name or status
            if (selectedValue) {
                let matched = false;

                // Try to match by name first (like "WCWO")
                matched = $("input[name='dotType'][value='" + selectedValue + "']").prop("checked", true).length > 0;

                // If not found by name, try to match by status (numeric value)
                if (!matched) {
                    $("input[name='dotType'][data-status='" + selectedValue + "']").prop("checked", true);
                }
            }
        },
        error: function () {
            console.error("Error fetching mapped Dot Type");
            $("input[name='dotType']").prop("checked", false);
        }
    });
}


