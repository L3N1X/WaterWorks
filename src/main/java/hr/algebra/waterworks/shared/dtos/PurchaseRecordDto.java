package hr.algebra.waterworks.shared.dtos;

import java.math.BigDecimal;

public record PurchaseRecordDto(String itemName, int amount, BigDecimal price) {
}
