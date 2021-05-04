package nsu.akatov.third_task.db;

import java.io.*;
import java.sql.*;

public class DBUtils {
    private static Connection connection;
    private static Statement statement;

    private static Connection getNewConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost:5432/dist";
        String user = "postgres";
        String passwd = "alex1999";
        return DriverManager.getConnection(url, user, passwd);
    }

    public static void init() throws SQLException, IOException, ClassNotFoundException {
        connection = getNewConnection();
        connection.setAutoCommit(false);

        File file = new File(DBUtils.class.getResource("/" + "init_db.sql").getFile());
        String initQuery = "";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            initQuery = initQuery.concat(line);
        }
        getStatement().execute(initQuery);
        connection.commit();
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() throws SQLException {
        if(statement == null){
            statement = connection.createStatement();
        }
        return statement;
    }
}
