package models;

import entities.Client;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {

    // Create a new client
    public boolean addClient(Client client) {
        String sql = "INSERT INTO client (f_name_client, l_name_client, phone_client, email_client, localisation_client) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, client.getFNameClient());
            pstmt.setString(2, client.getLNameClient());
            pstmt.setInt(3, client.getPhoneClient());
            pstmt.setString(4, client.getEmailClient());
            pstmt.setString(5, client.getLocalisationClient());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    client.setIdClient(generatedKeys.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // Get all clients
    public List<Client> getAllClients() {
        String sql = "SELECT * FROM client";
        List<Client> clientList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setFNameClient(rs.getString("f_name_client"));
                client.setLNameClient(rs.getString("l_name_client"));
                client.setPhoneClient(rs.getInt("phone_client"));
                client.setEmailClient(rs.getString("email_client"));
                client.setLocalisationClient(rs.getString("localisation_client"));

                clientList.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return clientList;
    }

    // Get client by ID
    public Client getClientById(int id) {
        String sql = "SELECT * FROM client WHERE id_client = ?";
        Client client = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setFNameClient(rs.getString("f_name_client"));
                client.setLNameClient(rs.getString("l_name_client"));
                client.setPhoneClient(rs.getInt("phone_client"));
                client.setEmailClient(rs.getString("email_client"));
                client.setLocalisationClient(rs.getString("localisation_client"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return client;
    }

    // Update an existing client
    public boolean updateClient(Client client) {
        String sql = "UPDATE client SET f_name_client = ?, l_name_client = ?, phone_client = ?, email_client = ?, localisation_client = ? WHERE id_client = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, client.getFNameClient());
            pstmt.setString(2, client.getLNameClient());
            pstmt.setInt(3, client.getPhoneClient());
            pstmt.setString(4, client.getEmailClient());
            pstmt.setString(5, client.getLocalisationClient());
            pstmt.setInt(6, client.getIdClient());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // Delete a client
    public boolean deleteClient(int id) {
        String sql = "DELETE FROM client WHERE id_client = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

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