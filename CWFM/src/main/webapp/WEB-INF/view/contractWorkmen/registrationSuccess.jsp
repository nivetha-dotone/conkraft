<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Registration Successful | Conkraft</title>

    <style>
        body {
            margin: 0;
            font-family: 'Noto Sans', Arial, sans-serif;
            background: #f4f8f8;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .success-card {
            background: #fff;
            border-radius: 14px;
            padding: 40px 30px;
            max-width: 480px;
            width: 90%;
            text-align: center;
            box-shadow: 0 8px 30px rgba(0,0,0,0.08);
        }

        .success-icon {
            width: 76px;
            height: 76px;
            border-radius: 50%;
            background: #e0f2eb;
            color: #13845b;
            font-size: 46px;
            line-height: 76px;
            margin: 0 auto 22px;
        }

        h2 {
            color: #005151;
            margin-bottom: 12px;
        }

        p {
            color: #666;
            font-size: 14px;
            line-height: 1.7;
        }

        .reference {
            background: #f1f7f7;
            border: 1px solid #d5e5e5;
            border-radius: 8px;
            padding: 16px;
            margin: 24px 0;
        }

        .reference strong {
            color: #005151;
            font-size: 22px;
        }

        .notice {
            font-size: 13px;
            color: #777;
        }
    </style>
</head>

<body>

<div class="success-card">

    <div class="success-icon">&#10003;</div>

    <h2>Registration Submitted Successfully!</h2>

    <p>
        Thank you for completing your workman registration.
        Your details and documents have been submitted successfully.
    </p>

    <div class="reference">
        <p>Registration Reference Number</p>
        <strong><c:out value="${registrationId}"/></strong>
    </div>

    <p>
        Your registration is currently under review by the
        Contractor Supervisor.
    </p>

    <p class="notice">
        Please save your reference number for future communication.
        You may now close this page.
    </p>

</div>

</body>
</html>