package entities;

public class Client {
    private int idClient;
    private String fNameClient;
    private String lNameClient;
    private int phoneClient;
    private String emailClient;
    private String localisationClient;

    // Default constructor
    public Client() {
    }

    // Constructor without ID (for insertion)
    public Client(String fNameClient, String lNameClient, int phoneClient, String emailClient, String localisationClient) {
        this.fNameClient = fNameClient;
        this.lNameClient = lNameClient;
        this.phoneClient = phoneClient;
        this.emailClient = emailClient;
        this.localisationClient = localisationClient;
    }

    // Constructor with all fields (for retrieval)
    public Client(int idClient, String fNameClient, String lNameClient, int phoneClient, String emailClient, String localisationClient) {
        this.idClient = idClient;
        this.fNameClient = fNameClient;
        this.lNameClient = lNameClient;
        this.phoneClient = phoneClient;
        this.emailClient = emailClient;
        this.localisationClient = localisationClient;
    }

    // Getters and Setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getFNameClient() {
        return fNameClient;
    }

    public void setFNameClient(String fNameClient) {
        this.fNameClient = fNameClient;
    }

    public String getLNameClient() {
        return lNameClient;
    }

    public void setLNameClient(String lNameClient) {
        this.lNameClient = lNameClient;
    }

    public int getPhoneClient() {
        return phoneClient;
    }

    public void setPhoneClient(int phoneClient) {
        this.phoneClient = phoneClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getLocalisationClient() {
        return localisationClient;
    }

    public void setLocalisationClient(String localisationClient) {
        this.localisationClient = localisationClient;
    }

    // Get full name
    public String getFullName() {
        return fNameClient + " " + lNameClient;
    }

    @Override
    public String toString() {
        return "Client [idClient=" + idClient + ", fNameClient=" + fNameClient + ", lNameClient=" + lNameClient +
                ", phoneClient=" + phoneClient + ", emailClient=" + emailClient + ", localisationClient=" + localisationClient + "]";
    }
}