package mpserver.domain;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Account {
    private String id;
    private String phoneNumber;
    private String name;
    private String password;
}
