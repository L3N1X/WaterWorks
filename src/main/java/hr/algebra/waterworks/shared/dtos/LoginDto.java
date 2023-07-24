package hr.algebra.waterworks.shared.dtos;

import java.time.LocalDateTime;

public record LoginDto(int userId, String ipAddress, LocalDateTime dateTime, String userEmail, String userFirstName, String userLastName, String userRoleName) {
}
