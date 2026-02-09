<%@ page import="com.wfd.dot1.cwfm.pojo.MasterUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%
String redirectUrl = (String) request.getAttribute("redirectUrl");
%>

<html>
<head>
    <title>DigiLocker Redirect</title>
</head>
<body>
    <% if (redirectUrl != null && !redirectUrl.isEmpty()) { %>
        <script>
            // Option 1: Redirect from here
            window.location.href = "<%=redirectUrl%>";
        </script>

        <!-- Option 2: Show inside iframe instead -->
        <!--
        <iframe src="<%=redirectUrl%>" width="100%" height="600px"></iframe>
        -->
    <% } else { %>
        <p>No DigiLocker URL found.</p>
    <% } %>
</body>
</html>
