package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.fxml.FXML;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@DatabaseTable(tableName = "clients")
public class Client {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "phone")
    private String phone;
    @DatabaseField(columnName = "status")
    private String status;
    @DatabaseField(columnName = "card")
    private int card;
//TODO check constructors list

    public Client(int id, String phone, String name, String status, int card) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.status = status;
        this.card = card;
    }

    public Client(String name, String phone, String status, int card) {
        this.phone = phone;
        this.name = name;
        this.status = status;
        this.card = card;
    }

    public Client(String name, String phone, String status) {
        this.phone = phone;
        this.name = name;
        this.status = status;
    }
}
