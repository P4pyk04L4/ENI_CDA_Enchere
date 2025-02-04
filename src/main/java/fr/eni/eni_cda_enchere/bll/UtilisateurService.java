package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    void createUser(Utilisateur user);
    Optional<Utilisateur> findByPseudo(String pseudo);
    List<Utilisateur> findAll();
    void updateUser(Utilisateur user);
    void deleteUser(String pseudo);
}
