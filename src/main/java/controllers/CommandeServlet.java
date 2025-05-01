package controllers;

import entities.Client;
import entities.Commande;
import models.ClientModel;
import models.CommandeModel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/commandes/*")
public class CommandeServlet extends HttpServlet {

    private CommandeModel commandeModel;
    private ClientModel clientModel;

    public void init() {
        commandeModel = new CommandeModel();
        clientModel = new ClientModel();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listCommandes(request, response);
                break;
            case "/new":
                showNewForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteCommande(request, response);
                break;
            case "/view":
                viewCommande(request, response);
                break;
            case "/client":
                listClientCommandes(request, response);
                break;
            default:
                listCommandes(request, response);
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
                insertCommande(request, response);
                break;
            case "/update":
                updateCommande(request, response);
                break;
            default:
                listCommandes(request, response);
                break;
        }
    }

    private void listCommandes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Commande> commandeList = commandeModel.getAllCommandes();
        request.setAttribute("commandeList", commandeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/commandes/list.jsp");
        dispatcher.forward(request, response);
    }

    private void listClientCommandes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        List<Commande> commandeList = commandeModel.getCommandesByClientId(clientId);
        Client client = clientModel.getClientById(clientId);

        request.setAttribute("commandeList", commandeList);
        request.setAttribute("client", client);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/commandes/client-commandes.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all clients for dropdown
        List<Client> clientList = clientModel.getAllClients();
        request.setAttribute("clientList", clientList);

        // Pre-select client if coming from client page
        String clientId = request.getParameter("clientId");
        if (clientId != null && !clientId.isEmpty()) {
            request.setAttribute("selectedClientId", Integer.parseInt(clientId));
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/commandes/add.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Commande commande = commandeModel.getCommandeById(id);

        if (commande != null) {
            // Get all clients for dropdown
            List<Client> clientList = clientModel.getAllClients();
            request.setAttribute("clientList", clientList);
            request.setAttribute("commande", commande);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/commandes/edit.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/commandes/list");
        }
    }

    private void viewCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Commande commande = commandeModel.getCommandeById(id);

        if (commande != null) {
            request.setAttribute("commande", commande);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/commandes/view.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/commandes/list");
        }
    }

    private void insertCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        String dateStr = request.getParameter("date");

        Date date = Date.valueOf(dateStr);

        Commande newCommande = new Commande(clientId, date);

        // Set the associated client
        Client client = clientModel.getClientById(clientId);
        newCommande.setClient(client);

        commandeModel.addCommande(newCommande);

        // Redirect to client commandes if came from there
        String redirect = request.getParameter("fromClient");
        if (redirect != null && redirect.equals("true")) {
            response.sendRedirect(request.getContextPath() + "/commandes/client?clientId=" + clientId);
        } else {
            response.sendRedirect(request.getContextPath() + "/commandes/list");
        }
    }

    private void updateCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        String dateStr = request.getParameter("date");

        Date date = Date.valueOf(dateStr);

        Commande commande = new Commande(id, clientId, date);

        // Set the associated client
        Client client = clientModel.getClientById(clientId);
        commande.setClient(client);

        commandeModel.updateCommande(commande);

        // Redirect to client commandes if came from there
        String redirect = request.getParameter("fromClient");
        if (redirect != null && redirect.equals("true")) {
            response.sendRedirect(request.getContextPath() + "/commandes/client?clientId=" + clientId);
        } else {
            response.sendRedirect(request.getContextPath() + "/commandes/list");
        }
    }

    private void deleteCommande(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Commande commande = commandeModel.getCommandeById(id);
        int clientId = commande.getIdClient();

        commandeModel.deleteCommande(id);

        // Redirect to client commandes if came from there
        String redirect = request.getParameter("fromClient");
        if (redirect != null && redirect.equals("true")) {
            response.sendRedirect(request.getContextPath() + "/commandes/client?clientId=" + clientId);
        } else {
            response.sendRedirect(request.getContextPath() + "/commandes/list");
        }
    }
}