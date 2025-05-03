package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    private int idMenu;
    private String nameMenu ;

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

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

    // Safe getter for Plats - returns only basic plat info to prevent circular references
    public List<Plat> getPlats() {
        if (plats == null) return new ArrayList<>();

        // Return a list of detached copies with only basic fields to break circular reference
        return plats.stream().map(plat -> {
            Plat platInfo = new Plat();
            platInfo.setIdPlat(plat.getIdPlat());
            platInfo.setNamePlat(plat.getNamePlat());
            platInfo.setPrixPlat(plat.getPrixPlat());
            platInfo.setDiscPlat(plat.getDiscPlat());
            platInfo.setAvisPlat(plat.getAvisPlat());
            // Important: do not include any collections or back-references
            return platInfo;
        }).collect(Collectors.toList());
    }

    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }

    // Add a plat to the menu
    public void addPlat(Plat plat, int quantity) {
        this.plats.add(plat);
        // In a real application, you would also store the quantity in the PlatMenu junction table
    }

    // This method is kept for internal model use only (database operations)
    // It should not be used in JSP views to prevent circular references
    public List<Plat> getFullPlats() {
        return this.plats;
    }

    @Override
    public String toString() {
        return "Menu [idMenu=" + idMenu + ", plats=" + plats + "]";
    }
}