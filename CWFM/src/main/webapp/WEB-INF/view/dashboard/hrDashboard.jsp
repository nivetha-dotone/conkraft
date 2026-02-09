 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title></head>
<body>

<div class="dashboard-container">

    <div class="card border-onboarding">
        <div class="card-left">
            <div class="icon-circle icon-onboarding">
               👤
            </div>
            <div class="card-info">
                <div class="title">Onboarding</div>
                <div class="desc">New hires awaiting approval</div>
            </div>
        </div>
        <div class="card-right">
            <div class="count">10</div>
            <button class="view-btn">View All</button>
        </div>
    </div>

    <div class="card border-renewals">
        <div class="card-left">
            <div class="icon-circle icon-renewals">
              📄
            </div>
            <div class="card-info">
                <div class="title">Renewals</div>
                <div class="desc">Contracts near expiration</div>
            </div>
        </div>
        <div class="card-right">
            <div class="count">11</div>
            <button class="view-btn">View All</button>
        </div>
    </div>

    <div class="card border-verifications">
        <div class="card-left">
            <div class="icon-circle icon-verifications">
                📋
            </div>
            <div class="card-info">
                <div class="title">Bill Verifications</div>
                <div class="desc">Invoices need review</div>
            </div>
        </div>
        <div class="card-right">
            <div class="count">22</div>
            <button class="view-btn">View All</button>
        </div>
    </div>

    <div class="card border-esmes">
        <div class="card-left">
            <div class="icon-circle icon-esmes">🧾
            </div>
            <div class="card-info">
                <div class="title">Bill Esmes</div>
                <div class="desc">New hire review approval</div>
            </div>
        </div>
        <div class="card-right">
            <div class="count">32</div>
            <button class="view-btn">View All</button>
        </div>
    </div>

</div>
</body>
</html>