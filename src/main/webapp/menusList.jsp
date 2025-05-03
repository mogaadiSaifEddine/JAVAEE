<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Menu Management</title>
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
        <div class="col-md-12">
          <div class="card">
            <div class="card-header">
              <h4>
                Menu Management
                <a
                  href="${pageContext.request.contextPath}/menus/new"
                  class="btn btn-primary float-right"
                  >Create New Menu</a
                >
              </h4>
            </div>
            <div class="row">
              <% java.util.List menuList =
              (java.util.List)request.getAttribute("menuList"); if (menuList !=
              null && !menuList.isEmpty()) { for (int i = 0; i <
              menuList.size(); i++) { Object menuObj = menuList.get(i); if
              (menuObj != null) { entities.Menu menu = (entities.Menu)menuObj;
              %>
              <div class="col-md-6 mb-4">
                <div class="card">
                  <div class="card-header">
                    <h5>Menu #<%= menu.getIdMenu() %></h5>
                  </div>
                  <div class="card-body">
                    <% java.util.List plats = menu.getPlats(); if (plats != null
                    && !plats.isEmpty()) { %>
                    <h6>Dishes:</h6>
                    <ul class="list-group">
                      <% float totalPrice = 0; for (int j = 0; j < plats.size();
                      j++) { entities.Plat plat = (entities.Plat)plats.get(j);
                      totalPrice += plat.getPrixPlat(); %>
                      <li
                        class="list-group-item d-flex justify-content-between align-items-center"
                      >
                        <%= plat.getNamePlat() %>
                        <span class="badge badge-primary badge-pill">
                          $<%= String.format("%.2f", plat.getPrixPlat()) %>
                        </span>
                      </li>
                      <% } %>
                    </ul>
                    <div class="mt-3">
                      <strong
                        >Total Price: $<%= String.format("%.2f", totalPrice)
                        %></strong
                      >
                    </div>
                    <% } else { %>
                    <p class="text-muted">
                      This menu doesn't contain any dishes yet.
                    </p>
                    <% } %>
                  </div>
                  <!-- Card footer with actions -->
                  <div class="card-footer">
                    <a
                      href="${pageContext.request.contextPath}/menus/view?id=<%= menu.getIdMenu() %>"
                      class="btn btn-info btn-sm"
                    >
                      <i class="fas fa-eye"></i> View
                    </a>
                    <a
                      href="${pageContext.request.contextPath}/menus/edit?id=<%= menu.getIdMenu() %>"
                      class="btn btn-primary btn-sm"
                    >
                      <i class="fas fa-edit"></i> Edit
                    </a>
                    <a
                      href="${pageContext.request.contextPath}/menus/delete?id=<%= menu.getIdMenu() %>"
                      class="btn btn-danger btn-sm"
                      onclick="return confirm('Are you sure you want to delete this menu?')"
                    >
                      <i class="fas fa-trash"></i> Delete
                    </a>
                  </div>
                </div>
              </div>
              <% } } } else { %>
              <div class="col-md-12">
                <div class="alert alert-info">
                  No menus available. Create your first menu!
                </div>
              </div>
              <% } %>
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
  </body>
</html>
