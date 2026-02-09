<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<title>DotType</title>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css"> 
    <script src="resources/js/cms/dottype.js"></script>
    <style>
        /* Add your styles here */
        .success {
            color: green;
            font-weight: bold;
            padding: 10px;
            background-color: #e0ffe0;
            border: 1px solid green;
            margin-bottom: 1rem;
        }

        .error {
            color: red;
            font-weight: bold;
            padding: 10px;
            background-color: #ffe0e0;
            border: 1px solid red;
            margin-bottom: 1rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;

            border: 1px solid #ddd;
        }

        th {
            background-color: #DDF3FF;
            color: #005151;
        }

        .checkbox-cell input[type="checkbox"] {
            margin: 0;
        }

        .action-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem;
            background-color: #f8f8f8;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
        }

        .action-buttons button {
            padding: 0.5rem 1rem;
            font-size: 1rem;
            cursor: pointer;
        }
         .success {
        color: green;
        font-weight: bold;
        padding: 10px;
        background-color: #e0ffe0;
        border: 1px solid green;
        margin-bottom: 1rem;
    }
    .error {
        color: red;
        font-weight: bold;
        padding: 10px;
        background-color: #ffe0e0;
        border: 1px solid red;
        margin-bottom: 1rem;
    }
 label {
    color: black;
}
    body {
        background-color: #FFFFFF; /* White background for the page */
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
    }

    .action-bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 1rem;
        background-color: #f8f8f8;
        margin-bottom: 1rem;
    }

    .action-buttons {
        display: flex;
        gap: 10px;
    }

    .action-buttons button {
        padding: 0.5rem 1rem;
        font-size: 1rem;
        cursor: pointer;
    }

    #searchForm {
        display: flex;
        align-items: center;
        flex-grow: 1;
        margin-right: 10px;
    }

    .search-box {
        width: 200px; /* Adjust width to fit layout */
        padding: 0.25rem; /* Reduced padding for height */
        font-size: 0.875rem; /* Smaller font size */
        border: 1px solid #ccc; /* Border to match design */
        border-radius: 4px; /* Slightly rounded corners */
        outline: none; /* Remove default outline */
        margin-right: 10px; /* Space between input and button */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }

    .table-container {
        overflow-x: auto;
        margin: 0; /* Remove space before the table */
        padding: 0; /* Remove padding if any */
    }

    table {
        width: 30%;
        border-collapse: collapse;
    }

    th, td {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
    }

    th {
        background-color: #DDF3FF; /* Light green for the table header */
        color: #005151; /* Text color from side nav bar */
        cursor: pointer;
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        font-size: 0.75rem; /* Decreased font size for table header */
        line-height: 1.2rem; /* Adjust line-height for better fit */
        padding: 6px; /* Reduced padding for table header */
    }

    .page-header {
        display: flex;
        align-items: center;
        justify-content: flex-start; /* Align elements to the left */
    gap: 10px;  /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
    }

    .page-header > div {
        display: flex;
        gap: 10px; /* Space between buttons */
    }

    @media (max-width: 768px) {
        .page-header {
            flex-direction: column; /* Stack items vertically on small screens */
            align-items: flex-start; /* Align items to the start */
        }

        #searchForm {
            width: 100%; 
            margin-right: 0; /* Remove margin on small screens */
        }

        .search-box {
            width: 100%; /* Full width for small screens */
        }

        .page-header > div {
            width: 100%; /* Full width for small screens */
            margin-top: 10px; /* Add space above buttons */
            flex-direction: column; /* Stack buttons vertically */
        }
    }
    .header-text-new {
        font-family: 'Noto Sans', Arial, sans-serif; /* Font family similar to grid header */
        font-size: 14px; /* Adjusted font size to match typical grid header size */
        font-weight: 600; /* Bold text for prominence */
        border: 1px solid #ddd; /* Lighter border for a cleaner look */
        white-space: nowrap; /* Prevent text from wrapping */
        padding: 8px 10px; /* Adjusted padding for better spacing */
          background-color: #E0E0E0;  /* Light background color to match grid header */
        color: #333; /* Text color for readability */
    }
       table th {
        border-top: 0.0625rem solid var(--zed_sys_color_border_lowEmphasis); /* Top border color */
        border-bottom: 1px solid var(--zed_sys_color_border_lowEmphasis); /* Bottom border color */
        border-right: none; /* No right border */
        background-color: #DDF3FF; /* Light green background color */
        color: var(--zed_sys_color_tableHeader_text); /* Text color */
        font-size: 0.75rem; /* Smaller font size */
        line-height: 1.2rem; /* Reduced line height */
        letter-spacing: normal; /* Letter spacing */
        font-family: 'Noto Sans', sans-serif; /* Font family */
        font-weight: 400; /* Font weight */
        text-align: center; /* Center align text */
        padding: 4px; /* Reduced padding for the table header */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }
    
    .error-row {
    background-color: #ffcccc !important;
}

