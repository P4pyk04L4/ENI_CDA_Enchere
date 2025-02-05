package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Adresse;

import java.util.List;

public interface AdresseDAO {

    Adresse getAdresse(long id);

    List<Adresse> getAdresses();

    int insertAdresse(Adresse adresse);

    void updateAdresse(Adresse adresse);

    void deleteAdresse(Adresse adresse);
}
