package hr.algebra.waterworks.shared.requests;

import java.math.BigDecimal;

public record ItemFilterRequest(
        String name,
        BigDecimal price
){}
