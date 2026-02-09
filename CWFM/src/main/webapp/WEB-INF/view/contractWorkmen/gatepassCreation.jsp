<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/cms/contractor.js"></script>
    <script src="resources/js/cms/workorder.js"></script>
    <script src="resources/js/cms/workmen.js"></script>
    <script src="resources/js/cms/report.js"></script>
    <script src="resources/js/jquery.min.js"></script>
    <style>
  body {
    margin: 0;
    overflow-x: hidden;
    font-family: 'Noto Sans', sans-serif;
}

#principalEmployerContent {
    padding: 20px;
    box-sizing: border-box;
    overflow-y: auto;
    height: calc(100vh - 60px); /* Adjust based on header height */
}

.page-header {
    background-color: #005151;
    color: #fff;
    padding: 15px;
    text-align: center;
    font-size: 24px;
    font-family: 'Noto Sans', sans-serif;
    position: fixed;
    width: 100%;
    top: 0;
    left: 0;
    z-index: 1000;
}

.header-buttons {
    float: right;
    margin-right: 20px;
}

.tabs {
    overflow: hidden;
    border-bottom: 2px solid #005151;
    margin-bottom: 20px;
}

.tabs button {
    background-color: #fff; /* Tab background color */
    border: 2px solid #005151; /* Tab border color */
    outline: none;
    padding: 10px 20px; /* Reduced height */
    cursor: pointer;
    font-size: 17px;
    transition: background-color 0.3s, color 0.3s;
    color: #005151; /* Tab text color */
    font-family: 'Noto Sans', sans-serif;
}

.tabs button.active {
    background-color: #005151; /* Active tab background color */
    color: #fff; /* Active tab text color */
    border-bottom: 2px solid #fff;
}

.tab-content {
    display: none;
    padding: 10px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
}

.tab-content.active {
    display: block;
}

.custom-label {
    font-family: 'Noto Sans', sans-serif;
    text-align: left;
    display: block;
    margin-bottom: 5px;
    color: #898989;/* Label text color */
    display: inline;
  padding: .2em .6em .3em;
  font-size: 85%;
  font-weight: 700;
  line-height: 1;
    white-space: nowrap;
  vertical-align: baseline;
  border-radius: .25em;
}

.custom-input-container {
    padding-left: 10px;
}

.custom-input, .custom-input-checkbox {
    height: 40px;
    font-family: 'Noto Sans', sans-serif;
    width: 100%;
    box-sizing: border-box;
}

.required-field {
    color: red;
    margin-right: 4px;
}



table.ControlLayout {
    border-collapse: separate; /* Ensure spacing is applied correctly */
    border-spacing: 10px; /* Adjust the value for the desired gap between cells */
}

