package entities;

import java.sql.Date;
import java.sql.Time;

public class Reservation {
    private int idClient;
    private int idTab;
    private Date dateReservation;
    private Time heureDebutReservation;
    private Time heureFinReservation;

    // References to associated entities
    private Client client;
    private MyTable table;

    // Default constructor
    public Reservation() {
    }

    // Constructor with required fields
    public Reservation(int idClient, int idTab, Date dateReservation, Time heureDebutReservation, Time heureFinReservation) {
        this.idClient = idClient;
        this.idTab = idTab;
        this.dateReservation = dateReservation;
        this.heureDebutReservation = heureDebutReservation;
        this.heureFinReservation = heureFinReservation;
    }

    // Getters and Setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdTab() {
        return idTab;
    }

    public void setIdTab(int idTab) {
        this.idTab = idTab;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Time getHeureDebutReservation() {
        return heureDebutReservation;
    }

    public void setHeureDebutReservation(Time heureDebutReservation) {
        this.heureDebutReservation = heureDebutReservation;
    }

    public Time getHeureFinReservation() {
        return heureFinReservation;
    }

    public void setHeureFinReservation(Time heureFinReservation) {
        this.heureFinReservation = heureFinReservation;
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

    // Safe getter for Table - returns only basic table info to prevent circular references
    public MyTable getTable() {
        if (table == null) return null;
        // Return a detached copy with only basic fields to break circular reference
        MyTable tableInfo = new MyTable();
        tableInfo.setIdTab(table.getIdTab());
        tableInfo.setNbrPlace(table.getNbrPlace());
        tableInfo.setLocalisationTable(table.getLocalisationTable());
        // Important: do not include any collections or back-references
        return tableInfo;
    }

    // Method to set the table but keep ID in sync
    public void setTable(MyTable table) {
        this.table = table;
        if (table != null) {
            this.idTab = table.getIdTab();
        }
    }

    // This method is kept for internal model use only (database operations)
    // It should not be used in JSP views to prevent circular references
    public Client getFullClient() {
        return this.client;
    }

    // This method is kept for internal model use only (database operations)
    // It should not be used in JSP views to prevent circular references
    public MyTable getFullTable() {
        return this.table;
    }

    @Override
    public String toString() {
        return "Reservation [idClient=" + idClient + ", idTab=" + idTab +
                ", dateReservation=" + dateReservation + ", heureDebutReservation=" + heureDebutReservation +
                ", heureFinReservation=" + heureFinReservation + "]";
    }
}