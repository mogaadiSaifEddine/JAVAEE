package entities;

import java.sql.Date;

public class Commande {
    private int idCmd;
    private int idClient;
    private Date dateCmd;
    private Client client; // Reference to the associated client

    // Default constructor
    public Commande() {
    }

    // Constructor without ID (for insertion)
    public Commande(int idClient, Date dateCmd) {
        this.idClient = idClient;
        this.dateCmd = dateCmd;
    }

    // Constructor with all fields (for retrieval)
    public Commande(int idCmd, int idClient, Date dateCmd) {
        this.idCmd = idCmd;
        this.idClient = idClient;
        this.dateCmd = dateCmd;
    }

    // Getters and Setters
    public int getIdCmd() {
        return idCmd;
    }

    public void setIdCmd(int idCmd) {
        this.idCmd = idCmd;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Date getDateCmd() {
        return dateCmd;
    }

    public void setDateCmd(Date dateCmd) {
        this.dateCmd = dateCmd;
    }

    // Safe getter for Client - returns only basic client info to prevent circular references
    public Client getClient() {
        if (client == null) return null;
        // Return a detached copy with only basic fields to break circular reference
        Client clientInfo = new Client();
        clientInfo.setIdClient(client.getIdClient());
        clientInfo.setFNameClient(client.getFNameClient());
        clientInfo.setLNameClient(client.getLNameClient());
        clientInfo.setPhoneClient(client.getPhoneClient());
        clientInfo.setEmailClient(client.getEmailClient());
        clientInfo.setLocalisationClient(client.getLocalisationClient());
        // Important: do not include any collections or back-references
        return clientInfo;
    }

    // Method to set the client but keep ID in sync
    public void setClient(Client client) {
        this.client = client;
        if (client != null) {
            this.idClient = client.getIdClient();
        }
    }

    // This method is kept for internal model use only (database operations)
    // It should not be used in JSP views to prevent circular references
    public Client getFullClient() {
        return this.client;
    }

    @Override
    public String toString() {
        return "Commande [idCmd=" + idCmd + ", idClient=" + idClient + ", dateCmd=" + dateCmd + "]";
    }
}