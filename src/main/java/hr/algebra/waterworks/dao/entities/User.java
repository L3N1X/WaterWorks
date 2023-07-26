package hr.algebra.waterworks.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String FirstName;
    private String LastName;
    private String Username;
    private String Password;
}
