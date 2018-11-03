package utilites;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import controller.LoginController;
import dao.factory.DaoFactory;
import dao.impl.ClientDaoDtoImpl;
import model.Call;
import model.Client;
import model.ClientDTO;
import model.Visit;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CVCtoDatabase {

    public static void main(String[] args) {

//        exportToCSV();
  //      exportToExcel();

//            new ClientDaoDtoImpl().findDtoWithCall();
//            new ClientDaoDtoImpl().findDtoNoCalls();
//        search();
    }

    //Export to Excel
    public String exportToExcel() {

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Visits");

            int rownum = 0;
            Cell cell;
            Row row;
            //
            HSSFCellStyle style = createStyleForTitle(workbook);

            row = sheet.createRow(rownum);

            // #
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue("#");
            cell.setCellStyle(style);
            // Name
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Name");
            cell.setCellStyle(style);
            // Phone
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Phone");
            cell.setCellStyle(style);
            // Status
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Status");
            cell.setCellStyle(style);
            // Card
            cell = row.createCell(4, CellType.NUMERIC);
            cell.setCellValue("Card");
            cell.setCellStyle(style);
            // Visit
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Visit");
            cell.setCellStyle(style);
            // Amount
            cell = row.createCell(6, CellType.NUMERIC);
            cell.setCellValue("Amount");
            cell.setCellStyle(style);
            // Call date
            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Call date");
            cell.setCellStyle(style);
            // Comment
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("Comment");
            cell.setCellStyle(style);

        List<ClientDTO> clients = DaoFactory.getClientDaoDtoImpl().exportAllDto();

            // Data
            for (ClientDTO client : clients) {
                rownum++;
                row = sheet.createRow(rownum);

                // EmpNo (A)
                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(client.getNumber());
                // EmpName (B)
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(client.getClientName());
                // Salary (C)
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(client.getClientPhone());
                // Salary (C)
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(client.getClientStatus());
                // Grade (D)
                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue(client.getCard());
                // Salary (C)
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(client.getVisitDate());
                // Salary (C)
                cell = row.createCell(6, CellType.NUMERIC);
                cell.setCellValue(client.getVisitsSum());
                // Salary (C)
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(client.getLastCallDate());
                // Salary (C)
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(client.getComment()
                );
            }
        String date = LocalDate.now().toString();
            String path = "d:\\base" + date + ".xls";
        try (OutputStream bw = Files.newOutputStream(Paths.get(path),StandardOpenOption.CREATE)) {
            workbook.write(bw);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            path = "Error with export";
           return path;
        }

    }
    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }




    //Export to CSV
    public void exportToCSV() {
        List<ClientDTO> list = DaoFactory.getClientDaoDtoImpl().exportAllDto();
        String date = LocalDate.now().toString();
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("/base" + date + ".csv"), StandardCharsets.UTF_8,StandardOpenOption.CREATE)) {
            CSVWriter writer = new CSVWriter(bw, ';',CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
            String[] row = {"#", "Name", "Phone", "Status", "Card", "Visit", "Amount", "Call date", "Comment"};
            writer.writeNext(row);

            for (int i = 0; i < list.size(); i++) {
                row = list.get(i).toString().split(";");
                writer.writeNext(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Base first parsing from csv
    private static void search() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/base.csv"))) {
            CSVReader reader = new CSVReader(br, ';');
            String[] list;
            Client client;
            reader.readNext();
            int i = 0;
            while ((list = reader.readNext()) != null) {
                i++;
                System.out.println(list.length + " " + i);

                if ((client = DaoFactory.getClientDao().findByPhone("0" + list[2])) == null) {
                    client = addClient(list);
                }

                addVisit(list, client);

                if (!list[7].isEmpty()) {
                    addCall(list, client);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    private static void addCall(String[] list, Client client) {
        Call call = new Call(client.getId(), list[7], list[5]);
        DaoFactory.getCallDao().call(call);
    }
    private static void addVisit(String[] list, Client client) {
        String amount = list[3];
        if (amount.isEmpty()) {
            amount = "0";
        }
        Visit visit = new Visit(client.getId(), list[0], Integer.parseInt(amount));
        DaoFactory.getVisitDao().add(visit);
    }
    private static Client addClient(String[] list) {
        Client client;
        if (list[8].isEmpty()) {
            client = new Client(list[1], "0" + list[2], list[4], 0);
        } else {
            client = new Client(list[1], "0" + list[2], list[4], Integer.parseInt(list[8]));
        }
        try {
            client = DaoFactory.getClientDao().add(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
}
