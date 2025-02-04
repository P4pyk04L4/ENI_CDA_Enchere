package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Categorie;

import java.util.List;

public interface CategorieDAO {
    Categorie read(long no_categorie);

    List<Categorie> findAll();

    void createCategory(String libelle);

    void updateCategory(Categorie cat);

    void deleteCategory(long no_categorie);
}