table.ControlLayout th,
table.ControlLayout td {
    padding: 10px; /* Add padding inside cells for spacing around content */
    vertical-align: top; /* Align the content to the top of the cell */
}
        body {
            margin: 0;
            overflow-x: hidden;
        }
        #principalEmployerContent {
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: calc(100vh - 20px);
        } 
        th label {
            text-align: left;
            display: block;
            font-weight: normal;
        }
        .custom-label {
            font-family: Arial, sans-serif;
            text-align: left;
            display: block;
        }
        .required-field {
            color: red;
            margin-right: 4px; 
        }
        .page-header {
            background-color: #005151;
            color: #fff;
            padding: 10px;
            text-align: center;
            font-size: 20px;
        }
        .header-buttons {
            float: right;
            margin-right: 10px;
        }
        .tabs {
            display: flex;
            margin-bottom: 10px;
        }
       
        .tabs button {
        padding: 5px 10px; /* Adjust padding to decrease size */
        font-size: 12px; /* Decrease font size */
        margin-right: 5px; /* Space between tabs */
        border: 1px solid #ddd; /* Optional: add a border for visibility */
        border-radius: 3px; /* Optional: rounded corners */
    }
        .tabs button.active {
            background-color: #005151;
            color: white;
            border-bottom: 2px solid #005151;
        }
        .tab-content {
            display: none;
            padding: 20px;
            background-color: white;
            border: 1px solid #ccc;
        }
        .tab-content.active {
            display: block;
        }
        .custom-select {
    display: inline-block;
    width: 100%; /* Reduced the width to 50%, adjust as needed */
    height: 25px;
    padding: 5px;
    font-size: 12px;
    font-family: Arial, sans-serif;
    line-height: 1.5;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

    .custom-select:focus {
        border-color: #80bdff;
        outline: 0;
        box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
    }
    .image-container {
        text-align: center;
        padding: 10px;
    }

    .image-container img {
        margin: 10px;
        cursor: pointer;
    }
   

    .tabs-container {
        display: flex;
        justify-content: space-between; /* Distribute space between tabs and buttons */
        align-items: center; /* Align items vertically */
    }

    .tabs {
        display: flex;
        flex-wrap: nowrap; /* Prevent wrapping of tabs */
    }

    .tabs button {
        margin-right: 10px; /* Space between tabs */
    }

    .action-buttons {
        display: flex; /* Align buttons horizontally */
        align-items: center; /* Center buttons vertically */
    }

    .action-buttons button {
        margin-left: 10px; /* Space between buttons */
    }
    .custom-mb {
    margin-bottom: 2.5rem; /* Example custom margin */
}
.custom-radio {
    margin-right: 5px; /* Adjust the spacing between the radio button and the label */
    vertical-align: middle; /* Align the radio button with the label text */
}

.custom-label-inline {
    display: inline-block;
    vertical-align: middle; /* Align the label text with the radio button */
    margin-left: 3px; /* Adjust this value to control the space between the radio button and the label */

}
td {
    padding: 5px; /* Add padding to cells for spacing */
}

input[type="radio"] {
    margin-right: 5px; /* Space between radio button and label */
    vertical-align: middle; /* Align radio button with label text */
}

label {
    vertical-align: middle; /* Align label text with radio button */
    display: inline-block; /* Ensure label appears on the same line as radio button */
    color: #495057; /* Set the text color to a dark shade */
    font-family: Arial, sans-serif;
}

/* #preview {
            width: 200px;
            height: 200px;
            border: 1px solid #ddd;
            margin-top: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        } */
        #preview img {
            max-width: 100%;
            max-height: 100%;
        }
        
    </style>
     <%
    	MasterUser user = (MasterUser) session.getAttribute("loginuser");
        String userId = user != null ? user.getUserId() : "";
		%>
		<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
		<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script>

async function fetchScreenConfig(screenName) {
    const response = await fetch(`/CWFM/contractworkmen/config/${screenName}`);
    return response.json();
}


async function applyScreenConfig(screenName) {
    const config = await fetchScreenConfig(screenName);

    Object.entries(config.fields).forEach(([field, settings]) => {
        const fieldElement = document.getElementById(field);

        if (!fieldElement ) return;

        // Show/Hide field
        if (settings.visible) {
            fieldElement.style.display = "";
        } else {
            fieldElement.style.display = "none";
        }

        // Make field mandatory
        if (settings.mandatory) {
            fieldElement.setAttribute("required", "required");
        } else {
            fieldElement.removeAttribute("required");
        }
    });
}

document.addEventListener("DOMContentLoaded", function() {
	const screenName = "gatePassCreation"; // Replace with the actual screen name dynamically
    applyScreenConfig(screenName);
    
});

</script>
</head>
<body>
 <div id="principalEmployerContent">
