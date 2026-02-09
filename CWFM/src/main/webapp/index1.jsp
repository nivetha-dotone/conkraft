<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
    
    <title>User Dashboard</title>
   <%--  <%@include file="/resources/component/allcss.jsp" %> --%>
<!-- <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'> -->
<link rel="stylesheet" type="text/css" href="resources/css/styles.css">
<link rel="stylesheet" href="styles.css">
<!-- <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> -->
 <style>
    .mainmenu-link .menu-icon {
        width: 20px;
        height: 20px;
        margin-right: 5px;
         color:black;
    }
  .users-link .menu-text {
    color:black /* Your desired color */;
    width: 20px;
    height: 20px;
    margin-right: 5px;
}
     .mainmenu-link .menu2-icon {
        width: 15px;
        height: 10px;
        margin-left: 15px;
         color:black;
    }
    .reports-link .report-icon {
        width: 20px;
        height: 20px;
        margin-right: 5px;
    }
     .reports-link .report2-icon {
        width: 15px;
        height: 10px;
        margin-left: 15px;
         color:black;
    }


   /*  .mainmenu-link.active .menu-icon.arrow-down {
        display: none; 
    }

    .mainmenu-link.active .menu-icon.arrow-up {
        display: inline-block;
    } */
    .mainmenu-link .arrow-up {
    display: none;
     color:black;
}

.mainmenu-link .arrow-down {
    display: inline-block;
     color:black;
}
.reports-link .arrow-up {
    display: none;
     color:black;
}

.reports-link .arrow-down {
    display: inline-block;
     color:black;
}
.header {
    background-color: rgb(0, 81, 81);
    color: white;
    padding: 10px;
    font-family: Arial, sans-serif; /* Set the font family */
}

.header h1 {
    margin: 0;
    font-size: 24px;
    font-weight: bold; /* Optionally set the font weight */
    
}
  .users-link .arrow-up {
    display: none;
}

.users-link .arrow-down {
    display: inline-block;
}
.main-content {
    margin-top: 60px;
    padding: 20px;
    overflow-y: auto;
    transition: margin-left 0.3s ease; 
}
</style>
<script>
    var contextPath = "${contextPath}";
    console.log(contextPath);
</script>
    <style>
        /* Your CSS styles */
        html, body {
            margin: 0;
            padding: 0;
        }

        .header {
            background-color: rgb(0, 81, 81);
            color: white;
            padding: 10px;
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: space-between;
            align-items: center;
         /*    position: fixed;
            width: auto;
            z-index: 1000; */
        }

        .left-menu {
            display: flex;
        }

        .left-menu a {
            margin-right: 10px;
            text-decoration: none;
            color: white;
        }

        .left-menu a img {
            width: 20px;
            height: 20px;
        }

        .welcome-logout {
            display: flex;
            align-items: center;
        }

        .welcome {
            margin-right: 10px;
        }

        .logout {
            text-decoration: none;
            color: white;
        }

        /* .sidebar {
            position: fixed;
            top: 60px;
            left: -200px;
            height: calc(100% - 60px);
            width: 200px;
            background-color: #fff;
            padding: 0;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            transition: left 0.3s ease;
        } */
.sidebar {
    position: fixed;
    top: 40px; /* Adjust this value to match the height of your header */
    left: 0; /* Start with the sidebar visible */
    height: calc(100% - 60px); /* Adjust height to fill remaining space */
    width: 200px; /* Fixed width for the sidebar */
    background-color: #fff;
    padding: 0;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease; /* Use transform for smoother animation */
    /* Ensure box-sizing includes padding and border */
    box-sizing: border-box;
    overflow-y: auto;
}
.sidebar.show {
    left: 0;
}
.sidebar.closed {
    width: 0; 
       transform: translateX(-200px); /* Move sidebar off-screen when closed */
}

/* .sidebar.show + .form-content {
    margin-left: 0;
    width: calc(100% - 20%);
} */
/* #mainContent.form-content {
    margin-left: 200px; 
} */

