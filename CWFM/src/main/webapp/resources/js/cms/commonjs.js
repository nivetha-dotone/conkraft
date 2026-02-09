function redirectToPEAdd() {
    // Fetch the content of add.jsp using AJAX
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Update the mainContent element with the fetched content
            document.getElementById("mainContent").innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/CWFM/principalEmployer/add", true);
    xhr.send();
}


function checkSessionStatus() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if (!response.sessionActive) {
                // Session has expired, redirect to login page
                window.location.href = "/CWFM";
            }
        }
    };
    xhr.open("GET", "/checkSessionStatus", true);
    xhr.send();
}
function showTab(tabId) {
    var i, tabcontent, tabbuttons;

    // Hide all tab content
    tabcontent = document.getElementsByClassName("tab-content");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Remove active class from all tab buttons
    tabbuttons = document.getElementsByClassName("tab-button");
    for (i = 0; i < tabbuttons.length; i++) {
        tabbuttons[i].classList.remove("active");
    }

    // Show the clicked tab's content and add active class to the clicked tab
    document.getElementById(tabId).style.display = "block";
    document.querySelector(`[data-tab="${tabId}"]`).classList.add("active");
}

 function toggleSubMenu(menuId) {
        const submenu = document.getElementById(menuId);
        const arrowUpIcon = submenu.previousElementSibling.querySelector('.arrow-up');
        const arrowDownIcon = submenu.previousElementSibling.querySelector('.arrow-down');

        if (submenu.style.display === 'block') {
            submenu.style.display = 'none';
            arrowUpIcon.style.display = 'none';
            arrowDownIcon.style.display = 'inline-block';
        } else {
            submenu.style.display = 'block';
            arrowUpIcon.style.display = 'inline-block';
            arrowDownIcon.style.display = 'none';
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        let submenuVisible = false;
        let submenuSelected = false;

        function toggleSubMenu() {
            const submenu = document.querySelector('#clms-sub-menu');
            const arrowUpIcon = document.querySelector('.arrow-up');
            const arrowDownIcon = document.querySelector('.arrow-down');

            submenuVisible = !submenuVisible;
            submenu.style.display = submenuVisible ? 'block' : 'none';
            arrowUpIcon.style.display = submenuVisible ? 'inline-block' : 'none';
            arrowDownIcon.style.display = submenuVisible ? 'none' : 'inline-block';
        }

        const clmsMenuLink = document.querySelector('.mainmenu-link');
        if (clmsMenuLink) {
            clmsMenuLink.addEventListener('click', (event) => {
                event.preventDefault(); // Prevent default link behavior
                toggleSubMenu();
            });
        }

        // Ensure submenu closes when the sidebar is hovered out
        const mainMenu = document.querySelector('.main-menu');
        mainMenu.addEventListener('mouseenter', function() {
            this.classList.add('expanded');
        });

        mainMenu.addEventListener('mouseleave', function() {
            if (!submenuSelected) {
                this.classList.remove('expanded');
            }
        });

        // Handle submenu selection
        document.querySelectorAll('#clms-sub-menu a').forEach(link => {
            link.addEventListener('click', function() {
                submenuSelected = true; // Mark submenu as selected
                highlightMenuItem(this.parentElement);
                loadContent(this.textContent);
                setTimeout(() => submenuSelected = false, 300); // Reset after a short delay
            });
        });

        function highlightMenuItem(menuItem) {
            const menuItems = document.querySelectorAll('#clms-sub-menu li');
            menuItems.forEach(item => item.classList.remove('active'));
            menuItem.classList.add('active');
        }

       /* function loadContent(text) {
            const headingText = text;
            updateHeading(headingText);
            document.getElementById("mainContent").innerHTML = `<h1>${text}</h1><p>Content for ${text} will go here.</p>`;
            resetSessionTimer();
        }*/

        function updateHeading(text) {
            const headingElement = document.querySelector('.top-nav .heading');
            headingElement.textContent = text;
        }

        function resetSessionTimer() {
            console.log('Session timer reset');
        }
    });


    function loadContent(link) {
        const headingText = link.textContent;
        updateHeading(headingText);
        const urlMatch = link.getAttribute('onclick').match(/'(.*?)'/);
        if (urlMatch) {
            const url = urlMatch[1]; // Extract URL from onclick
            console.log("Constructed URL:", url);
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("mainContent").innerHTML = this.responseText;
                    resetSessionTimer();
                }
            };
            xhttp.open("GET", url, true);
            xhttp.send();
        } else {
            console.error("Failed to extract URL from onclick attribute");
        }
    }
   

    function updateHeading(text) {
        const headingElement = document.querySelector('.top-nav .heading');
        headingElement.textContent = text;
    }

   
    function resetSidebar() {
        // Hide all sub-menus
        const subMenus = document.querySelectorAll('.sub-menu');
        subMenus.forEach(menu => {
            menu.style.display = 'none';
        });

        // Reset all menu item selections (optional, depends on how selections are handled)
        const menuItems = document.querySelectorAll('.main-menu-item');
        menuItems.forEach(item => {
            item.classList.remove('selected'); // Adjust according to your CSS class for selected items
        });

        // Show the default menu or reset to initial state
        // You might want to set a specific state here, or just leave it empty
    }
    
   

    function loadChangePassword() {
        console.log("Change Password clicked");
        // Add logic to load change password view
    }

    function loadLogout() {
        console.log("Logout clicked");
        // Add logic to handle logout
    }

    function resetSessionTimer() {
        // Placeholder for session timer reset logic
    }