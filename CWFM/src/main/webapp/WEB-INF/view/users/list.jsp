<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
 <title>Users List</title>
  <script src="resources/js/cms/users.js"></script>
 
  <!--   <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Role Rights List</title>
    <script src="resources/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/cms/principalEmployer.js"></script>
    <script src="resources/js/commonjs.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/cmsstyles.css">  -->
  <style>
 
 
    body {
        background-color: #FFFFFF; /* White background for the page */
         font-family: 'Noto Sans', sans-serif;
    }

    .action-bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 1rem;
        background-color: #f8f8f8;
        margin-bottom: 1rem;
    }

    .action-buttons {
        display: flex;
        gap: 10px;
    }

    .action-buttons button {
        padding: 0.5rem 1rem;
        font-size: 1rem;
        cursor: pointer;
    }

    #searchForm {
        display: flex;
        align-items: center;
        flex-grow: 1;
        margin-right: 10px;
    }

    .search-box {
        width: 200px; /* Adjust width to fit layout */
        padding: 0.25rem; /* Reduced padding for height */
        font-size: 0.875rem; /* Smaller font size */
        border: 1px solid #ccc; /* Border to match design */
        border-radius: 4px; /* Slightly rounded corners */
        outline: none; /* Remove default outline */
        margin-right: 10px; /* Space between input and button */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }

    .table-container {
        overflow-x: auto;
        margin: 0; /* Remove space before the table */
        padding: 0; /* Remove padding if any */
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    td {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
         font-family: 'Noto Sans', sans-serif;
         
    color: #898989;/* Label text color */
  padding: .2em .6em .3em;
  font-size: 85%;
  font-weight: 700;
  line-height: 1;
    white-space: nowrap;
  vertical-align: baseline;
  border-radius: .25em;
    }
     th {
        padding: 10px;
        text-align: left;
        border: 1px solid #ddd;
        font-size: 0.875rem; /* Smaller text size matching the side nav bar */
          font-weight: bold;
    }

    th {
        background-color: #DDF3FF; /* Light green for the table header */
        color: #005151; /* Text color from side nav bar */
        cursor: pointer;
        font-family: 'Volte Rounded', 'Noto Sans', sans-serif;
        font-size: 0.75rem; /* Decreased font size for table header */
        line-height: 1.2rem; /* Adjust line-height for better fit */
        padding: 6px; /* Reduced padding for table header */
    }

    .page-header {
        display: flex;
        align-items: center;
        justify-content: flex-end;  /* Distribute space between search and buttons */
        padding: 8px; /* Adjust padding */
        background-color: #FFFFFF; /* White background */
        border-bottom: 1px solid #ccc; /* Subtle border for separation */
         gap: 10px;
    }

    .page-header > div {
        display: flex;
        gap: 10px; /* Space between buttons */
    }

    @media (max-width: 768px) {
        .page-header {
            flex-direction: column; /* Stack items vertically on small screens */
            align-items: flex-start; /* Align items to the start */
        }

        #searchForm {
            width: 100%; /* Full width for small screens */
            margin-right: 0; /* Remove margin on small screens */
        }

        .search-box {
            width: 100%; /* Full width for small screens */
        }

        .page-header > div {
            width: 100%; /* Full width for small screens */
            margin-top: 10px; /* Add space above buttons */
            flex-direction: column; /* Stack buttons vertically */
        }
    }
    .header-text-new {
        font-family: 'Noto Sans', Arial, sans-serif; /* Font family similar to grid header */
        font-size: 14px; /* Adjusted font size to match typical grid header size */
        font-weight: 600; /* Bold text for prominence */
        border: 1px solid #ddd; /* Lighter border for a cleaner look */
        white-space: nowrap; /* Prevent text from wrapping */
        padding: 8px 10px; /* Adjusted padding for better spacing */
          background-color: #E0E0E0;  /* Light background color to match grid header */
        color: #333; /* Text color for readability */
          font-weight: bold;
    }
       table th {
        border-top: 0.0625rem solid var(--zed_sys_color_border_lowEmphasis); /* Top border color */
        border-bottom: 1px solid var(--zed_sys_color_border_lowEmphasis); /* Bottom border color */
        border-right: none; /* No right border */
        background-color: #DDF3FF; /* Light green background color */
        color: var(--zed_sys_color_tableHeader_text); /* Text color */
        font-size: 0.75rem; /* Smaller font size */
        line-height: 1.2rem; /* Reduced line height */
        letter-spacing: normal; /* Letter spacing */
        font-family: 'Noto Sans', sans-serif; /* Font family */
         font-weight: bold;
        text-align: center; /* Center align text */
        padding: 4px; /* Reduced padding for the table header */
        box-sizing: border-box; /* Include padding and border in element's total width and height */
    }
   
