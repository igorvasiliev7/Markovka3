package dao.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import dao.CallDao;
import model.Call;
import model.Client;

import java.sql.SQLException;
import java.util.List;

public class CallDaoImpl implements CallDao {
    private static final String URL = "jdbc:sqlite:identifier.sqlite";
    private Dao<Call, Integer> dao;

    {
        try {
            ConnectionSource source = new JdbcConnectionSource(URL);
            dao = DaoManager.createDao(source, Call.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void call(Call call) {
        try {
            dao.create(call);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Call> findByUserId(int id) {
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
    public Call findTheLast() {
        try {
            return dao.queryBuilder()
                    .orderBy("id", false)
                    .queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Call> findAll(){
        try {
            return dao.queryBuilder()
                    .orderBy("date", false)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Call call){
        try {
            dao.delete(call);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
