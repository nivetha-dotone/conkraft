<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
 <style>
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
            font-weight: bold;
            font-size: 15px; /* Reduced font size */
        }
        body {
            background-color: #FFFFFF;
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
            width: 150px; /* Reduced width */
            padding: 0.25rem;
            font-size: 0.875rem;
            border: 1px solid #ccc;
            border-radius: 4px;
            outline: none;
            margin-right: 10px;
            box-sizing: border-box;
        }
        .table-container {
            overflow-x: auto;
            margin: 0;
            padding: 0;
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
        padding: 4px; /* Reduced padding for table header */
    }
           .page-header {
        display: flex;
        align-items: center;
        justify-content: flex-start; 
        gap: 10px; 
        padding: 8px; 
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
    }
        select{
            width: 20%; /* Full width */
            padding: 6px; /* Reduced padding */
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 15px; /* Reduced font size */
            color: #000; /* Change text color to black */
            box-sizing: border-box;
        }
         select {
            background-color: #f9f9f9;
            color: #000;
        } 
       /*  input[type="text"] {
            background-color: #ffffff;
            color: #000;
        } */
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
    select{
            width: 20%; /* Full width */
            padding: 6px; /* Reduced padding */
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 15px; /* Reduced font size */
            color: #000; /* Change text color to black */
            box-sizing: border-box;
        }
        .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}
label {
            color: black;
            font-weight: bold;
            font-size: 12px; /* Reduced font size */
        }
        select, textarea {
    font-family: inherit;
    font-size: inherit;
    line-height: inherit;
    </style>
</head>
<body>
<div class="page-header">
    
   <label for="sectionDropdown" style=" color: darkcyan;"><spring:message code="label.selectSection"/></label>
        <select id="sectionDropdown" onchange="onSectionChange()" style="color:gray;" >
            <option value=""> Select Section </option>
            <c:forEach var="section" items="${sections}">
                <option value="${section.gmId}">${section.gmName}</option>
            </c:forEach>
        </select>
    
   <div class="page-header-buttons">

        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="saveMapping()">Save</button>
            <%-- <input type="hidden" name="gmTypeId" id="gmTypeId" value="${param.gmTypeId}"> --%>
    </div>
</div>
    <h4 style="color:gray;margin:4px;"><spring:message code="label.existingGeneralMasters"/></h4>
     <div style="display: flex; margin-top: 20px;margin-left:18px;">
        <!-- Available Pages -->
        <div>
            <h3 style=" color: darkcyan;font-size: 12px;font-weight: bold;list-style-type:none;"><spring:message code="label.availablePages"/></h3>
            <select id="availableBox" multiple style="width: 200px; height: 200px;color:gray;"></select>
        </div>

        <div style="margin: 0 20px; display: flex; flex-direction: column; justify-content: center;">
            <button onclick="addPageToSelected()">Add </button><br>
            <button onclick="removePageFromSelected()"> Remove</button>
        </div>

        <div>
            <h3 style=" color: darkcyan;font-size: 12px;font-weight: bold;padding-right:80px;"><spring:message code="label.selectedPages"/></h3>
            <select id="selectedBox" multiple style="width: 200px; height: 200px;color:gray;"></select>
        </div>
    </div>

  <!--   <div style="margin-top: 20px;">
        <button onclick="saveMapping()">Save</button>
    </div> -->
</body>
</html>
