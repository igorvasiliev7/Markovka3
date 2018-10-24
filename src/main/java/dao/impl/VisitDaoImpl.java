package dao.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import dao.VisitDao;
import model.Visit;

import java.sql.SQLException;
import java.util.List;

public class VisitDaoImpl implements VisitDao {
    private static final String URL = "jdbc:sqlite:identifier.sqlite";
    private Dao<Visit, Integer> dao;

    {
        try {
            ConnectionSource source = new JdbcConnectionSource(URL);
            dao = DaoManager.createDao(source, Visit.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void add(Visit visit) {
        try {
            dao.create(visit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Visit> findByUserId(int id) {
        try {
            return dao.queryBuilder()
                    .orderBy("date", true)
                    .where()
                    .eq("client_id", id)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Visit findTheLast() {
        try {
            return dao.queryBuilder()
                    .orderBy("id", false)
                    .queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
