package dao.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import dao.ClientDao;
import model.Call;
import model.Client;
import model.ClientDTO;

import java.sql.SQLException;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

    private static final String URL = "jdbc:sqlite:identifier.sqlite";
    private Dao<Client, Integer> dao;

    {
        try {
            ConnectionSource source = new JdbcConnectionSource(URL);
            dao = DaoManager.createDao(source, Client.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        @Override
        public Client add (Client client) throws SQLException {
                dao.create(client);
                return dao.queryBuilder()
                        .orderBy("id", false)
                        .queryForFirst();


        }

        @Override
        public Client findByPhone (String phone) throws SQLException {

                return dao.queryBuilder()
                            .where()
                            .eq("phone", phone)
                            .queryForFirst();
        }
        @Override
        public List<Client> findByStatus(String status){
            try {
                return dao.queryBuilder()
                        .where()
                        .eq("status", status)
                        .query();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void updateCard(Client client){
            try {
                dao.update(client);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public List<Client> findAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
