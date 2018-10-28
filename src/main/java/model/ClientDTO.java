package model;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class ClientDTO {
    private int clientId;
    private String clientName;
    private String clientPhone;
    private String clientStatus;
    private int card;
    private String lastCallDate;
//    private int visits;
//    private int visitsSum;


    public ClientDTO(String lastCallDate) {
        this.lastCallDate = lastCallDate;
    }
}
