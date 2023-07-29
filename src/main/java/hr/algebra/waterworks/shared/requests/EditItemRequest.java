package hr.algebra.waterworks.shared.requests;

import hr.algebra.waterworks.shared.dtos.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class EditItemRequest {
    private MultipartFile imageInput;
    private String errorMessage;
    private int selectedCategoryId;

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private String base64content;
    private boolean active;
    private int amount;

    public EditItemRequest(ItemDto itemDto){
        this.id = itemDto.id();
        this.name = itemDto.name();
        this.description = itemDto.description();
        this.price = itemDto.price();
        this.base64content = itemDto.base64content();
        this.active = itemDto.active();
        this.amount = itemDto.amount();
    }
}
