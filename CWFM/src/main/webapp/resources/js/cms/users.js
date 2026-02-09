	function searchUserWithUserAccount() {
    var userAccount = $('#userAccount').val().trim();
    console.log("Searching for userAccount:", userAccount);

    if (!userAccount) {
        alert("Please enter a user account.");
        return;
    }

    $.ajax({
        url: '/CWFM/usersController/getUserWithUserAccount',
        type: 'GET',
        data: { userAccount: userAccount },
        success: function(response) {
            console.log("Response received:", response); // ✅ Debug

            var tableBody = $('#UserTable tbody');
            tableBody.empty();

            if (response && response.length > 0) {
                $.each(response, function(index, user) {
                    var row = '<tr>' +
                        '<td><input type="checkbox" name="selectedUserIds" value="' + user.userId + '"></td>' +
                        '<td>' + user.userAccount + '</td>' +
                        '<td>' + user.emailId + '</td>' +
                        '<td>' + user.firstName + ' ' + user.lastName + '</td>' +
                        '<td>' + user.contactNumber + '</td>' +
                        '<td>' + user.status + '</td>' +
                        '</tr>';
                    tableBody.append(row);
                });
            } else {
                tableBody.append('<tr><td colspan="6">No users found</td></tr>');
            }
        },
        error: function(xhr, status, error) {
            console.error("Error during AJAX call:", error);
        }
    });
}


	
	
	/*function getContractors(unitId, userAccount) {
    var xhr = new XMLHttpRequest();
    var url = contextPath + "/contractworkmen/getAllContractors?unitId=" + unitId + "&userAccount=" + userAccount;
    //alert("URL: " + url);
    xhr.open("GET", url, true);

    xhr.onload = function() {
        if (xhr.status === 200) {
            // Parse the response as a JSON array of contractor objects
            var contractors = JSON.parse(xhr.responseText);
            console.log("Response:", contractors);
            
            // Find the contractor select element
            var contractorSelect = document.getElementById("contractor");
            
            // Clear existing options
            contractorSelect.innerHTML = '<option value="">Please select Contractor</option>';
            
            // Populate the dropdown with the new list of contractors
            contractors.forEach(function(contractor) {
                var option = document.createElement("option");
                option.value = contractor.contractorId;
                option.text = contractor.contractorName;
                contractorSelect.appendChild(option);
            });
        } else {
            console.error("Error:", xhr.statusText);
        }
    };

    xhr.onerror = function() {
        console.error("Request failed");
    };

    xhr.send();
}
	*/