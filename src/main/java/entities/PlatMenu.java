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

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
        if (plat != null) {
            this.idPlat = plat.getIdPlat();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        if (menu != null) {
            this.idMenu = menu.getIdMenu();
        }
    }

    @Override
    public String toString() {
        return "PlatMenu [idPlat=" + idPlat + ", idMenu=" + idMenu + ", qtPlat=" + qtPlat + "]";
    }
}