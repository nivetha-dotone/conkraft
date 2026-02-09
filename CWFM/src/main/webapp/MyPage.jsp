<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
</head>
<body>
    <!-- Sidebar -->
    <div class="sidenav" id="sidebar">
        <a href="#" class="toggle-btn" onclick="toggleSidebar()">&#9776;</a>
        <a href="#" onclick="showDashboard()">Dashboard</a>
        <a href="#" onclick="showMainMenu()">Main Menu</a>
        <a href="#" onclick="showReports()">Reports</a>
    </div>

    <!-- Main content -->
    <div class="form-content" id="mainContent">
        <!-- Initial content will be loaded here -->
    </div>

    <!-- JavaScript to handle sidebar toggle and content loading -->
    <script>
    function toggleSidebar() {
        var sidebar = document.getElementById('sidebar');
        var mainContent = document.getElementById('mainContent');
        var toggleButton = document.querySelector('.toggle-btn'); // Select the toggle button

        if (sidebar.style.width === '20%') {
            sidebar.style.width = '0';
            mainContent.style.marginLeft = '0';
            toggleButton.style.display = 'block'; // Ensure toggle button is displayed when sidebar is collapsed
        } else {
            sidebar.style.width = '20%';
            mainContent.style.marginLeft = '20%';
            toggleButton.style.display = 'none'; // Hide toggle button when sidebar is expanded
        }
    }

        function showDashboard() {
            var mainContent = document.getElementById('mainContent');
            mainContent.innerHTML = '<h2>Dashboard</h2><p>Charts will be displayed here</p>';
        }

        function showMainMenu() {
            var mainContent = document.getElementById('mainContent');
            mainContent.innerHTML = '<h2>Main Menu</h2><ul><li><a href="#" onclick="loadSubMenu(\'Principal Employer\')">Principal Employer</a></li><li><a href="#" onclick="loadSubMenu(\'Contractor\')">Contractor</a></li><li><a href="#" onclick="loadSubMenu(\'Workorder\')">Workorder</a></li><li><a href="#" onclick="loadSubMenu(\'Workmen\')">Workmen</a></li></ul>';
        }

        function showReports() {
            var mainContent = document.getElementById('mainContent');
            mainContent.innerHTML = '<h2>Reports</h2><ul><li><a href="#" onclick="loadSubMenu(\'Muster Roll\')">Muster Roll</a></li><li><a href="#" onclick="loadSubMenu(\'Daily Progress Reports\')">Daily Progress Reports</a></li></ul>';
        }

      /*   function loadSubMenu(subMenu) {
            var mainContent = document.getElementById('mainContent');
            mainContent.innerHTML = '<h2>' + subMenu + '</h2><p>Data for ' + subMenu + ' will be displayed here</p>';
        } */
        function loadSubMenu(subMenu) {
            var mainContent = document.getElementById('mainContent');
            mainContent.innerHTML = '<h2>' + subMenu + '</h2>' + 
                                     '<ul>' +
                                     '<li><a href="#" onclick="loadPath(\'Path 1\')">Path 1</a></li>' +
                                     '<li><a href="#" onclick="loadPath(\'Path 2\')">Path 2</a></li>' +
                                     '<li><a href="#" onclick="loadPath(\'Path 3\')">Path 3</a></li>' +
                                     '</ul>';
        }

        function loadPath(path) {
            var mainContent = document.getElementById('mainContent');
            mainContent.innerHTML = '<h2>' + path + '</h2><p>Content for ' + path + ' will be displayed here</p>';
        }
    </script>
</body>
</html>
