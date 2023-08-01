package hr.algebra.waterworks.controllers.view;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import hr.algebra.waterworks.dao.enums.PurchaseType;
import hr.algebra.waterworks.services.implementations.PaypalService;
import hr.algebra.waterworks.services.interfaces.PurchaseService;
import hr.algebra.waterworks.services.interfaces.WaterWorksService;
import hr.algebra.waterworks.shared.dtos.UserDto;
import hr.algebra.waterworks.shared.sessionmodels.Cart;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("purchase")
public class PurchaseController {
    private PurchaseService purchaseService;
    private WaterWorksService waterWorksService;
    private PaypalService paypalService;

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
                session.setAttribute("cart", new Cart());
                return "purchase-success";
            }
        }
        return "cash";
    }

    @GetMapping("paypal")
    public String purchaseCartViaPayPal(Model model, HttpSession session) {
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
                try {
                    Cart cart = (Cart)session.getAttribute("cart");
                    Payment payment = paypalService.createPayment(cart.getTotalPrice(), "EUR", "paypal", "sale", "Kupnja proizvoda u WaterWorks web", "http://localhost:6969", "http://localhost:6969/purchase/success");
                    for(Links link:payment.getLinks()) {
                        if(link.getRel().equals("approval_url")) {
                            return "redirect:"+link.getHref();
                        }
                    }
                    String receiptNumber = purchaseService.initPurchaseFromCart(
                            (Cart)session.getAttribute("cart"),
                            PurchaseType.PAYPAL, user);
                    model.addAttribute("receiptNumber", receiptNumber);
                    session.setAttribute("cart", new Cart());
                    return "purchase-success";
                } catch (PayPalRESTException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return "cash";
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

    @GetMapping("paypal-")
    public String getPaypalSuccessPage(Model model, @RequestParam("receiptnumner") String receiptNumber){
        model.addAttribute("receiptNumber", receiptNumber);
        return "purchase-success";
    }
}
