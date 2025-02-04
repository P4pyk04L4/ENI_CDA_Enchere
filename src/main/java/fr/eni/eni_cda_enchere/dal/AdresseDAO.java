package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Adresse;

public interface AdresseDAO {

    Adresse getAdresse(long id);

    void insertAdresse(Adresse adresse);

    void updateAdresse(Adresse adresse);

    void deleteAdresse(Adresse adresse);
}
