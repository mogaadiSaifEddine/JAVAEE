package entities;

public class PlatMenu {
    private int idPlat;
    private int idMenu;
    private int qtPlat;

    // References to associated entities
    private Plat plat;
    private Menu menu;

    // Default constructor
    public PlatMenu() {
    }

    // Constructor with all fields
    public PlatMenu(int idPlat, int idMenu, int qtPlat) {
        this.idPlat = idPlat;
        this.idMenu = idMenu;
        this.qtPlat = qtPlat;
    }

    // Getters and Setters
    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getQtPlat() {
        return qtPlat;
    }

    public void setQtPlat(int qtPlat) {
        this.qtPlat = qtPlat;
    }

    // Safe getter for Plat - returns only basic plat info to prevent circular references
    public Plat getPlat() {
        if (plat == null) return null;
        // Return a detached copy with only basic fields to break circular reference
        Plat platInfo = new Plat();
        platInfo.setIdPlat(plat.getIdPlat());
        platInfo.setPrixPlat(plat.getPrixPlat());
        platInfo.setDiscPlat(plat.getDiscPlat());
        platInfo.setNamePlat(plat.getNamePlat());
        platInfo.setAvisPlat(plat.getAvisPlat());
        // Important: do not include any collections or back-references
        return platInfo;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
        if (plat != null) {
            this.idPlat = plat.getIdPlat();
        }
    }

    // Safe getter for Menu - returns only basic menu info to prevent circular references
    public Menu getMenu() {
        if (menu == null) return null;
        // Return a detached copy with only basic fields to break circular reference
        Menu menuInfo = new Menu();
        menuInfo.setIdMenu(menu.getIdMenu());
        // Important: do not include the plats list to break circular reference
        return menuInfo;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        if (menu != null) {
            this.idMenu = menu.getIdMenu();
        }
    }

    // These methods are kept for internal model use only (database operations)
    // They should not be used in JSP views to prevent circular references
    public Plat getFullPlat() {
        return this.plat;
    }
    
    public Menu getFullMenu() {
        return this.menu;
    }

    @Override
    public String toString() {
        return "PlatMenu [idPlat=" + idPlat + ", idMenu=" + idMenu + ", qtPlat=" + qtPlat + "]";
    }
}