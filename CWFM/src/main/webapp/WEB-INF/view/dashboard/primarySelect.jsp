<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Employer & Department</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f4f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: #fff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 6px 15px rgba(0,0,0,0.1);
            width: 400px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: darkcyan;
        }
        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
            color: #333;
        }
        select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        button {
            margin-top: 25px;
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 6px;
            background-color: darkcyan;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
        }
        button:hover {
            background-color: #007777;
        }
    </style>
     <script src="resources/js/cms/dashboard.js"></script>
</head>
<body>
    <div class="container">
        <h2>Select Employer & Department</h2>
        
        <label for="principalEmployerId">Principal Employer:</label>
        <select id="principalEmployerId" name="principalEmployerId" required>
            <option value="">Select Employer</option>
            <c:forEach items="${principalEmployers}" var="pe">
                <option value="${pe.id}">${pe.description}</option>
            </c:forEach>
        </select>

        <label for="deptId">Department:</label>
        <select id="deptId" name="deptId" required>
            <option value="">Select Department</option>
            <c:forEach items="${Dept}" var="dept">
                <option value="${dept.id}">${dept.description}</option>
            </c:forEach>
        </select>

        <button type="button" onclick="getMyDashboard();">Proceed to Dashboard</button>
    </div>
</body>
</html>
