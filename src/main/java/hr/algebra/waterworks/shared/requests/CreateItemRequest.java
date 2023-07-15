package hr.algebra.waterworks.shared.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreateItemRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private int categoryId;

    public CreateItemRequest(String name, String description, BigDecimal price, int categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }
}
