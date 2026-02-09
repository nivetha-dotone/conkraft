<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Report List</title>
</head>
<body>
    <h1>Report List</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Report ID</th>
                <th>Parameters</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <!-- Loop through your report list to display each report -->
            <c:forEach var="report" items="${reports}">
                <tr>
                    <td>${report.id}</td>
                    <td>${report.parameters}</td>
                    <td>${report.status}</td>
                    <td>
                        <!-- Add a form with a button to trigger report generation -->
                        <form action="/generateReport" method="post">
                            <input type="hidden" name="reportId" value="${report.id}">
                            <input type="submit" value="Generate Report">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
