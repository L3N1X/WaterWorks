package hr.algebra.waterworks.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private int userId;
    private String ipAddress;
    private LocalDateTime dateTime;
}
