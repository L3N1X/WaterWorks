package hr.algebra.waterworks.controllers.view;

import hr.algebra.waterworks.dao.enums.PurchaseType;
import hr.algebra.waterworks.services.interfaces.PurchaseService;
import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.sessionmodels.Cart;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("purchase")
public class PurchaseController {
    private PurchaseService purchaseService;
    private WaterWorksService waterWorksService;

    @GetMapping("cash")
    public String purchaseCartForCash(Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = "";
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else {
                username = principal.toString();
            }
            UserDto user = waterWorksService.getUser(username);
            if(user != null){
                String receiptNumber = purchaseService.initPurchaseFromCart(
                        (Cart)session.getAttribute("cart"),
                        PurchaseType.CASH, user);
                model.addAttribute("receiptNumber", receiptNumber);
                return "purchase-success";
            }
        }
        return "cash";
    }

    @GetMapping("paypal")
    public String purchaseCartViaPayPal(HttpSession session) {
        return "pay-pal";
    }

    @GetMapping("receipts")
    public String getMyReceiptsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = "";
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else {
                username = principal.toString();
            }
            model.addAttribute("receipts", purchaseService.getReceipts(username));
            return "manage-receipts";
        }
        return "redirect:/";
    }
}
