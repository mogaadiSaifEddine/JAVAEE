<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Management</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Restaurant Management</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/commandes/list">Orders</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h4>Order Management
                            <a href="${pageContext.request.contextPath}/commandes/new" class="btn btn-primary float-right">Create New Order</a>
                        </h4>
                    </div>
                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <input type="text" id="searchInput" class="form-control" placeholder="Search orders...">
                            </div>
                            <div class="col-md-6">
                                <div class="form-row">
                                    <div class="col">
                                        <input type="date" id="dateFilter" class="form-control" value="${java.time.LocalDate.now()}">
                                    </div>
                                    <div class="col">
                                        <button id="filterBtn" class="btn btn-secondary">Filter by Date</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <table class="table table-striped table-bordered">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Order ID</th>
                                    <th>Client</th>
                                    <th>Date</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="commande" items="${commandeList}">
                                    <tr>
                                        <td>#${commande.idCmd}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/clients/view?id=${commande.client.idClient}">
                                                ${commande.client.FNameClient} ${commande.client.LNameClient}
                                            </a>
                                        </td>
                                        <td><fmt:formatDate value="${commande.dateCmd}" pattern="yyyy-MM-dd" /></td>
                                        <td>
                                            <!-- For demonstration purposes, let's assume a status based on date -->
                                            <c:set var="today" value="<%= new java.util.Date() %>" />
                                            <c:choose>
                                                <c:when test="${commande.dateCmd.time gt today.time}">
                                                    <span class="badge badge-warning">Pending</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-success">Completed</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/commandes/view?id=${commande.idCmd}" class="btn btn-info btn-sm">
                                                <i class="fas fa-eye"></i> View
                                            </a>
                                            <a href="${pageContext.request.contextPath}/commandes/edit?id=${commande.idCmd}" class="btn btn-primary btn-sm">
                                                <i class="fas fa-edit"></i> Edit
                                            </a>
                                            <a href="${pageContext.request.contextPath}/commandes/delete?id=${commande.idCmd}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this order?')">
                                                <i class="fas fa-trash"></i> Delete
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <c:if test="${empty commandeList}">
                            <div class="alert alert-info">
                                No orders found. Create your first order!
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <!-- Orders Summary -->
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h5>Daily Summary</h5>
                    </div>
                    <div class="card-body">
                        <canvas id="dailyChart" width="400" height="300"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h5>Recent Activity</h5>
                    </div>
                    <div class="card-body">
                        <ul class="list-group">
                            <c:forEach var="commande" items="${commandeList}" begin="0" end="4">
                                <li class="list-group-item">
                                    <div class="d-flex w-100 justify-content-between">
                                        <h6 class="mb-1">Order #${commande.idCmd}</h6>
                                        <small><fmt:formatDate value="${commande.dateCmd}" pattern="yyyy-MM-dd" /></small>
                                    </div>
                                    <p class="mb-1">Client: ${commande.client.FNameClient} ${commande.client.LNameClient}</p>
                                </li>
                            </c:forEach>
                            <c:if test="${empty commandeList}">
                                <li class="list-group-item">No recent activity</li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer class="py-5 bg-dark mt-5">
        <div class="container">
            <p class="m-0 text-center text-white">Copyright &copy; Restaurant Management System 2025</p>
        </div>
    </footer>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"></script>
    <script>
        // Search functionality
        $(document).ready(function() {
            $("#searchInput").on("keyup", function() {
                var value = $(this).val().toLowerCase();
                $("table tbody tr").filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });

            // Date filter functionality
            $("#filterBtn").click(function() {
                var selectedDate = $("#dateFilter").val();
                window.location.href = "${pageContext.request.contextPath}/commandes/list?date=" + selectedDate;
            });

            // Chart.js - Simple demo chart
            var ctx = document.getElementById('dailyChart').getContext('2d');
            var dailyChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
                    datasets: [{
                        label: 'Orders this Week',
                        data: [5, 8, 6, 9, 12, 15, 10],
                        backgroundColor: [
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(255, 99, 132, 0.2)'
                        ],
                        borderColor: [
                            'rgba(75, 192, 192, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)',
                            'rgba(255, 99, 132, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true,
                                stepSize: 5
                            }
                        }]
                    }
                }
            });
        });
    </script>
</body>
</html>">
                    <a class="nav-link" href="${pageContext.request.contextPath}/clients/list">Clients</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/plats/list">Dishes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/menus/list">Menus</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/tables/list">Tables</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/reservations/list">Reservations</a>
                </li>
                <li class="nav-item