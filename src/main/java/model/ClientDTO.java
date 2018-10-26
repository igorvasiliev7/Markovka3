package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ClientDTO {
   private Client client;
    private String lastCallDate;
    int visitsSum;
}
