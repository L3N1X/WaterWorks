package hr.algebra.waterworks.shared.dtos;

import java.math.BigDecimal;

public record ItemDto(int id,
        String name,
        String description,
        BigDecimal price,
        String categoryName,
        String base64content,
        boolean active,
        boolean available) {
}
