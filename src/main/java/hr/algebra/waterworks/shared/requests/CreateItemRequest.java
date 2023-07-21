package hr.algebra.waterworks.shared.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private int selectedCategoryId;
    private int amount;
    private MultipartFile imageInput;
    private String errorMessage;
}
