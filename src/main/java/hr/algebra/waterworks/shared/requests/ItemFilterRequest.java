package hr.algebra.waterworks.shared.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFilterRequest{
    private String name;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
}
