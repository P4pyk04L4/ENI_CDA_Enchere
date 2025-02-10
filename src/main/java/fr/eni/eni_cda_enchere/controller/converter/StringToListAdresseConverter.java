package fr.eni.eni_cda_enchere.controller.converter;

import fr.eni.eni_cda_enchere.bll.AdresseService;
import fr.eni.eni_cda_enchere.bo.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringToListAdresseConverter implements Converter<String, List<Adresse>> {

    private AdresseService adresseService;

    @Autowired
    public void setAdresseService(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

    @Override
    public List<Adresse> convert(String source) {
        List<Adresse> adressesList = new ArrayList<>();
        String[] split = source.split(",");
        for (String s : split) {
            int id = Integer.parseInt(s);
            adressesList.add(adresseService.getAdresse(id));
        }
        return adressesList;
    }
}
