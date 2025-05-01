package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Database Utility class for handling connections
 * This class provides methods to obtain database connections and close them safely
 */
public class DBUtil {
    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());

    // JDBC database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/restaurant_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = ""; // Replace with your actual password

    // Connection pool name (if using JNDI)
    private static final String JNDI_DATASOURCE = "java:comp/env/jdbc/restaurant_db";

    // Flag to determine whether to use connection pool or direct connection
    private static final boolean USE_CONNECTION_POOL = false;

    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading MySQL JDBC driver", e);
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    /**
     * Gets a database connection from either the connection pool or direct connection
     * @return A database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (USE_CONNECTION_POOL) {
            return getConnectionFromPool();
        } else {
            return getDirectConnection();
        }
    }

    /**
     * Gets a direct connection to the database without using a connection pool
     * @return A database connection
     * @throws SQLException if a database access error occurs
     */
    private static Connection getDirectConnection() throws SQLException {
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", JDBC_USER);
            //connectionProps.put("password", JDBC_PASSWORD);

            // Additional connection properties for better performance and security
            connectionProps.put("useSSL", "false");
            connectionProps.put("serverTimezone", "UTC");
            connectionProps.put("allowPublicKeyRetrieval", "true");
            connectionProps.put("useUnicode", "true");
            connectionProps.put("characterEncoding", "UTF-8");

            LOGGER.fine("Attempting to connect to database directly at: " + JDBC_URL);
            return DriverManager.getConnection(JDBC_URL, connectionProps);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error connecting to database", e);
            throw e;
        }
    }

    /**
     * Gets a database connection from the connection pool
     * @return A database connection from the pool
     * @throws SQLException if a database access error occurs
     */
    private static Connection getConnectionFromPool() throws SQLException {
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup(JNDI_DATASOURCE);
            LOGGER.fine("Obtained connection from pool: " + JNDI_DATASOURCE);
            return ds.getConnection();
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Error looking up datasource", e);
            throw new SQLException("Error looking up datasource", e);
        }
    }

    /**
     * Safely closes a database connection
     * @param connection The connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    LOGGER.fine("Database connection closed successfully");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing database connection", e);
            }
        }
    }

    /**
     * Utility method to test database connection
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            LOGGER.info("Database connection test successful!");
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection test failed!", e);
            return false;
        } finally {
            closeConnection(conn);
        }
    }
}