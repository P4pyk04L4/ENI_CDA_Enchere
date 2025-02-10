package fr.eni.eni_cda_enchere.controller.converter;

import fr.eni.eni_cda_enchere.bll.CategorieService;
import fr.eni.eni_cda_enchere.bo.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {

    private CategorieService categorieService;

    @Autowired
    public void setCategorieService(CategorieService categorieService) {
        this.categorieService = categorieService;
    }


    @Override
    public Categorie convert(String source) {
        return categorieService.findByNoCategory(Integer.parseInt(source));
    }
}
