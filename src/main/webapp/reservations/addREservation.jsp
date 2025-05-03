<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Reservation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
                <li class="nav-item">
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
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/reservations/list">Reservations</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/commandes/list">Orders</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <div class="card">
                    <div class="card-header">
                        <h4>Add New Reservation</h4>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/reservations/insert" method="post">
                            <div class="form-group">
                                <label for="clientId">Client:</label>
                                <select class="form-control" id="clientId" name="clientId" required>
                                    <option value="">-- Select Client --</option>
                                    <c:forEach var="client" items="${clientList}">
                                        <option value="${client.idClient}" ${client.idClient == selectedClientId ? 'selected' : ''}>
                                            ${client.FNameClient} ${client.LNameClient} (${client.emailClient})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="date">Date:</label>
                                <input type="date" class="form-control" id="date" name="date" required>
                            </div>

                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="startTime">Start Time:</label>
                                    <input type="time" class="form-control" id="startTime" name="startTime" required>
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="endTime">End Time:</label>
                                    <input type="time" class="form-control" id="endTime" name="endTime" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="tableId">Table:</label>
                                <select class="form-control" id="tableId" name="tableId" required>
                                    <option value="">-- Select Table --</option>
                                    <c:forEach var="table" items="${tableList}">
                                        <option value="${table.idTab}">
                                            Table #${table.idTab} - ${table.nbrPlace} seats (${table.localisationTable})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <c:if test="${not empty param.clientId}">
                                <input type="hidden" name="fromClient" value="true">
                            </c:if>

                            <button type="submit" class="btn btn-primary">Save Reservation</button>
                            <c:choose>
                                <c:when test="${not empty param.clientId}">
                                    <a href="${pageContext.request.contextPath}/reservations/client?clientId=${param.clientId}" class="btn btn-secondary">Cancel</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/reservations/list" class="btn btn-secondary">Cancel</a>
                                </c:otherwise>
                            </c:choose>
                        </form>
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
    <script>
        // Set default date to today
        document.getElementById('date').valueAsDate = new Date();

        // Set default times
        document.getElementById('startTime').value = '19:00';
        document.getElementById('endTime').value = '21:00';

        // Function to check available tables based on selected date and time
        function checkAvailableTables() {
            // This would be implemented with AJAX in a real application
            // to dynamically update the available tables
            console.log('Checking available tables...');
        }

        // Add event listeners to date and time inputs
        document.getElementById('date').addEventListener('change', checkAvailableTables);
        document.getElementById('startTime').addEventListener('change', checkAvailableTables);
        document.getElementById('endTime').addEventListener('change', checkAvailableTables);
    </script>
</body>
</html>