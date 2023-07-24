package hr.algebra.waterworks.services.interfaces;

import hr.algebra.waterworks.dao.entities.Login;
import hr.algebra.waterworks.shared.dtos.CategoryDto;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.dtos.LoginDto;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;

import java.io.IOException;
import java.util.List;

public interface WaterWorksService {

    //Items
    List<ItemDto> getAllItems(ItemFilterRequest request);
    ItemDto getItem(int id);
    ItemDto createItem(CreateItemRequest request) throws IOException;

    //Categories
    List<CategoryDto> getAllCategories();

    //Logins
    void createLogin(Login login);
    List<LoginDto> getAllLogins();
}
