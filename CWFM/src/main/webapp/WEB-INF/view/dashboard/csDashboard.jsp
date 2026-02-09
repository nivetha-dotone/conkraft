 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title></head>
<body>

<div class="dashboard-container">
  <c:forEach var="card" items="${cards}">
    <c:choose>
        <c:when test="${card.count > 0}">
            <a href="#" onclick="loadCommonListDashboard('${card.link}','${card.heading}')" class="card-link">
        </c:when>
        <c:otherwise>
            <a href="javascript:void(0);" class="card-link disabled" style="pointer-events:none; opacity:0.6;">
        </c:otherwise>
    </c:choose>
        <div class="card ${card.borderClass}">
            <div class="card-left">
                <div class="icon-circle ${card.iconClass}">
                    <img src="${card.iconUrl}" alt="icon" />
                </div>
                <div class="card-info">
                    <div class="title">${card.title}</div>
                    <div class="desc">${card.description}</div>
                </div>
            </div>
            <div class="card-right">
                <div class="count">${card.count}</div>
                <button class="view-btn">View</button>
            </div>
        </div>
    </a>
</c:forEach>
  
</div>


</body>
</html>