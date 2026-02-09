<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tabbed Forms Example</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-control-rounded {
            border-radius: 0.25rem;
        }
        .form-group {
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <!-- Entry Pass Type Radio Buttons -->
        <div class="mb-4">
            <label for="entryPassType" class="form-label">Entry Pass Type</label>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="entryPassType" id="quickOnboarding" value="quickOnboarding">
                <label class="form-check-label" for="quickOnboarding">Quick Onboarding</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="entryPassType" id="regular" value="regular">
                <label class="form-check-label" for="regular">Regular</label>
            </div>
        </div>

        <!-- Tab navigation -->
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <a class="nav-link active" id="form1-tab" data-bs-toggle="tab" href="#form1" role="tab" aria-controls="form1" aria-selected="true">Basic</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="form2-tab" data-bs-toggle="tab" href="#form2" role="tab" aria-controls="form2" aria-selected="false">Employment</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="form3-tab" data-bs-toggle="tab" href="#form3" role="tab" aria-controls="form3" aria-selected="false">Other</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="form4-tab" data-bs-toggle="tab" href="#form4" role="tab" aria-controls="form4" aria-selected="false">Wages</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="form5-tab" data-bs-toggle="tab" href="#form5" role="tab" aria-controls="form5" aria-selected="false">Documents</a>
            </li>
        </ul>

        <!-- Tab content -->
        <div class="tab-content" id="myTabContent">
            <!-- Form 1 -->
            <div class="tab-pane fade show active" id="form1" role="tabpanel" aria-labelledby="form1-tab">
                <form id="form1" action="/submitForm1" method="post">
                    <!-- Transaction Id and Aadhaar Number in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="transactionId" class="form-label">Transaction Id</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="transactionId" name="transactionId" required autocomplete="off">
                        </div>
                        <div class="col-md-2">
                            <label for="aadhaarNumber" class="form-label">Aadhaar Number</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="aadhaarNumber" name="aadhaarNumber" required autocomplete="off">
                        </div>
                    </div>

                    <!-- First Name and Last Name in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="firstName" class="form-label">First Name</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="firstName" name="firstName" required autocomplete="off">
                        </div>
                        <div class="col-md-2">
                            <label for="lastName" class="form-label">Last Name</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="lastName" name="lastName" required autocomplete="off">
                        </div>
                    </div>

                    <!-- Date of Birth and Gender in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="dob" class="form-label">Date of Birth</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="dob" name="dob" required autocomplete="off">
                        </div>
                        <div class="col-md-2">
                            <label for="gender" class="form-label">Gender</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="gender" name="gender" required>
                                <option value="">Select Gender</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                            </select>
                        </div>
                    </div>

                    <!-- Father/Husband Name and Mobile Number in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="fatherHusbandName" class="form-label">Father/Husband Name</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="fatherHusbandName" name="fatherHusbandName" required autocomplete="off">
                        </div>
                        <div class="col-md-2">
                            <label for="mobileNumber" class="form-label">Mobile Number</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="mobileNumber" name="mobileNumber" required autocomplete="off">
                        </div>
                    </div>

                    <!-- Contractor Employee Code and Marital Status in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="contractorEmployeeCode" class="form-label">Contractor Employee Code</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="contractorEmployeeCode" name="contractorEmployeeCode" required autocomplete="off">
                        </div>
                        <div class="col-md-2">
                            <label for="maritalStatus" class="form-label">Marital Status</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="maritalStatus" name="maritalStatus" required>
                                <option value="">Select Marital Status</option>
                                <option value="married">Married</option>
                                <option value="unmarried">Unmarried</option>
                            </select>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-sm btn-success" style="float:right;">Submit Form 1</button>
                </form>
            </div>

            <!-- Form 2 -->
            <div class="tab-pane fade" id="form2" role="tabpanel" aria-labelledby="form2-tab">
                <form id="form2" action="/submitForm2" method="post">
                    <!-- Date of Joining and Department in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="dateOfJoining" class="form-label">Date of Joining</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="dateOfJoining" name="dateOfJoining" required autocomplete="off">
                        </div>
                        <div class="col-md-2">
                            <label for="department" class="form-label">Department</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="department" name="department" required>
                                <option value="">Select Department</option>
                                <!-- Add department options here -->
                            </select>
                        </div>
                    </div>

                    <!-- Area and Principal Employer in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="area" class="form-label">Area</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="area" name="area" required>
                                <option value="">Select Area</option>
                                <!-- Add area options here -->
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label for="principalEmployer" class="form-label">Principal Employer</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="principalEmployer" name="principalEmployer" required>
                                <option value="">Select Principal Employer</option>
                                <!-- Add principal employer options here -->
                            </select>
                        </div>
                    </div>

                    <!-- Work Order Code and Trade in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="workOrderCode" class="form-label">Work Order Code</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="workOrderCode" name="workOrderCode" required>
                                <option value="">Select Work Order Code</option>
                                <!-- Add work order code options here -->
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label for="trade" class="form-label">Trade</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="trade" name="trade" required>
                                <option value="">Select Trade</option>
                                <!-- Add trade options here -->
                            </select>
                        </div>
                    </div>

                    <!-- Skill and Specialization in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="skill" class="form-label">Skill</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="skill" name="skill" required>
                                <option value="">Select Skill</option>
                                <!-- Add skill options here -->
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label for="specialization" class="form-label">Specialization</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="specialization" name="specialization" required>
                                <option value="">Select Specialization</option>
                                <!-- Add specialization options here -->
                            </select>
                        </div>
                    </div>

                    <!-- Engineer in Charge and Insurance Type in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="engineerInCharge" class="form-label">Engineer in Charge</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="engineerInCharge" name="engineerInCharge" required>
                                <option value="">Select Engineer in Charge</option>
                                <!-- Add engineer in charge options here -->
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label for="insuranceType" class="form-label">Insurance Type</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="insuranceType" name="insuranceType" required>
                                <option value="">Select Insurance Type</option>
                                <!-- Add insurance type options here -->
                            </select>
                        </div>
                    </div>

                    <!-- WC Policy/ESIC Number and Labor License Number in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="wcEsicNumber" class="form-label">WC Policy/ESIC Number</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="wcEsicNumber" name="wcEsicNumber" required>
                                <option value="">Select WC Policy/ESIC Number</option>
                                <!-- Add WC Policy/ESIC number options here -->
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label for="laborLicenseNumber" class="form-label">Labor License Number</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="laborLicenseNumber" name="laborLicenseNumber" required>
                                <option value="">Select Labor License Number</option>
                                <!-- Add labor license number options here -->
                            </select>
                        </div>
                    </div>

                    <!-- Hazardous Area and Workmen Eligible for PF in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="hazardousArea" class="form-label">Hazardous Area</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="hazardousArea" name="hazardousArea" required>
                                <option value="">Select Hazardous Area</option>
                                <!-- Add hazardous area options here -->
                            </select>
                        </div>
                        <div class="col-md-2">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="pfEligible" name="pfEligible">
                                <label class="form-check-label" for="pfEligible">Workmen Eligible for PF</label>
                            </div>
                        </div>
                    </div>

                    <!-- PF Number, UAN Number, and Access Area in one row -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="pfNumber" class="form-label">PF Number</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="pfNumber" name="pfNumber" autocomplete="off">
                        </div>
                        <div class="col-md-2">
                            <label for="uanNumber" class="form-label">UAN Number</label>
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control form-control-rounded" id="uanNumber" name="uanNumber" autocomplete="off">
                        </div>
                    </div>

                    <!-- Access Area -->
                    <div class="row mb-3">
                        <div class="col-md-2">
                            <label for="accessArea" class="form-label">Access Area</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-select form-control-rounded" id="accessArea" name="accessArea" required>
                                <option value="">Select Access Area</option>
                                <!-- Add access area options here -->
                            </select>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-sm btn-success" style="float:right;">Submit Form 2</button>
                </form>
            </div>

            <!-- Form 3 -->
            <div class="tab-pane fade" id="form3" role="tabpanel" aria-labelledby="form3-tab">
                <!-- Add your Form 3 fields here -->
            </div>

            <!-- Form 4 -->
            <div class="tab-pane fade" id="form4" role="tabpanel" aria-labelledby="form4-tab">
                <!-- Add your Form 4 fields here -->
            </div>

            <!-- Form 5 -->
            <div class="tab-pane fade" id="form5" role="tabpanel" aria-labelledby="form5-tab">
                <!-- Add your Form 5 fields here -->
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
