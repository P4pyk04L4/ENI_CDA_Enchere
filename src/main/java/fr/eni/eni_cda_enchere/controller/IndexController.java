package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.ArticleService;
import fr.eni.eni_cda_enchere.bll.EnchereService;
import fr.eni.eni_cda_enchere.bll.UtilisateurService;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
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

    public IndexController(ArticleService articleService, EnchereService enchereService, UtilisateurService utilisateurService) {
        this.articleService = articleService;
        this.enchereService = enchereService;
        this.utilisateurService = utilisateurService;
    }


    @GetMapping("/")
    public String showIndex(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
            ){
        List<ArticleAVendre> articleAVendreList = articleService.getAllArticleAVendre();
        for (ArticleAVendre articleAVendre : articleAVendreList) {
            int meilleure_offre = enchereService.getMeilleurPrix(articleAVendre.getNo_article());
            articleAVendre.setMeilleure_offre(meilleure_offre);
        }
        model.addAttribute("articleAVendreList", articleAVendreList);

        return "index";
    }

}
