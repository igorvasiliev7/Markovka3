package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private int id;
    private String phone;
    private String name;
    private String status;
}
