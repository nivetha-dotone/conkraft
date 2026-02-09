<!-- Include necessary libraries and stylesheets -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!-- Your existing script for creating/updating pie charts -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        var createOrUpdateChart = function (chartId, chartData) {
            var ctx = document.getElementById(chartId).getContext("2d");

            new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ["Category 1", "Category 2", "Category 3"],
                    datasets: [{
                        data: chartData,
                        backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56"],
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                }
            });
        };

        // Add calls to createOrUpdateChart for each chart
        createOrUpdateChart('principalEmployerChart', [30, 40, 30]);
        createOrUpdateChart('contractorChart', [40, 30, 30]);
        createOrUpdateChart('workorderChart', [20, 50, 30]);
        createOrUpdateChart('contractWorkmenChart', [10, 60, 30]);
    });
</script>
<style>
    #principalEmployerContent {
        margin-left: 260px;
        padding: 20px;
        box-sizing: border-box;
        overflow-y: auto;
        height: calc(100vh - 20px);
    }

    .dashboard-section {
        display: flex;
        flex-wrap: wrap;
    }

   
    .dashboard-chart {
        width: 50%;
        height: 200px; /* Adjusted height, make it smaller as needed */
        padding: 10px;
        box-sizing: border-box;
    }
</style>
</head>
<body>
<!-- Your existing HTML structure for displaying the charts -->
  
<div id="dashboardContainer" class="dashboard-section">
<table class="ControlLayout" cellspacing="0" cellpadding="0">
        <tbody>
    <div class="dashboard-chart">
        <h3>Principal Employer</h3>
        <canvas id="principalEmployerChart" width="400" height="200"> </canvas>
    </div>

    <div class="dashboard-chart">
        <h3>Contractor</h3>
        <canvas id="contractorChart" width="400" height="200"></canvas>
    </div>

    <div class="dashboard-chart">
        <h3>Workorder</h3>
        <canvas id="workorderChart" width="400" height="200"></canvas>
    </div>

    <div class="dashboard-chart">
        <h3>Contract Workmen</h3>
        <canvas id="contractWorkmenChart" width="400" height="200"></canvas>
    </div>
    </tbody>
    </table>
</div>