</style>
<script>
function searchUserWithUserAccount(){
	 var userAccount = $('#userAccount').val();
console.log(userAccount);
	 $.ajax({
	     url: '/CWFM/usersController/getUserWithUserAccount',
	     type: 'POST',
	     data: {
	    	 userAccount: userAccount
	     },
	     success: function(response) {
	         var tableBody = $('#UserTable tbody');
	         tableBody.empty();
				if (response.length > 0) {
	             $.each(response, function(index, wo) {
	                 var row = '<tr  >' +
		'<td  ><input type="checkbox" name="selectedUserIds" value="' + wo.userId + '"></td>'+
	                           '<td  >' + wo.userAccount + '</td>' +
	                           '<td  >' + wo.emailId + '</td>' +
		  '<td  >'+ wo.firstName + +wo.lastName  '</td>' +	
		  '<td  >' + wo.contactNumber + '</td>' +	
		  '<td  >' + wo.status + '</td>' +	
	                           '</tr>';
	                 tableBody.append(row);
	             });
	         } else {
	             tableBody.append('<tr><td colspan="3">No resources found</td></tr>');
	         }
	     },
	     error: function(xhr, status, error) {
	         console.error("Error fetching data:", error);
	     }
	 });
	}	
</script>
</head>
<body>
<div class="page-header">
   <%--  <form id="searchForm">
        <input type="text" class="search-box ng-pristine ng-untouched ng-valid ng-empty" id="userAccount" name="searchQuery"  autocomplete="off" placeholder="Search...">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="searchUserWithUserAccount()">Search</button>
    </form>
    <div> --%>
    <c:if test="${UserPermission.addRights eq 1 }">
         <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToUserAdd()">Add</button> 
    </c:if>
    <c:if test="${UserPermission.editRights eq 1 }">
         <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToUsersEdit()">Edit</button> 
     </c:if>
     <c:if test="${UserPermission.viewRights eq 1 }">
        <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToUsersView()">View</button>

     </c:if>
       <c:if test="${UserPermission.exportRights eq 1 }">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="usersExportToCSV()">Export</button>
    	</c:if>
    	<c:if test="${UserPermission.deleteRights eq 1 }">
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteSelectedUsers()">Deactive</button>
    	</c:if>
    </div>
    <!-- <div>
    <button type="submit" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToUserAdd()">Add</button>
    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="deleteSelectedUsers()">Delete</button>
     <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToUsersEdit()">Edit</button>
    <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="usersExportToCSV()">Export</button>
        <button type="button" class="btn btn-default process-footer-button-cancel ng-binding" onclick="redirectToUsersView()">View</button>
        
</div> -->
</div>
<form  >
    <div class="table-container">
        <table id="UserTable" >
        <thead>
             <tr>
                <!-- <th>User ID</th> -->
                <th class="checkbox-cell">
                    <input type="checkbox" id="selectAllUsers" onchange="toggleSelectAllUsers()">
                </th>
                <th id="userAccount">User Account</th>
                <th>Email</th>
                <th>Full Name</th>
                <th>Contact Number</th>
                <th>Status</th>
                <!-- <th>Actions</th> -->
            </tr>
        </thead>
        <tbody id="UserTable">
            <c:forEach var="user" items="${users}">
                <tr>
                 <%--    <td>${user.userId}</td> --%>
                  <td class="checkbox-cell">
                        <input type="checkbox" name="selectedUserIds" value="${user.userId}">
                    </td>
                    <td id="userAccount" style="text-transform: capitalize;">${user.userAccount}</td>
                    <td>${user.emailId}</td>
                    <td style="text-transform: capitalize;">${user.firstName} ${user.lastName}</td>
                    <td>${user.contactNumber}</td>
                    <td>${user.status}</td>
                    <%-- <td>
                        <a href="editUser?userId=${user.userId}">Edit</a> |
                        <a href="deleteUser?userId=${user.userId}" onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
                    </td> --%>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </div>
</form>
</body>
</html>
