package hr.algebra.waterworks.shared.dtos;

import hr.algebra.waterworks.dao.enums.UserRole;

public record UserDto(int id, String firstName, String lastName, String email, String role) {
}
