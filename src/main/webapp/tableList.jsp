<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Table Management</title>
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
          <li class="nav-item">
            <a
              class="nav-link"
              href="${pageContext.request.contextPath}/menus/list"
              >Menus</a
            >
          </li>
          <li class="nav-item active">
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
                Table Management
                <a
                  href="${pageContext.request.contextPath}/tables/new"
                  class="btn btn-primary float-right"
                  >Add New Table</a
                >
              </h4>
            </div>
            <div class="card-body">
              <div class="row mb-3">
                <div class="col-md-6">
                  <input
                    type="text"
                    id="searchInput"
                    class="form-control"
                    placeholder="Search tables..."
                  />
                </div>
                <div class="col-md-6">
                  <div class="form-row">
                    <div class="col">
                      <select id="locationFilter" class="form-control">
                        <option value="">All Locations</option>
                        <option value="Terrace">Terrace</option>
                        <option value="Main Hall">Main Hall</option>
                        <option value="Window">Window</option>
                        <option value="Private Room">Private Room</option>
                        <option value="Bar">Bar</option>
                      </select>
                    </div>
                    <div class="col">
                      <button id="filterBtn" class="btn btn-secondary">
                        Filter
                      </button>
                    </div>
                  </div>
                </div>
              </div>

              <div class="row">
                <c:forEach var="table" items="${tableList}">
                  <div
                    class="col-md-4 mb-4"
                    data-location="${table.localisationTable}"
                    data-seats="${table.nbrPlace}"
                  >
                    <div class="card h-100">
                      <div class="card-header">
                        <h5>Table #${table.idTab}</h5>
                      </div>
                      <div class="card-body">
                        <p><strong>Seats:</strong> ${table.nbrPlace}</p>
                        <p>
                          <strong>Location:</strong> ${table.localisationTable}
                        </p>
                        <div class="text-center">
                          <i class="fas fa-chair fa-2x mr-1"></i>
                          <c:forEach begin="1" end="${table.nbrPlace - 1}">
                            <i class="fas fa-chair fa-2x mr-1"></i>
                          </c:forEach>
                        </div>
                      </div>
                      <div class="card-footer">
                        <a
                          href="${pageContext.request.contextPath}/tables/view?id=${table.idTab}"
                          class="btn btn-info btn-sm"
                        >
                          <i class="fas fa-eye"></i> View
                        </a>
                        <a
                          href="${pageContext.request.contextPath}/tables/edit?id=${table.idTab}"
                          class="btn btn-primary btn-sm"
                        >
                          <i class="fas fa-edit"></i> Edit
                        </a>
                        <a
                          href="${pageContext.request.contextPath}/tables/delete?id=${table.idTab}"
                          class="btn btn-danger btn-sm"
                          onclick="return confirm('Are you sure you want to delete this table?')"
                        >
                          <i class="fas fa-trash"></i> Delete
                        </a>
                      </div>
                    </div>
                  </div>
                </c:forEach>
              </div>

              <c:if test="${empty tableList}">
                <div class="alert alert-info">
                  No tables found. Add your first table!
                </div>
              </c:if>
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
      $(document).ready(function () {
        // Search functionality
        $("#searchInput").on("keyup", function () {
          var value = $(this).val().toLowerCase();
          $(".col-md-4").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
          });
        });

        // Filter functionality
        $("#filterBtn").click(function () {
          var location = $("#locationFilter").val();

          if (location) {
            $(".col-md-4").hide();
            $(".col-md-4[data-location='" + location + "']").show();
          } else {
            $(".col-md-4").show();
          }
        });
      });
    </script>
  </body>
</html>
