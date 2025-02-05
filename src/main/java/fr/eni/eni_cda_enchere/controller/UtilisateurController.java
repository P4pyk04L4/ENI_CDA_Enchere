package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.UtilisateurService;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

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
        return "profil/view-utilisateurs";
    }

    @GetMapping("/profilpseudo")
    public String findByPseudo(@RequestParam String pseudo, Model model) {
        Optional<Utilisateur> utilisateur = utilisateurService.findByPseudo(pseudo);

            model.addAttribute("utilisateur", utilisateur.get());
            return "profil/view-profil";
    }

    // /edit/{pseudo} mais il y a Get et Post differents
    @GetMapping("/edit/{pseudo}")
    public String showEditForm(@PathVariable String pseudo, Model model) {
        Optional<Utilisateur> utilisateur = utilisateurService.findByPseudo(pseudo);
        model.addAttribute("utilisateur", utilisateur.orElse(null));
        return "profil/view-editProfil";
    }

    /*
    @PostMapping("/edit/{pseudo}")
    public String updateProfil(@PathVariable String pseudo, @Valid @ModelAttribute Utilisateur utilisateur, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editprofil";
        }
        utilisateur.setPseudo(pseudo);
        utilisateurService.updateUser(utilisateur);
        return "redirect:/utilisateurs/profilpseudo?pseudo=" + pseudo;
    }
*/


}
