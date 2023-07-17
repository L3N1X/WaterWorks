package hr.algebra.waterworks.dao.services.interfaces;

import hr.algebra.waterworks.shared.dtos.CategoryDto;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;

import java.util.List;

public interface WaterWorksService {

    //Items
    List<ItemDto> getAllItems(ItemFilterRequest request);
    ItemDto getItem(int id);
    ItemDto createItem(CreateItemRequest request);

    //Categories
    List<CategoryDto> getAllCategories();

}
