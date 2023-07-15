package hr.algebra.waterworks.shared.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import hr.algebra.waterworks.dao.entities.Item;
import lombok.Data;

import java.math.BigDecimal;

public record ItemDto(String name, BigDecimal price) {
}
