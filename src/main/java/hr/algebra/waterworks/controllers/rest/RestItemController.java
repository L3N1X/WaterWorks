package hr.algebra.waterworks.controllers.rest;

import hr.algebra.waterworks.dao.enums.ItemType;
import hr.algebra.waterworks.dao.entities.Item;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/item")
@AllArgsConstructor
public class RestItemController {

    @GetMapping("all")
    public List<ItemDto> getAll() {
        return generateItems();
    }

    @GetMapping("{name}")
    public ItemDto getItemByName(@PathVariable String name) {
        Optional<ItemDto> item = generateItems()
                .stream()
                .filter(i -> i.getName().equals(name))
                .findFirst();
        return item.orElse(null);
    }

    private static List<ItemDto> generateItems(){
        Item item1 = new Item( 1,"Item1", new BigDecimal("29.99"), ItemType.TOILET, null);
        Item item2 = new Item(2,"Item2", new BigDecimal("39.99"), ItemType.BIDET, null);
        Item item3 = new Item(3,"Item3", new BigDecimal("49.99"), ItemType.FAUCET, null);
        Item item = new Item(1, "", new BigDecimal("10"), ItemType.BIDET, "");
        return new ArrayList<>(Stream.of(item1,item2,item3).map(ItemDto::new).toList());
    }

}
