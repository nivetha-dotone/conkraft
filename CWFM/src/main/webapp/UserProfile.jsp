<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
  <head>
 
    <meta charset="UTF-8">
     <%@include file="resources/component/navbar2.jsp" %>
     <%@include file="resources/component/allcss2.jsp" %>
    
   
    <style>
        #principalEmployerContent {
            margin-left: 260px;
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: calc(100vh - 20px);
        }
        </style>
        <style>
 #contractorContent {
            margin-left: 260px;
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: calc(100vh - 20px);
        }
         </style>
        <style>
         #workOrderContent {
            margin-left: 260px;
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: calc(100vh - 20px);
        }
         </style>
        <style>
         #minWageContent {
            margin-left: 260px;
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: calc(100vh - 20px);
        }
         </style>
        <style>
         #contractWorkmenContent {
            margin-left: 260px;
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: calc(100vh - 20px);
        }
         </style>
        <style>
         #workmenWageContent {
            margin-left: 260px;
            padding: 20px;
            box-sizing: border-box;
            overflow-y: auto;
            height: calc(100vh - 20px);
        }
        
    </style>
    
    <script>
        function loadPrincipalEmployerPage() {
            document.getElementById("principalEmployerContent").innerHTML = '<iframe src="principalEmployer.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        </script>
        <script>
        
        function loadContractor() {
            document.getElementById("contractorContent").innerHTML = '<iframe src="ContractorHRProfile.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        </script>
        <script>
       /*  function loadWorkorder() {
            document.getElementById("workOrderContent").innerHTML = '<iframe src="workorder.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        } */
        function loadWorkorder() {
            console.log('Loading Workorder'); // Add this line
            document.getElementById("workOrderContent").innerHTML = '<iframe src="workorder.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        </script>
        <script>
        function loadMinWage() {
            document.getElementById("minWageContent").innerHTML = '<iframe src="minimumwagelist.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        </script>
        <script>
        function loadContractWorkmen() {
            document.getElementById("contractWorkmenContent").innerHTML = '<iframe src="workmenDetail.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }
        </script>
        <script>
        function loadWorkmenWage() {
            document.getElementById("workmenWageContent").innerHTML = '<iframe src="wageMasterData.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        }

       /*  function loadPrincipalDashboard() {
            document.getElementById("dashboardContainer").innerHTML = '<iframe src="dashboard.jsp" frameborder="0" width="100%" height="100%"></iframe>';
        } */
    </script>


       <title> Menu </title>
    <link href='component/style.css' rel='stylesheet'>
       <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
   </head>
<body>

  <div class="sidebar close">
     <div class="logo-details">
     <i class='bx bx-menu' ></i>
      <span class="logo_name">Menu</span>
    </div> 
    <ul class="nav-links">
      <!--  <li>
        <a href="#">
          <i class='bx bx-grid-alt' ></i>
          <span class="link_name">Dashboard</span>
        </a>
        <ul class="sub-menu blank">
          <li><a class="link_name" href="#">CLMS</a></li>
        </ul>
      </li>  -->
     <li>
    <a href="dashboard.jsp">
        <i class='bx bx-grid-alt'></i>
        <span class="link_name">Dashboard</span>
    </a>
    <ul class="sub-menu blank">
        <li><a class="link_name" href="dashboard.jsp">Dashboard</a></li>
    </ul>
</li>
      <li>
        <div class="iocn-link">
          <a href="#">
            <i class='bx bx-collection' ></i>
            <span class="link_name">CLMS</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">CLMS</a></li>
          <li><a href="#" onclick="loadPrincipalEmployerPage();">Principal Employer</a></li>
          <li><a  href="ContractorHRProfile.jsp">Contractor</a></li>
          <li><a  href="workorder.jsp">Workorder</a></li>
          <li><a  href="minimumwagelist.jsp">Minimum Wage Master</a></li>
          <li><a  href="workmenDetail.jsp">Contract Workmen</a></li>
          <li><a href="wageMasterData.jsp">Workmen Wages</a></li> 
          <!--  <li><a href="#" onclick="loadContractor();">Contractor</a></li>
          <li><a href="#" onclick="loadWorkorder();">Workorder</a></li>
          <li><a href="#" onclick="loadMinWage();">Minimum Wage Master</a></li>
          <li><a href="#" onclick="loadContractWorkmen();">Contract Workmen</a></li>
          <li><a href="#" onclick="loadWorkmenWage();">Workmen Wages</a></li> -->
        </ul>
      </li>
      <li>
        <div class="iocn-link">
          <a href="#">
            <i class='bx bx-book-alt' ></i>
            <span class="link_name">Compliance</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">Compliance</a></li>
          <li><a href="#">PF</a></li>
          <li><a href="#">ESIC</a></li>
          
        </ul>
      </li>
      <li>
        <div class="iocn-link">
          <a href="#">
            <i class='bx bx-plug' ></i>
            <span class="link_name">Reports</span>
          </a>
          <i class='bx bxs-chevron-down arrow' ></i>
        </div>
        <ul class="sub-menu">
          <li><a class="link_name" href="#">Reports</a></li>
          <li><a href="#">Wage Cost Report</a></li>
          <li><a href="#">Muster Roll Report</a></li>
          <li><a href="#">Daily Present Report</a></li>
        </ul>
      </li>
      
      <li>
    <div class="profile-details">
      <div class="profile-content">
        <img src="resources/img/profile.jpg" alt="profileImg">
      </div>
      <div class="name-job">
        <div class="profile_name">Sunil Sahu</div>
        <div class="job">Admin</div>
      </div>
      <i class='bx bx-log-out' ></i>
    </div>
    
  </li>
</ul>
  </div>
  
    
    
    
    
  <div class="content-section">
    <div id="principalEmployerContent">
    </div>
      <div id="contractorContent">
    </div>
     <div id="workOrderContent">
    </div>
     <div id="minWageContent">
    </div>
     <div id="contractWorkmenContent">
    </div>
     <div id="workmenWageContent">
    </div> 
    </div> 

  <script src="component/script.js"></script> 

</body>
</html>

