package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Utilisateur;

import java.util.List;

public interface ArticleService {

    ArticleAVendre getArticleAVendre(int id);

    List<ArticleAVendre> getAllArticleAVendre();

    List<ArticleAVendre> getAllActiveArticleAVendre();

    List<ArticleAVendre> getFilteredArticleAVendre(int noCategorie, String nom);

    int createArticleAVendre(ArticleAVendre articleAVendre);

    void updateArticleAVendre(ArticleAVendre articleAVendre);

    void deleteArticleAVendre(int id);

}
