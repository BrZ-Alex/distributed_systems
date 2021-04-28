package dao;

import db.DBUtils;
import entities.TagEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TagDAO extends MainDAO<TagEntity>{
    @Override
    public void insert(TagEntity obj) throws SQLException {
        Statement statement = DBUtils.getStatement();

        String sql = "insert into tags(node_id, k, v) " +
                "values (" + obj.getNodeId() + ", '"
                + obj.getK() + "', '"
                + obj.getV().replaceAll("'", "''") + "')";
        statement.execute(sql);
        DBUtils.getConnection().commit();
    }

    private static final String sql = "insert into tags(node_id, k, v) " +
            "values (?,?,?)";

    private static void prepare(PreparedStatement statement, TagEntity obj) throws SQLException {
        statement.setLong(1, obj.getNodeId());
        statement.setString(2, obj.getK());
        statement.setString(3, obj.getV());
    }

    @Override
    public void insertPrepared(TagEntity obj) throws SQLException {
        PreparedStatement statement = DBUtils.getTagPreparedStatement(sql);
        prepare(statement, obj);
        statement.execute();
        DBUtils.getConnection().commit();
    }

    @Override
    public void batchInsert(List<TagEntity> obj) throws SQLException {
        PreparedStatement statement = DBUtils.getTagPreparedStatement(sql);
        for (TagEntity tag : obj) {
            prepare(statement, tag);
            statement.addBatch();
        }
        statement.executeBatch();
        DBUtils.getConnection().commit();
    }
}