/* .sidebar.closed + .main-content {
    margin-left: 0;
}

.sidebar.show + .main-content {
    margin-left: 200px;
}
.sidebar + .main-content {
    margin-left: 200px;
}
  */       .sidebar ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        .sidebar li {
            margin-bottom: 10px;
        }

        .sidebar a {
            text-decoration: none;
            color: black;
        }

       /*  .main-content {
            margin-top: 60px;
            padding: 20px;
            overflow-y: auto;
        } */
 .main-content {
    margin-top: 60px; /* Adjust top margin to accommodate header height */
    height: calc(100% - 60px); /* Adjust height to fill remaining space */
    padding: 20px;
    transition: margin-left 0.3s ease; 
    overflow-y: auto;
}


/* .form-content {
    position: absolute;
    top: 0;
    left: 0; 
     margin-left: 20%;  
    transition: margin-left 0.3s ease; 
} */
/* .form-content {
    padding: 20px;
    box-sizing: border-box;
    overflow-y: auto;
    height: calc(100vh - 20px);
    margin-left: 0; /* Adjusted to 0 when sidebar is closed */
    transition: margin-left 0.5s ease, width 0.5s ease; /* Add width transition */
} */
.form-content {
     /* Adjust as needed when the sidebar is open */
    width: 70%; /* Adjust as needed when the sidebar is open */
    transition: margin-left 0.5s, width 0.5s;
    z-index: 1;
    margin-top: 20px;
    margin-right: 10px; /* Adjust to 0 when sidebar is closed */
     overflow-y: auto;
      margin-left: 200px;
       width: calc(100% - 200px); /* Adjust width to fill remaining space */
    transition: margin-left 0.3s ease, width 0.3s ease; /* Transition both margin and width */
      
}

.form-content.close {
    margin-left: 0; /* Adjust margin to fill the entire width when sidebar is closed */
    width: 100%; /* Adjust width to fill the entire width when sidebar is closed */
}



/* .sidebar.show + .form-content {
    margin-left: 0;
    width: calc(100% - 20px);
} */
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
</head>
<body>
    <!-- Header -->
    <div class="header">
        <div class="left-menu">
            <a href="#" onclick="toggleSidebar()">
                <img src="resources/img/3lines.png" alt="Menu">
            </a>
            <a href="index.jsp"><img src="resources/img/home.png" alt="Home"></a>
        </div>
        <div class="welcome-logout">
            <!-- Welcome message and logout link -->
            <a href="logout.jsp" class="logout">Logout</a>
        </div>
    </div>

    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
    <ul>
           <!--  <li id="dashboard-item" style="margin-top: 20px;">
    <a href="#" onclick="showDashboard()" class="dashboard-link">
        <img src="resources/img/graph.png" alt="Dashboard" class="menu-icon">
        <span class="menu-text">Dashboard</span>
    </a>
</li> -->
           
            
            <!-- <li id="main-menu-item" style="margin-top: 20px;">
                <a href="#" onclick="toggleSubMenu()" class="mainmenu-link">
                    <img src="resources/img/submenu.png" alt="Main Menu" class="menu-icon">
                    <span class="menu-text">CLMS</span>
                </a>
              <ul class="sub-menu">
    <li style="margin-top: 10px;"><a href="#" onclick="loadContent('principalEmployer.jsp')">Principal Employer</a></li>
    <li><a href="#" onclick="loadContent('ContractorHRProfile.jsp')">Contractor</a></li>
    <li><a href="#" onclick="loadContent('workorder.jsp')">Work Order</a></li>
    <li><a href="#" onclick="loadContent('minimumwagelist.jsp')">Minimum Wage Master</a></li>
    <li><a href="#" onclick="loadContent('workmenDetail.jsp')">Contract Workmen</a></li>
    <li><a href="#" onclick="loadContent('wageMasterData.jsp')">Workmen Wages</a></li>
