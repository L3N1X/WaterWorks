package hr.algebra.waterworks.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Item{

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int categoryId;
    private String imageName;
    private boolean active;
    private int amount;
}
