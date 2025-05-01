<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/tags/core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create New Order</title>
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
                <li class="nav-item active">
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
                        <h4>Create New Order</h4>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/commandes/insert" method="post">
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
                                <small class="text-muted">
                                    <a href="${pageContext.request.contextPath}/clients/new" target="_blank">
                                        <i class="fas fa-plus-circle"></i> Add New Client
                                    </a>
                                </small>
                            </div>

                            <div class="form-group">
                                <label for="date">Order Date:</label>
                                <input type="date" class="form-control" id="date" name="date" required>
                            </div>

                            <!-- In a real application, this would include order items -->
                            <div class="form-group">
                                <label>Order Items:</label>

                                <!-- Menu Selection -->
                                <div class="card mb-3">
                                    <div class="card-header bg-light">
                                        <h5 class="mb-0">Select Menu</h5>
                                    </div>
                                    <div class="card-body">
                                        <select class="form-control mb-2" id="menuSelect">
                                            <option value="">-- Select a Pre-defined Menu --</option>
                                            <!-- This would be populated from the database in a real application -->
                                            <option value="1">Lunch Menu</option>
                                            <option value="2">Dinner Menu</option>
                                            <option value="3">Special Menu</option>
                                        </select>
                                        <p class="text-muted">Or select individual dishes below</p>
                                    </div>
                                </div>

                                <!-- Individual Dishes -->
                                <div class="card">
                                    <div class="card-header bg-light">
                                        <h5 class="mb-0">Select Dishes</h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="input-group mb-3">
                                                    <select class="form-control" id="dishSelect">
                                                        <option value="">-- Select a Dish --</option>
                                                        <!-- This would be populated from the database in a real application -->
                                                        <option value="1" data-price="12.99">Spaghetti Carbonara - $12.99</option>
                                                        <option value="2" data-price="8.50">Caesar Salad - $8.50</option>
                                                        <option value="3" data-price="10.99">Margherita Pizza - $10.99</option>
                                                        <option value="4" data-price="18.99">Grilled Salmon - $18.99</option>
                                                        <option value="5" data-price="6.50">Chocolate Mousse - $6.50</option>
                                                    </select>
                                                    <div class="input-group-append">
                                                        <button class="btn btn-outline-secondary" type="button" id="addDishBtn">Add</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="selected-dishes">
                                            <table class="table" id="orderItemsTable">
                                                <thead>
                                                    <tr>
                                                        <th>Dish</th>
                                                        <th>Quantity</th>
                                                        <th>Price</th>
                                                        <th>Total</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <!-- Order items will be added here dynamically -->
                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                        <th colspan="3" class="text-right">Grand Total:</th>
                                                        <th id="grandTotal">$0.00</th>
                                                        <th></th>
                                                    </tr>
                                                </tfoot>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="note">Additional Notes:</label>
                                <textarea class="form-control" id="note" name="note" rows="3" placeholder="Special instructions, allergies, etc."></textarea>
                            </div>

                            <c:if test="${not empty param.clientId}">
                                <input type="hidden" name="fromClient" value="true">
                            </c:if>

                            <button type="submit" class="btn btn-primary">Create Order</button>
                            <c:choose>
                                <c:when test="${not empty param.clientId}">
                                    <a href="${pageContext.request.contextPath}/commandes/client?clientId=${param.clientId}" class="btn btn-secondary">Cancel</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/commandes/list" class="btn btn-secondary">Cancel</a>
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
        $(document).ready(function() {
            // Set default date to today
            document.getElementById('date').valueAsDate = new Date();

            // Store order items
            let orderItems = [];
            let grandTotal = 0;

            // Add dish to order
            $('#addDishBtn').click(function() {
                const dishSelect = $('#dishSelect');
                const dishId = dishSelect.val();

                if (dishId) {
                    const dishText = dishSelect.find('option:selected').text();
                    const dishPrice = parseFloat(dishSelect.find('option:selected').data('price'));

                    // Check if dish already exists in order
                    const existingItemIndex = orderItems.findIndex(item => item.id === dishId);

                    if (existingItemIndex !== -1) {
                        // Increment quantity if dish already exists
                        orderItems[existingItemIndex].quantity++;
                        orderItems[existingItemIndex].total = orderItems[existingItemIndex].quantity * orderItems[existingItemIndex].price;
                    } else {
                        // Add new dish
                        orderItems.push({
                            id: dishId,
                            name: dishText,
                            price: dishPrice,
                            quantity: 1,
                            total: dishPrice
                        });
                    }

                    // Reset dish select
                    dishSelect.val('');

                    // Update table
                    updateOrderItemsTable();
                }
            });

            // Remove dish from order
            $(document).on('click', '.remove-item-btn', function() {
                const itemIndex = $(this).data('index');
                orderItems.splice(itemIndex, 1);
                updateOrderItemsTable();
            });

            // Update quantity
            $(document).on('change', '.item-quantity', function() {
                const itemIndex = $(this).data('index');
                const newQuantity = parseInt($(this).val());

                if (newQuantity > 0) {
                    orderItems[itemIndex].quantity = newQuantity;
                    orderItems[itemIndex].total = newQuantity * orderItems[itemIndex].price;
                    updateOrderItemsTable();
                }
            });

            // Update order items table
            function updateOrderItemsTable() {
                const tbody = $('#orderItemsTable tbody');
                tbody.empty();
                grandTotal = 0;

                orderItems.forEach((item, index) => {
                    grandTotal += item.total;

                    tbody.append(`
                        <tr>
                            <td>${item.name}</td>
                            <td>
                                <input type="number" class="form-control form-control-sm item-quantity"
                                       data-index="${index}" value="${item.quantity}" min="1" style="width: 70px;">
                                <input type="hidden" name="itemId[]" value="${item.id}">
                                <input type="hidden" name="itemQuantity[]" value="${item.quantity}">
                            </td>
                            <td>$${item.price.toFixed(2)}</td>
                            <td>$${item.total.toFixed(2)}</td>
                            <td>
                                <button type="button" class="btn btn-danger btn-sm remove-item-btn" data-index="${index}">
                                    <i class="fas fa-times"></i>
                                </button>
                            </td>
                        </tr>
                    `);
                });

                // Update grand total
                $('#grandTotal').text('$' + grandTotal.toFixed(2));
            }

            // Handle menu selection
            $('#menuSelect').change(function() {
                const menuId = $(this).val();

                if (menuId) {
                    // In a real application, you would fetch the menu details via AJAX
                    // For demonstration, we'll add some predefined dishes

                    // Clear existing items
                    orderItems = [];

                    // Add menu items based on selection
                    if (menuId === '1') { // Lunch Menu
                        orderItems.push(
                            { id: '2', name: 'Caesar Salad - $8.50', price: 8.50, quantity: 1, total: 8.50 },
                            { id: '1', name: 'Spaghetti Carbonara - $12.99', price: 12.99, quantity: 1, total: 12.99 },
                            { id: '5', name: 'Chocolate Mousse - $6.50', price: 6.50, quantity: 1, total: 6.50 }
                        );
                    } else if (menuId === '2') { // Dinner Menu
                        orderItems.push(
                            { id: '2', name: 'Caesar Salad - $8.50', price: 8.50, quantity: 1, total: 8.50 },
                            { id: '4', name: 'Grilled Salmon - $18.99', price: 18.99, quantity: 1, total: 18.99 },
                            { id: '5', name: 'Chocolate Mousse - $6.50', price: 6.50, quantity: 1, total: 6.50 }
                        );
                    } else if (menuId === '3') { // Special Menu
                        orderItems.push(
                            { id: '3', name: 'Margherita Pizza - $10.99', price: 10.99, quantity: 1, total: 10.99 },
                            { id: '5', name: 'Chocolate Mousse - $6.50', price: 6.50, quantity: 1, total: 6.50 }
                        );
                    }

                    // Update table
                    updateOrderItemsTable();

                    // Reset menu select
                    $(this).val('');
                }
            });
        });
    </script>
</body>
</html>