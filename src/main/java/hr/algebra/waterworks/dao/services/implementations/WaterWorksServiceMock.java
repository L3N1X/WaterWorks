package hr.algebra.waterworks.dao.services.implementations;

import hr.algebra.waterworks.dao.entities.Category;
import hr.algebra.waterworks.dao.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.CategoryDto;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.dao.entities.Item;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WaterWorksServiceMock implements WaterWorksService {

    private final List<Item> items;
    private final List<Category> categories;

    public WaterWorksServiceMock() {

        Category category1 = new Category(1, "Miješaice");
        Category category2 = new Category(2, "Tuševi");
        categories = Stream.of(category1, category2).collect(Collectors.toList());

        Item item1 = new Item(1, "Miješalica za sudoper Voxort Kaya Silver", "Boja: crna, D: 14,9 cm, V:14,4 cm", new BigDecimal("29.99"), 1, null, true);
        Item item2 = new Item(2, "Miješalica podžbukna za umivaonik Voxort Perla", "Boja: crna, D: 18,3 cm", new BigDecimal("39.99"),  1, null, true);
        Item item3 = new Item(3, "Miješalica podžbukna za umivaonik Voxort Tea Black", "Boja: crna, D: 18,1 cm, V:38,2 cm", new BigDecimal("49.99"), 1,  null, true);
        Item item4 = new Item(4, "Miješalica za umivaonik potisna Voxort, krom", "Boja: crna, dužina izljeva: 12,5 cm, visina izljeva: 10,9 cm", new BigDecimal("49.99"), 1, null, true);
        Item item5 = new Item(5, "Miješalica za kadu Voxort Iris, krom/crna", "Boja: crna, dužina izljeva: 21,67 cm, visina izljeva: 24,84 cm", new BigDecimal("49.99"), 1,  null, true);
        Item item6 = new Item(6, "Miješalica za tuš kadu Voxort Iris, krom/crna", "Boja: crna, D: 12,8 cm, visina izljeva: 24,84 cm, promjer 1 cm", new BigDecimal("49.99"), 1, null, true);
        items = Stream.of(item1, item2, item3, item4, item5, item6).collect(Collectors.toList());
    }

    private List<ItemDto> itemsToDtos(List<Item> items, int categoryId){
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items)
        {
            String currentCategoryName = "";
            Optional<Category> currentItemCategory
                    = categories.stream().filter(c -> c.getId() == item.getCategoryId()).findFirst();
            if(currentItemCategory.isPresent())
                currentCategoryName = currentItemCategory.get().getName();
            // TODO WARNING: You have set available always to true, beware!
            ItemDto dto = new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice(), currentCategoryName, item.getImageName(), item.isActive(), true);
            if(categoryId == 0 || item.getCategoryId() == categoryId)
                itemDtos.add(dto);
        }
        return itemDtos;
    }


    @Override
    public List<ItemDto> getAllItems(ItemFilterRequest request) {
        if(request == null)
            return itemsToDtos(this.items, 0);
        List<ItemDto> dtos = itemsToDtos(this.items, request.getSelectedCategoryId());
        List<ItemDto> filteredDtos = new ArrayList<>();
        for (ItemDto dto : dtos)
        {
            if(request.getName().isEmpty() || dto.name().toUpperCase().contains(request.getName().toUpperCase()))
                filteredDtos.add(dto);
        }
        return filteredDtos;
    }

    @Override
    public ItemDto getItem(int id) {
        var item = itemsToDtos(this.items, 0).stream().filter(i -> i.id() == id).findFirst();
        return item.orElse(null);
    }

    @Override
    public ItemDto createItem(CreateItemRequest request) {
        //Item item = new Item(0, request.getName(), request.getPrice(), null, null);
        //items.add(item);
        //return new ItemDto(item);
        return null;
    }

    private List<CategoryDto> categoriesToDtos(List<Category> categories){
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories){
            CategoryDto dto = new CategoryDto(category.getId(), category.getName());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoriesToDtos(this.categories);
    }
}
