package hr.algebra.waterworks.shared.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreateItemRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private int categoryId;
    private int amount;
}
