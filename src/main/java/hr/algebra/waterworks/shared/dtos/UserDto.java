package hr.algebra.waterworks.shared.dtos;

import java.util.List;

public record UserDto(String firstName, String lastName, String username, List<String> roles) {
}
