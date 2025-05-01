package models;

import entities.Plat;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static utils.DBUtil.*;

public class PlatModel {

    // Create a new plat
    public boolean addPlat(Plat plat) {
        String sql = "INSERT INTO plat (prix_plat, disc_plat, name_plat, avis_plat) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setFloat(1, plat.getPrixPlat());
            pstmt.setString(2, plat.getDiscPlat());
            pstmt.setString(3, plat.getNamePlat());
            pstmt.setString(4, plat.getAvisPlat());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    plat.setIdPlat(generatedKeys.getInt(1));
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

    // Get all plats
    public List<Plat> getAllPlats() {
        String sql = "SELECT * FROM plat";
        List<Plat> platList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Plat plat = new Plat();
                plat.setIdPlat(rs.getInt("id_plat"));
                plat.setPrixPlat(rs.getFloat("prix_plat"));
                plat.setDiscPlat(rs.getString("disc_plat"));
                plat.setNamePlat(rs.getString("name_plat"));
                plat.setAvisPlat(rs.getString("avis_plat"));

                platList.add(plat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return platList;
    }

    // Get plat by ID
    public Plat getPlatById(int id) {
        String sql = "SELECT * FROM plat WHERE id_plat = ?";
        Plat plat = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                plat = new Plat();
                plat.setIdPlat(rs.getInt("id_plat"));
                plat.setPrixPlat(rs.getFloat("prix_plat"));
                plat.setDiscPlat(rs.getString("disc_plat"));
                plat.setNamePlat(rs.getString("name_plat"));
                plat.setAvisPlat(rs.getString("avis_plat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return plat;
    }

    // Update an existing plat
    public boolean updatePlat(Plat plat) {
        String sql = "UPDATE plat SET prix_plat = ?, disc_plat = ?, name_plat = ?, avis_plat = ? WHERE id_plat = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setFloat(1, plat.getPrixPlat());
            pstmt.setString(2, plat.getDiscPlat());
            pstmt.setString(3, plat.getNamePlat());
            pstmt.setString(4, plat.getAvisPlat());
            pstmt.setInt(5, plat.getIdPlat());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // Delete a plat
    public boolean deletePlat(int id) {
        String sql = "DELETE FROM plat WHERE id_plat = ?";
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