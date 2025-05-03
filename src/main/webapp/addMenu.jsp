<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Create New Menu</title>
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css"
    />
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/"
        >Restaurant Management</a
      >
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a
              class="nav-link"
              href="${pageContext.request.contextPath}/clients/list"
              >Clients</a
            >
          </li>
          <li class="nav-item">
            <a
              class="nav-link"
              href="${pageContext.request.contextPath}/plats/list"
              >Dishes</a
            >
          </li>
          <li class="nav-item active">
            <a
              class="nav-link"
              href="${pageContext.request.contextPath}/menus/list"
              >Menus</a
            >
          </li>
          <li class="nav-item">
            <a
              class="nav-link"
              href="${pageContext.request.contextPath}/tables/list"
              >Tables</a
            >
          </li>
          <li class="nav-item">
            <a
              class="nav-link"
              href="${pageContext.request.contextPath}/reservations/list"
              >Reservations</a
            >
          </li>
          <li class="nav-item">
            <a
              class="nav-link"
              href="${pageContext.request.contextPath}/commandes/list"
              >Orders</a
            >
          </li>
        </ul>
      </div>
    </nav>

    <div class="container mt-4">
      <div class="row">
        <div class="col-md-8 offset-md-2">
          <div class="card">
            <div class="card-header">
              <h4>Create New Menu</h4>
              <% java.util.List platList =
              (java.util.List)request.getAttribute("platList"); if (platList !=
              null) { %>
              <span><%= platList.size() %> Dishes</span>
              <% } else { %>
              <span>0 Dishes</span>
              <% } %>
            </div>
            <div class="card-body">
              <form
                action="${pageContext.request.contextPath}/menus/insert"
                method="post"
              >
                <div class="form-group">
                  <label for="menuName">Menu Name (Optional):</label>
                  <input
                    type="text"
                    class="form-control"
                    id="menuName"
                    name="menuName"
                    placeholder="e.g., Lunch Special, Dinner Menu, etc."
                  />
                </div>

                <div class="form-group">
                  <label>Select Dishes:</label>
                  <div class="dish-selection">
                    <% if (platList != null && !platList.isEmpty()) { for (int i
                    = 0; i < platList.size(); i++) { entities.Plat plat =
                    (entities.Plat)platList.get(i); %>
                    <div class="card mb-2">
                      <div class="card-body p-2">
                        <div class="form-check">
                          <input
                            class="form-check-input dish-checkbox"
                            type="checkbox"
                            name="platIds"
                            value="<%= plat.getIdPlat() %>"
                            id="plat<%= plat.getIdPlat() %>"
                          />
                          <label
                            class="form-check-label"
                            for="plat<%= plat.getIdPlat() %>"
                          >
                            <strong><%= plat.getNamePlat() %></strong> - $<%=
                            String.format("%.2f", plat.getPrixPlat()) %>
                          </label>
                          <p class="mb-0 small text-muted">
                            <%= plat.getDiscPlat() %>
                          </p>
                        </div>
                      </div>
                    </div>
                    <% } } else { %>
                    <div class="alert alert-warning">
                      No dishes available. Please
                      <a href="${pageContext.request.contextPath}/plats/new"
                        >add some dishes</a
                      >
                      first.
                    </div>
                    <% } %>
                  </div>
                </div>

                <div class="form-group">
                  <div class="card">
                    <div class="card-header bg-light">
                      <h5 class="mb-0">Menu Summary</h5>
                    </div>
                    <div class="card-body">
                      <div id="selectedItems">
                        <p class="text-muted">No dishes selected</p>
                      </div>
                      <hr />
                      <div class="d-flex justify-content-between">
                        <h5>Total Price:</h5>
                        <h5 id="totalPrice">$0.00</h5>
                      </div>
                    </div>
                  </div>
                </div>

                <button
                  type="submit"
                  class="btn btn-primary"
                  id="submitBtn"
                  disabled
                >
                  Create Menu
                </button>
                <a
                  href="${pageContext.request.contextPath}/menus/list"
                  class="btn btn-secondary"
                  >Cancel</a
                >
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>

    <footer class="py-5 bg-dark mt-5">
      <div class="container">
        <p class="m-0 text-center text-white">
          Copyright &copy; Restaurant Management System 2025
        </p>
      </div>
    </footer>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
      $(document).ready(function() {
          // Store dish prices for easy access
          let dishPrices = {};

          <% if (platList != null && !platList.isEmpty()) {
              for (int i = 0; i < platList.size(); i++) {
                  entities.Plat plat = (entities.Plat)platList.get(i);
          %>
              dishPrices[<%= plat.getIdPlat() %>] = <%= plat.getPrixPlat() %>;
          <%
              }
          } %>

          // Handle dish checkbox changes
          $('.dish-checkbox').change(function() {
              updateMenuSummary();
          });

          function updateMenuSummary() {
              let selectedDishes = $('.dish-checkbox:checked');
              let totalPrice = 0;
              let summaryHTML = '';

              if(selectedDishes.length > 0) {
                  summaryHTML = '<ul class="list-group">';

                  selectedDishes.each(function() {
                      let dishId = $(this).val();
                      let dishName = $(this).siblings('label').text();
                      let dishPrice = dishPrices[dishId];

                      totalPrice += dishPrice;

                      summaryHTML += `
                          <li class="list-group-item d-flex justify-content-between align-items-center">
                              ${dishName}
                              <span class="badge badge-primary badge-pill">$${dishPrice.toFixed(2)}</span>
                          </li>
                      `;
                  });

                  summaryHTML += '</ul>';
                  $('#submitBtn').prop('disabled', false);
              } else {
                  summaryHTML = '<p class="text-muted">No dishes selected</p>';
                  $('#submitBtn').prop('disabled', true);
              }

              $('#selectedItems').html(summaryHTML);
              $('#totalPrice').text('$' + totalPrice.toFixed(2));
          }
      });
    </script>
  </body>
</html>
