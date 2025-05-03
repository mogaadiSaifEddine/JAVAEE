package controllers;

import entities.Plat;
import models.PlatModel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PlatServlet extends HttpServlet {

    private PlatModel platModel;

    public void init() {
        platModel = new PlatModel();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listPlats(request, response);
                break;
            case "/new":
                showNewForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deletePlat(request, response);
                break;
            case "/view":
                viewPlat(request, response);
                break;
            default:
                listPlats(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("PlatServlet doPost");
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/insert":
                insertPlat(request, response);
                break;
            case "/update":
                updatePlat(request, response);
                break;
            default:
                listPlats(request, response);
                break;
        }
    }

    private void listPlats(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("platListProcessed") != null) {
            return;
        }
        System.out.println("Retrieving plat list");
        List<Plat> platList = platModel.getAllPlats();
        request.setAttribute("platList", platList);
        request.setAttribute("platListProcessed", Boolean.TRUE);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/paltList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/addPlat.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Plat plat = platModel.getPlatById(id);

        if (plat != null) {
            request.setAttribute("plat", plat);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/plats/edit.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/plats/list");
        }
    }

    private void viewPlat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Plat plat = platModel.getPlatById(id);

        if (plat != null) {
            request.setAttribute("plat", plat);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/plats/view.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/plats/list");
        }
    }

    private void insertPlat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        String description = request.getParameter("description");
        String avis = request.getParameter("avis");

        Plat newPlat = new Plat(price, description, name, avis);
        platModel.addPlat(newPlat);

        response.sendRedirect(request.getContextPath() + "/plats/list");
    }

    private void updatePlat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        String description = request.getParameter("description");
        String avis = request.getParameter("avis");

        Plat plat = new Plat(id, price, description, name, avis);
        platModel.updatePlat(plat);

        response.sendRedirect(request.getContextPath() + "/paltList.jsp");
    }

    private void deletePlat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        platModel.deletePlat(id);

        response.sendRedirect(request.getContextPath() + "/plats/list");
    }
}