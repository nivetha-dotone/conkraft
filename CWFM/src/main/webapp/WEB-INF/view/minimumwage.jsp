<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
<style>
    body {
        margin: 0;
        overflow-x: hidden;
    }

    #principalEmployerContent {
        padding: 20px;
        box-sizing: border-box;
        overflow-y: auto;
        height: calc(100vh - 20px);
    }
th label {
    text-align: left;
    display: block;
    font-weight: normal; /* Remove bold effect */
}
     tr {
        margin-bottom: 10px; /* Adjust the value as needed */
    }

    /* Add spacing between <td> elements */
    td {
        padding-bottom: 10px; /* Adjust the value as needed */
    }
    .custom-label {
    font-family: 'Your-Desired-Font', sans-serif; /* Replace 'Your-Desired-Font' with the actual font name */
    text-align: left;
    display: block;
    /* Add any other styling properties you need */
}

.custom-input-container {
    padding-left: 10px;
}

.custom-input {
    height: 40px; /* Adjust the height as needed */
    font-family: 'Your-Desired-Font', sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}

.custom-input-checkbox {
    height: 20px; /* Adjust the height as needed */
    font-family: 'Your-Desired-Font', sans-serif; /* Same font as the label */
    /* Add any other styling properties you need */
}
.required-field {
    color: red; /* Change this to your desired color */
    margin-right: 4px; /* Add space to the right of the * */
}
        .page-header {
            background-color: #005151;
            color: #fff;
            padding: 10px;
            text-align: center;
            font-size: 20px;
        }

        .header-buttons {
            float: right;
          /*   margin-top: -40px; */
            margin-right: 10px;
        }
</style>

   </head>
<body>
 <div class="page-header">
        Principal Employer Page
        <div class="header-buttons">
            <button onclick="saveData()">Save</button>
            <button onclick="refreshPage()">Refresh</button>
        </div>
    </div>
<div id="minWageContent">
	<table class="ControlLayout" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<td >
					<table >
						
						
		<!--    cms  minimumwage-->
						
							
						
						<tr>
							<th><label for="khtmlNameInput"><span></span>
								<fmt:message key="label.from" /></label></th>
							<td><html:text property="value(from)"
									styleId="khtmlNameInput" onchange="setDataChanged();" size="40"
									maxlength="50" disabled="true" /></td>
						</tr>

						<tr>
							<th><label for="khtmlNameInput"><span></span>
								<fmt:message key="label.trade" /></label></th>
							<td><html:text property="value(trade)"
									styleId="khtmlNameInput" onchange="setDataChanged();" size="40"
									maxlength="50" disabled="true" /></td>
						</tr>


						<tr>
							<th><label for="khtmlNameInput"><span></span>
								<fmt:message key="label.skill" /></label></th>
							<td><html:text property="value(skillset)"
									styleId="khtmlNameInput" onchange="setDataChanged();" size="40"
									maxlength="50" disabled="true"/></td>
						</tr>


						<tr>
							<th><label for="khtmlNameInput"><span></span>
								<fmt:message key="label.minimumwage" /></label></th>
							<td><html:text property="value(minimumwage)"
									styleId="khtmlNameInput" onchange="setDataChanged();" size="40"
									maxlength="30" /></td>
						</tr>
						<tr>
							<th><label for="khtmlNameInput"><span></span>
								<fmt:message key="label.daperday" /></label></th>
							<td><html:text property="value(daperday)"
									styleId="khtmlNameInput" onchange="setDataChanged();" size="40"
									maxlength="50" /></td>
						</tr>
						<tr>
							<th><label for="khtmlNameInput"><span></span>
								<fmt:message key="label.otherallowance" /></label></th>
							<td><html:text property="value(otherallowance)"
									styleId="khtmlNameInput" onchange="setDataChanged();" size="40"
									maxlength="50" /></td>
						</tr>
<!-- 						<tr> -->
<!-- 							<th><label for="khtmlNameInput"><span></span> -->
<%-- 								<fmt:message key="label.Total" /></label></th> --%>
<%-- 							<td> <html:text property="value(total)" --%>
<%-- 									styleId="khtmlNameInput" onchange="setDataChanged();" size="40" --%>
<%-- 									maxlength="50" />  --%>
<%-- 									<%= (Integer.parseInt(request.getParameter("minimumwage"))+Integer.parseInt(request.getParameter("daperday"))+Integer.parseInt("otherallowance")) %></td>
<%-- 						 --%></tr> 


						
						

					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="ControlLayout" cellspacing="0" cellpadding="0">
	    <thead>
        
		</thead>
		<tbody>
		
			<tr>
				
			</tr>
	
		</tbody>
	</table>
</div>
