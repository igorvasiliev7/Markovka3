package utilites;

import com.opencsv.CSVReader;
import dao.factory.DaoFactory;
import model.Call;
import model.Client;
import model.Visit;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;

public class CVCtoDatabase {
    public static void main(String[] args) {

        search();
    }
    private static void search() {
        try (BufferedReader br = new BufferedReader (new FileReader("src/main/resources/base.csv"))) {
            CSVReader reader = new CSVReader(br,';');
            String [] list;
            Client client;
            reader.readNext();
            int i=0;
            while ((list=reader.readNext())!=null){
                i++;
                System.out.println(list.length+" "+i);

                if((client=DaoFactory.getClientDao().findByPhone("0"+list[2]))==null){
                 client = addClient(list);}

                addVisit(list, client);

                if(!list[7].isEmpty()){
                    addCall(list, client);
                }
            }
        } catch (IOException|SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addCall(String[] list, Client client) {
        Call call = new Call(client.getId(),list[7],list[5]);
        DaoFactory.getCallDao().call(call);
    }

    private static void addVisit(String[] list, Client client) {
        String amount = list[3];
        if(amount.isEmpty()) {
            amount="0";
        }
        Visit visit = new Visit(client.getId(), list[0],Integer.parseInt(amount));
        DaoFactory.getVisitDao().add(visit);
    }

    private static Client addClient(String [] list) {
        Client client;
        if (list[8].isEmpty()) {
            client = new Client(list[1], "0"+list[2],list[4], 0);
        }
        else {
            client = new Client(list[1], "0"+list[2],list[4], Integer.parseInt(list[8]));
        }
        try {
         client = DaoFactory.getClientDao().add(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
}
