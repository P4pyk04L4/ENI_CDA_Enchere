package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.UtilisateurService;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/utilisateurs")public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<Utilisateur> utilisateurs = utilisateurService.findAll();
        model.addAttribute("utilisateurs", utilisateurs);
        return "contexte/view-utilisateurs"; // utilisateur/list.html にデータを渡す
    }
}
