package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Enchere;
import java.util.List;

public interface EnchereDAO {
    Enchere read(String id_utilisateur, long no_article, int montant);
    List<Enchere> readAllFromUserAndArticle(String id_utilisateur, long no_article);
    List<Enchere> readAllFromUser(String id_utilisateur);
    List<Enchere> readAllFromArticle(long no_article);
    Enchere getLastEnchereFromUserAndArticle(String id_utilisateur, int no_article);
    void create(Enchere enchere);
    int getMeilleurPrix(int no_article);
    String getMeilleurEnchereur(int no_article);
}
