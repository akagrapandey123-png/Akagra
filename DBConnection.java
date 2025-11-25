import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Loads JDBC Driver

            String url = "jdbc:mysql://localhost:3306/librarydb";
            String user = "root";
            String pass = "Akagra@885111";

            return DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
