package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.dao.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.requests.ItemFilterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
@SessionAttributes("categories")
public class HomeController {

    private WaterWorksService waterWorksService;

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("items", waterWorksService.getAllItems(null));
        model.addAttribute("itemFilter", new ItemFilterRequest());
        model.addAttribute("categories", waterWorksService.getAllCategories());
        return "home";
    }

    @PostMapping
    public String getFilteredHomePage(Model model, @ModelAttribute("itemFilter") ItemFilterRequest itemFilterRequest) {
        model.addAttribute("items", waterWorksService.getAllItems(itemFilterRequest));
        return "home";
    }

}
