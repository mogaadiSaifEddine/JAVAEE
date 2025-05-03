package controllers;

import entities.Client;
import entities.MyTable;
import entities.Reservation;
import models.ClientModel;
import models.ReservationModel;
import models.TableModel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ReservationServlet extends HttpServlet {

    private ReservationModel reservationModel;
    private ClientModel clientModel;
    private TableModel tableModel;

    public void init() {
        reservationModel = new ReservationModel();
        clientModel = new ClientModel();
        tableModel = new TableModel();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listReservations(request, response);
                break;
            case "/new":
                showNewForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteReservation(request, response);
                break;
            case "/view":
                viewReservation(request, response);
                break;
            case "/client":
                listClientReservations(request, response);
                break;
            default:
                listReservations(request, response);
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
                insertReservation(request, response);
                break;
            case "/update":
                updateReservation(request, response);
                break;
            default:
                listReservations(request, response);
                break;
        }
    }

    private void listReservations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Reservation> reservationList = reservationModel.getAllReservations();
        request.setAttribute("reservationList", reservationList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/reservationsList.jsp");
        dispatcher.forward(request, response);
    }

    private void listClientReservations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        List<Reservation> reservationList = reservationModel.getReservationsByClientId(clientId);
        Client client = clientModel.getClientById(clientId);

        request.setAttribute("reservationList", reservationList);
        request.setAttribute("client", client);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/reservations/client-reservations.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all clients and tables for dropdowns
        List<Client> clientList = clientModel.getAllClients();
        List<MyTable> tableList = tableModel.getAllTables();

        request.setAttribute("clientList", clientList);
        request.setAttribute("tableList", tableList);

        // Pre-select client if coming from client page
        String clientId = request.getParameter("clientId");
        if (clientId != null && !clientId.isEmpty()) {
            request.setAttribute("selectedClientId", Integer.parseInt(clientId));
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/addREservation.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        int tableId = Integer.parseInt(request.getParameter("tableId"));

        Reservation reservation = reservationModel.getReservation(clientId, tableId);

        if (reservation != null) {
            // Get all clients and tables for dropdowns
            List<Client> clientList = clientModel.getAllClients();
            List<MyTable> tableList = tableModel.getAllTables();

            request.setAttribute("clientList", clientList);
            request.setAttribute("tableList", tableList);
            request.setAttribute("reservation", reservation);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/reservations/edit.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/reservations/list");
        }
    }

    private void viewReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        int tableId = Integer.parseInt(request.getParameter("tableId"));

        Reservation reservation = reservationModel.getReservation(clientId, tableId);

        if (reservation != null) {
            request.setAttribute("reservation", reservation);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/reservations/view.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/reservations/list");
        }
    }

    private void insertReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        int tableId = Integer.parseInt(request.getParameter("tableId"));
        String dateStr = request.getParameter("date");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");

        Date date = Date.valueOf(dateStr);
        Time startTime = Time.valueOf(startTimeStr + ":00");
        Time endTime = Time.valueOf(endTimeStr + ":00");

        Reservation newReservation = new Reservation(clientId, tableId, date, startTime, endTime);

        // Set the associated entities
        Client client = clientModel.getClientById(clientId);
        MyTable table = tableModel.getTableById(tableId);
        newReservation.setClient(client);
        newReservation.setTable(table);

        reservationModel.addReservation(newReservation);

        // Redirect to client reservations if came from there
        String redirect = request.getParameter("fromClient");
        if (redirect != null && redirect.equals("true")) {
            response.sendRedirect(request.getContextPath() + "/reservations/client?clientId=" + clientId);
        } else {
            response.sendRedirect(request.getContextPath() + "/reservations/list");
        }
    }

    private void updateReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        int tableId = Integer.parseInt(request.getParameter("tableId"));
        String dateStr = request.getParameter("date");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");

        Date date = Date.valueOf(dateStr);
        Time startTime = Time.valueOf(startTimeStr + ":00");
        Time endTime = Time.valueOf(endTimeStr + ":00");

        Reservation reservation = new Reservation(clientId, tableId, date, startTime, endTime);

        // Set the associated entities
        Client client = clientModel.getClientById(clientId);
        MyTable table = tableModel.getTableById(tableId);
        reservation.setClient(client);
        reservation.setTable(table);

        reservationModel.updateReservation(reservation);

        // Redirect to client reservations if came from there
        String redirect = request.getParameter("fromClient");
        if (redirect != null && redirect.equals("true")) {
            response.sendRedirect(request.getContextPath() + "/reservations/client?clientId=" + clientId);
        } else {
            response.sendRedirect(request.getContextPath() + "/reservations/list");
        }
    }

    private void deleteReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));
        int tableId = Integer.parseInt(request.getParameter("tableId"));

        reservationModel.deleteReservation(clientId, tableId);

        // Redirect to client reservations if came from there
        String redirect = request.getParameter("fromClient");
        if (redirect != null && redirect.equals("true")) {
            response.sendRedirect(request.getContextPath() + "/reservations/client?clientId=" + clientId);
        } else {
            response.sendRedirect(request.getContextPath() + "/reservations/list");
        }
    }
}