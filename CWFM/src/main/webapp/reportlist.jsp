<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <title>Report List</title>
</head>
<body>
    <h2>Report List</h2>
    <table border="1">
        <tr>
            <th>Report ID</th>
            <th>Parameters</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${reports}" var="report">
            <tr>
                <td>${report.id}</td>
                <td>${report.parameters}</td>
                <td>${report.status}</td>
                <td>
                    <a href="/view-report/${report.id}">View</a>
                    <!-- Add more actions like download or delete if needed -->
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
