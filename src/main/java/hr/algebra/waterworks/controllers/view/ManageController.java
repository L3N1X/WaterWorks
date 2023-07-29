package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.publishers.LoginEventPublisher;
import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.EditItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        model.addAttribute("items", waterWorksService.getAllItems(null));
        model.addAttribute("itemFilter", new ItemFilterRequest());
        model.addAttribute("categories", waterWorksService.getAllCategories());
        return "manage-items";
    }

    @PostMapping("items")
    public String getFilteredItemsPage(Model model, @ModelAttribute("itemFilter") ItemFilterRequest itemFilterRequest) {
        model.addAttribute("items", waterWorksService.getAllItems(itemFilterRequest));
        return "manage-items";
    }

    @GetMapping("logins")
    public String getLogins(Model model){
        model.addAttribute("logins", waterWorksService.getAllLogins());
        return "logins";
    }

    @GetMapping("edit")
    public String getEditItemPage(Model model, @RequestParam("id") int id){
        EditItemRequest editItemRequest = new EditItemRequest(waterWorksService.getItem(id));
        model.addAttribute("categories", waterWorksService.getAllCategories());
        model.addAttribute("editItemRequest", editItemRequest);
        return "edit-item";
    }

    public String editItem(Model model, @ModelAttribute("editItemRequest") EditItemRequest editItemRequest) {
        return "edit-item";
    }
}
