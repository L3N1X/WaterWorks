package hr.algebra.waterworks.shared.sessionmodels;

import hr.algebra.waterworks.shared.dtos.ItemDto;
import lombok.Data;


import java.math.BigDecimal;
import java.util.*;

@Data
public class Cart {

    public Cart() {
        this.items = new HashMap<>();
    }
    private HashMap<ItemDto, Integer> items;

    public BigDecimal getTotalPrice(){
        BigDecimal totalSum = BigDecimal.ZERO;
        for (Map.Entry<ItemDto, Integer> entry : items.entrySet()) {
            ItemDto key = entry.getKey();
            Integer value = entry.getValue();
            BigDecimal price = key.price();
            BigDecimal sumForEntry = price.multiply(BigDecimal.valueOf(value));
            totalSum = totalSum.add(sumForEntry);
        }
        return totalSum;
    }
}
