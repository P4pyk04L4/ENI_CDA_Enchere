package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.ArticleService;
import fr.eni.eni_cda_enchere.bll.CategorieService;
import fr.eni.eni_cda_enchere.bll.EnchereService;
import fr.eni.eni_cda_enchere.bll.UtilisateurService;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Categorie;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import fr.eni.eni_cda_enchere.form.ArticlesFilterForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes({"sessionUser"})
public class IndexController {

    private ArticleService articleService;
    private EnchereService enchereService;
    private UtilisateurService utilisateurService;
    private CategorieService categorieService;

    public IndexController(ArticleService articleService, EnchereService enchereService, UtilisateurService utilisateurService, CategorieService categorieService) {
        this.articleService = articleService;
        this.enchereService = enchereService;
        this.utilisateurService = utilisateurService;
        this.categorieService = categorieService;
    }


    @GetMapping("/")
    public String showIndex(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
            ){
        List<ArticleAVendre> articleAVendreList = articleService.getAllActiveArticleAVendre();
        for (ArticleAVendre articleAVendre : articleAVendreList) {
            int meilleure_offre = enchereService.getMeilleurPrix(articleAVendre.getNo_article());
            articleAVendre.setMeilleure_offre(meilleure_offre);
        }

        ArticlesFilterForm filterForm = new ArticlesFilterForm();
        List<Categorie> categorieList = categorieService.findAllCategories();

        model.addAttribute("filterForm", filterForm);
        model.addAttribute("categorieList", categorieList);
        model.addAttribute("articleAVendreList", articleAVendreList);

        return "index";
    }

}
