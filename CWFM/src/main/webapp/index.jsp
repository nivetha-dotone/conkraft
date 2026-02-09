<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            overflow: hidden; /* Remove scroll bar */
        }
         .header {
            background-color: rgb(0, 81, 81);
            color: white;
            padding: 10px;
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            margin: 0;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer; /* Add pointer cursor for clickable element */
        }
       /*  .header {
            background-color: rgb(0, 81, 81);
            color: white;
            padding: 10px;
            font-family: Arial, sans-serif;
            text-align: center;
        } */
        .carousel-container {
            width: 100%;
            overflow: hidden;
            white-space: nowrap; /* Prevent images from stacking vertically */
            margin-top: 0; 
        }
        .carousel-slide {
            display: inline-block;
            width: 100%;
            white-space: nowrap; /* Prevent images from stacking vertically */
        }
        .carousel-slide img {
            width: 100%;
            height: 500px; /* Set a fixed height for all images */
            object-fit: cover; /* Ensure images cover the specified dimensions */
        }
          .login-button {
            padding: 7px 10px;
            background-color: #005151;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .login-button:hover {
            background-color: #005151; /* Change background color on hover */
        } 
       
       /*  .header {
            background-color: rgb(0, 81, 81);
            color: white;
            padding: 10px;
            font-family: Arial, sans-serif; 
        }
        .header h1 {
            margin: 0;
            font-size: 16px;
            font-weight: bold; 
        } */
    </style>
</head>
<body>
<div style="display: flex; align-items: center;">
  <img src="resources/img/Dot1.png" height="100" width="100" style="display: inline-block; margin-right: 10px;">
  <h1 style="display: inline-block; margin: 0;">DOT1 SOLUTIONS PVT LTD</h1>
</div>
<div class="header">
    <h1 onclick="redirectToIndexPage()">CONTRACT WORKFORCE MANAGEMENT SYSTEM</h1>
    <button class="login-button" onclick="redirectToLoginPage()">Login</button>
</div>
<div class="carousel-container">
    <div class="carousel-slide">
        <img src="resources/img/CL.jpg" alt="Slide 1" >
        <img src="resources/img/aadhar3.png" alt="Slide 2">
        <img src="resources/img/Report.jpg" alt="Slide 3">
    </div>
</div>

<script>
    var currentSlide = 0;
    var slidesContainer = document.querySelector('.carousel-slide');
    var slides = slidesContainer.querySelectorAll('img');

    function moveSlide() {
        currentSlide = (currentSlide + 1) % slides.length;
        updateSlidePosition();
    }

    function updateSlidePosition() {
        var slideWidth = slides[0].clientWidth;
        var offset = -slideWidth * currentSlide;
        slidesContainer.style.transform = `translateX(${offset}px)`;
    }

    setInterval(moveSlide, 3000); // Change slide every 3 seconds

    function redirectToLoginPage() {
        window.location.href = 'UserLogin.jsp';
    }

    function redirectToIndexPage() {
        window.location.href = 'index.jsp';
    }
</script>
</body>
</html>


<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Insert title here</title>
<%@include file="component/allcss.jsp" %>
</head>
<body>
<h1>
</h1>
<%@include file="component/navbar.jsp" %>

<div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
  </div>
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="resources/img/CL.jpg" class="d-block w-100" alt="..." height="600px">
      <div class="carousel-caption d-none d-md-block">
        <h5>CLMS</h5>
        <p>Contract Labor Management System</p>
      </div>
    </div>
    <div class="carousel-item">
      <img src="resources/img/Aadhar.png" class="d-block w-100" alt="..." height="600px">
      <div class="carousel-caption d-none d-md-block">
        <h5>EP Enrollment</h5>
        <p>Aadhar based EP Enrollement</p>
      </div>
    </div>
    <div class="carousel-item">
      <img src="resources/img/Report.jpg" class="d-block w-100" alt="..." height="600px">
      <div class="carousel-caption d-none d-md-block">
        <h5>Reports</h5>
        <p>Easy to extract the data in the form of Reports</p>
      </div>
    </div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>
</body>
</html> --%>