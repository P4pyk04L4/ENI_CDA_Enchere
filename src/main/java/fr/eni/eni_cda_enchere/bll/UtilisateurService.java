package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Utilisateur;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    void createUser(Utilisateur user);

    Optional<Utilisateur> findByPseudo(String pseudo);

    List<Utilisateur> findAll();

    void updateUser(Utilisateur user);

    void updateByUser(Utilisateur user);

    void updatePassword(Utilisateur utilisateur);

    void deleteUser(String pseudo);

    String getConnectedUsername();
}
