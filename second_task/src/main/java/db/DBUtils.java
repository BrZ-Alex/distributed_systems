package db;

import java.io.*;
import java.sql.*;

public class DBUtils {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement nodePreparedStatement;
    private static PreparedStatement tagPreparedStatement;

    private static Connection getNewConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost:5432/dist";
        String user = "postgres";
        String passwd = "postgres";
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

    public static PreparedStatement getNodePreparedStatement(String sql) throws SQLException {
        if(nodePreparedStatement == null){
            nodePreparedStatement = connection.prepareStatement(sql);
        }
        return nodePreparedStatement;
    }

    public static PreparedStatement getTagPreparedStatement(String sql) throws SQLException {
        if(tagPreparedStatement == null){
            tagPreparedStatement = connection.prepareStatement(sql);
        }
        return tagPreparedStatement;
    }
}
