package hr.algebra.waterworks.shared.dtos;

import hr.algebra.waterworks.dao.enums.PurchaseType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ReceiptDto{
    private int id;
    private String receiptNumber;
    private String username;
    private String userFullName;
    private List<PurchaseRecordDto> purchaseRecords;
    private BigDecimal totalPrice;
    private PurchaseType purchaseType;
    private LocalDateTime localDateTime;
}
