package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Enchere;
import fr.eni.eni_cda_enchere.dal.ArticleAVendreDAO;
import fr.eni.eni_cda_enchere.dal.EnchereDAO;
import fr.eni.eni_cda_enchere.exceptions.BusinessCode;
import fr.eni.eni_cda_enchere.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
    public List<ArticleAVendre> getFilteredArticleAVendre(String pseudo, int noCategorie, String nom, String achatVente, int critere) {
        return articleAVendreDAO.getFilteredArticleAVendre(pseudo, noCategorie, nom, achatVente, critere);
    }

    @Override
    public List<ArticleAVendre> cronGetAllArticles() {
        return articleAVendreDAO.cronGetAllArticles();
    }

    @Override
    public void cronUpdateArticleAVendre(ArticleAVendre articleAVendre) {
        articleAVendreDAO.cronUpdateArticleAVendre(articleAVendre);
    }

    @Override
    public int createArticleAVendre(ArticleAVendre articleAVendre) {

        BusinessException be = new BusinessException();
        boolean isValid = true;

        isValid &= validerArticle(articleAVendre, be);
        isValid &= validerNomArticle(articleAVendre.getNom_article(), be);
        isValid &= validerDescriptionArticle(articleAVendre.getDescription(), be);
        isValid &= validerDateDebutArticle(articleAVendre.getDate_debut_encheres(), be);
        isValid &= validerDateFinArticle(articleAVendre.getDate_fin_encheres(), be);
        isValid &= validerPrixVenteArticle(articleAVendre.getPrix_initial(), be);

        if (isValid) {
            return articleAVendreDAO.createArticleAVendre(articleAVendre);
        } else {
            throw be;
        }

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

    /**
     * METHODES DE VALIDATION DES BO
     */

    private boolean validerArticle(ArticleAVendre art, BusinessException be) {
        if(art == null){
            be.add(BusinessCode.VALIDATION_ARTICLE_NULL);
            return false;
        }
        return true;
    }

    private boolean validerNomArticle(String nom, BusinessException be){
        if(nom == null || nom.isBlank()){
            be.add(BusinessCode.VALIDATION_ARTICLE_NOM_BLANK);
            return false;
        }
        if(nom.length() < 5 || nom.length() > 30){
            be.add(BusinessCode.VALIDATION_ARTICLE_NOM_LONGUEUR);
            return false;
        }
        return true;
    }

    private boolean validerDescriptionArticle(String desc, BusinessException be){
        if(desc == null || desc.isBlank()){
            be.add(BusinessCode.VALIDATION_ARTICLE_DESCR_BLANK);
            return false;
        }
        if(desc.length() < 5 || desc.length() > 300){
            be.add(BusinessCode.VALIDATION_ARTICLE_DESCR_LONGUEUR);
            return false;
        }
        return true;
    }

    private boolean validerDateDebutArticle(LocalDate dateDebut, BusinessException be){
        if(dateDebut == null){
            be.add(BusinessCode.VALIDATION_ARTICLE_DATE_DEBUT);
            return false;
        }
        return true;
    }

    private boolean validerDateFinArticle(LocalDate dateFin, BusinessException be){
        if(dateFin == null){
            be.add(BusinessCode.VALIDATION_ARTICLE_DATE_FIN);
            return false;
        }
        return true;
    }

    private boolean validerPrixVenteArticle(int prixInitial, BusinessException be){
        if(prixInitial <= 0){
            be.add(BusinessCode.VALIDATION_ARTICLE_PRIX_VENTE);
            return false;
        }
        return true;
    }
}
