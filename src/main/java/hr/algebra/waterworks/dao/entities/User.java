package hr.algebra.waterworks.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String PasswordHash;
}
