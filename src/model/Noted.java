package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * The Class Noted.
 */
public class Noted {
    
    /**
     * Connect.
     *
     * @return the connection
     */
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:noted.db");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

