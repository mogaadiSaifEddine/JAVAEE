<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Client Management</title>
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
          <li class="nav-item active">
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
                Client Management
                <a
                  href="${pageContext.request.contextPath}/clients/new"
                  class="btn btn-primary float-right"
                  >Add New Client</a
                >
              </h4>
            </div>
            <div class="card-body">
              <div style="background-color: lightblue; padding: 10px;">
                <h3>First client details:</h3>
                <c:if test="${not empty clientList}">
                  Client #1 ID: ${clientList[0].idClient}<br>
                  Client #1 First Name: ${clientList[0].FNameClient}<br>
                  Client #1 toString: ${clientList[0]}<br>
                </c:if>
              </div>
              <table class="table table-bordered">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>Location</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <%
                    java.util.List clientList = (java.util.List)request.getAttribute("clientList");
                    if (clientList != null && !clientList.isEmpty()) {
                      for (int i = 0; i < clientList.size(); i++) {
                        Object clientObj = clientList.get(i);
                        if (clientObj != null) {
                          entities.Client client = (entities.Client)clientObj;
                  %>
                    <tr>
                      <td><%= client.getIdClient() %></td>
                      <td><%= client.getFNameClient() %></td>
                      <td><%= client.getLNameClient() %></td>
                      <td><%= client.getPhoneClient() %></td>
                      <td><%= client.getEmailClient() %></td>
                      <td><%= client.getLocalisationClient() %></td>
                      <td>
                        <a href="${pageContext.request.contextPath}/clients/delete?id=<%= client.getIdClient() %>" class="btn btn-danger btn-sm"
                          onclick="return confirm('Are you sure you want to delete this client?')">Delete</a>
                        <a href="${pageContext.request.contextPath}/reservations/client?clientId=<%= client.getIdClient() %>" class="btn btn-success btn-sm">Reservations</a>
                        <a href="${pageContext.request.contextPath}/commandes/client?clientId=<%= client.getIdClient() %>" class="btn btn-warning btn-sm">Orders</a>
                      </td>
                    </tr>
                  <%
                        }
                      }
                    } else {
                  %>
                    <tr>
                      <td colspan="7" class="text-center">No clients found. Please add a client.</td>
                    </tr>
                  <%
                    }
                  %>
                </tbody>
              </table>
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
