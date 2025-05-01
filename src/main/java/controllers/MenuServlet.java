package controllers;

import entities.Menu;
import entities.Plat;
import models.MenuModel;
import models.PlatModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/menus/*")
public class MenuServlet extends HttpServlet {

    private MenuModel menuModel;
    private PlatModel platModel;

    public void init() {
        menuModel = new MenuModel();
        platModel = new PlatModel();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        switch (action) {
            case "/list":
                listMenus(request, response);
                break;
            case "/new":
                showNewForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteMenu(request, response);
                break;
            case "/view":
                viewMenu(request, response);
                break;
            default:
                listMenus(request, response);
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
                insertMenu(request, response);
                break;
            case "/update":
                updateMenu(request, response);
                break;
            default:
                listMenus(request, response);
                break;
        }
    }

    private void listMenus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Menu> menuList = menuModel.getAllMenus();
        request.setAttribute("menuList", menuList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/menus/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all plats for checkboxes
        List<Plat> platList = platModel.getAllPlats();
        request.setAttribute("platList", platList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/menus/add.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Menu menu = menuModel.getMenuById(id);

        if (menu != null) {
            // Get all plats for checkboxes
            List<Plat> allPlats = platModel.getAllPlats();
            request.setAttribute("platList", allPlats);

            // Get plats in this menu
            List<Plat> menuPlats = menu.getPlats();

            // Create a list of plat IDs that are already in the menu for pre-selecting checkboxes
            List<Integer> selectedPlatIds = menuPlats.stream()
                    .map(Plat::getIdPlat)
                    .collect(Collectors.toList());

            request.setAttribute("menu", menu);
            request.setAttribute("selectedPlatIds", selectedPlatIds);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/menus/edit.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/menus/list");
        }
    }

    private void viewMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Menu menu = menuModel.getMenuById(id);

        if (menu != null) {
            request.setAttribute("menu", menu);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/menus/view.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/menus/list");
        }
    }

    private void insertMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Menu newMenu = new Menu();

        // Get the selected plat IDs from form
        String[] platIds = request.getParameterValues("platIds");

        if (platIds != null && platIds.length > 0) {
            List<Plat> plats = new ArrayList<>();

            for (String platIdStr : platIds) {
                int platId = Integer.parseInt(platIdStr);
                Plat plat = platModel.getPlatById(platId);
                if (plat != null) {
                    plats.add(plat);
                }
            }

            newMenu.setPlats(plats);
        }

        menuModel.addMenu(newMenu);

        response.sendRedirect(request.getContextPath() + "/menus/list");
    }

    private void updateMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        // First, delete the existing menu
        menuModel.deleteMenu(id);

        // Then create a new menu with the same ID (simulating an update)
        Menu menu = new Menu(id);

        // Get the selected plat IDs from form
        String[] platIds = request.getParameterValues("platIds");

        if (platIds != null && platIds.length > 0) {
            List<Plat> plats = new ArrayList<>();

            for (String platIdStr : platIds) {
                int platId = Integer.parseInt(platIdStr);
                Plat plat = platModel.getPlatById(platId);
                if (plat != null) {
                    plats.add(plat);
                }
            }

            menu.setPlats(plats);
        }

        menuModel.addMenu(menu);

        response.sendRedirect(request.getContextPath() + "/menus/list");
    }

    private void deleteMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        menuModel.deleteMenu(id);

        response.sendRedirect(request.getContextPath() + "/menus/list");
    }
}