</ul>
            </li> -->
    <li id="main-menu-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleSubMenu()" class="mainmenu-link">
       <!--  <img src="resources/img/submenu.png" alt="Before CLMS" class="menu-icon"> -->
        <span class="menu-text">CLMS</span>
        <img src="resources/img/uarrow.png" alt="After CLMS" class="menu2-icon arrow-up">
        <img src="resources/img/darrow.png" alt="After CLMS" class="menu2-icon arrow-down">
    </a>
    <ul class="sub-menu">
        <li style="margin-top: 10px;"><a href="#" onclick="loadContent('principalEmployer.jsp')">Principal Employer</a></li>
        <li><a href="#" onclick="loadContent('ContractorHRProfile.jsp')">Contractor</a></li>
        <li><a href="#" onclick="loadContent('workorder.jsp')">Work Order</a></li>
        <li><a href="#" onclick="loadContent('minimumwagelist.jsp')">Minimum Wage Master</a></li>
        <li><a href="#" onclick="loadContent('workmenDetail.jsp')">Contract Workmen</a></li>
        <li><a href="#" onclick="loadContent('wageMasterData.jsp')">Workmen Wages</a></li>
    </ul>
</li>
            <li id="reports-item" style="margin-top: 20px;">
                <a href="#" onclick="showReports()" class="reports-link">
             <!--        <img src="resources/img/compliance.png" alt="Reports" class="menu-icon"> -->
                    <span class="menu-text">Compliance</span>
                    <img src="resources/img/uarrow.png" alt="After reports" class="report2-icon arrow-up">
        <img src="resources/img/darrow.png" alt="After reports" class="report2-icon arrow-down">
                </a>
           <!--  <ul class="sub-report">
        <li><a href="#" >Wage Cost Report</a></li>
        <li><a href="#" >Muster Role Report</a></li>
    </ul> -->
            </li>
            
             <li id="reports-item" style="margin-top: 20px;">
                <a href="#" onclick="showReports()" class="reports-link">
                 <!--    <img src="resources/img/report.png" alt="Reports" class="menu-icon"> -->
                    <span class="menu-text">Reports</span>
                    <img src="resources/img/uarrow.png" alt="After reports" class="report2-icon arrow-up">
        <img src="resources/img/darrow.png" alt="After reports" class="report2-icon arrow-down">
                </a>
           <!--  <ul class="sub-report">
        <li><a href="#" >Wage Cost Report</a></li>
        <li><a href="#" >Muster Role Report</a></li>
    </ul> -->
            </li>
            
            <li id="users-item" style="margin-top: 20px;">
    <a href="#" onclick="toggleUsersSubMenu()" class="users-link">
    <!--     <img src="resources/img/report.png" alt="Users" class="users-icon"> -->
        <span class="menu-text">Users</span>
        <img src="resources/img/uarrow.png" alt="After users" class="users-icon arrow-up">
        <img src="resources/img/darrow.png" alt="After users" class="users-icon arrow-down">
    </a>
    <ul class="sub-users">
       <!--  <li><a href="#" onclick="loadContent('UserList.jsp')">User List</a></li> -->
        <li><a href="#" onclick="loadUserList()">User List</a></li>
        <li><a href="#" onclick="loadContent('UserAdd.jsp')">Add User</a></li>
    </ul> 
