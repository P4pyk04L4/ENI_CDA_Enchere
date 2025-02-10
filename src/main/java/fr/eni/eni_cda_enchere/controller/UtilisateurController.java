package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.AdresseService;
import fr.eni.eni_cda_enchere.bll.ArticleService;
import fr.eni.eni_cda_enchere.bll.UtilisateurService;
import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import fr.eni.eni_cda_enchere.exceptions.BusinessException;
import fr.eni.eni_cda_enchere.form.UserPasswordForm;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/utilisateurs")
@SessionAttributes({"utilisateurAModifier", "userSession"})
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final AdresseService adresseService;
    private final PasswordEncoder passwordEncoder;
    private final ArticleService articleService;

    public UtilisateurController(UtilisateurService utilisateurService, AdresseService adresseService, PasswordEncoder passwordEncoder, ArticleService articleService) {
        this.utilisateurService = utilisateurService;
        this.adresseService = adresseService;
        this.passwordEncoder = passwordEncoder;
        this.articleService = articleService;
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

        // VOIR POUR AFFICHER LES VENTES DE L'UTILISATEUR CONNECTE !!!

        if(utilisateur.isPresent()) {
            List<ArticleAVendre> articlesEnVente = articleService.getFilteredArticleAVendre(0, utilisateur.get().getPseudo());
            model.addAttribute("articlesEnVente", articlesEnVente);
        }

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

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("adresse", new Adresse());
        return "profil/view-register";
    }


    @GetMapping("edit/myProfile/myPassword")
    public String editPassword (
            Model model
    ){
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



    @Transactional
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("utilisateur") Utilisateur user,
            BindingResult userBindingResult,
            @Valid @ModelAttribute("adresse") Adresse address,
            BindingResult addressBindingResult
    ) {
        if (addressBindingResult.hasErrors()) {
            return "profil/view-register";
        } else {
           try {
               int idAddress = adresseService.createAdresse(address);
               System.out.println(address);


           } catch (BusinessException e) {
               e.getClefsExternalisations().forEach(key -> {
                   ObjectError err = new ObjectError("globalError", key);
                   addressBindingResult.addError(err);
               });
           }
            if (userBindingResult.hasErrors()) {
                return "profil/view-register";
            } else {
                try{
                    user.setAdresse(address);
                    user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
                    utilisateurService.createUser(user);
                    System.out.println(user);
                } catch (BusinessException e){
                    e.getClefsExternalisations().forEach( key -> {
                        ObjectError err = new ObjectError("globalError", key);
                        userBindingResult.addError(err);
                    });
                    return "profil/view-register";
                }
            }
            return "profil/view-register";
        }
    }
}
