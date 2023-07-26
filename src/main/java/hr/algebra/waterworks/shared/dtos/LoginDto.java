package hr.algebra.waterworks.shared.dtos;

import java.time.LocalDateTime;

public record LoginDto(String userId, String ipAddress, LocalDateTime dateTime, String userFirstName, String userLastName) {
}
