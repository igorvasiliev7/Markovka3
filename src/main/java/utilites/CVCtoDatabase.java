package utilites;

import com.opencsv.CSVReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            int i=0;
            while ((line=csvReader.readNext())!=null){
                System.out.println(++i);
                System.out.print(line[0]);
                System.out.print(line[1]);
                System.out.print(line[2]);
                System.out.print(line[3]);
                System.out.print(line[4]);
                System.out.print(line[5]);
                System.out.print(line[6]);
                System.out.print(line[7]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
