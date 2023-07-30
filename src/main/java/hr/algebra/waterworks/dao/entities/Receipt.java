package hr.algebra.waterworks.dao.entities;

import hr.algebra.waterworks.dao.enums.PurchaseType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Receipt {

    private int id;
    private String receiptNumber;
    private int userId;
    private String userFullName;
    private BigDecimal paidPrice;
    private PurchaseType purchaseType;
}
