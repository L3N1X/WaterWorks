package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.dao.services.interfaces.ItemService;
import hr.algebra.waterworks.shared.requests.CreateItemRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
@AllArgsConstructor
public class HomeController {

    private ItemService itemService;

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("items", itemService.GetAll(null));
        return "home";
    }

}
