package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.ArticleService;
import fr.eni.eni_cda_enchere.bll.EnchereService;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.custom.ArticleFilteredResponse;
import fr.eni.eni_cda_enchere.bo.custom.ArticleRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilterController {

    private final ArticleService articleService;
    private final EnchereService enchereService;

    public FilterController(ArticleService articleService, EnchereService enchereService) {
        this.articleService = articleService;
        this.enchereService = enchereService;
    }

    @PostMapping("/articles")
    public List<ArticleFilteredResponse> filterArticles(
            @RequestBody ArticleRequest request
            ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean estAuthentifie = auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"));

        List<ArticleAVendre> articlesFiltres = articleService.getFilteredArticleAVendre(request.getNoCategorie(), request.getSearchText());
        List<ArticleFilteredResponse> articleARetourner = new ArrayList<ArticleFilteredResponse>();

        for (ArticleAVendre articleAVendre : articlesFiltres) {
            int meilleure_offre = enchereService.getMeilleurPrix(articleAVendre.getNo_article());
            articleAVendre.setMeilleure_offre(meilleure_offre);
        }

        for (ArticleAVendre a : articlesFiltres) {
            articleARetourner.add(new ArticleFilteredResponse(a.getNo_article(), a.getNom_article(), a.getPrix_initial(), a.getMeilleure_offre(), a.getDate_fin_encheres(), a.getVendeur().getPseudo(), a.getCategorie().getNo_categorie(), estAuthentifie));
        }

        return articleARetourner;
    }

}
