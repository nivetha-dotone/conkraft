<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <script src="https://cdn.jsdelivr.net/gh/surepassio/surepass-digiboost-web-sdk@latest/index.min.js"></script>
    <title>DigiBoost Verification</title>
    <style>
        p {
            margin: 0
        }
        body {
            min-height: 100vh;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(to bottom right, #ebf4ff, #c3dafe);
            font-family: sans-serif;
        }

        .container {
            width: 100%;
            max-width: 400px;
            padding: 16px;
            box-sizing: border-box;
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        .icon-circle {
            width: 64px;
            height: 64px;
            background-color: #dbeafe; /* blue-100 */
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 1rem;
        }

        .svg-icon {
            width: 32px;
            height: 32px;
            color: #2563eb; /* blue-600 */
        }

        .title {
            text-align: center;
            font-size: 1.25rem;
            font-weight: 600;
            color: #1f2937; /* gray-900 */
        }

        .description {
            text-align: center;
            color: #4b5563; /* gray-600 */
            font-size: 0.875rem;
            margin-top: 0.5rem;
        }

        .info-list {
            margin-top: 1.5rem;
            margin-bottom: 1.5rem;
        }

        .info-item {
            display: flex;
            align-items: flex-start;
            gap: 12px;
            margin-bottom: 1rem;
        }

        .info-item svg {
            width: 20px;
            height: 20px;
            flex-shrink: 0;
        }

        .info-text {
            font-size: 0.875rem;
            font-weight: 500;
            color: #1f2937;
        }

        .info-subtext {
            font-size: 0.75rem;
            color: #4b5563;
        }

        .highlight-box {
            background-color: #eff6ff;
            border-radius: 0.5rem;
            padding: 1rem;
            text-align: center;
            margin-bottom: 1rem;
        }

        .highlight-box p:first-child {
            color: #1e40af;
            font-weight: 500;
            margin-bottom: 0.25rem;
        }

        .highlight-box p:last-child {
            font-size: 0.75rem;
            color: #2563eb;
        }

        .disclaimer {
            font-size: 0.75rem;
            color: #6b7280;
            text-align: center;
            line-height: 1.4;
            margin-top: 1rem;
        }

        @keyframes pulse {
            0%, 100% {
                transform: scale(1);
                opacity: 1;
            }
            50% {
                transform: scale(1.05);
                opacity: 0.9;
            }
        }
    </style>
</head>
<body>
<%
String token = (String) request.getAttribute("token");

%>

<div class="container">
    <div id="initial-screen">
        <div class="icon-circle">
            <!-- Shield SVG -->
            <svg class="svg-icon" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"
                 xmlns="http://www.w3.org/2000/svg">
                <path d="M12 3l7 4v5c0 5.25-3.75 10-7 11-3.25-1-7-5.75-7-11V7l7-4z" stroke-linecap="round"
                      stroke-linejoin="round"/>
            </svg>
        </div>
        <div class="title">Document Verification</div>
        <div class="description">Securely access your documents through DigiLocker</div>

        <div class="info-list">
            <div class="info-item">
                <!-- FileText SVG -->
                <svg fill="none" stroke="#2563eb" stroke-width="2" viewBox="0 0 24 24"
                     xmlns="http://www.w3.org/2000/svg">
                    <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z" stroke-linecap="round"
                          stroke-linejoin="round"/>
                    <path d="M14 2v6h6" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <div>
                    <p class="info-text">Instant Access</p>
                    <p class="info-subtext">Get immediate access to your verified documents</p>
                </div>
            </div>
            <div class="info-item">
                <!-- Shield SVG again -->
                <svg fill="none" stroke="#16a34a" stroke-width="2" viewBox="0 0 24 24"
                     xmlns="http://www.w3.org/2000/svg">
                    <path d="M12 3l7 4v5c0 5.25-3.75 10-7 11-3.25-1-7-5.75-7-11V7l7-4z" stroke-linecap="round"
                          stroke-linejoin="round"/>
                </svg>
                <div>
                    <p class="info-text">Secure & Trusted</p>
                    <p class="info-subtext">Government-backed digital document platform</p>
                </div>
            </div>
        </div>

        <div class="highlight-box">
            <p>Ready to proceed?</p>
            <p>Click the button below to securely connect with DigiLocker and access your documents</p>
        </div>

   <div id="status"></div>
        <div id="digilocker-sdk-button"></div>

        <p class="disclaimer">
            By clicking above, you will be redirected to the official DigiLocker platform to authenticate and authorize
            document access
        </p>
    </div>
</div>

<script>
	function handleSuccess(data) {
		//Do something here for success case
		console.log("Verification successful:", data);
        document.getElementById("status").innerHTML = 
            '<div class="success"> Verification completed successfully!</div>';
            document.getElementById("digilocker-sdk-button").style.display = "none";
        
        window.location.href = "/CWFM/contractworkmen/digiClientId?id=" + encodeURIComponent(data.client_id);	
		
	}

	function handleFailure() {
		//Do something here for failure case
		console.log("Verification failed:", error);
        document.getElementById("status").innerHTML = 
            '<div class="error"> Verification failed or was cancelled</div>';
	}

	window.DigiboostSdk({
		gateway: "production", //sandbox or production
		token: "<%= token %>",// init token from digilocker initialize
		onSuccess: handleSuccess,
		onFailure: handleFailure,
		selector: "#digilocker-sdk-button",
		style: {
			width: "100%",
			margin: "0px"
		}
	});
</script>
</body>
</html>