<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sample Web Application</title>
    <link href="/WEB-INF/resources/css/styles.css" rel="stylesheet">
    <!-- <link href="/WFD_CWFM/src/main/resources/static/css/styles.css" rel="stylesheet">
    <link href="/WFD_CWFM/src/main/resources/static/css/styles.css" rel="stylesheet">
<link rel="stylesheet" href="styles.css"> -->
    <!-- <link rel="stylesheet" href="styles.css"> -->
</head>

<body>
    <header>
        <div class="logo">
            <!-- <img src="Dot1.png" alt="Logo"> -->
            <img src="img/Dot1.png" alt="Dot1 Logo">
              <img src="/Dot1.png" alt="Dot1 Logo">
            <img src="WFD_CWFM/src/main/webapp/WEB-INF/resources/img/Dot1.png" alt="Dot2 Logo">
            <img src="/WFD_CWFM/src/main/webapp/WEB-INF/resources/img/Dot1.png" alt="Dot3 Logo">
            <img src="/resources/img/Dot1.png" alt="Dot4 Logo">
            <img src="/WEB-INF/resources/img/Dot1.png" alt="Dot5 Logo">
             <img src="/WFD_CWFM/src/main/webapp/WEB-INF/resources/img/Dot1.png" alt="Dot6 Logo">
              <img src="/WEB-INF/view/img/Dot1.png" alt="Dot7 Logo">
               <img src="/WEB-INF/view/component/img/Dot1.png" alt="Dot8 Logo">
                 <img src="/img/Dot1.png" alt="Dot9 Logo">
            <h1>Sample Web Application</h1>
        </div>
        <nav>
            <ul>
                <li><a href="#">Home</a></li>
                <li><a href="#">About</a></li>
                <li><a href="#">Services</a>
                    <ul class="submenu">
                        <li><a href="services-page1.html">Service 1</a></li>
                        <li><a href="services-page2.html">Service 2</a></li>
                        <li><a href="services-page3.html">Service 3</a></li>
                    </ul>
                </li>
                <li><a href="#">Contact</a></li>
            </ul>
        </nav>
    </header>

    <div class="container">
        <aside class="sidebar">
            <ul>
                <li><a href="#">Dashboard</a></li>
                <li><a href="#">Profile</a></li>
                <li>
                    <a href="#">Settings</a>
                    <ul class="submenu">
                     <li><a class="link_name" href="#">CLMS</a></li>
          <li><a href="principalEmployer,jsp">Principal Employer</a></li>
          <li><a  href="ContractorHRProfile.jsp">Contractor</a></li>
          <li><a  href="workorder.jsp">Workorder</a></li>
          <li><a  href="minimumwagelist.jsp">Minimum Wage Master</a></li>
          <li><a  href="workmenDetail.jsp">Contract Workmen</a></li>
          <li><a href="wageMasterData.jsp">Workmen Wages</a></li> 
                        <!-- <li><a href="general-settings.html">General</a></li>
                        <li><a href="security-settings.html">Security</a></li>
                        <li><a href="privacy-settings.html">Privacy</a></li> -->
                    </ul>
                </li>
                <li><a href="#">Logout</a></li>
            </ul>
        </aside>

        <main>
            <!-- Add your main content here -->
            <h2>Welcome to our Web Application</h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod nulla eget erat efficitur, sit amet
                eleifend lorem congue. Nullam dignissim, lectus non vulputate tempor, lacus elit ullamcorper metus, eget
                convallis risus tortor in elit. Vestibulum tincidunt metus eu turpis tempus blandit. Nulla facilisi. Nam
                auctor est magna, eget elementum urna scelerisque sed. Nulla porttitor justo sit amet nunc interdum
                gravida. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;</p>
        </main>
    </div>

    <footer>
        <p>&copy; 2024 Sample Web Application. All rights reserved.</p>
    </footer>
</body>

</html>
