package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurDAO {

    void create(Utilisateur utilisateur);//creat

    Optional<Utilisateur> findByPseudo(String pseudo);//read

    List<Utilisateur> findAll();//readAll

    void update(Utilisateur utilisateur);//update

    void updateByUser(Utilisateur utilisateur);

    void delete(String pseudo);//delete

}
