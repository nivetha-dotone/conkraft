<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users List</title>
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
                <ul class="navbar-nav ml-auto">
                    <!-- Add navigation links here -->
                    <li class="nav-item">
                        <a class="nav-link" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Add User</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Edit User</a>
                    </li>
                </ul>
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
        <td>${emp.fullName}</td>
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
