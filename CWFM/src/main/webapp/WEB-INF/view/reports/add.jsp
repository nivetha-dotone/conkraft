<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Report Setup</title>
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/report.js"></script>
    <script src="resources/js/commonjs.js"></script>
    <script>
     
    </script>
</head>
<body>
    <div class="page-header">
        Reports Add Page
        <div class="header-buttons">
            <button type="button" onclick="submitReportForm()">Save</button>
        </div>
    </div>
    <f:form id="addReportForm" action="/CWFM/reportSetup/addReport" method="post">
        <label for="reportName">Report Name:</label>
        <input type="text" id="reportName" name="reportName" autocomplete="off">
        <br>
        <label for="rdlName">RDL Name:</label>
        <input type="text" id="rdlName" name="rdlName" autocomplete="off">
        <br>
        <label for="reportDescription">Report Description:</label>
        <input type="text" id="reportDescription" name="reportDescription" autocomplete="off">
        <br>
        <label for="selectedGmTypes">Select GM Type:</label><br>
        <select id="availableOptions" multiple style="width: 200px;">
            <c:forEach var="gmType" items="${gmTypes}">
                <option value="${gmType.gmTypeId}">${gmType.gmType}</option>
            </c:forEach>
        </select>
        <div style="display: inline-block; vertical-align: top;">
            <button type="button" onclick="addSelected()">Add</button><br>
            <button type="button" onclick="addAll()">Add All</button><br>
            <button type="button" onclick="removeSelected()">Remove</button><br>
            <button type="button" onclick="removeAll()">Remove All</button><br>
        </div>
        <select id="selectedOptions" name="selectedGmTypes[]" multiple style="width: 200px;"></select>
        <br>
        <input type="hidden" id="selectedGmTypes" name="selectedGmTypes" />
        <!-- <input type="submit" value="Add Report"> -->
    </f:form>
</body>
</html>
