package dao.factory;

import dao.CallDao;
import dao.ClientDao;
import dao.VisitDao;
import dao.impl.CallDaoImpl;
import dao.impl.ClientDaoImpl;
import dao.impl.VisitDaoImpl;

public class DaoFactory {
    static public ClientDao getClientDao(){
        return new ClientDaoImpl();
    }

    static public VisitDao getVisitDao(){
        return new VisitDaoImpl();
    }

    static public CallDao getCallDao(){
        return new CallDaoImpl();
    }
}
