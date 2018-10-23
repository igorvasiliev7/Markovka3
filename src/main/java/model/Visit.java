package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Visit {
    private int id;
    private int clientId;
    private String date;
    private int amount;
}
