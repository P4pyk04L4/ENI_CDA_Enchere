package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.*;
import fr.eni.eni_cda_enchere.bo.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("/articles")
@SessionAttributes({"sessionUser"})
public class ArticleController {

    private final UtilisateurService utilisateurService;
    private final AdresseService adresseService;
    private final ArticleService articleService;
    private final EnchereService enchereService;
    private final CategorieService categorieService;

    public ArticleController(UtilisateurService utilisateurService,
                             AdresseService adresseService,
                             ArticleService articleService,
                             EnchereService enchereService, CategorieService categorieService) {
        this.utilisateurService = utilisateurService;
        this.adresseService = adresseService;
        this.articleService = articleService;
        this.enchereService = enchereService;
        this.categorieService = categorieService;
    }

    @GetMapping("/detail_enchere/{noArticle}")
    public String detailArticle(
            @PathVariable int noArticle,
            @ModelAttribute Enchere enchere,
            @ModelAttribute ArticleAVendre article,
            Model model) {

        ArticleAVendre a = articleService.getArticleAVendre(noArticle);

        if(a == null) {
            return "redirect:/";
        } else {
            String connected_username = utilisateurService.getConnectedUsername();
            int meilleur_prix = enchereService.getMeilleurPrix(noArticle);
            String meilleur_enchereur = enchereService.getMeilleurEnchereur(noArticle);

            a.setMeilleure_offre(meilleur_prix);
            model.addAttribute("article", a);
            System.out.println(a);
            System.out.println(a.getVendeur());

            if(!Objects.equals(a.getVendeur().getPseudo(), connected_username)){
                model.addAttribute("acquereur", true);
            } else {
                model.addAttribute("acquereur", false);
            }

            if(!Objects.equals(meilleur_enchereur, connected_username)){
                model.addAttribute("enchere_lead", false);
            } else {
                model.addAttribute("enchere_lead", true);
            }

            return "article/view-detail-enchere";
        }
    }

    @PostMapping("/bid/{noArticle}")
    public String bidArticle(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int noArticle,
            @ModelAttribute("enchere") Enchere enchere,
            BindingResult bindingResult
    ){
        String connected_username = utilisateurService.getConnectedUsername();
        Optional<Utilisateur> connected_user = utilisateurService.findByPseudo(connected_username);

        if(bindingResult.hasErrors()) {
            return "article/view-detail-enchere";
        } else {
            ArticleAVendre a = articleService.getArticleAVendre(noArticle);
            if(a == null) {
                return "article/view-detail-enchere";
            } else {
                LocalDateTime now = LocalDateTime.now();
                enchere.setArticleAVendre(a);
                enchere.setAcquereur(connected_user);
                enchere.setDate(now);
                System.out.println("Enchère créée : " + enchere);
                enchereService.createEnchere(enchere);

                return "redirect:/articles/detail_enchere/" + noArticle;
            }
        }
    }

    @GetMapping("/creer")
    public String creerArticle(
            Model model,
            @AuthenticationPrincipal
            UserDetails userDetails
    ) {
        Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();
        ArticleAVendre article = new ArticleAVendre();
        article.setRetrait(utilisateur.getAdresse());
        article.setVendeur(utilisateur);

        List<Categorie> categorieList = categorieService.findAllCategories();

        List<Adresse> adressesList = adresseService.getEniAdresses();
        adressesList.add(0, utilisateur.getAdresse());

        model.addAttribute("article", article);
        model.addAttribute("categorieList", categorieList);
        model.addAttribute("adressesList", adressesList);
        return "article/view-article-creation";
    }

    @PostMapping("/creer")
    public String enregistrerArticle(
            @Valid @ModelAttribute("article") ArticleAVendre article,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Erreur = " + bindingResult.getAllErrors());
            Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();

            article.setRetrait(utilisateur.getAdresse());
            article.setVendeur(utilisateur);

            List<Categorie> categorieList = categorieService.findAllCategories();

            List<Adresse> adressesList = adresseService.getEniAdresses();
            adressesList.add(0, utilisateur.getAdresse());

            model.addAttribute("article", article);
            model.addAttribute("categorieList", categorieList);
            model.addAttribute("adressesList", adressesList);
            return "article/view-article-creation";
        }


        if(article.getDate_debut_encheres().isEqual(LocalDate.now())){
            article.setStatut_enchere(1);
        }

        // Récupère l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();
        article.setVendeur(utilisateur);

        // Crée l'article dans la base de données
        int no_Article = articleService.createArticleAVendre(article);

        return "redirect:/articles/detail_enchere/" + no_Article;
    }

    @GetMapping({"/modifier/", "/modifier/{idArticle}"})
    public String modifierArticle(
            @PathVariable int idArticle,
            Model model
    ) {
        ArticleAVendre articleAVendre = articleService.getArticleAVendre(idArticle);

        List<Adresse> adressesList = adresseService.getEniAdresses();
        adressesList.add(0, articleAVendre.getRetrait());

        List<Categorie> categorieList = categorieService.findAllCategories();

        model.addAttribute("article", articleAVendre);
        model.addAttribute("adressesList", adressesList);
        model.addAttribute("categorieList", categorieList);

        return "article/view-article-creation";
    }

    @PostMapping({"/modifier/", "/modifier/{idArticle}"})
    public String enregistrerModificationsArticle(
            @PathVariable int idArticle,
            @Valid @ModelAttribute("article") ArticleAVendre article,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ){
        if (bindingResult.hasErrors()) {
            ArticleAVendre articleAVendre = articleService.getArticleAVendre(idArticle);

            List<Adresse> adressesList = adresseService.getEniAdresses();
            adressesList.add(0, articleAVendre.getRetrait());

            List<Categorie> categorieList = categorieService.findAllCategories();

            model.addAttribute("article", articleAVendre);
            model.addAttribute("adressesList", adressesList);
            model.addAttribute("categorieList", categorieList);
            return "article/view-article-creation";
        }

        // Récupère l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();
        article.setVendeur(utilisateur);

        articleService.updateArticleAVendre(article);

        return "redirect:/articles/detail_enchere/" + idArticle;
    }
}