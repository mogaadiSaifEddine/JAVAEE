<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dish Management</title>
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
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/clients/list">Clients</a>
                </li>
                <li class="nav-item active">
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
                <li class="nav-item">
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
                        <h4>Dish Management
                            <a href="${pageContext.request.contextPath}/plats/new" class="btn btn-primary float-right">Add New Dish</a>
                        </h4>
                    </div>
                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <input type="text" id="searchInput" class="form-control" placeholder="Search dishes...">
                            </div>
                            <div class="col-md-6">
                                <div class="btn-group" role="group">
                                    <button type="button" class="btn btn-outline-secondary" onclick="filterDishes('all')">All</button>
                                    <button type="button" class="btn btn-outline-secondary" onclick="filterDishes('appetizer')">Appetizers</button>
                                    <button type="button" class="btn btn-outline-secondary" onclick="filterDishes('main')">Main Courses</button>
                                    <button type="button" class="btn btn-outline-secondary" onclick="filterDishes('dessert')">Desserts</button>
                                    <button type="button" class="btn btn-outline-secondary" onclick="filterDishes('drink')">Drinks</button>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <c:forEach var="plat" items="${platList}">
                                <div class="col-md-4 mb-4 dish-item">
                                    <div class="card h-100">
                                        <div class="card-header">
                                            <h5>${plat.namePlat}</h5>
                                        </div>
                                        <div class="card-body">
                                            <p class="card-text">${plat.discPlat}</p>
                                            <p><strong>Price:</strong> $${plat.prixPlat}</p>
                                            <p><strong>Review:</strong> ${plat.avisPlat}</p>
                                        </div>
                                        <div class="card-footer">
                                            <a href="${pageContext.request.contextPath}/plats/view?id=${plat.idPlat}" class="btn btn-info btn-sm">
                                                <i class="fas fa-eye"></i> View
                                            </a>
                                            <a href="${pageContext.request.contextPath}/plats/edit?id=${plat.idPlat}" class="btn btn-primary btn-sm">
                                                <i class="fas fa-edit"></i> Edit
                                            </a>
                                            <a href="${pageContext.request.contextPath}/plats/delete?id=${plat.idPlat}"
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Are you sure you want to delete this dish?')">
                                                <i class="fas fa-trash"></i> Delete
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <c:if test="${empty platList}">
                            <div class="alert alert-info">
                                No dishes found. Add your first dish!
                            </div>
                        </c:if>
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
        $(document).ready(function() {
            // Search functionality
            $("#searchInput").on("keyup", function() {
                var value = $(this).val().toLowerCase();
                $(".dish-item").filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });
        });

        // Filter dishes by category
        function filterDishes(category) {
            if (category === 'all') {
                $(".dish-item").show();
            } else {
                $(".dish-item").hide();
                // In a real implementation, dishes would have a category attribute to filter by
                // This is just a placeholder for demonstration
                if (category === 'appetizer') {
                    // Show appetizers
                    $(".dish-item:contains('salad'), .dish-item:contains('soup')").show();
                } else if (category === 'main') {
                    // Show main courses
                    $(".dish-item:contains('steak'), .dish-item:contains('fish'), .dish-item:contains('pasta')").show();
                } else if (category === 'dessert') {
                    // Show desserts
                    $(".dish-item:contains('cake'), .dish-item:contains('ice cream'), .dish-item:contains('mousse')").show();
                } else if (category === 'drink') {
                    // Show drinks
                    $(".dish-item:contains('coffee'), .dish-item:contains('juice'), .dish-item:contains('wine')").show();
                }
            }
        }
    </script>
</body>
</html>