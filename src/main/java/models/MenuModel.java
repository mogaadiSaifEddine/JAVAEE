package models;

import entities.Menu;
import entities.Plat;
import entities.PlatMenu;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuModel {

    // Create a new menu
    public boolean addMenu(Menu menu) {
        String sql = "INSERT INTO menu () VALUES ()"; // Empty parentheses since there are no columns except the auto-increment ID
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int menuId = generatedKeys.getInt(1);
                    menu.setIdMenu(menuId);

                    // Add plats to the menu if any
                    if (menu.getPlats() != null && !menu.getPlats().isEmpty()) {
                        PlatMenuModel platMenuModel = new PlatMenuModel();
                        for (Plat plat : menu.getPlats()) {
                            PlatMenu platMenu = new PlatMenu(plat.getIdPlat(), menuId, 1); // Assuming quantity is 1 by default
                            platMenuModel.addPlatMenu(platMenu);
                        }
                    }

                    conn.commit();
                    return true;
                }
            }
            conn.rollback();
            return false;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeResources(null, pstmt, conn);
        }
    }

    // Get all menus
    public List<Menu> getAllMenus() {
        String sql = "SELECT * FROM menu";
        List<Menu> menuList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Menu menu = new Menu();
                menu.setIdMenu(rs.getInt("id_menu"));

                // Get plats for this menu
                PlatMenuModel platMenuModel = new PlatMenuModel();
                List<PlatMenu> platMenus = platMenuModel.getPlatMenusByMenuId(menu.getIdMenu());

                if (platMenus != null && !platMenus.isEmpty()) {
                    PlatModel platModel = new PlatModel();
                    List<Plat> plats = new ArrayList<>();

                    for (PlatMenu pm : platMenus) {
                        Plat plat = platModel.getPlatById(pm.getIdPlat());
                        if (plat != null) {
                            plats.add(plat);
                        }
                    }

                    menu.setPlats(plats);
                }

                menuList.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return menuList;
    }

    // Get menu by ID
    public Menu getMenuById(int id) {
        String sql = "SELECT * FROM menu WHERE id_menu = ?";
        Menu menu = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                menu = new Menu();
                menu.setIdMenu(rs.getInt("id_menu"));

                // Get plats for this menu
                PlatMenuModel platMenuModel = new PlatMenuModel();
                List<PlatMenu> platMenus = platMenuModel.getPlatMenusByMenuId(menu.getIdMenu());

                if (platMenus != null && !platMenus.isEmpty()) {
                    PlatModel platModel = new PlatModel();
                    List<Plat> plats = new ArrayList<>();

                    for (PlatMenu pm : platMenus) {
                        Plat plat = platModel.getPlatById(pm.getIdPlat());
                        if (plat != null) {
                            plats.add(plat);
                        }
                    }

                    menu.setPlats(plats);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return menu;
    }

    // Delete a menu
    public boolean deleteMenu(int id) {
        String sql = "DELETE FROM menu WHERE id_menu = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // First, delete all plat_menu entries for this menu
            PlatMenuModel platMenuModel = new PlatMenuModel();
            platMenuModel.deletePlatMenusByMenuId(id);

            // Then delete the menu itself
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                conn.commit();
                return true;
            }

            conn.rollback();
            return false;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    // Inner class to handle plat_menu operations
    private class PlatMenuModel {

        // Add a plat to a menu
        public boolean addPlatMenu(PlatMenu platMenu) {
            String sql = "INSERT INTO plat_menu (id_plat, id_menu, Qt_plat) VALUES (?, ?, ?)";
            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                conn = DBUtil.getConnection();
                pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, platMenu.getIdPlat());
                pstmt.setInt(2, platMenu.getIdMenu());
                pstmt.setInt(3, platMenu.getQtPlat());

                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                closeResources(null, pstmt, conn);
            }
        }

        // Get all plat_menu entries for a specific menu
        public List<PlatMenu> getPlatMenusByMenuId(int menuId) {
            String sql = "SELECT * FROM plat_menu WHERE id_menu = ?";
            List<PlatMenu> platMenuList = new ArrayList<>();
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                conn = DBUtil.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, menuId);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    PlatMenu platMenu = new PlatMenu();
                    platMenu.setIdPlat(rs.getInt("id_plat"));
                    platMenu.setIdMenu(rs.getInt("id_menu"));
                    platMenu.setQtPlat(rs.getInt("Qt_plat"));

                    platMenuList.add(platMenu);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResources(rs, pstmt, conn);
            }

            return platMenuList;
        }

        // Delete all plat_menu entries for a specific menu
        public boolean deletePlatMenusByMenuId(int menuId) {
            String sql = "DELETE FROM plat_menu WHERE id_menu = ?";
            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                conn = DBUtil.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, menuId);

                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                closeResources(null, pstmt, conn);
            }
        }
    }
}