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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        if (client != null) {
            this.idClient = client.getIdClient();
        }
    }

    public MyTable getTable() {
        return table;
    }

    public void setTable(MyTable table) {
        this.table = table;
        if (table != null) {
            this.idTab = table.getIdTab();
        }
    }

    @Override
    public String toString() {
        return "Reservation [idClient=" + idClient + ", idTab=" + idTab +
                ", dateReservation=" + dateReservation + ", heureDebutReservation=" + heureDebutReservation +
                ", heureFinReservation=" + heureFinReservation + "]";
    }
}