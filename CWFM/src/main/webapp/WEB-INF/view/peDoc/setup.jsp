<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Bill Verification Setup</title>
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- JS file -->
   <script src="resources/js/cms/principalEmployer.js"></script>
    <style>
 .accordion h3 {
            background-color: #f1f1f1;
            padding: 10px;
            margin: 0;
            border: 1px solid #ccc;
            cursor: pointer;
        }
        .accordion-content {
            display: none;
            border: 1px solid #ccc;
            border-top: none;
            padding: 15px;
            margin-bottom: 10px;
        }
        .accordion-content input[type="text"] {
            margin-bottom: 5px;
        }
        .remove-btn {
            color: red;
            cursor: pointer;
            margin-left: 10px;
        }
        input.error {
    border: 2px solid red !important;
}
 
    body {
        background-color: #FFFFFF; /* White background for the page */
         font-family: 'Noto Sans', sans-serif;
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
        width: 100%;
        border-collapse: collapse;
    }

    td {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
         font-family: 'Noto Sans', sans-serif;
         
    color: #898989;/* Label text color */
  padding: .2em .6em .3em;
  font-size: 85%;
  font-weight: 700;
  line-height: 1;
    white-space: nowrap;
  vertical-align: baseline;
  border-radius: .25em;
    }
     th {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
          font-weight: bold;
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
       /*  display: flex; */
        align-items: center;
        justify-content: space-between; /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
         gap: 10px; 
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
            width: 100%; /* Full width for small screens */
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
          font-weight: bold;
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
         font-weight: bold;
        text-align: center; /* Center align text */
        padding: 4px; /* Reduced padding for the table header */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }
   

        .error-message {
    color: red;
    font-size: 12px;
    display: inline-block; /* Keeps it in a single row */
    white-space: nowrap; /* Prevents line breaks */
    overflow: hidden;
    text-overflow: ellipsis; /* Adds "..." if text is too long */
    max-width: 100%; /* Adjust width based on layout */
}
 label {
            color: black;
            font-weight: bold;
            font-size: 12px; /* Reduced font size */
        }
         .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}
    </style>
</head>
<body>
<div class="page-header">
<div class="page-header-buttons">
    <h3 style="margin:6px; color: darkcyan;font-weight: bold;">PrincipalEmployer Documents</h3>
    
 <button type="button" onclick="saveDocuments()" class="btn btn-default process-footer-button-cancel ng-binding" style=" margin-left: auto;display: flex;" >Save Configuration</button>
 </div>
</div>
    <form action="saveReportConfig" method="post">
        <div class="accordion">
            <h3 onclick="toggleSection('peDocSection')" style="background-color: #DDF3FF;font-size: 0.75rem;font-weight: bold;">Documents</h3>
            <div class="accordion-content" id="peDocSection">
                <div id="documentsContainer">
                    <c:forEach var="report" items="${Documents}">
                        <div class="document-row">
                            <input type="text" name="peDoc" value="${report}" placeholder="Enter Document name" autocomplete="off" style="text-transform:capitalize;"/>
                            <span class="remove-btn" onclick="$(this).parent().remove()">[Remove]</span>
                        </div>
                    </c:forEach>
                </div><br>
                <button type="button" onclick="addReportRow()" class="btn btn-default process-footer-button-cancel ng-binding" >Add Documents</button>
            </div>
        </div>
        <br/><br/>
       <!--  <button type="button" onclick="saveDocuments()" class="btn btn-default process-footer-button-cancel ng-binding" style="margin:6px;" >Save Configuration</button> -->
        <div id="saveStatusMsg" style="margin-top: 10px; font-weight: bold;"></div>

    </form>
</body>
</html>
