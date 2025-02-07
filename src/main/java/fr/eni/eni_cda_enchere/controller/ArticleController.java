package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.*;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final UtilisateurService utilisateurService;
    private final AdresseService adresseService;
    private final ArticleService articleService;
    private final EnchereService enchereService;
    private final CategorieService categorieService;

    public ArticleController(UtilisateurService utilisateurService, AdresseService adresseService,
                             ArticleService articleService, EnchereService enchereService,
                             CategorieService categorieService) {
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
}
