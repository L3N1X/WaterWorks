package hr.algebra.waterworks.dao.services.implementations;

import hr.algebra.waterworks.dao.enums.ItemType;
import hr.algebra.waterworks.dao.services.interfaces.ItemService;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.dao.entities.Item;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ItemServiceMock implements ItemService {

    private final List<Item> items;

    public ItemServiceMock() {
        Item item1 = new Item( 1,"Item1", "Item 1 description", new BigDecimal("29.99"), ItemType.TOILET, null);
        Item item2 = new Item(2,"Item2","This is another description", new BigDecimal("39.99"), ItemType.BIDET, null);
        Item item3 = new Item(3,"Item3","Good item!", new BigDecimal("49.99"), ItemType.FAUCET, null);
        items = Stream.of(item1, item2, item3).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> GetAll(ItemFilterRequest request) {
        if(request != null){
            return items
                    .stream()
                    .filter(i -> request.name().equals(i.getName()) || request.price().compareTo(i.getPrice()) == 0)
                    .map(i -> new ItemDto(i.getName(), i.getPrice()))
                    .collect(Collectors.toList());
        }
        return items.stream().map(i -> new ItemDto(i.getName(), i.getPrice())).collect(Collectors.toList());
    }

    @Override
    public ItemDto Get(int id) {
        var item = items.stream().filter(i -> i.getId() == id).map(i -> new ItemDto(i.getName(), i.getPrice())).findFirst();
        return item.orElse(null);
    }

    @Override
    public ItemDto Create(CreateItemRequest request) {
        Item item = new Item(0, request.getName(), request.getPrice(), null, null);
        items.add(item);
        return new ItemDto(item);
    }
}
