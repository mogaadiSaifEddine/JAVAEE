package controllers;

import entities.MyTable;
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

@WebServlet("/tables/*")
public class TableServlet extends HttpServlet {

    private TableModel tableModel;

    public void init() {
        tableModel = new TableModel();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listTables(request, response);
                break;
            case "/new":
                showNewForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteTable(request, response);
                break;
            case "/view":
                viewTable(request, response);
                break;
            case "/available":
                listAvailableTables(request, response);
                break;
            default:
                listTables(request, response);
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
                insertTable(request, response);
                break;
            case "/update":
                updateTable(request, response);
                break;
            case "/search":
                searchAvailableTables(request, response);
                break;
            default:
                listTables(request, response);
                break;
        }
    }

    private void listTables(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MyTable> tableList = tableModel.getAllTables();
        request.setAttribute("tableList", tableList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/tables/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/tables/add.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        MyTable table = tableModel.getTableById(id);

        if (table != null) {
            request.setAttribute("table", table);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/tables/edit.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/tables/list");
        }
    }

    private void viewTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        MyTable table = tableModel.getTableById(id);

        if (table != null) {
            request.setAttribute("table", table);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/tables/view.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/tables/list");
        }
    }

    private void listAvailableTables(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/tables/available.jsp");
        dispatcher.forward(request, response);
    }

    private void searchAvailableTables(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dateStr = request.getParameter("date");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");

        Date date = Date.valueOf(dateStr);
        Time startTime = Time.valueOf(startTimeStr + ":00");
        Time endTime = Time.valueOf(endTimeStr + ":00");

        List<MyTable> availableTables = tableModel.getAvailableTables(date, startTime, endTime);

        request.setAttribute("tableList", availableTables);
        request.setAttribute("date", dateStr);
        request.setAttribute("startTime", startTimeStr);
        request.setAttribute("endTime", endTimeStr);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/tables/available-results.jsp");
        dispatcher.forward(request, response);
    }

    private void insertTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int nbrPlace = Integer.parseInt(request.getParameter("nbrPlace"));
        String localisation = request.getParameter("localisation");

        MyTable newTable = new MyTable(nbrPlace, localisation);
        tableModel.addTable(newTable);

        response.sendRedirect(request.getContextPath() + "/tables/list");
    }

    private void updateTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int nbrPlace = Integer.parseInt(request.getParameter("nbrPlace"));
        String localisation = request.getParameter("localisation");

        MyTable table = new MyTable(id, nbrPlace, localisation);
        tableModel.updateTable(table);

        response.sendRedirect(request.getContextPath() + "/tables/list");
    }

    private void deleteTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        tableModel.deleteTable(id);

        response.sendRedirect(request.getContextPath() + "/tables/list");
    }
}