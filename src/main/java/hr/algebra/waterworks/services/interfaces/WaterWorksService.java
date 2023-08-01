package hr.algebra.waterworks.services.interfaces;

import hr.algebra.waterworks.dao.entities.Login;
import hr.algebra.waterworks.shared.dtos.CategoryDto;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.dtos.LoginDto;
import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.requests.CreateCategoryRequest;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.EditItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface WaterWorksService {

    //Items
    List<ItemDto> getAllItems(ItemFilterRequest request, Optional<Boolean> active);
    ItemDto getItem(int id);
    ItemDto createItem(CreateItemRequest request) throws IOException;
    void editItem(EditItemRequest request) throws IOException;
    void toggleActiveItem(int id, boolean active);

    //Categories
    List<CategoryDto> getAllCategories();
    void createCategory(CreateCategoryRequest request);

    //Logins
    void createLogin(Login login);
    List<LoginDto> getAllLogins();

    //Users
    UserDto getUser(String username);
}
