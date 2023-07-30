package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.publishers.LoginEventPublisher;
import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.CategoryDto;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.requests.CreateCategoryRequest;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.EditItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("manage")
@SessionAttributes("categories")
@AllArgsConstructor
public class ManageController {

    private LoginEventPublisher loginEventPublisher;
    private WaterWorksService waterWorksService;

    @GetMapping("create")
    private String getCreateItemPage(Model model) {
        model.addAttribute("categories", waterWorksService.getAllCategories());
        model.addAttribute("createItemRequest", new CreateItemRequest());
        return "createItem";
    }

    @PostMapping("create")
    public String createNewItem(Model model, @ModelAttribute("createItemRequest") CreateItemRequest createItemRequest){
        try {
            waterWorksService.createItem(createItemRequest);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Došlo je do pogreške. Kontaktirajte administratora.");
            return "createItem";
        }
        return "redirect:/manage/items";
    }

    @GetMapping("items")
    public String getItemsPage(Model model) {
        model.addAttribute("items", waterWorksService.getAllItems(null, Optional.empty()));
        model.addAttribute("itemFilter", new ItemFilterRequest());
        model.addAttribute("categories", waterWorksService.getAllCategories());
        return "manage-items";
    }

    @PostMapping("items")
    public String getFilteredItemsPage(Model model, @ModelAttribute("itemFilter") ItemFilterRequest itemFilterRequest) {
        model.addAttribute("items", waterWorksService.getAllItems(itemFilterRequest, Optional.empty()));
        return "manage-items";
    }

    @GetMapping("logins")
    public String getLogins(Model model){
        model.addAttribute("logins", waterWorksService.getAllLogins());
        return "logins";
    }

    @GetMapping("edit")
    public String getEditItemPage(Model model, @RequestParam("id") int id){
        ItemDto itemDto = waterWorksService.getItem(id);
        EditItemRequest editItemRequest = new EditItemRequest(itemDto);
        Optional<CategoryDto> categoryId = waterWorksService.getAllCategories()
                .stream()
                .filter(c -> Objects.equals(c.name(), itemDto.categoryName()))
                .findFirst();
        categoryId.ifPresent(categoryDto -> editItemRequest.setSelectedCategoryId(categoryDto.id()));
        model.addAttribute("categories", waterWorksService.getAllCategories());
        model.addAttribute("editItemRequest", editItemRequest);
        return "edit-item";
    }

    @PostMapping("edit")
    public String editItem(Model model, @ModelAttribute("editItemRequest") EditItemRequest editItemRequest) {
        try {
            waterWorksService.editItem(editItemRequest);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage","Došlo je do pogreške pri ažuriranju artikla");
            return "edit-item";
        }
        return "redirect:/manage/items";
    }

    @GetMapping("activate")
    public String activateItem(@RequestParam("id") int id) {
        waterWorksService.toggleActiveItem(id, true);
        return "redirect:/manage/items";
    }

    @GetMapping("deactivate")
    public String deactivateItem(@RequestParam("id") int id) {
        waterWorksService.toggleActiveItem(id, false);
        return "redirect:/manage/items";
    }

    @GetMapping("categories")
    public String getManageCategoriesPage(Model model) {
        model.addAttribute("allCategories", waterWorksService.getAllCategories());
        return "manage-categories";
    }

    @GetMapping("newcategory")
    public String getNewCategoryPage(Model model) {
        model.addAttribute("createCategoryRequest", new CreateItemRequest());
        return "create-category";
    }

    @PostMapping("newcategory")
    public String createNewCategory(Model model, @ModelAttribute("createCategoryRequest") CreateCategoryRequest createCategoryRequest) {
        waterWorksService.createCategory(createCategoryRequest);
        return "redirect:/manage/categories";
    }
}
