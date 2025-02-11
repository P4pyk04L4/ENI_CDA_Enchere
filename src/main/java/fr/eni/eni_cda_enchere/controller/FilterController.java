package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.ArticleService;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilterController {

    private final ArticleService articleService;

    public FilterController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    public List<ArticleAVendre> filterArticles(
            @RequestBody ArticleRequest request
            ) {
        return articleService.getFilteredArticleAVendre(request.getNoCategorie(), request.getSearchText());
    }

    // CLASSE POUR CONSTRUIRE LA REQUETE

    public static class ArticleRequest {
            private int noCategorie;
            private String searchText;

        public int getNoCategorie() {
            return noCategorie;
        }

        public void setNoCategorie(int noCategorie) {
            this.noCategorie = noCategorie;
        }

        public String getSearchText() {
            return searchText;
        }

        public void setSearchText(String searchText) {
            if (searchText != null && searchText.trim().isEmpty()) {
                this.searchText = null;
            } else {
                this.searchText = searchText;
            }
        }
    }

}
