<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users List</title>
      <link rel="stylesheet" href="styles.css">
    <!-- Link to Bootstrap for responsive design -->
   <!--  <link rel="stylesheet" href="resources/bootstrap-4.5.3/css/bootstrap.min.css"> -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.3/css/bootstrap.min.css">
    <!-- Add your CSS imports or inline styles here -->
</head>
<body>
 <header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">User Management System</a>
        <!-- Add a responsive hamburger menu for mobile -->
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <!-- Replace the list with a div for menu items -->
            <div class="navbar-nav ml-auto">
                <!-- Add navigation links here -->
                <a class="nav-item nav-link" href="#">Home</a>
                <a class="nav-item nav-link" href="#">Add User</a>
                <a class="nav-item nav-link" href="#">Edit User</a>
            </div>
        </div>
    </nav>
</header>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header text-center">
                        <h4>All User Details</h4>
                        <c:if test="${not empty msg}">
                            <h5 class="text-success">${msg}</h5>
                            <c:remove var="msg" />
                        </c:if>
                    </div>
                    <div class="card-body">
                    <div id="userListContainer">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Id</th>
                                    <th scope="col">Full Name</th>
                                    <th scope="col">Address</th>
                                    <th scope="col">Email</th>
                                    <th scope="col">Password</th>
                                    <!-- <th scope="col">Action</th> -->
                                    <th scope="col">Role</th>
                                </tr>
                            </thead>
                            <tbody>
                               <c:forEach items="${userList}" var="emp">
    <tr>
        <td>${emp.id}</td>
        <td>${emp.fullname}</td>
        <td>${emp.address}</td>
        <td>${emp.email}</td>
        <td>${emp.password}</td>
        <td>${emp.role}</td>
    </tr>
</c:forEach>
                            </tbody>
                        </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
