package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "calls")
public class Call {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "client_id")
    private int clientId;
    @DatabaseField(columnName = "date")
    private String date;
    @DatabaseField (columnName = "comment")
    private String comment;

    public Call(int clientId, String date, String comment) {
        this.clientId = clientId;
        this.date = date;
        this.comment = comment;
    }
}
