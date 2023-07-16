package hr.algebra.waterworks.shared.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import hr.algebra.waterworks.dao.entities.Category;
import hr.algebra.waterworks.dao.entities.Item;
import lombok.Data;

import java.math.BigDecimal;

public record ItemDto(int id,
        String name,
        String description,
        BigDecimal price,
        CategoryDto category,
        String imageName,
        boolean active) {
}
