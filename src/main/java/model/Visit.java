package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
