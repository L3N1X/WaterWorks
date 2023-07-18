package hr.algebra.waterworks.shared.sessionmodels;

import hr.algebra.waterworks.shared.dtos.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Cart {

    public Cart() {
        this.items = new HashSet<>();
    }
    private Set<ItemDto> items;
    private String text = "KURAC";
}
