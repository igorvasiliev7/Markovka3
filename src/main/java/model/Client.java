package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.fxml.FXML;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@DatabaseTable(tableName = "clients")
public class Client {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "phone")
    private String phone;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "status")
    private String status;
    @DatabaseField(columnName = "card")
    private boolean card;

    public Client(String name, String phone,  String status) {
        this.phone = phone;
        this.name = name;
        this.status = status;
    }
}
