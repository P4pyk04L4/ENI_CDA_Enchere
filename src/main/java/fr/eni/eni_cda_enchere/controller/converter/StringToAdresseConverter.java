package fr.eni.eni_cda_enchere.controller.converter;

import fr.eni.eni_cda_enchere.bll.AdresseService;
import fr.eni.eni_cda_enchere.bo.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringToAdresseConverter implements Converter<String, Adresse> {

    private AdresseService adresseService;

    @Autowired
    public void setAdresseService(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

    @Override
    public Adresse convert(String source) {
        return adresseService.getAdresse(Integer.parseInt(source));
    }
}
