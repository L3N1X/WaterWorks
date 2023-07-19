package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.sessionmodels.Cart;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("cart")
@AllArgsConstructor
@SessionAttributes("cart")
public class CartController {

    private WaterWorksService waterWorksService;

    @GetMapping("add/{itemId}")
    public String addItem(HttpSession session, HttpServletRequest request, @PathVariable int itemId, Model model) {

        if(session.getAttribute("cart") == null)
            session.setAttribute("cart", new Cart());
        Cart cart = (Cart)session.getAttribute("cart");

        var item = waterWorksService.getItem(itemId);

        if (cart.getItems().containsKey(item)) {
            Integer amount = cart.getItems().get(item);
            cart.getItems().put(item, ++amount);
        } else {
            cart.getItems().put(item, 1);
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("addMore/{itemId}")
    public String addMoreItem(HttpSession session, @PathVariable int itemId, Model model){
        Cart cart = (Cart)session.getAttribute("cart");
        var item = waterWorksService.getItem(itemId);
        Integer amount = cart.getItems().get(item);
        amount++;
        cart.getItems().put(item, amount);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("remove/{itemId}")
    public String removeItem(HttpSession session, HttpServletRequest request, @PathVariable int itemId, Model model){
        Cart cart = (Cart)session.getAttribute("cart");
        var item = waterWorksService.getItem(itemId);
        Integer amount = cart.getItems().get(item);
        amount--;
        if(amount == 0){
            cart.getItems().remove(item);
        } else {
            cart.getItems().put(item, amount);
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping
    public String getCartPage(HttpSession session, Model model) {
        if(session.getAttribute("cart") == null)
            session.setAttribute("cart", new Cart());
        Cart cart = (Cart)session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "cart";
    }

}