</li>
        </ul>
    </div>

   <div id="mainContent" class="form-content" >
        <!-- Pie charts for principal employer, contractor, work order, and workmen -->
        <%-- <h2>Dashboard</h2>
        <div style="display: flex; justify-content: space-around;">
            <canvas id="principalEmployerChart" width="200" height="200"></canvas>
            <canvas id="contractorChart" width="200" height="200"></canvas>
            <canvas id="workOrderChart" width="200" height="200"></canvas>
            <canvas id="workmenChart" width="200" height="200"></canvas>
        </div> --%>
    </div>
    <!-- JavaScript to handle sidebar toggle and content loading -->
    <script>
   /*  function toggleSidebar() {
        var sidebar = document.querySelector('.sidebar');
        var mainContent = document.getElementById('mainContent');
        var sidebarWidth = 200; // Adjust this value to match your sidebar width

        if (sidebar.style.left === '0px' || sidebar.style.left === '') {
            sidebar.style.left = '-' + sidebarWidth + 'px';
            mainContent.style.marginLeft = '0px';
            mainContent.style.width = '100%'; // Adjust the width to fit the screen when the sidebar is closed
        } else {
            sidebar.style.left = '0px';
            mainContent.style.marginLeft = sidebarWidth + 'px';
            mainContent.style.width = 'calc(100% - ' + sidebarWidth + 'px)'; // Adjust the width to accommodate the sidebar
        }
    }
    document.addEventListener("DOMContentLoaded", function() {
        console.log("DOM fully loaded");
        var element = document.getElementById("mainContent");
        if (element) {
            console.log("Element found:", element);
            element.addEventListener("click", function() {
                console.log("Element clicked");
                // Your event handling code here
            });
        } else {
            console.error("Element not found.");
        }
    }); */
    // Function to handle sidebar toggle when the toggle button is clicked
   /*  document.addEventListener("DOMContentLoaded", function() {
        var toggleButton = document.querySelector('.toggle-btn');
        toggleButton.addEventListener('click', function() {
            toggleSidebar();
        });
    });
    function adjustMainContentWidth() {
        var mainContent = document.getElementById('mainContent');
        var sidebar = document.querySelector('.sidebar');
        var sidebarWidth = 200; // Adjust this value to match your sidebar width
        
        if (sidebar.style.left === '0px') {
            mainContent.style.marginLeft = sidebarWidth + 'px';
            mainContent.style.width = 'calc(100% - ' + sidebarWidth + 'px)';
        } else {
            mainContent.style.marginLeft = '0px';
            mainContent.style.width = '100%';
        }
    } */
  /*   var isSidebarExpanded = true;

    function toggleSidebar() {
        var sidebar = document.getElementById('sidebar');
        var mainContent = document.getElementById('mainContent');
        var toggleButton = document.querySelector('.toggle-btn');
        var listContainer = document.querySelector('.sidenav ul');

        if (isSidebarExpanded) {
            // Hide sidebar
            sidebar.style.width = '5%';
            mainContent.style.marginLeft = '5%';
            toggleButton.innerHTML = '>>';
            listContainer.style.display = 'none';
        } else {
            // Show sidebar
            sidebar.style.display = 'block';
            sidebar.style.width = '20%';
            mainContent.style.marginLeft = '20%';
            toggleButton.innerHTML = '<img src="resources/img/3lines.png" alt="Menu" class="toggle-img"> <span class="toggle-text">Menu</span>';
            listContainer.style.display = 'block';
        }

        // Toggle the sidebar state
        isSidebarExpanded = !isSidebarExpanded;

        // Adjust mainContent width when sidebar is toggled
        adjustMainContentWidth();
    } */

  /*   function adjustMainContentWidth() {
        var mainContent = document.getElementById('mainContent');
        if (!isSidebarExpanded) {
            mainContent.style.width = '95%';
        } else {
            mainContent.style.width = 'calc(100% - 20%)'; // Adjusted for sidebar width
        }
    } */

    // Call adjustMainContentWidth when the window is resized
   /*  window.addEventListener('resize', adjustMainContentWidth);

    // Initially adjust mainContent width based on sidebar state
    adjustMainContentWidth(); */

   /*  function loadContent(jspFile) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("mainContent").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", jspFile, true); // Change the request method to POST
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); // Set the content type
        xhttp.send(); // Send the request
    } */
    document.querySelector('.toggle-btn').addEventListener('click', function() {
        document.querySelector('.form-content').classList.toggle('form-content-open');
    });
    function loadContent(jspFile) {
        var links = document.querySelectorAll('.sub-menu li'); // Get all submenu links
        links.forEach(function(link) {
            link.classList.remove('highlighted'); // Remove 'highlighted' class from all links
        });

        // Add 'highlighted' class to the parent li element of the clicked link
        event.target.parentElement.classList.add('highlighted');

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("mainContent").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", jspFile, true);
        xhttp.send();
    }
    function loadUserList() {
        var contextPath = '<%= request.getContextPath() %>'; // This will be evaluated on the server side

        // Construct the URL using the contextPath variable
        var url = contextPath + '/user/userlist';
        console.log("Constructed URL:", url); // Log the constructed URL to the console

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("mainContent").innerHTML = this.responseText;
                
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }
    function loadPrincipalEmployerList() {
        var contextPath = '<%= request.getContextPath() %>'; // This will be evaluated on the server side

        // Construct the URL using the contextPath variable
        var url = contextPath + '/user/userlist';
        console.log("Constructed URL:", url); // Log the constructed URL to the console

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("mainContent").innerHTML = this.responseText;
                
            }
        };
        xhttp.open("GET", url, true);
        xhttp.send();
    }
    
    <%-- function loadUserList() {
        var contextPath = '<%= request.getContextPath() %>'; // This will be evaluated on the server side
        
        // Construct the URL using the contextPath variable
        var url = contextPath + '/user/userlist';
        console.log("Constructed URL:", url); // Log the constructed URL to the console
        alert(url);
        $.ajax({
            url: url, // Use the constructed URL
            type: "GET",
            success: function(data) {
                // Handle success response, possibly update the page with data
                console.log("Response Data:", data);
                $('#userListContainer').html(data);
            },
            error: function(xhr, status, error) {
                // Handle error response
                console.error(error);
            }
        });
    } --%>
      /*   function loadContent(jspFile) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("mainContent").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", jspFile, true);
            xhttp.send();
        } */
        /* function toggleSubMenu() {
            var subMenu = document.querySelector('#main-menu-item .sub-menu');
            subMenu.style.display = subMenu.style.display === 'block' ? 'none' : 'block';
        } */
        function toggleSubMenu() {
            var submenu = document.querySelector('.sub-menu');
            submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
            var mainmenuLink = document.querySelector('.mainmenu-link');
            mainmenuLink.classList.toggle('active');

            var arrowUpIcon = document.querySelector('.arrow-up');
            var arrowDownIcon = document.querySelector('.arrow-down');
            if (submenu.style.display === 'block') {
                arrowUpIcon.style.display = 'inline-block';
                arrowDownIcon.style.display = 'none';
            } else {
                arrowUpIcon.style.display = 'none';
                arrowDownIcon.style.display = 'inline-block';
            }
        }
        function showDashboard() {
            var mainContent = document.getElementById('mainContent');
            mainContent.innerHTML = '<h2>Dashboard</h2>'; // Clear existing content
            
            // Check if the canvas element exists before rendering the chart
            var principalEmployerCanvas = document.getElementById('principalEmployerChart');
            if (principalEmployerCanvas) {
                renderPieChart("principalEmployerChart", principalEmployerData);
                renderPieChart("contractorChart", contractorData);
                renderPieChart("workOrderChart", workOrderData);
                renderPieChart("workmenChart", workmenData);
            } else {
                console.error("Canvas element not found. Unable to render pie chart.");
            }
        }
         function showReports() {
        	 var subreport = document.querySelector('.sub-report');
        	 subreport.style.display = subreport.style.display === 'block' ? 'none' : 'block';
             var subreportLink = document.querySelector('.reports-link');
             subreportLink.classList.toggle('active');

             var arrowUpIcon = document.querySelector('.arrow-up');
             var arrowDownIcon = document.querySelector('.arrow-down');
             if (subreport.style.display === 'block') {
                 arrowUpIcon.style.display = 'inline-block';
                 arrowDownIcon.style.display = 'none';
             } else {
                 arrowUpIcon.style.display = 'none';
                 arrowDownIcon.style.display = 'inline-block';
             }
             } 

         function toggleUsersSubMenu() {
        	    var subUsersMenu = document.querySelector('.sub-users');
        	    subUsersMenu.style.display = subUsersMenu.style.display === 'block' ? 'none' : 'block';
        	    
        	    var usersLink = document.querySelector('.users-link');
        	    usersLink.classList.toggle('active');

        	    var arrowUpIcon = usersLink.querySelector('.arrow-up');
        	    var arrowDownIcon = usersLink.querySelector('.arrow-down');
        	    
        	    if (subUsersMenu.style.display === 'block') {
        	        arrowUpIcon.style.display = 'inline-block';
        	        arrowDownIcon.style.display = 'none';
        	    } else {
        	        arrowUpIcon.style.display = 'none';
        	        arrowDownIcon.style.display = 'inline-block';
        	    }
        	}

    // Function to render pie chart
    
    // Data for pie charts (replace with actual data)
    var principalEmployerData = {
        labels: ["Category 1", "Category 2", "Category 3"],
        values: [30, 40, 30],
        backgroundColors: ["#FF6384", "#36A2EB", "#FFCE56"]
    };

    var contractorData = {
        labels: ["Category A", "Category B", "Category C"],
        values: [20, 50, 30],
        backgroundColors: ["#FF6384", "#36A2EB", "#FFCE56"]
    };

    var workOrderData = {
        labels: ["Type X", "Type Y", "Type Z"],
        values: [45, 25, 30],
        backgroundColors: ["#FF6384", "#36A2EB", "#FFCE56"]
    };

    var workmenData = {
        labels: ["Skill 1", "Skill 2", "Skill 3"],
        values: [35, 30, 35],
        backgroundColors: ["#FF6384", "#36A2EB", "#FFCE56"]
    };

   
   /*  document.addEventListener("DOMContentLoaded", function() {
        var clmsMenuItem = document.getElementById('main-menu-item');
        var clmsSubMenu = document.querySelector('.sub-menu');
        clmsSubMenu.addEventListener('click', function(event) {
            event.stopPropagation(); // Prevent the click event from bubbling up to the parent menu item
        });
        clmsMenuItem.addEventListener('click', function(event) {
            toggleSubMenu(); // Toggle the submenu visibility
        });
    });
    // Call the function to render charts
    document.addEventListener("DOMContentLoaded", function() {
        renderCharts();
    }); */
    // Function to render pie charts
    function renderCharts() {
        renderPieChart("principalEmployerChart", principalEmployerData);
        renderPieChart("contractorChart", contractorData);
        renderPieChart("workOrderChart", workOrderData);
        renderPieChart("workmenChart", workmenData);
    }
    function renderPieChart(canvasId, data) {
        var ctx = document.getElementById(canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: data.labels,
                datasets: [{
                    data: data.values,
                    backgroundColor: data.backgroundColors
                }]
            },
            options: {
                responsive: false
            }
        });
    }
    
</script>
  <!-- <script>
        function toggleSidebar() {
            var sidebar = document.querySelector('.sidebar');
            sidebar.style.left = sidebar.style.left === '0px' ? '-200px' : '0px';
        }
    </script> -->
   <script>
   function toggleSidebar() {
	    var sidebar = document.querySelector('.sidebar');
	    var formContent = document.querySelector('.form-content');

	    if (sidebar.classList.contains('closed')) {
	        sidebar.classList.remove('closed');
	        formContent.style.marginLeft = '200px'; // Adjust the margin to accommodate the sidebar
	    } else {
	        sidebar.classList.add('closed');
	        formContent.style.marginLeft = '0'; // Reset the margin when the sidebar is closed
	    }
	}
</script>
</body>
</html>
