package hr.algebra.waterworks.controllers.view;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {

    @GetMapping("login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("register")
    public String getRegisterPage(){
        return "register";
    }

}
