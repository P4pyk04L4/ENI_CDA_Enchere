package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.dal.AdresseDAO;
import jakarta.validation.OverridesAttribute;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseServiceImpl implements AdresseService {
    private final AdresseDAO adresseDAO;

    public AdresseServiceImpl(AdresseDAO adresseDAO) {
        this.adresseDAO = adresseDAO;
    }

    @Override
    public Adresse getAdresse(long id) {
        return adresseDAO.getAdresse(id);
    }

    @Override
    public List<Adresse> getAdresses() {
        return adresseDAO.getAdresses();
    }

    @Override
    public List<Adresse> getEniAdresses() { return adresseDAO.getEniAdresses(); }


    @Override
    public int createAdresse(Adresse adresse){
        return adresseDAO.insertAdresse(adresse);
    }

    @Override
    public void updateAdresse(Adresse adresse) {
        adresseDAO.updateAdresse(adresse);
    }

    @Override
    public void deleteAdresse(Adresse adresse) {
        adresseDAO.deleteAdresse(adresse);
    }
}
