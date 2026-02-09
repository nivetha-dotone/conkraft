<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
 <title>Users Add</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="resources/js/jquery.min.js"></script>
   <!--  <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script> -->
 <!--    <script src="resources/js/commonjs.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css">  -->

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

/* .page-header {
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
} */

    .page-header {
        display: flex;
        align-items: center;
        justify-content: flex-end; /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
    }
.header-buttons {
    float: right;
    margin-right: 20px;
    gap:10px;
}

.tabs {
    overflow: hidden;
    border-bottom: 2px solid #005151;
    margin-bottom: 20px;
}

.tabs button {
    background-color: #fff; /* Tab background color */
    border: 1px solid #ddd; /* Optional: add a border for visibility */
     border-radius: 3px;
    outline: none;
    padding: 5px 10px;/* Reduced height */
    cursor: pointer;
    font-size: 12px;
    transition: background-color 0.3s, color 0.3s;
    color: #005151; /* Tab text color */
    font-family: 'Noto Sans', sans-serif;
     margin-right: 5px;
}
  

.tabs button.active {
    background-color: #005151; /* Active tab background color */
    color: #fff; /* Active tab text color */
    border-bottom: 2px solid #fff;
}

.tab-content {
    display: none;
    padding: 10px;
    background-color: white;
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
     .error-message {
    color: red;
    font-family: Arial, sans-serif;
    font-size: 12px;
    font-weight:bold;
}
 .error {
    background-color: #ffcccc; /* Light red background */
}
  .page-header-buttons {
    margin-left: auto;      /* <<< THIS moves the buttons to the right */
    display: flex;
    gap: 10px;
}
    </style>
</head>

<body>
    
<!-- <h1>Organization Level Entry</h1> -->
<div class="page-header">
 <div class="page-header-buttons">
    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="changePassword()">Save</button>
</div>
</div>
<form id="userFormID" autocomplete="off">
    <table class="ControlLayout">
        <tr>
            <th><label class="custom-label"><span class="required-field">*</span>User Account:</label></th>
            <td><input type="text" name="userAccount" id="userAccount" value="${userAccount}" readonly style="background-color: #e9ecef; height: 20px;" size="30" maxlength="30" autocomplete="off"/></td>
        </tr>
        <tr>
            <th><label class="custom-label"><span class="required-field">*</span>Old Password:</label></th>
            <td><input type="password" name="oldPassword" id="oldPassword" style="height: 20px;" size="30" maxlength="30" autocomplete="new-password" autocomplete="off"/></td>

            <th><label class="custom-label"><span class="required-field">*</span>New Password:</label></th>
           <!--  <td><input type="password" name="newPassword" style="height: 20px;" size="30" maxlength="30" /></td> -->
             <td>
                <input type="password" id="newPassword" name="newPassword" style="height: 20px;" size="30" maxlength="30" onkeyup="validatePassword()" autocomplete="off"/>
                <span id="passwordMessage" style="color: red;"></span>
            </td>
        </tr>
    </table>
</form>



</body>
</html>
