package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.dao.services.interfaces.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {

    private ItemService itemService;

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("items", itemService.GetAll(null));
        return "home";
    }

}
