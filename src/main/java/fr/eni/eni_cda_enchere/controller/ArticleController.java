package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.*;
import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Categorie;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/articles")
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
            Model model) {
        ArticleAVendre a = articleService.getArticleAVendre(noArticle);

        if(a == null) {
            return "redirect:/";
        } else {
            int meilleur_prix = enchereService.getMeilleurPrix(noArticle);
            a.setMeilleure_offre(meilleur_prix);
            model.addAttribute("article", a);
            System.out.println(a);
            System.out.println(a.getVendeur());
            return "article/view-detail-enchere";
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
            @RequestParam("noCategorie") int noCategorie, // Récupère l'ID de la catégorie
            @RequestParam("noRetrait") int noAdresse, // Récupère l'ID de l'adresse
            @Valid @ModelAttribute("article") ArticleAVendre article,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        if (bindingResult.hasErrors()) {
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

        // Récupère les objets Categorie et Adresse à partir de leurs IDs
        Categorie categorie = categorieService.findByNoCategory(noCategorie);
        Adresse adresse = adresseService.getAdresse(noAdresse);

        // Assigne les objets à l'article
        article.setCategorie(categorie);
        article.setRetrait(adresse);


        // Récupère l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();
        article.setVendeur(utilisateur);

        // Crée l'article dans la base de données
        int no_Article = articleService.createArticleAVendre(article);

        return "redirect:/articles/detail_enchere/" + no_Article;
    }
}