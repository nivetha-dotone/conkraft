<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CMSPRINCIPALEMPLOYER List</title>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css"> 
    <script src="resources/js/jquery.min.js"></script>
</head>
<body>
    <h2>CMSPRINCIPALEMPLOYER List</h2>
    <table border="1">
        <thead>
            <tr>
                <th>UNITID</th>
                <th>NAME</th>
                <th>ADDRESS</th>
                <th>MANAGERNAME</th>
                <th>MANAGERADDRS</th>
                <th>BUSINESSTYPE</th>
                <th>MAXWORKMEN</th>
                <th>MAXCNTRWORKMEN</th>
                <th>BOCWAPPLICABILITY</th>
                <th>ISMWAPPLICABILITY</th>
                <th>CODE</th>
                <th>ORGANIZATION</th>
                <th>PFCODE</th>
                <th>LICENSENUMBER</th>
                <th>WCNUMBER</th>
                <th>ESICNUMBER</th>
                <th>PTREGNO</th>
                <th>LWFREGNO</th>
                <th>FACTORYLICENCENUMBER</th>
                <th>ISRCAPPLICABLE</th>
                <th>RCNUMBER</th>
                <th>ATTACHMENTNM</th>
                <th>STATEID</th>
                <th>ISACTIVE</th>
                <th>UPDATEDTM</th>
                <th>UPDATEDBY</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${cmSPRINCIPALEMPLOYERs}" var="cmSPRINCIPALEMPLOYER">
                <tr>
                    <td>${cmSPRINCIPALEMPLOYER.UNITID}</td>
                    <td>${cmSPRINCIPALEMPLOYER.NAME}</td>
                    <td>${cmSPRINCIPALEMPLOYER.ADDRESS}</td>
                    <td>${cmSPRINCIPALEMPLOYER.MANAGERNAME}</td>
                    <td>${cmSPRINCIPALEMPLOYER.MANAGERADDRS}</td>
                    <td>${cmSPRINCIPALEMPLOYER.BUSINESSTYPE}</td>
                    <td>${cmSPRINCIPALEMPLOYER.MAXWORKMEN}</td>
                    <td>${cmSPRINCIPALEMPLOYER.MAXCNTRWORKMEN}</td>
                    <td>${cmSPRINCIPALEMPLOYER.BOCWAPPLICABILITY}</td>
                    <td>${cmSPRINCIPALEMPLOYER.ISMWAPPLICABILITY}</td>
                    <td>${cmSPRINCIPALEMPLOYER.CODE}</td>
                    <td>${cmSPRINCIPALEMPLOYER.ORGANIZATION}</td>
                    <td>${cmSPRINCIPALEMPLOYER.PFCODE}</td>
                    <td>${cmSPRINCIPALEMPLOYER.LICENSENUMBER}</td>
                    <td>${cmSPRINCIPALEMPLOYER.WCNUMBER}</td>
                    <td>${cmSPRINCIPALEMPLOYER.ESICNUMBER}</td>
                    <td>${cmSPRINCIPALEMPLOYER.PTREGNO}</td>
                    <td>${cmSPRINCIPALEMPLOYER.LWFREGNO}</td>
                    <td>${cmSPRINCIPALEMPLOYER.FACTORYLICENCENUMBER}</td>
                    <td>${cmSPRINCIPALEMPLOYER.ISRCAPPLICABLE}</td>
                    <td>${cmSPRINCIPALEMPLOYER.RCNUMBER}</td>
                    <td>${cmSPRINCIPALEMPLOYER.ATTACHMENTNM}</td>
                    <td>${cmSPRINCIPALEMPLOYER.STATEID}</td>
                    <td>${cmSPRINCIPALEMPLOYER.ISACTIVE}</td>
                    <td>${cmSPRINCIPALEMPLOYER.UPDATEDTM}</td>
                    <td>${cmSPRINCIPALEMPLOYER.UPDATEDBY}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
