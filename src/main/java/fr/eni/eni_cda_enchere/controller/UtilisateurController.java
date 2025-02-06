package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.UtilisateurService;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import fr.eni.eni_cda_enchere.form.UserPasswordForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/utilisateurs")
@SessionAttributes({"utilisateurAModifier"})
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurController(UtilisateurService utilisateurService, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1) {
        this.utilisateurService = utilisateurService;
        this.passwordEncoder = passwordEncoder1;
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
            return "profil/view-editProfil";
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

    @GetMapping("edit/myProfile/myPassword")
    public String editPassword(
            Model model
    ) {
        model.addAttribute("userPasswordForm", new UserPasswordForm());
        return "/profil/view-edit-password";
    }

    @PostMapping("edit/myProfile/myPassword")
    public String updatePassword(
            @AuthenticationPrincipal
            UserDetails userDetails,
            @Valid
            @ModelAttribute("userPasswordForm")
            UserPasswordForm userPasswordForm,
            BindingResult bindingResult,
            Model model
            ){
        if (bindingResult.hasErrors()) {
            return "/profil/view-edit-password";
        }

        Utilisateur utilisateurConnecte = utilisateurService.findByPseudo(userDetails.getUsername()).get();
        String motDePasseInitial = utilisateurConnecte.getMotDePasse();

        if(motDePasseInitial != null){

            if(passwordEncoder.matches(userPasswordForm.getMotDePasseActuel(), motDePasseInitial)){
                if(userPasswordForm.getMotDePasseNouveau().equals(userPasswordForm.getMotDePasseConfirmation())){

                    utilisateurConnecte.setMotDePasse(passwordEncoder.encode(userPasswordForm.getMotDePasseNouveau()));
                    utilisateurService.updatePassword(utilisateurConnecte);

                } else {
                    bindingResult.addError(new ObjectError("global", "Les mots de passe ne correspondent pas."));
                    System.out.println("ERREUR : Les mots de passe ne correspondent pas.");
                    return "profil/view-edit-password";
                }
            } else {
                bindingResult.addError(new ObjectError("global","Le mot de passe actuel est incorrect"));
                System.out.println("ERREUR : Le mot de passe actuel est incorrect");
                return "profil/view-edit-password";
            }
        }

        return "redirect:/utilisateurs/myProfile";
    }


}
