package hr.algebra.waterworks.shared.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
}
