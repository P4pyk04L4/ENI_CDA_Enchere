package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Categorie;

import java.util.List;

public interface CategorieService {
    Categorie findByNoCategory(long no_categorie);
    List<Categorie> findAllCategories();
    void saveCategory(String libelle);
    void deleteCategory(long no_categorie);
    void updateCategory(Categorie cat);
}