.error-message-row .error-message-cell {
    color: red;
    font-weight: bold;
    font-size: 13px;
    padding: 6px 10px;
    background-color: #ffe5e5;
    border-top: none;
}
    .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}  
    </style>
  <script src="resources/js/cms/workflow.js"></script>
</head>
<body>
<!-- Success -->
<c:if test="${not empty message}">
    <div class="alert alert-success mt-3">${message}</div>
</c:if>

<!-- Error -->
<c:if test="${not empty error}">
    <div class="alert alert-danger mt-3">${error}</div>
</c:if>
<div class="page-header" method="POST" onsubmit="return validateMasterValue()">
    
   <label for="gmTypeId" style="color: darkcyan;">Principal Employer:</label>
        <!-- <select class="custom-select" id="principalEmployer" name="principalEmployerId" onchange="getBusinessType(this.value)" required> -->
        <select class="custom-select" id="principalEmployer" name="principalEmployerId"  onchange="onBusinessTypeChange()" required style="padding:3px;color:gray;">
                                <option value="">Please select Principal Employer</option>
								<c:forEach var="pe" items="${PrincipalEmployer}">
                					<option value="${pe.unitId}">${pe.name}</option>
            					</c:forEach>
                                </select>
        
        <!-- <label for="masterName" style="color: darkcyan;">Business Type</label>
         <select class="custom-select" id="businessType" name="businessTypeId"   onchange="onBusinessTypeChange()" required>
                                <option value="">Please select Business Type</option>
                                </select> -->
       

       
 <div class="page-header-buttons">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveDotSelection()">Save</button>
         <!-- <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="exportGMMasterCSV()">Cancel</button> -->
         </div>
          <div id="formErrorMessage" class="error-message" style="display: none; color: red; font-weight: bold;"></div>
    </div>
    <div id="messageBox" style="display: none; margin: 10px;">
    <p id="messageText" style="padding: 5px; border-radius: 5px;font-weight: bold;"></p>
</div>
    
<div style="padding:10px;">
<h4  style="color: darkcyan;">DOT Type</h4>
<%-- <table id="dottype" class="no-dt">
    <c:forEach var="dot" items="${dotTypes}">
        <tr>
            <td>
                <input type="radio" name="dotType" value="${dot.status}" required />
                ${dot.name}
            </td>
        </tr>
    </c:forEach>
</table> --%>

<table id="dottype" class="no-dt">
    <c:forEach var="dot" items="${dotTypes}" varStatus="st">

        <c:if test="${st.index % 2 == 0}">
            <tr>
        </c:if>

        <td style="font-size: 11.9px;font-weight: 690;font-family: 'Noto Sans', sans-serif;color: #898989;padding: -0.8em .6em .3em;">
            <input type="radio" name="dotType" value="${dot.status}" required />
            ${dot.name}
        </td>

        <c:if test="${st.index % 2 == 1}">
            </tr>
        </c:if>

    </c:forEach>

    <c:if test="${fn:length(dotTypes) % 2 == 1}">
        </tr>
    </c:if>
</table>

</div>
 
</body>
</html>