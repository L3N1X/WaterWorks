package hr.algebra.waterworks.shared.dtos;

import hr.algebra.waterworks.dao.enums.PurchaseType;

import java.math.BigDecimal;
import java.util.List;

public record ReceiptDto(
        int id,
        String receiptNumber,
        int userId,
        UserDto user,
        String userFullName,
        List<ItemDto> purchasedItems,
        BigDecimal paidPrice,
        PurchaseType purchaseType
) {
}
