package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Enchere;

import java.time.LocalDateTime;
import java.util.List;

public interface EnchereService {
    Enchere findEnchere(String id_utilisateur, long no_article, int montant);
    List<Enchere> findAllEncheresFromUserAndArticle(String id_utilisateur, long no_article);
    List<Enchere> findAllEncheresFromUser(String id_utilisateur);
    List<Enchere> findAllEncheresFromArticle(long no_article);
    void createEnchere(Enchere enchere, int no_article, int ancien_montant);
    int getMeilleurPrix(int no_article);
    String getMeilleurEnchereur(int no_article);
}
