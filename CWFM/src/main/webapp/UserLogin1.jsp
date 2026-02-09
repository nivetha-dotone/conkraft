<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Login</title>
<%@include file="resources/component/allcss.jsp" %>
<style type="text/css">
.paint-card{
box-shadow:0 0 10px 0 rgba(0,0,0,0.3);
}
</style>
<style>
.button {
  background-color: #005151; /* Green */
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}


</style>
</head>
<body>
<%@include file="resources/component/navbar.jsp" %>
<div class="container p-5">
<div class="row">
<div class="col-md-4 offset-md">
<div class="card paint-card">
<div class="card-body">

<p class="fs-4 text-center">User Login</p>
<c:if test="${not empty msg}">
    <h5 class="text-danger">${msg}</h5>
    <% session.removeAttribute("msg"); %>  <!-- Remove msg from session -->
</c:if>
<%-- <c:if test="${not empty msg }">
							<h5 class="text-success">${msg }</h5>
							<c:remove var="msg" />
						</c:if> --%>
<form action="userlogin1" method="post">
<!-- <form action="UserProfile.jsp" method="post"> -->
<div class="mb-3">
<label class="form-label">User Name
</label>
<input required name="email" type="text" class="form-control"> 
</div>

<div class="mb-3">
<label class="form-label">Password
</label>
<input required name="password" type="password" class="form-control"> 
</div>
  <button type="submit" style="background-color: #005151;" class="btn text-white col-md-12">Login</button>
</form>
<!-- <br>Don't have an account? <a href="signup.jsp" class="clink">create one</a> -->
</div>
</div>
</div>
</div>

</div>
</body>
</html>