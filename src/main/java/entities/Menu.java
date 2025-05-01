package entities;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private int idMenu;
    private List<Plat> plats;

    // Default constructor
    public Menu() {
        this.plats = new ArrayList<>();
    }

    // Constructor with ID
    public Menu(int idMenu) {
        this.idMenu = idMenu;
        this.plats = new ArrayList<>();
    }

    // Getters and Setters
    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public List<Plat> getPlats() {
        return plats;
    }

    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }

    // Add a plat to the menu
    public void addPlat(Plat plat, int quantity) {
        this.plats.add(plat);
        // In a real application, you would also store the quantity in the PlatMenu junction table
    }

    @Override
    public String toString() {
        return "Menu [idMenu=" + idMenu + ", plats=" + plats + "]";
    }
}