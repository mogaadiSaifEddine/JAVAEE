package models;

import entities.MyTable;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableModel {

    // Create a new table
    public boolean addTable(MyTable table) {
        String sql = "INSERT INTO myTable (nbr_place, localisation_table) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, table.getNbrPlace());
            pstmt.setString(2, table.getLocalisationTable());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    table.setIdTab(generatedKeys.getInt(1));
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

    // Get all tables
    public List<MyTable> getAllTables() {
        String sql = "SELECT * FROM myTable";
        List<MyTable> tableList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MyTable table = new MyTable();
                table.setIdTab(rs.getInt("id_tab"));
                table.setNbrPlace(rs.getInt("nbr_place"));
                table.setLocalisationTable(rs.getString("localisation_table"));

                tableList.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return tableList;
    }

    // Get table by ID
    public MyTable getTableById(int id) {
        String sql = "SELECT * FROM myTable WHERE id_tab = ?";
        MyTable table = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                table = new MyTable();
                table.setIdTab(rs.getInt("id_tab"));
                table.setNbrPlace(rs.getInt("nbr_place"));
                table.setLocalisationTable(rs.getString("localisation_table"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return table;
    }

    // Get available tables for a specific date and time range
    public List<MyTable> getAvailableTables(Date date, Time startTime, Time endTime) {
        String sql = "SELECT * FROM myTable WHERE id_tab NOT IN " +
                "(SELECT id_tab FROM Reservation WHERE date_reservation = ? " +
                "AND ((heure_debut_reservation <= ? AND heure_fin_reservation > ?) " +
                "OR (heure_debut_reservation < ? AND heure_fin_reservation >= ?)))";

        List<MyTable> tableList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, date);
            pstmt.setTime(2, endTime);
            pstmt.setTime(3, startTime);
            pstmt.setTime(4, endTime);
            pstmt.setTime(5, startTime);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                MyTable table = new MyTable();
                table.setIdTab(rs.getInt("id_tab"));
                table.setNbrPlace(rs.getInt("nbr_place"));
                table.setLocalisationTable(rs.getString("localisation_table"));

                tableList.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return tableList;
    }

    // Update an existing table
    public boolean updateTable(MyTable table) {
        String sql = "UPDATE myTable SET nbr_place = ?, localisation_table = ? WHERE id_tab = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, table.getNbrPlace());
            pstmt.setString(2, table.getLocalisationTable());
            pstmt.setInt(3, table.getIdTab());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // Delete a table
    public boolean deleteTable(int id) {
        String sql = "DELETE FROM myTable WHERE id_tab = ?";
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