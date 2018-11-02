package model;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class ClientDTO {
    private int number;
    private int clientId;
    private String clientName;
    private String clientPhone;
    private String clientStatus;
    private int card;
    private String lastCallDate;
    private String comment;
    private String visitDate;
    private int visitsSum;

    public ClientDTO(int number) {
        this.number = number;
    }

    public ClientDTO(int number, String lastCallDate, String comment) {
        this.number = number;
        this.lastCallDate = lastCallDate;
        this.comment = comment;
    }
}
