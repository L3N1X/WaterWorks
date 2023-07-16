package hr.algebra.waterworks.dao.services.implementations;

import hr.algebra.waterworks.dao.services.interfaces.ItemService;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.dao.entities.Item;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ItemServiceMock implements ItemService {

    private final List<Item> items;

    public ItemServiceMock() {
        Item item1 = new Item(1, "Miješalica za sudoper Voxort Kaya", "Boja: crna, D: 14,9 cm, V:14,4 cm", new BigDecimal("29.99"), null, null, true);
        Item item2 = new Item(2, "Miješalica podžbukna za umivaonik Voxort Perla", "Boja: crna, D: 18,3 cm", new BigDecimal("39.99"), null, null, true);
        Item item3 = new Item(3, "Miješalica podžbukna za umivaonik Voxort Tea Black", "Boja: crna, D: 18,1 cm, V:38,2 cm", new BigDecimal("49.99"), null, null, true);
        Item item4 = new Item(4, "Miješalica za umivaonik potisna Voxort, krom", "Boja: crna, dužina izljeva: 12,5 cm, visina izljeva: 10,9 cm", new BigDecimal("49.99"), null, null, true);
        Item item5 = new Item(5, "Miješalica za kadu Voxort Iris, krom/crna", "Boja: crna, dužina izljeva: 21,67 cm, visina izljeva: 24,84 cm", new BigDecimal("49.99"), null, null, true);
        Item item6 = new Item(6, "Miješalica za tuš kadu Voxort Iris, krom/crna", "Boja: crna, D: 12,8 cm", new BigDecimal("49.99"), null, null, true);
        items = Stream.of(item1, item2, item3, item4, item5, item6).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> GetAll(ItemFilterRequest request) {
        if(request != null){
            return items
                    .stream()
                    .filter(i -> request.getName().equals(i.getName()) || request.getPriceFrom().compareTo(i.getPrice()) == 0)
                    .map(i -> new ItemDto(i.getId(), i.getName(), i.getDescription(), i.getPrice(), null, i.getImageName(), i.isActive()))
                    .collect(Collectors.toList());
        }
        return items
                .stream()
                .map(i -> new ItemDto(i.getId(), i.getName(), i.getDescription(), i.getPrice(), null, i.getImageName(), i.isActive()))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto Get(int id) {
        var item = items.
                stream()
                .filter(i -> i.getId() == id).map(i -> new ItemDto(i.getId(), i.getName(), i.getDescription(), i.getPrice(), null, i.getImageName(), i.isActive()))
                .findFirst();
        return item.orElse(null);
    }

    @Override
    public ItemDto Create(CreateItemRequest request) {
        //Item item = new Item(0, request.getName(), request.getPrice(), null, null);
        //items.add(item);
        //return new ItemDto(item);
        return null;
    }
}
