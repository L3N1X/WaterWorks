package hr.algebra.waterworks.shared.dtos;

import hr.algebra.waterworks.dao.enums.UserRole;

public record UserDto(String firstName, String lastName, String email, UserRole role) {
}
