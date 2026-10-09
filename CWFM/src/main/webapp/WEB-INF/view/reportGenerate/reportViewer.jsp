<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>View Report</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/css/cmsstyles.css">
	 <script src="resources/js/jquery.min.js"></script>
<script src="resources/js/cms/principalEmployer.js"></script>
<script src="resources/js/cms/contractor.js"></script>
<script src="resources/js/cms/workmen.js"></script>
<script src="resources/js/cms/report.js"></script>
<script src="resources/js/cms/generateReport.js"></script>


    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            color: #333;
        }

        h2 {
            margin-bottom: 15px;
        }

        .table-container {
            width: 100%;
            overflow: auto;
            max-height: 75vh;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            white-space: nowrap;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px 12px;
            text-align: left;
        }

        th {
            background-color: #005151;
            color: white;
            position: sticky;
            top: 0;
        }

        tbody tr:nth-child(even) {
            background-color: #f5f5f5;
        }
    </style>
</head>

<body>
<%-- <div id="reportError"
     style="display:none; color:#842029; margin-top:10px;">
</div>

<label id="msg-reportNotFound"><spring:message code="label.pfcap"/></label> --%>
    <h2>
        <c:out value="${reportTitle}" />
    </h2>

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <c:if test="${not empty reportData}">
                        <c:forEach
                                items="${reportData[0]}"
                                var="column">
                            <th>
                                <c:out value="${column.key}" />
                            </th>
                        </c:forEach>
                    </c:if>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${reportData}" var="row">
                    <tr>
                        <c:forEach items="${row}" var="column">
                            <td>
                                <c:out value="${column.value}" />
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <c:if test="${empty reportData}">
        <p>No report records found.</p>
    </c:if>

</body>
</html>
