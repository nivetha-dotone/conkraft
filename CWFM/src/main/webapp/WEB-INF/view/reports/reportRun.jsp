<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Report Parameters</title>
</head>
<body>
    <h1>Report Parameters</h1>
    <!-- Example code to render report details and parameter values -->
<%-- <h2>${reportName}</h2>
<p>${reportDescription}</p>
<c:forEach var="parameterType" items="${parameterTypes}">
    <label>${parameterType}</label>
    <select multiple>
        <c:forEach var="parameterValue" items="${parameterValuesMap[parameterType]}">
            <option>${parameterValue}</option>
        </c:forEach>
    </select>
</c:forEach> --%>
    
    <%-- Check if reportParameters is not null and iterate over it --%>
    <c:if test="${not empty reportParameters}">
        <form id="addReportForm" action="/CWFM/reportSetup/execute" method="post">
            <label for="reportName">Report Name:</label>
            <input type="text" id="reportName" name="reportName"  autocomplete="off" value="${reportParameters[0][0]}">>
            <br>
            <label for="reportDescription">Report Description:</label>
            <input type="text" id="reportDescription" name="reportDescription" autocomplete="off" value="${reportParameters[0][1]}">
            <br>
            <%-- Assuming the parameter 1 and parameter 2 values are the next two elements in the list --%>
           <label for="parameter1">Parameter 1:</label><br>
<select id="parameter1" name="parameter1" multiple style="width: 200px;">
    <c:forEach var="parameter" items="${reportParameters}">
        <c:if test="${parameter[2] == 'UnitCode'}">
            <c:forEach var="value" items="${fn:split(parameter[3], '\\\\n')}">
                <option value="${value}">${value}</option>
            </c:forEach>
        </c:if>
    </c:forEach>
</select>
<br>
<label for="parameter2">Parameter 2:</label><br>
<select id="parameter2" name="parameter2" multiple style="width: 200px;">
    <c:forEach var="parameter" items="${reportParameters}">
        <c:if test="${parameter[2] == 'Department'}">
            <c:forEach var="value" items="${fn:split(parameter[3], '\\\\n')}">
                <option value="${value}">${value}</option>
            </c:forEach>
        </c:if>
    </c:forEach>
</select>
            <br>
            <input type="submit" value="Submit">
        </form>
    </c:if>
</body>
</html>
