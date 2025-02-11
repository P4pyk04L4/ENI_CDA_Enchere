package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Enchere;
import fr.eni.eni_cda_enchere.dal.ArticleAVendreDAO;
import fr.eni.eni_cda_enchere.dal.EnchereDAO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleAVendreDAO articleAVendreDAO;
    private final EnchereDAO enchereDAO;

    public ArticleServiceImpl(ArticleAVendreDAO articleAVendreDAO, EnchereDAO enchereDAO) {
        this.articleAVendreDAO = articleAVendreDAO;
        this.enchereDAO = enchereDAO;
    }

    @Override
    public ArticleAVendre getArticleAVendre(int id) {
        return articleAVendreDAO.getArticleAVendre(id);
    }

    @Override
    public List<ArticleAVendre> getAllArticleAVendre() {
        return articleAVendreDAO.getAllArticleAVendre();
    }

    @Override
    public List<ArticleAVendre> getAllActiveArticleAVendre() {
        return articleAVendreDAO.getAllActiveArticleAVendre();
    }

    @Override
    public List<ArticleAVendre> getFilteredArticleAVendre(int noCategorie, String nom) {
        return articleAVendreDAO.getFilteredArticleAVendre(noCategorie, nom);
    }

    @Override
    public int createArticleAVendre(ArticleAVendre articleAVendre) {
        return articleAVendreDAO.createArticleAVendre(articleAVendre);
    }

    @Override
    public void updateArticleAVendre(ArticleAVendre articleAVendre) {
        articleAVendreDAO.updateArticleAVendre(articleAVendre);
    }

    @Override
    public void deleteArticleAVendre(int id) {
        articleAVendreDAO.deleteArticleAVendre(id);
    }

    @Override
    public List<ArticleAVendre> getAllUnactiveArticles() {
        return articleAVendreDAO.getAllUnactiveArticle();
    }

    //T'as juste à récup la liste de tous les articles et de faire un for each dessus puis de tester chaque article dans
    //le CRON
    @Override
    public void updateEnchereClosedStatus(ArticleAVendre a) {
        if (a.getStatut_enchere() == 1){
            if (a.getDate_fin_encheres().isAfter(LocalDate.now())) {
                a.setStatut_enchere(2);
            }
        }
    }
}
