package db;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
    private static Connection connection;

    private static Connection getNewConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost:5432/dist";
        String user = "postgres";
        String passwd = "alex1999";
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, passwd);
    }

    public static void init() throws SQLException, IOException, ClassNotFoundException {
        connection = getNewConnection();

        File file = new File(DBUtils.class.getResource("/" + "init_db.sql").getFile());
        char[] buf = new char[(int)file.length()];
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int count = reader.read(buf);
        Statement statement = connection.createStatement();
        statement.execute(new String(buf, 0, count));
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
