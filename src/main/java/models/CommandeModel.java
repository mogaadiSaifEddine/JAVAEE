package models;

import entities.Client;
import entities.Commande;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeModel {

    // Create a new commande
    public boolean addCommande(Commande commande) {
        String sql = "INSERT INTO Commande (id_client, date_cmd) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, commande.getIdClient());
            pstmt.setDate(2, commande.getDateCmd());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    commande.setIdCmd(generatedKeys.getInt(1));
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

    // Get all commandes
    public List<Commande> getAllCommandes() {
        String sql = "SELECT c.*, cl.f_name_client, cl.l_name_client, cl.phone_client, cl.email_client, cl.localisation_client " +
                "FROM Commande c " +
                "JOIN client cl ON c.id_client = cl.id_client";

        List<Commande> commandeList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setIdCmd(rs.getInt("id_cmd"));
                commande.setIdClient(rs.getInt("id_client"));
                commande.setDateCmd(rs.getDate("date_cmd"));

                // Create and set client
                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setFNameClient(rs.getString("f_name_client"));
                client.setLNameClient(rs.getString("l_name_client"));
                client.setPhoneClient(rs.getInt("phone_client"));
                client.setEmailClient(rs.getString("email_client"));
                client.setLocalisationClient(rs.getString("localisation_client"));
                commande.setClient(client);

                commandeList.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return commandeList;
    }

    // Get commande by ID
    public Commande getCommandeById(int id) {
        String sql = "SELECT c.*, cl.f_name_client, cl.l_name_client, cl.phone_client, cl.email_client, cl.localisation_client " +
                "FROM Commande c " +
                "JOIN client cl ON c.id_client = cl.id_client " +
                "WHERE c.id_cmd = ?";

        Commande commande = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                commande = new Commande();
                commande.setIdCmd(rs.getInt("id_cmd"));
                commande.setIdClient(rs.getInt("id_client"));
                commande.setDateCmd(rs.getDate("date_cmd"));

                // Create and set client
                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setFNameClient(rs.getString("f_name_client"));
                client.setLNameClient(rs.getString("l_name_client"));
                client.setPhoneClient(rs.getInt("phone_client"));
                client.setEmailClient(rs.getString("email_client"));
                client.setLocalisationClient(rs.getString("localisation_client"));
                commande.setClient(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return commande;
    }

    // Get commandes by client ID
    public List<Commande> getCommandesByClientId(int clientId) {
        String sql = "SELECT c.*, cl.f_name_client, cl.l_name_client, cl.phone_client, cl.email_client, cl.localisation_client " +
                "FROM Commande c " +
                "JOIN client cl ON c.id_client = cl.id_client " +
                "WHERE c.id_client = ?";

        List<Commande> commandeList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setIdCmd(rs.getInt("id_cmd"));
                commande.setIdClient(rs.getInt("id_client"));
                commande.setDateCmd(rs.getDate("date_cmd"));

                // Create and set client
                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setFNameClient(rs.getString("f_name_client"));
                client.setLNameClient(rs.getString("l_name_client"));
                client.setPhoneClient(rs.getInt("phone_client"));
                client.setEmailClient(rs.getString("email_client"));
                client.setLocalisationClient(rs.getString("localisation_client"));
                commande.setClient(client);

                commandeList.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return commandeList;
    }

    // Update an existing commande
    public boolean updateCommande(Commande commande) {
        String sql = "UPDATE Commande SET id_client = ?, date_cmd = ? WHERE id_cmd = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, commande.getIdClient());
            pstmt.setDate(2, commande.getDateCmd());
            pstmt.setInt(3, commande.getIdCmd());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // Delete a commande
    public boolean deleteCommande(int id) {
        String sql = "DELETE FROM Commande WHERE id_cmd = ?";
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