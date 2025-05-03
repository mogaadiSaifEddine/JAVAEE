package controllers;

import entities.Client;
import entities.Commande;
import entities.Reservation;
import models.ClientModel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.CommandeModel;
import models.ReservationModel;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// @WebServlet("/clients/*")
public class ClientServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ClientServlet.class.getName());
    
    private ClientModel clientModel;

    @Override
    public void init() {
        clientModel = new ClientModel();
        LOGGER.info("ClientServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        try {
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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request: " + action, e);
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        try {
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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing form submission: " + action, e);
            request.setAttribute("errorMessage", "An error occurred while processing your form: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listClients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          if (request.getAttribute("clientListProcessed") != null) {
            System.out.println("Preventing duplicate client list processing");
            return;
        }

        // Mark this request as being processed

        try {
        request.setAttribute("clientListProcessed", Boolean.TRUE);
            List<Client> clientList = clientModel.getAllClients();
            LOGGER.info("Retrieved " + clientList.size() + " clients");
            request.setAttribute("clientList", clientList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/clientList.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving client list", e);
            throw e; // Rethrow to be caught by the outer try-catch
        }
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/addClient.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Client client = clientModel.getClientById(id);

            if (client != null) {
                request.setAttribute("client", client);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/editClient.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Client with ID " + id + " not found");
                response.sendRedirect(request.getContextPath() + "clients/list");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid client ID format", e);
            request.setAttribute("errorMessage", "Invalid client ID format");
            response.sendRedirect(request.getContextPath() + "clients/list");
        }
    }

    private void viewClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Client client = clientModel.getClientById(id);

            if (client != null) {
                request.setAttribute("client", client);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/viewClient.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Client with ID " + id + " not found");
                response.sendRedirect(request.getContextPath() + "clients/list");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid client ID format", e);
            request.setAttribute("errorMessage", "Invalid client ID format");
            response.sendRedirect(request.getContextPath() + "clients/list");
        }
    }

    private void insertClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        try {
            int phone = Integer.parseInt(request.getParameter("phone"));
            String email = request.getParameter("email");
            String localisation = request.getParameter("localisation");

            Client newClient = new Client(firstName, lastName, phone, email, localisation);
            boolean success = clientModel.addClient(newClient);
            
            if (success) {
                LOGGER.info("Successfully added new client: " + firstName + " " + lastName);
                response.sendRedirect(request.getContextPath() + "/clients/list");
            } else {
                request.setAttribute("errorMessage", "Failed to add client to database");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addClient.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid phone number format", e);
            request.setAttribute("errorMessage", "Invalid phone number format");
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addClient.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    private void updateClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            int phone = Integer.parseInt(request.getParameter("phone"));
            String email = request.getParameter("email");
            String localisation = request.getParameter("localisation");

            Client client = new Client(id, firstName, lastName, phone, email, localisation);
            boolean success = clientModel.updateClient(client);

            if (success) {
                LOGGER.info("Successfully updated client ID " + id);
                response.sendRedirect(request.getContextPath() + "/clients/list");
            } else {
                request.setAttribute("errorMessage", "Failed to update client");
                request.setAttribute("client", client);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/editClient.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid ID or phone number format", e);
            request.setAttribute("errorMessage", "Invalid ID or phone number format");
            response.sendRedirect(request.getContextPath() + "/clients/list");
        }
    }

    private void deleteClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            CommandeModel commandeModel = new CommandeModel();
            ReservationModel reservationModel = new ReservationModel();
            List<Reservation> clientReservations = reservationModel.getReservationsByClientId(id);
            for (Reservation reservation : clientReservations) {
                reservationModel.deleteReservation(reservation.getIdClient(), reservation.getIdTab());
            }
            List<Commande> clientCommandes = commandeModel.getCommandesByClientId(id);
            for (Commande commande : clientCommandes) {
                commandeModel.deleteCommande(commande.getIdCmd());
    }
            boolean success = clientModel.deleteClient(id);

            if (success) {
                LOGGER.info("Successfully deleted client ID " + id);
            } else {
                LOGGER.warning("Failed to delete client ID " + id);
                request.setAttribute("errorMessage", "Failed to delete client. It may be referenced by reservations or orders.");
            }
            
            response.sendRedirect(request.getContextPath() + "/clients/list");
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid client ID format", e);
            request.setAttribute("errorMessage", "Invalid client ID format");
            response.sendRedirect(request.getContextPath() + "/clients/list");
        }
    }
}