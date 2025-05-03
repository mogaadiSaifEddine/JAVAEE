<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Restaurant Management System</title>
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css"
    />
  </head>
  <body>
    <div class="container">
      <header class="jumbotron my-4">
        <h1 class="display-3">Restaurant Management System</h1>
        <p class="lead">
          Manage your restaurant activities efficiently with our system.
        </p>
      </header>

      <div class="row text-center">
        <div class="col-lg-4 col-md-6 mb-4">
          <div class="card h-100">
            <div class="card-body">
              <h4 class="card-title">Clients</h4>
              <p class="card-text">
                Manage all your clients and their information.
              </p>
            </div>
            <div class="card-footer">
              <a
                href="${pageContext.request.contextPath}/clients/list"
                class="btn btn-primary"
                >Go to Clients</a
              >
            </div>
          </div>
        </div>

        <div class="col-lg-4 col-md-6 mb-4">
          <div class="card h-100">
            <div class="card-body">
              <h4 class="card-title">Dishes</h4>
              <p class="card-text">
                Manage all dishes offered by your restaurant.
              </p>
            </div>
            <div class="card-footer">
              <a
                href="${pageContext.request.contextPath}/plats/list"
                class="btn btn-primary"
                >Go to Dishes</a
              >
            </div>
          </div>
        </div>

        <div class="col-lg-4 col-md-6 mb-4">
          <div class="card h-100">
            <div class="card-body">
              <h4 class="card-title">Menus</h4>
              <p class="card-text">
                Create and manage menus for your restaurant.
              </p>
            </div>
            <div class="card-footer">
              <a
                href="${pageContext.request.contextPath}/menus/list"
                class="btn btn-primary"
                >Go to Menus</a
              >
            </div>
          </div>
        </div>

        <div class="col-lg-4 col-md-6 mb-4">
          <div class="card h-100">
            <div class="card-body">
              <h4 class="card-title">Tables</h4>
              <p class="card-text">
                Manage your restaurant tables and their availability.
              </p>
            </div>
            <div class="card-footer">
              <a
                href="${pageContext.request.contextPath}/tables/list"
                class="btn btn-primary"
                >Go to Tables</a
              >
            </div>
          </div>
        </div>

        <div class="col-lg-4 col-md-6 mb-4">
          <div class="card h-100">
            <div class="card-body">
              <h4 class="card-title">Reservations</h4>
              <p class="card-text">
                Manage table reservations for your clients.
              </p>
            </div>
            <div class="card-footer">
              <a
                href="${pageContext.request.contextPath}/reservations/list"
                class="btn btn-primary"
                >Go to Reservations</a
              >
            </div>
          </div>
        </div>

        <div class="col-lg-4 col-md-6 mb-4">
          <div class="card h-100">
            <div class="card-body">
              <h4 class="card-title">AAAAAAAAAAAAAA</h4>
              <p class="card-text">
                Manage client orders and track their status.
              </p>
            </div>
            <div class="card-footer">
              <a
                href="${pageContext.request.contextPath}/commandes/list"
                class="btn btn-primary"
                >Go to Orders</a
              >
            </div>
          </div>
        </div>
      </div>
    </div>

    <footer class="py-5 bg-dark">
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
