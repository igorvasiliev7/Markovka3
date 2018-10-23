package dao.factory;

import dao.ClientDao;
import dao.impl.ClientDaoImpl;

public class DaoFactory {
    static public ClientDao getClientDao(){
        return new ClientDaoImpl();
    }
}
