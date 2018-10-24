package utilites;

import com.opencsv.CSVReader;
import dao.factory.DaoFactory;
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
        try (BufferedReader br = new BufferedReader (new FileReader("src/main/resources/base1.csv"))) {
            String line;
            String [] list;
            while ((line=br.readLine())!=null){
                list = line.split(";");
               if(DaoFactory.getClientDao().findByPhone("0"+list[2])==null) addClient(list);
                addVisit(list);
            }

        } catch (IOException| SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addVisit(String[] list) {
        Visit visit = new Visit(list[0],)
    }

    private static void addClient(String [] list) {
        Client client;
        if (list[5].isEmpty()) {
            client = new Client(list[1], "0"+list[2],list[4], 0);
        }
        else {
            client = new Client(list[1], "0"+list[2],list[4], Integer.parseInt(list[5]));
        }
        try {
            DaoFactory.getClientDao().add(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
