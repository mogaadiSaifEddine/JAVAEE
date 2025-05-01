package entities;

public class MyTable {
    private int idTab;
    private int nbrPlace;
    private String localisationTable;

    // Default constructor
    public MyTable() {
    }

    // Constructor without ID (for insertion)
    public MyTable(int nbrPlace, String localisationTable) {
        this.nbrPlace = nbrPlace;
        this.localisationTable = localisationTable;
    }

    // Constructor with all fields (for retrieval)
    public MyTable(int idTab, int nbrPlace, String localisationTable) {
        this.idTab = idTab;
        this.nbrPlace = nbrPlace;
        this.localisationTable = localisationTable;
    }

    // Getters and Setters
    public int getIdTab() {
        return idTab;
    }

    public void setIdTab(int idTab) {
        this.idTab = idTab;
    }

    public int getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(int nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    public String getLocalisationTable() {
        return localisationTable;
    }

    public void setLocalisationTable(String localisationTable) {
        this.localisationTable = localisationTable;
    }

    @Override
    public String toString() {
        return "MyTable [idTab=" + idTab + ", nbrPlace=" + nbrPlace + ", localisationTable=" + localisationTable + "]";
    }
}