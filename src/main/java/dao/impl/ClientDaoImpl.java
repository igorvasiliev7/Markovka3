package dao.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import dao.ClientDao;
import model.Client;

import java.sql.SQLException;

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
        public void add (Client client) throws SQLException {
                dao.create(client);


        }

        @Override
        public Client findByPhone (String phone) throws SQLException {

                return dao.queryBuilder()
                            .where()
                            .eq("phone", phone)
                            .queryForFirst();
        }
}
