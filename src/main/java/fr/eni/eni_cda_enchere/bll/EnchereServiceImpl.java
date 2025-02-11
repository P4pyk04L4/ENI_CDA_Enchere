package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Enchere;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import fr.eni.eni_cda_enchere.dal.EnchereDAO;
import fr.eni.eni_cda_enchere.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {
    private final EnchereDAO enchereDAO;

    public EnchereServiceImpl(EnchereDAO enchereDAO) {
        this.enchereDAO = enchereDAO;
    }

    @Override
    public Enchere findEnchere(String id_utilisateur, long no_article, int montant) {
        return enchereDAO.read(id_utilisateur, no_article, montant);
    }

    @Override
    public List<Enchere> findAllEncheresFromUserAndArticle(String id_utilisateur, long no_article) {
        return enchereDAO.readAllFromUserAndArticle(id_utilisateur, no_article);
    }

    @Override
    public List<Enchere> findAllEncheresFromUser(String id_utilisateur) {
        return enchereDAO.readAllFromUser(id_utilisateur);
    }

    @Override
    public List<Enchere> findAllEncheresFromArticle(long no_article) {
        return enchereDAO.readAllFromArticle(no_article);
    }

    @Override
    public void createEnchere(Enchere enchere) {
        BusinessException be = new BusinessException();
        boolean isValid = true;
        isValid &= validerEnchere(enchere, be);
        isValid &= validerUser(enchere.getAcquereur().get(), be);
        isValid &= validerArticle(enchere.getArticleAVendre(), be);
        isValid &= validerDate(enchere.getDate(), be);
        isValid &= validerMontant(enchere, be);
        if (isValid) {
            enchereDAO.create(enchere);
        } else {
            be.add("Erreur lors de la création d'une nouvelle enchère.");
        }

    }

    @Override
    public int getMeilleurPrix(int no_article){
        return enchereDAO.getMeilleurPrix(no_article);
    }

    @Override
    public String getMeilleurEnchereur(int no_article){
        return enchereDAO.getMeilleurEnchereur(no_article);
    }

    /**
     * Méthodes de validation des BO
     */

    private boolean validerEnchere(Enchere e, BusinessException be) {
        if(e == null){
            be.add("Enchère null.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerUser(Utilisateur u, BusinessException be) {
        if(u == null){
            be.add("Utilisateur null.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerArticle(ArticleAVendre a, BusinessException be) {
        if(a == null){
            be.add("Article null.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerDate(LocalDateTime d, BusinessException be){
        if(d == null){
            be.add("Date null.");
            return false;
        } else if (d.isBefore(LocalDateTime.now())){
            be.add("Date invalide.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerMontant( Enchere e, BusinessException be) {
        int no_article = e.getArticleAVendre().getNo_article();
        int meilleure_offre = enchereDAO.getMeilleurPrix(no_article);

        if(e.getMontant() == 0){
            be.add("Montant null.");
            return false;
        } else if ( e.getMontant() < meilleure_offre){
            be.add("Montant inférieur à l'offre précédente.");
            return false;
        } else {
            return true;
        }
    }
}
