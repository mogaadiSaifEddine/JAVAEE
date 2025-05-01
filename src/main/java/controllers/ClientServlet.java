package controllers;

import entities.Client;
import models.ClientModel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/clients/*")
public class ClientServlet extends HttpServlet {

    private ClientModel clientModel;

    public void init() {
        clientModel = new ClientModel();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listClients(request, response);
                break;
            case "/new":
                showNewForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteClient(request, response);
                break;
            case "/view":
                viewClient(request, response);
                break;
            default:
                listClients(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/insert":
                insertClient(request, response);
                break;
            case "/update":
                updateClient(request, response);
                break;
            default:
                listClients(request, response);
                break;
        }
    }

    private void listClients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if we're in a recursive loop
        if (request.getAttribute("clientListProcessed") != null) {
            System.out.println("Preventing duplicate client list processing");
            return;
        }

        // Mark this request as being processed
        request.setAttribute("clientListProcessed", Boolean.TRUE);

        try {
            // Continue with normal processing
            List<Client> clientList = clientModel.getAllClients();
            System.out.println("Client list size: " + clientList.size());
            request.setAttribute("clientList", clientList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/clients/clientList.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // Log the error and show an error page
            System.err.println("Error retrieving client list: " + e.getMessage());
            e.printStackTrace();

            // Set error attributes
            request.setAttribute("errorMessage", "Database error. Please try again later.");
            RequestDispatcher errorDispatcher = request.getRequestDispatcher("/error.jsp");
            errorDispatcher.forward(request, response);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clients/add.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Client client = clientModel.getClientById(id);

        if (client != null) {
            request.setAttribute("client", client);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/clients/edit.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/clients/list");
        }
    }

    private void viewClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Client client = clientModel.getClientById(id);

        if (client != null) {
            request.setAttribute("client", client);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/clients/view.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/clients/list");
        }
    }

    private void insertClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int phone = Integer.parseInt(request.getParameter("phone"));
        String email = request.getParameter("email");
        String localisation = request.getParameter("localisation");

        Client newClient = new Client(firstName, lastName, phone, email, localisation);
        clientModel.addClient(newClient);

        response.sendRedirect(request.getContextPath() + "/clients/list");
    }

    private void updateClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int phone = Integer.parseInt(request.getParameter("phone"));
        String email = request.getParameter("email");
        String localisation = request.getParameter("localisation");

        Client client = new Client(id, firstName, lastName, phone, email, localisation);
        clientModel.updateClient(client);

        response.sendRedirect(request.getContextPath() + "/clients/list");
    }

    private void deleteClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        clientModel.deleteClient(id);

        response.sendRedirect(request.getContextPath() + "/clients/list");
    }
}