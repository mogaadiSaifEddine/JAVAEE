package entities;

public class Plat {
    private int idPlat;
    private float prixPlat;
    private String discPlat;
    private String namePlat;
    private String avisPlat;

    // Default constructor
    public Plat() {
    }

    // Constructor with all fields except ID (for insertion)
    public Plat(float prixPlat, String discPlat, String namePlat, String avisPlat) {
        this.prixPlat = prixPlat;
        this.discPlat = discPlat;
        this.namePlat = namePlat;
        this.avisPlat = avisPlat;
    }

    // Constructor with all fields (for retrieval)
    public Plat(int idPlat, float prixPlat, String discPlat, String namePlat, String avisPlat) {
        this.idPlat = idPlat;
        this.prixPlat = prixPlat;
        this.discPlat = discPlat;
        this.namePlat = namePlat;
        this.avisPlat = avisPlat;
    }

    // Getters and Setters
    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public float getPrixPlat() {
        return prixPlat;
    }

    public void setPrixPlat(float prixPlat) {
        this.prixPlat = prixPlat;
    }

    public String getDiscPlat() {
        return discPlat;
    }

    public void setDiscPlat(String discPlat) {
        this.discPlat = discPlat;
    }

    public String getNamePlat() {
        return namePlat;
    }

    public void setNamePlat(String namePlat) {
        this.namePlat = namePlat;
    }

    public String getAvisPlat() {
        return avisPlat;
    }

    public void setAvisPlat(String avisPlat) {
        this.avisPlat = avisPlat;
    }

    @Override
    public String toString() {
        return "Plat [idPlat=" + idPlat + ", prixPlat=" + prixPlat + ", discPlat=" + discPlat +
                ", namePlat=" + namePlat + ", avisPlat=" + avisPlat + "]";
    }
}