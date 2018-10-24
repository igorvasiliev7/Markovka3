package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@DatabaseTable(tableName = "visits")
public class Visit {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "client_id")
    private int clientId;
    @DatabaseField (columnName = "date")
    private String date;
    @DatabaseField (columnName = "amount")
    private int amount;

    public Visit(int clientId, String date, int amount) {
        this.clientId = clientId;
        this.date = date;
        this.amount = amount;
    }
}
