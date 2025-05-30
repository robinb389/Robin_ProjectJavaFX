package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Noted {
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:noted.db");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

