package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.UtilisateurService;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/utilisateurs")
@SessionAttributes({"utilisateurAModifier"})
public class UtilisateurController {

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
    public String findByPseudo(
            @RequestParam
            String pseudo,
            Model model
    ) {
        Optional<Utilisateur> utilisateur = utilisateurService.findByPseudo(pseudo);

        model.addAttribute("utilisateur", utilisateur.get());
        return "profil/view-profil";
    }

    // /edit/{pseudo} mais il y a Get et Post differents
    @GetMapping("/edit/{pseudo}")
    public String showEditForm(
        @PathVariable
        String pseudo,
        Model model)
    {
        Optional<Utilisateur> utilisateur = utilisateurService.findByPseudo(pseudo);
        model.addAttribute("utilisateurAModifier", utilisateur.orElse(null));

        return "profil/view-editProfil";
    }

    @PostMapping("/edit/{pseudo}")
    public String updateProfil(
            @PathVariable
            String pseudo,
            @Valid
            @ModelAttribute("utilisateurAModifier")
            Utilisateur utilisateurAModifier,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return "editprofil";
        }
        utilisateurService.updateUser(utilisateurAModifier);
        return "redirect:/utilisateurs/profilpseudo?pseudo=" + pseudo;
    }

    // AJOUT D'UNE SECURITE POUR N'AFFICHER QUE SON PROPRE PROFIL
    @GetMapping("/myProfile")
    public String showMyProfile(
            Model model,
            @AuthenticationPrincipal
            UserDetails userDetails
    ) {
        Optional<Utilisateur> utilisateur = utilisateurService.findByPseudo(userDetails.getUsername());

        model.addAttribute("utilisateur", utilisateur.get());
        return "profil/view-profil";
    }

    @GetMapping("/edit/myProfile")
    public String editMyProfile(
            Model model,
            @AuthenticationPrincipal
            UserDetails userDetails)
    {
        Optional<Utilisateur> utilisateur = utilisateurService.findByPseudo(userDetails.getUsername());
        model.addAttribute("utilisateurAModifier", utilisateur.orElse(null));

        return "profil/view-editProfil";
    }

    @PostMapping("/edit/myProfile")
    public String updateMyProfile(
            @Valid
            @ModelAttribute("utilisateurAModifier")
            Utilisateur utilisateurAModifier,
            BindingResult bindingResult,
            @AuthenticationPrincipal
            UserDetails userDetails)
    {
        if (bindingResult.hasErrors()) {
            return "editprofil";
        }
        utilisateurService.updateByUser(utilisateurAModifier);
        return "redirect:/utilisateurs/profilpseudo?pseudo=" + userDetails.getUsername();
    }

    @GetMapping("/public/profil/{pseudo}")
    public String showPublicProfile(
            @PathVariable String pseudo,
            Model model
    ) {
        Optional<Utilisateur> utilisateur = utilisateurService.findByPseudo(pseudo);

        if (utilisateur.isPresent()) {
            model.addAttribute("publicUtilisateur", utilisateur.get());
            return "profil/view-publicProfil";
        } else {
            System.out.println("Utilisateur not found for pseudo: " + pseudo);  // デバッグ用
            return "redirect:/error"; // cas d'utilisateur n'exist pas
        }

    }


}
