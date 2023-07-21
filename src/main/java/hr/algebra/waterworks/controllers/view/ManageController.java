package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("manage")
@AllArgsConstructor
public class ManageController {
    private WaterWorksService waterWorksService;

    @GetMapping("create")
    private String getCreateItemPage(Model model) {
        model.addAttribute("categories", waterWorksService.getAllCategories());
        model.addAttribute("createItemRequest", new CreateItemRequest());
        return "createItem";
    }

    @PostMapping("create")
    private String createNewItem(Model model, @ModelAttribute("createItemRequest") CreateItemRequest createItemRequest){
        try {
            waterWorksService.createItem(createItemRequest);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Došlo je do pogreške. Kontaktirajte administratora.");
            return "createItem";
        }
        return "redirect:/";
    }
}
