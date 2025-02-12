package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.ArticleService;
import fr.eni.eni_cda_enchere.bll.EnchereService;
import fr.eni.eni_cda_enchere.bll.UtilisateurService;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import fr.eni.eni_cda_enchere.bo.custom.ArticleFilteredResponse;
import fr.eni.eni_cda_enchere.bo.custom.ArticleRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UtilisateurService utilisateurService;

    public FilterController(ArticleService articleService, EnchereService enchereService, UtilisateurService utilisateurService) {
        this.articleService = articleService;
        this.enchereService = enchereService;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/articles")
    public List<ArticleFilteredResponse> filterArticles(
            @RequestBody ArticleRequest request,
            @AuthenticationPrincipal
            UserDetails userDetails
            ) {
        // VERIFICATION AUTHENTIFICATION
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean estAuthentifie = auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"));


        String achatVente = "achat";
        String pseudo = null;
        // VERIFICATION UTILISATEUR CONNECTE ET CRITERES
        if (estAuthentifie){
            pseudo = userDetails.getUsername();
            if(!request.isEstAchat() && request.isEstVente()) {
                achatVente = "vente";
            }
        }

        // ENVOI DE LA RECHERCHE
        List<ArticleAVendre> articlesFiltres = articleService.getFilteredArticleAVendre(pseudo, request.getNoCategorie(), request.getSearchText(), achatVente, request.getCritere());
        List<ArticleFilteredResponse> articleARetourner = new ArrayList<>();

        for (ArticleAVendre a : articlesFiltres) {
            int meilleure_offre = enchereService.getMeilleurPrix(a.getNo_article());
            a.setMeilleure_offre(meilleure_offre);

            articleARetourner.add(new ArticleFilteredResponse(a.getNo_article(),
                    a.getNom_article(), a.getPrix_initial(), a.getMeilleure_offre(),
                    a.getDate_fin_encheres(), a.getVendeur().getPseudo(),
                    a.getCategorie().getNo_categorie(), estAuthentifie));
        }

        return articleARetourner;
    }

}
