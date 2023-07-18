package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.dao.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.ItemDto;
import hr.algebra.waterworks.shared.sessionmodels.Cart;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("cart")
@AllArgsConstructor
@SessionAttributes("cart")
public class CartController {

    private WaterWorksService waterWorksService;

    @GetMapping("add")
    public String addItem(HttpServletRequest request, @RequestParam("itemId") int itemId, Model model) {

        Cart cart;
        if(model.getAttribute("cart") == null)
            cart = new Cart();
        else
            cart = (Cart) model.getAttribute("cart");

        var item = waterWorksService.getItem(itemId);

        assert cart != null;
        cart.getItems().add(item);
        cart.setText(item.name());

        model.addAttribute("cart", cart);
        model.addAttribute("cartText", "KART");

        return "redirect:" + request.getHeader("Referer");
    }

}
