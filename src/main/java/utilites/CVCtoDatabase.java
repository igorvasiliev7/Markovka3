package utilites;

import com.opencsv.CSVReader;
import dao.factory.DaoFactory;
import model.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
        try (FileInputStream fis = new FileInputStream("src/main/resources/base1.csv");
             InputStreamReader isr = new InputStreamReader(fis,
                     StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(isr,';')) {
            String[] line;
//            System.out.println(Arrays.toString(line));
            while ((line=csvReader.readNext())!=null){
             Client client = new Client(line[1], line[2],line[4], Integer.parseInt(line[5]));
                try {
                    DaoFactory.getClientDao().add(client);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
