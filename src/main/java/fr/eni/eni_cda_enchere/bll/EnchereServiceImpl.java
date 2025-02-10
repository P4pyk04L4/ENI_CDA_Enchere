package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Enchere;
import fr.eni.eni_cda_enchere.dal.EnchereDAO;
import org.springframework.stereotype.Service;

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
        enchereDAO.create(enchere);
    }

    @Override
    public int getMeilleurPrix(int no_article){
        return enchereDAO.getMeilleurPrix(no_article);
    }

    @Override
    public String getMeilleurEnchereur(int no_article){
        return enchereDAO.getMeilleurEnchereur(no_article);
    }
}
