package fr.eni.eni_cda_enchere.controller.converter;

import fr.eni.eni_cda_enchere.bll.CategorieService;
import fr.eni.eni_cda_enchere.bo.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringToListCategorieConverter implements Converter<String, List<Categorie>> {

    private CategorieService categorieService;

    @Autowired
    public void setCategorieService(CategorieService categorieService) {
        this.categorieService = categorieService;
    }


    @Override
    public List<Categorie> convert(String source) {
        List<Categorie> categorieList = new ArrayList<>();
        String[] split = source.split(",");
        for (String s : split) {
            int id = Integer.parseInt(s);
            categorieList.add(categorieService.findByNoCategory(id));
        }
        return categorieList;
    }
}
