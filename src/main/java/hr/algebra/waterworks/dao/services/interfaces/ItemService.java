package hr.algebra.waterworks.dao.services.interfaces;

import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;

import java.util.List;

public interface ItemService {
    List<ItemDto> GetAll(ItemFilterRequest request);
    ItemDto Get(int id);
    ItemDto Create(CreateItemRequest request);
}
