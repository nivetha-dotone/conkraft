<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
     <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
          <%-- <%@include file="component/navbar.jsp" %> --%>
   <style>
    .mainmenu-link .menu-icon {
        width: 20px;
        height: 20px;
        margin-right: 5px;
    }
     .mainmenu-link .menu2-icon {
        width: 15px;
        height: 10px;
        margin-left: 15px;
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
    }

    /* .mainmenu-link.active .menu-icon {
        background-image: url('resources/img/larrow.png'); /* Icon when submenu is open */
    } */

    .mainmenu-link.active .menu-icon.arrow-down {
        display: none; /* Hide arrow-down icon when submenu is active */
    }

    .mainmenu-link.active .menu-icon.arrow-up {
        display: inline-block; /* Display arrow-up icon when submenu is active */
    }
    .mainmenu-link .arrow-up {
    display: none;
}

.mainmenu-link .arrow-down {
    display: inline-block;
}
.reports-link .arrow-up {
    display: none;
}

.reports-link .arrow-down {
    display: inline-block;
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
</style>

</head>
<body>
    <!-- Sidebar -->
    <div class="header">
        <h1>CONTRACT WORKFORCE MANAGEMENT SYSTEM</h1>
    </div>
    <div class="sidenav" id="sidebar">
        <a href="#" class="toggle-btn" onclick="toggleSidebar()">
            <img src="resources/img/3lines.png" alt="Menu" class="toggle-img">
            <span class="toggle-text">Menu</span>
        </a> <!-- Toggle button -->
        <ul>
            <li id="dashboard-item" style="margin-top: 20px;">
                <a href="#" onclick="showDashboard()" class="dashboard-link">
                    <img src="resources/img/graph.png" alt="Dashboard" class="menu-icon">
                    <span class="menu-text">Dashboard</span>
                </a>
            </li>
           
            
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
        <img src="resources/img/submenu.png" alt="Before CLMS" class="menu-icon">
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
                    <img src="resources/img/compliance.png" alt="Reports" class="menu-icon">
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
                    <img src="resources/img/report.png" alt="Reports" class="menu-icon">
                    <span class="menu-text">Reports</span>
                    <img src="resources/img/uarrow.png" alt="After reports" class="report2-icon arrow-up">
        <img src="resources/img/darrow.png" alt="After reports" class="report2-icon arrow-down">
                </a>
           <!--  <ul class="sub-report">
        <li><a href="#" >Wage Cost Report</a></li>
        <li><a href="#" >Muster Role Report</a></li>
    </ul> -->
            </li>
        </ul>
    </div>

    <!-- Main content -->
    <!-- <div class="form-content" id="mainContent">
        Initial content will be loaded here
    </div> -->
<div class="form-content" id="mainContent">
        <!-- Pie charts for principal employer, contractor, work order, and workmen -->
        <h2>Dashboard</h2>
        <div style="display: flex; justify-content: space-around;">
            <canvas id="principalEmployerChart" width="200" height="200"></canvas>
            <canvas id="contractorChart" width="200" height="200"></canvas>
            <canvas id="workOrderChart" width="200" height="200"></canvas>
            <canvas id="workmenChart" width="200" height="200"></canvas>
        </div>
    </div>
    <!-- JavaScript to handle sidebar toggle and content loading -->
    <script>
    
    var isSidebarExpanded = true;

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
    }

    function adjustMainContentWidth() {
        var mainContent = document.getElementById('mainContent');
        if (!isSidebarExpanded) {
            mainContent.style.width = '95%';
        } else {
            mainContent.style.width = 'calc(100% - 20%)'; // Adjusted for sidebar width
        }
    }

    // Call adjustMainContentWidth when the window is resized
    window.addEventListener('resize', adjustMainContentWidth);

    // Initially adjust mainContent width based on sidebar state
    adjustMainContentWidth();
    
        function loadContent(jspFile) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("mainContent").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", jspFile, true);
            xhttp.send();
        }
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
    </script>
   <script>
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

   

    // Call the function to render charts
    document.addEventListener("DOMContentLoaded", function() {
        renderCharts();
    });
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


</body>
</html>
