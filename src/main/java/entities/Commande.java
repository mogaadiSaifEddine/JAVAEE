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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        if (client != null) {
            this.idClient = client.getIdClient();
        }
    }

    @Override
    public String toString() {
        return "Commande [idCmd=" + idCmd + ", idClient=" + idClient + ", dateCmd=" + dateCmd + "]";
    }
}