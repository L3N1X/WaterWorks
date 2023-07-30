package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.sessionmodels.Cart;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("checkout")
@AllArgsConstructor
public class CheckoutController {

    private WaterWorksService waterWorksService;

    @GetMapping
    public String getCheckoutPage(HttpSession session, Model model) {
        Cart cart = (Cart)session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "checkout";
    }

}
