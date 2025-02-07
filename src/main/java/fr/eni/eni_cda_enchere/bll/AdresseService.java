package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Adresse;

import java.util.List;

public interface AdresseService {

    Adresse getAdresse(long id);

    List<Adresse> getAdresses();

    List<Adresse> getEniAdresses();

    int createAdresse(Adresse adresse);

    void updateAdresse(Adresse adresse);

    void deleteAdresse(Adresse adresse);
}
