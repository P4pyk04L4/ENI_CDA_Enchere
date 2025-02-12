package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.ArticleAVendre;

import java.util.List;

public interface ArticleAVendreDAO {

    ArticleAVendre getArticleAVendre(int id);

    List<ArticleAVendre> getAllArticleAVendre();

    List<ArticleAVendre> getAllActiveArticleAVendre();

    List<ArticleAVendre> getAllUnactiveArticle();

    List<ArticleAVendre> getFilteredArticleAVendre(String pseudo, int noCategorie, String nom, String achatVente, int critere);

    List<ArticleAVendre> cronGetAllArticles();

    void cronUpdateArticleAVendre(ArticleAVendre articleAVendre);

    int createArticleAVendre(ArticleAVendre articleAVendre);

    void updateArticleAVendre(ArticleAVendre articleAVendre);

    void deleteArticleAVendre(int id);
}
