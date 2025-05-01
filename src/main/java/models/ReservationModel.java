package models;

import entities.Client;
import entities.MyTable;
import entities.Reservation;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationModel {

    // Create a new reservation
    public boolean addReservation(Reservation reservation) {
        String sql = "INSERT INTO Reservation (id_client, id_tab, date_reservation, heure_debut_reservation, heure_fin_reservation) " +
                "VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, reservation.getIdClient());
            pstmt.setInt(2, reservation.getIdTab());
            pstmt.setDate(3, reservation.getDateReservation());
            pstmt.setTime(4, reservation.getHeureDebutReservation());
            pstmt.setTime(5, reservation.getHeureFinReservation());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        String sql = "SELECT r.*, c.f_name_client, c.l_name_client, c.phone_client, c.email_client, c.localisation_client, " +
                "t.nbr_place, t.localisation_table " +
                "FROM Reservation r " +
                "JOIN client c ON r.id_client = c.id_client " +
                "JOIN myTable t ON r.id_tab = t.id_tab";

        List<Reservation> reservationList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setIdClient(rs.getInt("id_client"));
                reservation.setIdTab(rs.getInt("id_tab"));
                reservation.setDateReservation(rs.getDate("date_reservation"));
                reservation.setHeureDebutReservation(rs.getTime("heure_debut_reservation"));
                reservation.setHeureFinReservation(rs.getTime("heure_fin_reservation"));

                // Create and set client
                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setFNameClient(rs.getString("f_name_client"));
                client.setLNameClient(rs.getString("l_name_client"));
                client.setPhoneClient(rs.getInt("phone_client"));
                client.setEmailClient(rs.getString("email_client"));
                client.setLocalisationClient(rs.getString("localisation_client"));
                reservation.setClient(client);

                // Create and set table
                MyTable table = new MyTable();
                table.setIdTab(rs.getInt("id_tab"));
                table.setNbrPlace(rs.getInt("nbr_place"));
                table.setLocalisationTable(rs.getString("localisation_table"));
                reservation.setTable(table);

                reservationList.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return reservationList;
    }

    // Get reservations by client ID
    public List<Reservation> getReservationsByClientId(int clientId) {
        String sql = "SELECT r.*, c.f_name_client, c.l_name_client, c.phone_client, c.email_client, c.localisation_client, " +
                "t.nbr_place, t.localisation_table " +
                "FROM Reservation r " +
                "JOIN client c ON r.id_client = c.id_client " +
                "JOIN myTable t ON r.id_tab = t.id_tab " +
                "WHERE r.id_client = ?";

        List<Reservation> reservationList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setIdClient(rs.getInt("id_client"));
                reservation.setIdTab(rs.getInt("id_tab"));
                reservation.setDateReservation(rs.getDate("date_reservation"));
                reservation.setHeureDebutReservation(rs.getTime("heure_debut_reservation"));
                reservation.setHeureFinReservation(rs.getTime("heure_fin_reservation"));

                // Create and set client
                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setFNameClient(rs.getString("f_name_client"));
                client.setLNameClient(rs.getString("l_name_client"));
                client.setPhoneClient(rs.getInt("phone_client"));
                client.setEmailClient(rs.getString("email_client"));
                client.setLocalisationClient(rs.getString("localisation_client"));
                reservation.setClient(client);

                // Create and set table
                MyTable table = new MyTable();
                table.setIdTab(rs.getInt("id_tab"));
                table.setNbrPlace(rs.getInt("nbr_place"));
                table.setLocalisationTable(rs.getString("localisation_table"));
                reservation.setTable(table);

                reservationList.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return reservationList;
    }

    // Get reservation by client ID and table ID
    public Reservation getReservation(int clientId, int tableId) {
        String sql = "SELECT r.*, c.f_name_client, c.l_name_client, c.phone_client, c.email_client, c.localisation_client, " +
                "t.nbr_place, t.localisation_table " +
                "FROM Reservation r " +
                "JOIN client c ON r.id_client = c.id_client " +
                "JOIN myTable t ON r.id_tab = t.id_tab " +
                "WHERE r.id_client = ? AND r.id_tab = ?";

        Reservation reservation = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, tableId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                reservation = new Reservation();
                reservation.setIdClient(rs.getInt("id_client"));
                reservation.setIdTab(rs.getInt("id_tab"));
                reservation.setDateReservation(rs.getDate("date_reservation"));
                reservation.setHeureDebutReservation(rs.getTime("heure_debut_reservation"));
                reservation.setHeureFinReservation(rs.getTime("heure_fin_reservation"));

                // Create and set client
                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setFNameClient(rs.getString("f_name_client"));
                client.setLNameClient(rs.getString("l_name_client"));
                client.setPhoneClient(rs.getInt("phone_client"));
                client.setEmailClient(rs.getString("email_client"));
                client.setLocalisationClient(rs.getString("localisation_client"));
                reservation.setClient(client);

                // Create and set table
                MyTable table = new MyTable();
                table.setIdTab(rs.getInt("id_tab"));
                table.setNbrPlace(rs.getInt("nbr_place"));
                table.setLocalisationTable(rs.getString("localisation_table"));
                reservation.setTable(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return reservation;
    }

    // Update an existing reservation
    public boolean updateReservation(Reservation reservation) {
        String sql = "UPDATE Reservation SET date_reservation = ?, heure_debut_reservation = ?, heure_fin_reservation = ? " +
                "WHERE id_client = ? AND id_tab = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setDate(1, reservation.getDateReservation());
            pstmt.setTime(2, reservation.getHeureDebutReservation());
            pstmt.setTime(3, reservation.getHeureFinReservation());
            pstmt.setInt(4, reservation.getIdClient());
            pstmt.setInt(5, reservation.getIdTab());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // Delete a reservation
    public boolean deleteReservation(int clientId, int tableId) {
        String sql = "DELETE FROM Reservation WHERE id_client = ? AND id_tab = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, tableId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // Helper method to close resources
    private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) DBUtil.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}