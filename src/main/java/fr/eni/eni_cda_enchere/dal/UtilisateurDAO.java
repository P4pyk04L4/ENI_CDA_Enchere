package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurDAO {

    void INSERT(Utilisateur utilisateur);//creat

    Optional<Utilisateur> findByPseudo(String pseudo);//read

    List<Utilisateur> findAll();//readAll

    void UPDATE(Utilisateur utilisateur);//update

    void DELETE(String pseudo);//delete

}
