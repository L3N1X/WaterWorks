package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.dao.services.interfaces.ItemService;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private ItemService itemService;

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("items", itemService.GetAll(null));
        model.addAttribute("itemFilter", new ItemFilterRequest());
        return "home";
    }

    @PostMapping("filter")
    public String getFilteredHomePage(Model model, @ModelAttribute("itemFilter") ItemFilterRequest itemFilterRequest) {
        model.addAttribute("items", itemService.GetAll(itemFilterRequest));
        return "home";
    }

}
