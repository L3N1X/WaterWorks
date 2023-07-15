package hr.algebra.waterworks.dao.entities;

import hr.algebra.waterworks.dao.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Item{

    private int id;
    private String name;
    private String description;
    private BigDecimal price;

    private ItemType type;
    private String imageName;
}
