package dao;

import db.DBUtils;
import entities.NodeEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class NodeDAO extends MainDAO<NodeEntity>{
    @Override
    public void insert(NodeEntity obj) throws SQLException {
        Statement statement = DBUtils.getStatement();

        String sql = "insert into nodes(id, version, uid, users, changeset, lat, lon) " +
                "values ("
                + obj.getId() + ", "
                + obj.getVersion() + ", "
                + obj.getUid() + ", '"
                + obj.getUser().replaceAll("'", "''") + "',"
                + obj.getChangeset() + ", "
                + obj.getLat() + ", "
                + obj.getLon() +  ")";
        statement.execute(sql);
        DBUtils.getConnection().commit();
    }

    private static final String sql = "insert into nodes(id, version, uid, users, changeset, lat, lon) " +
            "values (?,?,?,?,?,?,?)";

    private static void prepare(PreparedStatement statement, NodeEntity obj) throws SQLException {
        statement.setLong(1, obj.getId());
        statement.setInt(2, obj.getVersion());
        statement.setInt(3, obj.getUid());
        statement.setString(4, obj.getUser());
        statement.setInt(5, obj.getChangeset());
        statement.setDouble(6, obj.getLat());
        statement.setDouble(7, obj.getLon());
    }

    @Override
    public void insertPrepared(NodeEntity obj) throws SQLException {
        PreparedStatement statement = DBUtils.getNodePreparedStatement(sql);
        prepare(statement, obj);
        statement.execute();
        DBUtils.getConnection().commit();
    }

    @Override
    public void batchInsert(List<NodeEntity> obj) throws SQLException {
        PreparedStatement statement = DBUtils.getNodePreparedStatement(sql);
        for (NodeEntity node : obj) {
            prepare(statement, node);
            statement.addBatch();
        }
        statement.executeBatch();
        DBUtils.getConnection().commit();
    }
}