<form id="dynamicForm" class="container">
    <div id="tab1" class="tab-content active">
        <!-- Aadhar Number and OTP -->
        <div class="row mb-3">
            <div class="col-md-3">
                <label for="aadharNumber" class="form-label">Aadhar Number</label>
                <input id="aadharNumber" name="aadharNumber" class="form-control" type="text" maxlength="12">
                <div id="error-aadhar" class="text-danger d-none" style="display: none;">Please enter a valid 12-digit Aadhar number</div>
            </div>
          <!--   <div class="col-md-3">
                <button type="button" class="btn btn-primary mt-4">Generate OTP</button>
            </div>
            <div class="col-md-3">
                <label for="otp" class="form-label">Enter OTP</label>
                <input id="otp" name="otp" class="form-control" type="text" maxlength="12" placeholder="Enter OTP here">
            </div>
            <div class="col-md-3">
                <button type="button" class="btn btn-primary mt-4">Verify OTP</button>
            </div> -->
        </div>

        <!-- First Name and Last Name -->
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="firstName" class="form-label">First Name</label>
                <input id="firstName" name="firstName" class="form-control" type="text" maxlength="30" autocomplete="off">
                <div id="error-firstName" class="text-danger d-none" style="display: none;">First Name is required</div>
            </div>
            <div class="col-md-6">
                <label for="lastName" class="form-label">Last Name</label>
                <input id="lastName" name="lastName" class="form-control" type="text" maxlength="30" autocomplete="off">
                <div id="error-lastName" class="text-danger d-none" style="display: none;">Last Name is required</div>
            </div>
        </div>

        <!-- Date of Birth and Gender -->
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="dateOfBirth" class="form-label">Date of Birth</label>
                <input id="dateOfBirth" name="dateOfBirth" class="form-control datetimepickerformat" type="text" onfocus="initializeDatePicker()" onclick="initializeDatePicker()" autocomplete="off">
                <div id="error-dateOfBirth" class="text-danger d-none" style="display: none;">Date Of Birth is required</div>
            </div>
            <div class="col-md-6">
                <label for="gender" class="form-label">Gender</label>
                <select id="gender" name="gender" class="form-select">
                    <option value="">Please select Gender</option>
                    <c:forEach var="option" items="${GenderOptions}">
                        <option value="${option.gmId}">${option.gmName}</option>
                    </c:forEach>
                </select>
                <div id="error-gender" class="text-danger d-none" style="display: none;">Gender is required</div>
            </div>
        </div>

        <!-- Father/Husband Name and ID Mark -->
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="relationName" class="form-label">Father/Husband Name</label>
                <input id="relationName" name="relationName" class="form-control" type="text" maxlength="30" autocomplete="off">
                <div id="error-relationName" class="text-danger d-none" style="display: none;">Father / Husband name is required</div>
            </div>
            <div class="col-md-6">
                <label for="idMark" class="form-label">ID Mark</label>
                <input id="idMark" name="idMark" class="form-control" type="text" maxlength="30" autocomplete="off">
                <div id="error-idMark" class="text-danger d-none" style="display: none;">ID Mark is required</div>
            </div>
        </div>

        <!-- Mobile Number and Marital Status -->
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="mobileNumber" class="form-label">Mobile Number</label>
                <input id="mobileNumber" name="mobileNumber" class="form-control" type="text" maxlength="30" autocomplete="off">
                <div id="error-mobileNumber" class="text-danger d-none" style="display: none;">Mobile Number is required</div>
            </div>
            <div class="col-md-6">
                <label for="maritalStatus" class="form-label">Marital Status</label>
                <select id="maritalStatus" name="maritalStatus" class="form-select">
                    <option value="">Please select Marital Status</option>
                    <option value="Single">Single</option>
                    <option value="Married">Married</option>
                </select>
                <div id="error-maritalStatus" class="text-danger d-none" style="display: none;">Marital Status is required</div>
            </div>
        </div>

        <!-- Address -->
        <div class="row mb-3">
            <div class="col-md-12">
                <label for="address" class="form-label">Address</label>
                <input id="address" name="address" class="form-control" type="text" autocomplete="off">
                <div id="error-address" class="text-danger d-none" style="display: none;">Address is required</div>
            </div>
        </div>

    </div>
</form>
</div>
</body>
</html>