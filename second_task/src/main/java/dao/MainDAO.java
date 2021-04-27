package dao;

import java.sql.SQLException;
import java.util.List;

public abstract class MainDAO<T> {
    public abstract void insert(T obj) throws SQLException;
    public abstract void insertPrepared(T obj) throws SQLException;
    public abstract void batchInsert(List<T> obj) throws SQLException;
}
