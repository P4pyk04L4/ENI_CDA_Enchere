package fr.eni.eni_cda_enchere.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"sessionUser"})
public class LoginController {

    //Ici on fait en sorte que le login par défaut soit remplacé par notre page login.html
    @GetMapping("/login")
    String login(){
        return "login";
    }
}
