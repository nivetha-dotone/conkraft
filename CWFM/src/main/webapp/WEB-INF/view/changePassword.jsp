<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<%@include file="/resources/component/allcss.jsp" %>
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

<div class="container p-5">
<div class="row">
<div class="col-md-4 offset-md">
<div class="card paint-card">
<div class="card-body">

<p class="fs-4 text-center">Change Password</p>
<form action="UserProfile.jsp" method="post">
<div class="mb-3">
<label class="form-label">Current Password
</label>
<input required id="currentpassword" name="currentpassword" type="password" class="form-control"> 
</div>

<div class="mb-3">
<label class="form-label">New Password
</label>
<input required id="newpassword" name="newpassword" type="password" class="form-control"> 
</div>
<div class="mb-3">
<label class="form-label">Confirm Password
</label>
<input required id="confirmpassword" name="confirmpassword" type="password" class="form-control"> 
</div>
  <button type="submit" style="background-color: #005151;"onclick="changePassword()" class="btn text-white col-md-12">Submit</button>
</form>
<!-- <br>Don't have an account? <a href="signup.jsp" class="clink">create one</a> -->
<p id="message" style="color: red;"></p>
</div>
</div>
</div>
</div>

</div>
</body>
</html>