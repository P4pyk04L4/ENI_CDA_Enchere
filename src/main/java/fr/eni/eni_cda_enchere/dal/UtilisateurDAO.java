package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurDAO {
    void create(Utilisateur utilisateur);

    Optional<Utilisateur> findByPseudo(String pseudo);

    List<Utilisateur> findAll();

    void update(Utilisateur utilisateur);

    void delete(String pseudo);

}
