<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Report Setup</title>
</head>
<body>
   <div class="page-header">
        Report Setup
        <div class="header-buttons">
        <button type="submit" onclick="redirectToReportAdd()">Add</button>
        <button type="submit" onclick="redirectToPEEdit('${cmSPRINCIPALEMPLOYER.UNITID}')">Edit</button>
        </div>
        </div>
    <table border="1">
        <thead>
            <tr>
                <th>Report Name</th>
                <th>Batch File Name</th>
               <!--  <th>Parameters</th> -->
            </tr>
        </thead>
        <tbody>
          <c:forEach var="report" items="${reports}">
                <tr>
                    <td>${report[0]}</td>
                    <td>${report[1]}</td>
                    <%-- <td>${report[2]}</td> --%>
                </tr>
            </c:forEach>
           <%--  <c:forEach items="${reports}" var="report">
                <tr>
                    <td>${report.reportName}</td>
                    <td>${report.batchFileName}</td>
                    <td>${report.parameters}</td>
                </tr>
            </c:forEach> --%>
        </tbody>
    </table>
   <!--  <form action="addReport" method="post">
        <label for="reportName">Report Name:</label>
        <input type="text" id="reportName" name="reportName">
        <br>
        <label for="batchFileName">Batch File Name:</label>
        <input type="text" id="batchFileName" name="batchFileName">
        <br>
        <label for="parameters">Parameters:</label>
        <input type="text" id="parameters" name="parameters">
        <br>
        <input type="submit" value="Add Report">
    </form> -->
</body>
</html>
