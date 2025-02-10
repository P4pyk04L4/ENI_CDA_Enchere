package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.dal.ArticleAVendreDAO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleAVendreDAO articleAVendreDAO;

    public ArticleServiceImpl(ArticleAVendreDAO articleAVendreDAO) {
        this.articleAVendreDAO = articleAVendreDAO;
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
}
