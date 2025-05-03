<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="jakarta.tags.core" prefix="c" %> <%@
taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Reservation Management</title>
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
          <li class="nav-item">
            <a
              class="nav-link"
              href="${pageContext.request.contextPath}/tables/list"
              >Tables</a
            >
          </li>
          <li class="nav-item active">
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
                Reservation Management
                <a
                  href="${pageContext.request.contextPath}/reservations/new"
                  class="btn btn-primary float-right"
                  >Add New Reservation</a
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
                    placeholder="Search reservations..."
                  />
                </div>
                <div class="col-md-6">
                  <div class="form-row">
                    <div class="col">
                      <input
                        type="date"
                        id="dateFilter"
                        class="form-control"
                        value="${java.time.LocalDate.now()}"
                      />
                    </div>
                    <div class="col">
                      <button id="filterBtn" class="btn btn-secondary">
                        Filter by Date
                      </button>
                    </div>
                  </div>
                </div>
              </div>

              <table class="table table-striped table-bordered">
                <thead class="thead-dark">
                  <tr>
                    <th>Client</th>
                    <th>Table</th>
                    <th>Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="reservation" items="${reservationList}">
                    <tr>
                      <td>
                        <a
                          href="${pageContext.request.contextPath}/clients/view?id=${reservation.client.idClient}"
                        >
                          ${reservation.client.FNameClient}
                          ${reservation.client.LNameClient}
                        </a>
                      </td>
                      <td>
                        <a
                          href="${pageContext.request.contextPath}/tables/view?id=${reservation.table.idTab}"
                        >
                          Table #${reservation.table.idTab}
                          (${reservation.table.nbrPlace} seats)
                        </a>
                      </td>
                      <td>
                        <fmt:formatDate
                          value="${reservation.dateReservation}"
                          pattern="yyyy-MM-dd"
                        />
                      </td>
                      <td>
                        <fmt:formatDate
                          value="${reservation.heureDebutReservation}"
                          pattern="HH:mm"
                        />
                      </td>
                      <td>
                        <fmt:formatDate
                          value="${reservation.heureFinReservation}"
                          pattern="HH:mm"
                        />
                      </td>
                      <td>
                        <a
                          href="${pageContext.request.contextPath}/reservations/view?clientId=${reservation.client.idClient}&tableId=${reservation.table.idTab}"
                          class="btn btn-info btn-sm"
                        >
                          <i class="fas fa-eye"></i>
                        </a>
                        <a
                          href="${pageContext.request.contextPath}/reservations/edit?clientId=${reservation.client.idClient}&tableId=${reservation.table.idTab}"
                          class="btn btn-primary btn-sm"
                        >
                          <i class="fas fa-edit"></i>
                        </a>
                        <a
                          href="${pageContext.request.contextPath}/reservations/delete?clientId=${reservation.client.idClient}&tableId=${reservation.table.idTab}"
                          class="btn btn-danger btn-sm"
                          onclick="return confirm('Are you sure you want to delete this reservation?')"
                        >
                          <i class="fas fa-trash"></i>
                        </a>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>

              <c:if test="${empty reservationList}">
                <div class="alert alert-info">
                  No reservations found. Add your first reservation!
                </div>
              </c:if>
            </div>
          </div>
        </div>
      </div>

      <!-- Calendar View -->
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
      // Search functionality
      $(document).ready(function () {
        $("#searchInput").on("keyup", function () {
          var value = $(this).val().toLowerCase();
          $("table tbody tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
          });
        });

        // Date filter functionality
        $("#filterBtn").click(function () {
          var selectedDate = $("#dateFilter").val();
          window.location.href =
            "${pageContext.request.contextPath}/reservations/list?date=" +
            selectedDate;
        });

        // Calendar functionality would be implemented here
        // For simplicity, we're showing a placeholder calendar
        populateCalendarDates();
      });

      function populateCalendarDates() {
        // Get current date
        var currentDate = new Date();
        var dayOfWeek = currentDate.getDay() || 7; // Make Sunday 7 instead of 0

        // Calculate Monday of the current week
        var monday = new Date(currentDate);
        monday.setDate(currentDate.getDate() - dayOfWeek + 1);

        // Populate dates for each day
        var dateDisplays = document.querySelectorAll(".date-display");
        for (var i = 0; i < 7; i++) {
          var date = new Date(monday);
          date.setDate(monday.getDate() + i);
          var formattedDate = date.toLocaleDateString("en-US", {
            month: "short",
            day: "numeric",
          });
          dateDisplays[i].textContent = formattedDate;

          // Highlight today
          if (date.toDateString() === currentDate.toDateString()) {
            dateDisplays[i].parentElement.classList.add("bg-light");
          }
        }

        // In a real application, you would populate reservations from the server data
        // This is just a placeholder for demonstration
        var reservationContainers = document.querySelectorAll(
          ".reservations-container"
        );
        // Just add a sample reservation to Wednesday
        var sampleReservation = document.createElement("div");
        sampleReservation.className = "reservation-item";
        sampleReservation.textContent = "19:00 - Table #2: John Doe";
        reservationContainers[2].appendChild(sampleReservation);
      }
    </script>
  </body>
</html>
